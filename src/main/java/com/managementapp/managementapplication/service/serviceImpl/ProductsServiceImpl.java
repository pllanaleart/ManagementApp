package com.managementapp.managementapplication.service.serviceImpl;

import com.managementapp.managementapplication.entity.ProductsEntity;
import com.managementapp.managementapplication.repository.ProductsRepository;
import com.managementapp.managementapplication.service.ProductsService;
import com.managementapp.managementapplication.shared.dto.ProductsDto;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductsServiceImpl implements ProductsService {
    ProductsRepository productsRepository;
    ModelMapper mapper = new ModelMapper();

    public ProductsServiceImpl(ProductsRepository productsRepository) {
        this.productsRepository = productsRepository;
    }

    @Override
    public List<ProductsDto> getAll(int page, int limitperpage) {
        List<ProductsDto> list = new ArrayList<>();
        org.springframework.data.domain.Pageable pageable = PageRequest.of(page,limitperpage);
        Page<ProductsEntity> productsEntitiesPage = productsRepository.findAll(pageable);
        List<ProductsEntity> productsEntities= productsEntitiesPage.getContent();
        for (ProductsEntity productsEntity:
             productsEntities) {
            list.add(mapper.map(productsEntity, ProductsDto.class));
        }

        return list;
    }
}
