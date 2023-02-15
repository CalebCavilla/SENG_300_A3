package com.autovend.hardware.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertThrows;

import java.util.Currency;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.Test.None;

import com.autovend.devices.BillValidator;
import com.autovend.devices.BillValidatorObserverStub;
import com.autovend.devices.FlowThroughEmitterStub;
import com.autovend.Bill;
import com.autovend.devices.AcceptorStub;
import com.autovend.devices.BidirectionalChannel;
import com.autovend.devices.UnidirectionalChannelStub;
import com.autovend.devices.SimulationException;


public class BillValidatorTest {
	
	Currency currency;
	int[] denominations;
	
	public BillValidatorObserverStub listener1, listener2, listener3;

	/**
	 * Sets up the test suite. This is run before every test method.
	 */
	@Before
	public void setup() {
		currency = Currency.getInstance("CAD");
		denominations = new int[]{5, 10, 20, 50, 100};
		
		// Create 3 listeners ... so you can see which ones receive events and which
		// don't.
		listener1 = new BillValidatorObserverStub("listener1");
		listener2 = new BillValidatorObserverStub("listener2");
		listener3 = new BillValidatorObserverStub("listener3");

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
	@Test (expected = SimulationException.class)
	public void test_creating_validator_with_null_currency(){
		currency = null;
		// Simulation Exception is expected to be thrown because of null currency;
		BillValidator validator = new BillValidator(currency, denominations);
	}
	
	/** 
	 * Try's to create an instance of validator with a valid currency but invalid denominations.
	 * Expected that the validator is not constructed and simulation error is thrown describing how denominations is null.
	 */
	@Test (expected = SimulationException.class)
	public void test_creating_validator_with_null_denominations(){
		denominations = null;
		// Simulation Exception is expected to be thrown because of null denominations;
		BillValidator validator = new BillValidator(currency, denominations);
	}
	
	/**
	 * Try's to create an instance of validator with a valid currency but invalid denominations.
	 * Expected that the validator is not constructed and simulation error is thrown describing how denominations is empty.
	 */
	@Test (expected = SimulationException.class)
	public void test_creating_validator_with_empty_denominations(){
		denominations = new int[]{};
		// Simulation Exception is expected to be thrown because of empty denominations;
		BillValidator validator = new BillValidator(currency, denominations);
	}
	
	/**
	 * Try's to create an instance of validator with a valid currency but invalid denominations.
	 * Expected that the validator is not constructed and simulation error is thrown describing how denominations cannot be negative.
	 */
	@Test (expected = SimulationException.class)
	public void test_creating_validator_with_negative_denominations(){
		denominations = new int[]{5, -10, 20, 50, 100};
		// Simulation Exception is expected to be thrown because of negative denominations;
		BillValidator validator = new BillValidator(currency, denominations);
	}
	
	/**
	 * Try's to create an instance of validator with a valid currency but invalid denominations.
	 * Expected that the validator is not constructed and simulation error is thrown describing how denominations must be unique.
	 */
	@Test (expected = SimulationException.class)
	public void test_creating_validator_with_nonUnique_denominations(){
		denominations = new int[]{5, 10, 10, 20, 50, 50, 100};
		// Simulation Exception is expected to be thrown because of non-unique denominations;
		BillValidator validator = new BillValidator(currency, denominations);
	}
	
	/**
	 * Runs connect() method to see if the method passes without throwing an exception when both channels are defined.
	 * Expected that the connection is made without issue and no error is thrown
	 */
	@Test 
	public void test_valid_connection(){
		
		BillValidator validator = new BillValidator(currency, denominations);
		
		// give validator a listener and enable it.
		validator.register(listener1);
		validator.disable();
		validator.enable();
		
		// validator should have been constructed with no issues
		assertNotEquals(null, validator);
		// listener1 should have successfully been registered as one of 'validator' listeners.
		assertEquals(validator, listener1.device);
		
		UnidirectionalChannelStub<Bill> unidirectionalChannel = new UnidirectionalChannelStub<Bill>(new AcceptorStub());
		BidirectionalChannel<Bill> bidirectionalChannel = new BidirectionalChannel<Bill>(new FlowThroughEmitterStub(), new AcceptorStub());
		validator.connect(bidirectionalChannel, unidirectionalChannel);
		
	}
	
	/**
	 * Runs connect() method to see if the method properly throws an exception when trying to connect a null channel.
	 * Expected that the connection fails and a simulation exception is thrown
	 */
	@Test (expected = SimulationException.class)
	public void test_invalid_connection(){
		
		BillValidator validator = new BillValidator(currency, denominations);
		
		// give validator a listener and enable it.
		validator.register(listener1);
		validator.disable();
		validator.enable();
		
		// validator should have been constructed with no issues
		assertNotEquals(null, validator);
		// listener1 should have successfully been registered as one of 'validator' listeners.
		assertEquals(validator, listener1.device);
		
		UnidirectionalChannelStub<Bill> unidirectionalChannel = new UnidirectionalChannelStub<Bill>(new AcceptorStub());
		BidirectionalChannel<Bill> bidirectionalChannel = null;
		validator.connect(bidirectionalChannel, unidirectionalChannel);
		
	}
	
	
	
	@After
	public void teardown() {
		listener1 = null;
		listener2 = null;
		listener3 = null;
	}

}
