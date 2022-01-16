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
@Table(name = "review_table")
public class Review {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(name = "comment")
	private String comment;
	@Column(name = "grade")
	private int grade;
	@Column(name = "enabled")
	private boolean enabled;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id")
	private User userReview;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "admin_id")
	private User adminReview;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "instructor_id")
	private User instructorReview;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "adventure_id")
	private Adventure adventureReview;

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

	public int getGrade() {
		return grade;
	}

	public void setGrade(int grade) {
		this.grade = grade;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public User getUserReview() {
		return userReview;
	}

	public void setUserReview(User userReview) {
		this.userReview = userReview;
	}

	public User getAdminReview() {
		return adminReview;
	}

	public void setAdminReview(User adminReview) {
		this.adminReview = adminReview;
	}

	public User getInstructorReview() {
		return instructorReview;
	}

	public void setInstructorReview(User instructorReview) {
		this.instructorReview = instructorReview;
	}

	public Adventure getAdventureReview() {
		return adventureReview;
	}

	public void setAdventureReview(Adventure adventureReview) {
		this.adventureReview = adventureReview;
	}

	public Review() {
		super();
	}

}
