package application.service;

import application.model.Cottage;
import application.model.dto.CottageDTO;

import java.util.List;

public interface CottageService {

    List<Cottage> findAll();
    Cottage findById(Long id);
    Cottage update(CottageDTO cottageDTO);
    Cottage delete(Long id);
    Cottage save(CottageDTO cottageDTO);

}
