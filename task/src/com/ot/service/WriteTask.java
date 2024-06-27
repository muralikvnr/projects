package com.ot.service;

import java.util.concurrent.Callable;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class WriteTask<T> implements Callable<T> {
	ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
	public T t;
	public WriteTask(T t) {
		this.t = t;
	}
	
	@Override
	public T call() throws Exception {
		lock.writeLock().lock();
		try {
			return t;
		}finally {
			lock.writeLock().unlock();
		}
	}

}
