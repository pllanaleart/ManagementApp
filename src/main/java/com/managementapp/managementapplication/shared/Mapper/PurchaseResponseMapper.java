package com.managementapp.managementapplication.shared.Mapper;

import com.managementapp.managementapplication.shared.dto.PurchaseProductDto;
import com.managementapp.managementapplication.shared.dto.PurchasesDto;
import com.managementapp.managementapplication.ui.response.purchasesResponse.PurchaseProductsResponseList;
import com.managementapp.managementapplication.ui.response.purchasesResponse.PurchaseResponseModel;

import java.util.HashSet;
import java.util.Set;

public class PurchaseResponseMapper {

    public static PurchaseResponseModel createPurchaseResponseModel(PurchasesDto purchasesDto){
        PurchaseResponseModel purchaseResponseModel = new PurchaseResponseModel();
        purchaseResponseModel.setId(purchasesDto.getId());
        purchaseResponseModel.setDescription(purchasesDto.getDescription());
        purchaseResponseModel.setDate(purchasesDto.getDate());
        purchaseResponseModel.setBuyInvoiceId(purchasesDto.getBuyInvoiceId());
        Set<PurchaseProductsResponseList> purchaseProductsResponseLists = new HashSet<>();
        for (PurchaseProductDto purchaseProductDto: purchasesDto.getProducts()){
            PurchaseProductsResponseList productsResponseList = new PurchaseProductsResponseList();
            productsResponseList.setProductName(purchaseProductDto.getProductsDto().getName());
            productsResponseList.setQuantity(purchaseProductDto.getQuantity().intValue());
            productsResponseList.setProductBuyPrice(purchaseProductDto.getBuyPrice());
            purchaseProductsResponseLists.add(productsResponseList);
        }
        purchaseResponseModel.setProductsResponseLists(purchaseProductsResponseLists);
        return purchaseResponseModel;
    }
}
