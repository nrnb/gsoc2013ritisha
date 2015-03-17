package org.cytoscape.fluxviz.internal.logic;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.cytoscape.fluxviz.internal.tasks.SetTypeNodeViewTask;
import org.cytoscape.model.CyNetwork;
import org.cytoscape.model.CyNode;
import org.cytoscape.model.CyRow;
import org.cytoscape.model.events.AddedNodesEvent;
import org.cytoscape.model.events.AddedNodesListener;

/**
 * Sets the defaults for the app-specific columns for the existing edges and newly added edges
 * @author laungani
 *
 */
public class NodeDefaultsSetter implements AddedNodesListener {

	NodeViewHandler nodeViewHandler;
	Context appContext;
	
	public NodeDefaultsSetter(NodeViewHandler nodeViewHandler, Context appContext)
	{
		this.nodeViewHandler = nodeViewHandler;
		this.appContext = appContext;
	}
	@Override
	public void handleEvent(AddedNodesEvent e) {

		CyNetwork network = e.getSource();
		if(appContext.containsNetwork(network))
		{
			Collection<CyNode> nodes = new ArrayList<CyNode>();
			nodes = e.getPayloadCollection();
			addDefaults(network, nodes, nodeViewHandler);
		}	
	}
	
	/**
	 * Adds defaults based on type, and calls the nodeViewHandler method to set default view
	 * @param network
	 * @param nodes
	 * @param nodeViewHandler
	 */
	public static void addDefaults(CyNetwork network, Collection<CyNode> nodes, NodeViewHandler nodeViewHandler)
	{
		CyRow row;
		
		for(CyNode currNode : nodes)
		{
			row = ColumnsCreator.DefaultNodeTable.getRow(currNode.getSUID());
			List<Double> portEfficiency = new ArrayList<Double>();
			portEfficiency.add(1.0);
			
			if(!row.isSet(ColumnsCreator.NODE_TYPE))
				row.set(ColumnsCreator.NODE_TYPE, SetTypeNodeViewTask.KINASE);
			
			if(!row.isSet(ColumnsCreator.NUM_OF_INPUTS))
				row.set(ColumnsCreator.NUM_OF_INPUTS, 1);
			
			if(!row.isSet(ColumnsCreator.TIME_RAMP))
				row.set(ColumnsCreator.TIME_RAMP, 1.0);
			
			if(!row.isSet(ColumnsCreator.RELATIVE_CONCENTRATION))
				row.set(ColumnsCreator.RELATIVE_CONCENTRATION, 1.0);
			
			if(!row.isSet(ColumnsCreator.DECAY))
				row.set(ColumnsCreator.DECAY, 0.0001);
			
			if(!row.isSet(ColumnsCreator.INITIAL_OUTPUT_VALUE))
				row.set(ColumnsCreator.INITIAL_OUTPUT_VALUE, 0.5);
			
			if(!row.isSet(ColumnsCreator.PORT_EFFICIENCY))
				row.set(ColumnsCreator.PORT_EFFICIENCY, portEfficiency);
			
			if(!row.isSet(ColumnsCreator.INTEGER_OUTPUT_NODE_THRESH))
				row.set(ColumnsCreator.INTEGER_OUTPUT_NODE_THRESH, 0.5);
			
			if(!row.isSet(ColumnsCreator.UPPER_BOUND))
				row.set(ColumnsCreator.UPPER_BOUND, 1.0);
			
			if(!row.isSet(ColumnsCreator.EDGE_SUM))
				row.set(ColumnsCreator.EDGE_SUM, 0.0);
			
			if(!row.isSet(ColumnsCreator.CURR_OUTPUT))
				row.set(ColumnsCreator.CURR_OUTPUT, 0.5);
			
			String type = row.get(ColumnsCreator.NODE_TYPE, String.class);
			
			row = ColumnsCreator.HiddenNodeTable.getRow(currNode.getSUID());
			
			if(!row.isSet(ColumnsCreator.NEXT_OUTPUT))
				row.set(ColumnsCreator.NEXT_OUTPUT, 0.0);	
			
			nodeViewHandler.setNodeView(currNode, network, type);
		}
	}
}
