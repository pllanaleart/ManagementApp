package com.managementapp.managementapplication.service.serviceImpl;

import com.managementapp.managementapplication.embededKeys.ProductsListKey;
import com.managementapp.managementapplication.entity.InvoiceEntity;
import com.managementapp.managementapplication.entity.ProductsListEntity;
import com.managementapp.managementapplication.entity.StockEntity;
import com.managementapp.managementapplication.repository.InvoiceRepository;
import com.managementapp.managementapplication.repository.ProductsListRepository;
import com.managementapp.managementapplication.repository.StockRepository;
import com.managementapp.managementapplication.service.InvoiceService;
import com.managementapp.managementapplication.shared.Mapper.InvoiceMapper;
import com.managementapp.managementapplication.shared.dto.InvoiceDto;
import com.managementapp.managementapplication.shared.dto.ProductsListDto;
import com.managementapp.managementapplication.ui.response.InvoiceTransferList;
import com.managementapp.managementapplication.ui.response.OperationStatusModel;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class InvoiceServiceImpl implements InvoiceService {

    private InvoiceRepository invoiceRepository;
    private ProductsListRepository productsListRepository;
    private StockRepository stockRepository;
    ModelMapper mapper = new ModelMapper();

    @Autowired
    public InvoiceServiceImpl(InvoiceRepository invoiceRepository, ProductsListRepository productsListRepository, StockRepository stockRepository) {
        this.invoiceRepository = invoiceRepository;
        this.productsListRepository = productsListRepository;
        this.stockRepository = stockRepository;
    }


    @Override
    public InvoiceTransferList findAll(int page, int limit, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        List<InvoiceDto> list = new ArrayList<>();
        Pageable pageable = PageRequest.of(page, limit, sort);
        Page<InvoiceEntity> invoiceEntitiesPage = invoiceRepository.findAll(pageable);
        List<InvoiceEntity> invoiceEntities = invoiceEntitiesPage.getContent();
        List<InvoiceDto> invoiceDtos = new ArrayList<>();
        for (InvoiceEntity invoice : invoiceEntities) {
            invoiceDtos.add(InvoiceMapper.convertToDto(invoice));
        }

        return new InvoiceTransferList(invoiceDtos, page, limit, invoiceEntitiesPage.getTotalElements(),
                invoiceEntitiesPage.getTotalPages(), invoiceEntitiesPage.isLast());
    }

    @Override
    public InvoiceDto createInvoice(InvoiceDto invoiceDto) {
        if (invoiceDto.getProductsListDtos() == null) {
            InvoiceEntity invoiceEntity = InvoiceMapper.convertToEntity(invoiceDto);
            InvoiceEntity createdEntity = invoiceRepository.save(invoiceEntity);
            return InvoiceMapper.convertToDto(createdEntity);
        } else {
            InvoiceEntity invoiceEntity = InvoiceMapper.convertToEntity(invoiceDto);
            productsListRepository.saveAll(invoiceEntity.getListEntities());
            return InvoiceMapper.convertToDto(invoiceRepository.save(invoiceEntity));
        }
    }

    @Override
    public ProductsListDto createProductsList(ProductsListDto productsListDto) {
        ProductsListEntity productsListEntity = new ProductsListEntity();
        productsListEntity.setId(productsListDto.getId());
        productsListEntity.setQuantity(productsListDto.getQuantity());
        ProductsListEntity createdProductList = productsListRepository.save(productsListEntity);
        ProductsListDto returnValue = new ProductsListDto();
        returnValue.setId(createdProductList.getId());
        returnValue.setQuantity(createdProductList.getQuantity());
        return returnValue;
    }

    @Override
    public InvoiceDto findByInvoiceId(Long id) {
        Optional<InvoiceEntity> foundInvoice = invoiceRepository.findById(id);
        if (foundInvoice.isPresent()) {
            return InvoiceMapper.convertToDto(foundInvoice.get());
        } else throw new RuntimeException("Not found");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public InvoiceDto updateInvoice(InvoiceDto invoiceDto) {

        InvoiceEntity foundInvoice = invoiceRepository.findInvoiceEntityById(invoiceDto.getId());
        if (foundInvoice.getId().equals(invoiceDto.getId()) && invoiceDto.getProductsListDtos() != null) {
            for (ProductsListEntity productsList : foundInvoice.getListEntities()) {
                StockEntity stock = stockRepository.findByProductsId(productsList.getProducts().getId());
                stock.setQuantity(stock.getQuantity() + productsList.getQuantity());
                stockRepository.save(stock);
            }
            for (ProductsListDto productsListDto : invoiceDto.getProductsListDtos()) {
                StockEntity newStock = stockRepository.findByProductsId(productsListDto.getId().getProduct_id());
                if (newStock.getQuantity() < productsListDto.getQuantity()) {
                    throw new RuntimeException("Not enugh stock for product with id: " + productsListDto.getId().getProduct_id());
                }
                newStock.setQuantity(newStock.getQuantity() - productsListDto.getQuantity());
                stockRepository.save(newStock);
            }
            productsListRepository.deleteAll(foundInvoice.getListEntities());
            InvoiceMapper.combineEntityWithDto(invoiceDto, foundInvoice);
            productsListRepository.saveAll(foundInvoice.getListEntities());
        }
        InvoiceMapper.combineEntityWithDto(invoiceDto, foundInvoice);
        InvoiceEntity savedEntity = invoiceRepository.save(foundInvoice);
        return InvoiceMapper.convertToDto(savedEntity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public OperationStatusModel deleteInvoice(InvoiceDto invoiceDto) {
        InvoiceEntity invoiceEntity = InvoiceMapper.convertToEntity(invoiceDto);

        for(ProductsListEntity productsList : invoiceEntity.getListEntities()){
            StockEntity stock = stockRepository.findByProductsId(productsList.getId().getProduct_id());
            stock.setQuantity(productsList.getQuantity()+stock.getQuantity());
            stockRepository.save(stock);
            productsListRepository.delete(productsList);
        }
        invoiceRepository.deleteById(invoiceDto.getId());

        return new OperationStatusModel("Delete","Success");
    }

    @Override
    public ProductsListDto findById(ProductsListKey productsListKey) {
        Optional<ProductsListEntity> productsListEntity = productsListRepository.findById(productsListKey);
        ProductsListDto productsListDto = new ProductsListDto();
        if (productsListEntity.isPresent()) {
            productsListDto = mapper.map(productsListEntity.get(), ProductsListDto.class);
        } else throw new RuntimeException("not found");
        return productsListDto;
    }

}
