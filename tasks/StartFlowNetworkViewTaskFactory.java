package org.cytoscape.fluxviz.internal.tasks;

import org.cytoscape.fluxviz.internal.logic.Context;
import org.cytoscape.fluxviz.internal.logic.Evaluator;
import org.cytoscape.fluxviz.internal.logic.ViewHandler;
import org.cytoscape.task.AbstractNetworkViewTaskFactory;
import org.cytoscape.view.model.CyNetworkView;
import org.cytoscape.work.TaskIterator;

public class StartFlowNetworkViewTaskFactory extends AbstractNetworkViewTaskFactory {

	ViewHandler viewHandler;
	Context appContext;
	boolean doRestart = false;

	/**
	 * Calls the StartFlowNetworkViewTask and the StopFlowNetworkViewTask. 
	 * @param nodeViewHandler
	 * @param edgeViewHandler
	 */
	public StartFlowNetworkViewTaskFactory(ViewHandler viewHandler, Context appContext, boolean doRestart)
	{
		super();
		this.viewHandler = viewHandler;
		this.appContext = appContext;
		this.doRestart = doRestart;
	}

	public boolean isReady(CyNetworkView networkView)
	{
		return (appContext.containsNetwork(networkView.getModel()) && (appContext.getEvaluator() == null));
	}

	@Override
	public TaskIterator createTaskIterator(CyNetworkView networkView) {
		appContext.setEvaluator(new Evaluator(viewHandler, appContext));
		viewHandler.refresh(networkView);
		return new TaskIterator(new StartFlowNetworkViewTask(networkView, viewHandler, appContext, doRestart));
	}
}
