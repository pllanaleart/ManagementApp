package com.managementapp.managementapplication.ui.controller;

import com.managementapp.managementapplication.service.PurchasesService;
import com.managementapp.managementapplication.shared.Mapper.PurchaseResponseMapper;
import com.managementapp.managementapplication.shared.dto.ProductsDto;
import com.managementapp.managementapplication.shared.dto.PurchaseProductDto;
import com.managementapp.managementapplication.shared.dto.PurchasesDto;
import com.managementapp.managementapplication.ui.request.ProductPurchaseRequestModel;
import com.managementapp.managementapplication.ui.request.PurchaseRequestModel;
import com.managementapp.managementapplication.ui.response.purchasesResponse.PurchaseResponseModel;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/purchases")
public class PurchasesController {

    private PurchasesService purchasesService;
    private ModelMapper mapper = new ModelMapper();

    @Autowired
    public PurchasesController(PurchasesService purchasesService) {
        this.purchasesService = purchasesService;
    }

    @GetMapping("/{id}")
    public PurchaseResponseModel getPurchaseById(@PathVariable Long id){
        PurchasesDto purchasesDto = purchasesService.findPurchaseById(id);
        PurchaseResponseModel purchaseResponseModel = PurchaseResponseMapper.createPurchaseResponseModel(purchasesDto);
        return purchaseResponseModel;
    }
    @PostMapping
    public PurchaseResponseModel createPurchase(@RequestBody PurchaseRequestModel purchaseRequestModel) {
        Set<PurchaseProductDto> purchaseProductDtoSet = new HashSet<>();
        PurchasesDto purchasesDto = mapper.map(purchaseRequestModel, PurchasesDto.class);
        purchasesDto.setId(null);
        for (ProductPurchaseRequestModel productPurchaseRequestModel: purchaseRequestModel.getProducts()){
            PurchaseProductDto productDto = new PurchaseProductDto();
            ProductsDto productsDto = new ProductsDto();
            productsDto.setId(productPurchaseRequestModel.getProductId());
            productDto.setProductsDto(productsDto);
            productDto.setQuantity(productPurchaseRequestModel.getQuantity());
            productDto.setBuyPrice(productPurchaseRequestModel.getBuyPrice());
            purchaseProductDtoSet.add(productDto);
        }
        purchasesDto.setProducts(purchaseProductDtoSet);
        PurchasesDto createdPurchase = purchasesService.createPurchase(purchasesDto);
        PurchaseResponseModel purchaseResponseModel = PurchaseResponseMapper.createPurchaseResponseModel(createdPurchase);
        return purchaseResponseModel;
    }
}
