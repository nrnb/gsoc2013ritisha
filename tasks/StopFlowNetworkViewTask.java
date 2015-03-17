package org.cytoscape.fluxviz.internal.tasks;

import org.cytoscape.fluxviz.internal.logic.Context;
import org.cytoscape.task.AbstractNetworkViewTask;
import org.cytoscape.view.model.CyNetworkView;
import org.cytoscape.work.TaskMonitor;

public class StopFlowNetworkViewTask extends AbstractNetworkViewTask {

	Context appContext;
	/**
	 * Interrupts the thread start by StartFlowNetworkViewTask
	 * @param networkView
	 * @param appContext
	 */
	public StopFlowNetworkViewTask(CyNetworkView networkView, Context appContext)
	{
		super(networkView);
		this.appContext = appContext;
	}
	@Override
	public void run(TaskMonitor arg0) throws Exception {
		appContext.getEvaluator().stopEvaluator();
		appContext.setEvaluator(null);
	}
}
