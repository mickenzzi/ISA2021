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
import application.model.Termin;
import application.model.User;
import application.model.dto.UserDTO;
import application.repository.RequestRepository;
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
	public void disableUser(Long userId, Long requestId) {
		User user = userRepository.findById(userId).orElseGet(null);
		user.setEnabled(false);
		Request request = requestRepository.findById(requestId).orElseGet(null);
		request.setDeleted(true);
		SimpleMailMessage mail = new SimpleMailMessage();
		mail.setTo(user.getEmail());
		mail.setFrom(env.getProperty("spring.mail.username"));
		mail.setSubject("Verifikacija naloga");
		mail.setText("Vas nalog nije prosao veirifikaciju.");
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
		user.setRole(userDTO.getRole());
		user.setFirstTimeLogged(false);
		userRepository.save(user);
	}

	@Override
	public User createUser(UserDTO userDTO) {
		 User admin = new User();
		 admin = userRepository.findByUsername("mickenzi");
		 SimpleMailMessage mail = new SimpleMailMessage();
		 mail.setTo("isaproject0@gmail.com");
		 mail.setFrom(env.getProperty("spring.mail.username"));
		 mail.setSubject("Verifikacija naloga");
		 mail.setText("Korisnik "+userDTO.getUsername()+" je podneo zahtev za kreiranje naloga.");
		 javaMailSender.send(mail);
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
		 user.setRole(userDTO.getRole());
		 user.setFirstTimeLogged(false);
		 userRepository.save(user);
		 Request request = new Request();
		 request.setTitle("Zahtev za verifikaciju naloga");
		 request.setUsername(userDTO.getUsername());
		 request.setUserRequest(admin);
		 request.setDeleted(false);
		 requestRepository.save(request);
		 return user;
	}

	@Override
	public void approveDeleteRequest(Long userId, Long requestId) {
		User user = userRepository.findById(userId).orElseGet(null);
		Request request = requestRepository.findById(requestId).orElseGet(null);
		request.setDeleted(true);
		SimpleMailMessage mail = new SimpleMailMessage();
		mail.setTo(user.getEmail());
		mail.setFrom(env.getProperty("spring.mail.username"));
		mail.setSubject("Brisanje naloga");
		mail.setText("Vas zahtev za brisanje naloga je odobren.");
		javaMailSender.send(mail);
		requestRepository.save(request);
		user.setDeleted(true);		
		userRepository.save(user);
		
	}

	@Override
	public void rejectDeleteRequest(Long userId, Long requestId) {
		User user = userRepository.findById(userId).orElseGet(null);
		user.setDeleted(false);
		Request request = requestRepository.findById(requestId).orElseGet(null);
		request.setDeleted(true);
		SimpleMailMessage mail = new SimpleMailMessage();
		mail.setTo(user.getEmail());
		mail.setFrom(env.getProperty("spring.mail.username"));
		mail.setSubject("Brisanje naloga");
		mail.setText("Vas zahtev za brisanje naloga nije odobren.");
		javaMailSender.send(mail);
		requestRepository.save(request);
		user.setDeleted(false);		
		userRepository.save(user);
	}

	@Override
	public boolean createAction(Long instructorId, Long adventureId, Termin term) throws ParseException{
		boolean free;
		free = true;
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyy HH:mm:ss");
		List<Adventure> adventures = new ArrayList<Adventure>();
		List<Adventure> allAdventures = new ArrayList<Adventure>();
		allAdventures = adventureRepository.findAll();
		for(Adventure a:allAdventures) {
			if(a.getUserAdventure().getId() == instructorId) {
				adventures.add(a);
			}
		}
		List<Termin> usedTermin = new ArrayList<Termin>();
		List<Termin> usedAdventureTermin = new ArrayList<Termin>();
		usedTermin = terminRepository.findAll();
		for(Termin t: usedTermin) {
			for(Adventure a: adventures) {
				if(t.getAdventureTermin().getId() == a.getId()) {
					usedAdventureTermin.add(t);
				}
			}
			
		}
		
		Date startDate = dateFormat.parse(term.getStart());
		Date endDate = dateFormat.parse(term.getEnd());
		
		for(Termin t1: usedAdventureTermin) {
			Date dmin = dateFormat.parse(t1.getStart());
			Date dmax = dateFormat.parse(t1.getEnd());
			if( (startDate.compareTo(dmin) >= 0 && endDate.compareTo(dmax)<=0) || (startDate.compareTo(dmin)>=0 && startDate.compareTo(dmax)<=0) ||(endDate.compareTo(dmin)>=0 && endDate.compareTo(dmax)<=0)) {
				free = false;
				break;
			}
		}
		if(free == true) {
			Termin termin1 = new Termin();
			Adventure adventure1 = adventureRepository.findById(adventureId).orElseGet(null);
			termin1.setAdventureTermin(adventure1);
			termin1.setStart(term.getStart());
			termin1.setEnd(term.getEnd());
			termin1.setDuration(term.getDuration());
			termin1.setReserved(false);
			terminRepository.save(termin1);
		}
		
		return free;
	}
	

}
