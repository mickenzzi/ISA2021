package application.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;

import application.model.Adventure;
import application.model.Loyalty;
import application.model.Request;
import application.model.Role;
import application.model.Termin;
import application.model.User;
import application.model.dto.UserDTO;
import application.repository.RequestRepository;
import application.repository.RoleRepository;
import application.repository.TerminRepository;
import application.repository.UserRepository;
import application.repository.AdventureRepository;
import application.repository.LoyaltyRepository;
import application.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private RequestRepository requestRepository;
	@Autowired
	private TerminRepository terminRepository;
	@Autowired
	private AdventureRepository adventureRepository;
	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private LoyaltyRepository loyaltyRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private JavaMailSender javaMailSender;
	@Autowired
	private Environment env;

	@Override
	public User findById(Long id) throws AccessDeniedException {
		return userRepository.findById(id).orElseGet(null);
	}

	@Override
	public User findByUsername(String username) throws UsernameNotFoundException {
		return userRepository.findByUsername(username);
	}

	@Override
	public List<User> findAll() throws AccessDeniedException {
		return userRepository.findAll();
	}

	@Override
	public User save(User user) {
		return userRepository.save(user);
	}

	@Override
	public void enableUser(Long userId, Long requestId) {
		User user = userRepository.findById(userId).orElseGet(null);
		user.setEnabled(true);
		user.setFirstTimeLogged(true);
		Request request = requestRepository.findById(requestId).orElseGet(null);
		SimpleMailMessage mail = new SimpleMailMessage();
		mail.setTo(user.getEmail());
		mail.setFrom(env.getProperty("spring.mail.username"));
		mail.setSubject("Verifikacija naloga");
		mail.setText("Vas nalog je verifikovan.");
		javaMailSender.send(mail);
		requestRepository.delete(request);
		userRepository.save(user);
	}

	@Override
	public void disableUser(Long userId, Long requestId, String text) {
		User user = userRepository.findById(userId).orElseGet(null);
		user.setEnabled(true);
		Request request = requestRepository.findById(requestId).orElseGet(null);
		SimpleMailMessage mail = new SimpleMailMessage();
		mail.setTo(user.getEmail());
		mail.setFrom(env.getProperty("spring.mail.username"));
		mail.setSubject("Verifikacija naloga");
		mail.setText(text);
		javaMailSender.send(mail);
		requestRepository.delete(request);
		userRepository.save(user);
	}

	@Override
	public void deleteUser(Long userId) {
		User user = userRepository.findById(userId).orElseGet(null);
		userRepository.delete(user);
	}

	@Override
	public void updateUser(UserDTO userDTO) {
		User user = userRepository.findById(userDTO.getId()).orElseGet(null);
		user.setFirstName(userDTO.getFirstName());
		user.setLastName(userDTO.getLastName());
		user.setAddress(userDTO.getAddress());
		user.setCity(userDTO.getCity());
		user.setCountry(userDTO.getCountry());
		user.setPhone(userDTO.getPhone());
		user.setUsername(userDTO.getUsername());
		user.setEmail(userDTO.getEmail());
		user.setPassword(passwordEncoder.encode(userDTO.getPassword1()));
		if (userDTO.getRole().equals("ADMIN")) {
			List<Role> roles = roleRepository.findByName("ROLE_ADMIN");
			user.setRoles(roles);
		} else if (userDTO.getRole().equals("INSTRUCTOR")) {
			List<Role> roles = roleRepository.findByName("ROLE_INSTRUCTOR");
			user.setRoles(roles);
		} else if (userDTO.getRole().equals("USER")) {
			List<Role> roles = roleRepository.findByName("ROLE_USER");
			user.setRoles(roles);
		} else if (userDTO.getRole().equals("COTTAGE_OWNER")) {
			List<Role> roles = roleRepository.findByName("ROLE_COTTAGE_OWNER");
			user.setRoles(roles);
		} else if (userDTO.getRole().equals("BOAT_OWNER")) {
			List<Role> roles = roleRepository.findByName("ROLE_BOAT_OWNER");
			user.setRoles(roles);
		}

		user.setPenalty(userDTO.getPenalty());
		user.setFirstTimeLogged(false);
		userRepository.save(user);
	}

	@Override
	public User createUser(UserDTO userDTO) {
		boolean check = true;
		User admin = userRepository.findByUsername("mickenzi");
		SimpleMailMessage mail = new SimpleMailMessage();
		mail.setTo(userDTO.getEmail());
		mail.setFrom(admin.getEmail());
		mail.setSubject("Verifikacija naloga");
		mail.setText("Korisnik " + userDTO.getUsername() + " je podneo zahtev za kreiranje naloga.");
		try {
			javaMailSender.send(mail);
			check = true;
		} catch (Exception e) {
			check = false;
		}
		if (check == true) {
			User user = new User();
			user.setFirstName(userDTO.getFirstName());
			user.setLastName(userDTO.getLastName());
			user.setAddress(userDTO.getAddress());
			user.setCity(userDTO.getCity());
			user.setCountry(userDTO.getCountry());
			user.setPhone(userDTO.getPhone());
			user.setUsername(userDTO.getUsername());
			user.setEmail(userDTO.getEmail());
			user.setPassword(passwordEncoder.encode(userDTO.getPassword1()));
			user.setEnabled(false);
			user.setMember(false);
			if (userDTO.getRole().equals("ADMIN")) {
				List<Role> roles = roleRepository.findByName("ROLE_ADMIN");
				user.setRoles(roles);
			} else if (userDTO.getRole().equals("INSTRUCTOR")) {
				List<Role> roles = roleRepository.findByName("ROLE_INSTRUCTOR");
				user.setRoles(roles);
			} else if (userDTO.getRole().equals("USER")) {
				List<Role> roles = roleRepository.findByName("ROLE_USER");
				user.setRoles(roles);
			} else if (userDTO.getRole().equals("COTTAGE_OWNER")) {
				List<Role> roles = roleRepository.findByName("ROLE_COTTAGE_OWNER");
				user.setRoles(roles);
			} else if (userDTO.getRole().equals("BOAT_OWNER")) {
				List<Role> roles = roleRepository.findByName("ROLE_BOAT_OWNER");
				user.setRoles(roles);
			}
			user.setFirstTimeLogged(false);
			user.setPenalty(0);
			user.setLoyaltyStatus("BRONZE");
			user.setCollectedPoints(0);
			userRepository.save(user);
			Request request = new Request();
			request.setTitle("Zahtev za verifikaciju naloga");
			request.setUsername(userDTO.getUsername());
			request.setUserRequest(admin);
			requestRepository.save(request);
			return user;
		} else {
			return null;
		}
	}

	@Override
	public void approveDeleteRequest(Long userId, Long requestId, String text) {
		User user = userRepository.findById(userId).orElseGet(null);
		Request request = requestRepository.findById(requestId).orElseGet(null);
		SimpleMailMessage mail = new SimpleMailMessage();
		mail.setTo(user.getEmail());
		mail.setFrom(env.getProperty("spring.mail.username"));
		mail.setSubject("Brisanje naloga");
		mail.setText(text);
		javaMailSender.send(mail);
		requestRepository.delete(request);
		userRepository.delete(user);

	}

	@Override
	public void rejectDeleteRequest(Long userId, Long requestId, String text) {
		User user = userRepository.findById(userId).orElseGet(null);
		Request request = requestRepository.findById(requestId).orElseGet(null);
		SimpleMailMessage mail = new SimpleMailMessage();
		mail.setTo(user.getEmail());
		mail.setFrom(env.getProperty("spring.mail.username"));
		mail.setSubject("Brisanje naloga");
		mail.setText(text);
		javaMailSender.send(mail);
		requestRepository.delete(request);
	}

	@Override
	public boolean createAction(Long instructorId, Long adventureId, Termin term, Double price, Long capacity)
			throws ParseException {
		boolean free;
		free = true;
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyy HH:mm:ss");
		List<Adventure> adventures = new ArrayList<Adventure>();
		List<Adventure> allAdventures = new ArrayList<Adventure>();
		allAdventures = adventureRepository.findAll();
		for (Adventure a : allAdventures) {
			if (a.getUserAdventure().getId() == instructorId) {
				adventures.add(a);
			}
		}
		List<Termin> usedTermin = new ArrayList<Termin>();
		List<Termin> usedAdventureTermin = new ArrayList<Termin>();
		usedTermin = terminRepository.findAll();
		for (Termin t : usedTermin) {
			for (Adventure a : adventures) {
				if (t.getAdventureTermin().getId() == a.getId()) {
					usedAdventureTermin.add(t);
				}
			}

		}

		Date startDate = dateFormat.parse(term.getStart());
		Date endDate = dateFormat.parse(term.getEnd());

		for (Termin t1 : usedAdventureTermin) {
			Date dmin = dateFormat.parse(t1.getStart());
			Date dmax = dateFormat.parse(t1.getEnd());
			if ((startDate.compareTo(dmin) >= 0 && endDate.compareTo(dmax) <= 0)
					|| (startDate.compareTo(dmin) >= 0 && startDate.compareTo(dmax) <= 0)
					|| (endDate.compareTo(dmin) >= 0 && endDate.compareTo(dmax) <= 0)) {
				free = false;
				break;
			}
		}
		if (free == true) {
			Termin termin1 = new Termin();
			Adventure adventure1 = adventureRepository.findById(adventureId).orElseGet(null);
			adventure1.setReserved(true);
			adventureRepository.save(adventure1);
			termin1.setAdventureTermin(adventure1);
			termin1.setStart(term.getStart());
			termin1.setEnd(term.getEnd());
			termin1.setDuration(term.getDuration());
			termin1.setPrice(price);
			termin1.setCapacity(capacity);
			termin1.setAction(true);
			termin1.setReserved(false);
			User instructor = userRepository.findById(instructorId).orElseGet(null);
			termin1.setInstructorTermin(instructor);
			SimpleMailMessage mail = new SimpleMailMessage();
			mail.setFrom(instructor.getEmail());
			List<User> users = new ArrayList<>();
			List<User> users1 = new ArrayList<>();
			users1 = userRepository.findAll();
			for (User u : users1) {
				if (u.getRoles().get(0).getName().equals("ROLE_USER")) {
					users.add(u);
				}
			}
			for (User u : users) {
				if(u.isMember()) {
					mail.setTo(u.getEmail());
					mail.setSubject("Akcija-avantura");
					mail.setText("Kreirana je akcija koja nudi razne pogodnosti prilikom rezervisanja avanture");
					javaMailSender.send(mail);
				}
			}
			terminRepository.save(termin1);
		}

		return free;
	}

	@Override
	public String convertRole(String role) {
		String finalRole = "";
		if (role.equals("ROLE_ADMIN")) {
			finalRole = "Admin";
		} else if (role.equals("ROLE_INSTRUCTOR")) {
			finalRole = "Instruktor pecanja";
		} else if (role.equals("ROLE_USER")) {
			finalRole = "Klijent";
		} else if (role.equals("ROLE_BOAT_OWNER")) {
			finalRole = "Vlasnik broda";
		} else {
			finalRole = "Vlasnik vikendice";
		}
		return finalRole;
	}

	@Override
	public void updateLoyaltyStatus(String name, int points) {
		Loyalty loyalty = loyaltyRepository.findByName(name);
		loyalty.setName(name);
		loyalty.setPoints(points);
		loyaltyRepository.save(loyalty);
		Loyalty bronze = loyaltyRepository.findByName("BRONZE");
		Loyalty silver = loyaltyRepository.findByName("SILVER");
		Loyalty gold = loyaltyRepository.findByName("GOLD");
		List<User> users = userRepository.findAll();
		for(User u: users) {
			if(u.getCollectedPoints() >= bronze.getPoints() && u.getCollectedPoints() < silver.getPoints()) {
				u.setLoyaltyStatus(bronze.getName());
				userRepository.save(u);
			}
			else if(u.getCollectedPoints() >= silver.getPoints() && u.getCollectedPoints() < gold.getPoints()) {
				u.setLoyaltyStatus(silver.getName());
				userRepository.save(u);
			}
			else if(u.getCollectedPoints() >= gold.getPoints()) {
				u.setLoyaltyStatus(gold.getName());
				userRepository.save(u);
			}
		}
	}

	@Override
	public Loyalty findGold() {
		Loyalty loyalty = loyaltyRepository.findByName("GOLD");
		return loyalty;
	}

	@Override
	public Loyalty findSilver() {
		Loyalty loyalty = loyaltyRepository.findByName("SILVER");
		return loyalty;
	}

	@Override
	public Loyalty findBronze() {
		Loyalty loyalty = loyaltyRepository.findByName("BRONZE");
		return loyalty;
	}

}
