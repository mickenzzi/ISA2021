package application.model.dto;

import java.util.List;
import application.model.User;

public class CottageDTO {

	private Long id;

	private String name;
	private String address;
	private String description;
	private int numberOfRooms;
	private int numberOfBeds;
	private String rules;
	private String price;
	private String info;
	private User userCottage;
	private List<String> images;

	public Long getId() {
		return id;
	}
	
	public List<String> getImages(){
		return images;
	}
	
	public void setImages(List<String> images) {
		this.images = images;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getNumberOfRooms() {
		return numberOfRooms;
	}

	public void setNumberOfRooms(int numberOfRooms) {
		this.numberOfRooms = numberOfRooms;
	}
	
	public int getNumberOfBeds() {
		return numberOfBeds;
	}

	public void setNumberOfBeds(int numberOfBeds) {
		this.numberOfBeds = numberOfBeds;
	}

	public String getRules() {
		return rules;
	}

	public void setTerm(String rules) {
		this.rules = rules;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public User getUserCottage() {
		return userCottage;
	}

	public void setUserCottage(User userCottage) {
		this.userCottage = userCottage;
	}

	public CottageDTO() {
		super();
	}

	public CottageDTO(Long id, String name, String address, String description, int numberOfRooms, int numberOfBeds, String rules, String price,
			String info) {
		super();
		this.id = id;
		this.name = name;
		this.address = address;
		this.description = description;
		this.numberOfRooms = numberOfRooms;
		this.numberOfBeds = numberOfBeds;
		this.rules = rules;
		this.price = price;
		this.info = info;
	}
}
