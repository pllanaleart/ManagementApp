package com.managementapp.managementapplication.ui.controller;

import com.managementapp.managementapplication.service.SellingsService;
import com.managementapp.managementapplication.shared.dto.SellingsDto;
import com.managementapp.managementapplication.ui.response.OperationStatusModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/sellings")
public class SellingsController {

    private SellingsService sellingsService;
    @Autowired
    public SellingsController(SellingsService sellingsService) {
        this.sellingsService = sellingsService;
    }


    @GetMapping("/{id}")
    public SellingsDto findById(@PathVariable Long id){
        return sellingsService.findById(id);
    }

    @PostMapping
    public SellingsDto createSell(@RequestBody SellingsDto sellingsDto)
    {
        return sellingsService.createSell(sellingsDto);
    }
    @PutMapping("/{id}")
    public SellingsDto updateSell(@PathVariable Long id, @RequestBody SellingsDto sellingsDto){
        SellingsDto sellings = sellingsService.findById(id);
        if(sellings != null){
            if (sellingsDto.getDescription() != null){
                sellings.setDescription(sellings.getDescription());
            }
            if(sellingsDto.getAmount() !=0 && sellings.getInvoiceId() == null){
                sellings.setAmount(sellingsDto.getAmount());
            }
            return sellingsService.updateSell(sellings);
        }throw  new RuntimeException("Sell not found");

    }
    @DeleteMapping("/{id}")
    OperationStatusModel deleteSell(@PathVariable Long id){
        SellingsDto sellingsDto = findById(id);
        return sellingsService.deleteSell(id,sellingsDto);
    }
}
