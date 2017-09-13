package fr.gouv.diplomatie.papyrus.codegen.core.utils;

import com.google.common.base.Objects;
import com.google.common.collect.Iterables;
import fr.gouv.diplomatie.papyrus.codegen.core.generators.GeneratorUtils;
import fr.gouv.diplomatie.papyrus.codegen.core.utils.ModelUtils;
import fr.gouv.diplomatie.papyrus.codegen.core.utils.PropertyUtils;
import fr.gouv.diplomatie.papyrus.codegen.core.utils.Utils;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import org.eclipse.emf.common.util.EList;
import org.eclipse.uml2.uml.AssociationClass;
import org.eclipse.uml2.uml.AttributeOwner;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Stereotype;
import org.eclipse.uml2.uml.Type;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;
import org.eclipse.xtext.xbase.lib.Conversions;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;

@SuppressWarnings("all")
public class ClassifierUtils {
  /**
   * retourne le nom de la classe modèle liée à la classe
   */
  public static String getModelName(final Classifier clazz) {
    String _name = clazz.getName();
    return (_name + "Model");
  }
  
  /**
   * retourne le nom de l'ainterface attributes liée à la classe
   */
  public static String getAttributesInterfaceName(final Classifier clazz) {
    String _name = clazz.getName();
    return (_name + "Attributes");
  }
  
  /**
   * retourne le nom de la classe métier liée à la classe
   */
  public static String getMetierClassName(final Classifier clazz) {
    String _name = clazz.getName();
    return (_name + "Metier");
  }
  
  /**
   * retourne le nom du dto liée à la classe
   */
  public static String getDtoClassName(final Classifier clazz) {
    String _name = clazz.getName();
    return (_name + "DTO");
  }
  
  /**
   * retourne le nom de l'application
   */
  public static String getApplicationName(final Classifier cl) {
    return Utils.lowerCase(cl.allOwningPackages().get(0).getModel().getName());
  }
  
  /**
   * retourne le nom du domaine
   */
  public static String getDomainName(final Classifier cl) {
    return Utils.lowerCase(cl.allOwningPackages().get(0).getName());
  }
  
  /**
   * retourne le chemin du fichier de la classe Model
   */
  public static String getModelPath(final Classifier clazz) {
    return GeneratorUtils.getModelPath(clazz, Boolean.valueOf(true));
  }
  
  /**
   * retourne le chemin du fichier de l'interface Attributes
   */
  public static String getAttributesInterfacePath(final Classifier clazz) {
    return GeneratorUtils.getAttributesInterfacePath(clazz, Boolean.valueOf(true));
  }
  
  /**
   * retourne le chemin du fichier de la classe metier
   */
  public static String getMetierClassPath(final Classifier clazz) {
    return GeneratorUtils.getMetierClassPath(clazz, Boolean.valueOf(true));
  }
  
  /**
   * retourne le chemin du fichier de la classe dto
   */
  public static String getDtoClassPath(final Classifier clazz) {
    return GeneratorUtils.getDtoClassPath(clazz, Boolean.valueOf(true));
  }
  
  /**
   * retourne le chemin du fichier de la classe enum
   */
  public static String getEnumClassPath(final Classifier clazz) {
    return GeneratorUtils.getEnumPath(clazz, Boolean.valueOf(true));
  }
  
  /**
   * retourne la classe Stereotype
   */
  public static Iterable<Stereotype> getStereotype(final Classifier clazz, final String stereoname) {
    final Function1<Stereotype, Boolean> _function = (Stereotype stereotype) -> {
      String _name = stereotype.getName();
      return Boolean.valueOf(Objects.equal(_name, stereoname));
    };
    return IterableExtensions.<Stereotype>filter(clazz.getAppliedStereotypes(), _function);
  }
  
  /**
   * retourne la valeur de la propriété liée au stereotype
   */
  public static Object getStereotypePropertyValue(final Classifier clazz, final String stereoname, final String property) {
    if (((ClassifierUtils.getStereotype(clazz, stereoname) == null) || IterableExtensions.isEmpty(ClassifierUtils.getStereotype(clazz, stereoname)))) {
      return null;
    }
    final Stereotype stereotype = ((Stereotype[])Conversions.unwrapArray(ClassifierUtils.getStereotype(clazz, stereoname), Stereotype.class))[0];
    if ((stereotype == null)) {
      return null;
    }
    return clazz.getValue(stereotype, property);
  }
  
  /**
   * renvoi les attributs de la classe
   * tableau vide si il y en a aucun
   */
  public static Collection<Property> getOwnedAttributes(final Classifier clazz) {
    Collection<Property> _xblockexpression = null;
    {
      final EList<Property> attributes = ClassifierUtils.getOwnedAttributesWNull(clazz);
      Collection<Property> _xifexpression = null;
      if ((attributes == null)) {
        _xifexpression = CollectionLiterals.<Property>emptySet();
      } else {
        _xifexpression = attributes;
      }
      _xblockexpression = _xifexpression;
    }
    return _xblockexpression;
  }
  
  /**
   * renvoi les attributs de la classe
   * tableau vide si il y en a aucun
   * incluant ceux des interface et classes généralisées
   */
  public static Collection<Property> getAttributes(final Classifier clazz) {
    Collection<Property> _xblockexpression = null;
    {
      final EList<Property> attributes = clazz.getAllAttributes();
      Collection<Property> _xifexpression = null;
      if ((attributes == null)) {
        _xifexpression = CollectionLiterals.<Property>emptySet();
      } else {
        _xifexpression = attributes;
      }
      _xblockexpression = _xifexpression;
    }
    return _xblockexpression;
  }
  
  /**
   * renvoi les attributs de la classe
   * null si il y en a aucun
   */
  public static EList<Property> getOwnedAttributesWNull(final Classifier clazz) {
    EList<Property> _xifexpression = null;
    if ((clazz instanceof AttributeOwner)) {
      _xifexpression = ((AttributeOwner) clazz).getOwnedAttributes();
    } else {
      return null;
    }
    return _xifexpression;
  }
  
  /**
   * test si une classe a plusieurs attributs de type keyattribute
   */
  public static boolean hasMultipleId(final Classifier clazz) {
    final Function1<Property, Boolean> _function = (Property attribut) -> {
      return Boolean.valueOf(PropertyUtils.isID(attribut));
    };
    final Iterable<Property> ids = IterableExtensions.<Property>filter(ClassifierUtils.getOwnedAttributes(clazz), _function);
    int _length = ((Object[])Conversions.unwrapArray(ids, Object.class)).length;
    return (_length > 1);
  }
  
  /**
   * retourne l'identifiant
   */
  public static Iterable<Property> getId(final Classifier clazz) {
    final Function1<Property, Boolean> _function = (Property attribut) -> {
      return Boolean.valueOf(PropertyUtils.isID(attribut));
    };
    final Iterable<Property> ids = IterableExtensions.<Property>filter(ClassifierUtils.getOwnedAttributes(clazz), _function);
    return ids;
  }
  
  /**
   * retourne les attributs multivalués
   */
  public static Iterable<Property> getMultivaluedOwnedAttributes(final Classifier clazz) {
    final Collection<Property> attributes = ClassifierUtils.getOwnedAttributes(clazz);
    final Function1<Property, Boolean> _function = (Property attribut) -> {
      return Boolean.valueOf(attribut.isMultivalued());
    };
    final Iterable<Property> multiAttributes = IterableExtensions.<Property>filter(attributes, _function);
    return multiAttributes;
  }
  
  /**
   * retourne les attributs multivalués
   * en incluant ceux venant des interfaces et des classes généralisées
   */
  public static Iterable<Property> getAllMultivaluedAttributes(final Classifier clazz) {
    final EList<Property> attributes = clazz.getAllAttributes();
    final Function1<Property, Boolean> _function = (Property attribut) -> {
      return Boolean.valueOf(attribut.isMultivalued());
    };
    final Iterable<Property> multiAttributes = IterableExtensions.<Property>filter(attributes, _function);
    return multiAttributes;
  }
  
  /**
   * retourne les attributs non multivalués
   */
  public static Iterable<Property> getNotMultivaluedOwnedAttributes(final Classifier clazz) {
    final Collection<Property> attributes = ClassifierUtils.getOwnedAttributes(clazz);
    final Function1<Property, Boolean> _function = (Property attribut) -> {
      boolean _isMultivalued = attribut.isMultivalued();
      return Boolean.valueOf((!_isMultivalued));
    };
    final Iterable<Property> simpleAttributes = IterableExtensions.<Property>filter(attributes, _function);
    return simpleAttributes;
  }
  
  /**
   * cherche les reference multivaluée de la classe ofClass dans une classe inClass
   */
  public static Iterable<Property> getMultivaluedReferencesToType(final Classifier inClass, final Classifier ofClass) {
    final Function1<Property, Boolean> _function = (Property attribut) -> {
      return Boolean.valueOf((Objects.equal(attribut.getType(), ofClass) && attribut.isMultivalued()));
    };
    final Iterable<Property> attributes = IterableExtensions.<Property>filter(ClassifierUtils.getOwnedAttributes(inClass), _function);
    return attributes;
  }
  
  /**
   * cherche tous les attributs multivalués dans le modele du type de la classe passée en paramètre
   */
  public static ArrayList<Property> getAllReferencesTo(final Classifier ofClass) {
    final Model model = ofClass.getModel();
    final Iterable<Type> classes = ModelUtils.getAllClasses(model);
    final ArrayList<Property> references = CollectionLiterals.<Property>newArrayList();
    for (final Type classe : classes) {
      Iterables.<Property>addAll(references, ClassifierUtils.getMultivaluedReferencesToType(((Classifier) classe), ofClass));
    }
    return references;
  }
  
  /**
   * cherche les classe d'association liée a la classe
   */
  public static Iterable<Type> getLinkedAssociationClass(final Classifier clazz) {
    final Model model = clazz.getModel();
    final Function1<Type, Boolean> _function = (Type type) -> {
      if ((type instanceof AssociationClass)) {
        final EList<Property> members = ((AssociationClass)type).getOwnedEnds();
        boolean isIn = false;
        for (final Property member : members) {
          Type _type = member.getType();
          boolean _equals = Objects.equal(_type, clazz);
          if (_equals) {
            isIn = true;
          }
        }
        return Boolean.valueOf(isIn);
      } else {
        return Boolean.valueOf(false);
      }
    };
    final Iterable<Type> associationsClasses = IterableExtensions.<Type>filter(model.getOwnedTypes(), _function);
    return associationsClasses;
  }
  
  /**
   * test si l'enum a des code ou non
   */
  public static boolean isEnumWithCode(final Classifier clazz) {
    final Collection<Property> valeurs = ClassifierUtils.getOwnedAttributes(clazz);
    final Property value = ((Property[])Conversions.unwrapArray(valeurs, Property.class))[0];
    final Object code = Utils.getStereotypePropertyValue(value, Utils.MODEL_CODELIBELLENOMENCLATURE, Utils.MODEL_CODELIBELLENOMENCLATURE_CODE);
    return ((code != null) && (!Objects.equal(code, "")));
  }
  
  /**
   * teste si une classe doit etre générée ou non
   */
  public static boolean canBeGenerated(final Classifier clazz) {
    boolean _isEntity = Utils.isEntity(clazz);
    if (_isEntity) {
      Object _stereotypePropertyValue = Utils.getStereotypePropertyValue(clazz, Utils.MODEL_ENTITY, Utils.MODEL_ENTITY_GENERATED);
      return (!Objects.equal(_stereotypePropertyValue, Boolean.valueOf(false)));
    } else {
      boolean _isNomenclature = Utils.isNomenclature(clazz);
      if (_isNomenclature) {
        Object _stereotypePropertyValue_1 = Utils.getStereotypePropertyValue(clazz, Utils.MODEL_NOMENCLATURE, Utils.MODEL_NOMENCLATURE_GENERATED);
        return (!Objects.equal(_stereotypePropertyValue_1, Boolean.valueOf(false)));
      } else {
        boolean _isValueObject = Utils.isValueObject(clazz);
        if (_isValueObject) {
          Object _stereotypePropertyValue_2 = Utils.getStereotypePropertyValue(clazz, Utils.MODEL_VALUEOBJECT, Utils.MODEL_VALUEOBJECT_GENERATED);
          return (!Objects.equal(_stereotypePropertyValue_2, Boolean.valueOf(false)));
        }
      }
    }
    return true;
  }
  
  /**
   * retourne la valeur de l'attribut tableName
   */
  public static Object getTableNameValue(final Classifier clazz) {
    boolean _isEntity = Utils.isEntity(clazz);
    if (_isEntity) {
      return Utils.getStereotypePropertyValue(clazz, Utils.MODEL_ENTITY, Utils.MODEL_ENTITY_TABLENAME);
    } else {
      boolean _isNomenclature = Utils.isNomenclature(clazz);
      if (_isNomenclature) {
        return Utils.getStereotypePropertyValue(clazz, Utils.MODEL_NOMENCLATURE, Utils.MODEL_NOMENCLATURE_TABLENAME);
      }
    }
    return null;
  }
  
  /**
   * retourne le nom de la table liée a la classe
   */
  public static String getTableName(final Classifier clazz) {
    final Object name = ClassifierUtils.getTableNameValue(clazz);
    if ((name == null)) {
      return Utils.toSnakeCase(clazz.getName());
    }
    return name.toString();
  }
  
  /**
   * retourne le chemin vers la classe
   */
  public static String getClassPath(final Classifier clazz) {
    final String path = Utils.getPackagePath(clazz);
    final String appName = clazz.getModel().getName();
    String _name = clazz.getPackage().getName();
    final String fileName = (_name + 
      File.separator);
    final String classPath = ((((path + File.separator) + appName) + File.separator) + fileName);
    return classPath;
  }
}
