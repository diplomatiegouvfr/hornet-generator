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
 * @version v1.1.5
 * @license CECILL-2.1
 */
package fr.gouv.diplomatie.papyrus.codegen.core.utils

import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.NamedElement
import org.eclipse.uml2.uml.Classifier
import org.eclipse.uml2.uml.Class

/**
 * classe utilitaire pour les Property
 */
class PropertyUtils {
	
	/**
	 * retourne la classe Stereotype dont le nom est stereoname
	 */
	static def getStereotype(Property prop, String stereoname) {
		return prop.appliedStereotypes.filter[stereotype |
			stereotype.name == stereoname
		]
	}
	
	/**
	 * retourne la valeur de l'attribut du stereotype de la classe
	 */
	static def getStereotypePropertyValue(Property prop, String stereoname, String property){
		if(prop.getStereotype(stereoname) ===null || prop.getStereotype(stereoname).empty)
		return null
		
		val stereotype = prop.getStereotype(stereoname).get(0);
		
		if(stereotype === null)
			return null
			 		
		return prop.getValue(stereotype ,property);
	}
	
	static def isAttribut(Property property){
		 return (property.getStereotype(Utils.MODEL_ATTRIBUTE) !==null && !(property.getStereotype(Utils.MODEL_ATTRIBUTE).empty))
	}
	
	static def isCodeLibelleNomenclature(Property property){
		 return (property.getStereotype(Utils.MODEL_CODELIBELLENOMENCLATURE) !==null && !(property.getStereotype(Utils.MODEL_CODELIBELLENOMENCLATURE).empty))
	}
	
	/**
	 * teste si une property est un id
	 */
	static def isID(Property property){
		return (property.getStereotype(Utils.MODEL_KEYATTRIBUTE) !==null && !(property.getStereotype(Utils.MODEL_KEYATTRIBUTE).empty))
	}
	
	/**
	 * retourne le nom de la classe possédant la property
	 */
	static def getOwnerName(Property property){
		val type= property.owner;
		if(type instanceof NamedElement){
			return type.name
		}else{
			return type.class.name
		}
	}
	
	/**
	 * retourne le nom du field id
	 */
	static def getIdFieldName(Property property, String additionalName){
		var name = ""
		if(additionalName!= "" && additionalName !== null){
			name = additionalName + Utils.getFirstToUpperCase(property.name)
		}else{
			name = property.name
		}
		return '''«Utils.toSnakeCase(name)»_«Utils.getFirstToLowerCase(property.getOwnerName)»'''
	}
	
	/**
	 * teste si l'attribut ne viens pas d'une association
	 */
	static def isBasicAttribute(Property property){
		return (!property.isClassAttribute)
	}
	
	/**
	 * teste si l'attribut viens d'une association
	 */
	static def isClassAttribute(Property property){
		return (property.type instanceof Class)
	}
	
	/**
	 * teste si l'attribut viens d'une association
	 */
	static def isAssociationAttribute(Property property){
		return property.association !== null
	}
	
	/**
	 * retourne le nom d'un attribut issu d'un attribut de type valueObject
	 */
	static def getValueObjectPropertyName(Property property, Property valueObjectProperty){
		return '''«valueObjectProperty.name»«Utils.getFirstToUpperCase(property.name)»'''
	}
	
	/**
	 * retourne le nom du champs lié a l'identifant de la classe entity associée
	 */
	static def getAssociationEntityIdName(Property property){
		val type = property.type
		if(type instanceof Classifier){
			val propName = ClassifierUtils.getId(type).get(0).name
			return propName + Utils.getFirstToUpperCase(type.name)
		}else{
			return ""
		}
		
	}
	
	/**
	 * retourne le nom du model d'une propriété multivaluée
	 */
	static def getMultivaluedPropertyModelName(Property property, Classifier fromClass){
		val tableName = Utils.addAdditionnalName(fromClass.name, property.name)
		'''«Utils.getFirstToUpperCase(tableName)»Model'''
	}
	
	/**
	 * retourne le nom du model d'une propriété multivaluée
	 */
	static def getMultivaluedPropertyDtoName(Property property, Classifier fromClass){
		val tableName = Utils.addAdditionnalName(fromClass.name, property.name)
		'''«Utils.getFirstToUpperCase(tableName)»DTO'''
	}
	
	/**
	 * retourne le nom de l'attribut d'une reference multivaluée
	 */
	static def String getReferenceAttributeName(Property property, Property id, Classifier clazz){
		'''«id.name»«Utils.getFirstToUpperCase(property.name)»'''
	}
	
	/**
	 * teste si un attribut peut être mis a null ou non
	 */
	static def isNullable(Property property){
		return (property.lower == 0)
	}	
	
	/**
	 * retourne la valeur par defaut
	 */
	static def getDefaultValue(Property property){
		property.^default
	}
		
	/**
	 * retourne la valeur de columnName ou name si l'attribut n'en possède pas (en y ajoutant l'additionalName)
	 * le paramètre additionalName ne sera pas mis en forme
	 */
	static def getDatabaseName(Property property, String name, String additionnalName){
		var retour = Utils.toSnakeCase(name)
		
		//récupération du columname si l'attribut en possède un
		val columnName = property.columnName
		if(columnName!== null && columnName !== ""){
			retour = (columnName as String)
			
		}
		//ajout de l'additionnalName (doit déja etre mis en forme)
		if(additionnalName !== null && additionnalName !== ""){
			retour = additionnalName +"_" + retour
		}
		return retour
	}
	
	static def getName(Property property, String name, String additionnalName){
		var retour = name
		
		//récupération du columname si l'attribut en possède un
		val columnName = property.columnName
		if(columnName!== null && columnName !== ""){
			retour = columnName as String
		}
		//ajout de l'additionnalName (doit déja etre mis en forme)
		if(additionnalName !== null && additionnalName !== ""){
			retour = Utils.addAdditionnalName(additionnalName, retour)
		}
		return retour
	}
	
	static def isOneToManyAttributes(Property property){
		if(property.isMultivalued && Utils.isEntity(property.type) && Utils.isEntity(property.owner as Classifier)){
			val association = property.association
			if(association !== null){
				 val member = association.memberEnds.filter[mem |
						mem.type == property.owner
				]
				if(member !== null){
					val end = member.get(0);					
					if(end.isMultivalued){
						return false
					}else{
						return true
					}
				}
			}
			//return true
			return false
		}else{
			return false
		}
	}
	
	
	/**                                                        *
	 * ----------------- stereotype attributes ----------------*
	*/
	
	/**
	 * retourne la valeur de l'attribut columnName du stéréotype attribut
	 */
	static def getColumnName(Property property){
		var name  = ""
		if(property.getStereotype(Utils.MODEL_KEYATTRIBUTE) ===null || property.getStereotype(Utils.MODEL_KEYATTRIBUTE).empty){
			name = Utils.getStereotypePropertyValue(property, Utils.MODEL_ATTRIBUTE, Utils.MODEL_ATTRIBUTE_COLUMNNAME) as String
		}else{
			name = Utils.getStereotypePropertyValue(property, Utils.MODEL_KEYATTRIBUTE, Utils.MODEL_ATTRIBUTE_COLUMNNAME)as String
		}
		return name
	}
	
		/**
	 * retourne la valeur de l'attribut index du stéréotype attribut
	 */
	static def getIndex(Property property){
		if (property.getStereotype(Utils.MODEL_KEYATTRIBUTE) !==null && !property.getStereotype(Utils.MODEL_KEYATTRIBUTE).empty){
			return Utils.getStereotypePropertyValue(property, Utils.MODEL_KEYATTRIBUTE, Utils.MODEL_ATTRIBUTE_INDEX)
		}else(property.getStereotype(Utils.MODEL_ATTRIBUTE) !==null && !property.getStereotype(Utils.MODEL_KEYATTRIBUTE).empty){
			return Utils.getStereotypePropertyValue(property, Utils.MODEL_ATTRIBUTE, Utils.MODEL_ATTRIBUTE_INDEX)
		}
	}
	
}