package com.autovend.devices;

import com.autovend.Bill;
/*
 * Simple Bill emitter that will never fail for testing purposes
 */
public class FlowThroughEmitterStub implements FlowThroughEmitter<Bill> {

	public FlowThroughEmitterStub() {
		// TODO Auto-generated constructor stub
	}

	/*
	 * stub will always emit no matter what
	 */
	@Override
	public boolean emit(Bill bill) throws DisabledException, OverloadException {
		return true;
	}

}
