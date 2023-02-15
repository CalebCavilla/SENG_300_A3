package com.autovend.hardware.test;

import java.util.Currency;

import com.autovend.devices.AbstractDevice;
import com.autovend.devices.BillValidator;
import com.autovend.devices.observers.AbstractDeviceObserver;
import com.autovend.devices.observers.BillValidatorObserver;

public class BillValidatorObserverDevice implements BillValidatorObserver {

	public BillValidatorObserverDevice() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void reactToEnabledEvent(AbstractDevice<? extends AbstractDeviceObserver> device) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void reactToDisabledEvent(AbstractDevice<? extends AbstractDeviceObserver> device) {
		// TODO Auto-generated method stub
		
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
