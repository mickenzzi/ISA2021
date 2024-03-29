package application.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import application.model.Request;
import application.model.User;
import application.repository.RequestRepository;
import application.repository.UserRepository;
import application.service.RequestService;

@Service
public class RequestServiceImpl implements RequestService{

	@Autowired
	private RequestRepository requestRepository;
	@Autowired
	private UserRepository userRepository;

	@Override
	public Request saveRequest(Request request) {
		return requestRepository.save(request);
	}

	@Override
	public Request findById(Long id) throws AccessDeniedException {
		return requestRepository.findById(id).orElseGet(null);
	}
	
	@Override
	public Request findByUsername(String username) throws  UsernameNotFoundException {
		return requestRepository.findByUsername(username);
	}

	@Override
	public List<Request> findAll(Long adminId) {
		List<Request> requests = requestRepository.findAll();
		List<Request> requests1 = new ArrayList<Request>();
		for(Request r: requests) {
			if(r.getUserRequest().getId() == adminId){
				requests1.add(r);
			}
		}
		return requests1;
	}

	@Override
	public Request createRequest(Long userId,String text) {
		User admin = new User();
		User user = new User();
		user = userRepository.findById(userId).orElseGet(null);
		admin = userRepository.findByUsername("mickenzi");
		Request request = new Request();
		request.setTitle("Zahtev za brisanje naloga-"+text);
		request.setUsername(user.getUsername());
		request.setUserRequest(admin);
		requestRepository.save(request);
		return null;
	}

}
