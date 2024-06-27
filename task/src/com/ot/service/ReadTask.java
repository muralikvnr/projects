package com.ot.service;

import java.util.concurrent.Callable;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReadTask<T> implements Callable<T> {
	ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
	public T t;
	public ReadTask(T t) {
		this.t = t;
	}
	
	@Override
	public T call() throws Exception {
		lock.readLock().lock();
	    try {
	    	return t;
	    }finally{
	    	lock.readLock().unlock();
	    }
	}

}
