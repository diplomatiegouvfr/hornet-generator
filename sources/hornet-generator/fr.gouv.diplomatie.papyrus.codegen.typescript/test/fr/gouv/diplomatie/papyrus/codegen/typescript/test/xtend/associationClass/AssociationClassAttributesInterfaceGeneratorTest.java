package fr.gouv.diplomatie.papyrus.codegen.typescript.test.xtend.associationClass;

import static org.junit.Assert.assertEquals;

import org.eclipse.uml2.uml.AssociationClass;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Property;
import org.junit.Test;

import fr.gouv.diplomatie.papyrus.codegen.core.generators.GeneratorUtils;
import fr.gouv.diplomatie.papyrus.codegen.core.test.AssociationReturn;
import fr.gouv.diplomatie.papyrus.codegen.core.test.TestUtils;
import fr.gouv.diplomatie.papyrus.codegen.typescript.test.testutils.HornetModelTypescript;
import fr.gouv.diplomatie.papyrus.codegen.typescript.xtend.associationClass.AssociationClassAttributesInterfaceGenerator;

public class AssociationClassAttributesInterfaceGeneratorTest {

	@Test
	public void testGenerateImports() {
		HornetModelTypescript hmodel = HornetModelTypescript.initModel();
				
		Class class_ = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		class_.applyStereotype(hmodel.entity);
		Class class2_ = TestUtils.createClass(hmodel.pckage, "maClasse2", false);
		class2_.applyStereotype(hmodel.entity);
		
		AssociationClass class3_ = TestUtils.createAssociationClass(class_, class2_, "test", "test2", "asso");	
		
		String path = GeneratorUtils.getAttributesInterfacePath(class_, true);
		String path2 = GeneratorUtils.getAttributesInterfacePath(class2_, true);
		
		String expect = "import { maClasse2Attributes } from \"" + path2 + "\";\nimport { maClasseAttributes } from \"" + path + "\";\n";
		assertEquals(expect, AssociationClassAttributesInterfaceGenerator.generateImports(class3_).toString());		
	}


	@Test
	public void testEntityGeneratePropertyAttributes() {
		HornetModelTypescript hmodel = HornetModelTypescript.initModel();
				
		Class class_ = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		class_.applyStereotype(hmodel.entity);
		Class class2_ = TestUtils.createClass(hmodel.pckage, "maClasse2", false);
		class2_.applyStereotype(hmodel.entity);
		
		AssociationReturn asso = TestUtils.createAssociationClassGetAll(class_, class2_, "test", "test2", "asso");	
		Property prop1 = asso.attributeClassUn;
		
		String expect = "\ntest: Array<maClasse2Attributes>;\n" + 
				"getTest(): Promise<Array<maClasse2Attributes>>;\n";
		assertEquals(expect, AssociationClassAttributesInterfaceGenerator.generatePropertyAttributes(prop1, "").toString());		
	}
	
	@Test
	public void testValueObjectGeneratePropertyAttributes() {
		HornetModelTypescript hmodel = HornetModelTypescript.initModel();
		
		Class class_ = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		class_.applyStereotype(hmodel.valueObject);
		TestUtils.createAttribute(class_, "attribute", hmodel.stringPT, 0, 1);
		
		Class class2_ = TestUtils.createClass(hmodel.pckage, "maClasse2", false);
		class2_.applyStereotype(hmodel.valueObject);
		
		AssociationReturn asso = TestUtils.createAssociationClassGetAll(class_, class2_, "test", "test2", "asso");	
		Property prop1 = asso.attributeClassDeux;
		
		String expect = "test2Attribute?: string;\n";
		assertEquals(expect, AssociationClassAttributesInterfaceGenerator.generatePropertyAttributes(prop1, "").toString());	
		
		TestUtils.createAttribute(class_, "attribute2", hmodel.stringPT, 0, 1);
		expect = "test2Attribute?: string;\ntest2Attribute2?: string;\n";
		assertEquals(expect, AssociationClassAttributesInterfaceGenerator.generatePropertyAttributes(prop1, "").toString());	
	}
	
	@Test
	public void testNomenclatureGeneratePropertyAttributes() {
		HornetModelTypescript hmodel = HornetModelTypescript.initModel();
		
		Class class_ = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		class_.applyStereotype(hmodel.nomenclature);
		TestUtils.createAttribute(class_, "attribute", hmodel.stringPT, 0, 1);
		
		Class class2_ = TestUtils.createClass(hmodel.pckage, "maClasse2", false);
		class2_.applyStereotype(hmodel.nomenclature);
		
		AssociationReturn asso = TestUtils.createAssociationClassGetAll(class_, class2_, "test", "test2", "asso");	
		Property prop1 = asso.attributeClassDeux;
		
		String expect = "\ncodeTest2: number;\n" + 
				"test2: Array<maClasseAttributes>;\n" + 
				"getTest2(): Promise<Array<maClasseAttributes>>;\n";
		assertEquals(expect, AssociationClassAttributesInterfaceGenerator.generatePropertyAttributes(prop1, "").toString());	
		
	}

	@Test
	public void testGenerateBasicAttribute() {
		HornetModelTypescript hmodel = HornetModelTypescript.initModel();
				
		Class class_ = TestUtils.createClass(hmodel.model, "maClasse", false);
		Class class2_ = TestUtils.createClass(hmodel.model, "maClasse2", false);
		class2_.applyStereotype(hmodel.hornetType);
		
		Property prop = TestUtils.createAttribute(class_, "test", class2_, 0, 1);
		
		assertEquals("test?: string;\n", AssociationClassAttributesInterfaceGenerator.generateBasicAttribute(prop, "").toString());
		
		TestUtils.setStereotypePropertyValue(class2_, hmodel.hornetType, hmodel.hornetTypeTypescriptType, "Test");
		assertEquals("test?: Test;\n", AssociationClassAttributesInterfaceGenerator.generateBasicAttribute(prop, "").toString());
		
		assertEquals("addTest?: Test;\n", AssociationClassAttributesInterfaceGenerator.generateBasicAttribute(prop, "add").toString());

	}

	@Test
	public void testGeneratePropertyIdAttribute() {
		HornetModelTypescript hmodel = HornetModelTypescript.initModel();
				
		Class class_ = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		Class class2_ = TestUtils.createClass(hmodel.pckage, "maClasse2", false);
		class2_.applyStereotype(hmodel.hornetType);
		
		Property prop = TestUtils.createAttribute(class_, "test", hmodel.stringPT, 0, 1);
		Property id = TestUtils.createAttribute(class_, "id", class2_, 0, 1);
		
		assertEquals("idTest: string;\n", AssociationClassAttributesInterfaceGenerator.generatePropertyIdAttribute(prop, id).toString());
		
		TestUtils.setStereotypePropertyValue(class2_, hmodel.hornetType, hmodel.hornetTypeTypescriptType, "Test");
		assertEquals("idTest: Test;\n", AssociationClassAttributesInterfaceGenerator.generatePropertyIdAttribute(prop, id).toString());
		
	}

}
