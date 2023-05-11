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
import com.managementapp.managementapplication.ui.response.InvoiceResponseModel;
import com.managementapp.managementapplication.ui.response.PoductQuantityModel;
import com.managementapp.managementapplication.ui.response.ProductResponseList;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.Buffer;
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

    @GetMapping("/{id}")
    public InvoiceResponseModel findById(@PathVariable Long id) {

        return createinvoiceResponseModel(invoiceService.findByInvoiceId(id));
    }

    @PostMapping
    public InvoiceResponseModel createInvoice(@RequestBody InvoiceRequestModel invoiceRequestModel) {
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
            double TVSHofAmount = (stockDto.getProductsDto().getPrice() * AppConstants.DEFAULT_TVSH) / 100;
            double productAmountwithTVSH = (stockDto.getProductsDto().getPrice() + TVSHofAmount) * invoiceQuantityModel.getQuantity();
            amount = amount + (stockDto.getProductsDto().getPrice() * invoiceQuantityModel.getQuantity());
            totalamount = totalamount + productAmountwithTVSH;
            BigDecimal formater = new BigDecimal(amount).setScale(2, RoundingMode.HALF_UP);
            BigDecimal formater2 = new BigDecimal(totalamount).setScale(2, RoundingMode.HALF_UP);
            amount = formater.doubleValue();
            totalamount = formater2.doubleValue();
            stockDto.setQuantity(stockDto.getQuantity() - createdProductList.getQuantity());
            stockService.updateStock(stockDto);
        }
        createdInvoice.setAmmount(amount);
        createdInvoice.setTotalForPayment(totalamount);
        createdInvoice = invoiceService.createInvoice(createdInvoice);

        return createinvoiceResponseModel(createdInvoice);
    }

    private InvoiceResponseModel createinvoiceResponseModel(InvoiceDto invoiceDto){
        InvoiceResponseModel responseModel = new InvoiceResponseModel();
        responseModel.setInvoiceNo(invoiceDto.getInvoiceNo());
        responseModel.setAmmount(invoiceDto.getAmmount());
        responseModel.setTvsh(invoiceDto.getTvsh());
        responseModel.setTotalForPayment(invoiceDto.getTotalForPayment());
        Set<PoductQuantityModel> quantityModelSet = new HashSet<>();
        for(ProductsListDto productResponseList : invoiceDto.getProductsListEntities()){
            Long productId = productResponseList.getId().getProduct_id();
            StockDto stockDto = stockService.findByProductId(productId);
            PoductQuantityModel product = new PoductQuantityModel();
            double TVSHofAmount = (stockDto.getProductsDto().getPrice() * AppConstants.DEFAULT_TVSH) / 100;
            double productAmountwithTVSH = (stockDto.getProductsDto().getPrice() + TVSHofAmount) * productResponseList.getQuantity();
            TVSHofAmount = new BigDecimal(TVSHofAmount).setScale(2,RoundingMode.HALF_UP).doubleValue();
            productAmountwithTVSH = new BigDecimal(productAmountwithTVSH).setScale(2,RoundingMode.HALF_UP).doubleValue();
            product.setName(stockDto.getProductsDto().getName());
            product.setPrice(stockDto.getProductsDto().getPrice());
            product.setProductTotal(productAmountwithTVSH);
            product.setQuantity(productResponseList.getQuantity());
            product.setTVSH(TVSHofAmount);
            quantityModelSet.add(product);
        }
        responseModel.setProducts(quantityModelSet);
        return responseModel;
    }
}
