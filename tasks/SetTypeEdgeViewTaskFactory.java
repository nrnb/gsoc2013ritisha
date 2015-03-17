package org.cytoscape.fluxviz.internal.tasks;

import org.cytoscape.fluxviz.internal.logic.Context;
import org.cytoscape.fluxviz.internal.logic.EdgeViewHandler;
import org.cytoscape.model.CyEdge;
import org.cytoscape.task.EdgeViewTaskFactory;
import org.cytoscape.view.model.CyNetworkView;
import org.cytoscape.view.model.View;
import org.cytoscape.work.TaskIterator;

public class SetTypeEdgeViewTaskFactory implements EdgeViewTaskFactory {

	String edgeType;
	EdgeViewHandler edgeViewHandler;
	Context appContext;
	
	/**
	 * Calls the SetTypeEdgeViewTask
	 * @param edgeType
	 * @param edgeViewHandler
	 */
	public SetTypeEdgeViewTaskFactory(String edgeType, EdgeViewHandler edgeViewHandler, Context appContext)
	{
		this.edgeType = edgeType;
		this.edgeViewHandler = edgeViewHandler;
		this.appContext = appContext;
	}
	
	@Override
	public TaskIterator createTaskIterator(View<CyEdge> edgeView, CyNetworkView networkView) {
		return new TaskIterator(new SetTypeEdgeViewTask(edgeView, networkView, edgeType, edgeViewHandler));
	}

	@Override
	public boolean isReady(View<CyEdge> edgeView, CyNetworkView networkView) {
		return appContext.containsNetwork(networkView.getModel());
	}

}
