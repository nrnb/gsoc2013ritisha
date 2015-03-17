package org.cytoscape.fluxviz.internal.logic;

import java.awt.Color;
import java.awt.Paint;
import java.util.ArrayList;
import java.util.List;

import org.cytoscape.fluxviz.internal.tasks.SetTypeEdgeViewTask;
import org.cytoscape.model.CyEdge;
import org.cytoscape.model.CyNetwork;
import org.cytoscape.model.CyRow;
import org.cytoscape.view.model.CyNetworkView;
import org.cytoscape.view.presentation.property.BasicVisualLexicon;
import org.cytoscape.view.presentation.property.LineTypeVisualProperty;
import org.cytoscape.view.presentation.property.values.LineType;
import org.cytoscape.view.vizmap.VisualMappingFunctionFactory;
import org.cytoscape.view.vizmap.VisualMappingManager;
import org.cytoscape.view.vizmap.VisualStyle;
import org.cytoscape.view.vizmap.VisualStyleFactory;
import org.cytoscape.view.vizmap.mappings.BoundaryRangeValues;
import org.cytoscape.view.vizmap.mappings.ContinuousMapping;
import org.cytoscape.view.vizmap.mappings.DiscreteMapping;

public class ViewHandler {
	
	NodeViewHandler nodeViewHandler;
	EdgeViewHandler edgeViewHandler;
	VisualMappingFunctionFactory continousVisualMappingFunctionFactory;
	VisualMappingFunctionFactory discreteVisualMappingFunctionFactory;
	VisualMappingManager visualMappingManager;
	VisualStyleFactory visualStyleFactory;
	ContinuousMapping<Double, Paint> currOutputNodeFillColorMapping;
	ContinuousMapping<Double, Paint> currOutputNodeSelectedColorMapping;
	ContinuousMapping<Double, Paint> activatingEdgeMapping;
	ContinuousMapping<Double, Paint> deactivatingEdgeMapping;
	ContinuousMapping<Double, Paint> edgeSumNodeBorderColorMapping;
	DiscreteMapping<Boolean, LineType> selectedNodeBorderLineTypeMapping;
	
	public ViewHandler(Context appContext)
	{
		nodeViewHandler = new NodeViewHandler(appContext);
		edgeViewHandler = new EdgeViewHandler(appContext);
		this.continousVisualMappingFunctionFactory = appContext.getContinousVisualMappingFunctionFactory();
		this.discreteVisualMappingFunctionFactory = appContext.getDiscreteVisualMappingFunctionFactory();
		this.visualMappingManager = appContext.getVisualMappingManager();
		this.visualStyleFactory = appContext.getVisualStyleFactory();
	}

	public void createVisualMappings()
	{
		Double val1 = 1.0001d;
		Double val2 = 1.0d;
		Double val3 = 0.0d;
		Double val4 = -0.000001d;
		Double val5 = 0.000001d;
		Double val6 = 1.01d;
		Double val7 = 10.0d;
		Double val8 = -1.0d;
		
		Color color1 = new Color(215, 25, 32); //red
		Color color2 = new Color(250, 184, 22); //yellow
		Color color3 = new Color(220, 220, 220); //grey
		Color color4 = new Color(115, 180, 226); //light blue
		Color color5 = new Color(46, 49, 146); //purple
		Color color6 = new Color(236, 132, 35); //yellow
		Color color7 = new Color(0, 102, 179); //dark blue
		Color color8 = new Color(248, 248, 248); //white
		Color color9 = new Color(199, 223, 244); //light blue
		Color color10 = new Color(115, 180, 226); //dark blue
		
		BoundaryRangeValues<Paint> brv1 = new BoundaryRangeValues<Paint>(color1, color1, color1);
		BoundaryRangeValues<Paint> brv2 = new BoundaryRangeValues<Paint>(color2, color2, color2);
		BoundaryRangeValues<Paint> brv3 = new BoundaryRangeValues<Paint>(color3, color3, color3);
		BoundaryRangeValues<Paint> brv4 = new BoundaryRangeValues<Paint>(color4, color4, color4);
		BoundaryRangeValues<Paint> brv5 = new BoundaryRangeValues<Paint>(color5, color5, color5);
		BoundaryRangeValues<Paint> brv6 = new BoundaryRangeValues<Paint>(color6, color6, color6);
		BoundaryRangeValues<Paint> brv7 = new BoundaryRangeValues<Paint>(color7, color7, color7);
		BoundaryRangeValues<Paint> brv8 = new BoundaryRangeValues<Paint>(color8, color8, color8);
		BoundaryRangeValues<Paint> brv9 = new BoundaryRangeValues<Paint>(color9, color9, color9);
		BoundaryRangeValues<Paint> brv10 = new BoundaryRangeValues<Paint>(color10, color10, color10);

		currOutputNodeFillColorMapping = (ContinuousMapping<Double, Paint>) continousVisualMappingFunctionFactory.createVisualMappingFunction(ColumnsCreator.CURR_OUTPUT, Double.class, BasicVisualLexicon.NODE_FILL_COLOR);
		currOutputNodeFillColorMapping.addPoint(val3, brv8);
		currOutputNodeFillColorMapping.addPoint(val2, brv9);
		currOutputNodeFillColorMapping.addPoint(val6, brv10);
		currOutputNodeFillColorMapping.addPoint(val7, brv10);

		currOutputNodeSelectedColorMapping = (ContinuousMapping<Double, Paint>) continousVisualMappingFunctionFactory.createVisualMappingFunction(ColumnsCreator.CURR_OUTPUT, Double.class, BasicVisualLexicon.NODE_SELECTED_PAINT);
		currOutputNodeSelectedColorMapping.addPoint(val3, brv8);
		currOutputNodeSelectedColorMapping.addPoint(val2, brv9);
		currOutputNodeSelectedColorMapping.addPoint(val6, brv10);
		currOutputNodeSelectedColorMapping.addPoint(val7, brv10);

		edgeSumNodeBorderColorMapping = (ContinuousMapping<Double, Paint>) continousVisualMappingFunctionFactory.createVisualMappingFunction(ColumnsCreator.EDGE_SUM, Double.class, BasicVisualLexicon.NODE_BORDER_PAINT);
		edgeSumNodeBorderColorMapping.addPoint(val4, brv6);
		edgeSumNodeBorderColorMapping.addPoint(val3, brv3);
		edgeSumNodeBorderColorMapping.addPoint(val5, brv7);
		edgeSumNodeBorderColorMapping.addPoint(val2, brv7);
		edgeSumNodeBorderColorMapping.addPoint(val8, brv6);

		activatingEdgeMapping = (ContinuousMapping<Double, Paint>) continousVisualMappingFunctionFactory.createVisualMappingFunction(ColumnsCreator.EDGE_SOURCE_NODE_OUTPUT, Double.class, BasicVisualLexicon.EDGE_UNSELECTED_PAINT);
		activatingEdgeMapping.addPoint(val1, brv5);
		activatingEdgeMapping.addPoint(val2, brv4);
		activatingEdgeMapping.addPoint(val3, brv3);
		
		deactivatingEdgeMapping = (ContinuousMapping<Double, Paint>) continousVisualMappingFunctionFactory.createVisualMappingFunction(ColumnsCreator.EDGE_SOURCE_NODE_OUTPUT, Double.class, BasicVisualLexicon.EDGE_UNSELECTED_PAINT);
		deactivatingEdgeMapping.addPoint(val1, brv1);
		deactivatingEdgeMapping.addPoint(val2, brv2);
		deactivatingEdgeMapping.addPoint(val3, brv3);
		
		selectedNodeBorderLineTypeMapping = (DiscreteMapping<Boolean, LineType>) discreteVisualMappingFunctionFactory.createVisualMappingFunction(CyNetwork.SELECTED, Boolean.class, BasicVisualLexicon.NODE_BORDER_LINE_TYPE);
		selectedNodeBorderLineTypeMapping.putMapValue(true, LineTypeVisualProperty.DOT);
		selectedNodeBorderLineTypeMapping.putMapValue(false, LineTypeVisualProperty.SOLID);
	}
	
	public void createFluxVizStyle()
	{
		VisualStyle currVisualStyle = visualMappingManager.getCurrentVisualStyle();
		String oldName = currVisualStyle.getTitle();
		if(oldName.startsWith("FluxViz_"))
			return;
		String newName = "FluxViz_" + oldName;
		VisualStyle newVisualStyle = visualStyleFactory.createVisualStyle(currVisualStyle);
		newVisualStyle.setTitle(newName);
		newVisualStyle.addVisualMappingFunction(currOutputNodeFillColorMapping);
		newVisualStyle.addVisualMappingFunction(currOutputNodeSelectedColorMapping);
		newVisualStyle.addVisualMappingFunction(selectedNodeBorderLineTypeMapping);
		newVisualStyle.addVisualMappingFunction(edgeSumNodeBorderColorMapping);
		visualMappingManager.addVisualStyle(newVisualStyle);
		visualMappingManager.setCurrentVisualStyle(newVisualStyle);
	}
	
	public void refresh(CyNetworkView networkView)
	{
		List<CyEdge> allEdges = new ArrayList<CyEdge>();
		allEdges = networkView.getModel().getEdgeList();
		for(CyEdge currEdge : allEdges)
		{
			CyRow row = ColumnsCreator.DefaultEdgeTable.getRow(currEdge.getSUID());
			String currEdgeType = row.get(ColumnsCreator.EDGE_TYPE, String.class);
			if(currEdgeType.equals(SetTypeEdgeViewTask.ACTIVATING))
			{
				networkView.getEdgeView(currEdge).setLockedValue(BasicVisualLexicon.EDGE_UNSELECTED_PAINT, activatingEdgeMapping.getMappedValue(row));
			}
			else if(currEdgeType.equals(SetTypeEdgeViewTask.DEACTIVATING))
			{
				networkView.getEdgeView(currEdge).setLockedValue(BasicVisualLexicon.EDGE_UNSELECTED_PAINT, deactivatingEdgeMapping.getMappedValue(row));
			}
		}
		
		VisualStyle style = visualMappingManager.getCurrentVisualStyle();
		style.apply(networkView);
		networkView.updateView();
	}

	public NodeViewHandler getNodeViewHandler() {
		return nodeViewHandler;
	}

	public EdgeViewHandler getEdgeViewHandler() {
		return edgeViewHandler;
	}
}
