package fr.gouv.diplomatie.papyrus.codegen.ui.core.validator;

import org.eclipse.uml2.uml.PackageableElement;


import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.Property;
import fr.gouv.diplomatie.papyrus.codegen.core.utils.Utils
import fr.gouv.diplomatie.papyrus.codegen.core.console.ConsoleUtils
import fr.gouv.diplomatie.papyrus.codegen.core.utils.ClassifierUtils
import org.eclipse.uml2.uml.NamedElement
import fr.gouv.diplomatie.papyrus.codegen.core.utils.PropertyUtils
import java.util.ArrayList

/**
 * classe de validation d'un modèle hornet papyrus
 */
public class HornetModelValidator {
	
	public ArrayList<String> errors;
	
	/**
	 * Teste si le modèle passé au générateur est valide ou non
	 */
	public def validate(PackageableElement packageableElement, ConsoleUtils console) {
		errors = newArrayList;
		console.out.println("Début de la validation");
		validateElement(packageableElement, console);
		return errors;
	}
	
	/**
	 * teste la validité d'un element du modèle
	 */
	public def validateElement(NamedElement packageableElement, ConsoleUtils console){
		if(packageableElement instanceof Package) {
			packageableElement.validatePackage(console);
		}else if (packageableElement instanceof Classifier){
			packageableElement.validateClassifier(console);
		}
	}
	
	/**
	 * teste la validité d'un package
	 */
	public def validatePackage(Package packageableElement, ConsoleUtils console){
		packageableElement.packagedElements.forEach[childPkg|
			childPkg.validateElement(console);
		]                                		
	}
	
	/**
	 * teste la validité d'une classe
	 */
	public def validateClassifier(Classifier packageableElement, ConsoleUtils console){
		val isEntity = Utils.isEntity(packageableElement);
		val isNomenclature = Utils.isNomenclature(packageableElement);
		val isValueObject = Utils.isValueObject(packageableElement);
		
		if(isEntity && (isNomenclature || isValueObject) || (isNomenclature && isValueObject)){
			errors.add("La classe " + packageableElement.name + " possède plus d'un stéréotype de classe");
		}
		
		val attributes = ClassifierUtils.getOwnedAttributes(packageableElement);
		attributes.forEach[attribut |
			attribut.validateProperty(console);
		]
	}
	
		/**
	 * teste la validité d'un package
	 */
	public def void validateProperty(Property packageableElement, ConsoleUtils console){
		val isAttribute = PropertyUtils.isAttribut(packageableElement);
		val isKeyAttribute = PropertyUtils.isID(packageableElement);
		val isCLNom = PropertyUtils.isCodeLibelleNomenclature(packageableElement);
		
		val owner = packageableElement.owner as Classifier
		
		// test un seul type d'attribut
		if(isAttribute && (isKeyAttribute || isCLNom) || (isKeyAttribute && isCLNom)){
			errors.add("L'attribut " + packageableElement.name + " de la classe " + owner.name +" possède plus d'un stéréotype parmis les stéréotypes keyAttribute, attribute et codeLibelleNomenclature");
		}
		
		// test un codelibellenomenclature doit etre dans une nomenclature
		if(isCLNom && !Utils.isNomenclature(owner)){
			errors.add("L'attribut " + packageableElement.name + " de la classe " + owner.name +" possède un stéréotype codeLibelleNomenclature mais n'est pas présent dans une nomenclature");
		}
		
		val screamingCamelCasePattern = "^[A-Z0-9_]*$"; 
		if(packageableElement.name.matches(screamingCamelCasePattern)){
			errors.add("Le nom de l'attribut " + packageableElement.name + " doit être écrit en camel case ou en snake case");
		}	                        			
	}
}
