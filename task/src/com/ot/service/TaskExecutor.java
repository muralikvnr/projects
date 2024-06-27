package com.ot.service;

import java.util.concurrent.Future;

import com.ot.dto.Task;

public interface TaskExecutor {
	<T> Future<T> submitTask(Task<T> task);
}
