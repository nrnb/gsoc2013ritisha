package org.cytoscape.fluxviz.internal.tasks;

import org.cytoscape.fluxviz.internal.logic.Context;
import org.cytoscape.model.CyNode;
import org.cytoscape.task.AbstractNodeViewTaskFactory;
import org.cytoscape.view.model.CyNetworkView;
import org.cytoscape.view.model.View;
import org.cytoscape.work.TaskIterator;

public class EditNodeAttributesTaskFactory extends AbstractNodeViewTaskFactory {

	Context appContext;
	
	public EditNodeAttributesTaskFactory(Context appContext)
	{
		super();
		this.appContext = appContext;
	}
	public boolean isReady(View<CyNode> nodeView, CyNetworkView networkView)
	{
		return appContext.containsNetwork(networkView.getModel());
	}
	
	@Override
	public TaskIterator createTaskIterator(View<CyNode> nodeView, CyNetworkView networkView) {
		return new TaskIterator(new EditNodeAttributesTask(nodeView, networkView));
	}

}
