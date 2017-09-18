package fr.gouv.diplomatie.papyrus.codegen.typescript.xtend.associationClass

import fr.gouv.diplomatie.papyrus.codegen.core.utils.ClassifierUtils
import fr.gouv.diplomatie.papyrus.codegen.core.utils.Utils
import org.eclipse.uml2.uml.AssociationClass
import org.eclipse.uml2.uml.Classifier
import org.eclipse.uml2.uml.Property
import fr.gouv.diplomatie.papyrus.codegen.core.utils.PropertyUtils
import fr.gouv.diplomatie.papyrus.codegen.typescript.utils.TypeUtils
import java.util.ArrayList
import org.eclipse.uml2.uml.Type

public class AssociationClassMetierClassGenerator{
	
	static def generateCode(AssociationClass clazz){
		'''
		import Bean from "hornet-js-bean/src/decorators/Bean";
		import Map from "hornet-js-bean/src/decorators/Map";
		
		«clazz.generateImports»
		
		@Bean
		export class «ClassifierUtils.getMetierClassName(clazz)»{
			«clazz.generateAttributes("")»
		}
		'''
	}
	
	/**
	 * génère les imports
	 */
	static def generateImports(AssociationClass clazz){
		val attributesTypes = clazz.generateAttributesImports(newArrayList(), clazz)
		'''
		«attributesTypes.fold("")[acc, type |
			if(Utils.isNomenclature(type)){
				acc +  '''
				import { «Utils.getFirstToUpperCase(type.name)» } from "«ClassifierUtils.getEnumClassPath(type as Classifier)»";
				'''
			}else{
				acc +  '''
				import { «ClassifierUtils.getMetierClassName(type as Classifier)» } from "«ClassifierUtils.getMetierClassPath(type as Classifier)»";
				'''
			}
		]»
		'''
	}
	
	/**
	 * génère les imports liés aux types des attributs
	 */
	 static def generateAttributesImports(AssociationClass clazz, ArrayList<Type> types, Classifier fromClass){
	 	val attributes = clazz.memberEnds.filter[ attribut |
			(Utils.isEntity(attribut.type))
		]
		
		val attributesEnums = clazz.memberEnds.filter[ attribut |
			(Utils.isNomenclature(attribut.type))
		]
		
		val attributesValueObject = clazz.memberEnds.filter[ attribut |
			(Utils.isValueObject(attribut.type))
		]
		
		for(attribut : attributes){
			if(!types.contains(attribut.type)){
				types.add(attribut.type)
			}
		}
		
		for(attribut : attributesEnums){
			if(!types.contains(attribut.type)){
				types.add(attribut.type)
			}
		}
		
		for(attribut: attributesValueObject){
			if(!types.contains(attribut.type)){
				types.add(attribut.type)
			}
		}
		return types
	 }
	 
	 /**
	 * génère les imports liés aux types des attributs
	 */
	/*  static def generateAttributesImports(Classifier clazz){
	 	val attributes = ClassifierUtils.getOwnedAttributes(clazz).filter[ attribut |
			(Utils.isEntity(attribut.type))
		]
		val attributesValueObject =ClassifierUtils.getOwnedAttributes(clazz).filter[ attribut |
			(Utils.isValueObject(attribut.type))
		]
		var types = newArrayList()
		for(attribut : attributes){
			if(!types.contains(attribut.type)){
				types.add(attribut.type)
			}
		}
		'''
		«types.fold("")[acc, type |
			acc +  '''
			import { «ClassifierUtils.getMetierClassName(type as Classifier)» } from "«ClassifierUtils.getMetierClassPath(type as Classifier)»";
			'''
		]»
		«attributesValueObject.fold("")[acc, attribut |
			var type = attribut.type
			if(type instanceof Classifier){
				acc+ '''«type.generateAttributesImports»'''
			}
		]»
		'''
	 }*/
	 
	 /**
	  * génère les attributs
	  */
	 static def generateAttributes(AssociationClass clazz, String additionnalName){
	 	val attributes = clazz.memberEnds
	 	'''
	 	«attributes.fold("")[acc, attribut |
	 		acc + '''«attribut.generateAttribut(additionnalName)»'''
	 	]»
	 	'''
	 }
	 
	 /*static def generatePropertyAttributes(Property property, String additionnalName){
	 	val type = property.type
	 	if(type instanceof Classifier){
		 	if(Utils.isEntity(type)){
		 		'''
		 		«property.generateRefAttributes(additionnalName)»
		 		'''
		 	}else if (Utils.isValueObject(type)){
		 		'''
		 		«type.generateAttributes(Utils.addAdditionnalName(additionnalName, property.name))»
		 		'''
		 	}
	 	}
	 }*/
	 
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
		}else if(Utils.isNomenclature(property.type)){
			'''«property.generateEnumAttribute(additionnalName)»'''
		}else{
			'''«property.generateEntityAttributes(additionnalName)»'''
		}
	}
	
		/**
	 * génère un attribut de type value Object
	 */
	static def generateValueObjectAttribute(Property property, String additionnalName){
		/*val name = Utils.addAdditionnalName(additionnalName, property.name)
		val type = property.type
		if(type instanceof Classifier){
			return '''«type.generateAttributes(name)»
			'''
		}else{
			''''''
		}*/
		val type = property.type
		val name = Utils.addAdditionnalName(additionnalName, property.name)
		if(type instanceof Classifier){
			'''
			
			«Utils.generateComments(property)»
			@Map(«ClassifierUtils.getMetierClassName(type)»)
			«Utils.getFirstToLowerCase(name)»: «ClassifierUtils.getMetierClassName(type)»;
			'''
		}
		
	}
	
	/**
	 * génère un attrbiut de type enum
	 */
	static def generateEnumAttribute(Property property, String additionnalName){
		val type = property.type
		val propName =  Utils.addAdditionnalName(additionnalName, property.name)
		var array = ""
		var endArray = ""
		if(property.multivalued){
			array = "Array<"
			endArray = ">"
		} 
		if(type instanceof Classifier){
			'''
			
			«Utils.generateComments(property)»
			@Map()
			«Utils.getFirstToLowerCase(propName)»: «array»«Utils.getFirstToUpperCase(type.name)»«endArray»;
			'''
		}
	}
	
	/**
	 * génère un attribut de type entity
	 */
	static def generateEntityAttributes(Property property, String additionnalName){
		val type = property.type
		if(type instanceof Classifier){
			'''
			«property.generateEntityAttribute(additionnalName)»
			'''
		}else{
			''''''
		}
		
	}
	
		/**
	 * génère un attribut de type entity
	 */
	static def generateEntityAttribute(Property property, String additionnalName){
		val type = property.type
		val propName =  Utils.addAdditionnalName(additionnalName, property.name)
		var array = ""
		var endArray = ""
		if(property.multivalued){
			array = "Array<"
			endArray = ">"
		} 
		if(type instanceof Classifier){
			'''
			
			«Utils.generateComments(property)»
			@Map(«ClassifierUtils.getMetierClassName(type)»)
			«Utils.getFirstToLowerCase(propName)»: «array»«ClassifierUtils.getMetierClassName(type)»«endArray»;
			'''
		}
	}
	
	/**
	 * génère un attribut de l'interface
	 */
	static def generateBasicAttribute(Property property, String additionnalName){
		var name = Utils.addAdditionnalName(additionnalName, property.name)
		'''
		
		«Utils.generateComments(property)»
		@Map()
		«Utils.getFirstToLowerCase(name)»: «TypeUtils.getTypescriptType(property.type)»;
		'''
	}
	 
	/* static def generateRefAttributes(Property property, String additionnalName){
	 	val type = property.type
		if(type instanceof Classifier){
			'''
			«property.generateRefAttribute(additionnalName)»
			'''
		}else{
			''''''
		}
	 }*/
	 
	 static def generateRefAttribute(Property property, String additionnalName){
		val name = Utils.addAdditionnalName(additionnalName, property.name)
	 	val type = property.type
	 	var array = ""
		var endArray = ""
		if(property.multivalued){
			array = "Array<"
			endArray = ">"
		} 
		if(type instanceof Classifier){
		 	'''
		 	
		 	«Utils.generateComments(property)»
		 	@Map(«array»«ClassifierUtils.getMetierClassName(type)»«endArray»)
		 	«Utils.getFirstToLowerCase(name)»: «array»«ClassifierUtils.getMetierClassName(type)»«endArray»;
		 	'''
	 	}
	 }
	 
}