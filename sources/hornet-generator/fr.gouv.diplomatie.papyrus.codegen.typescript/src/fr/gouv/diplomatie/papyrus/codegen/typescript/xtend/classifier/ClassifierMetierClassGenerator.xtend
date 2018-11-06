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
 * @version v1.3.2
 * @license CECILL-2.1
 */
package fr.gouv.diplomatie.papyrus.codegen.typescript.xtend.classifier;

import org.eclipse.uml2.uml.Classifier
import org.eclipse.uml2.uml.Property
import fr.gouv.diplomatie.papyrus.codegen.core.utils.ClassifierUtils
import fr.gouv.diplomatie.papyrus.codegen.core.utils.Utils
import fr.gouv.diplomatie.papyrus.codegen.core.utils.PropertyUtils
import fr.gouv.diplomatie.papyrus.codegen.typescript.utils.TypeUtils
import org.eclipse.uml2.uml.AssociationClass

public class ClassifierMetierClassGenerator {
	
	static def generateCode(Classifier clazz){
		'''
		import Bean from "hornet-js-bean/src/decorators/Bean";
		import Map from "hornet-js-bean/src/decorators/Map";
		
		«clazz.generateImports»
		
		«Utils.generateComments(clazz)»
		@Bean
		export class «ClassifierUtils.getMetierClassName(clazz)» «clazz.generateExtends»«clazz.generateImplements»{
		    «clazz.generateInterfaceAttributes»
		    «clazz.generateAttributes("")»
		    «IF Utils.isEntity(clazz)»
		    «clazz.generateAssociationsAttributes»
		    «clazz.generateManyToManyAttributes»«ENDIF»
		    
		}
		'''
		//«««clazz.generateOneToManyAttributes»
	}
	
	static def generateManyToManyAttributes(Classifier clazz){
		val attributes = ClassifierUtils.getManyToManyAttributes(clazz)
		'''
		«attributes.fold("")[ acc, attr |
			acc + '''«attr.generateManyToManyAttributes(clazz)»'''
		]»
		'''
	}
	
	static def generateManyToManyAttributes(Property property, Classifier clazz){
		val association = property.association
		if(association !== null){
			val ends = association.memberEnds.filter[mem | mem.type !== clazz]
			
			'''
			«ends.fold("")[acc, end |
				acc + '''«end.generateManyToManyAttribute(clazz)»'''
			]»
			'''
		}
	}
	
	static def generateManyToManyAttribute(Property prop, Classifier clazz){
		val type = prop.type as Classifier 
		'''
		
		@Map(«ClassifierUtils.getMetierClassName(type)»)
		«prop.name»: Array<«ClassifierUtils.getMetierClassName(type)»>;
		'''
	}
	
	static def generateOneToManyAttributes(Classifier clazz){
		val attributes = ClassifierUtils.getOneToManyAttributes(clazz)
		'''
		«attributes.fold("")[acc, attr |
			acc + '''«attr.generateOneToManyAttribute(clazz)»'''
		]»
		'''
	}
	
	static def generateOneToManyAttribute(Property property, Classifier clazz){
		//val owner = property.owner as Classifier
		val owner = property.type as Classifier
		val fieldName = Utils.getFirstToLowerCase(property.name)
		'''
		
		@Map(«ClassifierUtils.getMetierClassName(owner)»)
		«fieldName»: «ClassifierUtils.getMetierClassName(owner)»;
		'''
	}
	
	/**
	 * génère les imports
	 */
	static def generateImports(Classifier clazz){
		
		val options = new ClassifierUtils.ImportOptions
		options.importInterface = true
		options.importInterfaceAttributes = true
		options.importValueObject = true
		options.importValueObjectAttributes = false
		options.importAssociationClassAttributes = true
		options.importAssociationMemberAttributes = true
		
		val attributesTypes = ClassifierUtils.getAttributesImport(clazz, clazz,newArrayList(), options)
		'''
		«clazz.generateExtendsImports»
		«attributesTypes.fold("")[acc, type |
			if(Utils.isNomenclature(type)){
				acc +  '''
				import { «Utils.getFirstToUpperCase(type.name)» } from "«ClassifierUtils.getEnumClassPath(type as Classifier)»";
				'''
			}else{
				acc +  '''
				import { «ClassifierUtils.getMetierClassName(type as Classifier)» } from "«ClassifierUtils.getMetierClassPath(type as Classifier)»";
				'''
			}
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
/* 	 static def generateAttributesImports(Classifier clazz, ArrayList<Type> types, Classifier fromClass){
	 	val attributes = ClassifierUtils.getOwnedAttributes(clazz).filter[ attribut |
			(Utils.isEntity(attribut.type))
		]
		val attributesValueObject = ClassifierUtils.getOwnedAttributes(clazz).filter[ attribut |
			(Utils.isValueObject(attribut.type))
		]
		
		val attributesEnums = ClassifierUtils.getOwnedAttributes(clazz).filter[ attribut |
			(Utils.isNomenclature(attribut.type))
		]
		if(Utils.isEntity(clazz)){
			val oneToManyAttributes = ClassifierUtils.getOneToManyAttributes(clazz)
			for(attribut : oneToManyAttributes){
				if(!types.contains(attribut.owner)){
					types.add(attribut.owner as Classifier)
				}
			}
			val manyToManyAttributes = ClassifierUtils.getManyToManyAttributes(clazz)
			for(attribut : manyToManyAttributes){
				if(!types.contains(attribut.owner)){
					types.add(attribut.owner as Classifier)
				}
			}
		}
		
		val interfaces = clazz.directlyRealizedInterfaces
		
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
			interface.generateAttributesImports(types, fromClass)
		}
		
		for(attribut : attributesValueObject){
			if(!types.contains(attribut.type)){
				types.add(attribut.type)
			}
		}
		
		
		
		/ ajout des import pour les classes d'association/ 
		if(Utils.isEntity(fromClass)){
			val associationsClasses = ClassifierUtils.getLinkedAssociationClass(clazz)
			
			associationsClasses.forEach[ asso |
				if(!types.contains(asso)){
					types.add(asso)
				}
				
			]
		}
		return types
	 }
	 */
	 
	 /**
	 * génère les extends
	 */
	static def generateExtends(Classifier clazz){
		val parents = clazz.generalizations
		if(parents.length > 0){
			val parent = parents.get(0)
			'''extends «ClassifierUtils.getMetierClassName(parent.general)»'''
		}else{
			''''''
		}
	}
	
	static def generateImplements(Classifier clazz){
		val parents = clazz.directlyRealizedInterfaces
		if(parents.length > 0){
			''' implements «parents.fold("")[acc, parent |
				if(acc != ""){
					acc + ''', «ClassifierUtils.getMetierClassName(parent)»'''
				}else{
					acc + '''«ClassifierUtils.getMetierClassName(parent)»'''
				}
			]»
			'''
			
		}else{
			''''''
		}
	}
	
	static def generateInterfaceAttributes(Classifier clazz){
		val interfaces = clazz.directlyRealizedInterfaces
		'''
		«interfaces.fold("")[acc, interface |
			acc + '''«interface.generateAttributes("")»'''
		]»
		'''
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
		}else if(Utils.isNomenclature(property.type)){
			'''«property.generateEnumAttribute(additionnalName)»'''
		}else{
			'''«property.generateEntityAttributes(additionnalName)»'''
		}
	}
	
	/**
	 * génère un attribut de type value Object
	 */
	static def generateValueObjectAttribute(Property property, String additionnalName){
		val type = property.type
		if(type instanceof Classifier){
			val propName = Utils.addAdditionnalName(additionnalName, property.name)
			var array =""
			var endArray =""
			if(property.multivalued){
				array= "Array<"
				endArray =">"
			}
			'''
			
			«Utils.generateComments(property)»
			@Map(«ClassifierUtils.getMetierClassName(type)»)
			«Utils.getFirstToLowerCase(propName)»: «array»«ClassifierUtils.getMetierClassName(type)»«endArray»;
			'''
		}
	}
	
	/**
	 * génère un attribut de type enum
	 */
	static def generateEnumAttribute(Property property, String additionnalName){
		val propName = Utils.addAdditionnalName(additionnalName, property.name)
		val type = property.type
		var array =""
		var endArray =""
		if(property.multivalued){
			array= "Array<"
			endArray =">"
		}
		'''
		
		«Utils.generateComments(property)»
		@Map()
		«Utils.getFirstToLowerCase(propName)»: «array»«Utils.getFirstToUpperCase(type.name)»«endArray»;
		'''
	}
	
	/**
	 * génère un attribut de type entity
	 */
	static def generateEntityAttributes(Property property, String additonnalName){
		val type = property.type
		
		if(type instanceof Classifier){
			val propName =  Utils.addAdditionnalName(additonnalName, property.name)
			var array =""
			var endArray =""
			if(property.isMultivalued){
				array= "Array<"
				endArray =">"
			}
			'''
			
			«Utils.generateComments(property)»
			@Map(«ClassifierUtils.getMetierClassName(type)»)
			«Utils.getFirstToLowerCase(propName)»: «array»«ClassifierUtils.getMetierClassName(type)»«endArray»;
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
			array= "Set<"
			endArray =">"
		}
		'''
		
		«Utils.generateComments(property)»
		@Map()
		«Utils.getFirstToLowerCase(name)»: «array»«TypeUtils.getMetierTypescriptType(property.type)»«endArray»;
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
		val members = clazz.memberEnds.filter[member |
			member.type !== fromClass
		]
		
		'''
		«members.fold("")[acc, mem |
			val name = mem.name
			val type = mem.type as Classifier
			acc + 
			'''
			
			«IF Utils.isNomenclature(type)»
			@Map(«type.name»)
			«Utils.getFirstToLowerCase(name)»: Array<«type.name»>;
			«ELSE»
			@Map(«ClassifierUtils.getMetierClassName(type)»)
			«Utils.getFirstToLowerCase(name)»: Array<«ClassifierUtils.getMetierClassName(type)»>;
			«ENDIF»
			'''
		]
		»
		
		@Map(«ClassifierUtils.getMetierClassName(clazz)»)
		«Utils.getFirstToLowerCase(clazz.name)»: Array<«ClassifierUtils.getMetierClassName(clazz)»>;
		'''
				
	}
	
}