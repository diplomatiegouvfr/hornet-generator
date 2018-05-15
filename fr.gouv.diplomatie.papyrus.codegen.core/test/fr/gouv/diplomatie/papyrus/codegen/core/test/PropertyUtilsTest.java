package fr.gouv.diplomatie.papyrus.codegen.core.test;

import static org.junit.Assert.assertEquals;

import org.eclipse.uml2.uml.AggregationKind;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Type;
import org.junit.Test;

import fr.gouv.diplomatie.papyrus.codegen.core.test.HornetModel;
import fr.gouv.diplomatie.papyrus.codegen.core.test.TestUtils;
import fr.gouv.diplomatie.papyrus.codegen.core.utils.PropertyUtils;
import fr.gouv.diplomatie.papyrus.codegen.core.utils.Utils;

public class PropertyUtilsTest {

	@Test
	public void testClass() {
		PropertyUtils test = new PropertyUtils();
		assertEquals(PropertyUtils.class, test.getClass());
	}
	
	@Test
	public void testGetStereotype() {
		HornetModel hmodel = HornetModel.initModel();
			
		Class class_ = TestUtils.createClass(hmodel.model, "maClasse", false);
		Type booleanPT = TestUtils.importPrimitiveType(hmodel.model, "Boolean");
		Property prop = TestUtils.createAttribute(class_, "test", booleanPT, 0, 1);
		
		prop.applyStereotype(hmodel.attribute);
		assertEquals(hmodel.attribute, PropertyUtils.getStereotype(prop, Utils.MODEL_ATTRIBUTE).iterator().next());
	}

	@Test
	public void testGetStereotypePropertyValue() {
		HornetModel hmodel = HornetModel.initModel();
		
		Class class_ = TestUtils.createClass(hmodel.model, "maClasse", false);
		
		Property prop = TestUtils.createAttribute(class_, "test", hmodel.booleanPT, 0, 1);
		prop.applyStereotype(hmodel.attribute);
		
		TestUtils.setStereotypePropertyValue(prop, hmodel.attribute, hmodel.attributeColumnName, "test");
		assertEquals("test", PropertyUtils.getStereotypePropertyValue(prop, Utils.MODEL_ATTRIBUTE, Utils.MODEL_ATTRIBUTE_COLUMNNAME));
		
		TestUtils.setStereotypePropertyValue(prop, hmodel.attribute, hmodel.attributeColumnName, "add");
		assertEquals("add", PropertyUtils.getStereotypePropertyValue(prop, Utils.MODEL_ATTRIBUTE, Utils.MODEL_ATTRIBUTE_COLUMNNAME));
	}
	
	@Test
	public void testNotGetStereotypePropertyValue() {
		HornetModel hmodel = HornetModel.initModel();
		
		Class class_ = TestUtils.createClass(hmodel.model, "maClasse", false);
		
		Property prop = TestUtils.createAttribute(class_, "test", hmodel.booleanPT, 0, 1);
		
		assertEquals(null, PropertyUtils.getStereotypePropertyValue(prop, Utils.MODEL_ATTRIBUTE, Utils.MODEL_ATTRIBUTE_COLUMNNAME));
	}

	@Test
	public void testIsAttribut() {
		HornetModel hmodel = HornetModel.initModel();
			
		Class class_ = TestUtils.createClass(hmodel.model, "maClasse", false);
		Property prop = TestUtils.createAttribute(class_, "test", hmodel.booleanPT, 0, 1);
		
		assertEquals(false, PropertyUtils.isAttribut(prop));
		
		prop.applyStereotype(hmodel.attribute);
		assertEquals(true, PropertyUtils.isAttribut(prop));
	}

	@Test
	public void testIsCodeLibelleNomenclature() {
		HornetModel hmodel = HornetModel.initModel();
			
		Class class_ = TestUtils.createClass(hmodel.model, "maClasse", false);
		
		Property prop = TestUtils.createAttribute(class_, "test", hmodel.booleanPT, 0, 1);
		
		assertEquals(false, PropertyUtils.isCodeLibelleNomenclature(prop));
		
		prop.applyStereotype(hmodel.codeLibelleNomenclature);
		assertEquals(true, PropertyUtils.isCodeLibelleNomenclature(prop));
	}

	@Test
	public void testIsID() {
		HornetModel hmodel = HornetModel.initModel();
			
		Class class_ = TestUtils.createClass(hmodel.model, "maClasse", false);
		Property prop = TestUtils.createAttribute(class_, "test", hmodel.booleanPT, 0, 1);
		
		assertEquals(false, PropertyUtils.isID(prop));
		
		prop.applyStereotype(hmodel.keyAttribute);
		assertEquals(true, PropertyUtils.isID(prop));
	}

	@Test
	public void testGetOwnerName() {
		HornetModel hmodel = HornetModel.initModel();
			
		Class class_ = TestUtils.createClass(hmodel.model, "maClasse", false);
		
		Property prop = TestUtils.createAttribute(class_, "test", hmodel.booleanPT, 0, 1);
		
		assertEquals("maClasse", PropertyUtils.getOwnerName(prop));
		
	}

	@Test
	public void testGetIdFieldName() {
		HornetModel hmodel = HornetModel.initModel();
			
		Class class_ = TestUtils.createClass(hmodel.model, "maclasse", false);
		
		Property prop = TestUtils.createAttribute(class_, "test", hmodel.stringPT, 0, 1);
		
		prop.applyStereotype(hmodel.keyAttribute);
		
		assertEquals("test_maclasse", PropertyUtils.getIdFieldName(prop, ""));
		assertEquals("other_test_maclasse", PropertyUtils.getIdFieldName(prop, "other"));
		assertEquals("test_maclasse", PropertyUtils.getIdFieldName(prop, null));
	}

	@Test
	public void testIsBasicAttribute() {
		HornetModel hmodel = HornetModel.initModel();
			
		Class class_ = TestUtils.createClass(hmodel.model, "maClasse", false);
		
		Property prop = TestUtils.createAttribute(class_, "test", hmodel.booleanPT, 0, 1);
		assertEquals(true, PropertyUtils.isBasicAttribute(prop));
	}
	
	@Test
	public void testNotIsBasicAttribute() {
		HornetModel hmodel = HornetModel.initModel();
			
		Class class_ = TestUtils.createClass(hmodel.model, "maClasse", false);
		Class class2_ = TestUtils.createClass(hmodel.model, "maClasse2", false);
		
		Property prop = TestUtils.createAttribute(class_, "test", class2_, 0, 1);
		assertEquals(false, PropertyUtils.isBasicAttribute(prop));
	}

	@Test
	public void testIsClassAttribute() {
		HornetModel hmodel = HornetModel.initModel();
			
		Class class_ = TestUtils.createClass(hmodel.model, "maClasse", false);
		Class class2_ = TestUtils.createClass(hmodel.model, "otherClasse", false);
		
		Property prop = TestUtils.createAttribute(class_, "test", class2_, 0, 1);
		assertEquals(true, PropertyUtils.isClassAttribute(prop));
	}

	@Test
	public void testIsAssociationAttribute() {
		HornetModel hmodel = HornetModel.initModel();
			
		Class class_ = TestUtils.createClass(hmodel.model, "maClasse", false);
		Class class2_ = TestUtils.createClass(hmodel.model, "maClasse2", false);
		
		class_.createAssociation(true, AggregationKind.NONE_LITERAL, "test", 0, -1, class2_, true, AggregationKind.NONE_LITERAL, "test2", 1, 1);
		Property prop = class_.getAttribute("test", class2_);
		
		assertEquals(true, PropertyUtils.isAssociationAttribute(prop));
	}
	
	@Test
	public void testNotIsAssociationAttribute() {
		HornetModel hmodel = HornetModel.initModel();
			
		Class class_ = TestUtils.createClass(hmodel.model, "maClasse", false);		
		Property prop = TestUtils.createAttribute(class_, "test", hmodel.stringPT, 0, 1);
		
		assertEquals(false, PropertyUtils.isAssociationAttribute(prop));
	}

	@Test
	public void testGetValueObjectPropertyName() {
		HornetModel hmodel = HornetModel.initModel();
		
		Class class_ = TestUtils.createClass(hmodel.model, "maClasse", false);
		Class class2_ = TestUtils.createClass(hmodel.model, "otherClasse", false);
		
		Property prop = TestUtils.createAttribute(class_, "test", class2_, 0, 1);
		Property prop2 = TestUtils.createAttribute(class2_, "test2", hmodel.stringPT, 0, 1);
		assertEquals("testTest2", PropertyUtils.getValueObjectPropertyName(prop2, prop));
	}

	@Test
	public void testGetAssociationEntityIdName() {
		HornetModel hmodel = HornetModel.initModel();
			
		Class class_ = TestUtils.createClass(hmodel.model, "maClasse", false);
		Class class2_ = TestUtils.createClass(hmodel.model, "maClasse2", false);
		
		Property prop = TestUtils.createAttribute(class_, "test", class2_, 0, 1);
		prop.applyStereotype(hmodel.keyAttribute);
		Property prop2 = TestUtils.createAttribute(class2_, "test2", hmodel.stringPT, 0, 1);
		prop2.applyStereotype(hmodel.keyAttribute);
		
		assertEquals("test2MaClasse2", PropertyUtils.getAssociationEntityIdName(prop));
	}

	@Test
	public void testGetMultivaluedPropertyModelName() {
		HornetModel hmodel = HornetModel.initModel();
			
		Class class_ = TestUtils.createClass(hmodel.model, "maClasse", false);
		
		Property prop = TestUtils.createAttribute(class_, "test", hmodel.stringPT, 0, -1);
		assertEquals("MaClasseTestModel", PropertyUtils.getMultivaluedPropertyModelName(prop, class_).toString());
	}

	@Test
	public void testGetMultivaluedPropertyDtoName() {
		HornetModel hmodel = HornetModel.initModel();
			
		Class class_ = TestUtils.createClass(hmodel.model, "maClasse", false);
		
		Property prop = TestUtils.createAttribute(class_, "test", hmodel.stringPT, 0, -1);
		assertEquals("MaClasseTestDTO", PropertyUtils.getMultivaluedPropertyDtoName(prop, class_).toString());
	}

	@Test
	public void testGetReferenceAttributeName() {
		HornetModel hmodel = HornetModel.initModel();
			
		Class class_ = TestUtils.createClass(hmodel.model, "maClasse", false);
		Class class2_ = TestUtils.createClass(hmodel.model, "maClasse2", false);
		
		Property prop = TestUtils.createAttribute(class_, "test", class2_, 0, 1);
		prop.applyStereotype(hmodel.keyAttribute);
		Property prop2 = TestUtils.createAttribute(class2_, "test2", hmodel.stringPT, 0, 1);
		prop2.applyStereotype(hmodel.keyAttribute);
		
		assertEquals("test2Test", PropertyUtils.getReferenceAttributeName(prop, prop2, class_));
	}

	@Test
	public void testIsNullable() {
		HornetModel hmodel = HornetModel.initModel();
			
		Class class_ = TestUtils.createClass(hmodel.model, "maClasse", false);
		Class class2_ = TestUtils.createClass(hmodel.model, "otherClasse", false);
		
		Property prop = TestUtils.createAttribute(class_, "test", class2_, 0, 1);
		assertEquals(true, PropertyUtils.isNullable(prop));
		
		Property prop2 = TestUtils.createAttribute(class_, "test2", class2_, 1, 1);
		assertEquals(false, PropertyUtils.isNullable(prop2));
	}

	@Test
	public void testGetDefaultValue() {
		HornetModel hmodel = HornetModel.initModel();
			
		Class class_ = TestUtils.createClass(hmodel.model, "maClasse", false);		
		
		Property prop = TestUtils.createAttribute(class_, "test", hmodel.booleanPT, 0, 1);
		prop.setDefault("true");
		assertEquals("true", PropertyUtils.getDefaultValue(prop));
	}

	@Test
	public void testGetDatabaseName() {
		HornetModel hmodel = HornetModel.initModel();
			
		Class class_ = TestUtils.createClass(hmodel.model, "maClasse", false);
		
		Property prop = TestUtils.createAttribute(class_, "test", hmodel.stringPT, 0, 1);
		
		prop.applyStereotype(hmodel.attribute);
		
		assertEquals("TEST", PropertyUtils.getDatabaseName(prop, prop.getName(), "" ));
		assertEquals("SECOND_TEST", PropertyUtils.getDatabaseName(prop, prop.getName(), "SECOND" ));
		
		TestUtils.setStereotypePropertyValue(prop, hmodel.attribute, hmodel.attributeColumnName, "OTHER");
		
		assertEquals("OTHER", PropertyUtils.getDatabaseName(prop, prop.getName(), "" ));
		assertEquals("SECOND_OTHER", PropertyUtils.getDatabaseName(prop, prop.getName(), "SECOND" ));
		
		TestUtils.setStereotypePropertyValue(prop, hmodel.attribute, hmodel.attributeColumnName, "");
		
		assertEquals("TEST", PropertyUtils.getDatabaseName(prop, prop.getName(), "" ));
		assertEquals("TEST", PropertyUtils.getDatabaseName(prop, prop.getName(), null ));
		assertEquals("SECOND_TEST", PropertyUtils.getDatabaseName(prop, prop.getName(), "SECOND" ));
	}

	@Test
	public void testGetName() {
		HornetModel hmodel = HornetModel.initModel();
			
		Class class_ = TestUtils.createClass(hmodel.model, "maClasse", false);
		
		Property prop = TestUtils.createAttribute(class_, "test", hmodel.stringPT, 0, 1);
		
		prop.applyStereotype(hmodel.attribute);
		
		assertEquals("test", PropertyUtils.getName(prop, prop.getName(), "" ));
		assertEquals("secondTest", PropertyUtils.getName(prop, prop.getName(), "second" ));
		
		TestUtils.setStereotypePropertyValue(prop, hmodel.attribute, hmodel.attributeColumnName, "other");
		
		assertEquals("other", PropertyUtils.getName(prop, prop.getName(), "" ));
		assertEquals("secondOther", PropertyUtils.getName(prop, prop.getName(), "second" ));
		
		TestUtils.setStereotypePropertyValue(prop, hmodel.attribute, hmodel.attributeColumnName, "");
		
		assertEquals("test", PropertyUtils.getName(prop, prop.getName(), "" ));
		assertEquals("test", PropertyUtils.getName(prop, prop.getName(), null ));
		assertEquals("secondTest", PropertyUtils.getName(prop, prop.getName(), "second" ));
	}

	@Test
	public void testIsOneToManyAttributes() {
		HornetModel hmodel = HornetModel.initModel();
			
		Class class_ = TestUtils.createClass(hmodel.model, "maClasse", false);
		Class class2_ = TestUtils.createClass(hmodel.model, "maClasse2", false);
		class_.applyStereotype(hmodel.entity);
		class2_.applyStereotype(hmodel.entity);
		
		Property prop = TestUtils.createAttribute(class_, "test", hmodel.stringPT, 0, 1);
		assertEquals(false, PropertyUtils.isOneToManyAttributes(prop));
		class_.createAssociation(true, AggregationKind.NONE_LITERAL, "test", 0, -1, class2_, true, AggregationKind.NONE_LITERAL, "test2", 1, 1);
		Property prop2 = class_.getAttribute("test", class2_);
		assertEquals(true, PropertyUtils.isOneToManyAttributes(prop2));
	}
	
	@Test
	public void testNotIsOneToManyAttributes() {
		HornetModel hmodel = HornetModel.initModel();
			
		Class class_ = TestUtils.createClass(hmodel.model, "maClasse", false);
		Class class2_ = TestUtils.createClass(hmodel.model, "maClasse2", false);
		class_.applyStereotype(hmodel.entity);
		class2_.applyStereotype(hmodel.entity);
		
		Property prop = TestUtils.createAttribute(class_, "test", hmodel.stringPT, 0, -1);
		assertEquals(false, PropertyUtils.isOneToManyAttributes(prop));
		
		Property prope = TestUtils.createAttribute(class_, "teste", class2_, 0, -1);
		assertEquals(false, PropertyUtils.isOneToManyAttributes(prope));
		
		class_.createAssociation(true, AggregationKind.NONE_LITERAL, "test", 0, -1, class2_, true, AggregationKind.NONE_LITERAL, "test2", 1, -1);
		Property prop2 = class_.getAttribute("test", class2_);
		assertEquals(false, PropertyUtils.isOneToManyAttributes(prop2));
	}
	
	@Test
	public void testEntityIsOneToManyAttributes() {
		HornetModel hmodel = HornetModel.initModel();
			
		Class class_ = TestUtils.createClass(hmodel.model, "maClasse", false);
		Class class2_ = TestUtils.createClass(hmodel.model, "maClasse2", false);
		class_.applyStereotype(hmodel.entity);
		class2_.applyStereotype(hmodel.entity);
		
		Property prop = TestUtils.createAttribute(class_, "test", class2_, 0, 1);
		assertEquals(false, PropertyUtils.isOneToManyAttributes(prop));
	}

	@Test
	public void testGetColumnName() {
		HornetModel hmodel = HornetModel.initModel();
			
		Class class_ = TestUtils.createClass(hmodel.model, "maClasse", false);
		
		Property prop = TestUtils.createAttribute(class_, "test", hmodel.stringPT, 0, 1);
				
		prop.applyStereotype(hmodel.attribute);
		
		assertEquals(null, PropertyUtils.getColumnName(prop));
		
		TestUtils.setStereotypePropertyValue(prop, hmodel.attribute, hmodel.attributeColumnName, "OK");
		assertEquals("OK", PropertyUtils.getColumnName(prop));
		
	}
	
	@Test
	public void testKeyAttributeGetColumnName() {
		HornetModel hmodel = HornetModel.initModel();
			
		Class class_ = TestUtils.createClass(hmodel.model, "maClasse", false);
		
		Property prop = TestUtils.createAttribute(class_, "test", hmodel.stringPT, 0, 1);
				
		prop.applyStereotype(hmodel.keyAttribute);
		
		assertEquals(null, PropertyUtils.getColumnName(prop));
		
		TestUtils.setStereotypePropertyValue(prop, hmodel.keyAttribute, hmodel.attributeColumnName, "OK");
		assertEquals("OK", PropertyUtils.getColumnName(prop));
		
	}

}
