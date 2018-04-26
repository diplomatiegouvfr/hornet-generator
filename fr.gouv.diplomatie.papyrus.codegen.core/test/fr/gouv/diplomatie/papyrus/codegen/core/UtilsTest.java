package fr.gouv.diplomatie.papyrus.codegen.core;

import static org.junit.Assert.*;


import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import fr.gouv.diplomatie.papyrus.codegen.core.test.TestUtils;
import fr.gouv.diplomatie.papyrus.codegen.core.utils.Utils;

import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.Comment;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.Profile;
import org.eclipse.uml2.uml.Stereotype;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.UMLFactory;
import org.eclipse.uml2.uml.UMLPackage;
import org.eclipse.uml2.uml.resource.UMLResource;
import org.eclipse.uml2.uml.*;

public class UtilsTest {
	
	
	@Test
	public void testGetDomainName() throws Exception {
		
		Profile profile = TestUtils.createProfile("profile");
		Model model = TestUtils.createModel("model");
		
		Stereotype stereotype = TestUtils.createStereotype(profile, Utils.MODEL_DOMAIN, false);
		Class packageMetaclass = TestUtils.referenceMetaclass(profile, UMLPackage.Literals.PACKAGE.getName());
        TestUtils.createExtension(packageMetaclass, stereotype, false);
        
		profile.define();
		model.applyProfile(profile);
		
		Package pkg = TestUtils.createPackage(model, "package");
		pkg.applyStereotype(stereotype);
		
		Classifier class_ = TestUtils.createClass(pkg, "maClasse", false);
		
		assertEquals("package", Utils.getDomainName(class_));
	}

	@Test
	public void testGenerateList() {
		List<String> list = Arrays.asList("a", "b", "c", "d");
		String test = Utils.generateList(list);
		assertEquals("\"a\", \"b\", \"c\", \"d\"", test);
	}

	@Test
	public void testLowerCase() {
		String test = "AbCdEF";
		String get = Utils.lowerCase(test);
		assertEquals("abcdef", get);
	}

	@Test
	public void testCapitalize() {
		String test = "AbCdEF";
		String get = Utils.capitalize(test);
		assertEquals("ABCDEF", get);
	}

	@Test
	public void testToDbName() {
		String test = "abCdeFg";
		String get = Utils.toDbName(test);
		assertEquals("AB_CDE_FG", get);
	}

	@Test
	public void testGetAllgene() {
		Profile profile = TestUtils.createProfile("profile");
		Model model = TestUtils.createModel("model");
             
		profile.define();
		model.applyProfile(profile);
		
		Package pkg = TestUtils.createPackage(model, "package");
		
		Classifier class_ = TestUtils.createClass(pkg, "maClasse", false);
		Classifier class2_ = TestUtils.createClass(pkg, "classe2", false);
		Classifier class3_ = TestUtils.createClass(pkg, "classe3", false);
		TestUtils.createGeneralization(class_, class2_);
		TestUtils.createGeneralization(class_, class3_);
		
		Classifier[] classes = new Classifier[2];
		classes[0] = class2_;
		classes[1] = class3_;
		Classifier [] result =  Utils.getAllgene(class_);
		assertEquals(classes , result);
	}

	@Test
	public void testHasType() {
		Profile profile = TestUtils.createProfile("profile");
		Model model = TestUtils.createModel("model");
		
		Stereotype stereotype = TestUtils.createStereotype(profile, "test", false);
		Class packageMetaclass = TestUtils.referenceMetaclass(profile, UMLPackage.Literals.CLASS.getName());
        TestUtils.createExtension(packageMetaclass, stereotype, false);
        
        Stereotype stereotype2 = TestUtils.createStereotype(profile, "test2", false);
        TestUtils.createExtension(packageMetaclass, stereotype2, false);
        
        TestUtils.createGeneralization(stereotype2, stereotype);
        
		profile.define();
		model.applyProfile(profile);
		
		Package pkg = TestUtils.createPackage(model, "package");
		
		Classifier class_ = TestUtils.createClass(pkg, "maClasse", false);
		class_.applyStereotype(stereotype2);
		
		assertEquals(true, Utils.hasType(class_, "test"));
	}

	@Test
	public void testHasStereotype() {
		Profile profile = TestUtils.createProfile("profile");
		Model model = TestUtils.createModel("model");
		
		Stereotype stereotype = TestUtils.createStereotype(profile, Utils.MODEL_ENTITY, false);
		Class packageMetaclass = TestUtils.referenceMetaclass(profile, UMLPackage.Literals.CLASS.getName());
        TestUtils.createExtension(packageMetaclass, stereotype, false);
        
		profile.define();
		model.applyProfile(profile);
		
		Package pkg = TestUtils.createPackage(model, "package");
		
		Classifier class_ = TestUtils.createClass(pkg, "maClasse", false);
		class_.applyStereotype(stereotype);
		
		assertEquals(true, Utils.hasStereotype(class_, Utils.MODEL_ENTITY));
	}

	@Test
	public void testIsEntity() {
		Profile profile = TestUtils.createProfile("profile");
		Model model = TestUtils.createModel("model");
		
		Stereotype stereotype = TestUtils.createStereotype(profile, Utils.MODEL_ENTITY, false);
		Class packageMetaclass = TestUtils.referenceMetaclass(profile, UMLPackage.Literals.CLASS.getName());
        TestUtils.createExtension(packageMetaclass, stereotype, false);
        
		profile.define();
		model.applyProfile(profile);
		
		Package pkg = TestUtils.createPackage(model, "package");
		
		Classifier class_ = TestUtils.createClass(pkg, "maClasse", false);
		class_.applyStereotype(stereotype);
		
		assertEquals(true, Utils.isEntity(class_));
	}

	@Test
	public void testIsValueObject() {
		Profile profile = TestUtils.createProfile("profile");
		Model model = TestUtils.createModel("model");
		
		Stereotype stereotype = TestUtils.createStereotype(profile, Utils.MODEL_VALUEOBJECT, false);
		Class packageMetaclass = TestUtils.referenceMetaclass(profile, UMLPackage.Literals.CLASS.getName());
        TestUtils.createExtension(packageMetaclass, stereotype, false);
        
		profile.define();
		model.applyProfile(profile);
		
		Package pkg = TestUtils.createPackage(model, "package");
		
		Classifier class_ = TestUtils.createClass(pkg, "maClasse", false);
		class_.applyStereotype(stereotype);
		
		assertEquals(true, Utils.isValueObject(class_));
	}

	@Test
	public void testIsSequence() {
		Profile profile = TestUtils.createProfile("profile");
		Model model = TestUtils.createModel("model");
		
		Stereotype stereotype = TestUtils.createStereotype(profile, Utils.MODEL_SEQUENCE, false);
		Class packageMetaclass = TestUtils.referenceMetaclass(profile, UMLPackage.Literals.CLASS.getName());
        TestUtils.createExtension(packageMetaclass, stereotype, false);
        
		profile.define();
		model.applyProfile(profile);
		
		Package pkg = TestUtils.createPackage(model, "package");
		
		Classifier class_ = TestUtils.createClass(pkg, "maClasse", false);
		class_.applyStereotype(stereotype);
		
		assertEquals(true, Utils.isSequence(class_));
	}

	@Test
	public void testIsDomain() {
		Profile profile = TestUtils.createProfile("profile");
		Model model = TestUtils.createModel("model");
		
		Stereotype stereotype = TestUtils.createStereotype(profile, Utils.MODEL_DOMAIN, false);
		Class packageMetaclass = TestUtils.referenceMetaclass(profile, UMLPackage.Literals.PACKAGE.getName());
        TestUtils.createExtension(packageMetaclass, stereotype, false);
        
		profile.define();
		model.applyProfile(profile);
		
		Package pkg = TestUtils.createPackage(model, "package");
		
		pkg.applyStereotype(stereotype);
				
		assertEquals(true, Utils.isDomain(pkg));
	}

	@Test
	public void testIsAssociationTable() {
		Profile profile = TestUtils.createProfile("profile");
		Model model = TestUtils.createModel("model");
		
		Stereotype stereotype = TestUtils.createStereotype(profile, Utils.MODEL_ASSOCIATIONTABLE, false);
		Class packageMetaclass = TestUtils.referenceMetaclass(profile, UMLPackage.Literals.PACKAGE.getName());
        TestUtils.createExtension(packageMetaclass, stereotype, false);
        
		profile.define();
		model.applyProfile(profile);
		
		Package pkg = TestUtils.createPackage(model, "package");
		
		pkg.applyStereotype(stereotype);
				
		assertEquals(true, Utils.isAssociationTable(pkg));
	}

	@Test
	public void testIsAssociationLink() {
		Profile profile = TestUtils.createProfile("profile");
		Model model = TestUtils.createModel("model");
		
		Stereotype stereotype = TestUtils.createStereotype(profile, Utils.MODEL_ASSOCIATIONLINK, false);
		Class packageMetaclass = TestUtils.referenceMetaclass(profile, UMLPackage.Literals.CLASS.getName());
        TestUtils.createExtension(packageMetaclass, stereotype, false);
        
		profile.define();
		model.applyProfile(profile);
		
		Package pkg = TestUtils.createPackage(model, "package");
		
		Classifier class_ = TestUtils.createClass(pkg, "maClasse", false);
		class_.applyStereotype(stereotype);
				
		assertEquals(true, Utils.isAssociationLink(class_));
	}

	@Test
	public void testIsNomenclature() {
		Profile profile = TestUtils.createProfile("profile");
		Model model = TestUtils.createModel("model");
		
		Stereotype stereotype = TestUtils.createStereotype(profile, Utils.MODEL_NOMENCLATURE, false);
		Class packageMetaclass = TestUtils.referenceMetaclass(profile, UMLPackage.Literals.CLASS.getName());
        TestUtils.createExtension(packageMetaclass, stereotype, false);
        
		profile.define();
		model.applyProfile(profile);
		
		Package pkg = TestUtils.createPackage(model, "package");
		
		Classifier class_ = TestUtils.createClass(pkg, "maClasse", false);
		class_.applyStereotype(stereotype);
				
		assertEquals(true, Utils.isNomenclature(class_));
	}

	@Test
	public void testGetStereotype() {
		Profile profile = TestUtils.createProfile("profile");
		Model model = TestUtils.createModel("model");
		
		Stereotype stereotype = TestUtils.createStereotype(profile, Utils.MODEL_ENTITY, false);
		Class packageMetaclass = TestUtils.referenceMetaclass(profile, UMLPackage.Literals.CLASS.getName());
        TestUtils.createExtension(packageMetaclass, stereotype, false);
        
		profile.define();
		model.applyProfile(profile);
		
		Package pkg = TestUtils.createPackage(model, "package");
		
		Classifier class_ = TestUtils.createClass(pkg, "maClasse", false);
		class_.applyStereotype(stereotype);
			
		Iterable<Stereotype> result =Utils.getStereotype(class_, Utils.MODEL_ENTITY);
		assertEquals(stereotype, result.iterator().next());
	}

	@Test
	public void testGetStereotypePropertyValue() {
		Profile profile = TestUtils.createProfile("profile");
		Model model = TestUtils.createModel("model");
		Package pkg = TestUtils.createPackage(model, "package");
		Stereotype stereotype = TestUtils.createStereotype(profile, Utils.MODEL_ENTITY, false);
		
		Type booleanPT = TestUtils.importPrimitiveType(pkg, "Boolean");
		Property attribute = TestUtils.createAttribute(stereotype, Utils.MODEL_ENTITY_GENERATED, booleanPT, 0, 1);
		Class packageMetaclass = TestUtils.referenceMetaclass(profile, UMLPackage.Literals.CLASS.getName());
        TestUtils.createExtension(packageMetaclass, stereotype, false);
        
        
		profile.define();
		model.applyProfile(profile);
		
		
		Classifier class_ = TestUtils.createClass(pkg, "maClasse", false);
		class_.applyStereotype(stereotype);
		TestUtils.setStereotypePropertyValue(class_, stereotype, attribute, true);
		
		
		assertEquals(true, Utils.getStereotypePropertyValue(class_, Utils.MODEL_ENTITY, Utils.MODEL_ENTITY_GENERATED));
		TestUtils.setStereotypePropertyValue(class_, stereotype, attribute, false);
		assertEquals(false, Utils.getStereotypePropertyValue(class_, Utils.MODEL_ENTITY, Utils.MODEL_ENTITY_GENERATED));
	}

	@Test
	public void testToPath() {
		String test = "ab.cd.ef";
		String get = Utils.toPath(test);
		String expect = "ab" + File.separator + "cd" +  File.separator + "ef";
		assertEquals(expect, get);
	}

	@Test
	public void testToImport() {
		String test = "ab" + File.separator + "cd" +  File.separator + "ef";
		String get = Utils.toImport(test);
		String expect = "ab.cd.ef";
		assertEquals(expect, get);
	}

	@Test
	public void testGenerateComments() {
		Profile profile = TestUtils.createProfile("profile");
		Model model = TestUtils.createModel("model");
		
  
		profile.define();
		model.applyProfile(profile);
		
		Package pkg = TestUtils.createPackage(model, "package");
		
		Classifier class_ = TestUtils.createClass(pkg, "maClasse", false);
		Comment comment = class_.createOwnedComment();
		comment.setBody("test");
			
		String generated = Utils.generateComments(class_);
		String expected = "/**\n* test\n*/\n";
		assertEquals(expected,generated);
		
	}

	@Test
	public void testGetFirstToLowerCase() {
		String test = "Abcdef";
		String get = Utils.getFirstToLowerCase(test);
		String expect = "abcdef";
		assertEquals(expect, get);
	}

	@Test
	public void testGetFirstToUpperCase() {
		String test = "abcdef";
		String get = Utils.getFirstToUpperCase(test);
		String expect = "Abcdef";
		assertEquals(expect, get);
	}

	@Test
	public void testToSnakeCase() {
		String test = "abCdEf";
		String get = Utils.toSnakeCase(test);
		String expect = "ab_cd_ef";
		assertEquals(expect, get);
	}

	@Test
	public void testToTypescriptFileName() {
		String test = "abCdEf";
		String get = Utils.toTypescriptFileName(test);
		String expect = "ab-cd-ef";
		assertEquals(expect, get);
	}

	@Test
	public void testAddAdditionnalName() {
		String test = "abcdef";
		String addName = "test";
		String get = Utils.addAdditionnalName(addName, test);
		String expect = "testAbcdef";
		assertEquals(expect, get);
	}

	@Test
	public void testGetListPoint() {
		ArrayList<String> test = new ArrayList<String>();
		test.addAll(Arrays.asList("a","b","c"));
		String get = Utils.getListPoint(test);
		String expect = "a.b.c";
		assertEquals(expect, get);
	}

	@Test
	public void testGetListComma() {
		ArrayList<String> test = new ArrayList<String>();
		test.addAll(Arrays.asList("a","b","c"));
		String get = Utils.getListComma(test);
		String expect = "a, b, c";
		assertEquals(expect, get);
	}

	@Test
	public void testGetNameFromList() {
		ArrayList<String> test = new ArrayList<String>();
		test.addAll(Arrays.asList("ab","cd","ef"));
		String get = Utils.getNameFromList(test);
		String expect = "abCdEf";
		assertEquals(expect, get);
	}

	@Test
	public void testGetRootPackage() {
		Profile profile = TestUtils.createProfile("profile");
		Model model = TestUtils.createModel("model");
		Package pkg = TestUtils.createPackage(model, "package");
		
		Stereotype stereotype = TestUtils.createStereotype(profile, Utils.MODEL_APPLICATION, false);
		
		Type booleanPT = TestUtils.importPrimitiveType(pkg, "String");
		Property attribute = TestUtils.createAttribute(stereotype, Utils.MODEL_APPLICATION_ROOTPACKAGE, booleanPT, 0, 1);
		Class packageMetaclass = TestUtils.referenceMetaclass(profile, UMLPackage.Literals.MODEL.getName());
        TestUtils.createExtension(packageMetaclass, stereotype, false);
        
		profile.define();
		model.applyProfile(profile);
		
		Classifier class_ = TestUtils.createClass(pkg, "maClasse", false);
		
		model.applyStereotype(stereotype);
		TestUtils.setStereotypePropertyValue(model, stereotype, attribute, "test");
		
		assertEquals("test", Utils.getRootPackage(class_));
	}

	@Test
	public void testGetPackagePath() {
		Profile profile = TestUtils.createProfile("profile");
		Model model = TestUtils.createModel("model");
		Package pkg = TestUtils.createPackage(model, "package");
		
		Stereotype stereotype = TestUtils.createStereotype(profile, Utils.MODEL_APPLICATION, false);
		
		Type booleanPT = TestUtils.importPrimitiveType(pkg, "String");
		Property attribute = TestUtils.createAttribute(stereotype, Utils.MODEL_APPLICATION_ROOTPACKAGE, booleanPT, 0, 1);
		Class packageMetaclass = TestUtils.referenceMetaclass(profile, UMLPackage.Literals.MODEL.getName());
        TestUtils.createExtension(packageMetaclass, stereotype, false);
        
		profile.define();
		model.applyProfile(profile);
		
		Classifier class_ = TestUtils.createClass(pkg, "maClasse", false);
		
		model.applyStereotype(stereotype);
		TestUtils.setStereotypePropertyValue(model, stereotype, attribute, "ab.cd.ef");
		
		String expected = "ab" + File.separator + "cd" + File.separator + "ef";
		
		assertEquals(expected, Utils.getPackagePath(class_));
	}

	@Test
	public void testContainsName() {
		Model model = TestUtils.createModel("model");
		Model modelDeux = TestUtils.createModel("modelDeux");
		ArrayList<NamedElement> list = new ArrayList<NamedElement>();
		list.add(model);
		list.add(modelDeux);
		assertEquals(true, Utils.containsName(list, "model"));
		assertEquals(false, Utils.containsName(list, "modelTrois"));
	}

	@Test
	public void testIsType() {
		Profile profile = TestUtils.createProfile("profile");
		Model model = TestUtils.createModel("model");
        
		profile.define();
		model.applyProfile(profile);
		
		Package pkg = TestUtils.createPackage(model, "package");
		
		Classifier class_ = TestUtils.createClass(pkg, "maClasse", false);
		Classifier otherClass_ = TestUtils.createClass(pkg, "monAutreClasse", false);
		TestUtils.createGeneralization(class_, otherClass_);
		
		assertEquals(true, Utils.isType(class_, "monAutreClasse"));
		assertEquals(true, Utils.isType(otherClass_, "monAutreClasse"));
	}

	@Test
	public void testGetNomenclatureCode() {
		Profile profile = TestUtils.createProfile("profile");
		Model model = TestUtils.createModel("model");
		Package pkg = TestUtils.createPackage(model, "package");
		
		Stereotype stereotype = TestUtils.createStereotype(profile, Utils.MODEL_CODELIBELLENOMENCLATURE, false);
		
		Type integerPT = TestUtils.importPrimitiveType(pkg, "Integer");
		Property attribute = TestUtils.createAttribute(stereotype, Utils.MODEL_CODELIBELLENOMENCLATURE_CODE, integerPT, 0, 1);
		Class packageMetaclass = TestUtils.referenceMetaclass(profile, UMLPackage.Literals.CLASS.getName());
        TestUtils.createExtension(packageMetaclass, stereotype, false);
        
		profile.define();
		model.applyProfile(profile);
		
		Classifier class_ = TestUtils.createClass(pkg, "maClasse", false);
		
		class_.applyStereotype(stereotype);
		
		TestUtils.setStereotypePropertyValue(class_, stereotype, attribute, 1);
		assertEquals(1, Utils.getNomenclatureCode(class_));
		
		TestUtils.setStereotypePropertyValue(class_, stereotype, attribute, 2);
		assertEquals(2, Utils.getNomenclatureCode(class_));
	}

	@Test
	public void testGetNomenclatureLibelle() {
		Profile profile = TestUtils.createProfile("profile");
		Model model = TestUtils.createModel("model");
		Package pkg = TestUtils.createPackage(model, "package");
		
		Stereotype stereotype = TestUtils.createStereotype(profile, Utils.MODEL_CODELIBELLENOMENCLATURE, false);
		
		Type stringPT = TestUtils.importPrimitiveType(pkg, "String");
		Property attribute = TestUtils.createAttribute(stereotype, Utils.MODEL_CODELIBELLENOMENCLATURE_LIBELLE, stringPT, 0, 1);
		Class packageMetaclass = TestUtils.referenceMetaclass(profile, UMLPackage.Literals.CLASS.getName());
        TestUtils.createExtension(packageMetaclass, stereotype, false);
        
		profile.define();
		model.applyProfile(profile);
		
		Classifier class_ = TestUtils.createClass(pkg, "maClasse", false);
		
		class_.applyStereotype(stereotype);
		
		TestUtils.setStereotypePropertyValue(class_, stereotype, attribute, "test");
		assertEquals("test", Utils.getNomenclatureLibelle(class_));
		
		TestUtils.setStereotypePropertyValue(class_, stereotype, attribute, "testDeux");
		assertEquals("testDeux", Utils.getNomenclatureLibelle(class_));
	}

	@Test
	public void testAttributeGetAttributeLength() {
		Profile profile = TestUtils.createProfile("profile");
		Model model = TestUtils.createModel("model");
		Package pkg = TestUtils.createPackage(model, "package");
		
		Stereotype stereotype = TestUtils.createStereotype(profile, Utils.MODEL_ATTRIBUTE, false);
		
		Type pt = TestUtils.importPrimitiveType(pkg, "Integer");
		Property attribute = TestUtils.createAttribute(stereotype, Utils.MODEL_ATTRIBUTE_LENGTH, pt, 0, 1);
		Type pt2 = TestUtils.importPrimitiveType(pkg, "Boolean");
		Property attribute2 = TestUtils.createAttribute(stereotype, "hasLength", pt2, 0, 1);
		Class packageMetaclass = TestUtils.referenceMetaclass(profile, UMLPackage.Literals.CLASS.getName());
        TestUtils.createExtension(packageMetaclass, stereotype, false);
        
		profile.define();
		model.applyProfile(profile);
		
		Classifier class_ = TestUtils.createClass(pkg, "maClasse", false);
		
		class_.applyStereotype(stereotype);
		
		TestUtils.setStereotypePropertyValue(class_, stereotype, attribute, 1);
		TestUtils.setStereotypePropertyValue(class_, stereotype, attribute2, false);
		assertEquals(null, Utils.getAttributeLength(class_));
		
		TestUtils.setStereotypePropertyValue(class_, stereotype, attribute2, true);
		assertEquals(1, Utils.getAttributeLength(class_));
		
		TestUtils.setStereotypePropertyValue(class_, stereotype, attribute, 2);
		assertEquals(2, Utils.getAttributeLength(class_));
	}
	
	@Test
	public void testKeyAttributeGetAttributeLength() {
		Profile profile = TestUtils.createProfile("profile");
		Model model = TestUtils.createModel("model");
		Package pkg = TestUtils.createPackage(model, "package");
		
		Stereotype stereotype = TestUtils.createStereotype(profile, Utils.MODEL_KEYATTRIBUTE, false);
		
		Type pt = TestUtils.importPrimitiveType(pkg, "Integer");
		Property attribute = TestUtils.createAttribute(stereotype, Utils.MODEL_ATTRIBUTE_LENGTH, pt, 0, 1);
		Type pt2 = TestUtils.importPrimitiveType(pkg, "Boolean");
		Property attribute2 = TestUtils.createAttribute(stereotype, "hasLength", pt2, 0, 1);
		Class packageMetaclass = TestUtils.referenceMetaclass(profile, UMLPackage.Literals.CLASS.getName());
        TestUtils.createExtension(packageMetaclass, stereotype, false);
        
		profile.define();
		model.applyProfile(profile);
		
		Classifier class_ = TestUtils.createClass(pkg, "maClasse", false);
		
		class_.applyStereotype(stereotype);
		
		TestUtils.setStereotypePropertyValue(class_, stereotype, attribute, 1);
		TestUtils.setStereotypePropertyValue(class_, stereotype, attribute2, false);
		assertEquals(null, Utils.getAttributeLength(class_));
		
		TestUtils.setStereotypePropertyValue(class_, stereotype, attribute2, true);
		assertEquals(1, Utils.getAttributeLength(class_));
		
		TestUtils.setStereotypePropertyValue(class_, stereotype, attribute, 2);
		assertEquals(2, Utils.getAttributeLength(class_));
	}

	@Test
	public void testGetSequenceStartWith() {
		Profile profile = TestUtils.createProfile("profile");
		Model model = TestUtils.createModel("model");
		Package pkg = TestUtils.createPackage(model, "package");
		
		Stereotype stereotype = TestUtils.createStereotype(profile, Utils.MODEL_SEQUENCE, false);
		
		Type pt = TestUtils.importPrimitiveType(pkg, "Integer");
		Property attribute = TestUtils.createAttribute(stereotype, Utils.MODEL_SEQUENCE_STARTWITH, pt, 0, 1);
		Class packageMetaclass = TestUtils.referenceMetaclass(profile, UMLPackage.Literals.CLASS.getName());
        TestUtils.createExtension(packageMetaclass, stereotype, false);
        
		profile.define();
		model.applyProfile(profile);
		
		Classifier class_ = TestUtils.createClass(pkg, "maClasse", false);
		
		class_.applyStereotype(stereotype);
		
		TestUtils.setStereotypePropertyValue(class_, stereotype, attribute, 1);
		assertEquals(1, Utils.getSequenceStartWith(class_));
		
		TestUtils.setStereotypePropertyValue(class_, stereotype, attribute, 2);
		assertEquals(2, Utils.getSequenceStartWith(class_));
	}

	@Test
	public void testGetSequenceIncrementBy() {
		Profile profile = TestUtils.createProfile("profile");
		Model model = TestUtils.createModel("model");
		Package pkg = TestUtils.createPackage(model, "package");
		
		Stereotype stereotype = TestUtils.createStereotype(profile, Utils.MODEL_SEQUENCE, false);
		
		Type pt = TestUtils.importPrimitiveType(pkg, "Integer");
		Property attribute = TestUtils.createAttribute(stereotype, Utils.MODEL_SEQUENCE_INCREMENTBY, pt, 0, 1);
		Class packageMetaclass = TestUtils.referenceMetaclass(profile, UMLPackage.Literals.CLASS.getName());
        TestUtils.createExtension(packageMetaclass, stereotype, false);
        
		profile.define();
		model.applyProfile(profile);
		
		Classifier class_ = TestUtils.createClass(pkg, "maClasse", false);
		
		class_.applyStereotype(stereotype);
		
		TestUtils.setStereotypePropertyValue(class_, stereotype, attribute, 1);
		assertEquals(1, Utils.getSequenceIncrementBy(class_));
		
		TestUtils.setStereotypePropertyValue(class_, stereotype, attribute, 2);
		assertEquals(2, Utils.getSequenceIncrementBy(class_));
	}

	@Test
	public void testGetSequenceHasMaxValue() {
		Profile profile = TestUtils.createProfile("profile");
		Model model = TestUtils.createModel("model");
		Package pkg = TestUtils.createPackage(model, "package");
		
		Stereotype stereotype = TestUtils.createStereotype(profile, Utils.MODEL_SEQUENCE, false);
		
		Type pt = TestUtils.importPrimitiveType(pkg, "Boolean");
		Property attribute = TestUtils.createAttribute(stereotype, Utils.MODEL_SEQUENCE_HASMAXVALUE, pt, 0, 1);
		Class packageMetaclass = TestUtils.referenceMetaclass(profile, UMLPackage.Literals.CLASS.getName());
        TestUtils.createExtension(packageMetaclass, stereotype, false);
        
		profile.define();
		model.applyProfile(profile);
		
		Classifier class_ = TestUtils.createClass(pkg, "maClasse", false);
		
		class_.applyStereotype(stereotype);
		
		TestUtils.setStereotypePropertyValue(class_, stereotype, attribute, true);
		assertEquals(true, Utils.getSequenceHasMaxValue(class_));
		
		TestUtils.setStereotypePropertyValue(class_, stereotype, attribute, false);
		assertEquals(false, Utils.getSequenceHasMaxValue(class_));
	}

	@Test
	public void testGetSequenceMaxValue() {
		Profile profile = TestUtils.createProfile("profile");
		Model model = TestUtils.createModel("model");
		Package pkg = TestUtils.createPackage(model, "package");
		
		Stereotype stereotype = TestUtils.createStereotype(profile, Utils.MODEL_SEQUENCE, false);
		
		Type pt = TestUtils.importPrimitiveType(pkg, "Integer");
		Property attribute = TestUtils.createAttribute(stereotype, Utils.MODEL_SEQUENCE_MAXVALUE, pt, 0, 1);
		Class packageMetaclass = TestUtils.referenceMetaclass(profile, UMLPackage.Literals.CLASS.getName());
        TestUtils.createExtension(packageMetaclass, stereotype, false);
        
		profile.define();
		model.applyProfile(profile);
		
		Classifier class_ = TestUtils.createClass(pkg, "maClasse", false);
		
		class_.applyStereotype(stereotype);
		
		TestUtils.setStereotypePropertyValue(class_, stereotype, attribute, 1);
		assertEquals(1, Utils.getSequenceMaxValue(class_));
		
		TestUtils.setStereotypePropertyValue(class_, stereotype, attribute, 2);
		assertEquals(2, Utils.getSequenceMaxValue(class_));
	}

	@Test
	public void testGetSequenceHasMinValue() {
		Profile profile = TestUtils.createProfile("profile");
		Model model = TestUtils.createModel("model");
		Package pkg = TestUtils.createPackage(model, "package");
		
		Stereotype stereotype = TestUtils.createStereotype(profile, Utils.MODEL_SEQUENCE, false);
		
		Type pt = TestUtils.importPrimitiveType(pkg, "Boolean");
		Property attribute = TestUtils.createAttribute(stereotype, Utils.MODEL_SEQUENCE_HASMINVALUE, pt, 0, 1);
		Class packageMetaclass = TestUtils.referenceMetaclass(profile, UMLPackage.Literals.CLASS.getName());
        TestUtils.createExtension(packageMetaclass, stereotype, false);
        
		profile.define();
		model.applyProfile(profile);
		
		Classifier class_ = TestUtils.createClass(pkg, "maClasse", false);
		
		class_.applyStereotype(stereotype);
		
		TestUtils.setStereotypePropertyValue(class_, stereotype, attribute, true);
		assertEquals(true, Utils.getSequenceHasMinValue(class_));
		
		TestUtils.setStereotypePropertyValue(class_, stereotype, attribute, false);
		assertEquals(false, Utils.getSequenceHasMinValue(class_));
	}

	@Test
	public void testGetSequenceMinValue() {
		Profile profile = TestUtils.createProfile("profile");
		Model model = TestUtils.createModel("model");
		Package pkg = TestUtils.createPackage(model, "package");
		
		Stereotype stereotype = TestUtils.createStereotype(profile, Utils.MODEL_SEQUENCE, false);
		
		Type pt = TestUtils.importPrimitiveType(pkg, "Integer");
		Property attribute = TestUtils.createAttribute(stereotype, Utils.MODEL_SEQUENCE_MINVALUE, pt, 0, 1);
		Class packageMetaclass = TestUtils.referenceMetaclass(profile, UMLPackage.Literals.CLASS.getName());
        TestUtils.createExtension(packageMetaclass, stereotype, false);
        
		profile.define();
		model.applyProfile(profile);
		
		Classifier class_ = TestUtils.createClass(pkg, "maClasse", false);
		
		class_.applyStereotype(stereotype);
		
		TestUtils.setStereotypePropertyValue(class_, stereotype, attribute, 1);
		assertEquals(1, Utils.getSequenceMinValue(class_));
		
		TestUtils.setStereotypePropertyValue(class_, stereotype, attribute, 2);
		assertEquals(2, Utils.getSequenceMinValue(class_));
	}

	@Test
	public void testGetSequenceCache() {
		Profile profile = TestUtils.createProfile("profile");
		Model model = TestUtils.createModel("model");
		Package pkg = TestUtils.createPackage(model, "package");
		
		Stereotype stereotype = TestUtils.createStereotype(profile, Utils.MODEL_SEQUENCE, false);
		
		Type pt = TestUtils.importPrimitiveType(pkg, "Integer");
		Property attribute = TestUtils.createAttribute(stereotype, Utils.MODEL_SEQUENCE_CACHE, pt, 0, 1);
		Class packageMetaclass = TestUtils.referenceMetaclass(profile, UMLPackage.Literals.CLASS.getName());
        TestUtils.createExtension(packageMetaclass, stereotype, false);
        
		profile.define();
		model.applyProfile(profile);
		
		Classifier class_ = TestUtils.createClass(pkg, "maClasse", false);
		
		class_.applyStereotype(stereotype);
		
		TestUtils.setStereotypePropertyValue(class_, stereotype, attribute, 1);
		assertEquals(1, Utils.getSequenceCache(class_));
		
		TestUtils.setStereotypePropertyValue(class_, stereotype, attribute, 2);
		assertEquals(2, Utils.getSequenceCache(class_));
	}

	@Test
	public void testGetSequenceCycle() {
		Profile profile = TestUtils.createProfile("profile");
		Model model = TestUtils.createModel("model");
		Package pkg = TestUtils.createPackage(model, "package");
		
		Stereotype stereotype = TestUtils.createStereotype(profile, Utils.MODEL_SEQUENCE, false);
		
		Type pt = TestUtils.importPrimitiveType(pkg, "Boolean");
		Property attribute = TestUtils.createAttribute(stereotype, Utils.MODEL_SEQUENCE_CYCLE, pt, 0, 1);
		Class packageMetaclass = TestUtils.referenceMetaclass(profile, UMLPackage.Literals.CLASS.getName());
        TestUtils.createExtension(packageMetaclass, stereotype, false);
        
		profile.define();
		model.applyProfile(profile);
		
		Classifier class_ = TestUtils.createClass(pkg, "maClasse", false);
		
		class_.applyStereotype(stereotype);
		
		TestUtils.setStereotypePropertyValue(class_, stereotype, attribute, true);
		assertEquals(true, Utils.getSequenceCycle(class_));
		
		TestUtils.setStereotypePropertyValue(class_, stereotype, attribute, false);
		assertEquals(false, Utils.getSequenceCycle(class_));
	}
	
	@Test
	public void testGetGenerated() {
		Profile profile = TestUtils.createProfile("profile");
		Model model = TestUtils.createModel("model");
		Package pkg = TestUtils.createPackage(model, "package");
		
		profile.define();
		model.applyProfile(profile);
		
		Classifier class_ = TestUtils.createClass(pkg, "maClasse", false);
		
		assertEquals(true, Utils.getGenerated(class_));
	}

	@Test
	public void testEntityGetGenerated() {
		Profile profile = TestUtils.createProfile("profile");
		Model model = TestUtils.createModel("model");
		Package pkg = TestUtils.createPackage(model, "package");
		
		Stereotype stereotype = TestUtils.createStereotype(profile, Utils.MODEL_ENTITY, false);
		
		Type pt = TestUtils.importPrimitiveType(pkg, "Boolean");
		Property attribute = TestUtils.createAttribute(stereotype, Utils.MODEL_ENTITY_GENERATED, pt, 0, 1);
		Class packageMetaclass = TestUtils.referenceMetaclass(profile, UMLPackage.Literals.CLASS.getName());
        TestUtils.createExtension(packageMetaclass, stereotype, false);
        
		profile.define();
		model.applyProfile(profile);
		
		Classifier class_ = TestUtils.createClass(pkg, "maClasse", false);
		
		class_.applyStereotype(stereotype);
		
		TestUtils.setStereotypePropertyValue(class_, stereotype, attribute, true);
		assertEquals(true, Utils.getGenerated(class_));
		
		TestUtils.setStereotypePropertyValue(class_, stereotype, attribute, false);
		assertEquals(false, Utils.getGenerated(class_));
	}
	
	@Test
	public void testNomenclatureGetGenerated() {
		Profile profile = TestUtils.createProfile("profile");
		Model model = TestUtils.createModel("model");
		Package pkg = TestUtils.createPackage(model, "package");
		
		Stereotype stereotype = TestUtils.createStereotype(profile, Utils.MODEL_NOMENCLATURE, false);
		
		Type pt = TestUtils.importPrimitiveType(pkg, "Boolean");
		Property attribute = TestUtils.createAttribute(stereotype, Utils.MODEL_NOMENCLATURE_GENERATED, pt, 0, 1);
		Class packageMetaclass = TestUtils.referenceMetaclass(profile, UMLPackage.Literals.CLASS.getName());
        TestUtils.createExtension(packageMetaclass, stereotype, false);
        
		profile.define();
		model.applyProfile(profile);
		
		Classifier class_ = TestUtils.createClass(pkg, "maClasse", false);
		
		class_.applyStereotype(stereotype);
		
		TestUtils.setStereotypePropertyValue(class_, stereotype, attribute, true);
		assertEquals(true, Utils.getGenerated(class_));
		
		TestUtils.setStereotypePropertyValue(class_, stereotype, attribute, false);
		assertEquals(false, Utils.getGenerated(class_));
	}
	
	@Test
	public void testValueObjectGetGenerated() {
		Profile profile = TestUtils.createProfile("profile");
		Model model = TestUtils.createModel("model");
		Package pkg = TestUtils.createPackage(model, "package");
		
		Stereotype stereotype = TestUtils.createStereotype(profile, Utils.MODEL_VALUEOBJECT, false);
		
		Type pt = TestUtils.importPrimitiveType(pkg, "Boolean");
		Property attribute = TestUtils.createAttribute(stereotype, Utils.MODEL_VALUEOBJECT_GENERATED, pt, 0, 1);
		Class packageMetaclass = TestUtils.referenceMetaclass(profile, UMLPackage.Literals.CLASS.getName());
        TestUtils.createExtension(packageMetaclass, stereotype, false);
        
		profile.define();
		model.applyProfile(profile);
		
		Classifier class_ = TestUtils.createClass(pkg, "maClasse", false);
		
		class_.applyStereotype(stereotype);
		
		TestUtils.setStereotypePropertyValue(class_, stereotype, attribute, true);
		assertEquals(true, Utils.getGenerated(class_));
		
		TestUtils.setStereotypePropertyValue(class_, stereotype, attribute, false);
		assertEquals(false, Utils.getGenerated(class_));
	}
	
}
