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
package fr.gouv.diplomatie.papyrus.codegen.java.utils;

import com.google.common.base.Objects;
import fr.gouv.diplomatie.papyrus.codegen.core.utils.Utils;
import org.eclipse.uml2.uml.Association;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.internal.impl.EnumerationLiteralImpl;
import org.eclipse.xtext.xbase.lib.IterableExtensions;

@SuppressWarnings("all")
public class JavaPluginUtils {
  public static String MODEL_NATURALORDER = "naturalOrder";
  
  public static String MODEL_SCHEMA = "schema";
  
  public static String MODEL_ATTRIBUTE_SHOULDBENULL = "shouldBeNull";
  
  public static String MODEL_NUMERICTYPED = "numericTyped";
  
  public static String MODEL_NUMERICTYPED_MIN = "min";
  
  public static String MODEL_NUMERICTYPED_MAX = "max";
  
  public static String MODEL_NUMERICTYPED_NEGATIVE = "negative";
  
  public static String MODEL_NUMERICTYPED_NEGATIVEORZERO = "negativeOrZero";
  
  public static String MODEL_NUMERICTYPED_POSITIVE = "positive";
  
  public static String MODEL_NUMERICTYPED_POSITIVEORZERO = "positiveOrZero";
  
  public static String MODEL_NUMERICTYPED_DIGITS = "digits";
  
  public static String MODEL_NUMERICTYPED_DIGITSINTEGER = "digitsInteger";
  
  public static String MODEL_NUMERICTYPED_DIGITSFRACTION = "digitsFraction";
  
  public static String MODEL_NUMERICTYPED_DECIMALMIN = "decimalMin";
  
  public static String MODEL_NUMERICTYPED_DECIMALMAX = "decimalMax";
  
  public static String MODEL_BOOLEANTYPED = "booleanTyped";
  
  public static String MODEL_BOOLEANTYPED_ALWAYSTRUE = "alwaysTrue";
  
  public static String MODEL_BOOLEANTYPED_ALWAYSFALSE = "alwaysFalse";
  
  public static String MODEL_DATETYPED = "dateTyped";
  
  public static String MODEL_DATETYPED_FUTURE = "future";
  
  public static String MODEL_DATETYPED_PAST = "past";
  
  public static String MODEL_DATETYPED_FUTUREORPRESENT = "futureOrPresent";
  
  public static String MODEL_DATETYPED_PASTORPRESENT = "pastOrPresent";
  
  public static String MODEL_STRINGTYPED = "stringTyped";
  
  public static String MODEL_STRINGTYPED_CANBEEMPTY = "canBeEmpty";
  
  public static String MODEL_STRINGTYPED_PATTERN = "pattern";
  
  public static String MODEL_STRINGTYPED_SIZEMIN = "sizeMin";
  
  public static String MODEL_COLLECTION = "collection";
  
  public static String MODEL_COLLECTION_SIZEMIN = "sizeMin";
  
  public static String MODEL_COLLECTION_SIZEMAX = "sizeMax";
  
  /**
   * teste si un element est de type naturalOrder
   */
  public static boolean isNaturalOrderField(final NamedElement elem) {
    return Utils.hasStereotype(elem, JavaPluginUtils.MODEL_NATURALORDER);
  }
  
  /**
   * retourne le fetchType de l'element ou null si celui ci n'en possède pas
   */
  public static EnumerationLiteralImpl getFetchType(final NamedElement elem) {
    boolean _isAssociationTable = Utils.isAssociationTable(elem);
    if (_isAssociationTable) {
      Object _stereotypePropertyValue = Utils.getStereotypePropertyValue(elem, Utils.MODEL_ASSOCIATIONTABLE, Utils.MODEL_FETCHTYPE);
      return ((EnumerationLiteralImpl) _stereotypePropertyValue);
    } else {
      if ((elem instanceof Property)) {
        Association _association = ((Property)elem).getAssociation();
        boolean _tripleEquals = (_association == null);
        if (_tripleEquals) {
          if (((Utils.getStereotype(elem, Utils.MODEL_KEYATTRIBUTE) == null) || IterableExtensions.isEmpty(Utils.getStereotype(elem, Utils.MODEL_KEYATTRIBUTE)))) {
            Object _stereotypePropertyValue_1 = Utils.getStereotypePropertyValue(elem, Utils.MODEL_ATTRIBUTE, Utils.MODEL_FETCHTYPE);
            return ((EnumerationLiteralImpl) _stereotypePropertyValue_1);
          } else {
            Object _stereotypePropertyValue_2 = Utils.getStereotypePropertyValue(elem, Utils.MODEL_KEYATTRIBUTE, Utils.MODEL_FETCHTYPE);
            return ((EnumerationLiteralImpl) _stereotypePropertyValue_2);
          }
        } else {
          boolean _isAssociationLink = Utils.isAssociationLink(((Property)elem).getAssociation());
          if (_isAssociationLink) {
            Object _stereotypePropertyValue_3 = Utils.getStereotypePropertyValue(((Property)elem).getAssociation(), Utils.MODEL_ASSOCIATIONLINK, Utils.MODEL_FETCHTYPE);
            return ((EnumerationLiteralImpl) _stereotypePropertyValue_3);
          } else {
            return null;
          }
        }
      } else {
        return null;
      }
    }
  }
  
  public static Object getShouldBeNull(final NamedElement elem) {
    return Utils.getStereotypePropertyValue(elem, Utils.MODEL_ATTRIBUTE, JavaPluginUtils.MODEL_ATTRIBUTE_SHOULDBENULL);
  }
  
  public static Object getAlwaysTrue(final NamedElement elem) {
    return Utils.getStereotypePropertyValue(elem, JavaPluginUtils.MODEL_BOOLEANTYPED, JavaPluginUtils.MODEL_BOOLEANTYPED_ALWAYSTRUE);
  }
  
  public static Object getAlwaysFalse(final NamedElement elem) {
    return Utils.getStereotypePropertyValue(elem, JavaPluginUtils.MODEL_BOOLEANTYPED, JavaPluginUtils.MODEL_BOOLEANTYPED_ALWAYSFALSE);
  }
  
  public static Object getFuture(final NamedElement elem) {
    return Utils.getStereotypePropertyValue(elem, JavaPluginUtils.MODEL_DATETYPED, JavaPluginUtils.MODEL_DATETYPED_FUTURE);
  }
  
  public static Object getFutureOrPresent(final NamedElement elem) {
    return Utils.getStereotypePropertyValue(elem, JavaPluginUtils.MODEL_DATETYPED, JavaPluginUtils.MODEL_DATETYPED_FUTUREORPRESENT);
  }
  
  public static Object getPast(final NamedElement elem) {
    return Utils.getStereotypePropertyValue(elem, JavaPluginUtils.MODEL_DATETYPED, JavaPluginUtils.MODEL_DATETYPED_PAST);
  }
  
  public static Object getPastOrPresent(final NamedElement elem) {
    return Utils.getStereotypePropertyValue(elem, JavaPluginUtils.MODEL_DATETYPED, JavaPluginUtils.MODEL_DATETYPED_PASTORPRESENT);
  }
  
  public static Object getCollectionSizeMin(final NamedElement elem) {
    final Object hasSizeMin = Utils.getStereotypePropertyValue(elem, JavaPluginUtils.MODEL_COLLECTION, "hasSizeMin");
    boolean _equals = Objects.equal(hasSizeMin, Boolean.valueOf(true));
    if (_equals) {
      return Utils.getStereotypePropertyValue(elem, JavaPluginUtils.MODEL_COLLECTION, JavaPluginUtils.MODEL_COLLECTION_SIZEMIN);
    }
    return null;
  }
  
  public static Object getCollectionSizeMax(final NamedElement elem) {
    final Object hasSizeMax = Utils.getStereotypePropertyValue(elem, JavaPluginUtils.MODEL_COLLECTION, "hasSizeMax");
    boolean _equals = Objects.equal(hasSizeMax, Boolean.valueOf(true));
    if (_equals) {
      return Utils.getStereotypePropertyValue(elem, JavaPluginUtils.MODEL_COLLECTION, JavaPluginUtils.MODEL_COLLECTION_SIZEMAX);
    }
    return null;
  }
  
  public static Object getCanBeEmpty(final NamedElement elem) {
    return Utils.getStereotypePropertyValue(elem, JavaPluginUtils.MODEL_STRINGTYPED, JavaPluginUtils.MODEL_STRINGTYPED_CANBEEMPTY);
  }
  
  public static Object getPattern(final NamedElement elem) {
    return Utils.getStereotypePropertyValue(elem, JavaPluginUtils.MODEL_STRINGTYPED, JavaPluginUtils.MODEL_STRINGTYPED_PATTERN);
  }
  
  public static Object getSizeMin(final NamedElement elem) {
    final Object hasSizeMin = Utils.getStereotypePropertyValue(elem, JavaPluginUtils.MODEL_COLLECTION, "hasSizeMin");
    boolean _equals = Objects.equal(hasSizeMin, Boolean.valueOf(true));
    if (_equals) {
      return Utils.getStereotypePropertyValue(elem, JavaPluginUtils.MODEL_STRINGTYPED, JavaPluginUtils.MODEL_STRINGTYPED_SIZEMIN);
    }
    return null;
  }
  
  public static Object getMin(final NamedElement elem) {
    final Object hasMin = Utils.getStereotypePropertyValue(elem, JavaPluginUtils.MODEL_COLLECTION, "hasMin");
    boolean _equals = Objects.equal(hasMin, Boolean.valueOf(true));
    if (_equals) {
      return Utils.getStereotypePropertyValue(elem, JavaPluginUtils.MODEL_NUMERICTYPED, JavaPluginUtils.MODEL_NUMERICTYPED_MIN);
    }
    return null;
  }
  
  public static Object getMax(final NamedElement elem) {
    final Object hasMax = Utils.getStereotypePropertyValue(elem, JavaPluginUtils.MODEL_COLLECTION, "hasMax");
    boolean _equals = Objects.equal(hasMax, Boolean.valueOf(true));
    if (_equals) {
      return Utils.getStereotypePropertyValue(elem, JavaPluginUtils.MODEL_NUMERICTYPED, JavaPluginUtils.MODEL_NUMERICTYPED_MAX);
    }
    return null;
  }
  
  public static Object getNegative(final NamedElement elem) {
    return Utils.getStereotypePropertyValue(elem, JavaPluginUtils.MODEL_NUMERICTYPED, JavaPluginUtils.MODEL_NUMERICTYPED_NEGATIVE);
  }
  
  public static Object getNegativeOrZero(final NamedElement elem) {
    return Utils.getStereotypePropertyValue(elem, JavaPluginUtils.MODEL_NUMERICTYPED, JavaPluginUtils.MODEL_NUMERICTYPED_NEGATIVEORZERO);
  }
  
  public static Object getPositive(final NamedElement elem) {
    return Utils.getStereotypePropertyValue(elem, JavaPluginUtils.MODEL_NUMERICTYPED, JavaPluginUtils.MODEL_NUMERICTYPED_POSITIVE);
  }
  
  public static Object getPositiveOrZero(final NamedElement elem) {
    return Utils.getStereotypePropertyValue(elem, JavaPluginUtils.MODEL_NUMERICTYPED, JavaPluginUtils.MODEL_NUMERICTYPED_POSITIVEORZERO);
  }
  
  public static Object getDigits(final NamedElement elem) {
    return Utils.getStereotypePropertyValue(elem, JavaPluginUtils.MODEL_NUMERICTYPED, JavaPluginUtils.MODEL_NUMERICTYPED_DIGITS);
  }
  
  public static Object getDigitsFraction(final NamedElement elem) {
    return Utils.getStereotypePropertyValue(elem, JavaPluginUtils.MODEL_NUMERICTYPED, JavaPluginUtils.MODEL_NUMERICTYPED_DIGITSFRACTION);
  }
  
  public static Object getDigitsInteger(final NamedElement elem) {
    return Utils.getStereotypePropertyValue(elem, JavaPluginUtils.MODEL_NUMERICTYPED, JavaPluginUtils.MODEL_NUMERICTYPED_DIGITSINTEGER);
  }
  
  public static Object getDecimalMin(final NamedElement elem) {
    final Object hasDecimalMin = Utils.getStereotypePropertyValue(elem, JavaPluginUtils.MODEL_COLLECTION, "hasDecimalMin");
    boolean _equals = Objects.equal(hasDecimalMin, Boolean.valueOf(true));
    if (_equals) {
      return Utils.getStereotypePropertyValue(elem, JavaPluginUtils.MODEL_NUMERICTYPED, JavaPluginUtils.MODEL_NUMERICTYPED_DECIMALMIN);
    }
    return null;
  }
  
  public static Object getDecimalMax(final NamedElement elem) {
    final Object hasDecimalMax = Utils.getStereotypePropertyValue(elem, JavaPluginUtils.MODEL_COLLECTION, "hasDecimalMax");
    boolean _equals = Objects.equal(hasDecimalMax, Boolean.valueOf(true));
    if (_equals) {
      return Utils.getStereotypePropertyValue(elem, JavaPluginUtils.MODEL_NUMERICTYPED, JavaPluginUtils.MODEL_NUMERICTYPED_DECIMALMAX);
    }
    return null;
  }
}
