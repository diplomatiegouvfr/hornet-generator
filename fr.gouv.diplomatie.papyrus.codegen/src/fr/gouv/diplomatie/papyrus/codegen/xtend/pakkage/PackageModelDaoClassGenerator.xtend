package fr.gouv.diplomatie.papyrus.codegen.xtend.pakkage;

import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.Property;
import fr.gouv.diplomatie.papyrus.codegen.xtend.utils.Utils
import org.eclipse.uml2.uml.AssociationClass
import org.eclipse.uml2.uml.Classifier
import fr.gouv.diplomatie.papyrus.codegen.xtend.utils.ClassifierUtils
import fr.gouv.diplomatie.papyrus.codegen.xtend.utils.PropertyUtils

class PackageModelDaoClassGenerator{
	
	static def generateCode(Package pakkage){
		val model = pakkage.model
		val classes = model.getOwnedTypes().filter[type|
			Utils.isEntity(type)
		]
		
		val associationsClasses = model.getOwnedTypes().filter[type|
			type instanceof AssociationClass
		]
		'''
		import Sequelize = require("sequelize");
		import { Entity } from "hornet-js-database/src/decorators/dec-seq-entity";
		import { IModelDAO } from "hornet-js-database/src/interfaces/interface-model-dao";
		import { Utils } from "hornet-js-utils";
		import { SequelizeUtils } from "hornet-js-database/src/sequelize-utils";
		
		«pakkage.generateImports»
		
		export class ModelDAO implements IModelDAO {
			
			public static configDatabase: string = "configAppli";
			configDatabase: string = ModelDAO.configDatabase;
		
			constructor() {
			}
		    «classes.fold("")[acc, clazz |
		    	acc + '''«(clazz as Classifier).generateEntityDeclaration»'''
		    ]»
		    «associationsClasses.fold("")[acc, clazz |
		    	acc + '''«(clazz as Classifier).generateAssociationClassDeclaration»'''
		    ]»
		    
		    «classes.fold("")[acc, clazz |
    	    	acc + '''«(clazz as Classifier).generateEntityGetter»'''
    	    ]»
		}
		'''
	}
	
	static def generateImports(Package pakkage){
		val model = pakkage.model
		val classes = model.getOwnedTypes().filter[type|
			Utils.isEntity(type)
		]
		
		val associationsClasses = model.getOwnedTypes().filter[type|
			type instanceof AssociationClass
		]
		
		'''
		«classes.fold("")[acc, clazz |
	    	acc + '''«(clazz as Classifier).generateModelImport»'''
	    ]»«associationsClasses.fold("")[acc, clazz |
	    	acc + '''«(clazz as Classifier).generateAssociationModelImport»'''
	    ]»
		'''
	}
	
	static def generateModelImport(Classifier clazz){
		'''
		import { «ClassifierUtils.getModelName(clazz)» } from "«ClassifierUtils.getModelPath(clazz)»";
		«clazz.generateMultivaluedAttributesModelImport»
		'''
	}
	
	static def generateMultivaluedAttributesModelImport(Classifier clazz){
		val attributes = ClassifierUtils.getOwnedAttributes(clazz).filter[attribut |
			(attribut.multivalued)
		]
		
		'''«attributes.fold("")[acc, attribut |
			acc + '''«attribut.generateAttributImport»'''
		]»'''
	}
	
	static def generateAttributImport(Property property){
		val clazz = property.owner
		if(clazz instanceof Classifier){
			'''
			import { «PropertyUtils.getMultivaluedPropertyModelName(property)» } from "«ClassifierUtils.getModelPath(clazz)»";
			'''
		}
	}
	
	static def generateAssociationModelImport(Classifier clazz){
		'''
		
		import { «ClassifierUtils.getModelName(clazz)» } from "«ClassifierUtils.getModelPath(clazz)»";
		'''
	}
	
	/**
	 * génère les déclaration d'entité pour les entités
	 */
	static def generateEntityDeclaration(Classifier clazz){
		'''
		
		@Entity("«Utils.toSnakeCase(clazz.name)»", «ClassifierUtils.getModelName(clazz)»)
		static «Utils.getFirstToLowerCase(clazz.name)»Entity: Sequelize.Model<any, any>;
		«clazz.generateMultivaluedAttributesEntityDeclaration»
		'''
	}
	
	static def generateMultivaluedAttributesEntityDeclaration(Classifier clazz){
		val attributes = ClassifierUtils.getOwnedAttributes(clazz).filter[attribut |
			(attribut.multivalued)
		]
		
		'''«attributes.fold("")[acc, attribut |
			acc + '''«attribut.generateMultivaluedAttributEntityDeclaration»'''
		]»'''
	}
	
	static def generateMultivaluedAttributEntityDeclaration(Property property){
		val clazz = property.owner
		if(clazz instanceof Classifier){
			val tableName = Utils.addAdditionnalName(clazz.name, property.name)
			val entityName = clazz.name + Utils.getFirstToUpperCase(property.name)
			'''
			
			@Entity("«Utils.toSnakeCase(tableName)»", «PropertyUtils.getMultivaluedPropertyModelName(property)»)
			static «Utils.getFirstToLowerCase(entityName)»Entity: Sequelize.Model<any, any>;
			'''
		}
		
	}
	
	static def generateAssociationClassDeclaration(Classifier clazz){
		'''
		
		@Entity("«Utils.toSnakeCase(clazz.name)»", «ClassifierUtils.getModelName(clazz)»)
		static «Utils.getFirstToLowerCase(clazz.name)»Entity: Sequelize.Model<any, any>;
		'''
	}
	
	/**
	 * génère le getter de l'entité
	 */
	static def generateEntityGetter(Classifier clazz){
		'''
		
		static get«Utils.getFirstToUpperCase(clazz.name)»Entity(): Sequelize.Model<any, any>{
			var «Utils.getFirstToLowerCase(clazz.name)»Entity: Sequelize.Model<any, any> = ModelDAO.«Utils.getFirstToLowerCase(clazz.name)»Entity;
			«clazz.generateRelations(clazz,"")»
			«clazz.generateForeignRelations(clazz,'')»
			«clazz.generateAssociationRelations»
			return «Utils.getFirstToLowerCase(clazz.name)»Entity;
		}
		'''
	}
	
	/**
	 * génère les relations d'une classe
	 */
	static def generateRelations(Classifier clazz, Classifier fromClass, String additionnalName){
		val attributes = ClassifierUtils.getOwnedAttributes(clazz).filter[attribut |
			val type = attribut.type
			return (Utils.isEntity(type) || Utils.isValueObject(type))
		]
		
		'''
		«attributes.fold("")[acc, attribut |
			acc + '''«attribut.generateRelation(fromClass, additionnalName)»'''
		]»
		'''
	}
	
	/**
	 * génère la relation entre une property et la classe la contenant
	 */
	static def generateRelation(Property property, Classifier fromClass, String additionnalName){
		val type = property.type
		if(type instanceof Classifier){
			if(Utils.isEntity(type)){
				'''«property.generateEntityRelation(fromClass, additionnalName)»'''
			}else if(Utils.isValueObject(type)){
				val name = Utils.addAdditionnalName(additionnalName, property.name)
				'''«type.generateRelations(fromClass, name)»'''
			}
		}
	}
	
	static def generateEntityRelation(Property property, Classifier fromClass, String additionnalName){
		val type = property.type
		val name = Utils.addAdditionnalName(additionnalName, property.name)
		if(type instanceof Classifier){
			val ids = ClassifierUtils.getId(type)
			if(property.multivalued){
				val tableName = fromClass.name + Utils.getFirstToUpperCase(name)
				'''
				«ids.fold("")[acc, id |
					val idName = Utils.toSnakeCase(id.name + Utils.getFirstToUpperCase(fromClass.name))
					acc + '''
					SequelizeUtils.initRelationBelongsToMany(«Utils.getFirstToLowerCase(fromClass.name)»Entity, ModelDAO.«Utils.getFirstToLowerCase(type.name)»Entity, "«name»", "«Utils.toSnakeCase(idName)»", "«Utils.toSnakeCase(tableName)»");
					'''
				]»
				'''
			}else{
				'''
				«ids.fold("")[acc, id |
					val idName = Utils.toSnakeCase(Utils.addAdditionnalName(additionnalName,id.name + Utils.getFirstToUpperCase(property.name)))
					acc + '''
					SequelizeUtils.initRelationBelongsTo(«Utils.getFirstToLowerCase(fromClass.name)»Entity, ModelDAO.«Utils.getFirstToLowerCase(type.name)»Entity, "«name»", "«Utils.toSnakeCase(idName)»");
					'''
				]»
				'''
			}
		}
	}
	
	static def generateForeignRelations(Classifier clazz, Classifier fromClass, String additionnalName){
		val refs = ClassifierUtils.getAllReferencesTo(clazz)
		'''
		«refs.fold("")[acc, attribut |
			acc + '''«attribut.generateForeignRelation(fromClass, additionnalName)»'''
		]»
		'''
	}
	
	/**
	 * génère la relation entre une property et la classe la contenant
	 */
	static def generateForeignRelation(Property property, Classifier fromClass, String additionnalName){
		val type = property.type
		if(type instanceof Classifier){
			if(Utils.isEntity(type)){
				'''«property.generateForeignEntityRelation(fromClass, additionnalName)»'''
			}else if(Utils.isValueObject(type)){
				val name = Utils.addAdditionnalName(additionnalName, property.name)
				'''«type.generateForeignRelations(fromClass, name)»'''
			}
		}
	}
	
	/**
	 * génère les relations venant d'entité
	 */
	static def generateForeignEntityRelation(Property property, Classifier fromClass, String additionnalName){
		val type = property.type
		val owner = property.owner
		
		if(owner instanceof Classifier){
			if(type instanceof Classifier){
				val ids = ClassifierUtils.getId(type)
				
				
				if(property.multivalued){
					'''
					«ids.fold("")[acc, id |
						val idName = Utils.toSnakeCase(id.name + Utils.getFirstToUpperCase(type.name))
						var name = Utils.addAdditionnalName(additionnalName, property.name)
						if(property.association !== null){
							val member = property.association.memberEnds.filter[member |
								member.type == owner
							]
							
							name = member.get(0).name
						}
						val tableName = owner.name + Utils.getFirstToUpperCase(Utils.addAdditionnalName(additionnalName, property.name))
						acc + '''
						SequelizeUtils.initRelationBelongsToMany(«Utils.getFirstToLowerCase(fromClass.name)»Entity, ModelDAO.«Utils.getFirstToLowerCase(owner.name)»Entity, "«name»", "«Utils.toSnakeCase(idName)»", "«Utils.toSnakeCase(tableName)»");
						'''
					]»
					'''
				}else{
					'''
					«ids.fold("")[acc, id |
						val idName = Utils.toSnakeCase(id.name + Utils.getFirstToUpperCase(type.name))
						var name = Utils.addAdditionnalName(additionnalName, property.name)
						if(property.association !== null){
							val member = property.association.memberEnds.filter[member |
								member.type == owner
							]
							
							name = member.get(0).name
						}
						acc + '''
						SequelizeUtils.initRelationBelongsTo(«Utils.getFirstToLowerCase(fromClass.name)»Entity, ModelDAO.«Utils.getFirstToLowerCase(owner.name)»Entity, "«name»", "«Utils.toSnakeCase(idName)»");
						'''
					]»
					'''
				}
			}
		}
	}
	
	static def generateAssociationRelations(Classifier clazz){
		val model = clazz.model
		
		val associationsClasses = model.getOwnedTypes().filter[type|
			if(type instanceof AssociationClass){
				val members = type.ownedEnds
				var isIn = false
				for(member: members){
					if(member.type == clazz){
						isIn = true
					}
				}
				
				for(member: members){
					if(Utils.isValueObject(member.type)){
						isIn = false
					}
				}
				return isIn
			}else{
				return false
			}
		]
		
		val voAssoClass = model.getOwnedTypes().filter[type|
			if(type instanceof AssociationClass){
				val members = type.ownedEnds
				var isIn = false
				for(member: members){
					if(member.type == clazz){
						isIn = true
					}
				}
				if(isIn == true){
					var isVO = false
					for(member: members){
						if(Utils.isValueObject(member.type)){
							isVO = true
						}
					}
					isIn = isIn && isVO
				}
				return isIn
			}else{
				return false
			}
		]
		
		'''
		«associationsClasses.fold("")[acc, asso |
			acc + '''«(asso as AssociationClass).generateAssociationRelation(clazz)»'''
		]»
		«voAssoClass.fold("")[acc, asso |
			acc + '''«(asso as AssociationClass).generateVOAssociationRelation(clazz)»'''
		]»
		'''
	}
	
	static def generateAssociationRelation(AssociationClass clazz, Classifier fromClass){
		val ids = ClassifierUtils.getId(fromClass)
		'''
		«ids.fold("")[acc, id |
			acc + '''«id.generateAssociationIdRelation(clazz, fromClass)»'''
		]»
		'''
	}
	
	static def generateAssociationIdRelation(Property property, AssociationClass clazz, Classifier fromClass){
		val members = clazz.ownedEnds.filter[member |
			member.type != fromClass
		]
		val name = Utils.addAdditionnalName(property.name, fromClass.name)
		'''
		«members.fold("")[acc, member |
			val type = member.type
			acc + '''SequelizeUtils.initRelationBelongsToMany(«Utils.getFirstToLowerCase(fromClass.name)»Entity, ModelDAO.«Utils.getFirstToLowerCase(type.name)»Entity, "«member.name»", "«Utils.toSnakeCase(name)»", "«Utils.toSnakeCase(clazz.name)»");''' 
		]»
		'''
	}
	
	static def generateVOAssociationRelation(AssociationClass clazz, Classifier fromClass){
		val ids = ClassifierUtils.getId(fromClass)
		'''
		«ids.fold("")[acc, id |
			acc + '''«id.generateVOAssociationIdRelation(clazz, fromClass)»'''
		]»
		'''
	}
	
	static def generateVOAssociationIdRelation(Property property, AssociationClass clazz, Classifier fromClass){
		val members = clazz.ownedEnds.filter[member |
			member.type != fromClass
		]
		val name = Utils.addAdditionnalName(property.name, fromClass.name)
		'''
		«members.fold("")[acc, member |
			acc + '''SequelizeUtils.initRelationBelongsToMany(«Utils.getFirstToLowerCase(fromClass.name)»Entity, ModelDAO.«Utils.getFirstToLowerCase(clazz.name)»Entity, "«member.name»", "«Utils.toSnakeCase(name)»", "«Utils.toSnakeCase(clazz.name)»");''' 
		]»
		'''
	}
	
}