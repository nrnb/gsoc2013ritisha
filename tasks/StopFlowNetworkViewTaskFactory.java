package org.cytoscape.fluxviz.internal.tasks;

import org.cytoscape.fluxviz.internal.logic.Context;
import org.cytoscape.task.AbstractNetworkViewTaskFactory;
import org.cytoscape.view.model.CyNetworkView;
import org.cytoscape.work.TaskIterator;

public class StopFlowNetworkViewTaskFactory extends AbstractNetworkViewTaskFactory {

	Context appContext;
	
	public StopFlowNetworkViewTaskFactory(Context appContext)
	{
		super();
		this.appContext = appContext;
	}
	
	public boolean isReady(CyNetworkView networkView)
	{
		return (appContext.containsNetwork(networkView.getModel()) && !(appContext.getEvaluator() == null));
	}
	
	@Override
	public TaskIterator createTaskIterator(CyNetworkView networkView) {
		return new TaskIterator(new StopFlowNetworkViewTask(networkView, appContext));
	}
}
