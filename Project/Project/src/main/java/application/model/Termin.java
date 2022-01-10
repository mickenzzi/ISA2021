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
@Table(name = "termin_table")
public class Termin {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(name = "startDate")
	private String start;
	@Column(name = "endDate")
	private String end;
	@Column(name = "duration")
	private double duration;
	@Column(name = "reserved")
	private boolean reserved;
	
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "adventure_id")
	private Adventure adventureTermin;
	
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

	public double getDuration() {
		return duration;
	}

	public void setDuration(double duration) {
		this.duration = duration;
	}
	
	public boolean isReserved() {
		return reserved;
	}

	public void setReserved(boolean reserved) {
		this.reserved = reserved;
	}
	public Adventure getAdventureTermin() {
		return adventureTermin;
	}

	public void setAdventureTermin(Adventure adventureTermin) {
		this.adventureTermin = adventureTermin;
	}
	
	public Termin() {
		super();
	}

}
