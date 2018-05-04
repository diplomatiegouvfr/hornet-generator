package fr.gouv.diplomatie.papyrus.codegen.core;

import static org.junit.Assert.*;

import org.eclipse.uml2.uml.AssociationClass;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.Profile;
import org.eclipse.uml2.uml.Stereotype;
import org.eclipse.uml2.uml.UMLPackage;
import org.junit.Test;

import fr.gouv.diplomatie.papyrus.codegen.core.test.TestUtils;
import fr.gouv.diplomatie.papyrus.codegen.core.utils.AssociationClassUtils;
import fr.gouv.diplomatie.papyrus.codegen.core.utils.ClassifierUtils;
import fr.gouv.diplomatie.papyrus.codegen.core.utils.Utils;

public class AssociationClassUtilsTest {

	@Test
	public void testGetModelName() {
		Profile profile = TestUtils.createProfile("profile");
		Model model = TestUtils.createModel("model");
		Stereotype stereotype = TestUtils.createStereotype(profile, Utils.MODEL_ENTITY, false);
	
		Class packageMetaclass = TestUtils.referenceMetaclass(profile, UMLPackage.Literals.CLASS.getName());
        TestUtils.createExtension(packageMetaclass, stereotype, false);
        
		profile.define();
		model.applyProfile(profile);
		Class class_ = TestUtils.createClass(model, "maClasse", false);
		Class class2_ = TestUtils.createClass(model, "autreClasse", false);

		AssociationClass association = TestUtils.createAssociationClass(class_, class2_, "test1", "test2", "Asso");
		
		assertEquals("AssoModel", AssociationClassUtils.getModelName(association));
	}

	@Test
	public void testGetDtoName() {
		Profile profile = TestUtils.createProfile("profile");
		Model model = TestUtils.createModel("model");
		Stereotype stereotype = TestUtils.createStereotype(profile, Utils.MODEL_ENTITY, false);
	
		Class packageMetaclass = TestUtils.referenceMetaclass(profile, UMLPackage.Literals.CLASS.getName());
        TestUtils.createExtension(packageMetaclass, stereotype, false);
        
		profile.define();
		model.applyProfile(profile);
		Class class_ = TestUtils.createClass(model, "maClasse", false);
		Class class2_ = TestUtils.createClass(model, "autreClasse", false);

		AssociationClass association = TestUtils.createAssociationClass(class_, class2_, "test1", "test2", "Asso");
		
		assertEquals("AssoDto", AssociationClassUtils.getDtoName(association));
	}

}
