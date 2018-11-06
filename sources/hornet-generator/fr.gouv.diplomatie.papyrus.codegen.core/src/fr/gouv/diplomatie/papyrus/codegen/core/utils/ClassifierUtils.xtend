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
 * fr.gouv.diplomatie.papyrus.codegen.core - Ensembles des outils mis à dispositions
 * pour l'écriture d'un générateur de code Hornet
 *
 * @author MEAE - Ministère de l'Europe et des Affaires étrangères
 * @version v1.3.2
 * @license CECILL-2.1
 */
package fr.gouv.diplomatie.papyrus.codegen.core.utils

import org.eclipse.uml2.uml.Classifier
import org.eclipse.uml2.uml.AttributeOwner
import fr.gouv.diplomatie.papyrus.codegen.core.generators.GeneratorUtils
import org.eclipse.uml2.uml.AssociationClass
import java.io.File
import java.util.ArrayList
import org.eclipse.uml2.uml.Type

/**
 * classe utilitaire pour les Classifier
 */
class ClassifierUtils {
	
	/**
	 * retourne le nom de la classe modèle liée à la classe
	 */
	static def getModelName(Classifier clazz){
		return Utils.getFirstToLowerCase(clazz.name) + "Model"
	}
	
	/**
	 * retourne le nom de l'ainterface attributes liée à la classe
	 */
	static def getAttributesInterfaceName(Classifier clazz){
		return clazz.name + "Attributes"
	}
	
	/**
	 * retourne le nom de la classe métier liée à la classe
	 */
	static def getMetierClassName(Classifier clazz){
		return clazz.name + "Metier"
	}
	
	/**
	 * retourne le nom du dto liée à la classe
	*/
	static def getDtoClassName(Classifier clazz){
		return clazz.name + "DTO"
	}
	
	/**
	 * retourne le nom de l'application
	 */
	static def getApplicationName(Classifier cl) {
		Utils.lowerCase(cl.allOwningPackages.get(0).model.name)
	}
	
	/**
	 * retourne le nom du domaine
	 */
	static def getDomainName(Classifier cl) {
		Utils.lowerCase(cl.allOwningPackages.get(0).name)
	}
	
	/**
	 * retourne le chemin du fichier de la classe Model
	 */
	static def getModelPath(Classifier clazz){
		return GeneratorUtils.getModelPath(clazz, true)
	}
	
	/**
	 * retourne le chemin du fichier de l'interface Attributes
	 */
	static def getAttributesInterfacePath(Classifier clazz){
		return GeneratorUtils.getAttributesInterfacePath(clazz, true)
	}
	
	/**
	 * retourne le chemin du fichier de la classe metier
	 */
	static def getMetierClassPath(Classifier clazz){
		return GeneratorUtils.getMetierClassPath(clazz, true)
	}
	
	/**
	 * retourne le chemin du fichier de la classe dto
	 */
	static def getDtoClassPath(Classifier clazz){
		return GeneratorUtils.getDtoClassPath(clazz, true)
	}
	
	/**
	 * retourne le chemin du fichier de la classe enum
	 */
	static def getEnumClassPath(Classifier clazz){
		return GeneratorUtils.getEnumPath(clazz, true)
	}
	
	/**
	 * retourne la classe Stereotype 
	 */
	static def getStereotype(Classifier clazz, String stereoname) {
		return clazz.appliedStereotypes.filter[stereotype |
			stereotype.name == stereoname
		]
	}
	
	/**
	 * retourne la valeur de la propriété liée au stereotype
	 */
	static def getStereotypePropertyValue(Classifier clazz, String stereoname, String property){
		if(clazz.getStereotype(stereoname) ===null || clazz.getStereotype(stereoname).empty)
		return null
		
		val stereotype = clazz.getStereotype(stereoname).get(0);
		
		if(stereotype === null)
			return null
			 		
		return clazz.getValue(stereotype ,property);
	}
	
	/**
	 * renvoi les attributs de la classe
	 * tableau vide si il y en a aucun
	 */
	static def getOwnedAttributes(Classifier clazz) {
		val attributes = clazz.getOwnedAttributesWNull
		if (attributes === null) {
			emptySet
		}
		else {
			attributes
		}
	}
	
	/**
	 * renvoi les attributs de la classe
	 * tableau vide si il y en a aucun
	 * incluant ceux des interface et classes généralisées
	 */
	static def getAttributes(Classifier clazz) {
		val attributes = clazz.getAllAttributes
		if (attributes === null) {
			emptySet
		}
		else {
			attributes
		}
	}
	
	/**
	 * renvoi les attributs de la classe
	 * null si il y en a aucun
	 */
	static def getOwnedAttributesWNull(Classifier clazz) {	
		if (clazz instanceof AttributeOwner) {
			(clazz as AttributeOwner).ownedAttributes
		} else {
			//Sequence{}
		    return null
		}
	}
	
	/**
	 * test si une classe a plusieurs attributs de type keyattribute
	 */
	static def hasMultipleId(Classifier clazz){
		val ids = clazz.getOwnedAttributes.filter[attribut|
			PropertyUtils.isID(attribut)
		]
		return ids.length >1 
	}
	
	
	/**
	 * retourne l'identifiant
	 */
	static def getId(Classifier clazz){
		val ids = clazz.getOwnedAttributes.filter[attribut|
			PropertyUtils.isID(attribut)
		]
		return ids
	}
	
	/**
	 * retourne les attributs multivalués
	 */
	static def getMultivaluedOwnedAttributes(Classifier clazz){
		val attributes = clazz.getOwnedAttributes
		val multiAttributes = attributes.filter[attribut |
			return (attribut.multivalued)
		]
		return multiAttributes
	}
	
	
	/**
	 * retourne les attributs multivalués
	 * en incluant ceux venant des interfaces et des classes généralisées
	 */
	static def getAllMultivaluedAttributes(Classifier clazz){
		val attributes = clazz.getAllAttributes
		val multiAttributes = attributes.filter[attribut |
			return (attribut.multivalued)
		]
		return multiAttributes
	}
	
		/**
	 * retourne les attributs non multivalués
	 */
	static def getNotMultivaluedOwnedAttributes(Classifier clazz){
		val attributes = clazz.getOwnedAttributes
		val simpleAttributes = attributes.filter[attribut |
			return (!attribut.multivalued)
		]
		return simpleAttributes
	}
	
	/**
	 * cherche les reference multivaluée de la classe ofClass dans une classe inClass 
	 */
	static def getMultivaluedReferencesToType(Classifier inClass, Classifier ofClass){
		val attributes = inClass.getOwnedAttributes.filter[attribut |
			return ((attribut.type == ofClass) && (attribut.multivalued) && (attribut.association === null))
		]
		return attributes
	}
	
	/**
	 * cherche tous les attributs multivalués dans le modele du type de la classe passée en paramètre
	 */
	static def getAllReferencesTo(Classifier ofClass){
		val model = ofClass.model
		val classes = ModelUtils.getAllClasses(model)
		val references = newArrayList()
		for(classe : classes){
			references.addAll(ClassifierUtils.getMultivaluedReferencesToType(classe as Classifier, ofClass))
		}
		return references
	}
	
	/**
	 * retourne les attributs de type one to many de type ofType présents dans le package
	 */
	/*static def getOneToManyAttributes(Classifier ofType){
		val pakkage = ofType.package
		val classes = pakkage.getOwnedTypes().filter[type|
			Utils.isEntity(type) 
		]
		
		var attributesRef = new ArrayList
		
		for(classe : classes){
			val attributes = ClassifierUtils.getMultivaluedOwnedAttributes(classe as Classifier).filter[attr|
				if(attr.association !== null){
					val member = attr.association.memberEnds.filter[mem |mem.type == ofType]
					val memberEnd = attr.association.memberEnds.filter[mem |mem.type == classe]
					if(member.length > 0 && memberEnd.length > 0){
						val end = member.get(0);
						val otherEnd = memberEnd.get(0) 
						return end.isMultivalued && !otherEnd.isMultivalued
					}
					return false
				}else if(attr.type == ofType && !attr.multivalued){
					return true
				}
				return false
			]
			for(attribut : attributes){
				attributesRef.add(attribut)
			}
		}
		return attributesRef
	}*/
	
	
	static def getOneToManyAttributes(Classifier ofType){
		val pakkage = ofType.package
		val classes = pakkage.getOwnedTypes().filter[type|
			Utils.isEntity(type)
		]
		
		val test = new ArrayList
		var attributesRef = new ArrayList
		
		for(classe : classes){
			val attributes = ClassifierUtils.getMultivaluedOwnedAttributes(classe as Classifier).filter[attr |
				if(attr.association !== null){
					val member = attr.association.memberEnds.filter[mem |mem.type == ofType]
					val memberEnd = attr.association.memberEnds.filter[mem |mem.type == classe]
					if(member.length > 0 && memberEnd.length > 0){
						val end = member.get(0);
						val otherEnd = memberEnd.get(0) 
						//test différent ofType pour ne pas avoir en double dans le cas des associations a double sens entre deux classes
						if(end.isMultivalued && !otherEnd.isMultivalued && otherEnd.owner !== ofType){
							test.add(otherEnd)
						}
						return false
					}
					return false
				}else if(attr.type == ofType && !attr.multivalued){
					return true
				}
				return false
			]
			for(attribut : attributes){
				attributesRef.add(attribut)
			}
			
			if(!test.empty){
				for(attribut : test){
					if(!attributesRef.contains(attribut)){
						attributesRef.add(attribut)
					}
				}
			}

		}
		return attributesRef
	}
	
	
	/**
	 * retourne les attributs de type many to many de type ofType présents dans le package
	 */
	static def getManyToManyAttributes(Classifier ofType){
		val pakkage = ofType.package
		val classes = pakkage.getOwnedTypes().filter[type|
			Utils.isEntity(type) 
		]
		
		var attributesRef = new ArrayList
		
		for(classe : classes){
			val attributes = ClassifierUtils.getMultivaluedOwnedAttributes(classe as Classifier).filter[attr|
				if(attr.association !== null ){
					val ends = attr.association.ownedEnds
					if(ends!== null && !ends.isEmpty){
						val owner = ends.get(0).type;
						if(owner !== ofType){
							val member = attr.association.memberEnds.filter[mem |mem.type == ofType]
							val memberEnd = attr.association.memberEnds.filter[mem |mem.type == classe]
							
							if(member.length > 0 && memberEnd.length > 0){
								val end = member.get(0);
								val otherEnd =memberEnd.get(0) 
								return end.isMultivalued && otherEnd.isMultivalued
							}
						}
					}
					return false
				}
				return false
			]
			for(attribut : attributes){
				attributesRef.add(attribut)
			}
		}
		return attributesRef
	}
	
	/**
	 * cherche les classe d'association liée a la classe  
	 */
	static def getLinkedAssociationClass(Classifier clazz){
		val model = clazz.model
		val pakkage = clazz.package
		val associationsClasses = model.getOwnedTypes().filter[type|
			if(type instanceof AssociationClass){
				val members = type.ownedEnds
				var isIn = false
				for(member: members){
					if(member.type == clazz){
						isIn = true
					}
				}
				return isIn
			}else{
				return false
			}
		]
		
		var assoClassesInPakkage = newArrayList
		if(pakkage !== null){
			val associationsClassesInPakkage = pakkage.getOwnedTypes().filter[type|
			if(type instanceof AssociationClass){
				val members = type.ownedEnds
				var isIn = false
				for(member: members){
					if(member.type == clazz){
						isIn = true
					}
				}
				return isIn
			}else{
				return false
			}
			]
			for(asso : associationsClassesInPakkage){
				assoClassesInPakkage.add(asso)
			}
		}
		
		var classes = newArrayList
		
		for(asso : associationsClasses){
			if(!classes.contains(asso)){
				classes.add(asso)
			}
		}
	
		if(pakkage != model){
			for(asso : assoClassesInPakkage){
				if(!classes.contains(asso)){
					classes.add(asso)
				}
			}
		}
		return classes
	}
	
	/**
	 * test si l'enum a des code ou non
	 */
	static def isEnumWithCode(Classifier clazz){
		val valeurs = clazz.getOwnedAttributes
		if(!valeurs.empty){
			val value = valeurs.get(0)
			val code = Utils.getNomenclatureCode(value)
			return (code !== null && code != "")
		}else {
			return false
		}
	}
	
	/**
	 * teste si une classe doit etre générée ou non
	 */
	static def canBeGenerated(Classifier clazz){
		val generated = Utils.getGenerated(clazz)
		if(generated == false || generated === null){
			return false
		}
		return true
	}
			
	/**
	 * retourne le nom de la table liée a la classe
	 */
	static def String getTableName(Classifier clazz){
		val name = getTableNameValue(clazz)
		if(name === null){
			return Utils.toSnakeCase(clazz.name)
		}
		return name.toString
	}
	
	/**
	 * retourne le nom de la table liée a la classe
	 */
	static def String getDBTableName(Classifier clazz){
		val name = getTableNameValue(clazz)
		if(name === null || name === ""){
			return Utils.toDbName(clazz.name)
		}
		return Utils.toDbName(name.toString)
	}
	
	/**
	 * retourne le chemin vers la classe
	 */
	static def String getClassPath(Classifier clazz){
		if(clazz !== null){
			val path = Utils.getPackagePath(clazz);
			val appName = clazz.getModel().name;
			val fileName = clazz.getPackage().getName() + 
					File.separator ;
			val classPath = path + File.separator + appName + File.separator  +  fileName
		
			return classPath
		}
	}
	
	
	/**
	 * options de la méthodes d'import
	 */
	public static class ImportOptions{
		//import de la classe d'interface
		public Boolean importInterface = true;
		//import des classes liées aux attributs de l'interface
		public Boolean importInterfaceAttributes = true;
		//import de la classe de type valueObject
		public Boolean importValueObject = true;
		//import des classes liées aux attributs de la classe de type valueObject
		public Boolean importValueObjectAttributes = true;
		//import des classes d'asso
		public Boolean importAssociationClassAttributes = true;
		public Boolean importAssociationMemberAttributes = false;
	}
	
	/**
	 * classes nécessaires a importer liées aux attributs dans les classes
	 */
	static def ArrayList<Type> getAttributesImport (Classifier clazz, 
		Classifier fromClass,
		ArrayList<Type> types,
		ImportOptions options
	){
		
		// classes d'entité liées aux attributs
		val attributes = ClassifierUtils.getOwnedAttributes(clazz).filter[ attribut |
			((Utils.isEntity(attribut.type)) && (attribut.type !== clazz))
		]
		
		// classes d'enum liées aux attributs
		val attributesEnums = ClassifierUtils.getOwnedAttributes(clazz).filter[ attribut |
			(Utils.isNomenclature(attribut.type))
		]
		
		// classes valueobject liées aux attributs
		val attributesValueObject = ClassifierUtils.getOwnedAttributes(clazz).filter[ attribut |
			(Utils.isValueObject(attribut.type))
		]
		
		if(Utils.isEntity(clazz)){
			val oneToManyAttributes = ClassifierUtils.getOneToManyAttributes(clazz)
			for(attribut : oneToManyAttributes){
				if(!types.contains(attribut.type) && (attribut.type != clazz)){
					types.add(attribut.type as Classifier)
				}
			}
			
			val manyToManyAttributes = ClassifierUtils.getManyToManyAttributes(clazz)
			for(attribut : manyToManyAttributes){
				if(!types.contains(attribut.owner) && (attribut.owner != clazz)){
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
		
		//atributs venant des interfaces
		for(interface : interfaces){
			if(options.importInterface){
				if(!types.contains(interface)){
					types.add(interface)
				}
			}
			if(options.importInterfaceAttributes){
				interface.getAttributesImport(fromClass, types, options)
			}
			
		}
		
		//imports des classes d'association
		if(Utils.isEntity(fromClass)){
			val assosiationClasses = ClassifierUtils.getLinkedAssociationClass(clazz)
			
			assosiationClasses.forEach[asso |
				if(options.importAssociationClassAttributes){
					if(!types.contains(asso)){
						types.add(asso)
					}
				}
				
				if(options.importAssociationMemberAttributes){
					val members = (asso as AssociationClass).memberEnds.filter[member |
						member.type !== fromClass
					]
					for(mem: members){
						if(!types.contains(mem.type)){
							types.add(mem.type)
						}
					}
				}
			]
		}
	
		attributesValueObject.forEach[attribut |
			var type = attribut.type
			if(options.importValueObject){
				if(!types.contains(type)){
					types.add(type)
				}
			}
			if(options.importValueObjectAttributes){
				if(type instanceof Classifier){
					type.getAttributesImport(fromClass, types, options)
				}
			}	
			
		]
		return types
	}
	
	/**                                                        *
	 * ----------------- stereotype attributes ----------------*
	*/
	
	/**
	 * retourne la valeur de l'attribut tableName
	 */
	static def getTableNameValue(Classifier clazz){
		if(Utils.isEntity(clazz)){
			return Utils.getStereotypePropertyValue(clazz, Utils.MODEL_ENTITY, Utils.MODEL_ENTITY_TABLENAME)
		}else if(Utils.isNomenclature(clazz)){
			return Utils.getStereotypePropertyValue(clazz, Utils.MODEL_NOMENCLATURE, Utils.MODEL_NOMENCLATURE_TABLENAME)
		}
		return null
	}
	
	/**
	 * retourne la valeur de l'attribut schema
	 */
	static def getClassSchema(Classifier clazz){
		if(Utils.isEntity(clazz)){
			return Utils.getStereotypePropertyValue(clazz, Utils.MODEL_ENTITY, Utils.MODEL_DOMAIN_SCHEMA)
		}else if(Utils.isNomenclature(clazz)){
			return Utils.getStereotypePropertyValue(clazz, Utils.MODEL_NOMENCLATURE, Utils.MODEL_DOMAIN_SCHEMA)
		}else if(Utils.isAssociationTable(clazz)){
			return Utils.getStereotypePropertyValue(clazz, Utils.MODEL_ASSOCIATIONTABLE, Utils.MODEL_DOMAIN_SCHEMA)
		}
		return null
	}
	
	/**
	 * retourne le schema dans lequel se trouve la classe
	 * retourne null si il n'y a pas de schema
	 */
	static def getSchema(Classifier clazz){
		val classSchema = clazz.getClassSchema
		if(classSchema !== null && classSchema!== ""){
			return classSchema
		}
		val pkg = clazz.package
		return Utils.getSchemaName(pkg)
	}
}