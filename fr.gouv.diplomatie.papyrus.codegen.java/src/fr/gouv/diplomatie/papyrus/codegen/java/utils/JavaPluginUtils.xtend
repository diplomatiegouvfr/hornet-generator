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
 * fr.gouv.diplomatie.papyrus.codegen.java - Générateur de code java pour 
 * des applications Hornet JS
 *
 * @author MEAE - Ministère de l'Europe et des Affaires étrangères
 * @version v1.1.0
 * @license CECILL-2.1
 */
package fr.gouv.diplomatie.papyrus.codegen.java.utils

import org.eclipse.uml2.uml.NamedElement
import org.eclipse.uml2.uml.Property
import fr.gouv.diplomatie.papyrus.codegen.core.utils.Utils
import org.eclipse.uml2.uml.internal.impl.EnumerationLiteralImpl

public class JavaPluginUtils{
	
	public static var MODEL_NATURALORDER = 'naturalOrder';
	public static var MODEL_SCHEMA = 'schema';
	
	public static var MODEL_ATTRIBUTE_SHOULDBENULL = 'shouldBeNull'
	
	public static var MODEL_NUMERICTYPED = 'numericTyped'
	public static var MODEL_NUMERICTYPED_MIN = 'min';
	public static var MODEL_NUMERICTYPED_MAX = 'max';
	public static var MODEL_NUMERICTYPED_NEGATIVE = 'negative';
	public static var MODEL_NUMERICTYPED_NEGATIVEORZERO = 'negativeOrZero';
	public static var MODEL_NUMERICTYPED_POSITIVE = 'positive';
	public static var MODEL_NUMERICTYPED_POSITIVEORZERO = 'positiveOrZero';
	public static var MODEL_NUMERICTYPED_DIGITS = 'digits';
	public static var MODEL_NUMERICTYPED_DIGITSINTEGER = 'digitsInteger';
	public static var MODEL_NUMERICTYPED_DIGITSFRACTION = 'digitsFraction';
	public static var MODEL_NUMERICTYPED_DECIMALMIN = 'decimalMin';
	public static var MODEL_NUMERICTYPED_DECIMALMAX = 'decimalMax';
	
	public static var MODEL_BOOLEANTYPED = 'booleanTyped';
	public static var MODEL_BOOLEANTYPED_ALWAYSTRUE = 'alwaysTrue';
	public static var MODEL_BOOLEANTYPED_ALWAYSFALSE = 'alwaysFalse';
	
	public static var MODEL_DATETYPED = 'dateTyped';
	public static var MODEL_DATETYPED_FUTURE = 'future';
	public static var MODEL_DATETYPED_PAST = 'past';
	public static var MODEL_DATETYPED_FUTUREORPRESENT = 'futureOrPresent';
	public static var MODEL_DATETYPED_PASTORPRESENT = 'pastOrPresent';
	
	public static var MODEL_STRINGTYPED = 'stringTyped';
	public static var MODEL_STRINGTYPED_CANBEEMPTY= 'canBeEmpty';
	public static var MODEL_STRINGTYPED_PATTERN = 'pattern';
	public static var MODEL_STRINGTYPED_SIZEMIN = 'sizeMin';
	
	public static var MODEL_COLLECTION = 'collection';
	public static var MODEL_COLLECTION_SIZEMIN = 'sizeMin';
	public static var MODEL_COLLECTION_SIZEMAX = 'sizeMax';
	
	/**
	 * teste si un element est de type naturalOrder
	 */
	static def isNaturalOrderField(NamedElement elem){
		Utils.hasStereotype(elem, MODEL_NATURALORDER)
	}
	
		
	/**                                                        *
	 * ----------------- stereotype attributes ----------------*
	*/
	 
	/**
	 * retourne le fetchType de l'element ou null si celui ci n'en possède pas
	 */
	static def EnumerationLiteralImpl getFetchType(NamedElement elem){
		if(Utils.isAssociationTable(elem)){
			 return Utils.getStereotypePropertyValue(elem, Utils.MODEL_ASSOCIATIONTABLE, Utils.MODEL_FETCHTYPE) as EnumerationLiteralImpl
		}else if(elem instanceof Property){
			if(elem.association === null){
				if(Utils.getStereotype(elem, Utils.MODEL_KEYATTRIBUTE) ===null || Utils.getStereotype(elem, Utils.MODEL_KEYATTRIBUTE).empty){
					return Utils.getStereotypePropertyValue(elem, Utils.MODEL_ATTRIBUTE, Utils.MODEL_FETCHTYPE) as EnumerationLiteralImpl
				}else{
				 return  Utils.getStereotypePropertyValue(elem, Utils.MODEL_KEYATTRIBUTE, Utils.MODEL_FETCHTYPE) as EnumerationLiteralImpl
				}
			}else{
				if(Utils.isAssociationLink(elem.association)){
					return Utils.getStereotypePropertyValue(elem.association, Utils.MODEL_ASSOCIATIONLINK, Utils.MODEL_FETCHTYPE) as EnumerationLiteralImpl
				}else {
					return null
				}
			}
		}else{
			return null
		}		
	}
	
	static def getShouldBeNull(NamedElement elem){
		return Utils.getStereotypePropertyValue(elem, Utils.MODEL_ATTRIBUTE, MODEL_ATTRIBUTE_SHOULDBENULL)
	}
	
	static def getAlwaysTrue(NamedElement elem){
		return Utils.getStereotypePropertyValue(elem, MODEL_BOOLEANTYPED, MODEL_BOOLEANTYPED_ALWAYSTRUE)
	}
	
	static def getAlwaysFalse(NamedElement elem){
		return Utils.getStereotypePropertyValue(elem, MODEL_BOOLEANTYPED, MODEL_BOOLEANTYPED_ALWAYSFALSE)
	}
	
	static def getFuture(NamedElement elem){
		return Utils.getStereotypePropertyValue(elem, MODEL_DATETYPED, MODEL_DATETYPED_FUTURE)
	}
	
	static def getFutureOrPresent(NamedElement elem){
		return Utils.getStereotypePropertyValue(elem, MODEL_DATETYPED, MODEL_DATETYPED_FUTUREORPRESENT)
	}
	
	static def getPast(NamedElement elem){
		return Utils.getStereotypePropertyValue(elem, MODEL_DATETYPED, MODEL_DATETYPED_PAST)
	}
	
	static def getPastOrPresent(NamedElement elem){
		return Utils.getStereotypePropertyValue(elem, MODEL_DATETYPED, MODEL_DATETYPED_PASTORPRESENT)
	}
	
	static def getCollectionSizeMin(NamedElement elem){
		val hasSizeMin = Utils.getStereotypePropertyValue(elem, MODEL_COLLECTION, 'hasSizeMin')
		if(hasSizeMin == true){
			return Utils.getStereotypePropertyValue(elem, MODEL_COLLECTION, MODEL_COLLECTION_SIZEMIN)
		}
		return null
	}
	
	static def getCollectionSizeMax(NamedElement elem){
		val hasSizeMax = Utils.getStereotypePropertyValue(elem, MODEL_COLLECTION, 'hasSizeMax')
		if(hasSizeMax == true){
			return Utils.getStereotypePropertyValue(elem, MODEL_COLLECTION, MODEL_COLLECTION_SIZEMAX)
		}
		return null
	}
	
	static def getCanBeEmpty(NamedElement elem){
		return Utils.getStereotypePropertyValue(elem, MODEL_STRINGTYPED, MODEL_STRINGTYPED_CANBEEMPTY)
	}
	
	static def getPattern(NamedElement elem){
		return Utils.getStereotypePropertyValue(elem, MODEL_STRINGTYPED, MODEL_STRINGTYPED_PATTERN)
	}
	
	static def getSizeMin(NamedElement elem){
		val hasSizeMin = Utils.getStereotypePropertyValue(elem, MODEL_COLLECTION, 'hasSizeMin')
		if(hasSizeMin == true){
			return Utils.getStereotypePropertyValue(elem, MODEL_STRINGTYPED, MODEL_STRINGTYPED_SIZEMIN)
		}
		return null
	}
	
	static def getMin(NamedElement elem){
		val hasMin = Utils.getStereotypePropertyValue(elem, MODEL_COLLECTION, 'hasMin')
		if(hasMin == true){	
			return Utils.getStereotypePropertyValue(elem, MODEL_NUMERICTYPED, MODEL_NUMERICTYPED_MIN)
		}
		return null
	}
	
	static def getMax(NamedElement elem){
		val hasMax = Utils.getStereotypePropertyValue(elem, MODEL_COLLECTION, 'hasMax')
		if(hasMax == true){	
			return Utils.getStereotypePropertyValue(elem, MODEL_NUMERICTYPED, MODEL_NUMERICTYPED_MAX)
		}
		return null
	}
	
	static def getNegative(NamedElement elem){
		return Utils.getStereotypePropertyValue(elem, MODEL_NUMERICTYPED, MODEL_NUMERICTYPED_NEGATIVE)
	}
	
	static def getNegativeOrZero(NamedElement elem){
		return Utils.getStereotypePropertyValue(elem, MODEL_NUMERICTYPED, MODEL_NUMERICTYPED_NEGATIVEORZERO)
	}
	
	static def getPositive(NamedElement elem){
		return Utils.getStereotypePropertyValue(elem, MODEL_NUMERICTYPED, MODEL_NUMERICTYPED_POSITIVE)
	}
	
	static def getPositiveOrZero(NamedElement elem){
		return Utils.getStereotypePropertyValue(elem, MODEL_NUMERICTYPED, MODEL_NUMERICTYPED_POSITIVEORZERO)
	}
	
	static def getDigits(NamedElement elem){
		return Utils.getStereotypePropertyValue(elem, MODEL_NUMERICTYPED, MODEL_NUMERICTYPED_DIGITS)
	}
	
	static def getDigitsFraction(NamedElement elem){
		return Utils.getStereotypePropertyValue(elem, MODEL_NUMERICTYPED, MODEL_NUMERICTYPED_DIGITSFRACTION)
	}
	
	static def getDigitsInteger(NamedElement elem){
		return Utils.getStereotypePropertyValue(elem, MODEL_NUMERICTYPED, MODEL_NUMERICTYPED_DIGITSINTEGER)
	}
	
	static def getDecimalMin(NamedElement elem){
		val hasDecimalMin = Utils.getStereotypePropertyValue(elem, MODEL_COLLECTION, 'hasDecimalMin')
		if(hasDecimalMin == true){	
			return Utils.getStereotypePropertyValue(elem, MODEL_NUMERICTYPED, MODEL_NUMERICTYPED_DECIMALMIN)
		}
		return null
	}
	
	static def getDecimalMax(NamedElement elem){
		val hasDecimalMax = Utils.getStereotypePropertyValue(elem, MODEL_COLLECTION, 'hasDecimalMax')
		if(hasDecimalMax == true){	
			return Utils.getStereotypePropertyValue(elem, MODEL_NUMERICTYPED, MODEL_NUMERICTYPED_DECIMALMAX)
		}
		return null
	}
}