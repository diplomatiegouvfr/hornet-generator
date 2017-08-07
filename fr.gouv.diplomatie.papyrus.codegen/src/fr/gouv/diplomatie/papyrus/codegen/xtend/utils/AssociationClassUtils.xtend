package fr.gouv.diplomatie.papyrus.codegen.xtend.utils

import org.eclipse.uml2.uml.AssociationClass

class AssociationClassUtils{
	
	/**
	 * retourne le nom de la classe model
	 */
	static def getModelName(AssociationClass clazz){
		return clazz.name + "Model"
	}
	
}