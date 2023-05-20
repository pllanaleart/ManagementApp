package com.managementapp.managementapplication.service.serviceImpl;

import com.managementapp.managementapplication.entity.SellingsEntity;
import com.managementapp.managementapplication.repository.SellingsRepository;
import com.managementapp.managementapplication.service.SellingsService;
import com.managementapp.managementapplication.shared.dto.SellingsDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import java.util.Optional;

@Service
public class SellingsServiceImpl implements SellingsService {

    private SellingsRepository sellingsRepository;
    private ModelMapper mapper = new ModelMapper();

    @Autowired
    public SellingsServiceImpl(SellingsRepository sellingsRepository) {
        this.sellingsRepository = sellingsRepository;
    }


    @Override
    public SellingsDto findById(Long id) {
        Optional<SellingsEntity> sellingsEntity = sellingsRepository.findById(id);
        SellingsDto sellingsDto = new SellingsDto();
        if(sellingsEntity.isPresent()){
            sellingsDto = mapper.map(sellingsEntity.get(),SellingsDto.class);
        }
        return sellingsDto;
    }
}
