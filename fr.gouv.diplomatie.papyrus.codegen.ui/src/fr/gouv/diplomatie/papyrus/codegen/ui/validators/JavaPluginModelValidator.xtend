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