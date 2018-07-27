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
 * fr.gouv.diplomatie.papyrus.codegen.typescript - Générateur de code typescript pour 
 * des applications Hornet JS
 *
 * @author MEAE - Ministère de l'Europe et des Affaires étrangères
 * @version v1.1.3
 * @license CECILL-2.1
 */
package fr.gouv.diplomatie.papyrus.codegen.typescript.xtend.classifier;

import org.eclipse.uml2.uml.Classifier
import org.eclipse.uml2.uml.Property
import fr.gouv.diplomatie.papyrus.codegen.core.utils.ClassifierUtils
import fr.gouv.diplomatie.papyrus.codegen.core.utils.PropertyUtils
import fr.gouv.diplomatie.papyrus.codegen.typescript.utils.TypeUtils
import fr.gouv.diplomatie.papyrus.codegen.core.utils.Utils
import org.eclipse.uml2.uml.PrimitiveType
import org.eclipse.uml2.uml.Interface

public class ClassifierModelGenerator {
	
	static def generateCode(Classifier clazz){
		'''
		import Sequelize = require("sequelize");
		«clazz.generateImports»
		
		export const «ClassifierUtils.getModelName(clazz)»: Sequelize.DefineAttributes = {
		    «clazz.generateExtendsId»
		    «clazz.generateInterfacesAttributes»
		    «clazz.generateAttributes("", "", clazz, false, false)»
		};
		«clazz.generateMultivaluedAttributeModels»
		««««clazz.generateOneToManyAttribute»
		'''
	}
	
	static def generateOneToManyAttribute(Classifier clazz){
		val attributes = ClassifierUtils.getOneToManyAttributes(clazz)
		
		'''
		«attributes.fold("")[acc, attr |
			acc + '''«attr.generateOneToManyAttribute(clazz)»'''
		]»
		'''
	}
	
	static def generateOneToManyAttribute(Property property, Classifier clazz){
		val dbPropertyName = PropertyUtils.getDatabaseName(property, property.name, "")
		val owner = property.owner as Classifier
		val id = ClassifierUtils.getId(owner).get(0)
		val idDbName = PropertyUtils.getDatabaseName(id, id.name, "")
		val fieldName = idDbName + "_" + Utils.toDbName(owner.name) + "_" + dbPropertyName
		val name = Utils.getFirstToLowerCase(owner.name) + Utils.getFirstToUpperCase(property.name)
		'''
	    ,«name»: {
	        type: Sequelize.«TypeUtils.getSequelizeType(id.type)»«id.generateAttributeTypeLength»,
	        field: "«fieldName»",
	        references: {
	            model: "«ClassifierUtils.getModelName(owner)»",
	            key: "«id.name»",
	        },
	    },
		'''
	}
	
	/**
	 * génère les imports
	 */
	static def generateImports(Classifier clazz){
		''''''
	}
	
	/**
	 * génère les id de généralisations
	 */
	static def generateExtendsId(Classifier clazz){
		val extends = clazz.generalizations
		if(extends !== null && !extends.empty){
			val extend = extends.get(0)
			val ids = ClassifierUtils.getId(extend.general)
			val id = ids.get(0)
			val name = PropertyUtils.getDatabaseName(id, id.name, "")
			'''
			«id.name»: {
			    type: Sequelize.«TypeUtils.getSequelizeType(id.type)»«id.generateAttributeTypeLength»,
			    field: "«name»",
			    allowNull: «PropertyUtils.isNullable(id)»,
			    references: {
			        model: "«ClassifierUtils.getModelName(extend.general)»",
			        key: "«id.name»",
			    },
			},
			'''
		}
		
	}
	
	static def generateInterfacesAttributes(Classifier clazz){
		val interfaces = clazz.directlyRealizedInterfaces
		if(interfaces !== null && !interfaces.empty){
			'''
			«interfaces.fold("")[acc, interface |
				acc + '''«interface.generateAttributes("", "", clazz, false, false)»'''
			]»
			'''
		}else{
			''''''
		}
		
	}
	
	/**
	 * Génère les attributs de la classe
	 */
	static def generateAttributes(Classifier clazz, String additionnalName, String databaseAddName, Classifier fromClass, Boolean isPrimaryKey, Boolean nullable){
		val attributes = ClassifierUtils.getNotMultivaluedOwnedAttributes(clazz)
		'''«attributes.fold("")[acc, attribut|
			if(acc != ""){
 				acc + '''
 				''' + '''«attribut.generateAttributDefinition(additionnalName, databaseAddName, fromClass, isPrimaryKey, nullable)»'''
 			}else{
 				acc + '''«attribut.generateAttributDefinition(additionnalName, databaseAddName, fromClass, isPrimaryKey, nullable)»'''
 			}		
 		]»'''
		
	}
	
	/**
	 * génère un attribut
	 */
	static def generateAttributDefinition(Property property, String additionalName, String databaseAddName, Classifier fromClass, Boolean isPrimaryKey, Boolean nullable){
		if(PropertyUtils.isID(property)){
			'''«property.generateIdAttributeDefinition(additionalName, fromClass, nullable)»'''
		}else{
			'''«property.generateNIdAttributeDefinition(additionalName, databaseAddName, fromClass, isPrimaryKey, nullable)»'''
		}
	}
	
	/**
	 * génère la définition d'un attribut id
	 */
	static def generateIdAttributeDefinition(Property property, String additionnalName, Classifier fromClass, Boolean nullable){
		val name = PropertyUtils.getDatabaseName(property, property.name, additionnalName)
		val isSequence = Utils.isSequence(property)
		var isNullable = PropertyUtils.isNullable(property)
		if(nullable){
			isNullable = true
		}
		var auto = ""
		if(isSequence){
			auto = '''
			
			autoIncrement: true,
			'''
		}
		'''
		«property.name»: {
		    type: Sequelize.«TypeUtils.getSequelizeType(property.type)»«property.generateAttributeTypeLength»,«auto»
		    field: "«name»",
		    allowNull: «isNullable»,
		    primaryKey: true,
		},
		'''
	}
	
	/**
	 * génère la définition d'un attribut qui n'est pas un id
	 */
	static def generateNIdAttributeDefinition(Property property, String additionnalName, String databaseAddName, Classifier fromClass, Boolean isPrimaryKey, Boolean nullable){
		if(PropertyUtils.isClassAttribute(property)){
			'''«property.generateClassAttributeDefinition(additionnalName, databaseAddName, fromClass, isPrimaryKey, nullable)»'''
		}else{
			'''«property.generateBasicAttributeDefinition(additionnalName, databaseAddName, fromClass, isPrimaryKey, nullable)»'''
		}
	}
	
	/**
	 * génère la définition d'un attribut lié a la classe
	 */
	static def generateClassAttributeDefinition(Property property, String additionnalName, String databaseAddName, Classifier fromClass,Boolean isPrimaryKey, Boolean nullable){
		if(Utils.isValueObject(property.type)){
			'''«property.generateValueObjectAttributeDefinition(additionnalName, databaseAddName, fromClass, isPrimaryKey, nullable || PropertyUtils.isNullable(property))»'''
		}else if (Utils.isNomenclature(property.type)){
			'''«property.generateEnumAttributeDefinition(additionnalName, databaseAddName, fromClass, isPrimaryKey, nullable)»'''
		}else{
			'''«property.generateEntityAttributesDefinition(additionnalName, databaseAddName, fromClass, isPrimaryKey, nullable)»'''
		}
	}
	
	/**
	 * génère la définition d'un attribut de type value Object
	 */
	static def generateValueObjectAttributeDefinition(Property property, String additionnalName, String databaseAddName, Classifier fromClass, Boolean isPrimaryKey, Boolean nullable){
		val name = Utils.addAdditionnalName(additionnalName, property.name)
		val propName = PropertyUtils.getDatabaseName(property, property.name, databaseAddName)
		val type = property.type
		if(type instanceof Classifier){
			return '''«type.generateAttributes(name, propName, fromClass, isPrimaryKey, nullable && PropertyUtils.isNullable(property))»'''
			
		}else{
			''''''
		}
	}
	
	/**
	 * génère la définition d'un attribut de type entity
	 */
	static def generateEntityAttributesDefinition(Property property, String additonnalName, String dbAddName, Classifier fromClass, Boolean isPrimaryKey, Boolean nullable){
		val type = property.type
		if(type instanceof Classifier){
			val ids = ClassifierUtils.getId(type)
			ids.fold("")[acc , id |
				if(acc != ""){
					acc + '''«property.generateEntityAttributeDefinition(id, additonnalName, dbAddName, fromClass, isPrimaryKey, nullable)»'''
				}else{
					acc + '''«property.generateEntityAttributeDefinition(id, additonnalName, dbAddName, fromClass, isPrimaryKey, nullable)»'''
				}
			]
		}else{
			''''''
		}
		
	}
	
	static def generateEnumAttributeDefinition(Property property, String additionnalName, String databaseAddName, Classifier fromClass, Boolean isPrimaryKey, Boolean nullable){
		val type = property.type
		val sqlType = TypeUtils.getEnumSequelizeType(type as Classifier)
		val name = PropertyUtils.getDatabaseName(property, property.name, databaseAddName)
		val propName = Utils.addAdditionnalName(additionnalName, property.name)
		var isNullable = PropertyUtils.isNullable(property)
		if(nullable){
			isNullable = true
		}
		var pk = '''primaryKey: true,'''
		return '''
			code«Utils.getFirstToUpperCase(propName)»: {
			    type: Sequelize.«sqlType»,
			    field: "code_«name»",
			    allowNull: «isNullable»,«IF isPrimaryKey && !PropertyUtils.isNullable(property)»
			    «pk»«ENDIF»
			    references: {
			        model: "«ClassifierUtils.getModelName(type as Classifier)»",
			        key: "code",
			    },
			},
			'''
	}
	
	/**
	 * génère la définition d'un attribut de type entity
	 */
	static def generateEntityAttributeDefinition(Property property, Property id,  String additionnalName, String dbAddName, Classifier fromClass, Boolean isPrimaryKey, Boolean nullable){
		val type = property.type
		val propName = Utils.addAdditionnalName(additionnalName, id.name) + Utils.getFirstToUpperCase(property.name)
		val dbName = PropertyUtils.getDatabaseName(id, id.name, dbAddName)+ "_" +PropertyUtils.getDatabaseName(property, property.name, "")
		var pk = '''primaryKey: true,'''
		var isNullable = PropertyUtils.isNullable(property)
		if(nullable){
			isNullable = true
		}
		return '''
			«propName»: {
			    type: Sequelize.«TypeUtils.getSequelizeType(id.type)»«id.generateAttributeTypeLength»,
			    field: "«dbName»",
			    allowNull: «isNullable»,«IF isPrimaryKey && !PropertyUtils.isNullable(property)»
			    «pk»«ENDIF»
			    references: {
			        model: "«ClassifierUtils.getModelName(type as Classifier)»",
			        key: "«id.name»",
			    },
			},
			'''
	}
	
	/**
	 * génère la définition d'un attribut basique
	 */
	static def generateBasicAttributeDefinition(Property property, String additionnalName, String dbAddName, Classifier fromClass, Boolean isPrimaryKey, Boolean nullable){
		var name = PropertyUtils.getDatabaseName(property, property.name,dbAddName )
		var propName = Utils.addAdditionnalName(additionnalName, property.name)
		var pk = '''primaryKey: true,'''
		var isNullable = PropertyUtils.isNullable(property)
		if(nullable){
			isNullable = true
		}
		'''
		«propName»: {
		    type: «property.getAttributeSequelizeTypeDeclaration»«property.generateNIdAttributeDefaultValue»,
		    field: "«name»",«IF isPrimaryKey && !isNullable»
		    «pk»«ENDIF»
		    allowNull: «isNullable»,
		},
		'''
	}
	
	/**
	 * génère la déclaration de la taille du champs 
	*/
	static def generateAttributeTypeLength(Property property){
		val length = Utils.getAttributeLength(property)
		if(length !== null && length != 0){
			'''(«length»)'''
		}else{
			''''''
		}
	}

	
	/**
	 * génère la déclaration de la valeur par defaut
	 * TODO : à refaire
	 */
	static def generateNIdAttributeDefaultValue(Property property){
		val value = PropertyUtils.getDefaultValue(property)
		if(value !== null){
			''',
			''' + '''defaultValue: "«value»"'''
		}else{
			''''''
		}
	}
	
	/**
	 * génère les attributs liés aux liens multivalués du model 
	 */
	static def generateMultiValuedEntityAttributes(Classifier clazz, Boolean hasAttributes, String additionnalName){
		val references = ClassifierUtils.getAllReferencesTo(clazz).filter[attribut|
			if(attribut.association !== null){
				var member = attribut.association.ownedEnds.get(0)
				return (!member.multivalued) 
			}else{
				return false
			}
		]
		return
		'''
		«references.fold("")[acc, ref |
			if(!hasAttributes && acc==""){
				acc + '''«ref.generateReferenceAttributes(clazz, additionnalName)»'''
			}else{
				acc + '''«ref.generateReferenceAttributes(clazz, additionnalName)»'''
			}
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
		val toAttribute = property.association.ownedEnds.get(0)
		val fromClass = toAttribute.type
		if(fromClass instanceof Classifier ){
			if(!(toAttribute.multivalued)){
				val ids = ClassifierUtils.getId(fromClass)
				'''«ids.fold("")[acc, id |
					if(acc != ""){
						acc + '''
						''' + '''«property.generateReferenceAttributeAssocation(id, clazz, additionnalName)»'''
					}else{
						acc + '''«property.generateReferenceAttributeAssocation(id, clazz, additionnalName)»'''
					}
				]»'''
			}
		}else {
			''''''
		}
	}
	
	/**
	 *  génère l'attribut lié au lien multivalué du model  - id
	 */
	static def generateReferenceAttributeAssocation(Property property, Property id, Classifier clazz, String additionnalName){
		var field =  property.association.ownedEnds.get(0)
		val fromClass = field.type
		val name = id.name + Utils.getFirstToUpperCase(field.name)
		var fieldName = Utils.addAdditionnalName(additionnalName, name)
		'''
		«fieldName»: {
		    type: «id.getAttributeSequelizeTypeDeclaration»,
		    field: "«fieldName»",
		    allowNull: «PropertyUtils.isNullable(property)»,
		    references: {
		        model: "«ClassifierUtils.getModelName(fromClass as Classifier)»",
		        key: "«id.name»",
		    },
		},
		'''
	}
	
	static def generateReferenceAttributesNA(Property property, Classifier clazz, String additionnalName){
		val fromClass = property.owner
		if(fromClass instanceof Classifier){
			val ids = ClassifierUtils.getId(fromClass)
			'''«ids.fold("")[acc, id |
				if(acc != ""){
					acc + '''
					''' + '''«property.generateReferenceAttributeNA(id, clazz, additionnalName)»'''
				}else{
					acc + '''«property.generateReferenceAttributeNA(id, clazz, additionnalName)»'''
				}
			]»'''
		}
	}
	
	static def generateReferenceAttributeNA(Property property, Property id, Classifier clazz, String additionnalName){
		val fromClass = property.owner
		if(fromClass instanceof Classifier){
			val name = property.name + Utils.getFirstToUpperCase(id.name)
			val fieldName = Utils.addAdditionnalName(additionnalName, name)
			'''
			«fieldName»: {
			    type: «property.getAttributeSequelizeTypeDeclaration»,
			    field: "«fieldName»",
			    allowNull: «PropertyUtils.isNullable(property)»,
			    references: {
			        model: "«ClassifierUtils.getModelName(fromClass as Classifier)»",
			        key: "«id.name»",
			    },
			},
			'''
		}
	}

	/**
	 * génère les models liés aux attributs multi valués 
	 */
	static def generateMultivaluedAttributeModels(Classifier clazz){
		val attributes = ClassifierUtils.getMultivaluedOwnedAttributes(clazz)
		val primitiveAttributes = attributes.filter[attribut |
			val type = attribut.type
			return ( type instanceof PrimitiveType)
		]
		
		val ownedAttributes = ClassifierUtils.getOwnedAttributes(clazz)

		val multiAttributes = ownedAttributes.filter[attribut |
			val type = attribut.type
			val association = attribut.association
			if(association !== null){
				val members = association.memberEnds
				val firstEnd = members.filter[member | member.type == clazz].get(0)
				var secondEnd = firstEnd
				val secondEndMembers = members.filter[member | member.type !== clazz]
 				if(secondEndMembers === null || secondEndMembers.empty){
					secondEnd = members.filter[member | member.type == clazz].get(1)
				}else{
					secondEnd = secondEndMembers.get(0)
				}
				return (Utils.isEntity(type) && (attribut.multivalued) && (firstEnd.isMultivalued) && (secondEnd.isMultivalued))
			}else{
				return Utils.isEntity(type) && (attribut.multivalued)
			} 
		]
		
		val multivaluedEnums = ownedAttributes.filter[attribut |
			val type = attribut.type
			return (Utils.isNomenclature(type) && (attribut.multivalued))
		]
		
		val multivaluedValueObject = ownedAttributes.filter[attribut |
			val type = attribut.type
			return (Utils.isValueObject(type) && (attribut.multivalued))
		]
		
		val interfaces = clazz.directlyRealizedInterfaces
		
		return '''
		«primitiveAttributes.fold("")[acc, attribut|
			acc + '''«attribut.generateMultiValuedPrimitiveTypeModel(clazz)»'''
 		]»
		«multiAttributes.fold("")[acc, attribut |
 			acc + '''«attribut.generateMultiValuedNPTypeModel(clazz)»'''
 		]»«multivaluedEnums.fold("")[acc, attribut |
 			acc + '''«attribut.generateMultiValuedEnumModel(clazz)»'''
 		]»«multivaluedValueObject.fold("")[acc, attribut |
 			acc + '''«attribut.generateMultiValuedVOModel(clazz)»'''
 		]»«interfaces.fold("")[acc, interface |
	 		acc + '''«interface.generateInterfaceModels(clazz)»'''
	 	]»
		'''
		
	}
	
	static def generateMultivaluedAttribut(Property property, Classifier fromClass){
		val type = property.type 
		if(Utils.isEntity(type)){
			'''«property.generateMultiValuedNPTypeModel(fromClass)»'''
		}else if(Utils.isValueObject(type)){
			'''«property.generateMultiValuedVOModel(fromClass)»'''
		}else if(Utils.isNomenclature(type)){
			'''«property.generateMultiValuedEnumModel(fromClass)»'''
		}else{
			'''«property.generateMultiValuedPrimitiveTypeModel(fromClass)»'''
		}
	}

	/**
	 * génère le model d'un attribut multivalué
	 */
	static def generateMultiValuedPrimitiveTypeModel(Property property, Classifier fromClass){
		val ids = ClassifierUtils.getId(fromClass)
		'''
		
		export const «PropertyUtils.getMultivaluedPropertyModelName(property, fromClass)»: Sequelize.DefineAttributes={
		    «property.name»: {
		        type: «property.getAttributeSequelizeTypeDeclaration»,
		        field: "«Utils.toDbName(property.name)»",
		        primaryKey: true,
		        allowNull: false,
		    },
		    «ids.fold("")[acc, id |
				if(acc != ""){
					acc + '''«id.generateMultiValuedPrimitiveTypeModelIdAttributes( fromClass)»'''
				}else{
					acc + '''«id.generateMultiValuedPrimitiveTypeModelIdAttributes( fromClass)»'''
				}
			]»
		};
		'''
	}
	
	static def generateMultiValuedPrimitiveTypeModelIdAttributes(Property id, Classifier fromClass){
		val idName = id.name
		//val name = Utils.toDbName(idName + Utils.getFirstToUpperCase(fromClass.name))
		val DBName = PropertyUtils.getDatabaseName(id, id.name, "")
		'''
		«idName»:{
		    type: Sequelize.«TypeUtils.getSequelizeType(id.type)»«id.generateAttributeTypeLength»,
		    field: "«DBName»",
		    allowNull: false,
		    primaryKey: true,
		    references: {
		        model: "«ClassifierUtils.getModelName(fromClass)»",
		        key: "«idName»",
		    },
		},
		'''
	}
	
	/**
	 * génère les table d'association 
	 */	
	 static def generateMultiValuedNPTypeModel(Property property, Classifier fromClass){
	 	val type = property.type
	 	val owner = property.owner
	 	if(property.association !== null){
	 		if(type != owner){
		 		'''
		 		«property.generateNPTypeAssociationModel(fromClass)»
		 		'''
	 		}else{
	 			''''''
	 		}
	 	}else{
	 		'''
	 		«property.generateNPTypeModel(fromClass)»
	 		'''
	 	}
	}
	
	static def generateNPTypeAssociationModel(Property property, Classifier fromClass){
		val members = property.association.ownedEnds
 		val member = members.get(0)
		val idsOwner = ClassifierUtils.getId(fromClass)
		val idsProp = ClassifierUtils.getId(property.type as Classifier)
		'''
		
		export const «PropertyUtils.getMultivaluedPropertyModelName(property, fromClass)»: Sequelize.DefineAttributes={
		    «idsProp.fold("")[acc, id |
				val name = PropertyUtils.getName(property, property.name, "")
				if(acc != ""){
					acc + '''«property.generateNPTAssociationModelIdAttributes(id, fromClass, name)»'''
				}else{
					acc + '''«property.generateNPTAssociationModelIdAttributes(id, fromClass, name)»'''
				}
			]»
		    «idsOwner.fold("")[acc, id |
				if(acc != ""){
					acc + '''«member.generateNPTAssociationModelIdAttributes(id, fromClass, fromClass.name)»'''
				}else{
					acc + '''«member.generateNPTAssociationModelIdAttributes(id, fromClass, fromClass.name)»'''
				}
			]»
		};
		'''
	}
	
	static def generateNPTAssociationModelIdAttributes(Property property, Property id, Classifier fromClass, String additionnalName){
		val type = property.type
		var add = ""
		if(additionnalName !== "" && additionnalName !== null){
			add = "_" + Utils.toDbName(additionnalName)
		}
		val idFieldName = PropertyUtils.getDatabaseName(id, id.name, "") + add
		val idName = Utils.addAdditionnalName(id.name, type.name)
		if(type instanceof Classifier){
			'''
			«idName»:{
			    type: Sequelize.«TypeUtils.getSequelizeType(id.type)»«id.generateAttributeTypeLength»,
			    field: "«idFieldName»",
			    allowNull: false,
			    primaryKey: true,
			    references: {
			        model: "«ClassifierUtils.getModelName(type)»",
			        key: "«id.name»",
			    },
			},
			'''
		}
	}
	
	
	static def generateNPTypeModel(Property property, Classifier fromClass){
		val idsOwner = ClassifierUtils.getId(fromClass)
		val idsProp = ClassifierUtils.getId(property.type as Classifier)
		
		'''
		
		export const «PropertyUtils.getMultivaluedPropertyModelName(property, fromClass)»: Sequelize.DefineAttributes={
		    «idsOwner.fold("")[acc, id |
				if(acc != ""){
					acc + '''«id.generateNPTModelIdAttributes(fromClass, true)»'''
				}else{
					acc + '''«id.generateNPTModelIdAttributes(fromClass, true)»'''
				}
			]»
		    «idsProp.fold("")[acc, id |
				if(acc != ""){
					acc + '''«property.generateNPTModelIdAttributes(id, fromClass)»'''
				}else{
					acc + '''«property.generateNPTModelIdAttributes(id, fromClass)»'''
				}
			]»
		};
		'''
	}
	
	
	static def generateNPTModelIdAttributes(Property id, Classifier fromClass, Boolean typeName){
		val type = id.owner
		if(type instanceof Classifier){
			var idFieldName = ""
			if(typeName){
				idFieldName = PropertyUtils.getDatabaseName(id, id.name, "")+ "_" + Utils.toDbName(type.name)
			}else{
				idFieldName= PropertyUtils.getDatabaseName(id, id.name, "")
			}
			
			val idName = Utils.addAdditionnalName(id.name, type.name)
			if(type instanceof Classifier){
				'''
				«idName»:{
				    type: Sequelize.«TypeUtils.getSequelizeType(id.type)»«id.generateAttributeTypeLength»,
				    field: "«idFieldName»",
				    allowNull: false,
				    primaryKey: true,
				    references: {
				        model: "«ClassifierUtils.getModelName(type)»",
				        key: "«id.name»",
				    },
				},
				'''
			}
		}
	}
	
	static def generateNPTModelIdAttributes(Property property, Property id, Classifier fromClass){
		val type = property.type
		val idFieldName = PropertyUtils.getDatabaseName(id, id.name, "") + "_" + PropertyUtils.getDatabaseName(property, property.name, "")
		val idName = Utils.addAdditionnalName(id.name, type.name)
		if(type instanceof Classifier){
			'''
			«idName»:{
			    type: Sequelize.«TypeUtils.getSequelizeType(id.type)»«id.generateAttributeTypeLength»,
			    field: "«idFieldName»",
			    allowNull: false,
			    primaryKey: true,
			    references: {
			        model: "«ClassifierUtils.getModelName(type)»",
			        key: "«id.name»",
			    },
			},
			'''
		}
	}
	
	
	/**
	 * délcaration du type sequelize 
	 */
	static def getAttributeSequelizeTypeDeclaration(Property property){
		'''Sequelize.«TypeUtils.getSequelizeType(property.type)»«property.generateAttributeTypeLength»'''
	}
	
	
	/**
	 * génère une table d'association pour les enums 
	 */
	static def generateMultiValuedEnumModel(Property property, Classifier fromClass){
		val type = property.type
		val idsOwner = ClassifierUtils.getId(fromClass)
		
		'''
		
		export const «PropertyUtils.getMultivaluedPropertyModelName(property, fromClass)»: Sequelize.DefineAttributes={
		    «idsOwner.fold("")[acc, id |
				if(acc != ""){
					acc + '''«id.generateNPTModelIdAttributes(fromClass, false)»'''
				}else{
					acc + '''«id.generateNPTModelIdAttributes(fromClass, false)»'''
				}
			]»
		    code: {
		        type: Sequelize.«TypeUtils.getEnumSequelizeType(type as Classifier)»,
		        field: "code",
		        allowNull: false,
		        primaryKey: true,
		        references: {
		            model: "«ClassifierUtils.getModelName(type as Classifier)»",
		            key: "code",
		        },
		    },
		};
		'''
		
	}
	
	static def generateMultiValuedVOModel(Property property, Classifier fromClass){
		val type = property.type
		val idsOwner = ClassifierUtils.getId(fromClass)
		val name = PropertyUtils.getDatabaseName(property, property.name, "")
		'''
		
		export const «PropertyUtils.getMultivaluedPropertyModelName(property, fromClass)»: Sequelize.DefineAttributes={
		    «idsOwner.fold("")[acc, id |
				if(acc != ""){
					acc + '''«id.generateNPTModelIdAttributes(fromClass, false)»'''
				}else{
					acc + '''«id.generateNPTModelIdAttributes(fromClass, false)»'''
				}
			]»
		    «(type as Classifier).generateAttributes(property.name, name, fromClass, true, false)»
		};
		'''
	}
	
	static def generateInterfaceModels(Interface interf, Classifier fromClass){
		val attributes = ClassifierUtils.getMultivaluedOwnedAttributes(interf)
		
		'''
		«attributes.fold("")[acc, attribut|
 			acc + '''
 			«attribut.generateMultivaluedAttribut(fromClass)»'''	
 		]»
 		'''
	}
}
