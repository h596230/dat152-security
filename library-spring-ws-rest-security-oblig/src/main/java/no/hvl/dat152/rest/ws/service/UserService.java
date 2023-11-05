/**
 * 
 */
package no.hvl.dat152.rest.ws.service;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import no.hvl.dat152.rest.ws.exceptions.UserNotFoundException;
import no.hvl.dat152.rest.ws.model.Order;
import no.hvl.dat152.rest.ws.model.User;
import no.hvl.dat152.rest.ws.repository.UserRepository;

/**
 * @author tdoy
 */
@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
	public User saveUser(User user) {
		
		user = userRepository.save(user);
		
		return user;
	}
	
	public void deleteUser(Long id) throws UserNotFoundException {
		//exception is handled by findUser(id),exception is caught and re-thrown,
		userRepository.delete(findUser(id));
	}
	
	public User updateUser(User user, Long id) throws UserNotFoundException {
		User oldUser = findUser(id);
		boolean updated = false;
		if(!Objects.equals(oldUser.getFirstname(),user.getFirstname())){
			oldUser.setFirstname(user.getFirstname());
			updated = true;
		}
		if(!Objects.equals(oldUser.getLastname(),user.getLastname())){
			oldUser.setLastname(user.getLastname());
			updated = true;
		}

		if(updated){
			userRepository.save(oldUser);
		}
		return oldUser;
	}
	
	public List<User> findAllUsers(){
		
		List<User> allUsers = (List<User>) userRepository.findAll();
		
		return allUsers;
	}
	
	public User findUser(Long id) throws UserNotFoundException {
		
		User user = userRepository.findById(id)
				.orElseThrow(()-> new UserNotFoundException("User with id: "+id+" not found"));
		
		return user;
	}
	
	public Set<Order> findOrdersForUser(Long id) throws UserNotFoundException{
		
		User user = findUser(id);
		
		return user.getOrders();
	}

	public User createOrdersForUser(Long id, Order order) throws UserNotFoundException{
		User user = findUser(id);
		if (user == null) {
			throw new UserNotFoundException("User with id: " + id + " not found");
		}

		Set<Order> orders = user.getOrders();
		if (orders == null) {
			orders = new HashSet<>();
			user.setOrders(orders);
		}

		orders.add(order);

		saveUser(user);

		return user;
	}
}
