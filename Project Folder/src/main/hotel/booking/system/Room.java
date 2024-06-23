package hotel.booking.system;
public class Room {
	
	private int vip;
	private int deluxe;
	private int standard;
	private boolean available;
	
	public Room(int vip_rooms, int deluxe_rooms, int standard_rooms){
		vip = vip_rooms;
		deluxe = deluxe_rooms;
		standard = standard_rooms;
	}
	
	//method to check for room availability
	public boolean checkRoom(String room_type){
		//not ready for testing
		return available;
	}
	
	public int get_VIP(){
		return vip;
	}
	
	public void set_VIP(int num){
		vip = num;
	}
	
	public int get_deluxe(){
		return deluxe;
	}
	
	public void set_deluxe(int num){
		deluxe = num;
	}
	
	public int get_standard(){
		return standard;
	}

	public void set_standard(int num){
		standard = num;
	}
	
}
