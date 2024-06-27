 package com.ot;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import com.ot.dto.Task;
import com.ot.dto.TaskGroup;
import com.ot.dto.TaskType;
import com.ot.service.ReadTask;
import com.ot.service.TaskProcess;
import com.ot.service.WriteTask;
import com.util.UUIDGenerator;

public class Main {
  private static Logger LOG = Logger.getLogger(Main.class.getName());	
	public static void main(String[] args) {
		LOG.info("Process started:");
		Set<String> results = new TaskProcess().process(getEmpDtos());
		for (String val : results) {
			LOG.info(val);
		}
		LOG.info("Process End:");
	}
	
	private static List<Task> getEmpDtos(){
		
		TaskGroup taskGroup1 = new TaskGroup(UUIDGenerator.getUUID());
		TaskGroup taskGroup2 = new TaskGroup(UUIDGenerator.getUUID());
		TaskGroup taskGroup3 = new TaskGroup(UUIDGenerator.getUUID());
		TaskGroup taskGroup4 = new TaskGroup(UUIDGenerator.getUUID());
		
		List<Task> dtos = new ArrayList<>();
		Task o1 = new Task(UUIDGenerator.getUUID(),taskGroup1,TaskType.WRITE, new WriteTask("WRITE-1"));
		Task o2 = new Task(UUIDGenerator.getUUID(),taskGroup1,TaskType.READ,new ReadTask("READ-2"));
		Task o3 = new Task(UUIDGenerator.getUUID(),taskGroup2,TaskType.WRITE,new WriteTask("WRITE-3"));
		Task o4 = new Task(UUIDGenerator.getUUID(),taskGroup2,TaskType.READ,new ReadTask("READ-4"));
		Task o5 = new Task(UUIDGenerator.getUUID(),taskGroup3,TaskType.WRITE,new WriteTask("WRITE-5"));
		Task o6 = new Task(UUIDGenerator.getUUID(),taskGroup4,TaskType.WRITE,new WriteTask("WRITE-6"));
		
		dtos.add(o1);
		dtos.add(o2);
		dtos.add(o3);
		dtos.add(o4);
		dtos.add(o5);
		dtos.add(o6);
		
		return dtos;
	  }
}
