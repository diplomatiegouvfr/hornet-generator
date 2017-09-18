package fr.gouv.diplomatie.papyrus.codegen.sql.utils

import org.eclipse.uml2.uml.Type;
import org.eclipse.uml2.uml.Classifier
import fr.gouv.diplomatie.papyrus.codegen.core.utils.Utils

class TypeUtils{

	/**
	 * retourne le type dans la base de donnée
	 */
	static def getDatabaseType(Type type){
		if(Utils.isType(type,"Boolean")){
			return 'boolean'
		}else if(Utils.isType(type,"Integer")){
			return 'integer'
		}else if(Utils.isType(type,"BigInteger")){
			return 'bigint'
		}else if(Utils.isType(type,"Real")){
			return 'real'
		}else if(Utils.isType(type,"Double")){
			return 'double precision'
		}else if(Utils.isType(type,"Float")){
			return 'decimal'
		}else if(Utils.isType(type,"Date")){
			return 'timestamp'
		}else if(Utils.isType(type,"ByteArray")){
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