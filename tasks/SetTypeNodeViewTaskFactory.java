package org.cytoscape.fluxviz.internal.tasks;

import org.cytoscape.fluxviz.internal.logic.Context;
import org.cytoscape.fluxviz.internal.logic.ViewHandler;
import org.cytoscape.model.CyNode;
import org.cytoscape.task.AbstractNodeViewTaskFactory;
import org.cytoscape.view.model.CyNetworkView;
import org.cytoscape.view.model.View;
import org.cytoscape.work.TaskIterator;

public class SetTypeNodeViewTaskFactory extends AbstractNodeViewTaskFactory {

	String nodeType;
	ViewHandler viewHandler;
	Context appContext;
	
	/**
	 * Calls the SetTypeNodeViewTask
	 * @param type
	 * @param nodeViewHandler
	 * @param edgeViewHandler
	 */
	public SetTypeNodeViewTaskFactory(String type, ViewHandler viewHandler, Context appContext)
	{
		super();
		nodeType = type;
		this.viewHandler = viewHandler;
		this.appContext = appContext;
	}
	
	public boolean isReady(View<CyNode> nodeView, CyNetworkView networkView)
	{
		return appContext.containsNetwork(networkView.getModel());
	}
	
	@Override
	public TaskIterator createTaskIterator(View<CyNode> nodeView, CyNetworkView networkView) {
		return new TaskIterator(new SetTypeNodeViewTask(nodeView, networkView, nodeType, viewHandler));
	}
}
