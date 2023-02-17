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

import com.autovend.devices.AcceptorStub;
import com.autovend.devices.BidirectionalChannel;
import com.autovend.devices.BillStorage;
import com.autovend.devices.SimulationException;
import com.autovend.devices.UnidirectionalChannelStub;
import com.autovend.devices.BillStorageObserverStub;
import com.autovend.devices.BillValidator;
import com.autovend.Bill;
import com.autovend.devices.DisabledException;
import com.autovend.devices.FlowThroughEmitterStub;
import com.autovend.devices.OverloadException;

public class BillStorageTest {
	
	Bill[] storage;
	UnidirectionalChannelStub<Bill> unidirectionalChannel;
	BidirectionalChannel<Bill> bidirectionalChannel;
	
	public BillStorageObserverStub listener1, listener2, listener3;
	private int capacity = 10; 
	private int bill = 3;
	
	/**
	 * Sets up the test suite. This is run before every test method.
	 */
	@Before
	public void setup() {
		storage = new Bill[] {};
		// set default 'valid' instances for the channels
		unidirectionalChannel = new UnidirectionalChannelStub<Bill>(new AcceptorStub(false));
		bidirectionalChannel = new BidirectionalChannel<Bill>(new FlowThroughEmitterStub(), new AcceptorStub(false));
		
		// Create 3 listeners ... so you can see which ones receive events and which
		// don't.
		listener1 = new BillStorageObserverStub("listener1");

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
		
		BillStorage validator = new BillStorage(capacity);
		validator.register(listener1);
		validator.disable();
		validator.enable();
		
		assertNotEquals(null, validator);
		// listener1 should have successfully been registered as one of 'validator' listeners.
		assertEquals(validator, listener1.device);
		
	}
	@Test (expected = SimulationException.class)
	public void test_creating_validator_with_negative_capacity() {
		int capacity = -1;
		BillStorage validator = new BillStorage(capacity);
	}
	
	/**
	 * Runs load()
	 * 
	 */
	
	@Test 
	public void test_valid_loading() {
		BillStorage validator = new BillStorage(capacity);
		
		// give validator a listener and enable it.
		validator.register(listener1);
		validator.disable();
		validator.enable();
		
		// validator should have been constructed with no issues
		assertNotEquals(null, validator);
		// listener1 should have successfully been registered as one of 'validator' listeners.
		assertEquals(validator, listener1.device);
		

	}
	@Test (expected = SimulationException.class)
	public void test_invalid_loading_null_bill() {
		BillStorage validator = new BillStorage(capacity);

		
		// give validator a listener and enable it.
		validator.register(listener1);
		validator.disable();
		validator.enable();

	}	
	@Test (expected = SimulationException.class)
	public void test_invalid_loading_overcapacity() {
		
		BillStorage validator = new BillStorage(capacity);
		int capacity = 9;
		int nextIndex = 10;
		
		// give validator a listener and enable it.
		validator.register(listener1);
		validator.disable();
		validator.enable();

	}


	/**
	 * Runs the accept method with a valid Canadian 5$ bill with space in the acceptor but the validator is left disabled
	 * Expected that a DisabledException is thrown
	 */
	@Test (expected = DisabledException.class)
	public void test_valid_Bill_while_disabled(){
		
		BillStorage validator = new BillStorage(capacity); 
		
		// give validator a listener and enable it.
		validator.register(listener1);
		validator.disable();


	}
	
	/**
	 * Runs the accept method with an invalid null bill
	 * Expected that a SimulationException is thrown
	 */
	@Test (expected = SimulationException.class)
	public void test_invalid_bill_null(){
		
		BillStorage validator = new BillStorage(capacity);
		Bill bill = null;
		
		// give validator a listener and enable it.
		validator.register(listener1);
		validator.disable();
		validator.enable();

		
	}
	
	@Test 
	public void test_MaxStorage_equal_nextInt(){
		
		BillStorage validator = new BillStorage(capacity);
		
		// give validator a listener and enable it.
		validator.register(listener1);
		validator.disable();
		validator.enable();


	}
	
	@Test (expected = OverloadException.class)
	public void test__MaxStorage_not_equal_nextInt(){
		
		BillStorage validator = new BillStorage(capacity);
		
		
		
		// give validator a listener and enable it.
		validator.register(listener1);
		validator.disable();
		validator.enable();


	}
	
	@Test 
	public void test__index_not_greater_than_storage_length(){
		
		BillStorage validator = new BillStorage(capacity);
		
		int nextIndex = 1;
		storage = new Bill[] {};
		// give validator a listener and enable it.
		validator.register(listener1);
		validator.disable();
		validator.enable();

	}
	
	@After
	public void teardown() {
		listener1 = null;
		listener2 = null;
		listener3 = null;
	}

}
