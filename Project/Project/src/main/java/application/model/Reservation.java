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
@Table(name = "reservation_table")
public class Reservation {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(name = "start_date")
	private String start;
	@Column(name = "end_date")
	private String end;
	@Column(name="is_created")
	private boolean createdReservation; 
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "adventure_id")
	private Adventure adventureReservation;
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id")
	private User userReservation;
	private double price;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getStart() {
		return start;
	}
	public void setStart(String start) {
		this.start = start;
	}
	public String getEnd() {
		return end;
	}
	public void setEnd(String end) {
		this.end = end;
	}
	public Adventure getAdventureReservation() {
		return adventureReservation;
	}
	public void setAdventureReservation(Adventure adventureReservation) {
		this.adventureReservation = adventureReservation;
	}
	public User getUserReservation() {
		return userReservation;
	}
	public void setUserReservation(User userReservation) {
		this.userReservation = userReservation;
	}
	
	public boolean isCreatedReservation() {
		return createdReservation;
	}
	public void setCreatedReservation(boolean createdReservation) {
		this.createdReservation = createdReservation;
	}
	
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public Reservation() {
		super();
	}
	
	
}
