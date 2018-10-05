
/**
 * Copyright ou © ou Copr. Ministère de l'Europe et des Affaires étrangères (2017)
 * <p/>
 * pole-architecture.dga-dsi-psi@diplomatie.gouv.fr
 * <p/>
 * Ce logiciel est un programme informatique servant à faciliter la création
 * d'applications Web conformément aux référentiels généraux français : RGI, RGS et RGAA
 * <p/>
 * Ce logiciel est régi par la licence CeCILL soumise au droit français et
 * respectant les principes de diffusion des logiciels libres. Vous pouvez
 * utiliser, modifier et/ou redistribuer ce programme sous les conditions
 * de la licence CeCILL telle que diffusée par le CEA, le CNRS et l'INRIA
 * sur le site "http://www.cecill.info".
 * <p/>
 * En contrepartie de l'accessibilité au code source et des droits de copie,
 * de modification et de redistribution accordés par cette licence, il n'est
 * offert aux utilisateurs qu'une garantie limitée.  Pour les mêmes raisons,
 * seule une responsabilité restreinte pèse sur l'auteur du programme,  le
 * titulaire des droits patrimoniaux et les concédants successifs.
 * <p/>
 * A cet égard  l'attention de l'utilisateur est attirée sur les risques
 * associés au chargement,  à l'utilisation,  à la modification et/ou au
 * développement et à la reproduction du logiciel par l'utilisateur étant
 * donné sa spécificité de logiciel libre, qui peut le rendre complexe à
 * manipuler et qui le réserve donc à des développeurs et des professionnels
 * avertis possédant  des  connaissances  informatiques approfondies.  Les
 * utilisateurs sont donc invités à charger  et  tester  l'adéquation  du
 * logiciel à leurs besoins dans des conditions permettant d'assurer la
 * sécurité de leurs systèmes et ou de leurs données et, plus généralement,
 * à l'utiliser et l'exploiter dans les mêmes conditions de sécurité.
 * <p/>
 * Le fait que vous puissiez accéder à cet en-tête signifie que vous avez
 * pris connaissance de la licence CeCILL, et que vous en avez accepté les
 * termes.
 * <p/>
 * <p/>
 * Copyright or © or Copr. Ministry for Europe and Foreign Affairs (2017)
 * <p/>
 * pole-architecture.dga-dsi-psi@diplomatie.gouv.fr
 * <p/>
 * This software is a computer program whose purpose is to facilitate creation of
 * web application in accordance with french general repositories : RGI, RGS and RGAA.
 * <p/>
 * This software is governed by the CeCILL license under French law and
 * abiding by the rules of distribution of free software.  You can  use,
 * modify and/ or redistribute the software under the terms of the CeCILL
 * license as circulated by CEA, CNRS and INRIA at the following URL
 * "http://www.cecill.info".
 * <p/>
 * As a counterpart to the access to the source code and  rights to copy,
 * modify and redistribute granted by the license, users are provided only
 * with a limited warranty  and the software's author,  the holder of the
 * economic rights,  and the successive licensors  have only  limited
 * liability.
 * <p/>
 * In this respect, the user's attention is drawn to the risks associated
 * with loading,  using,  modifying and/or developing or reproducing the
 * software by the user in light of its specific status of free software,
 * that may mean  that it is complicated to manipulate,  and  that  also
 * therefore means  that it is reserved for developers  and  experienced
 * professionals having in-depth computer knowledge. Users are therefore
 * encouraged to load and test the software's suitability as regards their
 * requirements in conditions enabling the security of their systems and/or
 * data to be ensured and,  more generally, to use and operate it in the
 * same conditions as regards security.
 * <p/>
 * The fact that you are presently reading this means that you have had
 * knowledge of the CeCILL license and that you accept its terms.
 *
 */

/**
 * fr.gouv.diplomatie.papyrus.codegen.core - Ensembles des outils mis à dispositions
 * pour l'écriture d'un générateur de code Hornet
 *
 * @author MEAE - Ministère de l'Europe et des Affaires étrangères
 * @version v1.2.1
 * @license CECILL-2.1
 */
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

/**
 * classe utilitaire pour les tests 
 * métamodèle Hornet Papyrus
 * @author cadiomu
 *
 */
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
	 public Property entitySchema;
	 
	 public Stereotype valueObject;
	 public Property valueObjectGenerated;
	 
	 public Stereotype nomenclature;
	 public Property nomenclatureTableName;
	 public Property nomenclatureGenerated;
	 public Property nomenclatureSchema;
	 
	 public Stereotype associationTable;
	 public Property associationTableSchema;
	 
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
		entitySchema = TestUtils.createAttribute(entity, Utils.MODEL_DOMAIN_SCHEMA, stringPT, 0, 1);
		
		valueObjectGenerated = TestUtils.createAttribute(valueObject, Utils.MODEL_VALUEOBJECT_GENERATED, booleanPT, 0, 1);
		
		nomenclatureTableName = TestUtils.createAttribute(nomenclature, Utils.MODEL_NOMENCLATURE_TABLENAME, stringPT, 0, 1);
		nomenclatureGenerated = TestUtils.createAttribute(nomenclature, Utils.MODEL_NOMENCLATURE_GENERATED, booleanPT, 0, 1);
		nomenclatureSchema = TestUtils.createAttribute(nomenclature, Utils.MODEL_DOMAIN_SCHEMA, stringPT, 0, 1);
		
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
		
		associationTableSchema = TestUtils.createAttribute(associationTable, Utils.MODEL_DOMAIN_SCHEMA, stringPT, 0, 1);
		
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
