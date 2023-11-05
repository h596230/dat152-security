/**
 * 
 */
package no.hvl.dat152.rest.ws.service;

import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import no.hvl.dat152.rest.ws.exceptions.UserNotFoundException;
import no.hvl.dat152.rest.ws.model.Role;
import no.hvl.dat152.rest.ws.model.User;
import no.hvl.dat152.rest.ws.repository.RoleRepository;
import no.hvl.dat152.rest.ws.repository.UserRepository;
import org.springframework.transaction.annotation.Transactional;

import javax.management.relation.RoleNotFoundException;

/**
 * @author tdoy
 */
@Service
public class AdminUserService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	public User saveUser(User user) {
		
		user = userRepository.save(user);
		
		return user;
	}

	@Transactional
	public User deleteUserRole(Long id, String role) throws UserNotFoundException,RoleNotFoundException {

		User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("user not found "));
		Role roleToBeRemoved = roleRepository.findByName(role);
		if(roleToBeRemoved == null){
			throw new RoleNotFoundException("Role not found!");
		}
		if(!user.getRoles().contains(roleToBeRemoved)){
			throw new RoleNotFoundException("Role not found in this user!");
		}
		user.getRoles().remove(roleToBeRemoved);
		userRepository.save(user);
		return user;
	}

	@Transactional
	public User updateUserRole(Long id, String role) throws UserNotFoundException,RoleNotFoundException {
		User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("user not found "));
		Role roleObj = roleRepository.findByName(role);
		if(roleObj == null){
			throw new RoleNotFoundException("Role not found");
		}
		user.getRoles().add(roleObj);
		userRepository.save(user);
		return user;
	}
	
	public User findUser(Long id) throws UserNotFoundException {
		
		User user = userRepository.findById(id)
				.orElseThrow(()-> new UserNotFoundException("User with id: "+id+" not found"));
		
		return user;
	}
}
