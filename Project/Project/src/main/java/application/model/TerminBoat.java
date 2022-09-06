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

import org.springframework.data.annotation.Version;

@Entity
@Table(name = "termin_boat_table")
public class TerminBoat {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(name = "start_date")
	private String start;
	@Column(name = "end_date")
	private String end;
	@Column(name = "action_expire")
	private String actionExpireDate;
	@Column(name = "duration")
	private int daysDuration;
	@Column(name = "reserved")
	private boolean reserved;
	@Column(name = "is_action")
	private boolean action;
	@Column(name = "price")
	private double price;
	@Column(name = "capacity")
	private int capacity;
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id")
	private User userReserved;
	
	@Version
	private int version;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "boat_id")
	private Boat cottageTermin;

	
	public User getUserReserved() {
		return userReserved;
	}

	public void setUserReserved(User userReserved) {
		this.userReserved = userReserved;
	}

	public String getActionExpireDate() {
		return actionExpireDate;
	}

	public void setActionExpireDate(String actionExpireDate) {
		this.actionExpireDate = actionExpireDate;
	}

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

	public int getDaysDuration() {
		return daysDuration;
	}

	public void setDaysDuration(int daysDuration) {
		this.daysDuration = daysDuration;
	}

	public boolean isReserved() {
		return reserved;
	}

	public void setReserved(boolean reserved) {
		this.reserved = reserved;
	}

	public Boat getCottageTermin() {
		return cottageTermin;
	}

	public void setCottageTermin(Boat cottageTermin) {
		this.cottageTermin = cottageTermin;
	}

	public boolean isAction() {
		return action;
	}

	public void setAction(boolean action) {
		this.action = action;
	}	

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	public TerminBoat() {
		super();
	}
}

