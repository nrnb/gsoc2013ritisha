package org.cytoscape.fluxviz.internal.tasks;

import org.cytoscape.work.Task;
import org.cytoscape.work.TaskMonitor;

public class tpTask implements Task {

	@Override
	public void cancel() {
		;
	}

	@Override
	public void run(TaskMonitor arg0) throws Exception {
		System.out.println("in tp task");
	}

}
