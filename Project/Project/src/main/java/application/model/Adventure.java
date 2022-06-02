package application.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "adventure_table")
public class Adventure {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(name = "title")
	private String title;
	@Column(name = "address")
	private String address;
	@Column(name = "instructor_biography")
	private String instructorBiography;
	@Column(name = "image")
	private String image;
	@Column(name = "max_number")
	private int maxNumber;
	@Column(name = "rule")
	private String rule;
	@Column(name = "equipment")
	private String equipment;
	@Column(name = "price_list")
	private double priceList;
	@Column(name = "cancel_condition")
	private String cancelCondition;
	@Column(name="description")
	private String description;
	@Column(name="reserved")
	private boolean reserved;
	@Column(name="average_grade")
	private String avgGrade;
	
	
	@OneToMany(mappedBy="adventureImage", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JsonIgnore
	private List<Image> images;
	

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id")
	private User userAdventure;
	
	@OneToMany(mappedBy = "adventureTermin", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JsonIgnore
	private List<Termin> termins;
	
	@OneToMany(mappedBy = "adventureReservation", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JsonIgnore
	private List<Reservation> reservations;
	
	@OneToMany(mappedBy = "adventureReview", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JsonIgnore
	private List<Review> reviews;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getInstructorBiography() {
		return instructorBiography;
	}

	public void setInstructorBiography(String instructorBiography) {
		this.instructorBiography = instructorBiography;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public int getMaxNumber() {
		return maxNumber;
	}

	public void setMaxNumber(int maxNumber) {
		this.maxNumber = maxNumber;
	}
	
	public List<Image> getImages(){
		return images;
	}
	
	public void setImages(List<Image> images) {
		this.images = images;
	}

	public String getRule() {
		return rule;
	}

	public void setRule(String rule) {
		this.rule = rule;
	}

	public String getEquipment() {
		return equipment;
	}

	public void setEquipment(String equipment) {
		this.equipment = equipment;
	}

	public double getPriceList() {
		return priceList;
	}

	public void setPriceList(double priceList) {
		this.priceList = priceList;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}

	public String getCancelCondition() {
		return cancelCondition;
	}

	public void setCancelCondition(String cancelCondition) {
		this.cancelCondition = cancelCondition;
	}

	public User getUserAdventure() {
		return userAdventure;
	}

	public void setUserAdventure(User userAdventure) {
		this.userAdventure = userAdventure;
	}

	public List<Termin> getTermins() {
		return termins;
	}

	public void setTermins(List<Termin> termins) {
		this.termins = termins;
	}

	public List<Reservation> getReservations() {
		return reservations;
	}

	public void setReservations(List<Reservation> reservations) {
		this.reservations = reservations;
	}

	public List<Review> getReviews() {
		return reviews;
	}

	public void setReviews(List<Review> reviews) {
		this.reviews = reviews;
	}

	public boolean isReserved() {
		return reserved;
	}

	public void setReserved(boolean reserved) {
		this.reserved = reserved;
	}

	public String getAvgGrade() {
		return avgGrade;
	}

	public void setAvgGrade(String avgGrade) {
		this.avgGrade = avgGrade;
	}

	public Adventure() {
		super();
	}

}
