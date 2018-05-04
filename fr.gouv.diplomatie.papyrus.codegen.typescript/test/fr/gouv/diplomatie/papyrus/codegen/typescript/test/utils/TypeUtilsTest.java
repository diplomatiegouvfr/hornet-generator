package fr.gouv.diplomatie.papyrus.codegen.typescript.test.utils;

import static org.junit.Assert.*;

import org.eclipse.uml2.uml.AssociationClass;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.Profile;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Stereotype;
import org.eclipse.uml2.uml.Type;
import org.eclipse.uml2.uml.UMLPackage;
import org.junit.Test;

import fr.gouv.diplomatie.papyrus.codegen.core.test.TestUtils;
import fr.gouv.diplomatie.papyrus.codegen.core.utils.Utils;
import fr.gouv.diplomatie.papyrus.codegen.typescript.utils.TypeUtils;

public class TypeUtilsTest {

	@Test
	public void testGetSequelizeType() {
		Profile profile = TestUtils.createProfile("profile");
		Model model = TestUtils.createModel("model");
		Stereotype stereotype = TestUtils.createStereotype(profile, Utils.MODEL_HORNETTYPE, false);
		
		Type stringPT = TestUtils.importPrimitiveType(model, "String");
		Property attribute = TestUtils.createAttribute(stereotype, TypeUtils.MODEL_HORNETTYPE_SEQUELIZETYPE, stringPT, 0, 1);
	
		Class packageMetaclass = TestUtils.referenceMetaclass(profile, UMLPackage.Literals.CLASS.getName());
        TestUtils.createExtension(packageMetaclass, stereotype, false);
        
		profile.define();
		model.applyProfile(profile);
		Type class_ = TestUtils.createClass(model, "maClasse", false);
		class_.applyStereotype(stereotype);
		
		assertEquals("STRING", TypeUtils.getSequelizeType(class_));
		
		TestUtils.setStereotypePropertyValue(class_, stereotype, attribute, "TEST");
		
		assertEquals("TEST", TypeUtils.getSequelizeType(class_));
	}

	@Test
	public void testGetTypescriptType() {
		Profile profile = TestUtils.createProfile("profile");
		Model model = TestUtils.createModel("model");
		Stereotype stereotype = TestUtils.createStereotype(profile, Utils.MODEL_HORNETTYPE, false);
		
		Type stringPT = TestUtils.importPrimitiveType(model, "String");
		Property attribute = TestUtils.createAttribute(stereotype, TypeUtils.MODEL_HORNETTYPE_TYPESCRIPTTYPE, stringPT, 0, 1);
	
		Class packageMetaclass = TestUtils.referenceMetaclass(profile, UMLPackage.Literals.CLASS.getName());
        TestUtils.createExtension(packageMetaclass, stereotype, false);
        
		profile.define();
		model.applyProfile(profile);
		Type class_ = TestUtils.createClass(model, "maClasse", false);
		class_.applyStereotype(stereotype);
		
		assertEquals("string", TypeUtils.getTypescriptType(class_));
		
		TestUtils.setStereotypePropertyValue(class_, stereotype, attribute, "Test");
		
		assertEquals("Test", TypeUtils.getTypescriptType(class_));
	}

	@Test
	public void testGetMetierTypescriptType() {
		Profile profile = TestUtils.createProfile("profile");
		Model model = TestUtils.createModel("model");
		Stereotype stereotype = TestUtils.createStereotype(profile, Utils.MODEL_HORNETTYPE, false);
		
		Type stringPT = TestUtils.importPrimitiveType(model, "String");
		Property attribute = TestUtils.createAttribute(stereotype, TypeUtils.MODEL_HORNETTYPE_METIERCLASSTYPE, stringPT, 0, 1);
	
		Class packageMetaclass = TestUtils.referenceMetaclass(profile, UMLPackage.Literals.CLASS.getName());
        TestUtils.createExtension(packageMetaclass, stereotype, false);
        
		profile.define();
		model.applyProfile(profile);
		Type class_ = TestUtils.createClass(model, "maClasse", false);
		class_.applyStereotype(stereotype);
		
		assertEquals("string", TypeUtils.getMetierTypescriptType(class_));
		
		TestUtils.setStereotypePropertyValue(class_, stereotype, attribute, "Test");
		
		assertEquals("Test", TypeUtils.getMetierTypescriptType(class_));
	}

}
