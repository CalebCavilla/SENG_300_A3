package com.autovend.hardware.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import java.util.Currency;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.Test.None;

import com.autovend.devices.BillValidator;
import com.autovend.devices.BillValidatorObserverStub;
import com.autovend.devices.DisabledException;
import com.autovend.devices.FlowThroughEmitterStub;
import com.autovend.Bill;
import com.autovend.devices.AcceptorStub;
import com.autovend.devices.BidirectionalChannel;
import com.autovend.devices.UnidirectionalChannelStub;
import com.autovend.devices.SimulationException;


public class BillValidatorTest {
	
	Currency currency;
	int[] denominations;
	UnidirectionalChannelStub<Bill> unidirectionalChannel;
	BidirectionalChannel<Bill> bidirectionalChannel;
	
	public BillValidatorObserverStub listener1, listener2, listener3;

	/**
	 * Sets up the test suite. This is run before every test method.
	 */
	@Before
	public void setup() {
		
		// set default 'valid' values for the currency and denominations
		currency = Currency.getInstance("CAD");
		denominations = new int[]{5, 10, 20, 50, 100};
		
		// set default 'valid' instances for the channels
		unidirectionalChannel = new UnidirectionalChannelStub<Bill>(new AcceptorStub(false));
		bidirectionalChannel = new BidirectionalChannel<Bill>(new FlowThroughEmitterStub(), new AcceptorStub(false));
		
		
		// Create 3 listeners ... so you can see which ones receive events and which
		// don't.
		listener1 = new BillValidatorObserverStub("listener1");

		// Initialize the fields inside the listeners. Having these fields public would
		// be a bad idea in real code, but this is just a demo.
		listener1.device = null;

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
		
		validator.connect(bidirectionalChannel, unidirectionalChannel);
		
	}
	
	/**
	 * Runs connect() method to see if the method properly throws an exception when trying to connect a null bidirectional channel.
	 * Expected that the connection fails and a simulation exception is thrown
	 */
	@Test (expected = SimulationException.class)
	public void test_invalid_connection_null_bidirectional(){
		
		BillValidator validator = new BillValidator(currency, denominations);
		bidirectionalChannel = null;
		
		// give validator a listener and enable it.
		validator.register(listener1);
		validator.disable();
		validator.enable();
		// Simulation error is expected to be thrown from the null bidirectionalChannel
		validator.connect(bidirectionalChannel, unidirectionalChannel);
		
	}
	
	/**
	 * Runs connect() method to see if the method properly throws an exception when trying to connect a null Unidirectional channel.
	 * Expected that the connection fails and a simulation exception is thrown
	 */
	@Test (expected = SimulationException.class)
	public void test_invalid_connection_null_Unidirectional(){
		
		BillValidator validator = new BillValidator(currency, denominations);
		unidirectionalChannel = null;
		
		// give validator a listener and enable it.
		validator.register(listener1);
		validator.disable();
		validator.enable();
		// Simulation error is expected to be thrown from the null bidirectionalChannel
		validator.connect(bidirectionalChannel, unidirectionalChannel);
		
	}
	
	
	/**
	 * Runs the accept method with a valid Canadian 5$ bill with space in the acceptor
	 * registered observers should record the reactToValidBillEvent and the bill should be delivered
	 * Expected that a $5 CAD bill is accepted
	 */
	@Test 
	public void test__valid_Bill_with_Space(){
		
		BillValidator validator = new BillValidator(currency, denominations);
		Bill bill = new Bill(5, Currency.getInstance("CAD"));
		
		// give validator a listener and enable it.
		validator.register(listener1);
		validator.disable();
		validator.enable();
		validator.connect(bidirectionalChannel, unidirectionalChannel);
		// make the validator check a bill
		boolean result = validator.accept(bill);
		// double check that the currency and value of the bill detected by the lister is the same as the one originally passed.
		assertEquals(bill.getCurrency(), listener1.currency);
		assertEquals(bill.getValue(), listener1.value);
		assertTrue(result);
	}
	
	/**
	 * Runs the accept method with a valid Canadian 5$ bill with space in the acceptor but the validator is left disabled
	 * Expected that a DisabledException is thrown
	 */
	@Test (expected = DisabledException.class)
	public void test_valid_Bill_while_disabled(){
		
		BillValidator validator = new BillValidator(currency, denominations);
		Bill bill = new Bill(5, Currency.getInstance("CAD"));
		
		// give validator a listener and enable it.
		validator.register(listener1);
		validator.disable();
		validator.connect(bidirectionalChannel, unidirectionalChannel);
		// make the validator check a bill
		validator.accept(bill);
		
	}
	
	/**
	 * Runs the accept method with a valid Canadian 5$ bill with NO space in the acceptor
	 * registered observers should record the reactToValidBillEvent, but the bill should be ejected (not accepted)
	 * Expected that a $5 CAD bill is NOT accepted
	 */
	@Test 
	public void test_valid_Bill_with_no_space(){
		
		BillValidator validator = new BillValidator(currency, denominations);
		Bill bill = new Bill(5, Currency.getInstance("CAD"));
		// unidirectionalChannel is defined with a full acceptor.
		unidirectionalChannel = new UnidirectionalChannelStub<Bill>(new AcceptorStub(true));
		
		// give validator a listener and enable it.
		validator.register(listener1);
		validator.disable();
		validator.enable();
		validator.connect(bidirectionalChannel, unidirectionalChannel);
		boolean result = validator.accept(bill);
		// even though the bill should be rejected, it is still a valid bill so reactToValidBillEvent should still be triggered
		assertEquals(bill.getCurrency(), listener1.currency);
		assertEquals(bill.getValue(), listener1.value);
		// the validator should NOT accept the bill due to the full acceptor
		assertFalse(result);
		
		
	}
	
	/**
	 * Runs the accept method with a invalid $5 USD bill
	 * registered observers should record the reactToInvalidBillEvent
	 * Expected that a $5 USD bill is NOT accepted
	 */
	@Test 
	public void test_invalid_bill_wrong_currency(){
		
		BillValidator validator = new BillValidator(currency, denominations);
		// notice bill is in USD, while validator is configured to accept CAD
		Bill bill = new Bill(5, Currency.getInstance("USD"));

		
		// give validator a listener and enable it.
		validator.register(listener1);
		validator.disable();
		validator.enable();
		validator.connect(bidirectionalChannel, unidirectionalChannel);
		boolean result = validator.accept(bill);
		
		// the validator should NOT accept the bill due to the non matching currency types 
		assertFalse(result);
		
		
	}
	
	/**
	 * Runs the accept method with a invalid $121 CAN bill
	 * registered observers should record the reactToInvalidBillEvent
	 * Expected that a $121 CAN bill is NOT accepted
	 */
	@Test 
	public void test_invalid_bill_wrong_denomination(){
		
		BillValidator validator = new BillValidator(currency, denominations);
		// notice bill is in USD, while validator is configured to accept CAD
		Bill bill = new Bill(121, Currency.getInstance("CAD"));

		
		// give validator a listener and enable it.
		validator.register(listener1);
		validator.disable();
		validator.enable();
		validator.connect(bidirectionalChannel, unidirectionalChannel);
		boolean result = validator.accept(bill);
		
		// the validator should NOT accept the bill due to the non matching currency types 
		assertFalse(result);
		
		
	}
	
	
	/**
	 * Runs the accept method with an invalid null bill
	 * Expected that a SimulationException is thrown
	 */
	@Test (expected = SimulationException.class)
	public void test_invalid_bill_null(){
		
		BillValidator validator = new BillValidator(currency, denominations);
		Bill bill = null;
		
		// give validator a listener and enable it.
		validator.register(listener1);
		validator.disable();
		validator.enable();
		validator.connect(bidirectionalChannel, unidirectionalChannel);
		// make the validator check a bill
		validator.accept(bill);
		
	}
	
	
	
	@After
	public void teardown() {
		listener1 = null;
	}

}
