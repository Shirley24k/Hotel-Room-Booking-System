package hotel.booking.system;
import java.util.ArrayList;

public class WaitingList {
	
	
	private ArrayList <User> VIP;
	private ArrayList <User> member;
	private ArrayList <User> normal;
	
	public WaitingList() {
		VIP = new ArrayList <User>();
		member = new ArrayList <User>();
		normal = new ArrayList <User>();
	}
	
	public void addWaiting(Booking booking){
		User user = booking.getUser();
		switch(user.get_member_type())
		{
			//add VIP into VIP array list
			case "VIP":
				VIP.add(user);
				break;
			//add member into member array list
			case "member":
				member.add(user);
				break;
			//add non-member into normal array list
			case "normal":
				normal.add(user);
				break;
			//invalid member type cannot add waiting list
			default:
				throw new IllegalArgumentException("Invalid user type");
		}
	}
	
	public ArrayList <User> getWaiting(String member_type){
		
		switch(member_type)
		{
			//return to VIP array list
			case "VIP":
				return VIP;
			//return to member array list
			case "member":
				return member;
			//return to normal array list
			case "normal":
				return normal;
			default:
				throw new IllegalArgumentException("Invalid user type");
		}
		
	}
	
	public void removeWaiting(Booking booking){
		User user = booking.getUser();
		switch(user.get_member_type())
		{
			//remove VIP from VIP array list
			case "VIP":
				VIP.remove(user);
				break;
			//remove member from member array list
			case "member":
				member.remove(user);
				break;
			//remove non-member from normal array list
			case "normal":
				normal.remove(user);
				break;
			default:
				throw new IllegalArgumentException("Invalid user type");
		}
		
	}
	
	
}


