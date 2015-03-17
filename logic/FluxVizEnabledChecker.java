package org.cytoscape.fluxviz.internal.logic;

import org.cytoscape.model.CyNetwork;
import org.cytoscape.model.CyRow;
import org.cytoscape.model.events.NetworkAddedEvent;
import org.cytoscape.model.events.NetworkAddedListener;

public class FluxVizEnabledChecker implements NetworkAddedListener {

	Context appContext;
	ViewHandler viewHandler;
	
	public FluxVizEnabledChecker(Context appContext, ViewHandler viewHandler)
	{
		this.appContext = appContext;
		this.viewHandler = viewHandler;
	}
	
	@Override
	public void handleEvent(NetworkAddedEvent e) {
		CyNetwork network = e.getNetwork();
		CyRow row = network.getDefaultNetworkTable().getRow(network.getSUID());
		if(network.getDefaultNetworkTable().getColumn(ColumnsCreator.IS_FV_ENABLED) != null && 
				row.isSet(ColumnsCreator.IS_FV_ENABLED) && 
				row.get(ColumnsCreator.IS_FV_ENABLED, Boolean.class) && 
				!appContext.containsNetwork(network))
		{
			appContext.addNetwork(network);
			ColumnsCreator.createColumns(network, viewHandler);	
			viewHandler.createFluxVizStyle();	
		}		
	}

}
