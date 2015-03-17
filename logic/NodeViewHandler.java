package org.cytoscape.fluxviz.internal.logic;

import java.util.Collection;
import java.util.HashSet;

import org.cytoscape.fluxviz.internal.tasks.SetTypeNodeViewTask;
import org.cytoscape.model.CyNetwork;
import org.cytoscape.model.CyNode;
import org.cytoscape.view.model.CyNetworkView;
import org.cytoscape.view.model.CyNetworkViewManager;
import org.cytoscape.view.model.View;
import org.cytoscape.view.presentation.property.BasicVisualLexicon;
import org.cytoscape.view.presentation.property.NodeShapeVisualProperty;
import org.cytoscape.view.vizmap.VisualMappingManager;
import org.cytoscape.view.vizmap.VisualStyle;

/**
 * Handles setting of node view based on type
 * @author laungani
 *
 */
public class NodeViewHandler {
	
	CyNetworkViewManager cyNetworkViewManager;
	VisualMappingManager visualMappingManager;
	
	public NodeViewHandler(Context appContext)
	{
		this.cyNetworkViewManager = appContext.getNetworkViewManager();
		this.visualMappingManager = appContext.getVisualMappingManager();
	}
	
	/**
	 * Sets default node view which is that of kinase
	 * @param node
	 * @param network
	 */
	public void setDefaultNodeView(CyNode node, CyNetwork network)
	{
		Collection<CyNetworkView> networkViewCollection = new HashSet<CyNetworkView>();
		networkViewCollection = cyNetworkViewManager.getNetworkViews(network);
		CyNetworkView networkView = networkViewCollection.iterator().next();
		networkView.getNodeView(node).setLockedValue(BasicVisualLexicon.NODE_SHAPE, NodeShapeVisualProperty.DIAMOND);
		refresh(networkView);
	}
	
	/**
	 * Sets node view based on type
	 * @param nodeView
	 * @param networkView
	 * @param type
	 */
	public void setNodeView(CyNode node, CyNetwork network, String type)
	{
		Collection<CyNetworkView> networkViewCollection = new HashSet<CyNetworkView>();
		networkViewCollection = cyNetworkViewManager.getNetworkViews(network);
		CyNetworkView networkView = networkViewCollection.iterator().next();
		View<CyNode> nodeView = networkView.getNodeView(node);
		
		if(type.equals(SetTypeNodeViewTask.KINASE))
		{
			nodeView.setLockedValue(BasicVisualLexicon.NODE_SHAPE, NodeShapeVisualProperty.DIAMOND);
		}
		else if(type.equals(SetTypeNodeViewTask.GTPASE))
		{
			nodeView.setLockedValue(BasicVisualLexicon.NODE_SHAPE, NodeShapeVisualProperty.HEXAGON);
		}
		else if(type.equals(SetTypeNodeViewTask.MOLECULES))
		{
			nodeView.setLockedValue(BasicVisualLexicon.NODE_SHAPE, NodeShapeVisualProperty.TRIANGLE);
		}
		else if(type.equals(SetTypeNodeViewTask.RECEPTOR) )
		{
			nodeView.setLockedValue(BasicVisualLexicon.NODE_SHAPE, NodeShapeVisualProperty.PARALLELOGRAM);
		}
		else if(type.equals(SetTypeNodeViewTask.RECEPTOR_T_KINASE))
		{
			nodeView.setLockedValue(BasicVisualLexicon.NODE_SHAPE, NodeShapeVisualProperty.PARALLELOGRAM);
		}
		else if(type.equals(SetTypeNodeViewTask.PHOSPHATASE))
		{
			nodeView.setLockedValue(BasicVisualLexicon.NODE_SHAPE, NodeShapeVisualProperty.OCTAGON);
		}
		
		refresh(networkView);
	}
	
	public void refresh(CyNetworkView networkView)
	{
		VisualStyle style = visualMappingManager.getCurrentVisualStyle();
		style.apply(networkView);
		networkView.updateView();
	}

}
