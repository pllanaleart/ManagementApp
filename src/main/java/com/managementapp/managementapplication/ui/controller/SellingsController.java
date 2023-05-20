package com.managementapp.managementapplication.ui.controller;

import com.managementapp.managementapplication.service.SellingsService;
import com.managementapp.managementapplication.shared.dto.SellingsDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
