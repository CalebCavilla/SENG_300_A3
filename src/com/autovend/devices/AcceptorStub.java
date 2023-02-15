package com.autovend.devices;

import com.autovend.Bill;


/**
 * A simple bill acceptor that will always accept bill and always has space.
 */

public class AcceptorStub implements Acceptor<Bill> {

	public AcceptorStub() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean accept(Bill bill) throws OverloadException, DisabledException {
		return true;
	}

	@Override
	public boolean hasSpace() {
		return true;
	}

}
