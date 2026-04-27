package constants;

public enum KidFriendlyStatus {
		
	UNKNOWN("unknown"),
	APPROVED("approved"),
	REJECTED("rejected");
	
	private KidFriendlyStatus(String name) {
		this.name = name;
	}
	private String name;
}
