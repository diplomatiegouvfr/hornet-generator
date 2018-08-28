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
 * fr.gouv.diplomatie.papyrus.codegen.ui - Interface papyrus pour lancer 
 * le générateur Hornet JS
 *
 * @author MEAE - Ministère de l'Europe et des Affaires étrangères
 * @version v1.1.5
 * @license CECILL-2.1
 */
package fr.gouv.diplomatie.papyrus.codegen.ui.validators;

import fr.gouv.diplomatie.papyrus.codegen.ui.core.validator.HornetModelValidator;
import fr.gouv.diplomatie.papyrus.codegen.core.console.ConsoleUtils
import org.eclipse.uml2.uml.Property;
import fr.gouv.diplomatie.papyrus.codegen.core.utils.Utils
import fr.gouv.diplomatie.papyrus.codegen.java.utils.JavaPluginUtils
import org.eclipse.uml2.uml.NamedElement

/**
 * validation du modèle pour les stéréotypes liés à la génération java
 */
public class JavaPluginModelValidator extends HornetModelValidator {
	
	 public override validateProperty(Property packageableElement, ConsoleUtils console){
		super.validateProperty(packageableElement, console);
		val owner = packageableElement.owner as NamedElement
		val types = newArrayList("Boolean", "Date", "Real", "BigInteger", "Double", "Integer", "Float", "String")
		
		val isBooleanTyped = Utils.hasStereotype(packageableElement, JavaPluginUtils.MODEL_BOOLEANTYPED)
		val isDateTyped = Utils.hasStereotype(packageableElement, JavaPluginUtils.MODEL_DATETYPED)
		val isStringTyped = Utils.hasStereotype(packageableElement, JavaPluginUtils.MODEL_STRINGTYPED)
		val isNumericTyped = Utils.hasStereotype(packageableElement, JavaPluginUtils.MODEL_NUMERICTYPED)
		val isCollection = Utils.hasStereotype(packageableElement, JavaPluginUtils.MODEL_COLLECTION)
		
		if(isCollection){
			if(!packageableElement.isMultivalued ){
				errors.add("L'attibut " + packageableElement.name + " de la classe " + owner.name + " possède le stéréotype collection mais n'est pas multivalué.")
			}
			
			// cohérence des attributs
			val sizeMin = Utils.getStereotypePropertyValue(packageableElement, JavaPluginUtils.MODEL_COLLECTION, JavaPluginUtils.MODEL_COLLECTION_SIZEMIN);
			val hasSizeMin = Utils.getStereotypePropertyValue(packageableElement, JavaPluginUtils.MODEL_COLLECTION, JavaPluginUtils.MODEL_COLLECTION_HASSIZEMIN);
			if(sizeMin != 0 && hasSizeMin == false){
				warnings.add("L'attibut " + packageableElement.name + " de la classe " + owner.name + " possède, dans le stéréotype collection, un attribut sizeMin valué mais l'attribut hasSizeMin n'est pas à true. SizeMin ne sera pas pris en compte. ")
			}
			
			val sizeMax = Utils.getStereotypePropertyValue(packageableElement, JavaPluginUtils.MODEL_COLLECTION, JavaPluginUtils.MODEL_COLLECTION_SIZEMAX);
			val hasSizeMax = Utils.getStereotypePropertyValue(packageableElement, JavaPluginUtils.MODEL_COLLECTION, JavaPluginUtils.MODEL_COLLECTION_HASSIZEMAX);
			if(sizeMax != 0 && hasSizeMax == false){
				warnings.add("L'attibut " + packageableElement.name + " de la classe " + owner.name + " possède, dans le stéréotype collection, un attribut sizeMax valué mais l'attribut hasSizeMax n'est pas à true. SizeMax ne sera pas pris en compte. ")
			}
		}
		
		if(isBooleanTyped){
			val type = packageableElement.type.name
			types.remove("Boolean");
			if(types.contains(type)){
				errors.add("L'attibut " + packageableElement.name + " de la classe " + owner.name + " est de type " + type + " mais possède le stéréotype booleanTyped.")
			}
			types.add("Boolean");
			
			// cohérence des attributs
			val alwaysTrue = Utils.getStereotypePropertyValue(packageableElement, JavaPluginUtils.MODEL_BOOLEANTYPED, JavaPluginUtils.MODEL_BOOLEANTYPED_ALWAYSTRUE);
			val alwaysFalse = Utils.getStereotypePropertyValue(packageableElement, JavaPluginUtils.MODEL_BOOLEANTYPED, JavaPluginUtils.MODEL_BOOLEANTYPED_ALWAYSFALSE);
			
			if(alwaysTrue == true && alwaysFalse == true){
				errors.add("L'attibut " + packageableElement.name + " de la classe " + owner.name + " possède les deux attributs alwaysTrue et alwaysFalse du stéréotype booleanTyped à true. Ces deux attributs sont incompatibles");
			}
		}
		
		if(isDateTyped){
			val type = packageableElement.type.name
			types.remove("Date");
			if(types.contains(type)){
				errors.add("L'attibut " + packageableElement.name + " de la classe " + owner.name + " est de type " + type + " mais possède le stéréotype dateTyped.")
			}
			types.add("Date");
			
			// cohérence des attributs
			val future = Utils.getStereotypePropertyValue(packageableElement, JavaPluginUtils.MODEL_DATETYPED, JavaPluginUtils.MODEL_DATETYPED_FUTURE);
			val futureOrPresent = Utils.getStereotypePropertyValue(packageableElement, JavaPluginUtils.MODEL_DATETYPED, JavaPluginUtils.MODEL_DATETYPED_FUTUREORPRESENT);
			val Past = Utils.getStereotypePropertyValue(packageableElement, JavaPluginUtils.MODEL_DATETYPED, JavaPluginUtils.MODEL_DATETYPED_PAST);
			val PastOrPresent = Utils.getStereotypePropertyValue(packageableElement, JavaPluginUtils.MODEL_DATETYPED, JavaPluginUtils.MODEL_DATETYPED_PASTORPRESENT);
		
			val list = newArrayList();
			list.add(future);
			list.add(futureOrPresent);
			list.add(Past);
			list.add(PastOrPresent);
			val test = list.filter[att | 
				att == true
			]
			if(test.length > 1){		
				errors.add("L'attibut " + packageableElement.name + " de la classe " + owner.name + " possède des attributs incompatibles (parmis furture, futureOrPresent, Past et PastOrPresent) dans le stéréotype dateTyped")	
			}
		}
		
		if(isStringTyped){
			val type = packageableElement.type.name
			types.remove("String");
			if(types.contains(type)){
				errors.add("L'attibut " + packageableElement.name + " de la classe " + owner.name + " est de type " + type + " mais possède le stéréotype stringTyped.")
			}
			types.add("String");
			
			// cohérence des attributs
			val sizeMin = Utils.getStereotypePropertyValue(packageableElement, JavaPluginUtils.MODEL_STRINGTYPED, JavaPluginUtils.MODEL_STRINGTYPED_SIZEMIN);
			val hasSizeMin = Utils.getStereotypePropertyValue(packageableElement, JavaPluginUtils.MODEL_STRINGTYPED, JavaPluginUtils.MODEL_STRINGTYPED_HASSIZEMIN);
			if(sizeMin != 0 && hasSizeMin == false){
				warnings.add("L'attibut " + packageableElement.name + " de la classe " + owner.name + " possède, dans le stéréotype stringTyped, un attribut sizeMin valué mais l'attribut hasSizeMin n'est pas à true. SizeMin ne sera pas pris en compte. ")
			}
		}
		
		if(isNumericTyped){
			val type = packageableElement.type.name
			types.removeAll("Real", "BigInteger", "Double", "Integer", "Float");
			if(types.contains(type)){
				errors.add("L'attibut " + packageableElement.name + " de la classe " + owner.name + " est de type " + type + " mais possède le stéréotype numericTyped.")
			}
			types.addAll("Real", "BigInteger", "Double", "Integer", "Float");
			
			// cohérence des attributs
			val negative = Utils.getStereotypePropertyValue(packageableElement, JavaPluginUtils.MODEL_NUMERICTYPED, JavaPluginUtils.MODEL_NUMERICTYPED_NEGATIVE);
			val negativeOrZero = Utils.getStereotypePropertyValue(packageableElement, JavaPluginUtils.MODEL_NUMERICTYPED, JavaPluginUtils.MODEL_NUMERICTYPED_NEGATIVEORZERO);
			val positive = Utils.getStereotypePropertyValue(packageableElement, JavaPluginUtils.MODEL_NUMERICTYPED, JavaPluginUtils.MODEL_NUMERICTYPED_POSITIVE);
			val positiveOrZero = Utils.getStereotypePropertyValue(packageableElement, JavaPluginUtils.MODEL_NUMERICTYPED, JavaPluginUtils.MODEL_NUMERICTYPED_POSITIVEORZERO);
		
			val list = newArrayList();
			list.add(negative);
			list.add(negativeOrZero);
			list.add(positive);
			list.add(positiveOrZero);
			val test = list.filter[att | 
				att == true
			]
			if(test.length > 1){		
				errors.add("L'attibut " + packageableElement.name + " de la classe" + owner.name + " possède des attributs incompatibles (parmis negative, negativeOrZero, positive et positiveOrZero) dans le stéréotype numericTyped")	
			}
			
			val min = Utils.getStereotypePropertyValue(packageableElement, JavaPluginUtils.MODEL_NUMERICTYPED, JavaPluginUtils.MODEL_NUMERICTYPED_MIN);
			val hasMin = Utils.getStereotypePropertyValue(packageableElement, JavaPluginUtils.MODEL_NUMERICTYPED, JavaPluginUtils.MODEL_NUMERICTYPED_HASMIN);
			if(min != 0 && hasMin == false){
				warnings.add("L'attibut " + packageableElement.name + " de la classe " + owner.name + " possède, dans le stéréotype numericTyped, un attribut min valué mais l'attribut hasMin n'est pas à true. Min ne sera pas pris en compte. ")
			}
			
			val max = Utils.getStereotypePropertyValue(packageableElement, JavaPluginUtils.MODEL_NUMERICTYPED, JavaPluginUtils.MODEL_NUMERICTYPED_MAX);
			val hasMax = Utils.getStereotypePropertyValue(packageableElement, JavaPluginUtils.MODEL_NUMERICTYPED, JavaPluginUtils.MODEL_NUMERICTYPED_HASMAX);
			if(max != 0 && hasMax == false){
				warnings.add("L'attibut " + packageableElement.name + " de la classe " + owner.name + " possède, dans le stéréotype numericTyped, un attribut max valué mais l'attribut hasMax n'est pas à true. Max ne sera pas pris en compte. ")
			}
			
			val decimalMin = Utils.getStereotypePropertyValue(packageableElement, JavaPluginUtils.MODEL_NUMERICTYPED, JavaPluginUtils.MODEL_NUMERICTYPED_DECIMALMIN);
			val hasDecimalMin = Utils.getStereotypePropertyValue(packageableElement, JavaPluginUtils.MODEL_NUMERICTYPED, JavaPluginUtils.MODEL_NUMERICTYPED_HASDECIMALMIN);
			if(decimalMin != 0.0 && hasDecimalMin == false){
				warnings.add("L'attibut " + packageableElement.name + " de la classe " + owner.name + " possède, dans le stéréotype numericTyped, un attribut decimalMin valué mais l'attribut hasDecimalMin n'est pas à true. decimalMin ne sera pas pris en compte. ")
			}
			
			val decimalMax = Utils.getStereotypePropertyValue(packageableElement, JavaPluginUtils.MODEL_NUMERICTYPED, JavaPluginUtils.MODEL_NUMERICTYPED_DECIMALMAX);
			val hasDecimalMax = Utils.getStereotypePropertyValue(packageableElement, JavaPluginUtils.MODEL_NUMERICTYPED, JavaPluginUtils.MODEL_NUMERICTYPED_HASDECIMALMAX);
			if(decimalMax != 0.0 && hasDecimalMax == false){
				warnings.add("L'attibut " + packageableElement.name + " de la classe " + owner.name + " possède, dans le stéréotype numericTyped, un attribut decimalMax valué mais l'attribut hasDecimalMax n'est pas à true. decimalMax ne sera pas pris en compte. ")
			}
		}
	
	}
}