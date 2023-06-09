package com.managementapp.managementapplication.service.serviceImpl;

import com.managementapp.managementapplication.entity.ProductsEntity;
import com.managementapp.managementapplication.entity.StockEntity;
import com.managementapp.managementapplication.repository.ProductsRepository;
import com.managementapp.managementapplication.repository.StockRepository;
import com.managementapp.managementapplication.service.StockService;
import com.managementapp.managementapplication.shared.dto.ProductsDto;
import com.managementapp.managementapplication.shared.dto.StockDto;
import com.managementapp.managementapplication.ui.response.OperationStatusModel;
import com.managementapp.managementapplication.ui.response.stockResponse.StockResponseList;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class StockServiceImpl implements StockService {

    StockRepository stockRepository;
    ProductsRepository productsRepository;
    ModelMapper mapper = new ModelMapper();

    public StockServiceImpl(StockRepository stockRepository, ProductsRepository productsRepository) {
        this.stockRepository = stockRepository;
        this.productsRepository = productsRepository;
    }

    @Override
    public StockResponseList getAll(int page, int limiperpage, String sortBy, String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        ArrayList<StockDto> list = new ArrayList<>();
        Pageable pageable = PageRequest.of(page, limiperpage, sort);
        Page<StockEntity> entityPage = stockRepository.findAll(pageable);
        List<StockEntity> stockEntities = entityPage.getContent();

        for (StockEntity stockEntity : stockEntities) {
            StockDto stockDto = mapper.map(stockEntity, StockDto.class);
            stockDto.setProductsDto(mapper.map(stockEntity.getProductsEntity(), ProductsDto.class));
            list.add(stockDto);
        }
        return new StockResponseList(list, page, limiperpage, entityPage.getTotalElements(),
                entityPage.getTotalPages(), entityPage.isLast());

    }

    @Override
    public StockDto createStock(StockDto stockDto) {
        StockEntity stockEntity = mapper.map(stockDto, StockEntity.class);
        Optional<ProductsEntity> productsEntity = productsRepository.findById(stockDto.getProductsDto().getId());
        productsEntity.ifPresent(productsEntity1 -> stockEntity.setProductsEntity(productsEntity.get()));
        StockEntity createdEntity = stockRepository.save(stockEntity);
        stockDto = mapper.map(createdEntity, StockDto.class);
        stockDto.setProductsDto(mapper.map(createdEntity.getProductsEntity(), ProductsDto.class));
        return stockDto;
    }

    @Override
    public StockDto findByProductId(Long id) {
        StockEntity stockEntity = stockRepository.findByProductsId(id);
        if(stockEntity == null)throw new RuntimeException("No products in that id");
        ProductsEntity productsEntity = stockEntity.getProductsEntity();
        if(productsEntity == null) throw new RuntimeException("No product asociated in stock");
        ProductsDto productsDto = mapper.map(productsEntity, ProductsDto.class);
        StockDto returnValue = mapper.map(stockEntity , StockDto.class);
        returnValue.setProductsDto(productsDto);
        return returnValue;

    }

    @Override
    public StockDto updateStock(StockDto stockDto) {

        StockEntity stockEntity = mapper.map(stockDto, StockEntity.class);
        stockEntity.setProductsEntity(mapper.map(stockDto.getProductsDto(),ProductsEntity.class));
        StockEntity stock = stockRepository.save(stockEntity);
        StockDto returnValue = mapper.map(stock,StockDto.class);
        returnValue.setProductsDto(mapper.map(stockEntity.getProductsEntity(),ProductsDto.class));
        return returnValue;
    }

    @Override
    public OperationStatusModel deleteStock(StockDto stockDto) {
        StockEntity stockEntity = mapper.map(stockDto,StockEntity.class);
        if(stockEntity.getQuantity() == 0){
            stockRepository.delete(stockEntity);
        } else {
            throw new RuntimeException("Cannot delete while you have products in stock");
        }
        return new OperationStatusModel("Delete","Success");
    }

}
