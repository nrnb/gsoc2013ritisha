package org.cytoscape.fluxviz.internal.tasks;

import org.cytoscape.work.TaskFactory;
import org.cytoscape.work.TaskIterator;

public class tpTaskFactory implements TaskFactory {

	public tpTaskFactory()
	{
		System.out.println("registrng tp task");
	}
	@Override
	public TaskIterator createTaskIterator() {
		System.out.println("creating tp task itr");
		return new TaskIterator(new tpTask());
	}

	@Override
	public boolean isReady() {
		return true;
	}

}
