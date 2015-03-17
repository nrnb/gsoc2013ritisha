package org.cytoscape.fluxviz.internal.tasks;

import org.cytoscape.fluxviz.internal.logic.Context;
import org.cytoscape.fluxviz.internal.logic.Evaluator;
import org.cytoscape.fluxviz.internal.logic.ViewHandler;
import org.cytoscape.task.AbstractNetworkViewTask;
import org.cytoscape.view.model.CyNetworkView;
import org.cytoscape.work.TaskMonitor;

public class StartFlowNetworkViewTask extends AbstractNetworkViewTask {

	CyNetworkView networkView;
	ViewHandler viewHandler;
	Context appContext;
	Evaluator evaluator;
	boolean doRestart;
	
	/**
	 * Calls the evaluate method of the Evaluator 
	 * @param networkView
	 * @param nodeViewHandler
	 * @param edgeViewHandler
	 */
	public StartFlowNetworkViewTask(CyNetworkView networkView, ViewHandler viewHandler, Context appContext, boolean doRestart)
	{
		super(networkView);
		this.networkView = networkView;
		this.viewHandler = viewHandler;
		this.appContext = appContext;
		this.evaluator = appContext.getEvaluator();
		this.doRestart = doRestart;
		
		//check and set visual mapping
		viewHandler.createFluxVizStyle();
	}
	@Override
	public void run(TaskMonitor tm) throws Exception {
		evaluator.startEvaluator(networkView, doRestart);
	}
}
