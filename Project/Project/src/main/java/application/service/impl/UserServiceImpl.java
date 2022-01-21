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
		request.setDeleted(true);
		SimpleMailMessage mail = new SimpleMailMessage();
		mail.setTo(user.getEmail());
		mail.setFrom(env.getProperty("spring.mail.username"));
		mail.setSubject("Verifikacija naloga");
		mail.setText("Vas nalog je verifikovan.");
		javaMailSender.send(mail);
		requestRepository.save(request);
		userRepository.save(user);
	}

	@Override
	public void disableUser(Long userId, Long requestId, String text) {
		User user = userRepository.findById(userId).orElseGet(null);
		user.setEnabled(false);
		Request request = requestRepository.findById(requestId).orElseGet(null);
		request.setDeleted(true);
		SimpleMailMessage mail = new SimpleMailMessage();
		mail.setTo(user.getEmail());
		mail.setFrom(env.getProperty("spring.mail.username"));
		mail.setSubject("Verifikacija naloga");
		mail.setText(text);
		javaMailSender.send(mail);
		requestRepository.save(request);
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
			Role role = roleRepository.findByName("ROLE_ADMIN");
			user.setUserRole(role);
		} else if (userDTO.getRole().equals("INSTRUCTOR")) {
			Role role = roleRepository.findByName("ROLE_INSTRUCTOR");
			user.setUserRole(role);
		} else if (userDTO.getRole().equals("USER")) {
			Role role = roleRepository.findByName("ROLE_USER");
			user.setUserRole(role);
		} else if (userDTO.getRole().equals("COTTAGE_OWNER")) {
			Role role = roleRepository.findByName("ROLE_COTTAGE_OWNER");
			user.setUserRole(role);
		} else if (userDTO.getRole().equals("BOAT_OWNER")) {
			Role role = roleRepository.findByName("ROLE_BOAT_OWNER");
			user.setUserRole(role);
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
			user.setDeleted(false);
			System.out.println("Uloga:" + userDTO.getRole());
			if (userDTO.getRole().equals("ADMIN")) {
				Role role = roleRepository.findByName("ROLE_ADMIN");
				user.setUserRole(role);
			} else if (userDTO.getRole().equals("INSTRUCTOR")) {
				Role role = roleRepository.findByName("ROLE_INSTRUCTOR");
				user.setUserRole(role);
			} else if (userDTO.getRole().equals("USER")) {
				Role role = roleRepository.findByName("ROLE_USER");
				user.setUserRole(role);
			} else if (userDTO.getRole().equals("COTTAGE_OWNER")) {
				Role role = roleRepository.findByName("ROLE_COTTAGE_OWNER");
				user.setUserRole(role);
			} else if (userDTO.getRole().equals("BOAT_OWNER")) {
				Role role = roleRepository.findByName("ROLE_BOAT_OWNER");
				user.setUserRole(role);
			}
			user.setFirstTimeLogged(false);
			user.setPenalty(0);
			userRepository.save(user);
			Request request = new Request();
			request.setTitle("Zahtev za verifikaciju naloga");
			request.setUsername(userDTO.getUsername());
			request.setUserRequest(admin);
			request.setDeleted(false);
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
		request.setDeleted(true);
		SimpleMailMessage mail = new SimpleMailMessage();
		mail.setTo(user.getEmail());
		mail.setFrom(env.getProperty("spring.mail.username"));
		mail.setSubject("Brisanje naloga");
		mail.setText(text);
		javaMailSender.send(mail);
		requestRepository.save(request);
		user.setDeleted(true);
		userRepository.save(user);

	}

	@Override
	public void rejectDeleteRequest(Long userId, Long requestId, String text) {
		User user = userRepository.findById(userId).orElseGet(null);
		user.setDeleted(false);
		Request request = requestRepository.findById(requestId).orElseGet(null);
		request.setDeleted(true);
		SimpleMailMessage mail = new SimpleMailMessage();
		mail.setTo(user.getEmail());
		mail.setFrom(env.getProperty("spring.mail.username"));
		mail.setSubject("Brisanje naloga");
		mail.setText(text);
		javaMailSender.send(mail);
		requestRepository.save(request);
		user.setDeleted(false);
		userRepository.save(user);
	}

	@Override
	public boolean createAction(Long instructorId, Long adventureId, Termin term) throws ParseException {
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
			termin1.setAction(true);
			termin1.setReserved(false);
			User instructor = userRepository.findById(instructorId).orElseGet(null);
			SimpleMailMessage mail = new SimpleMailMessage();
			mail.setFrom(instructor.getEmail());
			List<User> users = new ArrayList<>();
			List<User> users1 = new ArrayList<>();
			users1 = userRepository.findAll();
			for (User u : users1) {
				if (u.getUserRole().getName().equals("ROLE_USER")) {
					users.add(u);
				}
			}
			for (User u : users) {
				mail.setTo(u.getEmail());
				mail.setSubject("Akcija-avantura");
				mail.setText("Kreirana je akcija koja nudi razne pogodnosti prilikom rezervisanja avanture");
				javaMailSender.send(mail);
			}
			terminRepository.save(termin1);
		}

		return free;
	}

}
