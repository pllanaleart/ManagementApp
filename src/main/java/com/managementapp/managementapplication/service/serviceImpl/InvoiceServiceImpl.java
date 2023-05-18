package com.managementapp.managementapplication.service.serviceImpl;

import com.managementapp.managementapplication.embededKeys.ProductsListKey;
import com.managementapp.managementapplication.entity.InvoiceEntity;
import com.managementapp.managementapplication.entity.ProductsListEntity;
import com.managementapp.managementapplication.repository.InvoiceRepository;
import com.managementapp.managementapplication.repository.ProductsListRepository;
import com.managementapp.managementapplication.service.InvoiceService;
import com.managementapp.managementapplication.shared.AppConstants;
import com.managementapp.managementapplication.shared.Mapper.InvoiceMapper;
import com.managementapp.managementapplication.shared.dto.InvoiceDto;
import com.managementapp.managementapplication.shared.dto.ProductsListDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class InvoiceServiceImpl implements InvoiceService {

    private InvoiceRepository invoiceRepository;
    private ProductsListRepository productsListRepository;
    ModelMapper mapper = new ModelMapper();

    @Autowired
    public InvoiceServiceImpl(InvoiceRepository invoiceRepository, ProductsListRepository productsListRepository) {
        this.invoiceRepository = invoiceRepository;
        this.productsListRepository = productsListRepository;
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
    public InvoiceDto updateInvoice(InvoiceDto invoiceDto) {

        InvoiceEntity foundInvoice = invoiceRepository.findInvoiceEntityById(invoiceDto.getId());

        if(foundInvoice.getId().equals(invoiceDto.getId()) && invoiceDto.getProductsListDtos() != null){
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
