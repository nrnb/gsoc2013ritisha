package org.cytoscape.fluxviz.internal.logic;

import java.util.ArrayList;
import java.util.List;

import org.cytoscape.model.CyEdge;
import org.cytoscape.model.CyNode;
import org.cytoscape.work.Tunable;
import org.cytoscape.work.TunableValidator;
import org.cytoscape.work.util.BoundedDouble;
import org.cytoscape.work.util.BoundedInteger;
import org.cytoscape.work.util.ListSingleSelection;

public class ControlTunables implements TunableValidator {

	public BoundedDouble playbackSpeed;	
	public ListSingleSelection<String> typeOfMolecule;
	public Boolean constitutivelyActive;
	public Boolean exceedDefaultValues;
	public ListSingleSelection<String> initialActivityLevel;
	public BoundedDouble continousInitialActivityLevel;
	public Boolean binaryInitialActivityLevel;
	public BoundedInteger integerInitialActivityLevel;
	public BoundedDouble moleculeConcentration;
	public ListSingleSelection<String> typeOfEdge;
	public BoundedDouble edgeEfficiency;
	
	Context appContext;
	List<CyNode> currentlySelectedNodes;
	List<CyEdge> currentlySelectedEdges;
	
	public ControlTunables(Context appContext)
	{
		this.appContext = appContext;
		currentlySelectedNodes = new ArrayList<CyNode>();
		currentlySelectedEdges = new ArrayList<CyEdge>();
		
		playbackSpeed = new BoundedDouble(0.0, appContext.getSpeed(), 5.0, false, false);
		typeOfMolecule = new ListSingleSelection<String>("Kinase", "Phosphatase", "GTPase", "Transcription Factor", "Ligands", "Small Mol Activator", "Small Mol Inhibitor", "Enzyme1", "Enzyme2", "Enzyme3");
		constitutivelyActive = new Boolean(false);
		exceedDefaultValues = new Boolean(false);
		initialActivityLevel = new ListSingleSelection<String>("Continous", "Binary", "Integer");
		continousInitialActivityLevel = new BoundedDouble(0.0, 0.0, 1.0, false, false);
		binaryInitialActivityLevel = new Boolean(false);
		integerInitialActivityLevel = new BoundedInteger(0, 0, 100, false, false);
		moleculeConcentration = new BoundedDouble(0.0, 0.0, 100.0, false, false);
		typeOfEdge = new ListSingleSelection<String>("Activating", "Deactivating");
		edgeEfficiency = new BoundedDouble(0.0, 0.0, 100.0, false, false);
	}
	
	public List<CyNode> getCurrentlySelectedNodes() {
		return currentlySelectedNodes;
	}

	public void setCurrentlySelectedNodes(List<CyNode> currentlySelectedNodes) {
		
		this.currentlySelectedNodes = currentlySelectedNodes;
	}

	public List<CyEdge> getCurrentlySelectedEdges() {
		return currentlySelectedEdges;
	}

	public void setCurrentlySelectedEdges(List<CyEdge> currentlySelectedEdges) {
		this.currentlySelectedEdges = currentlySelectedEdges;
	}

	@Tunable(description = "Playback speed in [seconds/cycle]", params = "slider=true")
	public BoundedDouble getPlaybackSpeed() {
		return playbackSpeed;
	}
	public void setPlaybackSpeed(BoundedDouble playbackSpeed) {
		
		appContext.setSpeed(playbackSpeed.getValue());
		this.playbackSpeed = playbackSpeed;
	}
	
	//Node Tunables. Get what nodes are selected.
	
	@Tunable(description = "Type of Molecule", groups = "MyNode Attributes")
	public ListSingleSelection<String> getTypeOfMolecule() {
		return typeOfMolecule;
	}
	public void setTypeOfMolecule(ListSingleSelection<String> typeOfMolecule) {
		this.typeOfMolecule = typeOfMolecule;
	}
	
	@Tunable(description = "Constitutively active", groups = "MyNode Attributes")
	public Boolean getConstitutivelyActive() {
		return constitutivelyActive;
	}
	public void setConstitutivelyActive(Boolean constitutivelyActive) {
		this.constitutivelyActive = constitutivelyActive;
	}
	
	@Tunable(description = "Exceed default values", groups = "MyNode Attributes")
	public Boolean getExceedDefaultValues() {
		return exceedDefaultValues;
	}
	public void setExceedDefaultValues(Boolean exceedDefaultValues) {
		this.exceedDefaultValues = exceedDefaultValues;
	}
	
	@Tunable(description = "Initial activity level", groups = "MyNode Attributes")
	public ListSingleSelection<String> getInitialActivityLevel() {
		return initialActivityLevel;
	}
	public void setInitialActivityLevel(
			ListSingleSelection<String> initialActivityLevel) {
		this.initialActivityLevel = initialActivityLevel;
	}
	
	@Tunable(description = "Continous Initial Activity Level", params = "slider=true", dependsOn = "initialActivityLevel=Continous", groups = "MyNode Attributes")
	public BoundedDouble getContinousInitialActivityLevel() {
		return continousInitialActivityLevel;
	}
	public void setContinousInitialActivityLevel(
			BoundedDouble continousInitialActivityLevel) {
		this.continousInitialActivityLevel = continousInitialActivityLevel;
	}
	
	@Tunable(description = "Binary Initial Activity Level", dependsOn = "initialActivityLevel=Binary", groups = "MyNode Attributes")
	public Boolean getBinaryInitialActivityLevel() {
		return binaryInitialActivityLevel;
	}
	public void setBinaryInitialActivityLevel(Boolean binaryInitialActivityLevel) {
		this.binaryInitialActivityLevel = binaryInitialActivityLevel;
	}
	
	@Tunable(description = "Integer Initial Activity Level", params = "slider=true", dependsOn = "initialActivityLevel=Integer", groups = "MyNode Attributes")
	public BoundedInteger getIntegerInitialActivityLevel() {
		return integerInitialActivityLevel;
	}
	public void setIntegerInitialActivityLevel(
			BoundedInteger integerInitialActivityLevel) {
		this.integerInitialActivityLevel = integerInitialActivityLevel;
	}
	
	@Tunable(description = "Molecule concentration as % of normal", params = "slider=true", groups = "MyNode Attributes")
	public BoundedDouble getMoleculeConcentration() {
		return moleculeConcentration;
	}
	public void setMoleculeConcentration(BoundedDouble moleculeConcentration) {
		this.moleculeConcentration = moleculeConcentration;
	}
	
	@Tunable(description = "Type of edge", groups = "MyEdge Attributes")
	public ListSingleSelection<String> getTypeOfEdge() {
		return typeOfEdge;
	}
	public void setTypeOfEdge(ListSingleSelection<String> typeOfEdge) {
		this.typeOfEdge = typeOfEdge;
	}
	
	@Tunable(description = "Edge efficiency as % of normal", params = "slider=true", groups = "MyEdge Attributes")
	public BoundedDouble getEdgeEfficiency() {
		return edgeEfficiency;
	}
	public void setEdgeEfficiency(BoundedDouble edgeEfficiency) {
		this.edgeEfficiency = edgeEfficiency;
	}
	
	@Override
	public ValidationState getValidationState(Appendable arg0) {
		// TODO Auto-generated method stub
		return null;
	}

}
