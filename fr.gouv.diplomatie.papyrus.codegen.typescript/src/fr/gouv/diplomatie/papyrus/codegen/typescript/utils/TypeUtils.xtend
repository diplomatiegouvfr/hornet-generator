package fr.gouv.diplomatie.papyrus.codegen.typescript.utils

import org.eclipse.uml2.uml.Type;
import org.eclipse.uml2.uml.Classifier
import fr.gouv.diplomatie.papyrus.codegen.core.utils.Utils

class TypeUtils{
	
	public static var MODEL_HORNETTYPE_SEQUELIZETYPE = 'sequelizeType';
	public static var MODEL_HORNETTYPE_METIERCLASSTYPE = 'metierClassType';
	public static var MODEL_HORNETTYPE_TYPESCRIPTTYPE = 'typescriptType';
	
	/**
	 * retourne le type sequelize
	 */
	static def getSequelizeType(Type type){
		val sequelizeType = Utils.getStereotypePropertyValue(type,Utils.MODEL_HORNETTYPE, MODEL_HORNETTYPE_SEQUELIZETYPE);
		if(sequelizeType === null || sequelizeType == ""){
			return "STRING"
		}
		return sequelizeType
	}
	
	/**
	 * retourne le type  typescript
	 */
	static def getTypescriptType(Type type){
		val typescriptType = Utils.getStereotypePropertyValue(type,Utils.MODEL_HORNETTYPE, MODEL_HORNETTYPE_TYPESCRIPTTYPE);
		if(typescriptType === null || typescriptType == ""){
			return "string"
		}
		return typescriptType
	}
	
	/**
	 * retourne le type typescript utilisé dans les classes métier
	 */
	static def getMetierTypescriptType(Type type){
		val metierClassType = Utils.getStereotypePropertyValue(type,Utils.MODEL_HORNETTYPE, MODEL_HORNETTYPE_METIERCLASSTYPE);
		if(metierClassType === null || metierClassType == ""){
			return "string"
		}
		return metierClassType
	}
	
	/**
	 * retourne le type du code de l'enum
	 */
	static def getEnumType(Classifier type){
		return "integer"
	}
	
	/**
	 * retourne le type du code de l'enum pour sequelize
	 */
	static def getEnumSequelizeType(Classifier type){
		return "INTEGER"
	}
	
		/**
	 * retourne le type du code de l'enum pour typescript
	 */
	static def getEnumTypescriptType(Classifier type){
		return "number"
	}
}