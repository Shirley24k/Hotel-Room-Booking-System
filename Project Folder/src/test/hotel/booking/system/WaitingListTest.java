package hotel.booking.system;
import hotel.booking.system.*;

import java.io.File;
import java.io.FileNotFoundException;
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
public class WaitingListTest {

	WaitingList waitingList = new WaitingList();
	static ArrayList<String[]> linesRead;
	static Scanner inputStream;

	@BeforeClass
	public static void setupClass() {
		linesRead = new ArrayList<String[]>();
		String fileName = "user.txt";
		inputStream = null;
		System.out.println("Reading test values from file " + fileName + "\n");
		try {
			inputStream = new Scanner(new File(fileName));
		} catch (FileNotFoundException e) {
			System.out.println("Error opening the file " + fileName);
			System.exit(0);
		}
		while (inputStream.hasNextLine()) {
			String singleLine = inputStream.nextLine();
			String[] tokens = singleLine.split(",");
			linesRead.add(tokens);
		}
	}

	@AfterClass
	public static void endClass() {
		// Closing the input file once all tests are complete
		System.out.println("Closing input file");
		inputStream.close();
	}

	@Before
	public void setupMethod() {
		System.out.println("Initializing the list");
		waitingList = new WaitingList();
	}

	/*
	 * WaitingList Class Unit Test
	 * Test Case 2.1.1
	 * Test add waiting list with VIP member_type
	 */
	
	@Test
	public void testValid_AddWaitingVIP() {
		User mockUser = mock(User.class);
		when(mockUser.get_member_type()).thenReturn(linesRead.get(0)[0]);
		Booking mockBooking = mock(Booking.class);
		when(mockBooking.getUser()).thenReturn(mockUser);
		waitingList.addWaiting(mockBooking);
		assertTrue(waitingList.getWaiting(linesRead.get(0)[0]).contains(mockUser));
	}
	
	/*
	 *WaitingList Class Unit Test
	 * Test Case 2.1.2
	 * Test add waiting list with member member_type
	 */
	
	@Test 
	public void testValid_AddWaitingMember() {
		User mockUser = mock(User.class);
		when(mockUser.get_member_type()).thenReturn(linesRead.get(1)[0]);
		Booking mockBooking = mock(Booking.class);
		when(mockBooking.getUser()).thenReturn(mockUser);
		waitingList.addWaiting(mockBooking);
		assertTrue(waitingList.getWaiting(linesRead.get(1)[0]).contains(mockUser));
	}
	
	/*
	 *WaitingList Class Unit Test
	 * Test Case 2.1.3
	 * Test add waiting list with normal member_type
	 */
	
	@Test
	public void testValid_AddWaitingNormal() {
		User mockUser = mock(User.class);
		when(mockUser.get_member_type()).thenReturn(linesRead.get(2)[0]);
		Booking mockBooking = mock(Booking.class);
		when(mockBooking.getUser()).thenReturn(mockUser);
		waitingList.addWaiting(mockBooking);
		assertTrue(waitingList.getWaiting(linesRead.get(2)[0]).contains(mockUser));
	}
		
	/*
	 *WaitingList Class Unit Test
	 * Test Case 2.2.1
	 * Test add waiting list with INVALID member_type
	 */
	
	@Test(expected=IllegalArgumentException.class)
	public void testInvalid1_AddWaiting() {
		User mockUser = mock(User.class);
		when(mockUser.get_member_type()).thenReturn(linesRead.get(3)[0]);
		Booking mockBooking = mock(Booking.class);
		when(mockBooking.getUser()).thenReturn(mockUser);
		waitingList.addWaiting(mockBooking);
	}
	
	/*
	 *WaitingList Class Unit Test
	 * Test Case 2.2.2
	 * Test add waiting list with INVALID member_type
	 */
	
	@Test(expected=IllegalArgumentException.class)
	public void testInvalid2_AddWaiting() {
		User mockUser = mock(User.class);
		when(mockUser.get_member_type()).thenReturn(linesRead.get(4)[0]);
		Booking mockBooking = mock(Booking.class);
		when(mockBooking.getUser()).thenReturn(mockUser);
		waitingList.addWaiting(mockBooking);
	}
	
	/*
	 *WaitingList Class Unit Test
	 * Test Case 2.2.3
	 * Test add waiting list with empty member_type
	 */
	
	@Test(expected=IllegalArgumentException.class)
	public void testInvalid3_AddWaiting() {
		User mockUser = mock(User.class);
		when(mockUser.get_member_type()).thenReturn(linesRead.get(5)[0]);
		Booking mockBooking = mock(Booking.class);
		when(mockBooking.getUser()).thenReturn(mockUser);
		waitingList.addWaiting(mockBooking);
	}
	
	/*
	 *WaitingList Class Unit Test
	 * Test Case 2.2.4
	 * Test add waiting list with null member_type
	 */
	
	@Test(expected=IllegalArgumentException.class)
	public void testInvalid4_AddWaiting() {
		User mockUser = mock(User.class);
		when(mockUser.get_member_type()).thenReturn(linesRead.get(6)[0]);
		Booking mockBooking = mock(Booking.class);
		when(mockBooking.getUser()).thenReturn(mockUser);
		waitingList.addWaiting(mockBooking);
	}
	
	/*
	 *WaitingList Class Unit Test
	 * Test Case 2.3.1
	 * Test the remove waiting list method with VIP member_type
	 */
	
	@Test
	public void testRemoveWaitingVIP() {
		User mockUser = mock(User.class);
		when(mockUser.get_member_type()).thenReturn(linesRead.get(0)[0]);
		Booking mockBooking = mock(Booking.class);
		when(mockBooking.getUser()).thenReturn(mockUser);
		waitingList.removeWaiting(mockBooking);		
		assertFalse(waitingList.getWaiting(linesRead.get(0)[0]).contains(mockUser));
	}
	
	/*
	 *WaitingList Class Unit Test
	 * Test Case 2.3.2
	 * Test the remove waiting list method with member member_type
	 */
	
	@Test
	public void testRemoveWaitingMember() {
		User mockUser = mock(User.class);
		when(mockUser.get_member_type()).thenReturn(linesRead.get(1)[0]);
		Booking mockBooking = mock(Booking.class);
		when(mockBooking.getUser()).thenReturn(mockUser);
		waitingList.removeWaiting(mockBooking);		
		assertFalse(waitingList.getWaiting(linesRead.get(1)[0]).contains(mockUser));
	}
	
	/*
	 *WaitingList Class Unit Test
	 * Test Case 2.3.3
	 * Test the remove waiting list method with normal member_type
	 */
	
	@Test
	public void testRemoveWaitingNormal() {
		User mockUser = mock(User.class);
		when(mockUser.get_member_type()).thenReturn(linesRead.get(2)[0]);
		Booking mockBooking = mock(Booking.class);
		when(mockBooking.getUser()).thenReturn(mockUser);
		waitingList.removeWaiting(mockBooking);		
		assertFalse(waitingList.getWaiting(linesRead.get(2)[0]).contains(mockUser));
	}
	
	/*
	 *WaitingList Class Unit Test
	 * Test Case 2.4.1
	 * Test the remove waiting list method with INVALID member_type
	 */
	
	//Test remove waiting list with INVALID member type
	@Test(expected=IllegalArgumentException.class)
	public void testInvalid1_RemoveWaiting() {
		User mockUser = mock(User.class);
		when(mockUser.get_member_type()).thenReturn(linesRead.get(3)[0]);
		Booking mockBooking = mock(Booking.class);
		when(mockBooking.getUser()).thenReturn(mockUser);
		waitingList.removeWaiting(mockBooking);	
	}
	
	/*
	 *WaitingList Class Unit Test
	 * Test Case 2.4.2
	 * Test the remove waiting list method with INVALID member_type
	 */
	
	@Test(expected=IllegalArgumentException.class)
	public void testInvalid2_RemoveWaiting() {
		User mockUser = mock(User.class);
		when(mockUser.get_member_type()).thenReturn(linesRead.get(4)[0]);
		Booking mockBooking = mock(Booking.class);
		when(mockBooking.getUser()).thenReturn(mockUser);
		waitingList.removeWaiting(mockBooking);	
	}
	
	/*
	 *WaitingList Class Unit Test
	 * Test Case 2.4.3
	 * Test the remove waiting list method with empty member_type
	 */
	
	@Test(expected=IllegalArgumentException.class)
	public void testInvalid3_RemoveWaiting() {
		User mockUser = mock(User.class);
		when(mockUser.get_member_type()).thenReturn(linesRead.get(5)[0]);
		Booking mockBooking = mock(Booking.class);
		when(mockBooking.getUser()).thenReturn(mockUser);
		waitingList.removeWaiting(mockBooking);	
	}
	
	/*
	 *WaitingList Class Unit Test
	 * Test Case 2.4.4
	 * Test the remove waiting list method with null member_type
	 */
	
	@Test(expected=IllegalArgumentException.class)
	public void testInvalid4_RemoveWaiting() {
		User mockUser = mock(User.class);
		when(mockUser.get_member_type()).thenReturn(linesRead.get(6)[0]);
		Booking mockBooking = mock(Booking.class);
		when(mockBooking.getUser()).thenReturn(mockUser);
		waitingList.removeWaiting(mockBooking);	
	}

	/*
	 *WaitingList Class Unit Test
	 * Test Case 2.5.1
	 * Test get waiting list to get waiting list based on member_type
	 */
	
	@Test
	public void testValid_GetWaiting() {
		// Test getWaiting for each member type
		for (int i = 7; i < 12; i++) {
			User mockUser = mock(User.class);
			when(mockUser.get_member_type()).thenReturn(linesRead.get(i)[0]);
			Booking mockBooking = mock(Booking.class);
			when(mockBooking.getUser()).thenReturn(mockUser);
			waitingList.addWaiting(mockBooking);
		}
		
		assertEquals(Integer.parseInt(linesRead.get(12)[0]), waitingList.getWaiting("VIP").size());
		assertEquals(Integer.parseInt(linesRead.get(13)[0]), waitingList.getWaiting("member").size());
		assertEquals(Integer.parseInt(linesRead.get(14)[0]), waitingList.getWaiting("normal").size());
	}
	
	/*
	 *WaitingList Class Unit Test
	 * Test Case 2.6.1
	 * Test get waiting list with INVALID member type
	 */
	
	
	@Test(expected=IllegalArgumentException.class)
	public void testInvalid1_GetWaiting() {
		waitingList.getWaiting(linesRead.get(3)[0]);
	}
	
	/*
	 *WaitingList Class Unit Test
	 * Test Case 2.6.2
	 * Test get waiting with INVALID member type
	 */
	
	@Test(expected=IllegalArgumentException.class)
	public void testInvalid2_GetWaiting() {
		waitingList.getWaiting(linesRead.get(4)[0]);
	}
	
	/*
	 *WaitingList Class Unit Test
	 * Test Case 2.6.3
	 * Test get waiting with empty member type
	 */
	
	@Test(expected=IllegalArgumentException.class)
	public void testInvalid3_GetWaiting() {
		waitingList.getWaiting(linesRead.get(5)[0]);
	}
	
	/*
	 *WaitingList Class Unit Test
	 * Test Case 2.6.4
	 * Test get waiting with null member type
	 */
	
	@Test(expected=IllegalArgumentException.class)
	public void testInvalid4_GetWaiting() {
		waitingList.getWaiting(linesRead.get(6)[0]);
	}

}
