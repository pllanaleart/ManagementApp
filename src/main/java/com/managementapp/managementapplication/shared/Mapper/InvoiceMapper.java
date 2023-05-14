package com.managementapp.managementapplication.shared.Mapper;

import com.managementapp.managementapplication.entity.InvoiceEntity;
import com.managementapp.managementapplication.entity.ProductsListEntity;
import com.managementapp.managementapplication.shared.dto.InvoiceDto;
import com.managementapp.managementapplication.shared.dto.ProductsListDto;

import java.util.HashSet;
import java.util.Set;

public class InvoiceMapper {


    public static InvoiceEntity convertToEntity(InvoiceDto dto) {
        InvoiceEntity entity = new InvoiceEntity();
        if ( dto.getId() != null) {
            entity.setId(dto.getId());
        }
        if ( dto.getInvoiceNo() != null) {
            entity.setInvoiceNo(dto.getInvoiceNo());
        }
        if (dto.getAmmount() != 0) {
            entity.setAmmount(dto.getAmmount());
        }
        if (dto.getTvsh() != 0) {
            entity.setTvsh(dto.getTvsh());
        }
        if (dto.getTotalForPayment() != 0) {
            entity.setTotalForPayment(dto.getTotalForPayment());
        }
        if (dto.getDateCreated() != null) {
            entity.setDateCreated(dto.getDateCreated());
        }

        if (dto.getProductsListDtos() != null) {
            Set<ProductsListEntity> listEntities = new HashSet<>();
            for (ProductsListDto productsListDto : dto.getProductsListDtos()) {
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
        if (entity.getAmmount() != 0) {
            dto.setAmmount(entity.getAmmount());
        }
        if (entity.getTvsh() != 0) {
            dto.setTvsh(entity.getTvsh());
        }
        if (entity.getTotalForPayment() != 0) {
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
}
