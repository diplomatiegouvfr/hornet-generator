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
 * fr.gouv.diplomatie.papyrus.codegen.ui.core - Outils pour l'écriture d'interface papyrus
 * de générateur 
 *
 * @author MEAE - Ministère de l'Europe et des Affaires étrangères
 * @version v1.3.2
 * @license CECILL-2.1
 */
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
import org.eclipse.uml2.uml.PrimitiveType
import org.eclipse.uml2.uml.AssociationClass
import org.eclipse.uml2.uml.Interface
import org.eclipse.uml2.uml.Association

/**
 * classe de validation d'un modèle Hornet Papyrus
 */
public class HornetModelValidator {
	
	public ArrayList<String> errors;
	public ArrayList<String> warnings;
	
	public String startGenerationMessage = "Début de la validation";
	
	/**
	 * Teste si le modèle passé au générateur est valide ou non
	 */
	public def validate(PackageableElement packageableElement, ConsoleUtils console) {
		errors = newArrayList;
		warnings = newArrayList;
		console.out.println(startGenerationMessage);
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
		
		val stereotypes = packageableElement.appliedStereotypes
		
		if(stereotypes.empty && (!(packageableElement instanceof AssociationClass)) && 
			(!(packageableElement instanceof Interface))&& 
			(!(packageableElement instanceof Association))
		){
			errors.add("La classe " + packageableElement.name + " ne possède pas de stéréotype");
		}
				
		if(isEntity && (isNomenclature || isValueObject) || (isNomenclature && isValueObject)){
			errors.add("La classe " + packageableElement.name + " possède plus d'un stéréotype de classe");
		}
		
		if(isEntity){
			val attributes = ClassifierUtils.getOwnedAttributes(packageableElement).filter[ att |
				PropertyUtils.isID(att)
			];
			if(attributes.length == 0){
				errors.add("La classe " + packageableElement.name + " possède le stéréotype entity mais n'a pas d'attribut possédant le stéréotype keyAttribute");
			}
		}
		
		val attributes = ClassifierUtils.getOwnedAttributes(packageableElement);
		attributes.forEach[attribut |
			attribut.validateProperty(console);
		]
	}
	
	/**
	 * teste la validité d'une propriété
	 */
	public def void validateProperty(Property packageableElement, ConsoleUtils console){
		val isAttribute = PropertyUtils.isAttribut(packageableElement);
		val isKeyAttribute = PropertyUtils.isID(packageableElement);
		val isCLNom = PropertyUtils.isCodeLibelleNomenclature(packageableElement);
		
		val owner = packageableElement.owner as Classifier
		val type = packageableElement.type as NamedElement
		
		val hornetType = Utils.hasStereotype(type, Utils.MODEL_HORNETTYPE);
		val primitiveType = type instanceof PrimitiveType;
		
		val stereotypes = packageableElement.appliedStereotypes
		
		if(stereotypes.empty && packageableElement.association === null){
			errors.add("L'attribut " + packageableElement.name + " ne possède aucun stéréotype d'attribut ( par défaut mettre le stéréotype attribute)");
		}
		
		if(primitiveType && !hornetType){
			errors.add("Le type de l'attribut " + packageableElement.name + " de la classe " + owner.name +" n'est pas un type Hornet (stéréotype hornetType)");
		}
		
		// test un seul type d'attribut
		if(isAttribute && (isKeyAttribute || isCLNom) || (isKeyAttribute && isCLNom)){
			errors.add("L'attribut " + packageableElement.name + " de la classe " + owner.name +" possède plus d'un stéréotype parmis les stéréotypes keyAttribute, attribute et codeLibelleNomenclature");
		}
		
		// test un codelibellenomenclature doit etre dans une nomenclature
		if(isCLNom && !Utils.isNomenclature(owner)){
			errors.add("L'attribut " + packageableElement.name + " de la classe " + owner.name +" possède un stéréotype codeLibelleNomenclature mais n'est pas présent dans une nomenclature");
		}
		
		val screamingSnakeCasePattern = "^[A-Z0-9_]*$"; 
		if(packageableElement.name.matches(screamingSnakeCasePattern)){
			errors.add("Le nom de l'attribut " + packageableElement.name + " de la classe " + owner.name + " doit être écrit en camel case ou en snake case");
		}	                  			
	}
}
