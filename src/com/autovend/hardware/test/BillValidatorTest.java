package com.autovend.hardware.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.util.Currency;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.autovend.devices.BillValidator;


public class BillValidatorTest {
	
	Currency currency = Currency.getInstance("CAD");
	
	int[] denominations = {5, 10, 20, 50, 100};
	public BillValidatorObserverDevice listener1, listener2, listener3;

	/**
	 * Sets up the test suite. This is run before every test method.
	 */
	@Before
	public void setup() {
		
		// Create 3 listeners ... so you can see which ones receive events and which
		// don't.
		listener1 = new BillValidatorObserverDevice("listener1");
		listener2 = new BillValidatorObserverDevice("listener2");
		listener3 = new BillValidatorObserverDevice("listener3");

		// Initialize the fields inside the listeners. Having these fields public would
		// be a bad idea in real code, but this is just a demo.
		listener1.device = null;
		listener2.device = null;
		listener3.device = null;

	}

	
	/**
	 * Creates an instance of validator with a valid currency and denominations.
	 * Expected that the validator is constructed without issue. 
	 */
	@Test
	public void test_creating_valid_validator(){
		BillValidator validator = new BillValidator(currency, denominations);
		
		// give validator a listener and enable it.
		validator.register(listener1);
		validator.disable();
		validator.enable();
		
		
		// validator should have been constructed with no issues
		assertNotEquals(null, validator);
		// listener1 should have successfully been registered as one of 'validator' listeners.
		assertEquals(validator, listener1.device);
	}
	
	
	@After
	public void teardown() {
		listener1 = null;
		listener2 = null;
		listener3 = null;
	}

}
