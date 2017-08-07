package fr.gouv.diplomatie.papyrus.codegen.xtend.utils

import org.eclipse.uml2.uml.Type;

class TypeUtils{
	
	static def getSequelizeType(Type type){
		if(type.name == "Boolean" || type.name == "EBoolean"){
			return 'BOOLEAN'
		}else if(type.name == "Integer"){
			return 'INTEGER'
		}else if(type.name == "BigInterger" || type.name == "EBigInteger"){
			return 'BIGINT'
		}else if(type.name == "Real"){
			return 'FLOAT'
		}else if(type.name == "EDouble"){
			return 'DOUBLE'
		}else if(type.name == "EFloat"){
			return 'FLOAT'
		}else if(type.name == "EDate" || type.name == "Date"){
			return 'DATE'
		}else{
			return 'STRING'
		}
	}
	
	static def getTypescriptType(Type type){
		if(type.name == "Boolean" || type.name == "EBoolean"){
			return 'boolean'
		}else if(type.name == "Integer"){
			return 'number'
		}else if(type.name == "BigInterger" || type.name == "EBigInteger"){
			return 'number'
		}else if(type.name == "Real"){
			return 'number'
		}else if(type.name == "EDouble"){
			return 'number'
		}else if(type.name == "EFloat"){
			return 'number'
		}else if(type.name == "EDate" || type.name == "Date"){
			return 'string'
		}else{
			return 'string'
		}
	}
}