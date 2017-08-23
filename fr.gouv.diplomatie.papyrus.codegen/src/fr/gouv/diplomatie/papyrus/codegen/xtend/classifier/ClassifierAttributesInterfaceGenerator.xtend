package fr.gouv.diplomatie.papyrus.codegen.xtend.classifier;

import org.eclipse.uml2.uml.Classifier
import org.eclipse.uml2.uml.Property

import fr.gouv.diplomatie.papyrus.codegen.xtend.utils.ClassifierUtils
import fr.gouv.diplomatie.papyrus.codegen.xtend.utils.PropertyUtils
import fr.gouv.diplomatie.papyrus.codegen.xtend.utils.Utils
import fr.gouv.diplomatie.papyrus.codegen.xtend.utils.TypeUtils
import fr.gouv.diplomatie.papyrus.codegen.xtend.utils.ModelUtils
import org.eclipse.uml2.uml.Type
import java.util.ArrayList
import org.eclipse.uml2.uml.AssociationClass

public class ClassifierAttributesInterfaceGenerator {
	
	static def generateCode(Classifier clazz){
		//«clazz.generateExtendsAttributes»
		'''
		«clazz.generateImports»
		
		export interface «ClassifierUtils.getAttributesInterfaceName(clazz)» «clazz.generateExtends»{
			
			«clazz.generateAttributes("")»
			««««clazz.generateMultiValuedEntityAttributes("")»
			
			«clazz.generateNotPrimitiveTypeAttributes("")»
			«IF Utils.isEntity(clazz)»«clazz.generateAllAssociationClassAtributes()»«ENDIF»
		}
		'''
	}
	
	/**
	 * génère les imports
	 */
	static def generateImports(Classifier clazz){
		val attributesTypes = clazz.generateAttributesImports(newArrayList())
		'''
		«clazz.generateExtendsImports»
		«attributesTypes.fold("")[acc, type |
			acc +  '''
			import { «ClassifierUtils.getAttributesInterfaceName(type as Classifier)» } from "«ClassifierUtils.getAttributesInterfacePath(type as Classifier)»";
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
	 static def generateAttributesImports(Classifier clazz, ArrayList<Type> types){
	 	val attributes = ClassifierUtils.getOwnedAttributes(clazz).filter[ attribut |
			(Utils.isEntity(attribut.type))
		]
		
		val attributesEnums = ClassifierUtils.getOwnedAttributes(clazz).filter[ attribut |
			(Utils.isNomenclature(attribut.type))
		]
		
		val attributesValueObject = ClassifierUtils.getOwnedAttributes(clazz).filter[ attribut |
			(Utils.isValueObject(attribut.type))
		]
		
		val interfaces = clazz.allRealizedInterfaces
		
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
		
		for(interface : interfaces){
			if(!types.contains(interface)){
				types.add(interface)
			}
		}
		
		if(Utils.isEntity(clazz)){
			val assosiationClasses = ClassifierUtils.getLinkedAssociationClass(clazz)
			
			assosiationClasses.forEach[asso |
				val members = (asso as AssociationClass).ownedEnds.filter[end |
					end.type != clazz
				]
				members.forEach[member |
					val type = member.type
					if(!types.contains(type)){
						types.add(type)
					}
				]	
			]
		}
	
		attributesValueObject.forEach[attribut |
			var type = attribut.type
			if(type instanceof Classifier){
				type.generateAttributesImports(types)
			}
			if(!types.contains(type)){
				types.add(type)
			}
		]
		return types
	 }
	
	/**
	 * génère les extends
	 */
	static def generateExtends(Classifier clazz){
		val parents = clazz.generalizations
		val interfaces = clazz.directlyRealizedInterfaces
		val extendsInterfaces = '''«interfaces.fold("")[acc, interface |
			if(acc != ""){
				acc + ''', «ClassifierUtils.getAttributesInterfaceName(interface)»''' 
			}else{
				acc + '''«ClassifierUtils.getAttributesInterfaceName(interface)»''' 
			}
		]»'''
		if(parents.length > 0){
			val parent = parents.get(0)
			var separator = ""
			if(interfaces !== null && !interfaces.empty){
				separator = ","
			}
			'''extends «ClassifierUtils.getAttributesInterfaceName(parent.general)»«separator» «extendsInterfaces»'''
		}else if(interfaces !== null && !interfaces.empty){
			'''extends «extendsInterfaces»'''
		}
	}
	
	static def generateExtendsAttributes(Classifier clazz){
		val parents = clazz.generalizations
		
		if(parents !== null && !parents.empty){
			'''«parents.fold("")[acc, parent |
				acc + 
				'''
				«Utils.getFirstToLowerCase(parent.general.name)»?: «ClassifierUtils.getAttributesInterfaceName(parent.general)»;
				'''
			]»'''
		}else{
			''''''
		}
	}
	
	
	/**
	 * génère les attributs simples de la classe
	 */
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
		var name = Utils.addAdditionnalName(additionnalName, property.name)
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
		}else if(Utils.isNomenclature(property.type)){
			'''«property.generateEnum(additionnalName)»'''
		}else{
			'''«property.generateEntityAttributes(additionnalName)»'''
		}
	}
	
	/**
	 * génère un attribut de type value Object
	 */
	static def generateValueObjectAttribute(Property property, String additionnalName){
		val name = Utils.addAdditionnalName(additionnalName, property.name)
		val type = property.type
		if(type instanceof Classifier){
			return '''«type.generateAttributes(name)»
			'''
		}else{
			''''''
		}
	}
	
	/**
	 * génère une classe enum
	 */
	static def generateEnum(Property property, String additonnalName){
		val propName =  Utils.addAdditionnalName(additonnalName, property.name) 
		val type = property.type
		'''
		«propName»?: «ClassifierUtils.getAttributesInterfaceName(type as Classifier)»;
		'''
	}
	
	/**
	 * génère un attribut de type entity
	 */
	static def generateEntityAttributes(Property property, String additonnalName){
		val type = property.type
		if(type instanceof Classifier){
			val ids = ClassifierUtils.getId(type)
			'''
			«ids.fold("")[acc , id |
				acc + '''
				«property.generateEntityAttribute(id, additonnalName)»'''
			]»
			'''
		}else{
			''''''
		}
		
	}
	
	/**
	 * génère un attribut de type entity
	 */
	static def generateEntityAttribute(Property property, Property id,  String additonnalName){
		val type = id.type
		val propName =  Utils.addAdditionnalName(additonnalName, id.name) + Utils.getFirstToUpperCase(property.name)
		
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
		val field =  property.association.ownedEnds.get(0)
		val name = id.name + Utils.getFirstToUpperCase(field.name)
		var fieldName = Utils.addAdditionnalName(additionnalName, name)
		val refName = field.name
		var array = ""
		var endArray = ""
		if(field.isMultivalued){
			array = "Array<"
			endArray =">"
		}
		val type = field.type
		if(type instanceof Classifier){
			'''
			
			«fieldName»?: «TypeUtils.getTypescriptType(id.type)»;
			«refName»: «array»«ClassifierUtils.getAttributesInterfaceName(type)»«endArray»;
			get«Utils.getFirstToUpperCase(refName)»(): Promise<«array»«ClassifierUtils.getAttributesInterfaceName(type)»«endArray»>;
			'''
		}
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
			val fieldName = Utils.addAdditionnalName(additionnalName, name)
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
	static def generateNotPrimitiveTypeAttributes(Classifier clazz, String additionnalName){
		val attributes = ClassifierUtils.getOwnedAttributes(clazz).filter[ attribut |
			(attribut.multivalued || Utils.isEntity(attribut.type) || Utils.isValueObject(attribut.type))
		]
		'''
		«attributes.fold("")[acc, attribut|
 			acc + '''
 			«attribut.generateNotPrimitiveTypeAttribut(additionnalName)»'''	
 		]»
 		'''
	}
	
	static def generateNotPrimitiveTypeAttribut(Property property, String additionnalName){
		if(Utils.isEntity(property.type)){
			'''«property.generateNPTEntityAttribut(additionnalName)»'''
		}else if(Utils.isValueObject(property.type)){
			'''«property.generateNPTValueObjectAttribut(additionnalName)»'''
		}else if(property.multivalued){
			var name = Utils.addAdditionnalName(additionnalName, property.name)
			if(Utils.isNomenclature(property.type)){
				'''
				«name»: Array<«ClassifierUtils.getAttributesInterfaceName(property.type as Classifier)»>;
				
				'''
			}else{
				'''
				«name»: Array<«TypeUtils.getTypescriptType(property.type)»>;
				
				'''
			}
		}
	}
	
	static def generateNPTEntityAttribut(Property property, String additionnalName){
		var name = "";
		/* 
		if(property.association !== null && property.association.name!== null){
			name = property.association.name
		}else{*/
			name = Utils.addAdditionnalName(additionnalName,property.name)
		//}
		
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
	
	static def generateNPTValueObjectAttribut(Property property, String additionnalName){
		val type = property.type
		if(type instanceof Classifier){
			val name = Utils.addAdditionnalName(additionnalName, property.name)
			var array = ""
			var endArray = ""
			if(property.isMultivalued){
				array = "Array<"
				endArray =">"
			}
			'''
			«type.generateNotPrimitiveTypeAttributes(name)»
			«property.name» : «array»«ClassifierUtils.getAttributesInterfaceName(type)»«endArray»;
			get«Utils.getFirstToUpperCase(property.name)»(): «array»«ClassifierUtils.getAttributesInterfaceName(type)»«endArray»;
			
			'''
			
		}
		
	}
	
	/**
	 * génère les attributs liés aux classe d'association
	 */
	static def generateAllAssociationClassAtributes(Classifier clazz){
		val assosiationClasses = ClassifierUtils.getLinkedAssociationClass(clazz)
		'''
		«assosiationClasses.fold("")[acc, asso |
			acc+ '''«(asso as AssociationClass).generateAssociationClassAtributes(clazz)»'''
		]»'''
	}
	
	static def generateAssociationClassAtributes(AssociationClass clazz, Classifier fromClass){
		val members = clazz.ownedEnds.filter[end |
			end.type != fromClass
		]
		val member = members.get(0)
		val type = member.type
		if(type instanceof Classifier){
			'''
			«Utils.getFirstToLowerCase(clazz.name)» : Array<«ClassifierUtils.getAttributesInterfaceName(type)»>;
			get«Utils.getFirstToUpperCase(clazz.name)»(): Promise<Array<«ClassifierUtils.getAttributesInterfaceName(type)»>>;
			
			'''
		}
	}
	
}