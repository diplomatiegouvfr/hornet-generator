package fr.gouv.diplomatie.papyrus.codegen.core.test;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.uml2.uml.AggregationKind;
import org.eclipse.uml2.uml.Association;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.Comment;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.Profile;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Stereotype;
import org.eclipse.uml2.uml.UMLPackage;
import org.junit.Test;

import fr.gouv.diplomatie.papyrus.codegen.core.test.HornetModel;
import fr.gouv.diplomatie.papyrus.codegen.core.test.TestUtils;
import fr.gouv.diplomatie.papyrus.codegen.core.utils.Utils;

public class UtilsTest {
	
	@Test
	public void testClass() {
		Utils test = new Utils();
		assertEquals(Utils.class, test.getClass());
	}
	
	@Test
	public void testGetDomainName() throws Exception {
		
		HornetModel hmodel = HornetModel.initModel();
		
		Package pkg = hmodel.pckage;
		pkg.applyStereotype(hmodel.domain);
		
		Classifier class_ = TestUtils.createClass(pkg, "maClasse", false);
		
		assertEquals("package", Utils.getDomainName(class_));
	}
	
	@Test
	public void testNoDomainGetDomainName() throws Exception {
		HornetModel hmodel = HornetModel.initModel();
		
		Package pkg = hmodel.pckage;
		
		Classifier class_ = TestUtils.createClass(pkg, "maClasse", false);
		
		assertEquals("", Utils.getDomainName(class_));
	}

	@Test
	public void testGenerateList() {
		List<String> list = Arrays.asList("a", "b", "c", "d");
		String test = Utils.generateList(list);
		assertEquals("\"a\", \"b\", \"c\", \"d\"", test);
	}
	
	@Test
	public void testNamedElementGenerateList() {
		HornetModel hmodel = HornetModel.initModel();
		Classifier class_ = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		Classifier class2_ = TestUtils.createClass(hmodel.pckage, "maClasse2", false);
		ArrayList<NamedElement> list = new ArrayList<NamedElement>();
		list.add(class_);
		list.add(class2_);
		String test = Utils.generateList(list);
		assertEquals("\"maClasse\", \"maClasse2\"", test);
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
		
		assertEquals("",  Utils.capitalize(""));
		assertEquals(null,  Utils.capitalize(null));
		assertEquals("S",  Utils.capitalize("s"));
	}

	@Test
	public void testToDbName() {
		String test = "abCdeFg";
		String get = Utils.toDbName(test);
		assertEquals("AB_CDE_FG", get);
	}

	@Test
	public void testGetAllgene() {
		HornetModel hmodel = HornetModel.initModel();
		
		Classifier class_ = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		Classifier class2_ = TestUtils.createClass(hmodel.pckage, "classe2", false);
		Classifier class3_ = TestUtils.createClass(hmodel.pckage, "classe3", false);
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
		HornetModel hmodel = HornetModel.initModel();
				
		Classifier class_ = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		class_.applyStereotype(hmodel.entity);
		
		assertEquals(true, Utils.hasStereotype(class_, Utils.MODEL_ENTITY));
	}
	
	@Test
	public void testNullHasStereotype() {
		assertEquals(false, Utils.hasStereotype(null, Utils.MODEL_ENTITY));
	}

	@Test
	public void testIsEntity() {
		HornetModel hmodel = HornetModel.initModel();
		
		Classifier class_ = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		class_.applyStereotype(hmodel.entity);
		
		assertEquals(true, Utils.isEntity(class_));
	}

	@Test
	public void testIsValueObject() {
		HornetModel hmodel = HornetModel.initModel();
		
		Classifier class_ = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		class_.applyStereotype(hmodel.valueObject);
		
		assertEquals(true, Utils.isValueObject(class_));
	}

	@Test
	public void testIsSequence() {
		HornetModel hmodel = HornetModel.initModel();
		
		Class class_ = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		Property prop = TestUtils.createAttribute(class_, "test", hmodel.stringPT, 0, 1);
		prop.applyStereotype(hmodel.sequence);
		
		assertEquals(true, Utils.isSequence(prop));
	}

	@Test
	public void testIsDomain() {
		HornetModel hmodel = HornetModel.initModel();
		
		Package pkg = hmodel.pckage;
		pkg.applyStereotype(hmodel.domain);
				
		assertEquals(true, Utils.isDomain(pkg));
	}

	@Test
	public void testIsAssociationTable() {
		HornetModel hmodel = HornetModel.initModel();
		
		Classifier class_ = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		class_.applyStereotype(hmodel.associationTable);
				
		assertEquals(true, Utils.isAssociationTable(class_));
	}

	@Test
	public void testIsAssociationLink() {
		HornetModel hmodel = HornetModel.initModel();
		
		Classifier class_ = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		Classifier class2_ = TestUtils.createClass(hmodel.pckage, "maClasse2", false);
		Association asso = class_.createAssociation(true, AggregationKind.NONE_LITERAL, "test", 0, 1, class2_, true, AggregationKind.NONE_LITERAL, "test2", 0, 1);
		asso.applyStereotype(hmodel.associationLink);			
		assertEquals(true, Utils.isAssociationLink(asso));
	}

	@Test
	public void testIsNomenclature() {
		HornetModel hmodel = HornetModel.initModel();
		
		Classifier class_ = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		class_.applyStereotype(hmodel.nomenclature);
				
		assertEquals(true, Utils.isNomenclature(class_));
	}

	@Test
	public void testGetStereotype() {
		HornetModel hmodel = HornetModel.initModel();
		
		Classifier class_ = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		class_.applyStereotype(hmodel.entity);
			
		Iterable<Stereotype> result =Utils.getStereotype(class_, Utils.MODEL_ENTITY);
		assertEquals(hmodel.entity, result.iterator().next());
	}

	@Test
	public void testGetStereotypePropertyValue() {
		HornetModel hmodel = HornetModel.initModel();
		
		Classifier class_ = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		class_.applyStereotype(hmodel.entity);
		TestUtils.setStereotypePropertyValue(class_, hmodel.entity, hmodel.entityGenerated, true);
		
		
		assertEquals(true, Utils.getStereotypePropertyValue(class_, Utils.MODEL_ENTITY, Utils.MODEL_ENTITY_GENERATED));
		TestUtils.setStereotypePropertyValue(class_, hmodel.entity, hmodel.entityGenerated, false);
		assertEquals(false, Utils.getStereotypePropertyValue(class_, Utils.MODEL_ENTITY, Utils.MODEL_ENTITY_GENERATED));
	}
	
	@Test
	public void testNotGetStereotypePropertyValue() {
		HornetModel hmodel = HornetModel.initModel();
		Classifier class_ = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		assertEquals(null, Utils.getStereotypePropertyValue(class_, Utils.MODEL_ENTITY, Utils.MODEL_ENTITY_GENERATED));
		
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
		HornetModel hmodel = HornetModel.initModel();
		
		Classifier class_ = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		Comment comment = class_.createOwnedComment();
		comment.setBody("test");
			
		String generated = Utils.generateComments(class_);
		String expected = "/**\n* test\n*/\n";
		assertEquals(expected,generated);
		
	}
	
	@Test
	public void testNoCommentGenerateComments() {
		HornetModel hmodel = HornetModel.initModel();
		
		Classifier class_ = TestUtils.createClass(hmodel.pckage, "maClasse", false);
			
		String generated = Utils.generateComments(class_);
		String expected = "";
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
	public void testNoAddNameAddAdditionnalName() {
		String test = "abcdef";
		String get = Utils.addAdditionnalName("", test);
		String expect = "abcdef";
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
	public void testGetListStringComma() {
		ArrayList<String> test = new ArrayList<String>();
		test.addAll(Arrays.asList("a","b","c"));
		String get = Utils.getListStringComma(test);
		String expect = "\"a\", \"b\", \"c\"";
		assertEquals(expect, get);
	}

	@Test
	public void testGetRootPackage() {
		HornetModel hmodel = HornetModel.initModel();
		
		Classifier class_ = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		
		Model model = hmodel.model;
		model.applyStereotype(hmodel.application);
		TestUtils.setStereotypePropertyValue(model, hmodel.application, hmodel.applicationRootPackage, "test");
		
		assertEquals("test", Utils.getRootPackage(class_));
	}
	
	@Test
	public void testNullGetRootPackage() {
		assertEquals(null, Utils.getRootPackage(null));
	}

	@Test
	public void testGetPackagePath() {
		HornetModel hmodel = HornetModel.initModel();
		
		Classifier class_ = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		
		Model model = hmodel.model;
		model.applyStereotype(hmodel.application);
		TestUtils.setStereotypePropertyValue(model, hmodel.application, hmodel.applicationRootPackage, "ab.cd.ef");
		
		String expected = "ab" + File.separator + "cd" + File.separator + "ef";
		
		assertEquals(expected, Utils.getPackagePath(class_));
	}
	
	@Test
	public void testDefaultGetPackagePath() {
		HornetModel hmodel = HornetModel.initModel();
		
		Classifier class_ = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		
		Model model = hmodel.model;
		model.applyStereotype(hmodel.application);
		
		String expected = "fr" + File.separator + "gouv" + File.separator + "diplomatie";
		
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
		HornetModel hmodel = HornetModel.initModel();

		Classifier class_ = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		Classifier otherClass_ = TestUtils.createClass(hmodel.pckage, "monAutreClasse", false);
		TestUtils.createGeneralization(class_, otherClass_);
		
		assertEquals(true, Utils.isType(class_, "monAutreClasse"));
		assertEquals(true, Utils.isType(otherClass_, "monAutreClasse"));
	}
	
	@Test
	public void testNotIsType() {
		HornetModel hmodel = HornetModel.initModel();

		Classifier class_ = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		TestUtils.createClass(hmodel.pckage, "monAutreClasse", false);
		assertEquals(false, Utils.isType(class_, "monAutreClasse"));
	}

	@Test
	public void testGetNomenclatureCode() {
		HornetModel hmodel = HornetModel.initModel();

		Class class_ = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		class_.applyStereotype(hmodel.nomenclature);
		Property prop = TestUtils.createAttribute(class_, "test", hmodel.stringPT, 0, 1);
		prop.applyStereotype(hmodel.codeLibelleNomenclature);
		
		TestUtils.setStereotypePropertyValue(prop, hmodel.codeLibelleNomenclature, hmodel.codeLibelleNomenclatureCode, 1);
		assertEquals(1, Utils.getNomenclatureCode(prop));
		
		TestUtils.setStereotypePropertyValue(prop, hmodel.codeLibelleNomenclature, hmodel.codeLibelleNomenclatureCode, 2);
		assertEquals(2, Utils.getNomenclatureCode(prop));
	}

	@Test
	public void testGetNomenclatureLibelle() {
		HornetModel hmodel = HornetModel.initModel();
		
		Class class_ = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		class_.applyStereotype(hmodel.nomenclature);
		Property prop = TestUtils.createAttribute(class_, "test", hmodel.stringPT, 0, 1);
		prop.applyStereotype(hmodel.codeLibelleNomenclature);
		
		TestUtils.setStereotypePropertyValue(prop, hmodel.codeLibelleNomenclature, hmodel.codeLibelleNomenclatureLibelle, "test");
		assertEquals("test", Utils.getNomenclatureLibelle(prop));
		
		TestUtils.setStereotypePropertyValue(prop, hmodel.codeLibelleNomenclature, hmodel.codeLibelleNomenclatureLibelle, "testDeux");
		assertEquals("testDeux", Utils.getNomenclatureLibelle(prop));
	}

	@Test
	public void testAttributeGetAttributeLength() {
		HornetModel hmodel = HornetModel.initModel();
		
		Class class_ = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		Property prop = TestUtils.createAttribute(class_, "test", hmodel.stringPT, 0, 1);
		
		prop.applyStereotype(hmodel.attribute);
		
		TestUtils.setStereotypePropertyValue(prop, hmodel.attribute, hmodel.attributeLength, 1);
		TestUtils.setStereotypePropertyValue(prop, hmodel.attribute, hmodel.attributeHasLength, false);
		assertEquals(null, Utils.getAttributeLength(prop));
		
		TestUtils.setStereotypePropertyValue(prop, hmodel.attribute, hmodel.attributeHasLength, true);
		assertEquals(1, Utils.getAttributeLength(prop));
		
		TestUtils.setStereotypePropertyValue(prop, hmodel.attribute, hmodel.attributeLength, 2);
		assertEquals(2, Utils.getAttributeLength(prop));
	}
	
	@Test
	public void testKeyAttributeGetAttributeLength() {
		HornetModel hmodel = HornetModel.initModel();
		
		Class class_ = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		Property prop = TestUtils.createAttribute(class_, "test", hmodel.stringPT, 0, 1);

		prop.applyStereotype(hmodel.keyAttribute);
		
		TestUtils.setStereotypePropertyValue(prop, hmodel.keyAttribute, hmodel.attributeLength, 1);
		TestUtils.setStereotypePropertyValue(prop, hmodel.keyAttribute, hmodel.attributeHasLength, false);
		assertEquals(null, Utils.getAttributeLength(prop));
		
		TestUtils.setStereotypePropertyValue(prop, hmodel.keyAttribute, hmodel.attributeHasLength, true);
		assertEquals(1, Utils.getAttributeLength(prop));
		
		TestUtils.setStereotypePropertyValue(prop, hmodel.keyAttribute, hmodel.attributeLength, 2);
		assertEquals(2, Utils.getAttributeLength(prop));
	}

	@Test
	public void testGetSequenceStartWith() {	
		HornetModel hmodel = HornetModel.initModel();
		
		Class class_ = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		Property prop = TestUtils.createAttribute(class_, "test", hmodel.stringPT, 0, 1);

		prop.applyStereotype(hmodel.sequence);
		
		TestUtils.setStereotypePropertyValue(prop, hmodel.sequence, hmodel.sequenceStartWith, 1);
		assertEquals(1, Utils.getSequenceStartWith(prop));
		
		TestUtils.setStereotypePropertyValue(prop, hmodel.sequence, hmodel.sequenceStartWith, 2);
		assertEquals(2, Utils.getSequenceStartWith(prop));
	}

	@Test
	public void testGetSequenceIncrementBy() {	
		HornetModel hmodel = HornetModel.initModel();
		
		Class class_ = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		Property prop = TestUtils.createAttribute(class_, "test", hmodel.stringPT, 0, 1);
		prop.applyStereotype(hmodel.sequence);
		
		TestUtils.setStereotypePropertyValue(prop, hmodel.sequence, hmodel.sequenceIncrementBy, 1);
		assertEquals(1, Utils.getSequenceIncrementBy(prop));
		
		TestUtils.setStereotypePropertyValue(prop, hmodel.sequence, hmodel.sequenceIncrementBy, 2);
		assertEquals(2, Utils.getSequenceIncrementBy(prop));
	}

	@Test
	public void testGetSequenceHasMaxValue() {
		HornetModel hmodel = HornetModel.initModel();
		
		Class class_ = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		Property prop = TestUtils.createAttribute(class_, "test", hmodel.stringPT, 0, 1);
		prop.applyStereotype(hmodel.sequence);
		
		TestUtils.setStereotypePropertyValue(prop, hmodel.sequence, hmodel.sequenceHasMaxValue, true);
		assertEquals(true, Utils.getSequenceHasMaxValue(prop));
		
		TestUtils.setStereotypePropertyValue(prop, hmodel.sequence, hmodel.sequenceHasMaxValue, false);
		assertEquals(false, Utils.getSequenceHasMaxValue(prop));
	}

	@Test
	public void testGetSequenceMaxValue() {
		HornetModel hmodel = HornetModel.initModel();
		
		Class class_ = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		Property prop = TestUtils.createAttribute(class_, "test", hmodel.stringPT, 0, 1);
		prop.applyStereotype(hmodel.sequence);
		
		TestUtils.setStereotypePropertyValue(prop, hmodel.sequence, hmodel.sequenceMaxValue, 1);
		assertEquals(1, Utils.getSequenceMaxValue(prop));
		
		TestUtils.setStereotypePropertyValue(prop, hmodel.sequence, hmodel.sequenceMaxValue, 2);
		assertEquals(2, Utils.getSequenceMaxValue(prop));
	}

	@Test
	public void testGetSequenceHasMinValue() {
		HornetModel hmodel = HornetModel.initModel();
		
		Class class_ = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		Property prop = TestUtils.createAttribute(class_, "test", hmodel.stringPT, 0, 1);
		prop.applyStereotype(hmodel.sequence);
		
		TestUtils.setStereotypePropertyValue(prop, hmodel.sequence, hmodel.sequenceHasMinValue, true);
		assertEquals(true, Utils.getSequenceHasMinValue(prop));
		
		TestUtils.setStereotypePropertyValue(prop, hmodel.sequence, hmodel.sequenceHasMinValue, false);
		assertEquals(false, Utils.getSequenceHasMinValue(prop));
	}

	@Test
	public void testGetSequenceMinValue() {
		HornetModel hmodel = HornetModel.initModel();
		
		Class class_ = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		Property prop = TestUtils.createAttribute(class_, "test", hmodel.stringPT, 0, 1);
		prop.applyStereotype(hmodel.sequence);
		
		TestUtils.setStereotypePropertyValue(prop, hmodel.sequence, hmodel.sequenceMinValue, 1);
		assertEquals(1, Utils.getSequenceMinValue(prop));
		
		TestUtils.setStereotypePropertyValue(prop, hmodel.sequence, hmodel.sequenceMinValue, 2);
		assertEquals(2, Utils.getSequenceMinValue(prop));
	}

	@Test
	public void testGetSequenceCache() {
		HornetModel hmodel = HornetModel.initModel();
		
		Class class_ = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		Property prop = TestUtils.createAttribute(class_, "test", hmodel.stringPT, 0, 1);
		prop.applyStereotype(hmodel.sequence);
		
		TestUtils.setStereotypePropertyValue(prop, hmodel.sequence, hmodel.sequenceCache, 1);
		assertEquals(1, Utils.getSequenceCache(prop));
		
		TestUtils.setStereotypePropertyValue(prop, hmodel.sequence, hmodel.sequenceCache, 2);
		assertEquals(2, Utils.getSequenceCache(prop));
	}

	@Test
	public void testGetSequenceCycle() {
		HornetModel hmodel = HornetModel.initModel();
		
		Class class_ = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		Property prop = TestUtils.createAttribute(class_, "test", hmodel.stringPT, 0, 1);
		prop.applyStereotype(hmodel.sequence);
		
		TestUtils.setStereotypePropertyValue(prop, hmodel.sequence, hmodel.sequenceCycle, true);
		assertEquals(true, Utils.getSequenceCycle(prop));
		
		TestUtils.setStereotypePropertyValue(prop, hmodel.sequence, hmodel.sequenceCycle, false);
		assertEquals(false, Utils.getSequenceCycle(prop));
	}
	
	@Test
	public void testGetGenerated() {
		HornetModel hmodel = HornetModel.initModel();
		
		Classifier class_ = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		
		assertEquals(true, Utils.getGenerated(class_));
	}

	@Test
	public void testEntityGetGenerated() {
		HornetModel hmodel = HornetModel.initModel();
		
		Classifier class_ = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		class_.applyStereotype(hmodel.entity);
		
		TestUtils.setStereotypePropertyValue(class_, hmodel.entity, hmodel.entityGenerated, true);
		assertEquals(true, Utils.getGenerated(class_));
		
		TestUtils.setStereotypePropertyValue(class_, hmodel.entity, hmodel.entityGenerated, false);
		assertEquals(false, Utils.getGenerated(class_));
	}
	
	@Test
	public void testNomenclatureGetGenerated() {
		HornetModel hmodel = HornetModel.initModel();
		
		Classifier class_ = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		class_.applyStereotype(hmodel.nomenclature);
		
		TestUtils.setStereotypePropertyValue(class_, hmodel.nomenclature, hmodel.nomenclatureGenerated, true);
		assertEquals(true, Utils.getGenerated(class_));
		
		TestUtils.setStereotypePropertyValue(class_, hmodel.nomenclature, hmodel.nomenclatureGenerated, false);
		assertEquals(false, Utils.getGenerated(class_));
	}
	
	@Test
	public void testValueObjectGetGenerated() {
		HornetModel hmodel = HornetModel.initModel();
		
		Classifier class_ = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		class_.applyStereotype(hmodel.valueObject);
		
		TestUtils.setStereotypePropertyValue(class_, hmodel.valueObject, hmodel.valueObjectGenerated, true);
		assertEquals(true, Utils.getGenerated(class_));
		
		TestUtils.setStereotypePropertyValue(class_, hmodel.valueObject, hmodel.valueObjectGenerated, false);
		assertEquals(false, Utils.getGenerated(class_));
	}
	
}
