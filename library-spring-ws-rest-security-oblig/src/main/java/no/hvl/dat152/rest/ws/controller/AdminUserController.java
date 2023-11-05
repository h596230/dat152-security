/**
 * 
 */
package no.hvl.dat152.rest.ws.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import no.hvl.dat152.rest.ws.exceptions.UserNotFoundException;
import no.hvl.dat152.rest.ws.model.User;
import no.hvl.dat152.rest.ws.service.AdminUserService;

import javax.management.relation.RoleNotFoundException;

/**
 * @author tdoy
 */
@RestController
@RequestMapping("/elibrary/api/v1/admin")
public class AdminUserController {

	@Autowired
	private AdminUserService userService;
	
	@PutMapping("/users/{id}")
	@PreAuthorize("hasAuthority('SUPER_ADMIN')")
	public ResponseEntity<Object> updateUserRole(@PathVariable("id") Long id, @RequestParam("role") String role) 
			throws UserNotFoundException, RoleNotFoundException {
		try {
			User user = userService.updateUserRole(id,role);
			return new ResponseEntity<>(user,HttpStatus.OK);
		}catch (UserNotFoundException e){
			throw new UserNotFoundException("User not found");
		}catch (RoleNotFoundException e){
			throw new RoleNotFoundException("Role not found");
		}
	}
	
	@DeleteMapping("/users/{id}")
	@PreAuthorize("hasAuthority('SUPER_ADMIN')")
	public ResponseEntity<Object> deleteUserRole(@PathVariable("id") Long id, 
			@RequestParam("role") String role) throws UserNotFoundException, RoleNotFoundException{
		try {
			User user = userService.deleteUserRole(id,role);
			//204 no content after removing the role
			return new ResponseEntity<>(user,HttpStatus.NO_CONTENT);
		}catch (UserNotFoundException e){
			throw new UserNotFoundException("User not found");
		}catch (RoleNotFoundException e){
			throw new RoleNotFoundException("Role not found");
		}
	}
	
}
