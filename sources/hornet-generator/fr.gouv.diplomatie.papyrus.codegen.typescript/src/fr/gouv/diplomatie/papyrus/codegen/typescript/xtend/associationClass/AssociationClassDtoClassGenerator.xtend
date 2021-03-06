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
package fr.gouv.diplomatie.papyrus.codegen.typescript.xtend.associationClass

import org.eclipse.uml2.uml.AssociationClass
import org.eclipse.uml2.uml.Property
import fr.gouv.diplomatie.papyrus.codegen.core.utils.ClassifierUtils
import fr.gouv.diplomatie.papyrus.codegen.core.utils.PropertyUtils
import fr.gouv.diplomatie.papyrus.codegen.core.utils.Utils
import org.eclipse.uml2.uml.Classifier
import fr.gouv.diplomatie.papyrus.codegen.typescript.utils.TypeUtils
import java.util.ArrayList
import org.eclipse.uml2.uml.Type
import fr.gouv.diplomatie.papyrus.codegen.typescript.xtend.classifier.ClassifierDtoClassGenerator

public class AssociationClassDtoClassGenerator{
	
	static def generateCode(AssociationClass clazz){
		
		'''
		import Alias from "hornet-js-bean/src/decorators/Alias";
		import Bean from "hornet-js-bean/src/decorators/Bean";
		import Map from "hornet-js-bean/src/decorators/Map";
		
		«clazz.generateImports»
		
		@Bean
		export class «ClassifierUtils.getDtoClassName(clazz)» {
		    «clazz.generateAttributes(newArrayList())»
		    «ClassifierDtoClassGenerator.generateAttributes(clazz, clazz)»
		}
		
		'''
	}
	
	
	
	/**
	 * génère les imports
	 */
	static def generateImports(AssociationClass clazz){
		val attributesTypes = clazz.generateAttributesImports(newArrayList())
		
		val options = new ClassifierUtils.ImportOptions
		options.importInterface = true
		options.importInterfaceAttributes = false
		options.importValueObject = true
		options.importValueObjectAttributes = true
		
		//ajout des imports non pris en compte
		ClassifierUtils.getAttributesImport(clazz, clazz, attributesTypes, options)
		
		'''
		«attributesTypes.fold("")[acc, type |
			acc +  '''
			import { «ClassifierUtils.getDtoClassName(type as Classifier)» } from "«ClassifierUtils.getDtoClassPath(type as Classifier)»";
			'''
		]»
		'''
	}
	
	static def generateAttributesImports(Classifier clazz,  ArrayList<Type> types){
		var attributes = ClassifierUtils.getOwnedAttributes(clazz).filter[ attribut |
			(Utils.isEntity(attribut.type))
		]
		
		var attributesValueObject = ClassifierUtils.getOwnedAttributes(clazz).filter[ attribut |
			(Utils.isValueObject(attribut.type))
		]
		
		var attributesEnums = ClassifierUtils.getOwnedAttributes(clazz).filter[ attribut |
			(Utils.isNomenclature(attribut.type))
		]
		
		if(clazz instanceof AssociationClass){
			attributes = clazz.ownedEnds.filter[ attribut |
				(Utils.isEntity(attribut.type))
			]
			
			attributesValueObject = clazz.ownedEnds.filter[ attribut |
				(Utils.isValueObject(attribut.type))
			]
			
			attributesEnums = clazz.ownedEnds.filter[ attribut |
			(Utils.isNomenclature(attribut.type))
			]
		}
		
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
		
		attributesValueObject.forEach[attribut |
			var type = attribut.type
			if(type instanceof Classifier){
				type.generateAttributesImports(types)
			}
		]
		return types
	}
	
	/**
	 * génère les attributs simples de la classe
	 */
	static def generateAttributes(AssociationClass clazz, ArrayList<String> names){
		val attributes = clazz.memberEnds
		'''
		«attributes.fold("")[acc, attribut|
 			acc + '''
 			«attribut.generateAttribut(newArrayList(names))»'''	
 		]»
 		'''
	}
	
	
	/**
	 * génère un attribut de la classe
	 */
	static def generateAttribut(Property property, ArrayList<String> names){
		if(PropertyUtils.isClassAttribute(property)){
			'''«property.generateClassAttribute(names)»'''
		}else{
			'''«property.generateBasicAttribute(names)»'''
		}
	}
	
	/**
	 * génère un attribut lié a la classe
	 */
	static def generateClassAttribute(Property property, ArrayList<String> names){
		if(Utils.isValueObject(property.type)){
			'''«property.generateValueObjectAttribute(names)»'''
		}else if(Utils.isNomenclature(property.type)){
			'''«property.generateEnumAttributes(names)»'''
		}else{
			'''«property.generateEntityAttributes(names)»'''
		}
	}
	
	/**
	 * génère un attribut de type value Object
	 */
	static def generateValueObjectAttribute(Property property, ArrayList<String> names){
		val name = property.name //Utils.addAdditionnalName(Utils.getNameFromList(names), property.name)
		val type = property.type
		names.add(name)
		if(type instanceof Classifier){
			if(!property.multivalued){
				return '''«type.generateAttributes(names)»
				'''
			}
		}else{
			''''''
		}
	}
	
	static def generateEnumAttributes(Property property, ArrayList<String> names){
		val type = property.type
		val name = Utils.addAdditionnalName(Utils.getNameFromList(names), property.name)
		val codeName = Utils.addAdditionnalName(Utils.getNameFromList(names), "code" + Utils.getFirstToUpperCase(property.name)) 
		if(type instanceof Classifier){
			'''
			
			@Map()
			«codeName»: number;
			
			@Map()
			«Utils.getFirstToLowerCase(name)»: «ClassifierUtils.getDtoClassName(type)»;
			'''
		}
	}
	
	/**
	 * génère un attribut de type entity
	 */
	static def generateEntityAttributes(Property property, ArrayList<String> names){
		val type = property.type
		val name = Utils.addAdditionnalName(Utils.getNameFromList(names), property.name)
		if(type instanceof Classifier){
			val ids = ClassifierUtils.getId(type)
			'''
			«ids.fold("")[acc, id |
				acc + '''«property.generateEntityAttribute(id, names)»'''
			]»
			
			@Map()
			«Utils.getFirstToLowerCase(name)»: «ClassifierUtils.getDtoClassName(type)»;
			'''
		}else{
			''''''
		}
		
	}
	
	/**
	 * génère les attributs simples de la classe
	 */
	static def generateAttributes(Classifier clazz, ArrayList<String> names){
		val attributes = ClassifierUtils.getOwnedAttributes(clazz)
		'''
		«attributes.fold("")[acc, attribut|
 			acc + '''
 			«attribut.generateAttribut( names)»'''	
 		]»
 		'''
	}
	
	static def generateEntityAttribute(Property property, Property id, ArrayList<String> names){
		val fieldName = Utils.addAdditionnalName(Utils.getNameFromList(names), id.name) + Utils.getFirstToUpperCase(property.name)
		val propName = Utils.addAdditionnalName(Utils.getNameFromList(names), property.name)
		val type = id.owner
		if(!property.multivalued){
/* 			if (!names.empty){
				'''
				
				@Map()
				@Alias('«fieldName»', '«Utils.getListPoint(names)».«property.name».«id.name»')
				«Utils.getFirstToLowerCase(fieldName)»: «TypeUtils.getMetierTypescriptType(id.type)»;
				'''
			}else{
				'''
				
				@Map()
				@Alias('«fieldName»', '«Utils.getFirstToLowerCase((type as Classifier).name)».«id.name»')
				«Utils.getFirstToLowerCase(fieldName)»: «TypeUtils.getMetierTypescriptType(id.type)»;
				'''
			}
*/				'''
				
				@Map()
				@Alias("«fieldName»", "«IF !names.isEmpty»«Utils.getListPoint(names)».«ENDIF»«property.name».«id.name»")
				«Utils.getFirstToLowerCase(fieldName)»: «TypeUtils.getMetierTypescriptType(id.type)»;
				'''
		}else{
			''''''
		}
		
	}
	
	/**
	 * génère un attribut de l'interface
	 */
	static def generateBasicAttribute(Property property, ArrayList<String> names){
		var name = Utils.addAdditionnalName(Utils.getNameFromList(names), property.name)
		if(!property.multivalued){
			'''
			
			@Map()
			@Alias("«name»"«IF !names.isEmpty», "«Utils.getListPoint(names)».«property.name»"«ENDIF»)
			«Utils.getFirstToLowerCase(name)»: «TypeUtils.getMetierTypescriptType(property.type)»;
			'''
		}else{
			''''''
		}
	}
}