/*package shop.service;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import shop.daoservice.UserDaoService;
import shop.model.User;
@Service
public class UserService {
	
	
	@Autowired
	UserDaoService userDaoService;
	

	public User createUser(User prod) throws ParseException {
		return userDaoService.save(prod);
	}

}
*/