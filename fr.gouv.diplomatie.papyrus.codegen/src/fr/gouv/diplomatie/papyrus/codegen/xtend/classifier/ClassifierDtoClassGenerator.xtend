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
import org.eclipse.uml2.uml.AssociationClass

public class ClassifierDtoClassGenerator{
	
	static def generateCode(Classifier clazz){
		'''
		import Alias from "hornet-js-bean/src/decorators/Alias";
		import Bean from "hornet-js-bean/src/decorators/Bean";
		import Map from "hornet-js-bean/src/decorators/Map";
		
		«clazz.generateImports»
		
		@Bean
		export class «ClassifierUtils.getDtoClassName(clazz)» «clazz.generateExtends»{
			«clazz.generateAttributes»
			«clazz.generateAssociationAttributes»
		}
		
		«clazz.generateMultivaluedAttributeDto»
		'''
	}
	
	/**
	 * génère les imports
	 */
	static def generateImports(Classifier clazz){
		val attributesTypes = clazz.generateAttributesImports(newArrayList())
		'''
		«clazz.generateExtendsImports»
		«attributesTypes.fold("")[acc, type |
			acc +  '''
			import { «ClassifierUtils.getDtoClassName(type as Classifier)» } from "«ClassifierUtils.getDtoClassPath(type as Classifier)»";
			'''
		]»
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
		
		if(Utils.isEntity(clazz)){
			val assosiationClasses = ClassifierUtils.getLinkedAssociationClass(clazz)
			
			assosiationClasses.forEach[asso |
				val members = (asso as AssociationClass).ownedEnds.filter[end |
					end.type != clazz
				]
				val member = members.get(0)
				val type = member.type		
				if(!types.contains(asso) && Utils.isValueObject(type)){
					types.add(asso)
				}else if(!types.contains(type) && Utils.isEntity(type)){
					types.add(type)
				}
			]
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
	
	static def generateAssociationAttributes(Classifier clazz){
		val assosiationClasses = ClassifierUtils.getLinkedAssociationClass(clazz)
		'''
		«assosiationClasses.fold("")[acc, asso |
 			acc+ '''«(asso as AssociationClass).generateAssociationClassAtributes(clazz)»'''
 		]»
		'''
	}
	
	/**
	 * génère les attributs simples de la classe
	 */
	static def generateAttributes(Classifier clazz){
		val attributes = ClassifierUtils.getOwnedAttributes(clazz)
		
		'''
		«attributes.fold("")[acc, attribut|
 			acc + '''
 			«attribut.generateAttribut(newArrayList())»'''	
 		]»
 		'''
	}
	
		/**
	 * génère les attributs simples de la classe
	 */
	static def generateAttributes(Classifier clazz, ArrayList<String> names){
		val attributes = ClassifierUtils.getOwnedAttributes(clazz)
		
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
		names.add(property.name)
		if(type instanceof Classifier){
			if(!property.multivalued){
				return '''«type.generateAttributes(names)»
				'''
			}else{
				'''
				
				@Map()
				«property.name»: Array<«ClassifierUtils.getDtoClassName(type)»>;
				'''
			}
		}else{
			''''''
		}
	}
	
	/**
	 * génère un attribut de type entity
	 */
	static def generateEntityAttributes(Property property, ArrayList<String> names){
		val type = property.type
		val name = Utils.addAdditionnalName(Utils.getNameFromList(names), property.name)
		
		if(type instanceof Classifier){
			if(!property.multivalued){
				val ids = ClassifierUtils.getId(type)
				'''
				«ids.fold("")[acc, id |
					acc + '''«property.generateEntityAttribute(id, names)»'''
				]»
				
				@Map()
				@Alias('«Utils.getListPoint(names)»')
				«name»: «ClassifierUtils.getDtoClassName(type)»;
				'''
			}else{
				'''
				
				@Map()
				«name»: Array<«ClassifierUtils.getDtoClassName(type)»>;
				'''
			}
		}else{
			''''''
		}
		
	}
	
	static def generateEntityAttribute(Property property, Property id, ArrayList<String> names){
		val fieldName = Utils.addAdditionnalName(Utils.getNameFromList(names), id.name) + Utils.getFirstToUpperCase(property.name)
		val propName = Utils.addAdditionnalName(Utils.getNameFromList(names), property.name)
		names.add(property.name)
		if(!property.multivalued){
			if(names.empty){
				'''
				
				@Map()
				@Alias('«fieldName»')
				«fieldName»: «TypeUtils.getMetierTypescriptType(id.type)»;
				'''
			}else{
				'''
				
				@Map()
				@Alias('«fieldName»', '«Utils.getListPoint(names)».«id.name»')
				«fieldName»: «TypeUtils.getMetierTypescriptType(id.type)»;
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
				
				@Map()
				«Utils.getFirstToLowerCase(owner.name)»: «ClassifierUtils.getDtoClassName(owner)»;
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
	 		if(type != owner){
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
			val type = property.type
			'''
			
			export class «PropertyUtils.getMultivaluedPropertyDtoName(property)»{
				«idsProp.fold("")[acc, id |
					if(acc != ""){
						acc + '''«member.generateNPTDtoIdAttributes(id)»'''
					}else{
						acc + '''«member.generateNPTDtoIdAttributes(id)»'''
					}
				]»
				
				@Map()
				«Utils.getFirstToLowerCase(type.name)»: «ClassifierUtils.getDtoClassName(type as Classifier)»;
				«idsOwner.fold("")[acc, id |
					if(acc != ""){
						acc + '''«property.generateNPTDtoIdAttributes(id)»'''
					}else{
						acc + '''«property.generateNPTDtoIdAttributes(id)»'''
					}
				]»
				
				@Map()
				«Utils.getFirstToLowerCase(owner.name)»: «ClassifierUtils.getDtoClassName(owner)»;
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
			val type = property.type
			'''
			
			export class «PropertyUtils.getMultivaluedPropertyDtoName(property)»{
				«idsOwner.fold("")[acc, id |
					if(acc != ""){
						acc + ''',«id.generateNPTModelIdAttributes()»'''
					}else{
						acc + '''«id.generateNPTModelIdAttributes()»'''
					}
				]»
				
				@Map()
				«Utils.getFirstToLowerCase(owner.name)»: «ClassifierUtils.getDtoClassName(owner)»;
				«idsProp.fold("")[acc, id |
					if(acc != ""){
						acc + ''',«property.generateNPTDtoIdAttributes(id)»'''
					}else{
						acc + '''«property.generateNPTDtoIdAttributes(id)»'''
					}
				]»
				
				@Map()
				«Utils.getFirstToLowerCase(type.name)»: «ClassifierUtils.getDtoClassName(type as Classifier)»;
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
	
	static def generateAssociationClassAtributes(AssociationClass clazz, Classifier fromClass){
		val members = clazz.ownedEnds.filter[end |
			end.type != fromClass
		]
		val member = members.get(0)
		val type = member.type
		if(type instanceof Classifier){
			if(Utils.isEntity(type)) {
				'''
				
				@Map()
				«Utils.getFirstToLowerCase(member.name)» : Array<«ClassifierUtils.getDtoClassName(type)»>;
				'''
			}else if(Utils.isValueObject(type)){
				'''
				
				@Map()
				«Utils.getFirstToLowerCase(member.name)» : Array<«ClassifierUtils.getDtoClassName(clazz)»>;
				'''
			}
		}
	}
	
}