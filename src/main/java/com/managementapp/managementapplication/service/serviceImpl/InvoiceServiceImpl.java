package com.managementapp.managementapplication.service.serviceImpl;

import com.managementapp.managementapplication.entity.InvoiceEntity;
import com.managementapp.managementapplication.entity.ProductsListEntity;
import com.managementapp.managementapplication.repository.InvoiceRepository;
import com.managementapp.managementapplication.repository.ProductsListRepository;
import com.managementapp.managementapplication.service.InvoiceService;
import com.managementapp.managementapplication.shared.dto.InvoiceDto;
import com.managementapp.managementapplication.shared.dto.ProductsListDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
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
        InvoiceEntity invoiceEntity = mapper.map(invoiceDto, InvoiceEntity.class);
        InvoiceEntity createdEntity = invoiceRepository.save(invoiceEntity);
        return mapper.map(createdEntity,InvoiceDto.class);
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
    public InvoiceDto updateInvoice(InvoiceDto invoiceDto) {
        InvoiceEntity invoiceEntity = new InvoiceEntity();
        invoiceEntity.setTvsh(invoiceDto.getTvsh());
        invoiceEntity.setInvoiceNo(invoiceDto.getInvoiceNo());
        invoiceEntity.setTotalForPayment(invoiceDto.getTotalForPayment());
        invoiceEntity.setAmmount(invoiceEntity.getAmmount());
        return null;
    }
}
