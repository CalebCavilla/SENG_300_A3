package com.autovend.devices;

import java.util.Currency;

import com.autovend.Barcode;
import com.autovend.devices.AbstractDevice;
import com.autovend.devices.BillValidator;
import com.autovend.devices.observers.AbstractDeviceObserver;
import com.autovend.devices.observers.BillValidatorObserver;

public class BillValidatorObserverStub implements BillValidatorObserver {

	/**
	 * Here, we will record the device on which an event occurs.
	 */
	public AbstractDevice<? extends AbstractDeviceObserver> device = null;

	/**
	 * This is the name of this listener.
	 */
	public String name;
	
	
	public BillValidatorObserverStub(String name) {
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
		// TODO Auto-generated method stub
		
	}

	@Override
	public void reactToInvalidBillDetectedEvent(BillValidator validator) {
		// TODO Auto-generated method stub
		
	}

}
