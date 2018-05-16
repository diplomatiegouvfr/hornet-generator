package fr.gouv.diplomatie.papyrus.codegen.java.test.utils;

import static org.junit.Assert.*;

import org.eclipse.uml2.uml.Class;
import org.junit.Test;

import fr.gouv.diplomatie.papyrus.codegen.core.test.TestUtils;
import fr.gouv.diplomatie.papyrus.codegen.java.test.testutils.HornetModelJava;
import fr.gouv.diplomatie.papyrus.codegen.java.utils.TypeUtils;

public class TypeUtilsTest {

	@Test
	public void testClass() {
		TypeUtils utils = new TypeUtils();
		assertEquals(TypeUtils.class, utils.getClass());
	}
	
	@Test
	public void testGetJavaType() {
		HornetModelJava hmodel = HornetModelJava.initModel();
		Class class_ = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		class_.applyStereotype(hmodel.hornetType);
		TestUtils.setStereotypePropertyValue(class_, hmodel.hornetType, hmodel.hornetTypeJavaType, "test");
		assertEquals("test", TypeUtils.getJavaType(class_));
		
		TestUtils.setStereotypePropertyValue(class_, hmodel.hornetType, hmodel.hornetTypeJavaType, "");
		assertEquals("String", TypeUtils.getJavaType(class_));
		
		TestUtils.setStereotypePropertyValue(class_, hmodel.hornetType, hmodel.hornetTypeJavaType, null);
		assertEquals("String", TypeUtils.getJavaType(class_));
	}

}
