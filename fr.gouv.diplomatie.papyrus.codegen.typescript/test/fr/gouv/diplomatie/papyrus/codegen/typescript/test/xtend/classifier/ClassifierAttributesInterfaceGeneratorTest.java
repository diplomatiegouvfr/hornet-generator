package fr.gouv.diplomatie.papyrus.codegen.typescript.test.xtend.classifier;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.eclipse.uml2.uml.AggregationKind;
import org.eclipse.uml2.uml.AssociationClass;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Type;
import org.junit.Test;

import fr.gouv.diplomatie.papyrus.codegen.core.generators.GeneratorUtils;
import fr.gouv.diplomatie.papyrus.codegen.core.test.TestUtils;
import fr.gouv.diplomatie.papyrus.codegen.core.utils.ClassifierUtils;
import fr.gouv.diplomatie.papyrus.codegen.core.utils.ClassifierUtils.ImportOptions;
import fr.gouv.diplomatie.papyrus.codegen.typescript.test.testutils.HornetModelTypescript;
import fr.gouv.diplomatie.papyrus.codegen.typescript.xtend.classifier.ClassifierAttributesInterfaceGenerator;

public class ClassifierAttributesInterfaceGeneratorTest {

	@Test
	public void testGenerateManyToManyAttribute() {
		HornetModelTypescript hmodel = HornetModelTypescript.initModel();
				
		Class class_ = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		class_.applyStereotype(hmodel.entity);
		Class class2_ = TestUtils.createClass(hmodel.pckage, "maClasse2", false);
		class2_.applyStereotype(hmodel.entity);
		
		Property prop = TestUtils.createAttribute(class_, "test", class2_, 0, -1);
		
		String expect = "test: Array<maClasse2Attributes>;\n" + 
				"getTest(): Promise<Array<maClasse2Attributes>>;\n";
		
		assertEquals(expect, ClassifierAttributesInterfaceGenerator.generateManyToManyAttribute(prop, class_).toString());		
	}

	@Test
	public void testGenerateOneToManyAttribute() {
		HornetModelTypescript hmodel = HornetModelTypescript.initModel();
				
		Class class_ = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		class_.applyStereotype(hmodel.entity);
		
		Property id = TestUtils.createAttribute(class_, "id", hmodel.stringPT, 0, 1);
		id.applyStereotype(hmodel.keyAttribute);
		
		Class class2_ = TestUtils.createClass(hmodel.pckage, "maClasse2", false);
		class2_.applyStereotype(hmodel.entity);
		
		Property id2 = TestUtils.createAttribute(class2_, "id", hmodel.stringPT, 0, 1);
		id2.applyStereotype(hmodel.keyAttribute);
		
		class_.createAssociation(true, AggregationKind.NONE_LITERAL	, "test", 0, 1, class2_, true, AggregationKind.NONE_LITERAL, "test2", 0, -1);
		Property test = class_.getAttribute("test", class2_);
		String expect = "idTest?: string;\ntest?: maClasse2Attributes;\n" + 
				"getTest(): Promise<maClasse2Attributes>;\n";
		
		assertEquals(expect, ClassifierAttributesInterfaceGenerator.generateOneToManyAttribute(test, class_).toString());	
	}

	@Test
	public void testGenerateImports() {
		HornetModelTypescript hmodel = HornetModelTypescript.initModel();
				
		Class class_ = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		class_.applyStereotype(hmodel.entity);
		Class class2_ = TestUtils.createClass(hmodel.pckage, "maClasse2", false);
		class2_.applyStereotype(hmodel.entity);
		
		TestUtils.createAttribute(class_, "test", class2_, 0, 1);
		
		String path2 = GeneratorUtils.getAttributesInterfacePath(class2_, true);
		
		String expect = "import { maClasse2Attributes } from \"" + path2 + "\";\n";
		assertEquals(expect, ClassifierAttributesInterfaceGenerator.generateImports(class_).toString());		
	}

	@Test
	public void testGenerateExtendsImports() {
		HornetModelTypescript hmodel = HornetModelTypescript.initModel();
				
		Class class_ = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		class_.applyStereotype(hmodel.entity);
		Class class2_ = TestUtils.createClass(hmodel.pckage, "maClasse2", false);
		class2_.applyStereotype(hmodel.entity);
		
		TestUtils.createGeneralization(class_, class2_);
		
		String path = ClassifierUtils.getAttributesInterfacePath(class2_);
		String expect = "import { maClasse2Attributes } from \"" + path +"\";\n";
		
		assertEquals(expect, ClassifierAttributesInterfaceGenerator.generateExtendsImports(class_).toString());
	}

	@Test
	public void testGenerateExtends() {
		HornetModelTypescript hmodel = HornetModelTypescript.initModel();
				
		Class class_ = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		class_.applyStereotype(hmodel.entity);
		Class class2_ = TestUtils.createClass(hmodel.pckage, "maClasse2", false);
		class2_.applyStereotype(hmodel.entity);
		
		TestUtils.createGeneralization(class_, class2_);
		String expect = "extends maClasse2Attributes ";
		
		assertEquals(expect, ClassifierAttributesInterfaceGenerator.generateExtends(class_).toString());
	}

	@Test
	public void testGenerateExtendsAttributes() {
		HornetModelTypescript hmodel = HornetModelTypescript.initModel();
						
		Class class_ = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		class_.applyStereotype(hmodel.entity);
		Class class2_ = TestUtils.createClass(hmodel.pckage, "maClasse2", false);
		class2_.applyStereotype(hmodel.entity);
		
		TestUtils.createGeneralization(class_, class2_);
		
		String expect = "maClasse2?: maClasse2Attributes;\n";
		
		assertEquals(expect, ClassifierAttributesInterfaceGenerator.generateExtendsAttributes(class_).toString());
	}

	@Test
	public void testGenerateAttributes() {
		HornetModelTypescript hmodel = HornetModelTypescript.initModel();
		
		Class class_ = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		class_.applyStereotype(hmodel.entity);
		TestUtils.createAttribute(class_, "test", hmodel.stringPT, 0, 1);
		TestUtils.createAttribute(class_, "test2", hmodel.stringPT, 0, -1);
		
		String expect = "test?: string;\n";
		assertEquals(expect, ClassifierAttributesInterfaceGenerator.generateAttributes(class_, "").toString());

	}

	@Test
	public void testGenerateBasicAttribute() {
		HornetModelTypescript hmodel = HornetModelTypescript.initModel();
				
		Class class_ = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		class_.applyStereotype(hmodel.entity);
		Class class2_ = TestUtils.createClass(hmodel.pckage, "maClasse2", false);
		class2_.applyStereotype(hmodel.entity);
		
		Property prop = TestUtils.createAttribute(class_, "test", hmodel.stringPT, 0, 1);
		
		assertEquals("test?: string;\n", ClassifierAttributesInterfaceGenerator.generateBasicAttribute(prop, "").toString());
		assertEquals("addTest?: string;\n", ClassifierAttributesInterfaceGenerator.generateBasicAttribute(prop, "add").toString());
	}

	@Test
	public void testGenerateValueObjectAttribute() {
		HornetModelTypescript hmodel = HornetModelTypescript.initModel();
				
		Class class_ = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		Class class2_ = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		class2_.applyStereotype(hmodel.valueObject);
		Property vo = TestUtils.createAttribute(class_, "vo", class2_, 0, 1);
		TestUtils.createAttribute(class2_, "test", hmodel.stringPT, 0, 1);
		TestUtils.createAttribute(class2_, "test2", hmodel.stringPT, 0, 1);
		
		String expect = "voTest?: string;\nvoTest2?: string;\n";
		assertEquals(expect, ClassifierAttributesInterfaceGenerator.generateValueObjectAttribute(vo, "").toString());
	}

	@Test
	public void testGenerateEnum() {
		HornetModelTypescript hmodel = HornetModelTypescript.initModel();
						
		Class class_ = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		class_.applyStereotype(hmodel.entity);
		Class class2_ = TestUtils.createClass(hmodel.pckage, "maClasse2", false);
		class2_.applyStereotype(hmodel.nomenclature);
		
		Property prop = TestUtils.createAttribute(class_, "test", class2_, 0, 1);
		
		assertEquals("codeTest?: number;\n" + 
				"test?: maClasse2Attributes;\n", ClassifierAttributesInterfaceGenerator.generateEnum(prop, "").toString());
	}

	@Test
	public void testGenerateEntityAttributes() {
		HornetModelTypescript hmodel = HornetModelTypescript.initModel();
						
		Class class_ = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		class_.applyStereotype(hmodel.entity);
		Class class2_ = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		class2_.applyStereotype(hmodel.entity);
		Property prop = TestUtils.createAttribute(class_, "test", class2_, 0, 1);
		Property prop2 = TestUtils.createAttribute(class2_, "test2", hmodel.stringPT, 0, 1);
		prop2.applyStereotype(hmodel.keyAttribute);
		
		String expect = "test2Test?: string;\n";
		assertEquals(expect, ClassifierAttributesInterfaceGenerator.generateEntityAttribute(prop, prop2, "").toString());
		
	}

	@Test
	public void testGenerateEntityAttribute() {
		HornetModelTypescript hmodel = HornetModelTypescript.initModel();
				
		Class class_ = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		class_.applyStereotype(hmodel.entity);
		Class class2_ = TestUtils.createClass(hmodel.pckage, "maClasse2", false);
		class2_.applyStereotype(hmodel.entity);
		
		Property prop = TestUtils.createAttribute(class_, "test", hmodel.stringPT, 0, 1);
		Property id = TestUtils.createAttribute(class_, "id", hmodel.stringPT, 0, 1);
		id.applyStereotype(hmodel.keyAttribute);
		
		assertEquals("idTest?: string;\n", ClassifierAttributesInterfaceGenerator.generateEntityAttribute(prop, id, "").toString());
	}

	@Test
	public void testGenerateReferenceAttributeAssocation() {
		HornetModelTypescript hmodel = HornetModelTypescript.initModel();
		
		Class class_ = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		class_.applyStereotype(hmodel.entity);
		Class class2_ = TestUtils.createClass(hmodel.pckage, "maClasse2", false);
		class2_.applyStereotype(hmodel.entity);
		Property id = TestUtils.createAttribute(class2_, "id", hmodel.stringPT, 0, 1);
		id.applyStereotype(hmodel.keyAttribute);
		
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
		HornetModelTypescript hmodel = HornetModelTypescript.initModel();
		
		Class class_ = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		class_.applyStereotype(hmodel.entity);
		Class class2_ = TestUtils.createClass(hmodel.pckage, "maClasse2", false);
		class2_.applyStereotype(hmodel.entity);
		Property id = TestUtils.createAttribute(class_, "id", hmodel.stringPT, 0, 1);
		id.applyStereotype(hmodel.keyAttribute);
		Property prop = TestUtils.createAttribute(class_, "test", class2_, 0, 1);
		
		String expect = "testId?: string;";
		assertEquals(expect, ClassifierAttributesInterfaceGenerator.generateReferenceAttributesNA(prop, class_, "").toString());
	}

	@Test
	public void testGenerateReferenceAttributeNA() {
		HornetModelTypescript hmodel = HornetModelTypescript.initModel();
		
		Class class_ = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		class_.applyStereotype(hmodel.entity);
		Class class2_ = TestUtils.createClass(hmodel.pckage, "maClasse2", false);
		class2_.applyStereotype(hmodel.entity);
		Property id = TestUtils.createAttribute(class_, "id", hmodel.stringPT, 0, 1);
		id.applyStereotype(hmodel.keyAttribute);
		Property prop = TestUtils.createAttribute(class_, "test", class2_, 0, 1);
		
		String expect = "testId?: string;";
		assertEquals(expect, ClassifierAttributesInterfaceGenerator.generateReferenceAttributeNA(prop, id, class_, "").toString());
	}

	@Test
	public void testGenerateEntityIdsAttributes() {
		HornetModelTypescript hmodel = HornetModelTypescript.initModel();
		
		Class class_ = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		class_.applyStereotype(hmodel.entity);
		Class class2_ = TestUtils.createClass(hmodel.pckage, "maClasse2", false);
		class2_.applyStereotype(hmodel.entity);
		Property id = TestUtils.createAttribute(class2_, "id", hmodel.stringPT, 0, 1);
		id.applyStereotype(hmodel.keyAttribute);
		Property prop = TestUtils.createAttribute(class_, "test", class2_, 0, 1);
		
		String expect = "testId: Array<string>;\n";
		assertEquals(expect, ClassifierAttributesInterfaceGenerator.generateEntityIdsAttributes(prop, "").toString());
	}

	@Test
	public void testGenerateEntityIdAttribute() {
		HornetModelTypescript hmodel = HornetModelTypescript.initModel();
				
		Class class_ = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		class_.applyStereotype(hmodel.entity);
		Class class2_ = TestUtils.createClass(hmodel.pckage, "maClasse2", false);
		class2_.applyStereotype(hmodel.entity);
		
		Property prop = TestUtils.createAttribute(class_, "test", hmodel.stringPT, 0, 1);
		Property id = TestUtils.createAttribute(class_, "id", hmodel.stringPT, 0, 1);
		id.applyStereotype(hmodel.keyAttribute);
		
		assertEquals("testId: Array<string>;\n", ClassifierAttributesInterfaceGenerator.generateEntityIdAttribute(prop, id, "").toString());
	}


	@Test
	public void testNomenclatureGenerateNotPrimitiveTypeAttribut() {
		HornetModelTypescript hmodel = HornetModelTypescript.initModel();
				
		Class class_ = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		class_.applyStereotype(hmodel.entity);
		Class class2_ = TestUtils.createClass(hmodel.pckage, "maClasse2", false);
		class2_.applyStereotype(hmodel.nomenclature);
		
		Property prop = TestUtils.createAttribute(class_, "test", class2_, 0, -1);
		TestUtils.createAttribute(class2_, "att", hmodel.stringPT, 0, 1);
		
		String expect = "test: Array<maClasse2Attributes>;\n\n";
		assertEquals(expect, ClassifierAttributesInterfaceGenerator.generateNotPrimitiveTypeAttribut(prop, "").toString());
	}
	
	@Test
	public void testGenerateNotPrimitiveTypeAttribut() {
		HornetModelTypescript hmodel = HornetModelTypescript.initModel();
				
		Class class_ = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		class_.applyStereotype(hmodel.entity);
		Class class2_ = TestUtils.createClass(hmodel.pckage, "maClasse2", false);
		
		Property prop = TestUtils.createAttribute(class_, "test", class2_, 0, -1);
		TestUtils.createAttribute(class2_, "att", hmodel.stringPT, 0, 1);
		
		String expect = "test: Array<string>;\n\n";
		assertEquals(expect, ClassifierAttributesInterfaceGenerator.generateNotPrimitiveTypeAttribut(prop, "").toString());
	}

	@Test
	public void testMultivaluedGenerateNPTEntityAttribut() {
		HornetModelTypescript hmodel = HornetModelTypescript.initModel();
						
		Class class_ = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		class_.applyStereotype(hmodel.entity);
		Class class2_ = TestUtils.createClass(hmodel.pckage, "maClasse2", false);
		
		Property prop = TestUtils.createAttribute(class_, "test", class2_, 0, -1);
		
		String expect = "test: Array<maClasse2Attributes>;\n" + 
				"getTest(): Promise<Array<maClasse2Attributes>>;\n" + 
				"\n";
		assertEquals(expect, ClassifierAttributesInterfaceGenerator.generateNPTEntityAttribut(prop, "").toString());
	}
	
	@Test
	public void testGenerateNPTEntityAttribut() {
		HornetModelTypescript hmodel = HornetModelTypescript.initModel();
						
		Class class_ = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		class_.applyStereotype(hmodel.entity);
		Class class2_ = TestUtils.createClass(hmodel.pckage, "maClasse2", false);
		
		Property prop = TestUtils.createAttribute(class_, "test", class2_, 0, 1);
		
		String expect = "test: maClasse2Attributes;\n" + 
				"getTest(): Promise<maClasse2Attributes>;\n" + 
				"\n";
		assertEquals(expect, ClassifierAttributesInterfaceGenerator.generateNPTEntityAttribut(prop, "").toString());
	}

	@Test
	public void testGenerateNPTValueObjectAttribut() {
		HornetModelTypescript hmodel = HornetModelTypescript.initModel();
						
		Class class_ = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		
		Class class2_ = TestUtils.createClass(hmodel.pckage, "maClasse2", false);
		class2_.applyStereotype(hmodel.valueObject);
		
		Property prop = TestUtils.createAttribute(class_, "test", class2_, 0, 1);
		
		String expect = "test: maClasse2Attributes;\n" + 
				"getTest(): maClasse2Attributes;\n" + 
				"\n";
		assertEquals(expect, ClassifierAttributesInterfaceGenerator.generateNPTValueObjectAttribut(prop, "").toString());
	}
	
	@Test
	public void testMultivaluedGenerateNPTValueObjectAttribut() {
		HornetModelTypescript hmodel = HornetModelTypescript.initModel();
						
		Class class_ = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		
		Class class2_ = TestUtils.createClass(hmodel.pckage, "maClasse2", false);
		class2_.applyStereotype(hmodel.valueObject);
		
		Property prop = TestUtils.createAttribute(class_, "test", class2_, 0, -1);
		
		String expect = "test: Array<maClasse2Attributes>;\n" + 
				"getTest(): Array<maClasse2Attributes>;\n" + 
				"\n";
		assertEquals(expect, ClassifierAttributesInterfaceGenerator.generateNPTValueObjectAttribut(prop, "").toString());
	}

	@Test
	public void testGenerateAssociationClassAtributes() {
		HornetModelTypescript hmodel = HornetModelTypescript.initModel();
				
		Class class_ = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		class_.applyStereotype(hmodel.entity);
		Class class2_ = TestUtils.createClass(hmodel.pckage, "maClasse2", false);
		class2_.applyStereotype(hmodel.entity);
		
		AssociationClass asso = TestUtils.createAssociationClass(class_, class2_, "test", "test2", "asso");
		
		String expect="test : Array<assoAttributes>;\n" + 
				"getAsso(): Promise<Array<assoAttributes>>;\n" + 
				"\n";
		assertEquals(expect, ClassifierAttributesInterfaceGenerator.generateAssociationClassAtributes(asso, class_).toString());
	}

}
