package fr.gouv.diplomatie.papyrus.codegen.typescript.test.xtend.associationClass;

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

import fr.gouv.diplomatie.papyrus.codegen.core.generators.GeneratorUtils;
import fr.gouv.diplomatie.papyrus.codegen.core.test.AssociationReturn;
import fr.gouv.diplomatie.papyrus.codegen.core.test.TestUtils;
import fr.gouv.diplomatie.papyrus.codegen.core.utils.Utils;
import fr.gouv.diplomatie.papyrus.codegen.typescript.utils.TypeUtils;
import fr.gouv.diplomatie.papyrus.codegen.typescript.xtend.associationClass.AssociationClassAttributesInterfaceGenerator;

public class AssociationClassAttributesInterfaceGeneratorTest {

	@Test
	public void testGenerateImports() {
		Profile profile = TestUtils.createProfile("profile");
		Model model = TestUtils.createModel("model");
		
		Stereotype stereotype = TestUtils.createStereotype(profile, Utils.MODEL_ENTITY, false);
		Class metaclass = TestUtils.referenceMetaclass(profile, UMLPackage.Literals.CLASS.getName());
        TestUtils.createExtension(metaclass, stereotype, false);
		
		profile.define();
		model.applyProfile(profile);
				
		Class class_ = TestUtils.createClass(model, "maClasse", false);
		class_.applyStereotype(stereotype);
		Class class2_ = TestUtils.createClass(model, "maClasse2", false);
		class2_.applyStereotype(stereotype);
		
		AssociationClass class3_ = TestUtils.createAssociationClass(class_, class2_, "test", "test2", "asso");	
		
		String path = GeneratorUtils.getAttributesInterfacePath(class_, true);
		String path2 = GeneratorUtils.getAttributesInterfacePath(class2_, true);
		
		String expect = "import { maClasse2Attributes } from \"" + path2 + "\";\nimport { maClasseAttributes } from \"" + path + "\";\n";
		assertEquals(expect, AssociationClassAttributesInterfaceGenerator.generateImports(class3_).toString());		
	}


	@Test
	public void testEntityGeneratePropertyAttributes() {
		Profile profile = TestUtils.createProfile("profile");
		Model model = TestUtils.createModel("model");
		
		Stereotype stereotype = TestUtils.createStereotype(profile, Utils.MODEL_ENTITY, false);
		Class metaclass = TestUtils.referenceMetaclass(profile, UMLPackage.Literals.CLASS.getName());
        TestUtils.createExtension(metaclass, stereotype, false);
		
		profile.define();
		model.applyProfile(profile);
				
		Class class_ = TestUtils.createClass(model, "maClasse", false);
		class_.applyStereotype(stereotype);
		Class class2_ = TestUtils.createClass(model, "maClasse2", false);
		class2_.applyStereotype(stereotype);
		
		AssociationReturn asso = TestUtils.createAssociationClassGetAll(class_, class2_, "test", "test2", "asso");	
		Property prop1 = asso.attributeClassUn;
		
		String expect = "\ntest: Array<maClasse2Attributes>;\n" + 
				"getTest(): Promise<Array<maClasse2Attributes>>;\n";
		assertEquals(expect, AssociationClassAttributesInterfaceGenerator.generatePropertyAttributes(prop1, "").toString());		
	}
	
	@Test
	public void testValueObjectGeneratePropertyAttributes() {
		Profile profile = TestUtils.createProfile("profile");
		Model model = TestUtils.createModel("model");
		
		Stereotype stereotype = TestUtils.createStereotype(profile, Utils.MODEL_VALUEOBJECT, false);
		Class metaclass = TestUtils.referenceMetaclass(profile, UMLPackage.Literals.CLASS.getName());
        TestUtils.createExtension(metaclass, stereotype, false);
		
		profile.define();
		
		model.applyProfile(profile);
		Type stringPT = TestUtils.importPrimitiveType(profile, "String");
		
		Class class_ = TestUtils.createClass(model, "maClasse", false);
		class_.applyStereotype(stereotype);
		Property one = TestUtils.createAttribute(class_, "attribute", stringPT, 0, 1);
		
		Class class2_ = TestUtils.createClass(model, "maClasse2", false);
		class2_.applyStereotype(stereotype);
		
		AssociationReturn asso = TestUtils.createAssociationClassGetAll(class_, class2_, "test", "test2", "asso");	
		Property prop1 = asso.attributeClassDeux;
		
		String expect = "test2Attribute?: string;\n";
		assertEquals(expect, AssociationClassAttributesInterfaceGenerator.generatePropertyAttributes(prop1, "").toString());	
		
		Property two = TestUtils.createAttribute(class_, "attribute2", stringPT, 0, 1);
		expect = "test2Attribute?: string;\ntest2Attribute2?: string;\n";
		assertEquals(expect, AssociationClassAttributesInterfaceGenerator.generatePropertyAttributes(prop1, "").toString());	
	}
	
	@Test
	public void testNomenclatureGeneratePropertyAttributes() {
		Profile profile = TestUtils.createProfile("profile");
		Model model = TestUtils.createModel("model");
		
		Stereotype stereotype = TestUtils.createStereotype(profile, Utils.MODEL_NOMENCLATURE, false);
		Class metaclass = TestUtils.referenceMetaclass(profile, UMLPackage.Literals.CLASS.getName());
        TestUtils.createExtension(metaclass, stereotype, false);
		
		profile.define();
		
		model.applyProfile(profile);
		Type stringPT = TestUtils.importPrimitiveType(profile, "String");
		
		Class class_ = TestUtils.createClass(model, "maClasse", false);
		class_.applyStereotype(stereotype);
		Property one = TestUtils.createAttribute(class_, "attribute", stringPT, 0, 1);
		
		Class class2_ = TestUtils.createClass(model, "maClasse2", false);
		class2_.applyStereotype(stereotype);
		
		AssociationReturn asso = TestUtils.createAssociationClassGetAll(class_, class2_, "test", "test2", "asso");	
		Property prop1 = asso.attributeClassDeux;
		
		String expect = "\ncodeTest2: number;\n" + 
				"test2: Array<maClasseAttributes>;\n" + 
				"getTest2(): Promise<Array<maClasseAttributes>>;\n";
		assertEquals(expect, AssociationClassAttributesInterfaceGenerator.generatePropertyAttributes(prop1, "").toString());	
		
	}

	@Test
	public void testGenerateBasicAttribute() {
		Profile profile = TestUtils.createProfile("profile");
		Model model = TestUtils.createModel("model");
		
		Stereotype stereotype = TestUtils.createStereotype(profile, Utils.MODEL_HORNETTYPE, false);
		Class metaclass = TestUtils.referenceMetaclass(profile, UMLPackage.Literals.CLASS.getName());
        TestUtils.createExtension(metaclass, stereotype, false);
		
		Type stringPT = TestUtils.importPrimitiveType(profile, "String");
		Property attribute = TestUtils.createAttribute(stereotype, TypeUtils.MODEL_HORNETTYPE_TYPESCRIPTTYPE, stringPT, 0, 1);
        
		profile.define();
		model.applyProfile(profile);
				
		Class class_ = TestUtils.createClass(model, "maClasse", false);
		Class class2_ = TestUtils.createClass(model, "maClasse2", false);
		class2_.applyStereotype(stereotype);
		
		Property prop = TestUtils.createAttribute(class_, "test", class2_, 0, 1);
		
		assertEquals("test?: string;\n", AssociationClassAttributesInterfaceGenerator.generateBasicAttribute(prop, "").toString());
		
		TestUtils.setStereotypePropertyValue(class2_, stereotype, attribute, "Test");
		assertEquals("test?: Test;\n", AssociationClassAttributesInterfaceGenerator.generateBasicAttribute(prop, "").toString());
		
		assertEquals("addTest?: Test;\n", AssociationClassAttributesInterfaceGenerator.generateBasicAttribute(prop, "add").toString());

	}

	@Test
	public void testGeneratePropertyIdAttribute() {
		Profile profile = TestUtils.createProfile("profile");
		Model model = TestUtils.createModel("model");
		
		Stereotype stereotype = TestUtils.createStereotype(profile, Utils.MODEL_HORNETTYPE, false);
		Class metaclass = TestUtils.referenceMetaclass(profile, UMLPackage.Literals.CLASS.getName());
        TestUtils.createExtension(metaclass, stereotype, false);
		
		Type stringPT = TestUtils.importPrimitiveType(profile, "String");
		Property attribute = TestUtils.createAttribute(stereotype, TypeUtils.MODEL_HORNETTYPE_TYPESCRIPTTYPE, stringPT, 0, 1);
        
		profile.define();
		model.applyProfile(profile);
				
		Class class_ = TestUtils.createClass(model, "maClasse", false);
		Class class2_ = TestUtils.createClass(model, "maClasse2", false);
		class2_.applyStereotype(stereotype);
		
		Property prop = TestUtils.createAttribute(class_, "test", stringPT, 0, 1);
		Property id = TestUtils.createAttribute(class_, "id", class2_, 0, 1);
		
		assertEquals("idTest: string;\n", AssociationClassAttributesInterfaceGenerator.generatePropertyIdAttribute(prop, id).toString());
		
		TestUtils.setStereotypePropertyValue(class2_, stereotype, attribute, "Test");
		assertEquals("idTest: Test;\n", AssociationClassAttributesInterfaceGenerator.generatePropertyIdAttribute(prop, id).toString());
		
	}

}
