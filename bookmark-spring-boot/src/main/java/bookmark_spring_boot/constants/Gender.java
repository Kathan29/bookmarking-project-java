package bookmark_spring_boot.constants;

public enum Gender {
	MALE(0),
	FEMALE(1),
	TRANSGENDER(2);
	
	private Gender(int val) {
		this.val = val;
	}
	private int val;
}
