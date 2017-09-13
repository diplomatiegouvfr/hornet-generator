package fr.gouv.diplomatie.papyrus.codegen.xtend.utils

import org.eclipse.uml2.uml.Classifier
import org.eclipse.uml2.uml.AttributeOwner
import fr.gouv.diplomatie.papyrus.codegen.generators.GeneratorUtils
import org.eclipse.uml2.uml.AssociationClass

class ClassifierUtils{
	
	static def getModelName(Classifier clazz){
		return clazz.name + "Model"
	}
	
	static def getAttributesInterfaceName(Classifier clazz){
		return clazz.name + "Attributes"
	}
	
	static def getMetierClassName(Classifier clazz){
		return clazz.name + "Metier"
	}
	
	static def getDtoClassName(Classifier clazz){
		return clazz.name + "DTO"
	}
	
	/**
	 * retourne le chemin du fichier de la classe Model
	 */
	static def getModelPath(Classifier clazz){
		return GeneratorUtils.getModelPath(clazz, true)
	}
	
	/**
	 * retourne le chemin du fichier de l'interface Attributes
	 */
	static def getAttributesInterfacePath(Classifier clazz){
		return GeneratorUtils.getAttributesInterfacePath(clazz, true)
	}
	
	/**
	 * retourne le chemin du fichier de la classe metier
	 */
	static def getMetierClassPath(Classifier clazz){
		return GeneratorUtils.getMetierClassPath(clazz, true)
	}
	
	/**
	 * retourne le chemin du fichier de la classe dto
	 */
	static def getDtoClassPath(Classifier clazz){
		return GeneratorUtils.getDtoClassPath(clazz, true)
	}
	
	/**
	 * retourne le chemin du fichier de la classe enum
	 */
	static def getEnumClassPath(Classifier clazz){
		return GeneratorUtils.getEnumPath(clazz, true)
	}
	
	/**
	 * retourne la classe Stereotype 
	 */
	static def getStereotype(Classifier clazz, String stereoname) {
		return clazz.appliedStereotypes.filter[stereotype |
			stereotype.name == stereoname
		]
	}
	
	/**
	 * retourne la valeur de la propriété liée au stereotype
	 */
	static def getStereotypePropertyValue(Classifier clazz, String stereoname, String property){
		if(clazz.getStereotype(stereoname) ===null || clazz.getStereotype(stereoname).empty)
		return null
		
		val stereotype = clazz.getStereotype(stereoname).get(0);
		
		if(stereotype === null)
			return null
			 		
		return clazz.getValue(stereotype ,property);
	}
	
	/**
	 * renvoi les attributs de la classe
	 * tableau vide si il y en a aucun
	 */
	static def getOwnedAttributes(Classifier clazz) {
		val attributes = clazz.getOwnedAttributesWNull
		if (attributes === null) {
			emptySet
		}
		else {
			attributes
		}
	}
	
	/**
	 * renvoi les attributs de la classe
	 * tableau vide si il y en a aucun
	 * incluant ceux des interface et classes généralisées
	 */
	static def getAttributes(Classifier clazz) {
		val attributes = clazz.getAllAttributes
		if (attributes === null) {
			emptySet
		}
		else {
			attributes
		}
	}
	
	/**
	 * renvoi les attributs de la classe
	 * null si il y en a aucun
	 */
	static def getOwnedAttributesWNull(Classifier clazz) {	
		if (clazz instanceof AttributeOwner) {
			(clazz as AttributeOwner).ownedAttributes
		} else {
			//Sequence{}
		    return null
		}
	}
	
	/**
	 * test si une classe a plusieurs attributs de type keyattribute
	 */
	static def hasMultipleId(Classifier clazz){
		val ids = clazz.getOwnedAttributes.filter[attribut|
			PropertyUtils.isID(attribut)
		]
		return ids.length >1 
	}
	
	
	/**
	 * retourne l'identifiant
	 */
	static def getId(Classifier clazz){
		val ids = clazz.getOwnedAttributes.filter[attribut|
			PropertyUtils.isID(attribut)
		]
		return ids
	}
	
	/**
	 * retourne les attributs multivalués
	 */
	static def getMultivaluedOwnedAttributes(Classifier clazz){
		val attributes = clazz.getOwnedAttributes
		val multiAttributes = attributes.filter[attribut |
			return (attribut.multivalued)
		]
		return multiAttributes
	}
	
	/**
	 * retourne les attributs multivalués
	 * en incluant ceux venant des interfaces et des classes généralisées
	 */
	static def getAllMultivaluedAttributes(Classifier clazz){
		val attributes = clazz.getAllAttributes
		val multiAttributes = attributes.filter[attribut |
			return (attribut.multivalued)
		]
		return multiAttributes
	}
	
		/**
	 * retourne les attributs non multivalués
	 */
	static def getNotMultivaluedOwnedAttributes(Classifier clazz){
		val attributes = clazz.getOwnedAttributes
		val simpleAttributes = attributes.filter[attribut |
			return (!attribut.multivalued)
		]
		return simpleAttributes
	}
	
	/**
	 * cherche les reference multivaluée de la classe ofClass dans une classe inClass 
	 */
	static def getMultivaluedReferencesToType(Classifier inClass, Classifier ofClass){
		val attributes = inClass.getOwnedAttributes.filter[attribut |
			return ((attribut.type == ofClass) && (attribut.multivalued))
		]
		return attributes
	}
	
	static def getAllReferencesTo(Classifier ofClass){
		val model = ofClass.model
		val classes = ModelUtils.getAllClasses(model)
		val references = newArrayList()
		for(classe : classes){
			references.addAll(ClassifierUtils.getMultivaluedReferencesToType(classe as Classifier, ofClass))
		}
		return references
	}
	
	static def getLinkedAssociationClass(Classifier clazz){
		val model = clazz.model
		val associationsClasses = model.getOwnedTypes().filter[type|
			if(type instanceof AssociationClass){
				val members = type.ownedEnds
				var isIn = false
				for(member: members){
					if(member.type == clazz){
						isIn = true
					}
				}
				return isIn
			}else{
				return false
			}
		]
		return associationsClasses
	}
	
	/**
	 * test si l'enum a des code ou non
	 */
	static def isEnumWithCode(Classifier clazz){
		val valeurs = clazz.getOwnedAttributes
		val value = valeurs.get(0)
		val code = Utils.getStereotypePropertyValue(value, Utils.MODEL_CODELIBELLENOMENCLATURE, Utils.MODEL_CODELIBELLENOMENCLATURE_CODE)
		return (code !== null && code != "")
	}
	
	/**
	 * teste si une classe doit etre générée ou non
	 */
	static def canBeGenerated(Classifier clazz){
		if(Utils.isEntity(clazz)){
			return (Utils.getStereotypePropertyValue(clazz, Utils.MODEL_ENTITY, Utils.MODEL_ENTITY_GENERATED) != false)
		}else if(Utils.isNomenclature(clazz)){
			return (Utils.getStereotypePropertyValue(clazz, Utils.MODEL_NOMENCLATURE, Utils.MODEL_NOMENCLATURE_GENERATED) != false)
		}else if (Utils.isValueObject(clazz)){
			return(Utils.getStereotypePropertyValue(clazz, Utils.MODEL_VALUEOBJECT, Utils.MODEL_VALUEOBJECT_GENERATED) != false)
		}
		return true
	}
	
	static def getTableNameValue(Classifier clazz){
		if(Utils.isEntity(clazz)){
			return Utils.getStereotypePropertyValue(clazz, Utils.MODEL_ENTITY, Utils.MODEL_ENTITY_TABLENAME)
		}else if(Utils.isNomenclature(clazz)){
			return Utils.getStereotypePropertyValue(clazz, Utils.MODEL_NOMENCLATURE, Utils.MODEL_NOMENCLATURE_TABLENAME)
		}
		return null
	}
	
	static def String getTableName(Classifier clazz){
		val name = getTableNameValue(clazz)
		if(name === null){
			return Utils.toSnakeCase(clazz.name)
		}
		return name.toString
	}
}