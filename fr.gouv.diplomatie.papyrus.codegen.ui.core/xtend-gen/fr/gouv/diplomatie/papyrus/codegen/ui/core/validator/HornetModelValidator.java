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
 */
package fr.gouv.diplomatie.papyrus.codegen.ui.core.validator;

import fr.gouv.diplomatie.papyrus.codegen.core.console.ConsoleUtils;
import fr.gouv.diplomatie.papyrus.codegen.core.utils.ClassifierUtils;
import fr.gouv.diplomatie.papyrus.codegen.core.utils.PropertyUtils;
import fr.gouv.diplomatie.papyrus.codegen.core.utils.Utils;
import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Consumer;
import org.eclipse.emf.common.util.EList;
import org.eclipse.uml2.uml.Association;
import org.eclipse.uml2.uml.AssociationClass;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Interface;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.PackageableElement;
import org.eclipse.uml2.uml.PrimitiveType;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Stereotype;
import org.eclipse.uml2.uml.Type;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;
import org.eclipse.xtext.xbase.lib.Conversions;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;

/**
 * classe de validation d'un modèle Hornet Papyrus
 */
@SuppressWarnings("all")
public class HornetModelValidator {
  public ArrayList<String> errors;
  
  public ArrayList<String> warnings;
  
  public String startGenerationMessage = "Début de la validation";
  
  /**
   * Teste si le modèle passé au générateur est valide ou non
   */
  public ArrayList<String> validate(final PackageableElement packageableElement, final ConsoleUtils console) {
    this.errors = CollectionLiterals.<String>newArrayList();
    this.warnings = CollectionLiterals.<String>newArrayList();
    console.out.println(this.startGenerationMessage);
    this.validateElement(packageableElement, console);
    return this.errors;
  }
  
  /**
   * teste la validité d'un element du modèle
   */
  public void validateElement(final NamedElement packageableElement, final ConsoleUtils console) {
    if ((packageableElement instanceof org.eclipse.uml2.uml.Package)) {
      this.validatePackage(((org.eclipse.uml2.uml.Package)packageableElement), console);
    } else {
      if ((packageableElement instanceof Classifier)) {
        this.validateClassifier(((Classifier)packageableElement), console);
      }
    }
  }
  
  /**
   * teste la validité d'un package
   */
  public void validatePackage(final org.eclipse.uml2.uml.Package packageableElement, final ConsoleUtils console) {
    final Consumer<PackageableElement> _function = (PackageableElement childPkg) -> {
      this.validateElement(childPkg, console);
    };
    packageableElement.getPackagedElements().forEach(_function);
  }
  
  /**
   * teste la validité d'une classe
   */
  public void validateClassifier(final Classifier packageableElement, final ConsoleUtils console) {
    final boolean isEntity = Utils.isEntity(packageableElement);
    final boolean isNomenclature = Utils.isNomenclature(packageableElement);
    final boolean isValueObject = Utils.isValueObject(packageableElement);
    final EList<Stereotype> stereotypes = packageableElement.getAppliedStereotypes();
    if ((((stereotypes.isEmpty() && (!(packageableElement instanceof AssociationClass))) && (!(packageableElement instanceof Interface))) && (!(packageableElement instanceof Association)))) {
      String _name = packageableElement.getName();
      String _plus = ("La classe " + _name);
      String _plus_1 = (_plus + " ne possède pas de stéréotype");
      this.errors.add(_plus_1);
    }
    if (((isEntity && (isNomenclature || isValueObject)) || (isNomenclature && isValueObject))) {
      String _name_1 = packageableElement.getName();
      String _plus_2 = ("La classe " + _name_1);
      String _plus_3 = (_plus_2 + " possède plus d\'un stéréotype de classe");
      this.errors.add(_plus_3);
    }
    if (isEntity) {
      final Function1<Property, Boolean> _function = (Property att) -> {
        return Boolean.valueOf(PropertyUtils.isID(att));
      };
      final Iterable<Property> attributes = IterableExtensions.<Property>filter(ClassifierUtils.getOwnedAttributes(packageableElement), _function);
      int _length = ((Object[])Conversions.unwrapArray(attributes, Object.class)).length;
      boolean _equals = (_length == 0);
      if (_equals) {
        String _name_2 = packageableElement.getName();
        String _plus_4 = ("La classe " + _name_2);
        String _plus_5 = (_plus_4 + " possède le stéréotype entity mais n\'a pas d\'attribut possédant le stéréotype keyAttribute");
        this.errors.add(_plus_5);
      }
    }
    final Collection<Property> attributes_1 = ClassifierUtils.getOwnedAttributes(packageableElement);
    final Consumer<Property> _function_1 = (Property attribut) -> {
      this.validateProperty(attribut, console);
    };
    attributes_1.forEach(_function_1);
  }
  
  /**
   * teste la validité d'une propriété
   */
  public void validateProperty(final Property packageableElement, final ConsoleUtils console) {
    final boolean isAttribute = PropertyUtils.isAttribut(packageableElement);
    final boolean isKeyAttribute = PropertyUtils.isID(packageableElement);
    final boolean isCLNom = PropertyUtils.isCodeLibelleNomenclature(packageableElement);
    Element _owner = packageableElement.getOwner();
    final Classifier owner = ((Classifier) _owner);
    Type _type = packageableElement.getType();
    final NamedElement type = ((NamedElement) _type);
    final boolean hornetType = Utils.hasStereotype(type, Utils.MODEL_HORNETTYPE);
    final boolean primitiveType = (type instanceof PrimitiveType);
    final EList<Stereotype> stereotypes = packageableElement.getAppliedStereotypes();
    if ((stereotypes.isEmpty() && (packageableElement.getAssociation() == null))) {
      String _name = packageableElement.getName();
      String _plus = ("L\'attribut " + _name);
      String _plus_1 = (_plus + " ne possède aucun stéréotype d\'attribut ( par défaut mettre le stéréotype attribute)");
      this.errors.add(_plus_1);
    }
    if ((primitiveType && (!hornetType))) {
      String _name_1 = packageableElement.getName();
      String _plus_2 = ("Le type de l\'attribut " + _name_1);
      String _plus_3 = (_plus_2 + " de la classe ");
      String _name_2 = owner.getName();
      String _plus_4 = (_plus_3 + _name_2);
      String _plus_5 = (_plus_4 + " n\'est pas un type Hornet (stéréotype hornetType)");
      this.errors.add(_plus_5);
    }
    if (((isAttribute && (isKeyAttribute || isCLNom)) || (isKeyAttribute && isCLNom))) {
      String _name_3 = packageableElement.getName();
      String _plus_6 = ("L\'attribut " + _name_3);
      String _plus_7 = (_plus_6 + " de la classe ");
      String _name_4 = owner.getName();
      String _plus_8 = (_plus_7 + _name_4);
      String _plus_9 = (_plus_8 + " possède plus d\'un stéréotype parmis les stéréotypes keyAttribute, attribute et codeLibelleNomenclature");
      this.errors.add(_plus_9);
    }
    if ((isCLNom && (!Utils.isNomenclature(owner)))) {
      String _name_5 = packageableElement.getName();
      String _plus_10 = ("L\'attribut " + _name_5);
      String _plus_11 = (_plus_10 + " de la classe ");
      String _name_6 = owner.getName();
      String _plus_12 = (_plus_11 + _name_6);
      String _plus_13 = (_plus_12 + " possède un stéréotype codeLibelleNomenclature mais n\'est pas présent dans une nomenclature");
      this.errors.add(_plus_13);
    }
    final String screamingSnakeCasePattern = "^[A-Z0-9_]*$";
    boolean _matches = packageableElement.getName().matches(screamingSnakeCasePattern);
    if (_matches) {
      String _name_7 = packageableElement.getName();
      String _plus_14 = ("Le nom de l\'attribut " + _name_7);
      String _plus_15 = (_plus_14 + " de la classe ");
      String _name_8 = owner.getName();
      String _plus_16 = (_plus_15 + _name_8);
      String _plus_17 = (_plus_16 + " doit être écrit en camel case ou en snake case");
      this.errors.add(_plus_17);
    }
  }
}
