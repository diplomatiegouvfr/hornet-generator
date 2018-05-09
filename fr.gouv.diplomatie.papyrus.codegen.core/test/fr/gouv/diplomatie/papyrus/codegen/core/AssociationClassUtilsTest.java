package fr.gouv.diplomatie.papyrus.codegen.core;

import static org.junit.Assert.*;

import org.eclipse.uml2.uml.AssociationClass;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Model;
import org.junit.Test;

import fr.gouv.diplomatie.papyrus.codegen.core.test.HornetModel;
import fr.gouv.diplomatie.papyrus.codegen.core.test.TestUtils;
import fr.gouv.diplomatie.papyrus.codegen.core.utils.AssociationClassUtils;

public class AssociationClassUtilsTest {
	
	private AssociationClass initTest(){
		HornetModel hmodel = HornetModel.initModel();
		Model model = hmodel.model;
		
		Class class_ = TestUtils.createClass(model, "maClasse", false);
		Class class2_ = TestUtils.createClass(model, "autreClasse", false);

		AssociationClass association = TestUtils.createAssociationClass(class_, class2_, "test1", "test2", "Asso");
		return association;
	}

	@Test
	public void testGetModelName() {
		
		AssociationClass association = initTest();
		assertEquals("AssoModel", AssociationClassUtils.getModelName(association));
	}

	@Test
	public void testGetDtoName() {
		
		AssociationClass association = initTest();
		assertEquals("AssoDto", AssociationClassUtils.getDtoName(association));
	}

}
