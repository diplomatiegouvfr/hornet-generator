package fr.gouv.diplomatie.papyrus.codegen.sql.utils

import org.eclipse.uml2.uml.Type;
import org.eclipse.uml2.uml.Classifier

class TypeUtils{

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
	
}