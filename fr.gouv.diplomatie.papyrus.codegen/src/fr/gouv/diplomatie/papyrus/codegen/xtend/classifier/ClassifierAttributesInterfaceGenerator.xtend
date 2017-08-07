package fr.gouv.diplomatie.papyrus.codegen.xtend.classifier;

import org.eclipse.uml2.uml.Classifier
import org.eclipse.uml2.uml.Property

import fr.gouv.diplomatie.papyrus.codegen.xtend.utils.ClassifierUtils
import fr.gouv.diplomatie.papyrus.codegen.xtend.utils.PropertyUtils
import fr.gouv.diplomatie.papyrus.codegen.xtend.utils.Utils
import fr.gouv.diplomatie.papyrus.codegen.xtend.utils.TypeUtils
import fr.gouv.diplomatie.papyrus.codegen.xtend.utils.ModelUtils

public class ClassifierAttributesInterfaceGenerator {
	
	static def generateCode(Classifier clazz){
		'''
		«clazz.generateImports»
		
		export interface «ClassifierUtils.getAttributesInterfaceName(clazz)» «clazz.generateExtends»{
			«clazz.generateAttributes("")»
			«clazz.generateMultiValuedEntityAttributes("")»
			
			«clazz.generateNotPrimitiveTypeAttributes»
		}
		'''
	}
	
	/**
	 * génère les imports
	 */
	static def generateImports(Classifier clazz){
		'''
		«clazz.generateExtendsImports»
		«clazz.generateAttributesImports»
		'''
	}
	
	/**
	 * génère les imports lié aux extensions
	 */
	static def generateExtendsImports(Classifier clazz){
		val parents = clazz.generalizations
		if(parents.length > 0){
			'''«parents.fold("")[acc, parent |
				acc + '''import { «ClassifierUtils.getAttributesInterfaceName(parent.general)» } from "«ClassifierUtils.getAttributesInterfacePath(parent.general)»";
				'''
			]»'''
		}else{
			''''''
		}
	}
	
	/**
	 * génère les imports liés aux types des attributs
	 */
	 static def generateAttributesImports(Classifier clazz){
	 	val attributes = ClassifierUtils.getOwnedAttributes(clazz).filter[ attribut |
			(Utils.isEntity(attribut.type))
		]
		var types = newArrayList()
		for(attribut : attributes){
			if(!types.contains(attribut.type)){
				types.add(attribut.type)
			}
		}
		'''
		«types.fold("")[acc, type |
			acc +  '''
			import { «ClassifierUtils.getAttributesInterfaceName(type as Classifier)» } from "«ClassifierUtils.getAttributesInterfacePath(type as Classifier)»";
			'''
		]»
		'''
	 }
	
	/**
	 * génère les extends
	 */
	static def generateExtends(Classifier clazz){
		val parents = clazz.generalizations
		if(parents.length > 0){
			'''«parents.fold("")[acc, parent |
				if(acc != ""){
					acc + ''',«ClassifierUtils.getAttributesInterfaceName(parent.general)»'''
				}else{
					acc + '''extends «ClassifierUtils.getAttributesInterfaceName(parent.general)»'''
				}
			]»'''
		}else{
			''''''
		}
	}
	
	static def generateAttributes(Classifier clazz, String additionnalName){
		val attributes = ClassifierUtils.getNotMultivaluedOwnedAttributes(clazz)
		'''
		«attributes.fold("")[acc, attribut|
 			acc + '''
 			«attribut.generateAttribut(additionnalName)»'''	
 		]»
 		'''
	}
	
	static def generateAttribut(Property property, String additionnalName){
		if(PropertyUtils.isClassAttribute(property)){
			'''«property.generateClassAttribute(additionnalName)»'''
		}else{
			'''«property.generateBasicAttribute(additionnalName)»'''
		}
	}
	
	/**
	 * génère un attribut de l'interface
	 */
	static def generateBasicAttribute(Property property, String additionnalName){
		var name = additionnalName.addAdditionnalName(property.name)
		'''
		«name»?: «TypeUtils.getTypescriptType(property.type)»;
		'''
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
		val name = additionnalName.addAdditionnalName(property.name)
		val type = property.type
		if(type instanceof Classifier){
			return '''«type.generateAttributes(name)»
			'''
		}else{
			''''''
		}
	}
	
	/**
	 * génère un attribut de type entity
	 */
	static def generateEntityAttributes(Property property, String additonnalName){
		val type = property.type
		if(type instanceof Classifier){
			val ids = ClassifierUtils.getId(type)
			ids.fold("")[acc , id |
				acc + '''
				«property.generateEntityAttribute(id, additonnalName)»'''
			]
		}else{
			''''''
		}
		
	}
	
	/**
	 * génère un attribut de type entity
	 */
	static def generateEntityAttribute(Property property, Property id,  String additonnalName){
		val type = id.type
		val propName =  additonnalName.addAdditionnalName(id.name) + Utils.getFirstToUpperCase(property.name)
		'''
		«propName»?: «TypeUtils.getTypescriptType(type)»;
		'''
	}
	
	/**
	 * génère les attributs liés aux liens multivalués du model 
	 */
	static def generateMultiValuedEntityAttributes(Classifier clazz, String additionnalName){
		val model = clazz.model
		val classes = ModelUtils.getAllClasses(model)
		val references = newArrayList()
		for(classe : classes){
			references.addAll(ClassifierUtils.getMultivaluedReferencesToType(classe as Classifier, clazz))
		}
		val attributesEntity = ClassifierUtils.getOwnedAttributes(clazz).filter[attribut |
			Utils.isEntity(attribut.type) && attribut.multivalued
		]
		return
		'''
		«references.fold("")[acc, ref |
			acc + '''«ref.generateReferenceAttributes(clazz, additionnalName)»'''
		]»
		«attributesEntity.fold("")[acc, attribut |
			acc + '''«attribut.generateEntityIdsAttributes(additionnalName)»'''
		]»
		'''
	}
	
	/**
	 * génère l'attribut lié au lien multivalué du model 
	 */
	static def generateReferenceAttributes(Property property, Classifier clazz, String additionnalName){
		if(property.association !== null){
			'''«property.generateReferenceAttributesAssocation(clazz,additionnalName)»'''
		}else{
			'''«property.generateReferenceAttributesNA(clazz,additionnalName)»'''
		}
	}
	
	/**
	 *  génère l'attribut lié au lien multivalué du model  - chaques id
	 */
	static def generateReferenceAttributesAssocation(Property property, Classifier clazz, String additionnalName){
		val fromClass = property.association.ownedEnds.get(0).type
		if(fromClass instanceof Classifier){
			val ids = ClassifierUtils.getId(fromClass)
			'''«ids.fold("")[acc, id |
				acc + '''«property.generateReferenceAttributeAssocation(id, clazz, additionnalName)»'''
			]»'''
		}else {
			''''''
		}
	}
	
	/**
	 *  génère l'attribut lié au lien multivalué du model  - id
	 */
	static def generateReferenceAttributeAssocation(Property property, Property id, Classifier clazz, String additionnalName){
		var field =  property.association.ownedEnds.get(0)
		val name = id.name + Utils.getFirstToUpperCase(field.name)
		var fieldName = additionnalName.addAdditionnalName(name)
		'''
		«fieldName»?: «TypeUtils.getTypescriptType(id.type)»;
		'''
	}
	
	/**
	 * génère les attributs liés a une référence qui ne viens pas d'une association
	 */
	static def generateReferenceAttributesNA(Property property, Classifier clazz, String additionnalName){
		val fromClass = property.owner
		if(fromClass instanceof Classifier){
			val ids = ClassifierUtils.getId(fromClass)
			'''«ids.fold("")[acc, id |
				acc + '''«property.generateReferenceAttributeNA(id, clazz, additionnalName)»'''
			]»'''
		}
	}
	
	/**
	 * génère l'attribut lié a une référence qui ne viens pas d'une association
	 */
	static def generateReferenceAttributeNA(Property property, Property id, Classifier clazz, String additionnalName){
		val fromClass = property.owner
		if(fromClass instanceof Classifier){
			val name = property.name + Utils.getFirstToUpperCase(id.name)
			val fieldName = additionnalName.addAdditionnalName(name)
			'''
			«fieldName»?: «TypeUtils.getTypescriptType(property.type)»'''
		}
	}
	
	/**
	 * génère la déclaration des attributs id lié aux attribut de type entité multivalué
	 */
	static def generateEntityIdsAttributes(Property property, String additionnalName){
		val type = property.type
		if(type instanceof Classifier){
			val ids = ClassifierUtils.getId(type)
			'''
			«ids.fold("")[acc, id |
				acc + '''«property.generateEntityIdAttribute(id, additionnalName)»'''
			]»
			'''
		}
		
	}
	
	static def generateEntityIdAttribute(Property property, Property id, String additionnalName){
		val name = property.name + Utils.getFirstToUpperCase(id.name)
		'''
		«name»: Array<«TypeUtils.getTypescriptType(id.type)»>;
		'''
	}
	
	/**
	 * génère les attributs multivalués ou de type non primitifs
	 */
	static def generateNotPrimitiveTypeAttributes(Classifier clazz){
		val attributes = ClassifierUtils.getOwnedAttributes(clazz).filter[ attribut |
			(attribut.multivalued || Utils.isEntity(attribut.type) || (Utils.isValueObject(attribut.type) && attribut.multivalued))
		]
		'''
		«attributes.fold("")[acc, attribut|
 			acc + '''
 			«attribut.generateNotPrimitiveTypeAttribut()»'''	
 		]»
 		'''
	}
	
	static def generateNotPrimitiveTypeAttribut(Property property){
		if(Utils.isEntity(property.type)){
			'''«property.generateNPTEntityAttribut»'''
		}else if(Utils.isValueObject(property.type)){
			'''«property.generateNPTValueObjectAttribut»'''
		}else if(property.multivalued){
			'''
			«property.name»: Array<«TypeUtils.getTypescriptType(property.type)»>;
			
			'''
		}
	}
	
	static def generateNPTEntityAttribut(Property property){
		var name = "";
		if(property.association !== null && property.association.name!== null){
			name = property.association.name
		}else{
			name = "ref" + Utils.getFirstToUpperCase(property.name)
		}
		
		if(property.type instanceof Classifier){
			if(property.multivalued){
				'''
				«name»: Array<«ClassifierUtils.getAttributesInterfaceName(property.type as Classifier)»>;
				get«Utils.getFirstToUpperCase(name)»(): Promise<Array<«ClassifierUtils.getAttributesInterfaceName(property.type as Classifier)»>>;
				
				'''
			}else{
				'''
				«name»: «ClassifierUtils.getAttributesInterfaceName(property.type as Classifier)»;
				get«Utils.getFirstToUpperCase(name)»(): Promise<«ClassifierUtils.getAttributesInterfaceName(property.type as Classifier)»>;
				
				'''
			}
		}
	}
	
	static def generateNPTValueObjectAttribut(Property property){
		var name = "";
		if(property.association !== null && property.association.name!== null){
			name = property.association.name
		}else{
			name = "ref" + Utils.getFirstToUpperCase(property.name)
		}
		
		if(property.type instanceof Classifier){
			if(property.multivalued){
				'''
				«name»: Array<«ClassifierUtils.getAttributesInterfaceName(property.type as Classifier)»>;
				get«Utils.getFirstToUpperCase(name)»(): Promise<Array<«ClassifierUtils.getAttributesInterfaceName(property.type as Classifier)»>>;
				
				'''
			}
		}
	}
	
	/**
	 * si additionnalName est définit ajoute le nom additionnel devant le champs 
	 */
	static def addAdditionnalName(String additionnalName, String name){
		var newName =""
		if(additionnalName != "" && additionnalName !==null){
			newName = additionnalName + Utils.getFirstToUpperCase(name)
		}else{
			newName = name
		}
		
		return newName
	}
	
}