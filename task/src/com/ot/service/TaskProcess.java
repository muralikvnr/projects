package com.ot.service;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import com.ot.dto.Task;

public class TaskProcess<T> {
  private static Logger LOG = Logger.getLogger(TaskProcess.class.getName());
  private ExecutorService service;
  
  public Set<T> process(List<Task> tasks){
	Set<T> results = new HashSet<>();
	try {
		service = Executors.newFixedThreadPool(getTotalThreads());
		
		Map<UUID, List<Task>> taskGroupMap = tasks.stream().collect(Collectors.groupingBy(v->v.taskGroup().groupUUID(), Collectors.mapping(v->v, Collectors.toList())));
		
		TaskExecutor taskService = new TaskExecutorImpl(service);
		for (UUID groupId : taskGroupMap.keySet()) {
			results.addAll(taskProcess(taskGroupMap.get(groupId),taskService));
		}
		service.shutdown();
	}catch(Exception e) {
		LOG.fine("TaskProcess-->process() Error while executing the process:"+e.getMessage());
		if(service!=null) {
		  service.shutdown();
		}
	}
	return results;
}
	
	private Set<T> taskProcess(List<Task> tasks,TaskExecutor taskService) {
		Set results = new HashSet();
		for (Task task : tasks) {
			Future<String> submitTask = taskService.submitTask(task);
			try {
				results.add(submitTask.get());
			} catch (InterruptedException | ExecutionException e) {
				LOG.fine("TaskProcess-->taskProcess() Error in taskProcess method:"+e.getMessage());
			}
		}
		return results;
	}
	private static int getTotalThreads() {
		int threadCount = 1;
		 Properties properties = new Properties();
		 String PROPERTIES_FILE_PATH ="resources/task.properties";
		 LOG.info("Properties file Path is: "+ PROPERTIES_FILE_PATH);
		 try(InputStream inputStream = new FileInputStream(PROPERTIES_FILE_PATH)){		   
		   properties.load(inputStream);
		   threadCount = Integer.valueOf(properties.getProperty("total.threads"));
		   LOG.info("Total number of threads are :"+threadCount);
		  } catch (IOException e) {
			  LOG.fine("Error while reading a file. Path is"+ PROPERTIES_FILE_PATH);
		  } 
		  return threadCount;
	}
}
