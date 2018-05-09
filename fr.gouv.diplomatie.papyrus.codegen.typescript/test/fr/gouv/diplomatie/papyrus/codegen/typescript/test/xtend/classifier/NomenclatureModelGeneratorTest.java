package fr.gouv.diplomatie.papyrus.codegen.typescript.test.xtend.classifier;

import static org.junit.Assert.*;

import org.eclipse.uml2.uml.Class;
import org.junit.Test;

import fr.gouv.diplomatie.papyrus.codegen.core.test.TestUtils;
import fr.gouv.diplomatie.papyrus.codegen.typescript.test.testutils.HornetModelTypescript;
import fr.gouv.diplomatie.papyrus.codegen.typescript.xtend.classifier.NomenclatureModelGenerator;

public class NomenclatureModelGeneratorTest {

	@Test
	public void testGenerateCode() {
		HornetModelTypescript hmodel = HornetModelTypescript.initModel();
		
		Class class_ = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		class_.applyStereotype(hmodel.nomenclature);
		
		String expect = "import Sequelize = require(\"sequelize\");\n" + 
				"\n" + 
				"export var maClasseModel: Sequelize.DefineAttributes={\n" + 
				"	code: {\n" + 
				"		type: Sequelize.INTEGER,\n" + 
				"		field: \"CODE\",\n" + 
				"		allowNull: false,\n" + 
				"		primaryKey: true\n" + 
				"	},\n" + 
				"	libelle: {\n" + 
				"		type: Sequelize.TEXT,\n" + 
				"		field: \"LIBELLE\",\n" + 
				"		allowNull: false\n" + 
				"	}\n" + 
				"}\n";
		assertEquals(expect, NomenclatureModelGenerator.generateCode(class_).toString());
	}

}
