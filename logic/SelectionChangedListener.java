package org.cytoscape.fluxviz.internal.logic;

import java.util.ArrayList;
import java.util.List;

import org.cytoscape.model.CyIdentifiable;
import org.cytoscape.model.CyNetwork;
import org.cytoscape.model.CyRow;
import org.cytoscape.model.events.RowsSetEvent;
import org.cytoscape.model.events.RowsSetListener;

public class SelectionChangedListener implements RowsSetListener {

	Context appContext;
	
	public SelectionChangedListener(Context appContext)
	{
		this.appContext = appContext;
	}
	@Override
	public void handleEvent(RowsSetEvent e) {

		List<CyRow> allRows;
		if(e.containsColumn(CyNetwork.SELECTED))
		{
			if(e.getSource().equals(ColumnsCreator.DefaultNodeTable))
			{
				//we dont want to check just the ones which have been set, we want to check all the values.
				allRows = ColumnsCreator.DefaultNodeTable.getAllRows();
				List<Long> selectedNodesSUIDs = new ArrayList<Long>();
				for(CyRow currRow : allRows)
				{
					if(currRow.get(CyNetwork.SELECTED, Boolean.class))
					{
						selectedNodesSUIDs.add(currRow.get(CyIdentifiable.SUID, Long.class));
					}
				}
				if(selectedNodesSUIDs.size() == 1)
				{
					//put attribute values of this node on tunables.
				}
				else if(selectedNodesSUIDs.size() == 0)
				{
					//figure something out
				}
				else
				{
					//put default values and allow mass edit.
				}
			}
			
			if(e.getSource().equals(ColumnsCreator.DefaultEdgeTable))
			{
				allRows = ColumnsCreator.DefaultEdgeTable.getAllRows();
				List<Long> selectedEdgesSUIDs = new ArrayList<Long>();
				for(CyRow currRow : allRows)
				{
					if(currRow.get(CyNetwork.SELECTED, Boolean.class))
					{
						selectedEdgesSUIDs.add(currRow.get(CyIdentifiable.SUID, Long.class));
					}
				}
				if(selectedEdgesSUIDs.size() == 1)
				{
					//put attribute values of this edge on tunables.
				}
				else if(selectedEdgesSUIDs.size() == 0)
				{
					//figure something out
				}
				else
				{
					//put default values and allow mass edit.
				}
			}
		}
	}
}
