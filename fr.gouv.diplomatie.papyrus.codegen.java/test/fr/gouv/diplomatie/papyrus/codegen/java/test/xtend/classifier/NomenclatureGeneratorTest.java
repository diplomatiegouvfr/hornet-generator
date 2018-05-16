package fr.gouv.diplomatie.papyrus.codegen.java.test.xtend.classifier;

import static org.junit.Assert.*;

import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Property;
import org.junit.Test;

import fr.gouv.diplomatie.papyrus.codegen.core.test.TestUtils;
import fr.gouv.diplomatie.papyrus.codegen.java.test.testutils.HornetModelJava;
import fr.gouv.diplomatie.papyrus.codegen.java.xtend.classifier.NomenclatureGenerator;

public class NomenclatureGeneratorTest {

	@Test
	public void testClass() {
		NomenclatureGenerator gene = new NomenclatureGenerator();
		assertEquals(NomenclatureGenerator.class, gene.getClass());
	}
	
	@Test
	public void testGenerateCode() {
		HornetModelJava hmodel = HornetModelJava.initModel();
		Class class_  = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		class_.applyStereotype(hmodel.nomenclature);
		
		String expect = "package fr.gouv.diplomatie;\n" + 
				"\n" + 
				"public enum maClasse {\n" + 
				"}\n";
		assertEquals(expect, NomenclatureGenerator.generateCode(class_));
	}
	
	@Test
	public void testGenerateAttributes() {
		HornetModelJava hmodel = HornetModelJava.initModel();
		Class class_  = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		class_.applyStereotype(hmodel.nomenclature);
		
		Property prop = TestUtils.createAttribute(class_, "test", hmodel.stringPT, 0, 1);
		prop.applyStereotype(hmodel.codeLibelleNomenclature);
		
		Property prop2 = TestUtils.createAttribute(class_, "test2", hmodel.stringPT, 0, 1);
		prop2.applyStereotype(hmodel.codeLibelleNomenclature);
		
		String expect = "test,\ntest2\n";
		assertEquals(expect, NomenclatureGenerator.generateAttributes(class_).toString());
	}

	@Test
	public void testGenerateAttribut() {
		HornetModelJava hmodel = HornetModelJava.initModel();
		Class class_  = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		class_.applyStereotype(hmodel.nomenclature);
		
		Property prop = TestUtils.createAttribute(class_, "test", hmodel.stringPT, 0, 1);
		prop.applyStereotype(hmodel.codeLibelleNomenclature);
		
		String expect = "test";
		assertEquals(expect, NomenclatureGenerator.generateAttribut(prop));
	}

}
