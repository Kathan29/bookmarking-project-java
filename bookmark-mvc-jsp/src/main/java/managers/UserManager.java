package managers;

import dao.UserDao;
import entities.User;
import util.StringUtil;

public class UserManager {
	private static UserManager instance = new UserManager();
	private static UserDao dao = new UserDao();

	private UserManager() {
	};

	public static UserManager getInstance() {
		return instance;
	}

	
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
