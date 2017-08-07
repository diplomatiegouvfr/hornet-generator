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
				''' + '''«member.generateMemberAttributes»'''	
			}else{
				acc + '''«member.generateMemberAttributes»'''
			}
		]»
		'''
	}
	
	/**
	 * génère les attribut liés à au membre de la classe d'association
	 */
	static def generateMemberAttributes(Property property){
		val type = property.type
		if(Utils.isEntity(type)){
			'''«property.generateEntityMemberAttributes»'''
		}else if(Utils.isValueObject(type)){
			'''«property.generateValueObjectMemberAttributes»'''
		}
	}
	
	/**
	 * génère les attributs liés à un membre de type entity
	 */
	static def generateEntityMemberAttributes(Property property){
		val ids = ClassifierUtils.getId(property.type as Classifier)
		'''
		«ids.fold("")[acc, id |
			if(acc != ""){
				acc + ''',
				'''  + '''«property.generateEntityMemberAttribute(id)»'''
			}else{
				acc + '''«property.generateEntityMemberAttribute(id)»'''
			}
		]»
		'''
		
	}
	
	/**
	 * génère l'attribut lié à un membre de type entity
	 */
	static def generateEntityMemberAttribute(Property property, Property id){
		val name = id.name + Utils.getFirstToUpperCase(property.name)
		'''
		«name»: {
			type: Sequelize.«TypeUtils.getSequelizeType(property.type)»«property.generateIdAttributeTypeLength»,
			field: "«Utils.toSnakeCase(name)»",
			allowNull: «PropertyUtils.isNullable(property)»,
			primaryKey: true,
			references: {
				model: "«property.type.name»",
				key: "«id.name»"
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
	static def generateValueObjectMemberAttributes(Property property){
		ClassifierModelGenerator.generateValueObjectAttributeDefinition(property,"")
	}
	
}