package com.ot.service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

import com.ot.dto.Task;

public class TaskExecutorImpl implements TaskExecutor {
	private ExecutorService service;
	public TaskExecutorImpl(ExecutorService service) {
		this.service = service;
	}
	@Override
	public <T> Future<T> submitTask(Task<T> task) {
		return service.submit(task.taskAction());
	}
}
