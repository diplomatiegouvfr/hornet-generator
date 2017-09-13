package fr.gouv.diplomatie.papyrus.codegen.sql.xtend.pakkage;

import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Classifier
import org.eclipse.uml2.uml.AssociationClass
import fr.gouv.diplomatie.papyrus.codegen.core.utils.Utils
import fr.gouv.diplomatie.papyrus.codegen.core.utils.PropertyUtils
import fr.gouv.diplomatie.papyrus.codegen.core.utils.TypeUtils
import fr.gouv.diplomatie.papyrus.codegen.core.utils.ClassifierUtils
import org.eclipse.uml2.uml.PrimitiveType
import org.eclipse.uml2.uml.Interface
import java.util.ArrayList

public class PackageDatabaseScriptGenerator{
	
	static def generateCode(Package pakkage){
		
		val model = pakkage.model
		val classes = model.getOwnedTypes().filter[type|
			Utils.isEntity(type) && ClassifierUtils.canBeGenerated(type as Classifier)
		]
		
		val enums = model.getOwnedTypes().filter[type|
			Utils.isNomenclature(type) && ClassifierUtils.canBeGenerated(type as Classifier)
		]
		
		val associationsClasses = model.getOwnedTypes().filter[type|
			type instanceof AssociationClass
		]
		'''
		«classes.fold("")[acc, clazz |
			acc + '''«(clazz as Classifier).generateTable()»'''
		]»
		«enums.fold("")[acc, clazz |
			acc + '''«(clazz as Classifier).generateEnumTable()»'''
		]
		»
		«classes.fold("")[acc, clazz |
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
		
		CREATE TABLE «ClassifierUtils.getTableName(clazz)»(
			«clazz.generateExtendsId»
			«clazz.generateInterfaceAttributes(clazz)»
			«clazz.generateAttributes("", clazz, false)»
		);
		«clazz.generateIds»
				
		«attributes.fold("")[acc, id |
			acc + '''«id.generateSequence»'''
		]»
		'''
	}
	
	/**
	 * génère les tables d'attributs multiple et les foreign keys
	 */
	static def generateAlters(Classifier clazz){
		'''
		«clazz.generateMultivaluedAttributesTable(clazz)»
		«clazz.generateForeignKeys("", ClassifierUtils.getTableName(clazz))»
		«clazz.generateExtendsForeignKey()»
		'''
	}
	
	/**
	 * génère dans la classe l'id de la classe étendue
	 */
	static def generateExtendsId(Classifier clazz){
		val parents = clazz.generalizations
		if(parents.length > 0){
			'''«parents.fold("")[acc, parent |
				acc + '''«parent.general.generateExtendId»'''
			]»'''
		}else{
			''''''
		}
	}
	
	static def generateExtendId(Classifier clazz){
		val ids = ClassifierUtils.getId(clazz)
		val id = ids.get(0)
		'''
		«Utils.toSnakeCase(id.name)» «id.generateAttributType»«id.generateStringLength» NOT NULL,
		'''
	}
	
	static def generateInterfaceAttributes(Classifier clazz, Classifier fromClass){
		val interfaces = clazz.directlyRealizedInterfaces
		if(interfaces !== null && !interfaces.empty){
			'''
			«interfaces.fold("")[acc, interface |
				acc + '''«interface.generateAttributes("", fromClass, false)»'''
			]»,
			'''
		}else{
			''''''
		}
		
	}
	
	/**
	 * Génère les attributs de la classe
	 */
	static def generateAttributes(Classifier clazz, String additionnalName, Classifier fromClass, Boolean nullable){
		val attributes = ClassifierUtils.getNotMultivaluedOwnedAttributes(clazz)
		'''«attributes.fold("")[acc, attribut|
			if(acc != ""){
 				acc + ''',
 				''' + '''«attribut.generateAttributDefinition(additionnalName, fromClass, nullable)»'''
 			}else{
 				acc + '''«attribut.generateAttributDefinition(additionnalName, fromClass, nullable)»'''
 			}		
 		]»'''
		
	}
	
	/**
	 * génère un attribut
	 */ 
	static def generateAttributDefinition(Property property, String additionalName, Classifier fromClass, Boolean nullable){
		if(PropertyUtils.isID(property)){
			'''«property.generateIdAttributeDefinition(additionalName)»'''
		}else{
			'''«property.generateNIdAttributeDefinition(additionalName, fromClass, nullable)»'''
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
	static def generateNIdAttributeDefinition(Property property, String additionnalName, Classifier fromClass, Boolean nullable){
		if(PropertyUtils.isClassAttribute(property)){
			'''«property.generateClassAttributeDefinition(additionnalName, fromClass, nullable)»'''
		}else{
			'''«property.generateBasicAttributeDefinition(additionnalName, fromClass, nullable)»'''
		}
	}
	
	/**
	 * génère la définition d'un attribut lié a la classe
	 */
	static def generateClassAttributeDefinition(Property property, String additionnalName, Classifier fromClass, Boolean nullable){
		if(Utils.isValueObject(property.type)){
			'''«property.generateValueObjectAttributeDefinition(additionnalName, fromClass, nullable)»'''
		}else if(Utils.isNomenclature(property.type)){
			'''«property.generateEumAttributesDefinition(additionnalName, fromClass, nullable)»'''
		}else{
			'''«property.generateEntityAttributesDefinition(additionnalName, fromClass, nullable)»'''
		}
	}
	
	/**
	 * génère la définition d'un attribut de type value Object
	 */
	static def generateValueObjectAttributeDefinition(Property property, String additionnalName, Classifier fromClass, Boolean nullable){
		val name = Utils.addAdditionnalName(additionnalName, property.name)
		val type = property.type
		if(type instanceof Classifier){
			//val attributes = ClassifierUtils.getNotMultivaluedOwnedAttributes(type)
			//val hasAttributes = (!attributes.empty && attributes !== null)
			return '''«type.generateAttributes(name, fromClass, nullable || PropertyUtils.isNullable(property))»'''
			//«««type.generateMultiValuedEntityAttributes(hasAttributes, name)»
		}else{
			''''''
		}
	}
	
	/**
	 * génère la définition d'un attribut de type value enum
	 */
	static def generateEumAttributesDefinition(Property property, String additionnalName, Classifier fromClass, Boolean nullable){
		var name = Utils.addAdditionnalName(additionnalName, property.name)
		val type = property.type
		if(type instanceof Classifier){
			var sqlType = TypeUtils.getEnumType(type)
			'''code_«Utils.toSnakeCase(name)» «sqlType» «property.generateNullable(nullable)»'''
		}
	}
	
	/**
	 * génère la définition d'un attribut basique
	 */
	static def generateBasicAttributeDefinition(Property property, String additionnalName, Classifier fromClass, Boolean nullable){
		var name = Utils.addAdditionnalName(additionnalName, property.name)
		'''«Utils.toSnakeCase(name)» «property.generateAttributType»«property.generateStringLength» «property.generateNullable(nullable)»'''
	}
	
	/**
	 * génère la définition d'un attribut de type entity
	 */
	static def generateEntityAttributesDefinition(Property property, String additonnalName, Classifier fromClass, Boolean nullable){
		val type = property.type
		if(type instanceof Classifier){
			val ids = ClassifierUtils.getId(type)
			'''«ids.fold("")[acc , id |
				if(acc != ""){
					acc + ''',«property.generateEntityAttributeDefinition(id, additonnalName,fromClass, nullable)»'''
				}else{
					acc + '''«property.generateEntityAttributeDefinition(id, additonnalName, fromClass, nullable)»'''
				}
			]»'''
		}else{
			''''''
		}
		
	}
	
	/**
	 * génère la définition d'un attribut de type entity
	 */
	static def generateEntityAttributeDefinition(Property property, Property id,  String additionnalName, Classifier fromClass, Boolean nullable){
		val propName = Utils.addAdditionnalName(additionnalName, id.name) + Utils.getFirstToUpperCase(property.name)
		return 
		'''«Utils.toSnakeCase(propName)» «id.generateAttributType»«id.generateStringLength» «property.generateNullable(nullable)»'''
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
		
		ALTER TABLE ONLY «ClassifierUtils.getTableName(clazz)»
			ADD CONSTRAINT «ClassifierUtils.getTableName(clazz)»_pkey PRIMARY KEY («name»);
		'''
	}
	
	static def generateExtendsForeignKey(Classifier clazz){
		val parents = clazz.generalizations
		if( parents !== null && !parents.empty){
			'''«parents.fold("")[acc, parent |
				acc + '''«parent.general.generateExtendForeignKey(clazz)»'''
			]»'''
		}else{
			''''''
		}
	}
	
	static def generateExtendForeignKey(Classifier clazz, Classifier fromClass){
		val ids = ClassifierUtils.getId(clazz)
		if(ids !== null && !ids.empty){
			val idsName = ids.fold("")[acc, id |
				if(acc != ""){
					acc + ''', «Utils.toSnakeCase(id.name)»'''
				}else{
					acc + '''«Utils.toSnakeCase(id.name)»'''
				}
			]
			'''
			
			ALTER TABLE ONLY «ClassifierUtils.getTableName(fromClass)»
			    ADD CONSTRAINT «ClassifierUtils.getTableName(fromClass)»_«Utils.toSnakeCase(clazz.name)»_ids_fkey
			    FOREIGN KEY («idsName») REFERENCES «ClassifierUtils.getTableName(clazz)»(«idsName»);
			'''
		}else{
			''''''
		}
	}
	/**
	 * génère les clés étrangères
	 */
	static def generateForeignKeys(Classifier clazz, String additionnalName, String tableName){
		val attributes = ClassifierUtils.getOwnedAttributes(clazz).filter[attribut |
			 (
				(Utils.isEntity(attribut.type) && !(attribut.multivalued)) || 
				(Utils.isValueObject(attribut.type) && !(attribut.multivalued)) ||
				(Utils.isNomenclature(attribut.type) && !(attribut.multivalued))	
			)
		]
		
		val interfaces = clazz.getAllUsedInterfaces
		
		'''«attributes.fold("")[acc, attribut|
			if(Utils.isEntity(attribut.type)){
				acc + '''«attribut.generateForeignKey(additionnalName, tableName)»'''
			}else if(Utils.isValueObject(attribut.type)){
				acc + '''«(attribut.type as Classifier).generateForeignKeys(attribut.name, tableName)»'''
			}else if (Utils.isNomenclature(attribut.type)){
				acc + '''«attribut.generateEnumForeignKey(additionnalName, tableName)»'''
			}
		]»«interfaces.fold("")[acc, interface |
			acc + '''«interface.generateForeignKeys(additionnalName, tableName)»'''
		]»'''
	}
	
	/**
	 * génère une clé étrangère
	 */
	static def generateForeignKey(Property property, String additionnalName, String tableName){
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
				
				ALTER TABLE ONLY «Utils.toSnakeCase(tableName)»
				    ADD CONSTRAINT «Utils.toSnakeCase(tableName)»_«Utils.toSnakeCase(propName)»_ids_fkey 
				    FOREIGN KEY («fieldToClass») REFERENCES «ClassifierUtils.getTableName(toClass)»(«fieldInClass»);
				'''
			}
		}
		return ''''''
	}
	
	/**
	 * génère une clé étrangère liée à un attribut de type enum
	 */
	static def generateEnumForeignKey(Property property, String additionnalName, String tableName){
		val type = property.type
		val propName = Utils.addAdditionnalName(additionnalName, property.name)
		'''
		
		ALTER TABLE ONLY «Utils.toSnakeCase(tableName)»
		    ADD CONSTRAINT «Utils.toSnakeCase(tableName)»_«Utils.toSnakeCase(propName)»_code_fkey 
		    FOREIGN KEY (code_«Utils.toSnakeCase(propName)») REFERENCES «ClassifierUtils.getTableName(type as Classifier)»(code);
		'''
	}
	
	static def generateMultivaluedAttributesTable(Classifier clazz, Classifier fromClass){
		val attributes = ClassifierUtils.getMultivaluedOwnedAttributes(clazz)
		
		val interfaces = clazz.directlyRealizedInterfaces
		
		'''«attributes.fold("")[acc, attribut |
			acc + '''«attribut.generateMultivaluedAttributeTable(fromClass)»'''
		]»«interfaces.fold("")[acc, interface |
			acc + '''«interface.generateInterfaceMultivaluedAttributesTables(fromClass)»'''
		]»'''
	}
	
	static def generateInterfaceMultivaluedAttributesTables(Interface interf, Classifier fromClass){
		val attributes = ClassifierUtils.getOwnedAttributes(interf).filter[attribut |
			(attribut.multivalued)
		]
		
		
		'''«attributes.fold("")[acc, attribut |
			acc + '''«attribut.generateMultivaluedAttributeTable(fromClass)»'''
		]»'''
	}
	
	static def generateMultivaluedAttributeTable(Property property, Classifier fromClass){
		val type = property.type
		if(type instanceof PrimitiveType){
			'''«property.generateMutilvaluedPTTable(fromClass)»'''
		}else if(Utils.isEntity(type)){
			'''«property.generateMutilvaluedEntityTable(fromClass)»'''
		}else if(Utils.isValueObject(type)){
			'''«property.generateValueObjectEntityTable(fromClass)»'''
		}else if(Utils.isNomenclature(type)){
			'''«property.generateEnumAttributTable(fromClass)»'''
		}else{
			''''''
		}
	}
	
	static def generateMutilvaluedPTTable(Property property, Classifier fromClass){

		val tableName = ClassifierUtils.getTableName(fromClass) + Utils.getFirstToUpperCase(property.name)
		val ids = ClassifierUtils.getId(fromClass) 
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
		    FOREIGN KEY («idsName») REFERENCES «ClassifierUtils.getTableName(fromClass)»(«idsName»);
		    
		ALTER TABLE ONLY «Utils.toSnakeCase(tableName)»
		    ADD CONSTRAINT «Utils.toSnakeCase(tableName)»_pkey PRIMARY KEY(«idsName», «Utils.toSnakeCase(property.name)»);
		'''
		
	}
	
	/**
	 * génère une table d'association pour un attribut de type entity multivalué
	 */
	static def generateMutilvaluedEntityTable(Property property, Classifier fromClass){
		val type = property.type
		if(type instanceof Classifier){
			val tableName = ClassifierUtils.getTableName(fromClass) + Utils.getFirstToUpperCase(property.name)
			val idsProps = ClassifierUtils.getId(type) 
			val idsOwner = ClassifierUtils.getId(fromClass) 
			val idsOwnerName = idsOwner.fold("")[acc, id |
				if(acc != ""){
					acc + ''', «Utils.toSnakeCase(id.name)»_«Utils.toSnakeCase(fromClass.name)»'''
				}else{
					acc + '''«Utils.toSnakeCase(id.name)»_«Utils.toSnakeCase(fromClass.name)»'''
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
			    ADD CONSTRAINT «Utils.toSnakeCase(tableName)»_«Utils.toSnakeCase(fromClass.name)»_ids_fkey
			    FOREIGN KEY («idsOwnerName») REFERENCES «ClassifierUtils.getTableName(fromClass)»(«idsOwnerBaseName»);
			    
			ALTER TABLE ONLY «Utils.toSnakeCase(tableName)»
			    ADD CONSTRAINT «Utils.toSnakeCase(tableName)»_«Utils.toSnakeCase(type.name)»_ids_fkey
			    FOREIGN KEY («idsPropsName») REFERENCES «ClassifierUtils.getTableName(type)»(«idsPropsBaseName»);
			    
			ALTER TABLE ONLY «Utils.toSnakeCase(tableName)»
			    ADD CONSTRAINT «Utils.toSnakeCase(tableName)»_pkey PRIMARY KEY(«idsOwnerName», «idsPropsName»);
			'''
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
	
	static def generateValueObjectEntityTable(Property property, Classifier fromClass){
		val type = property.type
		if(type instanceof Classifier){
			val tableName = ClassifierUtils.getTableName(fromClass) + Utils.getFirstToUpperCase(property.name)
			
			val idsOwner = ClassifierUtils.getId(fromClass) 
			val idsName = idsOwner.fold("")[acc, id |
				if(acc != ""){
					acc + ''', «Utils.toSnakeCase(id.name)»'''
				}else{
					acc + '''«Utils.toSnakeCase(id.name)»'''
				}
			]
			val pkeys = idsName + ', ' + Utils.getListComma(type.getAttributList(newArrayList(), property.name))
			'''
			
			CREATE TABLE «Utils.toSnakeCase(tableName)»(
				«type.generateAttributes(property.name, fromClass, false)»,
				«idsOwner.fold("")[acc, id |
					if(acc != ""){
						acc + ''',
						''' + '''«id.generateIdAttributeDefinition("")»'''
					}else{
						acc + '''«id.generateIdAttributeDefinition("")»'''
					}
				]»
			);
			«type.generateForeignKeys(property.name, tableName)»
			
			ALTER TABLE ONLY «Utils.toSnakeCase(tableName)»
			    ADD CONSTRAINT «Utils.toSnakeCase(tableName)»_ids_fkey
			    FOREIGN KEY («idsName») REFERENCES «ClassifierUtils.getTableName(fromClass)»(«idsName»);
			    
			ALTER TABLE ONLY «Utils.toSnakeCase(tableName)»
			    ADD CONSTRAINT «Utils.toSnakeCase(tableName)»_pkey PRIMARY KEY(«pkeys»);
			    
			'''	
		}	
	}
	
	static def getAttributList(Classifier type, ArrayList<String> names, String additionnalName){
		val attributes = ClassifierUtils.getNotMultivaluedOwnedAttributes(type)
		attributes.forEach[attribut |
			if(!PropertyUtils.isNullable(attribut)){
				val attrType = attribut.type
				val name = Utils.toSnakeCase(Utils.addAdditionnalName(additionnalName, attribut.name))
				if(Utils.isEntity(attrType)){
					val ids = ClassifierUtils.getId(attrType as Classifier) 
					ids.forEach[id |
						var idName = Utils.toSnakeCase(Utils.addAdditionnalName(additionnalName, id.name + Utils.getFirstToUpperCase(attribut.name)))
						names.add(idName)
					]
				}else if(Utils.isNomenclature(attrType)){
					names.add(name)
				}else if(Utils.isValueObject(attrType)){
					val voName = Utils.addAdditionnalName(additionnalName, attribut.name)
					(attrType as Classifier).getAttributList(names, voName)
				}else{
					names.add(name)
				}
			}
		]
		return names
	}
	
	/**
	 * génère les tables liées a des tableaux d'enum
	 */
	static def generateEnumAttributTable(Property property, Classifier fromClass){
		val type = property.type
		if(type instanceof Classifier){
			val tableName = ClassifierUtils.getTableName(fromClass) + Utils.getFirstToUpperCase(property.name)
			
			val idsOwner = ClassifierUtils.getId(fromClass) 
			val idsName = idsOwner.fold("")[acc, id |
				if(acc != ""){
					acc + ''', «Utils.toSnakeCase(id.name)»'''
				}else{
					acc + '''«Utils.toSnakeCase(id.name)»'''
				}
			]
			
			val sqlType = TypeUtils.getEnumType(type)
			'''
			
			CREATE TABLE «Utils.toSnakeCase(tableName)»(
				code «sqlType» NOT NULL,
				«idsOwner.fold("")[acc, id |
					if(acc != ""){
						acc + ''',
						''' + '''«id.generateIdAttributeDefinition("")»'''
					}else{
						acc + '''«id.generateIdAttributeDefinition("")»'''
					}
				]»
			);«type.generateForeignKeys(property.name, tableName)»
			
			ALTER TABLE ONLY «Utils.toSnakeCase(tableName)»
			    ADD CONSTRAINT «Utils.toSnakeCase(tableName)»_ids_fkey
			    FOREIGN KEY («idsName») REFERENCES «ClassifierUtils.getTableName(fromClass)»(«idsName»);
			
			ALTER TABLE ONLY «Utils.toSnakeCase(tableName)»
			    ADD CONSTRAINT «Utils.toSnakeCase(tableName)»_code_fkey
			    FOREIGN KEY (code) REFERENCES «ClassifierUtils.getTableName(type)»(code);
			
			ALTER TABLE ONLY «Utils.toSnakeCase(tableName)»
			    ADD CONSTRAINT «Utils.toSnakeCase(tableName)»_pkey PRIMARY KEY («idsName», code);
			'''	
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
			val name = ClassifierUtils.getTableName(owner) + Utils.getFirstToUpperCase(property.name)
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
				OWNED BY «ClassifierUtils.getTableName(owner)».«Utils.toSnakeCase(property.name)»;
			
			ALTER TABLE ONLY «ClassifierUtils.getTableName(owner)» 
				ALTER COLUMN «Utils.toSnakeCase(property.name)» 
				SET DEFAULT nextval('«Utils.toSnakeCase(name)»_seq'::regclass);
			'''
		}
	}
	
	static def generateAssociationTable(AssociationClass clazz){
		'''
		
		CREATE TABLE «Utils.toSnakeCase(clazz.name)»(
			«clazz.generateAssociationAttributes("", clazz)»
		);
		«clazz.generateAssociationForeignKeys()»
		
		ALTER TABLE ONLY «Utils.toSnakeCase(clazz.name)»
			ADD CONSTRAINT «Utils.toSnakeCase(clazz.name)»_pkey PRIMARY KEY («Utils.getListComma(clazz.getAssociationAttributList(newArrayList(), ""))»);
		'''
	}
	
	static def getAssociationAttributList(AssociationClass type, ArrayList<String> names, String additionnalName){
		val attributes = type.memberEnds
		attributes.forEach[attribut |
			val attrType = attribut.type
			val name = Utils.toSnakeCase(Utils.addAdditionnalName(additionnalName, attribut.name))
			
			if(Utils.isEntity(attrType)){
				val ids = ClassifierUtils.getId(attrType as Classifier) 
				ids.forEach[id |
					var idName = Utils.toSnakeCase(Utils.addAdditionnalName(additionnalName, id.name + Utils.getFirstToUpperCase(attribut.name)))
					names.add(idName)
				]
			}else if(Utils.isNomenclature(attrType)){
				names.add(name)
			}else if(Utils.isValueObject(attrType)){
				val voName = Utils.addAdditionnalName(additionnalName, attribut.name)
				(attrType as Classifier).getAttributList(names, voName)
			}else{
				names.add(name)
			}
		]
		return names
	}
	
		/**
	 * Génère les attributs de la classe
	 */
	static def generateAssociationAttributes(AssociationClass clazz, String additionnalName, Classifier fromClass){
		val attributes = clazz.memberEnds
		'''«attributes.fold("")[acc, attribut|
			if(acc != ""){
 				acc + ''',
 				''' + '''«attribut.generateAttributDefinition(additionnalName, fromClass, false)»'''
 			}else{
 				acc + '''«attribut.generateAttributDefinition(additionnalName, fromClass, false)»'''
 			}		
 		]»'''
		
	}
	
	static def generateAssociationForeignKeys(AssociationClass clazz){
		val members = clazz.memberEnds
		'''«members.fold("")[acc, member |
			acc + '''«member.generateAssociationForeignKeys(clazz)»'''
		]»'''
	}
	
	static def generateAssociationForeignKeys(Property property, Classifier fromClass){
		val type = property.type
		if(type instanceof Classifier){
			if(Utils.isEntity(type)){
				return '''«property.generateAssociationForeignKeysEntity(fromClass)»'''
			}else if (Utils.isValueObject(type)){
				return '''«type.generateForeignKeys(property.name, ClassifierUtils.getTableName(fromClass))»'''
			}else if(Utils.isNomenclature(type)){
				return '''«property.generateAssociationForeignKeysEnum(fromClass)»'''
			}
		}
		''''''
	}
	
	static def generateAssociationForeignKeysEntity(Property property, Classifier fromClass){
		val type = property.type
		if(type instanceof Classifier){
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
					acc + ''', «Utils.toSnakeCase(id.name)»_«Utils.toSnakeCase(property.name)»'''
				}else{
					acc + '''«Utils.toSnakeCase(id.name)»_«Utils.toSnakeCase(property.name)»'''
				}
			]
			return '''
			
			ALTER TABLE ONLY «ClassifierUtils.getTableName(fromClass)»
				ADD CONSTRAINT «ClassifierUtils.getTableName(fromClass)»_«Utils.toSnakeCase(property.name)»_ids_fkey 
				FOREIGN KEY («idsNameInClass») REFERENCES «ClassifierUtils.getTableName(type)»(«idsName»);
			'''
		}
		return ''''''
		
	}
	
	static def generateAssociationForeignKeysEnum(Property property, Classifier fromClass){
		val type = property.type
		if(type instanceof Classifier){
			return '''
			
			ALTER TABLE ONLY «ClassifierUtils.getTableName(fromClass)»
				ADD CONSTRAINT «ClassifierUtils.getTableName(fromClass)»_«Utils.toSnakeCase(property.name)»_code_fkey 
				FOREIGN KEY («property.name») REFERENCES «ClassifierUtils.getTableName(type)»(code);
			'''
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
	
	static def generateNullable(Property property, Boolean nullable){
		var isNullable = PropertyUtils.isNullable(property)
		if(nullable){
			isNullable = true
		}
		
		if(!isNullable){
			return '''NOT NULL'''
		}else{
			''''''
		}
	}
	
	/**
	 * génère une table enum
	 */
	static def generateEnumTable(Classifier clazz){
		val hasCode = ClassifierUtils.isEnumWithCode(clazz)
		var sqlType = TypeUtils.getEnumType(clazz)
		'''
		
		CREATE TABLE «ClassifierUtils.getTableName(clazz)»(
			code «sqlType» NOT NULL,
			libelle text
		);
		
		ALTER TABLE ONLY «ClassifierUtils.getTableName(clazz)»
			ADD CONSTRAINT «ClassifierUtils.getTableName(clazz)»_pkey PRIMARY KEY (code);
		«IF !hasCode»
		
		CREATE SEQUENCE «ClassifierUtils.getTableName(clazz)»_code_seq
		    START WITH 1
		    INCREMENT BY 1
		    NO MAXVALUE
		    NO MINVALUE
		    CACHE 1
		    NO CYCLE;
		    
		ALTER SEQUENCE «ClassifierUtils.getTableName(clazz)»_code_seq
			OWNED BY «ClassifierUtils.getTableName(clazz)».code;
		«ENDIF»
		'''
	}
}