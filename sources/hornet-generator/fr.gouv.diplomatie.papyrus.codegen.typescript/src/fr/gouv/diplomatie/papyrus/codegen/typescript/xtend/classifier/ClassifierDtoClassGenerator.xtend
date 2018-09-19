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
 * @version v1.2.1
 * @license CECILL-2.1
 */
package fr.gouv.diplomatie.papyrus.codegen.typescript.xtend.classifier;

import org.eclipse.uml2.uml.Classifier
import org.eclipse.uml2.uml.Property
import fr.gouv.diplomatie.papyrus.codegen.core.utils.ClassifierUtils
import fr.gouv.diplomatie.papyrus.codegen.core.utils.Utils
import fr.gouv.diplomatie.papyrus.codegen.core.utils.PropertyUtils
import fr.gouv.diplomatie.papyrus.codegen.typescript.utils.TypeUtils
import org.eclipse.uml2.uml.Type
import java.util.ArrayList
import org.eclipse.uml2.uml.PrimitiveType
import org.eclipse.uml2.uml.AssociationClass
import org.eclipse.uml2.uml.Interface

public class ClassifierDtoClassGenerator{
	
	static def generateCode(Classifier clazz){
		'''
		import Alias from "hornet-js-bean/src/decorators/Alias";
		import Bean from "hornet-js-bean/src/decorators/Bean";
		import Map from "hornet-js-bean/src/decorators/Map";
		
		«clazz.generateImports»
		
		@Bean
		export class «ClassifierUtils.getDtoClassName(clazz)» «clazz.generateExtends»{
		    «clazz.generateExtendsAttributes»
		    «clazz.generateInterfaceAttributes»
		    «clazz.generateAttributes(clazz)»
		    «clazz.generateAssociationAttributes»
		    «clazz.generateManyToManyAttributes»
		}
		
		«clazz.generateMultivaluedAttributeDto»
		'''
		//«clazz.generateOneToManyAttributes»
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
		
		@Map(«ClassifierUtils.getDtoClassName(type)»)
		«prop.name»: Array<«ClassifierUtils.getDtoClassName(type)»>;
		'''
	}
	
	static def generateOneToManyAttributes(Classifier clazz){
		val attributes = ClassifierUtils.getOneToManyAttributes(clazz)
		'''
		«attributes.fold("")[ acc, attr |
			acc + '''«attr.generateOneToManyAttribute(clazz)»'''
		]»
		'''
	}
	
	static def generateOneToManyAttribute(Property property, Classifier clazz){
		//val owner = property.owner as Classifier
		val owner = property.type as Classifier
		val fieldName = Utils.getFirstToLowerCase(property.name)
		val id = ClassifierUtils.getId(owner).get(0)
		val idName = Utils.getFirstToLowerCase(id.name) + Utils.getFirstToUpperCase(fieldName)
		val alias = fieldName + '.' + Utils.getFirstToLowerCase(id.name)
		'''
		
		@Map()
		@Alias("«idName»", "«alias»")
		«idName»: «TypeUtils.getMetierTypescriptType(id.type)»;
		
		@Map(«ClassifierUtils.getDtoClassName(owner)»)
		«fieldName»: «ClassifierUtils.getDtoClassName(owner)»;
		'''
	}
	
	/**
	 * génère les imports
	 */
	static def generateImports(Classifier clazz){
		
		val options = new ClassifierUtils.ImportOptions
		options.importInterface = false
		options.importInterfaceAttributes = true
		options.importValueObject = false
		options.importValueObjectAttributes = true
		options.importAssociationClassAttributes = true
		options.importAssociationMemberAttributes = true
		
		val attributesTypes = ClassifierUtils.getAttributesImport(clazz, clazz,  newArrayList(), options)
		'''
		«clazz.generateExtendsImports»
		«attributesTypes.fold("")[acc, type |
			acc +  '''
			import { «ClassifierUtils.getDtoClassName(type as Classifier)» } from "«ClassifierUtils.getDtoClassPath(type as Classifier)»";
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
				acc + '''import { «ClassifierUtils.getDtoClassName(parent.general)» } from "«ClassifierUtils.getDtoClassPath(parent.general)»";
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
			(Utils.isEntity(attribut.type))
		]
		val attributesValueObject = ClassifierUtils.getOwnedAttributes(clazz).filter[ attribut |
			(Utils.isValueObject(attribut.type))
		]
		
		val attributesEnums = ClassifierUtils.getOwnedAttributes(clazz).filter[ attribut |
			(Utils.isNomenclature(attribut.type))
		]
		
		val interfaces = clazz.directlyRealizedInterfaces
		
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
			interface.generateAttributesImports(types)
		}
		
		if(Utils.isEntity(clazz)){
			val assosiationClasses = ClassifierUtils.getLinkedAssociationClass(clazz)
			
			assosiationClasses.forEach[asso |
				if(!types.contains(asso) ){
					types.add(asso)
				}
			]
		}
		
		attributesValueObject.forEach[attribut |
			var type = attribut.type
			if(type instanceof Classifier){
				type.generateAttributesImports(types)
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
		if(parents.length > 0){
			'''«parents.fold("")[acc, parent |
				if(acc != ""){
					acc + ''',«ClassifierUtils.getDtoClassName(parent.general)»'''
				}else{
					acc + '''extends «ClassifierUtils.getDtoClassName(parent.general)»'''
				}
			]»'''
		}else{
			''''''
		}
	}
	
	/**
	 * génère les attributs liés aux extends
	 */
	static def generateExtendsAttributes(Classifier clazz){
		val parents = clazz.generalizations
		if(parents !== null && !parents.empty){
			'''«parents.fold("")[acc, parent |
				acc + 
				'''
				
				@Map(«ClassifierUtils.getDtoClassName(parent.general)»)
				«Utils.getFirstToLowerCase(parent.general.name)»: «ClassifierUtils.getDtoClassName(parent.general)»;
				'''
			]»'''
		}else{
			''''''
		}
	}
	
	static def generateAssociationAttributes(Classifier clazz){
		val assosiationClasses = ClassifierUtils.getLinkedAssociationClass(clazz)
		'''
		«assosiationClasses.fold("")[acc, asso |
 			acc+ '''«(asso as AssociationClass).generateAssociationClassAtributes(clazz)»'''
 		]»
		'''
	}
	
	static def generateInterfaceAttributes(Classifier clazz){
		val interfaces = clazz.directlyRealizedInterfaces
		'''
		«interfaces.fold("")[acc, interface |
			acc + '''«interface.generateAttributes(clazz)»'''
		]»
		'''
	}
	 
	
	/**
	 * génère les attributs simples de la classe
	 */
	static def generateAttributes(Classifier clazz, Classifier fromClass){
		val attributes = ClassifierUtils.getOwnedAttributes(clazz)
		
		'''
		«attributes.fold("")[acc, attribut|
 			acc + '''
 			«attribut.generateAttribut(newArrayList(), fromClass)»'''	
 		]»
 		'''
	}
	
	/**
	 * génère les attributs simples de la classe
	 */
	static def generateAttributes(Classifier clazz, ArrayList<String> names, Classifier fromClass){
		val attributes = ClassifierUtils.getOwnedAttributes(clazz)
		
		'''
		«attributes.fold("")[acc, attribut|
 			acc + '''
 			«attribut.generateAttribut(newArrayList(names), fromClass)»'''	
 		]»
 		'''
	}
	
	/**
	 * génère un attribut de la classe
	 */
	static def generateAttribut(Property property, ArrayList<String> names, Classifier fromClass){
		if(PropertyUtils.isClassAttribute(property)){
			'''«property.generateClassAttribute(names, fromClass)»'''
		}else{
			'''«property.generateBasicAttribute(names)»'''
		}
	}
	
	/**
	 * génère un attribut lié a la classe
	 */
	static def generateClassAttribute(Property property, ArrayList<String> names, Classifier fromClass){
		if(Utils.isValueObject(property.type)){
			'''«property.generateValueObjectAttribute(names, fromClass)»'''
		}else if(Utils.isNomenclature(property.type)){
			'''«property.generateEnumAttributes(names)»'''
		}else{
			'''«property.generateEntityAttributes(names)»'''
		}
	}
	
	/**
	 * génère un attribut de type value Object
	 */
	static def generateValueObjectAttribute(Property property, ArrayList<String> names, Classifier fromClass){
		val name = Utils.addAdditionnalName(Utils.getNameFromList(names), property.name)
		val type = property.type
		names.add(property.name)
		if(type instanceof Classifier){
			if(!property.multivalued){
				return 
				'''
				«type.generateAttributes(names, fromClass)»
				'''
			}else{
				val tableName = Utils.addAdditionnalName(fromClass.name, property.name)
				'''
				
				@Map()
				«name»: Array<«Utils.getFirstToUpperCase(tableName)»DTO>;
				'''
			}
		}else{
			''''''
		}
	}
	
	/**
	 * génère un attribut de type entity ou enum
	 */
	static def generateEntityAttributes(Property property, ArrayList<String> names){
		val type = property.type
		val name = Utils.addAdditionnalName(Utils.getNameFromList(names), property.name)
		val propName = Utils.getListPoint(names) + '.' + property.name 
		//names.add(property.name)
		var alias = ""
		if(!names.empty){
			alias = '''
			@Alias("«name»", "«propName»")'''
		}
		if(type instanceof Classifier){
			if(!property.multivalued){
				val ids = ClassifierUtils.getId(type)
				
				return '''
				«ids.fold("")[acc, id |
					acc + '''«property.generateEntityAttribute(id, names)»'''
				]»
				
				@Map(«ClassifierUtils.getDtoClassName(type)»)
				«alias»
				«name»: «ClassifierUtils.getDtoClassName(type)»;
				'''
				
			}else{
				return '''
				
				@Map(«ClassifierUtils.getDtoClassName(type)»)
				«alias»
				«name»: Array<«ClassifierUtils.getDtoClassName(type)»>;
				'''
			}
		}else{
			''''''
		}
		
	}
	
	static def generateEnumAttributes(Property property, ArrayList<String> names){
		val type = property.type
		val name = Utils.addAdditionnalName(Utils.getNameFromList(names), property.name)
		val propName = Utils.getListPoint(names) + '.' + property.name 
		val codeName = Utils.addAdditionnalName(Utils.getNameFromList(names),property.name + "Code" )
		val codeAliasName = propName + '.code'
		var alias = ""
		var codeAlias = ""
		if(!names.empty){
			alias = '''
			@Alias("«name»", "«propName»")'''
			codeAlias = '''
			@Alias("«codeAliasName»")'''
			
		}
		if(type instanceof Classifier){
			if(!property.multivalued){
				
				return '''
				
				@Map()
				«codeAlias»
				«codeName»: number;
				
				@Map(«ClassifierUtils.getDtoClassName(type)»)
				«alias»
				«name»: «ClassifierUtils.getDtoClassName(type)»;
				'''
				
			}else{
				return '''
				@Map()
				«codeAlias»
				«codeName»: Array<number>;
				
				@Map(«ClassifierUtils.getDtoClassName(type)»)
				«alias»
				«name»: Array<«ClassifierUtils.getDtoClassName(type)»>;
				'''
			}
		}
	}
	
	static def generateEntityAttribute(Property property, Property id, ArrayList<String> names){
		val fieldName = Utils.addAdditionnalName(Utils.getNameFromList(names), id.name) + Utils.getFirstToUpperCase(property.name)
		//val propName = Utils.addAdditionnalName(Utils.getNameFromList(names), property.name)
		names.add(property.name)
		if(!property.multivalued){
			if(names.empty){
				'''
				
				@Map()
				@Alias("«fieldName»")
				«fieldName»: «TypeUtils.getMetierTypescriptType(id.type)»;
				'''
			}else{
				'''
				
				@Map()
				@Alias("«fieldName»", "«Utils.getListPoint(names)».«id.name»")
				«fieldName»: «TypeUtils.getMetierTypescriptType(id.type)»;
				'''
			}
			
		}else{
			''''''
		}
		
	}
	
	/**
	 * génère un attribut de l'interface
	 */
	static def generateBasicAttribute(Property property, ArrayList<String> names){
		var name = Utils.addAdditionnalName(Utils.getNameFromList(names), property.name)
		var alias = ''''''
		if(!names.empty){
			alias = '''
			
			@Alias("«Utils.getListPoint(names)».«property.name»", "«name»")
			'''
		}
		if(!property.multivalued){
			'''
			
			@Map()«alias»
			«name»: «TypeUtils.getMetierTypescriptType(property.type)»;
			'''
		}else{
			''''''
		}
	}
	
	
	/**
	 * génère les dto liés aux attributs multi valués 
	 */
	static def generateMultivaluedAttributeDto(Classifier clazz){
		val attributes = ClassifierUtils.getMultivaluedOwnedAttributes(clazz)
		
		val primitiveAttributes = attributes.filter[attribut |
			val type = attribut.type
			return ( type instanceof PrimitiveType)
		]
		
		val ownedAttributes = ClassifierUtils.getOwnedAttributes(clazz)

		val multiAttributes = ownedAttributes.filter[attribut |
			val type = attribut.type
			return ((Utils.isEntity(type) || Utils.isValueObject(type) || Utils.isNomenclature(type)) && (attribut.multivalued) && (!PropertyUtils.isOneToManyAttributes(attribut)))
		]
		
		val interfaces = clazz.directlyRealizedInterfaces
		
		return '''
		«primitiveAttributes.fold("")[acc, attribut|
			acc + '''«attribut.generateMultiValuedPrimitiveTypeDto(clazz)»'''
 		]»
		«multiAttributes.fold("")[acc, attribut |
 			acc + '''«attribut.generateMultiValuedNPTypeDto(clazz)»'''
 		]»«interfaces.fold("")[acc, interface |
 			acc + '''«interface.generateInterfaceMultivaluedDto(clazz)»'''
 		]»
		'''
		
	}
	
	/**
	 * génère le model d'un attribut multivalué
	 */
	static def generateMultiValuedPrimitiveTypeDto(Property property, Classifier fromClass){
		val ids = ClassifierUtils.getId(fromClass)
		'''
		
		export class «PropertyUtils.getMultivaluedPropertyDtoName(property, fromClass)» {
			
			@Map()
			«property.name»: «TypeUtils.getMetierTypescriptType(property.type)»;
			«ids.fold("")[acc, id |
				if(acc != ""){
					acc + ''',«property.generateMultiValuedPrimitiveTypeDtoIdAttributes(id)»'''
				}else{
					acc + '''«property.generateMultiValuedPrimitiveTypeDtoIdAttributes(id)»'''
				}
			]»
			
			@Map(«ClassifierUtils.getDtoClassName(fromClass)»)
			«Utils.getFirstToLowerCase(fromClass.name)»: «ClassifierUtils.getDtoClassName(fromClass)»;
		}
		'''
	}
	
	static def generateMultiValuedPrimitiveTypeDtoIdAttributes(Property property, Property id){
		val idName = id.name
		'''
		
		@Map()
		«idName»: «TypeUtils.getMetierTypescriptType(id.type)»;
		'''
		
	}
	
	
	/**
	 * génère les table d'association 
	 */	
	 static def generateMultiValuedNPTypeDto(Property property, Classifier fromClass){
	 	if(Utils.isEntity(property.type)){
	 		'''«property.generateMultiValuedEntityDto(fromClass)»'''
	 	}else if(Utils.isValueObject(property.type)){
	 		'''«property.generateMultiValuedValueObjectDto(fromClass)»'''
	 	}else if (Utils.isNomenclature(property.type)){
	 		'''«property.generateMultiValuedEnumDto(fromClass)»'''
	 	}
	 	
	}
	
	static def generateMultiValuedEntityDto(Property property, Classifier fromClass){
		val type = property.type
	 	val owner = property.owner
	 	if(property.association !== null){
	 		if(type != owner){
		 		'''
		 		«property.generateNPTypeAssociationDto(fromClass)»
		 		'''
	 		}else{
	 			''''''
	 		}
	 	}else{
	 		'''
	 		«property.generateNPTypeDto(fromClass)»
	 		'''
	 	}
	}
	
	static def generateNPTypeAssociationDto(Property property, Classifier fromClass){
		
		val members = property.association.memberEnds.filter[attr | attr.type == fromClass]
 		val member = members.get(0)
		val idsOwner = ClassifierUtils.getId(fromClass)
		val idsProp = ClassifierUtils.getId(member.type as Classifier)
		val type = property.type
		'''
		
		export class «PropertyUtils.getMultivaluedPropertyDtoName(property, fromClass)»{
			«idsProp.fold("")[acc, id |
				if(acc != ""){
					acc + '''«member.generateNPTDtoIdAttributes(id)»'''
				}else{
					acc + '''«member.generateNPTDtoIdAttributes(id)»'''
				}
			]»
			
			@Map(«ClassifierUtils.getDtoClassName(fromClass)»)
			«Utils.getFirstToLowerCase(fromClass.name)»: «ClassifierUtils.getDtoClassName(fromClass)»;
			«idsOwner.fold("")[acc, id |
				if(acc != ""){
					acc + '''«property.generateNPTDtoIdAttributes(id)»'''
				}else{
					acc + '''«property.generateNPTDtoIdAttributes(id)»'''
				}
			]»
			
			@Map(«ClassifierUtils.getDtoClassName(type as Classifier)»)
			«Utils.getFirstToLowerCase(type.name)»: «ClassifierUtils.getDtoClassName(type as Classifier)»;
		}
		'''
	}
	
	static def generateNPTDtoIdAttributes(Property property, Property id){
		val type = property.type
		val idName = Utils.addAdditionnalName(id.name, type.name)
		if(type instanceof Classifier){
			'''
			
			@Map()
			«idName»: «TypeUtils.getMetierTypescriptType(id.type)»;
			'''
		}
	}
	
	
	static def generateNPTypeDto(Property property, Classifier fromClass){
		val idsOwner = ClassifierUtils.getId(fromClass)
		val idsProp = ClassifierUtils.getId(property.type as Classifier)
		val type = property.type
		'''
		
		export class «PropertyUtils.getMultivaluedPropertyDtoName(property, fromClass)»{
			«idsOwner.fold("")[acc, id |
				if(acc != ""){
					acc + ''',«id.generateNPTModelIdAttributes()»'''
				}else{
					acc + '''«id.generateNPTModelIdAttributes()»'''
				}
			]»
			
			@Map(«ClassifierUtils.getDtoClassName(fromClass)»)
			«Utils.getFirstToLowerCase(fromClass.name)»: «ClassifierUtils.getDtoClassName(fromClass)»;
			«idsProp.fold("")[acc, id |
				if(acc != ""){
					acc + ''',«property.generateNPTDtoIdAttributes(id)»'''
				}else{
					acc + '''«property.generateNPTDtoIdAttributes(id)»'''
				}
			]»
			
			@Map(«ClassifierUtils.getDtoClassName(type as Classifier)»)
			«Utils.getFirstToLowerCase(type.name)»: «ClassifierUtils.getDtoClassName(type as Classifier)»;
		}
		'''
	}
	
	static def generateNPTModelIdAttributes(Property id){
		val type = id.owner
		if(type instanceof Classifier){
			val idName = Utils.addAdditionnalName(id.name, type.name)
			'''
			
			@Map()
			«idName»: «TypeUtils.getMetierTypescriptType(id.type)»;
			'''
		}
	}
	
	static def generateAssociationClassAtributes(AssociationClass clazz, Classifier fromClass){
		val members = clazz.memberEnds.filter[member |
			member.type !== fromClass
		]
		
		'''
		«members.fold("")[acc, mem |
			val name = mem.name
			val type = mem.type as Classifier
			acc + 
			'''
			
			@Map(«ClassifierUtils.getDtoClassName(type)»)
			«Utils.getFirstToLowerCase(name)»: Array<«ClassifierUtils.getDtoClassName(type)»>;
			'''
		]
		»
		
		@Map(«ClassifierUtils.getDtoClassName(clazz)»)
		«Utils.getFirstToLowerCase(clazz.name)»: Array<«ClassifierUtils.getDtoClassName(clazz)»>;
		'''
		
	}
	
	static def generateMultiValuedValueObjectDto(Property property, Classifier fromClass){
		val idsOwner = ClassifierUtils.getId(fromClass)
		val type = property.type
		'''
		
		export class «PropertyUtils.getMultivaluedPropertyDtoName(property, fromClass)»{
			«idsOwner.fold("")[acc, id |
				if(acc != ""){
					acc + ''',«id.generateNPTModelIdAttributes()»'''
				}else{
					acc + '''«id.generateNPTModelIdAttributes()»'''
				}
			]»
			
			@Map(«ClassifierUtils.getDtoClassName(fromClass)»)
			«Utils.getFirstToLowerCase(fromClass.name)»: «ClassifierUtils.getDtoClassName(fromClass)»;
			«(type as Classifier).generateAttributes(newArrayList(property.name), type as Classifier)»
			
		}
		'''
	}
	
	
	static def generateMultiValuedEnumDto(Property property, Classifier fromClass){
		val idsOwner = ClassifierUtils.getId(fromClass)
		val type = property.type
		'''
		
		export class «PropertyUtils.getMultivaluedPropertyDtoName(property, fromClass)»{
			«idsOwner.fold("")[acc, id |
				if(acc != ""){
					acc + ''',«id.generateNPTModelIdAttributes()»'''
				}else{
					acc + '''«id.generateNPTModelIdAttributes()»'''
				}
			]»
			
			@Map(«ClassifierUtils.getDtoClassName(fromClass)»)
			«Utils.getFirstToLowerCase(fromClass.name)»: «ClassifierUtils.getDtoClassName(fromClass)»;
			
			@Map(«ClassifierUtils.getDtoClassName(type as Classifier)»)
			«Utils.getFirstToLowerCase(property.name)»: «ClassifierUtils.getDtoClassName(type as Classifier)»;
		}
		'''
	}
	
	static def generateInterfaceMultivaluedDto(Interface interf, Classifier fromClass){
		val attributes = ClassifierUtils.getMultivaluedOwnedAttributes(interf)
		
		'''
		«attributes.fold("")[acc, attribut|
 			acc + '''
 			«attribut.generateMultiValuedNPTypeDto( fromClass)»'''	
 		]»
 		'''
	}
	
}