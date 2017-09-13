package fr.gouv.diplomatie.papyrus.codegen.xtend.classifier;

import org.eclipse.uml2.uml.Classifier
import fr.gouv.diplomatie.papyrus.codegen.xtend.utils.ClassifierUtils

public class NomenclatureAttributesClassGenerator{
	
	static def generateCode(Classifier clazz){
		'''
		
		export class «ClassifierUtils.getAttributesInterfaceName(clazz)»{
			
			code?: number;
			libelle?: string;
			
		}
		'''
	}
	
}