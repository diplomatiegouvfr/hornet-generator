/**
 * Copyright ou © ou Copr. Ministère de l'Europe et des Affaires étrangères (2017)
 * <p/>
 * pole-architecture.dga-dsi-psi@diplomatie.gouv.fr
 * <p/>
 * Ce logiciel est un programme informatique servant à faciliter la création
 * d'applications Web conformément aux référentiels généraux français : RGI, RGS et RGAA
 * <p/>
 * Ce logiciel est régi par la licence CeCILL soumise au droit français et
 * respectant les principes de diffusion des logiciels libres. Vous pouvez
 * utiliser, modifier et/ou redistribuer ce programme sous les conditions
 * de la licence CeCILL telle que diffusée par le CEA, le CNRS et l'INRIA
 * sur le site "http://www.cecill.info".
 * <p/>
 * En contrepartie de l'accessibilité au code source et des droits de copie,
 * de modification et de redistribution accordés par cette licence, il n'est
 * offert aux utilisateurs qu'une garantie limitée.  Pour les mêmes raisons,
 * seule une responsabilité restreinte pèse sur l'auteur du programme,  le
 * titulaire des droits patrimoniaux et les concédants successifs.
 * <p/>
 * A cet égard  l'attention de l'utilisateur est attirée sur les risques
 * associés au chargement,  à l'utilisation,  à la modification et/ou au
 * développement et à la reproduction du logiciel par l'utilisateur étant
 * donné sa spécificité de logiciel libre, qui peut le rendre complexe à
 * manipuler et qui le réserve donc à des développeurs et des professionnels
 * avertis possédant  des  connaissances  informatiques approfondies.  Les
 * utilisateurs sont donc invités à charger  et  tester  l'adéquation  du
 * logiciel à leurs besoins dans des conditions permettant d'assurer la
 * sécurité de leurs systèmes et ou de leurs données et, plus généralement,
 * à l'utiliser et l'exploiter dans les mêmes conditions de sécurité.
 * <p/>
 * Le fait que vous puissiez accéder à cet en-tête signifie que vous avez
 * pris connaissance de la licence CeCILL, et que vous en avez accepté les
 * termes.
 * <p/>
 * <p/>
 * Copyright or © or Copr. Ministry for Europe and Foreign Affairs (2017)
 * <p/>
 * pole-architecture.dga-dsi-psi@diplomatie.gouv.fr
 * <p/>
 * This software is a computer program whose purpose is to facilitate creation of
 * web application in accordance with french general repositories : RGI, RGS and RGAA.
 * <p/>
 * This software is governed by the CeCILL license under French law and
 * abiding by the rules of distribution of free software.  You can  use,
 * modify and/ or redistribute the software under the terms of the CeCILL
 * license as circulated by CEA, CNRS and INRIA at the following URL
 * "http://www.cecill.info".
 * <p/>
 * As a counterpart to the access to the source code and  rights to copy,
 * modify and redistribute granted by the license, users are provided only
 * with a limited warranty  and the software's author,  the holder of the
 * economic rights,  and the successive licensors  have only  limited
 * liability.
 * <p/>
 * In this respect, the user's attention is drawn to the risks associated
 * with loading,  using,  modifying and/or developing or reproducing the
 * software by the user in light of its specific status of free software,
 * that may mean  that it is complicated to manipulate,  and  that  also
 * therefore means  that it is reserved for developers  and  experienced
 * professionals having in-depth computer knowledge. Users are therefore
 * encouraged to load and test the software's suitability as regards their
 * requirements in conditions enabling the security of their systems and/or
 * data to be ensured and,  more generally, to use and operate it in the
 * same conditions as regards security.
 * <p/>
 * The fact that you are presently reading this means that you have had
 * knowledge of the CeCILL license and that you accept its terms.
 *
 */

/**
 * fr.gouv.diplomatie.papyrus.codegen.sql - Générateur de code sql pour 
 * des applications Hornet JS
 *
 * @author MEAE - Ministère de l'Europe et des Affaires étrangères
 * @version v1.1.3
 * @license CECILL-2.1
 */
package fr.gouv.diplomatie.papyrus.codegen.sql.xtend.pakkage;

import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Classifier
import org.eclipse.uml2.uml.AssociationClass
import fr.gouv.diplomatie.papyrus.codegen.core.utils.Utils
import fr.gouv.diplomatie.papyrus.codegen.core.utils.PropertyUtils
import fr.gouv.diplomatie.papyrus.codegen.sql.utils.TypeUtils
import fr.gouv.diplomatie.papyrus.codegen.core.utils.ClassifierUtils
import org.eclipse.uml2.uml.PrimitiveType
import org.eclipse.uml2.uml.Interface
import java.util.ArrayList
import fr.gouv.diplomatie.papyrus.codegen.sql.utils.SqlClassifierUtils

public class PackageDatabaseScriptGenerator{
	
	/**
	 * TODO
	 * changer le nom de ref : ex: id_nationalite etc...
	 */
	static def generateCode(Package pakkage){
		
		val model = pakkage.model
		
		val packages = model.ownedElements.filter[elem |
			elem instanceof Package
		]
		val classes = pakkage.getOwnedTypes().filter[type|
			Utils.isEntity(type) && ClassifierUtils.canBeGenerated(type as Classifier)
		]
		
		val enums = pakkage.getOwnedTypes().filter[type|
			Utils.isNomenclature(type) && ClassifierUtils.canBeGenerated(type as Classifier)
		]
		
		val associationsClasses = model.getOwnedTypes().filter[type|
			type instanceof AssociationClass
		]
		
		val assoInPakkage = pakkage.getOwnedTypes().filter[type|
			type instanceof AssociationClass
		]
		
		'''
		«packages.fold("")[acc, pkg |
			acc +  '''«(pkg as Package).generateCreateSchema»'''
		]»
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
		«assoInPakkage.fold("")[acc, clazz |
			acc + '''«(clazz as AssociationClass).generateAssociationTable()»'''
		]»
		'''
	}
	
	static def generateCreateSchema(Package pkg){
		val schema = Utils.getSchemaName(pkg)
		return 
		'''«IF schema !== null && schema != ""»
		CREATE SCHEMA «schema»;«ENDIF»
		'''
	}
	
	static def generateTable(Classifier clazz){
		val attributes = ClassifierUtils.getOwnedAttributes(clazz).filter(id |
			Utils.isSequence(id)
		)
		val schema = SqlClassifierUtils.generateSchemaName(clazz);
		'''
		
		CREATE TABLE «schema»«ClassifierUtils.getDBTableName(clazz)»(
			«clazz.generateExtendsId»
			«clazz.generateInterfaceAttributes(clazz)»
			«clazz.generateAttributes("", clazz, false)»«clazz.generateManyToOneRef»
		);
		«clazz.generateIds»
		
		«attributes.fold("")[acc, id |
			acc + '''«id.generateSequence»'''
		]»
		'''
	}
	
	/**
	 * génère les clés étrangères liées aux one to many
	 */
	static def generateManyToOneRef(Classifier clazz){
		val attributesRef = ClassifierUtils.getOneToManyAttributes(clazz)
		
		'''
		«attributesRef.fold("")[acc, attr |
			acc + ''',
«attr.generateAttrForeignKey(clazz, "")»'''
		]»
		'''	
	}
	
	static def generateAttrForeignKey(Property property, Classifier fromClass, String additionnalName){
		val dbPropertyName = PropertyUtils.getDatabaseName(property, property.name, "")
		val owner = property.owner as Classifier
		val id = ClassifierUtils.getId(owner).get(0)
		val idDbName = PropertyUtils.getDatabaseName(id, id.name, "")
		val fieldName = idDbName + "_" + Utils.toDbName(owner.name) + "_" + dbPropertyName
		
		val nullable = PropertyUtils.isNullable(property)
		
		'''
		«fieldName» «id.generateAttributType»«id.generateStringLength» «property.generateNullable(nullable)»
		'''
	}
	
	/**
	 * génère les tables d'attributs multiple et les foreign keys
	 */
	static def generateAlters(Classifier clazz){
		val schema = SqlClassifierUtils.generateSchemaName(clazz)
		'''
		«clazz.generateMultivaluedAttributesTable(clazz)»
		«clazz.generateForeignKeys("", ClassifierUtils.getDBTableName(clazz), schema)»
		«clazz.generateExtendsForeignKey()»
		«clazz.generateOneToManyForeignKey»
		'''
	}
	
	/**
	 * génère les alters liés aux champs one to many
	 */
	static def generateOneToManyForeignKey(Classifier clazz){
		val attributes = ClassifierUtils.getOneToManyAttributes(clazz)
		
		'''
		«attributes.fold("")[acc, attr |
			acc + '''«attr.generateAttributesAlterForeignKey(clazz, "")»'''
		]»
		'''	
	}
	
	static def generateAttributesAlterForeignKey(Property property, Classifier clazz, String additionnalName){
		val dbPropertyName = PropertyUtils.getDatabaseName(property, property.name, "")
		val owner = property.owner as Classifier
		val id = ClassifierUtils.getId(owner).get(0)
		val idDbName = PropertyUtils.getDatabaseName(id, id.name, "")
		val fieldName = idDbName + "_" + Utils.toDbName(owner.name) + "_" + dbPropertyName
		val schema = SqlClassifierUtils.generateSchemaName(clazz)
		val ownerSchema = SqlClassifierUtils.generateSchemaName(owner)
		'''
		
		ALTER TABLE ONLY «schema»«ClassifierUtils.getDBTableName(clazz)»
		    ADD CONSTRAINT «Utils.toDbName(clazz.name)»_«ClassifierUtils.getDBTableName(owner)»_«dbPropertyName»_ids_fkey
		    FOREIGN KEY («fieldName») REFERENCES «ownerSchema»«ClassifierUtils.getDBTableName(owner)»(«idDbName»);
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
		val name = id.name
		val propertyName = PropertyUtils.getDatabaseName(id, name, null)
		'''
		«propertyName» «id.generateAttributType»«id.generateStringLength» NOT NULL,
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
		val name = property.name
		var propertyName = PropertyUtils.getDatabaseName(property, name, additionnalName)
		'''«propertyName» «property.generateAttributType»«property.generateStringLength» NOT NULL'''
	}
	
	
	
	static def generateStringLength(Property property){
		val length = Utils.getAttributeLength(property)
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
		val name = property.name
		val propertyName = PropertyUtils.getDatabaseName(property, name, additionnalName)
		val type = property.type
		if(type instanceof Classifier){
			return '''«type.generateAttributes(propertyName, fromClass, nullable || PropertyUtils.isNullable(property))»'''
		}else{
			''''''
		}
	}
	
	/**
	 * génère la définition d'un attribut de type value enum
	 */
	static def generateEumAttributesDefinition(Property property, String additionnalName, Classifier fromClass, Boolean nullable){
		var name = property.name
		val propertyName = PropertyUtils.getDatabaseName(property, name, additionnalName)
		val type = property.type
		if(type instanceof Classifier){
			var sqlType = TypeUtils.getEnumType(type)
			'''code_«propertyName» «sqlType» «property.generateNullable(nullable)»'''
		}
	}
	
	/**
	 * génère la définition d'un attribut basique
	 */
	static def generateBasicAttributeDefinition(Property property, String additionnalName, Classifier fromClass, Boolean nullable){
		val name = property.name
		val propertyName = PropertyUtils.getDatabaseName(property, name, additionnalName)
		'''«propertyName» «property.generateAttributType»«property.generateStringLength» «property.generateNullable(nullable)»'''
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
					acc + ''',«property.generateEntityAttributeDefinition(id, additonnalName, fromClass, nullable)»'''
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
		val name = PropertyUtils.getName(property, property.name, "")
		val idName = PropertyUtils.getDatabaseName(id, id.name, additionnalName)
		val propertyName = PropertyUtils.getDatabaseName(property, name, idName)
		return 
		'''«propertyName» «id.generateAttributType»«id.generateStringLength» «property.generateNullable(nullable)»'''
	}
	
	/**
	 * génère la déclaration des ids
	 */
	static def generateIds(Classifier clazz){
		val ids = ClassifierUtils.getId(clazz)
		val name = ids.fold("")[acc, id |
			val name =PropertyUtils.getDatabaseName(id, id.name, null)
			if( acc != ""){
				acc + ''', «name»'''
			}else{
				acc + '''«name»'''
			}
		]
		val schema = SqlClassifierUtils.generateSchemaName(clazz)
		'''
		
		ALTER TABLE ONLY «schema»«ClassifierUtils.getDBTableName(clazz)»
			ADD CONSTRAINT «ClassifierUtils.getDBTableName(clazz)»_pkey PRIMARY KEY («name»);
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
				val name = PropertyUtils.getDatabaseName(id, id.name, null)
				if(acc != ""){
					acc + ''', «name»'''
				}else{
					acc + '''«name»'''
				}
			]
			val fromSchema =  SqlClassifierUtils.generateSchemaName(fromClass)
			val schema = SqlClassifierUtils.generateSchemaName(clazz)
			'''
			
			ALTER TABLE ONLY «fromSchema»«ClassifierUtils.getDBTableName(fromClass)»
			    ADD CONSTRAINT «ClassifierUtils.getDBTableName(fromClass)»_«Utils.toDbName(clazz.name)»_ids_fkey
			    FOREIGN KEY («idsName») REFERENCES «schema»«ClassifierUtils.getDBTableName(clazz)»(«idsName»);
			'''
		}else{
			''''''
		}
	}
	/**
	 * génère les clés étrangères
	 */
	static def generateForeignKeys(Classifier clazz, String additionnalName, String tableName, String fromSchema){
		val attributes = ClassifierUtils.getOwnedAttributes(clazz).filter[attribut |
			 (
				(Utils.isEntity(attribut.type) && !(attribut.multivalued)) || 
				(Utils.isValueObject(attribut.type) && !(attribut.multivalued)) ||
				(Utils.isNomenclature(attribut.type) && !(attribut.multivalued))	
			)
		]
				
		val interfaces = clazz.getAllUsedInterfaces
		'''«attributes.fold("")[acc, attribut|
			val attributName = PropertyUtils.getDatabaseName(attribut, attribut.name, additionnalName)
			if(Utils.isEntity(attribut.type)){
				acc + '''«attribut.generateForeignKey(additionnalName, tableName, fromSchema)»'''
			}else if(Utils.isValueObject(attribut.type)){
				acc + '''«(attribut.type as Classifier).generateForeignKeys(attributName, tableName, fromSchema)»'''
			}else if (Utils.isNomenclature(attribut.type)){
				acc + '''«attribut.generateEnumForeignKey(additionnalName, tableName, fromSchema)»'''
			}
		]»«interfaces.fold("")[acc, interface |
			acc + '''«interface.generateForeignKeys(additionnalName, tableName, fromSchema)»'''
		]»'''
	}
	
	/**
	 * génère une clé étrangère
	 */
	static def generateForeignKey(Property property, String additionnalName, String tableName, String schema){
		val fromClass = property.owner
		val toClass = property.type
		if(fromClass instanceof Classifier){
			if(toClass instanceof Classifier){
				val propName = PropertyUtils.getDatabaseName(property, property.name, additionnalName)
				val ids = ClassifierUtils.getId(toClass)
				var fieldToClass = ids.fold("")[acc, id |
					
					val name =   id.name
					val propertyName = PropertyUtils.getDatabaseName(id, name, additionnalName) +"_"+ PropertyUtils.getDatabaseName(property , property.name, null)
					if(acc !=""){
						acc + ''', «propertyName»'''
					}else{
						acc + '''«propertyName»'''
					}
				]
				
				var fieldInClass = ids.fold("")[acc, field |
					
					val name =  field.name
					val propertyName = PropertyUtils.getDatabaseName(field, name, null)
					if(acc !=""){
						acc + ''', «propertyName»'''
					}else{
						acc + '''«propertyName»'''
					}
				]
				
				val toschema =  SqlClassifierUtils.generateSchemaName(toClass)
				return
				'''
				
				ALTER TABLE ONLY «schema»«tableName»
				    ADD CONSTRAINT «tableName»_«propName»_ids_fkey
				    FOREIGN KEY («fieldToClass») REFERENCES «toschema»«ClassifierUtils.getDBTableName(toClass)»(«fieldInClass»);
				'''
			}
		}
		return ''''''
	}
	
	/**
	 * génère une clé étrangère liée à un attribut de type enum
	 */
	static def generateEnumForeignKey(Property property, String additionnalName, String tableName, String schema){
		val type = property.type as Classifier
		val propertyName = PropertyUtils.getDatabaseName(property, property.name, additionnalName)
		val owner = property.owner as Classifier 
		val typeSchema =  SqlClassifierUtils.generateSchemaName(type)
		'''
		
		ALTER TABLE ONLY «schema»«tableName»
		    ADD CONSTRAINT «tableName»_«propertyName»_code_fkey
		    FOREIGN KEY (code_«propertyName») REFERENCES «typeSchema»«ClassifierUtils.getDBTableName(type as Classifier)»(code);
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
		val propertyName = PropertyUtils.getDatabaseName(property, property.name, null)
		val tableName =  ClassifierUtils.getDBTableName(fromClass) + "_" + propertyName
		val ids = ClassifierUtils.getId(fromClass) 
		val idsName = ids.fold("")[acc, id |
			val idName = PropertyUtils.getDatabaseName(id, id.name, null)
			if(acc != ""){
				acc + ''', «idName»'''
			}else{
				acc + '''«idName»'''
			}
		]
		
		val schema =  SqlClassifierUtils.generateSchemaName(fromClass)
		'''
		
		CREATE TABLE «schema»«tableName»(
			«propertyName» «property.generateAttributType»«property.generateStringLength» NOT NULL,
			«ids.fold("")[acc, id |
				if(acc != ""){
					acc + ''',
					''' + '''«id.generateIdAttributeDefinition("")»'''
				}else{
					acc + '''«id.generateIdAttributeDefinition("")»'''
				}
			]»
		);
		
		ALTER TABLE ONLY «schema»«tableName»
		    ADD CONSTRAINT «tableName»_ids_fkey
		    FOREIGN KEY («idsName») REFERENCES «schema»«ClassifierUtils.getDBTableName(fromClass)»(«idsName»);
		    
		ALTER TABLE ONLY «schema»«tableName»
		    ADD CONSTRAINT «tableName»_pkey PRIMARY KEY(«idsName», «propertyName»);
		'''
		
	}
	
	/**
	 * génère une table d'association pour un attribut de type entity multivalué
	 */
	static def generateMutilvaluedEntityTable(Property property, Classifier fromClass){
		val type = property.type
		val association = property.association
		
		if(type instanceof Classifier){
			val fieldName = PropertyUtils.getDatabaseName(property, property.name, null)
			val tableName = ClassifierUtils.getDBTableName(fromClass) +"_" + fieldName
			val idsProps = ClassifierUtils.getId(type) 
			val idsOwner = ClassifierUtils.getId(fromClass) 
			val idsOwnerName = idsOwner.fold("")[acc, id |
				val propertyName = PropertyUtils.getDatabaseName(id, id.name, null)
				if(acc != ""){
					acc + ''', «propertyName»_«Utils.toDbName(fromClass.name)»'''
				}else{
					acc + '''«propertyName»_«Utils.toDbName(fromClass.name)»'''
				}
			]
			val idsOwnerBaseName = idsOwner.fold("")[acc, id |
				val propertyName = PropertyUtils.getDatabaseName(id, id.name, null)
				if(acc != ""){
					acc + ''', «propertyName»'''
				}else{
					acc + '''«propertyName»'''
				}
			]
			
			val idsPropsName = idsProps.fold("")[acc, id |
				val propertyName = PropertyUtils.getDatabaseName(id, id.name, null)
				if(acc != ""){
					acc + ''', «propertyName»_«fieldName»'''
				}else{
					acc + '''«propertyName»_«fieldName»'''
				}
			]
			
			val idsPropsBaseName = idsProps.fold("")[acc, id |
				val propertyName = PropertyUtils.getDatabaseName(id, id.name, null)
				if(acc != ""){
					acc + ''', «propertyName»'''
				}else{
					acc + '''«propertyName»'''
				}
			]
			val schema =  SqlClassifierUtils.generateSchemaName(fromClass)
			val typeSchema =  SqlClassifierUtils.generateSchemaName(type)
			val data =
			'''
			
			CREATE TABLE «schema»«tableName»(
				«idsProps.fold("")[acc, id |
					if(acc != ""){
						acc + ''',
						''' + '''«id.generateMultiIdAttributeDef(fieldName)»'''
					}else{
						acc + '''«id.generateMultiIdAttributeDef(fieldName)»'''
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
			
			ALTER TABLE ONLY «schema»«tableName»
			    ADD CONSTRAINT «tableName»_«Utils.toDbName(fromClass.name)»_ids_fkey
			    FOREIGN KEY («idsOwnerName») REFERENCES «schema»«ClassifierUtils.getDBTableName(fromClass)»(«idsOwnerBaseName»);
			    
			ALTER TABLE ONLY «schema»«tableName»
			    ADD CONSTRAINT «tableName»_«Utils.toDbName(type.name)»_ids_fkey
			    FOREIGN KEY («idsPropsName») REFERENCES «typeSchema»«ClassifierUtils.getDBTableName(type)»(«idsPropsBaseName»);
			    
			ALTER TABLE ONLY «schema»«tableName»
			    ADD CONSTRAINT «tableName»_pkey PRIMARY KEY(«idsOwnerName», «idsPropsName»);
			'''
			if(association !== null){
				 val member = association.memberEnds.filter[mem |
						mem.type == fromClass
				]
				if(member !== null){
					val end = member.get(0);
					
					if(end.isMultivalued){
						return data
					}
				}
				return ''''''
			}else{
					return data
			}
		
		}
	}
	
	/**
	 * génère la définition d'un attribut id
	 */
	static def generateMultiIdAttributeDef(Property property, String additionnalName){
		val propertyName = PropertyUtils.getDatabaseName(property, property.name, "")
		val owner = property.owner
		if(owner instanceof Classifier){
			if(additionnalName != "" && additionnalName !== null){
				'''«propertyName»_«additionnalName» «property.generateAttributType»«property.generateStringLength» NOT NULL'''
			}else{
				'''«propertyName» «property.generateAttributType»«property.generateStringLength» NOT NULL'''
			}
		}
	}
	
	/**
	 * génère la définition d'un attribut id
	 */
	static def generateMultiIdAttributeDefinition(Property property, String additionnalName){
		val name = property.name
		val propertyName = PropertyUtils.getDatabaseName(property, name, additionnalName)
		val owner = property.owner
		if(owner instanceof Classifier){
			'''«propertyName»_«Utils.toDbName(owner.name)» «property.generateAttributType»«property.generateStringLength» NOT NULL'''
		}
	}
	
	static def generateValueObjectEntityTable(Property property, Classifier fromClass){
		val type = property.type
		if(type instanceof Classifier){
			val name = PropertyUtils.getDatabaseName(property, property.name, null)
			val prefix = ClassifierUtils.getDBTableName(fromClass)
			val tableName = prefix + "_" + name 
			
			val idsOwner = ClassifierUtils.getId(fromClass) 
			val idsName = idsOwner.fold("")[acc, id |
				val idName = PropertyUtils.getDatabaseName(id, id.name, null)
				if(acc != ""){
					acc + ''', «idName»'''
				}else{
					acc + '''«idName»'''
				}
			]
			val attr = Utils.getListStringComma(type.getAttributList(newArrayList(), name))
			var pkeys = idsName 
			if(attr !== null && attr != ""){
				pkeys += ', ' + Utils.getListStringComma(type.getAttributList(newArrayList(), name))
			}
			val schema =  SqlClassifierUtils.generateSchemaName(fromClass)
			'''
			
			CREATE TABLE «schema»«tableName»(
				«type.generateAttributes(name, fromClass, false)»,
				«idsOwner.fold("")[acc, id |
					if(acc != ""){
						acc + ''',
						''' + '''«id.generateIdAttributeDefinition("")»'''
					}else{
						acc + '''«id.generateIdAttributeDefinition("")»'''
					}
				]»
			);
			«type.generateForeignKeys(name, tableName, schema)»
			
			ALTER TABLE ONLY «schema»«tableName»
			    ADD CONSTRAINT «tableName»_ids_fkey
			    FOREIGN KEY («idsName») REFERENCES «schema»«ClassifierUtils.getDBTableName(fromClass)»(«idsName»);
			    
			ALTER TABLE ONLY «schema»«tableName»
			    ADD CONSTRAINT «tableName»_pkey PRIMARY KEY(«pkeys»);
			
			'''	
		}	
	}
	
	static def getAttributList(Classifier type, ArrayList<String> names, String additionnalName){
		val attributes = ClassifierUtils.getNotMultivaluedOwnedAttributes(type)
		attributes.forEach[attribut |
			if(!PropertyUtils.isNullable(attribut)){
				val attrType = attribut.type
				//val name = Utils.toSnakeCase(Utils.addAdditionnalName(additionnalName, attribut.name))
				val propertyName = PropertyUtils.getDatabaseName(attribut, attribut.name, additionnalName)
				if(Utils.isEntity(attrType)){
					val ids = ClassifierUtils.getId(attrType as Classifier) 
					ids.forEach[id |
						val idpropName = PropertyUtils.getDatabaseName(id, id.name, additionnalName)
						val propName = PropertyUtils.getDatabaseName(attribut, attribut.name, null)
						var idName = idpropName + "_" + propName
						names.add(idName)
					]
				}else if(Utils.isNomenclature(attrType)){
					names.add("code_" + propertyName)
				}else if(Utils.isValueObject(attrType)){
					//val voName = Utils.addAdditionnalName(additionnalName, propertyName)
					(attrType as Classifier).getAttributList(names, propertyName)
				}else{
					names.add(propertyName)
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
			val propertyName = PropertyUtils.getDatabaseName(property, property.name, null)
			val tableName = ClassifierUtils.getDBTableName(fromClass) + "_" + propertyName
			
			val idsOwner = ClassifierUtils.getId(fromClass) 
			val idsName = idsOwner.fold("")[acc, id |
				val idName= PropertyUtils.getDatabaseName(id, id.name, null)
				if(acc != ""){
					acc + ''', «idName»'''
				}else{
					acc + '''«idName»'''
				}
			]
			
			val sqlType = TypeUtils.getEnumType(type)
			val schema =  SqlClassifierUtils.generateSchemaName(fromClass)
			val typeSchema =  SqlClassifierUtils.generateSchemaName(type)
			'''
			
			CREATE TABLE «schema»«tableName»(
				code «sqlType» NOT NULL,
				«idsOwner.fold("")[acc, id |
					if(acc != ""){
						acc + ''',
						''' + '''«id.generateIdAttributeDefinition("")»'''
					}else{
						acc + '''«id.generateIdAttributeDefinition("")»'''
					}
				]»
			);«type.generateForeignKeys(Utils.toDbName(property.name), tableName, schema)»
			
			ALTER TABLE ONLY «schema»«tableName»
			    ADD CONSTRAINT «tableName»_ids_fkey
			    FOREIGN KEY («idsName») REFERENCES «schema»«ClassifierUtils.getDBTableName(fromClass)»(«idsName»);
			
			ALTER TABLE ONLY «schema»«tableName»
			    ADD CONSTRAINT «tableName»_code_fkey
			    FOREIGN KEY (code) REFERENCES «typeSchema»«ClassifierUtils.getDBTableName(type)»(code);
			
			ALTER TABLE ONLY «schema»«tableName»
			    ADD CONSTRAINT «tableName»_pkey PRIMARY KEY («idsName», code);
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
			val startWith = Utils.getSequenceStartWith(property)
			val inscrementBy = Utils.getSequenceIncrementBy(property)
			
			val hasMaxValue = Utils.getSequenceHasMaxValue(property)
			val maxValue = Utils.getSequenceMaxValue(property)
			if(hasMaxValue == true || maxValue!= 0 ){
				maxVal = '''MAXVALUE «maxValue»'''
			}
			val hasMinValue = Utils.getSequenceHasMinValue(property)
			val minValue = Utils.getSequenceMinValue(property)
			if(hasMinValue == true || minValue != 0){
				minVal = '''MINVALUE «minValue»'''
			}
			
			val cache = Utils.getSequenceCache(property)
			val isCycle = Utils.getSequenceCycle(property)
			var cycle = "NO CYCLE"
			if(isCycle == true){
				cycle = "CYCLE"
			}
			val propertyName = PropertyUtils.getDatabaseName(property, property.name, null)
			val name = ClassifierUtils.getDBTableName(owner)  + "_" + propertyName
			
			val schema =  SqlClassifierUtils.generateSchemaName(owner)
			val schemaseq =  SqlClassifierUtils.generateSchemaName(owner)
			'''
			CREATE SEQUENCE «schema»«name»_seq
			    START WITH «startWith»
			    INCREMENT BY «inscrementBy»
			    «maxVal»
			    «minVal»
			    CACHE «cache»
			    «cycle»;
			    
			ALTER SEQUENCE «schema»«name»_seq
				OWNED BY «schema»«ClassifierUtils.getDBTableName(owner)».«propertyName»;
			
			ALTER TABLE ONLY «schema»«ClassifierUtils.getDBTableName(owner)» 
				ALTER COLUMN «propertyName» 
				SET DEFAULT nextval('«schemaseq»«name»_seq'::regclass);
			'''
		}
	}
	
	static def generateAssociationTable(AssociationClass clazz){
		val schema = SqlClassifierUtils.generateSchemaName(clazz)
		'''
		
		CREATE TABLE «schema»«Utils.toDbName(clazz.name)»(
			«clazz.generateAssociationAttributes("", clazz)»
		);
		«clazz.generateAssociationForeignKeys()»
		
		ALTER TABLE ONLY «schema»«Utils.toDbName(clazz.name)»
			ADD CONSTRAINT «Utils.toDbName(clazz.name)»_pkey PRIMARY KEY («Utils.getListComma(clazz.getAssociationAttributList(newArrayList(), ""))»);
		'''
	}
	
	static def getAssociationAttributList(AssociationClass type, ArrayList<String> names, String additionnalName){
		val attributes = type.memberEnds
		attributes.forEach[attribut |
			val attrType = attribut.type
			//val name = Utils.toSnakeCase(Utils.addAdditionnalName(additionnalName, attribut.name))
			val propertyName = PropertyUtils.getDatabaseName(attribut, attribut.name, additionnalName)
			if(Utils.isEntity(attrType)){
				val ids = ClassifierUtils.getId(attrType as Classifier) 
				ids.forEach[id |
					val idpropName = PropertyUtils.getDatabaseName(id, id.name, additionnalName)
					var idName = idpropName + "_" +Utils.toDbName(attribut.name)
					names.add(idName)
				]
			}else if(Utils.isNomenclature(attrType)){
				names.add("code_" + propertyName)
			}else if(Utils.isValueObject(attrType)){
				//val voName = Utils.addAdditionnalName(additionnalName, attribut.name)
				(attrType as Classifier).getAttributList(names, propertyName)
			}else{
				names.add(propertyName)
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
		val schema =  SqlClassifierUtils.generateSchemaName(fromClass)
		if(type instanceof Classifier){
			if(Utils.isEntity(type)){
				return '''«property.generateAssociationForeignKeysEntity(fromClass)»'''
			}else if (Utils.isValueObject(type)){
				return '''«type.generateForeignKeys(Utils.toDbName(property.name), ClassifierUtils.getDBTableName(fromClass), schema)»'''
			}else if(Utils.isNomenclature(type)){
				return '''«property.generateAssociationForeignKeysEnum(fromClass, schema)»'''
			}
		}
		''''''
	}
	
	static def generateAssociationForeignKeysEntity(Property property, Classifier fromClass){
		val type = property.type
		if(type instanceof Classifier){
			val ids = ClassifierUtils.getId(type)
			val idsName = ids.fold("")[acc, id |
				val idName = PropertyUtils.getDatabaseName(id, id.name, null)
				if(acc != ""){
					acc + ''', «idName»'''
				}else{
					acc + '''«idName»'''
				}
			]
			
			val idsNameInClass = ids.fold("")[acc, id |
				val idName = PropertyUtils.getDatabaseName(id, id.name, null)
				val propName = PropertyUtils.getDatabaseName(property, property.name, idName)
				if(acc != ""){
					acc + ''', «propName»'''
				}else{
					acc + '''«propName»'''
				}
			]
			val propName = PropertyUtils.getDatabaseName(property, property.name, null)
			
			val schema =  SqlClassifierUtils.generateSchemaName(fromClass)
			val typeSchema =  SqlClassifierUtils.generateSchemaName(type)
			return '''
			
			ALTER TABLE ONLY «schema»«ClassifierUtils.getDBTableName(fromClass)»
				ADD CONSTRAINT «ClassifierUtils.getDBTableName(fromClass)»_«propName»_ids_fkey
				FOREIGN KEY («idsNameInClass») REFERENCES «typeSchema»«ClassifierUtils.getDBTableName(type)»(«idsName»);
			'''
		}
		return ''''''
		
	}
	
	static def generateAssociationForeignKeysEnum(Property property, Classifier fromClass, String schema){
		val type = property.type
		if(type instanceof Classifier){
			val propName = "code_" + PropertyUtils.getDatabaseName(property, property.name, null)
			val typeSchema =  SqlClassifierUtils.generateSchemaName(type)
			return '''
			
			ALTER TABLE ONLY «schema»«ClassifierUtils.getDBTableName(fromClass)»
				ADD CONSTRAINT «ClassifierUtils.getDBTableName(fromClass)»_«propName»_code_fkey
				FOREIGN KEY («propName») REFERENCES «typeSchema»«ClassifierUtils.getDBTableName(type)»(code);
			'''
		}
		return ''''''
		
	}
	
	static def generateAttributType(Property property){
		var type = TypeUtils.getDatabaseType(property.type)
		if(type == 'character'){
			val length = Utils.getAttributeLength(property)
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
		val schema = SqlClassifierUtils.generateSchemaName(clazz);
		val attributes = ClassifierUtils.getOwnedAttributes(clazz);
		'''
		
		CREATE TABLE «schema»«ClassifierUtils.getDBTableName(clazz)»(
			code «sqlType» NOT NULL,
			libelle text
		);
		
		ALTER TABLE ONLY «schema»«ClassifierUtils.getDBTableName(clazz)»
			ADD CONSTRAINT «ClassifierUtils.getDBTableName(clazz)»_pkey PRIMARY KEY (code);
		«IF !hasCode»
		
		CREATE SEQUENCE «schema»«ClassifierUtils.getDBTableName(clazz)»_code_seq
		    START WITH 1
		    INCREMENT BY 1
		    NO MAXVALUE
		    NO MINVALUE
		    CACHE 1
		    NO CYCLE;
		    
		ALTER SEQUENCE «schema»«ClassifierUtils.getDBTableName(clazz)»_code_seq
			OWNED BY «schema»«ClassifierUtils.getDBTableName(clazz)».code;
		«ENDIF»
		
		«attributes.fold("")[acc, att |
			acc + '''«att.generateInsertValue(clazz)»'''
		]»
		'''
	}
	
	static def generateInsertValue(Property prop, Classifier owner){
		val schema = SqlClassifierUtils.generateSchemaName(owner);
		val hasCode = ClassifierUtils.isEnumWithCode(owner);
		val code = Utils.getNomenclatureCode(prop)
		var libelle = Utils.getNomenclatureLibelle(prop)
		if(!hasCode){
			'''
			INSERT INTO «schema»«ClassifierUtils.getDBTableName(owner)» (LIBELLE) VALUES («libelle»);
			'''
		}else{
			'''
			INSERT INTO «schema»«ClassifierUtils.getDBTableName(owner)» (CODE, LIBELLE) VALUES («code», '«libelle»');
			'''
		}
	}
	

}