package fr.gouv.diplomatie.papyrus.codegen.typescript.xtend.classifier;

import org.eclipse.uml2.uml.Classifier
import org.eclipse.uml2.uml.Property
import fr.gouv.diplomatie.papyrus.codegen.core.utils.ClassifierUtils
import fr.gouv.diplomatie.papyrus.codegen.core.utils.PropertyUtils
import fr.gouv.diplomatie.papyrus.codegen.typescript.utils.TypeUtils
import fr.gouv.diplomatie.papyrus.codegen.core.utils.Utils
import org.eclipse.uml2.uml.PrimitiveType
import org.eclipse.uml2.uml.Interface

public class ClassifierModelGenerator {
	
	static def generateCode(Classifier clazz){
		//val attributes = ClassifierUtils.getNotMultivaluedOwnedAttributes(clazz)
		//val hasAttributes = (!attributes.empty && attributes !== null)
		'''
		import * as _ from "lodash";
		import Sequelize = require("sequelize");
		«clazz.generateImports»
		
		export var «ClassifierUtils.getModelName(clazz)»: Sequelize.DefineAttributes = {
			«clazz.generateExtendsId»
			«clazz.generateInterfacesAttributes»
			«clazz.generateAttributes("", clazz, false, false)»
		}
		
		«clazz.generateMultivaluedAttributeModels»
		«««clazz.generateMultiValuedEntityAttributes(hasAttributes, "")»
		'''
	}
	
	/**
	 * génère les imports
	 */
	static def generateImports(Classifier clazz){
		''''''
	}
	
	/**
	 * génère les id de généralisations
	 */
	static def generateExtendsId(Classifier clazz){
		val extends = clazz.generalizations
		if(extends !== null && !extends.empty){
			val extend = extends.get(0)
			val ids = ClassifierUtils.getId(extend.general)
			val id = ids.get(0)
			'''
			«id.name»: {
				type: Sequelize.«TypeUtils.getSequelizeType(id.type)»«id.generateIdAttributeTypeLength»,
				field: "«Utils.toSnakeCase(id.name)»",
				allowNull: «PropertyUtils.isNullable(id)»,
				references: {
					model: "«ClassifierUtils.getModelName(extend.general)»",
					key: "«id.name»"
				}
			},'''
		}
		
	}
	
	static def generateInterfacesAttributes(Classifier clazz){
		val interfaces = clazz.directlyRealizedInterfaces
		if(interfaces !== null && !interfaces.empty){
			'''
			«interfaces.fold("")[acc, interface |
				acc + '''«interface.generateAttributes("", clazz, false, false)»'''
			]»,
			'''
		}else{
			''''''
		}
		
	}
	
	/**
	 * Génère les attributs de la classe
	 */
	static def generateAttributes(Classifier clazz, String additionnalName, Classifier fromClass, Boolean isPrimaryKey, Boolean nullable){
		val attributes = ClassifierUtils.getNotMultivaluedOwnedAttributes(clazz)
		'''«attributes.fold("")[acc, attribut|
			if(acc != ""){
 				acc + ''',
 				''' + '''«attribut.generateAttributDefinition(additionnalName, fromClass, isPrimaryKey, nullable)»'''
 			}else{
 				acc + '''«attribut.generateAttributDefinition(additionnalName, fromClass, isPrimaryKey, nullable)»'''
 			}		
 		]»'''
		
	}
	
	/**
	 * génère un attribut
	 */
	static def generateAttributDefinition(Property property, String additionalName, Classifier fromClass, Boolean isPrimaryKey, Boolean nullable){
		if(PropertyUtils.isID(property)){
			'''«property.generateIdAttributeDefinition(additionalName, fromClass, nullable)»'''
		}else{
			'''«property.generateNIdAttributeDefinition(additionalName, fromClass, isPrimaryKey, nullable)»'''
		}
	}
	
	/**
	 * génère la définition d'un attribut id
	 */
	static def generateIdAttributeDefinition(Property property, String additionnalName, Classifier fromClass, Boolean nullable){
		val name = Utils.addAdditionnalName(additionnalName, property.name)
		val isSequence = Utils.isSequence(property)
		var isNullable = PropertyUtils.isNullable(property)
		if(nullable){
			isNullable = true
		}
		var auto = ""
		if(isSequence){
			auto = '''
			
			autoIncrement: true,
			'''
		}
		'''
		«name»: {
			type: Sequelize.«TypeUtils.getSequelizeType(property.type)»«property.generateIdAttributeTypeLength»,«auto»
			field: "«Utils.toSnakeCase(name)»",
			allowNull: «isNullable»,
			primaryKey: true
		}'''
	}
	
	/**
	 * génère la définition d'un attribut qui n'est pas un id
	 */
	static def generateNIdAttributeDefinition(Property property, String additionnalName, Classifier fromClass, Boolean isPrimaryKey, Boolean nullable){
		if(PropertyUtils.isClassAttribute(property)){
			'''«property.generateClassAttributeDefinition(additionnalName, fromClass, isPrimaryKey, nullable)»'''
		}else{
			'''«property.generateBasicAttributeDefinition(additionnalName, fromClass, isPrimaryKey, nullable)»'''
		}
	}
	
	/**
	 * génère la définition d'un attribut lié a la classe
	 */
	static def generateClassAttributeDefinition(Property property, String additionnalName, Classifier fromClass,Boolean isPrimaryKey, Boolean nullable){
		if(Utils.isValueObject(property.type)){
			'''«property.generateValueObjectAttributeDefinition(additionnalName, fromClass, isPrimaryKey, nullable || PropertyUtils.isNullable(property))»'''
		}else if (Utils.isNomenclature(property.type)){
			'''«property.generateEnumAttributeDefinition(additionnalName, fromClass, isPrimaryKey, nullable)»'''
		}else{
			'''«property.generateEntityAttributesDefinition(additionnalName, fromClass, isPrimaryKey, nullable)»'''
		}
	}
	
	/**
	 * génère la définition d'un attribut de type value Object
	 */
	static def generateValueObjectAttributeDefinition(Property property, String additionnalName, Classifier fromClass, Boolean isPrimaryKey, Boolean nullable){
		val name = Utils.addAdditionnalName(additionnalName, property.name)
		val type = property.type
		if(type instanceof Classifier){
			val attributes = ClassifierUtils.getNotMultivaluedOwnedAttributes(type)
			val hasAttributes = (!attributes.empty && attributes !== null)
			return '''«type.generateAttributes(name, fromClass, isPrimaryKey, nullable && PropertyUtils.isNullable(property))»'''
			//«««type.generateMultiValuedEntityAttributes(hasAttributes, name)»
			
		}else{
			''''''
		}
	}
	
	/**
	 * génère la définition des attributs liés a un attribut de type value object
	 */
/* 	static def generateValueObjectAttributDefinition(Property property, Property parentProperty, String additionnalName){
		val propName = PropertyUtils.getValueObjectPropertyName(property, parentProperty)
		var name = ""
		if(additionnalName != "" && additionnalName!== null){
			name = additionnalName + Utils.getFirstToUpperCase(propName)
		}else{
			name = propName
		}
		'''
		«name»: {
			type: «property.getAttributeSequelizeTypeDeclaration»,
			field: "«Utils.toSnakeCase(name)»"«property.generateNIdAttributeDefaultValue»,
			allowNull: «PropertyUtils.isNullable(property)»
		}'''
	}*/
	
	/**
	 * génère la définition d'un attribut de type entity
	 */
	static def generateEntityAttributesDefinition(Property property, String additonnalName, Classifier fromClass, Boolean isPrimaryKey, Boolean nullable){
		val type = property.type
		if(type instanceof Classifier){
			val ids = ClassifierUtils.getId(type)
			ids.fold("")[acc , id |
				if(acc != ""){
					acc + ''',«property.generateEntityAttributeDefinition(id, additonnalName, fromClass, isPrimaryKey, nullable)»'''
				}else{
					acc + '''«property.generateEntityAttributeDefinition(id, additonnalName, fromClass, isPrimaryKey, nullable)»'''
				}
			]
		}else{
			''''''
		}
		
	}
	
	static def generateEnumAttributeDefinition(Property property, String additionnalName, Classifier fromClass, Boolean isPrimaryKey, Boolean nullable){
		val type = property.type
		val sqlType = TypeUtils.getEnumSequelizeType(type as Classifier)
		val propName = Utils.addAdditionnalName(additionnalName, property.name)
		var isNullable = PropertyUtils.isNullable(property)
		if(nullable){
			isNullable = true
		}
		var pk = '''primaryKey: true,'''
		return '''
			code«Utils.getFirstToUpperCase(propName)»: {
				type: Sequelize.«sqlType»,
				field: "code_«Utils.toSnakeCase(propName)»",
				allowNull: «isNullable»,«IF isPrimaryKey && !PropertyUtils.isNullable(property)»
				«pk»«ENDIF»
				references: {
					model: "«ClassifierUtils.getModelName(type as Classifier)»",
					key: "code"
				}
			}'''
	}
	
	/**
	 * génère la définition d'un attribut de type entity
	 */
	static def generateEntityAttributeDefinition(Property property, Property id,  String additionnalName, Classifier fromClass, Boolean isPrimaryKey, Boolean nullable){
		val type = property.type
		val propName = Utils.addAdditionnalName(additionnalName, id.name) + Utils.getFirstToUpperCase(property.name)
		var pk = '''primaryKey: true,'''
		var isNullable = PropertyUtils.isNullable(property)
		if(nullable){
			isNullable = true
		}
		return '''
			«propName»: {
				type: Sequelize.«TypeUtils.getSequelizeType(id.type)»«id.generateNIdAttributeTypeLength»,
				field: "«Utils.toSnakeCase(propName)»",
				allowNull: «isNullable»,«IF isPrimaryKey && !PropertyUtils.isNullable(property)»
				«pk»«ENDIF»
				references: {
					model: "«ClassifierUtils.getModelName(type as Classifier)»",
					key: "«id.name»"
				}
			}'''
	}
	
	/**
	 * génère la définition d'un attribut basique
	 */
	static def generateBasicAttributeDefinition(Property property, String additionnalName, Classifier fromClass, Boolean isPrimaryKey, Boolean nullable){
		var name = Utils.addAdditionnalName(additionnalName, property.name)
		var pk = '''primaryKey: true,'''
		var isNullable = PropertyUtils.isNullable(property)
		if(nullable){
			isNullable = true
		}
		'''
		«name»: {
			type: «property.getAttributeSequelizeTypeDeclaration»«property.generateNIdAttributeDefaultValue»,
			field: "«Utils.toSnakeCase(name)»",«IF isPrimaryKey && !isNullable»
			«pk»«ENDIF»
			allowNull: «isNullable»
		}'''
	}
	
	/**
	 * génère la déclaration de la taille du champs id
	*/
	static def generateIdAttributeTypeLength(Property property){
		val length = PropertyUtils.getStereotypePropertyValue(property, Utils.MODEL_KEYATTRIBUTE, Utils.MODEL_ATTRIBUTE_LENGTH )
		if(length !== null && length != 0){
			'''(«length»)'''
		}else{
			''''''
		}
	}
	
	/**
	 * génère la déclaration de la taille du champs non id
	 */
	static def generateNIdAttributeTypeLength(Property property){
		val length = PropertyUtils.getStereotypePropertyValue(property, Utils.MODEL_ATTRIBUTE, Utils.MODEL_ATTRIBUTE_LENGTH )
		if(length !== null && length != 0){
			'''(«length»)'''
		}else{
			''''''
		}
	}
	
	/**
	 * génère la déclaration de la valeur par defaut
	 * TODO : à refaire
	 */
	static def generateNIdAttributeDefaultValue(Property property){
		val value = PropertyUtils.getDefaultValue(property)
		if(value !== null){
			''',
			''' + '''defaultValue: "«value»"'''
		}else{
			''''''
		}
	}
	
	/**
	 * génère les attributs liés aux liens multivalués du model 
	 */
	static def generateMultiValuedEntityAttributes(Classifier clazz, Boolean hasAttributes, String additionnalName){
		val references = ClassifierUtils.getAllReferencesTo(clazz).filter[attribut|
			if(attribut.association !== null){
				var member = attribut.association.ownedEnds.get(0)
				return (!member.multivalued) 
			}else{
				return false
			}
		]
		return
		'''
		«references.fold("")[acc, ref |
			if(!hasAttributes && acc==""){
				acc + '''«ref.generateReferenceAttributes(clazz, additionnalName)»'''
			}else{
				acc + ''',«ref.generateReferenceAttributes(clazz, additionnalName)»'''
			}
		]»
		'''
	}
	
	/**
	 * génère l'attribut lié au lien multivalué du model 
	 */
	static def generateReferenceAttributes(Property property, Classifier clazz, String additionnalName){
		if(property.association !== null){
			'''«property.generateReferenceAttributesAssocation(clazz,additionnalName)»'''
		}else{
			'''«property.generateReferenceAttributesNA(clazz,additionnalName)»'''
		}
	}
	
	/**
	 *  génère l'attribut lié au lien multivalué du model  - chaques id
	 */
	static def generateReferenceAttributesAssocation(Property property, Classifier clazz, String additionnalName){
		val toAttribute = property.association.ownedEnds.get(0)
		val fromClass = toAttribute.type
		if(fromClass instanceof Classifier ){
			if(!(toAttribute.multivalued)){
				val ids = ClassifierUtils.getId(fromClass)
				'''«ids.fold("")[acc, id |
					if(acc != ""){
						acc + ''',
						''' + '''«property.generateReferenceAttributeAssocation(id, clazz, additionnalName)»'''
					}else{
						acc + '''«property.generateReferenceAttributeAssocation(id, clazz, additionnalName)»'''
					}
				]»'''
			}
		}else {
			''''''
		}
	}
	
	/**
	 *  génère l'attribut lié au lien multivalué du model  - id
	 */
	static def generateReferenceAttributeAssocation(Property property, Property id, Classifier clazz, String additionnalName){
		var field =  property.association.ownedEnds.get(0)
		val fromClass = field.type
		val name = id.name + Utils.getFirstToUpperCase(field.name)
		var fieldName = Utils.addAdditionnalName(additionnalName, name)
		'''
		«fieldName»: {
			type: «id.getAttributeSequelizeTypeDeclaration»,
			field: "«Utils.toSnakeCase(fieldName)»",
			allowNull: «PropertyUtils.isNullable(property)»,
			references: {
				model: "«ClassifierUtils.getModelName(fromClass as Classifier)»",
				key: "«id.name»"
			}
		}'''
	}
	
	static def generateReferenceAttributesNA(Property property, Classifier clazz, String additionnalName){
		val fromClass = property.owner
		if(fromClass instanceof Classifier){
			val ids = ClassifierUtils.getId(fromClass)
			'''«ids.fold("")[acc, id |
				if(acc != ""){
					acc + ''',
					''' + '''«property.generateReferenceAttributeNA(id, clazz, additionnalName)»'''
				}else{
					acc + '''«property.generateReferenceAttributeNA(id, clazz, additionnalName)»'''
				}
			]»'''
		}
	}
	
	static def generateReferenceAttributeNA(Property property, Property id, Classifier clazz, String additionnalName){
		val fromClass = property.owner
		if(fromClass instanceof Classifier){
			val name = property.name + Utils.getFirstToUpperCase(id.name)
			val fieldName = Utils.addAdditionnalName(additionnalName, name)
			'''
			«fieldName»: {
				type: «property.getAttributeSequelizeTypeDeclaration»,
				field: "«Utils.toSnakeCase(fieldName)»",
				allowNull: «PropertyUtils.isNullable(property)»,
				references: {
					model: "«ClassifierUtils.getModelName(fromClass as Classifier)»",
					key: "«id.name»"
				}
			}'''
		}
	}

	/**
	 * génère les models liés aux attributs multi valués 
	 */
	static def generateMultivaluedAttributeModels(Classifier clazz){
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
		
		val multivaluedEnums = ownedAttributes.filter[attribut |
			val type = attribut.type
			return (Utils.isNomenclature(type) && (attribut.multivalued))
		]
		
		val multivaluedValueObject = ownedAttributes.filter[attribut |
			val type = attribut.type
			return (Utils.isValueObject(type) && (attribut.multivalued))
		]
		
		val interfaces = clazz.directlyRealizedInterfaces
		
		return '''
		«primitiveAttributes.fold("")[acc, attribut|
			acc + '''«attribut.generateMultiValuedPrimitiveTypeModel(clazz)»'''
 		]»
		«multiAttributes.fold("")[acc, attribut |
 			acc + '''«attribut.generateMultiValuedNPTypeModel(clazz)»'''
 		]»«multivaluedEnums.fold("")[acc, attribut |
 			acc + '''«attribut.generateMultiValuedEnumModel(clazz)»'''
 		]»«multivaluedValueObject.fold("")[acc, attribut |
 			acc + '''«attribut.generateMultiValuedVOModel(clazz)»'''
 		]»«interfaces.fold("")[acc, interface |
	 		acc + '''«interface.generateInterfaceModels(clazz)»'''
	 	]»
		'''
		
	}
	
	static def generateMultivaluedAttribut(Property property, Classifier fromClass){
		val type = property.type 
		if(Utils.isEntity(type)){
			'''«property.generateMultiValuedNPTypeModel(fromClass)»'''
		}else if(Utils.isValueObject(type)){
			'''«property.generateMultiValuedVOModel(fromClass)»'''
		}else if(Utils.isNomenclature(type)){
			'''«property.generateMultiValuedEnumModel(fromClass)»'''
		}else{
			'''«property.generateMultiValuedPrimitiveTypeModel(fromClass)»'''
		}
	}

	/**
	 * génère le model d'un attribut multivalué
	 */
	static def generateMultiValuedPrimitiveTypeModel(Property property, Classifier fromClass){
		val ids = ClassifierUtils.getId(fromClass)
		'''
		
		export var «PropertyUtils.getMultivaluedPropertyModelName(property, fromClass)»: Sequelize.DefineAttributes={
			«property.name»: {
				type: «property.getAttributeSequelizeTypeDeclaration»,
				field: "«Utils.toSnakeCase(property.name)»",
				primaryKey: true,
				allowNull: false
			},
			«ids.fold("")[acc, id |
				if(acc != ""){
					acc + ''',«property.generateMultiValuedPrimitiveTypeModelIdAttributes(id, fromClass)»'''
				}else{
					acc + '''«property.generateMultiValuedPrimitiveTypeModelIdAttributes(id, fromClass)»'''
				}
			]»
		}
		'''
	}
	
	static def generateMultiValuedPrimitiveTypeModelIdAttributes(Property property, Property id, Classifier fromClass){
		val idName = id.name
		val name = idName + Utils.getFirstToUpperCase(fromClass.name)
		'''
		«idName»:{
			type: Sequelize.«TypeUtils.getSequelizeType(id.type)»«id.generateIdAttributeTypeLength»,
			field: "«Utils.toSnakeCase(name)»",
			allowNull: false,
			primaryKey: true,
			references: {
				model: "«ClassifierUtils.getModelName(fromClass)»",
				key: "«idName»"
			}
		}
		'''
	}
	
	/**
	 * génère les table d'association 
	 */	
	 static def generateMultiValuedNPTypeModel(Property property, Classifier fromClass){
	 	val type = property.type
	 	val owner = property.owner
	 	if(property.association !== null){
	 		if(type != owner){
		 		'''
		 		«property.generateNPTypeAssociationModel(fromClass)»
		 		'''
	 		}else{
	 			''''''
	 		}
	 	}else{
	 		'''
	 		«property.generateNPTypeModel(fromClass)»
	 		'''
	 	}
	}
	
	static def generateNPTypeAssociationModel(Property property, Classifier fromClass){
		val members = property.association.ownedEnds
 		val member = members.get(0)
		val idsOwner = ClassifierUtils.getId(fromClass)
		val idsProp = ClassifierUtils.getId(property.type as Classifier)
		'''
		
		export var «PropertyUtils.getMultivaluedPropertyModelName(property, fromClass)»: Sequelize.DefineAttributes={
			«idsProp.fold("")[acc, id |
				if(acc != ""){
					acc + ''',«property.generateNPTAssociationModelIdAttributes(id, fromClass)»'''
				}else{
					acc + '''«property.generateNPTAssociationModelIdAttributes(id, fromClass)»'''
				}
			]»,
			«idsOwner.fold("")[acc, id |
				if(acc != ""){
					acc + ''',«member.generateNPTAssociationModelIdAttributes(id, fromClass)»'''
				}else{
					acc + '''«member.generateNPTAssociationModelIdAttributes(id, fromClass)»'''
				}
			]»
		}
		'''
	}
	
	static def generateNPTAssociationModelIdAttributes(Property property, Property id, Classifier fromClass){
		val type = property.type
		val idName = Utils.addAdditionnalName(id.name, type.name)
		if(type instanceof Classifier){
			'''
			«idName»:{
				type: Sequelize.«TypeUtils.getSequelizeType(id.type)»«id.generateIdAttributeTypeLength»,
				field: "«Utils.toSnakeCase(idName)»",
				allowNull: false,
				primaryKey: true,
				references: {
					model: "«ClassifierUtils.getModelName(type)»",
					key: "«id.name»"
				}
			}'''
		}
	}
	
	
	static def generateNPTypeModel(Property property, Classifier fromClass){
		val idsOwner = ClassifierUtils.getId(fromClass)
		val idsProp = ClassifierUtils.getId(property.type as Classifier)
		
		'''
		
		export var «PropertyUtils.getMultivaluedPropertyModelName(property, fromClass)»: Sequelize.DefineAttributes={
			«idsOwner.fold("")[acc, id |
				if(acc != ""){
					acc + ''',«id.generateNPTModelIdAttributes(fromClass)»'''
				}else{
					acc + '''«id.generateNPTModelIdAttributes(fromClass)»'''
				}
			]»,
			«idsProp.fold("")[acc, id |
				if(acc != ""){
					acc + ''',«property.generateNPTModelIdAttributes(id, fromClass)»'''
				}else{
					acc + '''«property.generateNPTModelIdAttributes(id, fromClass)»'''
				}
			]»
		}
		'''
	}
	
	
	static def generateNPTModelIdAttributes(Property id, Classifier fromClass){
		val type = id.owner
		if(type instanceof Classifier){
			val idName = Utils.addAdditionnalName(id.name, type.name)
			if(type instanceof Classifier){
				'''
				«idName»:{
					type: Sequelize.«TypeUtils.getSequelizeType(id.type)»«id.generateIdAttributeTypeLength»,
					field: "«Utils.toSnakeCase(idName)»",
					allowNull: false,
					primaryKey: true,
					references: {
						model: "«ClassifierUtils.getModelName(type)»",
						key: "«id.name»"
					}
				}'''
			}
		}
	}
	
	static def generateNPTModelIdAttributes(Property property, Property id, Classifier fromClass){
		val type = property.type
		val idName = Utils.addAdditionnalName(id.name, type.name)
		if(type instanceof Classifier){
			'''
			«idName»:{
				type: Sequelize.«TypeUtils.getSequelizeType(id.type)»«id.generateIdAttributeTypeLength»,
				field: "«Utils.toSnakeCase(idName)»",
				allowNull: false,
				primaryKey: true,
				references: {
					model: "«ClassifierUtils.getModelName(type)»",
					key: "«id.name»"
				}
			}
			'''
		}
	}
	
	
	/**
	 * délcaration du type sequelize 
	 */
	static def getAttributeSequelizeTypeDeclaration(Property property){
		'''Sequelize.«TypeUtils.getSequelizeType(property.type)»«property.generateNIdAttributeTypeLength»'''
	}
	
	
	static def generateExtends(Classifier clazz){
		val parents = clazz.generalizations
		if(parents.length > 0){
			'''«parents.fold("")[acc, parent |
				acc + ''',«ClassifierUtils.getModelName(parent.general)»'''
			]»'''
		}else{
			''''''
		}
	}
	
	/**
	 * génère une table d'association pour les enums 
	 */
	static def generateMultiValuedEnumModel(Property property, Classifier fromClass){
		val type = property.type
		val idsOwner = ClassifierUtils.getId(fromClass)
		
		'''
		
		export var «PropertyUtils.getMultivaluedPropertyModelName(property, fromClass)»: Sequelize.DefineAttributes={
			«idsOwner.fold("")[acc, id |
				if(acc != ""){
					acc + ''',«id.generateNPTModelIdAttributes(fromClass)»'''
				}else{
					acc + '''«id.generateNPTModelIdAttributes(fromClass)»'''
				}
			]»,
			code: {
				type: Sequelize.«TypeUtils.getEnumSequelizeType(type as Classifier)»,
				field: "code",
				allowNull: false,
				primaryKey: true,
				references: {
					model: "«ClassifierUtils.getModelName(type as Classifier)»",
					key: "code"
				}
			}
		}
		'''
		
	}
	
	static def generateMultiValuedVOModel(Property property, Classifier fromClass){
		val type = property.type
		val idsOwner = ClassifierUtils.getId(fromClass)
		
		'''
		
		export var «PropertyUtils.getMultivaluedPropertyModelName(property, fromClass)»: Sequelize.DefineAttributes={
			«idsOwner.fold("")[acc, id |
				if(acc != ""){
					acc + ''',«id.generateNPTModelIdAttributes(fromClass)»'''
				}else{
					acc + '''«id.generateNPTModelIdAttributes(fromClass)»'''
				}
			]»,
			«(type as Classifier).generateAttributes(property.name, fromClass, true, false)»
		}
		'''
	}
	
	static def generateInterfaceModels(Interface interf, Classifier fromClass){
		val attributes = ClassifierUtils.getMultivaluedOwnedAttributes(interf)
		
		'''
		«attributes.fold("")[acc, attribut|
 			acc + '''
 			«attribut.generateMultivaluedAttribut(fromClass)»'''	
 		]»
 		'''
	}
}
