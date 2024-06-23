package hotel.booking.system;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import hotel.booking.system.*;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(JUnitParamsRunner.class)
public class BookingTest {   
	
	int vipRoom;
	int deluxeRoom;
	int standardRoom;
	
	/*
	 *Booking Class Unit Test
	 * Test Case 3.1.1
	 * Test get user method 
	 */
	
	@Test
	public void testGetUser() {
		User user = new User("Alice", "VIP", true);
		Booking booking = new Booking(user, 0, 0, 0);
		User ARuser = booking.getUser();
		assertEquals(user, ARuser);
	}
	
	/*
	 *Booking Class Unit Test
	 * Test Case 3.2.1
	 * Test get VIP room method
	 */
	
	@Test
	public void testGetVIPRoom() {
		User user = new User("Alice", "VIP", true);
		Booking booking = new Booking(user, 2, 1, 0);
		int ARvip = booking.get_vip_rooms();
		assertEquals(2, ARvip);
	}
	
	/*
	 *Booking Class Unit Test
	 * Test Case 3.3.1
	 * Test get Deluxe room method
	 */
	
	@Test
	public void testGetDeluxeRoom() {
		User user = new User("Alice", "VIP", true);
		Booking booking = new Booking(user, 2, 1, 0);
		int ARdeluxe = booking.get_deluxe_rooms();
		assertEquals(1, ARdeluxe);
	}
	
	/*
	 *Booking Class Unit Test
	 * Test Case 3.4.1
	 * Test get Standard room method
	 */
	
	@Test
	public void testGetStandardRoom() {
		User user = new User("Alice", "VIP", true);
		Booking booking = new Booking(user, 2, 1, 0);
		int ARstandard = booking.get_standard_rooms();
		assertEquals(0, ARstandard);
	}
	
	/*
	 *Booking Class Unit Test
	 * Test Case 3.5.1 ~ 3.5.26
	 * Test VALID set booking method for VIP member_type
	 * Boundary Value Analysis
	 */
	
	@Test
	@Parameters({
		"VIP, 3, 3, 3, 1, 1, 0, 0, false",
		"VIP, 3, 3, 3, 3, 3, 0, 0, false",
		"VIP, 2, 3, 3, 3, 2, 1, 0, false",
		"VIP, 1, 1, 3, 3, 1, 1, 1, false",
		"VIP, 3, 3, 0, 1, 1, 0, 0, false",
		"VIP, 3, 3, 0, 3, 3, 0, 0, false",
		"VIP, 2, 3, 0, 3, 2, 1, 0, false",
		"VIP, 1, 1, 0, 3, 0, 0, 0, true",
		"VIP, 3, 0, 3, 1, 1, 0, 0, false",
		"VIP, 3, 0, 3, 3, 3, 0, 0, false",
		"VIP, 2, 0, 3, 3, 2, 0, 1, false",
		"VIP, 1, 0, 1, 3, 0, 0, 0, true",
		"VIP, 3, 0, 0, 1, 1, 0, 0, false",
		"VIP, 3, 0, 0, 3, 3, 0, 0, false",
		"VIP, 2, 0, 0, 3, 0, 0, 0, true",
		"VIP, 0, 3, 3, 1, 0, 1, 0, false",
		"VIP, 0, 3, 3, 3, 0, 3, 0, false",
		"VIP, 0, 2, 3, 3, 0, 2, 1, false",
		"VIP, 0, 1, 1, 3, 0, 0, 0, true",
		"VIP, 0, 3, 0, 1, 0, 1, 0, false",
		"VIP, 0, 3, 0, 3, 0, 3, 0, false",
		"VIP, 0, 1, 0, 3, 0, 0, 0, true",
		"VIP, 0, 0, 3, 1, 0, 0, 1, false",
		"VIP, 0, 0, 3, 3, 0, 0, 3, false",
		"VIP, 0, 0, 1, 3, 0, 0, 0, true",
		"VIP, 0, 0, 0, 1, 0, 0, 0, true"
		})
	
    public void testValidSetBooking_VIPMember(String member_type, int initialVipRoom, int initialDeluxeRoom, int initialStandardRoom,int reqRoom, int bookVIP, int bookDeluxe, int bookStandard, boolean waitinglist) {
		vipRoom = initialVipRoom;
		deluxeRoom = initialDeluxeRoom;
		standardRoom = initialStandardRoom;
		
		// Mock the User, Room, and WaitingList
        User user = mock(User.class);
        Room room = mock(Room.class);
        WaitingList waitingList = mock(WaitingList.class);

        when(user.get_member_type()).thenReturn(member_type);
        

        // Mocking room availability
        when(room.checkRoom("VIP")).thenAnswer(invocation -> vipRoom > 0);
        when(room.checkRoom("Deluxe")).thenAnswer(invocation -> deluxeRoom > 0);
        when(room.checkRoom("Standard")).thenAnswer(invocation -> standardRoom > 0);
        
        doAnswer(invocation ->{
        	vipRoom--; 
        	return null;
        }).when(room).set_VIP(anyInt());
        
        doAnswer(invocation ->{
        	deluxeRoom--;
        	return null;
        }).when(room).set_deluxe(anyInt());
        
        doAnswer(invocation ->{
        	standardRoom--; 
        	return null;
        }).when(room).set_standard(anyInt());

        // Mocking room count getters
        when(room.get_VIP()).thenReturn(vipRoom);
        when(room.get_deluxe()).thenReturn(deluxeRoom);
        when(room.get_standard()).thenReturn(standardRoom);

        // Booking instantiation and action
        Booking booking = new Booking(user, 0, 0, 0);
        booking.setBooking(room, waitingList, reqRoom);

        // Assertions on room allocation
        assertEquals(bookVIP, booking.get_vip_rooms());
        assertEquals(bookDeluxe, booking.get_deluxe_rooms());
        assertEquals(bookStandard, booking.get_standard_rooms());

        // Verify interactions with the waiting list
        if (waitinglist) {
            verify(waitingList).addWaiting(booking);
        } else {
            verify(waitingList, never()).addWaiting(booking);
        }

    }
	
	/*
	 *Booking Class Unit Test
	 * Test Case 3.6.1 ~ 3.6.24
	 * Test INVALID set booking method for VIP member_type
	 */
	
	@Test(expected=IllegalArgumentException.class)
	@Parameters({
		"VIP, 3, 3, 3, -1",
		"VIP, 3, 3, 3, 0",
		"VIP, 3, 3, 3, 4",
		"VIP, 3, 3, 0, -1",
		"VIP, 3, 3, 0, 0",
		"VIP, 3, 3, 0, 4",
		"VIP, 3, 0, 3, -1",
		"VIP, 3, 0, 3, 0",
		"VIP, 3, 0, 3, 4",
		"VIP, 3, 0, 0, -1",
		"VIP, 3, 0, 0, 0",
		"VIP, 3, 0, 0, 4",
		"VIP, 0, 3, 3, -1",
		"VIP, 0, 3, 3, 0",
		"VIP, 0, 3, 3, 4",
		"VIP, 0, 3, 0, -1",
		"VIP, 0, 3, 0, 0",
		"VIP, 0, 3, 0, 4",
		"VIP, 0, 0, 3, -1",
		"VIP, 0, 0, 3, 0",
		"VIP, 0, 0, 3, 4",
		"VIP, 0, 0, 0, -1",
		"VIP, 0, 0, 0, 0",
		"VIP, 0, 0, 0, 4"
		})
	
    public void testInvalidSetBooking_VIPMember(String member_type, int initialVipRoom, int initialDeluxeRoom, int initialStandardRoom, int reqRoom) {
		vipRoom = initialVipRoom;
		deluxeRoom = initialDeluxeRoom;
		standardRoom = initialStandardRoom;
		
		// Mock the User, Room, and WaitingList
        User user = mock(User.class);
        Room room = mock(Room.class);
        WaitingList waitingList = mock(WaitingList.class);

        when(user.get_member_type()).thenReturn(member_type);
        

        // Mocking room availability
        when(room.checkRoom("VIP")).thenAnswer(invocation -> vipRoom > 0);
        when(room.checkRoom("Deluxe")).thenAnswer(invocation -> deluxeRoom > 0);
        when(room.checkRoom("Standard")).thenAnswer(invocation -> standardRoom > 0);
        
        doAnswer(invocation ->{
        	vipRoom--; 
        	return null;
        }).when(room).set_VIP(anyInt());
        
        doAnswer(invocation ->{
        	deluxeRoom--;
        	return null;
        }).when(room).set_deluxe(anyInt());
        
        doAnswer(invocation ->{
        	standardRoom--; 
        	return null;
        }).when(room).set_standard(anyInt());

        // Mocking room count getters
        when(room.get_VIP()).thenReturn(vipRoom);
        when(room.get_deluxe()).thenReturn(deluxeRoom);
        when(room.get_standard()).thenReturn(standardRoom);

        // Booking instantiation and action
        Booking booking = new Booking(user, 0, 0, 0);
        booking.setBooking(room, waitingList, reqRoom);

    }

	/*
	 *Booking Class Unit Test
	 * Test Case 3.7.1 ~ 3.7.43
	 * Test VALID set booking method for member member_type
	 */
	
	@Test
	@Parameters({
		"member, true, 3, 3, 3, 1, 0, 1, 0, false, false",
		"member, true, 3, 3, 3, 2, 0, 2, 0, false, false",
		"member, true, 3, 1, 3, 2, 0, 1, 1, false, false", 
		"member, false, 3, 3, 3, 1, 0, 1, 0, false, false",
		"member, false, 3, 3, 3, 2, 0, 2, 0, false, false",
		"member, false, 3, 1, 3, 2, 0, 1, 1, false, false", 
		"member, true, 3, 3, 0, 1, 0, 1, 0, false, false",
		"member, true, 3, 3, 0, 2, 0, 2, 0, false, false",
		"member, true, 3, 1, 0, 2, 1, 1, 0, false, true",
		"member, false, 3, 3, 0, 1, 0, 1, 0, false, false",
		"member, false, 3, 3, 0, 2, 0, 2, 0, false, false",
		"member, false, 3, 1, 0, 2, 0, 0, 0, true, false",
		"member, true, 3, 0, 3, 1, 0, 0, 1, false, false",
		"member, true, 3, 0, 3, 2, 0, 0, 2, false, false",
		"member, true, 1, 0, 1, 2, 1, 0, 1, false, true",
		"member, false, 3, 0, 3, 1, 0, 0, 1, false, false",
		"member, false, 3, 0, 3, 2, 0, 0, 2, false, false",
		"member, false, 3, 0, 1, 2, 0, 0, 0, true, false", 
		"member, true, 3, 0, 0, 1, 1, 0, 0, false, true",
		"member, true, 3, 0, 0, 2, 0, 0, 0, true, false", 
		"member, false, 3, 0, 0, 1, 0, 0, 0, true, false",
		"member, true, 0, 3, 3, 1, 0, 1, 0, false, false",
		"member, true, 0, 3, 3, 2, 0, 2, 0, false, false",
		"member, true, 0, 1, 3, 2, 0, 1, 1, false, false",
		"member, false, 0, 3, 3, 1, 0, 1, 0, false, false",
		"member, false, 0, 3, 3, 2, 0, 2, 0, false, false",
		"member, false, 0, 1, 3, 2, 0, 1, 1, false, false",
		"member, true, 0, 3, 0, 1, 0, 1, 0, false, false",
		"member, true, 0, 3, 0, 2, 0, 2, 0, false, false",
		"member, true, 0, 1, 0, 2, 0, 0, 0, true, false",
		"member, false, 0, 3, 0, 1, 0, 1, 0, false, false",
		"member, false, 0, 3, 0, 2, 0, 2, 0, false, false",
		"member, false, 0, 1, 0, 2, 0, 0, 0, true, false",
		"member, true, 0, 0, 3, 1, 0, 0, 1, false, false",
		"member, true, 0, 0, 3, 2, 0, 0, 2, false, false",
		"member, true, 0, 0, 1, 2, 0, 0, 0, true, false",
		"member, false, 0, 0, 3, 1, 0, 0, 1, false, false",
		"member, false, 0, 0, 3, 2, 0, 0, 2, false, false",
		"member, false, 0, 0, 1, 2, 0, 0, 0, true, false", 
		"member, true, 0, 0, 0, 1, 0, 0, 0, true, false",
		"member, true, 0, 0, 0, 2, 0, 0, 0, true, false",
		"member, false, 0, 0, 0, 1, 0, 0, 0, true, false",
		"member, false, 0, 0, 0, 2, 0, 0, 0, true, false"
	})
	
	
	public void testValidSetBooking_NormalMember(String member_type, boolean excReward, int initialVipRoom, int initialDeluxeRoom, int initialStandardRoom,int reqRoom, int bookVIP, int bookDeluxe, int bookStandard, boolean waitinglist, boolean rewardRedemption) {
		vipRoom = initialVipRoom;
		deluxeRoom = initialDeluxeRoom;
		standardRoom = initialStandardRoom;
		
		// Mock the User, Room, and WaitingList
        User user = mock(User.class);
        Room room = mock(Room.class);
        WaitingList waitingList = mock(WaitingList.class);

        when(user.get_member_type()).thenReturn(member_type);
        when(user.get_excl_reward()).thenAnswer(invocation -> excReward);
        
        doAnswer(invocation -> {
            boolean arg = invocation.getArgument(0); // Get the boolean argument passed to the method
            if (arg) {
                // Update the excl_reward to false after the first loop
                when(user.get_excl_reward()).thenReturn(false);
            }
            return null; // Return null or any other value if necessary
        }).when(user).set_excl_reward(anyBoolean());

        // Mocking room availability
        when(room.checkRoom("VIP")).thenAnswer(invocation -> vipRoom > 0);
        when(room.checkRoom("Deluxe")).thenAnswer(invocation -> deluxeRoom > 0);
        when(room.checkRoom("Standard")).thenAnswer(invocation -> standardRoom > 0);
        
        doAnswer(invocation ->{
        	vipRoom--; 
        	return null;
        }).when(room).set_VIP(anyInt());
        
        doAnswer(invocation ->{
        	deluxeRoom--;
        	return null;
        }).when(room).set_deluxe(anyInt());
        
        doAnswer(invocation ->{
        	standardRoom--; 
        	return null;
        }).when(room).set_standard(anyInt());

        // Mocking room count getters
        when(room.get_VIP()).thenReturn(vipRoom);
        when(room.get_deluxe()).thenReturn(deluxeRoom);
        when(room.get_standard()).thenReturn(standardRoom);

        // Booking instantiation and action
        Booking booking = new Booking(user, 0, 0, 0);
        booking.setBooking(room, waitingList, reqRoom);

        // Assertions on room allocation
        assertEquals(bookVIP, booking.get_vip_rooms());
        assertEquals(bookDeluxe, booking.get_deluxe_rooms());
        assertEquals(bookStandard, booking.get_standard_rooms());

        // Verify interactions with the waiting list
        if (waitinglist) {
            verify(waitingList).addWaiting(booking);
        } else {
            verify(waitingList, never()).addWaiting(booking);
        }

        // Verify reward redemption
        if (rewardRedemption) {
            verify(user).set_excl_reward(false);
        } else {
            verify(user, never()).set_excl_reward(anyBoolean());
        }
    }
	
	/*
	 *Booking Class Unit Test
	 * Test Case 3.8.1 ~ 3.8.48
	 * Test INVALID set booking method for member member_type
	 */
	
	@Test(expected=IllegalArgumentException.class)
	@Parameters({
		"member, true, 3, 3, 3, -1",
		"member, true, 3, 3, 3, 0",
		"member, true, 3, 3, 3, 3", 
		"member, false, 3, 3, 3, -1",
		"member, false, 3, 3, 3, 0",
		"member, false, 3, 3, 3, 3", 
		"member, true, 3, 3, 0, -1",
		"member, true, 3, 3, 0, 0",
		"member, true, 3, 3, 0, 3",
		"member, false, 3, 3, 0, -1",
		"member, false, 3, 3, 0, 0",
		"member, false, 3, 3, 0, 3",
		"member, true, 3, 0, 3, -1",
		"member, true, 3, 0, 3, 0",
		"member, true, 3, 0, 3, 3",
		"member, false, 3, 0, 3,-1",
		"member, false, 3, 0, 3, 0",
		"member, false, 3, 0, 3, 3", 
		"member, true, 3, 0, 0, -1",
		"member, true, 3, 0, 0, 0", 
		"member, true, 3, 0, 0, 3",
		"member, false, 3, 0, 0, -1",
		"member, false, 3, 0, 0, 0", 
		"member, false, 3, 0, 0, 3",
		"member, true, 0, 3, 3, -1",
		"member, true, 0, 3, 3, 0",
		"member, true, 0, 3, 3, 3",
		"member, false, 0, 3, 3, -1",
		"member, false, 0, 3, 3, 0",
		"member, false, 0, 3, 3, 3",
		"member, true, 0, 3, 0, -1",
		"member, true, 0, 3, 0, 0",
		"member, true, 0, 3, 0, 3",
		"member, false, 0, 3, 0, -1",
		"member, false, 0, 3, 0, 0",
		"member, false, 0, 3, 0, 3",
		"member, true, 0, 0, 3, -1",
		"member, true, 0, 0, 3, 0",
		"member, true, 0, 0, 3, 3",
		"member, false, 0, 0, 3, -1",
		"member, false, 0, 0, 3, 0",
		"member, false, 0, 0, 3, 3", 
		"member, true, 0, 0, 0, -1",
		"member, true, 0, 0, 0, 0",
		"member, true, 0, 0, 0, 3",
		"member, false, 0, 0, 0, -1",
		"member, false, 0, 0, 0, 0",
		"member, false, 0, 0, 0, 3"
	})
	
	
	public void testInvalidSetBooking_NormalMember(String member_type, boolean excReward, int initialVipRoom, int initialDeluxeRoom, int initialStandardRoom,int reqRoom) {
		vipRoom = initialVipRoom;
		deluxeRoom = initialDeluxeRoom;
		standardRoom = initialStandardRoom;
		
		// Mock the User, Room, and WaitingList
        User user = mock(User.class);
        Room room = mock(Room.class);
        WaitingList waitingList = mock(WaitingList.class);

        when(user.get_member_type()).thenReturn(member_type);
        when(user.get_excl_reward()).thenAnswer(invocation -> excReward);
        
        doAnswer(invocation -> {
            boolean arg = invocation.getArgument(0); // Get the boolean argument passed to the method
            if (arg) {
                // Update the excl_reward to false after the first loop
                when(user.get_excl_reward()).thenReturn(false);
            }
            return null; // Return null or any other value if necessary
        }).when(user).set_excl_reward(anyBoolean());

        // Mocking room availability
        when(room.checkRoom("VIP")).thenAnswer(invocation -> vipRoom > 0);
        when(room.checkRoom("Deluxe")).thenAnswer(invocation -> deluxeRoom > 0);
        when(room.checkRoom("Standard")).thenAnswer(invocation -> standardRoom > 0);
        
        doAnswer(invocation ->{
        	vipRoom--; 
        	return null;
        }).when(room).set_VIP(anyInt());
        
        doAnswer(invocation ->{
        	deluxeRoom--;
        	return null;
        }).when(room).set_deluxe(anyInt());
        
        doAnswer(invocation ->{
        	standardRoom--; 
        	return null;
        }).when(room).set_standard(anyInt());

        // Mocking room count getters
        when(room.get_VIP()).thenReturn(vipRoom);
        when(room.get_deluxe()).thenReturn(deluxeRoom);
        when(room.get_standard()).thenReturn(standardRoom);

        // Booking instantiation and action
        Booking booking = new Booking(user, 0, 0, 0);
        booking.setBooking(room, waitingList, reqRoom);

    }

	/*
	 *Booking Class Unit Test
	 * Test Case 3.9.1 ~ 3.9.2
	 * Test VALID set booking method for normal member_type
	 */
	
	@Test
	@Parameters({
		"normal, 1, 1, 1, false",
		"normal, 0, 1, 0, true",
	})
	public void testValidSetBooking_NonMember(String member_type, int standardRoom, int reqRoom, int bookStandard, boolean waitinglist) {
		// Create a mock User for non-member
        User user = mock(User.class);
        when(user.get_member_type()).thenReturn(member_type);
        
        // Create a mock Room with available Standard rooms
        Room room = mock(Room.class);
        when(room.checkRoom("Standard")).thenReturn(standardRoom > 0);
        when(room.get_standard()).thenReturn(standardRoom);
       
        // Create a mock WaitingList
        WaitingList waitingList = mock(WaitingList.class);
        
        // Create a Booking instance
        Booking booking = new Booking(user, 0, 0, 0);

        // Call the setBooking method with requested room quantity
        booking.setBooking(room, waitingList, reqRoom);
        
        // Verify that the booking has been made successfully
        assertEquals(bookStandard, booking.get_standard_rooms()); 
        
        if (waitinglist) {
            verify(waitingList).addWaiting(booking);
        } else {
            verify(waitingList, never()).addWaiting(booking);
        }
	}
	
	/*
	 *Booking Class Unit Test
	 * Test Case 3.10.1 ~ 3.10.6
	 * Test INVALID set booking method for normal member_type
	 */
	
	@Test(expected = IllegalArgumentException.class)
	@Parameters({
		"normal, 1, -1",
		"normal, 1, 0",
		"normal, 1, 2",
		"normal, 0, -1",
		"normal, 0, 0",
		"normal, 0, 2"
	})
	public void testInvalidSetBooking_NonMember(String member_type, int standardRoom, int reqRoom) {
		// Create a mock User for non-member
        User user = mock(User.class);
        when(user.get_member_type()).thenReturn(member_type);
        
        // Create a mock Room with available Standard rooms
        Room room = mock(Room.class);
        when(room.checkRoom("Standard")).thenReturn(standardRoom > 0);
        when(room.get_standard()).thenReturn(standardRoom);
        
        // Create a mock WaitingList
        WaitingList waitingList = mock(WaitingList.class);
        
        // Create a Booking instance
        Booking booking = new Booking(user, 0, 0, 0);

        // Call the setBooking method with requested room quantity
        booking.setBooking(room, waitingList, reqRoom);
    
	}
	
	/*
	 *Booking Class Unit Test
	 * Test Case 3.11.1 ~ 3.11.3
	 * Test cancel booking method and verify released room quantity
	 */
	
	private Object[] getBookingParam() {
		Booking booking1 = new Booking(new User("Ali", "VIP", true), 2, 1, 0);
		Booking booking2 = new Booking(new User("Bunny", "member", true), 0, 2, 0);
		Booking booking3 = new Booking(new User("Cally", "normal", false), 0, 0, 1);
		return new Object[] {
			new Object[] {booking1, 3, 3, 3, 5, 4, 3},
			new Object[] {booking2, 2, 2, 2, 2, 4, 2},
			new Object[] {booking3, 2, 2, 2, 2, 2, 3}
		};
	}
	
	@Test
	@Parameters(method="getBookingParam")
	public void testCancelBooking(Booking booking, int initialVIP, int initialDeluxe, int initialStandard, int expectedVIP, int expectedDeluxe, int expectedStandard) {
	    // Mock the Room and WaitingList
	    Room room = mock(Room.class);
	    WaitingList waitingList = mock(WaitingList.class);

	    when(room.get_VIP()).thenReturn(initialVIP);
	    when(room.get_deluxe()).thenReturn(initialDeluxe);
	    when(room.get_standard()).thenReturn(initialStandard);
	    
	    // Call the cancelBooking method
	    booking.cancelBooking(room, waitingList);

	    // Verify that the room quantities are updated
	    verify(room).set_VIP(expectedVIP);
	    verify(room).set_deluxe(expectedDeluxe); 
	    verify(room).set_standard(expectedStandard); 

	    // Verify that the user is not removed from the waiting list
	    verify(waitingList, never()).removeWaiting(booking);
	}
	
	/*
	 *Booking Class Unit Test
	 * Test Case 3.12.1 ~ 3.12.3
	 * Test cancel booking and remove user from waiting list
	 */
	
	private Object[] getWaitingListParam() {
		Booking booking1 = new Booking(new User("Ali", "VIP", true), 2, 1, 0);
		Booking booking2 = new Booking(new User("Bunny", "member", true), 0, 2, 0);
		Booking booking3 = new Booking(new User("Cally", "normal", false), 0, 0, 1);
		ArrayList<User> VIP = new ArrayList<User>();
		ArrayList<User> member = new ArrayList<User>();
		ArrayList<User> normal = new ArrayList<User>();
		return new Object[] {
			new Object[] {booking1, VIP},
			new Object[] {booking2, member},
			new Object[] {booking3, normal}
		};
	}
	
	@Test
	@Parameters(method = "getWaitingListParam")
	public void cancelBookingInWaitingList(Booking booking, ArrayList<User> member_type_list) {
		Room room = mock(Room.class);
		WaitingList waitinglist = mock(WaitingList.class);
		member_type_list.add(booking.getUser());
		when(waitinglist.getWaiting(anyString())).thenReturn(member_type_list);
		booking.cancelBooking(room, waitinglist);
		
		verify(waitinglist).removeWaiting(booking);		
	}

}