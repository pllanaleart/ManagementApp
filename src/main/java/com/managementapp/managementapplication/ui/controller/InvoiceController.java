package com.managementapp.managementapplication.ui.controller;

import com.managementapp.managementapplication.embededKeys.ProductsListKey;
import com.managementapp.managementapplication.service.InvoiceService;
import com.managementapp.managementapplication.service.StockService;
import com.managementapp.managementapplication.shared.AppConstants;
import com.managementapp.managementapplication.shared.dto.InvoiceDto;
import com.managementapp.managementapplication.shared.dto.ProductsListDto;
import com.managementapp.managementapplication.shared.dto.StockDto;
import com.managementapp.managementapplication.ui.request.InvoiceQuantityModel;
import com.managementapp.managementapplication.ui.request.InvoiceRequestModel;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


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

    @GetMapping("/{id}")
    public InvoiceDto findById(@PathVariable Long id){

        return invoiceService.findByInvoiceId(id);
    }

    @PostMapping
    public InvoiceDto createInvoice(@RequestBody InvoiceRequestModel invoiceRequestModel) {
        InvoiceDto invoiceDto = mapper.map(invoiceRequestModel, InvoiceDto.class);
        InvoiceDto createdInvoice = invoiceService.createInvoice(invoiceDto);
        createdInvoice.setTvsh(AppConstants.DEFAULT_TVSH);
        double amount = 0;
        double totalamount = 0;
        for (InvoiceQuantityModel invoiceQuantityModel : invoiceRequestModel.getInvoiceQuantityModels()) {
            StockDto stockDto = stockService.findByProductId(invoiceQuantityModel.getProductId());
            if (stockDto.getQuantity() < invoiceQuantityModel.getQuantity())
                throw new RuntimeException(stockDto.getProductsDto().getName() +
                        " doesnt have stock, stock left: " + stockDto.getQuantity());
            ProductsListKey productsListKey = new ProductsListKey(createdInvoice.getId(), stockDto.getProductsDto().getId());
            ProductsListDto productsListDto = new ProductsListDto(productsListKey, invoiceQuantityModel.getQuantity());
            ProductsListDto createdProductList = invoiceService.createProductsList(productsListDto);
            amount = amount + stockDto.getProductsDto().getPrice();
            double TVSHofAmount = (stockDto.getProductsDto().getPrice() * 1.16) - stockDto.getProductsDto().getPrice();
            totalamount = totalamount + TVSHofAmount;
            stockDto.setQuantity(stockDto.getQuantity() - createdProductList.getQuantity());
            stockService.updateStock(stockDto);
        }
        createdInvoice.setAmmount(amount);
        createdInvoice.setTotalForPayment(totalamount);

        return invoiceService.createInvoice(createdInvoice);
    }
}
