package com.util;

import java.util.UUID;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class UUIDGenerator {
	public static  UUID getUUID() {
		Lock lock = new ReentrantLock();
		lock.lock();
		try {
			return UUID.randomUUID();
		}finally {
			lock.unlock();
		}
		
	}
}
