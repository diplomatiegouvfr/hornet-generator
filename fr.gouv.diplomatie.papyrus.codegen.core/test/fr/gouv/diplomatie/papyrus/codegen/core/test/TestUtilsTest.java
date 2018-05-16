package fr.gouv.diplomatie.papyrus.codegen.core.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import org.eclipse.emf.common.util.URI;
import org.eclipse.uml2.uml.AssociationClass;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Enumeration;
import org.eclipse.uml2.uml.Interface;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.Profile;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Stereotype;
import org.eclipse.uml2.uml.Type;
import org.eclipse.uml2.uml.UMLPackage;
import org.eclipse.uml2.uml.resource.UMLResource;
import org.junit.Test;

import fr.gouv.diplomatie.papyrus.codegen.core.utils.Utils;

public class TestUtilsTest {
	
	@Test
	public void testClass() {
		TestUtils utils = new TestUtils();
		assertEquals(TestUtils.class, utils.getClass());
	}

	@Test
	public void testCreateModel() {
		Model model = TestUtils.createModel("model");
		assertEquals("model", model.getName());
	}

	@Test
	public void testCreateProfile() {
		Profile profile = TestUtils.createProfile("profile");
		assertEquals("profile", profile.getName());
	}

	@Test
	public void testCreateInterface() {
		Interface interf = TestUtils.createInterface("interf");
		assertEquals("interf", interf.getName());
	}

	@Test
	public void testCreateStereotype() {
		Profile profile = TestUtils.createProfile("profile");
		Stereotype stereotype = TestUtils.createStereotype(profile, "test", false);
		assertEquals("test", stereotype.getName());
	}

	@Test
	public void testCreatePackage() {
		Model model = TestUtils.createModel("model");
		Package pkg = TestUtils.createPackage(model, "pkg");
		assertEquals("pkg", pkg.getName());
	}

	@Test
	public void testCreateClass() {
		Model model = TestUtils.createModel("model");
		Package pkg = TestUtils.createPackage(model, "pkg");
		Class class_ = TestUtils.createClass(pkg, "class", false);
		assertEquals("class", class_.getName());
	}

	@Test
	public void testCreateEnumeration() {
		Model model = TestUtils.createModel("model");
		Package pkg = TestUtils.createPackage(model, "pkg");
		Enumeration enumeration =  TestUtils.createEnumeration(pkg, "enum");
		assertEquals("enum", enumeration.getName());
	}

	@Test
	public void testGetpackage() {
		Package pkg = TestUtils.getpackage("model", "pkg");
		assertEquals("pkg", pkg.getName());
	}

	@Test
	public void testGetClass() {
		Class class_ = TestUtils.getClass("model", "pkg", "class", false);
		assertEquals("class", class_.getName());
	}

	@Test
	public void testGetStereotype() {
		Stereotype stereo = TestUtils.getStereotype("profile", "stereo", false);
		assertEquals("stereo", stereo.getName());
	}

	@Test
	public void testCreateExtension() {
		Profile profile = TestUtils.createProfile("profile");
		Model model = TestUtils.createModel("model");
		Class metaclass = TestUtils.referenceMetaclass(profile, UMLPackage.Literals.CLASS.getName());
		Stereotype stereo = TestUtils.createStereotype(profile, "stereo", false);
		TestUtils.createExtension(metaclass, stereo, false);
		
		profile.define();
		model.applyProfile(profile);
			
		Class class_ = TestUtils.createClass(model, "test", false);
		class_.applyStereotype(stereo);
		
	}

	@Test
	public void testCreateGeneralization() {
		Profile profile = TestUtils.createProfile("profile");
		Class propmetaclass = TestUtils.referenceMetaclass(profile, UMLPackage.Literals.PROPERTY.getName());

		Stereotype attribute = TestUtils.createStereotype(profile, Utils.MODEL_ATTRIBUTE, false);
		TestUtils.createExtension(propmetaclass, attribute, false);
		 
		Stereotype keyAttribute = TestUtils.createStereotype(profile, Utils.MODEL_KEYATTRIBUTE, false);
		TestUtils.createExtension(propmetaclass, keyAttribute, false);
		TestUtils.createGeneralization(keyAttribute, attribute);
	}

	@Test
	public void testReferenceMetaclass() {
		Profile profile = TestUtils.createProfile("profile");
		Class propmetaclass = TestUtils.referenceMetaclass(profile, UMLPackage.Literals.PROPERTY.getName());
		assertEquals("Property", propmetaclass.getName());
	}

	@Test
	public void testLoad() {
		Package _package = TestUtils.load(URI.createURI(UMLResource.UML_METAMODEL_URI));
		assertNotNull(_package);
	}

	@Test
	public void testCreateAttribute() {
		Package pkg = TestUtils.getpackage("model", "pkg");
		Type integerPT = TestUtils.importPrimitiveType(pkg, "Integer");
		Class class_ = TestUtils.createClass(pkg, "class", false);
		Property attr = TestUtils.createAttribute(class_, "test", integerPT, 0, 1);
		assertEquals("test", attr.getName());
	}

	@Test
	public void testImportPrimitiveType() {
		Package pkg = TestUtils.getpackage("model", "pkg");
		Type integerPT = TestUtils.importPrimitiveType(pkg, "Integer");
		assertEquals("Integer", integerPT.getName());
	}

	@Test
	public void testSetStereotypePropertyValue() {
		Profile profile = TestUtils.createProfile("profile");
		Class propmetaclass = TestUtils.referenceMetaclass(profile, UMLPackage.Literals.CLASS.getName());
		Type stringPT = TestUtils.importPrimitiveType(profile, "String");
		Stereotype attribute = TestUtils.createStereotype(profile, Utils.MODEL_ATTRIBUTE, false);
		Property attributeColumnName = TestUtils.createAttribute(attribute, Utils.MODEL_ATTRIBUTE_COLUMNNAME, stringPT, 0, 1);
		TestUtils.createExtension(propmetaclass, attribute, false);
		
		Model model = TestUtils.createModel("model");
		Package pkg = TestUtils.createPackage(model, "pkg");
		
		profile.define();
		model.applyProfile(profile);
		
		Class class_ = TestUtils.createClass(pkg, "class", false);
		class_.applyStereotype(attribute);
		TestUtils.setStereotypePropertyValue(class_, attribute, attributeColumnName, "test");
	}

	@Test
	public void testCreateAssociationClass() {
		Profile profile = TestUtils.createProfile("profile");
		Model model = TestUtils.createModel("model");
		Package pkg = TestUtils.createPackage(model, "pkg");
		
		profile.define();
		model.applyProfile(profile);
		Class class_ = TestUtils.createClass(pkg, "class", false);
		Class class2_ = TestUtils.createClass(pkg, "class2", false);
		
		AssociationClass asso = TestUtils.createAssociationClass(class_, class2_, "test", "test2", "asso");
		assertEquals("asso", asso.getName());
	}

	@Test
	public void testCreateAssociationClassGetAll() {
		Profile profile = TestUtils.createProfile("profile");
		Model model = TestUtils.createModel("model");
		Package pkg = TestUtils.createPackage(model, "pkg");
		
		profile.define();
		model.applyProfile(profile);
		Class class_ = TestUtils.createClass(pkg, "class", false);
		Class class2_ = TestUtils.createClass(pkg, "class2", false);
		
		AssociationReturn asso = TestUtils.createAssociationClassGetAll(class_, class2_, "test", "test2", "asso");
		assertEquals("asso", asso.association.getName());
		assertEquals("test", asso.attributeClassUn.getName());
		assertEquals("test2", asso.attributeClassDeux.getName());
	}

}
