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
@Table(name = "report_table")
public class OwnerReport {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "comment", nullable = false)
	private String comment;
	@Column(name = "is_sanctioned", nullable = false)
	private boolean sanctioned;
	@Column(name = "is_approved", nullable = false)
	private boolean approved;
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "term_id", nullable = false)
	private TerminCottage term;
	@Column(name = "missed_term", nullable = false)
	private boolean missedTerm;
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id", nullable = false)
	private User owner;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}

	public boolean isSanctioned() {
		return sanctioned;
	}
	public void setSanctioned(boolean sanctioned) {
		this.sanctioned = sanctioned;
	}
	public boolean isApproved() {
		return approved;
	}
	public void setApproved(boolean approved) {
		this.approved = approved;
	}
	public TerminCottage getTerm() {
		return term;
	}
	public void setTerm(TerminCottage term) {
		this.term = term;
	}
	
	public User getOwner() {
		return owner;
	}
	public void setOwner(User owner) {
		this.owner = owner;
	}
	public boolean isMissedTerm() {
		return missedTerm;
	}
	public void setMissedTerm(boolean missedTerm) {
		this.missedTerm = missedTerm;
	}
	public OwnerReport(Long id, String comment, boolean sanctioned, boolean approved, TerminCottage term,
			boolean missedTerm, User owner) {
		super();
		this.id = id;
		this.comment = comment;
		this.sanctioned = sanctioned;
		this.approved = approved;
		this.term = term;
		this.missedTerm = missedTerm;
		this.owner = owner;
	}
	public OwnerReport() {
		super();
		// TODO Auto-generated constructor stub
	}


	

	
	
	
	
}
