package com.autovend.devices;

import java.util.Currency;

import com.autovend.Barcode;
import com.autovend.devices.AbstractDevice;
import com.autovend.devices.BillValidator;
import com.autovend.devices.observers.AbstractDeviceObserver;
import com.autovend.devices.observers.BillValidatorObserver;

public class BillStorageObserverStub implements BillValidatorObserver {

	/**
	 * Here, we will record the device on which an event occurs.
	 */
	public AbstractDevice<? extends AbstractDeviceObserver> device = null;

	// This is the name of this listener.
	public String name;
	// This is the currency of the bill the bill validator device checks
	public Currency currency;
	// This is the value of the bill the bill validator device checks
	public int value;
	
	
	public BillStorageObserverStub(String name) {
		this.name = name;
	}

	@Override
	public void reactToEnabledEvent(AbstractDevice<? extends AbstractDeviceObserver> device) {
		this.device = device;
		
	}

	@Override
	public void reactToDisabledEvent(AbstractDevice<? extends AbstractDeviceObserver> device) {
		this.device = device;
		
	}

	@Override
	public void reactToValidBillDetectedEvent(BillValidator validator, Currency currency, int value) {
		this.device = validator;
		this.currency = currency;
		this.value = value;
		System.out.println("Valid " + currency + " bill detected worth: " + value + " dollars");
		
	}

	@Override
	public void reactToInvalidBillDetectedEvent(BillValidator validator) {
		this.device = validator;
		System.out.println("Invalid bill detected");
		
	}

}
