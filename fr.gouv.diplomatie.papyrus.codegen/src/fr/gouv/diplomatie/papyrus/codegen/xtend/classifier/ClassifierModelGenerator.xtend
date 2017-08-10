package fr.gouv.diplomatie.papyrus.codegen.xtend.classifier;

import org.eclipse.uml2.uml.Classifier
import org.eclipse.uml2.uml.Property
import fr.gouv.diplomatie.papyrus.codegen.xtend.utils.ClassifierUtils
import fr.gouv.diplomatie.papyrus.codegen.xtend.utils.PropertyUtils
import fr.gouv.diplomatie.papyrus.codegen.xtend.utils.TypeUtils
import fr.gouv.diplomatie.papyrus.codegen.xtend.utils.Utils
import org.eclipse.uml2.uml.PrimitiveType

public class ClassifierModelGenerator {
	
	static def generateCode(Classifier clazz){
		//val attributes = ClassifierUtils.getNotMultivaluedOwnedAttributes(clazz)
		//val hasAttributes = (!attributes.empty && attributes !== null)
		'''
		import * as _ from "lodash";
		import Sequelize = require("sequelize");
		«clazz.generateImports»
		
		export var «ClassifierUtils.getModelName(clazz)»: Sequelize.DefineAttributes = «clazz.generateAssignForExtends»{
			«clazz.generateAttributes("")»
		}«clazz.generateExtends»«clazz.generateAssignClose»
		
		«clazz.generateMultivaluedAttributeModels»
		«««clazz.generateMultiValuedEntityAttributes(hasAttributes, "")»
		'''
	}
	
	/**
	 * génère les imports
	 */
	static def generateImports(Classifier clazz){
		'''
		«clazz.generateExtendsImports»
		'''
	}
	
	/**
	 * génère les imports lié aux extensions
	 */
	static def generateExtendsImports(Classifier clazz){
		val parents = clazz.generalizations
		if(parents.length > 0){
			'''«parents.fold("")[acc, parent |
				acc + '''import { «ClassifierUtils.getModelName(parent.general)» } from "«ClassifierUtils.getModelPath(parent.general)»";
				'''
			]»'''
		}else{
			''''''
		}
	}
	
	
	/**
	 * ajoute le assignIn pour les extends
	 */
	static def generateAssignForExtends(Classifier clazz){
		val parents = clazz.generalizations
		if(parents.length > 0){
			'''_.assignIn('''
		}else{
			''''''
		}
	}
	
	static def generateAssignClose(Classifier clazz){
		val parents = clazz.generalizations
		if(parents.length > 0){
			''')'''
		}else{
			''''''
		}
	}
	
	/**
	 * Génère les attributs de la classe
	 */
	static def generateAttributes(Classifier clazz, String additionnalName){
		val attributes = ClassifierUtils.getNotMultivaluedOwnedAttributes(clazz)
		'''
		«attributes.fold("")[acc, attribut|
			if(acc != ""){
 				acc + ''',
 				''' + '''«attribut.generateAttributDefinition(additionnalName)»'''
 			}else{
 				acc + '''«attribut.generateAttributDefinition(additionnalName)»'''
 			}		
 		]»
		'''
		
	}
	
	/**
	 * génère un attribut
	 */
	static def generateAttributDefinition(Property property, String additionalName){
		if(PropertyUtils.isID(property)){
			'''«property.generateIdAttributeDefinition(additionalName)»'''
		}else{
			'''«property.generateNIdAttributeDefinition(additionalName)»'''
		}
	}
	
	/**
	 * génère la définition d'un attribut id
	 */
	static def generateIdAttributeDefinition(Property property, String additionnalName){
		val name = Utils.addAdditionnalName(additionnalName, property.name)
		'''
		«name»: {
			type: Sequelize.«TypeUtils.getSequelizeType(property.type)»«property.generateIdAttributeTypeLength»,
			field: "«Utils.toSnakeCase(name)»",
			allowNull: «PropertyUtils.isNullable(property)»,
			primaryKey: true
		}'''
	}
	
	/**
	 * génère la définition d'un attribut qui n'est pas un id
	 */
	static def generateNIdAttributeDefinition(Property property, String additionnalName){
		if(PropertyUtils.isClassAttribute(property)){
			'''«property.generateClassAttributeDefinition(additionnalName)»'''
		}else{
			'''«property.generateBasicAttributeDefinition(additionnalName)»'''
		}
	}
	
	/**
	 * génère la définition d'un attribut lié a la classe
	 */
	static def generateClassAttributeDefinition(Property property, String additionnalName){
		if(Utils.isValueObject(property.type)){
			'''«property.generateValueObjectAttributeDefinition(additionnalName)»'''
		}else{
			'''«property.generateEntityAttributesDefinition(additionnalName)»'''
		}
	}
	
	/**
	 * génère la définition d'un attribut de type value Object
	 */
	static def generateValueObjectAttributeDefinition(Property property, String additionnalName){
		val name = Utils.addAdditionnalName(additionnalName, property.name)
		val type = property.type
		if(type instanceof Classifier){
			val attributes = ClassifierUtils.getNotMultivaluedOwnedAttributes(type)
			val hasAttributes = (!attributes.empty && attributes !== null)
			return '''«type.generateAttributes(name)»
			«type.generateMultiValuedEntityAttributes(hasAttributes, name)»'''
		}else{
			''''''
		}
	}
	
	/**
	 * génère la définition des attributs liés a un attribut de type value object
	 */
	static def generateValueObjectAttributDefinition(Property property, Property parentProperty, String additionnalName){
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
	}
	
	/**
	 * génère la définition d'un attribut de type entity
	 */
	static def generateEntityAttributesDefinition(Property property, String additonnalName){
		val type = property.type
		if(type instanceof Classifier){
			val ids = ClassifierUtils.getId(type)
			ids.fold("")[acc , id |
				if(acc != ""){
					acc + ''',«property.generateEntityAttributeDefinition(id, additonnalName)»'''
				}else{
					acc + '''«property.generateEntityAttributeDefinition(id, additonnalName)»'''
				}
			]
		}else{
			''''''
		}
		
	}
	
	/**
	 * génère la définition d'un attribut de type entity
	 */
	static def generateEntityAttributeDefinition(Property property, Property id,  String additionnalName){
		val type = property.type
		val propName = Utils.addAdditionnalName(additionnalName, id.name) + Utils.getFirstToUpperCase(property.name)
		return '''
			«propName»: {
				type: Sequelize.«TypeUtils.getSequelizeType(id.type)»«id.generateNIdAttributeTypeLength»,
				field: "«Utils.toSnakeCase(propName)»"«property.generateNIdAttributeDefaultValue»,
				allowNull: «PropertyUtils.isNullable(property)»,
				references: {
					model: "«type.name»",
					key: "«id.name»"
				}
			}'''
	}
	
	/**
	 * génère la définition d'un attribut basique
	 */
	static def generateBasicAttributeDefinition(Property property, String additionnalName){
		var name = Utils.addAdditionnalName(additionnalName, property.name)
		'''
		«name»: {
			type: «property.getAttributeSequelizeTypeDeclaration»,
			field: "«Utils.toSnakeCase(name)»"«property.generateNIdAttributeDefaultValue»,
			allowNull: «PropertyUtils.isNullable(property)»
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
	 */
	static def generateNIdAttributeDefaultValue(Property property){
		val value = property.defaultValue
		if(value !== null){
			''',
			defaultValue:"«value»"'''
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
				model: "«fromClass.name»",
				key: "«id.name»"
			}
		}
		'''
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
					model: "«fromClass.name»",
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
		
		return '''
		«primitiveAttributes.fold("")[acc, attribut|
			acc + '''«attribut.generateMultiValuedPrimitiveTypeModel»'''
 		]»
		«multiAttributes.fold("")[acc, attribut |
 			acc + '''«attribut.generateMultiValuedNPTypeModel»'''
 		]»
		'''
		
	}

	/**
	 * génère le model d'un attribut multivalué
	 */
	static def generateMultiValuedPrimitiveTypeModel(Property property){
		val owner = property.owner
		if(owner instanceof Classifier){
			val ids = ClassifierUtils.getId(owner)
			'''
			
			export var «PropertyUtils.getMultivaluedPropertyModelName(property)»: Sequelize.DefineAttributes={
				"«property.name»": {
					type: «property.getAttributeSequelizeTypeDeclaration»,
					field: "«Utils.toSnakeCase(property.name)»",
					primaryKey: true,
					allowNull: false
				},
				«ids.fold("")[acc, id |
					if(acc != ""){
						acc + ''',«property.generateMultiValuedPrimitiveTypeModelIdAttributes(id)»'''
					}else{
						acc + '''«property.generateMultiValuedPrimitiveTypeModelIdAttributes(id)»'''
					}
				]»
			}
			'''
		}else{
			''''''
		}
	}
	
	static def generateMultiValuedPrimitiveTypeModelIdAttributes(Property property, Property id){
		val idName = id.name
		val owner = property.owner
		if(owner instanceof Classifier){
			val name = idName + Utils.getFirstToUpperCase(owner.name)
			'''
			"«idName»":{
				type: Sequelize.«TypeUtils.getSequelizeType(id.type)»«id.generateIdAttributeTypeLength»,
				field: "«Utils.toSnakeCase(name)»",
				allowNull: false,
				references: {
					model: "«owner.name»",
					key: "«idName»"
				}
			}
			'''
		}
	}
	
	/**
	 * génère les table d'association 
	 */	
	 static def generateMultiValuedNPTypeModel(Property property){
	 	val type = property.type
	 	val owner = property.owner
	 	if(property.association !== null){
	 		val members = property.association.ownedEnds
	 		val member = members.get(0)
	 		if(type != owner && member.multivalued){
		 		'''
		 		«property.generateNPTypeAssociationModel»
		 		'''
	 		}else{
	 			''''''
	 		}
	 	}else{
	 		'''
	 		«property.generateNPTypeModel»
	 		'''
	 	}
	}
	
	static def generateNPTypeAssociationModel(Property property){
		val owner = property.owner
		if(owner instanceof Classifier){
			val members = property.association.ownedEnds
	 		val member = members.get(0)
			val idsOwner = ClassifierUtils.getId(owner)
			val idsProp = ClassifierUtils.getId(member.type as Classifier)
			
			'''
			
			export var «PropertyUtils.getMultivaluedPropertyModelName(property)»: Sequelize.DefineAttributes={
				«idsProp.fold("")[acc, id |
					if(acc != ""){
						acc + ''',«member.generateNPTAssociationModelIdAttributes(id)»'''
					}else{
						acc + '''«member.generateNPTAssociationModelIdAttributes(id)»'''
					}
				]»,
				«idsOwner.fold("")[acc, id |
					if(acc != ""){
						acc + ''',«property.generateNPTAssociationModelIdAttributes(id)»'''
					}else{
						acc + '''«property.generateNPTAssociationModelIdAttributes(id)»'''
					}
				]»
			}
			'''
		}else{
			''''''
		}
	}
	
	static def generateNPTAssociationModelIdAttributes(Property property, Property id){
		val type = property.type
		val idName = Utils.addAdditionnalName(id.name, type.name)
		if(type instanceof Classifier){
			'''
			"«idName»":{
				type: Sequelize.«TypeUtils.getSequelizeType(id.type)»«id.generateIdAttributeTypeLength»,
				field: "«Utils.toSnakeCase(idName)»",
				allowNull: false,
				references: {
					model: "«type.name»",
					key: "«id.name»"
				}
			}
			'''
		}
	}
	
	
	static def generateNPTypeModel(Property property){
		val owner = property.owner
		if(owner instanceof Classifier){
			val idsOwner = ClassifierUtils.getId(owner)
			val idsProp = ClassifierUtils.getId(property.type as Classifier)
			
			'''
			
			export var «PropertyUtils.getMultivaluedPropertyModelName(property)»: Sequelize.DefineAttributes={
				«idsOwner.fold("")[acc, id |
					if(acc != ""){
						acc + ''',«id.generateNPTModelIdAttributes()»'''
					}else{
						acc + '''«id.generateNPTModelIdAttributes()»'''
					}
				]»,
				«idsProp.fold("")[acc, id |
					if(acc != ""){
						acc + ''',«property.generateNPTModelIdAttributes(id)»'''
					}else{
						acc + '''«property.generateNPTModelIdAttributes(id)»'''
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
				"«idName»":{
					type: Sequelize.«TypeUtils.getSequelizeType(id.type)»«id.generateIdAttributeTypeLength»,
					field: "«Utils.toSnakeCase(idName)»",
					allowNull: false,
					references: {
						model: "«type.name»",
						key: "«id.name»"
					}
				}
				'''
			}
		}
	}
	
	static def generateNPTModelIdAttributes(Property property, Property id){
		val type = property.type
		val idName = Utils.addAdditionnalName(id.name, property.name)
		if(type instanceof Classifier){
			'''
			"«idName»":{
				type: Sequelize.«TypeUtils.getSequelizeType(id.type)»«id.generateIdAttributeTypeLength»,
				field: "«Utils.toSnakeCase(idName)»",
				allowNull: false,
				references: {
					model: "«type.name»",
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
}
