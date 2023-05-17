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
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
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
        Set<ProductsListDto> createdSet = new HashSet<>();
        for (InvoiceQuantityModel invoiceQuantityModel : invoiceRequestModel.getInvoiceQuantityModels()) {
            StockDto stockDto = stockService.findByProductId(invoiceQuantityModel.getProductId());
            if (stockDto.getQuantity() < invoiceQuantityModel.getQuantity())
                throw new RuntimeException(stockDto.getProductsDto().getName() +
                        " doesnt have stock, stock left: " + stockDto.getQuantity());
            ProductsListKey productsListKey = new ProductsListKey();
            productsListKey.setProduct_id(stockDto.getProductsDto().getId());
            ProductsListDto productsListDto = new ProductsListDto(productsListKey, invoiceQuantityModel.getQuantity());
            createdSet.add(productsListDto);
            stockDto.setQuantity(stockDto.getQuantity() - productsListDto.getQuantity());
            stockService.updateStock(stockDto);
        }
        InvoiceDto createdInvoice = invoiceService.createInvoice(invoiceDto);
        for (ProductsListDto productsListDto : createdSet) {
            productsListDto.getId().setInvoice_id(createdInvoice.getId());

        }
        createdInvoice.setProductsListDtos(createdSet);
        createdInvoice = invoiceCalculation(createdInvoice);
        createdInvoice = invoiceService.createInvoice(createdInvoice);

        return createinvoiceResponseModel(createdInvoice);
    }

    @PutMapping("/{id}")
    public InvoiceResponseModel updateInvoice(@PathVariable Long id, @RequestBody InvoiceDto updatedDto) {
        InvoiceDto foundInvoice = invoiceService.findByInvoiceId(id);
        if (foundInvoice == null) throw new RuntimeException("Invoice not found");
        updatedDto.setId(foundInvoice.getId());
        InvoiceDto updatedInvoice = new InvoiceDto();
        if (updatedDto.getProductsListDtos() == null) {
            updatedInvoice = invoiceService.updateInvoice(updatedDto);
        } else {
            invoiceCalculation(updatedDto);
            updatedInvoice= invoiceService.updateInvoice(updatedDto);
        }

        return createinvoiceResponseModel(updatedInvoice);
    }

    private InvoiceResponseModel createinvoiceResponseModel(InvoiceDto invoiceDto) {
        InvoiceResponseModel responseModel = new InvoiceResponseModel();
        responseModel.setInvoiceNo(invoiceDto.getInvoiceNo());
        responseModel.setAmmount(invoiceDto.getAmmount());
        responseModel.setTvsh(invoiceDto.getTvsh());
        responseModel.setTotalForPayment(invoiceDto.getTotalForPayment());
        Set<PoductQuantityModel> quantityModelSet = new HashSet<>();
        for (ProductsListDto productResponseList : invoiceDto.getProductsListDtos()) {
            Long productId = productResponseList.getId().getProduct_id();
            StockDto stockDto = stockService.findByProductId(productId);
            PoductQuantityModel product = new PoductQuantityModel();
            double TVSHofAmount = (stockDto.getProductsDto().getPrice() * AppConstants.DEFAULT_TVSH) / 100;
            double productAmountwithTVSH = (stockDto.getProductsDto().getPrice() + TVSHofAmount) * productResponseList.getQuantity();
            TVSHofAmount = new BigDecimal(TVSHofAmount).setScale(2, RoundingMode.HALF_UP).doubleValue();
            productAmountwithTVSH = new BigDecimal(productAmountwithTVSH).setScale(2, RoundingMode.HALF_UP).doubleValue();
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

    private InvoiceDto invoiceCalculation(InvoiceDto invoiceDto) {
        double amuntMinusTVSH = 0;
        double totalamount = 0;
        for (ProductsListDto productsListDto : invoiceDto.getProductsListDtos()) {
            StockDto stockDto = stockService.findByProductId(productsListDto.getId().getProduct_id());
            double TVSHofAmount = (stockDto.getProductsDto().getPrice() * AppConstants.DEFAULT_TVSH) / 100;
            double productAmountwithTVSH = (stockDto.getProductsDto().getPrice() + TVSHofAmount) * productsListDto.getQuantity();
            amuntMinusTVSH = amuntMinusTVSH + (stockDto.getProductsDto().getPrice() * productsListDto.getQuantity());
            totalamount = totalamount + productAmountwithTVSH;
            amuntMinusTVSH = new BigDecimal(amuntMinusTVSH).setScale(2, RoundingMode.HALF_UP).doubleValue();
            totalamount = new BigDecimal(totalamount).setScale(2, RoundingMode.HALF_UP).doubleValue();
        }
        invoiceDto.setAmmount(amuntMinusTVSH);
        invoiceDto.setTvsh(AppConstants.DEFAULT_TVSH);
        invoiceDto.setTotalForPayment(totalamount);
        return invoiceDto;
    }
}
