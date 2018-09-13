package fr.gouv.diplomatie.papyrus.codegen.java.test.utils;

import static org.junit.Assert.*;

import org.junit.Test;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Interface;
import org.eclipse.uml2.uml.Property;

import fr.gouv.diplomatie.papyrus.codegen.core.test.TestUtils;
import fr.gouv.diplomatie.papyrus.codegen.java.test.testutils.HornetModelJava;
import fr.gouv.diplomatie.papyrus.codegen.java.utils.JavaClassifierUtils;

public class JavaClassifierUtilsTest {

	@Test
	public void testClass() {
		JavaClassifierUtils utils = new JavaClassifierUtils();
		assertEquals(JavaClassifierUtils.class,utils.getClass());
	}
	
	@Test
	public void testGetNaturalOrderField() {
		HornetModelJava hmodel = HornetModelJava.initModel();
		Class class_ = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		
		Property prop = TestUtils.createAttribute(class_, "test", hmodel.stringPT, 0, 1);
		prop.applyStereotype(hmodel.naturalOrder);
		TestUtils.createAttribute(class_, "test2", hmodel.stringPT, 0, 1);
	
		assertEquals(prop, JavaClassifierUtils.getNaturalOrderField(class_));
	}
	
	@Test
	public void testExtGetNaturalOrderField() {
		HornetModelJava hmodel = HornetModelJava.initModel();
		Class class_ = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		Class class2_ = TestUtils.createClass(hmodel.pckage, "maClasse2", false);
		
		Property prop = TestUtils.createAttribute(class_, "test", hmodel.stringPT, 0, 1);
		prop.applyStereotype(hmodel.naturalOrder);
		TestUtils.createAttribute(class_, "test2", hmodel.stringPT, 0, 1);
		
		Property prop2 = TestUtils.createAttribute(class2_, "test3", hmodel.stringPT, 0, 1);
		prop2.applyStereotype(hmodel.naturalOrder);
		TestUtils.createAttribute(class2_, "test4", hmodel.stringPT, 0, 1);
		
		Interface interf = hmodel.pckage.createOwnedInterface("interf");
		Property interfprop = interf.createOwnedAttribute("att", hmodel.stringPT, 0, 1);
		interfprop.applyStereotype(hmodel.naturalOrder);
		interf.createOwnedAttribute("att2", hmodel.stringPT, 0, 1);
		
		class_.createInterfaceRealization("real", interf);
		
		TestUtils.createGeneralization(class_, class2_);
		
		assertEquals(null, JavaClassifierUtils.getNaturalOrderField(class_));
	}

	@Test
	public void testGetNaturalOrderFieldWithExtend() {
		HornetModelJava hmodel = HornetModelJava.initModel();
		Class class_ = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		Class class2_ = TestUtils.createClass(hmodel.pckage, "maClasse2", false);
		
		TestUtils.createAttribute(class_, "test", hmodel.stringPT, 0, 1);
		TestUtils.createAttribute(class_, "test2", hmodel.stringPT, 0, 1);
		
		Property prop2 = TestUtils.createAttribute(class2_, "test3", hmodel.stringPT, 0, 1);
		prop2.applyStereotype(hmodel.naturalOrder);
		TestUtils.createAttribute(class2_, "test4", hmodel.stringPT, 0, 1);
		
		Interface interf = hmodel.pckage.createOwnedInterface("interf");
		interf.createOwnedAttribute("att", hmodel.stringPT, 0, 1);
		interf.createOwnedAttribute("att2", hmodel.stringPT, 0, 1);
		
		class_.createInterfaceRealization("real", interf);
		
		TestUtils.createGeneralization(class_, class2_);
		
		assertEquals(prop2, JavaClassifierUtils.getNaturalOrderFieldWithExtend(class_));
	}
	
	@Test
	public void testInterfGetNaturalOrderFieldWithExtend() {
		HornetModelJava hmodel = HornetModelJava.initModel();
		Class class_ = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		
		TestUtils.createAttribute(class_, "test", hmodel.stringPT, 0, 1);
		TestUtils.createAttribute(class_, "test2", hmodel.stringPT, 0, 1);
		
		Interface interf = hmodel.pckage.createOwnedInterface("interf");
		Property interfProp = interf.createOwnedAttribute("att", hmodel.stringPT, 0, 1);
		interfProp.applyStereotype(hmodel.naturalOrder);
		interf.createOwnedAttribute("att2", hmodel.stringPT, 0, 1);
		
		class_.createInterfaceRealization("real", interf);
		
		assertEquals(interfProp, JavaClassifierUtils.getNaturalOrderFieldWithExtend(class_));
	}

	@Test
	public void testNoSchemaGetSchema() {
		HornetModelJava hmodel = HornetModelJava.initModel();
		Class class_ = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		assertEquals(null, JavaClassifierUtils.getSchema(class_));
	}
	
	/*
	@Test
	public void testGetSchema() {
		HornetModelJava hmodel = HornetModelJava.initModel();
		Class class_ = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		class_.applyStereotype(hmodel.entity);
		TestUtils.setStereotypePropertyValue(class_, hmodel.entity, hmodel.entitySchema, "test");
		assertEquals("test", JavaClassifierUtils.getSchema(class_));
	}*/

}
