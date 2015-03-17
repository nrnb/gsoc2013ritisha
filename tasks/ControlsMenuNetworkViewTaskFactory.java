package org.cytoscape.fluxviz.internal.tasks;

import org.cytoscape.fluxviz.internal.logic.Context;
import org.cytoscape.task.AbstractNetworkViewTaskFactory;
import org.cytoscape.view.model.CyNetworkView;
import org.cytoscape.work.TaskIterator;

public class ControlsMenuNetworkViewTaskFactory extends AbstractNetworkViewTaskFactory {

	Context appContext;
	
	public ControlsMenuNetworkViewTaskFactory(Context appContext)
	{
		super();
		this.appContext = appContext;
	}
	
	public boolean isReady(CyNetworkView networkView)
	{
		return (appContext.containsNetwork(networkView.getModel()));
	}
	
	@Override
	public TaskIterator createTaskIterator(CyNetworkView networkView) {
		return new TaskIterator(new ControlsMenuNetworkViewTask(networkView, appContext, this));
	}

}
