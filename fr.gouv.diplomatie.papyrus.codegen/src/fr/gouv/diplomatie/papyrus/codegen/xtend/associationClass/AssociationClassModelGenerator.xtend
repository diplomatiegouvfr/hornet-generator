package fr.gouv.diplomatie.papyrus.codegen.xtend.associationClass

import org.eclipse.uml2.uml.AssociationClass
import org.eclipse.uml2.uml.Property
import fr.gouv.diplomatie.papyrus.codegen.xtend.utils.AssociationClassUtils
import fr.gouv.diplomatie.papyrus.codegen.xtend.utils.Utils
import fr.gouv.diplomatie.papyrus.codegen.xtend.utils.ClassifierUtils
import org.eclipse.uml2.uml.Classifier
import fr.gouv.diplomatie.papyrus.codegen.xtend.utils.TypeUtils
import fr.gouv.diplomatie.papyrus.codegen.xtend.utils.PropertyUtils
import fr.gouv.diplomatie.papyrus.codegen.xtend.classifier.ClassifierModelGenerator

public class AssociationClassModelGenerator {
	
	static def generateCode(AssociationClass clazz){
		'''
		import Sequelize = require("sequelize");
		
		export var «AssociationClassUtils.getModelName(clazz)»: Sequelize.DefineAttributes = {
			«clazz.generateAttributes»
		}
		'''
	}
	
	/**
	 * génère les attributs du model de la classe d'association
	 */
	static def generateAttributes(AssociationClass clazz){
		val members = clazz.memberEnds
		return
		'''
		«members.fold("")[acc, member |
			if(acc != ""){
				acc + ''',
				''' + '''«member.generateMemberAttributes(clazz)»'''	
			}else{
				acc + '''«member.generateMemberAttributes(clazz)»'''
			}
		]»
		'''
	}
	
	/**
	 * génère les attribut liés à au membre de la classe d'association
	 */
	static def generateMemberAttributes(Property property, Classifier fromClass){
		val type = property.type
		if(Utils.isEntity(type)){
			'''«property.generateEntityMemberAttributes(fromClass)»'''
		}else if(Utils.isNomenclature(type)){
			'''«property.generateEnumMemberAttributes(fromClass)»'''
		}else if(Utils.isValueObject(type)){
			'''«property.generateValueObjectMemberAttributes(fromClass)»'''
		}
	}
	
	/**
	 * génère les attributs liés à un membre de type entity
	 */
	static def generateEntityMemberAttributes(Property property, Classifier fromClass){
		val ids = ClassifierUtils.getId(property.type as Classifier)
		'''«ids.fold("")[acc, id |
			if(acc != ""){
				acc + ''',
				'''  + '''«property.generateEntityMemberAttribute(id, fromClass)»'''
			}else{
				acc + '''«property.generateEntityMemberAttribute(id, fromClass)»'''
			}
		]»'''
		
	}
	
	/**
	 * génère l'attribut lié à un membre de type entity
	 */
	static def generateEntityMemberAttribute(Property property, Property id, Classifier fromClass){
		val name = id.name + Utils.getFirstToUpperCase(property.name)
		'''
		«name»: {
			type: Sequelize.«TypeUtils.getSequelizeType(id.type)»«id.generateIdAttributeTypeLength»,
			field: "«Utils.toSnakeCase(name)»",
			allowNull: «PropertyUtils.isNullable(property)»,
			primaryKey: true,
			references: {
				model: "«ClassifierUtils.getModelName(property.type as Classifier)»",
				key: "«id.name»"
			}
		}'''
	}
	
	static def generateEnumMemberAttributes(Property property, Classifier fromClass){
		val name = property.name
		val type = property.type
		'''
		«name»: {
			type: Sequelize.«TypeUtils.getEnumSequelizeType(type as Classifier)»,
			field: "«Utils.toSnakeCase(name)»",
			allowNull: «PropertyUtils.isNullable(property)»,
			primaryKey: true,
			references: {
				model: "«ClassifierUtils.getModelName(type as Classifier)»",
				key: "code"
			}
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
	 * génère les attributs liés a un membre de type valued object
	 */
	static def generateValueObjectMemberAttributes(Property property, Classifier fromClass){
		ClassifierModelGenerator.generateValueObjectAttributeDefinition(property,"",fromClass, true)
	}
	
}