package application.service.impl;

import application.model.Cottage;
import application.model.dto.CottageDTO;
import application.repository.CottageRepository;
import application.service.CottageService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class CottageServiceImpl implements CottageService {

    @Autowired
    CottageRepository cottageRepository;

    @Override
    public List<Cottage> findAll() {
        return cottageRepository.findAll();
    }

    @Override
    public Cottage findById(Long id) {
        return cottageRepository.findById(id).orElseGet(null);
    }

    @Override
    public Cottage update(CottageDTO cottageDTO) {
        return null;
    }

    @Override
    public Cottage delete(Long id) {
        return null;
    }

    @Override
    public Cottage save(CottageDTO cottageDTO) {
        return cottageRepository.save(new Cottage(cottageDTO));
    }
}
