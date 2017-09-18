package fr.gouv.diplomatie.papyrus.codegen.core.utils

import org.eclipse.uml2.uml.Type;
import org.eclipse.uml2.uml.Classifier

class TypeUtils{
	
	/**
	 * retourne le type sequelize
	 */
	static def getSequelizeType(Type type){
		if(type.name == "Boolean"){
			return 'BOOLEAN'
		}else if(type.name == "Integer"){
			return 'INTEGER'
		}else if(type.name == "BigInteger" ){
			return 'BIGINT'
		}else if(type.name == "Real"){
			return 'FLOAT'
		}else if(type.name == "Double"){
			return 'DOUBLE'
		}else if(type.name == "Float"){
			return 'FLOAT'
		}else if(type.name == "Date"){
			return 'DATE'
		}else if(type.name == "ByteArray"){
			return 'BLOB'
		}else{
			return 'STRING'
		}
	}
	
	/**
	 * retourne le type  typescript
	 */
	static def getTypescriptType(Type type){
		if(type.name == "Boolean"){
			return 'boolean'
		}else if(type.name == "Integer"){
			return 'number'
		}else if(type.name == "BigInteger"){
			return 'number'
		}else if(type.name == "Real"){
			return 'number'
		}else if(type.name == "Double"){
			return 'number'
		}else if(type.name == "Float"){
			return 'number'
		}else if(type.name == "Date"){
			return 'string'
		}else if(type.name == "ByteArray"){
			return 'Buffer'
		}else{
			return 'string'
		}
	}
	
	/**
	 * retourne le type typescript utilisé dans les classes métier
	 */
	static def getMetierTypescriptType(Type type){
		if(type.name == "Boolean"){
			return 'boolean'
		}else if(type.name == "Integer"){
			return 'number'
		}else if(type.name == "BigInteger"){
			return 'number'
		}else if(type.name == "Real"){
			return 'number'
		}else if(type.name == "Double"){
			return 'number'
		}else if(type.name == "Float"){
			return 'number'
		}else if(type.name == "Date"){
			return 'Date'
		}else if(type.name == "ByteArray"){
			return 'Buffer'
		}else{
			return 'string'
		}
	}
	
	/**
	 * retourne le type dans la base de donnée
	 */
	static def getDatabaseType(Type type){
		if(type.name == "Boolean"){
			return 'boolean'
		}else if(type.name == "Integer"){
			return 'integer'
		}else if(type.name == "BigInteger"){
			return 'bigint'
		}else if(type.name == "Real"){
			return 'real'
		}else if(type.name == "Double"){
			return 'double precision'
		}else if(type.name == "Float"){
			return 'decimal'
		}else if(type.name == "Date"){
			return 'timestamp'
		}else if(type.name == "ByteArray"){
			return 'bytea'
		}else {
			return 'character'
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