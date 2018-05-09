package fr.gouv.diplomatie.papyrus.codegen.core.test;

import static org.junit.Assert.*;

import java.util.Iterator;

import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Type;
import org.junit.Test;

import fr.gouv.diplomatie.papyrus.codegen.core.test.HornetModel;
import fr.gouv.diplomatie.papyrus.codegen.core.test.TestUtils;
import fr.gouv.diplomatie.papyrus.codegen.core.utils.ModelUtils;

public class ModelUtilsTest {

	@Test
	public void testGetAllClasses() {
		HornetModel hmodel = HornetModel.initModel();
		Class class_ = TestUtils.createClass(hmodel.model, "maClasse", false);
		Class class2_ = TestUtils.createClass(hmodel.model, "autreClasse", false);
		
		Iterable<Type> list = ModelUtils.getAllClasses(hmodel.model);
		Iterator<Type> it = list.iterator();
		assertEquals(class_,it.next());
		assertEquals(class2_,it.next());
	}

}
