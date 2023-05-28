package com.managementapp.managementapplication.shared.Mapper;

import com.managementapp.managementapplication.entity.ProductsEntity;
import com.managementapp.managementapplication.entity.PurchaseProductEntity;
import com.managementapp.managementapplication.entity.PurchasesEntity;
import com.managementapp.managementapplication.shared.dto.PurchaseProductDto;
import com.managementapp.managementapplication.shared.dto.PurchasesDto;
import org.modelmapper.ModelMapper;

import java.util.HashSet;
import java.util.Set;

public class PurchaseMapper {

    private static ModelMapper mapper = new ModelMapper();

    public static PurchasesEntity combineToEntity(PurchasesEntity purchasesEntity, PurchasesDto purchasesDto){
        Set<PurchaseProductEntity> purchaseProductEntitySet = new HashSet<>();

        if(purchasesDto.getId()!= null){
            purchasesEntity.setId(purchasesDto.getId());
        }
        if(purchasesDto.getDescription() != null){
            purchasesEntity.setDescription(purchasesDto.getDescription());
        }
        if(purchasesDto.getDate() != null){
            purchasesEntity.setDate(purchasesDto.getDate());
        }
        if(purchasesDto.getBuyInvoiceId()!= null){
            purchasesEntity.setBuyInvoiceNumber(purchasesDto.getBuyInvoiceId());
        }
        if(purchasesDto.getProducts()!=null){
            for (PurchaseProductDto productDto: purchasesDto.getProducts()){
                PurchaseProductEntity purchaseProductEntity = new PurchaseProductEntity();
                if (productDto.getId() != null){
                    purchaseProductEntity.setId(productDto.getId());
                }
                if(productDto.getQuantity() != null){
                    purchaseProductEntity.setQuantity(productDto.getQuantity());
                }
                if(productDto.getBuyPrice() != 0){
                    purchaseProductEntity.setBuyPrice(productDto.getBuyPrice());
                }
                if(productDto.getProductsDto() != null){
                    purchaseProductEntity.setProducts(mapper.map(productDto.getProductsDto(), ProductsEntity.class));
                }
                if(productDto.getPurchasesDto() != null){
                    purchaseProductEntity.setPurchases(mapper.map(productDto.getPurchasesDto(),PurchasesEntity.class));
                }
                purchaseProductEntitySet.add(purchaseProductEntity);
            }
            purchasesEntity.setProducts(purchaseProductEntitySet);
        }
        return purchasesEntity;
    }
}
