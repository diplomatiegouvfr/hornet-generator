package fr.gouv.diplomatie.papyrus.codegen.sql.xtend.pakkage;

import com.google.common.base.Objects;
import fr.gouv.diplomatie.papyrus.codegen.core.utils.ClassifierUtils;
import fr.gouv.diplomatie.papyrus.codegen.core.utils.PropertyUtils;
import fr.gouv.diplomatie.papyrus.codegen.core.utils.Utils;
import fr.gouv.diplomatie.papyrus.codegen.sql.utils.TypeUtils;
import java.util.ArrayList;
import java.util.function.Consumer;
import org.eclipse.emf.common.util.EList;
import org.eclipse.uml2.uml.AssociationClass;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Generalization;
import org.eclipse.uml2.uml.Interface;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.PrimitiveType;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Type;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;
import org.eclipse.xtext.xbase.lib.Conversions;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.Functions.Function2;
import org.eclipse.xtext.xbase.lib.IterableExtensions;

@SuppressWarnings("all")
public class PackageDatabaseScriptGenerator {
  public static CharSequence generateCode(final org.eclipse.uml2.uml.Package pakkage) {
    CharSequence _xblockexpression = null;
    {
      final Model model = pakkage.getModel();
      final Function1<Type, Boolean> _function = (Type type) -> {
        return Boolean.valueOf((Utils.isEntity(type) && ClassifierUtils.canBeGenerated(((Classifier) type))));
      };
      final Iterable<Type> classes = IterableExtensions.<Type>filter(model.getOwnedTypes(), _function);
      final Function1<Type, Boolean> _function_1 = (Type type) -> {
        return Boolean.valueOf((Utils.isNomenclature(type) && ClassifierUtils.canBeGenerated(((Classifier) type))));
      };
      final Iterable<Type> enums = IterableExtensions.<Type>filter(model.getOwnedTypes(), _function_1);
      final Function1<Type, Boolean> _function_2 = (Type type) -> {
        return Boolean.valueOf((type instanceof AssociationClass));
      };
      final Iterable<Type> associationsClasses = IterableExtensions.<Type>filter(model.getOwnedTypes(), _function_2);
      StringConcatenation _builder = new StringConcatenation();
      final Function2<String, Type, String> _function_3 = (String acc, Type clazz) -> {
        StringConcatenation _builder_1 = new StringConcatenation();
        CharSequence _generateTable = PackageDatabaseScriptGenerator.generateTable(((Classifier) clazz));
        _builder_1.append(_generateTable);
        return (acc + _builder_1);
      };
      String _fold = IterableExtensions.<Type, String>fold(classes, "", _function_3);
      _builder.append(_fold);
      _builder.newLineIfNotEmpty();
      final Function2<String, Type, String> _function_4 = (String acc, Type clazz) -> {
        StringConcatenation _builder_1 = new StringConcatenation();
        CharSequence _generateEnumTable = PackageDatabaseScriptGenerator.generateEnumTable(((Classifier) clazz));
        _builder_1.append(_generateEnumTable);
        return (acc + _builder_1);
      };
      String _fold_1 = IterableExtensions.<Type, String>fold(enums, "", _function_4);
      _builder.append(_fold_1);
      _builder.newLineIfNotEmpty();
      final Function2<String, Type, String> _function_5 = (String acc, Type clazz) -> {
        StringConcatenation _builder_1 = new StringConcatenation();
        CharSequence _generateAlters = PackageDatabaseScriptGenerator.generateAlters(((Classifier) clazz));
        _builder_1.append(_generateAlters);
        return (acc + _builder_1);
      };
      String _fold_2 = IterableExtensions.<Type, String>fold(classes, "", _function_5);
      _builder.append(_fold_2);
      _builder.newLineIfNotEmpty();
      final Function2<String, Type, String> _function_6 = (String acc, Type clazz) -> {
        StringConcatenation _builder_1 = new StringConcatenation();
        CharSequence _generateAssociationTable = PackageDatabaseScriptGenerator.generateAssociationTable(((AssociationClass) clazz));
        _builder_1.append(_generateAssociationTable);
        return (acc + _builder_1);
      };
      String _fold_3 = IterableExtensions.<Type, String>fold(associationsClasses, "", _function_6);
      _builder.append(_fold_3);
      _builder.newLineIfNotEmpty();
      _xblockexpression = _builder;
    }
    return _xblockexpression;
  }
  
  public static CharSequence generateTable(final Classifier clazz) {
    CharSequence _xblockexpression = null;
    {
      final Function1<Property, Boolean> _function = (Property id) -> {
        return Boolean.valueOf(Utils.isSequence(id));
      };
      final Iterable<Property> attributes = IterableExtensions.<Property>filter(ClassifierUtils.getOwnedAttributes(clazz), _function);
      StringConcatenation _builder = new StringConcatenation();
      _builder.newLine();
      _builder.append("CREATE TABLE ");
      String _tableName = ClassifierUtils.getTableName(clazz);
      _builder.append(_tableName);
      _builder.append("(");
      _builder.newLineIfNotEmpty();
      _builder.append("\t");
      CharSequence _generateExtendsId = PackageDatabaseScriptGenerator.generateExtendsId(clazz);
      _builder.append(_generateExtendsId, "\t");
      _builder.newLineIfNotEmpty();
      _builder.append("\t");
      CharSequence _generateInterfaceAttributes = PackageDatabaseScriptGenerator.generateInterfaceAttributes(clazz, clazz);
      _builder.append(_generateInterfaceAttributes, "\t");
      _builder.newLineIfNotEmpty();
      _builder.append("\t");
      CharSequence _generateAttributes = PackageDatabaseScriptGenerator.generateAttributes(clazz, "", clazz, Boolean.valueOf(false));
      _builder.append(_generateAttributes, "\t");
      _builder.newLineIfNotEmpty();
      _builder.append(");");
      _builder.newLine();
      CharSequence _generateIds = PackageDatabaseScriptGenerator.generateIds(clazz);
      _builder.append(_generateIds);
      _builder.newLineIfNotEmpty();
      _builder.append("\t\t");
      _builder.newLine();
      final Function2<String, Property, String> _function_1 = (String acc, Property id) -> {
        StringConcatenation _builder_1 = new StringConcatenation();
        CharSequence _generateSequence = PackageDatabaseScriptGenerator.generateSequence(id);
        _builder_1.append(_generateSequence);
        return (acc + _builder_1);
      };
      String _fold = IterableExtensions.<Property, String>fold(attributes, "", _function_1);
      _builder.append(_fold);
      _builder.newLineIfNotEmpty();
      _xblockexpression = _builder;
    }
    return _xblockexpression;
  }
  
  /**
   * génère les tables d'attributs multiple et les foreign keys
   */
  public static CharSequence generateAlters(final Classifier clazz) {
    StringConcatenation _builder = new StringConcatenation();
    CharSequence _generateMultivaluedAttributesTable = PackageDatabaseScriptGenerator.generateMultivaluedAttributesTable(clazz, clazz);
    _builder.append(_generateMultivaluedAttributesTable);
    _builder.newLineIfNotEmpty();
    CharSequence _generateForeignKeys = PackageDatabaseScriptGenerator.generateForeignKeys(clazz, "", ClassifierUtils.getTableName(clazz));
    _builder.append(_generateForeignKeys);
    _builder.newLineIfNotEmpty();
    CharSequence _generateExtendsForeignKey = PackageDatabaseScriptGenerator.generateExtendsForeignKey(clazz);
    _builder.append(_generateExtendsForeignKey);
    _builder.newLineIfNotEmpty();
    return _builder;
  }
  
  /**
   * génère dans la classe l'id de la classe étendue
   */
  public static CharSequence generateExtendsId(final Classifier clazz) {
    CharSequence _xblockexpression = null;
    {
      final EList<Generalization> parents = clazz.getGeneralizations();
      CharSequence _xifexpression = null;
      int _length = ((Object[])Conversions.unwrapArray(parents, Object.class)).length;
      boolean _greaterThan = (_length > 0);
      if (_greaterThan) {
        StringConcatenation _builder = new StringConcatenation();
        final Function2<String, Generalization, String> _function = (String acc, Generalization parent) -> {
          StringConcatenation _builder_1 = new StringConcatenation();
          CharSequence _generateExtendId = PackageDatabaseScriptGenerator.generateExtendId(parent.getGeneral());
          _builder_1.append(_generateExtendId);
          return (acc + _builder_1);
        };
        String _fold = IterableExtensions.<Generalization, String>fold(parents, "", _function);
        _builder.append(_fold);
        _xifexpression = _builder;
      } else {
        StringConcatenation _builder_1 = new StringConcatenation();
        _xifexpression = _builder_1;
      }
      _xblockexpression = _xifexpression;
    }
    return _xblockexpression;
  }
  
  public static CharSequence generateExtendId(final Classifier clazz) {
    CharSequence _xblockexpression = null;
    {
      final Iterable<Property> ids = ClassifierUtils.getId(clazz);
      final Property id = ((Property[])Conversions.unwrapArray(ids, Property.class))[0];
      StringConcatenation _builder = new StringConcatenation();
      String _snakeCase = Utils.toSnakeCase(id.getName());
      _builder.append(_snakeCase);
      _builder.append(" ");
      String _generateAttributType = PackageDatabaseScriptGenerator.generateAttributType(id);
      _builder.append(_generateAttributType);
      CharSequence _generateStringLength = PackageDatabaseScriptGenerator.generateStringLength(id);
      _builder.append(_generateStringLength);
      _builder.append(" NOT NULL,");
      _builder.newLineIfNotEmpty();
      _xblockexpression = _builder;
    }
    return _xblockexpression;
  }
  
  public static CharSequence generateInterfaceAttributes(final Classifier clazz, final Classifier fromClass) {
    CharSequence _xblockexpression = null;
    {
      final EList<Interface> interfaces = clazz.directlyRealizedInterfaces();
      CharSequence _xifexpression = null;
      if (((interfaces != null) && (!interfaces.isEmpty()))) {
        StringConcatenation _builder = new StringConcatenation();
        final Function2<String, Interface, String> _function = (String acc, Interface interface_) -> {
          StringConcatenation _builder_1 = new StringConcatenation();
          CharSequence _generateAttributes = PackageDatabaseScriptGenerator.generateAttributes(interface_, "", fromClass, Boolean.valueOf(false));
          _builder_1.append(_generateAttributes);
          return (acc + _builder_1);
        };
        String _fold = IterableExtensions.<Interface, String>fold(interfaces, "", _function);
        _builder.append(_fold);
        _builder.append(",");
        _builder.newLineIfNotEmpty();
        _xifexpression = _builder;
      } else {
        StringConcatenation _builder_1 = new StringConcatenation();
        _xifexpression = _builder_1;
      }
      _xblockexpression = _xifexpression;
    }
    return _xblockexpression;
  }
  
  /**
   * Génère les attributs de la classe
   */
  public static CharSequence generateAttributes(final Classifier clazz, final String additionnalName, final Classifier fromClass, final Boolean nullable) {
    CharSequence _xblockexpression = null;
    {
      final Iterable<Property> attributes = ClassifierUtils.getNotMultivaluedOwnedAttributes(clazz);
      StringConcatenation _builder = new StringConcatenation();
      final Function2<String, Property, String> _function = (String acc, Property attribut) -> {
        String _xifexpression = null;
        boolean _notEquals = (!Objects.equal(acc, ""));
        if (_notEquals) {
          StringConcatenation _builder_1 = new StringConcatenation();
          _builder_1.append(",");
          _builder_1.newLine();
          String _plus = (acc + _builder_1);
          StringConcatenation _builder_2 = new StringConcatenation();
          CharSequence _generateAttributDefinition = PackageDatabaseScriptGenerator.generateAttributDefinition(attribut, additionnalName, fromClass, nullable);
          _builder_2.append(_generateAttributDefinition);
          _xifexpression = (_plus + _builder_2);
        } else {
          StringConcatenation _builder_3 = new StringConcatenation();
          CharSequence _generateAttributDefinition_1 = PackageDatabaseScriptGenerator.generateAttributDefinition(attribut, additionnalName, fromClass, nullable);
          _builder_3.append(_generateAttributDefinition_1);
          _xifexpression = (acc + _builder_3);
        }
        return _xifexpression;
      };
      String _fold = IterableExtensions.<Property, String>fold(attributes, "", _function);
      _builder.append(_fold);
      _xblockexpression = _builder;
    }
    return _xblockexpression;
  }
  
  /**
   * génère un attribut
   */
  public static CharSequence generateAttributDefinition(final Property property, final String additionalName, final Classifier fromClass, final Boolean nullable) {
    CharSequence _xifexpression = null;
    boolean _isID = PropertyUtils.isID(property);
    if (_isID) {
      StringConcatenation _builder = new StringConcatenation();
      CharSequence _generateIdAttributeDefinition = PackageDatabaseScriptGenerator.generateIdAttributeDefinition(property, additionalName);
      _builder.append(_generateIdAttributeDefinition);
      _xifexpression = _builder;
    } else {
      StringConcatenation _builder_1 = new StringConcatenation();
      CharSequence _generateNIdAttributeDefinition = PackageDatabaseScriptGenerator.generateNIdAttributeDefinition(property, additionalName, fromClass, nullable);
      _builder_1.append(_generateNIdAttributeDefinition);
      _xifexpression = _builder_1;
    }
    return _xifexpression;
  }
  
  /**
   * génère la définition d'un attribut id
   */
  public static CharSequence generateIdAttributeDefinition(final Property property, final String additionnalName) {
    CharSequence _xblockexpression = null;
    {
      final String name = Utils.addAdditionnalName(additionnalName, property.getName());
      StringConcatenation _builder = new StringConcatenation();
      String _snakeCase = Utils.toSnakeCase(name);
      _builder.append(_snakeCase);
      _builder.append(" ");
      String _generateAttributType = PackageDatabaseScriptGenerator.generateAttributType(property);
      _builder.append(_generateAttributType);
      CharSequence _generateStringLength = PackageDatabaseScriptGenerator.generateStringLength(property);
      _builder.append(_generateStringLength);
      _builder.append(" NOT NULL");
      _xblockexpression = _builder;
    }
    return _xblockexpression;
  }
  
  public static CharSequence generateStringLength(final Property property) {
    CharSequence _xblockexpression = null;
    {
      final Object length = PropertyUtils.getStereotypePropertyValue(property, Utils.MODEL_ATTRIBUTE, Utils.MODEL_ATTRIBUTE_LENGTH);
      CharSequence _xifexpression = null;
      if (((length != null) && (!Objects.equal(length, Integer.valueOf(0))))) {
        StringConcatenation _builder = new StringConcatenation();
        _builder.append(" ");
        _builder.append("varying(");
        _builder.append(length, " ");
        _builder.append(")");
        _xifexpression = _builder;
      } else {
        StringConcatenation _builder_1 = new StringConcatenation();
        _xifexpression = _builder_1;
      }
      _xblockexpression = _xifexpression;
    }
    return _xblockexpression;
  }
  
  /**
   * génère la définition d'un attribut qui n'est pas un id
   */
  public static CharSequence generateNIdAttributeDefinition(final Property property, final String additionnalName, final Classifier fromClass, final Boolean nullable) {
    CharSequence _xifexpression = null;
    boolean _isClassAttribute = PropertyUtils.isClassAttribute(property);
    if (_isClassAttribute) {
      StringConcatenation _builder = new StringConcatenation();
      CharSequence _generateClassAttributeDefinition = PackageDatabaseScriptGenerator.generateClassAttributeDefinition(property, additionnalName, fromClass, nullable);
      _builder.append(_generateClassAttributeDefinition);
      _xifexpression = _builder;
    } else {
      StringConcatenation _builder_1 = new StringConcatenation();
      CharSequence _generateBasicAttributeDefinition = PackageDatabaseScriptGenerator.generateBasicAttributeDefinition(property, additionnalName, fromClass, nullable);
      _builder_1.append(_generateBasicAttributeDefinition);
      _xifexpression = _builder_1;
    }
    return _xifexpression;
  }
  
  /**
   * génère la définition d'un attribut lié a la classe
   */
  public static CharSequence generateClassAttributeDefinition(final Property property, final String additionnalName, final Classifier fromClass, final Boolean nullable) {
    CharSequence _xifexpression = null;
    boolean _isValueObject = Utils.isValueObject(property.getType());
    if (_isValueObject) {
      StringConcatenation _builder = new StringConcatenation();
      CharSequence _generateValueObjectAttributeDefinition = PackageDatabaseScriptGenerator.generateValueObjectAttributeDefinition(property, additionnalName, fromClass, nullable);
      _builder.append(_generateValueObjectAttributeDefinition);
      _xifexpression = _builder;
    } else {
      CharSequence _xifexpression_1 = null;
      boolean _isNomenclature = Utils.isNomenclature(property.getType());
      if (_isNomenclature) {
        StringConcatenation _builder_1 = new StringConcatenation();
        CharSequence _generateEumAttributesDefinition = PackageDatabaseScriptGenerator.generateEumAttributesDefinition(property, additionnalName, fromClass, nullable);
        _builder_1.append(_generateEumAttributesDefinition);
        _xifexpression_1 = _builder_1;
      } else {
        StringConcatenation _builder_2 = new StringConcatenation();
        CharSequence _generateEntityAttributesDefinition = PackageDatabaseScriptGenerator.generateEntityAttributesDefinition(property, additionnalName, fromClass, nullable);
        _builder_2.append(_generateEntityAttributesDefinition);
        _xifexpression_1 = _builder_2;
      }
      _xifexpression = _xifexpression_1;
    }
    return _xifexpression;
  }
  
  /**
   * génère la définition d'un attribut de type value Object
   */
  public static CharSequence generateValueObjectAttributeDefinition(final Property property, final String additionnalName, final Classifier fromClass, final Boolean nullable) {
    CharSequence _xblockexpression = null;
    {
      final String name = Utils.addAdditionnalName(additionnalName, property.getName());
      final Type type = property.getType();
      CharSequence _xifexpression = null;
      if ((type instanceof Classifier)) {
        StringConcatenation _builder = new StringConcatenation();
        Object _generateAttributes = PackageDatabaseScriptGenerator.generateAttributes(((Classifier)type), name, fromClass, Boolean.valueOf(((nullable).booleanValue() || PropertyUtils.isNullable(property))));
        _builder.append(_generateAttributes);
        return _builder.toString();
      } else {
        StringConcatenation _builder_1 = new StringConcatenation();
        _xifexpression = _builder_1;
      }
      _xblockexpression = _xifexpression;
    }
    return _xblockexpression;
  }
  
  /**
   * génère la définition d'un attribut de type value enum
   */
  public static CharSequence generateEumAttributesDefinition(final Property property, final String additionnalName, final Classifier fromClass, final Boolean nullable) {
    CharSequence _xblockexpression = null;
    {
      String name = Utils.addAdditionnalName(additionnalName, property.getName());
      final Type type = property.getType();
      CharSequence _xifexpression = null;
      if ((type instanceof Classifier)) {
        CharSequence _xblockexpression_1 = null;
        {
          String sqlType = TypeUtils.getEnumType(((Classifier)type));
          StringConcatenation _builder = new StringConcatenation();
          _builder.append("code_");
          String _snakeCase = Utils.toSnakeCase(name);
          _builder.append(_snakeCase);
          _builder.append(" ");
          _builder.append(sqlType);
          _builder.append(" ");
          CharSequence _generateNullable = PackageDatabaseScriptGenerator.generateNullable(property, nullable);
          _builder.append(_generateNullable);
          _xblockexpression_1 = _builder;
        }
        _xifexpression = _xblockexpression_1;
      }
      _xblockexpression = _xifexpression;
    }
    return _xblockexpression;
  }
  
  /**
   * génère la définition d'un attribut basique
   */
  public static CharSequence generateBasicAttributeDefinition(final Property property, final String additionnalName, final Classifier fromClass, final Boolean nullable) {
    CharSequence _xblockexpression = null;
    {
      String name = Utils.addAdditionnalName(additionnalName, property.getName());
      StringConcatenation _builder = new StringConcatenation();
      String _snakeCase = Utils.toSnakeCase(name);
      _builder.append(_snakeCase);
      _builder.append(" ");
      String _generateAttributType = PackageDatabaseScriptGenerator.generateAttributType(property);
      _builder.append(_generateAttributType);
      CharSequence _generateStringLength = PackageDatabaseScriptGenerator.generateStringLength(property);
      _builder.append(_generateStringLength);
      _builder.append(" ");
      CharSequence _generateNullable = PackageDatabaseScriptGenerator.generateNullable(property, nullable);
      _builder.append(_generateNullable);
      _xblockexpression = _builder;
    }
    return _xblockexpression;
  }
  
  /**
   * génère la définition d'un attribut de type entity
   */
  public static CharSequence generateEntityAttributesDefinition(final Property property, final String additonnalName, final Classifier fromClass, final Boolean nullable) {
    CharSequence _xblockexpression = null;
    {
      final Type type = property.getType();
      CharSequence _xifexpression = null;
      if ((type instanceof Classifier)) {
        CharSequence _xblockexpression_1 = null;
        {
          final Iterable<Property> ids = ClassifierUtils.getId(((Classifier)type));
          StringConcatenation _builder = new StringConcatenation();
          final Function2<String, Property, String> _function = (String acc, Property id) -> {
            String _xifexpression_1 = null;
            boolean _notEquals = (!Objects.equal(acc, ""));
            if (_notEquals) {
              StringConcatenation _builder_1 = new StringConcatenation();
              _builder_1.append(",");
              String _generateEntityAttributeDefinition = PackageDatabaseScriptGenerator.generateEntityAttributeDefinition(property, id, additonnalName, fromClass, nullable);
              _builder_1.append(_generateEntityAttributeDefinition);
              _xifexpression_1 = (acc + _builder_1);
            } else {
              StringConcatenation _builder_2 = new StringConcatenation();
              String _generateEntityAttributeDefinition_1 = PackageDatabaseScriptGenerator.generateEntityAttributeDefinition(property, id, additonnalName, fromClass, nullable);
              _builder_2.append(_generateEntityAttributeDefinition_1);
              _xifexpression_1 = (acc + _builder_2);
            }
            return _xifexpression_1;
          };
          String _fold = IterableExtensions.<Property, String>fold(ids, "", _function);
          _builder.append(_fold);
          _xblockexpression_1 = _builder;
        }
        _xifexpression = _xblockexpression_1;
      } else {
        StringConcatenation _builder = new StringConcatenation();
        _xifexpression = _builder;
      }
      _xblockexpression = _xifexpression;
    }
    return _xblockexpression;
  }
  
  /**
   * génère la définition d'un attribut de type entity
   */
  public static String generateEntityAttributeDefinition(final Property property, final Property id, final String additionnalName, final Classifier fromClass, final Boolean nullable) {
    String _addAdditionnalName = Utils.addAdditionnalName(additionnalName, id.getName());
    String _firstToUpperCase = Utils.getFirstToUpperCase(property.getName());
    final String propName = (_addAdditionnalName + _firstToUpperCase);
    StringConcatenation _builder = new StringConcatenation();
    String _snakeCase = Utils.toSnakeCase(propName);
    _builder.append(_snakeCase);
    _builder.append(" ");
    String _generateAttributType = PackageDatabaseScriptGenerator.generateAttributType(id);
    _builder.append(_generateAttributType);
    CharSequence _generateStringLength = PackageDatabaseScriptGenerator.generateStringLength(id);
    _builder.append(_generateStringLength);
    _builder.append(" ");
    CharSequence _generateNullable = PackageDatabaseScriptGenerator.generateNullable(property, nullable);
    _builder.append(_generateNullable);
    return _builder.toString();
  }
  
  /**
   * génère la déclaration des ids
   */
  public static CharSequence generateIds(final Classifier clazz) {
    CharSequence _xblockexpression = null;
    {
      final Iterable<Property> ids = ClassifierUtils.getId(clazz);
      final Function2<String, Property, String> _function = (String acc, Property id) -> {
        String _xifexpression = null;
        boolean _notEquals = (!Objects.equal(acc, ""));
        if (_notEquals) {
          StringConcatenation _builder = new StringConcatenation();
          _builder.append(", ");
          String _snakeCase = Utils.toSnakeCase(id.getName());
          _builder.append(_snakeCase);
          _xifexpression = (acc + _builder);
        } else {
          StringConcatenation _builder_1 = new StringConcatenation();
          String _snakeCase_1 = Utils.toSnakeCase(id.getName());
          _builder_1.append(_snakeCase_1);
          _xifexpression = (acc + _builder_1);
        }
        return _xifexpression;
      };
      final String name = IterableExtensions.<Property, String>fold(ids, "", _function);
      StringConcatenation _builder = new StringConcatenation();
      _builder.newLine();
      _builder.append("ALTER TABLE ONLY ");
      String _tableName = ClassifierUtils.getTableName(clazz);
      _builder.append(_tableName);
      _builder.newLineIfNotEmpty();
      _builder.append("\t");
      _builder.append("ADD CONSTRAINT ");
      String _tableName_1 = ClassifierUtils.getTableName(clazz);
      _builder.append(_tableName_1, "\t");
      _builder.append("_pkey PRIMARY KEY (");
      _builder.append(name, "\t");
      _builder.append(");");
      _builder.newLineIfNotEmpty();
      _xblockexpression = _builder;
    }
    return _xblockexpression;
  }
  
  public static CharSequence generateExtendsForeignKey(final Classifier clazz) {
    CharSequence _xblockexpression = null;
    {
      final EList<Generalization> parents = clazz.getGeneralizations();
      CharSequence _xifexpression = null;
      if (((parents != null) && (!parents.isEmpty()))) {
        StringConcatenation _builder = new StringConcatenation();
        final Function2<String, Generalization, String> _function = (String acc, Generalization parent) -> {
          StringConcatenation _builder_1 = new StringConcatenation();
          CharSequence _generateExtendForeignKey = PackageDatabaseScriptGenerator.generateExtendForeignKey(parent.getGeneral(), clazz);
          _builder_1.append(_generateExtendForeignKey);
          return (acc + _builder_1);
        };
        String _fold = IterableExtensions.<Generalization, String>fold(parents, "", _function);
        _builder.append(_fold);
        _xifexpression = _builder;
      } else {
        StringConcatenation _builder_1 = new StringConcatenation();
        _xifexpression = _builder_1;
      }
      _xblockexpression = _xifexpression;
    }
    return _xblockexpression;
  }
  
  public static CharSequence generateExtendForeignKey(final Classifier clazz, final Classifier fromClass) {
    CharSequence _xblockexpression = null;
    {
      final Iterable<Property> ids = ClassifierUtils.getId(clazz);
      CharSequence _xifexpression = null;
      if (((ids != null) && (!IterableExtensions.isEmpty(ids)))) {
        CharSequence _xblockexpression_1 = null;
        {
          final Function2<String, Property, String> _function = (String acc, Property id) -> {
            String _xifexpression_1 = null;
            boolean _notEquals = (!Objects.equal(acc, ""));
            if (_notEquals) {
              StringConcatenation _builder = new StringConcatenation();
              _builder.append(", ");
              String _snakeCase = Utils.toSnakeCase(id.getName());
              _builder.append(_snakeCase);
              _xifexpression_1 = (acc + _builder);
            } else {
              StringConcatenation _builder_1 = new StringConcatenation();
              String _snakeCase_1 = Utils.toSnakeCase(id.getName());
              _builder_1.append(_snakeCase_1);
              _xifexpression_1 = (acc + _builder_1);
            }
            return _xifexpression_1;
          };
          final String idsName = IterableExtensions.<Property, String>fold(ids, "", _function);
          StringConcatenation _builder = new StringConcatenation();
          _builder.newLine();
          _builder.append("ALTER TABLE ONLY ");
          String _tableName = ClassifierUtils.getTableName(fromClass);
          _builder.append(_tableName);
          _builder.newLineIfNotEmpty();
          _builder.append("    ");
          _builder.append("ADD CONSTRAINT ");
          String _tableName_1 = ClassifierUtils.getTableName(fromClass);
          _builder.append(_tableName_1, "    ");
          _builder.append("_");
          String _snakeCase = Utils.toSnakeCase(clazz.getName());
          _builder.append(_snakeCase, "    ");
          _builder.append("_ids_fkey");
          _builder.newLineIfNotEmpty();
          _builder.append("    ");
          _builder.append("FOREIGN KEY (");
          _builder.append(idsName, "    ");
          _builder.append(") REFERENCES ");
          String _tableName_2 = ClassifierUtils.getTableName(clazz);
          _builder.append(_tableName_2, "    ");
          _builder.append("(");
          _builder.append(idsName, "    ");
          _builder.append(");");
          _builder.newLineIfNotEmpty();
          _xblockexpression_1 = _builder;
        }
        _xifexpression = _xblockexpression_1;
      } else {
        StringConcatenation _builder = new StringConcatenation();
        _xifexpression = _builder;
      }
      _xblockexpression = _xifexpression;
    }
    return _xblockexpression;
  }
  
  /**
   * génère les clés étrangères
   */
  public static CharSequence generateForeignKeys(final Classifier clazz, final String additionnalName, final String tableName) {
    CharSequence _xblockexpression = null;
    {
      final Function1<Property, Boolean> _function = (Property attribut) -> {
        return Boolean.valueOf((((Utils.isEntity(attribut.getType()) && (!attribut.isMultivalued())) || (Utils.isValueObject(attribut.getType()) && (!attribut.isMultivalued()))) || (Utils.isNomenclature(attribut.getType()) && (!attribut.isMultivalued()))));
      };
      final Iterable<Property> attributes = IterableExtensions.<Property>filter(ClassifierUtils.getOwnedAttributes(clazz), _function);
      final EList<Interface> interfaces = clazz.getAllUsedInterfaces();
      StringConcatenation _builder = new StringConcatenation();
      final Function2<String, Property, String> _function_1 = (String acc, Property attribut) -> {
        String _xifexpression = null;
        boolean _isEntity = Utils.isEntity(attribut.getType());
        if (_isEntity) {
          StringConcatenation _builder_1 = new StringConcatenation();
          String _generateForeignKey = PackageDatabaseScriptGenerator.generateForeignKey(attribut, additionnalName, tableName);
          _builder_1.append(_generateForeignKey);
          _xifexpression = (acc + _builder_1);
        } else {
          String _xifexpression_1 = null;
          boolean _isValueObject = Utils.isValueObject(attribut.getType());
          if (_isValueObject) {
            StringConcatenation _builder_2 = new StringConcatenation();
            Type _type = attribut.getType();
            Object _generateForeignKeys = PackageDatabaseScriptGenerator.generateForeignKeys(((Classifier) _type), attribut.getName(), tableName);
            _builder_2.append(_generateForeignKeys);
            _xifexpression_1 = (acc + _builder_2);
          } else {
            String _xifexpression_2 = null;
            boolean _isNomenclature = Utils.isNomenclature(attribut.getType());
            if (_isNomenclature) {
              StringConcatenation _builder_3 = new StringConcatenation();
              CharSequence _generateEnumForeignKey = PackageDatabaseScriptGenerator.generateEnumForeignKey(attribut, additionnalName, tableName);
              _builder_3.append(_generateEnumForeignKey);
              _xifexpression_2 = (acc + _builder_3);
            }
            _xifexpression_1 = _xifexpression_2;
          }
          _xifexpression = _xifexpression_1;
        }
        return _xifexpression;
      };
      String _fold = IterableExtensions.<Property, String>fold(attributes, "", _function_1);
      _builder.append(_fold);
      final Function2<String, Interface, String> _function_2 = (String acc, Interface interface_) -> {
        StringConcatenation _builder_1 = new StringConcatenation();
        Object _generateForeignKeys = PackageDatabaseScriptGenerator.generateForeignKeys(interface_, additionnalName, tableName);
        _builder_1.append(_generateForeignKeys);
        return (acc + _builder_1);
      };
      String _fold_1 = IterableExtensions.<Interface, String>fold(interfaces, "", _function_2);
      _builder.append(_fold_1);
      _xblockexpression = _builder;
    }
    return _xblockexpression;
  }
  
  /**
   * génère une clé étrangère
   */
  public static String generateForeignKey(final Property property, final String additionnalName, final String tableName) {
    final Element fromClass = property.getOwner();
    final Type toClass = property.getType();
    if ((fromClass instanceof Classifier)) {
      if ((toClass instanceof Classifier)) {
        final String propName = Utils.addAdditionnalName(additionnalName, property.getName());
        final Iterable<Property> ids = ClassifierUtils.getId(((Classifier)toClass));
        final Function2<String, Property, String> _function = (String acc, Property field) -> {
          String _xblockexpression = null;
          {
            String _addAdditionnalName = Utils.addAdditionnalName(additionnalName, field.getName());
            String _firstToUpperCase = Utils.getFirstToUpperCase(property.getName());
            final String name = (_addAdditionnalName + _firstToUpperCase);
            String _xifexpression = null;
            boolean _notEquals = (!Objects.equal(acc, ""));
            if (_notEquals) {
              StringConcatenation _builder = new StringConcatenation();
              _builder.append(", ");
              String _snakeCase = Utils.toSnakeCase(name);
              _builder.append(_snakeCase);
              _xifexpression = (acc + _builder);
            } else {
              StringConcatenation _builder_1 = new StringConcatenation();
              String _snakeCase_1 = Utils.toSnakeCase(name);
              _builder_1.append(_snakeCase_1);
              _xifexpression = (acc + _builder_1);
            }
            _xblockexpression = _xifexpression;
          }
          return _xblockexpression;
        };
        String fieldToClass = IterableExtensions.<Property, String>fold(ids, "", _function);
        final Function2<String, Property, String> _function_1 = (String acc, Property field) -> {
          String _xblockexpression = null;
          {
            final String name = field.getName();
            String _xifexpression = null;
            boolean _notEquals = (!Objects.equal(acc, ""));
            if (_notEquals) {
              StringConcatenation _builder = new StringConcatenation();
              _builder.append(", ");
              String _snakeCase = Utils.toSnakeCase(name);
              _builder.append(_snakeCase);
              _xifexpression = (acc + _builder);
            } else {
              StringConcatenation _builder_1 = new StringConcatenation();
              String _snakeCase_1 = Utils.toSnakeCase(name);
              _builder_1.append(_snakeCase_1);
              _xifexpression = (acc + _builder_1);
            }
            _xblockexpression = _xifexpression;
          }
          return _xblockexpression;
        };
        String fieldInClass = IterableExtensions.<Property, String>fold(ids, "", _function_1);
        StringConcatenation _builder = new StringConcatenation();
        _builder.newLine();
        _builder.append("ALTER TABLE ONLY ");
        String _snakeCase = Utils.toSnakeCase(tableName);
        _builder.append(_snakeCase);
        _builder.newLineIfNotEmpty();
        _builder.append("    ");
        _builder.append("ADD CONSTRAINT ");
        String _snakeCase_1 = Utils.toSnakeCase(tableName);
        _builder.append(_snakeCase_1, "    ");
        _builder.append("_");
        String _snakeCase_2 = Utils.toSnakeCase(propName);
        _builder.append(_snakeCase_2, "    ");
        _builder.append("_ids_fkey ");
        _builder.newLineIfNotEmpty();
        _builder.append("    ");
        _builder.append("FOREIGN KEY (");
        _builder.append(fieldToClass, "    ");
        _builder.append(") REFERENCES ");
        String _tableName = ClassifierUtils.getTableName(((Classifier)toClass));
        _builder.append(_tableName, "    ");
        _builder.append("(");
        _builder.append(fieldInClass, "    ");
        _builder.append(");");
        _builder.newLineIfNotEmpty();
        return _builder.toString();
      }
    }
    StringConcatenation _builder_1 = new StringConcatenation();
    return _builder_1.toString();
  }
  
  /**
   * génère une clé étrangère liée à un attribut de type enum
   */
  public static CharSequence generateEnumForeignKey(final Property property, final String additionnalName, final String tableName) {
    CharSequence _xblockexpression = null;
    {
      final Type type = property.getType();
      final String propName = Utils.addAdditionnalName(additionnalName, property.getName());
      StringConcatenation _builder = new StringConcatenation();
      _builder.newLine();
      _builder.append("ALTER TABLE ONLY ");
      String _snakeCase = Utils.toSnakeCase(tableName);
      _builder.append(_snakeCase);
      _builder.newLineIfNotEmpty();
      _builder.append("    ");
      _builder.append("ADD CONSTRAINT ");
      String _snakeCase_1 = Utils.toSnakeCase(tableName);
      _builder.append(_snakeCase_1, "    ");
      _builder.append("_");
      String _snakeCase_2 = Utils.toSnakeCase(propName);
      _builder.append(_snakeCase_2, "    ");
      _builder.append("_code_fkey ");
      _builder.newLineIfNotEmpty();
      _builder.append("    ");
      _builder.append("FOREIGN KEY (code_");
      String _snakeCase_3 = Utils.toSnakeCase(propName);
      _builder.append(_snakeCase_3, "    ");
      _builder.append(") REFERENCES ");
      String _tableName = ClassifierUtils.getTableName(((Classifier) type));
      _builder.append(_tableName, "    ");
      _builder.append("(code);");
      _builder.newLineIfNotEmpty();
      _xblockexpression = _builder;
    }
    return _xblockexpression;
  }
  
  public static CharSequence generateMultivaluedAttributesTable(final Classifier clazz, final Classifier fromClass) {
    CharSequence _xblockexpression = null;
    {
      final Iterable<Property> attributes = ClassifierUtils.getMultivaluedOwnedAttributes(clazz);
      final EList<Interface> interfaces = clazz.directlyRealizedInterfaces();
      StringConcatenation _builder = new StringConcatenation();
      final Function2<String, Property, String> _function = (String acc, Property attribut) -> {
        StringConcatenation _builder_1 = new StringConcatenation();
        CharSequence _generateMultivaluedAttributeTable = PackageDatabaseScriptGenerator.generateMultivaluedAttributeTable(attribut, fromClass);
        _builder_1.append(_generateMultivaluedAttributeTable);
        return (acc + _builder_1);
      };
      String _fold = IterableExtensions.<Property, String>fold(attributes, "", _function);
      _builder.append(_fold);
      final Function2<String, Interface, String> _function_1 = (String acc, Interface interface_) -> {
        StringConcatenation _builder_1 = new StringConcatenation();
        CharSequence _generateInterfaceMultivaluedAttributesTables = PackageDatabaseScriptGenerator.generateInterfaceMultivaluedAttributesTables(interface_, fromClass);
        _builder_1.append(_generateInterfaceMultivaluedAttributesTables);
        return (acc + _builder_1);
      };
      String _fold_1 = IterableExtensions.<Interface, String>fold(interfaces, "", _function_1);
      _builder.append(_fold_1);
      _xblockexpression = _builder;
    }
    return _xblockexpression;
  }
  
  public static CharSequence generateInterfaceMultivaluedAttributesTables(final Interface interf, final Classifier fromClass) {
    CharSequence _xblockexpression = null;
    {
      final Function1<Property, Boolean> _function = (Property attribut) -> {
        return Boolean.valueOf(attribut.isMultivalued());
      };
      final Iterable<Property> attributes = IterableExtensions.<Property>filter(ClassifierUtils.getOwnedAttributes(interf), _function);
      StringConcatenation _builder = new StringConcatenation();
      final Function2<String, Property, String> _function_1 = (String acc, Property attribut) -> {
        StringConcatenation _builder_1 = new StringConcatenation();
        CharSequence _generateMultivaluedAttributeTable = PackageDatabaseScriptGenerator.generateMultivaluedAttributeTable(attribut, fromClass);
        _builder_1.append(_generateMultivaluedAttributeTable);
        return (acc + _builder_1);
      };
      String _fold = IterableExtensions.<Property, String>fold(attributes, "", _function_1);
      _builder.append(_fold);
      _xblockexpression = _builder;
    }
    return _xblockexpression;
  }
  
  public static CharSequence generateMultivaluedAttributeTable(final Property property, final Classifier fromClass) {
    CharSequence _xblockexpression = null;
    {
      final Type type = property.getType();
      CharSequence _xifexpression = null;
      if ((type instanceof PrimitiveType)) {
        StringConcatenation _builder = new StringConcatenation();
        CharSequence _generateMutilvaluedPTTable = PackageDatabaseScriptGenerator.generateMutilvaluedPTTable(property, fromClass);
        _builder.append(_generateMutilvaluedPTTable);
        _xifexpression = _builder;
      } else {
        CharSequence _xifexpression_1 = null;
        boolean _isEntity = Utils.isEntity(type);
        if (_isEntity) {
          StringConcatenation _builder_1 = new StringConcatenation();
          CharSequence _generateMutilvaluedEntityTable = PackageDatabaseScriptGenerator.generateMutilvaluedEntityTable(property, fromClass);
          _builder_1.append(_generateMutilvaluedEntityTable);
          _xifexpression_1 = _builder_1;
        } else {
          CharSequence _xifexpression_2 = null;
          boolean _isValueObject = Utils.isValueObject(type);
          if (_isValueObject) {
            StringConcatenation _builder_2 = new StringConcatenation();
            CharSequence _generateValueObjectEntityTable = PackageDatabaseScriptGenerator.generateValueObjectEntityTable(property, fromClass);
            _builder_2.append(_generateValueObjectEntityTable);
            _xifexpression_2 = _builder_2;
          } else {
            CharSequence _xifexpression_3 = null;
            boolean _isNomenclature = Utils.isNomenclature(type);
            if (_isNomenclature) {
              StringConcatenation _builder_3 = new StringConcatenation();
              CharSequence _generateEnumAttributTable = PackageDatabaseScriptGenerator.generateEnumAttributTable(property, fromClass);
              _builder_3.append(_generateEnumAttributTable);
              _xifexpression_3 = _builder_3;
            } else {
              StringConcatenation _builder_4 = new StringConcatenation();
              _xifexpression_3 = _builder_4;
            }
            _xifexpression_2 = _xifexpression_3;
          }
          _xifexpression_1 = _xifexpression_2;
        }
        _xifexpression = _xifexpression_1;
      }
      _xblockexpression = _xifexpression;
    }
    return _xblockexpression;
  }
  
  public static CharSequence generateMutilvaluedPTTable(final Property property, final Classifier fromClass) {
    CharSequence _xblockexpression = null;
    {
      String _tableName = ClassifierUtils.getTableName(fromClass);
      String _firstToUpperCase = Utils.getFirstToUpperCase(property.getName());
      final String tableName = (_tableName + _firstToUpperCase);
      final Iterable<Property> ids = ClassifierUtils.getId(fromClass);
      final Function2<String, Property, String> _function = (String acc, Property id) -> {
        String _xifexpression = null;
        boolean _notEquals = (!Objects.equal(acc, ""));
        if (_notEquals) {
          StringConcatenation _builder = new StringConcatenation();
          _builder.append(", ");
          String _snakeCase = Utils.toSnakeCase(id.getName());
          _builder.append(_snakeCase);
          _xifexpression = (acc + _builder);
        } else {
          StringConcatenation _builder_1 = new StringConcatenation();
          String _snakeCase_1 = Utils.toSnakeCase(id.getName());
          _builder_1.append(_snakeCase_1);
          _xifexpression = (acc + _builder_1);
        }
        return _xifexpression;
      };
      final String idsName = IterableExtensions.<Property, String>fold(ids, "", _function);
      StringConcatenation _builder = new StringConcatenation();
      _builder.newLine();
      _builder.append("CREATE TABLE ");
      String _snakeCase = Utils.toSnakeCase(tableName);
      _builder.append(_snakeCase);
      _builder.append("(");
      _builder.newLineIfNotEmpty();
      _builder.append("\t");
      String _snakeCase_1 = Utils.toSnakeCase(property.getName());
      _builder.append(_snakeCase_1, "\t");
      _builder.append(" ");
      String _generateAttributType = PackageDatabaseScriptGenerator.generateAttributType(property);
      _builder.append(_generateAttributType, "\t");
      CharSequence _generateStringLength = PackageDatabaseScriptGenerator.generateStringLength(property);
      _builder.append(_generateStringLength, "\t");
      _builder.append(" NOT NULL,");
      _builder.newLineIfNotEmpty();
      _builder.append("\t");
      final Function2<String, Property, String> _function_1 = (String acc, Property id) -> {
        String _xifexpression = null;
        boolean _notEquals = (!Objects.equal(acc, ""));
        if (_notEquals) {
          StringConcatenation _builder_1 = new StringConcatenation();
          _builder_1.append(",");
          _builder_1.newLine();
          String _plus = (acc + _builder_1);
          StringConcatenation _builder_2 = new StringConcatenation();
          CharSequence _generateIdAttributeDefinition = PackageDatabaseScriptGenerator.generateIdAttributeDefinition(id, "");
          _builder_2.append(_generateIdAttributeDefinition);
          _xifexpression = (_plus + _builder_2);
        } else {
          StringConcatenation _builder_3 = new StringConcatenation();
          CharSequence _generateIdAttributeDefinition_1 = PackageDatabaseScriptGenerator.generateIdAttributeDefinition(id, "");
          _builder_3.append(_generateIdAttributeDefinition_1);
          _xifexpression = (acc + _builder_3);
        }
        return _xifexpression;
      };
      String _fold = IterableExtensions.<Property, String>fold(ids, "", _function_1);
      _builder.append(_fold, "\t");
      _builder.newLineIfNotEmpty();
      _builder.append(");");
      _builder.newLine();
      _builder.newLine();
      _builder.append("ALTER TABLE ONLY ");
      String _snakeCase_2 = Utils.toSnakeCase(tableName);
      _builder.append(_snakeCase_2);
      _builder.newLineIfNotEmpty();
      _builder.append("    ");
      _builder.append("ADD CONSTRAINT ");
      String _snakeCase_3 = Utils.toSnakeCase(tableName);
      _builder.append(_snakeCase_3, "    ");
      _builder.append("_ids_fkey");
      _builder.newLineIfNotEmpty();
      _builder.append("    ");
      _builder.append("FOREIGN KEY (");
      _builder.append(idsName, "    ");
      _builder.append(") REFERENCES ");
      String _tableName_1 = ClassifierUtils.getTableName(fromClass);
      _builder.append(_tableName_1, "    ");
      _builder.append("(");
      _builder.append(idsName, "    ");
      _builder.append(");");
      _builder.newLineIfNotEmpty();
      _builder.append("    ");
      _builder.newLine();
      _builder.append("ALTER TABLE ONLY ");
      String _snakeCase_4 = Utils.toSnakeCase(tableName);
      _builder.append(_snakeCase_4);
      _builder.newLineIfNotEmpty();
      _builder.append("    ");
      _builder.append("ADD CONSTRAINT ");
      String _snakeCase_5 = Utils.toSnakeCase(tableName);
      _builder.append(_snakeCase_5, "    ");
      _builder.append("_pkey PRIMARY KEY(");
      _builder.append(idsName, "    ");
      _builder.append(", ");
      String _snakeCase_6 = Utils.toSnakeCase(property.getName());
      _builder.append(_snakeCase_6, "    ");
      _builder.append(");");
      _builder.newLineIfNotEmpty();
      _xblockexpression = _builder;
    }
    return _xblockexpression;
  }
  
  /**
   * génère une table d'association pour un attribut de type entity multivalué
   */
  public static CharSequence generateMutilvaluedEntityTable(final Property property, final Classifier fromClass) {
    CharSequence _xblockexpression = null;
    {
      final Type type = property.getType();
      CharSequence _xifexpression = null;
      if ((type instanceof Classifier)) {
        CharSequence _xblockexpression_1 = null;
        {
          String _tableName = ClassifierUtils.getTableName(fromClass);
          String _firstToUpperCase = Utils.getFirstToUpperCase(property.getName());
          final String tableName = (_tableName + _firstToUpperCase);
          final Iterable<Property> idsProps = ClassifierUtils.getId(((Classifier)type));
          final Iterable<Property> idsOwner = ClassifierUtils.getId(fromClass);
          final Function2<String, Property, String> _function = (String acc, Property id) -> {
            String _xifexpression_1 = null;
            boolean _notEquals = (!Objects.equal(acc, ""));
            if (_notEquals) {
              StringConcatenation _builder = new StringConcatenation();
              _builder.append(", ");
              String _snakeCase = Utils.toSnakeCase(id.getName());
              _builder.append(_snakeCase);
              _builder.append("_");
              String _snakeCase_1 = Utils.toSnakeCase(fromClass.getName());
              _builder.append(_snakeCase_1);
              _xifexpression_1 = (acc + _builder);
            } else {
              StringConcatenation _builder_1 = new StringConcatenation();
              String _snakeCase_2 = Utils.toSnakeCase(id.getName());
              _builder_1.append(_snakeCase_2);
              _builder_1.append("_");
              String _snakeCase_3 = Utils.toSnakeCase(fromClass.getName());
              _builder_1.append(_snakeCase_3);
              _xifexpression_1 = (acc + _builder_1);
            }
            return _xifexpression_1;
          };
          final String idsOwnerName = IterableExtensions.<Property, String>fold(idsOwner, "", _function);
          final Function2<String, Property, String> _function_1 = (String acc, Property id) -> {
            String _xifexpression_1 = null;
            boolean _notEquals = (!Objects.equal(acc, ""));
            if (_notEquals) {
              StringConcatenation _builder = new StringConcatenation();
              _builder.append(", ");
              String _snakeCase = Utils.toSnakeCase(id.getName());
              _builder.append(_snakeCase);
              _xifexpression_1 = (acc + _builder);
            } else {
              StringConcatenation _builder_1 = new StringConcatenation();
              String _snakeCase_1 = Utils.toSnakeCase(id.getName());
              _builder_1.append(_snakeCase_1);
              _xifexpression_1 = (acc + _builder_1);
            }
            return _xifexpression_1;
          };
          final String idsOwnerBaseName = IterableExtensions.<Property, String>fold(idsOwner, "", _function_1);
          final Function2<String, Property, String> _function_2 = (String acc, Property id) -> {
            String _xifexpression_1 = null;
            boolean _notEquals = (!Objects.equal(acc, ""));
            if (_notEquals) {
              StringConcatenation _builder = new StringConcatenation();
              _builder.append(", ");
              String _snakeCase = Utils.toSnakeCase(id.getName());
              _builder.append(_snakeCase);
              _builder.append("_");
              String _snakeCase_1 = Utils.toSnakeCase(((Classifier)type).getName());
              _builder.append(_snakeCase_1);
              _xifexpression_1 = (acc + _builder);
            } else {
              StringConcatenation _builder_1 = new StringConcatenation();
              String _snakeCase_2 = Utils.toSnakeCase(id.getName());
              _builder_1.append(_snakeCase_2);
              _builder_1.append("_");
              String _snakeCase_3 = Utils.toSnakeCase(((Classifier)type).getName());
              _builder_1.append(_snakeCase_3);
              _xifexpression_1 = (acc + _builder_1);
            }
            return _xifexpression_1;
          };
          final String idsPropsName = IterableExtensions.<Property, String>fold(idsProps, "", _function_2);
          final Function2<String, Property, String> _function_3 = (String acc, Property id) -> {
            String _xifexpression_1 = null;
            boolean _notEquals = (!Objects.equal(acc, ""));
            if (_notEquals) {
              StringConcatenation _builder = new StringConcatenation();
              _builder.append(", ");
              String _snakeCase = Utils.toSnakeCase(id.getName());
              _builder.append(_snakeCase);
              _xifexpression_1 = (acc + _builder);
            } else {
              StringConcatenation _builder_1 = new StringConcatenation();
              String _snakeCase_1 = Utils.toSnakeCase(id.getName());
              _builder_1.append(_snakeCase_1);
              _xifexpression_1 = (acc + _builder_1);
            }
            return _xifexpression_1;
          };
          final String idsPropsBaseName = IterableExtensions.<Property, String>fold(idsProps, "", _function_3);
          StringConcatenation _builder = new StringConcatenation();
          _builder.newLine();
          _builder.append("CREATE TABLE ");
          String _snakeCase = Utils.toSnakeCase(tableName);
          _builder.append(_snakeCase);
          _builder.append("(");
          _builder.newLineIfNotEmpty();
          _builder.append("\t");
          final Function2<String, Property, String> _function_4 = (String acc, Property id) -> {
            String _xifexpression_1 = null;
            boolean _notEquals = (!Objects.equal(acc, ""));
            if (_notEquals) {
              StringConcatenation _builder_1 = new StringConcatenation();
              _builder_1.append(",");
              _builder_1.newLine();
              String _plus = (acc + _builder_1);
              StringConcatenation _builder_2 = new StringConcatenation();
              CharSequence _generateMultiIdAttributeDefinition = PackageDatabaseScriptGenerator.generateMultiIdAttributeDefinition(id, "");
              _builder_2.append(_generateMultiIdAttributeDefinition);
              _xifexpression_1 = (_plus + _builder_2);
            } else {
              StringConcatenation _builder_3 = new StringConcatenation();
              CharSequence _generateMultiIdAttributeDefinition_1 = PackageDatabaseScriptGenerator.generateMultiIdAttributeDefinition(id, "");
              _builder_3.append(_generateMultiIdAttributeDefinition_1);
              _xifexpression_1 = (acc + _builder_3);
            }
            return _xifexpression_1;
          };
          String _fold = IterableExtensions.<Property, String>fold(idsProps, "", _function_4);
          _builder.append(_fold, "\t");
          _builder.append(",");
          _builder.newLineIfNotEmpty();
          _builder.append("\t");
          final Function2<String, Property, String> _function_5 = (String acc, Property id) -> {
            String _xifexpression_1 = null;
            boolean _notEquals = (!Objects.equal(acc, ""));
            if (_notEquals) {
              StringConcatenation _builder_1 = new StringConcatenation();
              _builder_1.append(",");
              _builder_1.newLine();
              String _plus = (acc + _builder_1);
              StringConcatenation _builder_2 = new StringConcatenation();
              CharSequence _generateMultiIdAttributeDefinition = PackageDatabaseScriptGenerator.generateMultiIdAttributeDefinition(id, "");
              _builder_2.append(_generateMultiIdAttributeDefinition);
              _xifexpression_1 = (_plus + _builder_2);
            } else {
              StringConcatenation _builder_3 = new StringConcatenation();
              CharSequence _generateMultiIdAttributeDefinition_1 = PackageDatabaseScriptGenerator.generateMultiIdAttributeDefinition(id, "");
              _builder_3.append(_generateMultiIdAttributeDefinition_1);
              _xifexpression_1 = (acc + _builder_3);
            }
            return _xifexpression_1;
          };
          String _fold_1 = IterableExtensions.<Property, String>fold(idsOwner, "", _function_5);
          _builder.append(_fold_1, "\t");
          _builder.newLineIfNotEmpty();
          _builder.append(");");
          _builder.newLine();
          _builder.newLine();
          _builder.append("ALTER TABLE ONLY ");
          String _snakeCase_1 = Utils.toSnakeCase(tableName);
          _builder.append(_snakeCase_1);
          _builder.newLineIfNotEmpty();
          _builder.append("    ");
          _builder.append("ADD CONSTRAINT ");
          String _snakeCase_2 = Utils.toSnakeCase(tableName);
          _builder.append(_snakeCase_2, "    ");
          _builder.append("_");
          String _snakeCase_3 = Utils.toSnakeCase(fromClass.getName());
          _builder.append(_snakeCase_3, "    ");
          _builder.append("_ids_fkey");
          _builder.newLineIfNotEmpty();
          _builder.append("    ");
          _builder.append("FOREIGN KEY (");
          _builder.append(idsOwnerName, "    ");
          _builder.append(") REFERENCES ");
          String _tableName_1 = ClassifierUtils.getTableName(fromClass);
          _builder.append(_tableName_1, "    ");
          _builder.append("(");
          _builder.append(idsOwnerBaseName, "    ");
          _builder.append(");");
          _builder.newLineIfNotEmpty();
          _builder.append("    ");
          _builder.newLine();
          _builder.append("ALTER TABLE ONLY ");
          String _snakeCase_4 = Utils.toSnakeCase(tableName);
          _builder.append(_snakeCase_4);
          _builder.newLineIfNotEmpty();
          _builder.append("    ");
          _builder.append("ADD CONSTRAINT ");
          String _snakeCase_5 = Utils.toSnakeCase(tableName);
          _builder.append(_snakeCase_5, "    ");
          _builder.append("_");
          String _snakeCase_6 = Utils.toSnakeCase(((Classifier)type).getName());
          _builder.append(_snakeCase_6, "    ");
          _builder.append("_ids_fkey");
          _builder.newLineIfNotEmpty();
          _builder.append("    ");
          _builder.append("FOREIGN KEY (");
          _builder.append(idsPropsName, "    ");
          _builder.append(") REFERENCES ");
          String _tableName_2 = ClassifierUtils.getTableName(((Classifier)type));
          _builder.append(_tableName_2, "    ");
          _builder.append("(");
          _builder.append(idsPropsBaseName, "    ");
          _builder.append(");");
          _builder.newLineIfNotEmpty();
          _builder.append("    ");
          _builder.newLine();
          _builder.append("ALTER TABLE ONLY ");
          String _snakeCase_7 = Utils.toSnakeCase(tableName);
          _builder.append(_snakeCase_7);
          _builder.newLineIfNotEmpty();
          _builder.append("    ");
          _builder.append("ADD CONSTRAINT ");
          String _snakeCase_8 = Utils.toSnakeCase(tableName);
          _builder.append(_snakeCase_8, "    ");
          _builder.append("_pkey PRIMARY KEY(");
          _builder.append(idsOwnerName, "    ");
          _builder.append(", ");
          _builder.append(idsPropsName, "    ");
          _builder.append(");");
          _builder.newLineIfNotEmpty();
          _xblockexpression_1 = _builder;
        }
        _xifexpression = _xblockexpression_1;
      }
      _xblockexpression = _xifexpression;
    }
    return _xblockexpression;
  }
  
  /**
   * génère la définition d'un attribut id
   */
  public static CharSequence generateMultiIdAttributeDefinition(final Property property, final String additionnalName) {
    CharSequence _xblockexpression = null;
    {
      final String name = Utils.addAdditionnalName(additionnalName, property.getName());
      final Element owner = property.getOwner();
      CharSequence _xifexpression = null;
      if ((owner instanceof Classifier)) {
        StringConcatenation _builder = new StringConcatenation();
        String _snakeCase = Utils.toSnakeCase(name);
        _builder.append(_snakeCase);
        _builder.append("_");
        String _snakeCase_1 = Utils.toSnakeCase(((Classifier)owner).getName());
        _builder.append(_snakeCase_1);
        _builder.append(" ");
        String _generateAttributType = PackageDatabaseScriptGenerator.generateAttributType(property);
        _builder.append(_generateAttributType);
        CharSequence _generateStringLength = PackageDatabaseScriptGenerator.generateStringLength(property);
        _builder.append(_generateStringLength);
        _builder.append(" NOT NULL");
        _xifexpression = _builder;
      }
      _xblockexpression = _xifexpression;
    }
    return _xblockexpression;
  }
  
  public static CharSequence generateValueObjectEntityTable(final Property property, final Classifier fromClass) {
    CharSequence _xblockexpression = null;
    {
      final Type type = property.getType();
      CharSequence _xifexpression = null;
      if ((type instanceof Classifier)) {
        CharSequence _xblockexpression_1 = null;
        {
          String _tableName = ClassifierUtils.getTableName(fromClass);
          String _firstToUpperCase = Utils.getFirstToUpperCase(property.getName());
          final String tableName = (_tableName + _firstToUpperCase);
          final Iterable<Property> idsOwner = ClassifierUtils.getId(fromClass);
          final Function2<String, Property, String> _function = (String acc, Property id) -> {
            String _xifexpression_1 = null;
            boolean _notEquals = (!Objects.equal(acc, ""));
            if (_notEquals) {
              StringConcatenation _builder = new StringConcatenation();
              _builder.append(", ");
              String _snakeCase = Utils.toSnakeCase(id.getName());
              _builder.append(_snakeCase);
              _xifexpression_1 = (acc + _builder);
            } else {
              StringConcatenation _builder_1 = new StringConcatenation();
              String _snakeCase_1 = Utils.toSnakeCase(id.getName());
              _builder_1.append(_snakeCase_1);
              _xifexpression_1 = (acc + _builder_1);
            }
            return _xifexpression_1;
          };
          final String idsName = IterableExtensions.<Property, String>fold(idsOwner, "", _function);
          String _listComma = Utils.getListComma(PackageDatabaseScriptGenerator.getAttributList(((Classifier)type), CollectionLiterals.<String>newArrayList(), property.getName()));
          final String pkeys = ((idsName + ", ") + _listComma);
          StringConcatenation _builder = new StringConcatenation();
          _builder.newLine();
          _builder.append("CREATE TABLE ");
          String _snakeCase = Utils.toSnakeCase(tableName);
          _builder.append(_snakeCase);
          _builder.append("(");
          _builder.newLineIfNotEmpty();
          _builder.append("\t");
          CharSequence _generateAttributes = PackageDatabaseScriptGenerator.generateAttributes(((Classifier)type), property.getName(), fromClass, Boolean.valueOf(false));
          _builder.append(_generateAttributes, "\t");
          _builder.append(",");
          _builder.newLineIfNotEmpty();
          _builder.append("\t");
          final Function2<String, Property, String> _function_1 = (String acc, Property id) -> {
            String _xifexpression_1 = null;
            boolean _notEquals = (!Objects.equal(acc, ""));
            if (_notEquals) {
              StringConcatenation _builder_1 = new StringConcatenation();
              _builder_1.append(",");
              _builder_1.newLine();
              String _plus = (acc + _builder_1);
              StringConcatenation _builder_2 = new StringConcatenation();
              CharSequence _generateIdAttributeDefinition = PackageDatabaseScriptGenerator.generateIdAttributeDefinition(id, "");
              _builder_2.append(_generateIdAttributeDefinition);
              _xifexpression_1 = (_plus + _builder_2);
            } else {
              StringConcatenation _builder_3 = new StringConcatenation();
              CharSequence _generateIdAttributeDefinition_1 = PackageDatabaseScriptGenerator.generateIdAttributeDefinition(id, "");
              _builder_3.append(_generateIdAttributeDefinition_1);
              _xifexpression_1 = (acc + _builder_3);
            }
            return _xifexpression_1;
          };
          String _fold = IterableExtensions.<Property, String>fold(idsOwner, "", _function_1);
          _builder.append(_fold, "\t");
          _builder.newLineIfNotEmpty();
          _builder.append(");");
          _builder.newLine();
          CharSequence _generateForeignKeys = PackageDatabaseScriptGenerator.generateForeignKeys(((Classifier)type), property.getName(), tableName);
          _builder.append(_generateForeignKeys);
          _builder.newLineIfNotEmpty();
          _builder.newLine();
          _builder.append("ALTER TABLE ONLY ");
          String _snakeCase_1 = Utils.toSnakeCase(tableName);
          _builder.append(_snakeCase_1);
          _builder.newLineIfNotEmpty();
          _builder.append("    ");
          _builder.append("ADD CONSTRAINT ");
          String _snakeCase_2 = Utils.toSnakeCase(tableName);
          _builder.append(_snakeCase_2, "    ");
          _builder.append("_ids_fkey");
          _builder.newLineIfNotEmpty();
          _builder.append("    ");
          _builder.append("FOREIGN KEY (");
          _builder.append(idsName, "    ");
          _builder.append(") REFERENCES ");
          String _tableName_1 = ClassifierUtils.getTableName(fromClass);
          _builder.append(_tableName_1, "    ");
          _builder.append("(");
          _builder.append(idsName, "    ");
          _builder.append(");");
          _builder.newLineIfNotEmpty();
          _builder.append("    ");
          _builder.newLine();
          _builder.append("ALTER TABLE ONLY ");
          String _snakeCase_3 = Utils.toSnakeCase(tableName);
          _builder.append(_snakeCase_3);
          _builder.newLineIfNotEmpty();
          _builder.append("    ");
          _builder.append("ADD CONSTRAINT ");
          String _snakeCase_4 = Utils.toSnakeCase(tableName);
          _builder.append(_snakeCase_4, "    ");
          _builder.append("_pkey PRIMARY KEY(");
          _builder.append(pkeys, "    ");
          _builder.append(");");
          _builder.newLineIfNotEmpty();
          _builder.append("    ");
          _builder.newLine();
          _xblockexpression_1 = _builder;
        }
        _xifexpression = _xblockexpression_1;
      }
      _xblockexpression = _xifexpression;
    }
    return _xblockexpression;
  }
  
  public static ArrayList<String> getAttributList(final Classifier type, final ArrayList<String> names, final String additionnalName) {
    final Iterable<Property> attributes = ClassifierUtils.getNotMultivaluedOwnedAttributes(type);
    final Consumer<Property> _function = (Property attribut) -> {
      boolean _isNullable = PropertyUtils.isNullable(attribut);
      boolean _not = (!_isNullable);
      if (_not) {
        final Type attrType = attribut.getType();
        final String name = Utils.toSnakeCase(Utils.addAdditionnalName(additionnalName, attribut.getName()));
        boolean _isEntity = Utils.isEntity(attrType);
        if (_isEntity) {
          final Iterable<Property> ids = ClassifierUtils.getId(((Classifier) attrType));
          final Consumer<Property> _function_1 = (Property id) -> {
            String _name = id.getName();
            String _firstToUpperCase = Utils.getFirstToUpperCase(attribut.getName());
            String _plus = (_name + _firstToUpperCase);
            String idName = Utils.toSnakeCase(Utils.addAdditionnalName(additionnalName, _plus));
            names.add(idName);
          };
          ids.forEach(_function_1);
        } else {
          boolean _isNomenclature = Utils.isNomenclature(attrType);
          if (_isNomenclature) {
            names.add(name);
          } else {
            boolean _isValueObject = Utils.isValueObject(attrType);
            if (_isValueObject) {
              final String voName = Utils.addAdditionnalName(additionnalName, attribut.getName());
              PackageDatabaseScriptGenerator.getAttributList(((Classifier) attrType), names, voName);
            } else {
              names.add(name);
            }
          }
        }
      }
    };
    attributes.forEach(_function);
    return names;
  }
  
  /**
   * génère les tables liées a des tableaux d'enum
   */
  public static CharSequence generateEnumAttributTable(final Property property, final Classifier fromClass) {
    CharSequence _xblockexpression = null;
    {
      final Type type = property.getType();
      CharSequence _xifexpression = null;
      if ((type instanceof Classifier)) {
        CharSequence _xblockexpression_1 = null;
        {
          String _tableName = ClassifierUtils.getTableName(fromClass);
          String _firstToUpperCase = Utils.getFirstToUpperCase(property.getName());
          final String tableName = (_tableName + _firstToUpperCase);
          final Iterable<Property> idsOwner = ClassifierUtils.getId(fromClass);
          final Function2<String, Property, String> _function = (String acc, Property id) -> {
            String _xifexpression_1 = null;
            boolean _notEquals = (!Objects.equal(acc, ""));
            if (_notEquals) {
              StringConcatenation _builder = new StringConcatenation();
              _builder.append(", ");
              String _snakeCase = Utils.toSnakeCase(id.getName());
              _builder.append(_snakeCase);
              _xifexpression_1 = (acc + _builder);
            } else {
              StringConcatenation _builder_1 = new StringConcatenation();
              String _snakeCase_1 = Utils.toSnakeCase(id.getName());
              _builder_1.append(_snakeCase_1);
              _xifexpression_1 = (acc + _builder_1);
            }
            return _xifexpression_1;
          };
          final String idsName = IterableExtensions.<Property, String>fold(idsOwner, "", _function);
          final String sqlType = TypeUtils.getEnumType(((Classifier)type));
          StringConcatenation _builder = new StringConcatenation();
          _builder.newLine();
          _builder.append("CREATE TABLE ");
          String _snakeCase = Utils.toSnakeCase(tableName);
          _builder.append(_snakeCase);
          _builder.append("(");
          _builder.newLineIfNotEmpty();
          _builder.append("\t");
          _builder.append("code ");
          _builder.append(sqlType, "\t");
          _builder.append(" NOT NULL,");
          _builder.newLineIfNotEmpty();
          _builder.append("\t");
          final Function2<String, Property, String> _function_1 = (String acc, Property id) -> {
            String _xifexpression_1 = null;
            boolean _notEquals = (!Objects.equal(acc, ""));
            if (_notEquals) {
              StringConcatenation _builder_1 = new StringConcatenation();
              _builder_1.append(",");
              _builder_1.newLine();
              String _plus = (acc + _builder_1);
              StringConcatenation _builder_2 = new StringConcatenation();
              CharSequence _generateIdAttributeDefinition = PackageDatabaseScriptGenerator.generateIdAttributeDefinition(id, "");
              _builder_2.append(_generateIdAttributeDefinition);
              _xifexpression_1 = (_plus + _builder_2);
            } else {
              StringConcatenation _builder_3 = new StringConcatenation();
              CharSequence _generateIdAttributeDefinition_1 = PackageDatabaseScriptGenerator.generateIdAttributeDefinition(id, "");
              _builder_3.append(_generateIdAttributeDefinition_1);
              _xifexpression_1 = (acc + _builder_3);
            }
            return _xifexpression_1;
          };
          String _fold = IterableExtensions.<Property, String>fold(idsOwner, "", _function_1);
          _builder.append(_fold, "\t");
          _builder.newLineIfNotEmpty();
          _builder.append(");");
          CharSequence _generateForeignKeys = PackageDatabaseScriptGenerator.generateForeignKeys(((Classifier)type), property.getName(), tableName);
          _builder.append(_generateForeignKeys);
          _builder.newLineIfNotEmpty();
          _builder.newLine();
          _builder.append("ALTER TABLE ONLY ");
          String _snakeCase_1 = Utils.toSnakeCase(tableName);
          _builder.append(_snakeCase_1);
          _builder.newLineIfNotEmpty();
          _builder.append("    ");
          _builder.append("ADD CONSTRAINT ");
          String _snakeCase_2 = Utils.toSnakeCase(tableName);
          _builder.append(_snakeCase_2, "    ");
          _builder.append("_ids_fkey");
          _builder.newLineIfNotEmpty();
          _builder.append("    ");
          _builder.append("FOREIGN KEY (");
          _builder.append(idsName, "    ");
          _builder.append(") REFERENCES ");
          String _tableName_1 = ClassifierUtils.getTableName(fromClass);
          _builder.append(_tableName_1, "    ");
          _builder.append("(");
          _builder.append(idsName, "    ");
          _builder.append(");");
          _builder.newLineIfNotEmpty();
          _builder.newLine();
          _builder.append("ALTER TABLE ONLY ");
          String _snakeCase_3 = Utils.toSnakeCase(tableName);
          _builder.append(_snakeCase_3);
          _builder.newLineIfNotEmpty();
          _builder.append("    ");
          _builder.append("ADD CONSTRAINT ");
          String _snakeCase_4 = Utils.toSnakeCase(tableName);
          _builder.append(_snakeCase_4, "    ");
          _builder.append("_code_fkey");
          _builder.newLineIfNotEmpty();
          _builder.append("    ");
          _builder.append("FOREIGN KEY (code) REFERENCES ");
          String _tableName_2 = ClassifierUtils.getTableName(((Classifier)type));
          _builder.append(_tableName_2, "    ");
          _builder.append("(code);");
          _builder.newLineIfNotEmpty();
          _builder.newLine();
          _builder.append("ALTER TABLE ONLY ");
          String _snakeCase_5 = Utils.toSnakeCase(tableName);
          _builder.append(_snakeCase_5);
          _builder.newLineIfNotEmpty();
          _builder.append("    ");
          _builder.append("ADD CONSTRAINT ");
          String _snakeCase_6 = Utils.toSnakeCase(tableName);
          _builder.append(_snakeCase_6, "    ");
          _builder.append("_pkey PRIMARY KEY (");
          _builder.append(idsName, "    ");
          _builder.append(", code);");
          _builder.newLineIfNotEmpty();
          _xblockexpression_1 = _builder;
        }
        _xifexpression = _xblockexpression_1;
      }
      _xblockexpression = _xifexpression;
    }
    return _xblockexpression;
  }
  
  /**
   * génère la séquence liée a un attribut
   */
  public static CharSequence generateSequence(final Property property) {
    CharSequence _xblockexpression = null;
    {
      final Element owner = property.getOwner();
      CharSequence _xifexpression = null;
      if ((owner instanceof Classifier)) {
        CharSequence _xblockexpression_1 = null;
        {
          StringConcatenation _builder = new StringConcatenation();
          _builder.append("NO MAXVALUE");
          String maxVal = _builder.toString();
          StringConcatenation _builder_1 = new StringConcatenation();
          _builder_1.append("NO MINVALUE");
          String minVal = _builder_1.toString();
          String _tableName = ClassifierUtils.getTableName(((Classifier)owner));
          String _firstToUpperCase = Utils.getFirstToUpperCase(property.getName());
          final String name = (_tableName + _firstToUpperCase);
          final Object startWith = Utils.getStereotypePropertyValue(property, Utils.MODEL_SEQUENCE, Utils.MODEL_SEQUENCE_STARTWITH);
          final Object inscrementBy = Utils.getStereotypePropertyValue(property, Utils.MODEL_SEQUENCE, Utils.MODEL_SEQUENCE_INCREMENTBY);
          final Object hasMaxValue = Utils.getStereotypePropertyValue(property, Utils.MODEL_SEQUENCE, Utils.MODEL_SEQUENCE_HASMAXVALUE);
          final Object maxValue = Utils.getStereotypePropertyValue(property, Utils.MODEL_SEQUENCE, Utils.MODEL_SEQUENCE_MAXVALUE);
          if ((Objects.equal(hasMaxValue, Boolean.valueOf(true)) || (!Objects.equal(maxValue, Integer.valueOf(0))))) {
            StringConcatenation _builder_2 = new StringConcatenation();
            _builder_2.append("MAXVALUE ");
            _builder_2.append(maxValue);
            maxVal = _builder_2.toString();
          }
          final Object hasMinValue = Utils.getStereotypePropertyValue(property, Utils.MODEL_SEQUENCE, Utils.MODEL_SEQUENCE_HASMINVALUE);
          final Object minValue = Utils.getStereotypePropertyValue(property, Utils.MODEL_SEQUENCE, Utils.MODEL_SEQUENCE_MINVALUE);
          if ((Objects.equal(hasMinValue, Boolean.valueOf(true)) || (!Objects.equal(minValue, Integer.valueOf(0))))) {
            StringConcatenation _builder_3 = new StringConcatenation();
            _builder_3.append("MINVALUE ");
            _builder_3.append(minValue);
            minVal = _builder_3.toString();
          }
          final Object cache = Utils.getStereotypePropertyValue(property, Utils.MODEL_SEQUENCE, Utils.MODEL_SEQUENCE_CACHE);
          final Object isCycle = Utils.getStereotypePropertyValue(property, Utils.MODEL_SEQUENCE, Utils.MODEL_SEQUENCE_CYCLE);
          String cycle = "NO CYCLE";
          boolean _equals = Objects.equal(isCycle, Boolean.valueOf(true));
          if (_equals) {
            cycle = "CYCLE";
          }
          StringConcatenation _builder_4 = new StringConcatenation();
          _builder_4.append("CREATE SEQUENCE ");
          String _snakeCase = Utils.toSnakeCase(name);
          _builder_4.append(_snakeCase);
          _builder_4.append("_seq");
          _builder_4.newLineIfNotEmpty();
          _builder_4.append("    ");
          _builder_4.append("START WITH ");
          _builder_4.append(startWith, "    ");
          _builder_4.newLineIfNotEmpty();
          _builder_4.append("    ");
          _builder_4.append("INCREMENT BY ");
          _builder_4.append(inscrementBy, "    ");
          _builder_4.newLineIfNotEmpty();
          _builder_4.append("    ");
          _builder_4.append(maxVal, "    ");
          _builder_4.newLineIfNotEmpty();
          _builder_4.append("    ");
          _builder_4.append(minVal, "    ");
          _builder_4.newLineIfNotEmpty();
          _builder_4.append("    ");
          _builder_4.append("CACHE ");
          _builder_4.append(cache, "    ");
          _builder_4.newLineIfNotEmpty();
          _builder_4.append("    ");
          _builder_4.append(cycle, "    ");
          _builder_4.append(";");
          _builder_4.newLineIfNotEmpty();
          _builder_4.append("    ");
          _builder_4.newLine();
          _builder_4.append("ALTER SEQUENCE ");
          String _snakeCase_1 = Utils.toSnakeCase(name);
          _builder_4.append(_snakeCase_1);
          _builder_4.append("_seq ");
          _builder_4.newLineIfNotEmpty();
          _builder_4.append("\t");
          _builder_4.append("OWNED BY ");
          String _tableName_1 = ClassifierUtils.getTableName(((Classifier)owner));
          _builder_4.append(_tableName_1, "\t");
          _builder_4.append(".");
          String _snakeCase_2 = Utils.toSnakeCase(property.getName());
          _builder_4.append(_snakeCase_2, "\t");
          _builder_4.append(";");
          _builder_4.newLineIfNotEmpty();
          _builder_4.newLine();
          _builder_4.append("ALTER TABLE ONLY ");
          String _tableName_2 = ClassifierUtils.getTableName(((Classifier)owner));
          _builder_4.append(_tableName_2);
          _builder_4.append(" ");
          _builder_4.newLineIfNotEmpty();
          _builder_4.append("\t");
          _builder_4.append("ALTER COLUMN ");
          String _snakeCase_3 = Utils.toSnakeCase(property.getName());
          _builder_4.append(_snakeCase_3, "\t");
          _builder_4.append(" ");
          _builder_4.newLineIfNotEmpty();
          _builder_4.append("\t");
          _builder_4.append("SET DEFAULT nextval(\'");
          String _snakeCase_4 = Utils.toSnakeCase(name);
          _builder_4.append(_snakeCase_4, "\t");
          _builder_4.append("_seq\'::regclass);");
          _builder_4.newLineIfNotEmpty();
          _xblockexpression_1 = _builder_4;
        }
        _xifexpression = _xblockexpression_1;
      }
      _xblockexpression = _xifexpression;
    }
    return _xblockexpression;
  }
  
  public static CharSequence generateAssociationTable(final AssociationClass clazz) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.newLine();
    _builder.append("CREATE TABLE ");
    String _snakeCase = Utils.toSnakeCase(clazz.getName());
    _builder.append(_snakeCase);
    _builder.append("(");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    CharSequence _generateAssociationAttributes = PackageDatabaseScriptGenerator.generateAssociationAttributes(clazz, "", clazz);
    _builder.append(_generateAssociationAttributes, "\t");
    _builder.newLineIfNotEmpty();
    _builder.append(");");
    _builder.newLine();
    CharSequence _generateAssociationForeignKeys = PackageDatabaseScriptGenerator.generateAssociationForeignKeys(clazz);
    _builder.append(_generateAssociationForeignKeys);
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    _builder.append("ALTER TABLE ONLY ");
    String _snakeCase_1 = Utils.toSnakeCase(clazz.getName());
    _builder.append(_snakeCase_1);
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("ADD CONSTRAINT ");
    String _snakeCase_2 = Utils.toSnakeCase(clazz.getName());
    _builder.append(_snakeCase_2, "\t");
    _builder.append("_pkey PRIMARY KEY (");
    String _listComma = Utils.getListComma(PackageDatabaseScriptGenerator.getAssociationAttributList(clazz, CollectionLiterals.<String>newArrayList(), ""));
    _builder.append(_listComma, "\t");
    _builder.append(");");
    _builder.newLineIfNotEmpty();
    return _builder;
  }
  
  public static ArrayList<String> getAssociationAttributList(final AssociationClass type, final ArrayList<String> names, final String additionnalName) {
    final EList<Property> attributes = type.getMemberEnds();
    final Consumer<Property> _function = (Property attribut) -> {
      final Type attrType = attribut.getType();
      final String name = Utils.toSnakeCase(Utils.addAdditionnalName(additionnalName, attribut.getName()));
      boolean _isEntity = Utils.isEntity(attrType);
      if (_isEntity) {
        final Iterable<Property> ids = ClassifierUtils.getId(((Classifier) attrType));
        final Consumer<Property> _function_1 = (Property id) -> {
          String _name = id.getName();
          String _firstToUpperCase = Utils.getFirstToUpperCase(attribut.getName());
          String _plus = (_name + _firstToUpperCase);
          String idName = Utils.toSnakeCase(Utils.addAdditionnalName(additionnalName, _plus));
          names.add(idName);
        };
        ids.forEach(_function_1);
      } else {
        boolean _isNomenclature = Utils.isNomenclature(attrType);
        if (_isNomenclature) {
          names.add(name);
        } else {
          boolean _isValueObject = Utils.isValueObject(attrType);
          if (_isValueObject) {
            final String voName = Utils.addAdditionnalName(additionnalName, attribut.getName());
            PackageDatabaseScriptGenerator.getAttributList(((Classifier) attrType), names, voName);
          } else {
            names.add(name);
          }
        }
      }
    };
    attributes.forEach(_function);
    return names;
  }
  
  /**
   * Génère les attributs de la classe
   */
  public static CharSequence generateAssociationAttributes(final AssociationClass clazz, final String additionnalName, final Classifier fromClass) {
    CharSequence _xblockexpression = null;
    {
      final EList<Property> attributes = clazz.getMemberEnds();
      StringConcatenation _builder = new StringConcatenation();
      final Function2<String, Property, String> _function = (String acc, Property attribut) -> {
        String _xifexpression = null;
        boolean _notEquals = (!Objects.equal(acc, ""));
        if (_notEquals) {
          StringConcatenation _builder_1 = new StringConcatenation();
          _builder_1.append(",");
          _builder_1.newLine();
          String _plus = (acc + _builder_1);
          StringConcatenation _builder_2 = new StringConcatenation();
          CharSequence _generateAttributDefinition = PackageDatabaseScriptGenerator.generateAttributDefinition(attribut, additionnalName, fromClass, Boolean.valueOf(false));
          _builder_2.append(_generateAttributDefinition);
          _xifexpression = (_plus + _builder_2);
        } else {
          StringConcatenation _builder_3 = new StringConcatenation();
          CharSequence _generateAttributDefinition_1 = PackageDatabaseScriptGenerator.generateAttributDefinition(attribut, additionnalName, fromClass, Boolean.valueOf(false));
          _builder_3.append(_generateAttributDefinition_1);
          _xifexpression = (acc + _builder_3);
        }
        return _xifexpression;
      };
      String _fold = IterableExtensions.<Property, String>fold(attributes, "", _function);
      _builder.append(_fold);
      _xblockexpression = _builder;
    }
    return _xblockexpression;
  }
  
  public static CharSequence generateAssociationForeignKeys(final AssociationClass clazz) {
    CharSequence _xblockexpression = null;
    {
      final EList<Property> members = clazz.getMemberEnds();
      StringConcatenation _builder = new StringConcatenation();
      final Function2<String, Property, String> _function = (String acc, Property member) -> {
        StringConcatenation _builder_1 = new StringConcatenation();
        CharSequence _generateAssociationForeignKeys = PackageDatabaseScriptGenerator.generateAssociationForeignKeys(member, clazz);
        _builder_1.append(_generateAssociationForeignKeys);
        return (acc + _builder_1);
      };
      String _fold = IterableExtensions.<Property, String>fold(members, "", _function);
      _builder.append(_fold);
      _xblockexpression = _builder;
    }
    return _xblockexpression;
  }
  
  public static CharSequence generateAssociationForeignKeys(final Property property, final Classifier fromClass) {
    CharSequence _xblockexpression = null;
    {
      final Type type = property.getType();
      if ((type instanceof Classifier)) {
        boolean _isEntity = Utils.isEntity(type);
        if (_isEntity) {
          StringConcatenation _builder = new StringConcatenation();
          String _generateAssociationForeignKeysEntity = PackageDatabaseScriptGenerator.generateAssociationForeignKeysEntity(property, fromClass);
          _builder.append(_generateAssociationForeignKeysEntity);
          return _builder.toString();
        } else {
          boolean _isValueObject = Utils.isValueObject(type);
          if (_isValueObject) {
            StringConcatenation _builder_1 = new StringConcatenation();
            CharSequence _generateForeignKeys = PackageDatabaseScriptGenerator.generateForeignKeys(((Classifier)type), property.getName(), ClassifierUtils.getTableName(fromClass));
            _builder_1.append(_generateForeignKeys);
            return _builder_1.toString();
          } else {
            boolean _isNomenclature = Utils.isNomenclature(type);
            if (_isNomenclature) {
              StringConcatenation _builder_2 = new StringConcatenation();
              String _generateAssociationForeignKeysEnum = PackageDatabaseScriptGenerator.generateAssociationForeignKeysEnum(property, fromClass);
              _builder_2.append(_generateAssociationForeignKeysEnum);
              return _builder_2.toString();
            }
          }
        }
      }
      StringConcatenation _builder_3 = new StringConcatenation();
      _xblockexpression = _builder_3;
    }
    return _xblockexpression;
  }
  
  public static String generateAssociationForeignKeysEntity(final Property property, final Classifier fromClass) {
    final Type type = property.getType();
    if ((type instanceof Classifier)) {
      final Iterable<Property> ids = ClassifierUtils.getId(((Classifier)type));
      final Function2<String, Property, String> _function = (String acc, Property id) -> {
        String _xifexpression = null;
        boolean _notEquals = (!Objects.equal(acc, ""));
        if (_notEquals) {
          StringConcatenation _builder = new StringConcatenation();
          _builder.append(", ");
          String _snakeCase = Utils.toSnakeCase(id.getName());
          _builder.append(_snakeCase);
          _xifexpression = (acc + _builder);
        } else {
          StringConcatenation _builder_1 = new StringConcatenation();
          String _snakeCase_1 = Utils.toSnakeCase(id.getName());
          _builder_1.append(_snakeCase_1);
          _xifexpression = (acc + _builder_1);
        }
        return _xifexpression;
      };
      final String idsName = IterableExtensions.<Property, String>fold(ids, "", _function);
      final Function2<String, Property, String> _function_1 = (String acc, Property id) -> {
        String _xifexpression = null;
        boolean _notEquals = (!Objects.equal(acc, ""));
        if (_notEquals) {
          StringConcatenation _builder = new StringConcatenation();
          _builder.append(", ");
          String _snakeCase = Utils.toSnakeCase(id.getName());
          _builder.append(_snakeCase);
          _builder.append("_");
          String _snakeCase_1 = Utils.toSnakeCase(property.getName());
          _builder.append(_snakeCase_1);
          _xifexpression = (acc + _builder);
        } else {
          StringConcatenation _builder_1 = new StringConcatenation();
          String _snakeCase_2 = Utils.toSnakeCase(id.getName());
          _builder_1.append(_snakeCase_2);
          _builder_1.append("_");
          String _snakeCase_3 = Utils.toSnakeCase(property.getName());
          _builder_1.append(_snakeCase_3);
          _xifexpression = (acc + _builder_1);
        }
        return _xifexpression;
      };
      final String idsNameInClass = IterableExtensions.<Property, String>fold(ids, "", _function_1);
      StringConcatenation _builder = new StringConcatenation();
      _builder.newLine();
      _builder.append("ALTER TABLE ONLY ");
      String _tableName = ClassifierUtils.getTableName(fromClass);
      _builder.append(_tableName);
      _builder.newLineIfNotEmpty();
      _builder.append("\t");
      _builder.append("ADD CONSTRAINT ");
      String _tableName_1 = ClassifierUtils.getTableName(fromClass);
      _builder.append(_tableName_1, "\t");
      _builder.append("_");
      String _snakeCase = Utils.toSnakeCase(property.getName());
      _builder.append(_snakeCase, "\t");
      _builder.append("_ids_fkey ");
      _builder.newLineIfNotEmpty();
      _builder.append("\t");
      _builder.append("FOREIGN KEY (");
      _builder.append(idsNameInClass, "\t");
      _builder.append(") REFERENCES ");
      String _tableName_2 = ClassifierUtils.getTableName(((Classifier)type));
      _builder.append(_tableName_2, "\t");
      _builder.append("(");
      _builder.append(idsName, "\t");
      _builder.append(");");
      _builder.newLineIfNotEmpty();
      return _builder.toString();
    }
    StringConcatenation _builder_1 = new StringConcatenation();
    return _builder_1.toString();
  }
  
  public static String generateAssociationForeignKeysEnum(final Property property, final Classifier fromClass) {
    final Type type = property.getType();
    if ((type instanceof Classifier)) {
      StringConcatenation _builder = new StringConcatenation();
      _builder.newLine();
      _builder.append("ALTER TABLE ONLY ");
      String _tableName = ClassifierUtils.getTableName(fromClass);
      _builder.append(_tableName);
      _builder.newLineIfNotEmpty();
      _builder.append("\t");
      _builder.append("ADD CONSTRAINT ");
      String _tableName_1 = ClassifierUtils.getTableName(fromClass);
      _builder.append(_tableName_1, "\t");
      _builder.append("_");
      String _snakeCase = Utils.toSnakeCase(property.getName());
      _builder.append(_snakeCase, "\t");
      _builder.append("_code_fkey ");
      _builder.newLineIfNotEmpty();
      _builder.append("\t");
      _builder.append("FOREIGN KEY (");
      String _name = property.getName();
      _builder.append(_name, "\t");
      _builder.append(") REFERENCES ");
      String _tableName_2 = ClassifierUtils.getTableName(((Classifier)type));
      _builder.append(_tableName_2, "\t");
      _builder.append("(code);");
      _builder.newLineIfNotEmpty();
      return _builder.toString();
    }
    StringConcatenation _builder_1 = new StringConcatenation();
    return _builder_1.toString();
  }
  
  public static String generateAttributType(final Property property) {
    String type = TypeUtils.getDatabaseType(property.getType());
    boolean _equals = Objects.equal(type, "character");
    if (_equals) {
      final Object length = PropertyUtils.getStereotypePropertyValue(property, Utils.MODEL_ATTRIBUTE, Utils.MODEL_ATTRIBUTE_LENGTH);
      if (((length == null) || Objects.equal(length, Integer.valueOf(0)))) {
        type = "text";
      }
    }
    return type;
  }
  
  public static CharSequence generateNullable(final Property property, final Boolean nullable) {
    CharSequence _xblockexpression = null;
    {
      boolean isNullable = PropertyUtils.isNullable(property);
      if ((nullable).booleanValue()) {
        isNullable = true;
      }
      CharSequence _xifexpression = null;
      if ((!isNullable)) {
        StringConcatenation _builder = new StringConcatenation();
        _builder.append("NOT NULL");
        return _builder.toString();
      } else {
        StringConcatenation _builder_1 = new StringConcatenation();
        _xifexpression = _builder_1;
      }
      _xblockexpression = _xifexpression;
    }
    return _xblockexpression;
  }
  
  /**
   * génère une table enum
   */
  public static CharSequence generateEnumTable(final Classifier clazz) {
    CharSequence _xblockexpression = null;
    {
      final boolean hasCode = ClassifierUtils.isEnumWithCode(clazz);
      String sqlType = TypeUtils.getEnumType(clazz);
      StringConcatenation _builder = new StringConcatenation();
      _builder.newLine();
      _builder.append("CREATE TABLE ");
      String _tableName = ClassifierUtils.getTableName(clazz);
      _builder.append(_tableName);
      _builder.append("(");
      _builder.newLineIfNotEmpty();
      _builder.append("\t");
      _builder.append("code ");
      _builder.append(sqlType, "\t");
      _builder.append(" NOT NULL,");
      _builder.newLineIfNotEmpty();
      _builder.append("\t");
      _builder.append("libelle text");
      _builder.newLine();
      _builder.append(");");
      _builder.newLine();
      _builder.newLine();
      _builder.append("ALTER TABLE ONLY ");
      String _tableName_1 = ClassifierUtils.getTableName(clazz);
      _builder.append(_tableName_1);
      _builder.newLineIfNotEmpty();
      _builder.append("\t");
      _builder.append("ADD CONSTRAINT ");
      String _tableName_2 = ClassifierUtils.getTableName(clazz);
      _builder.append(_tableName_2, "\t");
      _builder.append("_pkey PRIMARY KEY (code);");
      _builder.newLineIfNotEmpty();
      {
        if ((!hasCode)) {
          _builder.newLine();
          _builder.append("CREATE SEQUENCE ");
          String _tableName_3 = ClassifierUtils.getTableName(clazz);
          _builder.append(_tableName_3);
          _builder.append("_code_seq");
          _builder.newLineIfNotEmpty();
          _builder.append("    ");
          _builder.append("START WITH 1");
          _builder.newLine();
          _builder.append("    ");
          _builder.append("INCREMENT BY 1");
          _builder.newLine();
          _builder.append("    ");
          _builder.append("NO MAXVALUE");
          _builder.newLine();
          _builder.append("    ");
          _builder.append("NO MINVALUE");
          _builder.newLine();
          _builder.append("    ");
          _builder.append("CACHE 1");
          _builder.newLine();
          _builder.append("    ");
          _builder.append("NO CYCLE;");
          _builder.newLine();
          _builder.append("    ");
          _builder.newLine();
          _builder.append("ALTER SEQUENCE ");
          String _tableName_4 = ClassifierUtils.getTableName(clazz);
          _builder.append(_tableName_4);
          _builder.append("_code_seq");
          _builder.newLineIfNotEmpty();
          _builder.append("\t");
          _builder.append("OWNED BY ");
          String _tableName_5 = ClassifierUtils.getTableName(clazz);
          _builder.append(_tableName_5, "\t");
          _builder.append(".code;");
          _builder.newLineIfNotEmpty();
        }
      }
      _xblockexpression = _builder;
    }
    return _xblockexpression;
  }
}
