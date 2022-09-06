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
@Table(name = "boat_image_table")
public class BoatImage {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "url", nullable = false)
	private String url;
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "boat_id")
	private Boat cottageId;
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getImageUrl() {
		return url;
	}
	
	public void setImageUrl(String url) {
		this.url = url;
	}
	
	public Boat getCottageId() {
		return cottageId;
	}
	
	public void setCottageId(Boat boatId) {
		this.cottageId = boatId;
	}

	public BoatImage() {
		super();
	}

	public BoatImage(Long id, String url, Boat cottageId) {
		super();
		this.id = id;
		this.url = url;
		this.cottageId = cottageId;
	}
	
	
}
