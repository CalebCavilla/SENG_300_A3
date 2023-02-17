package com.autovend.devices;

import java.util.Currency;

import com.autovend.Barcode;
import com.autovend.devices.AbstractDevice;
import com.autovend.devices.BillStorage;
import com.autovend.devices.observers.AbstractDeviceObserver;
import com.autovend.devices.observers.BillStorageObserver;

public class BillStorageObserverStub implements BillStorageObserver {

	/**
	 * Here, we will record the device on which an event occurs.
	 */
	public AbstractDevice<? extends AbstractDeviceObserver> device = null;

	// This is the name of this listener.
	public String name;
	
	public BillStorage unit;
	
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
	public void reactToBillsFullEvent(BillStorage unit) {
	
		this.unit= unit;
		System.out.println("The indicated bill storage unit is full of bills");
	}
	@Override
	public void reactToBillAddedEvent(BillStorage unit) {
		this.unit= unit;
		System.out.println("A bill has been added to the indicated storage unit");
		}
	@Override
	public void reactToBillsLoadedEvent(BillStorage unit) {
		this.unit= unit;
		System.out.println("The indicated storage unit has been loaded with bills");
	}
	@Override
	public void reactToBillsUnloadedEvent(BillStorage unit) {
		this.unit= unit;
		System.out.println("The storage unit has been emptied of bills");
	}
}


