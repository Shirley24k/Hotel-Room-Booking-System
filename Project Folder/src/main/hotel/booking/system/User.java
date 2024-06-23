package hotel.booking.system;
public class User {
	
	private String name;
	private String member_type;
	private boolean excl_reward;
	
	//constructor
	public User(String name, String member_type, boolean excl_reward){
		if (name == null || name.isEmpty()) {
			throw new IllegalArgumentException("Empty name");
		}
		if (member_type == null || member_type.isEmpty()) {
			throw new IllegalArgumentException("Empty name");
		}
		if (!member_type.equals("VIP") && !member_type.equals("member") && !member_type.equals("normal")) {
			throw new IllegalArgumentException("Invalid member type");
		}
		if (member_type.equals("normal") && excl_reward == true){
			throw new IllegalArgumentException("Non-member are not entitled to exclusive reward");
		}
		if(!(excl_reward==true||excl_reward==false)) {
			throw new IllegalArgumentException("Invalid exclusive reward status");
		}
		
		this.name = name;
		this.member_type = member_type;
		this.excl_reward = excl_reward;
	}
	
	public String getName(){
		return name;
	}
	
	public String get_member_type(){
		return member_type;
	}
	
	public boolean get_excl_reward(){
		return excl_reward;
	}
	
	public void set_excl_reward(boolean excl_reward){
		if (member_type.equals("normal") && excl_reward == true)
			throw new IllegalArgumentException("Non-member are not entitled to exclusive reward");
		if(!(excl_reward==true||excl_reward==false)) {
			throw new IllegalArgumentException("Invalid exclusive reward status");
		}
		this.excl_reward = excl_reward;
	}
}
