package asgn2Manifests;

import java.util.ArrayList;

import asgn2Codes.ContainerCode;
import asgn2Containers.FreightContainer;
import asgn2Exceptions.ManifestException;

/**
 * A class for managing a container ship's cargo manifest. It allows freight
 * containers of various types to be loaded and unloaded, within various
 * constraints.
 * <p>
 * In particular, the ship's captain has set the following rules for loading of
 * new containers:
 * <ol>
 * <li>
 * New containers may be loaded only if doing so does not exceed the ship's
 * weight limit.</li>
 * <li>
 * New containers are to be loaded as close to the bridge as possible. (Stack
 * number zero is nearest the bridge.)</li>
 * <li>
 * A new container may be added to a stack only if doing so will not exceed the
 * maximum allowed stack height.
 * <li>
 * A new container may be loaded only if a container with the same code is not
 * already on board.</li>
 * <li>
 * Stacks of containers must be homogeneous, i.e., each stack must consist of
 * containers of one type (general, refrigerated or dangerous goods) only.</li>
 * </ol>
 * <p>
 * Furthermore, since the containers are moved by an overhead crane, a container
 * can be unloaded only if it is on top of a stack.
 * 
 * @author Zehui Zhang (N8646236)
 * @version 1.0
 */
public class CargoManifest {

	private int numStacks;
	private int maxHeight;
	private int maxWeight;
	private ArrayList<ArrayList<FreightContainer>> manifest;
	private int currentWeight;

	/**
	 * Constructs a new cargo manifest in preparation for a voyage. When a cargo
	 * manifest is constructed the specific cargo parameters for the voyage are
	 * set, including the number of stack spaces available on the deck (which
	 * depends on the deck configuration for the voyage), the maximum allowed
	 * height of any stack (which depends on the weather conditions expected for
	 * the voyage), and the total weight of containers allowed onboard (which
	 * depends on the amount of ballast and fuel being carried).
	 * 
	 * @param numStacks
	 *            the number of stacks that can be accommodated on deck
	 * @param maxHeight
	 *            the maximum allowable height of any stack
	 * @param maxWeight
	 *            the maximum weight of containers allowed on board (in tonnes)
	 * @throws ManifestException
	 *             if negative numbers are given for any of the parameters
	 */
	public CargoManifest(Integer numStacks, Integer maxHeight, Integer maxWeight)
			throws ManifestException {
		// Implementation Here

		if (numStacks < 0 || maxHeight < 0 || maxWeight < 0 ) {
			throw new ManifestException("Invliad Manifest Parameters.");
		}

		this.numStacks = numStacks;
		this.maxHeight = maxHeight;
		this.maxWeight = maxWeight;

		// Initialize the stack
		manifest = new ArrayList();
		for (int i = 0; i < numStacks; i++) {
			ArrayList<FreightContainer> stack = new ArrayList<FreightContainer>();
			manifest.add(stack);
		}
		currentWeight = 0;
	}

	/**
	 * Loads a freight container onto the ship, provided that it can be
	 * accommodated within the five rules set by the captain.
	 * 
	 * @param newContainer
	 *            the new freight container to be loaded
	 * @throws ManifestException
	 *             if adding this container would exceed the ship's weight
	 *             limit; if a container with the same code is already on board;
	 *             or if no suitable space can be found for this container
	 */
	public void loadContainer(FreightContainer newContainer)
			throws ManifestException {
		// Implementation Here

		// Check if the container exceed the max weight
		currentWeight += newContainer.getGrossWeight();
		if (this.maxWeight < currentWeight)
			throw new ManifestException(
					"The contianer exceed the max weight allowed.");

		// Check if the container is already onboad
		for (int i = 0; i < manifest.size(); i++) {
			ArrayList<FreightContainer> currentStack = manifest.get(i);
			for (int j = 0; j < currentStack.size(); j++) {
				if (currentStack.get(j).getCode().equals(newContainer.getCode()))
					throw new ManifestException(
							"The contianer is alrady onboard.");
			}
		}

		
		int stackIndex = -1;

		for (int i = 0; i < manifest.size(); i++) {
			ArrayList<FreightContainer> currentStack = manifest.get(i);
			// If the currentStack is empty then load it here
			if (currentStack.size() == 0 ){
				stackIndex = i;
				break;
			}
			
			// Check if any homogeneous containers and also check the stack height
			for (int j = 0; j < currentStack.size(); j++) {
				if (currentStack.get(j).getClass().isInstance(newContainer)) {
					stackIndex = i; // this is the stack with the same type
									// containers;
					break;
				}
			}
			if (stackIndex != -1) {
				// Check the height of the current available stack, braek the
				// loop if current
				// height less than maxHeight, otherwise continue to look for
				// the suitable space
				if (manifest.get(stackIndex).size() < this.maxHeight) {
					break;
				} else {
					stackIndex = -1;
				}
			}
		}

		// Throw exception if no suitable space
		if (stackIndex == -1) {
			throw new ManifestException("No suitable space for this container.");
		}

		// Load the container
		ArrayList<FreightContainer> availableStack = manifest.get(stackIndex);
		availableStack.add(newContainer);

	}

	/**
	 * Unloads a particular container from the ship, provided that it is
	 * accessible (i.e., on top of a stack).
	 * 
	 * @param containerId
	 *            the code of the container to be unloaded
	 * @throws ManifestException
	 *             if the container is not accessible because it's not on the
	 *             top of a stack (including the case where it's not on board
	 *             the ship at all)
	 */
	public void unloadContainer(ContainerCode containerId)
			throws ManifestException {
		// Implementation Here

		// Throw exception if container is not on board
		if (whichStack(containerId) == null || howHigh(containerId) == null) {
			throw new ManifestException("The container is not on board");
		}

		int stackIndex = whichStack(containerId);
		int ContainerIndex = howHigh(containerId);

		// Throw exception if container is not on the top
		if (ContainerIndex != (manifest.get(stackIndex).size() - 1)) {
			throw new ManifestException(
					"The container is not on the top of the stack");
		}

		// Unload the container
		manifest.get(stackIndex).remove(ContainerIndex);
	}

	/**
	 * Returns which stack holds a particular container, if any. The container
	 * of interest is identified by its unique code. Constant <code>null</code>
	 * is returned if the container is not on board.
	 * 
	 * @param queryContainer
	 *            the container code for the container of interest
	 * @return the number of the stack with the container in it, or
	 *         <code>null</code> if the container is not on board
	 */
	public Integer whichStack(ContainerCode queryContainer) {
		// Implementation Here
		for (int i = 0; i < numStacks; i++) {
			ArrayList<FreightContainer> currentStack = manifest.get(i);
			for (int j = 0; j < currentStack.size(); j++) {
				if (currentStack.get(j).getCode().equals(queryContainer)) {
					return i;
				}
			}
		}
		return null;
	}

	/**
	 * Returns how high in its stack a particular container is. The container of
	 * interest is identified by its unique code. Height is measured in the
	 * number of containers, counting from zero. Thus the container at the
	 * bottom of a stack is at "height" 0, the container above it is at height
	 * 1, and so on. Constant <code>null</code> is returned if the container is
	 * not on board.
	 * 
	 * @param queryContainer
	 *            the container code for the container of interest
	 * @return the container's height in the stack, or <code>null</code> if the
	 *         container is not on board
	 */
	public Integer howHigh(ContainerCode queryContainer) {
		// Implementation Here
		if (whichStack(queryContainer) != null) {
			int stackNo = whichStack(queryContainer);
			ArrayList<FreightContainer> currentStack = manifest.get(stackNo);
			for (int i = 0; i < currentStack.size(); i++) {
				if (currentStack.get(i).getCode().equals(queryContainer)) {
					return i;
				}
			}
		}
		return null;
	}

	/**
	 * Returns the contents of a particular stack as an array, starting with the
	 * bottommost container at position zero in the array.
	 * 
	 * @param stackNo
	 *            the number of the stack of interest
	 * @return the stack's freight containers as an array
	 * @throws ManifestException
	 *             if there is no such stack on the ship
	 */
	public FreightContainer[] toArray(Integer stackNo) throws ManifestException {
		// Implementation Here

		if (stackNo < 0 || stackNo > (manifest.size() - 1)) {
			throw new ManifestException("There is no such stack on the ship");
		}

		ArrayList<FreightContainer> currentStack = manifest.get(stackNo);
		FreightContainer[] containerArray = new FreightContainer[currentStack
				.size()];

		if (currentStack.size() > 0) {

			for (int i = 0; i < currentStack.size(); i++) {
				containerArray[i] = currentStack.get(i);
			}
		}
		return containerArray;
	}

	/* ***** toString methods added to support the GUI ***** */

	public String toString(ContainerCode toFind) {
		// Some variables here are used and not declared. You can work it out
		String toReturn = "";
		for (int i = 0; i < manifest.size(); ++i) {
			ArrayList<FreightContainer> currentStack = manifest.get(i);
			toReturn += "|";
			for (int j = 0; j < currentStack.size(); ++j) {
				if (toFind != null
						&& currentStack.get(j).getCode().equals(toFind))
					toReturn += "|*" + currentStack.get(j).getCode().toString()
							+ "*|";
				else
					toReturn += "| " + currentStack.get(j).getCode().toString()
							+ " |";
			}
			if (currentStack.size() == 0)
				toReturn += "|  ||\n";
			else
				toReturn += "|\n";
		}
		return toReturn;
	}

	@Override
	public String toString() {
		return toString(null);
	}
	
	/***
	 * Return the manifest of the cargo 
	 * @return
	 */
	public ArrayList<ArrayList<FreightContainer>> getManifest() {
		return this.manifest;
	}
}
