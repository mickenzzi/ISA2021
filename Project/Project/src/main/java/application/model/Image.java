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
@Table(name = "image_table")
public class Image {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(name = "url")
	private String url;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "adventure_id")
	private Adventure adventureImage;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Adventure getAdventureImage() {
		return adventureImage;
	}

	public void setAdventureImage(Adventure adventureImage) {
		this.adventureImage = adventureImage;
	}

	public Image() {
		super();
	}

}
