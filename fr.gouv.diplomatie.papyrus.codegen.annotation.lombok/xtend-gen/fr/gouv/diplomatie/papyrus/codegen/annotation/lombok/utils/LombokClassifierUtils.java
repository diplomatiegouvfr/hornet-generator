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
package fr.gouv.diplomatie.papyrus.codegen.annotation.lombok.utils;

import com.google.common.base.Objects;
import fr.gouv.diplomatie.papyrus.codegen.core.utils.Utils;
import org.eclipse.uml2.uml.Association;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.Property;
import org.eclipse.xtext.xbase.lib.IterableExtensions;

@SuppressWarnings("all")
public class LombokClassifierUtils {
  public static String MODEL_ENTITY_LOMBOKGETTER = "lombokGetter";
  
  public static String MODEL_ENTITY_LOMBOKSETTER = "lombokSetter";
  
  public static String MODEL_ENTITY_LOMBOKNOARGSCONS = "lombokNoArgsConstructor";
  
  public static String MODEL_ENTITY_LOMBOKALLARGSCONS = "lombokAllArgsConstructor";
  
  public static String MODEL_ENTITY_LOMBOKTOSTRING = "lombokToString";
  
  public static String MODEL_ENTITY_LOMBOKEQANDHASHC = "lombokEqualsAndHashCode";
  
  public static String MODEL_LOMBOKDISPLAYWITH = "lombokDisplayWith";
  
  /**
   * teste si la classe doit avoir une annotation getter
   */
  public static Object hasGetterAnnotation(final Classifier clazz) {
    final Object getter = LombokClassifierUtils.getGetterAnnotation(clazz);
    if (((getter == null) || Objects.equal(getter, ""))) {
      return Boolean.valueOf(false);
    }
    return getter;
  }
  
  public static Object getGetterAnnotation(final Classifier clazz) {
    return Utils.getStereotypePropertyValue(clazz, Utils.MODEL_ENTITY, LombokClassifierUtils.MODEL_ENTITY_LOMBOKGETTER);
  }
  
  /**
   * teste si la classe doit avoir une annotation setter
   */
  public static Object hasSetterAnnotation(final Classifier clazz) {
    final Object setter = LombokClassifierUtils.getSetterAnnotation(clazz);
    if (((setter == null) || Objects.equal(setter, ""))) {
      return Boolean.valueOf(false);
    }
    return setter;
  }
  
  public static Object getSetterAnnotation(final Classifier clazz) {
    return Utils.getStereotypePropertyValue(clazz, Utils.MODEL_ENTITY, LombokClassifierUtils.MODEL_ENTITY_LOMBOKSETTER);
  }
  
  /**
   * teste si la classe doit avoir une annotation NoArgsConstructor
   */
  public static Object hasNoArgsConstructor(final Classifier clazz) {
    final Object noArgsCons = LombokClassifierUtils.getNoArgsConstructorAnnotation(clazz);
    if (((noArgsCons == null) || Objects.equal(noArgsCons, ""))) {
      return Boolean.valueOf(false);
    }
    return noArgsCons;
  }
  
  public static Object getNoArgsConstructorAnnotation(final Classifier clazz) {
    return Utils.getStereotypePropertyValue(clazz, Utils.MODEL_ENTITY, LombokClassifierUtils.MODEL_ENTITY_LOMBOKNOARGSCONS);
  }
  
  /**
   * teste si la classe doit avoir une annotation AllArgsConstructor
   */
  public static Object hasAllArgsConstructor(final Classifier clazz) {
    final Object allArgsCons = LombokClassifierUtils.getAllArgsConstructorAnnotation(clazz);
    if (((allArgsCons == null) || Objects.equal(allArgsCons, ""))) {
      return Boolean.valueOf(false);
    }
    return allArgsCons;
  }
  
  public static Object getAllArgsConstructorAnnotation(final Classifier clazz) {
    return Utils.getStereotypePropertyValue(clazz, Utils.MODEL_ENTITY, LombokClassifierUtils.MODEL_ENTITY_LOMBOKALLARGSCONS);
  }
  
  /**
   * teste si la classe doit avoir une annotation ToString
   */
  public static Object hasToString(final Classifier clazz) {
    final Object toString = LombokClassifierUtils.getToStringAnnotation(clazz);
    if (((toString == null) || Objects.equal(toString, ""))) {
      return Boolean.valueOf(false);
    }
    return toString;
  }
  
  public static Object getToStringAnnotation(final Classifier clazz) {
    return Utils.getStereotypePropertyValue(clazz, Utils.MODEL_ENTITY, LombokClassifierUtils.MODEL_ENTITY_LOMBOKTOSTRING);
  }
  
  /**
   * teste si la classe doit avoir une annotation EqualsAndHashCode
   */
  public static Object hasEqualsAndHashCode(final Classifier clazz) {
    final Object eqAndHashCode = LombokClassifierUtils.getEqualsAndHashCode(clazz);
    if (((eqAndHashCode == null) || Objects.equal(eqAndHashCode, ""))) {
      return Boolean.valueOf(false);
    }
    return eqAndHashCode;
  }
  
  public static Object getEqualsAndHashCode(final Classifier clazz) {
    return Utils.getStereotypePropertyValue(clazz, Utils.MODEL_ENTITY, LombokClassifierUtils.MODEL_ENTITY_LOMBOKEQANDHASHC);
  }
  
  /**
   * retourne la valeur de l'attribut displayWith
   */
  public static Boolean getLombokDisplayWith(final NamedElement elem) {
    boolean _isAssociationTable = Utils.isAssociationTable(elem);
    if (_isAssociationTable) {
      Object _stereotypePropertyValue = Utils.getStereotypePropertyValue(elem, Utils.MODEL_ASSOCIATIONTABLE, LombokClassifierUtils.MODEL_LOMBOKDISPLAYWITH);
      return ((Boolean) _stereotypePropertyValue);
    } else {
      if ((elem instanceof Property)) {
        Association _association = ((Property)elem).getAssociation();
        boolean _tripleEquals = (_association == null);
        if (_tripleEquals) {
          if (((Utils.getStereotype(elem, Utils.MODEL_KEYATTRIBUTE) == null) || IterableExtensions.isEmpty(Utils.getStereotype(elem, Utils.MODEL_KEYATTRIBUTE)))) {
            Object _stereotypePropertyValue_1 = Utils.getStereotypePropertyValue(elem, Utils.MODEL_ATTRIBUTE, LombokClassifierUtils.MODEL_LOMBOKDISPLAYWITH);
            return ((Boolean) _stereotypePropertyValue_1);
          } else {
            Object _stereotypePropertyValue_2 = Utils.getStereotypePropertyValue(elem, Utils.MODEL_KEYATTRIBUTE, LombokClassifierUtils.MODEL_LOMBOKDISPLAYWITH);
            return ((Boolean) _stereotypePropertyValue_2);
          }
        } else {
          boolean _isAssociationLink = Utils.isAssociationLink(((Property)elem).getAssociation());
          if (_isAssociationLink) {
            Object _stereotypePropertyValue_3 = Utils.getStereotypePropertyValue(((Property)elem).getAssociation(), Utils.MODEL_ASSOCIATIONLINK, LombokClassifierUtils.MODEL_LOMBOKDISPLAYWITH);
            return ((Boolean) _stereotypePropertyValue_3);
          }
          return Boolean.valueOf(true);
        }
      } else {
        return Boolean.valueOf(true);
      }
    }
  }
}
