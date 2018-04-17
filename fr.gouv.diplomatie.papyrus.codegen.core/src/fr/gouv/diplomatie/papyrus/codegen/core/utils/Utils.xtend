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
 * @version v1.1.0
 * @license CECILL-2.1
 */
package fr.gouv.diplomatie.papyrus.codegen.core.utils

import org.eclipse.uml2.uml.NamedElement
import java.io.File
import java.util.ArrayList
import fr.gouv.diplomatie.papyrus.codegen.core.console.ConsoleUtils
import org.eclipse.uml2.uml.Type
import org.eclipse.uml2.uml.Classifier
import org.eclipse.uml2.uml.Stereotype

public class Utils{
	
	public static var console = new ConsoleUtils();
		
	public static var MODEL_DOMAIN = 'domain';
	
	public static var MODEL_ENTITY = 'entity';
	public static var MODEL_ENTITY_GENERATED = 'generated';
	public static var MODEL_ENTITY_TABLENAME = 'tableName';
	public static var MODEL_ENTITY_PERSISTENT = 'isPersistent';
	
	public static var MODEL_FETCHTYPE = 'fetchType';
	
	public static var MODEL_ASSOCIATIONTABLE = 'associationTable';
	
	public static var MODEL_ASSOCIATIONLINK = 'associationLink';
	
	public static var MODEL_NOMENCLATURE = 'nomenclature';
	public static var MODEL_NOMENCLATURE_VALEURS = 'valeurs';
	public static var MODEL_NOMENCLATURE_GENERATED = 'generated';
	public static var MODEL_NOMENCLATURE_TABLENAME = 'tableName';
	
	public static var MODEL_CODELIBELLENOMENCLATURE = 'CodeLibelleNomenclature';
	public static var MODEL_CODELIBELLENOMENCLATURE_CODE = 'code';
	public static var MODEL_CODELIBELLENOMENCLATURE_LIBELLE = 'libelle';
	
	public static var MODEL_VALUEOBJECT = 'valueObject';
	public static var MODEL_VALUEOBJECT_GENERATED = 'generated';
	public static var MODEL_VALUEOBJECT_PERSISTENT = 'isPersistent';
	
	public static var MODEL_ATTRIBUTE = 'attribute';
	public static var MODEL_ATTRIBUTE_LENGTH = 'length';
	public static var MODEL_ATTRIBUTE_PERSISTENT = 'isPersistent';
	public static var MODEL_ATTRIBUTE_COLUMNNAME = 'columnName';
	
	public static var MODEL_KEYATTRIBUTE = 'keyAttribute';
	
	public static var MODEL_SEQUENCE = 'sequence';
	public static var MODEL_SEQUENCE_STARTWITH = 'startWith';
	public static var MODEL_SEQUENCE_INCREMENTBY = 'incrementBy';
	public static var MODEL_SEQUENCE_HASMINVALUE = 'hasMinValue';
	public static var MODEL_SEQUENCE_MINVALUE = 'minValue';
	public static var MODEL_SEQUENCE_HASMAXVALUE = 'hasMaxValue';
	public static var MODEL_SEQUENCE_MAXVALUE = 'maxValue';
	public static var MODEL_SEQUENCE_CACHE = 'cache';
	public static var MODEL_SEQUENCE_CYCLE = 'cycle';
	
	public static var MODEL_APPLICATION = 'application';
	public static var MODEL_APPLICATION_ROOTPACKAGE = 'rootPackage';
	
	public static var MODEL_HORNETTYPE = 'hornetType';
	
	static def getDomainName(Classifier clazz){
		val classPckg =clazz.package
		
		if(Utils.isDomain(classPckg)){
			return classPckg.name
		}
		return ""
	}
	
	/**
	 * génère une chaine a partir d'une liste
	 */
	static def generateList(Iterable list){
		var retour = "";
		for(elem : list){
			if(elem instanceof NamedElement){
				retour += '"' +elem.name + '"'+ ", "
			}else{
				retour += '"' +elem + '"' + ", "
			}
		}
		return retour.substring(0,retour.length -2);
	}
	
	/**
	 * renvoie la chaine en lettres minuscules
	 */
	def static lowerCase(String str) {
		str.toLowerCase
	}
	
	/**
	 * renvoie la chaine en lettres capitales
	 */
	def static capitalize(String s) {
		if (s === null || s.length() == 0)
			return s;
		if (Character.isUpperCase(s.charAt(0)))
			return s;
		if (s.length() == 1)
			return s.toUpperCase();
		return s.substring(0, 1).toUpperCase() + s.substring(1);
	}
	
	/**
	 * renvoie la chaine sous le format de base de données 
	 * ex: idTest -> ID_TEST
	 */
	static def toDbName(String name){
		return (toSnakeCase(name)).toUpperCase
	}
	

	static def Classifier[] getAllgene(Classifier elem){
		val genes = elem.generalizations
		var allTypes = newArrayList
		for(gene : genes){
			allTypes.add(gene.general)
			allTypes.addAll(gene.general.getAllgene)
		}
		
		return allTypes
	}
	
	static def hasType(NamedElement elem, String typeName){
		val stereos = elem.appliedStereotypes
		var allTypes = newArrayList
		for(stereo : stereos){
			allTypes.addAll(stereo.getAllgene)	
		}
		for(type: allTypes){
			if (type.name == typeName){
				return true
			}
		}
		return false
	}
	
	/**
	 * a appelé pour tester si un element possède le stereotype passer en paramètre
	 */
	static def hasStereotype(NamedElement elem, String stereotypeName){
		if(elem !== null){
			return (elem.getStereotype(stereotypeName) !==null && !(elem.getStereotype(stereotypeName).empty))|| elem.hasType(stereotypeName)
		}else{
			return false
		}
	}
	
	/**
	 * teste si un element est de type entity
	 */
	static def isEntity(NamedElement elem){
		elem.hasStereotype(MODEL_ENTITY)
	}
	
	/**
	 * teste si un element est de type valueObject
	 */
	static def isValueObject(NamedElement elem){
		elem.hasStereotype(MODEL_VALUEOBJECT)
	}
	
	/**
	 * teste si un element est de type sequence
	 */
	static def isSequence(NamedElement elem){
		elem.hasStereotype(MODEL_SEQUENCE)
	}
	
	/**
	 * teste si un element est de type domain
	 */
	static def isDomain(NamedElement elem){
		elem.hasStereotype(MODEL_DOMAIN)
	}
	
	/**
	 * teste si un element est de type associationTable
	 */
	static def isAssociationTable(NamedElement elem){
		elem.hasStereotype(MODEL_ASSOCIATIONTABLE)
	}
	
	/**
	 * teste si un element est de type associationLink
	 */
	static def isAssociationLink(NamedElement elem){
		elem.hasStereotype(MODEL_ASSOCIATIONLINK)
	}
	
	/**
	 * teste si un element est un enum
	 */
	static def isNomenclature(NamedElement elem){
		elem.hasStereotype(MODEL_NOMENCLATURE)
	}
	
	/**
	 * retourne la classe Stereotype 
	 */
	static def getStereotype(NamedElement clazz, String stereoname) {
		return clazz.appliedStereotypes.filter[stereotype |
			stereotype.name == stereoname
		]
	}
	
	/**
	 * retourne la valeur de la propriété liée au stereotype
	 */
	static def getStereotypePropertyValue(NamedElement elem, String stereoname, String property){
		if(elem.getStereotype(stereoname) ===null || elem.getStereotype(stereoname).empty)
		return null
		
		val stereotype = elem.getStereotype(stereoname).get(0);
		
		if(stereotype === null)
			return null
			 		
		return elem.getValue(stereotype ,property);
	}
	
	/**
	 * chemin de fichier passer en notation pointée
	 */
	static def toPath(String path){
		return path.replaceAll("\\.", File.separator);
	}
	
	/**
	 * inverse
	 */
	static def toImport(String path){
		return path.replaceAll("\\/", ".");
	}
	
	
	/**
	 * Génère les commentaires
	 */
	static def generateComments(NamedElement elem){
		val comments = elem.ownedComments
		if(comments !== null && !comments.empty){
			return
			'''
			/**
			«comments.fold("")[acc, comment|
				acc + '''* «comment.body»'''
			]»
			*/
			'''
		}else{
			return ''''''
		}
		
	}
	
	/**
	 * renvoie la chaine avec la première lettre en minuscule
	 */
	static def getFirstToLowerCase(String str){
		return str.toFirstLower()
	}
	
	/**
	 * renvoie la chaine avec la premiere lettre en majuscule
	 */
	static def getFirstToUpperCase(String str){
		return str.toFirstUpper()
	}
	
	/**
	 * convertit la chaine en snake case
	 */
	static def String toSnakeCase( String varName){
		var String result = ""
		var String[] cutName = varName.split("(?=\\p{Upper})");
		for(String name : cutName){
			if(result ==""){
				result +=  name.toLowerCase	
			}else{
				result += "_" + name.toLowerCase	
			}
		}
		return result
	}
	
	/**
	 * convertit la chaine en nom de fichier typescript
	 */
	static def String toTypescriptFileName( String varName){
		var String result = ""
		var String[] cutName = varName.split("(?=\\p{Upper})");
		for(String name : cutName){
			if(result ==""){
				result +=  name.toLowerCase	
			}else{
				result += "-" + name.toLowerCase	
			}
		}
		return result
	}
	
	/**
	 * si additionnalName est définit ajoute le nom additionnel devant le champs 
	 */
	static def addAdditionnalName(String additionnalName, String name){
		var newName =""
		if(additionnalName != "" && additionnalName !==null){
			newName = additionnalName + Utils.getFirstToUpperCase(name)
		}else{
			newName = name
		}
		
		return newName
	}
	
	/**
	 * renvoie la liste en notation pointée
	 */
	static def String getListPoint(ArrayList<String> names){
		var String result=""
		for(String name : names){
			if(result == ""){
				result += name
			}else{
				result += '.' + name
			}
		}
		return result
	}
	
	/**
	 * renvoie la liste des noms séparés par des virgules
	 */
	static def String getListComma(ArrayList<String> names){
		var String result=""
		for(String name : names){
			if(result == ""){
				result += name
			}else{
				result += ', ' + name
			}
		}
		return result
	}
	
		/**
	 * renvoie la liste des noms séparés par des virgules
	 */
	static def String getListStringComma(ArrayList<String> names){
		var String result=""
		for(String name : names){
			if(result == ""){
				result += '"' + name + '"'
			}else{
				result += ', "' + name + '"'
			}
		}
		return result
	}
	
	/**
	 * renvoie la liste en notation pointée
	 */
	static def String getNameFromList(ArrayList<String> names){
		var String result=""
		for(String name : names){
			if(result == ""){
				result += name
			}else{
				result += Utils.getFirstToUpperCase(name)
			}
		}
		return result
	}
	
	/**
	 * package du projet
	 */
	static def getRootPackage(NamedElement elem){
		if(elem !== null){
			val model = elem.model;
			return Utils.getStereotypePropertyValue(model,MODEL_APPLICATION, MODEL_APPLICATION_ROOTPACKAGE);
		}
		return null
	}
	
	static def getPackagePath(NamedElement elem) {
		var path ="";
		if(Utils.getRootPackage(elem) !== null) {
			val rootPackage = Utils.getRootPackage(elem).toString();
			rootPackage.replace("\\", File.separator)
			rootPackage.replace("/", File.separator)
			path = toPath(rootPackage)
		}else {
			path = "fr"+ File.separator + "gouv" + File.separator + "diplomatie"
		}
		return path;
	}
	
	/**
	 * teste si une liste contiens un élément dont le nom est name
	 */
	static def containsName(ArrayList<NamedElement> list, String name){
		for(elem : list){
			if(elem.name == name){
				return true
			}
		}
		return false
	}
	
	/**
	 * teste si un type est ou étend le type name
	 */
	static def isType(Type type, String name){
		var generalization = newArrayList()
		if(type instanceof Classifier){
			for(elem : type.generalizations){
				generalization.add(elem.general)
			}
		}		
		if(type.name == name || (generalization!== null && !generalization.empty && (generalization.containsName(name)))){
			return true
		}
		return false
	}
	
	/**                                                        *
	 * ----------------- stereotype attributes ----------------*
	 */
	 
	 /**
	 * retourne le code de la propriété de la nomenclature
	 */
	static def getNomenclatureCode(NamedElement elem){
		return Utils.getStereotypePropertyValue(elem, Utils.MODEL_CODELIBELLENOMENCLATURE, Utils.MODEL_CODELIBELLENOMENCLATURE_CODE)
	}
	
	/**
	 * retourne le code de la propriété de la nomenclature
	 */
	static def getNomenclatureLibelle(NamedElement elem){
		return Utils.getStereotypePropertyValue(elem, Utils.MODEL_CODELIBELLENOMENCLATURE, Utils.MODEL_CODELIBELLENOMENCLATURE_LIBELLE)
	}
	
	static def getAttributeLength(NamedElement elem){
		
		if(Utils.hasStereotype(elem, Utils.MODEL_KEYATTRIBUTE)){
			val hasLength = Utils.getStereotypePropertyValue(elem, Utils.MODEL_KEYATTRIBUTE, 'hasLength')
			if(hasLength == true){
				return Utils.getStereotypePropertyValue(elem, Utils.MODEL_KEYATTRIBUTE, Utils.MODEL_ATTRIBUTE_LENGTH)
			}
			return null
		}else{
			val hasLength = Utils.getStereotypePropertyValue(elem, Utils.MODEL_ATTRIBUTE, 'hasLength')
			if(hasLength == true){
				return Utils.getStereotypePropertyValue(elem, Utils.MODEL_ATTRIBUTE, Utils.MODEL_ATTRIBUTE_LENGTH)	
			}
			return null
		}
	}
	
	static def getSequenceStartWith(NamedElement elem){
		return Utils.getStereotypePropertyValue(elem, Utils.MODEL_SEQUENCE, Utils.MODEL_SEQUENCE_STARTWITH)
	}
	
	static def getSequenceIncrementBy(NamedElement elem){
		return Utils.getStereotypePropertyValue(elem, Utils.MODEL_SEQUENCE, Utils.MODEL_SEQUENCE_INCREMENTBY)
	}
	
	static def getSequenceHasMaxValue(NamedElement elem){
		return Utils.getStereotypePropertyValue(elem, Utils.MODEL_SEQUENCE, Utils.MODEL_SEQUENCE_HASMAXVALUE)
	}
	
	static def getSequenceMaxValue(NamedElement elem){
		return Utils.getStereotypePropertyValue(elem, Utils.MODEL_SEQUENCE, Utils.MODEL_SEQUENCE_MAXVALUE)
	}
	
	static def getSequenceHasMinValue(NamedElement elem){
		return Utils.getStereotypePropertyValue(elem, Utils.MODEL_SEQUENCE, Utils.MODEL_SEQUENCE_HASMINVALUE)
	}
	
	static def getSequenceMinValue(NamedElement elem){
		return Utils.getStereotypePropertyValue(elem, Utils.MODEL_SEQUENCE, Utils.MODEL_SEQUENCE_MINVALUE)
	}
	
	static def getSequenceCache(NamedElement elem){
		return Utils.getStereotypePropertyValue(elem, Utils.MODEL_SEQUENCE, Utils.MODEL_SEQUENCE_CACHE)
	}
	
	static def getSequenceCycle(NamedElement elem){
		return Utils.getStereotypePropertyValue(elem, Utils.MODEL_SEQUENCE, Utils.MODEL_SEQUENCE_CYCLE)
	}
	
	static def getGenerated(NamedElement elem){
		if(Utils.isEntity(elem)){
			return Utils.getStereotypePropertyValue(elem, Utils.MODEL_ENTITY, Utils.MODEL_ENTITY_GENERATED)
		}else if(Utils.isNomenclature(elem)){
			return Utils.getStereotypePropertyValue(elem, Utils.MODEL_NOMENCLATURE, Utils.MODEL_NOMENCLATURE_GENERATED)
		}else if (Utils.isValueObject(elem)){
			return Utils.getStereotypePropertyValue(elem, Utils.MODEL_VALUEOBJECT, Utils.MODEL_VALUEOBJECT_GENERATED)
		}
		return true
	}
	
}