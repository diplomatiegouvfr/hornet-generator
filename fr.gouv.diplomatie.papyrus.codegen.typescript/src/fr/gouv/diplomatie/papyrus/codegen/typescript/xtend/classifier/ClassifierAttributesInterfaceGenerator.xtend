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
import fr.gouv.diplomatie.papyrus.codegen.core.utils.Utils
import fr.gouv.diplomatie.papyrus.codegen.typescript.utils.TypeUtils
import fr.gouv.diplomatie.papyrus.codegen.core.utils.ModelUtils
import org.eclipse.uml2.uml.AssociationClass

public class ClassifierAttributesInterfaceGenerator {
	
	static def generateCode(Classifier clazz){
		//«clazz.generateExtendsAttributes»
		'''
		«clazz.generateImports»
		
		export interface «ClassifierUtils.getAttributesInterfaceName(clazz)» «clazz.generateExtends»{
		    
		    «clazz.generateAttributes("")»

		    «clazz.generateNotPrimitiveTypeAttributes("")»
		    «IF Utils.isEntity(clazz)»«clazz.generateOneToManyAttributes»
		    «clazz.generateAllAssociationClassAtributes()»
		    «clazz.generateManyToManyAttributes»«ENDIF»
		}
		'''
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
		«prop.name»: Array<«ClassifierUtils.getAttributesInterfaceName(type)»>;
		get«Utils.getFirstToUpperCase(prop.name)»(): Promise<Array<«ClassifierUtils.getAttributesInterfaceName(type)»>>;
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
	
	static def generateOneToManyAttribute(Property property, Classifier fromClass){
		val owner = property.owner as Classifier
		val fieldName = Utils.getFirstToLowerCase(owner.name)+ Utils.getFirstToUpperCase(property.name)
		val id = ClassifierUtils.getId(owner).get(0);
		val idName = Utils.getFirstToLowerCase(id.name) + Utils.getFirstToUpperCase(fieldName)
		'''
		«idName»?: «TypeUtils.getTypescriptType(id.type)»;
		«fieldName»?: «ClassifierUtils.getAttributesInterfaceName(owner)»;
		get«Utils.getFirstToUpperCase(fieldName)»(): Promise<«ClassifierUtils.getAttributesInterfaceName(owner)»>;
		'''
	}
	
	/**
	 * génère les imports
	 */
	static def generateImports(Classifier clazz){
		
		val options = new ClassifierUtils.ImportOptions
		options.importInterface = true
		options.importInterfaceAttributes = false
		options.importValueObject = true
		options.importValueObjectAttributes = true
		
		val attributesTypes = ClassifierUtils.getAttributesImport(clazz, clazz, newArrayList(), options)
		'''
		«clazz.generateExtendsImports»
		«attributesTypes.fold("")[acc, type |
			acc +  '''
			import { «ClassifierUtils.getAttributesInterfaceName(type as Classifier)» } from "«ClassifierUtils.getAttributesInterfacePath(type as Classifier)»";
			'''
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
				acc + '''import { «ClassifierUtils.getAttributesInterfaceName(parent.general)» } from "«ClassifierUtils.getAttributesInterfacePath(parent.general)»";
				'''
			]»'''
		}else{
			''''''
		}
	}
	
	/**
	 * génère les imports liés aux types des attributs
	 */
/* 	 static def generateAttributesImports(Classifier clazz, ArrayList<Type> types){
	 	val attributes = ClassifierUtils.getOwnedAttributes(clazz).filter[ attribut |
			((Utils.isEntity(attribut.type)) && (attribut.type !== clazz))
		]
		
		val attributesEnums = ClassifierUtils.getOwnedAttributes(clazz).filter[ attribut |
			(Utils.isNomenclature(attribut.type))
		]
		
		val attributesValueObject = ClassifierUtils.getOwnedAttributes(clazz).filter[ attribut |
			(Utils.isValueObject(attribut.type))
		]
		if(Utils.isEntity(clazz)){
			val oneToManyAttributes = ClassifierUtils.getOneToManyAttributes(clazz)
			for(attribut : oneToManyAttributes){
				if(!types.contains(attribut.owner) && (attribut.owner != clazz)){
					types.add(attribut.owner as Classifier)
				}
			}
			
			val manyToManyAttributes = ClassifierUtils.getManyToManyAttributes(clazz)
			for(attribut : manyToManyAttributes){
				if(!types.contains(attribut.owner) && (attribut.owner != clazz)){
					types.add(attribut.owner as Classifier)
				}
			}
		}
		
		val interfaces = clazz.allRealizedInterfaces
		
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
		}
		
		if(Utils.isEntity(clazz)){
			val assosiationClasses = ClassifierUtils.getLinkedAssociationClass(clazz)
			
			assosiationClasses.forEach[asso |
				if(!types.contains(asso)){
					types.add(asso)
				}
			]
		}
	
		attributesValueObject.forEach[attribut |
			var type = attribut.type
			if(type instanceof Classifier){
				type.generateAttributesImports(types)
			}
			if(!types.contains(type)){
				types.add(type)
			}
		]
		return types
	 }
*/	
	/**
	 * génère les extends
	 */
	static def generateExtends(Classifier clazz){
		val parents = clazz.generalizations
		val interfaces = clazz.directlyRealizedInterfaces
		val extendsInterfaces = '''«interfaces.fold("")[acc, interface |
			if(acc != ""){
				acc + ''', «ClassifierUtils.getAttributesInterfaceName(interface)»''' 
			}else{
				acc + '''«ClassifierUtils.getAttributesInterfaceName(interface)»''' 
			}
		]»'''
		if(parents.length > 0){
			val parent = parents.get(0)
			var separator = ""
			if(interfaces !== null && !interfaces.empty){
				separator = ","
			}
			'''extends «ClassifierUtils.getAttributesInterfaceName(parent.general)»«separator» «extendsInterfaces»'''
		}else if(interfaces !== null && !interfaces.empty){
			'''extends «extendsInterfaces»'''
		}
	}
	
	static def generateExtendsAttributes(Classifier clazz){
		val parents = clazz.generalizations
		
		if(parents !== null && !parents.empty){
			'''«parents.fold("")[acc, parent |
				acc + 
				'''
				«Utils.getFirstToLowerCase(parent.general.name)»?: «ClassifierUtils.getAttributesInterfaceName(parent.general)»;
				'''
			]»'''
		}else{
			''''''
		}
	}
	
	
	/**
	 * génère les attributs simples de la classe
	 */
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
	 * génère un attribut de l'interface
	 */
	static def generateBasicAttribute(Property property, String additionnalName){
		var name = Utils.addAdditionnalName(additionnalName, property.name)
		'''
		«name»?: «TypeUtils.getTypescriptType(property.type)»;
		'''
	}

	
	/**
	 * génère un attribut lié a la classe
	 */
	static def generateClassAttribute(Property property, String additionnalName){
		if(Utils.isValueObject(property.type)){
			'''«property.generateValueObjectAttribute(additionnalName)»'''
		}else if(Utils.isNomenclature(property.type)){
			'''«property.generateEnum(additionnalName)»'''
		}else{
			'''«property.generateEntityAttributes(additionnalName)»'''
		}
	}
	
	/**
	 * génère un attribut de type value Object
	 */
	static def generateValueObjectAttribute(Property property, String additionnalName){
		val name = Utils.addAdditionnalName(additionnalName, property.name)
		val type = property.type
		if(type instanceof Classifier){
			return '''«type.generateAttributes(name)»
			'''
		}else{
			''''''
		}
	}
	
	/**
	 * génère une classe enum
	 */
	static def generateEnum(Property property, String additionnalName){
		val propName =  Utils.addAdditionnalName(additionnalName, property.name) 
		val type = property.type
		val codeName = Utils.addAdditionnalName(additionnalName, "code" + Utils.getFirstToUpperCase(property.name)) 
		'''
		«codeName»?: number;
		«propName»?: «ClassifierUtils.getAttributesInterfaceName(type as Classifier)»;
		'''
	}
	
	/**
	 * génère un attribut de type entity
	 */
	static def generateEntityAttributes(Property property, String additonnalName){
		val type = property.type
		if(type instanceof Classifier){
			val ids = ClassifierUtils.getId(type)
			'''
			«ids.fold("")[acc , id |
				acc + '''
				«property.generateEntityAttribute(id, additonnalName)»'''
			]»
			'''
		}else{
			''''''
		}
		
	}
	
	/**
	 * génère un attribut de type entity
	 */
	static def generateEntityAttribute(Property property, Property id,  String additonnalName){
		val type = id.type
		val propName =  Utils.addAdditionnalName(additonnalName, id.name) + Utils.getFirstToUpperCase(property.name)
		
		'''
		«propName»?: «TypeUtils.getTypescriptType(type)»;
		'''
	}
	
	/**
	 * génère les attributs liés aux liens multivalués du model 
	 */
	static def generateMultiValuedEntityAttributes(Classifier clazz, String additionnalName){
		val model = clazz.model
		val classes = ModelUtils.getAllClasses(model)
		val references = newArrayList()
		for(classe : classes){
			references.addAll(ClassifierUtils.getMultivaluedReferencesToType(classe as Classifier, clazz))
		}
		val attributesEntity = ClassifierUtils.getOwnedAttributes(clazz).filter[attribut |
			Utils.isEntity(attribut.type) && attribut.multivalued
		]
		return
		'''
		«references.fold("")[acc, ref |
			acc + '''«ref.generateReferenceAttributes(clazz, additionnalName)»'''
		]»
		«attributesEntity.fold("")[acc, attribut |
			acc + '''«attribut.generateEntityIdsAttributes(additionnalName)»'''
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
		val fromClass = property.association.ownedEnds.get(0).type
		if(fromClass instanceof Classifier){
			val ids = ClassifierUtils.getId(fromClass)
			'''«ids.fold("")[acc, id |
				acc + '''«property.generateReferenceAttributeAssocation(id, clazz, additionnalName)»'''
			]»'''
		}else {
			''''''
		}
	}
	
	/**
	 *  génère l'attribut lié au lien multivalué du model  - id
	 */
	static def generateReferenceAttributeAssocation(Property property, Property id, Classifier clazz, String additionnalName){
		val field =  property.association.ownedEnds.get(0)
		val name = id.name + Utils.getFirstToUpperCase(field.name)
		var fieldName = Utils.addAdditionnalName(additionnalName, name)
		val refName = field.name
		var array = ""
		var endArray = ""
		if(field.isMultivalued){
			array = "Array<"
			endArray =">"
		}
		val type = field.type
		if(type instanceof Classifier){
			'''
			
			«fieldName»?: «TypeUtils.getTypescriptType(id.type)»;
			«refName»: «array»«ClassifierUtils.getAttributesInterfaceName(type)»«endArray»;
			get«Utils.getFirstToUpperCase(refName)»(): Promise<«array»«ClassifierUtils.getAttributesInterfaceName(type)»«endArray»>;
			'''
		}
	}
	
	/**
	 * génère les attributs liés a une référence qui ne viens pas d'une association
	 */
	static def generateReferenceAttributesNA(Property property, Classifier clazz, String additionnalName){
		val fromClass = property.owner
		if(fromClass instanceof Classifier){
			val ids = ClassifierUtils.getId(fromClass)
			'''«ids.fold("")[acc, id |
				acc + '''«property.generateReferenceAttributeNA(id, clazz, additionnalName)»'''
			]»'''
		}
	}
	
	/**
	 * génère l'attribut lié a une référence qui ne viens pas d'une association
	 */
	static def generateReferenceAttributeNA(Property property, Property id, Classifier clazz, String additionnalName){
		val fromClass = property.owner
		if(fromClass instanceof Classifier){
			val name = property.name + Utils.getFirstToUpperCase(id.name)
			val fieldName = Utils.addAdditionnalName(additionnalName, name)
			'''
			«fieldName»?: «TypeUtils.getTypescriptType(property.type)»;'''
		}
	}
	
	/**
	 * génère la déclaration des attributs id lié aux attribut de type entité multivalué
	 */
	static def generateEntityIdsAttributes(Property property, String additionnalName){
		val type = property.type
		if(type instanceof Classifier){
			val ids = ClassifierUtils.getId(type)
			'''
			«ids.fold("")[acc, id |
				acc + '''«property.generateEntityIdAttribute(id, additionnalName)»'''
			]»
			'''
		}
		
	}
	
	static def generateEntityIdAttribute(Property property, Property id, String additionnalName){
		val name = property.name + Utils.getFirstToUpperCase(id.name)
		'''
		«name»: Array<«TypeUtils.getTypescriptType(id.type)»>;
		'''
	}
	
	/**
	 * génère les attributs multivalués ou de type non primitifs
	 */
	static def generateNotPrimitiveTypeAttributes(Classifier clazz, String additionnalName){
		val attributes = ClassifierUtils.getOwnedAttributes(clazz).filter[ attribut |
			(attribut.multivalued || Utils.isEntity(attribut.type) || Utils.isValueObject(attribut.type))
		]
		'''
		«attributes.fold("")[acc, attribut|
 			acc + '''
 			«attribut.generateNotPrimitiveTypeAttribut(additionnalName)»'''	
 		]»
 		'''
	}
	
	static def generateNotPrimitiveTypeAttribut(Property property, String additionnalName){
		if(Utils.isEntity(property.type)){
			'''«property.generateNPTEntityAttribut(additionnalName)»'''
		}else if(Utils.isValueObject(property.type)){
			'''«property.generateNPTValueObjectAttribut(additionnalName)»'''
		}else if(property.multivalued){
			var name = Utils.addAdditionnalName(additionnalName, property.name)
			if(Utils.isNomenclature(property.type)){
				'''
				«name»: Array<«ClassifierUtils.getAttributesInterfaceName(property.type as Classifier)»>;
				
				'''
			}else{
				'''
				«name»: Array<«TypeUtils.getTypescriptType(property.type)»>;
				
				'''
			}
		}
	}
	
	static def generateNPTEntityAttribut(Property property, String additionnalName){
		var name = "";
		/* 
		if(property.association !== null && property.association.name!== null){
			name = property.association.name
		}else{*/
			name = Utils.addAdditionnalName(additionnalName,property.name)
		//}
		
		if(property.type instanceof Classifier){
			if(property.multivalued){
				'''
				«name»: Array<«ClassifierUtils.getAttributesInterfaceName(property.type as Classifier)»>;
				get«Utils.getFirstToUpperCase(name)»(): Promise<Array<«ClassifierUtils.getAttributesInterfaceName(property.type as Classifier)»>>;
				
				'''
			}else{
				'''
				«name»: «ClassifierUtils.getAttributesInterfaceName(property.type as Classifier)»;
				get«Utils.getFirstToUpperCase(name)»(): Promise<«ClassifierUtils.getAttributesInterfaceName(property.type as Classifier)»>;
				
				'''
			}
		}
	}
	
	static def generateNPTValueObjectAttribut(Property property, String additionnalName){
		val type = property.type
		if(type instanceof Classifier){
			val name = Utils.addAdditionnalName(additionnalName, property.name)
			var array = ""
			var endArray = ""
			if(property.isMultivalued){
				array = "Array<"
				endArray =">"
			}
			'''
			«type.generateNotPrimitiveTypeAttributes(name)»
			«property.name»: «array»«ClassifierUtils.getAttributesInterfaceName(type)»«endArray»;
			get«Utils.getFirstToUpperCase(property.name)»(): «array»«ClassifierUtils.getAttributesInterfaceName(type)»«endArray»;
			
			'''
			
		}
		
	}
	
	/**
	 * génère les attributs liés aux classe d'association
	 */
	static def generateAllAssociationClassAtributes(Classifier clazz){
		val assosiationClasses = ClassifierUtils.getLinkedAssociationClass(clazz)
		'''
		«assosiationClasses.fold("")[acc, asso |
			acc+ '''«(asso as AssociationClass).generateAssociationClassAtributes(clazz)»'''
		]»'''
	}
	
	/**
	 * génère les attributs liés a une table d'association
	 */
	static def generateAssociationClassAtributes(AssociationClass clazz, Classifier fromClass){
		val member = clazz.memberEnds.filter[member |
			member.type !== fromClass
		]
		
		if(member.length >1){
			'''
			«Utils.getFirstToLowerCase(clazz.name)» : Array<«ClassifierUtils.getAttributesInterfaceName(clazz)»>;
			get«Utils.getFirstToUpperCase(clazz.name)»(): Promise<Array<«ClassifierUtils.getAttributesInterfaceName(clazz)»>>;
			
			'''
		}else{
			val name = member.get(0).name
			'''
			«Utils.getFirstToLowerCase(name)» : Array<«ClassifierUtils.getAttributesInterfaceName(clazz)»>;
			get«Utils.getFirstToUpperCase(clazz.name)»(): Promise<Array<«ClassifierUtils.getAttributesInterfaceName(clazz)»>>;
			
			'''
		}
		

		
	}
	
}