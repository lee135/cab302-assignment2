package asgn2Tests;

/* Some valid container codes used in the tests below:
 * INKU2633836
 * KOCU8090115
 * MSCU6639871
 * CSQU3054389
 */

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import asgn2Codes.ContainerCode;
import asgn2Containers.DangerousGoodsContainer;
import asgn2Containers.FreightContainer;
import asgn2Containers.GeneralGoodsContainer;
import asgn2Containers.RefrigeratedContainer;
import asgn2Exceptions.InvalidCodeException;
import asgn2Exceptions.InvalidContainerException;

/***
 * Unit test for Container
 * 
 * @author Zehui Zhang (N8646236)
 *
 */

public class ContainerTests {
	// Implementation Here - includes tests for ContainerCode and for the actual
	// container classes.
	private static ContainerCode containerCode;

	private String code1;
	private String code2;
	private String code3;
	private String code4;
	private String code5;
	private String code6;
	private String code7;

	private int grossWeight1;
	private int grossWeight2;
	
	private int category;
	
	private int temprature;

	private GeneralGoodsContainer container;

	private FreightContainer container2;
	@Before
	public void setUp() throws Exception {
		code1="MESU123541";		//invalid
		code2="2ESU123541";		//invalid
		code3="MESZ123541";		//invalid
		code4="MSCU66a9871";	//invalid
		code5="CSQU3054384";	//invalid
		code6="KOCU8090115";	//valid
		code7="MSCU6639871";	//valid
		
		grossWeight1 = 35;		//invalid
		grossWeight2 = 23;		//valid
		
		category = 2;
		
		temprature = 0;
	}
	
	
	//Test the ContainerCode Class
	
	/***
	 * Test the length of the container code
	 * @throws InvalidCodeException 
	 */
	@Test(expected = InvalidCodeException.class)
    public void testContinerCodeLength() throws InvalidCodeException {  
		
		containerCode = new ContainerCode(code1);
    }  
	
	/***
	 * Test the owner code of the container code
	 * @throws InvalidCodeException 
	 */
	@Test(expected = InvalidCodeException.class)
    public void testOwnerCode() throws InvalidCodeException {  
		containerCode = new ContainerCode(code2);
    }  
	
	/***
	 * Test the category code of the container code
	 * @throws InvalidCodeException 
	 */
	@Test(expected = InvalidCodeException.class)
    public void testCategortyCode() throws InvalidCodeException {  
		containerCode = new ContainerCode(code3);
    }  
	
	/***
	 * Test the serial code of the container code
	 * @throws InvalidCodeException 
	 */
	@Test(expected = InvalidCodeException.class)
    public void testSerialCode() throws InvalidCodeException {  
		containerCode = new ContainerCode(code4);
    } 
	
	/***
	 * Test the check code of the container code
	 * @throws InvalidCodeException 
	 */
	@Test(expected = InvalidCodeException.class)
    public void testCheckCode() throws InvalidCodeException {  
		containerCode = new ContainerCode(code5);
    } 
	
	/***
	 * Test the valid ContainerCode
	 * @throws InvalidCodeException 
	 */
	@Test
    public void testValidCode() throws InvalidCodeException {  
		containerCode = new ContainerCode(code6);
		assertEquals(code6, containerCode.toString()); 
    } 
	
	/***
	 * Test toString function 
	 * @throws InvalidCodeException 
	 */
	@Test
    public void testToString() throws InvalidCodeException {  
		containerCode = new ContainerCode(code7);
		assertEquals(code7, containerCode.toString()); 
    }
	
	/***
	 * Test equals function 
	 * @throws InvalidCodeException 
	 */
	@Test
    public void testEquals() throws InvalidCodeException {  
		containerCode = new ContainerCode(code7);
		ContainerCode containerCode2 = new ContainerCode("MSCU6639871");
		assertTrue(containerCode.equals(containerCode2)); 
    } 

	//Test the Container Classes
	/***
	 * Test the constructor of the Container class
	 * expect to throw InvalidContainerException exception
	 * @throws InvalidCodeException, InvalidContainerException 
	 */
	@Test(expected = InvalidContainerException.class)
    public void testInvalidGrossWeight() throws InvalidCodeException, InvalidContainerException {  
		containerCode = new ContainerCode(code6);
		setContainer2(new GeneralGoodsContainer(containerCode, grossWeight1));
    }
	

	/***
	 * Test the constructor of the Container class
	 * expect to throw InvalidContainerException exception
	 * @throws InvalidCodeException, InvalidContainerException 
	 */
	@Test
    public void testValidGrossWeight() throws InvalidCodeException, InvalidContainerException {  
		containerCode = new ContainerCode(code6);
		setContainer(new GeneralGoodsContainer(containerCode, grossWeight2));
    }
	
	
	/***
	 * Test the DangerousGoodsContainer class
	 * expect to get the container code
	 * @throws InvalidCodeException, InvalidContainerException 
	 */
	@Test
    public void testGetCode() throws InvalidCodeException, InvalidContainerException {  
		containerCode = new ContainerCode(code6);
		DangerousGoodsContainer container = new DangerousGoodsContainer(containerCode, grossWeight2, category);
		assertEquals(containerCode, container.getCode()); 
    } 
	
	/***
	 * Test the DangerousGoodsContainer class
	 * expect to get the gross weight
	 * @throws InvalidCodeException, InvalidContainerException 
	 */
	@Test
    public void testGetGrossWeight() throws InvalidCodeException, InvalidContainerException {  
		containerCode = new ContainerCode(code6);
		DangerousGoodsContainer container = new DangerousGoodsContainer(containerCode, grossWeight2, category);
		assertEquals((Integer)grossWeight2, container.getGrossWeight());
 
	}
	
	
	/***
	 * Test the DangerousGoodsContainer class
	 * expect to get the category code
	 * @throws InvalidCodeException, InvalidContainerException 
	 */
	@Test
    public void testGetCategory() throws InvalidCodeException, InvalidContainerException {  
		containerCode = new ContainerCode(code6);
		DangerousGoodsContainer container = new DangerousGoodsContainer(containerCode, grossWeight2, category);
		assertEquals((Integer)category, container.getCategory());
 
	}
	
	
	/***
	 * Test the RefrigeratedContainer class
	 * expect to get the temprature
	 * @throws InvalidCodeException, InvalidContainerException 
	 */
	@Test
    public void testGetTemperature() throws InvalidCodeException, InvalidContainerException {  
		containerCode = new ContainerCode(code6);
		
		RefrigeratedContainer container = new RefrigeratedContainer(containerCode, grossWeight2, temprature);
		assertEquals((Integer)temprature, container.getTemperature());
 
	}


	public GeneralGoodsContainer getContainer() {
		return container;
	}


	public void setContainer(GeneralGoodsContainer container) {
		this.container = container;
	}


	public FreightContainer getContainer2() {
		return container2;
	}


	public void setContainer2(FreightContainer container2) {
		this.container2 = container2;
	}
	
}
