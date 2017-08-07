package fr.gouv.diplomatie.papyrus.codegen.xtend.utils

import org.eclipse.uml2.uml.NamedElement
import java.io.File

class Utils{
	
	public static var MODEL_ENTITY = 'entity';
	public static var MODEL_VALUEOBJECT = 'valueObject';
	public static var MODEL_ATTRIBUTE = 'attribute';
	public static var MODEL_ATTRIBUTE_LENGTH = 'length';
	public static var MODEL_KEYATTRIBUTE = 'keyAttribute';
	
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
	
	
	def static lowerCase(String str) {
		str.toLowerCase
	}
	
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
		return (elem.getStereotype(MODEL_ENTITY) !==null && !(elem.getStereotype(MODEL_ENTITY).empty))
	}
	
		/**
	 * teste si un element est de type valueObject
	 */
	static def isValueObject(NamedElement elem){
		return (elem.getStereotype(MODEL_VALUEOBJECT) !==null && !(elem.getStereotype(MODEL_VALUEOBJECT).empty))
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
	static def getStereotypePropertyValue(NamedElement clazz, String stereoname, String property){
		if(clazz.getStereotype(stereoname) ===null || clazz.getStereotype(stereoname).empty)
		return null
		
		val stereotype = clazz.getStereotype(stereoname).get(0);
		
		if(stereotype === null)
			return null
			 		
		return clazz.getValue(stereotype ,property);
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
	
	static def getFirstToLowerCase(String str){
		return str.toFirstLower()
	}
	
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
	
}