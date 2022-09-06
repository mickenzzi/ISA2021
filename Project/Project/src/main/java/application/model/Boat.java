package application.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Entity
@Table(name = "boat_table")
public class Boat {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "name", nullable = false)
	private String name;
	@Column(name = "type", nullable = false)
	private String type;
	@Column(name = "lenght", nullable = false)
	private String lenght;
	@Column(name = "engine_number", nullable = false)
	private String engineNumber;
	@Column(name = "engine_power", nullable = false)
	private String enginePower;
	@Column(name = "max_speed", nullable = false)
	private String maxSpeed;
	@Column(name = "navigation_equipment", nullable = false)
	private String navigationEquipment;
	@Column(name = "address", nullable = false)
	private String address;
	@Column(name = "description", nullable = false)
	private String description;
	@Column(name = "image", nullable = false)
	private String image;
	@Column(name = "capacity", nullable = false)
	private String capacity;
	@Column(name = "rules", nullable = false)
	private String rules;
	@Column(name = "fishing_equipment", nullable = false)
	private String fishingEquipment;
	@Column(name = "price", nullable = false)
	private double price;
	@Column(name = "info", nullable = false)
	private String info;
	@Column(name = "cancel_terms")
	private String cancelTerms;
	@Column(name = "latitude")
	private double latitude;
	@Column(name = "longitude")
	private double longitude;
	@Column(name = "reserved")
	private boolean reserved;
	//fetch - Eager koristimo kada su nam neophodni podaci i povezanih tabela 
	//preporuka je koristiti Lazy kad god mozemo
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id")
	private User userBoat;
	public Long getId() {
		return id;
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
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getLenght() {
		return lenght;
	}
	public void setLenght(String lenght) {
		this.lenght = lenght;
	}
	public String getEngineNumber() {
		return engineNumber;
	}
	public void setEngineNumber(String engineNumber) {
		this.engineNumber = engineNumber;
	}
	public String getEnginePower() {
		return enginePower;
	}
	public void setEnginePower(String enginePower) {
		this.enginePower = enginePower;
	}
	public String getMaxSpeed() {
		return maxSpeed;
	}
	public void setMaxSpeed(String maxSpeed) {
		this.maxSpeed = maxSpeed;
	}
	public String getNavigationEquipment() {
		return navigationEquipment;
	}
	public void setNavigationEquipment(String navigationEquipment) {
		this.navigationEquipment = navigationEquipment;
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
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getCapacity() {
		return capacity;
	}
	public void setCapacity(String capacity) {
		this.capacity = capacity;
	}
	public String getRules() {
		return rules;
	}
	public void setRules(String rules) {
		this.rules = rules;
	}
	public String getFishingEquipment() {
		return fishingEquipment;
	}
	public void setFishingEquipment(String fishingEquipment) {
		this.fishingEquipment = fishingEquipment;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	public String getCancelTerms() {
		return cancelTerms;
	}
	public void setCancelTerms(String cancelTerms) {
		this.cancelTerms = cancelTerms;
	}
	public double getLatitude() {
		return latitude;
	}
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	public double getLongitude() {
		return longitude;
	}
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	public User getUserBoat() {
		return userBoat;
	}
	public void setUserBoat(User userBoat) {
		this.userBoat = userBoat;
	}
	
	public Boat() {
		super();
	}
	
	public Boat(Long id, String name, String type, String lenght, String engineNumber, String enginePower,
			String maxSpeed, String navigationEquipment, String address, String description, String image,
			String capacity, String rules, String fishingEquipment, double price, String info, String cancelTerms,
			double latitude, double longitude, User userBoat) {
		super();
		this.id = id;
		this.name = name;
		this.type = type;
		this.lenght = lenght;
		this.engineNumber = engineNumber;
		this.enginePower = enginePower;
		this.maxSpeed = maxSpeed;
		this.navigationEquipment = navigationEquipment;
		this.address = address;
		this.description = description;
		this.image = image;
		this.capacity = capacity;
		this.rules = rules;
		this.fishingEquipment = fishingEquipment;
		this.price = price;
		this.info = info;
		this.cancelTerms = cancelTerms;
		this.latitude = latitude;
		this.longitude = longitude;
		this.userBoat = userBoat;
	}

	
	

}
