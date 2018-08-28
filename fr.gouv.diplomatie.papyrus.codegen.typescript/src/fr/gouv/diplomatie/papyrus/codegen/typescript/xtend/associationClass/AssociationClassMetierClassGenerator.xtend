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
 * @version v1.1.5
 * @license CECILL-2.1
 */
package fr.gouv.diplomatie.papyrus.codegen.typescript.xtend.associationClass

import fr.gouv.diplomatie.papyrus.codegen.core.utils.ClassifierUtils
import fr.gouv.diplomatie.papyrus.codegen.core.utils.Utils
import org.eclipse.uml2.uml.AssociationClass
import org.eclipse.uml2.uml.Classifier
import org.eclipse.uml2.uml.Property
import fr.gouv.diplomatie.papyrus.codegen.core.utils.PropertyUtils
import fr.gouv.diplomatie.papyrus.codegen.typescript.utils.TypeUtils
import java.util.ArrayList
import org.eclipse.uml2.uml.Type

public class AssociationClassMetierClassGenerator{
	
	static def generateCode(AssociationClass clazz){
		'''
		import Bean from "hornet-js-bean/src/decorators/Bean";
		import Map from "hornet-js-bean/src/decorators/Map";
		
		«clazz.generateImports»
		
		@Bean
		export class «ClassifierUtils.getMetierClassName(clazz)»{
		    «clazz.generateAttributes("")»
		}
		'''
	}
	
	/**
	 * génère les imports
	 */
	static def generateImports(AssociationClass clazz){
		val attributesTypes = clazz.generateAttributesImports(newArrayList(), clazz)
		'''
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
	 * génère les imports liés aux types des attributs
	 */
	 static def generateAttributesImports(AssociationClass clazz, ArrayList<Type> types, Classifier fromClass){
	 	val attributes = clazz.memberEnds.filter[ attribut |
			(Utils.isEntity(attribut.type))
		]
		
		val attributesEnums = clazz.memberEnds.filter[ attribut |
			(Utils.isNomenclature(attribut.type))
		]
		
		val attributesValueObject = clazz.memberEnds.filter[ attribut |
			(Utils.isValueObject(attribut.type))
		]
		
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
		
		for(attribut: attributesValueObject){
			if(!types.contains(attribut.type)){
				types.add(attribut.type)
			}
		}
		return types
	 }
	 
	 /**
	  * génère les attributs
	  */
	 static def generateAttributes(AssociationClass clazz, String additionnalName){
	 	val attributes = clazz.memberEnds
	 	'''
	 	«attributes.fold("")[acc, attribut |
	 		acc + '''«attribut.generateAttribut(additionnalName)»'''
	 	]»
	 	'''
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
		val name = Utils.addAdditionnalName(additionnalName, property.name)
		if(type instanceof Classifier){
			'''
			
			«Utils.generateComments(property)»
			@Map(«ClassifierUtils.getMetierClassName(type)»)
			«Utils.getFirstToLowerCase(name)»: «ClassifierUtils.getMetierClassName(type)»;
			'''
		}
		
	}
	
	/**
	 * génère un attrbiut de type enum
	 */
	static def generateEnumAttribute(Property property, String additionnalName){
		val type = property.type
		val propName =  Utils.addAdditionnalName(additionnalName, property.name)
		var array = ""
		var endArray = ""
		if(property.multivalued){
			array = "Array<"
			endArray = ">"
		} 
		if(type instanceof Classifier){
			'''
			
			«Utils.generateComments(property)»
			@Map()
			«Utils.getFirstToLowerCase(propName)»: «array»«Utils.getFirstToUpperCase(type.name)»«endArray»;
			'''
		}
	}
	
	/**
	 * génère un attribut de type entity
	 */
	static def generateEntityAttributes(Property property, String additionnalName){
		val type = property.type
		if(type instanceof Classifier){
			'''
			«property.generateEntityAttribute(additionnalName)»
			'''
		}else{
			''''''
		}
		
	}
	
		/**
	 * génère un attribut de type entity
	 */
	static def generateEntityAttribute(Property property, String additionnalName){
		val type = property.type
		val propName =  Utils.addAdditionnalName(additionnalName, property.name)
		var array = ""
		var endArray = ""
		if(property.multivalued){
			array = "Array<"
			endArray = ">"
		} 
		if(type instanceof Classifier){
			'''
			
			«Utils.generateComments(property)»
			@Map(«ClassifierUtils.getMetierClassName(type)»)
			«Utils.getFirstToLowerCase(propName)»: «array»«ClassifierUtils.getMetierClassName(type)»«endArray»;
			'''
		}
	}
	
	/**
	 * génère un attribut de l'interface
	 */
	static def generateBasicAttribute(Property property, String additionnalName){
		var name = Utils.addAdditionnalName(additionnalName, property.name)
		'''
		
		«Utils.generateComments(property)»
		@Map()
		«Utils.getFirstToLowerCase(name)»: «TypeUtils.getTypescriptType(property.type)»;
		'''
	}
	 
	 static def generateRefAttribute(Property property, String additionnalName){
		val name = Utils.addAdditionnalName(additionnalName, property.name)
	 	val type = property.type
	 	var array = ""
		var endArray = ""
		if(property.multivalued){
			array = "Array<"
			endArray = ">"
		} 
		if(type instanceof Classifier){
		 	'''
		 	
		 	«Utils.generateComments(property)»
		 	@Map(«array»«ClassifierUtils.getMetierClassName(type)»«endArray»)
		 	«Utils.getFirstToLowerCase(name)»: «array»«ClassifierUtils.getMetierClassName(type)»«endArray»;
		 	'''
	 	}
	 }
	 
}