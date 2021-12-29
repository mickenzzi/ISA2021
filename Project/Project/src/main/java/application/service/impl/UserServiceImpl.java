package application.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;

import application.model.Request;
import application.model.User;
import application.model.dto.UserDTO;
import application.repository.RequestRepository;
import application.repository.UserRepository;
import application.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private RequestRepository requestRepository;
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
	public void enableUser(Long userId) {
		User user = userRepository.findById(userId).orElseGet(null);
		user.setEnabled(true);
		Request request = requestRepository.findByUsername(user.getUsername());
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
	public void disableUser(Long userId) {
		User user = userRepository.findById(userId).orElseGet(null);
		user.setEnabled(false);
		Request request = requestRepository.findByUsername(user.getUsername());
		request.setDeleted(true);
		SimpleMailMessage mail = new SimpleMailMessage();
		mail.setTo(user.getEmail());
		mail.setFrom(env.getProperty("spring.mail.username"));
		mail.setSubject("Verifikacija naloga");
		mail.setText("Vas nalog nije prosao veirifikaciju.");
		requestRepository.save(request);
		userRepository.save(user);
	}

	@Override
	public void deleteUser(Long userId) {
		User user = userRepository.findById(userId).orElseGet(null);
		user.setDeleted(true);		
		userRepository.save(user);
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
		 userRepository.save(user);
		 Request request = new Request();
		 request.setTitle("Verifikacija naloga");
		 request.setUsername(userDTO.getUsername());
		 request.setUserRequest(admin);
		 request.setDeleted(false);
		 requestRepository.save(request);
		 return user;
	}
	

}
