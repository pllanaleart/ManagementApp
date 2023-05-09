package com.managementapp.managementapplication.ui.controller;

import com.managementapp.managementapplication.embededKeys.ProductsListKey;
import com.managementapp.managementapplication.repository.InvoiceRepository;
import com.managementapp.managementapplication.repository.ProductsListRepository;
import com.managementapp.managementapplication.service.InvoiceService;
import com.managementapp.managementapplication.service.ProductsService;
import com.managementapp.managementapplication.service.StockService;
import com.managementapp.managementapplication.shared.AppConstants;
import com.managementapp.managementapplication.shared.dto.InvoiceDto;
import com.managementapp.managementapplication.shared.dto.ProductsDto;
import com.managementapp.managementapplication.shared.dto.ProductsListDto;
import com.managementapp.managementapplication.shared.dto.StockDto;
import com.managementapp.managementapplication.ui.request.InvoiceQuantityModel;
import com.managementapp.managementapplication.ui.request.InvoiceRequestModel;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/invoices")
public class InvoiceController {

    private InvoiceService invoiceService;
    private StockService stockService;
    private ModelMapper mapper = new ModelMapper();

    @Autowired
    public InvoiceController(InvoiceService invoiceService, StockService stockService) {
        this.invoiceService = invoiceService;
        this.stockService = stockService;
    }

    @PostMapping
    public InvoiceDto createInvoice(@RequestBody InvoiceRequestModel invoiceRequestModel){
        InvoiceDto invoiceDto =mapper.map(invoiceRequestModel, InvoiceDto.class);
        InvoiceDto createdInvoice = invoiceService.createInvoice(invoiceDto);
        createdInvoice.setTvsh(AppConstants.DEFAULT_TVSH);
        Set<ProductsListDto> productsListDtoSet = new HashSet<>();
        double amount=0;
        double totalamount=0;
        for(InvoiceQuantityModel invoiceQuantityModel: invoiceRequestModel.getInvoiceQuantityModels()){
            StockDto stockDto = stockService.findByProductId(invoiceQuantityModel.getProductId());
            if(stockDto.getQuantity()<invoiceQuantityModel.getQuantity())throw new RuntimeException(stockDto.getProductsDto().getName()+
                    " doesnt have stock, stock left: "+stockDto.getQuantity());
            ProductsListKey productsListKey = new ProductsListKey(createdInvoice.getId(),stockDto.getProductsDto().getId());
            ProductsListDto productsListDto = new ProductsListDto(productsListKey , invoiceQuantityModel.getQuantity());
            ProductsListDto createdProductList = invoiceService.createProductsList(productsListDto);
            productsListDtoSet.add(createdProductList);
            amount = amount + stockDto.getProductsDto().getPrice();
            double amountminusTVSH = (stockDto.getProductsDto().getPrice() * 1.16)-stockDto.getProductsDto().getPrice();
            totalamount=totalamount+amountminusTVSH;
            stockDto.setQuantity(stockDto.getQuantity()-createdProductList.getQuantity());

        }
        createdInvoice.setAmmount(amount);
        createdInvoice.setTotalForPayment(totalamount);
        createdInvoice.setProductsListEntities(productsListDtoSet);

        return createdInvoice;
//        double totalamount=0;
//        double amount = 0;
//        Set<ProductsListDto> productsListDtoSet=new HashSet<>();
//        for (InvoiceQuantityModel quantityModel: invoiceRequestModel.getInvoiceQuantityModels()
//             ) {
//            StockDto stockDto = stockService.findByProductId(quantityModel.getProductId());
//            if(stockDto.getQuantity()<quantityModel.getQuantity())throw new RuntimeException(stockDto.getProductsDto().getName()+
//                    " doesnt have stock, stock left: "+stockDto.getQuantity());
//            ProductsListKey productsListKey = new ProductsListKey(invoiceDto.getId(),quantityModel.getProductId());
//            ProductsListDto productsListDto = new ProductsListDto(productsListKey , quantityModel.getQuantity());
//            productsListDtoSet.add(productsListDto);
//            amount = amount + stockDto.getProductsDto().getPrice();
//            double amountminusTVSH = (stockDto.getProductsDto().getPrice() * 1.16)-stockDto.getProductsDto().getPrice();
//            totalamount=totalamount+amountminusTVSH;
//        }
//        invoiceDto.setAmmount(amount);
//        invoiceDto.setTotalForPayment(totalamount);
//        invoiceDto.setProductsListEntities(productsListDtoSet);
//        return invoiceService.createInvoice(invoiceDto);
    }
}
