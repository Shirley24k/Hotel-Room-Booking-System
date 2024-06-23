package hotel.booking.system;
import java.util.*;

public class Booking {
	
	private static final int VIP_MEMBER_MAX_ROOM = 3;
	private static final int MEMBER_MAX_ROOM = 2;
	private static final int NORMAL_MAX_ROOM = 1;
	
	private Printer printer = new Printer();
	private User user;
	private int vip_rooms;
	private int deluxe_rooms;
	private int standard_rooms;
	
	public Booking(User user, int vip_rooms, int deluxe_rooms, int standard_rooms){
		this.user = user;
		this.vip_rooms = vip_rooms;
		this.deluxe_rooms = deluxe_rooms;
		this.standard_rooms = standard_rooms;
	}

	
	public User getUser() {
		return user;
	}
	
	public int get_vip_rooms() {
		return vip_rooms;
	}
	
	public int get_deluxe_rooms() {
		return deluxe_rooms;
	}
	
	public int get_standard_rooms() {
		return standard_rooms;
	}
	
	public void setBooking(Room room, WaitingList waiting_list, int requested_room_qty) {
		
		if (requested_room_qty <= 0) {
			throw new IllegalArgumentException("Room quantity should be greater than 0");
		}
		
		String member_type = user.get_member_type();
		int vip_room_qty = room.get_VIP();
		int deluxe_room_qty = room.get_deluxe();
		int standard_room_qty = room.get_standard();

		// Store initial room quantities to revert changes if necessary
		int initialVipQty = vip_room_qty;
		int initialDeluxeQty = deluxe_room_qty;
		int initialStandardQty = standard_room_qty;
		boolean rewardRedemption = false;
		
		//VIP members
		if(member_type.equals("VIP")) {
			//Check if the room quantity requested by VIP is more than maximum room can be booked
			if(requested_room_qty > VIP_MEMBER_MAX_ROOM){
				throw new IllegalArgumentException("Room quantity requested more than maximum quantity allowed");
			}
			
			//When the number of rooms requested cannot be fulfilled, place VIP into waiting list
			else if (vip_room_qty + deluxe_room_qty + standard_room_qty < requested_room_qty) {
				waiting_list.addWaiting(this);
			}
			
			else {
				int allocatedRooms = 0;
				int vipRooms = 0;
				int deluxeRooms = 0;
				int standardRooms = 0;
				

				for (int i=0; i < requested_room_qty; i++) {
					//Allocate default room of VIP: VIP room
					if (room.checkRoom("VIP")) {
						vipRooms++;
						vip_room_qty--;
						room.set_VIP(vip_room_qty);
						allocatedRooms++;
						vip_room_qty = room.get_VIP();
					}
					//When VIP unavailable, allocate Deluxe room
					else if(room.checkRoom("Deluxe")) {
						deluxeRooms++;
						deluxe_room_qty--;
						room.set_deluxe(deluxe_room_qty);
						allocatedRooms++;
						deluxe_room_qty = room.get_deluxe();
					}
					//When VIP and Deluxe unavailable, allocate Standard room
					else {
						standardRooms++;
						standard_room_qty--;
						room.set_standard(standard_room_qty);
						allocatedRooms++;
						standard_room_qty = room.get_standard();
					}
				}

				// If all requested rooms were allocated, else place the member on the waiting list
				if (allocatedRooms == requested_room_qty) {
					this.vip_rooms = vipRooms;
					this.deluxe_rooms = deluxeRooms;
					this.standard_rooms = standardRooms;
					printer.printInfo(this);
				} else {
					// Revert changes
					room.set_VIP(initialVipQty);
					room.set_deluxe(initialDeluxeQty);
					room.set_standard(initialStandardQty);
					waiting_list.addWaiting(this);
				}
			}
		}
		//Normal member
		else if (member_type.equals("member")) {
			//Check if the room quantity requested by normal member is more than maximum room can be booked
			if(requested_room_qty > MEMBER_MAX_ROOM){
				throw new IllegalArgumentException("Room quantity requested more than maximum quantity allowed");
			}
			
			//When the number of rooms requested cannot be fulfilled, place normal member into waiting list
			else if (!user.get_excl_reward() && deluxe_room_qty + standard_room_qty < requested_room_qty) {
				waiting_list.addWaiting(this);
			}
			else if (user.get_excl_reward() && vip_room_qty + deluxe_room_qty + standard_room_qty < requested_room_qty) {
				waiting_list.addWaiting(this);
			}
			
			else {
				
				int allocatedRooms = 0;
				int vipRooms = 0;
				int deluxeRooms = 0;
				int standardRooms = 0;
				
				for (int i=0; i < requested_room_qty; i++) {
					//Allocate default room of normal member: Deluxe
					if (room.checkRoom("Deluxe")) {
						deluxeRooms++;
						deluxe_room_qty--;
						room.set_deluxe(deluxe_room_qty);
						allocatedRooms++;
					}
					//Default room unavailable, assign standard room to normal member
					else if(room.checkRoom("Standard")){
						standardRooms++;
						standard_room_qty--;
						room.set_standard(standard_room_qty);
						allocatedRooms++;
					}
					//When Standard room not available, allocate VIP room to user with exclusive reward
					else if (user.get_excl_reward() && room.checkRoom("VIP") && !rewardRedemption){
						vipRooms++;
						vip_room_qty--;
						room.set_VIP(vip_room_qty);
						rewardRedemption = true;
						allocatedRooms++;
					}
					//When Deluxe room not available, allocate user to waiting list
					else {
						break;
					}
				}

				// If all requested rooms were allocated, else place the member on the waiting list
				if (allocatedRooms == requested_room_qty) {
					this.vip_rooms = vipRooms;
					this.deluxe_rooms = deluxeRooms;
					this.standard_rooms = standardRooms;
					printer.printInfo(this);
					if (rewardRedemption) {
						user.set_excl_reward(false);
					}
				} else {
					// Revert changes					
					room.set_VIP(initialVipQty);
					room.set_deluxe(initialDeluxeQty);
					room.set_standard(initialStandardQty);
					waiting_list.addWaiting(this);
				}
			}
		}
		//Non-member
		else {
			//Check if room quantity requested by non-member is more than maximum room can be booked
			if(requested_room_qty > NORMAL_MAX_ROOM){
				throw new IllegalArgumentException("Room quantity requested more than maximum quantity allowed");
			}
			//When the number of rooms requested cannot be fulfilled, place non-member into waiting list
			else if (standard_room_qty < requested_room_qty) {
				waiting_list.addWaiting(this);
			}
			//Allocate default room of non-member: Standard room
			else {
				this.standard_rooms++;
				standard_room_qty--;
				room.set_standard(standard_room_qty);
				printer.printInfo(this);
			}
		}
	}
	
	public void cancelBooking(Room room, WaitingList waiting_list) {
		
		boolean waitingListRemoved = false;
		
		String member_type = getUser().get_member_type();
		for (User waitList: waiting_list.getWaiting(member_type)) {
			if (waitList == this.getUser()) {
				waiting_list.removeWaiting(this);
				waitingListRemoved = true;
				break;
			}
		}
		if (!waitingListRemoved) {
			int vipToRelease = get_vip_rooms();
			int deluxeToRelease = get_deluxe_rooms();
			int standardToRelease = get_standard_rooms();
			
			room.set_VIP(room.get_VIP() + vipToRelease);
			room.set_deluxe(room.get_deluxe() + deluxeToRelease);
			room.set_standard(room.get_standard() + standardToRelease);
		}
	}
	
	
	

}
