package fr.gouv.diplomatie.papyrus.codegen.typescript.test.xtend.classifier;

import static org.junit.Assert.*;

import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Property;
import org.junit.Test;

import fr.gouv.diplomatie.papyrus.codegen.core.test.TestUtils;
import fr.gouv.diplomatie.papyrus.codegen.typescript.test.testutils.HornetModelTypescript;
import fr.gouv.diplomatie.papyrus.codegen.typescript.xtend.classifier.NomenclatureEnumClassGenerator;

public class NomenclatureEnumClassGeneratorTest {
	


	@Test
	public void testGenerateCode() {	
		HornetModelTypescript hmodel = HornetModelTypescript.initModel();
		
		Class class_ = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		class_.applyStereotype(hmodel.nomenclature);
		
		Property prop = TestUtils.createAttribute(class_, "test", hmodel.stringPT, 0, 1);
		prop.applyStereotype(hmodel.codeLibelleNomenclature);
		TestUtils.setStereotypePropertyValue(prop, hmodel.codeLibelleNomenclature, hmodel.codeLibelleNomenclatureCode, 1);
		TestUtils.setStereotypePropertyValue(prop, hmodel.codeLibelleNomenclature, hmodel.codeLibelleNomenclatureLibelle, "lib");
		
		String expect  ="export enum MaClasse{\n	" + 
				NomenclatureEnumClassGenerator.generateAttributes(class_)+"}\n";
		assertEquals(expect, NomenclatureEnumClassGenerator.generateCode(class_).toString());
	}

	@Test
	public void testGenerateValue() {
		HornetModelTypescript hmodel = HornetModelTypescript.initModel();
		
		Class class_ = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		class_.applyStereotype(hmodel.nomenclature);
		
		Property prop = TestUtils.createAttribute(class_, "test", hmodel.stringPT, 0, 1);
		prop.applyStereotype(hmodel.codeLibelleNomenclature);
		TestUtils.setStereotypePropertyValue(prop, hmodel.codeLibelleNomenclature, hmodel.codeLibelleNomenclatureCode, 1);
		TestUtils.setStereotypePropertyValue(prop, hmodel.codeLibelleNomenclature, hmodel.codeLibelleNomenclatureLibelle, "lib");
		
		String expect  ="lib = 1";
		assertEquals(expect, NomenclatureEnumClassGenerator.generateValue(prop).toString());
	}

}
