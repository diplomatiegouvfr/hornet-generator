package fr.gouv.diplomatie.papyrus.codegen.core.utils

import org.eclipse.uml2.uml.Type;
import org.eclipse.uml2.uml.Classifier

class TypeUtils{
	
	/**
	 * retourne le type sequelize
	 */
	static def getSequelizeType(Type type){
		if(type.name == "Boolean" || type.name == "EBoolean"){
			return 'BOOLEAN'
		}else if(type.name == "Integer"){
			return 'INTEGER'
		}else if(type.name == "BigInteger" || type.name == "EBigInteger"){
			return 'BIGINT'
		}else if(type.name == "Real"){
			return 'FLOAT'
		}else if(type.name == "EDouble"){
			return 'DOUBLE'
		}else if(type.name == "EFloat"){
			return 'FLOAT'
		}else if(type.name == "EDate" || type.name == "Date"){
			return 'DATE'
		}else if(type.name == "EByteArray"){
			return 'BLOB'
		}else{
			return 'STRING'
		}
	}
	
	/**
	 * retourne le type  typescript
	 */
	static def getTypescriptType(Type type){
		if(type.name == "Boolean" || type.name == "EBoolean"){
			return 'boolean'
		}else if(type.name == "Integer"){
			return 'number'
		}else if(type.name == "BigInteger" || type.name == "EBigInteger"){
			return 'number'
		}else if(type.name == "Real"){
			return 'number'
		}else if(type.name == "EDouble"){
			return 'number'
		}else if(type.name == "EFloat"){
			return 'number'
		}else if(type.name == "EDate" || type.name == "Date"){
			return 'string'
		}else if(type.name == "EByteArray"){
			return 'Buffer'
		}else{
			return 'string'
		}
	}
	
	/**
	 * retourne le type typescript utilisé dans les classes métier
	 */
	static def getMetierTypescriptType(Type type){
		if(type.name == "Boolean" || type.name == "EBoolean"){
			return 'boolean'
		}else if(type.name == "Integer"){
			return 'number'
		}else if(type.name == "BigInteger" || type.name == "EBigInteger"){
			return 'number'
		}else if(type.name == "Real"){
			return 'number'
		}else if(type.name == "EDouble"){
			return 'number'
		}else if(type.name == "EFloat"){
			return 'number'
		}else if(type.name == "EDate" || type.name == "Date"){
			return 'Date'
		}else if(type.name == "EByteArray"){
			return 'Buffer'
		}else{
			return 'string'
		}
	}
	
	/**
	 * retourne le type dans la base de donnée
	 */
	static def getDatabaseType(Type type){
		if(type.name == "Boolean" || type.name == "EBoolean"){
			return 'boolean'
		}else if(type.name == "Integer"){
			return 'integer'
		}else if(type.name == "BigInteger" || type.name == "EBigInteger"){
			return 'bigint'
		}else if(type.name == "Real"){
			return 'real'
		}else if(type.name == "EDouble"){
			return 'double precision'
		}else if(type.name == "EFloat"){
			return 'decimal'
		}else if(type.name == "EDate" || type.name == "Date"){
			return 'timestamp'
		}else if(type.name == "EByteArray"){
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