package org.cytoscape.fluxviz.internal.tasks;

import org.cytoscape.fluxviz.internal.logic.ColumnsCreator;
import org.cytoscape.fluxviz.internal.logic.EdgeViewHandler;
import org.cytoscape.model.CyEdge;
import org.cytoscape.model.CyRow;
import org.cytoscape.task.AbstractEdgeViewTask;
import org.cytoscape.view.model.CyNetworkView;
import org.cytoscape.view.model.View;

import org.cytoscape.work.TaskMonitor;

/**
 * @author laungani
 *
 */
public class SetTypeEdgeViewTask extends AbstractEdgeViewTask {

	View<CyEdge> edgeView;
	CyNetworkView networkView;
	String type;
	EdgeViewHandler edgeViewHandler;
	public static String ACTIVATING = "Activating";
	public static String DEACTIVATING = "Deactivating";
	
	/**
	 * Sets the type of edge to Activating or Deactivating and sets the view accordingly
	 * @param edgeView
	 * @param networkView
	 * @param type
	 * @param edgeViewHandler
	 */
	public SetTypeEdgeViewTask(View<CyEdge> edgeView, CyNetworkView networkView, String type, EdgeViewHandler edgeViewHandler)
	{
		super(edgeView, networkView);
		this.edgeView = edgeView;
		this.networkView = networkView;
		this.type = type;
		this.edgeViewHandler = edgeViewHandler;
	}
	@Override
	public void run(TaskMonitor tm) throws Exception {

		//update type in default edge table
		CyRow row = ColumnsCreator.DefaultEdgeTable.getRow(edgeView.getModel().getSUID());
		row.set(ColumnsCreator.EDGE_TYPE, type);
		
		//update the look of the edge based on type
		edgeViewHandler.setEdgeView(edgeView.getModel(), networkView.getModel(), type);
	}

}
