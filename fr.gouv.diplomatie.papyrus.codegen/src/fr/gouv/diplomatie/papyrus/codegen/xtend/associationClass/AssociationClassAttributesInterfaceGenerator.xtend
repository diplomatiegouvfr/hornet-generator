package fr.gouv.diplomatie.papyrus.codegen.xtend.associationClass

import org.eclipse.uml2.uml.AssociationClass
import org.eclipse.uml2.uml.Property

import fr.gouv.diplomatie.papyrus.codegen.xtend.utils.ClassifierUtils
import fr.gouv.diplomatie.papyrus.codegen.xtend.utils.Utils
import org.eclipse.uml2.uml.Classifier
import fr.gouv.diplomatie.papyrus.codegen.xtend.utils.TypeUtils
import org.eclipse.uml2.uml.Type
import java.util.ArrayList

public class AssociationClassAttributesInterfaceGenerator{
	
	static def generateCode(AssociationClass clazz){
		'''
		«clazz.generateImports»
		
		export interface «ClassifierUtils.getAttributesInterfaceName(clazz)» {
			«clazz.generateAttributes("")»
		}
		'''
	}
	
	/**
	 * génère les imports
	 */
	static def generateImports(AssociationClass clazz){
		val attributesTypes = clazz.generateAttributesImports(newArrayList())
		'''
		«attributesTypes.fold("")[acc, type |
			acc +  '''
			import { «ClassifierUtils.getAttributesInterfaceName(type as Classifier)» } from "«ClassifierUtils.getAttributesInterfacePath(type as Classifier)»";
			'''
		]»
		'''
	}
	
	/**
	 * génère les imports liés aux types des attributs
	 */
	 static  def Type [] generateAttributesImports(Classifier clazz, ArrayList<Type> types){
	 			var attributes = ClassifierUtils.getOwnedAttributes(clazz).filter[ attribut |
			(Utils.isEntity(attribut.type))
		]
		
		var attributesValueObject = ClassifierUtils.getOwnedAttributes(clazz).filter[ attribut |
			(Utils.isValueObject(attribut.type))
		]
		
		var attributesEnums = ClassifierUtils.getOwnedAttributes(clazz).filter[ attribut |
			(Utils.isNomenclature(attribut.type))
		]
		
		if(clazz instanceof AssociationClass){
			attributes = clazz.ownedEnds.filter[ attribut |
				(Utils.isEntity(attribut.type))
			]
			
			attributesValueObject = clazz.ownedEnds.filter[ attribut |
				(Utils.isValueObject(attribut.type))
			]
			
			attributesEnums = clazz.ownedEnds.filter[ attribut |
			(Utils.isNomenclature(attribut.type))
			]
		}
		
		for(attribut : attributes){
			if(!types.contains(attribut.type)){
				types.add(attribut.type)
			}
		}
		
		for(attribut : attributesEnums){
			if(!types.contains(attribut.type)){
				types.add(attribut.type)
			}
		}
		
		attributesValueObject.forEach[attribut |
			var type = attribut.type
			if(type instanceof Classifier){
				type.generateAttributesImports(types)
			}
		]
		return types
	 }
	 
	 static def generateAttributes(Classifier clazz, String additionnalName){
	 	var attributes = ClassifierUtils.getOwnedAttributes(clazz)
	 	if(clazz instanceof AssociationClass){
	 		attributes = clazz.memberEnds
	 	}
	 	'''
	 	«attributes.fold("")[acc, attribut |
	 		acc + '''«attribut.generatePropertyAttributes(additionnalName)»'''
	 	]»
	 	'''
	 }
	 
	 static def generatePropertyAttributes(Property property, String additionnalName){
	 	val type = property.type
	 	val name = Utils.addAdditionnalName(additionnalName, property.name)
	 	if(type instanceof Classifier){
		 	if(Utils.isEntity(type)){
		 		val ids = ClassifierUtils.getId(type)
		 		var array = ""
		 		var endArray = ""
		 		if(property.multivalued){
		 			array = "Array<"
		 			endArray = ">"
		 		}
		 		'''
		 		
		 		«ids.fold("")[acc, id |
		 			acc + '''«property.generatePropertyIdAttribute(id)»'''
		 		]»
		 		«Utils.getFirstToLowerCase(name)»: «array»«ClassifierUtils.getAttributesInterfaceName(type)»«endArray»;
		 		get«Utils.getFirstToUpperCase(name)»(): Promise<«array»«ClassifierUtils.getAttributesInterfaceName(type)»«endArray»>;
		 		'''
		 	}else if (Utils.isValueObject(type)){
		 		'''
		 		«type.generateAttributes(name)»
		 		'''
		 	}else if(Utils.isNomenclature(type)){
		 		var array = ""
		 		var endArray = ""
		 		if(property.multivalued){
		 			array = "Array<"
		 			endArray = ">"
		 		}
		 		val codeName = Utils.addAdditionnalName(additionnalName, "code" + Utils.getFirstToUpperCase(property.name)) 
		 		'''
		 		
		 		«codeName»: number;
		 		«Utils.getFirstToLowerCase(name)»: «array»«ClassifierUtils.getAttributesInterfaceName(type)»«endArray»;
		 		get«Utils.getFirstToUpperCase(name)»(): Promise<«array»«ClassifierUtils.getAttributesInterfaceName(type)»«endArray»>;
		 		'''
		 	}else{
		 		'''«property.generateBasicAttribute(name)»'''
		 	}
	 	}
	 }
	 
	/**
	 * génère un attribut de l'interface
	 */
	static def generateBasicAttribute(Property property, String additionnalName){
		var name = Utils.addAdditionnalName(additionnalName, property.name)
		'''
		«name»?: «TypeUtils.getTypescriptType(property.type)»;
		'''
	}
	 
	 static def generatePropertyIdAttribute(Property property, Property id){
	 	val name = id.name + Utils.getFirstToUpperCase(property.name)
	 	'''
	 	«name»: «TypeUtils.getTypescriptType(id.type)»;
	 	'''
	 }
	
}