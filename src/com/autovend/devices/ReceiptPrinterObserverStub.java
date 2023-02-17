package com.autovend.devices;

import com.autovend.devices.observers.AbstractDeviceObserver;
import com.autovend.devices.observers.ReceiptPrinterObserver;

public class ReceiptPrinterObserverStub implements ReceiptPrinterObserver {

	public AbstractDevice<? extends AbstractDeviceObserver> device = null;
	String name;
	
	
	public ReceiptPrinterObserverStub(String name) {
		this.name = name;
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
	public void reactToOutOfPaperEvent(ReceiptPrinter printer) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void reactToOutOfInkEvent(ReceiptPrinter printer) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void reactToPaperAddedEvent(ReceiptPrinter printer) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void reactToInkAddedEvent(ReceiptPrinter printer) {
		// TODO Auto-generated method stub
		
	}

}
