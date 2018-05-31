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
 * @version v1.1.0
 * @license CECILL-2.1
 */
package fr.gouv.diplomatie.papyrus.codegen.typescript.xtend.pakkage;

import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.Property;
import fr.gouv.diplomatie.papyrus.codegen.core.utils.Utils
import org.eclipse.uml2.uml.AssociationClass
import org.eclipse.uml2.uml.Classifier
import fr.gouv.diplomatie.papyrus.codegen.core.utils.ClassifierUtils
import fr.gouv.diplomatie.papyrus.codegen.core.utils.PropertyUtils
import org.eclipse.uml2.uml.Interface

class PackageModelDaoClassGenerator{
	
	static def generateCode(Package pakkage){
		val model = pakkage.model
		val classes = pakkage.getOwnedTypes().filter[type|
			Utils.isEntity(type) && ClassifierUtils.canBeGenerated(type as Classifier)
		]
		
		val enums = pakkage.getOwnedTypes().filter[type|
			Utils.isNomenclature(type) && ClassifierUtils.canBeGenerated(type as Classifier)
		]
		
		val associationsClassesInPakkage = pakkage.getOwnedTypes().filter[type|
			type instanceof AssociationClass
		]
		
		val associationsClasses = model.getOwnedTypes().filter[type|
			type instanceof AssociationClass
		]
		'''
		import Sequelize = require("sequelize");
		import { Entity } from "hornet-js-database/src/decorators/dec-seq-entity";
		import { Utils } from "hornet-js-utils";
		import { SequelizeUtils } from "hornet-js-database/src/sequelize/sequelize-utils";
		import { injectable, Scope, Side } from "hornet-js-core/src/inject/injectable";
		import { HornetSequelizeModel } from "hornet-js-database/src/sequelize/hornet-sequelize-model";
		import { HornetSequelizeInstanceModel } from "hornet-js-database/src/sequelize/hornet-sequelize-attributes";
		
		import { inject } from "hornet-js-core/src/inject/inject";
		
		«pakkage.generateImports»
		
		@injectable(ModelDAO, Scope.SINGLETON, Side.SERVER)
		export class ModelDAO extends HornetSequelizeModel {
		
		    «classes.fold("")[acc, clazz |
		    	acc + '''«(clazz as Classifier).generateEntityDeclaration»'''
		    ]»
		    «enums.fold("")[acc, clazz |
		    	acc + '''«(clazz as Classifier).generateEntityDeclaration»'''
		    	
		    ]»
		    «associationsClasses.fold("")[acc, clazz |
		    	acc + '''«(clazz as Classifier).generateAssociationClassDeclaration»'''
		    ]»
		    «associationsClassesInPakkage.fold("")[acc, clazz |
		    	acc + '''«(clazz as Classifier).generateAssociationClassDeclaration»'''
		    ]»
		    
		    /**
		    * ATTENTION: pensez à ajouter la clé dans l'injector-context
		    * Injector.register("config", nom de la configuration);
		    */
		    constructor(@inject("config")conf?: string) {
		    	super(conf);
		    	«classes.fold("")[acc, clazz |
			    	acc + '''«(clazz as Classifier).generateCallEntityGetter»'''
			    ]»
			    «classes.fold("")[acc, clazz |
			        acc + '''«(clazz as Classifier).generateCallMultivaluedAttributesEntityGetter»'''
			    ]»
			    «associationsClasses.fold("")[acc, clazz |
			    	acc + '''«(clazz as Classifier).generateCallACEntityGetter»'''
			    ]»
			    «associationsClassesInPakkage.fold("")[acc, clazz |
			    	acc + '''«(clazz as Classifier).generateCallACEntityGetter»'''
			    ]»
				«enums.fold("")[acc, clazz |
			    	acc + '''«(clazz as Classifier).generateCallEnumEntityGetter»'''
			    ]»
		   }
		    
		    /** METHODS */
		    «classes.fold("")[acc, clazz |
		    	acc + '''«(clazz as Classifier).generateEntityGetter»'''
		    ]»
		    «classes.fold("")[acc, clazz |
		        acc + '''«(clazz as Classifier).generateMultivaluedAttributesEntityGetter»'''
		    ]»
		    «associationsClasses.fold("")[acc, clazz |
		    	acc + '''«(clazz as Classifier).generateACEntityGetter»'''
		    ]»
		    «associationsClassesInPakkage.fold("")[acc, clazz |
		    	acc + '''«(clazz as Classifier).generateACEntityGetter»'''
		    ]»
		    «enums.fold("")[acc, clazz |
		    	acc + '''«(clazz as Classifier).generateEnumEntityGetter»'''
		    ]»
		}
		'''
	}
	
	static def generateImports(Package pakkage){
		val model = pakkage.model
		val classes = pakkage.getOwnedTypes().filter[type|
			(Utils.isEntity(type) || Utils.isNomenclature(type))  && ClassifierUtils.canBeGenerated(type as Classifier)
		]
		
		val enums = model.getOwnedTypes().filter[type|
			Utils.isNomenclature(type)  && ClassifierUtils.canBeGenerated(type as Classifier)
		]
		
		val associationsClasses = model.getOwnedTypes().filter[type|
			type instanceof AssociationClass
		]
		
		val associationsClassesInPkg = pakkage.getOwnedTypes().filter[type|
			type instanceof AssociationClass
		]
		
		'''
		«classes.fold("")[acc, clazz |
	    	acc + '''«(clazz as Classifier).generateModelImport»'''
	    ]»«enums.fold("")[acc, clazz |
	    	acc + '''«(clazz as Classifier).generateModelImport»'''
	    ]»«associationsClasses.fold("")[acc, clazz |
	    	acc + '''«(clazz as Classifier).generateAssociationModelImport»'''
	    ]»«associationsClassesInPkg.fold("")[acc, clazz |
	    	acc + '''«(clazz as Classifier).generateAssociationModelImport»'''
	    ]»
		«classes.fold("")[acc, clazz |
	    	acc + '''«(clazz as Classifier).generateAttributesImport»'''
	    ]»«enums.fold("")[acc, clazz |
	    	acc + '''«(clazz as Classifier).generateAttributesImport»'''
	    ]»«associationsClasses.fold("")[acc, clazz |
	    	acc + '''«(clazz as Classifier).generateAssociationAttributesImport»'''
	    ]»«associationsClassesInPkg.fold("")[acc, clazz |
	    	acc + '''«(clazz as Classifier).generateAssociationAttributesImport»'''
	    ]»
		'''
	}
	
	/**
	 * génère l'import de la classe de model
	 */
	static def generateModelImport(Classifier clazz){
		'''
		import { «ClassifierUtils.getModelName(clazz)» } from "«ClassifierUtils.getModelPath(clazz)»";
		«clazz.generateMultivaluedAttributesModelImport(clazz)»
		'''
	}
	
	/**
	 * génère l'import de l'interface attributes
	 */
	static def generateAttributesImport(Classifier clazz){
		'''
		import { «ClassifierUtils.getAttributesInterfaceName(clazz)» } from "«ClassifierUtils.getAttributesInterfacePath(clazz)»";
		'''
	}
	
	static def generateMultivaluedAttributesModelImport(Classifier clazz, Classifier fromClass){
		val attributes = ClassifierUtils.getOwnedAttributes(clazz).filter[attribut |
			(attribut.multivalued) && (!PropertyUtils.isOneToManyAttributes(attribut))
		]
		
		val interfaces = clazz.directlyRealizedInterfaces
		
		'''«attributes.fold("")[acc, attribut |
			acc + '''«attribut.generateAttributImport(fromClass)»'''
		]»«interfaces.fold("")[acc, interface |
			acc + '''«interface.generateInterfaceAttributImport(fromClass)»'''
		]»'''
	}
	
	static def generateInterfaceAttributImport(Classifier clazz, Classifier fromClass ){
		val attributes = ClassifierUtils.getOwnedAttributes(clazz).filter[attribut |
			(attribut.multivalued) && (!PropertyUtils.isOneToManyAttributes(attribut))
		]
		'''«attributes.fold("")[acc, attribut |
			acc + '''«attribut.generateAttributImport(fromClass)»'''
		]»'''
	}
	
	static def generateAttributImport(Property property, Classifier fromClass){
		'''
		import { «PropertyUtils.getMultivaluedPropertyModelName(property,fromClass)» } from "«ClassifierUtils.getModelPath(fromClass)»";
		'''
	}
	
	static def generateAssociationModelImport(Classifier clazz){
		'''
		import { «ClassifierUtils.getModelName(clazz)» } from "«ClassifierUtils.getModelPath(clazz)»";
		'''
	}
	
	static def generateAssociationAttributesImport(Classifier clazz){
		'''
		import { «ClassifierUtils.getAttributesInterfaceName(clazz)» } from "«ClassifierUtils.getAttributesInterfacePath(clazz)»";
		'''
	}
	
	/**
	 * génère les déclaration d'entité pour les entités
	 */
	static def generateEntityDeclaration(Classifier clazz){
		val options = clazz.generateEntityDecoratorOption
		'''
		
		@Entity("«ClassifierUtils.getDBTableName(clazz)»", «ClassifierUtils.getModelName(clazz)»«options»)
		public «Utils.getFirstToLowerCase(clazz.name)»Entity: HornetSequelizeInstanceModel<«ClassifierUtils.getAttributesInterfaceName(clazz)»>;
		«IF Utils.isEntity(clazz)»«clazz.generateMultivaluedAttributesEntityDeclaration»«ENDIF»
		'''
	}
	
	static def generateMultivaluedAttributesEntityDeclaration(Classifier clazz){
		val attributes = ClassifierUtils.getOwnedAttributes(clazz).filter[attribut |
			(attribut.multivalued)
		]
		
		val interfaces = clazz.directlyRealizedInterfaces
		
		'''«attributes.fold("")[acc, attribut |
			acc + '''«attribut.generateMultivaluedAttributEntityDeclaration(clazz)»'''
		]»«interfaces.fold("")[acc, interface |
			acc + '''«interface.generateInterfaceMultivaluedAttributesDeclaration(clazz)»'''
		]»'''
	}
	
	static def generateInterfaceMultivaluedAttributesDeclaration(Interface interf, Classifier fromClass){
		val attributes = ClassifierUtils.getOwnedAttributes(interf).filter[attribut |
			(attribut.multivalued)
		]
		'''«attributes.fold("")[acc, attribut |
			acc + '''«attribut.generateMultivaluedAttributEntityDeclaration(fromClass)»'''
		]»'''
	}
	
	static def generateMultivaluedAttributEntityDeclaration(Property property, Classifier fromClass){
		val tableName = ClassifierUtils.getDBTableName(fromClass)+ "_" + PropertyUtils.getDatabaseName(property, property.name, "")
		val entityName = fromClass.name + Utils.getFirstToUpperCase(property.name)
		if (!PropertyUtils.isOneToManyAttributes(property)){
			val options = fromClass.generateEntityDecoratorOption
			'''
			
			@Entity("«tableName»", «PropertyUtils.getMultivaluedPropertyModelName(property,fromClass)»«options»)
			public «Utils.getFirstToLowerCase(entityName)»Entity: HornetSequelizeInstanceModel<any>;
			'''
		}else{
			return ''''''
		}
	}
	
	static def generateAssociationClassDeclaration(Classifier clazz){
		val options = clazz.generateEntityDecoratorOption
		'''
		
		@Entity("«ClassifierUtils.getDBTableName(clazz)»", «ClassifierUtils.getModelName(clazz)»«options»)
		public «Utils.getFirstToLowerCase(clazz.name)»Entity: HornetSequelizeInstanceModel<«ClassifierUtils.getAttributesInterfaceName(clazz)»>;
		'''
	}
	
	/**
	 * génère le getter de l'entité
	 */
	static def generateEntityGetter(Classifier clazz){
		'''
		
		private init«Utils.getFirstToUpperCase(clazz.name)»Entity(): void{
			«clazz.generateExtendsIdRelation»
			«clazz.generateRelations(clazz,"", "")»
			«clazz.generateForeignRelations(clazz,'')»
			«clazz.generateAssociationRelations»
			«clazz.generateOneToManyRelations»
		}
		'''
	}
	
	static def generateOneToManyRelations(Classifier clazz){
		val attributes = ClassifierUtils.getOneToManyAttributes(clazz)
		'''
		«attributes.fold("")[acc, attribut|
			acc + '''«attribut.generateOneToManyRelation(clazz)»'''
		]»
		'''
	}
	
	static def generateOneToManyRelation(Property property, Classifier clazz){
		val owner = property.owner as Classifier
		val alias = Utils.getFirstToLowerCase(owner.name) + Utils.getFirstToUpperCase(property.name)
		
		val dbPropertyName = PropertyUtils.getDatabaseName(property, property.name, "")
		val id = ClassifierUtils.getId(owner).get(0)
		val idDbName = PropertyUtils.getDatabaseName(id, id.name, "")
		val fieldName = idDbName + "_" + Utils.toDbName(owner.name) + "_" + dbPropertyName
		'''
		SequelizeUtils.initRelationBelongsTo({ fromEntity: this.«Utils.getFirstToLowerCase(clazz.name)»Entity, toEntity: this.«Utils.getFirstToLowerCase(owner.name)»Entity, alias: "«alias»", foreignKey: "«fieldName»" });
		'''
	}
	
	/**
	 * génère l'appel au getter de l'entité
	 */
	static def generateCallEntityGetter(Classifier clazz){
		'''
		this.init«Utils.getFirstToUpperCase(clazz.name)»Entity();
		'''
	}
	
	static def generateExtendsIdRelation(Classifier clazz){
		val parents = clazz.generalizations
		if( parents !== null && !parents.empty){
			'''«parents.fold("")[ acc, parent |
				acc + '''«parent.general.generateExtendIdrelation(clazz)»'''
			]»'''
		}else{
			''''''
		}
	}
	
	static def generateExtendIdrelation(Classifier clazz, Classifier fromClass){
		val ids = ClassifierUtils.getId(clazz)
		'''
		«ids.fold("")[acc, id |
			val idName = PropertyUtils.getDatabaseName(id, id.name, "")
			acc + generateInitRelationBelongsTo(
				'''this.«Utils.getFirstToLowerCase(fromClass.name)»Entity''',
				'''this.«Utils.getFirstToLowerCase(clazz.name)»Entity''',
				'''"«idName»"''',
				'''"«idName»"'''
			)
		]»
		'''
	}
	
	/**
	 * génère les relations d'une classe
	 */
	static def generateRelations(Classifier clazz, Classifier fromClass, String additionnalName, String databaseAddName){
		var attributes = ClassifierUtils.getOwnedAttributes(clazz).filter[attribut |
			val type = attribut.type
			return (Utils.isEntity(type) || Utils.isValueObject(type)) || Utils.isNomenclature(type) || attribut.multivalued
		]
		
		if(clazz instanceof AssociationClass){
			attributes = clazz.memberEnds.filter[attribut |
				val type = attribut.type
				return (Utils.isEntity(type) || Utils.isValueObject(type)) || Utils.isNomenclature(type) || attribut.multivalued
			]
		}
		
		val interfaces = clazz.directlyRealizedInterfaces
		
		'''
		«attributes.fold("")[acc, attribut |
			acc + '''«attribut.generateRelation(fromClass, additionnalName, databaseAddName)»'''
		]»
		«IF clazz instanceof AssociationClass»«interfaces.fold("")[acc, interface |
			acc + '''«interface.generateInterfaceRelations(fromClass)»'''
		]»«ENDIF»
		'''
	}
	
	static def generateInterfaceRelations(Interface interf, Classifier fromClass){
		val attributes = ClassifierUtils.getOwnedAttributes(interf).filter[attribut |
			val type = attribut.type
			return (Utils.isEntity(type) || Utils.isValueObject(type)) || Utils.isNomenclature(type) || attribut.multivalued
		]
		'''
		«attributes.fold("")[acc, attribut |
			acc + '''«attribut.generateRelation(fromClass, "", "")»'''
		]»
		'''
	}
	
	/**
	 * génère la relation entre une property et la classe la contenant
	 */
	static def generateRelation(Property property, Classifier fromClass, String additionnalName, String databaseAddName){
		val type = property.type
		if(type instanceof Classifier){
			if(Utils.isEntity(type)){
				'''«property.generateEntityRelation(fromClass, additionnalName, databaseAddName)»'''
			}else if(Utils.isNomenclature(type)){
				'''«property.generateEnumRelation(fromClass, additionnalName, databaseAddName)»'''
			}else if(Utils.isValueObject(type)){
				val propName = Utils.addAdditionnalName(property.name, additionnalName)
				val name = PropertyUtils.getDatabaseName(property, property.name, additionnalName)
				val tableName = Utils.addAdditionnalName(fromClass.name, propName)
				
				if(property.multivalued){
					val ids = ClassifierUtils.getId(fromClass)
					'''«ids.fold("")[acc, id |
						//val idName = Utils.toSnakeCase(id.name + Utils.getFirstToUpperCase(fromClass.name))
						val idName = PropertyUtils.getDatabaseName(id, id.name, "") + "_" +Utils.toDbName(fromClass.name)
						acc + generateInitRelationBelongsToMany(
							'''this.«Utils.getFirstToLowerCase(fromClass.name)»Entity''',
							'''this.«Utils.getFirstToLowerCase(tableName)»Entity''',
							'''"«propName»"''',
							'''"«idName»"''',
							'''"«Utils.toDbName(tableName)»"'''
						)
					]»'''
				}else{
					'''«type.generateRelations(fromClass, Utils.addAdditionnalName(additionnalName, property.name), name)»'''
				}
				
			}else{
				'''«property.generatePTRelation(fromClass, additionnalName)»'''
			}
		}
	}
	
	static def generateEntityRelation(Property property, Classifier fromClass, String additionnalName, String databaseAddName){
		val type = property.type
		val name = Utils.addAdditionnalName(additionnalName, property.name)
		val databasename = PropertyUtils.getName(property, property.name, additionnalName)
		if(type instanceof Classifier){
			val ids = ClassifierUtils.getId(fromClass)
			if(property.multivalued){
				val association = property.association
				if(association !== null){
					 val member = association.memberEnds.filter[mem |
							mem.type == fromClass
					]
					if(member !== null){
						val end = member.get(0);					
						if(end.isMultivalued){
							val tableName = fromClass.name + Utils.getFirstToUpperCase(databasename)
							return '''
							«ids.fold("")[acc, id |
								val idName = PropertyUtils.getDatabaseName(id, id.name, "") + "_" + Utils.toDbName(fromClass.name)
								
								acc + generateInitRelationBelongsToMany(
									'''this.«Utils.getFirstToLowerCase(fromClass.name)»Entity''',
									'''this.«Utils.getFirstToLowerCase(type.name)»Entity''',
									'''"«name»"''',
									'''"«idName»"''',
									'''"«Utils.toDbName(tableName)»"'''
								)
							]»
							'''
							
						}
					}
				}
				if(PropertyUtils.isOneToManyAttributes(property)){
					val owner = property.owner as Classifier
					val dbPropertyName = PropertyUtils.getDatabaseName(property, property.name, "")
					val id = ClassifierUtils.getId(owner).get(0)
					val idDbName = PropertyUtils.getDatabaseName(id, id.name, "")
					val fieldName = idDbName + "_" + Utils.toDbName(owner.name) + "_" + dbPropertyName
								
					'''
					SequelizeUtils.initRelationHasMany({ fromEntity: this.«Utils.getFirstToLowerCase(fromClass.name)»Entity, toEntity: this.«Utils.getFirstToLowerCase(type.name)»Entity, alias: "«name»", foreignKey: "«fieldName»" });
					'''
				}else{
					val owner = property.owner as Classifier
					val id = ClassifierUtils.getId(owner).get(0)
					val idDbName = PropertyUtils.getDatabaseName(id, id.name, "")
					val fieldName = idDbName + "_" + Utils.toDbName(owner.name)
					val tableName = Utils.addAdditionnalName(fromClass.name, property.name)
					val througTable = Utils.toDbName(fromClass.name) + "_" + PropertyUtils.getDatabaseName(property, property.name, "")	
					
					return generateInitRelationBelongsToMany(
						'''this.«Utils.getFirstToLowerCase(fromClass.name)»Entity''',
						'''this.«Utils.getFirstToLowerCase(type.name)»Entity''',
						'''"«name»"''',
						'''"«fieldName»"''',
						'''"«througTable»"'''	
					)
				}
				
			}else{
				val idsProp = ClassifierUtils.getId(type)
				'''
				«idsProp.fold("")[acc, id |
					var idName =""
					if(databaseAddName != "" && databaseAddName !== null){
						 idName= databaseAddName + "_" + PropertyUtils.getDatabaseName(id, id.name, "") + "_" + PropertyUtils.getDatabaseName(property,property.name,"")
					}else{
						 idName= PropertyUtils.getDatabaseName(id, id.name, "") + "_" + PropertyUtils.getDatabaseName(property,property.name,"")
					}
					acc + generateInitRelationBelongsTo(
						'''this.«Utils.getFirstToLowerCase(fromClass.name)»Entity''',
						'''this.«Utils.getFirstToLowerCase(type.name)»Entity''',
						'''"«name»"''',
						'''"«idName»"'''
					)
				]»
				'''
			}
		}
	}
	
	/**
	 * génère les relation avec les enums
	 */
	static def generateEnumRelation(Property property, Classifier fromClass, String additionnalName, String databaseAddName){
		val type = property.type
		val tableName = Utils.toDbName(fromClass.name) + "_" + PropertyUtils.getDatabaseName(property, property.name, "")
		val name = Utils.addAdditionnalName(additionnalName, property.name)
		val propName = PropertyUtils.getDatabaseName(property, property.name, databaseAddName)
		if(property.multivalued){
			val ids = ClassifierUtils.getId(fromClass)
			'''
			«ids.fold("")[acc, id |
				val idName = PropertyUtils.getDatabaseName(id, id.name, "")
				acc + generateInitRelationBelongsToMany(
					'''this.«Utils.getFirstToLowerCase(fromClass.name)»Entity''',
					'''this.«Utils.getFirstToLowerCase(type.name)»Entity''',
					'''"«name»"''',
					'''"«idName»"''',
					'''"«tableName»"'''
				)
			]»
			'''
		}else{
			generateInitRelationBelongsTo(
				'''this.«Utils.getFirstToLowerCase(fromClass.name)»Entity''',
				'''this.«Utils.getFirstToLowerCase(type.name)»Entity''',
				'''"«name»"''',
				'''"CODE_«propName»"'''
			)
		}
	}
	
	/**
	 * génère les relation avec les attribut primitifs multivalués
	 */
	static def generatePTRelation(Property property, Classifier fromClass, String additionnalName){
		val tableName = Utils.addAdditionnalName(fromClass.name, property.name)
		val ids = ClassifierUtils.getId(fromClass)
		'''
		«ids.fold("")[acc, id |
			val idName = PropertyUtils.getDatabaseName(id, id.name, "")
			acc + generateInitRelationBelongsToMany(
				'''this.«Utils.getFirstToLowerCase(fromClass.name)»Entity''',
				'''this.«Utils.getFirstToLowerCase(tableName)»Entity''',
				'''"«property.name»"''',
				'''"«idName»"''',
				'''"«Utils.toDbName(tableName)»"'''
			)
		]»
		'''
	
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
				val tableName = Utils.addAdditionnalName(fromClass.name, name)
				
				if(property.multivalued){
					val ids = ClassifierUtils.getId(fromClass)
					'''«ids.fold("")[acc, id |
						val idName = PropertyUtils.getDatabaseName(id, id.name, "") + "_" + Utils.toDbName(fromClass.name)
						acc + generateInitRelationBelongsToMany(
							'''this.«Utils.getFirstToLowerCase(fromClass.name)»Entity''',
							'''this.«Utils.getFirstToLowerCase(tableName)»Entity''',
							'''"«name»"''',
							'''"«idName»"''',
							'''"«Utils.toDbName(tableName)»"'''
						)
					]»'''
				}else{
					'''«type.generateForeignRelations(fromClass, name)»'''
				}
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
						val idName = PropertyUtils.getDatabaseName(id, id.name, "") + "_" + Utils.toDbName(fromClass.name)
						var name = Utils.addAdditionnalName(additionnalName, property.name)
						if(property.association !== null){
							val member = property.association.memberEnds.filter[member |
								member.type == owner
							]
							
							name = member.get(0).name
						}
						val tableName = owner.name + Utils.getFirstToUpperCase(Utils.addAdditionnalName(additionnalName, property.name))
						acc + generateInitRelationBelongsToMany(
							'''this.«Utils.getFirstToLowerCase(fromClass.name)»Entity''',
							'''this.«Utils.getFirstToLowerCase(owner.name)»Entity''',
							'''"«name»"''',
							'''"«idName»"''',
							'''"«Utils.toDbName(tableName)»"'''
						)
					]»
					'''
				}else{
					'''
					«ids.fold("")[acc, id |
						val idName = PropertyUtils.getDatabaseName(id, id.name, "") + "_" + Utils.toDbName(type.name)
						var name = Utils.addAdditionnalName(additionnalName, property.name)
						if(property.association !== null){
							val member = property.association.memberEnds.filter[member |
								member.type == owner
							]
							
							name = member.get(0).name
						}
						acc + generateInitRelationBelongsTo(
							'''this.«Utils.getFirstToLowerCase(fromClass.name)»Entity''',
							'''this.«Utils.getFirstToLowerCase(owner.name)»Entity''',
							'''"«name»"''',
							'''"«idName»"'''
						)
					]»
					'''
				}
			}
		}
	}
	
	static def generateAssociationRelations(Classifier clazz){
		val model = clazz.model

		val associationsClasses = model.allOwnedElements.filter[type|
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
		val members = clazz.memberEnds.filter(member|
			member.type == fromClass
		)
		
		val test = clazz.memberEnds.filter(member|
			member.type !== fromClass
		)
		'''
		«test.fold("")[acc, member |
			val name = PropertyUtils.getDatabaseName(property,property.name,"")+ "_" +  Utils.toDbName(members.get(0).name)
			val type = member.type as Classifier
			if(Utils.isEntity(type)){
				val id = ClassifierUtils.getId(type).get(0)
				val otherKeyName = PropertyUtils.getDatabaseName(id, id.name,"")+ "_" +  Utils.toDbName(type.name)
				acc + generateInitRelationBelongsToMany(
					'''this.«Utils.getFirstToLowerCase(fromClass.name)»Entity''',
					'''this.«Utils.getFirstToLowerCase(type.name)»Entity''',
					'''"«test.get(0).name»"''',
					'''"«name»"''',
					'''"«Utils.toDbName(clazz.name)»"''',
					'''"«otherKeyName»"'''
				)
			}else{
				acc + generateInitRelationBelongsToMany(
					'''this.«Utils.getFirstToLowerCase(fromClass.name)»Entity''',
					'''this.«Utils.getFirstToLowerCase(type.name)»Entity''',
					'''"«test.get(0).name»"''',
					'''"«name»"''',
					'''"«Utils.toDbName(clazz.name)»"'''
				)
			}
			
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
		val name = PropertyUtils.getDatabaseName(property,property.name,"")+ "_" + Utils.toDbName(fromClass.name)
		'''
		«members.fold("")[acc, member |
			acc + generateInitRelationBelongsToMany(
				'''this.«Utils.getFirstToLowerCase(fromClass.name)»Entity''',
				'''this.«Utils.getFirstToLowerCase(clazz.name)»Entity''',
				'''"«member.name»"''',
				'''"«name»"''',
				'''"«Utils.toDbName(clazz.name)»"'''
			)
		]»
		'''
	}
	
	static def generateEnumEntityGetter(Classifier clazz){
		'''
		
		public init«Utils.getFirstToUpperCase(clazz.name)»Entity(): void{
			«clazz.generateEnumForeignRelations(clazz,'')»
		}
		'''
	}
	
	static def generateCallEnumEntityGetter(Classifier clazz){
		'''
		this.init«Utils.getFirstToUpperCase(clazz.name)»Entity();
		'''
	}
	
	static def generateEnumForeignRelations(Classifier clazz, Classifier fromClass, String additionnalName){
		val refs = ClassifierUtils.getAllReferencesTo(clazz)
		'''
		«refs.fold("")[acc, attribut |
			acc + '''«attribut.generateEnumForeignRelation(fromClass, additionnalName)»'''
		]»
		'''
	}
	
	static def generateEnumForeignRelation(Property property, Classifier fromClass, String additionnalName){
		val type = property.owner
		if(type instanceof Classifier){
			if(Utils.isEntity(type)){
				'''«property.generateEnumForeignEntityRelation(fromClass, additionnalName)»'''
			}else if(Utils.isValueObject(type)){
				val name = Utils.addAdditionnalName(additionnalName, property.name)
				'''«type.generateEnumForeignRelations(fromClass, name)»'''
			}
		}
	}
	
	static def generateEnumForeignEntityRelation(Property property, Classifier fromClass, String additionnalName){
		val type = property.type
		val owner = property.owner
		
		if(owner instanceof Classifier){
			if(type instanceof Classifier){
				var name = Utils.addAdditionnalName(additionnalName, property.name)
				if(property.association !== null){
					val member = property.association.memberEnds.filter[member |
						member.type == owner
					]
					
					name = member.get(0).name
				}
				val tableName = owner.name + Utils.getFirstToUpperCase(Utils.addAdditionnalName(additionnalName, property.name))
				if(property.multivalued){
					generateInitRelationBelongsToMany(
						'''this.«Utils.getFirstToLowerCase(fromClass.name)»Entity''',
						'''this.«Utils.getFirstToLowerCase(owner.name)»Entity''',
						'''"«Utils.toDbName(name)»"''',
						'''"CODE"''',
						'''"«Utils.toDbName(tableName)»"'''
					)
				}else{
					 generateInitRelationBelongsTo(
						'''this.«Utils.getFirstToLowerCase(fromClass.name)»Entity''',
						'''this.«Utils.getFirstToLowerCase(owner.name)»Entity''',
						'''"«Utils.toDbName(name)»"''',
						'''"CODE"'''
					)
				}
			}
		}
	}
	
	/**
	 * génère les entity getter des classe d'association
	 */
	static def generateACEntityGetter(Classifier clazz){
		'''
		
		public init«Utils.getFirstToUpperCase(clazz.name)»Entity(): void{
			«clazz.generateRelations(clazz,"", "")»
		}
		'''
	}
	
	static def generateCallACEntityGetter(Classifier clazz){
		'''
		this.init«Utils.getFirstToUpperCase(clazz.name)»Entity();
		'''
	}
	
	static def generateMultivaluedAttributesEntityGetter(Classifier clazz){
		val attributes = ClassifierUtils.getMultivaluedOwnedAttributes(clazz)
		val interfaces = clazz.directlyRealizedInterfaces
		'''
		«attributes.fold("")[acc, attribut |
			val entityName = clazz.name + Utils.getFirstToUpperCase(attribut.name)
			acc + '''«attribut.generateAttributEntityGetter(clazz, entityName)»'''
		]»«interfaces.fold("")[acc, interface |
			acc + '''«interface.generateInterfacesAttributesEntityGetter(clazz)»'''
		]»
		'''
	}
	
	static def generateCallMultivaluedAttributesEntityGetter(Classifier clazz){
		val attributes = ClassifierUtils.getMultivaluedOwnedAttributes(clazz).filter[attr | !(PropertyUtils.isOneToManyAttributes(attr))]
		val interfaces = clazz.directlyRealizedInterfaces
		'''
		«attributes.fold("")[acc, attribut |
			val entityName = clazz.name + Utils.getFirstToUpperCase(attribut.name)
			acc + '''«attribut.generateCallAttributEntityGetter(clazz, entityName)»'''
		]»«interfaces.fold("")[acc, interface |
			acc + '''«interface.generateCallInterfacesAttributesEntityGetter(clazz)»'''
		]»
		'''
	}
	
	static def generateAttributEntityGetter(Property property, Classifier fromClass, String entityName){
		val tableName = Utils.addAdditionnalName(fromClass.name, property.name)
		val ids = ClassifierUtils.getId(fromClass)
		val type = property.type
		if(!PropertyUtils.isOneToManyAttributes(property)){
		'''
		
		public init«tableName»Entity(): void{
			«ids.fold("")[acc, id |
				val idName = PropertyUtils.getDatabaseName(id, PropertyUtils.getDatabaseName(id, id.name, ""), "")
				if(Utils.isEntity(type)){
					acc + generateInitRelationBelongsTo(
						'''this.«Utils.getFirstToLowerCase(entityName)»Entity''',
						'''this.«Utils.getFirstToLowerCase(fromClass.name)»Entity''',
						'''"«Utils.getFirstToLowerCase(fromClass.name)»"''',
						'''"«idName»_«Utils.toDbName(fromClass.name)»"'''
					)
				}else{
					acc + generateInitRelationBelongsTo(
						'''this.«Utils.getFirstToLowerCase(entityName)»Entity''',
						'''this.«Utils.getFirstToLowerCase(fromClass.name)»Entity''',
						'''"«Utils.getFirstToLowerCase(fromClass.name)»"''',
						'''"«idName»"'''
					)
				}
			]»
			«property.generateMARelation(fromClass, "", entityName)»
		}
		'''}else{''''''}
	}
	
	static def generateCallAttributEntityGetter(Property property, Classifier fromClass, String entityName){
		val tableName = Utils.addAdditionnalName(fromClass.name, property.name);
		'''
		this.init«tableName»Entity();
		'''
	}
	
	static def generateMARelation(Property property, Classifier fromClass, String additionnalName, String entityName){
		val name = Utils.addAdditionnalName(additionnalName, property.name)
		val dbName = PropertyUtils.getDatabaseName(property, property.name, Utils.toDbName(additionnalName))
		val propName = PropertyUtils.getName(property, property.name, additionnalName)
		val type = property.type
		if(type instanceof Classifier){
			if(Utils.isEntity(type)){
				val ids = ClassifierUtils.getId(type)
				'''
				«ids.fold("")[acc, id |
					val propertyDbName = PropertyUtils.getDatabaseName(property, property.name,"")
					var idName =  PropertyUtils.getDatabaseName(id, id.name, "")+ "_" + propertyDbName
					if(additionnalName != "" && additionnalName !== null){
						idName =  Utils.toDbName(additionnalName) + "_"+ PropertyUtils.getDatabaseName(id, id.name, "") + "_" + propertyDbName
					}
					
					//var fieldName = Utils.addAdditionnalName(additionnalName, PropertyUtils.getName(id, id.name, type.name))
					var fieldName = Utils.addAdditionnalName(additionnalName, property.name)

					/*if(additionnalName == ""){
						idName = additionnalName + "_" + PropertyUtils.getDatabaseName(id, id.name, type.name)
						fieldName =  Utils.addAdditionnalName(additionnalName, type.name)
					}*/
					acc + generateInitRelationBelongsTo(
						'''this.«Utils.getFirstToLowerCase(entityName)»Entity''',
						'''this.«Utils.getFirstToLowerCase(type.name)»Entity''',
						'''"«Utils.getFirstToLowerCase(fieldName)»"''',
						'''"«idName»"'''
					)
				]»
				'''
			}else if(Utils.isValueObject(type)){
				'''«property.generateVOMARelation(fromClass,name, entityName)»'''
			}else if(Utils.isNomenclature(type)){
				if(additionnalName == ""){
					generateInitRelationBelongsTo(
						'''this.«Utils.getFirstToLowerCase(entityName)»Entity''',
						'''this.«Utils.getFirstToLowerCase(type.name)»Entity''',
						'''"«Utils.getFirstToLowerCase(name)»"''',
						'''"CODE"'''
					)
				}else{
					generateInitRelationBelongsTo(
						'''this.«Utils.getFirstToLowerCase(entityName)»Entity''',
						'''this.«Utils.getFirstToLowerCase(type.name)»Entity''',
						'''"«Utils.getFirstToLowerCase(name)»"''',
						'''"CODE_«dbName»"'''
					)					
				}
			}
		}
	}
	
	static def generateVOMARelation( Property property, Classifier fromClass,String additionnalName, String entityName){
		val type = property.type
		val attributes = ClassifierUtils.getOwnedAttributes(type as Classifier)
		
		'''
		«attributes.fold("")[acc, attribut |
			acc + '''«attribut.generateMARelation(fromClass, additionnalName, entityName)»'''
		]»
		'''
	}
	
	static def generateInterfacesAttributesEntityGetter(Interface interf, Classifier fromClass){
		val attributes = ClassifierUtils.getMultivaluedOwnedAttributes(interf)
		
		'''
		«attributes.fold("")[acc, attribut |
			val entityName = fromClass.name + Utils.getFirstToUpperCase(attribut.name)
			acc + '''«attribut.generateAttributEntityGetter(fromClass, entityName)»'''
		]»
		'''
	}
	
	static def generateCallInterfacesAttributesEntityGetter(Interface interf, Classifier fromClass){
		val attributes = ClassifierUtils.getMultivaluedOwnedAttributes(interf)
		
		'''
		«attributes.fold("")[acc, attribut |
			val entityName = fromClass.name + Utils.getFirstToUpperCase(attribut.name)
			acc + '''«attribut.generateCallAttributEntityGetter(fromClass, entityName)»'''
		]»
		'''
	}
	
	/**
	 * génère une ligne initRelationBelongsTo
	 */
	static def generateInitRelationBelongsTo(String fromEntity, String toEntity, String alias, String foreignKey){
		return 
		'''
		SequelizeUtils.initRelationBelongsTo({fromEntity: «fromEntity», toEntity: «toEntity», alias: «alias», foreignKey: «foreignKey»});
		'''
	}
	
	/**
	 * génère une ligne initRelationBelongsToMany avec option otherKey
	 */
	static def generateInitRelationBelongsToMany(String fromEntity, String toEntity, String alias, String foreignKey, String throughTable, String otherKey){
		return 
		'''
		SequelizeUtils.initRelationBelongsToMany({fromEntity: «fromEntity», toEntity: «toEntity», alias: «alias», foreignKey: «foreignKey», throughTable: «throughTable», otherKey: «otherKey»});
		'''
	}
	
	/**
	 * génère une ligne initRelationBelongsToMany
	 */
	static def generateInitRelationBelongsToMany(String fromEntity, String toEntity, String alias, String foreignKey, String throughTable){
		return 
		'''
		SequelizeUtils.initRelationBelongsToMany({fromEntity: «fromEntity», toEntity: «toEntity», alias: «alias», foreignKey: «foreignKey», throughTable: «throughTable»});
		'''
	}
	
	/**
	 * génère les options du décorateur entity
	 */
	static def generateEntityDecoratorOption(Classifier clazz){
		val schema = ClassifierUtils.getSchema(clazz)
		'''«IF schema !== null && schema != ""», {schema: "«schema»"}«ENDIF»'''
	}
}