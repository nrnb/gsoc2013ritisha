package org.cytoscape.fluxviz.internal.logic;

import java.awt.event.ActionEvent;
import java.util.Map;
import java.util.Properties;

import org.cytoscape.application.CyApplicationManager;
import org.cytoscape.application.events.SetCurrentNetworkViewEvent;
import org.cytoscape.application.events.SetCurrentNetworkViewListener;
import org.cytoscape.application.swing.AbstractCyAction;
import org.cytoscape.view.model.CyNetworkViewManager;
import org.cytoscape.view.model.events.NetworkViewAddedEvent;
import org.cytoscape.view.model.events.NetworkViewAddedListener;

public class ControlsActions extends AbstractCyAction implements
		SetCurrentNetworkViewListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ControlsActions(Map<String, String> props, CyApplicationManager applicationManager, CyNetworkViewManager networkViewManager)
	{
		super(props, applicationManager, networkViewManager);
	}
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void handleEvent(SetCurrentNetworkViewEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
