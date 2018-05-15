package fr.gouv.diplomatie.papyrus.codegen.core.test;

import static org.junit.Assert.*;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;

import org.eclipse.uml2.uml.AggregationKind;
import org.eclipse.uml2.uml.AssociationClass;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.Profile;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Stereotype;
import org.eclipse.uml2.uml.Type;
import org.eclipse.uml2.uml.UMLPackage;
import org.junit.Test;

import fr.gouv.diplomatie.papyrus.codegen.core.test.HornetModel;
import fr.gouv.diplomatie.papyrus.codegen.core.test.TestUtils;
import fr.gouv.diplomatie.papyrus.codegen.core.utils.ClassifierUtils;
import fr.gouv.diplomatie.papyrus.codegen.core.utils.Utils;

public class ClassifierUtilsTest {

	@Test
	public void testClass() {
		ClassifierUtils test = new ClassifierUtils();
		assertEquals(ClassifierUtils.class, test.getClass());
	}
	
	@Test
	public void testGetModelName() {
		HornetModel hmodel = HornetModel.initModel();
		Classifier class_ = TestUtils.createClass(hmodel.model, "MaClasse", false);
		assertEquals("MaClasseModel", ClassifierUtils.getModelName(class_));
	}

	@Test
	public void testGetAttributesInterfaceName() {
		HornetModel hmodel = HornetModel.initModel();
		Classifier class_ = TestUtils.createClass(hmodel.model, "MaClasse", false);
		assertEquals("MaClasseAttributes", ClassifierUtils.getAttributesInterfaceName(class_));
	}

	@Test
	public void testGetMetierClassName() {
		HornetModel hmodel = HornetModel.initModel();
		Classifier class_ = TestUtils.createClass(hmodel.model, "MaClasse", false);
		assertEquals("MaClasseMetier", ClassifierUtils.getMetierClassName(class_));
	}

	@Test
	public void testGetDtoClassName() {
		HornetModel hmodel = HornetModel.initModel();
		Classifier class_ = TestUtils.createClass(hmodel.model, "MaClasse", false);
		assertEquals("MaClasseDTO", ClassifierUtils.getDtoClassName(class_));
	}

	@Test
	public void testGetApplicationName() {
		HornetModel hmodel = HornetModel.initModel();
		Classifier class_ = TestUtils.createClass(hmodel.model, "MaClasse", false);
		assertEquals("model", ClassifierUtils.getApplicationName(class_));
	}

	@Test
	public void testGetDomainName() {
		HornetModel hmodel = HornetModel.initModel();
		Classifier class_ = TestUtils.createClass(hmodel.pckage, "MaClasse", false);
		assertEquals("package", ClassifierUtils.getDomainName(class_));
	}

	@Test
	public void testGetModelPath() {
		HornetModel hmodel = HornetModel.initModel();
		Classifier class_ = TestUtils.createClass(hmodel.pckage, "MaClasse", false);
		String expected = "src" + File.separator + "models" +  File.separator + "models" + File.separator  + "ma-classe-model";
		assertEquals(expected, ClassifierUtils.getModelPath(class_));
	}

	@Test
	public void testGetAttributesInterfacePath() {
		HornetModel hmodel = HornetModel.initModel();
		Classifier class_ = TestUtils.createClass(hmodel.pckage, "MaClasse", false);
		String expected = "src" + File.separator + "models" +  File.separator + "attributes" + File.separator  + "ma-classe-attributes";
		assertEquals(expected, ClassifierUtils.getAttributesInterfacePath(class_));
	}

	@Test
	public void testGetMetierClassPath() {
		HornetModel hmodel = HornetModel.initModel();
		Classifier class_ = TestUtils.createClass(hmodel.pckage, "MaClasse", false);
		String expected = "src" + File.separator + "models" +  File.separator + "metier" + File.separator  + "ma-classe-metier";
		assertEquals(expected, ClassifierUtils.getMetierClassPath(class_));
	}

	@Test
	public void testGetDtoClassPath() {
		HornetModel hmodel = HornetModel.initModel();
		Classifier class_ = TestUtils.createClass(hmodel.pckage, "MaClasse", false);
		String expected = "src" + File.separator + "models" +  File.separator + "dto" + File.separator  + "ma-classe-dto";
		assertEquals(expected, ClassifierUtils.getDtoClassPath(class_));
	}

	@Test
	public void testGetEnumClassPath() {
		HornetModel hmodel = HornetModel.initModel();
		Classifier class_ = TestUtils.createClass(hmodel.pckage, "MaClasse", false);
		String expected = "src" + File.separator + "models" +  File.separator +  "ma-classe-enum";
		assertEquals(expected, ClassifierUtils.getEnumClassPath(class_));
	}

	@Test
	public void testGetStereotype() {
		HornetModel hmodel = HornetModel.initModel();
		Classifier class_ = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		class_.applyStereotype(hmodel.entity);
		
		assertEquals(hmodel.entity, ClassifierUtils.getStereotype(class_, Utils.MODEL_ENTITY).iterator().next());
	}

	@Test
	public void testGetStereotypePropertyValue() {
		HornetModel hmodel = HornetModel.initModel();
		Classifier class_ = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		class_.applyStereotype(hmodel.entity);
		
		TestUtils.setStereotypePropertyValue(class_, hmodel.entity, hmodel.entityGenerated, true);
		assertEquals(true, ClassifierUtils.getStereotypePropertyValue(class_, Utils.MODEL_ENTITY, Utils.MODEL_ENTITY_GENERATED));
		
		TestUtils.setStereotypePropertyValue(class_, hmodel.entity, hmodel.entityGenerated, false);
		assertEquals(false, ClassifierUtils.getStereotypePropertyValue(class_, Utils.MODEL_ENTITY, Utils.MODEL_ENTITY_GENERATED));
	}

	@Test
	public void testGetOwnedAttributes() {
		HornetModel hmodel = HornetModel.initModel();
		Class class_ = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		Property attribute1 = TestUtils.createAttribute(class_, "test", hmodel.booleanPT, 0, 1);
		Property attribute2 = TestUtils.createAttribute(class_, "test2", hmodel.integerPT, 0, 1);
		
		ArrayList<Property> attributes = new ArrayList<Property>();
		attributes.add(attribute1);
		attributes.add(attribute2);
		
		assertEquals(attributes, ClassifierUtils.getOwnedAttributes(class_));
	}
	
	@Test
	public void testNoAttributesGetOwnedAttributes() {
		HornetModel hmodel = HornetModel.initModel();
		Class class_ = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		
		ArrayList<Property> attributes = new ArrayList<Property>();
		assertEquals(attributes, ClassifierUtils.getOwnedAttributes(class_));
	}
	
	@Test
	public void testWithGeneralizationGetOwnedAttributes() {
		HornetModel hmodel = HornetModel.initModel();
		
		Class class_ = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		Class classGene = TestUtils.createClass(hmodel.pckage, "autreClasse", false);
		
		Property attribute1 = TestUtils.createAttribute(class_, "test", hmodel.booleanPT, 0, 1);
		Property attribute2 = TestUtils.createAttribute(class_, "test2", hmodel.integerPT, 0, 1);
		
		TestUtils.createAttribute(classGene, "test3", hmodel.integerPT, 0, 1);
		
		TestUtils.createGeneralization(class_, classGene);
		
		ArrayList<Property> attributes = new ArrayList<Property>();
		attributes.add(attribute1);
		attributes.add(attribute2);
		
		assertEquals(attributes, ClassifierUtils.getOwnedAttributes(class_));
	}

	@Test
	public void testGetAttributes() {
		HornetModel hmodel = HornetModel.initModel();
		
		Class class_ = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		Class classGene = TestUtils.createClass(hmodel.pckage, "autreClasse", false);
		
		Property attribute1 = TestUtils.createAttribute(class_, "test", hmodel.booleanPT, 0, 1);
		Property attribute2 = TestUtils.createAttribute(class_, "test2", hmodel.integerPT, 0, 1);
		Property attribute3 = TestUtils.createAttribute(classGene, "test3", hmodel.integerPT, 0, 1);
		
		TestUtils.createGeneralization(class_, classGene);
		
		ArrayList<Property> attributes = new ArrayList<Property>();
		attributes.add(attribute1);
		attributes.add(attribute2);
		attributes.add(attribute3);
		
		assertEquals(attributes, ClassifierUtils.getAttributes(class_));
	}
	
	@Test
	public void testNoAttributesGetAttributes() {
		HornetModel hmodel = HornetModel.initModel();
		
		Class class_ = TestUtils.createClass(hmodel.pckage, "maClasse", false);
				
		ArrayList<Property> attributes = new ArrayList<Property>();
		assertEquals(attributes, ClassifierUtils.getAttributes(class_));
	}

	@Test
	public void testGetOwnedAttributesWNull() {
		HornetModel hmodel = HornetModel.initModel();
		
		Class class_ = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		Class classGene = TestUtils.createClass(hmodel.pckage, "autreClasse", false);
		
		Property attribute1 = TestUtils.createAttribute(class_, "test", hmodel.booleanPT, 0, 1);
		Property attribute2 = TestUtils.createAttribute(class_, "test2", hmodel.integerPT, 0, 1);
		
		TestUtils.createAttribute(classGene, "test3", hmodel.integerPT, 0, 1);
		
		TestUtils.createGeneralization(class_, classGene);
		
		ArrayList<Property> attributes = new ArrayList<Property>();
		attributes.add(attribute1);
		attributes.add(attribute2);
		
		assertEquals(attributes, ClassifierUtils.getOwnedAttributesWNull(class_));
	}
	
	@Test
	public void testNullGetOwnedAttributesWNull() {
		HornetModel hmodel = HornetModel.initModel();
		
		Class class_ = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		Class classGene = TestUtils.createClass(hmodel.pckage, "autreClasse", false);
				
		TestUtils.createAttribute(classGene, "test3", hmodel.integerPT, 0, 1);
		
		TestUtils.createGeneralization(class_, classGene);
		
		ArrayList<Property> attributes = new ArrayList<Property>();
		
		assertEquals(attributes, ClassifierUtils.getOwnedAttributesWNull(class_));
	}

	@Test
	public void testHasMultipleId() {
		HornetModel hmodel = HornetModel.initModel();
		
		Class class_ = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		
		Property attribute1 = TestUtils.createAttribute(class_, "test", hmodel.booleanPT, 0, 1);
		Property attribute2 = TestUtils.createAttribute(class_, "test2", hmodel.integerPT, 0, 1);
		
		attribute1.applyStereotype(hmodel.keyAttribute);	
		assertEquals(false, ClassifierUtils.hasMultipleId(class_));
		
		attribute2.applyStereotype(hmodel.keyAttribute);
		assertEquals(true, ClassifierUtils.hasMultipleId(class_));
	}

	@Test
	public void testGetId() {
		HornetModel hmodel = HornetModel.initModel();
		
		Class class_ = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		
		Property attribute1 = TestUtils.createAttribute(class_, "test", hmodel.booleanPT, 0, 1);
		Property attribute2 = TestUtils.createAttribute(class_, "test2", hmodel.integerPT, 0, 1);
		
		attribute1.applyStereotype(hmodel.keyAttribute);	
		assertEquals(attribute1, ClassifierUtils.getId(class_).iterator().next());
		
		attribute2.applyStereotype(hmodel.keyAttribute);
		
		Iterable<Property> result =  ClassifierUtils.getId(class_);
		Iterator<Property> it = result.iterator();
		assertEquals(attribute1,it.next());
		assertEquals(attribute2,it.next());
	}

	@Test
	public void testGetMultivaluedOwnedAttributes() {
		HornetModel hmodel = HornetModel.initModel();
		
		Class class_ = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		
		TestUtils.createAttribute(class_, "test", hmodel.booleanPT, 0, 1);
		Property attribute2 = TestUtils.createAttribute(class_, "test2", hmodel.integerPT, 0, -1);
		
		assertEquals(attribute2, ClassifierUtils.getMultivaluedOwnedAttributes(class_).iterator().next());
		
	}

	@Test
	public void testGetAllMultivaluedAttributes() {
		HornetModel hmodel = HornetModel.initModel();
		Class class_ = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		Class classGene = TestUtils.createClass(hmodel.pckage, "maClasseGene", false);
		
		TestUtils.createAttribute(class_, "test", hmodel.booleanPT, 0, 1);
		Property attribute2 = TestUtils.createAttribute(class_, "test2", hmodel.integerPT, 0, -1);
		
		Property attribute3 = TestUtils.createAttribute(classGene, "test3", hmodel.integerPT, 0, -1);
		
		TestUtils.createGeneralization(class_, classGene);
		
		Iterable<Property> result = ClassifierUtils.getAllMultivaluedAttributes(class_);
		Iterator<Property> it = result.iterator();
		
		assertEquals(attribute2, it.next());
		assertEquals(attribute3, it.next());
	}

	@Test
	public void testGetNotMultivaluedOwnedAttributes() {
		HornetModel hmodel = HornetModel.initModel();
		
		Class class_ = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		
		Property attribute1 = TestUtils.createAttribute(class_, "test", hmodel.booleanPT, 0, 1);
		TestUtils.createAttribute(class_, "test2", hmodel.integerPT, 0, -1);
		
		Iterable<Property> result = ClassifierUtils.getNotMultivaluedOwnedAttributes(class_);
		int length = 0;
		for(Property p : result) {
			length++;
		}
		assertEquals(attribute1,result.iterator().next());
		assertEquals(1,length);
	
	}

	@Test
	public void testGetMultivaluedReferencesToType() {
		HornetModel hmodel = HornetModel.initModel();
		
		Class class_ = TestUtils.createClass(hmodel.model, "maClasse", false);
		Class otherClass = TestUtils.createClass(hmodel.model, "autreClasse", false);
		Property attribute1 = TestUtils.createAttribute(otherClass, "test", class_, 0, -1);
		class_.applyStereotype(hmodel.entity);
		otherClass.applyStereotype(hmodel.entity);
		
		assertEquals(attribute1,ClassifierUtils.getMultivaluedReferencesToType(otherClass, class_).iterator().next());
		
	}

	@Test
	public void testGetAllReferencesTo() {
		HornetModel hmodel = HornetModel.initModel();
		
		Class class_ = TestUtils.createClass(hmodel.model, "maClasse", false);
		Class otherClass = TestUtils.createClass(hmodel.model, "autreClasse", false);
		Property attribute1 = TestUtils.createAttribute(otherClass, "test", class_, 0, -1);
		class_.applyStereotype(hmodel.entity);
		otherClass.applyStereotype(hmodel.entity);
		
		assertEquals(attribute1,ClassifierUtils.getAllReferencesTo(class_).iterator().next());
		
	}

	@Test
	public void testGetOneToManyAttributes() {
		HornetModel hmodel = HornetModel.initModel();
		
		Class class_ = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		Class otherClass = TestUtils.createClass(hmodel.pckage, "autreClasse", false);
		class_.applyStereotype(hmodel.entity);
		otherClass.applyStereotype(hmodel.entity);
		
		class_.createAssociation(false, AggregationKind.NONE_LITERAL, "test", 1, 1, otherClass, true, AggregationKind.NONE_LITERAL, "test2", 0, -1);	
		
		ArrayList<Property> result = ClassifierUtils.getOneToManyAttributes(class_);
		Property test = result.get(0);
			
		assertEquals("test2",test.getName());
	}

	@Test
	public void testGetManyToManyAttributes() {
		HornetModel hmodel = HornetModel.initModel();
		
		Class class_ = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		Class otherClass = TestUtils.createClass(hmodel.pckage, "autreClasse", false);
		class_.applyStereotype(hmodel.entity);
		otherClass.applyStereotype(hmodel.entity);
		
		class_.createAssociation(false, AggregationKind.NONE_LITERAL, "test", 0, -1, otherClass, true, AggregationKind.NONE_LITERAL, "test2", 0, -1);	
		
		ArrayList<Property> result = ClassifierUtils.getManyToManyAttributes(class_);
		Property test = result.get(0);
			
		assertEquals("test2",test.getName());
		
	}

	@Test
	public void testGetLinkedAssociationClass() {
		HornetModel hmodel = HornetModel.initModel();
		Class class_ = TestUtils.createClass(hmodel.model, "maClasse", false);
		Class class2_ = TestUtils.createClass(hmodel.model, "autreClasse", false);

		AssociationClass association = TestUtils.createAssociationClass(class_, class2_, "test1", "test2", "asso");
		
		assertEquals(association, ClassifierUtils.getLinkedAssociationClass(class_).get(0));
	}

	@Test
	public void testIsEnumWithCode() {
		
		HornetModel hmodel = HornetModel.initModel();
		
		Class class_ = TestUtils.createClass(hmodel.model, "maClasse", false);
		class_.applyStereotype(hmodel.nomenclature);
		
		Property attribute1 = TestUtils.createAttribute(class_, "test", hmodel.integerPT, 0, -1);
		attribute1.applyStereotype(hmodel.codeLibelleNomenclature);
		
		TestUtils.setStereotypePropertyValue(attribute1, hmodel.codeLibelleNomenclature, hmodel.codeLibelleNomenclatureCode, 2);
		
		assertEquals(true, ClassifierUtils.isEnumWithCode(class_));
	}

	@Test
	public void testCanBeGenerated() {
		HornetModel hmodel = HornetModel.initModel();
		
		Classifier class_ = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		class_.applyStereotype(hmodel.entity);
		
		TestUtils.setStereotypePropertyValue(class_, hmodel.entity, hmodel.entityGenerated, true);
		assertEquals(true, ClassifierUtils.canBeGenerated(class_));
		
		TestUtils.setStereotypePropertyValue(class_, hmodel.entity, hmodel.entityGenerated, false);
		assertEquals(false, ClassifierUtils.canBeGenerated(class_));
	}

	@Test
	public void testGetTableName() {
		HornetModel hmodel = HornetModel.initModel();
		
		Classifier class_ = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		class_.applyStereotype(hmodel.entity);
		
		assertEquals("ma_classe", ClassifierUtils.getTableName(class_));
		
		TestUtils.setStereotypePropertyValue(class_, hmodel.entity, hmodel.entityTableName, "testTableName");
		assertEquals("testTableName", ClassifierUtils.getTableName(class_));
	}

	@Test
	public void testGetDBTableName() {
		HornetModel hmodel = HornetModel.initModel();
		
		Classifier class_ = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		class_.applyStereotype(hmodel.entity);
		
		assertEquals("MA_CLASSE", ClassifierUtils.getDBTableName(class_));
		
		TestUtils.setStereotypePropertyValue(class_, hmodel.entity, hmodel.entityTableName, "testTableName");
		assertEquals("TEST_TABLE_NAME", ClassifierUtils.getDBTableName(class_));
	}

	@Test
	public void testGetClassPath() {
		
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
		
		String expected = "ab" + File.separator + "cd" + File.separator + "ef" + File.separator + "model" + File.separator  +  "package" + File.separator ;
		
		assertEquals(expected, ClassifierUtils.getClassPath(class_));
	}
	
	@Test
	public void testNullGetClassPath() {
		assertEquals(null, ClassifierUtils.getClassPath(null));
	}

	@Test
	public void testGetTableNameValue() {
		HornetModel hmodel = HornetModel.initModel();
		
		Classifier class_ = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		class_.applyStereotype(hmodel.entity);
		
		TestUtils.setStereotypePropertyValue(class_, hmodel.entity, hmodel.entityTableName, "testTableName");
		assertEquals("testTableName", ClassifierUtils.getTableNameValue(class_));
	}
	
	@Test
	public void testNoTypeGetTableNameValue() {
		HornetModel hmodel = HornetModel.initModel();
		
		Classifier class_ = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		assertEquals(null, ClassifierUtils.getTableNameValue(class_));
	}
	
	@Test
	public void testNomenclatureGetTableNameValue() {
		HornetModel hmodel = HornetModel.initModel();
		
		Classifier class_ = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		class_.applyStereotype(hmodel.nomenclature);
		
		TestUtils.setStereotypePropertyValue(class_, hmodel.nomenclature, hmodel.nomenclatureTableName, "testTableName");
		assertEquals("testTableName", ClassifierUtils.getTableNameValue(class_));
	}

}
