package fr.gouv.diplomatie.papyrus.codegen.xtend.classifier;

import org.eclipse.uml2.uml.Classifier
import org.eclipse.uml2.uml.Property
import fr.gouv.diplomatie.papyrus.codegen.xtend.utils.ClassifierUtils
import fr.gouv.diplomatie.papyrus.codegen.xtend.utils.Utils
import fr.gouv.diplomatie.papyrus.codegen.xtend.utils.PropertyUtils
import fr.gouv.diplomatie.papyrus.codegen.xtend.utils.TypeUtils
import org.eclipse.uml2.uml.Type
import java.util.ArrayList
import org.eclipse.uml2.uml.PrimitiveType

public class ClassifierDtoClassGenerator{
	
	static def generateCode(Classifier clazz){
		'''
		import Alias from "hornet-js-bean/src/decorators/Alias";
		import Bean from "hornet-js-bean/src/decorators/Bean";
		import Map from "hornet-js-bean/src/decorators/Map";
		
		«clazz.generateImports»
		
		@Bean
		export class «ClassifierUtils.getDtoClassName(clazz)» «clazz.generateExtends»{
			«clazz.generateAttributes("")»
		}
		
		«clazz.generateMultivaluedAttributeDto»
		'''
	}
	
	/**
	 * génère les imports
	 */
	static def generateImports(Classifier clazz){
		//val attributesTypes = clazz.generateAttributesImports(newArrayList())
		'''
		«clazz.generateExtendsImports»
		««««attributesTypes.fold("")[acc, type |
		«««	acc +  '''
		«««	import { «ClassifierUtils.getDtoClassName(type as Classifier)» } from "«ClassifierUtils.getDtoClassPath(type as Classifier)»";
		«««	'''
		«««]»
		'''
	}
	
	/**
	 * génère les imports lié aux extensions
	 */
	static def generateExtendsImports(Classifier clazz){
		val parents = clazz.generalizations
		if(parents.length > 0){
			'''«parents.fold("")[acc, parent |
				acc + '''import { «ClassifierUtils.getDtoClassName(parent.general)» } from "«ClassifierUtils.getDtoClassPath(parent.general)»";
				'''
			]»'''
		}else{
			''''''
		}
	}
	
	/**
	 * génère les imports liés aux types des attributs
	 */
	 static def generateAttributesImports(Classifier clazz, ArrayList<Type> types){
	 	val attributes = ClassifierUtils.getOwnedAttributes(clazz).filter[ attribut |
			(Utils.isEntity(attribut.type))
		]
		val attributesValueObject = ClassifierUtils.getOwnedAttributes(clazz).filter[ attribut |
			(Utils.isValueObject(attribut.type))
		]
		for(attribut : attributes){
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
	 * génère les extends
	 */
	static def generateExtends(Classifier clazz){
		val parents = clazz.generalizations
		if(parents.length > 0){
			'''«parents.fold("")[acc, parent |
				if(acc != ""){
					acc + ''',«ClassifierUtils.getDtoClassName(parent.general)»'''
				}else{
					acc + '''extends «ClassifierUtils.getDtoClassName(parent.general)»'''
				}
			]»'''
		}else{
			''''''
		}
	}
	
	/**
	 * génère les attributs simples de la classe
	 */
	static def generateAttributes(Classifier clazz, String additionnalName){
		val attributes = ClassifierUtils.getOwnedAttributes(clazz)
		'''
		«attributes.fold("")[acc, attribut|
 			acc + '''
 			«attribut.generateAttribut(additionnalName)»'''	
 		]»
 		'''
	}
	
	/**
	 * génère un attribut de la classe
	 */
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
		if(type instanceof Classifier){
			if(!property.multivalued){
				return '''«type.generateAttributes(name)»
				'''
			}
		}else{
			''''''
		}
	}
	
	/**
	 * génère un attribut de type entity
	 */
	static def generateEntityAttributes(Property property, String additionnalName){
		val type = property.type
		
		if(type instanceof Classifier){
			val ids = ClassifierUtils.getId(type)
			'''
			«ids.fold("")[acc, id |
				acc + '''«property.generateEntityAttribute(id, additionnalName)»'''
			]»
			'''
		}else{
			''''''
		}
		
	}
	
	static def generateEntityAttribute(Property property, Property id, String additionnalName){
		val fieldName = Utils.addAdditionnalName(additionnalName, id.name) + Utils.getFirstToUpperCase(property.name)
		val propName = Utils.addAdditionnalName(additionnalName, property.name)
		if(!property.multivalued){
			'''
			
			@Map()
			@Alias('«propName».«id.name»')
			«fieldName»: «TypeUtils.getMetierTypescriptType(id.type)»;
			'''
		}else{
			''''''
		}
		
	}
	
	/**
	 * génère un attribut de l'interface
	 */
	static def generateBasicAttribute(Property property, String additionnalName){
		var name = Utils.addAdditionnalName(additionnalName, property.name)
		if(!property.multivalued){
			'''
			
			@Map()
			«name»: «TypeUtils.getMetierTypescriptType(property.type)»;
			'''
		}else{
			''''''
		}
	}
	
	
	/**
	 * génère les dto liés aux attributs multi valués 
	 */
	static def generateMultivaluedAttributeDto(Classifier clazz){
		val attributes = ClassifierUtils.getMultivaluedOwnedAttributes(clazz)
		val primitiveAttributes = attributes.filter[attribut |
			val type = attribut.type
			return ( type instanceof PrimitiveType)
		]
		
		val ownedAttributes = ClassifierUtils.getOwnedAttributes(clazz)

		val multiAttributes = ownedAttributes.filter[attribut |
			val type = attribut.type
			return (Utils.isEntity(type) && (attribut.multivalued))
		]
		
		return '''
		«primitiveAttributes.fold("")[acc, attribut|
			acc + '''«attribut.generateMultiValuedPrimitiveTypeDto»'''
 		]»
		«multiAttributes.fold("")[acc, attribut |
 			acc + '''«attribut.generateMultiValuedNPTypeDto»'''
 		]»
		'''
		
	}
	
	/**
	 * génère le model d'un attribut multivalué
	 */
	static def generateMultiValuedPrimitiveTypeDto(Property property){
		val owner = property.owner
		if(owner instanceof Classifier){
			val ids = ClassifierUtils.getId(owner)
			'''
			
			export class «PropertyUtils.getMultivaluedPropertyDtoName(property)» {
				
				@Map()
				«property.name»: «TypeUtils.getMetierTypescriptType(property.type)»;
				«ids.fold("")[acc, id |
					if(acc != ""){
						acc + ''',«property.generateMultiValuedPrimitiveTypeDtoIdAttributes(id)»'''
					}else{
						acc + '''«property.generateMultiValuedPrimitiveTypeDtoIdAttributes(id)»'''
					}
				]»
			}
			'''
		}else{
			''''''
		}
	}
	
	static def generateMultiValuedPrimitiveTypeDtoIdAttributes(Property property, Property id){
		val idName = id.name
		val owner = property.owner
		if(owner instanceof Classifier){
			'''
			
			@Map()
			«idName»: «TypeUtils.getMetierTypescriptType(id.type)»;
			'''
		}
	}
	
	
	/**
	 * génère les table d'association 
	 */	
	 static def generateMultiValuedNPTypeDto(Property property){
	 	val type = property.type
	 	val owner = property.owner
	 	if(property.association !== null){
	 		val members = property.association.ownedEnds
	 		val member = members.get(0)
	 		if(type != owner && member.multivalued){
		 		'''
		 		«property.generateNPTypeAssociationDto»
		 		'''
	 		}else{
	 			''''''
	 		}
	 	}else{
	 		'''
	 		«property.generateNPTypeDto»
	 		'''
	 	}
	}
	
	static def generateNPTypeAssociationDto(Property property){
		val owner = property.owner
		if(owner instanceof Classifier){
			val members = property.association.ownedEnds
	 		val member = members.get(0)
			val idsOwner = ClassifierUtils.getId(owner)
			val idsProp = ClassifierUtils.getId(member.type as Classifier)
			
			'''
			
			export class «PropertyUtils.getMultivaluedPropertyDtoName(property)»{
				«idsProp.fold("")[acc, id |
					if(acc != ""){
						acc + ''',«member.generateNPTDtoIdAttributes(id)»'''
					}else{
						acc + '''«member.generateNPTDtoIdAttributes(id)»'''
					}
				]»
				«idsOwner.fold("")[acc, id |
					if(acc != ""){
						acc + ''',«property.generateNPTDtoIdAttributes(id)»'''
					}else{
						acc + '''«property.generateNPTDtoIdAttributes(id)»'''
					}
				]»
			}
			'''
		}else{
			''''''
		}
	}
	
	static def generateNPTDtoIdAttributes(Property property, Property id){
		val type = property.type
		val idName = Utils.addAdditionnalName(id.name, type.name)
		if(type instanceof Classifier){
			'''
			
			@Map()
			«idName»: «TypeUtils.getMetierTypescriptType(id.type)»;
			'''
		}
	}
	
	
	static def generateNPTypeDto(Property property){
		val owner = property.owner
		if(owner instanceof Classifier){
			val idsOwner = ClassifierUtils.getId(owner)
			val idsProp = ClassifierUtils.getId(property.type as Classifier)
			
			'''
			
			export class «PropertyUtils.getMultivaluedPropertyDtoName(property)»{
				«idsOwner.fold("")[acc, id |
					if(acc != ""){
						acc + ''',«id.generateNPTModelIdAttributes()»'''
					}else{
						acc + '''«id.generateNPTModelIdAttributes()»'''
					}
				]»
				«idsProp.fold("")[acc, id |
					if(acc != ""){
						acc + ''',«property.generateNPTDtoIdAttributes(id)»'''
					}else{
						acc + '''«property.generateNPTDtoIdAttributes(id)»'''
					}
				]»
			}
			'''
		}else{
			''''''
		}
	}
	
	static def generateNPTModelIdAttributes(Property id){
		val type = id.owner
		if(type instanceof Classifier){
			val idName = Utils.addAdditionnalName(id.name, type.name)
			if(type instanceof Classifier){
				'''
				
				@Map()
				«idName»: «TypeUtils.getMetierTypescriptType(id.type)»;
				'''
			}
		}
	}
	
}