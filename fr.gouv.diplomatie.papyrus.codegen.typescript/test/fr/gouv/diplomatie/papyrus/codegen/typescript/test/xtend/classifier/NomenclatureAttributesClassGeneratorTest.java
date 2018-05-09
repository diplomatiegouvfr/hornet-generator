package fr.gouv.diplomatie.papyrus.codegen.typescript.test.xtend.classifier;

import static org.junit.Assert.*;

import org.eclipse.uml2.uml.Class;
import org.junit.Test;

import fr.gouv.diplomatie.papyrus.codegen.core.test.TestUtils;
import fr.gouv.diplomatie.papyrus.codegen.typescript.test.testutils.HornetModelTypescript;
import fr.gouv.diplomatie.papyrus.codegen.typescript.xtend.classifier.NomenclatureAttributesClassGenerator;

public class NomenclatureAttributesClassGeneratorTest {

	@Test
	public void testGenerateCode() {
		HornetModelTypescript hmodel = HornetModelTypescript.initModel();
		
		Class class_ = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		class_.applyStereotype(hmodel.nomenclature);
		
		String expect = "\nexport class maClasseAttributes{\n" + 
				"	\n" + 
				"	code?: number;\n" + 
				"	libelle?: string;\n" + 
				"	\n" + 
				"}\n";
		
		assertEquals(expect, NomenclatureAttributesClassGenerator.generateCode(class_).toString());
	}

}
