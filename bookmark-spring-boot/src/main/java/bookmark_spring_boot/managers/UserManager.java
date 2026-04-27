package bookmark_spring_boot.managers;

import org.springframework.stereotype.Service;

import bookmark_spring_boot.dao.UserDao;
import bookmark_spring_boot.entities.User;
import bookmark_spring_boot.util.StringUtil;

@Service
public class UserManager {
	
	private UserDao dao;

	private UserManager(UserDao dao) {
		this.dao = dao;
	};


	
	public User getUser(long userId) {
		// TODO Auto-generated method stub
		return dao.getUser(userId);
	}

	public User authenticate(String email, String password) {
		String encodedPassword = StringUtil.encodePassword(password);
		//System.out.println("password : "+password+"encodedPassword : "+encodedPassword);
		return dao.authenticate(email,encodedPassword);
	}
}
