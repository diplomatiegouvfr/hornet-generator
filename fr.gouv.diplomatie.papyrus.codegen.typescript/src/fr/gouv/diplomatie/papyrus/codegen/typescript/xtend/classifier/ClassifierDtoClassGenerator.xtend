package fr.gouv.diplomatie.papyrus.codegen.typescript.xtend.classifier;

import org.eclipse.uml2.uml.Classifier
import org.eclipse.uml2.uml.Property
import fr.gouv.diplomatie.papyrus.codegen.core.utils.ClassifierUtils
import fr.gouv.diplomatie.papyrus.codegen.core.utils.Utils
import fr.gouv.diplomatie.papyrus.codegen.core.utils.PropertyUtils
import fr.gouv.diplomatie.papyrus.codegen.typescript.utils.TypeUtils
import org.eclipse.uml2.uml.Type
import java.util.ArrayList
import org.eclipse.uml2.uml.PrimitiveType
import org.eclipse.uml2.uml.AssociationClass
import org.eclipse.uml2.uml.Interface

public class ClassifierDtoClassGenerator{
	
	static def generateCode(Classifier clazz){
		'''
		import Alias from "hornet-js-bean/src/decorators/Alias";
		import Bean from "hornet-js-bean/src/decorators/Bean";
		import Map from "hornet-js-bean/src/decorators/Map";
		
		«clazz.generateImports»
		
		@Bean
		export class «ClassifierUtils.getDtoClassName(clazz)» «clazz.generateExtends»{
			«clazz.generateExtendsAttributes»
			«clazz.generateInterfaceAttributes»
			«clazz.generateAttributes(clazz)»
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
		
		val attributesEnums = ClassifierUtils.getOwnedAttributes(clazz).filter[ attribut |
			(Utils.isNomenclature(attribut.type))
		]
		
		val interfaces = clazz.directlyRealizedInterfaces
		
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
		
		for(interface : interfaces){
			interface.generateAttributesImports(types)
		}
		
		if(Utils.isEntity(clazz)){
			val assosiationClasses = ClassifierUtils.getLinkedAssociationClass(clazz)
			
			assosiationClasses.forEach[asso |
				if(!types.contains(asso) ){
					types.add(asso)
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
	
	/**
	 * génère les attributs liés aux extends
	 */
	static def generateExtendsAttributes(Classifier clazz){
		val parents = clazz.generalizations
		if(parents !== null && !parents.empty){
			'''«parents.fold("")[acc, parent |
				acc + 
				'''
				
				@Map()
				«Utils.getFirstToLowerCase(parent.general.name)»: «ClassifierUtils.getDtoClassName(parent.general)»;
				'''
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
	
	static def generateInterfaceAttributes(Classifier clazz){
		val interfaces = clazz.directlyRealizedInterfaces
		'''
		«interfaces.fold("")[acc, interface |
			acc + '''«interface.generateAttributes(clazz)»'''
		]»
		'''
	}
	 
	
	/**
	 * génère les attributs simples de la classe
	 */
	static def generateAttributes(Classifier clazz, Classifier fromClass){
		val attributes = ClassifierUtils.getOwnedAttributes(clazz)
		
		'''
		«attributes.fold("")[acc, attribut|
 			acc + '''
 			«attribut.generateAttribut(newArrayList(), fromClass)»'''	
 		]»
 		'''
	}
	
		/**
	 * génère les attributs simples de la classe
	 */
	static def generateAttributes(Classifier clazz, ArrayList<String> names, Classifier fromClass){
		val attributes = ClassifierUtils.getOwnedAttributes(clazz)
		
		'''
		«attributes.fold("")[acc, attribut|
 			acc + '''
 			«attribut.generateAttribut(newArrayList(names), fromClass)»'''	
 		]»
 		'''
	}
	
	/**
	 * génère un attribut de la classe
	 */
	static def generateAttribut(Property property, ArrayList<String> names, Classifier fromClass){
		if(PropertyUtils.isClassAttribute(property)){
			'''«property.generateClassAttribute(names, fromClass)»'''
		}else{
			'''«property.generateBasicAttribute(names)»'''
		}
	}
	
	/**
	 * génère un attribut lié a la classe
	 */
	static def generateClassAttribute(Property property, ArrayList<String> names, Classifier fromClass){
		if(Utils.isValueObject(property.type)){
			'''«property.generateValueObjectAttribute(names, fromClass)»'''
		}else if(Utils.isNomenclature(property.type)){
			'''«property.generateEnumAttributes(names)»'''
		}else{
			'''«property.generateEntityAttributes(names)»'''
		}
	}
	
	/**
	 * génère un attribut de type value Object
	 */
	static def generateValueObjectAttribute(Property property, ArrayList<String> names, Classifier fromClass){
		val name = Utils.addAdditionnalName(Utils.getNameFromList(names), property.name)
		val type = property.type
		names.add(property.name)
		if(type instanceof Classifier){
			if(!property.multivalued){
				return 
				'''
				«type.generateAttributes(names, fromClass)»
				'''
			}else{
				val tableName = Utils.addAdditionnalName(fromClass.name, property.name)
				'''
				
				@Map()
				«name»: Array<«Utils.getFirstToUpperCase(tableName)»DTO>;
				'''
			}
		}else{
			''''''
		}
	}
	
	/**
	 * génère un attribut de type entity ou enum
	 */
	static def generateEntityAttributes(Property property, ArrayList<String> names){
		val type = property.type
		val name = Utils.addAdditionnalName(Utils.getNameFromList(names), property.name)
		val propName = Utils.getListPoint(names) + '.' + property.name 
		//names.add(property.name)
		if(type instanceof Classifier){
			if(!property.multivalued){
				val ids = ClassifierUtils.getId(type)
				var alias = ""
				if(!names.empty){
					alias = '''
					@Alias('«name»', '«propName»')'''
				}
				return '''
				«ids.fold("")[acc, id |
					acc + '''«property.generateEntityAttribute(id, names)»'''
				]»
				
				@Map()
				«alias»
				«name»: «ClassifierUtils.getDtoClassName(type)»;
				'''
				
			}else{
				return '''
				
				@Map()
				«name»: Array<«ClassifierUtils.getDtoClassName(type)»>;
				'''
			}
		}else{
			''''''
		}
		
	}
	
	static def generateEnumAttributes(Property property, ArrayList<String> names){
		val type = property.type
		val name = Utils.addAdditionnalName(Utils.getNameFromList(names), property.name)
		val propName = Utils.getListPoint(names) + '.' + property.name 
		val codeName = Utils.addAdditionnalName(Utils.getNameFromList(names), "code" + Utils.getFirstToUpperCase(property.name))
		val codeAliasName = propName + '.code'
		if(type instanceof Classifier){
			if(!property.multivalued){
				var alias = ""
				var codeAlias = ""
				if(!names.empty){
					alias = '''
					@Alias('«name»', '«propName»')'''
					codeAlias = '''
					@Alias('«codeAliasName»')'''
					
				}
				return '''
				
				@Map()
				«codeAlias»
				«codeName»: number;
				
				@Map()
				«alias»
				«name»: «ClassifierUtils.getDtoClassName(type)»;
				'''
				
			}else{
				return '''
				@Map()
				«codeName»: Array<number>;
				
				@Map()
				«name»: Array<«ClassifierUtils.getDtoClassName(type)»>;
				'''
			}
		}
	}
	
	static def generateEntityAttribute(Property property, Property id, ArrayList<String> names){
		val fieldName = Utils.addAdditionnalName(Utils.getNameFromList(names), id.name) + Utils.getFirstToUpperCase(property.name)
		//val propName = Utils.addAdditionnalName(Utils.getNameFromList(names), property.name)
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
		var alias = ''''''
		if(!names.empty){
			alias = '''
			
			@Alias('«Utils.getListPoint(names)».«property.name»','«name»')
			'''
		}
		if(!property.multivalued){
			'''
			
			@Map()«alias»
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
			return ((Utils.isEntity(type) || Utils.isValueObject(type) || Utils.isNomenclature(type)) && (attribut.multivalued))
		]
		
		val interfaces = clazz.directlyRealizedInterfaces
		
		return '''
		«primitiveAttributes.fold("")[acc, attribut|
			acc + '''«attribut.generateMultiValuedPrimitiveTypeDto(clazz)»'''
 		]»
		«multiAttributes.fold("")[acc, attribut |
 			acc + '''«attribut.generateMultiValuedNPTypeDto(clazz)»'''
 		]»«interfaces.fold("")[acc, interface |
 			acc + '''«interface.generateInterfaceMultivaluedDto(clazz)»'''
 		]»
		'''
		
	}
	
	/**
	 * génère le model d'un attribut multivalué
	 */
	static def generateMultiValuedPrimitiveTypeDto(Property property, Classifier fromClass){
		val ids = ClassifierUtils.getId(fromClass)
		'''
		
		export class «PropertyUtils.getMultivaluedPropertyDtoName(property, fromClass)» {
			
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
			«Utils.getFirstToLowerCase(fromClass.name)»: «ClassifierUtils.getDtoClassName(fromClass)»;
		}
		'''
	}
	
	static def generateMultiValuedPrimitiveTypeDtoIdAttributes(Property property, Property id){
		val idName = id.name
		'''
		
		@Map()
		«idName»: «TypeUtils.getMetierTypescriptType(id.type)»;
		'''
		
	}
	
	
	/**
	 * génère les table d'association 
	 */	
	 static def generateMultiValuedNPTypeDto(Property property, Classifier fromClass){
	 	if(Utils.isEntity(property.type)){
	 		'''«property.generateMultiValuedEntityDto(fromClass)»'''
	 	}else if(Utils.isValueObject(property.type)){
	 		'''«property.generateMultiValuedValueObjectDto(fromClass)»'''
	 	}else if (Utils.isNomenclature(property.type)){
	 		'''«property.generateMultiValuedEnumDto(fromClass)»'''
	 	}
	 	
	}
	
	static def generateMultiValuedEntityDto(Property property, Classifier fromClass){
		val type = property.type
	 	val owner = property.owner
	 	if(property.association !== null){
	 		if(type != owner){
		 		'''
		 		«property.generateNPTypeAssociationDto(fromClass)»
		 		'''
	 		}else{
	 			''''''
	 		}
	 	}else{
	 		'''
	 		«property.generateNPTypeDto(fromClass)»
	 		'''
	 	}
	}
	
	static def generateNPTypeAssociationDto(Property property, Classifier fromClass){
		
		val members = property.association.ownedEnds
 		val member = members.get(0)
		val idsOwner = ClassifierUtils.getId(fromClass)
		val idsProp = ClassifierUtils.getId(member.type as Classifier)
		val type = property.type
		'''
		
		export class «PropertyUtils.getMultivaluedPropertyDtoName(property, fromClass)»{
			«idsProp.fold("")[acc, id |
				if(acc != ""){
					acc + '''«member.generateNPTDtoIdAttributes(id)»'''
				}else{
					acc + '''«member.generateNPTDtoIdAttributes(id)»'''
				}
			]»
			
			@Map()
			«Utils.getFirstToLowerCase(fromClass.name)»: «ClassifierUtils.getDtoClassName(fromClass)»;
			«idsOwner.fold("")[acc, id |
				if(acc != ""){
					acc + '''«property.generateNPTDtoIdAttributes(id)»'''
				}else{
					acc + '''«property.generateNPTDtoIdAttributes(id)»'''
				}
			]»
			
			@Map()
			«Utils.getFirstToLowerCase(type.name)»: «ClassifierUtils.getDtoClassName(type as Classifier)»;
		}
		'''
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
	
	
	static def generateNPTypeDto(Property property, Classifier fromClass){
		val idsOwner = ClassifierUtils.getId(fromClass)
		val idsProp = ClassifierUtils.getId(property.type as Classifier)
		val type = property.type
		'''
		
		export class «PropertyUtils.getMultivaluedPropertyDtoName(property, fromClass)»{
			«idsOwner.fold("")[acc, id |
				if(acc != ""){
					acc + ''',«id.generateNPTModelIdAttributes()»'''
				}else{
					acc + '''«id.generateNPTModelIdAttributes()»'''
				}
			]»
			
			@Map()
			«Utils.getFirstToLowerCase(fromClass.name)»: «ClassifierUtils.getDtoClassName(fromClass)»;
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
		'''
		
		@Map()
		«Utils.getFirstToLowerCase(clazz.name)» : Array<«ClassifierUtils.getDtoClassName(clazz)»>;
		'''
		
	}
	
	static def generateMultiValuedValueObjectDto(Property property, Classifier fromClass){
		val idsOwner = ClassifierUtils.getId(fromClass)
		val type = property.type
		'''
		
		export class «PropertyUtils.getMultivaluedPropertyDtoName(property, fromClass)»{
			«idsOwner.fold("")[acc, id |
				if(acc != ""){
					acc + ''',«id.generateNPTModelIdAttributes()»'''
				}else{
					acc + '''«id.generateNPTModelIdAttributes()»'''
				}
			]»
			
			@Map()
			«Utils.getFirstToLowerCase(fromClass.name)»: «ClassifierUtils.getDtoClassName(fromClass)»;
			«(type as Classifier).generateAttributes(newArrayList(property.name), type as Classifier)»
			
		}
		'''
	}
	
	
	static def generateMultiValuedEnumDto(Property property, Classifier fromClass){
		val idsOwner = ClassifierUtils.getId(fromClass)
		val type = property.type
		'''
		
		export class «PropertyUtils.getMultivaluedPropertyDtoName(property, fromClass)»{
			«idsOwner.fold("")[acc, id |
				if(acc != ""){
					acc + ''',«id.generateNPTModelIdAttributes()»'''
				}else{
					acc + '''«id.generateNPTModelIdAttributes()»'''
				}
			]»
			
			@Map()
			«Utils.getFirstToLowerCase(fromClass.name)»: «ClassifierUtils.getDtoClassName(fromClass)»;
			
			@Map()
			«Utils.getFirstToLowerCase(property.name)»: «ClassifierUtils.getDtoClassName(type as Classifier)»;
		}
		'''
	}
	
	static def generateInterfaceMultivaluedDto(Interface interf, Classifier fromClass){
		val attributes = ClassifierUtils.getMultivaluedOwnedAttributes(interf)
		
		'''
		«attributes.fold("")[acc, attribut|
 			acc + '''
 			«attribut.generateMultiValuedNPTypeDto( fromClass)»'''	
 		]»
 		'''
	}
	
}