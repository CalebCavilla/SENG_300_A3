package com.autovend.hardware.test;

import java.util.Currency;

import org.junit.After;
import org.junit.Before;

import com.autovend.Barcode;
import com.autovend.BarcodedUnit;
import com.autovend.Numeral;

import com.autovend.devices.BillValidator;
import com.autovend.devices.observers.SampleBarcodeScannerObserver;
import com.autovend.devices.observers.BillValidatorObserver;

public class BillValidatorTest {

	public BillValidator validator;
	Currency currency;
	int[] denominations;
	public BillValidatorObserverDevice listener1, listener2, listener3;

	/**
	 * Sets up the test suite. This is run before every test method.
	 */
	@Before
	public void setup() {
		
		validator = new BillValidator(currency, denominations);

		
		// Create 3 listeners ... so you can see which ones receive events and which
		// don't.
		listener1 = new BillValidatorObserverDevice();
		listener2 = new BillValidatorObserverDevice();
		listener3 = new BillValidatorObserverDevice();

		// Initialize the fields inside the listeners. Having these fields public would
		// be a bad idea in real code, but this is just a demo.
		listener1.device = null;
		listener1.barcode = null;
		listener2.device = null;
		listener2.barcode = null;
		listener3.device = null;
		listener3.barcode = null;

		// We'll register the first and second listeners, but not the third for now.
		validator.register(listener1);
		validator.register(listener2);
		
		// Explicitly enable the scanner so that the listeners find out the device on which they are registered.
		// Usually this isn't necessary for normal use, but this simplified the demo.
		scanner.disable();
		scanner.enable();
	}

	@After
	public void teardown() {
		scanner = null;
		barcode = null;
		item = null;
		listener1 = null;
		listener2 = null;
		listener3 = null;
	}

}
