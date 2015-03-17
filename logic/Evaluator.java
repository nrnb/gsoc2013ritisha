package org.cytoscape.fluxviz.internal.logic;

import java.util.ArrayList;
import java.util.List;

import org.cytoscape.fluxviz.internal.tasks.SetTypeEdgeViewTask;
import org.cytoscape.model.CyEdge;
import org.cytoscape.model.CyNetwork;
import org.cytoscape.model.CyNode;
import org.cytoscape.model.CyRow;
import org.cytoscape.view.model.CyNetworkView;

/**
 * Creates the thread to run the "evaluate" code calculating the currOutput at every tick
 * @author laungani
 *
 */
public class Evaluator extends Thread {
	
	CyNetwork network;
	CyNetworkView networkView;
	ViewHandler viewHandler;
	Context appContext;
	boolean running;
	boolean doRestart;
	
	public Evaluator(ViewHandler viewHandler, Context appContext)
	{
		super();
		this.viewHandler = viewHandler;
		this.appContext = appContext;
	}

	public void startEvaluator(CyNetworkView networkView, boolean doRestart)
	{
		this.running = true;
		this.networkView = networkView;
		this.network = networkView.getModel();
		this.doRestart = doRestart;
		this.start();
	}
	
	public void stopEvaluator()
	{
		this.running = false;
	}
	
	public void run()
	{
		List<CyNode> allNodes = new ArrayList<CyNode>();
		allNodes = network.getNodeList();
		CyRow row;

		if(doRestart)
		{
			for(CyNode currNode : allNodes)
			{
				row = ColumnsCreator.DefaultNodeTable.getRow(currNode.getSUID());
				Double initialOutput = row.get(ColumnsCreator.INITIAL_OUTPUT_VALUE, Double.class);
				row.set(ColumnsCreator.CURR_OUTPUT, initialOutput);
			}
		}
		
		while(running)
		{
			for(CyNode currNode : allNodes)
			{
				Double nextOutput = evaluate(currNode, network);
				row = ColumnsCreator.HiddenNodeTable.getRow(currNode.getSUID());
				row.set(ColumnsCreator.NEXT_OUTPUT, nextOutput);
			}
			
			for(CyNode currNode : allNodes)
			{
				row = ColumnsCreator.HiddenNodeTable.getRow(currNode.getSUID());
				Double currOutput = row.get(ColumnsCreator.NEXT_OUTPUT, Double.class);
				row = ColumnsCreator.DefaultNodeTable.getRow(currNode.getSUID());
				row.set(ColumnsCreator.CURR_OUTPUT, currOutput);
				 
				//Set the EDGE_SOURCE_NODE_OUTPUT for all outgoing edges
				List<CyEdge> outgoingEdges = new ArrayList<CyEdge>();
				outgoingEdges = network.getAdjacentEdgeList(currNode, CyEdge.Type.OUTGOING);
				for(CyEdge currEdge : outgoingEdges)
				{
					row = ColumnsCreator.DefaultEdgeTable.getRow(currEdge.getSUID());
					row.set(ColumnsCreator.EDGE_SOURCE_NODE_OUTPUT, currOutput);
				}
			}
			
			viewHandler.refresh(networkView);
			
			try {
				Thread.sleep((long)(10000 - appContext.getSpeed()*1000));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 
	 * @param node
	 * @param network
	 * @param defaultEdgeTable
	 * @param defaultNodeTable
	 * @param hiddenNodeTable
	 * @return nextOutput
	 */
	public Double evaluate(CyNode node, CyNetwork network)
	{

		Double v = new Double(0.0);
		Double newV = new Double(0.0);
		Double edgeSourceOutput = new Double(0.0);
		Double edgeEfficiency = new Double(0.0);
		Double edgeSourcePort1Efficiency = new Double(0.0);
		String edgeType;
		CyRow row;
		Double currentOutput = new Double(0.0);
		Double timeRamp = new Double(0.0);
		Double relativeConc = new Double(0.0);
		Double decay = new Double(0.0);
		
		//In the future, we can add functionality for nodeTypes and for numberOfInputs here.
		//For v1.0 each nodeType will only have one input site, so the code below will function ubiquitously for all nodeTypes in v1.0
		//In the future, we will iterate over all of the ports here: (in v1.0 we only have one port)
		List<CyEdge> incomingEdges = new ArrayList<CyEdge>();
		incomingEdges = network.getAdjacentEdgeList(node, CyEdge.Type.INCOMING);

		for(CyEdge currEdge : incomingEdges)
		{
			row = ColumnsCreator.DefaultEdgeTable.getRow(currEdge.getSUID());
			edgeType = row.get(ColumnsCreator.EDGE_TYPE, String.class);
			edgeEfficiency = row.get(ColumnsCreator.EDGE_EFFICIENCY, Double.class);
			
			CyNode sourceNode = currEdge.getSource();
			row = ColumnsCreator.DefaultNodeTable.getRow(sourceNode.getSUID());
			edgeSourceOutput = row.get(ColumnsCreator.CURR_OUTPUT, Double.class);
			
			List<Double> edgeSourceNodePortEfficiencyList = new ArrayList<Double>();
			edgeSourceNodePortEfficiencyList = row.getList(ColumnsCreator.PORT_EFFICIENCY, Double.class);
			edgeSourcePort1Efficiency = edgeSourceNodePortEfficiencyList.get(0);
			
			if(edgeType.equals(SetTypeEdgeViewTask.ACTIVATING))
			{
				v += edgeSourceOutput.doubleValue() * edgeEfficiency.doubleValue() * edgeSourcePort1Efficiency.doubleValue();
			}
			else if(edgeType.equals(SetTypeEdgeViewTask.DEACTIVATING))
			{
				v -= edgeSourceOutput.doubleValue() * edgeEfficiency.doubleValue() * edgeSourcePort1Efficiency.doubleValue();
			}
		}
		
		row = ColumnsCreator.DefaultNodeTable.getRow(node.getSUID());
		currentOutput = row.get(ColumnsCreator.CURR_OUTPUT, Double.class);
		timeRamp = row.get(ColumnsCreator.TIME_RAMP, Double.class);
		relativeConc = row.get(ColumnsCreator.RELATIVE_CONCENTRATION, Double.class);
		decay = row.get(ColumnsCreator.DECAY, Double.class);
		row.set(ColumnsCreator.EDGE_SUM, v);
		
		newV = currentOutput.doubleValue() + (v.doubleValue() * (timeRamp.doubleValue() + relativeConc.doubleValue())/2) - decay.doubleValue();
		if(newV < 0)
		{
			newV = 0.0;
		}
		return newV;
	}
}
