package com.autovend.devices;

public class UnidirectionalChannelStub<T> extends UnidirectionalChannel<T> {

	public UnidirectionalChannelStub(Acceptor<T> sink) {
		super(sink);
	}

}
