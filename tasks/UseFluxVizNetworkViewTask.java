package org.cytoscape.fluxviz.internal.tasks;

import org.cytoscape.fluxviz.internal.logic.ColumnsCreator;
import org.cytoscape.fluxviz.internal.logic.Context;
import org.cytoscape.fluxviz.internal.logic.ViewHandler;
import org.cytoscape.task.AbstractNetworkViewTask;
import org.cytoscape.view.model.CyNetworkView;
import org.cytoscape.work.TaskMonitor;

public class UseFluxVizNetworkViewTask extends AbstractNetworkViewTask {

	ViewHandler viewHandler;
	Context appContext;
	CyNetworkView networkView;
	
	public UseFluxVizNetworkViewTask(CyNetworkView networkView, ViewHandler viewHandler, Context appContext)
	{
		super(networkView);
		this.appContext = appContext;
		this.networkView = networkView;
		this.viewHandler = viewHandler;
	}
	
	@Override
	public void run(TaskMonitor arg0) throws Exception {
		
		appContext.addNetwork(networkView.getModel());
		ColumnsCreator.createColumns(networkView.getModel(), viewHandler);	
		viewHandler.createFluxVizStyle();		
	}

}
