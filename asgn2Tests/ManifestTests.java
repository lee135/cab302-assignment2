package asgn2Tests;

/* Some valid container codes used in the tests below:
 * INKU2633836
 * KOCU8090115
 * MSCU6639871
 * CSQU3054389
 * QUTU7200318
 * IBMU4882351
 */

import org.junit.Before;
import org.junit.Test;

import asgn2Codes.ContainerCode;
import asgn2Containers.DangerousGoodsContainer;
import asgn2Containers.FreightContainer;
import asgn2Containers.GeneralGoodsContainer;
import asgn2Containers.RefrigeratedContainer;
import asgn2Exceptions.InvalidCodeException;
import asgn2Exceptions.InvalidContainerException;
import asgn2Exceptions.ManifestException;
import asgn2Manifests.CargoManifest;
import static org.junit.Assert.*;

/***
 * Unit test for Manifest
 * 
 * @author Yu Zhang (N8628769)
 *
 */

public class ManifestTests {
	//Implementation Here	

	private CargoManifest manefist;
	
	private int numStacks = 2;
	private int maxHeight = 2;
	private int maxWeight = 150;
	
	private GeneralGoodsContainer container1;
	private GeneralGoodsContainer container2;
	private GeneralGoodsContainer container3;
	private RefrigeratedContainer container4;
	private RefrigeratedContainer container5;
	private RefrigeratedContainer container6;

	@Before
	public void setUp() throws Exception {
		
		container1 = new GeneralGoodsContainer(new ContainerCode("INKU2633836"), 25);
		container2 = new GeneralGoodsContainer(new ContainerCode("KOCU8090115"), 25);
		container3 = new GeneralGoodsContainer(new ContainerCode("MSCU6639871"), 25);
		container4 = new RefrigeratedContainer(new ContainerCode("CSQU3054389"), 25, 0);
		container5 = new RefrigeratedContainer(new ContainerCode("QUTU7200318"), 25, 1);
		container6 = new RefrigeratedContainer(new ContainerCode("IBMU4882351"), 25, 5);
		
	}

	/***
	 * Test constructor
	 * @throws ManifestException 
	 */
	@Test(expected = ManifestException.class)
    public void testInvalidNumStacksParameter() throws ManifestException {  
		int numStacks = -1;
		int maxHeight = 3;
		int maxWeight = 30;
		CargoManifest cm = new CargoManifest(numStacks, maxHeight, maxWeight);
    }  
	
	@Test(expected = ManifestException.class)
    public void testInvalidMaxHeightParameter() throws ManifestException {  
		int numStacks = 5;
		int maxHeight = -1;
		int maxWeight = 30;
		CargoManifest cm = new CargoManifest(numStacks, maxHeight, maxWeight);
    }  
	
	@Test(expected = ManifestException.class)
    public void testInvalidMaxWeightParameter() throws ManifestException {  
		int numStacks = 5;
		int maxHeight = 3;
		int maxWeight = -3;
		CargoManifest cm = new CargoManifest(numStacks, maxHeight, maxWeight);
    }  
	
	
	/***
	 * Test loadContainer
	 * @throws ManifestException 
	 */
	@Test
    public void testLoadContainer() throws ManifestException {  
		
		manefist = new CargoManifest(numStacks, maxHeight, maxWeight);
		
		manefist.loadContainer(container1);
		FreightContainer[] array = manefist.toArray(0);
		FreightContainer[] expectedArray = {container1};
		assertArrayEquals(expectedArray, array);
    }
	
	/***
	 * Test loadContainer
	 * load two same type container into the sam stack
	 * @throws ManifestException 
	 */
	@Test
    public void testLoadTwoContainer() throws ManifestException {  
		
		manefist = new CargoManifest(numStacks, maxHeight, maxWeight);
		manefist.loadContainer(container1);
		manefist.loadContainer(container2);
		FreightContainer[] array = manefist.toArray(0);
		FreightContainer[] expectedArray = {container1, container2};
		assertArrayEquals(expectedArray, array);
    }
	
	/***
	 * Test loadContainer
	 * load two different type container into the two stacks
	 * @throws ManifestException 
	 */
	@Test
    public void testLoadDiffContainer() throws ManifestException {  
		
		manefist = new CargoManifest(numStacks, maxHeight, maxWeight);
		manefist.loadContainer(container1);
		manefist.loadContainer(container2);
		manefist.loadContainer(container4);
		manefist.loadContainer(container5);
		FreightContainer[] array1 = manefist.toArray(0);
		FreightContainer[] expectedArray1 = {container1, container2};
		assertArrayEquals(expectedArray1, array1);
		FreightContainer[] array2 = manefist.toArray(1);
		FreightContainer[] expectedArray2 = {container4, container5};
		assertArrayEquals(expectedArray2, array2);
    }
	
	/***
	 * Test loadContainer
	 * load a onboad container into the ship, expect to throw exception
	 * @throws ManifestException 
	 */
	@Test(expected = ManifestException.class)
    public void testLoadedContainer() throws ManifestException {  
		
		manefist = new CargoManifest(numStacks, maxHeight, maxWeight);
		manefist.loadContainer(container1);
		manefist.loadContainer(container4);
		manefist.loadContainer(container1);
    }
	
	/***
	 * Test loadContainer
	 * load a container into a full ship, expect to throw exception
	 * @throws ManifestException 
	 * @throws InvalidCodeException 
	 * @throws InvalidContainerException 
	 */
	@Test(expected = ManifestException.class)
    public void testNoSpace() throws ManifestException, InvalidContainerException, InvalidCodeException {  
		
		manefist = new CargoManifest(numStacks, maxHeight, maxWeight);
		manefist.loadContainer(container1);
		manefist.loadContainer(container2);
		manefist.loadContainer(container4);
		manefist.loadContainer(container5);
		manefist.loadContainer(container3);
    }
	
	/***
	 * Test loadContainer
	 * load a  RefrigeratedContainer into a ship, but the  RefrigeratedContainer 
	 * stack is full, expect to throw exception
	 * @throws ManifestException 
	 * @throws InvalidCodeException 
	 * @throws InvalidContainerException 
	 */
	@Test(expected = ManifestException.class)
    public void testNoRefrigeratedContainerSpace() throws ManifestException, InvalidContainerException, InvalidCodeException {  
		
		manefist = new CargoManifest(numStacks, maxHeight, maxWeight);
		manefist.loadContainer(container1);
		manefist.loadContainer(container4);
		manefist.loadContainer(container5);
		manefist.loadContainer(container6);
    }
	
}
