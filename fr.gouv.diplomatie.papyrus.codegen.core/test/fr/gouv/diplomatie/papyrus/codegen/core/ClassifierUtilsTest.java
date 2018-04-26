package fr.gouv.diplomatie.papyrus.codegen.core;

import static org.junit.Assert.*;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;

import org.eclipse.uml2.uml.AggregationKind;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.Profile;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Stereotype;
import org.eclipse.uml2.uml.Type;
import org.eclipse.uml2.uml.UMLPackage;
import org.junit.Test;

import fr.gouv.diplomatie.papyrus.codegen.core.test.TestUtils;
import fr.gouv.diplomatie.papyrus.codegen.core.utils.ClassifierUtils;
import fr.gouv.diplomatie.papyrus.codegen.core.utils.Utils;

public class ClassifierUtilsTest {

	@Test
	public void testGetModelName() {
		Profile profile = TestUtils.createProfile("profile");
		Model model = TestUtils.createModel("model");
        
		profile.define();
		model.applyProfile(profile);
		
		Package pkg = TestUtils.createPackage(model, "package");
		
		Classifier class_ = TestUtils.createClass(pkg, "MaClasse", false);
		
		assertEquals("MaClasseModel", ClassifierUtils.getModelName(class_));
	}

	@Test
	public void testGetAttributesInterfaceName() {
		Profile profile = TestUtils.createProfile("profile");
		Model model = TestUtils.createModel("model");
        
		profile.define();
		model.applyProfile(profile);
		
		Package pkg = TestUtils.createPackage(model, "package");
		
		Classifier class_ = TestUtils.createClass(pkg, "MaClasse", false);
		
		assertEquals("MaClasseAttributes", ClassifierUtils.getAttributesInterfaceName(class_));
	}

	@Test
	public void testGetMetierClassName() {
		Profile profile = TestUtils.createProfile("profile");
		Model model = TestUtils.createModel("model");
        
		profile.define();
		model.applyProfile(profile);
		
		Package pkg = TestUtils.createPackage(model, "package");
		
		Classifier class_ = TestUtils.createClass(pkg, "MaClasse", false);
		
		assertEquals("MaClasseMetier", ClassifierUtils.getMetierClassName(class_));
	}

	@Test
	public void testGetDtoClassName() {
		Profile profile = TestUtils.createProfile("profile");
		Model model = TestUtils.createModel("model");
        
		profile.define();
		model.applyProfile(profile);
		
		Package pkg = TestUtils.createPackage(model, "package");
		
		Classifier class_ = TestUtils.createClass(pkg, "MaClasse", false);
		
		assertEquals("MaClasseDTO", ClassifierUtils.getDtoClassName(class_));
	}

	@Test
	public void testGetApplicationName() {
		Profile profile = TestUtils.createProfile("profile");
		Model model = TestUtils.createModel("model");
        
		profile.define();
		model.applyProfile(profile);
		
		Package pkg = TestUtils.createPackage(model, "package");
		
		Classifier class_ = TestUtils.createClass(pkg, "MaClasse", false);
		
		assertEquals("model", ClassifierUtils.getApplicationName(class_));
	}

	@Test
	public void testGetDomainName() {
		Profile profile = TestUtils.createProfile("profile");
		Model model = TestUtils.createModel("model");
        
		profile.define();
		model.applyProfile(profile);
		
		Package pkg = TestUtils.createPackage(model, "package");
		
		Classifier class_ = TestUtils.createClass(pkg, "MaClasse", false);
		
		assertEquals("package", ClassifierUtils.getDomainName(class_));
	}

	@Test
	public void testGetModelPath() {
		Profile profile = TestUtils.createProfile("profile");
		Model model = TestUtils.createModel("model");
        
		profile.define();
		model.applyProfile(profile);
		
		Package pkg = TestUtils.createPackage(model, "package");
		
		Classifier class_ = TestUtils.createClass(pkg, "MaClasse", false);
		
		String expected = "src" + File.separator + "models" +  File.separator + "models" + File.separator  + "ma-classe-model";
		assertEquals(expected, ClassifierUtils.getModelPath(class_));
	}

	@Test
	public void testGetAttributesInterfacePath() {
		Profile profile = TestUtils.createProfile("profile");
		Model model = TestUtils.createModel("model");
        
		profile.define();
		model.applyProfile(profile);
		
		Package pkg = TestUtils.createPackage(model, "package");
		
		Classifier class_ = TestUtils.createClass(pkg, "MaClasse", false);
		
		String expected = "src" + File.separator + "models" +  File.separator + "attributes" + File.separator  + "ma-classe-attributes";
		assertEquals(expected, ClassifierUtils.getAttributesInterfacePath(class_));
	}

	@Test
	public void testGetMetierClassPath() {
		Profile profile = TestUtils.createProfile("profile");
		Model model = TestUtils.createModel("model");
        
		profile.define();
		model.applyProfile(profile);
		
		Package pkg = TestUtils.createPackage(model, "package");
		
		Classifier class_ = TestUtils.createClass(pkg, "MaClasse", false);
		
		String expected = "src" + File.separator + "models" +  File.separator + "metier" + File.separator  + "ma-classe-metier";
		assertEquals(expected, ClassifierUtils.getMetierClassPath(class_));
	}

	@Test
	public void testGetDtoClassPath() {
		Profile profile = TestUtils.createProfile("profile");
		Model model = TestUtils.createModel("model");
        
		profile.define();
		model.applyProfile(profile);
		
		Package pkg = TestUtils.createPackage(model, "package");
		
		Classifier class_ = TestUtils.createClass(pkg, "MaClasse", false);
		
		String expected = "src" + File.separator + "models" +  File.separator + "dto" + File.separator  + "ma-classe-dto";
		assertEquals(expected, ClassifierUtils.getDtoClassPath(class_));
	}

	@Test
	public void testGetEnumClassPath() {
		Profile profile = TestUtils.createProfile("profile");
		Model model = TestUtils.createModel("model");
        
		profile.define();
		model.applyProfile(profile);
		
		Package pkg = TestUtils.createPackage(model, "package");
		
		Classifier class_ = TestUtils.createClass(pkg, "MaClasse", false);
		
		String expected = "src" + File.separator + "models" +  File.separator +  "ma-classe-enum";
		assertEquals(expected, ClassifierUtils.getEnumClassPath(class_));
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
		
		assertEquals(stereotype, ClassifierUtils.getStereotype(class_, Utils.MODEL_ENTITY).iterator().next());
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
		
		
		assertEquals(true, ClassifierUtils.getStereotypePropertyValue(class_, Utils.MODEL_ENTITY, Utils.MODEL_ENTITY_GENERATED));
		TestUtils.setStereotypePropertyValue(class_, stereotype, attribute, false);
		assertEquals(false, ClassifierUtils.getStereotypePropertyValue(class_, Utils.MODEL_ENTITY, Utils.MODEL_ENTITY_GENERATED));
	}

	@Test
	public void testGetOwnedAttributes() {
		Profile profile = TestUtils.createProfile("profile");
		Model model = TestUtils.createModel("model");
		Package pkg = TestUtils.createPackage(model, "package");    
        
		profile.define();
		model.applyProfile(profile);
		
		
		Class class_ = TestUtils.createClass(pkg, "maClasse", false);
		Type booleanPT = TestUtils.importPrimitiveType(pkg, "Boolean");
		Property attribute1 = TestUtils.createAttribute(class_, "test", booleanPT, 0, 1);
		Type integerPT = TestUtils.importPrimitiveType(pkg, "Integer");
		Property attribute2 = TestUtils.createAttribute(class_, "test2", integerPT, 0, 1);
		
		ArrayList<Property> attributes = new ArrayList<Property>();
		attributes.add(attribute1);
		attributes.add(attribute2);
		
		assertEquals(attributes, ClassifierUtils.getOwnedAttributes(class_));
	}
	
	@Test
	public void testWithGeneralizationGetOwnedAttributes() {
		Profile profile = TestUtils.createProfile("profile");
		Model model = TestUtils.createModel("model");
		Package pkg = TestUtils.createPackage(model, "package");    
        
		profile.define();
		model.applyProfile(profile);
		
		
		Class class_ = TestUtils.createClass(pkg, "maClasse", false);
		Class classGene = TestUtils.createClass(pkg, "autreClasse", false);
		
		Type booleanPT = TestUtils.importPrimitiveType(pkg, "Boolean");
		Property attribute1 = TestUtils.createAttribute(class_, "test", booleanPT, 0, 1);
		Type integerPT = TestUtils.importPrimitiveType(pkg, "Integer");
		Property attribute2 = TestUtils.createAttribute(class_, "test2", integerPT, 0, 1);
		
		TestUtils.createAttribute(classGene, "test3", integerPT, 0, 1);
		
		TestUtils.createGeneralization(class_, classGene);
		
		ArrayList<Property> attributes = new ArrayList<Property>();
		attributes.add(attribute1);
		attributes.add(attribute2);
		
		assertEquals(attributes, ClassifierUtils.getOwnedAttributes(class_));
	}

	@Test
	public void testGetAttributes() {
		Profile profile = TestUtils.createProfile("profile");
		Model model = TestUtils.createModel("model");
		Package pkg = TestUtils.createPackage(model, "package");    
        
		profile.define();
		model.applyProfile(profile);
		
		
		Class class_ = TestUtils.createClass(pkg, "maClasse", false);
		Class classGene = TestUtils.createClass(pkg, "autreClasse", false);
		
		Type booleanPT = TestUtils.importPrimitiveType(pkg, "Boolean");
		Property attribute1 = TestUtils.createAttribute(class_, "test", booleanPT, 0, 1);
		Type integerPT = TestUtils.importPrimitiveType(pkg, "Integer");
		Property attribute2 = TestUtils.createAttribute(class_, "test2", integerPT, 0, 1);
		
		Property attribute3 = TestUtils.createAttribute(classGene, "test3", integerPT, 0, 1);
		
		TestUtils.createGeneralization(class_, classGene);
		
		ArrayList<Property> attributes = new ArrayList<Property>();
		attributes.add(attribute1);
		attributes.add(attribute2);
		attributes.add(attribute3);
		
		assertEquals(attributes, ClassifierUtils.getAttributes(class_));
	}

	@Test
	public void testGetOwnedAttributesWNull() {
		Profile profile = TestUtils.createProfile("profile");
		Model model = TestUtils.createModel("model");
		Package pkg = TestUtils.createPackage(model, "package");    
        
		profile.define();
		model.applyProfile(profile);
		
		
		Class class_ = TestUtils.createClass(pkg, "maClasse", false);
		Class classGene = TestUtils.createClass(pkg, "autreClasse", false);
		
		Type booleanPT = TestUtils.importPrimitiveType(pkg, "Boolean");
		Property attribute1 = TestUtils.createAttribute(class_, "test", booleanPT, 0, 1);
		Type integerPT = TestUtils.importPrimitiveType(pkg, "Integer");
		Property attribute2 = TestUtils.createAttribute(class_, "test2", integerPT, 0, 1);
		
		TestUtils.createAttribute(classGene, "test3", integerPT, 0, 1);
		
		TestUtils.createGeneralization(class_, classGene);
		
		ArrayList<Property> attributes = new ArrayList<Property>();
		attributes.add(attribute1);
		attributes.add(attribute2);
		
		assertEquals(attributes, ClassifierUtils.getOwnedAttributesWNull(class_));
	}
	
	@Test
	public void testNullGetOwnedAttributesWNull() {
		Profile profile = TestUtils.createProfile("profile");
		Model model = TestUtils.createModel("model");
		Package pkg = TestUtils.createPackage(model, "package");    
        
		profile.define();
		model.applyProfile(profile);
		
		
		Class class_ = TestUtils.createClass(pkg, "maClasse", false);
		Class classGene = TestUtils.createClass(pkg, "autreClasse", false);
		
		Type integerPT = TestUtils.importPrimitiveType(pkg, "Integer");
		
		TestUtils.createAttribute(classGene, "test3", integerPT, 0, 1);
		
		TestUtils.createGeneralization(class_, classGene);
		
		ArrayList<Property> attributes = new ArrayList<Property>();
		
		assertEquals(attributes, ClassifierUtils.getOwnedAttributesWNull(class_));
	}

	@Test
	public void testHasMultipleId() {
		Profile profile = TestUtils.createProfile("profile");
		Model model = TestUtils.createModel("model");
		Package pkg = TestUtils.createPackage(model, "package");    
		Stereotype stereotype = TestUtils.createStereotype(profile, Utils.MODEL_KEYATTRIBUTE, false);
		
		Class propertyMetaclass = TestUtils.referenceMetaclass(profile, UMLPackage.Literals.PROPERTY.getName());
        TestUtils.createExtension(propertyMetaclass, stereotype, false);
        
		profile.define();
		model.applyProfile(profile);
		
		Class class_ = TestUtils.createClass(pkg, "maClasse", false);
		
		Type booleanPT = TestUtils.importPrimitiveType(pkg, "Boolean");
		Property attribute1 = TestUtils.createAttribute(class_, "test", booleanPT, 0, 1);
		Type integerPT = TestUtils.importPrimitiveType(pkg, "Integer");
		Property attribute2 = TestUtils.createAttribute(class_, "test2", integerPT, 0, 1);
		
		attribute1.applyStereotype(stereotype);	
		assertEquals(false, ClassifierUtils.hasMultipleId(class_));
		
		attribute2.applyStereotype(stereotype);
		assertEquals(true, ClassifierUtils.hasMultipleId(class_));
	}

	@Test
	public void testGetId() {
		Profile profile = TestUtils.createProfile("profile");
		Model model = TestUtils.createModel("model");
		Package pkg = TestUtils.createPackage(model, "package");    
		Stereotype stereotype = TestUtils.createStereotype(profile, Utils.MODEL_KEYATTRIBUTE, false);
		
		Class propertyMetaclass = TestUtils.referenceMetaclass(profile, UMLPackage.Literals.PROPERTY.getName());
        TestUtils.createExtension(propertyMetaclass, stereotype, false);
        
		profile.define();
		model.applyProfile(profile);
		
		Class class_ = TestUtils.createClass(pkg, "maClasse", false);
		
		Type booleanPT = TestUtils.importPrimitiveType(pkg, "Boolean");
		Property attribute1 = TestUtils.createAttribute(class_, "test", booleanPT, 0, 1);
		Type integerPT = TestUtils.importPrimitiveType(pkg, "Integer");
		Property attribute2 = TestUtils.createAttribute(class_, "test2", integerPT, 0, 1);
		
		attribute1.applyStereotype(stereotype);	
		assertEquals(attribute1, ClassifierUtils.getId(class_).iterator().next());
		
		attribute2.applyStereotype(stereotype);
		
		Iterable<Property> result =  ClassifierUtils.getId(class_);
		Iterator it = result.iterator();
		assertEquals(attribute1,it.next());
		assertEquals(attribute2,it.next());
	}

	@Test
	public void testGetMultivaluedOwnedAttributes() {
		Profile profile = TestUtils.createProfile("profile");
		Model model = TestUtils.createModel("model");
		Package pkg = TestUtils.createPackage(model, "package");    
		Stereotype stereotype = TestUtils.createStereotype(profile, Utils.MODEL_KEYATTRIBUTE, false);
		
		Class propertyMetaclass = TestUtils.referenceMetaclass(profile, UMLPackage.Literals.PROPERTY.getName());
        TestUtils.createExtension(propertyMetaclass, stereotype, false);
        
		profile.define();
		model.applyProfile(profile);
		
		Class class_ = TestUtils.createClass(pkg, "maClasse", false);
		
		Type booleanPT = TestUtils.importPrimitiveType(pkg, "Boolean");
		Property attribute1 = TestUtils.createAttribute(class_, "test", booleanPT, 0, 1);
		Type integerPT = TestUtils.importPrimitiveType(pkg, "Integer");
		Property attribute2 = TestUtils.createAttribute(class_, "test2", integerPT, 0, -1);
		
		assertEquals(attribute2, ClassifierUtils.getMultivaluedOwnedAttributes(class_).iterator().next());
		
	}

	@Test
	public void testGetAllMultivaluedAttributes() {
		Profile profile = TestUtils.createProfile("profile");
		Model model = TestUtils.createModel("model");
		Package pkg = TestUtils.createPackage(model, "package");    
		Stereotype stereotype = TestUtils.createStereotype(profile, Utils.MODEL_KEYATTRIBUTE, false);
		
		Class propertyMetaclass = TestUtils.referenceMetaclass(profile, UMLPackage.Literals.PROPERTY.getName());
        TestUtils.createExtension(propertyMetaclass, stereotype, false);
        
		profile.define();
		model.applyProfile(profile);
		
		Class class_ = TestUtils.createClass(pkg, "maClasse", false);
		Class classGene = TestUtils.createClass(pkg, "maClasseGene", false);
		
		Type booleanPT = TestUtils.importPrimitiveType(pkg, "Boolean");
		Property attribute1 = TestUtils.createAttribute(class_, "test", booleanPT, 0, 1);
		Type integerPT = TestUtils.importPrimitiveType(pkg, "Integer");
		Property attribute2 = TestUtils.createAttribute(class_, "test2", integerPT, 0, -1);
		
		Property attribute3 = TestUtils.createAttribute(classGene, "test3", integerPT, 0, -1);
		
		TestUtils.createGeneralization(class_, classGene);
		
		Iterable<Property> result = ClassifierUtils.getAllMultivaluedAttributes(class_);
		Iterator<Property> it = result.iterator();
		
		assertEquals(attribute2, it.next());
		assertEquals(attribute3, it.next());
	}

	@Test
	public void testGetNotMultivaluedOwnedAttributes() {
		Profile profile = TestUtils.createProfile("profile");
		Model model = TestUtils.createModel("model");
		Package pkg = TestUtils.createPackage(model, "package");    
		Stereotype stereotype = TestUtils.createStereotype(profile, Utils.MODEL_KEYATTRIBUTE, false);
		
		Class propertyMetaclass = TestUtils.referenceMetaclass(profile, UMLPackage.Literals.PROPERTY.getName());
        TestUtils.createExtension(propertyMetaclass, stereotype, false);
        
		profile.define();
		model.applyProfile(profile);
		
		Class class_ = TestUtils.createClass(pkg, "maClasse", false);
		
		Type booleanPT = TestUtils.importPrimitiveType(pkg, "Boolean");
		Property attribute1 = TestUtils.createAttribute(class_, "test", booleanPT, 0, 1);
		Type integerPT = TestUtils.importPrimitiveType(pkg, "Integer");
		TestUtils.createAttribute(class_, "test2", integerPT, 0, -1);
		
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
		Profile profile = TestUtils.createProfile("profile");
		Model model = TestUtils.createModel("model");
		Package pkg = TestUtils.createPackage(model, "package");
		Stereotype stereotype = TestUtils.createStereotype(profile, Utils.MODEL_ENTITY, false);
	
		Class packageMetaclass = TestUtils.referenceMetaclass(profile, UMLPackage.Literals.CLASS.getName());
        TestUtils.createExtension(packageMetaclass, stereotype, false);
        
		profile.define();
		model.applyProfile(profile);
		
		Class class_ = TestUtils.createClass(model, "maClasse", false);
		Class otherClass = TestUtils.createClass(model, "autreClasse", false);
		Property attribute1 = TestUtils.createAttribute(otherClass, "test", class_, 0, -1);
		class_.applyStereotype(stereotype);
		otherClass.applyStereotype(stereotype);
		
		assertEquals(attribute1,ClassifierUtils.getMultivaluedReferencesToType(otherClass, class_).iterator().next());
		
	}

	@Test
	public void testGetAllReferencesTo() {
		Profile profile = TestUtils.createProfile("profile");
		Model model = TestUtils.createModel("model");
		Package pkg = TestUtils.createPackage(model, "package");
		Stereotype stereotype = TestUtils.createStereotype(profile, Utils.MODEL_ENTITY, false);
	
		Class packageMetaclass = TestUtils.referenceMetaclass(profile, UMLPackage.Literals.CLASS.getName());
        TestUtils.createExtension(packageMetaclass, stereotype, false);
        
		profile.define();
		model.applyProfile(profile);
		
		Class class_ = TestUtils.createClass(model, "maClasse", false);
		Class otherClass = TestUtils.createClass(model, "autreClasse", false);
		Property attribute1 = TestUtils.createAttribute(otherClass, "test", class_, 0, -1);
		class_.applyStereotype(stereotype);
		otherClass.applyStereotype(stereotype);
		
		assertEquals(attribute1,ClassifierUtils.getAllReferencesTo(class_).iterator().next());
		
	}

	@Test
	public void testGetOneToManyAttributes() {
		Profile profile = TestUtils.createProfile("profile");
		Model model = TestUtils.createModel("model");
		Package pkg = TestUtils.createPackage(model, "package");
		Stereotype stereotype = TestUtils.createStereotype(profile, Utils.MODEL_ENTITY, false);
	
		Class packageMetaclass = TestUtils.referenceMetaclass(profile, UMLPackage.Literals.CLASS.getName());
        TestUtils.createExtension(packageMetaclass, stereotype, false);
        
		profile.define();
		model.applyProfile(profile);
		
		Class class_ = TestUtils.createClass(pkg, "maClasse", false);
		Class otherClass = TestUtils.createClass(pkg, "autreClasse", false);
		class_.applyStereotype(stereotype);
		otherClass.applyStereotype(stereotype);
		
		class_.createAssociation(false, AggregationKind.NONE_LITERAL, "test", 1, 1, otherClass, true, AggregationKind.NONE_LITERAL, "test2", 0, -1);	
		
		ArrayList<Property> result = ClassifierUtils.getOneToManyAttributes(class_);
		Property test = result.get(0);
			
		assertEquals("test2",test.getName());
	}

	@Test
	public void testGetManyToManyAttributes() {
		Profile profile = TestUtils.createProfile("profile");
		Model model = TestUtils.createModel("model");
		Package pkg = TestUtils.createPackage(model, "package");
		Stereotype stereotype = TestUtils.createStereotype(profile, Utils.MODEL_ENTITY, false);
	
		Class packageMetaclass = TestUtils.referenceMetaclass(profile, UMLPackage.Literals.CLASS.getName());
        TestUtils.createExtension(packageMetaclass, stereotype, false);
        
		profile.define();
		model.applyProfile(profile);
		
		Class class_ = TestUtils.createClass(pkg, "maClasse", false);
		Class otherClass = TestUtils.createClass(pkg, "autreClasse", false);
		class_.applyStereotype(stereotype);
		otherClass.applyStereotype(stereotype);
		
		class_.createAssociation(false, AggregationKind.NONE_LITERAL, "test", 0, -1, otherClass, true, AggregationKind.NONE_LITERAL, "test2", 0, -1);	
		
		ArrayList<Property> result = ClassifierUtils.getManyToManyAttributes(class_);
		Property test = result.get(0);
			
		assertEquals("test2",test.getName());
		
	}

	@Test
	public void testGetLinkedAssociationClass() {
		Profile profile = TestUtils.createProfile("profile");
		Model model = TestUtils.createModel("model");
		Package pkg = TestUtils.createPackage(model, "package");
		Stereotype stereotype = TestUtils.createStereotype(profile, Utils.MODEL_ENTITY, false);
	
		Class packageMetaclass = TestUtils.referenceMetaclass(profile, UMLPackage.Literals.CLASS.getName());
        TestUtils.createExtension(packageMetaclass, stereotype, false);
        
		profile.define();
		model.applyProfile(profile);
		Class class_ = TestUtils.createClass(pkg, "maClasse", false);
		Type type = model.createOwnedType("test", class_.eClass());
		
		fail("Not yet implemented");
	}

	@Test
	public void testIsEnumWithCode() {
		Profile profile = TestUtils.createProfile("profile");
		Model model = TestUtils.createModel("model");
		Package pkg = TestUtils.createPackage(model, "package");
		Stereotype stereotype = TestUtils.createStereotype(profile, Utils.MODEL_NOMENCLATURE, false);
		
		Stereotype stereotype2 = TestUtils.createStereotype(profile, Utils.MODEL_CODELIBELLENOMENCLATURE, false);
		
		Type IntegerPT = TestUtils.importPrimitiveType(pkg, "String");
		Property attribute = TestUtils.createAttribute(stereotype2, Utils.MODEL_CODELIBELLENOMENCLATURE_CODE, IntegerPT, 0, 1);
		Class classMetaclass = TestUtils.referenceMetaclass(profile, UMLPackage.Literals.CLASS.getName());
		Class propertyMetaclass = TestUtils.referenceMetaclass(profile, UMLPackage.Literals.PROPERTY.getName());
		
        TestUtils.createExtension(classMetaclass, stereotype, false);
        TestUtils.createExtension(propertyMetaclass, stereotype2, false);
        
		profile.define();
		model.applyProfile(profile);
		
		Class class_ = TestUtils.createClass(model, "maClasse", false);
		class_.applyStereotype(stereotype);
		
		Property attribute1 = TestUtils.createAttribute(class_, "test", IntegerPT, 0, -1);
		attribute1.applyStereotype(stereotype2);
		TestUtils.setStereotypePropertyValue(attribute1, stereotype2, attribute, "");
		
		assertEquals(false, ClassifierUtils.isEnumWithCode(class_));
		TestUtils.setStereotypePropertyValue(attribute1, stereotype2, attribute, "test");
		
		assertEquals(true, ClassifierUtils.isEnumWithCode(class_));
	}

	@Test
	public void testCanBeGenerated() {
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
		assertEquals(true, ClassifierUtils.canBeGenerated(class_));
		
		TestUtils.setStereotypePropertyValue(class_, stereotype, attribute, false);
		assertEquals(false, ClassifierUtils.canBeGenerated(class_));
	}

	@Test
	public void testGetTableName() {
		Profile profile = TestUtils.createProfile("profile");
		Model model = TestUtils.createModel("model");
		Package pkg = TestUtils.createPackage(model, "package");
		Stereotype stereotype = TestUtils.createStereotype(profile, Utils.MODEL_ENTITY, false);
		
		Type stringPT = TestUtils.importPrimitiveType(pkg, "String");
		Property attribute = TestUtils.createAttribute(stereotype, Utils.MODEL_ENTITY_TABLENAME, stringPT, 0, 1);
		Class packageMetaclass = TestUtils.referenceMetaclass(profile, UMLPackage.Literals.CLASS.getName());
        TestUtils.createExtension(packageMetaclass, stereotype, false);
        
		profile.define();
		model.applyProfile(profile);
		
		Classifier class_ = TestUtils.createClass(pkg, "maClasse", false);
		class_.applyStereotype(stereotype);
		
		assertEquals("ma_classe", ClassifierUtils.getTableName(class_));
		
		TestUtils.setStereotypePropertyValue(class_, stereotype, attribute, "testTableName");
		assertEquals("testTableName", ClassifierUtils.getTableName(class_));
	}

	@Test
	public void testGetDBTableName() {
		Profile profile = TestUtils.createProfile("profile");
		Model model = TestUtils.createModel("model");
		Package pkg = TestUtils.createPackage(model, "package");
		Stereotype stereotype = TestUtils.createStereotype(profile, Utils.MODEL_ENTITY, false);
		
		Type stringPT = TestUtils.importPrimitiveType(pkg, "String");
		Property attribute = TestUtils.createAttribute(stereotype, Utils.MODEL_ENTITY_TABLENAME, stringPT, 0, 1);
		Class packageMetaclass = TestUtils.referenceMetaclass(profile, UMLPackage.Literals.CLASS.getName());
        TestUtils.createExtension(packageMetaclass, stereotype, false);
        
		profile.define();
		model.applyProfile(profile);
		
		Classifier class_ = TestUtils.createClass(pkg, "maClasse", false);
		class_.applyStereotype(stereotype);
		
		assertEquals("MA_CLASSE", ClassifierUtils.getDBTableName(class_));
		
		TestUtils.setStereotypePropertyValue(class_, stereotype, attribute, "testTableName");
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
	public void testGetTableNameValue() {
		Profile profile = TestUtils.createProfile("profile");
		Model model = TestUtils.createModel("model");
		Package pkg = TestUtils.createPackage(model, "package");
		Stereotype stereotype = TestUtils.createStereotype(profile, Utils.MODEL_ENTITY, false);
		
		Type stringPT = TestUtils.importPrimitiveType(pkg, "String");
		Property attribute = TestUtils.createAttribute(stereotype, Utils.MODEL_ENTITY_TABLENAME, stringPT, 0, 1);
		Class packageMetaclass = TestUtils.referenceMetaclass(profile, UMLPackage.Literals.CLASS.getName());
        TestUtils.createExtension(packageMetaclass, stereotype, false);
        
		profile.define();
		model.applyProfile(profile);
		
		Classifier class_ = TestUtils.createClass(pkg, "maClasse", false);
		class_.applyStereotype(stereotype);
		
		TestUtils.setStereotypePropertyValue(class_, stereotype, attribute, "testTableName");
		assertEquals("testTableName", ClassifierUtils.getTableNameValue(class_));
	}
	
	@Test
	public void testNomenclatureGetTableNameValue() {
		Profile profile = TestUtils.createProfile("profile");
		Model model = TestUtils.createModel("model");
		Package pkg = TestUtils.createPackage(model, "package");
		Stereotype stereotype = TestUtils.createStereotype(profile, Utils.MODEL_NOMENCLATURE, false);
		
		Type stringPT = TestUtils.importPrimitiveType(pkg, "String");
		Property attribute = TestUtils.createAttribute(stereotype, Utils.MODEL_NOMENCLATURE_TABLENAME, stringPT, 0, 1);
		Class packageMetaclass = TestUtils.referenceMetaclass(profile, UMLPackage.Literals.CLASS.getName());
        TestUtils.createExtension(packageMetaclass, stereotype, false);
        
		profile.define();
		model.applyProfile(profile);
		
		Classifier class_ = TestUtils.createClass(pkg, "maClasse", false);
		class_.applyStereotype(stereotype);
		
		TestUtils.setStereotypePropertyValue(class_, stereotype, attribute, "testTableName");
		assertEquals("testTableName", ClassifierUtils.getTableNameValue(class_));
	}

}
