package fr.gouv.diplomatie.papyrus.codegen.java.test.utils;

import static org.junit.Assert.*;

import org.eclipse.uml2.uml.AggregationKind;
import org.eclipse.uml2.uml.Association;
import org.eclipse.uml2.uml.AssociationClass;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Property;
import org.junit.Test;

import fr.gouv.diplomatie.papyrus.codegen.core.test.TestUtils;
import fr.gouv.diplomatie.papyrus.codegen.java.test.testutils.HornetModelJava;
import fr.gouv.diplomatie.papyrus.codegen.java.utils.JavaPluginUtils;

public class JavaPluginUtilsTest {

	@Test
	public void testClass() {
		JavaPluginUtils utils = new JavaPluginUtils();
		assertEquals(JavaPluginUtils.class, utils.getClass());

	}
	
	@Test
	public void testIsNaturalOrderField() {
		HornetModelJava hmodel = HornetModelJava.initModel();
		Class class_ = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		
		Property prop = TestUtils.createAttribute(class_, "test", hmodel.stringPT, 0, 1);
		prop.applyStereotype(hmodel.naturalOrder);
	
		assertEquals(false, JavaPluginUtils.isNaturalOrderField(class_));
		assertEquals(true, JavaPluginUtils.isNaturalOrderField(prop));

	}

	@Test
	public void testAssociationTableGetFetchType() {
		HornetModelJava hmodel = HornetModelJava.initModel();
		Class class_ = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		Class class2_ = TestUtils.createClass(hmodel.pckage, "maClasse2", false);
		
		AssociationClass asso = TestUtils.createAssociationClass(class_, class2_, "att1", "att2", "asso");
		asso.applyStereotype(hmodel.associationTable);
		TestUtils.setStereotypePropertyValue(asso, hmodel.associationTable, hmodel.associationTableFetchType, hmodel.fetchTypeLazy);
		assertEquals(hmodel.fetchTypeLazy, JavaPluginUtils.getFetchType(asso));
	}
	
	@Test
	public void testRandomGetFetchType() {
		HornetModelJava hmodel = HornetModelJava.initModel();
		Class class_ = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		assertEquals(null, JavaPluginUtils.getFetchType(class_));
	}
	
	@Test
	public void testAttributeGetFetchType() {
		HornetModelJava hmodel = HornetModelJava.initModel();
		Class class_ = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		Property prop = TestUtils.createAttribute(class_, "test", hmodel.stringPT, 0, 1);
		prop.applyStereotype(hmodel.attribute);
		TestUtils.setStereotypePropertyValue(prop, hmodel.attribute, hmodel.attributeFetchType, hmodel.fetchTypeLazy);
		assertEquals(hmodel.fetchTypeLazy, JavaPluginUtils.getFetchType(prop));
	}
	
	@Test
	public void testKeyAttributeGetFetchType() {
		HornetModelJava hmodel = HornetModelJava.initModel();
		Class class_ = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		Property prop = TestUtils.createAttribute(class_, "test", hmodel.stringPT, 0, 1);
		prop.applyStereotype(hmodel.keyAttribute);
		TestUtils.setStereotypePropertyValue(prop, hmodel.keyAttribute, hmodel.attributeFetchType, hmodel.fetchTypeLazy);
		assertEquals(hmodel.fetchTypeLazy, JavaPluginUtils.getFetchType(prop));
	}
	
	@Test
	public void testAssociationLinkGetFetchType() {
		HornetModelJava hmodel = HornetModelJava.initModel();
		Class class_ = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		Class class2_ = TestUtils.createClass(hmodel.pckage, "maClasse2", false);
		
		Association asso = class_.createAssociation(true, AggregationKind.NONE_LITERAL, "test", 0, 1, class2_, true, AggregationKind.NONE_LITERAL, "test2", 0, 1);
		asso.applyStereotype(hmodel.associationLink);
		TestUtils.setStereotypePropertyValue(asso, hmodel.associationLink, hmodel.associationTableFetchType, hmodel.fetchTypeLazy);
		
		Property prop = TestUtils.createAttribute(class_, "att", hmodel.booleanPT, 0, 1);
		prop.setAssociation(asso);
		assertEquals(hmodel.fetchTypeLazy, JavaPluginUtils.getFetchType(prop));
	}
	
	@Test
	public void testAssociationGetFetchType() {
		HornetModelJava hmodel = HornetModelJava.initModel();
		Class class_ = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		Class class2_ = TestUtils.createClass(hmodel.pckage, "maClasse2", false);
		
		Association asso = class_.createAssociation(true, AggregationKind.NONE_LITERAL, "test", 0, 1, class2_, true, AggregationKind.NONE_LITERAL, "test2", 0, 1);
		
		Property prop = TestUtils.createAttribute(class_, "att", hmodel.booleanPT, 0, 1);
		prop.setAssociation(asso);
		assertEquals(null, JavaPluginUtils.getFetchType(prop));
	}

	@Test
	public void testGetShouldBeNull() {
		HornetModelJava hmodel = HornetModelJava.initModel();
		Class class_ = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		Property prop = TestUtils.createAttribute(class_, "att", hmodel.booleanPT, 0, 1);
		prop.applyStereotype(hmodel.attribute);
		TestUtils.setStereotypePropertyValue(prop, hmodel.attribute, hmodel.attributeShouldBeNull, true);
		assertEquals(true, JavaPluginUtils.getShouldBeNull(prop));
		TestUtils.setStereotypePropertyValue(prop, hmodel.attribute, hmodel.attributeShouldBeNull, false);
		assertEquals(false, JavaPluginUtils.getShouldBeNull(prop));
	}

	@Test
	public void testGetAlwaysTrue() {
		HornetModelJava hmodel = HornetModelJava.initModel();
		Class class_ = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		Property prop = TestUtils.createAttribute(class_, "att", hmodel.booleanPT, 0, 1);
		prop.applyStereotype(hmodel.booleanTyped);
		TestUtils.setStereotypePropertyValue(prop, hmodel.booleanTyped, hmodel.booleanTypedAlwaysTrue, true);
		assertEquals(true, JavaPluginUtils.getAlwaysTrue(prop));
		TestUtils.setStereotypePropertyValue(prop, hmodel.booleanTyped, hmodel.booleanTypedAlwaysTrue, false);
		assertEquals(false, JavaPluginUtils.getAlwaysTrue(prop));
	}

	@Test
	public void testGetAlwaysFalse() {
		HornetModelJava hmodel = HornetModelJava.initModel();
		Class class_ = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		Property prop = TestUtils.createAttribute(class_, "att", hmodel.booleanPT, 0, 1);
		prop.applyStereotype(hmodel.booleanTyped);
		TestUtils.setStereotypePropertyValue(prop, hmodel.booleanTyped, hmodel.booleanTypedAlwaysFalse, true);
		assertEquals(true, JavaPluginUtils.getAlwaysFalse(prop));
		TestUtils.setStereotypePropertyValue(prop, hmodel.booleanTyped, hmodel.booleanTypedAlwaysFalse, false);
		assertEquals(false, JavaPluginUtils.getAlwaysFalse(prop));
	}

	@Test
	public void testGetFuture() {
		HornetModelJava hmodel = HornetModelJava.initModel();
		Class class_ = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		Property prop = TestUtils.createAttribute(class_, "att", hmodel.booleanPT, 0, 1);
		prop.applyStereotype(hmodel.dateTyped);
		TestUtils.setStereotypePropertyValue(prop, hmodel.dateTyped, hmodel.dateTypedFuture, true);
		assertEquals(true, JavaPluginUtils.getFuture(prop));
	}

	@Test
	public void testGetFutureOrPresent() {
		HornetModelJava hmodel = HornetModelJava.initModel();
		Class class_ = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		Property prop = TestUtils.createAttribute(class_, "att", hmodel.booleanPT, 0, 1);
		prop.applyStereotype(hmodel.dateTyped);
		TestUtils.setStereotypePropertyValue(prop, hmodel.dateTyped, hmodel.dateTypedFutureOrPresent, true);
		assertEquals(true, JavaPluginUtils.getFutureOrPresent(prop));
	}

	@Test
	public void testGetPast() {
		HornetModelJava hmodel = HornetModelJava.initModel();
		Class class_ = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		Property prop = TestUtils.createAttribute(class_, "att", hmodel.booleanPT, 0, 1);
		prop.applyStereotype(hmodel.dateTyped);
		TestUtils.setStereotypePropertyValue(prop, hmodel.dateTyped, hmodel.dateTypedPast, true);
		assertEquals(true, JavaPluginUtils.getPast(prop));
	}

	@Test
	public void testGetPastOrPresent() {
		HornetModelJava hmodel = HornetModelJava.initModel();
		Class class_ = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		Property prop = TestUtils.createAttribute(class_, "att", hmodel.booleanPT, 0, 1);
		prop.applyStereotype(hmodel.dateTyped);
		TestUtils.setStereotypePropertyValue(prop, hmodel.dateTyped, hmodel.dateTypedPastOrPresent, true);
		assertEquals(true, JavaPluginUtils.getPastOrPresent(prop));
	}

	@Test
	public void testGetCollectionSizeMin() {
		HornetModelJava hmodel = HornetModelJava.initModel();
		Class class_ = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		Property prop = TestUtils.createAttribute(class_, "att", hmodel.stringPT, 0, -1);
		prop.applyStereotype(hmodel.collection);
		TestUtils.setStereotypePropertyValue(prop, hmodel.collection, hmodel.collectionHasSizeMin, true);
		TestUtils.setStereotypePropertyValue(prop, hmodel.collection, hmodel.collectionSizeMin, 2);
		assertEquals(2, JavaPluginUtils.getCollectionSizeMin(prop));
		
		TestUtils.setStereotypePropertyValue(prop, hmodel.collection, hmodel.collectionHasSizeMin, false);
		assertEquals(null, JavaPluginUtils.getCollectionSizeMin(prop));
	}

	@Test
	public void testGetCollectionSizeMax() {
		HornetModelJava hmodel = HornetModelJava.initModel();
		Class class_ = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		Property prop = TestUtils.createAttribute(class_, "att", hmodel.stringPT, 0, -1);
		prop.applyStereotype(hmodel.collection);
		TestUtils.setStereotypePropertyValue(prop, hmodel.collection, hmodel.collectionHasSizeMax, true);
		TestUtils.setStereotypePropertyValue(prop, hmodel.collection, hmodel.collectionSizeMax, 2);
		assertEquals(2, JavaPluginUtils.getCollectionSizeMax(prop));
		
		TestUtils.setStereotypePropertyValue(prop, hmodel.collection, hmodel.collectionHasSizeMax, false);
		assertEquals(null, JavaPluginUtils.getCollectionSizeMax(prop));
	}

	@Test
	public void testGetCanBeEmpty() {
		HornetModelJava hmodel = HornetModelJava.initModel();
		Class class_ = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		Property prop = TestUtils.createAttribute(class_, "att", hmodel.stringPT, 0, 1);
		prop.applyStereotype(hmodel.stringTyped);
		TestUtils.setStereotypePropertyValue(prop, hmodel.stringTyped, hmodel.stringTypedCanBeEmpty, true);
		assertEquals(true, JavaPluginUtils.getCanBeEmpty(prop));
	}

	@Test
	public void testGetPattern() {
		HornetModelJava hmodel = HornetModelJava.initModel();
		Class class_ = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		Property prop = TestUtils.createAttribute(class_, "att", hmodel.stringPT, 0, 1);
		prop.applyStereotype(hmodel.stringTyped);
		TestUtils.setStereotypePropertyValue(prop, hmodel.stringTyped, hmodel.stringTypedPattern, "test");
		assertEquals("test", JavaPluginUtils.getPattern(prop));
	}

	@Test
	public void testGetSizeMin() {
		HornetModelJava hmodel = HornetModelJava.initModel();
		Class class_ = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		Property prop = TestUtils.createAttribute(class_, "att", hmodel.stringPT, 0, -1);
		prop.applyStereotype(hmodel.stringTyped);
		TestUtils.setStereotypePropertyValue(prop, hmodel.stringTyped, hmodel.stringTypedHasSizeMin, true);
		TestUtils.setStereotypePropertyValue(prop, hmodel.stringTyped, hmodel.stringTypedSizeMin, 2);
		assertEquals(2, JavaPluginUtils.getSizeMin(prop));
		
		TestUtils.setStereotypePropertyValue(prop, hmodel.stringTyped, hmodel.stringTypedHasSizeMin, false);
		assertEquals(null, JavaPluginUtils.getSizeMin(prop));
	}

	@Test
	public void testGetMin() {
		HornetModelJava hmodel = HornetModelJava.initModel();
		Class class_ = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		Property prop = TestUtils.createAttribute(class_, "att", hmodel.integerPT, 0, -1);
		prop.applyStereotype(hmodel.numericTyped);
		TestUtils.setStereotypePropertyValue(prop, hmodel.numericTyped, hmodel.numericTypedHasMin, true);
		TestUtils.setStereotypePropertyValue(prop, hmodel.numericTyped, hmodel.numericTypedMin, 2);
		assertEquals(2, JavaPluginUtils.getMin(prop));
		
		TestUtils.setStereotypePropertyValue(prop, hmodel.numericTyped, hmodel.numericTypedHasMin, false);
		assertEquals(null, JavaPluginUtils.getMin(prop));
	}

	@Test
	public void testGetMax() {
		HornetModelJava hmodel = HornetModelJava.initModel();
		Class class_ = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		Property prop = TestUtils.createAttribute(class_, "att", hmodel.integerPT, 0, -1);
		prop.applyStereotype(hmodel.numericTyped);
		TestUtils.setStereotypePropertyValue(prop, hmodel.numericTyped, hmodel.numericTypedHasMax, true);
		TestUtils.setStereotypePropertyValue(prop, hmodel.numericTyped, hmodel.numericTypedMax, 10);
		assertEquals(10, JavaPluginUtils.getMax(prop));
		
		TestUtils.setStereotypePropertyValue(prop, hmodel.numericTyped, hmodel.numericTypedHasMax, false);
		assertEquals(null, JavaPluginUtils.getMax(prop));
	}

	@Test
	public void testGetNegative() {
		HornetModelJava hmodel = HornetModelJava.initModel();
		Class class_ = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		Property prop = TestUtils.createAttribute(class_, "att", hmodel.integerPT, 0, -1);
		prop.applyStereotype(hmodel.numericTyped);
		TestUtils.setStereotypePropertyValue(prop, hmodel.numericTyped, hmodel.numericTypedNegative, true);
		assertEquals(true, JavaPluginUtils.getNegative(prop));
	}

	@Test
	public void testGetNegativeOrZero() {
		HornetModelJava hmodel = HornetModelJava.initModel();
		Class class_ = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		Property prop = TestUtils.createAttribute(class_, "att", hmodel.integerPT, 0, -1);
		prop.applyStereotype(hmodel.numericTyped);
		TestUtils.setStereotypePropertyValue(prop, hmodel.numericTyped, hmodel.numericTypedNegativeOrZero, true);
		assertEquals(true, JavaPluginUtils.getNegativeOrZero(prop));
	}

	@Test
	public void testGetPositive() {
		HornetModelJava hmodel = HornetModelJava.initModel();
		Class class_ = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		Property prop = TestUtils.createAttribute(class_, "att", hmodel.integerPT, 0, -1);
		prop.applyStereotype(hmodel.numericTyped);
		TestUtils.setStereotypePropertyValue(prop, hmodel.numericTyped, hmodel.numericTypedPositive, true);
		assertEquals(true, JavaPluginUtils.getPositive(prop));
	}

	@Test
	public void testGetPositiveOrZero() {
		HornetModelJava hmodel = HornetModelJava.initModel();
		Class class_ = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		Property prop = TestUtils.createAttribute(class_, "att", hmodel.integerPT, 0, -1);
		prop.applyStereotype(hmodel.numericTyped);
		TestUtils.setStereotypePropertyValue(prop, hmodel.numericTyped, hmodel.numericTypedPositiveOrZero, true);
		assertEquals(true, JavaPluginUtils.getPositiveOrZero(prop));
	}

	@Test
	public void testGetDigits() {
		HornetModelJava hmodel = HornetModelJava.initModel();
		Class class_ = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		Property prop = TestUtils.createAttribute(class_, "att", hmodel.integerPT, 0, -1);
		prop.applyStereotype(hmodel.numericTyped);
		TestUtils.setStereotypePropertyValue(prop, hmodel.numericTyped, hmodel.numericTypedDigits, true);
		assertEquals(true, JavaPluginUtils.getDigits(prop));
	}

	@Test
	public void testGetDigitsFraction() {
		HornetModelJava hmodel = HornetModelJava.initModel();
		Class class_ = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		Property prop = TestUtils.createAttribute(class_, "att", hmodel.integerPT, 0, -1);
		prop.applyStereotype(hmodel.numericTyped);
		TestUtils.setStereotypePropertyValue(prop, hmodel.numericTyped, hmodel.numericTypedDigitsFraction, 10);
		assertEquals(10, JavaPluginUtils.getDigitsFraction(prop));
	}

	@Test
	public void testGetDigitsInteger() {
		HornetModelJava hmodel = HornetModelJava.initModel();
		Class class_ = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		Property prop = TestUtils.createAttribute(class_, "att", hmodel.integerPT, 0, -1);
		prop.applyStereotype(hmodel.numericTyped);
		TestUtils.setStereotypePropertyValue(prop, hmodel.numericTyped, hmodel.numericTypedDigitsInteger, 10);
		assertEquals(10, JavaPluginUtils.getDigitsInteger(prop));
	}

	@Test
	public void testGetDecimalMin() {
		HornetModelJava hmodel = HornetModelJava.initModel();
		Class class_ = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		Property prop = TestUtils.createAttribute(class_, "att", hmodel.integerPT, 0, -1);
		prop.applyStereotype(hmodel.numericTyped);
		TestUtils.setStereotypePropertyValue(prop, hmodel.numericTyped, hmodel.numericTypedHasDecimalMin, true);
		TestUtils.setStereotypePropertyValue(prop, hmodel.numericTyped, hmodel.numericTypedDecimalMin, 10);
		assertEquals(10, JavaPluginUtils.getDecimalMin(prop));
		
		TestUtils.setStereotypePropertyValue(prop, hmodel.numericTyped, hmodel.numericTypedHasDecimalMin, false);
		assertEquals(null, JavaPluginUtils.getDecimalMin(prop));
	}

	@Test
	public void testGetDecimalMax() {
		HornetModelJava hmodel = HornetModelJava.initModel();
		Class class_ = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		Property prop = TestUtils.createAttribute(class_, "att", hmodel.integerPT, 0, -1);
		prop.applyStereotype(hmodel.numericTyped);
		TestUtils.setStereotypePropertyValue(prop, hmodel.numericTyped, hmodel.numericTypedHasDecimalMax, true);
		TestUtils.setStereotypePropertyValue(prop, hmodel.numericTyped, hmodel.numericTypedDecimalMax, 10);
		assertEquals(10, JavaPluginUtils.getDecimalMax(prop));
		
		TestUtils.setStereotypePropertyValue(prop, hmodel.numericTyped, hmodel.numericTypedHasDecimalMax, false);
		assertEquals(null, JavaPluginUtils.getDecimalMax(prop));
	}

}
