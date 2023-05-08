package com.managementapp.managementapplication.service.serviceImpl;

import com.managementapp.managementapplication.entity.ProductsEntity;
import com.managementapp.managementapplication.repository.ProductsRepository;
import com.managementapp.managementapplication.service.ProductsService;
import com.managementapp.managementapplication.shared.dto.ProductsDto;
import com.managementapp.managementapplication.ui.response.OperationStatusModel;
import com.managementapp.managementapplication.ui.response.ProductResponseList;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductsServiceImpl implements ProductsService {
    ProductsRepository productsRepository;
    ModelMapper mapper = new ModelMapper();

    @Autowired
    public ProductsServiceImpl(ProductsRepository productsRepository) {
        this.productsRepository = productsRepository;
    }

    @Override
    public ProductResponseList getAll(int page, int limitperpage, String sortBy,String sortDir) {
        Sort sort= sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        List<ProductsDto> list = new ArrayList<>();
        org.springframework.data.domain.Pageable pageable = PageRequest.of(page, limitperpage, sort);
        Page<ProductsEntity> productsEntitiesPage = productsRepository.findAll(pageable);
        List<ProductsEntity> productsEntities = productsEntitiesPage.getContent();
        for (ProductsEntity productsEntity :
                productsEntities) {
            list.add(mapper.map(productsEntity, ProductsDto.class));
        }
        ProductResponseList productResponseList = new ProductResponseList(
                list,
                page,
                limitperpage,
                productsEntitiesPage.getTotalElements(),
                productsEntitiesPage.getTotalPages(),
                productsEntitiesPage.isLast()
        );
        return productResponseList;
    }

    @Override
    public ProductsDto findById(Long id) {
        Optional<ProductsEntity> productsEntity = productsRepository.findById(id);
        if (productsEntity.isEmpty()) throw new RuntimeException("Not found");
        return mapper.map(productsEntity, ProductsDto.class);
    }

    @Override
    public ProductsDto createProduct(ProductsDto productsDto) {

        ProductsEntity productsEntity = mapper.map(productsDto, ProductsEntity.class);
        ProductsEntity createdEntity = productsRepository.save(productsEntity);
        return mapper.map(createdEntity, ProductsDto.class);
    }

    @Override
    public ProductsDto updateProduct(Long id, ProductsDto productsDto) {
        ProductsDto productFound = findById(id);
        ProductsEntity productsEntity = mapper.map(productFound, ProductsEntity.class);
        if (productsDto.getName() != null) {
            productsEntity.setName(productsDto.getName());
        }
        if (productsDto.getDescription() != null) {
            productsEntity.setDescription(productsDto.getDescription());
        }
        if (productsDto.getMrp() != null) {
            productsEntity.setMrp(productsDto.getMrp());
        }
        if (productsDto.getPrice() != 0) {
            productsEntity.setPrice(productsDto.getPrice());
        }
        if (productsDto.getBarcodeNumber() != null) {
            productsEntity.setBarcodeNumber(productsDto.getBarcodeNumber());
        }
        ProductsEntity productsCreated = productsRepository.save(productsEntity);
        return mapper.map(productsCreated, ProductsDto.class);
    }

    @Override
    public OperationStatusModel deleteProduct(Long id) {
        ProductsDto productToDelete = findById(id);
        ProductsEntity productsEntity = mapper.map(productToDelete, ProductsEntity.class);
        productsRepository.delete(productsEntity);
        return new OperationStatusModel("delete", "success");
    }
}
