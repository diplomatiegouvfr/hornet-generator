package fr.gouv.diplomatie.papyrus.codegen.ui.validators;

import fr.gouv.diplomatie.papyrus.codegen.ui.core.validator.HornetModelValidator;
import fr.gouv.diplomatie.papyrus.codegen.core.console.ConsoleUtils
import org.eclipse.uml2.uml.Property;
import fr.gouv.diplomatie.papyrus.codegen.core.utils.Utils
import fr.gouv.diplomatie.papyrus.codegen.java.utils.JavaPluginUtils
import org.eclipse.uml2.uml.NamedElement

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
		
		if(isCollection && !packageableElement.isMultivalued ){
			errors.add("L'attibut " + packageableElement.name + " de la classe" + owner.name + " possède le stéréotype collection mais n'est pas multivalué.")
		}
		
		if(isBooleanTyped){
			val type = packageableElement.type.name
			types.remove("Boolean");
			if(types.contains(type)){
				errors.add("L'attibut " + packageableElement.name + " de la classe" + owner.name + " est de type " + type + " mais possède le stéréotype booleanTyped.")
			}
			types.add("Boolean");
		}
		
		if(isDateTyped){
			val type = packageableElement.type.name
			types.remove("Date");
			if(types.contains(type)){
				errors.add("L'attibut " + packageableElement.name + " de la classe" + owner.name + " est de type " + type + " mais possède le stéréotype dateTyped.")
			}
			types.add("Date");
		}
		
		if(isStringTyped){
			val type = packageableElement.type.name
			types.remove("String");
			if(types.contains(type)){
				errors.add("L'attibut " + packageableElement.name + " de la classe" + owner.name + " est de type " + type + " mais possède le stéréotype stringTyped.")
			}
			types.add("String");
		}
		
		if(isNumericTyped){
			val type = packageableElement.type.name
			types.removeAll("Real", "BigInteger", "Double", "Integer", "Float");
			if(types.contains(type)){
				errors.add("L'attibut " + packageableElement.name + " de la classe" + owner.name + " est de type " + type + " mais possède le stéréotype numericTyped.")
			}
			types.addAll("Real", "BigInteger", "Double", "Integer", "Float");
		}
	
	}
}