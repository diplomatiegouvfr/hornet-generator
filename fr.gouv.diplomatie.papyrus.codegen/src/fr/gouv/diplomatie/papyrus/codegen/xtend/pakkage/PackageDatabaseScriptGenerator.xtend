package fr.gouv.diplomatie.papyrus.codegen.xtend.pakkage;

import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Classifier
import org.eclipse.uml2.uml.AssociationClass
import fr.gouv.diplomatie.papyrus.codegen.xtend.utils.ClassifierUtils
import fr.gouv.diplomatie.papyrus.codegen.xtend.utils.Utils
import fr.gouv.diplomatie.papyrus.codegen.xtend.utils.PropertyUtils
import fr.gouv.diplomatie.papyrus.codegen.xtend.utils.TypeUtils
import org.eclipse.uml2.uml.PrimitiveType

public class PackageDatabaseScriptGenerator{
	
	static def generateCode(Package pakkage){
		val model = pakkage.model
		val classes = model.getOwnedTypes().filter[type|
			Utils.isEntity(type)
		]
		
		val associationsClasses = model.getOwnedTypes().filter[type|
			type instanceof AssociationClass
		]
		'''
		«classes.fold("")[acc, clazz |
			acc + '''«(clazz as Classifier).generateTable()»'''
		]»«classes.fold("")[acc, clazz |
			acc + '''«(clazz as Classifier).generateAlters()»'''
		]»
		«associationsClasses.fold("")[acc, clazz |
			acc + '''«(clazz as AssociationClass).generateAssociationTable()»'''
		]»
		'''
	}
	
	static def generateTable(Classifier clazz){
		val attributes = ClassifierUtils.getOwnedAttributes(clazz).filter(id |
			Utils.isSequence(id)
		)
		'''
		
		CREATE TABLE «Utils.toSnakeCase(clazz.name)»(
			«clazz.generateAttributes("")»
		);
		«clazz.generateIds»
				
		«attributes.fold("")[acc, id |
			acc + '''«id.generateSequence»'''
		]»
		'''
	}
	
	static def generateAlters(Classifier clazz){
		'''
		«clazz.generateMultivaluedAttributesTable»
		«clazz.generateForeignKeys("", clazz)»
		'''
	}
	
	/**
	 * Génère les attributs de la classe
	 */
	static def generateAttributes(Classifier clazz, String additionnalName){
		val attributes = ClassifierUtils.getNotMultivaluedOwnedAttributes(clazz)
		'''«attributes.fold("")[acc, attribut|
			if(acc != ""){
 				acc + ''',
 				''' + '''«attribut.generateAttributDefinition(additionnalName)»'''
 			}else{
 				acc + '''«attribut.generateAttributDefinition(additionnalName)»'''
 			}		
 		]»'''
		
	}
	
	/**
	 * génère un attribut
	 */
	static def generateAttributDefinition(Property property, String additionalName){
		if(PropertyUtils.isID(property)){
			'''«property.generateIdAttributeDefinition(additionalName)»'''
		}else{
			'''«property.generateNIdAttributeDefinition(additionalName)»'''
		}
	}
	
	/**
	 * génère la définition d'un attribut id
	 */
	static def generateIdAttributeDefinition(Property property, String additionnalName){
		val name = Utils.addAdditionnalName(additionnalName, property.name)
		'''«Utils.toSnakeCase(name)» «property.generateAttributType»«property.generateStringLength» NOT NULL'''
	}
	
	
	
	static def generateStringLength(Property property){
		val length = PropertyUtils.getStereotypePropertyValue(property, Utils.MODEL_ATTRIBUTE, Utils.MODEL_ATTRIBUTE_LENGTH )
		if(length !== null && length != 0){
			''' varying(«length»)'''
		}else{
			''''''
		}	
	}
	
	/**
	 * génère la définition d'un attribut qui n'est pas un id
	 */
	static def generateNIdAttributeDefinition(Property property, String additionnalName){
		if(PropertyUtils.isClassAttribute(property)){
			'''«property.generateClassAttributeDefinition(additionnalName)»'''
		}else{
			'''«property.generateBasicAttributeDefinition(additionnalName)»'''
		}
	}
	
	/**
	 * génère la définition d'un attribut lié a la classe
	 */
	static def generateClassAttributeDefinition(Property property, String additionnalName){
		if(Utils.isValueObject(property.type)){
			'''«property.generateValueObjectAttributeDefinition(additionnalName)»'''
		}else{
			'''«property.generateEntityAttributesDefinition(additionnalName)»'''
		}
	}
	
	/**
	 * génère la définition d'un attribut de type value Object
	 */
	static def generateValueObjectAttributeDefinition(Property property, String additionnalName){
		val name = Utils.addAdditionnalName(additionnalName, property.name)
		val type = property.type
		if(type instanceof Classifier){
			//val attributes = ClassifierUtils.getNotMultivaluedOwnedAttributes(type)
			//val hasAttributes = (!attributes.empty && attributes !== null)
			return '''«type.generateAttributes(name)»'''
			//«««type.generateMultiValuedEntityAttributes(hasAttributes, name)»
		}else{
			''''''
		}
	}
	
	/**
	 * génère la définition d'un attribut basique
	 */
	static def generateBasicAttributeDefinition(Property property, String additionnalName){
		var name = Utils.addAdditionnalName(additionnalName, property.name)
		'''«Utils.toSnakeCase(name)» «property.generateAttributType»«property.generateStringLength»'''
	}
	
	/**
	 * génère la définition d'un attribut de type entity
	 */
	static def generateEntityAttributesDefinition(Property property, String additonnalName){
		val type = property.type
		if(type instanceof Classifier){
			val ids = ClassifierUtils.getId(type)
			'''«ids.fold("")[acc , id |
				if(acc != ""){
					acc + ''',«property.generateEntityAttributeDefinition(id, additonnalName)»'''
				}else{
					acc + '''«property.generateEntityAttributeDefinition(id, additonnalName)»'''
				}
			]»'''
		}else{
			''''''
		}
		
	}
	
	/**
	 * génère la définition d'un attribut de type entity
	 */
	static def generateEntityAttributeDefinition(Property property, Property id,  String additionnalName){
		val propName = Utils.addAdditionnalName(additionnalName, id.name) + Utils.getFirstToUpperCase(property.name)
		return 
		'''«Utils.toSnakeCase(propName)» «id.generateAttributType»«id.generateStringLength»'''
	}
	
	/**
	 * génère la déclaration des ids
	 */
	static def generateIds(Classifier clazz){
		val ids = ClassifierUtils.getId(clazz)
		val name = ids.fold("")[acc, id |
			if( acc != ""){
				acc + ''', «Utils.toSnakeCase(id.name)»'''
			}else{
				acc + '''«Utils.toSnakeCase(id.name)»'''
			}
		]
		'''
		
		ALTER TABLE ONLY «Utils.toSnakeCase(clazz.name)»
			ADD CONSTRAINT «Utils.toSnakeCase(clazz.name)»_pkey PRIMARY KEY («name»);
		'''
	}
	
	/**
	 * génère les clés étrangères
	 */
	static def generateForeignKeys(Classifier clazz, String additionnalName, Classifier fromClazz){
		val attributes = ClassifierUtils.getOwnedAttributes(clazz).filter[attribut |
			((Utils.isEntity(attribut.type) && !(attribut.multivalued)) || (Utils.isValueObject(attribut.type) && !(attribut.multivalued)))
		]
		
		'''«attributes.fold("")[acc, attribut|
			if(Utils.isEntity(attribut.type)){
				acc + '''«attribut.generateForeignKey(additionnalName, fromClazz)»'''
			}else if(Utils.isValueObject(attribut.type)){
				acc + '''«(attribut.type as Classifier).generateForeignKeys(attribut.name, fromClazz)»'''
			}
		]»'''
	}
	
	/**
	 * génère une clé étrangère
	 */
	static def generateForeignKey(Property property, String additionnalName, Classifier clazz){
		val fromClass = property.owner
		val toClass = property.type
		if(fromClass instanceof Classifier){
			if(toClass instanceof Classifier){
				val propName = Utils.addAdditionnalName(additionnalName, property.name)
				val ids = ClassifierUtils.getId(toClass)
				var fieldToClass = ids.fold("")[acc, field |
					
					val name =   Utils.addAdditionnalName(additionnalName, field.name ) + Utils.getFirstToUpperCase(property.name)
					if(acc !=""){
						acc + ''', «Utils.toSnakeCase(name)»'''
					}else{
						acc + '''«Utils.toSnakeCase(name)»'''
					}
				]
				
				var fieldInClass = ids.fold("")[acc, field |
					
					val name =  field.name
					if(acc !=""){
						acc + ''', «Utils.toSnakeCase(name)»'''
					}else{
						acc + '''«Utils.toSnakeCase(name)»'''
					}
				]
				return
				'''
				
				ALTER TABLE ONLY «Utils.toSnakeCase(clazz.name)»
				    ADD CONSTRAINT «Utils.toSnakeCase(clazz.name)»_«Utils.toSnakeCase(propName)»_ids_fkey 
				    FOREIGN KEY («fieldToClass») REFERENCES «Utils.toSnakeCase(toClass.name)»(«fieldInClass»);
				'''
			}
		}
		return ''''''
	}
	
	static def generateMultivaluedAttributesTable(Classifier clazz){
		val attributes = ClassifierUtils.getOwnedAttributes(clazz).filter[attribut |
			(attribut.multivalued)
		]
		
		'''«attributes.fold("")[acc, attribut |
			acc + '''«attribut.generateMultivaluedAttributeTable»'''
		]»'''
	}
	
	static def generateMultivaluedAttributeTable(Property property){
		val type = property.type
		if(type instanceof PrimitiveType){
			'''«property.generateMutilvaluedPTTable»'''
		}else if(Utils.isEntity(type)){
			'''«property.generateMutilvaluedEntityTable»'''
		}else if(Utils.isValueObject(type)){
			'''«property.generateValueObjectEntityTable»'''
		}else{
			''''''
		}
	}
	
	static def generateMutilvaluedPTTable(Property property){

		val owner = property.owner
		if(owner instanceof Classifier){
			val tableName = owner.name + Utils.getFirstToUpperCase(property.name)
			val ids = ClassifierUtils.getId(owner) 
			val idsName = ids.fold("")[acc, id |
				if(acc != ""){
					acc + ''', «Utils.toSnakeCase(id.name)»'''
				}else{
					acc + '''«Utils.toSnakeCase(id.name)»'''
				}
			]
			'''
			
			CREATE TABLE «Utils.toSnakeCase(tableName)»(
				«Utils.toSnakeCase(property.name)» «property.generateAttributType»«property.generateStringLength» NOT NULL,
				«ids.fold("")[acc, id |
					if(acc != ""){
						acc + ''',
						''' + '''«id.generateIdAttributeDefinition("")»'''
					}else{
						acc + '''«id.generateIdAttributeDefinition("")»'''
					}
				]»
			);
			
			ALTER TABLE ONLY «Utils.toSnakeCase(tableName)»
			    ADD CONSTRAINT «Utils.toSnakeCase(tableName)»_ids_fkey
			    FOREIGN KEY («idsName») REFERENCES «Utils.toSnakeCase(owner.name)»(«idsName»);
			'''
		}
	}
	
	/**
	 * génère une table d'association pour un attribut de type entity multivalué
	 */
	static def generateMutilvaluedEntityTable(Property property){
		val type = property.type
		val owner = property.owner
		if(owner instanceof Classifier){
			if(type instanceof Classifier){
				val tableName = owner.name + Utils.getFirstToUpperCase(property.name)
				val idsProps = ClassifierUtils.getId(type) 
				val idsOwner = ClassifierUtils.getId(owner) 
				val idsOwnerName = idsOwner.fold("")[acc, id |
					if(acc != ""){
						acc + ''', «Utils.toSnakeCase(id.name)»_«Utils.toSnakeCase(owner.name)»'''
					}else{
						acc + '''«Utils.toSnakeCase(id.name)»_«Utils.toSnakeCase(owner.name)»'''
					}
				]
				val idsOwnerBaseName = idsOwner.fold("")[acc, id |
					if(acc != ""){
						acc + ''', «Utils.toSnakeCase(id.name)»'''
					}else{
						acc + '''«Utils.toSnakeCase(id.name)»'''
					}
				]
				
				val idsPropsName = idsProps.fold("")[acc, id |
					if(acc != ""){
						acc + ''', «Utils.toSnakeCase(id.name)»_«Utils.toSnakeCase(type.name)»'''
					}else{
						acc + '''«Utils.toSnakeCase(id.name)»_«Utils.toSnakeCase(type.name)»'''
					}
				]
				
				val idsPropsBaseName = idsProps.fold("")[acc, id |
					if(acc != ""){
						acc + ''', «Utils.toSnakeCase(id.name)»'''
					}else{
						acc + '''«Utils.toSnakeCase(id.name)»'''
					}
				]
				'''
				
				CREATE TABLE «Utils.toSnakeCase(tableName)»(
					«idsProps.fold("")[acc, id |
						if(acc != ""){
							acc + ''',
							''' + '''«id.generateMultiIdAttributeDefinition("")»'''
						}else{
							acc + '''«id.generateMultiIdAttributeDefinition("")»'''
						}
					]»,
					«idsOwner.fold("")[acc, id |
						if(acc != ""){
							acc + ''',
							''' + '''«id.generateMultiIdAttributeDefinition("")»'''
						}else{
							acc + '''«id.generateMultiIdAttributeDefinition("")»'''
						}
					]»
				);
				
				ALTER TABLE ONLY «Utils.toSnakeCase(tableName)»
				    ADD CONSTRAINT «Utils.toSnakeCase(tableName)»_«Utils.toSnakeCase(owner.name)»_ids_fkey
				    FOREIGN KEY («idsOwnerName») REFERENCES «Utils.toSnakeCase(owner.name)»(«idsOwnerBaseName»);
				    
				ALTER TABLE ONLY «Utils.toSnakeCase(tableName)»
				    ADD CONSTRAINT «Utils.toSnakeCase(tableName)»_«Utils.toSnakeCase(type.name)»_ids_fkey
				    FOREIGN KEY («idsPropsName») REFERENCES «Utils.toSnakeCase(type.name)»(«idsPropsBaseName»);
				'''
			}
		}
	}
	
	/**
	 * génère la définition d'un attribut id
	 */
	static def generateMultiIdAttributeDefinition(Property property, String additionnalName){
		val name = Utils.addAdditionnalName(additionnalName, property.name)
		val owner = property.owner
		if(owner instanceof Classifier){
			'''«Utils.toSnakeCase(name)»_«Utils.toSnakeCase(owner.name)» «property.generateAttributType»«property.generateStringLength» NOT NULL'''
		}
	}
	
	static def generateValueObjectEntityTable(Property property){
		val type = property.type
		val owner = property.owner
		if(owner instanceof Classifier){
			if(type instanceof Classifier){
				val tableName = owner.name + Utils.getFirstToUpperCase(property.name)
				
				val idsOwner = ClassifierUtils.getId(owner) 
				val idsName = idsOwner.fold("")[acc, id |
					if(acc != ""){
						acc + ''', «Utils.toSnakeCase(id.name)»'''
					}else{
						acc + '''«Utils.toSnakeCase(id.name)»'''
					}
				]
				'''
				
				CREATE TABLE «Utils.toSnakeCase(tableName)»(
					«type.generateAttributes("")»,
					«idsOwner.fold("")[acc, id |
						if(acc != ""){
							acc + ''',
							''' + '''«id.generateIdAttributeDefinition("")»'''
						}else{
							acc + '''«id.generateIdAttributeDefinition("")»'''
						}
					]»
				);«type.generateForeignKeys(property.name, type)»
				
				ALTER TABLE ONLY «Utils.toSnakeCase(tableName)»
				    ADD CONSTRAINT «Utils.toSnakeCase(tableName)»_ids_fkey
				    FOREIGN KEY («idsName») REFERENCES «Utils.toSnakeCase(type.name)»(«idsName»);
				'''	
			}	
		}
	}
	
	/**
	 * génère la séquence liée a un attribut
	 */
	static def generateSequence(Property property){
		val owner = property.owner
		if(owner instanceof Classifier){
			var maxVal = '''NO MAXVALUE'''
			var minVal = '''NO MINVALUE'''
			val name = owner.name + Utils.getFirstToUpperCase(property.name)
			val startWith = Utils.getStereotypePropertyValue(property, Utils.MODEL_SEQUENCE, Utils.MODEL_SEQUENCE_STARTWITH)
			val inscrementBy = Utils.getStereotypePropertyValue(property, Utils.MODEL_SEQUENCE, Utils.MODEL_SEQUENCE_INCREMENTBY)
			
			val hasMaxValue = Utils.getStereotypePropertyValue(property, Utils.MODEL_SEQUENCE, Utils.MODEL_SEQUENCE_HASMAXVALUE)
			val maxValue = Utils.getStereotypePropertyValue(property, Utils.MODEL_SEQUENCE, Utils.MODEL_SEQUENCE_MAXVALUE)
			if(hasMaxValue == true || maxValue!= 0 ){
				maxVal = '''MAXVALUE «maxValue»'''
			}
			val hasMinValue = Utils.getStereotypePropertyValue(property, Utils.MODEL_SEQUENCE, Utils.MODEL_SEQUENCE_HASMINVALUE)
			val minValue = Utils.getStereotypePropertyValue(property, Utils.MODEL_SEQUENCE, Utils.MODEL_SEQUENCE_MINVALUE)
			if(hasMinValue == true || minValue != 0){
				minVal = '''MINVALUE «minValue»'''
			}
			
			val cache = Utils.getStereotypePropertyValue(property, Utils.MODEL_SEQUENCE, Utils.MODEL_SEQUENCE_CACHE)
			val isCycle = Utils.getStereotypePropertyValue(property, Utils.MODEL_SEQUENCE, Utils.MODEL_SEQUENCE_CYCLE)
			var cycle = "NO CYCLE"
			if(isCycle == true){
				cycle = "CYCLE"
			}
			'''
			CREATE SEQUENCE «Utils.toSnakeCase(name)»_seq
			    START WITH «startWith»
			    INCREMENT BY «inscrementBy»
			    «maxVal»
			    «minVal»
			    CACHE «cache»
			    «cycle»;
			    
			ALTER SEQUENCE «Utils.toSnakeCase(name)»_seq 
				OWNED BY «Utils.toSnakeCase(owner.name)».«Utils.toSnakeCase(property.name)»;
			
			ALTER TABLE ONLY «Utils.toSnakeCase(owner.name)» 
				ALTER COLUMN «Utils.toSnakeCase(property.name)» 
				SET DEFAULT nextval('«Utils.toSnakeCase(name)»_seq'::regclass);
			'''
		}
	}
	
	static def generateAssociationTable(AssociationClass clazz){
		'''
		
		CREATE TABLE «Utils.toSnakeCase(clazz.name)»(
			«clazz.generateAssociationAttributes("")»
		);
		«clazz.generateAssociationForeignKeys()»
		'''
	}
	
		/**
	 * Génère les attributs de la classe
	 */
	static def generateAssociationAttributes(AssociationClass clazz, String additionnalName){
		val attributes = clazz.memberEnds
		'''«attributes.fold("")[acc, attribut|
			if(acc != ""){
 				acc + ''',
 				''' + '''«attribut.generateAttributDefinition(additionnalName)»'''
 			}else{
 				acc + '''«attribut.generateAttributDefinition(additionnalName)»'''
 			}		
 		]»'''
		
	}
	
	static def generateAssociationForeignKeys(AssociationClass clazz){
		val members = clazz.memberEnds
		'''«members.fold("")[acc, member |
			acc + '''«member.generateAssociationForeignKeys»'''
		]»'''
	}
	
	static def generateAssociationForeignKeys(Property property){
		val owner = property.owner
		val type = property.type
		if(type instanceof Classifier){
			if(Utils.isEntity(type)){
				return '''«property.generateAssociationForeignKeysEntity»'''
			}else if (Utils.isValueObject(type)){
				return '''«type.generateForeignKeys(property.name, owner as Classifier)»'''
			}
		}
		''''''
	}
	
	static def generateAssociationForeignKeysEntity(Property property){
		val type = property.type
		val owner = property.owner
		if(type instanceof Classifier){
			if(owner instanceof Classifier){
				val ids = ClassifierUtils.getId(type)
				val idsName = ids.fold("")[acc, id |
					if(acc != ""){
						acc + ''', «Utils.toSnakeCase(id.name)»'''
					}else{
						acc + '''«Utils.toSnakeCase(id.name)»'''
					}
				]
				
				val idsNameInClass = ids.fold("")[acc, id |
					if(acc != ""){
						acc + ''', «Utils.toSnakeCase(id.name)»_«Utils.toSnakeCase(type.name)»'''
					}else{
						acc + '''«Utils.toSnakeCase(id.name)»_«Utils.toSnakeCase(type.name)»'''
					}
				]
				return '''
				
				ALTER TABLE ONLY «Utils.toSnakeCase(owner.name)»
					ADD CONSTRAINT «Utils.toSnakeCase(owner.name)»_«Utils.toSnakeCase(property.name)»_ids_fkey 
					FOREIGN KEY («idsNameInClass») REFERENCES «Utils.toSnakeCase(type.name)»(«idsName»);
				'''
			}
		}
		return ''''''
		
	}
	
	static def generateAttributType(Property property){
		var type = TypeUtils.getDatabaseType(property.type)
		if(type == 'character'){
			val length = PropertyUtils.getStereotypePropertyValue(property, Utils.MODEL_ATTRIBUTE, Utils.MODEL_ATTRIBUTE_LENGTH )
			if(length === null || length == 0){
				type = 'text'
			}
		}
		
		return type
	}
	
	static def generateNullable(Property property){
		val nullable = PropertyUtils.isNullable(property)
		if(!nullable){
			return '''NOT NULL'''
		}else{
			''''''
		}
	}
}