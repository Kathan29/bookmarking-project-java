package bookmark_spring_boot.constants;

public enum UserType {

	USER("user"),
	EDITOR("editor"),
	CHIEF_EDITOR("chiefeditor");

	private UserType(String name) {
		this.name = name;
	}
	private String name;
}
