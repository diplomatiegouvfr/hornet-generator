package fr.gouv.diplomatie.papyrus.codegen.xtend.associationClass

import org.eclipse.uml2.uml.AssociationClass
import org.eclipse.uml2.uml.Property

import fr.gouv.diplomatie.papyrus.codegen.xtend.utils.ClassifierUtils
import fr.gouv.diplomatie.papyrus.codegen.xtend.utils.Utils
import org.eclipse.uml2.uml.Classifier
import fr.gouv.diplomatie.papyrus.codegen.xtend.utils.TypeUtils
import fr.gouv.diplomatie.papyrus.codegen.xtend.utils.PropertyUtils
import org.eclipse.uml2.uml.Type
import java.util.ArrayList

public class AssociationClassAttributesInterfaceGenerator{
	
	static def generateCode(AssociationClass clazz){
		'''
		«clazz.generateImports»
		
		export interface «ClassifierUtils.getAttributesInterfaceName(clazz)» {
			«clazz.generateAttributes()»
		}
		'''
	}
	
	/**
	 * génère les imports
	 */
	static def generateImports(AssociationClass clazz){
		val attributesTypes = clazz.generateAttributesImports(newArrayList())
		'''
		«attributesTypes.fold("")[acc, type |
			acc +  '''
			import { «ClassifierUtils.getAttributesInterfaceName(type as Classifier)» } from "«ClassifierUtils.getAttributesInterfacePath(type as Classifier)»";
			'''
		]»
		'''
	}
	
	/**
	 * génère les imports liés aux types des attributs
	 */
	 static def generateAttributesImports(AssociationClass clazz, ArrayList<Type> types){
	 	val attributes = clazz.memberEnds.filter[ attribut |
			(Utils.isEntity(attribut.type))
		]
		val attributesValueObject = clazz.memberEnds.filter[ attribut |
			(Utils.isValueObject(attribut.type))
		]
		for(attribut : attributes){
			if(!types.contains(attribut.type)){
				types.add(attribut.type)
			}
		}
		attributesValueObject.forEach[attribut|
			var type = attribut.type
			if(type instanceof Classifier){
				type.generateAttributesImports(types)
			}
		]
		return types
	 }
	 
	 /**
	 * génère les imports liés aux types des attributs
	 */
	 static def generateAttributesImports(Classifier clazz, ArrayList<Type> types){
	 	val attributes = ClassifierUtils.getOwnedAttributes(clazz).filter[ attribut |
			(Utils.isEntity(attribut.type))
		]
		val attributesValueObject =ClassifierUtils.getOwnedAttributes(clazz).filter[ attribut |
			(Utils.isValueObject(attribut.type))
		]
		for(attribut : attributes){
			if(!types.contains(attribut.type)){
				types.add(attribut.type)
			}
		}
		attributesValueObject.forEach[ attribut |
			var type = attribut.type
			if(type instanceof Classifier){
				type.generateAttributesImports(types)
			}
		]
		return types
	 }
	 
	 static def generateAttributes(AssociationClass clazz){
	 	val attributes = clazz.memberEnds
	 	'''
	 	«attributes.fold("")[acc, attribut |
	 		acc + '''«attribut.generatePropertyAttributes»'''
	 	]»
	 	'''
	 }
	 
	 static def generatePropertyAttributes(Property property){
	 	val type = property.type
	 	if(type instanceof Classifier){
		 	if(Utils.isEntity(type)){
		 		val ids = ClassifierUtils.getId(type)
		 		var array = ""
		 		var endArray = ""
		 		if(property.multivalued){
		 			array = "Array<"
		 			endArray = ">"
		 		}
		 		'''
		 		
		 		«ids.fold("")[acc, id |
		 			acc + '''«property.generatePropertyIdAttribute(id)»'''
		 		]»
		 		«property.name»: «array»«ClassifierUtils.getAttributesInterfaceName(type)»«endArray»;
		 		get«Utils.getFirstToUpperCase(property.name)»(): Promise<«array»«ClassifierUtils.getAttributesInterfaceName(type)»«endArray»>;
		 		'''
		 	}else if (Utils.isValueObject(type)){
		 		'''
		 		«type.generateAttributes(property.name)»
		 		'''
		 	}
	 	}
	 }
	 
	 static def generateAttributes(Classifier clazz, String additionnalName){
		val attributes = ClassifierUtils.getNotMultivaluedOwnedAttributes(clazz)
		'''
		«attributes.fold("")[acc, attribut|
 			acc + '''
 			«attribut.generateAttribut(additionnalName)»'''	
 		]»
 		'''
	}
	
	static def generateAttribut(Property property, String additionnalName){
		if(PropertyUtils.isClassAttribute(property)){
			'''«property.generateClassAttribute(additionnalName)»'''
		}else{
			'''«property.generateBasicAttribute(additionnalName)»'''
		}
	}
	
	/**
	 * génère un attribut lié a la classe
	 */
	static def generateClassAttribute(Property property, String additionnalName){
		if(Utils.isValueObject(property.type)){
			'''«property.generateValueObjectAttribute(additionnalName)»'''
		}else{
			'''«property.generateEntityAttributes(additionnalName)»'''
		}
	}
	
	/**
	 * génère un attribut de type value Object
	 */
	static def generateValueObjectAttribute(Property property, String additionnalName){
		val name = Utils.addAdditionnalName(additionnalName, property.name)
		val type = property.type
		var array = ""
		var endArray = ""
		if(property.isMultivalued){
			array = "Array<"
			endArray =">"
		}
		if(type instanceof Classifier){
			return '''«type.generateAttributes(name)»
			«property.name» : «array»«ClassifierUtils.getAttributesInterfaceName(type)»«endArray»;
			get«Utils.getFirstToUpperCase(property.name)»(): «array»«ClassifierUtils.getAttributesInterfaceName(type)»«endArray»;
			'''
		}else{
			''''''
		}
	}
	
	/**
	 * génère un attribut de type entity
	 */
	static def generateEntityAttributes(Property property, String additonnalName){
		val type = property.type
		if(type instanceof Classifier){
			val ids = ClassifierUtils.getId(type)
			ids.fold("")[acc , id |
				acc + '''
				«property.generateEntityAttribute(id, additonnalName)»'''
			]
		}else{
			''''''
		}
		
	}
	
		/**
	 * génère un attribut de type entity
	 */
	static def generateEntityAttribute(Property property, Property id,  String additionnalName){
		val type = id.type
		val propName =  Utils.addAdditionnalName(additionnalName, id.name) + Utils.getFirstToUpperCase(property.name)
		'''
		«propName»?: «TypeUtils.getTypescriptType(type)»;
		'''
	}
	
	/**
	 * génère un attribut de l'interface
	 */
	static def generateBasicAttribute(Property property, String additionnalName){
		var name = Utils.addAdditionnalName(additionnalName, property.name)
		'''
		«name»?: «TypeUtils.getTypescriptType(property.type)»;
		'''
	}
	 
	 static def generatePropertyIdAttribute(Property property, Property id){
	 	val name = id.name + Utils.getFirstToUpperCase(property.name)
	 	'''
	 	«name»: «TypeUtils.getTypescriptType(id.type)»;
	 	'''
	 }
	
}