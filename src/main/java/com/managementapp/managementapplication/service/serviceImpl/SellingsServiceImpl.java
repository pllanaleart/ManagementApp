package com.managementapp.managementapplication.service.serviceImpl;

import com.managementapp.managementapplication.entity.SellingsEntity;
import com.managementapp.managementapplication.repository.SellingsRepository;
import com.managementapp.managementapplication.service.SellingsService;
import com.managementapp.managementapplication.shared.dto.SellingsDto;
import com.managementapp.managementapplication.ui.response.OperationStatusModel;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
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
        if (sellingsEntity.isPresent()) {
            sellingsDto = mapper.map(sellingsEntity.get(), SellingsDto.class);
        }
        return sellingsDto;
    }

    @Override
    public SellingsDto createSell(SellingsDto sellingsDto) {
        sellingsDto.setInvoiceId(null);
        sellingsDto.setDate(new Date());
        SellingsEntity sellingsEntity = sellingsRepository.save(mapper.map(sellingsDto, SellingsEntity.class));
        return mapper.map(sellingsEntity, SellingsDto.class);
    }

    @Override
    public SellingsDto updateSell(SellingsDto sellingsDto) {
       Optional<SellingsEntity> sellingsEntity = sellingsRepository.findById(sellingsDto.getId());
       SellingsEntity savedSell = new SellingsEntity();
       if(sellingsEntity.isPresent()) {
           sellingsEntity.get().setDescription(sellingsDto.getDescription());
           if (sellingsDto.getAmount() != sellingsEntity.get().getAmount()) {
               sellingsEntity.get().setAmount(sellingsDto.getAmount());
           }
           savedSell = sellingsRepository.save(sellingsEntity.get());
       }

        return mapper.map(savedSell, SellingsDto.class);

    }

    @Override
    public OperationStatusModel deleteSell(Long id, SellingsDto sellingsDto) {
        Optional<SellingsEntity> sellingsEntity = sellingsRepository.findById(id);
        if (sellingsDto.getInvoiceId() == null && sellingsEntity.isPresent()){
            sellingsRepository.delete(sellingsEntity.get());
            return new OperationStatusModel("Delete", "success");
        }else {
            return new OperationStatusModel("Not Deleted","Cannot be deleted while is an invoice registerd");
        }    }
}
