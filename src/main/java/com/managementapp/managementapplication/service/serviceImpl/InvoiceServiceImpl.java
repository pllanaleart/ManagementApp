package com.managementapp.managementapplication.service.serviceImpl;

import com.managementapp.managementapplication.embededKeys.ProductsListKey;
import com.managementapp.managementapplication.entity.InvoiceEntity;
import com.managementapp.managementapplication.entity.ProductsListEntity;
import com.managementapp.managementapplication.entity.StockEntity;
import com.managementapp.managementapplication.repository.InvoiceRepository;
import com.managementapp.managementapplication.repository.ProductsListRepository;
import com.managementapp.managementapplication.repository.StockRepository;
import com.managementapp.managementapplication.service.InvoiceService;
import com.managementapp.managementapplication.service.StockService;
import com.managementapp.managementapplication.shared.AppConstants;
import com.managementapp.managementapplication.shared.Mapper.InvoiceMapper;
import com.managementapp.managementapplication.shared.dto.InvoiceDto;
import com.managementapp.managementapplication.shared.dto.ProductsListDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class InvoiceServiceImpl implements InvoiceService {

    private InvoiceRepository invoiceRepository;
    private ProductsListRepository productsListRepository;
    private StockRepository stockRepository;
    ModelMapper mapper = new ModelMapper();

    @Autowired
    public InvoiceServiceImpl(InvoiceRepository invoiceRepository, ProductsListRepository productsListRepository,StockRepository stockRepository) {
        this.invoiceRepository = invoiceRepository;
        this.productsListRepository = productsListRepository;
        this.stockRepository = stockRepository;
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
        if(foundInvoice.getId().equals(invoiceDto.getId()) && invoiceDto.getProductsListDtos() != null){
            for(ProductsListEntity productsList : foundInvoice.getListEntities()){
                StockEntity stock = stockRepository.findByProductsId(productsList.getProducts().getId());
                stock.setQuantity(stock.getQuantity() + productsList.getQuantity());
                stockRepository.save(stock);
            }
            for(ProductsListDto productsListDto : invoiceDto.getProductsListDtos()){
                StockEntity newStock = stockRepository.findByProductsId(productsListDto.getId().getProduct_id());
                if(newStock.getQuantity()<productsListDto.getQuantity()) {
                    throw new RuntimeException("Not enugh stock for product with id: " + productsListDto.getId().getProduct_id());
                }
                newStock.setQuantity(newStock.getQuantity() - productsListDto.getQuantity());
                stockRepository.save(newStock);
            }
            productsListRepository.deleteAll(foundInvoice.getListEntities());
            InvoiceMapper.combineEntityWithDto(invoiceDto,foundInvoice);
            productsListRepository.saveAll(foundInvoice.getListEntities());
        }
        InvoiceMapper.combineEntityWithDto(invoiceDto,foundInvoice);
        InvoiceEntity savedEntity = invoiceRepository.save(foundInvoice);
        return InvoiceMapper.convertToDto(savedEntity);
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
