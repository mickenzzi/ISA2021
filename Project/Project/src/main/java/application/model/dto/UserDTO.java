package application.model.dto;

import application.model.User;

public class UserDTO {
	private Long id;
	private String firstName;
	private String lastName;
	private String address;
	private String city;
	private String country;
	private String phone;
	private String email;
	private String password1;
	private String password2;
	private String username;
	private String role;

	private int penalty;

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

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword1() {
		return password1;
	}

	public void setPassword1(String password1) {
		this.password1 = password1;
	}
	
	public String getRole() {
		return role;
	}

	public void setRol(String role) {
		this.role = role;
	}

	public String getPassword2() {
		return password2;
	}

	public void setPassword2(String password2) {
		this.password2 = password2;
	}
	
	public int getPenalty() {
		return penalty;
	}

	public void setPenalty(int penalty) {
		this.penalty = penalty;
	}

	public UserDTO() {
		super();
	}

	public UserDTO(User user) {
		this(user.getId(), user.getFirstName(), user.getLastName(), user.getAddress(), user.getCity(),
				user.getCountry(), user.getPhone(), user.getEmail(), user.getUsername(), user.getPassword());
	}

	public UserDTO(Long id, String firstName, String lastName, String address, String city, String country,
			String phone, String email, String username, String password1) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.address = address;
		this.city = city;
		this.country = country;
		this.phone = phone;
		this.email = email;
		this.username = username;
		this.password1 = password1;
	}

}
