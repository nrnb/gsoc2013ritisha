package org.cytoscape.fluxviz.internal.tasks;

import java.util.ArrayList;
import java.util.List;

import org.cytoscape.fluxviz.internal.logic.ColumnsCreator;
import org.cytoscape.model.CyNode;
import org.cytoscape.model.CyRow;
import org.cytoscape.task.AbstractNodeViewTask;
import org.cytoscape.view.model.CyNetworkView;
import org.cytoscape.view.model.View;
import org.cytoscape.work.TaskMonitor;
import org.cytoscape.work.Tunable;

public class EditNodeAttributesTask extends AbstractNodeViewTask {

	@Tunable(description = "Node Type")
	public String nodeType;
	@Tunable(description = "Number of Inputs")
	public int numOfInputs;
	@Tunable(description = "Time Ramp")
	public double timeRamp;
	@Tunable(description = "Relative Concentration")
	public double relativeConc;
	@Tunable(description = "Decay")
	public double decay;
	@Tunable(description = "Initial Output Value")
	public double initialOutputValue;
	@Tunable(description = "Port Efficiency")
	public List<Double> portEfficiency;
	@Tunable(description = "Integer Output Node Threshold")
	public double intOutputNodeThresh;
	@Tunable(description = "Upper Bound")
	public double upperBound;
	
	CyRow row;
	
	public EditNodeAttributesTask(View<CyNode> nodeView, CyNetworkView networkView)
	{
		super(nodeView, networkView);
		this.row = ColumnsCreator.DefaultNodeTable.getRow(nodeView.getModel().getSUID());
		nodeType = row.get(ColumnsCreator.NODE_TYPE, String.class);
		numOfInputs = row.get(ColumnsCreator.NUM_OF_INPUTS, Integer.class);
		timeRamp = row.get(ColumnsCreator.TIME_RAMP, Double.class);
		relativeConc = row.get(ColumnsCreator.RELATIVE_CONCENTRATION, Double.class);
		decay = row.get(ColumnsCreator.DECAY, Double.class);
		initialOutputValue = row.get(ColumnsCreator.INITIAL_OUTPUT_VALUE, Double.class);
		portEfficiency = new ArrayList<Double>();
		portEfficiency = row.getList(ColumnsCreator.PORT_EFFICIENCY, Double.class);
		intOutputNodeThresh = row.get(ColumnsCreator.INTEGER_OUTPUT_NODE_THRESH, Double.class);
		upperBound = row.get(ColumnsCreator.UPPER_BOUND, Double.class);
	}
	
	@Override
	public void run(TaskMonitor arg0) throws Exception {
		row.set(ColumnsCreator.NODE_TYPE, nodeType);
		row.set(ColumnsCreator.NUM_OF_INPUTS, numOfInputs);
		row.set(ColumnsCreator.TIME_RAMP, timeRamp);
		row.set(ColumnsCreator.RELATIVE_CONCENTRATION, relativeConc);
		row.set(ColumnsCreator.DECAY, decay);
		row.set(ColumnsCreator.INITIAL_OUTPUT_VALUE, initialOutputValue);
		row.set(ColumnsCreator.PORT_EFFICIENCY, portEfficiency);
		row.set(ColumnsCreator.INTEGER_OUTPUT_NODE_THRESH, intOutputNodeThresh);
		row.set(ColumnsCreator.UPPER_BOUND, upperBound);
	}
}
