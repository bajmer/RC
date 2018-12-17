package model.requirements;

import model.data.Levels;
import model.enums.TerrainType;
import model.enums.cards.IdeaType;
import model.resources.Resources;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Requirements {
	private Set<TerrainType> requiredTerrains;
	private List<IdeaType> requiredInventions;
	private Resources requiredResources;
	private Levels requiredLevels;
	private boolean requiredShelter;

	public Requirements() {
		this.requiredTerrains = new HashSet<>();
		this.requiredInventions = new ArrayList<>();
		this.requiredResources = new Resources();
		this.requiredLevels = new Levels();
		this.requiredShelter = false;
	}

	public Requirements(Set<TerrainType> requiredTerrains, List<IdeaType> requiredInventions, Resources requiredResources, Levels requiredLevels, boolean requiredShelter) {
		this.requiredTerrains = requiredTerrains;
		this.requiredInventions = requiredInventions;
		this.requiredResources = requiredResources;
		this.requiredLevels = requiredLevels;
		this.requiredShelter = requiredShelter;
	}

	public Set<TerrainType> getRequiredTerrains() {
		return requiredTerrains;
	}

	public void setRequiredTerrains(Set<TerrainType> requiredTerrains) {
		this.requiredTerrains = requiredTerrains;
	}

	public List<IdeaType> getRequiredInventions() {
		return requiredInventions;
	}

	public void setRequiredInventions(List<IdeaType> requiredInventions) {
		this.requiredInventions = requiredInventions;
	}

	public Resources getRequiredResources() {
		return requiredResources;
	}

	public void setRequiredResources(Resources requiredResources) {
		this.requiredResources = requiredResources;
	}

	public Levels getRequiredLevels() {
		return requiredLevels;
	}

	public void setRequiredLevels(Levels requiredLevels) {
		this.requiredLevels = requiredLevels;
	}

	public boolean isRequiredShelter() {
		return requiredShelter;
	}

	public void setRequiredShelter(boolean requiredShelter) {
		this.requiredShelter = requiredShelter;
	}
}
