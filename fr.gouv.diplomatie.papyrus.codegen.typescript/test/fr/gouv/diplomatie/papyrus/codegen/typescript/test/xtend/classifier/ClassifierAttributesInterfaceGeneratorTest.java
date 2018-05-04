package fr.gouv.diplomatie.papyrus.codegen.typescript.test.xtend.classifier;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.eclipse.uml2.uml.AggregationKind;
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
import fr.gouv.diplomatie.papyrus.codegen.core.test.TestUtils;
import fr.gouv.diplomatie.papyrus.codegen.core.utils.ClassifierUtils;
import fr.gouv.diplomatie.papyrus.codegen.core.utils.Utils;
import fr.gouv.diplomatie.papyrus.codegen.typescript.xtend.classifier.ClassifierAttributesInterfaceGenerator;

public class ClassifierAttributesInterfaceGeneratorTest {

	@Test
	public void testGenerateManyToManyAttribute() {
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
		
		Property prop = TestUtils.createAttribute(class_, "test", class2_, 0, -1);
		
		String expect = "test: Array<maClasse2Attributes>;\n" + 
				"getTest(): Promise<Array<maClasse2Attributes>>;\n";
		
		assertEquals(expect, ClassifierAttributesInterfaceGenerator.generateManyToManyAttribute(prop, class_).toString());		
	}

	@Test
	public void testGenerateOneToManyAttribute() {
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
		
		class_.createAssociation(true, AggregationKind.NONE_LITERAL	, "test", 0, 1, class2_, true, AggregationKind.NONE_LITERAL, "test2", 0, -1);
		Property test = class_.getAttribute("test", class2_);
		String expect = "maClasseTest: maClasseAttributes;\n" + 
				"getMaClasseTest(): Promise<maClasseAttributes>;\n";
		
		assertEquals(expect, ClassifierAttributesInterfaceGenerator.generateOneToManyAttribute(test, class_).toString());	
	}

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
		
		TestUtils.createAttribute(class_, "test", class2_, 0, 1);
		
		String path2 = GeneratorUtils.getAttributesInterfacePath(class2_, true);
		
		String expect = "import { maClasse2Attributes } from \"" + path2 + "\";\n";
		assertEquals(expect, ClassifierAttributesInterfaceGenerator.generateImports(class_).toString());		
	}

	@Test
	public void testGenerateExtendsImports() {
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
		
		TestUtils.createGeneralization(class_, class2_);
		
		String path = ClassifierUtils.getAttributesInterfacePath(class2_);
		String expect = "import { maClasse2Attributes } from \"" + path +"\";\n";
		
		assertEquals(expect, ClassifierAttributesInterfaceGenerator.generateExtendsImports(class_).toString());
	}

	@Test
	public void testGenerateAttributesImports() {
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
		
		TestUtils.createAttribute(class_, "test", class2_, 0, 1);
				
		ArrayList<Type> expect = new ArrayList<Type>();
		expect.add(class2_);
		
		ArrayList<Type> list = new ArrayList<Type>();
		assertEquals(expect, ClassifierAttributesInterfaceGenerator.generateAttributesImports(class_, list));		
	}

	@Test
	public void testGenerateExtends() {
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
		
		TestUtils.createGeneralization(class_, class2_);
		String expect = "extends maClasse2Attributes ";
		
		assertEquals(expect, ClassifierAttributesInterfaceGenerator.generateExtends(class_).toString());
	}

	@Test
	public void testGenerateExtendsAttributes() {
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
		
		TestUtils.createGeneralization(class_, class2_);
		
		String expect = "maClasse2?: maClasse2Attributes;\n";
		
		assertEquals(expect, ClassifierAttributesInterfaceGenerator.generateExtendsAttributes(class_).toString());
	}

	@Test
	public void testGenerateAttributes() {
		Profile profile = TestUtils.createProfile("profile");
		Model model = TestUtils.createModel("model");
		
		Stereotype stereotype = TestUtils.createStereotype(profile, Utils.MODEL_ENTITY, false);
		Class metaclass = TestUtils.referenceMetaclass(profile, UMLPackage.Literals.CLASS.getName());
        TestUtils.createExtension(metaclass, stereotype, false);
        
		profile.define();
		model.applyProfile(profile);
						
		Type stringPT = TestUtils.importPrimitiveType(profile, "String");
		
		Class class_ = TestUtils.createClass(model, "maClasse", false);
		class_.applyStereotype(stereotype);
		TestUtils.createAttribute(class_, "test", stringPT, 0, 1);
		TestUtils.createAttribute(class_, "test2", stringPT, 0, -1);
		
		String expect = "test?: string;\n";
		assertEquals(expect, ClassifierAttributesInterfaceGenerator.generateAttributes(class_, "").toString());

	}

	@Test
	public void testGenerateBasicAttribute() {
		Profile profile = TestUtils.createProfile("profile");
		Model model = TestUtils.createModel("model");
		
		Stereotype stereotype = TestUtils.createStereotype(profile, Utils.MODEL_ENTITY, false);
		Class metaclass = TestUtils.referenceMetaclass(profile, UMLPackage.Literals.CLASS.getName());
        TestUtils.createExtension(metaclass, stereotype, false);
		
		profile.define();
		model.applyProfile(profile);
		
		Type stringPT = TestUtils.importPrimitiveType(profile, "String");
				
		Class class_ = TestUtils.createClass(model, "maClasse", false);
		class_.applyStereotype(stereotype);
		Class class2_ = TestUtils.createClass(model, "maClasse2", false);
		class2_.applyStereotype(stereotype);
		
		Property prop = TestUtils.createAttribute(class_, "test", stringPT, 0, 1);
		
		assertEquals("test?: string;\n", ClassifierAttributesInterfaceGenerator.generateBasicAttribute(prop, "").toString());
		assertEquals("addTest?: string;\n", ClassifierAttributesInterfaceGenerator.generateBasicAttribute(prop, "add").toString());
	}

	@Test
	public void testGenerateValueObjectAttribute() {
		Profile profile = TestUtils.createProfile("profile");
		Model model = TestUtils.createModel("model");
		
		Stereotype stereotype = TestUtils.createStereotype(profile, Utils.MODEL_VALUEOBJECT, false);
		Class metaclass = TestUtils.referenceMetaclass(profile, UMLPackage.Literals.CLASS.getName());
        TestUtils.createExtension(metaclass, stereotype, false);
		
		profile.define();
		model.applyProfile(profile);
		
		Type stringPT = TestUtils.importPrimitiveType(profile, "String");
				
		Class class_ = TestUtils.createClass(model, "maClasse", false);
		Class class2_ = TestUtils.createClass(model, "maClasse", false);
		class2_.applyStereotype(stereotype);
		Property vo = TestUtils.createAttribute(class_, "vo", class2_, 0, 1);
		TestUtils.createAttribute(class2_, "test", stringPT, 0, 1);
		TestUtils.createAttribute(class2_, "test2", stringPT, 0, 1);
		
		String expect = "voTest?: string;\nvoTest2?: string;\n";
		assertEquals(expect, ClassifierAttributesInterfaceGenerator.generateValueObjectAttribute(vo, "").toString());
	}

	@Test
	public void testGenerateEnum() {
		Profile profile = TestUtils.createProfile("profile");
		Model model = TestUtils.createModel("model");
		
		Stereotype stereotype = TestUtils.createStereotype(profile, Utils.MODEL_ENTITY, false);
		Class metaclass = TestUtils.referenceMetaclass(profile, UMLPackage.Literals.CLASS.getName());
        TestUtils.createExtension(metaclass, stereotype, false);
        
		Stereotype stereotype2 = TestUtils.createStereotype(profile, Utils.MODEL_NOMENCLATURE, false);
        TestUtils.createExtension(metaclass, stereotype2, false);
		
		profile.define();
		model.applyProfile(profile);
						
		Class class_ = TestUtils.createClass(model, "maClasse", false);
		class_.applyStereotype(stereotype);
		Class class2_ = TestUtils.createClass(model, "maClasse2", false);
		class2_.applyStereotype(stereotype2);
		
		Property prop = TestUtils.createAttribute(class_, "test", class2_, 0, 1);
		
		assertEquals("codeTest?: number;\n" + 
				"test?: maClasse2Attributes;\n", ClassifierAttributesInterfaceGenerator.generateEnum(prop, "").toString());
	}

	@Test
	public void testGenerateEntityAttributes() {
		Profile profile = TestUtils.createProfile("profile");
		Model model = TestUtils.createModel("model");
		
		Stereotype stereotype = TestUtils.createStereotype(profile, Utils.MODEL_ENTITY, false);
		Class metaclass = TestUtils.referenceMetaclass(profile, UMLPackage.Literals.CLASS.getName());
        TestUtils.createExtension(metaclass, stereotype, false);
        
        Stereotype stereotype2 = TestUtils.createStereotype(profile, Utils.MODEL_KEYATTRIBUTE, false);
		Class propmetaclass = TestUtils.referenceMetaclass(profile, UMLPackage.Literals.PROPERTY.getName());
        TestUtils.createExtension(propmetaclass, stereotype2, false);
		
		profile.define();
		model.applyProfile(profile);
		
		Type stringPT = TestUtils.importPrimitiveType(profile, "String");
						
		Class class_ = TestUtils.createClass(model, "maClasse", false);
		class_.applyStereotype(stereotype);
		Class class2_ = TestUtils.createClass(model, "maClasse", false);
		class2_.applyStereotype(stereotype);
		Property prop = TestUtils.createAttribute(class_, "test", class2_, 0, 1);
		Property prop2 = TestUtils.createAttribute(class2_, "test2", stringPT, 0, 1);
		prop2.applyStereotype(stereotype2);
		
		String expect = "test2Test?: string;\n";
		assertEquals(expect, ClassifierAttributesInterfaceGenerator.generateEntityAttribute(prop, prop2, "").toString());
		
	}

	@Test
	public void testGenerateEntityAttribute() {
		Profile profile = TestUtils.createProfile("profile");
		Model model = TestUtils.createModel("model");
		
		Stereotype stereotype = TestUtils.createStereotype(profile, Utils.MODEL_ENTITY, false);
		Class metaclass = TestUtils.referenceMetaclass(profile, UMLPackage.Literals.CLASS.getName());
        TestUtils.createExtension(metaclass, stereotype, false);
        
        Stereotype stereotype2 = TestUtils.createStereotype(profile, Utils.MODEL_KEYATTRIBUTE, false);
		Class propmetaclass = TestUtils.referenceMetaclass(profile, UMLPackage.Literals.PROPERTY.getName());
        TestUtils.createExtension(propmetaclass, stereotype2, false);
		
		profile.define();
		model.applyProfile(profile);
		
		Type stringPT = TestUtils.importPrimitiveType(profile, "String");
				
		Class class_ = TestUtils.createClass(model, "maClasse", false);
		class_.applyStereotype(stereotype);
		Class class2_ = TestUtils.createClass(model, "maClasse2", false);
		class2_.applyStereotype(stereotype);
		
		Property prop = TestUtils.createAttribute(class_, "test", stringPT, 0, 1);
		Property id = TestUtils.createAttribute(class_, "id", stringPT, 0, 1);
		id.applyStereotype(stereotype2);
		
		assertEquals("idTest?: string;\n", ClassifierAttributesInterfaceGenerator.generateEntityAttribute(prop, id, "").toString());
	}

	@Test
	public void testGenerateReferenceAttributeAssocation() {
		Profile profile = TestUtils.createProfile("profile");
		Model model = TestUtils.createModel("model");
		
		Stereotype stereotype = TestUtils.createStereotype(profile, Utils.MODEL_ENTITY, false);
		Class metaclass = TestUtils.referenceMetaclass(profile, UMLPackage.Literals.CLASS.getName());
        TestUtils.createExtension(metaclass, stereotype, false);
        
        Stereotype stereotype2 = TestUtils.createStereotype(profile, Utils.MODEL_KEYATTRIBUTE, false);
		Class propmetaclass = TestUtils.referenceMetaclass(profile, UMLPackage.Literals.PROPERTY.getName());
        TestUtils.createExtension(propmetaclass, stereotype2, false);
		
		profile.define();
		model.applyProfile(profile);
		
		Type stringPT = TestUtils.importPrimitiveType(profile, "String");
		
		Class class_ = TestUtils.createClass(model, "maClasse", false);
		class_.applyStereotype(stereotype);
		Class class2_ = TestUtils.createClass(model, "maClasse2", false);
		class2_.applyStereotype(stereotype);
		Property id = TestUtils.createAttribute(class2_, "id", stringPT, 0, 1);
		id.applyStereotype(stereotype2);
		
		class_.createAssociation(true, AggregationKind.NONE_LITERAL, "test", 0, 1, class2_, true, AggregationKind.NONE_LITERAL, "test2", 0, 1);
		Property test = class_.getAttribute("test", class2_);
		Property test2 = class2_.getAttribute("test2", class_);
		test.getAssociation().getOwnedEnds().add(test);
		test.getAssociation().getOwnedEnds().add(test2);
		
		String expect = "\nidTest?: string;\n" + 
				"test: maClasse2Attributes;\n" + 
				"getTest(): Promise<maClasse2Attributes>;\n";
		assertEquals(expect, ClassifierAttributesInterfaceGenerator.generateReferenceAttributeAssocation(test, id, class_, "").toString());

	}

	@Test
	public void testGenerateReferenceAttributesNA() {
		Profile profile = TestUtils.createProfile("profile");
		Model model = TestUtils.createModel("model");
		
		Stereotype stereotype = TestUtils.createStereotype(profile, Utils.MODEL_ENTITY, false);
		Class metaclass = TestUtils.referenceMetaclass(profile, UMLPackage.Literals.CLASS.getName());
        TestUtils.createExtension(metaclass, stereotype, false);
        
        Stereotype stereotype2 = TestUtils.createStereotype(profile, Utils.MODEL_KEYATTRIBUTE, false);
		Class propmetaclass = TestUtils.referenceMetaclass(profile, UMLPackage.Literals.PROPERTY.getName());
        TestUtils.createExtension(propmetaclass, stereotype2, false);
		
		profile.define();
		model.applyProfile(profile);
		
		Type stringPT = TestUtils.importPrimitiveType(profile, "String");
		
		Class class_ = TestUtils.createClass(model, "maClasse", false);
		class_.applyStereotype(stereotype);
		Class class2_ = TestUtils.createClass(model, "maClasse2", false);
		class2_.applyStereotype(stereotype);
		Property id = TestUtils.createAttribute(class_, "id", stringPT, 0, 1);
		id.applyStereotype(stereotype2);
		Property prop = TestUtils.createAttribute(class_, "test", class2_, 0, 1);
		
		String expect = "testId?: string;";
		assertEquals(expect, ClassifierAttributesInterfaceGenerator.generateReferenceAttributesNA(prop, class_, "").toString());
	}

	@Test
	public void testGenerateReferenceAttributeNA() {
		Profile profile = TestUtils.createProfile("profile");
		Model model = TestUtils.createModel("model");
		
		Stereotype stereotype = TestUtils.createStereotype(profile, Utils.MODEL_ENTITY, false);
		Class metaclass = TestUtils.referenceMetaclass(profile, UMLPackage.Literals.CLASS.getName());
        TestUtils.createExtension(metaclass, stereotype, false);
        
        Stereotype stereotype2 = TestUtils.createStereotype(profile, Utils.MODEL_KEYATTRIBUTE, false);
		Class propmetaclass = TestUtils.referenceMetaclass(profile, UMLPackage.Literals.PROPERTY.getName());
        TestUtils.createExtension(propmetaclass, stereotype2, false);
		
		profile.define();
		model.applyProfile(profile);
		
		Type stringPT = TestUtils.importPrimitiveType(profile, "String");
		
		Class class_ = TestUtils.createClass(model, "maClasse", false);
		class_.applyStereotype(stereotype);
		Class class2_ = TestUtils.createClass(model, "maClasse2", false);
		class2_.applyStereotype(stereotype);
		Property id = TestUtils.createAttribute(class_, "id", stringPT, 0, 1);
		id.applyStereotype(stereotype2);
		Property prop = TestUtils.createAttribute(class_, "test", class2_, 0, 1);
		
		String expect = "testId?: string;";
		assertEquals(expect, ClassifierAttributesInterfaceGenerator.generateReferenceAttributeNA(prop, id, class_, "").toString());
	}

	@Test
	public void testGenerateEntityIdsAttributes() {
		Profile profile = TestUtils.createProfile("profile");
		Model model = TestUtils.createModel("model");
		
		Stereotype stereotype = TestUtils.createStereotype(profile, Utils.MODEL_ENTITY, false);
		Class metaclass = TestUtils.referenceMetaclass(profile, UMLPackage.Literals.CLASS.getName());
        TestUtils.createExtension(metaclass, stereotype, false);
        
        Stereotype stereotype2 = TestUtils.createStereotype(profile, Utils.MODEL_KEYATTRIBUTE, false);
		Class propmetaclass = TestUtils.referenceMetaclass(profile, UMLPackage.Literals.PROPERTY.getName());
        TestUtils.createExtension(propmetaclass, stereotype2, false);
		
		profile.define();
		model.applyProfile(profile);
		
		Type stringPT = TestUtils.importPrimitiveType(profile, "String");
		
		Class class_ = TestUtils.createClass(model, "maClasse", false);
		class_.applyStereotype(stereotype);
		Class class2_ = TestUtils.createClass(model, "maClasse2", false);
		class2_.applyStereotype(stereotype);
		Property id = TestUtils.createAttribute(class2_, "id", stringPT, 0, 1);
		id.applyStereotype(stereotype2);
		Property prop = TestUtils.createAttribute(class_, "test", class2_, 0, 1);
		
		String expect = "testId: Array<string>;\n";
		assertEquals(expect, ClassifierAttributesInterfaceGenerator.generateEntityIdsAttributes(prop, "").toString());
	}

	@Test
	public void testGenerateEntityIdAttribute() {
		Profile profile = TestUtils.createProfile("profile");
		Model model = TestUtils.createModel("model");
		
		Stereotype stereotype = TestUtils.createStereotype(profile, Utils.MODEL_ENTITY, false);
		Class metaclass = TestUtils.referenceMetaclass(profile, UMLPackage.Literals.CLASS.getName());
        TestUtils.createExtension(metaclass, stereotype, false);
        
        Stereotype stereotype2 = TestUtils.createStereotype(profile, Utils.MODEL_KEYATTRIBUTE, false);
		Class propmetaclass = TestUtils.referenceMetaclass(profile, UMLPackage.Literals.PROPERTY.getName());
        TestUtils.createExtension(propmetaclass, stereotype2, false);
		
		profile.define();
		model.applyProfile(profile);
		
		Type stringPT = TestUtils.importPrimitiveType(profile, "String");
				
		Class class_ = TestUtils.createClass(model, "maClasse", false);
		class_.applyStereotype(stereotype);
		Class class2_ = TestUtils.createClass(model, "maClasse2", false);
		class2_.applyStereotype(stereotype);
		
		Property prop = TestUtils.createAttribute(class_, "test", stringPT, 0, 1);
		Property id = TestUtils.createAttribute(class_, "id", stringPT, 0, 1);
		id.applyStereotype(stereotype2);
		
		assertEquals("testId: Array<string>;\n", ClassifierAttributesInterfaceGenerator.generateEntityIdAttribute(prop, id, "").toString());
	}


	@Test
	public void testNomenclatureGenerateNotPrimitiveTypeAttribut() {
		Profile profile = TestUtils.createProfile("profile");
		Model model = TestUtils.createModel("model");
		
		Stereotype stereotype = TestUtils.createStereotype(profile, Utils.MODEL_ENTITY, false);
		Class metaclass = TestUtils.referenceMetaclass(profile, UMLPackage.Literals.CLASS.getName());
        TestUtils.createExtension(metaclass, stereotype, false);
        
        Stereotype stereotype2 = TestUtils.createStereotype(profile, Utils.MODEL_NOMENCLATURE, false);
        TestUtils.createExtension(metaclass, stereotype2, false);
		
		profile.define();
		model.applyProfile(profile);
		
		Type stringPT = TestUtils.importPrimitiveType(profile, "String");
				
		Class class_ = TestUtils.createClass(model, "maClasse", false);
		class_.applyStereotype(stereotype);
		Class class2_ = TestUtils.createClass(model, "maClasse2", false);
		class2_.applyStereotype(stereotype2);
		
		Property prop = TestUtils.createAttribute(class_, "test", class2_, 0, -1);
		TestUtils.createAttribute(class2_, "att", stringPT, 0, 1);
		
		String expect = "test: Array<maClasse2Attributes>;\n\n";
		assertEquals(expect, ClassifierAttributesInterfaceGenerator.generateNotPrimitiveTypeAttribut(prop, "").toString());
	}
	
	@Test
	public void testGenerateNotPrimitiveTypeAttribut() {
		Profile profile = TestUtils.createProfile("profile");
		Model model = TestUtils.createModel("model");
		
		Stereotype stereotype = TestUtils.createStereotype(profile, Utils.MODEL_ENTITY, false);
		Class metaclass = TestUtils.referenceMetaclass(profile, UMLPackage.Literals.CLASS.getName());
        TestUtils.createExtension(metaclass, stereotype, false);
		
		profile.define();
		model.applyProfile(profile);
		
		Type stringPT = TestUtils.importPrimitiveType(profile, "String");
				
		Class class_ = TestUtils.createClass(model, "maClasse", false);
		class_.applyStereotype(stereotype);
		Class class2_ = TestUtils.createClass(model, "maClasse2", false);
		
		Property prop = TestUtils.createAttribute(class_, "test", class2_, 0, -1);
		TestUtils.createAttribute(class2_, "att", stringPT, 0, 1);
		
		String expect = "test: Array<string>;\n\n";
		assertEquals(expect, ClassifierAttributesInterfaceGenerator.generateNotPrimitiveTypeAttribut(prop, "").toString());
	}

	@Test
	public void testMultivaluedGenerateNPTEntityAttribut() {
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
		
		Property prop = TestUtils.createAttribute(class_, "test", class2_, 0, -1);
		
		String expect = "test: Array<maClasse2Attributes>;\n" + 
				"getTest(): Promise<Array<maClasse2Attributes>>;\n" + 
				"\n";
		assertEquals(expect, ClassifierAttributesInterfaceGenerator.generateNPTEntityAttribut(prop, "").toString());
	}
	
	@Test
	public void testGenerateNPTEntityAttribut() {
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
		
		Property prop = TestUtils.createAttribute(class_, "test", class2_, 0, 1);
		
		String expect = "test: maClasse2Attributes;\n" + 
				"getTest(): Promise<maClasse2Attributes>;\n" + 
				"\n";
		assertEquals(expect, ClassifierAttributesInterfaceGenerator.generateNPTEntityAttribut(prop, "").toString());
	}

	@Test
	public void testGenerateNPTValueObjectAttribut() {
		Profile profile = TestUtils.createProfile("profile");
		Model model = TestUtils.createModel("model");
		
		Stereotype stereotype = TestUtils.createStereotype(profile, Utils.MODEL_VALUEOBJECT, false);
		Class metaclass = TestUtils.referenceMetaclass(profile, UMLPackage.Literals.CLASS.getName());
        TestUtils.createExtension(metaclass, stereotype, false);
		
		profile.define();
		model.applyProfile(profile);
						
		Class class_ = TestUtils.createClass(model, "maClasse", false);
		
		Class class2_ = TestUtils.createClass(model, "maClasse2", false);
		class2_.applyStereotype(stereotype);
		
		Property prop = TestUtils.createAttribute(class_, "test", class2_, 0, 1);
		
		String expect = "test: maClasse2Attributes;\n" + 
				"getTest(): maClasse2Attributes;\n" + 
				"\n";
		assertEquals(expect, ClassifierAttributesInterfaceGenerator.generateNPTValueObjectAttribut(prop, "").toString());
	}
	
	@Test
	public void testMultivaluedGenerateNPTValueObjectAttribut() {
		Profile profile = TestUtils.createProfile("profile");
		Model model = TestUtils.createModel("model");
		
		Stereotype stereotype = TestUtils.createStereotype(profile, Utils.MODEL_VALUEOBJECT, false);
		Class metaclass = TestUtils.referenceMetaclass(profile, UMLPackage.Literals.CLASS.getName());
        TestUtils.createExtension(metaclass, stereotype, false);
		
		profile.define();
		model.applyProfile(profile);
						
		Class class_ = TestUtils.createClass(model, "maClasse", false);
		
		Class class2_ = TestUtils.createClass(model, "maClasse2", false);
		class2_.applyStereotype(stereotype);
		
		Property prop = TestUtils.createAttribute(class_, "test", class2_, 0, -1);
		
		String expect = "test: Array<maClasse2Attributes>;\n" + 
				"getTest(): Array<maClasse2Attributes>;\n" + 
				"\n";
		assertEquals(expect, ClassifierAttributesInterfaceGenerator.generateNPTValueObjectAttribut(prop, "").toString());
	}

	@Test
	public void testGenerateAssociationClassAtributes() {
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
		
		AssociationClass asso = TestUtils.createAssociationClass(class_, class2_, "test", "test2", "asso");
		
		String expect="test : Array<assoAttributes>;\n" + 
				"getAsso(): Promise<Array<assoAttributes>>;\n" + 
				"\n";
		assertEquals(expect, ClassifierAttributesInterfaceGenerator.generateAssociationClassAtributes(asso, class_).toString());
	}

}
