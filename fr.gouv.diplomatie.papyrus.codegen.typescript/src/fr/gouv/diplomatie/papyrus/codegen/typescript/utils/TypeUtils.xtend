package fr.gouv.diplomatie.papyrus.codegen.typescript.utils

import org.eclipse.uml2.uml.Type;
import org.eclipse.uml2.uml.Classifier
import fr.gouv.diplomatie.papyrus.codegen.core.utils.Utils

class TypeUtils{
	

	
	/**
	 * retourne le type sequelize
	 */
	static def getSequelizeType(Type type){
		
		if(Utils.isType(type, "Boolean")){
			return 'BOOLEAN'
		}else if(Utils.isType(type,"Integer")){
			return 'INTEGER'
		}else if(Utils.isType(type,"BigInteger")){
			return 'BIGINT'
		}else if(Utils.isType(type,"Real")){
			return 'FLOAT'
		}else if(Utils.isType(type,"Double")){
			return 'DOUBLE'
		}else if(Utils.isType(type,"Float")){
			return 'FLOAT'
		}else if(Utils.isType(type,"Date")){
			return 'DATE'
		}else if(Utils.isType(type,"ByteArray")){
			return 'BLOB'
		}else{
			return 'STRING'
		}
	}
	
	/**
	 * retourne le type  typescript
	 */
	static def getTypescriptType(Type type){
		if(Utils.isType(type,"Boolean")){
			return 'boolean'
		}else if(Utils.isType(type,"Integer")){
			return 'number'
		}else if(Utils.isType(type,"BigInteger")){
			return 'number'
		}else if(Utils.isType(type,"Real")){
			return 'number'
		}else if(Utils.isType(type,"Double")){
			return 'number'
		}else if(Utils.isType(type,"Float")){
			return 'number'
		}else if(Utils.isType(type,"Date")){
			return 'string'
		}else if(Utils.isType(type,"ByteArray")){
			return 'Buffer'
		}else{
			return 'string'
		}
	}
	
	/**
	 * retourne le type typescript utilisé dans les classes métier
	 */
	static def getMetierTypescriptType(Type type){
		if(Utils.isType(type,"Boolean")){
			return 'boolean'
		}else if(Utils.isType(type,"Integer")){
			return 'number'
		}else if(Utils.isType(type,"BigInteger")){
			return 'number'
		}else if(Utils.isType(type,"Real")){
			return 'number'
		}else if(Utils.isType(type,"Double")){
			return 'number'
		}else if(Utils.isType(type,"Float")){
			return 'number'
		}else if(Utils.isType(type,"Date")){
			return 'Date'
		}else if(Utils.isType(type,"ByteArray")){
			return 'Buffer'
		}else{
			return 'string'
		}
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