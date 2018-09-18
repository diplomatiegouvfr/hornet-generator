package fr.gouv.diplomatie.papyrus.codegen.annotation.lombok.test.utils;

import static org.junit.Assert.*;

import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.AggregationKind;
import org.eclipse.uml2.uml.Association;
import org.eclipse.uml2.uml.AssociationClass;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Property;
import org.junit.Test;

import fr.gouv.diplomatie.papyrus.codegen.annotation.lombok.test.testutils.HornetModelLombok;
import fr.gouv.diplomatie.papyrus.codegen.annotation.lombok.utils.LombokClassifierUtils;
import fr.gouv.diplomatie.papyrus.codegen.core.test.TestUtils;

public class LombokClassifierUtilsTest {

	@Test
	public void testClass() {
		LombokClassifierUtils utils = new LombokClassifierUtils();
		assertEquals(LombokClassifierUtils.class, utils.getClass());
	}
	
	@Test
	public void testHasGetterAnnotation() {
		HornetModelLombok hmodel = HornetModelLombok.initModel();
		Classifier class_ = TestUtils.createClass(hmodel.pckage, "class", false);
		class_.applyStereotype(hmodel.entity);
		TestUtils.setStereotypePropertyValue(class_, hmodel.entity, hmodel.entityLombokGetter, true);
		assertEquals(true, LombokClassifierUtils.hasGetterAnnotation(class_));
	}
	
	@Test
	public void testNotHasGetterAnnotation() {
		HornetModelLombok hmodel = HornetModelLombok.initModel();
		Classifier class_ = TestUtils.createClass(hmodel.pckage, "class", false);
		assertEquals(false, LombokClassifierUtils.hasGetterAnnotation(class_));
		
		class_.applyStereotype(hmodel.entity);
		TestUtils.setStereotypePropertyValue(class_, hmodel.entity, hmodel.entityLombokGetter, "");
		assertEquals(false, LombokClassifierUtils.hasGetterAnnotation(class_));
		
		TestUtils.setStereotypePropertyValue(class_, hmodel.entity, hmodel.entityLombokGetter, null);
		assertEquals(false, LombokClassifierUtils.hasGetterAnnotation(class_));
	}

	@Test
	public void testGetGetterAnnotation() {
		HornetModelLombok hmodel = HornetModelLombok.initModel();
		Classifier class_ = TestUtils.createClass(hmodel.pckage, "class", false);
		class_.applyStereotype(hmodel.entity);
		TestUtils.setStereotypePropertyValue(class_, hmodel.entity, hmodel.entityLombokGetter, true);
		assertEquals(true, LombokClassifierUtils.getGetterAnnotation(class_));
	}

	@Test
	public void testHasSetterAnnotation() {
		HornetModelLombok hmodel = HornetModelLombok.initModel();
		Classifier class_ = TestUtils.createClass(hmodel.pckage, "class", false);
		class_.applyStereotype(hmodel.entity);
		TestUtils.setStereotypePropertyValue(class_, hmodel.entity, hmodel.entityLombokSetter, true);
		assertEquals(true, LombokClassifierUtils.hasSetterAnnotation(class_));
	}

	@Test
	public void testNotHasSetterAnnotation() {
		HornetModelLombok hmodel = HornetModelLombok.initModel();
		Classifier class_ = TestUtils.createClass(hmodel.pckage, "class", false);
		assertEquals(false, LombokClassifierUtils.hasSetterAnnotation(class_));
		
		class_.applyStereotype(hmodel.entity);
		TestUtils.setStereotypePropertyValue(class_, hmodel.entity, hmodel.entityLombokSetter, "");
		assertEquals(false, LombokClassifierUtils.hasSetterAnnotation(class_));
	}
	
	@Test
	public void testGetSetterAnnotation() {
		HornetModelLombok hmodel = HornetModelLombok.initModel();
		Classifier class_ = TestUtils.createClass(hmodel.pckage, "class", false);
		class_.applyStereotype(hmodel.entity);
		TestUtils.setStereotypePropertyValue(class_, hmodel.entity, hmodel.entityLombokSetter, true);
		assertEquals(true, LombokClassifierUtils.getSetterAnnotation(class_));
	}

	@Test
	public void testHasNoArgsConstructor() {
		HornetModelLombok hmodel = HornetModelLombok.initModel();
		Classifier class_ = TestUtils.createClass(hmodel.pckage, "class", false);
		class_.applyStereotype(hmodel.entity);
		TestUtils.setStereotypePropertyValue(class_, hmodel.entity, hmodel.entityLombokNoArgsCons, true);
		assertEquals(true, LombokClassifierUtils.hasNoArgsConstructor(class_));
	}
	
	@Test
	public void testNotHasNoArgsConstructor() {
		HornetModelLombok hmodel = HornetModelLombok.initModel();
		Classifier class_ = TestUtils.createClass(hmodel.pckage, "class", false);
		assertEquals(false, LombokClassifierUtils.hasNoArgsConstructor(class_));
		
		class_.applyStereotype(hmodel.entity);
		TestUtils.setStereotypePropertyValue(class_, hmodel.entity, hmodel.entityLombokNoArgsCons, "");
		assertEquals(false, LombokClassifierUtils.hasNoArgsConstructor(class_));
	}

	@Test
	public void testGetNoArgsConstructorAnnotation() {
		HornetModelLombok hmodel = HornetModelLombok.initModel();
		Classifier class_ = TestUtils.createClass(hmodel.pckage, "class", false);
		class_.applyStereotype(hmodel.entity);
		TestUtils.setStereotypePropertyValue(class_, hmodel.entity, hmodel.entityLombokNoArgsCons, true);
		assertEquals(true, LombokClassifierUtils.getNoArgsConstructorAnnotation(class_));
	}

	@Test
	public void testHasAllArgsConstructor() {
		HornetModelLombok hmodel = HornetModelLombok.initModel();
		Classifier class_ = TestUtils.createClass(hmodel.pckage, "class", false);
		class_.applyStereotype(hmodel.entity);
		TestUtils.setStereotypePropertyValue(class_, hmodel.entity, hmodel.entityLombokAllArgsCons, true);
		assertEquals(true, LombokClassifierUtils.hasAllArgsConstructor(class_));
	}
	
	@Test
	public void testNotHasAllArgsConstructor() {
		HornetModelLombok hmodel = HornetModelLombok.initModel();
		Classifier class_ = TestUtils.createClass(hmodel.pckage, "class", false);
		assertEquals(false, LombokClassifierUtils.hasAllArgsConstructor(class_));
		
		class_.applyStereotype(hmodel.entity);
		TestUtils.setStereotypePropertyValue(class_, hmodel.entity, hmodel.entityLombokAllArgsCons, "");
		assertEquals(false, LombokClassifierUtils.hasAllArgsConstructor(class_));
	}

	@Test
	public void testGetAllArgsConstructorAnnotation() {
		HornetModelLombok hmodel = HornetModelLombok.initModel();
		Classifier class_ = TestUtils.createClass(hmodel.pckage, "class", false);
		class_.applyStereotype(hmodel.entity);
		TestUtils.setStereotypePropertyValue(class_, hmodel.entity, hmodel.entityLombokAllArgsCons, true);
		assertEquals(true, LombokClassifierUtils.getAllArgsConstructorAnnotation(class_));
	}

	@Test
	public void testHasToString() {
		HornetModelLombok hmodel = HornetModelLombok.initModel();
		Classifier class_ = TestUtils.createClass(hmodel.pckage, "class", false);
		class_.applyStereotype(hmodel.entity);
		TestUtils.setStereotypePropertyValue(class_, hmodel.entity, hmodel.entityLombokToString, true);
		assertEquals(true, LombokClassifierUtils.hasToString(class_));
	}
	
	@Test
	public void testNotHasToString() {
		HornetModelLombok hmodel = HornetModelLombok.initModel();
		Classifier class_ = TestUtils.createClass(hmodel.pckage, "class", false);
		assertEquals(false, LombokClassifierUtils.hasToString(class_));
		class_.applyStereotype(hmodel.entity);
		TestUtils.setStereotypePropertyValue(class_, hmodel.entity, hmodel.entityLombokToString, "");
		assertEquals(false, LombokClassifierUtils.hasToString(class_));
	}

	@Test
	public void testGetToStringAnnotation() {
		HornetModelLombok hmodel = HornetModelLombok.initModel();
		Classifier class_ = TestUtils.createClass(hmodel.pckage, "class", false);
		class_.applyStereotype(hmodel.entity);
		TestUtils.setStereotypePropertyValue(class_, hmodel.entity, hmodel.entityLombokToString, true);
		assertEquals(true, LombokClassifierUtils.getToStringAnnotation(class_));
	}

	@Test
	public void testHasEqualsAndHashCode() {
		HornetModelLombok hmodel = HornetModelLombok.initModel();
		Classifier class_ = TestUtils.createClass(hmodel.pckage, "class", false);
		class_.applyStereotype(hmodel.entity);
		TestUtils.setStereotypePropertyValue(class_, hmodel.entity, hmodel.entityLombokEqAndHashc, true);
		assertEquals(true, LombokClassifierUtils.hasEqualsAndHashCode(class_));
	}
	
	@Test
	public void testNotHasEqualsAndHashCode() {
		HornetModelLombok hmodel = HornetModelLombok.initModel();
		Classifier class_ = TestUtils.createClass(hmodel.pckage, "class", false);
		assertEquals(false, LombokClassifierUtils.hasEqualsAndHashCode(class_));
		
		class_.applyStereotype(hmodel.entity);
		TestUtils.setStereotypePropertyValue(class_, hmodel.entity, hmodel.entityLombokEqAndHashc, "");
		assertEquals(false, LombokClassifierUtils.hasEqualsAndHashCode(class_));
	}

	@Test
	public void testGetEqualsAndHashCode() {
		HornetModelLombok hmodel = HornetModelLombok.initModel();
		Classifier class_ = TestUtils.createClass(hmodel.pckage, "class", false);
		class_.applyStereotype(hmodel.entity);
		TestUtils.setStereotypePropertyValue(class_, hmodel.entity, hmodel.entityLombokEqAndHashc, true);
		assertEquals(true, LombokClassifierUtils.getEqualsAndHashCode(class_));
	}

	@Test
	public void testGetLombokDisplayWith() {
		HornetModelLombok hmodel =  HornetModelLombok.initModel();
		Class class_ = TestUtils.createClass(hmodel.pckage, "class", false);
		Property prop = TestUtils.createAttribute(class_, "att", hmodel.stringPT, 0, 1);
		prop.applyStereotype(hmodel.attribute);
		TestUtils.setStereotypePropertyValue(prop, hmodel.attribute, hmodel.attributeLombokDisplayWith, true);
		assertEquals(true, LombokClassifierUtils.getLombokDisplayWith(prop));
	}
	
	@Test
	public void testKeyAttributeGetLombokDisplayWith() {
		HornetModelLombok hmodel = HornetModelLombok.initModel();
		Class class_ = TestUtils.createClass(hmodel.pckage, "class", false);
		Property prop = TestUtils.createAttribute(class_, "att", hmodel.stringPT, 0, 1);
		prop.applyStereotype(hmodel.keyAttribute);
		TestUtils.setStereotypePropertyValue(prop, hmodel.keyAttribute, hmodel.attributeLombokDisplayWith, true);
		assertEquals(true, LombokClassifierUtils.getLombokDisplayWith(prop));
	}
	
	@Test
	public void testAssociationTableGetLombokDisplayWith() {
		HornetModelLombok hmodel = HornetModelLombok.initModel();
		Class class_ = TestUtils.createClass(hmodel.pckage, "class", false);
		Class class2_ = TestUtils.createClass(hmodel.pckage, "class2", false);
		AssociationClass asso = TestUtils.createAssociationClass(class_, class2_, "test", "test2", "asso");
		asso.applyStereotype(hmodel.associationTable);
		TestUtils.setStereotypePropertyValue(asso, hmodel.associationTable, hmodel.associationTableLombokDisplayWith, true);
		assertEquals(true, LombokClassifierUtils.getLombokDisplayWith(asso));
	}
	
	@Test
	public void testAssociationLinkGetLombokDisplayWith() {
		HornetModelLombok hmodel = HornetModelLombok.initModel();
		Class class_ = TestUtils.createClass(hmodel.pckage, "class", false);
		Class class2_ = TestUtils.createClass(hmodel.pckage, "class2", false);
		Association asso = class_.createAssociation(true, AggregationKind.NONE_LITERAL, "test", 0, 1, class2_, true, AggregationKind.NONE_LITERAL, "test2", 0, 1);
		asso.applyStereotype(hmodel.associationLink);
		Property prop = class_.getAttribute("test", class2_);
		prop.setAssociation(asso);
		
		TestUtils.setStereotypePropertyValue(asso, hmodel.associationLink, hmodel.associationTableLombokDisplayWith, true);
		assertEquals(true, LombokClassifierUtils.getLombokDisplayWith(prop));
	}
	
	@Test
	public void testAssoGetLombokDisplayWith() {
		HornetModelLombok hmodel = HornetModelLombok.initModel();
		Class class_ = TestUtils.createClass(hmodel.pckage, "class", false);
		Class class2_ = TestUtils.createClass(hmodel.pckage, "class2", false);
		Association asso = class_.createAssociation(true, AggregationKind.NONE_LITERAL, "test", 0, 1, class2_, true, AggregationKind.NONE_LITERAL, "test2", 0, 1);
		Property prop = class_.getAttribute("test", class2_);
		prop.setAssociation(asso);
		assertEquals(true, LombokClassifierUtils.getLombokDisplayWith(prop));
	}
	
	@Test
	public void testRandomGetLombokDisplayWith() {
		HornetModelLombok hmodel = HornetModelLombok.initModel();
		Class class_ = TestUtils.createClass(hmodel.pckage, "class", false);
		assertEquals(true, LombokClassifierUtils.getLombokDisplayWith(class_));
	}

}
