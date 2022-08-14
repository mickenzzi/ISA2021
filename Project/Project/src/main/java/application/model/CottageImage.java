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
@Table(name = "cottage_image_table")
public class CottageImage {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "url", nullable = false)
	private String url;
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "cottage_id")
	private Cottage cottageId;
	
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
	
	public Cottage getCottageId() {
		return cottageId;
	}
	
	public void setCottageId(Cottage cottageId) {
		this.cottageId = cottageId;
	}

	public CottageImage() {
		super();
	}

	public CottageImage(Long id, String url, Cottage cottageId) {
		super();
		this.id = id;
		this.url = url;
		this.cottageId = cottageId;
	}
	
	
}
