package application.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import application.model.Request;
import application.repository.RequestRepository;
import application.service.RequestService;

@Service
public class RequestServiceImpl implements RequestService{

	@Autowired
	private RequestRepository requestRepository;

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
			if(r.getUserRequest().getId() == adminId && r.isDeleted() == false){
				requests1.add(r);
			}
		}
		return requests1;
	}

}
