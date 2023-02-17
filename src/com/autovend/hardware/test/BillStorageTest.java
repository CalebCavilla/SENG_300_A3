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
	int[] bills;
	
	/**
	 * Sets up the test suite. This is run before every test method.
	 */
	@Before
	public void setup() {
		storage = new Bill[] {};
		
		bills = new int[] {};
		
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
	public void test_creating_valid_storage(){
		
		BillStorage storage = new BillStorage(capacity);
		storage.register(listener1);
		storage.disable();
		storage.enable();
		
		assertNotEquals(null, storage);
		// listener1 should have successfully been registered as one of 'validator' listeners.
		assertEquals(storage, listener1.device);
		 
	}
	@Test (expected = SimulationException.class)
	public void test_not_creating_storage_with_negative_capacity() {
		capacity = (-1);
		BillStorage storage = new BillStorage(capacity);
	}
	
	/**
	 * Runs load()
	 * 
	 */
	@Test 
	public void test_storage_loading() {
		BillStorage storage = new BillStorage(capacity);
		
		// give storage a listener and enable it.
		storage.register(listener1);
		storage.disable();
		storage.enable();
		
		// validator should have been constructed with no issues
		assertNotEquals(null, storage);
		// listener1 should have successfully been registered as one of 'validator' listeners.
		assertEquals(storage, listener1.device);
		

	}
	//Test if bills == null
	@Test (expected = SimulationException.class)
	public void test_storage_loading_null_bills() {
		BillStorage storage = new BillStorage(capacity);
		bills = null;
		// give storage a listener and enable it.
		storage.register(listener1);
		storage.disable();
		storage.enable();

	}	
	//Test if (bills.length + nextIndex > storage.length)
	@Test (expected = SimulationException.class)
	public void test_storage_loading_overcapacity() {
		
		BillStorage storage = new BillStorage(capacity);
		int capacity = 9;
		int nextIndex = 10;
		
		// give storage a listener and enable it.
		storage.register(listener1);
		storage.disable();
		storage.enable();

	}


	// disabled
	@Test (expected = DisabledException.class)
	public void test_storage_Bill_while_disabled(){
		
		BillStorage storage = new BillStorage(capacity); 
		Bill bill = new Bill(5, Currency.getInstance("CAD"));
		
		// give validator a listener and enable it.
		storage.register(listener1);
		storage.disable();
		try {
			storage.accept(bill);
		} catch (DisabledException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (OverloadException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//Test if bill == null
	@Test (expected = SimulationException.class)
	public void test_storage_bill_null(){
		
		BillStorage storage = new BillStorage(capacity);
		Bill bill = null;
		
		// give storage a listener and enable it.
		storage.register(listener1);
		storage.disable();
		storage.enable();

		
	}
	//Test if storage.length() == nextInt()
	@Test 
	public void test_Storage_length_equal_nextInt(){
		
		BillStorage storage = new BillStorage(capacity);
		
		// give storage a listener and enable it.
		storage.register(listener1);
		storage.disable();
		storage.enable();


	}
	//Test if storage.length() != nextInt()
	@Test (expected = OverloadException.class)
	public void test_Storage_not_equal_nextInt(){
		
		BillStorage storage = new BillStorage(capacity);
		
		
		// give storage a listener and enable it.
		storage.register(listener1);
		storage.disable();
		storage.enable();


	}
	//Test if storage.length() > nextInt()
	@Test 
	public void test_storage_length_greater_than_nextInt(){
		
		BillStorage storage = new BillStorage(capacity);
		
		int nextIndex = 1;

		// give storage a listener and enable it.
		storage.register(listener1);
		storage.disable();
		storage.enable();

	}
	
	@After
	public void teardown() {
		listener1 = null;
		listener2 = null;
		listener3 = null;
	}

}
