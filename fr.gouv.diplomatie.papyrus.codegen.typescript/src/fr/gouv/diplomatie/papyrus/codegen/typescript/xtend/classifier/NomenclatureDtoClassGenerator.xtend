package fr.gouv.diplomatie.papyrus.codegen.typescript.xtend.classifier;

import org.eclipse.uml2.uml.Classifier
import fr.gouv.diplomatie.papyrus.codegen.core.utils.ClassifierUtils
import fr.gouv.diplomatie.papyrus.codegen.typescript.utils.TypeUtils

public class NomenclatureDtoClassGenerator{
	
	static def generateCode(Classifier clazz){
		'''
		import Alias from "hornet-js-bean/src/decorators/Alias";
		import Bean from "hornet-js-bean/src/decorators/Bean";
		import Map from "hornet-js-bean/src/decorators/Map";
		
		@Bean
		export class «ClassifierUtils.getDtoClassName(clazz)»{
			@Map()
			code: «TypeUtils.getEnumTypescriptType(clazz)»;
			
			@Map()
			libelle: string;
		}
		'''
	}
}