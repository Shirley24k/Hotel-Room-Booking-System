package hotel.booking.system;

import hotel.booking.system.*;

import java.util.ArrayList;
import java.util.Scanner;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.runner.RunWith;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

@RunWith(JUnitParamsRunner.class)
public class IntegrationTest {
	
	/*
	 * Integration Test
	 * Test Case 4.1.1
	 * Test integration between booking, room, user, and waiting list 
	 */
	
	@Test
    public void testBookingIntegration() {
        
		User user = new User("Alice", "normal", false);
        // Create a mock room
        Room room = mock(Room.class);
        when(room.checkRoom("VIP")).thenReturn(true); 
        when(room.checkRoom("Deluxe")).thenReturn(true);
        when(room.checkRoom("Standard")).thenReturn(false);
        when(room.get_VIP()).thenReturn(2);
        when(room.get_deluxe()).thenReturn(1);
        when(room.get_standard()).thenReturn(0);
        
        // Create a waiting list
        WaitingList waitingList = new WaitingList();

        // Create a booking
        Booking booking = new Booking(user, 0, 0, 0);

        // Perform the booking
        booking.setBooking(room, waitingList, 1);
        
        // Check if the booking was added to the waiting list
        assertTrue(waitingList.getWaiting("normal").contains(user));

        // Cancel the booking
        booking.cancelBooking(room, waitingList);

        // Check if the booking was removed from the waiting list
        assertFalse(waitingList.getWaiting("normal").contains(user));
    }
	
	
	/*
	 * Integration Test
	 * Test Case 4.1.2
	 * Invalid test for integration between booking, room, user, and waiting list 
	 * with invalid user exclusive reward
	 */
	
	@Test(expected = IllegalArgumentException.class)
    public void testInvalidUserBookingIntegration() {
        
		User user = new User("Alice", "normal", true); //user with invalid exclusive reward corresponding to member type
        // Create a mock room
        Room room = mock(Room.class);
        when(room.checkRoom("VIP")).thenReturn(true); 
        when(room.checkRoom("Deluxe")).thenReturn(true);
        when(room.checkRoom("Standard")).thenReturn(false);
        when(room.get_VIP()).thenReturn(2);
        when(room.get_deluxe()).thenReturn(1);
        when(room.get_standard()).thenReturn(0);
        
        // Create a waiting list
        WaitingList waitingList = new WaitingList();

        // Create a booking
        Booking booking = new Booking(user, 0, 0, 0);

        // Perform the booking
        booking.setBooking(room, waitingList, 1);
        
        // Check if the booking was not added to the waiting list
        assertFalse(waitingList.getWaiting("normal").contains(user));

        // Cancel the booking
        booking.cancelBooking(room, waitingList);
        
        // Check if the booking was not in the waiting list
        assertFalse(waitingList.getWaiting("normal").contains(user));

    }

	/*
	 * Integration Test
	 * Test Case 4.1.3
	 * Invalid test for integration between booking, room, user, and waiting list
	 * with invalid requested room quantity 
	 */
	
	@Test(expected = IllegalArgumentException.class)
    public void testInvalidRoomBookingIntegration() {
        
		User user = new User("Alice", "normal", false); 
        // Create a mock room
        Room room = mock(Room.class);
        when(room.checkRoom("VIP")).thenReturn(true); 
        when(room.checkRoom("Deluxe")).thenReturn(true);
        when(room.checkRoom("Standard")).thenReturn(false);
        when(room.get_VIP()).thenReturn(2);
        when(room.get_deluxe()).thenReturn(1);
        when(room.get_standard()).thenReturn(0);
        
        // Create a waiting list
        WaitingList waitingList = new WaitingList();

        // Create a booking
        Booking booking = new Booking(user, 0, 0, 0);

        // Perform the booking
        booking.setBooking(room, waitingList, 3); //invalid requested room quantity for non member
        
        // Check if the booking was not added to the waiting list
        assertFalse(waitingList.getWaiting("normal").contains(user));
        
        // Cancel the booking
        booking.cancelBooking(room, waitingList);
        
        // Check if the booking was not in the waiting list
        assertFalse(waitingList.getWaiting("normal").contains(user));

    }
}
