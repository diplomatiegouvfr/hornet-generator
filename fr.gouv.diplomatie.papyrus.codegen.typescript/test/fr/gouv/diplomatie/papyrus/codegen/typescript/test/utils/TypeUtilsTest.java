package fr.gouv.diplomatie.papyrus.codegen.typescript.test.utils;

import static org.junit.Assert.assertEquals;

import org.eclipse.uml2.uml.Type;
import org.junit.Test;

import fr.gouv.diplomatie.papyrus.codegen.core.test.TestUtils;
import fr.gouv.diplomatie.papyrus.codegen.typescript.test.testutils.HornetModelTypescript;
import fr.gouv.diplomatie.papyrus.codegen.typescript.utils.TypeUtils;

public class TypeUtilsTest {

	@Test
	public void testGetSequelizeType() {
		HornetModelTypescript hmodel = HornetModelTypescript.initModel();
		
		Type class_ = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		class_.applyStereotype(hmodel.hornetType);
		
		assertEquals("STRING", TypeUtils.getSequelizeType(class_));
		
		TestUtils.setStereotypePropertyValue(class_, hmodel.hornetType, hmodel.hornetTypeSequelizeType, "TEST");
		assertEquals("TEST", TypeUtils.getSequelizeType(class_));
	}

	@Test
	public void testGetTypescriptType() {
		HornetModelTypescript hmodel = HornetModelTypescript.initModel();	
		
		Type class_ = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		class_.applyStereotype(hmodel.hornetType);
		
		assertEquals("string", TypeUtils.getTypescriptType(class_));
		
		TestUtils.setStereotypePropertyValue(class_, hmodel.hornetType, hmodel.hornetTypeTypescriptType, "Test");
		assertEquals("Test", TypeUtils.getTypescriptType(class_));
	}

	@Test
	public void testGetMetierTypescriptType() {
		HornetModelTypescript hmodel = HornetModelTypescript.initModel();
		
	    Type class_ = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		class_.applyStereotype(hmodel.hornetType);
		
		assertEquals("string", TypeUtils.getMetierTypescriptType(class_));
		
		TestUtils.setStereotypePropertyValue(class_, hmodel.hornetType, hmodel.hornetTypeMetierClassType, "Test");		
		assertEquals("Test", TypeUtils.getMetierTypescriptType(class_));
	}

}
