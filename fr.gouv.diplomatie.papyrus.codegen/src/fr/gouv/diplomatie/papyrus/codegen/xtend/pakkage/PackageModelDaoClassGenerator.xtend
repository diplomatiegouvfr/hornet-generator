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
			«TODO»
			return «Utils.getFirstToLowerCase(clazz.name)»Entity;
		}
		'''
	}
}