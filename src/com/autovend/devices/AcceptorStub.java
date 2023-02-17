package com.autovend.devices;

import com.autovend.Bill;


/**
 * A simple bill acceptor that will accept a bill if it has space, otherwise it will reject .
 */

public class AcceptorStub implements Acceptor<Bill> {

	boolean full;
	
	public AcceptorStub(boolean full) {
		this.full = full;
	}

	@Override
	public boolean accept(Bill bill) throws OverloadException, DisabledException {
		return hasSpace();
	}          

	@Override
	public boolean hasSpace() {
		if (this.full) {
			return false;
		}else {
			return true;
		}
	}

}
