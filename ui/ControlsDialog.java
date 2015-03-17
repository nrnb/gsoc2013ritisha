package org.cytoscape.fluxviz.internal.ui;

import javax.swing.JDialog;
import javax.swing.JPanel;

import org.cytoscape.fluxviz.internal.logic.ColumnsCreator;
import org.cytoscape.fluxviz.internal.logic.Context;
import org.cytoscape.fluxviz.internal.logic.ControlTunables;
import org.cytoscape.fluxviz.internal.tasks.ControlsMenuNetworkViewTaskFactory;
import org.cytoscape.model.CyNetwork;
import org.cytoscape.view.model.CyNetworkView;
import org.cytoscape.work.TaskFactory;
import org.cytoscape.work.swing.PanelTaskManager;

public class ControlsDialog extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	CyNetworkView networkView;
	Context appContext;
	CyNetwork network;
	String networkName;
	JPanel attributesPanel;
	PanelTaskManager panelTaskManager;
	
	public ControlsDialog(CyNetworkView networkView, Context appContext, ControlsMenuNetworkViewTaskFactory controlsMenuTaskFactory)
	{
		super(appContext.getSwingApplication().getJFrame(), "My FluxViz Controls", false);		

		this.networkView = networkView;
		this.appContext = appContext;
		this.network = networkView.getModel();
		this.attributesPanel = new JPanel();
		this.panelTaskManager = appContext.getPanelTaskManager();
		networkName = ColumnsCreator.DefaultNetworkTable.getRow(network.getSUID()).get(CyNetwork.NAME, String.class);
		add(attributesPanel);
		
		ControlTunables tunablesContext = new ControlTunables(appContext);
		
		if(panelTaskManager.validateAndApplyTunables(tunablesContext))
		{
			System.out.println("validateAndApplyTunables");
			TaskFactory taskFactory = appContext.getProvisioner().createFor(controlsMenuTaskFactory);
			JPanel tunablePanel = panelTaskManager.getConfiguration(taskFactory, tunablesContext);
			attributesPanel.removeAll();
			attributesPanel.add(tunablePanel);
		}
		
		setTitle(networkName + " FluxViz Controls");
        pack();
		setAlwaysOnTop(true);
		setResizable(false);
		setLocationRelativeTo(null);
		setVisible(true);
	}
}
