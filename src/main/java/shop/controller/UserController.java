/*package shop.controller;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import shop.model.User;
import shop.service.UserService;

//@RestController("/")
public class UserController {

	
	UserService userService;

	
	@PostMapping("user")
	public ResponseEntity<String> createUser(@RequestBody User user ) throws ParseException {
		user =userService.createUser(user);
		
		
		return new ResponseEntity<String>(user.getEmail()+" created successfully", 
			      HttpStatus.CREATED);
	}

}
*/