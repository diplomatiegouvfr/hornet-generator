package fr.gouv.diplomatie.papyrus.codegen.typescript.xtend.classifier;

import org.eclipse.uml2.uml.Classifier
import org.eclipse.uml2.uml.Property
import fr.gouv.diplomatie.papyrus.codegen.core.utils.Utils
import fr.gouv.diplomatie.papyrus.codegen.core.utils.ClassifierUtils
import org.eclipse.uml2.uml.NamedElement

public class  NomenclatureEnumClassGenerator{
	
	static def generateCode(Classifier clazz){
		'''
		«Utils.generateComments(clazz)»
		export enum «Utils.getFirstToUpperCase(clazz.name)»{
			«clazz.generateAttributes»
		}
		'''
	}
	
	/**
	 * génère les attribut de l'enum
	 */
	static def generateAttributes(Classifier clazz){
		val valeurs = ClassifierUtils.getOwnedAttributes(clazz)
		'''
		«valeurs.fold("")[ acc, valeur |
			if(acc != ""){
				acc + ''',
				''' + '''«valeur.generateValue»'''
			}else{
				acc + '''«valeur.generateValue»'''					
			}
		]»
		'''
	}
	
	static def generateValue(Property value){
		val code = Utils.getStereotypePropertyValue(value as NamedElement, Utils.MODEL_CODELIBELLENOMENCLATURE, Utils.MODEL_CODELIBELLENOMENCLATURE_CODE)
		val libelle = Utils.getStereotypePropertyValue(value as NamedElement, Utils.MODEL_CODELIBELLENOMENCLATURE, Utils.MODEL_CODELIBELLENOMENCLATURE_LIBELLE)
		if(code !== null){
			'''«libelle» = «code»'''
		}else{
			'''«libelle»'''
		}
	}
}