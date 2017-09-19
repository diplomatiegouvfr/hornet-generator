package fr.gouv.diplomatie.papyrus.codegen.core.utils

import org.eclipse.uml2.uml.NamedElement
import java.io.File
import java.util.ArrayList
import fr.gouv.diplomatie.papyrus.codegen.core.console.ConsoleUtils
import org.eclipse.uml2.uml.Type
import org.eclipse.uml2.uml.Classifier

public class Utils{
	
	public static var console = new ConsoleUtils();
		
	public static var MODEL_ENTITY = 'entity';
	public static var MODEL_ENTITY_GENERATED = 'generated';
	public static var MODEL_ENTITY_TABLENAME = 'tableName';
	public static var MODEL_ENTITY_PERSISTENT = 'isPersistent';
	
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
	public static var MODEL_ATTRIBUTE_SEARCHABLE = 'searchable';
	public static var MODEL_ATTRIBUTE_LENGTH = 'length';
	public static var MODEL_ATTRIBUTE_PERSISTENT = 'isPersistent';
	public static var MODEL_ATTRIBUTE_SEARCHANALYZER = 'searchAnalyzer';
	
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
	 * teste si un element est de type entity
	 */
	static def isEntity(NamedElement elem){
		if(elem !== null){
			return (elem.getStereotype(MODEL_ENTITY) !==null && !(elem.getStereotype(MODEL_ENTITY).empty))
		}else{
			return false
		}
	}
	
	/**
	 * teste si un element est de type valueObject
	 */
	static def isValueObject(NamedElement elem){
		if(elem !== null){
			return (elem.getStereotype(MODEL_VALUEOBJECT) !==null && !(elem.getStereotype(MODEL_VALUEOBJECT).empty))
		}else{
			return false
		}
	}
	
	/**
	 * teste si un element est de type sequence
	 */
	static def isSequence(NamedElement elem){
		if(elem !== null){
			return (elem.getStereotype(MODEL_SEQUENCE) !==null && !(elem.getStereotype(MODEL_SEQUENCE).empty))	
		}else{
			return false
		}
	}
	
	/**
	 * teste si un element est un enum
	 */
	static def isNomenclature(NamedElement elem){
		if(elem !== null){
			return (elem.getStereotype(MODEL_NOMENCLATURE) !== null && !(elem.getStereotype(MODEL_NOMENCLATURE).empty))
		}else{
			return false
		}
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
}