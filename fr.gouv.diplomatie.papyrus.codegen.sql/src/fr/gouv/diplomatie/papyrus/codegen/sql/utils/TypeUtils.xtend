package fr.gouv.diplomatie.papyrus.codegen.sql.utils

import org.eclipse.uml2.uml.Type;
import org.eclipse.uml2.uml.Classifier
import fr.gouv.diplomatie.papyrus.codegen.core.utils.Utils

class TypeUtils{
	
	public static var MODEL_HORNETTYPE_DATABASETYPE = 'databaseType';

	/**
	 * retourne le type dans la base de donnée
	 */
	static def getDatabaseType(Type type){
		val databaseType = Utils.getStereotypePropertyValue(type,Utils.MODEL_HORNETTYPE, MODEL_HORNETTYPE_DATABASETYPE);
		if(databaseType === null || databaseType == ""){
			return "character"
		}
		return databaseType
	}
	
	/**
	 * retourne le type du code de l'enum
	 */
	static def getEnumType(Classifier type){
		return "integer"
	}
	
}