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
@Table(name = "entity_subscriber_table")
public class EntitySubscriber {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id")
	private User subscriber;
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "boat_id")
	private Boat boat;
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "cottage_id")
	private Cottage cottage;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public User getSubscriber() {
		return subscriber;
	}
	public void setSubscriber(User subscriber) {
		this.subscriber = subscriber;
	}
	public Boat getBoat() {
		return boat;
	}
	public void setBoat(Boat boat) {
		this.boat = boat;
	}
	public Cottage getCottage() {
		return cottage;
	}
	public void setCottage(Cottage cottage) {
		this.cottage = cottage;
	}
	
	public EntitySubscriber() {
		super();
	}
	
	public EntitySubscriber(Long id, User subscriber, Boat boat, Cottage cottage) {
		super();
		this.id = id;
		this.subscriber = subscriber;
		this.boat = boat;
		this.cottage = cottage;
	}



}
	