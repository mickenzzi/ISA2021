package application.service;

import java.util.List;

import application.model.User;
import application.model.dto.UserDTO;

public interface UserService {
	User findById(Long id);
	User findByUsername(String username);
	List<User> findAll();
	User save(User user);
	User createUser(UserDTO userDTO);
	void enableUser(Long userId, Long requestId);
	void disableUser(Long userId, Long requestId);
	void approveDeleteRequest(Long userId, Long requestId);
	void rejectDeleteRequest(Long userId, Long requestId);
	void deleteUser(Long userId);
	void updateUser(UserDTO userDTO);
}
