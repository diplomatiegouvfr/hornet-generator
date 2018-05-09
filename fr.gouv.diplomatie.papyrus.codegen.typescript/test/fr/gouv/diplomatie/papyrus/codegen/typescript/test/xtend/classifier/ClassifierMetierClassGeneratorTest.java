package fr.gouv.diplomatie.papyrus.codegen.typescript.test.xtend.classifier;

import static org.junit.Assert.assertEquals;

import org.eclipse.uml2.uml.AggregationKind;
import org.eclipse.uml2.uml.AssociationClass;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Interface;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.Profile;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Stereotype;
import org.eclipse.uml2.uml.UMLPackage;
import org.junit.Test;

import fr.gouv.diplomatie.papyrus.codegen.core.test.TestUtils;
import fr.gouv.diplomatie.papyrus.codegen.core.utils.Utils;
import fr.gouv.diplomatie.papyrus.codegen.typescript.test.testutils.HornetModelTypescript;
import fr.gouv.diplomatie.papyrus.codegen.typescript.xtend.classifier.ClassifierMetierClassGenerator;

public class ClassifierMetierClassGeneratorTest {

	@Test
	public void testGenerateManyToManyAttribute() {
		HornetModelTypescript hmodel = HornetModelTypescript.initModel();
		
		Class class_ = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		class_.applyStereotype(hmodel.entity);
		Class class2_ = TestUtils.createClass(hmodel.pckage, "maClasse2", false);
		class2_.applyStereotype(hmodel.entity);
		
		Property prop = TestUtils.createAttribute(class_, "test", class2_, 0, -1);
		
		String expect = "\n@Map(maClasse2Metier)\n" + 
				"test: Array<maClasse2Metier>;\n";
		assertEquals(expect, ClassifierMetierClassGenerator.generateManyToManyAttribute(prop, class_).toString());
	}

	@Test
	public void testGenerateOneToManyAttribute() {
		HornetModelTypescript hmodel = HornetModelTypescript.initModel();
		
		Class class_ = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		class_.applyStereotype(hmodel.entity);
		Class class2_ = TestUtils.createClass(hmodel.pckage, "maClasse2", false);
		class2_.applyStereotype(hmodel.entity);
		
		class_.createAssociation(true, AggregationKind.NONE_LITERAL, "test", 0, -1, class2_, true, AggregationKind.NONE_LITERAL, "test2", 0, 1);
		Property prop = class_.getAttribute("test", class2_);
		
		String expect  ="\n@Map(maClasseMetier)\n" + 
				"maClasseTest: maClasseMetier;\n";
		assertEquals(expect, ClassifierMetierClassGenerator.generateOneToManyAttribute(prop, class_).toString());
	}

	@Test
	public void testGenerateExtends() {
		HornetModelTypescript hmodel = HornetModelTypescript.initModel();
		
		Class class_ = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		class_.applyStereotype(hmodel.entity);
		Class class2_ = TestUtils.createClass(hmodel.pckage, "maClasse2", false);
		class2_.applyStereotype(hmodel.entity);
		
		TestUtils.createGeneralization(class_, class2_);
		
		String expect = "extends maClasse2Metier";
		assertEquals(expect, ClassifierMetierClassGenerator.generateExtends(class_).toString());

	}

	@Test
	public void testGenerateImplements() {
		HornetModelTypescript hmodel = HornetModelTypescript.initModel();
		
		Class class_ = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		class_.applyStereotype(hmodel.entity);
		Interface interf = TestUtils.createInterface("interf");
		
		class_.createInterfaceRealization("test", interf);
		
		String expect = " implements interfMetier\n";
		assertEquals(expect, ClassifierMetierClassGenerator.generateImplements(class_).toString());
		
	}

	@Test
	public void testGenerateValueObjectAttribute() {
		HornetModelTypescript hmodel = HornetModelTypescript.initModel();
		
		Class class_ = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		class_.applyStereotype(hmodel.entity);
		Class class2_ = TestUtils.createClass(hmodel.pckage, "maClasse2", false);
		class2_.applyStereotype(hmodel.valueObject);
		
		Property prop = TestUtils.createAttribute(class_, "test", class2_, 0, 1);
		
		String expect = "\n@Map(maClasse2Metier)\n" + 
				"test: maClasse2Metier;\n";
		assertEquals(expect, ClassifierMetierClassGenerator.generateValueObjectAttribute(prop, "").toString());
		
		expect = "\n@Map(maClasse2Metier)\n" + 
				"addOtherTest: maClasse2Metier;\n";
		assertEquals(expect, ClassifierMetierClassGenerator.generateValueObjectAttribute(prop, "addOther").toString());
		
	}

	@Test
	public void testGenerateEnumAttribute() {
		HornetModelTypescript hmodel = HornetModelTypescript.initModel();
		
		Class class_ = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		class_.applyStereotype(hmodel.entity);
		Class class2_ = TestUtils.createClass(hmodel.pckage, "maClasse2", false);
		class2_.applyStereotype(hmodel.nomenclature);
		
		Property prop = TestUtils.createAttribute(class_, "test", class2_, 0, 1);
		
		String expect = "\n@Map(maClasse2Metier)\n" + 
				"test: maClasse2Metier;\n";
		assertEquals(expect, ClassifierMetierClassGenerator.generateValueObjectAttribute(prop, "").toString());
		
		expect = "\n@Map(maClasse2Metier)\n" + 
				"addOtherTest: maClasse2Metier;\n";
		assertEquals(expect, ClassifierMetierClassGenerator.generateValueObjectAttribute(prop, "addOther").toString());
	}
	
	@Test
	public void testMultivaluedGenerateEnumAttribute() {
		Profile profile = TestUtils.createProfile("profile");
		Model model = TestUtils.createModel("model");
		
		Stereotype stereotype = TestUtils.createStereotype(profile, Utils.MODEL_ENTITY, false);
		Class metaclass = TestUtils.referenceMetaclass(profile, UMLPackage.Literals.CLASS.getName());
        TestUtils.createExtension(metaclass, stereotype, false);
        
        Stereotype stereotype2 = TestUtils.createStereotype(profile, Utils.MODEL_NOMENCLATURE, false);
        TestUtils.createExtension(metaclass, stereotype2, false);
		
		profile.define();
		model.applyProfile(profile);
				
		Class class_ = TestUtils.createClass(model, "maClasse", false);
		class_.applyStereotype(stereotype);
		Class class2_ = TestUtils.createClass(model, "maClasse2", false);
		class2_.applyStereotype(stereotype2);
		
		Property prop = TestUtils.createAttribute(class_, "test", class2_, 0, -1);
		
		String expect = "\n@Map(maClasse2Metier)\n" + 
				"test: Array<maClasse2Metier>;\n";
		assertEquals(expect, ClassifierMetierClassGenerator.generateValueObjectAttribute(prop, "").toString());
	}

	@Test
	public void testGenerateEntityAttributes() {
		HornetModelTypescript hmodel = HornetModelTypescript.initModel();
		
		Class class_ = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		class_.applyStereotype(hmodel.entity);
		Class class2_ = TestUtils.createClass(hmodel.pckage, "maClasse2", false);
		class2_.applyStereotype(hmodel.entity);
		
		Property prop = TestUtils.createAttribute(class_, "test", class2_, 0, 1);
		Property id = TestUtils.createAttribute(class_, "id", hmodel.stringPT, 0, 1);
		Property id2 = TestUtils.createAttribute(class2_, "id2", hmodel.stringPT, 0, 1);
		id.applyStereotype(hmodel.keyAttribute);
		id2.applyStereotype(hmodel.keyAttribute);
		
		String expect = "\n@Map(maClasse2Metier)\n" + 
				"test: maClasse2Metier;\n";
		assertEquals(expect, ClassifierMetierClassGenerator.generateEntityAttributes(prop, "").toString());
	}
	
	@Test
	public void testMultivaluedGenerateEntityAttributes() {
		HornetModelTypescript hmodel = HornetModelTypescript.initModel();
		
		Class class_ = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		class_.applyStereotype(hmodel.entity);
		Class class2_ = TestUtils.createClass(hmodel.pckage, "maClasse2", false);
		class2_.applyStereotype(hmodel.entity);
		
		Property prop = TestUtils.createAttribute(class_, "test", class2_, 0, -1);
		Property id = TestUtils.createAttribute(class_, "id", hmodel.stringPT, 0, 1);
		Property id2 = TestUtils.createAttribute(class2_, "id2", hmodel.stringPT, 0, 1);
		id.applyStereotype(hmodel.keyAttribute);
		id2.applyStereotype(hmodel.keyAttribute);
		
		String expect = "\n@Map(maClasse2Metier)\n" + 
				"test: Array<maClasse2Metier>;\n";
		assertEquals(expect, ClassifierMetierClassGenerator.generateEntityAttributes(prop, "").toString());
	}

	@Test
	public void testGenerateBasicAttribute() {
		HornetModelTypescript hmodel = HornetModelTypescript.initModel();
		
		Class class_ = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		class_.applyStereotype(hmodel.entity);
		
		Property prop = TestUtils.createAttribute(class_, "test", hmodel.stringPT, 0, 1);
		Property id = TestUtils.createAttribute(class_, "id", hmodel.stringPT, 0, 1);
		id.applyStereotype(hmodel.keyAttribute);
		
		String expect = "\n@Map()\n" + 
				"test: string;\n";
		assertEquals(expect, ClassifierMetierClassGenerator.generateBasicAttribute(prop, "").toString());
	}
	
	@Test
	public void testMultivaluedGenerateBasicAttribute() {
		HornetModelTypescript hmodel = HornetModelTypescript.initModel();
		
		Class class_ = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		class_.applyStereotype(hmodel.entity);
		
		Property prop = TestUtils.createAttribute(class_, "test", hmodel.stringPT, 0, -1);
		Property id = TestUtils.createAttribute(class_, "id", hmodel.stringPT, 0, 1);
		id.applyStereotype(hmodel.keyAttribute);
		
		String expect = "\n@Map()\n" + 
				"test: Set<string>;\n";
		assertEquals(expect, ClassifierMetierClassGenerator.generateBasicAttribute(prop, "").toString());
	}

	@Test
	public void testGenerateAssociationAttributes() {
		HornetModelTypescript hmodel = HornetModelTypescript.initModel();
		
		Class class_ = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		class_.applyStereotype(hmodel.entity);
		Class class2_ = TestUtils.createClass(hmodel.pckage, "maClasse2", false);
		class2_.applyStereotype(hmodel.entity);
		
		AssociationClass asso = TestUtils.createAssociationClass(class_, class2_, "test", "test2", "asso");
		
		String expect = "\n@Map(assoMetier)\n" + 
				"asso: Array<assoMetier>;\n";
		assertEquals(expect, ClassifierMetierClassGenerator.generateAssociationAttributes(asso, class_).toString());
	}

}
