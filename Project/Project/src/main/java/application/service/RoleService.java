package application.service;

import application.model.Role;

public interface RoleService {
	Role findById(Long id);
	Role findByName(String name);
}