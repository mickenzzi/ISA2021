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
	private Boat boatId;
	
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
	
	public Boat getBoatId() {
		return boatId;
	}
	
	public void setBoatId(Boat boatId) {
		this.boatId = boatId;
	}

	public BoatImage() {
		super();
	}

	public BoatImage(Long id, String url, Boat boatId) {
		super();
		this.id = id;
		this.url = url;
		this.boatId = boatId;
	}
	
	
}
