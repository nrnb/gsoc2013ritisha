package org.cytoscape.fluxviz.internal.logic;

import java.util.ArrayList;
import java.util.List;

import org.cytoscape.application.swing.CySwingApplication;
import org.cytoscape.model.CyNetwork;
import org.cytoscape.task.DynamicTaskFactoryProvisioner;
import org.cytoscape.view.model.CyNetworkViewManager;
import org.cytoscape.view.vizmap.VisualMappingFunctionFactory;
import org.cytoscape.view.vizmap.VisualMappingManager;
import org.cytoscape.view.vizmap.VisualStyleFactory;
import org.cytoscape.work.swing.PanelTaskManager;

public class Context {
	
	private List<CyNetwork> activeNetworks;
	private Evaluator evaluator = null;
	double speed;
	
	VisualMappingManager visualMappingManager;
	CyNetworkViewManager networkViewManager;
	VisualStyleFactory visualStyleFactory;
	VisualMappingFunctionFactory continousVisualMappingFunctionFactory;
	VisualMappingFunctionFactory discreteVisualMappingFunctionFactory;
	PanelTaskManager panelTaskManager;
	CySwingApplication swingApplication;
	DynamicTaskFactoryProvisioner provisioner;
	

	public VisualMappingManager getVisualMappingManager() {
		return visualMappingManager;
	}

	public void setVisualMappingManager(VisualMappingManager visualMappingManager) {
		this.visualMappingManager = visualMappingManager;
	}

	public CyNetworkViewManager getNetworkViewManager() {
		return networkViewManager;
	}

	public void setNetworkViewManager(CyNetworkViewManager networkViewManager) {
		this.networkViewManager = networkViewManager;
	}

	public VisualStyleFactory getVisualStyleFactory() {
		return visualStyleFactory;
	}

	public void setVisualStyleFactory(VisualStyleFactory visualStyleFactory) {
		this.visualStyleFactory = visualStyleFactory;
	}

	public VisualMappingFunctionFactory getContinousVisualMappingFunctionFactory() {
		return continousVisualMappingFunctionFactory;
	}

	public void setContinousVisualMappingFunctionFactory(
			VisualMappingFunctionFactory continousVisualMappingFunctionFactory) {
		this.continousVisualMappingFunctionFactory = continousVisualMappingFunctionFactory;
	}

	public VisualMappingFunctionFactory getDiscreteVisualMappingFunctionFactory() {
		return discreteVisualMappingFunctionFactory;
	}

	public void setDiscreteVisualMappingFunctionFactory(
			VisualMappingFunctionFactory discreteVisualMappingFunctionFactory) {
		this.discreteVisualMappingFunctionFactory = discreteVisualMappingFunctionFactory;
	}

	public PanelTaskManager getPanelTaskManager() {
		return panelTaskManager;
	}

	public void setPanelTaskManager(PanelTaskManager panelTaskManager) {
		this.panelTaskManager = panelTaskManager;
	}

	public CySwingApplication getSwingApplication() {
		return swingApplication;
	}

	public void setSwingApplication(CySwingApplication swingApplication) {
		this.swingApplication = swingApplication;
	}

	public DynamicTaskFactoryProvisioner getProvisioner() {
		return provisioner;
	}

	public void setProvisioner(DynamicTaskFactoryProvisioner provisioner) {
		this.provisioner = provisioner;
	}

	public double getSpeed() {
		return speed;
	}

	public void setSpeed(double speed) {
		this.speed = speed;
	}

	public Evaluator getEvaluator() {
		return evaluator;
	}

	public void setEvaluator(Evaluator evaluator) {
		this.evaluator = evaluator;
	}

	public Context()
	{
		activeNetworks = new ArrayList<CyNetwork>();
		speed = 2.0;
	}

	public boolean containsNetwork(CyNetwork network)
	{
		return activeNetworks.contains(network);
	}
	
	public void addNetwork(CyNetwork network)
	{
		activeNetworks.add(network);
	}
}
