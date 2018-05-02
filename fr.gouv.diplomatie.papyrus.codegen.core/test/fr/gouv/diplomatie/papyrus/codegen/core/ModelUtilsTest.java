package fr.gouv.diplomatie.papyrus.codegen.core;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Iterator;

import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.Profile;
import org.eclipse.uml2.uml.Stereotype;
import org.eclipse.uml2.uml.UMLPackage;
import org.eclipse.uml2.uml.Type;
import org.junit.Test;

import fr.gouv.diplomatie.papyrus.codegen.core.test.TestUtils;
import fr.gouv.diplomatie.papyrus.codegen.core.utils.ModelUtils;
import fr.gouv.diplomatie.papyrus.codegen.core.utils.Utils;

public class ModelUtilsTest {

	@Test
	public void testGetAllClasses() {
		Profile profile = TestUtils.createProfile("profile");
		Model model = TestUtils.createModel("model");
		Stereotype stereotype = TestUtils.createStereotype(profile, Utils.MODEL_ENTITY, false);
	
		Class packageMetaclass = TestUtils.referenceMetaclass(profile, UMLPackage.Literals.CLASS.getName());
        TestUtils.createExtension(packageMetaclass, stereotype, false);
        
		profile.define();
		model.applyProfile(profile);
		Class class_ = TestUtils.createClass(model, "maClasse", false);
		Class class2_ = TestUtils.createClass(model, "autreClasse", false);
		
		Iterable<Type> list = ModelUtils.getAllClasses(model);
		Iterator<Type> it = list.iterator();
		assertEquals(class_,it.next());
		assertEquals(class2_,it.next());
	}

}
