package fr.gouv.diplomatie.papyrus.codegen.core;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.eclipse.uml2.uml.AggregationKind;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.Profile;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Stereotype;
import org.eclipse.uml2.uml.Type;
import org.eclipse.uml2.uml.UMLPackage;
import org.junit.Test;

import fr.gouv.diplomatie.papyrus.codegen.core.test.TestUtils;
import fr.gouv.diplomatie.papyrus.codegen.core.utils.PropertyUtils;
import fr.gouv.diplomatie.papyrus.codegen.core.utils.Utils;

public class PropertyUtilsTest {

	@Test
	public void testGetStereotype() {
		Profile profile = TestUtils.createProfile("profile");
		Model model = TestUtils.createModel("model");
		
		Stereotype stereotype = TestUtils.createStereotype(profile, Utils.MODEL_ATTRIBUTE, false);
		Class propertyMetaclass = TestUtils.referenceMetaclass(profile, UMLPackage.Literals.PROPERTY.getName());
        TestUtils.createExtension(propertyMetaclass, stereotype, false);
        
		profile.define();
		model.applyProfile(profile);
			
		Class class_ = TestUtils.createClass(model, "maClasse", false);
		Type booleanPT = TestUtils.importPrimitiveType(model, "Boolean");
		Property prop = TestUtils.createAttribute(class_, "test", booleanPT, 0, 1);
		
		prop.applyStereotype(stereotype);
		assertEquals(stereotype, PropertyUtils.getStereotype(prop, Utils.MODEL_ATTRIBUTE).iterator().next());
	}

	@Test
	public void testGetStereotypePropertyValue() {
		Profile profile = TestUtils.createProfile("profile");
		Model model = TestUtils.createModel("model");
		
		Stereotype stereotype = TestUtils.createStereotype(profile, Utils.MODEL_ATTRIBUTE, false);
		Class propertyMetaclass = TestUtils.referenceMetaclass(profile, UMLPackage.Literals.PROPERTY.getName());
        TestUtils.createExtension(propertyMetaclass, stereotype, false);
        
        Type booleanPT = TestUtils.importPrimitiveType(model, "Boolean");
		Property attribute = TestUtils.createAttribute(stereotype, Utils.MODEL_ATTRIBUTE_COLUMNNAME, booleanPT, 0, 1);
        
		profile.define();
		model.applyProfile(profile);
			
		Class class_ = TestUtils.createClass(model, "maClasse", false);
		
		Property prop = TestUtils.createAttribute(class_, "test", booleanPT, 0, 1);
		
		prop.applyStereotype(stereotype);
		
		TestUtils.setStereotypePropertyValue(prop, stereotype, attribute, true);
		assertEquals(true, PropertyUtils.getStereotypePropertyValue(prop, Utils.MODEL_ATTRIBUTE, Utils.MODEL_ATTRIBUTE_COLUMNNAME));
		
		TestUtils.setStereotypePropertyValue(prop, stereotype, attribute, false);
		assertEquals(false, PropertyUtils.getStereotypePropertyValue(prop, Utils.MODEL_ATTRIBUTE, Utils.MODEL_ATTRIBUTE_COLUMNNAME));
	}

	@Test
	public void testIsAttribut() {
		Profile profile = TestUtils.createProfile("profile");
		Model model = TestUtils.createModel("model");
		
		Stereotype stereotype = TestUtils.createStereotype(profile, Utils.MODEL_ATTRIBUTE, false);
		Class propertyMetaclass = TestUtils.referenceMetaclass(profile, UMLPackage.Literals.PROPERTY.getName());
        TestUtils.createExtension(propertyMetaclass, stereotype, false);
        
        Type booleanPT = TestUtils.importPrimitiveType(model, "Boolean");
        
		profile.define();
		model.applyProfile(profile);
			
		Class class_ = TestUtils.createClass(model, "maClasse", false);
		
		Property prop = TestUtils.createAttribute(class_, "test", booleanPT, 0, 1);
		
		assertEquals(false, PropertyUtils.isAttribut(prop));
		
		prop.applyStereotype(stereotype);
		assertEquals(true, PropertyUtils.isAttribut(prop));
	}

	@Test
	public void testIsCodeLibelleNomenclature() {
		Profile profile = TestUtils.createProfile("profile");
		Model model = TestUtils.createModel("model");
		
		Stereotype stereotype = TestUtils.createStereotype(profile, Utils.MODEL_CODELIBELLENOMENCLATURE, false);
		Class propertyMetaclass = TestUtils.referenceMetaclass(profile, UMLPackage.Literals.PROPERTY.getName());
        TestUtils.createExtension(propertyMetaclass, stereotype, false);
        
        Type booleanPT = TestUtils.importPrimitiveType(model, "Boolean");
        
		profile.define();
		model.applyProfile(profile);
			
		Class class_ = TestUtils.createClass(model, "maClasse", false);
		
		Property prop = TestUtils.createAttribute(class_, "test", booleanPT, 0, 1);
		
		assertEquals(false, PropertyUtils.isCodeLibelleNomenclature(prop));
		
		prop.applyStereotype(stereotype);
		assertEquals(true, PropertyUtils.isCodeLibelleNomenclature(prop));
	}

	@Test
	public void testIsID() {
		Profile profile = TestUtils.createProfile("profile");
		Model model = TestUtils.createModel("model");
		
		Stereotype stereotype = TestUtils.createStereotype(profile, Utils.MODEL_KEYATTRIBUTE, false);
		Class propertyMetaclass = TestUtils.referenceMetaclass(profile, UMLPackage.Literals.PROPERTY.getName());
        TestUtils.createExtension(propertyMetaclass, stereotype, false);
        
        Type booleanPT = TestUtils.importPrimitiveType(model, "Boolean");
        
		profile.define();
		model.applyProfile(profile);
			
		Class class_ = TestUtils.createClass(model, "maClasse", false);
		
		Property prop = TestUtils.createAttribute(class_, "test", booleanPT, 0, 1);
		
		assertEquals(false, PropertyUtils.isID(prop));
		
		prop.applyStereotype(stereotype);
		assertEquals(true, PropertyUtils.isID(prop));
	}

	@Test
	public void testGetOwnerName() {
		Profile profile = TestUtils.createProfile("profile");
		Model model = TestUtils.createModel("model");
		
        
        Type booleanPT = TestUtils.importPrimitiveType(model, "Boolean");
        
		profile.define();
		model.applyProfile(profile);
			
		Class class_ = TestUtils.createClass(model, "maClasse", false);
		
		Property prop = TestUtils.createAttribute(class_, "test", booleanPT, 0, 1);
		
		assertEquals("maClasse", PropertyUtils.getOwnerName(prop));
		
	}

	@Test
	public void testGetIdFieldName() {
		Profile profile = TestUtils.createProfile("profile");
		Model model = TestUtils.createModel("model");
		
		Stereotype stereotype = TestUtils.createStereotype(profile, Utils.MODEL_KEYATTRIBUTE, false);
		Class propertyMetaclass = TestUtils.referenceMetaclass(profile, UMLPackage.Literals.PROPERTY.getName());
        TestUtils.createExtension(propertyMetaclass, stereotype, false);
    
        Type stringPT = TestUtils.importPrimitiveType(model, "String"); 
        
		profile.define();
		model.applyProfile(profile);
			
		Class class_ = TestUtils.createClass(model, "maclasse", false);
		
		Property prop = TestUtils.createAttribute(class_, "test", stringPT, 0, 1);
		
		prop.applyStereotype(stereotype);
		
		assertEquals("test_maclasse", PropertyUtils.getIdFieldName(prop, ""));
		assertEquals("other_test_maclasse", PropertyUtils.getIdFieldName(prop, "other"));
		
	}

	@Test
	public void testIsBasicAttribute() {
		Profile profile = TestUtils.createProfile("profile");
		Model model = TestUtils.createModel("model");
		     
        Type booleanPT = TestUtils.importPrimitiveType(model, "Boolean");
        
		profile.define();
		model.applyProfile(profile);
			
		Class class_ = TestUtils.createClass(model, "maClasse", false);
		
		Property prop = TestUtils.createAttribute(class_, "test", booleanPT, 0, 1);
		assertEquals(true, PropertyUtils.isBasicAttribute(prop));
	}

	@Test
	public void testIsClassAttribute() {
		Profile profile = TestUtils.createProfile("profile");
		Model model = TestUtils.createModel("model");
               
		profile.define();
		model.applyProfile(profile);
			
		Class class_ = TestUtils.createClass(model, "maClasse", false);
		Class class2_ = TestUtils.createClass(model, "otherClasse", false);
		
		Property prop = TestUtils.createAttribute(class_, "test", class2_, 0, 1);
		assertEquals(true, PropertyUtils.isClassAttribute(prop));
	}

	@Test
	public void testIsAssociationAttribute() {
		Profile profile = TestUtils.createProfile("profile");
		Model model = TestUtils.createModel("model");
  
		profile.define();
		model.applyProfile(profile);
			
		Class class_ = TestUtils.createClass(model, "maClasse", false);
		Class class2_ = TestUtils.createClass(model, "maClasse2", false);
		
		class_.createAssociation(true, AggregationKind.NONE_LITERAL, "test", 0, -1, class2_, true, AggregationKind.NONE_LITERAL, "test2", 1, 1);
		Property prop = class_.getAttribute("test", class2_);
		
		assertEquals(true, PropertyUtils.isAssociationAttribute(prop));
	}

	@Test
	public void testGetValueObjectPropertyName() {
		Profile profile = TestUtils.createProfile("profile");
		Model model = TestUtils.createModel("model");
               
		profile.define();
		model.applyProfile(profile);
		Type stringPT = TestUtils.importPrimitiveType(model, "String"); 
		Class class_ = TestUtils.createClass(model, "maClasse", false);
		Class class2_ = TestUtils.createClass(model, "otherClasse", false);
		
		Property prop = TestUtils.createAttribute(class_, "test", class2_, 0, 1);
		Property prop2 = TestUtils.createAttribute(class2_, "test2", stringPT, 0, 1);
		assertEquals("testTest2", PropertyUtils.getValueObjectPropertyName(prop2, prop));
	}

	@Test
	public void testGetAssociationEntityIdName() {
		Profile profile = TestUtils.createProfile("profile");
		Model model = TestUtils.createModel("model");
		
		Stereotype stereotype = TestUtils.createStereotype(profile, Utils.MODEL_ENTITY, false);
		Class classMetaclass = TestUtils.referenceMetaclass(profile, UMLPackage.Literals.CLASS.getName());
        TestUtils.createExtension(classMetaclass, stereotype, false);
    
		
		Stereotype stereotype2 = TestUtils.createStereotype(profile, Utils.MODEL_KEYATTRIBUTE, false);
		Class propertyMetaclass = TestUtils.referenceMetaclass(profile, UMLPackage.Literals.PROPERTY.getName());
        TestUtils.createExtension(propertyMetaclass, stereotype2, false);
    
        Type stringPT = TestUtils.importPrimitiveType(model, "String"); 
        
		profile.define();
		model.applyProfile(profile);
			
		Class class_ = TestUtils.createClass(model, "maClasse", false);
		Class class2_ = TestUtils.createClass(model, "maClasse2", false);
		
		Property prop = TestUtils.createAttribute(class_, "test", class2_, 0, 1);
		prop.applyStereotype(stereotype2);
		Property prop2 = TestUtils.createAttribute(class2_, "test2", stringPT, 0, 1);
		prop2.applyStereotype(stereotype2);
		
		assertEquals("test2MaClasse2", PropertyUtils.getAssociationEntityIdName(prop));
	}

	@Test
	public void testGetMultivaluedPropertyModelName() {
		Profile profile = TestUtils.createProfile("profile");
		Model model = TestUtils.createModel("model");
		
		Type stringPT = TestUtils.importPrimitiveType(model, "String");
		
		profile.define();
		model.applyProfile(profile);
			
		Class class_ = TestUtils.createClass(model, "maClasse", false);
		
		Property prop = TestUtils.createAttribute(class_, "test", stringPT, 0, -1);
		assertEquals("MaClasseTestModel", PropertyUtils.getMultivaluedPropertyModelName(prop, class_).toString());
	}

	@Test
	public void testGetMultivaluedPropertyDtoName() {
		Profile profile = TestUtils.createProfile("profile");
		Model model = TestUtils.createModel("model");
		
		Type stringPT = TestUtils.importPrimitiveType(model, "String");
		
		profile.define();
		model.applyProfile(profile);
			
		Class class_ = TestUtils.createClass(model, "maClasse", false);
		
		Property prop = TestUtils.createAttribute(class_, "test", stringPT, 0, -1);
		assertEquals("MaClasseTestDTO", PropertyUtils.getMultivaluedPropertyDtoName(prop, class_).toString());
	}

	@Test
	public void testGetReferenceAttributeName() {
		Profile profile = TestUtils.createProfile("profile");
		Model model = TestUtils.createModel("model");
		
		Stereotype stereotype = TestUtils.createStereotype(profile, Utils.MODEL_ENTITY, false);
		Class classMetaclass = TestUtils.referenceMetaclass(profile, UMLPackage.Literals.CLASS.getName());
        TestUtils.createExtension(classMetaclass, stereotype, false);
    
		
		Stereotype stereotype2 = TestUtils.createStereotype(profile, Utils.MODEL_KEYATTRIBUTE, false);
		Class propertyMetaclass = TestUtils.referenceMetaclass(profile, UMLPackage.Literals.PROPERTY.getName());
        TestUtils.createExtension(propertyMetaclass, stereotype2, false);
    
        Type stringPT = TestUtils.importPrimitiveType(model, "String"); 
        
		profile.define();
		model.applyProfile(profile);
			
		Class class_ = TestUtils.createClass(model, "maClasse", false);
		Class class2_ = TestUtils.createClass(model, "maClasse2", false);
		
		Property prop = TestUtils.createAttribute(class_, "test", class2_, 0, 1);
		prop.applyStereotype(stereotype2);
		Property prop2 = TestUtils.createAttribute(class2_, "test2", stringPT, 0, 1);
		prop2.applyStereotype(stereotype2);
		
		assertEquals("test2Test", PropertyUtils.getReferenceAttributeName(prop, prop2, class_));
	}

	@Test
	public void testIsNullable() {
		Profile profile = TestUtils.createProfile("profile");
		Model model = TestUtils.createModel("model");
		
               
		profile.define();
		model.applyProfile(profile);
			
		Class class_ = TestUtils.createClass(model, "maClasse", false);
		Class class2_ = TestUtils.createClass(model, "otherClasse", false);
		
		Property prop = TestUtils.createAttribute(class_, "test", class2_, 0, 1);
		assertEquals(true, PropertyUtils.isNullable(prop));
		
		Property prop2 = TestUtils.createAttribute(class_, "test2", class2_, 1, 1);
		assertEquals(false, PropertyUtils.isNullable(prop2));
	}

	@Test
	public void testGetDefaultValue() {
		Profile profile = TestUtils.createProfile("profile");
		Model model = TestUtils.createModel("model");
		
               
		profile.define();
		model.applyProfile(profile);
			
		Class class_ = TestUtils.createClass(model, "maClasse", false);		
		Type booleanPT = TestUtils.importPrimitiveType(model, "Boolean");
		
		Property prop = TestUtils.createAttribute(class_, "test", booleanPT, 0, 1);
		prop.setDefault("true");
		assertEquals("true", PropertyUtils.getDefaultValue(prop));
	}

	@Test
	public void testGetDatabaseName() {
		Profile profile = TestUtils.createProfile("profile");
		Model model = TestUtils.createModel("model");
		
		Stereotype stereotype = TestUtils.createStereotype(profile, Utils.MODEL_ATTRIBUTE, false);
		Class propertyMetaclass = TestUtils.referenceMetaclass(profile, UMLPackage.Literals.PROPERTY.getName());
        TestUtils.createExtension(propertyMetaclass, stereotype, false);
        
        Type stringPT = TestUtils.importPrimitiveType(model, "String");
		Property attribute = TestUtils.createAttribute(stereotype, Utils.MODEL_ATTRIBUTE_COLUMNNAME, stringPT, 0, 1);
        
		profile.define();
		model.applyProfile(profile);
			
		Class class_ = TestUtils.createClass(model, "maClasse", false);
		
		Property prop = TestUtils.createAttribute(class_, "test", stringPT, 0, 1);
		
		prop.applyStereotype(stereotype);
		
		assertEquals("TEST", PropertyUtils.getDatabaseName(prop, prop.getName(), "" ));
		assertEquals("SECOND_TEST", PropertyUtils.getDatabaseName(prop, prop.getName(), "SECOND" ));
		
		TestUtils.setStereotypePropertyValue(prop, stereotype, attribute, "OTHER");
		
		assertEquals("OTHER", PropertyUtils.getDatabaseName(prop, prop.getName(), "" ));
		assertEquals("SECOND_OTHER", PropertyUtils.getDatabaseName(prop, prop.getName(), "SECOND" ));
	}

	@Test
	public void testGetName() {
		
		Profile profile = TestUtils.createProfile("profile");
		Model model = TestUtils.createModel("model");
		
		Stereotype stereotype = TestUtils.createStereotype(profile, Utils.MODEL_ATTRIBUTE, false);
		Class propertyMetaclass = TestUtils.referenceMetaclass(profile, UMLPackage.Literals.PROPERTY.getName());
        TestUtils.createExtension(propertyMetaclass, stereotype, false);
        
        Type stringPT = TestUtils.importPrimitiveType(model, "String");
		Property attribute = TestUtils.createAttribute(stereotype, Utils.MODEL_ATTRIBUTE_COLUMNNAME, stringPT, 0, 1);
        
		profile.define();
		model.applyProfile(profile);
			
		Class class_ = TestUtils.createClass(model, "maClasse", false);
		
		Property prop = TestUtils.createAttribute(class_, "test", stringPT, 0, 1);
		
		prop.applyStereotype(stereotype);
		
		assertEquals("test", PropertyUtils.getName(prop, prop.getName(), "" ));
		assertEquals("secondTest", PropertyUtils.getName(prop, prop.getName(), "second" ));
		
		TestUtils.setStereotypePropertyValue(prop, stereotype, attribute, "other");
		
		assertEquals("other", PropertyUtils.getName(prop, prop.getName(), "" ));
		assertEquals("secondOther", PropertyUtils.getName(prop, prop.getName(), "second" ));
	}

	@Test
	public void testIsOneToManyAttributes() {
		Profile profile = TestUtils.createProfile("profile");
		Model model = TestUtils.createModel("model");
		
		Stereotype stereotype = TestUtils.createStereotype(profile, Utils.MODEL_ATTRIBUTE, false);
		Class propertyMetaclass = TestUtils.referenceMetaclass(profile, UMLPackage.Literals.PROPERTY.getName());
		Stereotype stereotype2 = TestUtils.createStereotype(profile, Utils.MODEL_ENTITY, false);
		Class classMetaclass = TestUtils.referenceMetaclass(profile, UMLPackage.Literals.CLASS.getName());
		
		Type stringPT = TestUtils.importPrimitiveType(model, "String");
		
        TestUtils.createExtension(propertyMetaclass, stereotype, false);
        TestUtils.createExtension(classMetaclass, stereotype2, false);
        
        
		profile.define();
		model.applyProfile(profile);
			
		Class class_ = TestUtils.createClass(model, "maClasse", false);
		Class class2_ = TestUtils.createClass(model, "maClasse2", false);
		class_.applyStereotype(stereotype2);
		class2_.applyStereotype(stereotype2);
		
		Property prop = TestUtils.createAttribute(class_, "test", stringPT, 0, 1);
		assertEquals(false, PropertyUtils.isOneToManyAttributes(prop));
		class_.createAssociation(true, AggregationKind.NONE_LITERAL, "test", 0, -1, class2_, true, AggregationKind.NONE_LITERAL, "test2", 1, 1);
		Property prop2 = class_.getAttribute("test", class2_);
		assertEquals(true, PropertyUtils.isOneToManyAttributes(prop2));
	}

	@Test
	public void testGetColumnName() {
		Profile profile = TestUtils.createProfile("profile");
		Model model = TestUtils.createModel("model");
		
		Stereotype stereotype = TestUtils.createStereotype(profile, Utils.MODEL_ATTRIBUTE, false);
		Class propertyMetaclass = TestUtils.referenceMetaclass(profile, UMLPackage.Literals.PROPERTY.getName());
		
		Type stringPT = TestUtils.importPrimitiveType(model, "String");
		Property attribute = TestUtils.createAttribute(stereotype, Utils.MODEL_ATTRIBUTE_COLUMNNAME, stringPT, 0, 1);
		
        TestUtils.createExtension(propertyMetaclass, stereotype, false);
        
        
		profile.define();
		model.applyProfile(profile);
			
		Class class_ = TestUtils.createClass(model, "maClasse", false);
		
		Property prop = TestUtils.createAttribute(class_, "test", stringPT, 0, 1);
				
		prop.applyStereotype(stereotype);
		
		assertEquals(null, PropertyUtils.getColumnName(prop));
		
		TestUtils.setStereotypePropertyValue(prop, stereotype, attribute, "OK");
		assertEquals("OK", PropertyUtils.getColumnName(prop));
		
	}

}
