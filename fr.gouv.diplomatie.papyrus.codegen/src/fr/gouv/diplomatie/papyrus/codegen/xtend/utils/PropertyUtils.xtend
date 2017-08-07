package fr.gouv.diplomatie.papyrus.codegen.xtend.utils

import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.NamedElement
import org.eclipse.uml2.uml.Classifier
import org.eclipse.uml2.uml.Class

class PropertyUtils {
	
	/**
	 * retourne la classe Stereotype dont le nom est stereoname
	 */
	static def getStereotype(Property prop, String stereoname) {
		return prop.appliedStereotypes.filter[stereotype |
			stereotype.name == stereoname
		]
	}
	
	/**
	 * retourne la valeur de l'attribut du stereotype de la classe
	 */
	static def getStereotypePropertyValue(Property prop, String stereoname, String property){
		if(prop.getStereotype(stereoname) ===null || prop.getStereotype(stereoname).empty)
		return null
		
		val stereotype = prop.getStereotype(stereoname).get(0);
		
		if(stereotype === null)
			return null
			 		
		return prop.getValue(stereotype ,property);
	}
	
	/**
	 * teste si une property est un id
	 */
	static def isID(Property property){
		return (property.getStereotype(Utils.MODEL_KEYATTRIBUTE) !==null && !(property.getStereotype(Utils.MODEL_KEYATTRIBUTE).empty))
	}
	
	/**
	 * retourne le nom de la classe possédant la property
	 */
	static def getOwnerName(Property property){
		val type= property.owner;
		if(type instanceof NamedElement){
			return type.name
		}else{
			return type.class.name
		}
	}
	
	/**
	 * retourne le nom du field id
	 */
	static def getIdFieldName(Property property, String additionalName){
		var name = ""
		if(additionalName!= "" && additionalName !== null){
			name = additionalName + Utils.getFirstToUpperCase(property.name)
		}else{
			name = property.name
		}
		return '''«Utils.toSnakeCase(name)»_«Utils.getFirstToLowerCase(property.getOwnerName)»'''
	}
	
	/**
	 * teste si l'attribut ne viens pas d'une association
	 */
	static def isBasicAttribute(Property property){
		return (!property.isClassAttribute)
	}
	
	/**
	 * teste si l'attribut viens d'une association
	 */
	static def isClassAttribute(Property property){
		return (property.type instanceof Class)
	}
	
	/**
	 * teste si l'attribut viens d'une association
	 */
	static def isAssociationAttribute(Property property){
		return property.association !== null
	}
	
	/**
	 * retourne le nom d'un attribut issu d'un attribut de type valueObject
	 */
	static def getValueObjectPropertyName(Property property, Property valueObjectProperty){
		return '''«valueObjectProperty.name»«Utils.getFirstToUpperCase(property.name)»'''
	}
	
	/**
	 * retourne le nom du champs lié a l'identifant de la classe entity associée
	 */
	static def getAssociationEntityIdName(Property property){
		val type = property.type
		if(type instanceof Classifier){
			val propName = ClassifierUtils.getId(type).get(0).name
			return propName + Utils.getFirstToUpperCase(type.name)
		}else{
			return ""
		}
		
	}
	
	/**
	 * retourne le nom du model d'une propriété multivaluée
	 */
	static def getMultivaluedPropertyModelName(Property property){
		'''«property.getOwnerName»«Utils.getFirstToUpperCase(property.name)»Model'''
	}
	
	/**
	 * retourne le nom de l'attribut d'une reference multivaluée
	 */
	static def String getReferenceAttributeName(Property property, Property id, Classifier clazz){
		'''«id.name»«Utils.getFirstToUpperCase(property.name)»'''
	}
	
	static def isNullable(Property property){
		return (property.lower == 0)
	}
	
}