package bookmark_spring_boot.constants;

public enum KidFriendlyStatus {
		
	UNKNOWN("unknown"),
	APPROVED("approved"),
	REJECTED("rejected");
	
	private KidFriendlyStatus(String name) {
		this.name = name;
	}
	private String name;
}
