package fr.gouv.diplomatie.papyrus.codegen.xtend.utils

import org.eclipse.uml2.uml.Model
import org.eclipse.uml2.uml.Class

class ModelUtils{
	
	/**
	 * retourne les classes du modele
	 */
	static def getAllClasses(Model model){
		val classes = model.getOwnedTypes().filter[type|
			type instanceof Class	
		]
		return classes
	}
}