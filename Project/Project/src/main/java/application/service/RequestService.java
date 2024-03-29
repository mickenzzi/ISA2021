package application.service;

import java.util.List;

import application.model.Request;

public interface RequestService {
	Request saveRequest(Request request);
	Request findById(Long id);
	Request findByUsername(String username);
	List<Request> findAll(Long adminId);
	Request createRequest(Long userId,String text);
}
