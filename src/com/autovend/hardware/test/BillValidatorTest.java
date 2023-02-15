package com.autovend.hardware.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertThrows;

import java.util.Currency;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.autovend.devices.BillValidator;
import com.autovend.devices.SimulationException;


public class BillValidatorTest {
	
	Currency currency;
	int[] denominations;
	
	public BillValidatorObserverDevice listener1, listener2, listener3;

	/**
	 * Sets up the test suite. This is run before every test method.
	 */
	@Before
	public void setup() {
		currency = Currency.getInstance("CAD");
		denominations = new int[]{5, 10, 20, 50, 100};
		
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
	
	/**
	 * Try's to create an instance of validator with an invalid currency but valid denominations.
	 * Expected that the validator is not constructed and simulation error is thrown describing how currency is null.
	 */
	@Test
	public void test_creating_validator_with_null_currency(){
		currency = null;
		// Simulation Exception is expected to be thrown because of null currency;
		assertThrows("currency is null", SimulationException.class, () -> new BillValidator(currency, denominations));
	}
	
	/**
	 * Try's to create an instance of validator with a valid currency but invalid denominations.
	 * Expected that the validator is not constructed and simulation error is thrown describing how denominations is null.
	 */
	@Test
	public void test_creating_validator_with_null_denominations(){
		denominations = null;
		// Simulation Exception is expected to be thrown because of null denominations;
		assertThrows("denominations is null", SimulationException.class, () -> new BillValidator(currency, denominations));
	}
	
	/**
	 * Try's to create an instance of validator with a valid currency but invalid denominations.
	 * Expected that the validator is not constructed and simulation error is thrown describing how denominations is empty.
	 */
	@Test
	public void test_creating_validator_with_empty_denominations(){
		denominations = null;
		// Simulation Exception is expected to be thrown because of empty denominations;
		assertThrows("There must be at least one denomination.", SimulationException.class, () -> new BillValidator(currency, denominations));
	}
	
	
	@After
	public void teardown() {
		listener1 = null;
		listener2 = null;
		listener3 = null;
	}

}
