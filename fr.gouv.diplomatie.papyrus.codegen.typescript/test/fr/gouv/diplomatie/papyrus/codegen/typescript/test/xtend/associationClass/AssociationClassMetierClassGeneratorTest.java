package fr.gouv.diplomatie.papyrus.codegen.typescript.test.xtend.associationClass;

import static org.junit.Assert.*;

import java.util.ArrayList;

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
import fr.gouv.diplomatie.papyrus.codegen.core.utils.Utils;
import fr.gouv.diplomatie.papyrus.codegen.typescript.xtend.associationClass.AssociationClassDtoClassGenerator;
import fr.gouv.diplomatie.papyrus.codegen.typescript.xtend.associationClass.AssociationClassMetierClassGenerator;

public class AssociationClassMetierClassGeneratorTest {

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
		
		String path = GeneratorUtils.getMetierClassPath(class_, true);
		String path2 = GeneratorUtils.getMetierClassPath(class2_, true);
		
		String expect = "import { maClasse2Metier } from \"" + path2 + "\";\nimport { maClasseMetier } from \"" + path + "\";\n";
		assertEquals(expect, AssociationClassMetierClassGenerator.generateImports(class3_).toString());	
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
		
		AssociationClass class3_ = TestUtils.createAssociationClass(class_, class2_, "test", "test2", "asso");	
		
		ArrayList<Type> empty = new ArrayList<Type>();
		ArrayList<Type> expect = new ArrayList<Type>();
		expect.add(class2_);
		expect.add(class_);
		assertEquals(expect, AssociationClassMetierClassGenerator.generateAttributesImports(class3_, empty, class3_));	
	}

	@Test
	public void testGenerateValueObjectAttribute() {
		Profile profile = TestUtils.createProfile("profile");
		Model model = TestUtils.createModel("model");
		
		Stereotype stereotype = TestUtils.createStereotype(profile, Utils.MODEL_VALUEOBJECT, false);
		Class metaclass = TestUtils.referenceMetaclass(profile, UMLPackage.Literals.CLASS.getName());
        TestUtils.createExtension(metaclass, stereotype, false);
        
		Type stringPT = TestUtils.importPrimitiveType(profile, "String");
		
		profile.define();
		model.applyProfile(profile);
								
		Class class_ = TestUtils.createClass(model, "maClasse", false);
		Class class2_ = TestUtils.createClass(model, "maClasse2", false);
		class2_.applyStereotype(stereotype);
				
		Property att = TestUtils.createAttribute(class_, "att", class2_, 0, 1);
		Property att2 = TestUtils.createAttribute(class2_, "att2", stringPT, 0, 1);
				
		String expect = "\n@Map(maClasse2Metier)\n" + 
				"att: maClasse2Metier;\n";
		assertEquals(expect, AssociationClassMetierClassGenerator.generateValueObjectAttribute(att, "").toString());
		
		expect = "\n@Map(maClasse2Metier)\n" + 
				"testAtt: maClasse2Metier;\n";
		assertEquals(expect, AssociationClassMetierClassGenerator.generateValueObjectAttribute(att, "test").toString());
	}

	@Test
	public void testGenerateEnumAttribute() {
		Profile profile = TestUtils.createProfile("profile");
		Model model = TestUtils.createModel("model");
		
		Stereotype stereotype = TestUtils.createStereotype(profile, Utils.MODEL_NOMENCLATURE, false);
		Class metaclass = TestUtils.referenceMetaclass(profile, UMLPackage.Literals.CLASS.getName());
        TestUtils.createExtension(metaclass, stereotype, false);
		
		profile.define();
		model.applyProfile(profile);
								
		Class class_ = TestUtils.createClass(model, "maClasse", false);
		Class class2_ = TestUtils.createClass(model, "maClasse2", false);
		class2_.applyStereotype(stereotype);
				
		Property att = TestUtils.createAttribute(class_, "att", class2_, 0, 1);
				
		String expect = "\n@Map()\n" + 
				"att: MaClasse2;\n";
		assertEquals(expect, AssociationClassMetierClassGenerator.generateEnumAttribute(att, "").toString());
		
		expect = "\n@Map()\n" + 
				"testAtt: MaClasse2;\n";
		assertEquals(expect, AssociationClassMetierClassGenerator.generateEnumAttribute(att, "test").toString());
	}

	@Test
	public void testGenerateEntityAttribute() {
		Profile profile = TestUtils.createProfile("profile");
		Model model = TestUtils.createModel("model");
		
		Stereotype stereotype = TestUtils.createStereotype(profile, Utils.MODEL_ENTITY, false);
		Class metaclass = TestUtils.referenceMetaclass(profile, UMLPackage.Literals.CLASS.getName());
        TestUtils.createExtension(metaclass, stereotype, false);
		
		profile.define();
		model.applyProfile(profile);
								
		Class class_ = TestUtils.createClass(model, "maClasse", false);
		Class class2_ = TestUtils.createClass(model, "maClasse2", false);
		class2_.applyStereotype(stereotype);
				
		Property att = TestUtils.createAttribute(class_, "att", class2_, 0, 1);
				
		String expect = "\n@Map(maClasse2Metier)\n" + 
				"att: maClasse2Metier;\n";
		assertEquals(expect, AssociationClassMetierClassGenerator.generateEntityAttribute(att, "").toString());
		
		expect = "\n@Map(maClasse2Metier)\n" + 
				"testAtt: maClasse2Metier;\n";
		assertEquals(expect, AssociationClassMetierClassGenerator.generateEntityAttribute(att, "test").toString());
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
								
		Class class_ = TestUtils.createClass(model, "maClasse", false);
		Class class2_ = TestUtils.createClass(model, "maClasse2", false);
		class2_.applyStereotype(stereotype);
		
		Type stringPT = TestUtils.importPrimitiveType(profile, "String");
		
		Property att = TestUtils.createAttribute(class_, "att", stringPT, 0, 1);
				
		String expect = "\n@Map()\n" + 
				"att: string;\n";
		assertEquals(expect, AssociationClassMetierClassGenerator.generateBasicAttribute(att, "").toString());
		
		expect = "\n@Map()\n" + 
				"testAtt: string;\n";
		assertEquals(expect, AssociationClassMetierClassGenerator.generateBasicAttribute(att, "test").toString());
	}

	@Test
	public void testGenerateRefAttribute() {
		Profile profile = TestUtils.createProfile("profile");
		Model model = TestUtils.createModel("model");
		
		Stereotype stereotype = TestUtils.createStereotype(profile, Utils.MODEL_ENTITY, false);
		Class metaclass = TestUtils.referenceMetaclass(profile, UMLPackage.Literals.CLASS.getName());
        TestUtils.createExtension(metaclass, stereotype, false);
		
		profile.define();
		model.applyProfile(profile);
								
		Class class_ = TestUtils.createClass(model, "maClasse", false);
		Class class2_ = TestUtils.createClass(model, "maClasse2", false);
		class2_.applyStereotype(stereotype);
		
		Property att = TestUtils.createAttribute(class_, "att", class2_, 0, 1);
				
		String expect = "\n@Map(maClasse2Metier)\n" + 
				"att: maClasse2Metier;\n";
		assertEquals(expect, AssociationClassMetierClassGenerator.generateRefAttribute(att, "").toString());
		
		expect = "\n@Map(maClasse2Metier)\n" + 
				"testAtt: maClasse2Metier;\n";
		assertEquals(expect, AssociationClassMetierClassGenerator.generateRefAttribute(att, "test").toString());
	}

}
