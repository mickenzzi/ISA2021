package application.service;

import java.util.List;

import application.model.Cottage;
import application.model.CottageImage;
import application.model.dto.CottageDTO;

public interface CottageService {
	List<Cottage> findAll();
    Cottage findById(Long id);
    void update(Cottage cottage);
    void delete(Long id);
    Cottage save(Cottage cottage, Long id);
    List<CottageImage> findImagesByCottageId(Long id);
    CottageImage findImageById(Long id);
    void updateImage(CottageImage cottageImage);
    void deleteImage(CottageImage cottageImage, Long id);
    boolean saveImage(String url, Long id);
}
