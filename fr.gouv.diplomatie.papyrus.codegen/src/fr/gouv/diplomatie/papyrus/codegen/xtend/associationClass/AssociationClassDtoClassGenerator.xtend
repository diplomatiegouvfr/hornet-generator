package fr.gouv.diplomatie.papyrus.codegen.xtend.associationClass

import org.eclipse.uml2.uml.AssociationClass
import org.eclipse.uml2.uml.Property
import fr.gouv.diplomatie.papyrus.codegen.xtend.utils.ClassifierUtils
import fr.gouv.diplomatie.papyrus.codegen.xtend.utils.PropertyUtils
import fr.gouv.diplomatie.papyrus.codegen.xtend.utils.Utils
import org.eclipse.uml2.uml.Classifier
import fr.gouv.diplomatie.papyrus.codegen.xtend.utils.TypeUtils
import java.util.ArrayList
import org.eclipse.uml2.uml.Type

public class AssociationClassDtoClassGenerator{
	
	static def generateCode(AssociationClass clazz){
		
		'''
		import Alias from "hornet-js-bean/src/decorators/Alias";
		import Bean from "hornet-js-bean/src/decorators/Bean";
		import Map from "hornet-js-bean/src/decorators/Map";
		
		«clazz.generateImports»
		
		@Bean
		export class «ClassifierUtils.getDtoClassName(clazz)» {
			«clazz.generateAttributes(newArrayList())»
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
			import { «ClassifierUtils.getDtoClassName(type as Classifier)» } from "«ClassifierUtils.getDtoClassPath(type as Classifier)»";
			'''
		]»
		'''
	}
	
	static def generateAttributesImports(Classifier clazz,  ArrayList<Type> types){
		var attributes = ClassifierUtils.getOwnedAttributes(clazz).filter[ attribut |
			(Utils.isEntity(attribut.type))
		]
		
		var attributesValueObject = ClassifierUtils.getOwnedAttributes(clazz).filter[ attribut |
			(Utils.isValueObject(attribut.type))
		]
		
		var attributesEnums = ClassifierUtils.getOwnedAttributes(clazz).filter[ attribut |
			(Utils.isNomenclature(attribut.type))
		]
		
		if(clazz instanceof AssociationClass){
			attributes = clazz.ownedEnds.filter[ attribut |
				(Utils.isEntity(attribut.type))
			]
			
			attributesValueObject = clazz.ownedEnds.filter[ attribut |
				(Utils.isValueObject(attribut.type))
			]
			
			attributesEnums = clazz.ownedEnds.filter[ attribut |
			(Utils.isNomenclature(attribut.type))
			]
		}
		
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
		
		attributesValueObject.forEach[attribut |
			var type = attribut.type
			if(type instanceof Classifier){
				type.generateAttributesImports(types)
			}
		]
		return types
	}
	
	/**
	 * génère les attributs simples de la classe
	 */
	static def generateAttributes(AssociationClass clazz, ArrayList<String> names){
		val attributes = clazz.memberEnds
		'''
		«attributes.fold("")[acc, attribut|
 			acc + '''
 			«attribut.generateAttribut(names)»'''	
 		]»
 		'''
	}
	
	
	/**
	 * génère un attribut de la classe
	 */
	static def generateAttribut(Property property, ArrayList<String> names){
		if(PropertyUtils.isClassAttribute(property)){
			'''«property.generateClassAttribute(names)»'''
		}else{
			'''«property.generateBasicAttribute(names)»'''
		}
	}
	
	/**
	 * génère un attribut lié a la classe
	 */
	static def generateClassAttribute(Property property, ArrayList<String> names){
		if(Utils.isValueObject(property.type)){
			'''«property.generateValueObjectAttribute(names)»'''
		}else if(Utils.isNomenclature(property.type)){
			'''«property.generateEnumAttributes(names)»'''
		}else{
			'''«property.generateEntityAttributes(names)»'''
		}
	}
	
	/**
	 * génère un attribut de type value Object
	 */
	static def generateValueObjectAttribute(Property property, ArrayList<String> names){
		val name = Utils.addAdditionnalName(Utils.getNameFromList(names), property.name)
		val type = property.type
		names.add(name)
		if(type instanceof Classifier){
			if(!property.multivalued){
				return '''«type.generateAttributes(names)»
				'''
			}
		}else{
			''''''
		}
	}
	
	static def generateEnumAttributes(Property property, ArrayList<String> names){
		val type = property.type
		val name = Utils.addAdditionnalName(Utils.getNameFromList(names), property.name)
		val codeName = Utils.addAdditionnalName(Utils.getNameFromList(names), "code" + Utils.getFirstToUpperCase(property.name)) 
		if(type instanceof Classifier){
			'''
			
			@Map()
			«codeName»: number;
			
			@Map()
			«Utils.getFirstToLowerCase(name)»: «ClassifierUtils.getDtoClassName(type)»;
			'''
		}
	}
	
	/**
	 * génère un attribut de type entity
	 */
	static def generateEntityAttributes(Property property, ArrayList<String> names){
		val type = property.type
		val name = Utils.addAdditionnalName(Utils.getNameFromList(names), property.name)
		if(type instanceof Classifier){
			val ids = ClassifierUtils.getId(type)
			'''
			«ids.fold("")[acc, id |
				acc + '''«property.generateEntityAttribute(id, names)»'''
			]»
			
			@Map()
			«Utils.getFirstToLowerCase(name)»: «ClassifierUtils.getDtoClassName(type)»;
			'''
		}else{
			''''''
		}
		
	}
	
	/**
	 * génère les attributs simples de la classe
	 */
	static def generateAttributes(Classifier clazz, ArrayList<String> names){
		val attributes = ClassifierUtils.getOwnedAttributes(clazz)
		'''
		«attributes.fold("")[acc, attribut|
 			acc + '''
 			«attribut.generateAttribut( names)»'''	
 		]»
 		'''
	}
	
	static def generateEntityAttribute(Property property, Property id, ArrayList<String> names){
		val fieldName = Utils.addAdditionnalName(Utils.getNameFromList(names), id.name) + Utils.getFirstToUpperCase(property.name)
		val propName = Utils.addAdditionnalName(Utils.getNameFromList(names), property.name)
		val type = id.owner
		if(!property.multivalued){
			if (!names.empty){
				'''
				
				@Map()
				@Alias('«fieldName»', '«Utils.getListPoint(names)».«property.name».«id.name»')
				«Utils.getFirstToLowerCase(fieldName)»: «TypeUtils.getMetierTypescriptType(id.type)»;
				'''
			}else{
				'''
				
				@Map()
				@Alias('«fieldName»', '«Utils.getFirstToLowerCase((type as Classifier).name)».«id.name»')
				«Utils.getFirstToLowerCase(fieldName)»: «TypeUtils.getMetierTypescriptType(id.type)»;
				'''
			}
			
		}else{
			''''''
		}
		
	}
	
	/**
	 * génère un attribut de l'interface
	 */
	static def generateBasicAttribute(Property property, ArrayList<String> names){
		var name = Utils.addAdditionnalName(Utils.getNameFromList(names), property.name)
		if(!property.multivalued){
			'''
			
			@Map()
			@Alias('«name»', '«Utils.getListPoint(names)».«property.name»')
			«Utils.getFirstToLowerCase(name)»: «TypeUtils.getMetierTypescriptType(property.type)»;
			'''
		}else{
			''''''
		}
	}
}