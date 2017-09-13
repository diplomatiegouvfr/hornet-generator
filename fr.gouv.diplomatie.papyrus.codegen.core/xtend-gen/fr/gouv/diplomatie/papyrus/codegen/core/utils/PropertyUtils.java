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
  
  public static boolean isNullable(final Property property) {
    int _lower = property.getLower();
    return (_lower == 0);
  }
  
  public static String getDefaultValue(final Property property) {
    return property.getDefault();
  }
}
