package bookmark_spring_boot.entities;

import bookmark_spring_boot.constants.Gender;
import bookmark_spring_boot.constants.UserType;

public record User(long id, String email, String password, String firstName, String lastName, UserType userType,
			Gender gender) {}
