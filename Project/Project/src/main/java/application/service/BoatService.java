package application.service;

import java.util.List;

import application.model.Boat;
import application.model.BoatImage;

public interface BoatService {
	List<Boat> findAll();
	Boat findById(Long id);
    void update(Boat cottage);
    void delete(Long id);
    Boat save(Boat cottage, Long id);
    List<BoatImage> findImagesByBoatId(Long id);
    BoatImage findImageById(Long id);
    void updateImage(BoatImage cottageImage);
    void deleteImage(Long id);
    boolean saveImage(String url, Long id);
    List<BoatImage> getAllImages();
}
