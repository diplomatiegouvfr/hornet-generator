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
import fr.gouv.diplomatie.papyrus.codegen.typescript.xtend.associationClass.AssociationClassMetierClassGenerator;

public class AssociationClassMetierClassGeneratorTest {

	@Test
	public void testGenerateImports() {
		HornetModelTypescript hmodel = HornetModelTypescript.initModel();
				
		Class class_ = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		class_.applyStereotype(hmodel.entity);
		Class class2_ = TestUtils.createClass(hmodel.pckage, "maClasse2", false);
		class2_.applyStereotype(hmodel.entity);
		
		AssociationClass class3_ = TestUtils.createAssociationClass(class_, class2_, "test", "test2", "asso");	
		
		String path = GeneratorUtils.getMetierClassPath(class_, true);
		String path2 = GeneratorUtils.getMetierClassPath(class2_, true);
		
		String expect = "import { maClasse2Metier } from \"" + path2 + "\";\nimport { maClasseMetier } from \"" + path + "\";\n";
		assertEquals(expect, AssociationClassMetierClassGenerator.generateImports(class3_).toString());	
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
		assertEquals(expect, AssociationClassMetierClassGenerator.generateAttributesImports(class3_, empty, class3_));	
	}

	@Test
	public void testGenerateValueObjectAttribute() {
		HornetModelTypescript hmodel = HornetModelTypescript.initModel();
								
		Class class_ = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		Class class2_ = TestUtils.createClass(hmodel.pckage, "maClasse2", false);
		class2_.applyStereotype(hmodel.valueObject);
				
		Property att = TestUtils.createAttribute(class_, "att", class2_, 0, 1);
		TestUtils.createAttribute(class2_, "att2", hmodel.stringPT, 0, 1);
				
		String expect = "\n@Map(maClasse2Metier)\n" + 
				"att: maClasse2Metier;\n";
		assertEquals(expect, AssociationClassMetierClassGenerator.generateValueObjectAttribute(att, "").toString());
		
		expect = "\n@Map(maClasse2Metier)\n" + 
				"testAtt: maClasse2Metier;\n";
		assertEquals(expect, AssociationClassMetierClassGenerator.generateValueObjectAttribute(att, "test").toString());
	}

	@Test
	public void testGenerateEnumAttribute() {
		HornetModelTypescript hmodel = HornetModelTypescript.initModel();
								
		Class class_ = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		Class class2_ = TestUtils.createClass(hmodel.pckage, "maClasse2", false);
		class2_.applyStereotype(hmodel.nomenclature);
				
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
		HornetModelTypescript hmodel = HornetModelTypescript.initModel();
								
		Class class_ = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		Class class2_ = TestUtils.createClass(hmodel.pckage, "maClasse2", false);
		class2_.applyStereotype(hmodel.entity);
				
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
		HornetModelTypescript hmodel = HornetModelTypescript.initModel();

		Class class_ = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		Class class2_ = TestUtils.createClass(hmodel.pckage, "maClasse2", false);
		class2_.applyStereotype(hmodel.entity);
				
		Property att = TestUtils.createAttribute(class_, "att", hmodel.stringPT, 0, 1);
				
		String expect = "\n@Map()\n" + 
				"att: string;\n";
		assertEquals(expect, AssociationClassMetierClassGenerator.generateBasicAttribute(att, "").toString());
		
		expect = "\n@Map()\n" + 
				"testAtt: string;\n";
		assertEquals(expect, AssociationClassMetierClassGenerator.generateBasicAttribute(att, "test").toString());
	}

	@Test
	public void testGenerateRefAttribute() {
		HornetModelTypescript hmodel = HornetModelTypescript.initModel();
								
		Class class_ = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		Class class2_ = TestUtils.createClass(hmodel.pckage, "maClasse2", false);
		class2_.applyStereotype(hmodel.entity);
		
		Property att = TestUtils.createAttribute(class_, "att", class2_, 0, 1);
				
		String expect = "\n@Map(maClasse2Metier)\n" + 
				"att: maClasse2Metier;\n";
		assertEquals(expect, AssociationClassMetierClassGenerator.generateRefAttribute(att, "").toString());
		
		expect = "\n@Map(maClasse2Metier)\n" + 
				"testAtt: maClasse2Metier;\n";
		assertEquals(expect, AssociationClassMetierClassGenerator.generateRefAttribute(att, "test").toString());
	}

}
