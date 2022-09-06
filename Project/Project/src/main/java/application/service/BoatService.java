package application.service;

import java.util.List;

import application.model.Boat;
import application.model.BoatImage;
import application.model.EntitySubscriber;

public interface BoatService {
	List<Boat> findAll();
	Boat findById(Long id);
    void update(Boat cottage);
    void delete(Long id);
    Boat save(Boat cottage, Long id);
    List<BoatImage> findImagesByCottageId(Long id);
    BoatImage findImageById(Long id);
    void updateImage(BoatImage cottageImage);
    void deleteImage(Long id);
    boolean saveImage(String url, Long id);
    List<BoatImage> getAllImages();
    List<Boat> findOwnerCottages(Long id);
    void subscribe (Long cottageId, Long userId);
    void unsubscribe (Long cottageId, Long userId);
    List<EntitySubscriber> findAllSubsByCottage(Long cottageId);
}
