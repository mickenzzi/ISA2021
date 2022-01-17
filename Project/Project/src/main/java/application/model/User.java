package application.model;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "user_table")
public class User implements UserDetails {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(name = "first_name", nullable = false)
	private String firstName;
	@Column(name = "last_name", nullable = false)
	private String lastName;
	@Column(name = "address", nullable = false)
	private String address;
	@Column(name = "city", nullable = false)
	private String city;
	@Column(name = "country", nullable = false)
	private String country;
	@Column(name = "phone", nullable = false)
	private String phone;
	@Column(name = "email", nullable = false)
	private String email;
	@Column(name = "username")
	private String username;
	@Column(name = "password", nullable = false)
	private String password;
	@Column(name = "enabled")
	private boolean enabled;
	@Column(name = "deleted")
	private boolean deleted;
	@Column(name = "last_password_reset_date")
	private Timestamp lastPasswordResetDate;
	@Column(name = "role")
	private String role;
	@Column(name = "first_time_logged")
	private boolean firstTimeLogged;
	@Column(name = "penalty")
	private int penalty;

	// fetch - Lazy koristimo kada zelimo samo podatke tabele u kojoj se nalazimo a
	// ne podatke i povezanih tabela
	// mapped by string predstavlja naziv objekta u klasama sa kojima je user
	// povezan
	@OneToMany(mappedBy = "userCottage", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JsonIgnore
	private List<Cottage> cottages;
	@OneToMany(mappedBy = "userRequest", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JsonIgnore
	private List<Request> requests;
	@OneToMany(mappedBy = "userAdventure", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JsonIgnore
	private List<Adventure> adventures;

	@OneToMany(mappedBy = "userReservation", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JsonIgnore
	private List<Reservation> reservations;

	@OneToMany(mappedBy = "userReview", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JsonIgnore
	private List<Review> userReviews;

	@OneToMany(mappedBy = "instructorReview", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JsonIgnore
	private List<Review> instructorReviews;

	@OneToMany(mappedBy = "adminReview", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JsonIgnore
	private List<Review> adminReviews;

	@OneToMany(mappedBy = "userComment", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JsonIgnore
	private List<Comment> userComments;

	@OneToMany(mappedBy = "instructorComment", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JsonIgnore
	private List<Comment> instructorComments;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public List<Cottage> getCottages() {
		return cottages;
	}

	public void setCottages(List<Cottage> cottages) {
		this.cottages = cottages;
	}

	public Timestamp getLastPasswordResetDate() {
		return lastPasswordResetDate;
	}

	public void setLastPasswordResetDate(Timestamp lastPasswordResetDate) {
		this.lastPasswordResetDate = lastPasswordResetDate;
	}

	public void setPassword(String password) {
		Timestamp now = new Timestamp(new Date().getTime());
		this.setLastPasswordResetDate(now);
		this.password = password;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public List<Request> getRequests() {
		return requests;
	}

	public void setRequests(List<Request> requests) {
		this.requests = requests;
	}

	public boolean isFirstTimeLogged() {
		return firstTimeLogged;
	}

	public void setFirstTimeLogged(boolean firstTimeLogged) {
		this.firstTimeLogged = firstTimeLogged;
	}

	public List<Adventure> getAdventures() {
		return adventures;
	}

	public void setAdventures(List<Adventure> adventures) {
		this.adventures = adventures;
	}

	public List<Reservation> getReservations() {
		return reservations;
	}

	public void setReservations(List<Reservation> reservations) {
		this.reservations = reservations;
	}

	public List<Review> getUserReviews() {
		return userReviews;
	}

	public void setUserReviews(List<Review> userReviews) {
		this.userReviews = userReviews;
	}

	public List<Review> getInstructorReviews() {
		return instructorReviews;
	}

	public void setInstructorReviews(List<Review> instructorReviews) {
		this.instructorReviews = instructorReviews;
	}

	public List<Review> getAdminReviews() {
		return adminReviews;
	}

	public void setAdminReviews(List<Review> adminReviews) {
		this.adminReviews = adminReviews;
	}

	public int getPenalty() {
		return penalty;
	}

	public void setPenalty(int penalty) {
		this.penalty = penalty;
	}

	public List<Comment> getUserComments() {
		return userComments;
	}

	public void setUserComments(List<Comment> userComments) {
		this.userComments = userComments;
	}

	public List<Comment> getInstructorComments() {
		return instructorComments;
	}

	public void setInstructorComments(List<Comment> instructorComments) {
		this.instructorComments = instructorComments;
	}

	public User() {
		super();
	}

	public User(Long id, String firstName, String lastName, String address, String city, String country, String phone,
			String email, String password, String role) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.address = address;
		this.city = city;
		this.country = country;
		this.phone = phone;
		this.email = email;
		this.password = password;
		this.role = role;
	}

	@JsonIgnore
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return null;
	}

	@JsonIgnore
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@JsonIgnore
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@JsonIgnore
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return enabled;
	}

}
