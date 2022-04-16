package application.model;

import java.util.List;

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
@Table(name = "cottage_table")
public class Cottage {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "name", nullable = false)
	private String name;
	@Column(name = "address", nullable = false)
	private String address;
	@Column(name = "description", nullable = false)
	private String description;
	//@Column(name = "pictures", nullable = false)
	//private List<String> pictures;
	@Column(name = "room", nullable = false)
	private String room;
	@Column(name = "term", nullable = false)
	private String term;
	@Column(name = "price", nullable = false)
	private String price;
	@Column(name = "info", nullable = false)
	private String info;
	@Column(name = "termin", nullable = false)
	private String termin;
	//fetch - Eager koristimo kada su nam neophodni podaci i povezanih tabela 
	//preporuka je koristiti Lazy kad god mozemo
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id")
	private User userCottage;

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

	public String getRoom() {
		return room;
	}

	public void setRoom(String room) {
		this.room = room;
	}

	public String getTerm() {
		return term;
	}

	public void setTerm(String term) {
		this.term = term;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public String getTermin() {
		return termin;
	}

	public void setTermin(String termin) {
		this.termin = termin;
	}

	public User getUserCottage() {
		return userCottage;
	}

	public void setUserCottage(User userCottage) {
		this.userCottage = userCottage;
	}

	public Cottage() {
		super();
	}

	public Cottage(Long id, String name, String address, String description, String room, String term, String price,
			String info, String termin) {
		super();
		this.id = id;
		this.name = name;
		this.address = address;
		this.description = description;
		this.room = room;
		this.term = term;
		this.price = price;
		this.info = info;
		this.termin = termin;
	}
}
