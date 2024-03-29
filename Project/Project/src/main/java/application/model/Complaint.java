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
@Table(name = "complaint_table")
public class Complaint {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(name = "content")
	private String content;
	@Column(name= "answered")
	private boolean answered;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id")
	private User userComplaint;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "instructor_id")
	private User instructorComplaint;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "admin_id")
	private User adminComplaint;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public User getUserComplaint() {
		return userComplaint;
	}

	public void setUserComplaint(User userComplaint) {
		this.userComplaint = userComplaint;
	}

	public User getInstructorComplaint() {
		return instructorComplaint;
	}

	public void setInstructorComplaint(User instructorComplaint) {
		this.instructorComplaint = instructorComplaint;
	}
	
	public User getAdminComplaint() {
		return adminComplaint;
	}

	public void setAdminComplaint(User adminComplaint) {
		this.adminComplaint = adminComplaint;
	}

	public boolean isAnswered() {
		return answered;
	}

	public void setAnswered(boolean answered) {
		this.answered = answered;
	}

	public Complaint() {
		super();
	}

}
