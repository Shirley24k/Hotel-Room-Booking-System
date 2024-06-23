package hotel.booking.system;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import hotel.booking.system.*;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(JUnitParamsRunner.class)
public class UserTest {
	
	/*
	 * User Class Unit Test
	 * Test Case 1.1.1~1.1.5
	 * test with VALID user constructor
	 */
	
	@Test
	@Parameters(method = "ValidUser")
	public void testValidUser(String name, String member_type, boolean excl_reward) {
		User user = new User(name, member_type, excl_reward);
		//Verify User object is created successfully
		assertEquals(name, user.getName());
		assertEquals(member_type, user.get_member_type());
		assertEquals(excl_reward, user.get_excl_reward());
	}
	
	private Object[] ValidUser() {
		return new Object[] {
				new Object[] {"Alicia", "VIP", false},
				new Object[] {"Zebra", "VIP", true},
				new Object[] {"Barbel", "member", false},
				new Object[] {"Joey", "member", true},
				new Object[] {"Cariene", "normal", false}	
		};
	}
	
	/*
	 * User Class Unit Test
	 * Test Case 1.2.1~1.2.17
	 * test with INVALID user constructor
	 */
	
	@Test(expected=IllegalArgumentException.class)
	@Parameters(method = "InvalidUser")
	public void testInvalidUser(String name, String member_type, boolean excl_reward) {
		User user = new User(name, member_type, excl_reward);
	}
	
	private Object[] InvalidUser() {
		return new Object[] {
				new Object[]{null, "VIP", false},    			//VIP member with null name (no exclusive reward)
				new Object[]{null, "VIP", true},    			//VIP member with null name (exclusive reward)
				new Object[]{null, "member", false},     		//Normal member with null name (no exclusive reward)
				new Object[]{null, "member", true},     		//Normal member with null name (exclusive reward)
				new Object[]{null, "normal", false},   			//Non-member with null name
				new Object[]{"", "VIP", false},    				//VIP member with empty name (no exclusive reward)
				new Object[]{"", "VIP", true},					//VIP member with empty name (exclusive reward)
				new Object[]{"", "member", false},     			//Normal member (no exclusive reward) with empty name
				new Object[]{"", "member", true},     			//Normal member (exclusive reward) with empty name
				new Object[]{"", "normal", false},  			//Non-member with empty name
				new Object[]{"Alice", null, false},    			//null member with no exclusive reward
				new Object[]{"Brian", null, true},     			//null member with exclusive reward
				new Object[]{"Cecelia", "", false},     		//empty member with no exclusive reward
				new Object[]{"Desmond", "", true},   			//empty member with exclusive reward
				new Object[]{"Esther", "vipMember", false},    	//invalid member with no exclusive reward
				new Object[]{"Felicia", "try123", true},     	//invalid member with exclusive reward
				new Object[] {"Alice", "normal", true}			//non-member with exclusive reward
		};
	}
	
	/*
	 * User Class Unit Test
	 * Test Case 1.3.1
	 * test with VALID set exclusive reward
	 */
	
	
	@Test
	public void testValidSetUserExclReward() {
		User user = new User("Alice", "member", true);
		user.set_excl_reward(false);
		assertEquals(false, user.get_excl_reward());
	}
	
	/*
	 * User Class Unit Test
	 * Test Case 1.4.1
	 * test with INVALID set exclusive reward
	 */
	
	
	@Test(expected=IllegalArgumentException.class)
	public void testInvalidSetUserExclReward() 
	{
		User user = new User("Alice", "normal", false);
		user.set_excl_reward(true);
	}
}
