package fr.gouv.diplomatie.papyrus.codegen.typescript.test.xtend.classifier;

import static org.junit.Assert.*;

import org.eclipse.uml2.uml.Class;
import org.junit.Test;

import fr.gouv.diplomatie.papyrus.codegen.core.test.TestUtils;
import fr.gouv.diplomatie.papyrus.codegen.typescript.test.testutils.HornetModelTypescript;
import fr.gouv.diplomatie.papyrus.codegen.typescript.xtend.classifier.NomenclatureDtoClassGenerator;

public class NomenclatureDtoClassGeneratorTest {

	@Test
	public void testGenerateCode() {
		HornetModelTypescript hmodel = HornetModelTypescript.initModel();
		
		Class class_ = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		class_.applyStereotype(hmodel.nomenclature);
		
		String expect = "import Alias from \"hornet-js-bean/src/decorators/Alias\";\n" + 
				"import Bean from \"hornet-js-bean/src/decorators/Bean\";\n" + 
				"import Map from \"hornet-js-bean/src/decorators/Map\";\n" + 
				"\n" + 
				"@Bean\n" + 
				"export class maClasseDTO{\n" + 
				"    @Map()\n" + 
				"    code: number;\n" + 
				"    \n" + 
				"    @Map()\n" + 
				"    libelle: string;\n" + 
				"}\n";
		
		assertEquals(expect, NomenclatureDtoClassGenerator.generateCode(class_).toString());
	}

}
