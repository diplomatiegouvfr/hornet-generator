package fr.gouv.diplomatie.papyrus.codegen.xtend.classifier;

import org.eclipse.uml2.uml.Classifier
import org.eclipse.uml2.uml.Property
import fr.gouv.diplomatie.papyrus.codegen.xtend.utils.ClassifierUtils
import fr.gouv.diplomatie.papyrus.codegen.xtend.utils.Utils
import fr.gouv.diplomatie.papyrus.codegen.xtend.utils.PropertyUtils
import fr.gouv.diplomatie.papyrus.codegen.xtend.utils.TypeUtils
import org.eclipse.uml2.uml.Type
import java.util.ArrayList
import org.eclipse.uml2.uml.AssociationClass

public class ClassifierMetierClassGenerator {
	
	static def generateCode(Classifier clazz){
		'''
		import Bean from "hornet-js-bean/src/decorators/Bean";
		import Map from "hornet-js-bean/src/decorators/Map";
		
		«clazz.generateImports»
		
		@Bean
		export class «ClassifierUtils.getMetierClassName(clazz)» «clazz.generateExtends»{
			«clazz.generateAttributes("")»
			«IF Utils.isEntity(clazz)»«clazz.generateAssociationsAttributes»«ENDIF»
			
		}
		'''
	}
	
	/**
	 * génère les imports
	 */
	static def generateImports(Classifier clazz){
		val attributesTypes = clazz.generateAttributesImports(newArrayList(), clazz)
		'''
		«clazz.generateExtendsImports»
		«attributesTypes.fold("")[acc, type |
			acc +  '''
			import { «ClassifierUtils.getMetierClassName(type as Classifier)» } from "«ClassifierUtils.getMetierClassPath(type as Classifier)»";
			'''
		]»
		'''
	}
	
	/**
	 * génère les imports lié aux extensions
	 */
	static def generateExtendsImports(Classifier clazz){
		val parents = clazz.generalizations
		if(parents.length > 0){
			'''«parents.fold("")[acc, parent |
				acc + '''import { «ClassifierUtils.getMetierClassName(parent.general)» } from "«ClassifierUtils.getMetierClassPath(parent.general)»";
				'''
			]»'''
		}else{
			''''''
		}
	}

	
	static def generateAssociationImport(AssociationClass clazz, Classifier fromClass){
		val members = clazz.ownedEnds.filter[member |
			member.type != fromClass
		]
		'''
		«members.fold("")[acc, member |
			val type = member.type
			if(type instanceof Classifier){
				acc + '''
				import { «ClassifierUtils.getMetierClassName(type as Classifier)» } from "«ClassifierUtils.getMetierClassPath(type as Classifier)»";
				'''
			}
		]»
		'''
	}
	
	/**
	 * génère les imports liés aux types des attributs
	 */
	 static def generateAttributesImports(Classifier clazz, ArrayList<Type> types, Classifier fromClass){
	 	val attributes = ClassifierUtils.getOwnedAttributes(clazz).filter[ attribut |
			(Utils.isEntity(attribut.type))
		]
		val attributesValueObject = ClassifierUtils.getOwnedAttributes(clazz).filter[ attribut |
			(Utils.isValueObject(attribut.type))
		]
		for(attribut : attributes){
			if(!types.contains(attribut.type)){
				types.add(attribut.type)
			}
		}
		/*attributesValueObject.forEach[attribut |
			var type = attribut.type
			if(type instanceof Classifier){
				type.generateAttributesImports(types, fromClass)
			}
		]*/
		
		for(attribut : attributesValueObject){
			if(!types.contains(attribut.type)){
				types.add(attribut.type)
			}
		}
		
		/* ajout des import pour les classes d'association*/ 
		if(Utils.isEntity(fromClass)){
			val associationsClasses = ClassifierUtils.getLinkedAssociationClass(clazz)
			
			associationsClasses.forEach[ asso |
				if(asso instanceof AssociationClass){
					val members = asso.ownedEnds.filter[member |
						return member.type != fromClass
					]
					members.forEach[member |
						val type = member.type
						if(type instanceof Classifier && !types.contains(type)){
							types.add(type)
						}
					]
				}
			]
		}
		return types
	 }
	 
	 /**
	 * génère les extends
	 */
	static def generateExtends(Classifier clazz){
		val parents = clazz.generalizations
		if(parents.length > 0){
			'''«parents.fold("")[acc, parent |
				if(acc != ""){
					acc + ''',«ClassifierUtils.getMetierClassName(parent.general)»'''
				}else{
					acc + '''extends «ClassifierUtils.getMetierClassName(parent.general)»'''
				}
			]»'''
		}else{
			''''''
		}
	}
	 
	 /**
	 * génère les attributs simples de la classe
	 */
	static def generateAttributes(Classifier clazz, String additionnalName){
		val attributes = ClassifierUtils.getOwnedAttributes(clazz)
		'''
		«attributes.fold("")[acc, attribut|
 			acc + '''
 			«attribut.generateAttribut(additionnalName)»'''	
 		]»
 		'''
	}
	
	/**
	 * génère un attribut de la classe
	 */
	static def generateAttribut(Property property, String additionnalName){
		if(PropertyUtils.isClassAttribute(property)){
			'''«property.generateClassAttribute(additionnalName)»'''
		}else{
			'''«property.generateBasicAttribute(additionnalName)»'''
		}
	}
	
	/**
	 * génère un attribut lié a la classe
	 */
	static def generateClassAttribute(Property property, String additionnalName){
		if(Utils.isValueObject(property.type)){
			'''«property.generateValueObjectAttribute(additionnalName)»'''
		}else{
			'''«property.generateEntityAttributes(additionnalName)»'''
		}
	}
	
	/**
	 * génère un attribut de type value Object
	 */
	static def generateValueObjectAttribute(Property property, String additionnalName){
		/*val name = Utils.addAdditionnalName(additionnalName, property.name)
		val type = property.type
		if(type instanceof Classifier){
			if(!property.multivalued){
				return '''«type.generateAttributes(name)»
				'''
			}
		}else{
			''''''
		}*/
		val type = property.type
		if(type instanceof Classifier){
			val propName = Utils.addAdditionnalName(additionnalName, property.name)
			'''
			
			«Utils.generateComments(property)»
			@Map(«ClassifierUtils.getMetierClassName(type)»)
			«propName»: «ClassifierUtils.getMetierClassName(type)»;
			'''
		}
	}
	
	/**
	 * génère un attribut de type entity
	 */
	static def generateEntityAttributes(Property property, String additonnalName){
		val type = property.type
		
		if(type instanceof Classifier){
			val propName =  Utils.addAdditionnalName(additonnalName, property.name)
			var alias = "ref" + Utils.getFirstToUpperCase(propName)
			if(property.association !== null && property.association.name !== null){
				alias = property.association.name
			}
			var array =""
			var endArray =""
			if(property.isMultivalued){
				array= "Array<"
				endArray =">"
			}
			'''
			
			«Utils.generateComments(property)»
			@Map(«ClassifierUtils.getMetierClassName(type)»)
			«propName»: «array»«ClassifierUtils.getMetierClassName(type)»«endArray»;
			'''
		}else{
			''''''
		}
		
	}
	
	/**
	 * génère un attribut de l'interface
	 */
	static def generateBasicAttribute(Property property, String additionnalName){
		var name = Utils.addAdditionnalName(additionnalName, property.name)
		var array =""
		var endArray =""
		if(property.isMultivalued){
			array= "Array<"
			endArray =">"
		}
		'''
		
		«Utils.generateComments(property)»
		@Map()
		«name»: «array»«TypeUtils.getMetierTypescriptType(property.type)»«endArray»;
		'''
	}
	
	/**
	 * génère les attributs issus des associations
	 */
	static def generateAssociationsAttributes(Classifier clazz){
		val associationsClasses = ClassifierUtils.getLinkedAssociationClass(clazz)
		
		'''
		«associationsClasses.fold("")[acc, asso |
			acc + '''«(asso as AssociationClass).generateAssociationAttributes(clazz)»'''
		]»
		'''
	}
	
	static def generateAssociationAttributes(AssociationClass clazz, Classifier fromClass){
		val members = clazz.ownedEnds.filter[member |
			member.type != fromClass
		]
		'''
		«members.fold("")[acc, member |
			val type = member.type
			if(type instanceof Classifier){
				acc + 
				'''
				
				@Map(«ClassifierUtils.getMetierClassName(type)»)
				«Utils.getFirstToLowerCase(clazz.name)»: Array<«ClassifierUtils.getMetierClassName(type)»>;
				'''
			}
		]»
		'''
	}
	
}