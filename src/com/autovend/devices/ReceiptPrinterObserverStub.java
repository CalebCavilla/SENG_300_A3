package com.autovend.devices;

import com.autovend.devices.observers.AbstractDeviceObserver;
import com.autovend.devices.observers.ReceiptPrinterObserver;

public class ReceiptPrinterObserverStub implements ReceiptPrinterObserver {

	public AbstractDevice<? extends AbstractDeviceObserver> device = null;
	String name;
	public boolean inkAdded;
	public boolean paperAdded;
	public boolean outOfInk;
	public boolean outOfPaper;
	
	
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
		outOfPaper = true;
		System.out.println("Out of Paper!");
		
	}

	@Override
	public void reactToOutOfInkEvent(ReceiptPrinter printer) {
		outOfInk = true;
		System.out.println("Out of Ink!");
		
	}

	@Override
	public void reactToPaperAddedEvent(ReceiptPrinter printer) {
		paperAdded = true;
		System.out.println("Paper added!");
		
	}

	@Override
	public void reactToInkAddedEvent(ReceiptPrinter printer) {
		inkAdded = true;
		System.out.println("Ink added!");
		
	}

}
