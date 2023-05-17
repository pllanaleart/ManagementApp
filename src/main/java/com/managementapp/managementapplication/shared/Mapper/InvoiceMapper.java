package com.managementapp.managementapplication.shared.Mapper;

import com.managementapp.managementapplication.entity.InvoiceEntity;
import com.managementapp.managementapplication.entity.ProductsListEntity;
import com.managementapp.managementapplication.shared.dto.InvoiceDto;
import com.managementapp.managementapplication.shared.dto.ProductsListDto;

import java.util.HashSet;
import java.util.Set;

public class InvoiceMapper {


    public static InvoiceEntity convertToEntity(InvoiceDto invoiceDto) {
        InvoiceEntity entity = new InvoiceEntity();
        Set<ProductsListEntity> listEntities = new HashSet<>();

        if (invoiceDto.getId() != null) {
            entity.setId(invoiceDto.getId());
        }
        if (invoiceDto.getInvoiceNo() != null) {
            entity.setInvoiceNo(invoiceDto.getInvoiceNo());
        }
        if (invoiceDto.getAmmount() != 0.0) {
            entity.setAmmount(invoiceDto.getAmmount());
        }
        if (invoiceDto.getTvsh() != 0.0) {
            entity.setTvsh(invoiceDto.getTvsh());
        }
        if (invoiceDto.getTotalForPayment() != 0.0) {
            entity.setTotalForPayment(invoiceDto.getTotalForPayment());
        }
        if (invoiceDto.getDateCreated() != null) {
            entity.setDateCreated(invoiceDto.getDateCreated());
        }

        if (invoiceDto.getProductsListDtos() != null) {
            for (ProductsListDto productsListDto : invoiceDto.getProductsListDtos()) {
                ProductsListEntity listEntity = new ProductsListEntity();
                listEntity.setId(productsListDto.getId());
                if (productsListDto.getQuantity() != 0) {
                    listEntity.setQuantity(productsListDto.getQuantity());
                }
                listEntities.add(listEntity);
            }
            entity.setListEntities(listEntities);
        }

        return entity;
    }

    public static InvoiceDto convertToDto(InvoiceEntity entity) {
        InvoiceDto dto = new InvoiceDto();
        if (entity.getId() != null) {
            dto.setId(entity.getId());
        }
        if (entity.getInvoiceNo() != null) {
            dto.setInvoiceNo(entity.getInvoiceNo());
        }
        if (entity.getAmmount() != 0.0) {
            dto.setAmmount(entity.getAmmount());
        }
        if (entity.getTvsh() != 0.0) {
            dto.setTvsh(entity.getTvsh());
        }
        if (entity.getTotalForPayment() != 0.0) {
            dto.setTotalForPayment(entity.getTotalForPayment());
        }
        if (entity.getDateCreated() != null) {
            dto.setDateCreated(entity.getDateCreated());
        }

        if (entity.getListEntities() != null) {
            Set<ProductsListDto> listDtos = new HashSet<>();
            for (ProductsListEntity listEntity : entity.getListEntities()) {
                ProductsListDto listDto = new ProductsListDto();
                listDto.setId(listEntity.getId());
                if (listEntity.getQuantity() != 0) {
                    listDto.setQuantity(listEntity.getQuantity());
                }
                listDtos.add(listDto);
            }
            dto.setProductsListDtos(listDtos);
        }

        return dto;
    }

    public static InvoiceEntity combineEntityWithDto(InvoiceDto invoiceDto, InvoiceEntity invoiceEntity) {
        InvoiceDto dto = new InvoiceDto();
        if (invoiceDto.getId() != null) {
            invoiceEntity.setId(invoiceDto.getId());
        }
        if (invoiceDto.getInvoiceNo() != null) {
            invoiceEntity.setInvoiceNo(invoiceDto.getInvoiceNo());
        }
        if (invoiceDto.getAmmount() != 0.0) {
            invoiceEntity.setAmmount(invoiceDto.getAmmount());
        }
        if (invoiceDto.getTvsh() != 0.0) {
            invoiceEntity.setTvsh(invoiceDto.getTvsh());
        }
        if (invoiceDto.getTotalForPayment() != 0.0) {
            invoiceEntity.setTotalForPayment(invoiceDto.getTotalForPayment());
        }
        if (invoiceDto.getDateCreated() != null) {
            invoiceEntity.setDateCreated(invoiceDto.getDateCreated());
        }

        if (invoiceDto.getProductsListDtos() != null) {
            Set<ProductsListEntity> listDtos = new HashSet<>();
            for (ProductsListDto productsListDto : invoiceDto.getProductsListDtos()) {
                ProductsListEntity listDto = new ProductsListEntity();
                listDto.setId(productsListDto.getId());
                if (productsListDto.getQuantity() != 0) {
                    listDto.setQuantity(productsListDto.getQuantity());
                }
                listDtos.add(listDto);
            }
            invoiceEntity.setListEntities(listDtos);
        }

        return invoiceEntity;
    }
}
