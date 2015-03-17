package org.cytoscape.fluxviz.internal.tasks;

import java.util.ArrayList;
import java.util.List;

import org.cytoscape.fluxviz.internal.logic.ColumnsCreator;
import org.cytoscape.fluxviz.internal.logic.EdgeViewHandler;
import org.cytoscape.fluxviz.internal.logic.ViewHandler;
import org.cytoscape.model.CyEdge;
import org.cytoscape.model.CyNetwork;
import org.cytoscape.model.CyNode;
import org.cytoscape.model.CyRow;
import org.cytoscape.task.AbstractNodeViewTask;
import org.cytoscape.view.model.CyNetworkView;
import org.cytoscape.view.model.View;

import org.cytoscape.work.TaskMonitor;

public class SetTypeNodeViewTask extends AbstractNodeViewTask {

	View<CyNode> nodeView;
	CyNetworkView networkView;
	String type;
	ViewHandler viewHandler;
	public static String KINASE = "Kinase";
	public static String MOLECULES = "Molecules";
	public static String GTPASE = "GTPase";
	public static String RECEPTOR = "Receptor";
	public static String RECEPTOR_T_KINASE = "ReceptorTKinase";
	public static String PHOSPHATASE = "Phosphatase";
	
	/**
	 * Sets the node type as selected by user
	 * @param nodeView
	 * @param networkView
	 * @param type
	 * @param nodeViewHandler
	 * @param edgeViewHandler
	 */
	public SetTypeNodeViewTask(View<CyNode> nodeView, CyNetworkView networkView, String type, ViewHandler viewHandler)
	{
		super(nodeView,networkView);	
		this.nodeView = nodeView;
		this.networkView = networkView;
		this.type = type;
		this.viewHandler = viewHandler;
	}
	@Override
	public void run(TaskMonitor tm) throws Exception {

		//set the default values in columns
		setDefaultsForNodeType();
		
		//update the view for node
		viewHandler.getNodeViewHandler().setNodeView(nodeView.getModel(), networkView.getModel(), type);
		
		//get all the outgoing edge, update their columns and views
		List<CyEdge> outgoingEdges = new ArrayList<CyEdge>();
		outgoingEdges = networkView.getModel().getAdjacentEdgeList(nodeView.getModel(), CyEdge.Type.OUTGOING);	
		
		for(CyEdge currEdge : outgoingEdges)
		{
			setDefaultsForEdgeBasedOnNodeType(currEdge, networkView.getModel(), type, viewHandler.getEdgeViewHandler());
		}
	}
	
	/**
	 * Sets defaults for outgoing edges from the node based on node type
	 * @param edge
	 * @param network
	 * @param nodeType
	 * @param edgeViewHandler
	 */
	public static void setDefaultsForEdgeBasedOnNodeType(CyEdge edge, CyNetwork network, String nodeType, EdgeViewHandler edgeViewHandler)
	{
		String edgeTypeVal = SetTypeEdgeViewTask.ACTIVATING;
		double edgeEfficiencyVal = 1.0;
		int targetInputVal = 1;
		
		//getting the source node output
		CyNode sourceNode = edge.getSource();
		double edgeSourceNodeOutputVal = ColumnsCreator.DefaultNodeTable.getRow(sourceNode.getSUID()).get(ColumnsCreator.CURR_OUTPUT, Double.class);
		
		if(nodeType.equals(KINASE))
		{
			edgeTypeVal = SetTypeEdgeViewTask.ACTIVATING;
			edgeEfficiencyVal = 0.2;
			targetInputVal = 1;
		}
		
		else if(nodeType.equals(GTPASE))
		{
			edgeTypeVal = SetTypeEdgeViewTask.ACTIVATING;
			edgeEfficiencyVal = 0.08;
			targetInputVal = 1;
		}
		
		else if(nodeType.equals(MOLECULES))
		{
			edgeTypeVal = SetTypeEdgeViewTask.ACTIVATING;
			edgeEfficiencyVal = 0.4;
			targetInputVal = 1;
		}
		
		else if(nodeType.equals(RECEPTOR))
		{
			edgeTypeVal = SetTypeEdgeViewTask.ACTIVATING;
			edgeEfficiencyVal = 0.2;
			targetInputVal = 1;
		}
		
		else if(nodeType.equals(RECEPTOR_T_KINASE))
		{
			edgeTypeVal = SetTypeEdgeViewTask.ACTIVATING;
			edgeEfficiencyVal = 0.2;
			targetInputVal = 1;
		}
		
		else if(nodeType.equals(PHOSPHATASE))
		{
			edgeTypeVal = SetTypeEdgeViewTask.DEACTIVATING;
			edgeEfficiencyVal = 0.2;
			targetInputVal = 1;
		}

		CyRow row = ColumnsCreator.DefaultEdgeTable.getRow(edge.getSUID());
		
		if(!row.isSet(ColumnsCreator.EDGE_TYPE))
			row.set(ColumnsCreator.EDGE_TYPE, edgeTypeVal);
		
		if(!row.isSet(ColumnsCreator.EDGE_EFFICIENCY))
			row.set(ColumnsCreator.EDGE_EFFICIENCY, edgeEfficiencyVal);
		
		if(!row.isSet(ColumnsCreator.TARGET_INPUT))
			row.set(ColumnsCreator.TARGET_INPUT, targetInputVal);
		
		if(!row.isSet(ColumnsCreator.EDGE_SOURCE_NODE_OUTPUT))
			row.set(ColumnsCreator.EDGE_SOURCE_NODE_OUTPUT, edgeSourceNodeOutputVal);
		
		//set the edge view
		edgeViewHandler.setEdgeView(edge, network, row.get(ColumnsCreator.EDGE_TYPE, String.class));
	}
	
	/**
	 * Sets the app-specific columns to default values
	 */
	public void setDefaultsForNodeType()
	{
		CyRow row1, row2;
		CyNode node = nodeView.getModel();
		double port1EfficiencyVal = 1.0;
		int numOfInputsVal = 1;
		double timeRampVal = 1.0;
		double relativeConcVal = 1.0;
		double decayVal = 0.0001;
		double initialOutputVal = 0.0;
		double intOutputNodeThreshVal = 0.5;
		double upperBoundVal = 1.0;
		double currOutputVal = 0.0;
		double nextOutputVal = 0.0;
		
		row1 = ColumnsCreator.DefaultNodeTable.getRow(node.getSUID());
		row2 = ColumnsCreator.HiddenNodeTable.getRow(node.getSUID());

		List<Double> portEfficiency = new ArrayList<Double>();
		
		if(type.equals(KINASE))
		{
			port1EfficiencyVal = 1.0;
			numOfInputsVal = 1;
			timeRampVal = 1.0;
			relativeConcVal = 1.0;
			decayVal = 0.0001;
			initialOutputVal = 0.0;
			intOutputNodeThreshVal = 0.5;
			upperBoundVal = 1.0;
			currOutputVal = 0.0;
			nextOutputVal = 0.0;
		}
		
		else if(type.equals(GTPASE))
		{
			port1EfficiencyVal = 1.0;
			numOfInputsVal = 1;
			timeRampVal = 1.0;
			relativeConcVal = 1.0;
			decayVal = 0.0001;
			initialOutputVal = 0.0;
			intOutputNodeThreshVal = 0.5;
			upperBoundVal = 1.0;
			currOutputVal = 0.0;
			nextOutputVal = 0.0;
		}
		
		else if(type.equals(MOLECULES))
		{
			port1EfficiencyVal = 1.0;
			numOfInputsVal = 1;
			timeRampVal = 1.0;
			relativeConcVal = 1.0;
			decayVal = 0.0001;
			initialOutputVal = 0.0;
			intOutputNodeThreshVal = 0.5;
			upperBoundVal = 1.0;
			currOutputVal = 0.0;
			nextOutputVal = 0.0;
		}
		
		else if(type.equals(RECEPTOR))
		{
			port1EfficiencyVal = 1.0;
			numOfInputsVal = 1;
			timeRampVal = 1.0;
			relativeConcVal = 1.0;
			decayVal = 0.0001;
			initialOutputVal = 0.0;
			intOutputNodeThreshVal = 0.5;
			upperBoundVal = 1.0;
			currOutputVal = 0.0;
			nextOutputVal = 0.0;
		}
		
		else if(type.equals(RECEPTOR_T_KINASE))
		{
			port1EfficiencyVal = 1.0;
			numOfInputsVal = 1;
			timeRampVal = 1.0;
			relativeConcVal = 1.0;
			decayVal = 0.0001;
			initialOutputVal = 0.0;
			intOutputNodeThreshVal = 0.5;
			upperBoundVal = 1.0;
			currOutputVal = 0.0;
			nextOutputVal = 0.0;
		}
		
		else if(type.equals(PHOSPHATASE))
		{
			port1EfficiencyVal = 1.0;
			numOfInputsVal = 1;
			timeRampVal = 1.0;
			relativeConcVal = 1.0;
			decayVal = 0.0001;
			initialOutputVal = 0.0;
			intOutputNodeThreshVal = 0.5;
			upperBoundVal = 1.0;
			currOutputVal = 0.0;
			nextOutputVal = 0.0;
		}
		
		portEfficiency.add(port1EfficiencyVal);
		
		row1.set(ColumnsCreator.NODE_TYPE, type);
		row1.set(ColumnsCreator.NUM_OF_INPUTS, numOfInputsVal);
		row1.set(ColumnsCreator.TIME_RAMP, timeRampVal);
		row1.set(ColumnsCreator.RELATIVE_CONCENTRATION, relativeConcVal);
		row1.set(ColumnsCreator.DECAY, decayVal);
		row1.set(ColumnsCreator.INITIAL_OUTPUT_VALUE, initialOutputVal);
		row1.set(ColumnsCreator.PORT_EFFICIENCY, portEfficiency);
		row1.set(ColumnsCreator.INTEGER_OUTPUT_NODE_THRESH, intOutputNodeThreshVal);
		row1.set(ColumnsCreator.UPPER_BOUND, upperBoundVal);
		row1.set(ColumnsCreator.CURR_OUTPUT, currOutputVal);
		row2.set(ColumnsCreator.NEXT_OUTPUT, nextOutputVal);	
	}
}
