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
 * fr.gouv.diplomatie.papyrus.codegen.annotation.lombok - Générateur d'annotations Lombok pour 
 * des entités JPA
 *
 * @author MEAE - Ministère de l'Europe et des Affaires étrangères
 * @version v1.3.2
 * @license CECILL-2.1
 */
package fr.gouv.diplomatie.papyrus.codegen.annotation.lombok.utils

import org.eclipse.uml2.uml.Classifier
import org.eclipse.uml2.uml.Property
import fr.gouv.diplomatie.papyrus.codegen.core.utils.Utils
import org.eclipse.uml2.uml.NamedElement

class LombokClassifierUtils{
	
	public static var MODEL_ENTITY_LOMBOKGETTER = 'lombokGetter';
	public static var MODEL_ENTITY_LOMBOKSETTER = 'lombokSetter';
	public static var MODEL_ENTITY_LOMBOKNOARGSCONS = 'lombokNoArgsConstructor';
	public static var MODEL_ENTITY_LOMBOKALLARGSCONS = 'lombokAllArgsConstructor';
	public static var MODEL_ENTITY_LOMBOKTOSTRING = 'lombokToString';
	public static var MODEL_ENTITY_LOMBOKEQANDHASHC = 'lombokEqualsAndHashCode';
	
	public static var MODEL_LOMBOKDISPLAYWITH = 'lombokDisplayWith';
	
	/**
	 * teste si la classe doit avoir une annotation getter
	 */
	static def hasGetterAnnotation(Classifier clazz){
		val getter = clazz.getGetterAnnotation
		if(getter === null || getter == ""){
			return false
		}
		return getter
	}
	
	static def getGetterAnnotation(Classifier clazz){
		 return Utils.getStereotypePropertyValue(clazz,Utils.MODEL_ENTITY, MODEL_ENTITY_LOMBOKGETTER)
	}
	
	/**
	 * teste si la classe doit avoir une annotation setter
	 */
	static def hasSetterAnnotation(Classifier clazz){
		val setter = clazz.getSetterAnnotation
		if(setter === null || setter == ""){
			return false
		}
		return setter
	}
	
	static def getSetterAnnotation(Classifier clazz){
		return Utils.getStereotypePropertyValue(clazz,Utils.MODEL_ENTITY, MODEL_ENTITY_LOMBOKSETTER)
	}
	
	/**
	 * teste si la classe doit avoir une annotation NoArgsConstructor
	 */
	static def hasNoArgsConstructor(Classifier clazz){
		val noArgsCons = clazz.getNoArgsConstructorAnnotation
		if(noArgsCons === null || noArgsCons == ""){
			return false
		}
		return noArgsCons
	}
	
	static def getNoArgsConstructorAnnotation(Classifier clazz){
		return Utils.getStereotypePropertyValue(clazz,Utils.MODEL_ENTITY, MODEL_ENTITY_LOMBOKNOARGSCONS)
	}
	
	/**
	 * teste si la classe doit avoir une annotation AllArgsConstructor
	 */
	static def hasAllArgsConstructor(Classifier clazz){
		val allArgsCons = clazz.getAllArgsConstructorAnnotation
		if(allArgsCons === null || allArgsCons == ""){
			return false
		}
		return allArgsCons
	}
	
	static def getAllArgsConstructorAnnotation(Classifier clazz){
		return Utils.getStereotypePropertyValue(clazz,Utils.MODEL_ENTITY, MODEL_ENTITY_LOMBOKALLARGSCONS)
	}
	
	/**
	 * teste si la classe doit avoir une annotation ToString
	 */
	static def hasToString(Classifier clazz){
		val toString = clazz.getToStringAnnotation
		if(toString === null || toString == ""){
			return false
		}
		return toString
	}
	
	static def getToStringAnnotation(Classifier clazz){
		return Utils.getStereotypePropertyValue(clazz,Utils.MODEL_ENTITY, MODEL_ENTITY_LOMBOKTOSTRING)
	}
	
	/**
	 * teste si la classe doit avoir une annotation EqualsAndHashCode
	 */
	static def hasEqualsAndHashCode(Classifier clazz){
		val eqAndHashCode = clazz.getEqualsAndHashCode
		if(eqAndHashCode === null || eqAndHashCode == ""){
			return false
		}
		return eqAndHashCode
	}
	
	static def getEqualsAndHashCode(Classifier clazz){
		return Utils.getStereotypePropertyValue(clazz,Utils.MODEL_ENTITY, MODEL_ENTITY_LOMBOKEQANDHASHC)
	}
	
	/**
	 * retourne la valeur de l'attribut displayWith
	 */
	static def Boolean getLombokDisplayWith(NamedElement elem){
		if(Utils.isAssociationTable(elem)){
			 return Utils.getStereotypePropertyValue(elem, Utils.MODEL_ASSOCIATIONTABLE, LombokClassifierUtils.MODEL_LOMBOKDISPLAYWITH) as Boolean
		}else if(elem instanceof Property ){
			if(elem.association === null){
				if(Utils.getStereotype(elem, Utils.MODEL_KEYATTRIBUTE) ===null || Utils.getStereotype(elem, Utils.MODEL_KEYATTRIBUTE).empty){
					return Utils.getStereotypePropertyValue(elem, Utils.MODEL_ATTRIBUTE, LombokClassifierUtils.MODEL_LOMBOKDISPLAYWITH) as Boolean
				}else{
				 return  Utils.getStereotypePropertyValue(elem, Utils.MODEL_KEYATTRIBUTE, LombokClassifierUtils.MODEL_LOMBOKDISPLAYWITH) as Boolean
				}
			}else{
				if(Utils.isAssociationLink(elem.association)){
					return Utils.getStereotypePropertyValue(elem.association, Utils.MODEL_ASSOCIATIONLINK, LombokClassifierUtils.MODEL_LOMBOKDISPLAYWITH) as Boolean
				}
				return true
			}
		}else{
			return true
		}		
	}
}