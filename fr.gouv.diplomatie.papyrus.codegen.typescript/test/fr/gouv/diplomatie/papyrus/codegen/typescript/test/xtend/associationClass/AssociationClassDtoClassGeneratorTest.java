package fr.gouv.diplomatie.papyrus.codegen.typescript.test.xtend.associationClass;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.eclipse.uml2.uml.AssociationClass;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Type;
import org.junit.Test;

import fr.gouv.diplomatie.papyrus.codegen.core.generators.GeneratorUtils;
import fr.gouv.diplomatie.papyrus.codegen.core.test.TestUtils;
import fr.gouv.diplomatie.papyrus.codegen.typescript.test.testutils.HornetModelTypescript;
import fr.gouv.diplomatie.papyrus.codegen.typescript.xtend.associationClass.AssociationClassDtoClassGenerator;

public class AssociationClassDtoClassGeneratorTest {

	@Test
	public void testGenerateImports() {
		HornetModelTypescript hmodel = HornetModelTypescript.initModel();
				
		Class class_ = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		class_.applyStereotype(hmodel.entity);
		Class class2_ = TestUtils.createClass(hmodel.pckage, "maClasse2", false);
		class2_.applyStereotype(hmodel.entity);
		
		AssociationClass class3_ = TestUtils.createAssociationClass(class_, class2_, "test", "test2", "asso");	
		
		String path = GeneratorUtils.getDtoClassPath(class_, true);
		String path2 = GeneratorUtils.getDtoClassPath(class2_, true);
		
		String expect = "import { maClasse2DTO } from \"" + path2 + "\";\nimport { maClasseDTO } from \"" + path + "\";\n";
		assertEquals(expect, AssociationClassDtoClassGenerator.generateImports(class3_).toString());	
	}
	
	@Test
	public void testGenerateAttributesImports() {
		HornetModelTypescript hmodel = HornetModelTypescript.initModel();
				
		Class class_ = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		class_.applyStereotype(hmodel.entity);
		Class class2_ = TestUtils.createClass(hmodel.pckage, "maClasse2", false);
		class2_.applyStereotype(hmodel.entity);
		
		AssociationClass class3_ = TestUtils.createAssociationClass(class_, class2_, "test", "test2", "asso");	
		
		ArrayList<Type> empty = new ArrayList<Type>();
		ArrayList<Type> expect = new ArrayList<Type>();
		expect.add(class2_);
		expect.add(class_);
		assertEquals(expect, AssociationClassDtoClassGenerator.generateAttributesImports(class3_, empty));	
	}

	@Test
	public void testGenerateValueObjectAttribute() {
		HornetModelTypescript hmodel = HornetModelTypescript.initModel();
								
		Class class_ = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		Class class2_ = TestUtils.createClass(hmodel.pckage, "maClasse2", false);
		class2_.applyStereotype(hmodel.valueObject);
		
		Property att = TestUtils.createAttribute(class_, "att", class2_, 0, 1);
		
		TestUtils.createAttribute(class2_, "att2", hmodel.booleanPT , 0, 1);
				
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
		HornetModelTypescript hmodel = HornetModelTypescript.initModel();
						
		Class class_ = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		Class class2_ = TestUtils.createClass(hmodel.pckage, "maClasse2", false);
		class2_.applyStereotype(hmodel.nomenclature);
		
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
		HornetModelTypescript hmodel = HornetModelTypescript.initModel();
						
		Class class_ = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		Class class2_ = TestUtils.createClass(hmodel.pckage, "maClasse2", false);
		class2_.applyStereotype(hmodel.entity);
		
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
		HornetModelTypescript hmodel = HornetModelTypescript.initModel();
						
		Class class_ = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		Class class2_ = TestUtils.createClass(hmodel.pckage, "maClasse2", false);
		class2_.applyStereotype(hmodel.entity);
		
		Property att = TestUtils.createAttribute(class_, "att", class2_, 0, 1);
		Property id = TestUtils.createAttribute(class2_, "id", hmodel.stringPT, 0, 1);
				
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
		HornetModelTypescript hmodel = HornetModelTypescript.initModel();
			
		Class class_ = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		Class class2_ = TestUtils.createClass(hmodel.pckage, "maClasse2", false);
		class2_.applyStereotype(hmodel.entity);
		
		Property att = TestUtils.createAttribute(class_, "att", hmodel.stringPT, 0, 1);
				
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
