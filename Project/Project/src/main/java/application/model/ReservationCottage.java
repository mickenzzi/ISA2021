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
@Table(name = "reservation_cottage_table")
public class ReservationCottage {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(name = "start_date")
	private String start;
	@Column(name = "end_date")
	private String end;
	@Column(name="is_approved")
	private boolean approved;
	@Column(name="price")
	private double price;
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "adventure_id")
	private Cottage cottageReservation;
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id")
	private User userReservation;
	
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
	public Cottage getCottageReservation() {
		return cottageReservation;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public void setAdventureReservation(Cottage cottageReservation) {
		this.cottageReservation = cottageReservation;
	}
	public User getUserReservation() {
		return userReservation;
	}
	public void setUserReservation(User userReservation) {
		this.userReservation = userReservation;
	}
	public boolean isApproved() {
		return approved;
	}
	public void setApproved(boolean approved) {
		this.approved = approved;
	}
	
	public ReservationCottage() {
		super();
	}
}
