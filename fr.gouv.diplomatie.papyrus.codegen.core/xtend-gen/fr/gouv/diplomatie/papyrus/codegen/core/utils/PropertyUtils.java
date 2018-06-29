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
package fr.gouv.diplomatie.papyrus.codegen.core.utils;

import com.google.common.base.Objects;
import fr.gouv.diplomatie.papyrus.codegen.core.utils.ClassifierUtils;
import fr.gouv.diplomatie.papyrus.codegen.core.utils.Utils;
import org.eclipse.uml2.uml.Association;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Stereotype;
import org.eclipse.uml2.uml.Type;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.xbase.lib.Conversions;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;

/**
 * classe utilitaire pour les Property
 */
@SuppressWarnings("all")
public class PropertyUtils {
  /**
   * retourne la classe Stereotype dont le nom est stereoname
   */
  public static Iterable<Stereotype> getStereotype(final Property prop, final String stereoname) {
    final Function1<Stereotype, Boolean> _function = (Stereotype stereotype) -> {
      String _name = stereotype.getName();
      return Boolean.valueOf(Objects.equal(_name, stereoname));
    };
    return IterableExtensions.<Stereotype>filter(prop.getAppliedStereotypes(), _function);
  }
  
  /**
   * retourne la valeur de l'attribut du stereotype de la classe
   */
  public static Object getStereotypePropertyValue(final Property prop, final String stereoname, final String property) {
    if (((PropertyUtils.getStereotype(prop, stereoname) == null) || IterableExtensions.isEmpty(PropertyUtils.getStereotype(prop, stereoname)))) {
      return null;
    }
    final Stereotype stereotype = ((Stereotype[])Conversions.unwrapArray(PropertyUtils.getStereotype(prop, stereoname), Stereotype.class))[0];
    if ((stereotype == null)) {
      return null;
    }
    return prop.getValue(stereotype, property);
  }
  
  public static boolean isAttribut(final Property property) {
    return ((PropertyUtils.getStereotype(property, Utils.MODEL_ATTRIBUTE) != null) && (!IterableExtensions.isEmpty(PropertyUtils.getStereotype(property, Utils.MODEL_ATTRIBUTE))));
  }
  
  public static boolean isCodeLibelleNomenclature(final Property property) {
    return ((PropertyUtils.getStereotype(property, Utils.MODEL_CODELIBELLENOMENCLATURE) != null) && (!IterableExtensions.isEmpty(PropertyUtils.getStereotype(property, Utils.MODEL_CODELIBELLENOMENCLATURE))));
  }
  
  /**
   * teste si une property est un id
   */
  public static boolean isID(final Property property) {
    return ((PropertyUtils.getStereotype(property, Utils.MODEL_KEYATTRIBUTE) != null) && (!IterableExtensions.isEmpty(PropertyUtils.getStereotype(property, Utils.MODEL_KEYATTRIBUTE))));
  }
  
  /**
   * retourne le nom de la classe possédant la property
   */
  public static String getOwnerName(final Property property) {
    final Element type = property.getOwner();
    if ((type instanceof NamedElement)) {
      return ((NamedElement)type).getName();
    } else {
      return type.getClass().getName();
    }
  }
  
  /**
   * retourne le nom du field id
   */
  public static String getIdFieldName(final Property property, final String additionalName) {
    String name = "";
    if (((!Objects.equal(additionalName, "")) && (additionalName != null))) {
      String _firstToUpperCase = Utils.getFirstToUpperCase(property.getName());
      String _plus = (additionalName + _firstToUpperCase);
      name = _plus;
    } else {
      name = property.getName();
    }
    StringConcatenation _builder = new StringConcatenation();
    String _snakeCase = Utils.toSnakeCase(name);
    _builder.append(_snakeCase);
    _builder.append("_");
    String _firstToLowerCase = Utils.getFirstToLowerCase(PropertyUtils.getOwnerName(property));
    _builder.append(_firstToLowerCase);
    return _builder.toString();
  }
  
  /**
   * teste si l'attribut ne viens pas d'une association
   */
  public static boolean isBasicAttribute(final Property property) {
    boolean _isClassAttribute = PropertyUtils.isClassAttribute(property);
    return (!_isClassAttribute);
  }
  
  /**
   * teste si l'attribut viens d'une association
   */
  public static boolean isClassAttribute(final Property property) {
    Type _type = property.getType();
    return (_type instanceof org.eclipse.uml2.uml.Class);
  }
  
  /**
   * teste si l'attribut viens d'une association
   */
  public static boolean isAssociationAttribute(final Property property) {
    Association _association = property.getAssociation();
    return (_association != null);
  }
  
  /**
   * retourne le nom d'un attribut issu d'un attribut de type valueObject
   */
  public static String getValueObjectPropertyName(final Property property, final Property valueObjectProperty) {
    StringConcatenation _builder = new StringConcatenation();
    String _name = valueObjectProperty.getName();
    _builder.append(_name);
    String _firstToUpperCase = Utils.getFirstToUpperCase(property.getName());
    _builder.append(_firstToUpperCase);
    return _builder.toString();
  }
  
  /**
   * retourne le nom du champs lié a l'identifant de la classe entity associée
   */
  public static String getAssociationEntityIdName(final Property property) {
    final Type type = property.getType();
    if ((type instanceof Classifier)) {
      final String propName = ((Property[])Conversions.unwrapArray(ClassifierUtils.getId(((Classifier)type)), Property.class))[0].getName();
      String _firstToUpperCase = Utils.getFirstToUpperCase(((Classifier)type).getName());
      return (propName + _firstToUpperCase);
    } else {
      return "";
    }
  }
  
  /**
   * retourne le nom du model d'une propriété multivaluée
   */
  public static CharSequence getMultivaluedPropertyModelName(final Property property, final Classifier fromClass) {
    CharSequence _xblockexpression = null;
    {
      final String tableName = Utils.addAdditionnalName(fromClass.getName(), property.getName());
      StringConcatenation _builder = new StringConcatenation();
      String _firstToUpperCase = Utils.getFirstToUpperCase(tableName);
      _builder.append(_firstToUpperCase);
      _builder.append("Model");
      _xblockexpression = _builder;
    }
    return _xblockexpression;
  }
  
  /**
   * retourne le nom du model d'une propriété multivaluée
   */
  public static CharSequence getMultivaluedPropertyDtoName(final Property property, final Classifier fromClass) {
    CharSequence _xblockexpression = null;
    {
      final String tableName = Utils.addAdditionnalName(fromClass.getName(), property.getName());
      StringConcatenation _builder = new StringConcatenation();
      String _firstToUpperCase = Utils.getFirstToUpperCase(tableName);
      _builder.append(_firstToUpperCase);
      _builder.append("DTO");
      _xblockexpression = _builder;
    }
    return _xblockexpression;
  }
  
  /**
   * retourne le nom de l'attribut d'une reference multivaluée
   */
  public static String getReferenceAttributeName(final Property property, final Property id, final Classifier clazz) {
    StringConcatenation _builder = new StringConcatenation();
    String _name = id.getName();
    _builder.append(_name);
    String _firstToUpperCase = Utils.getFirstToUpperCase(property.getName());
    _builder.append(_firstToUpperCase);
    return _builder.toString();
  }
  
  /**
   * teste si un attribut peut être mis a null ou non
   */
  public static boolean isNullable(final Property property) {
    int _lower = property.getLower();
    return (_lower == 0);
  }
  
  /**
   * retourne la valeur par defaut
   */
  public static String getDefaultValue(final Property property) {
    return property.getDefault();
  }
  
  /**
   * retourne la valeur de columnName ou name si l'attribut n'en possède pas (en y ajoutant l'additionalName)
   * le paramètre additionalName ne sera pas mis en forme
   */
  public static String getDatabaseName(final Property property, final String name, final String additionnalName) {
    String retour = Utils.toSnakeCase(name).toUpperCase();
    final String columnName = PropertyUtils.getColumnName(property);
    if (((columnName != null) && (columnName != ""))) {
      retour = ((String) columnName).toUpperCase();
    }
    if (((additionnalName != null) && (additionnalName != ""))) {
      retour = ((additionnalName + "_") + retour);
    }
    return retour;
  }
  
  public static String getName(final Property property, final String name, final String additionnalName) {
    String retour = name;
    final String columnName = PropertyUtils.getColumnName(property);
    if (((columnName != null) && (columnName != ""))) {
      retour = ((String) columnName);
    }
    if (((additionnalName != null) && (additionnalName != ""))) {
      retour = Utils.addAdditionnalName(additionnalName, retour);
    }
    return retour;
  }
  
  public static boolean isOneToManyAttributes(final Property property) {
    if (((property.isMultivalued() && Utils.isEntity(property.getType())) && Utils.isEntity(((Classifier) property.getOwner())))) {
      final Association association = property.getAssociation();
      if ((association != null)) {
        final Function1<Property, Boolean> _function = (Property mem) -> {
          Type _type = mem.getType();
          Element _owner = property.getOwner();
          return Boolean.valueOf(Objects.equal(_type, _owner));
        };
        final Iterable<Property> member = IterableExtensions.<Property>filter(association.getMemberEnds(), _function);
        if ((member != null)) {
          final Property end = ((Property[])Conversions.unwrapArray(member, Property.class))[0];
          boolean _isMultivalued = end.isMultivalued();
          if (_isMultivalued) {
            return false;
          } else {
            return true;
          }
        }
      }
      return false;
    } else {
      return false;
    }
  }
  
  /**
   * retourne la valeur de l'attribut columnName du stéréotype attribut
   */
  public static String getColumnName(final Property property) {
    String name = "";
    if (((PropertyUtils.getStereotype(property, Utils.MODEL_KEYATTRIBUTE) == null) || IterableExtensions.isEmpty(PropertyUtils.getStereotype(property, Utils.MODEL_KEYATTRIBUTE)))) {
      Object _stereotypePropertyValue = Utils.getStereotypePropertyValue(property, Utils.MODEL_ATTRIBUTE, Utils.MODEL_ATTRIBUTE_COLUMNNAME);
      name = ((String) _stereotypePropertyValue);
    } else {
      Object _stereotypePropertyValue_1 = Utils.getStereotypePropertyValue(property, Utils.MODEL_KEYATTRIBUTE, Utils.MODEL_ATTRIBUTE_COLUMNNAME);
      name = ((String) _stereotypePropertyValue_1);
    }
    return name;
  }
}
