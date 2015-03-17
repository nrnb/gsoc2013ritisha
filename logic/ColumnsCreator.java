package org.cytoscape.fluxviz.internal.logic;


import java.util.ArrayList;
import java.util.List;

import org.cytoscape.fluxviz.internal.tasks.SetTypeEdgeViewTask;
import org.cytoscape.fluxviz.internal.tasks.SetTypeNodeViewTask;
import org.cytoscape.model.CyEdge;
import org.cytoscape.model.CyNetwork;
import org.cytoscape.model.CyNode;
import org.cytoscape.model.CyRow;
import org.cytoscape.model.CyTable;
import org.cytoscape.model.events.NetworkAddedEvent;
import org.cytoscape.model.events.NetworkAddedListener;

/**
 * 
 * @author laungani
 * The class handles creating app-specific columns like decay etc.
 *
 */
public class ColumnsCreator implements NetworkAddedListener {

	public static CyTable DefaultNodeTable;
	public static CyTable DefaultEdgeTable;
	public static CyTable HiddenNodeTable;
	public static CyTable DefaultNetworkTable;
	
	public static String NODE_TYPE = "Node Type";
	public static String NUM_OF_INPUTS = "Number of Inputs";
	public static String TIME_RAMP = "Time Ramp";
	public static String RELATIVE_CONCENTRATION = "Relative Concentration";
	public static String DECAY = "Decay";
	public static String INITIAL_OUTPUT_VALUE = "Initial value of Output";
	public static String PORT_EFFICIENCY = "Port Efficiency";
	public static String INTEGER_OUTPUT_NODE_THRESH = "Integer Output Node Threshold";
	public static String UPPER_BOUND = "Upper Bound";
	public static String EDGE_SUM = "Edge Sum";
	public static String CURR_OUTPUT = "Current Output";
	public static String NEXT_OUTPUT = "Next Output";
	
	public static String EDGE_TYPE = "Edge Type";
	public static String TARGET_INPUT = "Target Input";
	public static String EDGE_EFFICIENCY = "Edge Efficiency";
	public static String EDGE_SOURCE_NODE_OUTPUT = "Source Node Output";
	
	public static String IS_FV_ENABLED = "Is FluxViz Enabled";
	
	CyNetwork network;
	ViewHandler viewHandler;

	public ColumnsCreator(ViewHandler viewHandler)
	{
		this.viewHandler = viewHandler;
	}
	
	@Override
	public void handleEvent(NetworkAddedEvent e) {

		network = e.getNetwork();
		createColumns(network, viewHandler);
	}
	
	/**
	 * @param network the columns are created in the tables of this network
	 * @param nodeViewHandler the object whose method will be called to set default view for nodes
	 * @param edgeViewHandler the object whose method will be called to set default view for edges
	 */
	public static void createColumns(CyNetwork network, ViewHandler viewHandler)
	{
		//get tables
		DefaultNodeTable = network.getDefaultNodeTable();
		DefaultEdgeTable = network.getDefaultEdgeTable();
		DefaultNetworkTable = network.getDefaultNetworkTable();
		HiddenNodeTable = network.getTable(CyNode.class, CyNetwork.HIDDEN_ATTRS);
		List<CyNode> allNodes = new ArrayList<CyNode>();
		List<CyEdge> allEdges = new ArrayList<CyEdge>();
		
		//add columns
		if(DefaultNodeTable.getColumn(NODE_TYPE) == null)
			DefaultNodeTable.createColumn(NODE_TYPE, String.class, true, SetTypeNodeViewTask.KINASE);

		if(DefaultNodeTable.getColumn(NUM_OF_INPUTS) == null)
			DefaultNodeTable.createColumn(NUM_OF_INPUTS, Integer.class, true, 1);

		if(DefaultNodeTable.getColumn(TIME_RAMP) == null)
			DefaultNodeTable.createColumn(TIME_RAMP, Double.class, true, 1.0);

		if(DefaultNodeTable.getColumn(RELATIVE_CONCENTRATION) == null)
			DefaultNodeTable.createColumn(RELATIVE_CONCENTRATION, Double.class, true, 1.0);

		if(DefaultNodeTable.getColumn(DECAY) == null)
			DefaultNodeTable.createColumn(DECAY, Double.class, true, 0.0001);
		
		if(DefaultNodeTable.getColumn(INITIAL_OUTPUT_VALUE) == null)
			DefaultNodeTable.createColumn(INITIAL_OUTPUT_VALUE, Double.class, true, 0.0);
		
		if(DefaultNodeTable.getColumn(PORT_EFFICIENCY) == null)
			DefaultNodeTable.createListColumn(PORT_EFFICIENCY, Double.class, true);
		
		if(DefaultNodeTable.getColumn(INTEGER_OUTPUT_NODE_THRESH) == null)
			DefaultNodeTable.createColumn(INTEGER_OUTPUT_NODE_THRESH, Double.class, true, 0.5);
		
		if(DefaultNodeTable.getColumn(UPPER_BOUND) == null)
			DefaultNodeTable.createColumn(UPPER_BOUND, Double.class, true, 1.0);
		
		if(DefaultNodeTable.getColumn(CURR_OUTPUT) == null)
			DefaultNodeTable.createColumn(CURR_OUTPUT, Double.class, true, 0.5);
		
		if(DefaultNodeTable.getColumn(EDGE_SUM) == null)
			DefaultNodeTable.createColumn(EDGE_SUM, Double.class, true, 0.0);
		
		if(HiddenNodeTable.getColumn(NEXT_OUTPUT) == null)
			HiddenNodeTable.createColumn(NEXT_OUTPUT, Double.class, true, 0.0);
		
		if(DefaultEdgeTable.getColumn(EDGE_TYPE) == null)
			DefaultEdgeTable.createColumn(EDGE_TYPE, String.class, true, SetTypeEdgeViewTask.ACTIVATING);
		
		if(DefaultEdgeTable.getColumn(TARGET_INPUT) == null)
			DefaultEdgeTable.createColumn(TARGET_INPUT, Integer.class, true, 1);
		
		if(DefaultEdgeTable.getColumn(EDGE_EFFICIENCY) == null)
			DefaultEdgeTable.createColumn(EDGE_EFFICIENCY, Double.class, true, 1.0);
		
		if(DefaultEdgeTable.getColumn(EDGE_SOURCE_NODE_OUTPUT) == null)
			DefaultEdgeTable.createColumn(EDGE_SOURCE_NODE_OUTPUT, Double.class, true, 0.5);
		
		if(DefaultNetworkTable.getColumn(IS_FV_ENABLED) == null)
			DefaultNetworkTable.createColumn(IS_FV_ENABLED, Boolean.class, true);
		
		CyRow row = DefaultNetworkTable.getRow(network.getSUID());
		if(!row.isSet(ColumnsCreator.IS_FV_ENABLED))
			row.set(ColumnsCreator.IS_FV_ENABLED, true);
		
		allNodes = network.getNodeList();
		allEdges = network.getEdgeList();
		EdgeDefaultsSetter.addDefaults(network, allEdges, viewHandler.getEdgeViewHandler());
		NodeDefaultsSetter.addDefaults(network, allNodes, viewHandler.getNodeViewHandler());
	}
}
