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

public class AssociationClassDtoClassGeneratorTest {

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
		
		String path = GeneratorUtils.getDtoClassPath(class_, true);
		String path2 = GeneratorUtils.getDtoClassPath(class2_, true);
		
		String expect = "import { maClasse2DTO } from \"" + path2 + "\";\nimport { maClasseDTO } from \"" + path + "\";\n";
		assertEquals(expect, AssociationClassDtoClassGenerator.generateImports(class3_).toString());	
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
		assertEquals(expect, AssociationClassDtoClassGenerator.generateAttributesImports(class3_, empty));	
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
		
		Type booleanPT = TestUtils.importPrimitiveType(model, "Boolean");
								
		Class class_ = TestUtils.createClass(model, "maClasse", false);
		Class class2_ = TestUtils.createClass(model, "maClasse2", false);
		class2_.applyStereotype(stereotype);
		
		Property att = TestUtils.createAttribute(class_, "att", class2_, 0, 1);
		
		Property att2 = TestUtils.createAttribute(class2_, "att2",booleanPT , 0, 1);
				
		String expect = "\n@Map()\n" + 
				"@Alias('attAtt2', 'att.att2')\n" + 
				"attAtt2: string;\n";
		ArrayList<String> list = new ArrayList<String>();
		assertEquals(expect, AssociationClassDtoClassGenerator.generateValueObjectAttribute(att, list).toString());	
		
		expect = "\n@Map()\n" + 
				"@Alias('testOtherAttAtt2', 'test.other.att.att2')\n" + 
				"testOtherAttAtt2: string;\n";
		ArrayList<String> list2= new ArrayList<String>();
		list2.add("test");
		list2.add("other");
		assertEquals(expect, AssociationClassDtoClassGenerator.generateValueObjectAttribute(att, list2).toString());
	}

	@Test
	public void testGenerateEnumAttributes() {
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
				"codeAtt: number;\n" + 
				"\n" + 
				"@Map()\n" + 
				"att: maClasse2DTO;\n";
		ArrayList<String> list = new ArrayList<String>();
		assertEquals(expect, AssociationClassDtoClassGenerator.generateEnumAttributes(att, list).toString());	
		
		expect = "\n@Map()\n" + 
				"testOtherCodeAtt: number;\n" + 
				"\n" + 
				"@Map()\n" + 
				"testOtherAtt: maClasse2DTO;\n";
		list.add("test");
		list.add("other");
		assertEquals(expect, AssociationClassDtoClassGenerator.generateEnumAttributes(att,list).toString());
	}

	@Test
	public void testGenerateEntityAttributes() {
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
				
		String expect = "\n@Map()\n" + 
				"att: maClasse2DTO;\n";
		ArrayList<String> list = new ArrayList<String>();
		assertEquals(expect, AssociationClassDtoClassGenerator.generateEntityAttributes(att, list).toString());	
		
		expect = "\n@Map()\n" + 
				"testOtherAtt: maClasse2DTO;\n";
		list.add("test");
		list.add("other");
		assertEquals(expect, AssociationClassDtoClassGenerator.generateEntityAttributes(att,list).toString());
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
		
		Type stringPT = TestUtils.importPrimitiveType(profile, "String");
						
		Class class_ = TestUtils.createClass(model, "maClasse", false);
		Class class2_ = TestUtils.createClass(model, "maClasse2", false);
		class2_.applyStereotype(stereotype);
		
		Property att = TestUtils.createAttribute(class_, "att", class2_, 0, 1);
		Property id = TestUtils.createAttribute(class2_, "id", stringPT, 0, 1);
				
		String expect = "\n@Map()\n" + 
				"@Alias('idAtt', 'att.id')\n" + 
				"idAtt: string;\n";
		ArrayList<String> list = new ArrayList<String>();
		assertEquals(expect, AssociationClassDtoClassGenerator.generateEntityAttribute(att, id,  list).toString());	
		
		expect = "\n@Map()\n" + 
				"@Alias('testOtherIdAtt', 'test.other.att.id')\n" + 
				"testOtherIdAtt: string;\n";
		list.add("test");
		list.add("other");
		assertEquals(expect, AssociationClassDtoClassGenerator.generateEntityAttribute(att, id, list).toString());
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
		Class class2_ = TestUtils.createClass(model, "maClasse2", false);
		class2_.applyStereotype(stereotype);
		
		Property att = TestUtils.createAttribute(class_, "att", stringPT, 0, 1);
				
		String expect = "\n@Map()\n" + 
				"@Alias('att')\n" + 
				"att: string;\n";
		ArrayList<String> list = new ArrayList<String>();
		assertEquals(expect, AssociationClassDtoClassGenerator.generateBasicAttribute(att, list).toString());
		
		expect = "\n@Map()\n" + 
				"@Alias('testOtherAtt', 'test.other.att')\n" + 
				"testOtherAtt: string;\n";
		list.add("test");
		list.add("other");
		assertEquals(expect, AssociationClassDtoClassGenerator.generateBasicAttribute(att, list).toString());
	}

}
