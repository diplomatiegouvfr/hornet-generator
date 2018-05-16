package fr.gouv.diplomatie.papyrus.codegen.core.test;

import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.Profile;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Stereotype;
import org.eclipse.uml2.uml.Type;
import org.eclipse.uml2.uml.UMLPackage;

import fr.gouv.diplomatie.papyrus.codegen.core.utils.Utils;

public class HornetModel {
	
	public static final HornetModel instance = new HornetModel();
	
	 public Model model;
	 public Profile profile;
	 public Package pckage;
	 
	 public Stereotype domain;
	 
	 public Stereotype application;
	 public Property applicationRootPackage;
	 
	 public Stereotype entity;
	 public Property entityGenerated;
	 public Property entityTableName;
	 
	 public Stereotype valueObject;
	 public Property valueObjectGenerated;
	 
	 public Stereotype nomenclature;
	 public Property nomenclatureTableName;
	 public Property nomenclatureGenerated;
	 
	 public Stereotype associationTable;
	 
	 public Stereotype associationLink;
	 
	 public Stereotype attribute;
	 public Property attributeColumnName;
	 public Property attributeLength;
	 public Property attributeHasLength;
	 
	 public Stereotype keyAttribute;
	 
	 public Stereotype hornetType;
	 
	 public Stereotype codeLibelleNomenclature;
	 public Property codeLibelleNomenclatureCode;
	 public Property codeLibelleNomenclatureLibelle;
	 
	 public Stereotype sequence;
	 public Property sequenceStartWith;
	 public Property sequenceIncrementBy;
	 public Property sequenceHasMaxValue;
	 public Property sequenceMaxValue;
	 public Property sequenceHasMinValue;
	 public Property sequenceMinValue;
	 public Property sequenceCache;
	 public Property sequenceCycle;
	 
	 public Type booleanPT;
	 public Type stringPT;
	 public Type integerPT;
	 
	 public Class metaclass;
	 public Class associationmetaclass;
	 public Class propmetaclass;
	 public Class packagemetaclass;
	 
	 public HornetModel() {
		 
		profile = TestUtils.createProfile("profile");
		model = TestUtils.createModel("model");
		pckage = TestUtils.createPackage(model, "package");
			
		metaclass = TestUtils.referenceMetaclass(profile, UMLPackage.Literals.CLASS.getName());
		associationmetaclass = TestUtils.referenceMetaclass(profile, UMLPackage.Literals.ASSOCIATION.getName());
		propmetaclass = TestUtils.referenceMetaclass(profile, UMLPackage.Literals.PROPERTY.getName());
		packagemetaclass = TestUtils.referenceMetaclass(profile, UMLPackage.Literals.PACKAGE.getName());
		
		domain = TestUtils.createStereotype(profile, Utils.MODEL_DOMAIN, false);
		TestUtils.createExtension(packagemetaclass, domain, false);
		 
		application = TestUtils.createStereotype(profile, Utils.MODEL_APPLICATION, false);
		TestUtils.createExtension(packagemetaclass, application, false);
		
		entity = TestUtils.createStereotype(profile, Utils.MODEL_ENTITY, false);
		TestUtils.createExtension(metaclass, entity, false);
		 
		valueObject = TestUtils.createStereotype(profile, Utils.MODEL_VALUEOBJECT, false);
		TestUtils.createExtension(metaclass, valueObject, false);
		 
		hornetType = TestUtils.createStereotype(profile, Utils.MODEL_HORNETTYPE, false);
		TestUtils.createExtension(metaclass, hornetType, false);
		 
		nomenclature = TestUtils.createStereotype(profile, Utils.MODEL_NOMENCLATURE, false);
		TestUtils.createExtension(metaclass, nomenclature, false);
		
		associationTable = TestUtils.createStereotype(profile, Utils.MODEL_ASSOCIATIONTABLE, false);
		TestUtils.createExtension(metaclass, associationTable, false);
		
		associationLink = TestUtils.createStereotype(profile, Utils.MODEL_ASSOCIATIONLINK, false);
		TestUtils.createExtension(associationmetaclass, associationLink, false);
		
		attribute = TestUtils.createStereotype(profile, Utils.MODEL_ATTRIBUTE, false);
		TestUtils.createExtension(propmetaclass, attribute, false);
		 
		keyAttribute = TestUtils.createStereotype(profile, Utils.MODEL_KEYATTRIBUTE, false);
		TestUtils.createExtension(propmetaclass, keyAttribute, false);
		
		codeLibelleNomenclature = TestUtils.createStereotype(profile, Utils.MODEL_CODELIBELLENOMENCLATURE, false);
		TestUtils.createExtension(propmetaclass, codeLibelleNomenclature, false);
		
		sequence = TestUtils.createStereotype(profile, Utils.MODEL_SEQUENCE, false);
		TestUtils.createExtension(propmetaclass, sequence, false);
		
		// Primary types
		booleanPT = TestUtils.importPrimitiveType(pckage, "Boolean");
		stringPT = TestUtils.importPrimitiveType(pckage, "String");
		integerPT = TestUtils.importPrimitiveType(pckage, "Integer");
		
		applicationRootPackage = TestUtils.createAttribute(application, Utils.MODEL_APPLICATION_ROOTPACKAGE, stringPT, 0, 1);
		
		entityGenerated = TestUtils.createAttribute(entity, Utils.MODEL_ENTITY_GENERATED, booleanPT, 0, 1);
		entityTableName = TestUtils.createAttribute(entity, Utils.MODEL_ENTITY_TABLENAME, stringPT, 0, 1);
		
		valueObjectGenerated = TestUtils.createAttribute(valueObject, Utils.MODEL_VALUEOBJECT_GENERATED, booleanPT, 0, 1);
		
		nomenclatureTableName = TestUtils.createAttribute(nomenclature, Utils.MODEL_NOMENCLATURE_TABLENAME, stringPT, 0, 1);
		nomenclatureGenerated = TestUtils.createAttribute(nomenclature, Utils.MODEL_NOMENCLATURE_GENERATED, booleanPT, 0, 1);
		
		attributeColumnName = TestUtils.createAttribute(attribute, Utils.MODEL_ATTRIBUTE_COLUMNNAME, stringPT, 0, 1);
		attributeLength = TestUtils.createAttribute(attribute, Utils.MODEL_ATTRIBUTE_LENGTH, integerPT, 0, 1);
		attributeHasLength = TestUtils.createAttribute(attribute, "hasLength" , booleanPT, 0, 1);
		
		codeLibelleNomenclatureCode = TestUtils.createAttribute(codeLibelleNomenclature, Utils.MODEL_CODELIBELLENOMENCLATURE_CODE, integerPT, 0, 1);
		codeLibelleNomenclatureLibelle = TestUtils.createAttribute(codeLibelleNomenclature, Utils.MODEL_CODELIBELLENOMENCLATURE_LIBELLE, stringPT, 0, 1);
		
		sequenceStartWith = TestUtils.createAttribute(sequence, Utils.MODEL_SEQUENCE_STARTWITH, integerPT, 0, 1);
		sequenceIncrementBy = TestUtils.createAttribute(sequence, Utils.MODEL_SEQUENCE_INCREMENTBY, integerPT, 0, 1);
		sequenceHasMaxValue = TestUtils.createAttribute(sequence, Utils.MODEL_SEQUENCE_HASMAXVALUE, booleanPT, 0, 1);
		sequenceMaxValue = TestUtils.createAttribute(sequence, Utils.MODEL_SEQUENCE_MAXVALUE, integerPT, 0, 1);
		sequenceHasMinValue = TestUtils.createAttribute(sequence, Utils.MODEL_SEQUENCE_HASMINVALUE, booleanPT, 0, 1);
		sequenceMinValue = TestUtils.createAttribute(sequence, Utils.MODEL_SEQUENCE_MINVALUE, integerPT, 0, 1);
		sequenceCache = TestUtils.createAttribute(sequence, Utils.MODEL_SEQUENCE_CACHE, integerPT, 0, 1);
		sequenceCycle = TestUtils.createAttribute(sequence, Utils.MODEL_SEQUENCE_CYCLE, booleanPT, 0, 1);
		
		TestUtils.createGeneralization(keyAttribute, attribute);
		
		profile.define();
		model.applyProfile(profile);
	 }
	 
	 public void clean() {
		model = TestUtils.createModel("model");
		pckage = TestUtils.createPackage(model, "package");
		model.applyProfile(profile);
	 }
	 
	 public static HornetModel initModel() {
		instance.clean();
	 	return instance;
	 }
}
