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
package fr.gouv.diplomatie.papyrus.codegen.sql.xtend.pakkage;

import com.google.common.base.Objects;
import fr.gouv.diplomatie.papyrus.codegen.core.utils.AssociationClassUtils;
import fr.gouv.diplomatie.papyrus.codegen.core.utils.ClassifierUtils;
import fr.gouv.diplomatie.papyrus.codegen.core.utils.PropertyUtils;
import fr.gouv.diplomatie.papyrus.codegen.core.utils.Utils;
import fr.gouv.diplomatie.papyrus.codegen.sql.utils.SqlClassifierUtils;
import fr.gouv.diplomatie.papyrus.codegen.sql.utils.TypeUtils;
import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Consumer;
import org.eclipse.emf.common.util.EList;
import org.eclipse.uml2.uml.Association;
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
  /**
   * TODO
   * changer le nom de ref : ex: id_nationalite etc...
   */
  public static CharSequence generateCode(final org.eclipse.uml2.uml.Package pakkage) {
    CharSequence _xblockexpression = null;
    {
      final Model model = pakkage.getModel();
      final Function1<Element, Boolean> _function = (Element elem) -> {
        return Boolean.valueOf((elem instanceof org.eclipse.uml2.uml.Package));
      };
      final Iterable<Element> packages = IterableExtensions.<Element>filter(model.getOwnedElements(), _function);
      final Function1<Type, Boolean> _function_1 = (Type type) -> {
        return Boolean.valueOf((Utils.isEntity(type) && ClassifierUtils.canBeGenerated(((Classifier) type))));
      };
      final Iterable<Type> classes = IterableExtensions.<Type>filter(pakkage.getOwnedTypes(), _function_1);
      final Function1<Type, Boolean> _function_2 = (Type type) -> {
        return Boolean.valueOf((Utils.isNomenclature(type) && ClassifierUtils.canBeGenerated(((Classifier) type))));
      };
      final Iterable<Type> enums = IterableExtensions.<Type>filter(pakkage.getOwnedTypes(), _function_2);
      final Function1<Type, Boolean> _function_3 = (Type type) -> {
        return Boolean.valueOf((type instanceof AssociationClass));
      };
      final Iterable<Type> associationsClasses = IterableExtensions.<Type>filter(model.getOwnedTypes(), _function_3);
      final Function1<Type, Boolean> _function_4 = (Type type) -> {
        return Boolean.valueOf((type instanceof AssociationClass));
      };
      final Iterable<Type> assoInPakkage = IterableExtensions.<Type>filter(pakkage.getOwnedTypes(), _function_4);
      StringConcatenation _builder = new StringConcatenation();
      final Function2<String, Element, String> _function_5 = (String acc, Element pkg) -> {
        StringConcatenation _builder_1 = new StringConcatenation();
        String _generateCreateSchema = PackageDatabaseScriptGenerator.generateCreateSchema(((org.eclipse.uml2.uml.Package) pkg));
        _builder_1.append(_generateCreateSchema);
        return (acc + _builder_1);
      };
      String _fold = IterableExtensions.<Element, String>fold(packages, "", _function_5);
      _builder.append(_fold);
      _builder.newLineIfNotEmpty();
      final Function2<String, Type, String> _function_6 = (String acc, Type clazz) -> {
        StringConcatenation _builder_1 = new StringConcatenation();
        CharSequence _generateTable = PackageDatabaseScriptGenerator.generateTable(((Classifier) clazz));
        _builder_1.append(_generateTable);
        return (acc + _builder_1);
      };
      String _fold_1 = IterableExtensions.<Type, String>fold(classes, "", _function_6);
      _builder.append(_fold_1);
      _builder.newLineIfNotEmpty();
      final Function2<String, Type, String> _function_7 = (String acc, Type clazz) -> {
        StringConcatenation _builder_1 = new StringConcatenation();
        CharSequence _generateEnumTable = PackageDatabaseScriptGenerator.generateEnumTable(((Classifier) clazz));
        _builder_1.append(_generateEnumTable);
        return (acc + _builder_1);
      };
      String _fold_2 = IterableExtensions.<Type, String>fold(enums, "", _function_7);
      _builder.append(_fold_2);
      _builder.newLineIfNotEmpty();
      final Function2<String, Type, String> _function_8 = (String acc, Type clazz) -> {
        StringConcatenation _builder_1 = new StringConcatenation();
        CharSequence _generateAlters = PackageDatabaseScriptGenerator.generateAlters(((Classifier) clazz));
        _builder_1.append(_generateAlters);
        return (acc + _builder_1);
      };
      String _fold_3 = IterableExtensions.<Type, String>fold(classes, "", _function_8);
      _builder.append(_fold_3);
      _builder.newLineIfNotEmpty();
      final Function2<String, Type, String> _function_9 = (String acc, Type clazz) -> {
        StringConcatenation _builder_1 = new StringConcatenation();
        CharSequence _generateAssociationTable = PackageDatabaseScriptGenerator.generateAssociationTable(((AssociationClass) clazz));
        _builder_1.append(_generateAssociationTable);
        return (acc + _builder_1);
      };
      String _fold_4 = IterableExtensions.<Type, String>fold(associationsClasses, "", _function_9);
      _builder.append(_fold_4);
      _builder.newLineIfNotEmpty();
      {
        boolean _notEquals = (!Objects.equal(pakkage, model));
        if (_notEquals) {
          final Function2<String, Type, String> _function_10 = (String acc, Type clazz) -> {
            StringConcatenation _builder_1 = new StringConcatenation();
            CharSequence _generateAssociationTable = PackageDatabaseScriptGenerator.generateAssociationTable(((AssociationClass) clazz));
            _builder_1.append(_generateAssociationTable);
            return (acc + _builder_1);
          };
          String _fold_5 = IterableExtensions.<Type, String>fold(assoInPakkage, "", _function_10);
          _builder.append(_fold_5);
        }
      }
      _builder.newLineIfNotEmpty();
      _xblockexpression = _builder;
    }
    return _xblockexpression;
  }
  
  public static String generateCreateSchema(final org.eclipse.uml2.uml.Package pkg) {
    final Object schema = Utils.getSchemaName(pkg);
    StringConcatenation _builder = new StringConcatenation();
    {
      if (((schema != null) && (!Objects.equal(schema, "")))) {
        _builder.newLineIfNotEmpty();
        _builder.append("CREATE SCHEMA ");
        _builder.append(schema);
        _builder.append(";");
      }
    }
    _builder.newLineIfNotEmpty();
    return _builder.toString();
  }
  
  public static CharSequence generateTable(final Classifier clazz) {
    CharSequence _xblockexpression = null;
    {
      final Function1<Property, Boolean> _function = (Property id) -> {
        return Boolean.valueOf(Utils.isSequence(id));
      };
      final Iterable<Property> attributes = IterableExtensions.<Property>filter(ClassifierUtils.getOwnedAttributes(clazz), _function);
      final String schema = SqlClassifierUtils.generateSchemaName(clazz);
      StringConcatenation _builder = new StringConcatenation();
      _builder.newLine();
      _builder.append("CREATE TABLE ");
      _builder.append(schema);
      String _dBTableName = ClassifierUtils.getDBTableName(clazz);
      _builder.append(_dBTableName);
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
      CharSequence _generateManyToOneRef = PackageDatabaseScriptGenerator.generateManyToOneRef(clazz);
      _builder.append(_generateManyToOneRef, "\t");
      _builder.newLineIfNotEmpty();
      _builder.append(");");
      _builder.newLine();
      CharSequence _generateIds = PackageDatabaseScriptGenerator.generateIds(clazz);
      _builder.append(_generateIds);
      _builder.newLineIfNotEmpty();
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
   * génère les clés étrangères liées aux one to many
   */
  public static CharSequence generateManyToOneRef(final Classifier clazz) {
    CharSequence _xblockexpression = null;
    {
      final ArrayList<Property> attributesRef = ClassifierUtils.getOneToManyAttributes(clazz);
      StringConcatenation _builder = new StringConcatenation();
      final Function2<String, Property, String> _function = (String acc, Property attr) -> {
        StringConcatenation _builder_1 = new StringConcatenation();
        _builder_1.append(",");
        _builder_1.newLine();
        CharSequence _generateAttrForeignKey = PackageDatabaseScriptGenerator.generateAttrForeignKey(attr, clazz, "");
        _builder_1.append(_generateAttrForeignKey);
        return (acc + _builder_1);
      };
      String _fold = IterableExtensions.<Property, String>fold(attributesRef, "", _function);
      _builder.append(_fold);
      _builder.newLineIfNotEmpty();
      _xblockexpression = _builder;
    }
    return _xblockexpression;
  }
  
  public static CharSequence generateAttrForeignKey(final Property property, final Classifier fromClass, final String additionnalName) {
    CharSequence _xblockexpression = null;
    {
      final String dbPropertyName = PropertyUtils.getDatabaseName(property, property.getName(), "");
      Type _type = property.getType();
      final Classifier owner = ((Classifier) _type);
      final Property id = ((Property[])Conversions.unwrapArray(ClassifierUtils.getId(owner), Property.class))[0];
      final String idDbName = PropertyUtils.getDatabaseName(id, id.getName(), "");
      final String fieldName = ((idDbName + "_") + dbPropertyName);
      final boolean nullable = PropertyUtils.isNullable(property);
      StringConcatenation _builder = new StringConcatenation();
      _builder.append(fieldName);
      _builder.append(" ");
      Object _generateAttributType = PackageDatabaseScriptGenerator.generateAttributType(id);
      _builder.append(_generateAttributType);
      CharSequence _generateStringLength = PackageDatabaseScriptGenerator.generateStringLength(id);
      _builder.append(_generateStringLength);
      _builder.append(" ");
      CharSequence _generateNullable = PackageDatabaseScriptGenerator.generateNullable(property, Boolean.valueOf(nullable));
      _builder.append(_generateNullable);
      _builder.newLineIfNotEmpty();
      _xblockexpression = _builder;
    }
    return _xblockexpression;
  }
  
  /**
   * génère les tables d'attributs multiple et les foreign keys
   */
  public static CharSequence generateAlters(final Classifier clazz) {
    CharSequence _xblockexpression = null;
    {
      final String schema = SqlClassifierUtils.generateSchemaName(clazz);
      StringConcatenation _builder = new StringConcatenation();
      CharSequence _generateMultivaluedAttributesTable = PackageDatabaseScriptGenerator.generateMultivaluedAttributesTable(clazz, clazz);
      _builder.append(_generateMultivaluedAttributesTable);
      _builder.newLineIfNotEmpty();
      CharSequence _generateForeignKeys = PackageDatabaseScriptGenerator.generateForeignKeys(clazz, "", ClassifierUtils.getDBTableName(clazz), schema);
      _builder.append(_generateForeignKeys);
      _builder.newLineIfNotEmpty();
      CharSequence _generateExtendsForeignKey = PackageDatabaseScriptGenerator.generateExtendsForeignKey(clazz);
      _builder.append(_generateExtendsForeignKey);
      _builder.newLineIfNotEmpty();
      CharSequence _generateOneToManyForeignKey = PackageDatabaseScriptGenerator.generateOneToManyForeignKey(clazz);
      _builder.append(_generateOneToManyForeignKey);
      _builder.newLineIfNotEmpty();
      _xblockexpression = _builder;
    }
    return _xblockexpression;
  }
  
  /**
   * génère les alters liés aux champs one to many
   */
  public static CharSequence generateOneToManyForeignKey(final Classifier clazz) {
    CharSequence _xblockexpression = null;
    {
      final ArrayList<Property> attributes = ClassifierUtils.getOneToManyAttributes(clazz);
      StringConcatenation _builder = new StringConcatenation();
      final Function2<String, Property, String> _function = (String acc, Property attr) -> {
        StringConcatenation _builder_1 = new StringConcatenation();
        CharSequence _generateAttributesAlterForeignKey = PackageDatabaseScriptGenerator.generateAttributesAlterForeignKey(attr, clazz, "");
        _builder_1.append(_generateAttributesAlterForeignKey);
        return (acc + _builder_1);
      };
      String _fold = IterableExtensions.<Property, String>fold(attributes, "", _function);
      _builder.append(_fold);
      _builder.newLineIfNotEmpty();
      _xblockexpression = _builder;
    }
    return _xblockexpression;
  }
  
  public static CharSequence generateAttributesAlterForeignKey(final Property property, final Classifier clazz, final String additionnalName) {
    CharSequence _xblockexpression = null;
    {
      final String dbPropertyName = PropertyUtils.getDatabaseName(property, property.getName(), "");
      Type _type = property.getType();
      final Classifier owner = ((Classifier) _type);
      final Property id = ((Property[])Conversions.unwrapArray(ClassifierUtils.getId(owner), Property.class))[0];
      final String idDbName = PropertyUtils.getDatabaseName(id, id.getName(), "");
      final String fieldName = ((idDbName + "_") + dbPropertyName);
      final String schema = SqlClassifierUtils.generateSchemaName(clazz);
      final String ownerSchema = SqlClassifierUtils.generateSchemaName(owner);
      StringConcatenation _builder = new StringConcatenation();
      _builder.newLine();
      _builder.append("ALTER TABLE ONLY ");
      _builder.append(schema);
      String _dBTableName = ClassifierUtils.getDBTableName(clazz);
      _builder.append(_dBTableName);
      _builder.newLineIfNotEmpty();
      _builder.append("    ");
      _builder.append("ADD CONSTRAINT ");
      String _dbName = Utils.toDbName(clazz.getName());
      _builder.append(_dbName, "    ");
      _builder.append("_");
      _builder.append(dbPropertyName, "    ");
      _builder.append("_ids_fkey");
      _builder.newLineIfNotEmpty();
      _builder.append("    ");
      _builder.append("FOREIGN KEY (");
      _builder.append(fieldName, "    ");
      _builder.append(") REFERENCES ");
      _builder.append(ownerSchema, "    ");
      String _dBTableName_1 = ClassifierUtils.getDBTableName(owner);
      _builder.append(_dBTableName_1, "    ");
      _builder.append("(");
      _builder.append(idDbName, "    ");
      _builder.append(");");
      _builder.newLineIfNotEmpty();
      _xblockexpression = _builder;
    }
    return _xblockexpression;
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
      final String name = id.getName();
      final String propertyName = PropertyUtils.getDatabaseName(id, name, null);
      StringConcatenation _builder = new StringConcatenation();
      _builder.append(propertyName);
      _builder.append(" ");
      Object _generateAttributType = PackageDatabaseScriptGenerator.generateAttributType(id);
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
      final String name = property.getName();
      String propertyName = PropertyUtils.getDatabaseName(property, name, additionnalName);
      StringConcatenation _builder = new StringConcatenation();
      _builder.append(propertyName);
      _builder.append(" ");
      Object _generateAttributType = PackageDatabaseScriptGenerator.generateAttributType(property);
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
      final Object length = Utils.getAttributeLength(property);
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
      final String name = property.getName();
      final String propertyName = PropertyUtils.getDatabaseName(property, name, additionnalName);
      final Type type = property.getType();
      CharSequence _xifexpression = null;
      if ((type instanceof Classifier)) {
        StringConcatenation _builder = new StringConcatenation();
        Object _generateAttributes = PackageDatabaseScriptGenerator.generateAttributes(((Classifier)type), propertyName, fromClass, Boolean.valueOf(((nullable).booleanValue() || PropertyUtils.isNullable(property))));
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
      String name = property.getName();
      final String propertyName = PropertyUtils.getDatabaseName(property, name, additionnalName);
      final Type type = property.getType();
      CharSequence _xifexpression = null;
      if ((type instanceof Classifier)) {
        CharSequence _xblockexpression_1 = null;
        {
          String sqlType = TypeUtils.getEnumType(((Classifier)type));
          StringConcatenation _builder = new StringConcatenation();
          _builder.append("code_");
          _builder.append(propertyName);
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
      final String name = property.getName();
      final String propertyName = PropertyUtils.getDatabaseName(property, name, additionnalName);
      StringConcatenation _builder = new StringConcatenation();
      _builder.append(propertyName);
      _builder.append(" ");
      Object _generateAttributType = PackageDatabaseScriptGenerator.generateAttributType(property);
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
    final String name = PropertyUtils.getName(property, property.getName(), "");
    final String idName = PropertyUtils.getDatabaseName(id, id.getName(), additionnalName);
    final String propertyName = PropertyUtils.getDatabaseName(property, name, idName);
    StringConcatenation _builder = new StringConcatenation();
    _builder.append(propertyName);
    _builder.append(" ");
    Object _generateAttributType = PackageDatabaseScriptGenerator.generateAttributType(id);
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
        String _xblockexpression_1 = null;
        {
          final String name = PropertyUtils.getDatabaseName(id, id.getName(), null);
          String _xifexpression = null;
          boolean _notEquals = (!Objects.equal(acc, ""));
          if (_notEquals) {
            StringConcatenation _builder = new StringConcatenation();
            _builder.append(", ");
            _builder.append(name);
            _xifexpression = (acc + _builder);
          } else {
            StringConcatenation _builder_1 = new StringConcatenation();
            _builder_1.append(name);
            _xifexpression = (acc + _builder_1);
          }
          _xblockexpression_1 = _xifexpression;
        }
        return _xblockexpression_1;
      };
      final String name = IterableExtensions.<Property, String>fold(ids, "", _function);
      final String schema = SqlClassifierUtils.generateSchemaName(clazz);
      StringConcatenation _builder = new StringConcatenation();
      _builder.newLine();
      _builder.append("ALTER TABLE ONLY ");
      _builder.append(schema);
      String _dBTableName = ClassifierUtils.getDBTableName(clazz);
      _builder.append(_dBTableName);
      _builder.newLineIfNotEmpty();
      _builder.append("\t");
      _builder.append("ADD CONSTRAINT ");
      String _dBTableName_1 = ClassifierUtils.getDBTableName(clazz);
      _builder.append(_dBTableName_1, "\t");
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
            String _xblockexpression_2 = null;
            {
              final String name = PropertyUtils.getDatabaseName(id, id.getName(), null);
              String _xifexpression_1 = null;
              boolean _notEquals = (!Objects.equal(acc, ""));
              if (_notEquals) {
                StringConcatenation _builder = new StringConcatenation();
                _builder.append(", ");
                _builder.append(name);
                _xifexpression_1 = (acc + _builder);
              } else {
                StringConcatenation _builder_1 = new StringConcatenation();
                _builder_1.append(name);
                _xifexpression_1 = (acc + _builder_1);
              }
              _xblockexpression_2 = _xifexpression_1;
            }
            return _xblockexpression_2;
          };
          final String idsName = IterableExtensions.<Property, String>fold(ids, "", _function);
          final String fromSchema = SqlClassifierUtils.generateSchemaName(fromClass);
          final String schema = SqlClassifierUtils.generateSchemaName(clazz);
          StringConcatenation _builder = new StringConcatenation();
          _builder.newLine();
          _builder.append("ALTER TABLE ONLY ");
          _builder.append(fromSchema);
          String _dBTableName = ClassifierUtils.getDBTableName(fromClass);
          _builder.append(_dBTableName);
          _builder.newLineIfNotEmpty();
          _builder.append("    ");
          _builder.append("ADD CONSTRAINT ");
          String _dBTableName_1 = ClassifierUtils.getDBTableName(fromClass);
          _builder.append(_dBTableName_1, "    ");
          _builder.append("_");
          String _dbName = Utils.toDbName(clazz.getName());
          _builder.append(_dbName, "    ");
          _builder.append("_ids_fkey");
          _builder.newLineIfNotEmpty();
          _builder.append("    ");
          _builder.append("FOREIGN KEY (");
          _builder.append(idsName, "    ");
          _builder.append(") REFERENCES ");
          _builder.append(schema, "    ");
          String _dBTableName_2 = ClassifierUtils.getDBTableName(clazz);
          _builder.append(_dBTableName_2, "    ");
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
  public static CharSequence generateForeignKeys(final Classifier clazz, final String additionnalName, final String tableName, final String fromSchema) {
    CharSequence _xblockexpression = null;
    {
      final Function1<Property, Boolean> _function = (Property attribut) -> {
        return Boolean.valueOf((((Utils.isEntity(attribut.getType()) && (!attribut.isMultivalued())) || (Utils.isValueObject(attribut.getType()) && (!attribut.isMultivalued()))) || (Utils.isNomenclature(attribut.getType()) && (!attribut.isMultivalued()))));
      };
      final Iterable<Property> attributes = IterableExtensions.<Property>filter(ClassifierUtils.getOwnedAttributes(clazz), _function);
      final EList<Interface> interfaces = clazz.getAllUsedInterfaces();
      StringConcatenation _builder = new StringConcatenation();
      final Function2<String, Property, String> _function_1 = (String acc, Property attribut) -> {
        String _xblockexpression_1 = null;
        {
          final String attributName = PropertyUtils.getDatabaseName(attribut, attribut.getName(), additionnalName);
          String _xifexpression = null;
          boolean _isEntity = Utils.isEntity(attribut.getType());
          if (_isEntity) {
            StringConcatenation _builder_1 = new StringConcatenation();
            String _generateForeignKey = PackageDatabaseScriptGenerator.generateForeignKey(attribut, additionnalName, tableName, fromSchema);
            _builder_1.append(_generateForeignKey);
            _xifexpression = (acc + _builder_1);
          } else {
            String _xifexpression_1 = null;
            boolean _isValueObject = Utils.isValueObject(attribut.getType());
            if (_isValueObject) {
              StringConcatenation _builder_2 = new StringConcatenation();
              Type _type = attribut.getType();
              Object _generateForeignKeys = PackageDatabaseScriptGenerator.generateForeignKeys(((Classifier) _type), attributName, tableName, fromSchema);
              _builder_2.append(_generateForeignKeys);
              _xifexpression_1 = (acc + _builder_2);
            } else {
              String _xifexpression_2 = null;
              boolean _isNomenclature = Utils.isNomenclature(attribut.getType());
              if (_isNomenclature) {
                StringConcatenation _builder_3 = new StringConcatenation();
                CharSequence _generateEnumForeignKey = PackageDatabaseScriptGenerator.generateEnumForeignKey(attribut, additionnalName, tableName, fromSchema);
                _builder_3.append(_generateEnumForeignKey);
                _xifexpression_2 = (acc + _builder_3);
              }
              _xifexpression_1 = _xifexpression_2;
            }
            _xifexpression = _xifexpression_1;
          }
          _xblockexpression_1 = _xifexpression;
        }
        return _xblockexpression_1;
      };
      String _fold = IterableExtensions.<Property, String>fold(attributes, "", _function_1);
      _builder.append(_fold);
      final Function2<String, Interface, String> _function_2 = (String acc, Interface interface_) -> {
        StringConcatenation _builder_1 = new StringConcatenation();
        Object _generateForeignKeys = PackageDatabaseScriptGenerator.generateForeignKeys(interface_, additionnalName, tableName, fromSchema);
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
  public static String generateForeignKey(final Property property, final String additionnalName, final String tableName, final String schema) {
    final Element fromClass = property.getOwner();
    final Type toClass = property.getType();
    if ((fromClass instanceof Classifier)) {
      if ((toClass instanceof Classifier)) {
        final String propName = PropertyUtils.getDatabaseName(property, property.getName(), additionnalName);
        final Iterable<Property> ids = ClassifierUtils.getId(((Classifier)toClass));
        final Function2<String, Property, String> _function = (String acc, Property id) -> {
          String _xblockexpression = null;
          {
            final String name = id.getName();
            String _databaseName = PropertyUtils.getDatabaseName(id, name, additionnalName);
            String _plus = (_databaseName + "_");
            String _databaseName_1 = PropertyUtils.getDatabaseName(property, property.getName(), null);
            final String propertyName = (_plus + _databaseName_1);
            String _xifexpression = null;
            boolean _notEquals = (!Objects.equal(acc, ""));
            if (_notEquals) {
              StringConcatenation _builder = new StringConcatenation();
              _builder.append(", ");
              _builder.append(propertyName);
              _xifexpression = (acc + _builder);
            } else {
              StringConcatenation _builder_1 = new StringConcatenation();
              _builder_1.append(propertyName);
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
            final String propertyName = PropertyUtils.getDatabaseName(field, name, null);
            String _xifexpression = null;
            boolean _notEquals = (!Objects.equal(acc, ""));
            if (_notEquals) {
              StringConcatenation _builder = new StringConcatenation();
              _builder.append(", ");
              _builder.append(propertyName);
              _xifexpression = (acc + _builder);
            } else {
              StringConcatenation _builder_1 = new StringConcatenation();
              _builder_1.append(propertyName);
              _xifexpression = (acc + _builder_1);
            }
            _xblockexpression = _xifexpression;
          }
          return _xblockexpression;
        };
        String fieldInClass = IterableExtensions.<Property, String>fold(ids, "", _function_1);
        final String toschema = SqlClassifierUtils.generateSchemaName(((Classifier)toClass));
        StringConcatenation _builder = new StringConcatenation();
        _builder.newLine();
        _builder.append("ALTER TABLE ONLY ");
        _builder.append(schema);
        _builder.append(tableName);
        _builder.newLineIfNotEmpty();
        _builder.append("    ");
        _builder.append("ADD CONSTRAINT ");
        _builder.append(tableName, "    ");
        _builder.append("_");
        _builder.append(propName, "    ");
        _builder.append("_ids_fkey");
        _builder.newLineIfNotEmpty();
        _builder.append("    ");
        _builder.append("FOREIGN KEY (");
        _builder.append(fieldToClass, "    ");
        _builder.append(") REFERENCES ");
        _builder.append(toschema, "    ");
        String _dBTableName = ClassifierUtils.getDBTableName(((Classifier)toClass));
        _builder.append(_dBTableName, "    ");
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
  public static CharSequence generateEnumForeignKey(final Property property, final String additionnalName, final String tableName, final String schema) {
    CharSequence _xblockexpression = null;
    {
      Type _type = property.getType();
      final Classifier type = ((Classifier) _type);
      final String propertyName = PropertyUtils.getDatabaseName(property, property.getName(), additionnalName);
      Element _owner = property.getOwner();
      final Classifier owner = ((Classifier) _owner);
      final String typeSchema = SqlClassifierUtils.generateSchemaName(type);
      StringConcatenation _builder = new StringConcatenation();
      _builder.newLine();
      _builder.append("ALTER TABLE ONLY ");
      _builder.append(schema);
      _builder.append(tableName);
      _builder.newLineIfNotEmpty();
      _builder.append("    ");
      _builder.append("ADD CONSTRAINT ");
      _builder.append(tableName, "    ");
      _builder.append("_");
      _builder.append(propertyName, "    ");
      _builder.append("_code_fkey");
      _builder.newLineIfNotEmpty();
      _builder.append("    ");
      _builder.append("FOREIGN KEY (code_");
      _builder.append(propertyName, "    ");
      _builder.append(") REFERENCES ");
      _builder.append(typeSchema, "    ");
      String _dBTableName = ClassifierUtils.getDBTableName(((Classifier) type));
      _builder.append(_dBTableName, "    ");
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
          String _generateMutilvaluedEntityTable = PackageDatabaseScriptGenerator.generateMutilvaluedEntityTable(property, fromClass);
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
      final String propertyName = PropertyUtils.getDatabaseName(property, property.getName(), null);
      String _dBTableName = ClassifierUtils.getDBTableName(fromClass);
      String _plus = (_dBTableName + "_");
      final String tableName = (_plus + propertyName);
      final Iterable<Property> ids = ClassifierUtils.getId(fromClass);
      final Function2<String, Property, String> _function = (String acc, Property id) -> {
        String _xblockexpression_1 = null;
        {
          final String idName = PropertyUtils.getDatabaseName(id, id.getName(), null);
          String _xifexpression = null;
          boolean _notEquals = (!Objects.equal(acc, ""));
          if (_notEquals) {
            StringConcatenation _builder = new StringConcatenation();
            _builder.append(", ");
            _builder.append(idName);
            _xifexpression = (acc + _builder);
          } else {
            StringConcatenation _builder_1 = new StringConcatenation();
            _builder_1.append(idName);
            _xifexpression = (acc + _builder_1);
          }
          _xblockexpression_1 = _xifexpression;
        }
        return _xblockexpression_1;
      };
      final String idsName = IterableExtensions.<Property, String>fold(ids, "", _function);
      final String schema = SqlClassifierUtils.generateSchemaName(fromClass);
      StringConcatenation _builder = new StringConcatenation();
      _builder.newLine();
      _builder.append("CREATE TABLE ");
      _builder.append(schema);
      _builder.append(tableName);
      _builder.append("(");
      _builder.newLineIfNotEmpty();
      _builder.append("\t");
      _builder.append(propertyName, "\t");
      _builder.append(" ");
      Object _generateAttributType = PackageDatabaseScriptGenerator.generateAttributType(property);
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
          String _plus_1 = (acc + _builder_1);
          StringConcatenation _builder_2 = new StringConcatenation();
          CharSequence _generateIdAttributeDefinition = PackageDatabaseScriptGenerator.generateIdAttributeDefinition(id, "");
          _builder_2.append(_generateIdAttributeDefinition);
          _xifexpression = (_plus_1 + _builder_2);
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
      _builder.append(schema);
      _builder.append(tableName);
      _builder.newLineIfNotEmpty();
      _builder.append("    ");
      _builder.append("ADD CONSTRAINT ");
      _builder.append(tableName, "    ");
      _builder.append("_ids_fkey");
      _builder.newLineIfNotEmpty();
      _builder.append("    ");
      _builder.append("FOREIGN KEY (");
      _builder.append(idsName, "    ");
      _builder.append(") REFERENCES ");
      _builder.append(schema, "    ");
      String _dBTableName_1 = ClassifierUtils.getDBTableName(fromClass);
      _builder.append(_dBTableName_1, "    ");
      _builder.append("(");
      _builder.append(idsName, "    ");
      _builder.append(");");
      _builder.newLineIfNotEmpty();
      _builder.append("    ");
      _builder.newLine();
      _builder.append("ALTER TABLE ONLY ");
      _builder.append(schema);
      _builder.append(tableName);
      _builder.newLineIfNotEmpty();
      _builder.append("    ");
      _builder.append("ADD CONSTRAINT ");
      _builder.append(tableName, "    ");
      _builder.append("_pkey PRIMARY KEY(");
      _builder.append(idsName, "    ");
      _builder.append(", ");
      _builder.append(propertyName, "    ");
      _builder.append(");");
      _builder.newLineIfNotEmpty();
      _xblockexpression = _builder;
    }
    return _xblockexpression;
  }
  
  /**
   * génère une table d'association pour un attribut de type entity multivalué
   */
  public static String generateMutilvaluedEntityTable(final Property property, final Classifier fromClass) {
    final Type type = property.getType();
    final Association association = property.getAssociation();
    if ((type instanceof Classifier)) {
      final String fieldName = PropertyUtils.getDatabaseName(property, property.getName(), null);
      String _dBTableName = ClassifierUtils.getDBTableName(fromClass);
      String _plus = (_dBTableName + "_");
      final String tableName = (_plus + fieldName);
      final Iterable<Property> idsProps = ClassifierUtils.getId(((Classifier)type));
      final Iterable<Property> idsOwner = ClassifierUtils.getId(fromClass);
      final Function2<String, Property, String> _function = (String acc, Property id) -> {
        String _xblockexpression = null;
        {
          final String propertyName = PropertyUtils.getDatabaseName(id, id.getName(), null);
          String _xifexpression = null;
          boolean _notEquals = (!Objects.equal(acc, ""));
          if (_notEquals) {
            StringConcatenation _builder = new StringConcatenation();
            _builder.append(", ");
            _builder.append(propertyName);
            _builder.append("_");
            String _dbName = Utils.toDbName(fromClass.getName());
            _builder.append(_dbName);
            _xifexpression = (acc + _builder);
          } else {
            StringConcatenation _builder_1 = new StringConcatenation();
            _builder_1.append(propertyName);
            _builder_1.append("_");
            String _dbName_1 = Utils.toDbName(fromClass.getName());
            _builder_1.append(_dbName_1);
            _xifexpression = (acc + _builder_1);
          }
          _xblockexpression = _xifexpression;
        }
        return _xblockexpression;
      };
      final String idsOwnerName = IterableExtensions.<Property, String>fold(idsOwner, "", _function);
      final Function2<String, Property, String> _function_1 = (String acc, Property id) -> {
        String _xblockexpression = null;
        {
          final String propertyName = PropertyUtils.getDatabaseName(id, id.getName(), null);
          String _xifexpression = null;
          boolean _notEquals = (!Objects.equal(acc, ""));
          if (_notEquals) {
            StringConcatenation _builder = new StringConcatenation();
            _builder.append(", ");
            _builder.append(propertyName);
            _xifexpression = (acc + _builder);
          } else {
            StringConcatenation _builder_1 = new StringConcatenation();
            _builder_1.append(propertyName);
            _xifexpression = (acc + _builder_1);
          }
          _xblockexpression = _xifexpression;
        }
        return _xblockexpression;
      };
      final String idsOwnerBaseName = IterableExtensions.<Property, String>fold(idsOwner, "", _function_1);
      final Function2<String, Property, String> _function_2 = (String acc, Property id) -> {
        String _xblockexpression = null;
        {
          final String propertyName = PropertyUtils.getDatabaseName(id, id.getName(), null);
          String _xifexpression = null;
          boolean _notEquals = (!Objects.equal(acc, ""));
          if (_notEquals) {
            StringConcatenation _builder = new StringConcatenation();
            _builder.append(", ");
            _builder.append(propertyName);
            _builder.append("_");
            _builder.append(fieldName);
            _xifexpression = (acc + _builder);
          } else {
            StringConcatenation _builder_1 = new StringConcatenation();
            _builder_1.append(propertyName);
            _builder_1.append("_");
            _builder_1.append(fieldName);
            _xifexpression = (acc + _builder_1);
          }
          _xblockexpression = _xifexpression;
        }
        return _xblockexpression;
      };
      final String idsPropsName = IterableExtensions.<Property, String>fold(idsProps, "", _function_2);
      final Function2<String, Property, String> _function_3 = (String acc, Property id) -> {
        String _xblockexpression = null;
        {
          final String propertyName = PropertyUtils.getDatabaseName(id, id.getName(), null);
          String _xifexpression = null;
          boolean _notEquals = (!Objects.equal(acc, ""));
          if (_notEquals) {
            StringConcatenation _builder = new StringConcatenation();
            _builder.append(", ");
            _builder.append(propertyName);
            _xifexpression = (acc + _builder);
          } else {
            StringConcatenation _builder_1 = new StringConcatenation();
            _builder_1.append(propertyName);
            _xifexpression = (acc + _builder_1);
          }
          _xblockexpression = _xifexpression;
        }
        return _xblockexpression;
      };
      final String idsPropsBaseName = IterableExtensions.<Property, String>fold(idsProps, "", _function_3);
      final String schema = SqlClassifierUtils.generateSchemaName(fromClass);
      final String typeSchema = SqlClassifierUtils.generateSchemaName(((Classifier)type));
      StringConcatenation _builder = new StringConcatenation();
      _builder.newLine();
      _builder.append("CREATE TABLE ");
      _builder.append(schema);
      _builder.append(tableName);
      _builder.append("(");
      _builder.newLineIfNotEmpty();
      _builder.append("\t");
      final Function2<String, Property, String> _function_4 = (String acc, Property id) -> {
        String _xifexpression = null;
        boolean _notEquals = (!Objects.equal(acc, ""));
        if (_notEquals) {
          StringConcatenation _builder_1 = new StringConcatenation();
          _builder_1.append(",");
          _builder_1.newLine();
          String _plus_1 = (acc + _builder_1);
          StringConcatenation _builder_2 = new StringConcatenation();
          CharSequence _generateMultiIdAttributeDef = PackageDatabaseScriptGenerator.generateMultiIdAttributeDef(id, fieldName);
          _builder_2.append(_generateMultiIdAttributeDef);
          _xifexpression = (_plus_1 + _builder_2);
        } else {
          StringConcatenation _builder_3 = new StringConcatenation();
          CharSequence _generateMultiIdAttributeDef_1 = PackageDatabaseScriptGenerator.generateMultiIdAttributeDef(id, fieldName);
          _builder_3.append(_generateMultiIdAttributeDef_1);
          _xifexpression = (acc + _builder_3);
        }
        return _xifexpression;
      };
      String _fold = IterableExtensions.<Property, String>fold(idsProps, "", _function_4);
      _builder.append(_fold, "\t");
      _builder.append(",");
      _builder.newLineIfNotEmpty();
      _builder.append("\t");
      final Function2<String, Property, String> _function_5 = (String acc, Property id) -> {
        String _xifexpression = null;
        boolean _notEquals = (!Objects.equal(acc, ""));
        if (_notEquals) {
          StringConcatenation _builder_1 = new StringConcatenation();
          _builder_1.append(",");
          _builder_1.newLine();
          String _plus_1 = (acc + _builder_1);
          StringConcatenation _builder_2 = new StringConcatenation();
          CharSequence _generateMultiIdAttributeDefinition = PackageDatabaseScriptGenerator.generateMultiIdAttributeDefinition(id, "");
          _builder_2.append(_generateMultiIdAttributeDefinition);
          _xifexpression = (_plus_1 + _builder_2);
        } else {
          StringConcatenation _builder_3 = new StringConcatenation();
          CharSequence _generateMultiIdAttributeDefinition_1 = PackageDatabaseScriptGenerator.generateMultiIdAttributeDefinition(id, "");
          _builder_3.append(_generateMultiIdAttributeDefinition_1);
          _xifexpression = (acc + _builder_3);
        }
        return _xifexpression;
      };
      String _fold_1 = IterableExtensions.<Property, String>fold(idsOwner, "", _function_5);
      _builder.append(_fold_1, "\t");
      _builder.newLineIfNotEmpty();
      _builder.append(");");
      _builder.newLine();
      _builder.newLine();
      _builder.append("ALTER TABLE ONLY ");
      _builder.append(schema);
      _builder.append(tableName);
      _builder.newLineIfNotEmpty();
      _builder.append("    ");
      _builder.append("ADD CONSTRAINT ");
      _builder.append(tableName, "    ");
      _builder.append("_");
      String _dbName = Utils.toDbName(fromClass.getName());
      _builder.append(_dbName, "    ");
      _builder.append("_ids_fkey");
      _builder.newLineIfNotEmpty();
      _builder.append("    ");
      _builder.append("FOREIGN KEY (");
      _builder.append(idsOwnerName, "    ");
      _builder.append(") REFERENCES ");
      _builder.append(schema, "    ");
      String _dBTableName_1 = ClassifierUtils.getDBTableName(fromClass);
      _builder.append(_dBTableName_1, "    ");
      _builder.append("(");
      _builder.append(idsOwnerBaseName, "    ");
      _builder.append(");");
      _builder.newLineIfNotEmpty();
      _builder.append("    ");
      _builder.newLine();
      _builder.append("ALTER TABLE ONLY ");
      _builder.append(schema);
      _builder.append(tableName);
      _builder.newLineIfNotEmpty();
      _builder.append("    ");
      _builder.append("ADD CONSTRAINT ");
      _builder.append(tableName, "    ");
      _builder.append("_");
      String _dbName_1 = Utils.toDbName(((Classifier)type).getName());
      _builder.append(_dbName_1, "    ");
      _builder.append("_ids_fkey");
      _builder.newLineIfNotEmpty();
      _builder.append("    ");
      _builder.append("FOREIGN KEY (");
      _builder.append(idsPropsName, "    ");
      _builder.append(") REFERENCES ");
      _builder.append(typeSchema, "    ");
      String _dBTableName_2 = ClassifierUtils.getDBTableName(((Classifier)type));
      _builder.append(_dBTableName_2, "    ");
      _builder.append("(");
      _builder.append(idsPropsBaseName, "    ");
      _builder.append(");");
      _builder.newLineIfNotEmpty();
      _builder.append("    ");
      _builder.newLine();
      _builder.append("ALTER TABLE ONLY ");
      _builder.append(schema);
      _builder.append(tableName);
      _builder.newLineIfNotEmpty();
      _builder.append("    ");
      _builder.append("ADD CONSTRAINT ");
      _builder.append(tableName, "    ");
      _builder.append("_pkey PRIMARY KEY(");
      _builder.append(idsOwnerName, "    ");
      _builder.append(", ");
      _builder.append(idsPropsName, "    ");
      _builder.append(");");
      _builder.newLineIfNotEmpty();
      final String data = _builder.toString();
      if ((association != null)) {
        final Function1<Property, Boolean> _function_6 = (Property mem) -> {
          Type _type = mem.getType();
          return Boolean.valueOf(Objects.equal(_type, fromClass));
        };
        final Iterable<Property> member = IterableExtensions.<Property>filter(association.getMemberEnds(), _function_6);
        if ((member != null)) {
          final Property end = ((Property[])Conversions.unwrapArray(member, Property.class))[0];
          boolean _isMultivalued = end.isMultivalued();
          if (_isMultivalued) {
            return data;
          }
        }
        StringConcatenation _builder_1 = new StringConcatenation();
        return _builder_1.toString();
      } else {
        return data;
      }
    }
    return null;
  }
  
  /**
   * génère la définition d'un attribut id
   */
  public static CharSequence generateMultiIdAttributeDef(final Property property, final String additionnalName) {
    CharSequence _xblockexpression = null;
    {
      final String propertyName = PropertyUtils.getDatabaseName(property, property.getName(), "");
      final Element owner = property.getOwner();
      CharSequence _xifexpression = null;
      if ((owner instanceof Classifier)) {
        CharSequence _xifexpression_1 = null;
        if (((!Objects.equal(additionnalName, "")) && (additionnalName != null))) {
          StringConcatenation _builder = new StringConcatenation();
          _builder.append(propertyName);
          _builder.append("_");
          _builder.append(additionnalName);
          _builder.append(" ");
          Object _generateAttributType = PackageDatabaseScriptGenerator.generateAttributType(property);
          _builder.append(_generateAttributType);
          CharSequence _generateStringLength = PackageDatabaseScriptGenerator.generateStringLength(property);
          _builder.append(_generateStringLength);
          _builder.append(" NOT NULL");
          _xifexpression_1 = _builder;
        } else {
          StringConcatenation _builder_1 = new StringConcatenation();
          _builder_1.append(propertyName);
          _builder_1.append(" ");
          Object _generateAttributType_1 = PackageDatabaseScriptGenerator.generateAttributType(property);
          _builder_1.append(_generateAttributType_1);
          CharSequence _generateStringLength_1 = PackageDatabaseScriptGenerator.generateStringLength(property);
          _builder_1.append(_generateStringLength_1);
          _builder_1.append(" NOT NULL");
          _xifexpression_1 = _builder_1;
        }
        _xifexpression = _xifexpression_1;
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
      final String name = property.getName();
      final String propertyName = PropertyUtils.getDatabaseName(property, name, additionnalName);
      final Element owner = property.getOwner();
      CharSequence _xifexpression = null;
      if ((owner instanceof Classifier)) {
        StringConcatenation _builder = new StringConcatenation();
        _builder.append(propertyName);
        _builder.append("_");
        String _dbName = Utils.toDbName(((Classifier)owner).getName());
        _builder.append(_dbName);
        _builder.append(" ");
        Object _generateAttributType = PackageDatabaseScriptGenerator.generateAttributType(property);
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
          final String name = PropertyUtils.getDatabaseName(property, property.getName(), null);
          final String prefix = ClassifierUtils.getDBTableName(fromClass);
          final String tableName = ((prefix + "_") + name);
          final Iterable<Property> idsOwner = ClassifierUtils.getId(fromClass);
          final Function2<String, Property, String> _function = (String acc, Property id) -> {
            String _xblockexpression_2 = null;
            {
              final String idName = PropertyUtils.getDatabaseName(id, id.getName(), null);
              String _xifexpression_1 = null;
              boolean _notEquals = (!Objects.equal(acc, ""));
              if (_notEquals) {
                StringConcatenation _builder = new StringConcatenation();
                _builder.append(", ");
                _builder.append(idName);
                _xifexpression_1 = (acc + _builder);
              } else {
                StringConcatenation _builder_1 = new StringConcatenation();
                _builder_1.append(idName);
                _xifexpression_1 = (acc + _builder_1);
              }
              _xblockexpression_2 = _xifexpression_1;
            }
            return _xblockexpression_2;
          };
          final String idsName = IterableExtensions.<Property, String>fold(idsOwner, "", _function);
          final String attr = Utils.getListStringComma(PackageDatabaseScriptGenerator.getAttributList(((Classifier)type), CollectionLiterals.<String>newArrayList(), name));
          String pkeys = idsName;
          if (((attr != null) && (!Objects.equal(attr, "")))) {
            String _pkeys = pkeys;
            String _listStringComma = Utils.getListStringComma(PackageDatabaseScriptGenerator.getAttributList(((Classifier)type), CollectionLiterals.<String>newArrayList(), name));
            String _plus = (", " + _listStringComma);
            pkeys = (_pkeys + _plus);
          }
          final String schema = SqlClassifierUtils.generateSchemaName(fromClass);
          StringConcatenation _builder = new StringConcatenation();
          _builder.newLine();
          _builder.append("CREATE TABLE ");
          _builder.append(schema);
          _builder.append(tableName);
          _builder.append("(");
          _builder.newLineIfNotEmpty();
          _builder.append("\t");
          CharSequence _generateAttributes = PackageDatabaseScriptGenerator.generateAttributes(((Classifier)type), name, fromClass, Boolean.valueOf(false));
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
              String _plus_1 = (acc + _builder_1);
              StringConcatenation _builder_2 = new StringConcatenation();
              CharSequence _generateIdAttributeDefinition = PackageDatabaseScriptGenerator.generateIdAttributeDefinition(id, "");
              _builder_2.append(_generateIdAttributeDefinition);
              _xifexpression_1 = (_plus_1 + _builder_2);
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
          CharSequence _generateForeignKeys = PackageDatabaseScriptGenerator.generateForeignKeys(((Classifier)type), name, tableName, schema);
          _builder.append(_generateForeignKeys);
          _builder.newLineIfNotEmpty();
          _builder.newLine();
          _builder.append("ALTER TABLE ONLY ");
          _builder.append(schema);
          _builder.append(tableName);
          _builder.newLineIfNotEmpty();
          _builder.append("    ");
          _builder.append("ADD CONSTRAINT ");
          _builder.append(tableName, "    ");
          _builder.append("_ids_fkey");
          _builder.newLineIfNotEmpty();
          _builder.append("    ");
          _builder.append("FOREIGN KEY (");
          _builder.append(idsName, "    ");
          _builder.append(") REFERENCES ");
          _builder.append(schema, "    ");
          String _dBTableName = ClassifierUtils.getDBTableName(fromClass);
          _builder.append(_dBTableName, "    ");
          _builder.append("(");
          _builder.append(idsName, "    ");
          _builder.append(");");
          _builder.newLineIfNotEmpty();
          _builder.append("    ");
          _builder.newLine();
          _builder.append("ALTER TABLE ONLY ");
          _builder.append(schema);
          _builder.append(tableName);
          _builder.newLineIfNotEmpty();
          _builder.append("    ");
          _builder.append("ADD CONSTRAINT ");
          _builder.append(tableName, "    ");
          _builder.append("_pkey PRIMARY KEY(");
          _builder.append(pkeys, "    ");
          _builder.append(");");
          _builder.newLineIfNotEmpty();
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
        final String propertyName = PropertyUtils.getDatabaseName(attribut, attribut.getName(), additionnalName);
        boolean _isEntity = Utils.isEntity(attrType);
        if (_isEntity) {
          final Iterable<Property> ids = ClassifierUtils.getId(((Classifier) attrType));
          final Consumer<Property> _function_1 = (Property id) -> {
            final String idpropName = PropertyUtils.getDatabaseName(id, id.getName(), additionnalName);
            final String propName = PropertyUtils.getDatabaseName(attribut, attribut.getName(), null);
            String idName = ((idpropName + "_") + propName);
            names.add(idName);
          };
          ids.forEach(_function_1);
        } else {
          boolean _isNomenclature = Utils.isNomenclature(attrType);
          if (_isNomenclature) {
            names.add(("code_" + propertyName));
          } else {
            boolean _isValueObject = Utils.isValueObject(attrType);
            if (_isValueObject) {
              PackageDatabaseScriptGenerator.getAttributList(((Classifier) attrType), names, propertyName);
            } else {
              names.add(propertyName);
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
          final String propertyName = PropertyUtils.getDatabaseName(property, property.getName(), null);
          String _dBTableName = ClassifierUtils.getDBTableName(fromClass);
          String _plus = (_dBTableName + "_");
          final String tableName = (_plus + propertyName);
          final Iterable<Property> idsOwner = ClassifierUtils.getId(fromClass);
          final Function2<String, Property, String> _function = (String acc, Property id) -> {
            String _xblockexpression_2 = null;
            {
              final String idName = PropertyUtils.getDatabaseName(id, id.getName(), null);
              String _xifexpression_1 = null;
              boolean _notEquals = (!Objects.equal(acc, ""));
              if (_notEquals) {
                StringConcatenation _builder = new StringConcatenation();
                _builder.append(", ");
                _builder.append(idName);
                _xifexpression_1 = (acc + _builder);
              } else {
                StringConcatenation _builder_1 = new StringConcatenation();
                _builder_1.append(idName);
                _xifexpression_1 = (acc + _builder_1);
              }
              _xblockexpression_2 = _xifexpression_1;
            }
            return _xblockexpression_2;
          };
          final String idsName = IterableExtensions.<Property, String>fold(idsOwner, "", _function);
          final String sqlType = TypeUtils.getEnumType(((Classifier)type));
          final String schema = SqlClassifierUtils.generateSchemaName(fromClass);
          final String typeSchema = SqlClassifierUtils.generateSchemaName(((Classifier)type));
          StringConcatenation _builder = new StringConcatenation();
          _builder.newLine();
          _builder.append("CREATE TABLE ");
          _builder.append(schema);
          _builder.append(tableName);
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
              String _plus_1 = (acc + _builder_1);
              StringConcatenation _builder_2 = new StringConcatenation();
              CharSequence _generateIdAttributeDefinition = PackageDatabaseScriptGenerator.generateIdAttributeDefinition(id, "");
              _builder_2.append(_generateIdAttributeDefinition);
              _xifexpression_1 = (_plus_1 + _builder_2);
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
          CharSequence _generateForeignKeys = PackageDatabaseScriptGenerator.generateForeignKeys(((Classifier)type), Utils.toDbName(property.getName()), tableName, schema);
          _builder.append(_generateForeignKeys);
          _builder.newLineIfNotEmpty();
          _builder.newLine();
          _builder.append("ALTER TABLE ONLY ");
          _builder.append(schema);
          _builder.append(tableName);
          _builder.newLineIfNotEmpty();
          _builder.append("    ");
          _builder.append("ADD CONSTRAINT ");
          _builder.append(tableName, "    ");
          _builder.append("_ids_fkey");
          _builder.newLineIfNotEmpty();
          _builder.append("    ");
          _builder.append("FOREIGN KEY (");
          _builder.append(idsName, "    ");
          _builder.append(") REFERENCES ");
          _builder.append(schema, "    ");
          String _dBTableName_1 = ClassifierUtils.getDBTableName(fromClass);
          _builder.append(_dBTableName_1, "    ");
          _builder.append("(");
          _builder.append(idsName, "    ");
          _builder.append(");");
          _builder.newLineIfNotEmpty();
          _builder.newLine();
          _builder.append("ALTER TABLE ONLY ");
          _builder.append(schema);
          _builder.append(tableName);
          _builder.newLineIfNotEmpty();
          _builder.append("    ");
          _builder.append("ADD CONSTRAINT ");
          _builder.append(tableName, "    ");
          _builder.append("_code_fkey");
          _builder.newLineIfNotEmpty();
          _builder.append("    ");
          _builder.append("FOREIGN KEY (code) REFERENCES ");
          _builder.append(typeSchema, "    ");
          String _dBTableName_2 = ClassifierUtils.getDBTableName(((Classifier)type));
          _builder.append(_dBTableName_2, "    ");
          _builder.append("(code);");
          _builder.newLineIfNotEmpty();
          _builder.newLine();
          _builder.append("ALTER TABLE ONLY ");
          _builder.append(schema);
          _builder.append(tableName);
          _builder.newLineIfNotEmpty();
          _builder.append("    ");
          _builder.append("ADD CONSTRAINT ");
          _builder.append(tableName, "    ");
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
          final Object startWith = Utils.getSequenceStartWith(property);
          final Object inscrementBy = Utils.getSequenceIncrementBy(property);
          final Object hasMaxValue = Utils.getSequenceHasMaxValue(property);
          final Object maxValue = Utils.getSequenceMaxValue(property);
          if ((Objects.equal(hasMaxValue, Boolean.valueOf(true)) || (!Objects.equal(maxValue, Integer.valueOf(0))))) {
            StringConcatenation _builder_2 = new StringConcatenation();
            _builder_2.append("MAXVALUE ");
            _builder_2.append(maxValue);
            maxVal = _builder_2.toString();
          }
          final Object hasMinValue = Utils.getSequenceHasMinValue(property);
          final Object minValue = Utils.getSequenceMinValue(property);
          if ((Objects.equal(hasMinValue, Boolean.valueOf(true)) || (!Objects.equal(minValue, Integer.valueOf(0))))) {
            StringConcatenation _builder_3 = new StringConcatenation();
            _builder_3.append("MINVALUE ");
            _builder_3.append(minValue);
            minVal = _builder_3.toString();
          }
          final Object cache = Utils.getSequenceCache(property);
          final Object isCycle = Utils.getSequenceCycle(property);
          String cycle = "NO CYCLE";
          boolean _equals = Objects.equal(isCycle, Boolean.valueOf(true));
          if (_equals) {
            cycle = "CYCLE";
          }
          final String propertyName = PropertyUtils.getDatabaseName(property, property.getName(), null);
          String _dBTableName = ClassifierUtils.getDBTableName(((Classifier)owner));
          String _plus = (_dBTableName + "_");
          final String name = (_plus + propertyName);
          final String schema = SqlClassifierUtils.generateSchemaName(((Classifier)owner));
          final String schemaseq = SqlClassifierUtils.generateSchemaName(((Classifier)owner));
          StringConcatenation _builder_4 = new StringConcatenation();
          _builder_4.append("CREATE SEQUENCE ");
          _builder_4.append(schema);
          _builder_4.append(name);
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
          _builder_4.append(schema);
          _builder_4.append(name);
          _builder_4.append("_seq");
          _builder_4.newLineIfNotEmpty();
          _builder_4.append("\t");
          _builder_4.append("OWNED BY ");
          _builder_4.append(schema, "\t");
          String _dBTableName_1 = ClassifierUtils.getDBTableName(((Classifier)owner));
          _builder_4.append(_dBTableName_1, "\t");
          _builder_4.append(".");
          _builder_4.append(propertyName, "\t");
          _builder_4.append(";");
          _builder_4.newLineIfNotEmpty();
          _builder_4.newLine();
          _builder_4.append("ALTER TABLE ONLY ");
          _builder_4.append(schema);
          String _dBTableName_2 = ClassifierUtils.getDBTableName(((Classifier)owner));
          _builder_4.append(_dBTableName_2);
          _builder_4.append(" ");
          _builder_4.newLineIfNotEmpty();
          _builder_4.append("\t");
          _builder_4.append("ALTER COLUMN ");
          _builder_4.append(propertyName, "\t");
          _builder_4.append(" ");
          _builder_4.newLineIfNotEmpty();
          _builder_4.append("\t");
          _builder_4.append("SET DEFAULT nextval(\'");
          _builder_4.append(schemaseq, "\t");
          _builder_4.append(name, "\t");
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
    CharSequence _xblockexpression = null;
    {
      final String schema = SqlClassifierUtils.generateSchemaName(clazz);
      StringConcatenation _builder = new StringConcatenation();
      _builder.newLine();
      _builder.append("CREATE TABLE ");
      _builder.append(schema);
      String _dBTableName = AssociationClassUtils.getDBTableName(clazz);
      _builder.append(_dBTableName);
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
      _builder.append(schema);
      String _dBTableName_1 = AssociationClassUtils.getDBTableName(clazz);
      _builder.append(_dBTableName_1);
      _builder.newLineIfNotEmpty();
      _builder.append("\t");
      _builder.append("ADD CONSTRAINT ");
      String _dBTableName_2 = AssociationClassUtils.getDBTableName(clazz);
      _builder.append(_dBTableName_2, "\t");
      _builder.append("_pkey PRIMARY KEY (");
      String _listComma = Utils.getListComma(PackageDatabaseScriptGenerator.getAssociationAttributList(clazz, CollectionLiterals.<String>newArrayList(), ""));
      _builder.append(_listComma, "\t");
      _builder.append(");");
      _builder.newLineIfNotEmpty();
      _xblockexpression = _builder;
    }
    return _xblockexpression;
  }
  
  public static ArrayList<String> getAssociationAttributList(final AssociationClass type, final ArrayList<String> names, final String additionnalName) {
    final EList<Property> attributes = type.getMemberEnds();
    final Consumer<Property> _function = (Property attribut) -> {
      final Type attrType = attribut.getType();
      final String propertyName = PropertyUtils.getDatabaseName(attribut, attribut.getName(), additionnalName);
      boolean _isEntity = Utils.isEntity(attrType);
      if (_isEntity) {
        final Iterable<Property> ids = ClassifierUtils.getId(((Classifier) attrType));
        final Consumer<Property> _function_1 = (Property id) -> {
          final String idpropName = PropertyUtils.getDatabaseName(id, id.getName(), additionnalName);
          String _dbName = Utils.toDbName(attribut.getName());
          String idName = ((idpropName + "_") + _dbName);
          names.add(idName);
        };
        ids.forEach(_function_1);
      } else {
        boolean _isNomenclature = Utils.isNomenclature(attrType);
        if (_isNomenclature) {
          names.add(("code_" + propertyName));
        } else {
          boolean _isValueObject = Utils.isValueObject(attrType);
          if (_isValueObject) {
            PackageDatabaseScriptGenerator.getAttributList(((Classifier) attrType), names, propertyName);
          } else {
            names.add(propertyName);
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
  
  public static CharSequence generateAssociationForeignKeys(final Property property, final AssociationClass fromClass) {
    CharSequence _xblockexpression = null;
    {
      final Type type = property.getType();
      final String schema = SqlClassifierUtils.generateSchemaName(fromClass);
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
            CharSequence _generateForeignKeys = PackageDatabaseScriptGenerator.generateForeignKeys(((Classifier)type), Utils.toDbName(property.getName()), AssociationClassUtils.getDBTableName(fromClass), schema);
            _builder_1.append(_generateForeignKeys);
            return _builder_1.toString();
          } else {
            boolean _isNomenclature = Utils.isNomenclature(type);
            if (_isNomenclature) {
              StringConcatenation _builder_2 = new StringConcatenation();
              String _generateAssociationForeignKeysEnum = PackageDatabaseScriptGenerator.generateAssociationForeignKeysEnum(property, fromClass, schema);
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
  
  public static String generateAssociationForeignKeysEntity(final Property property, final AssociationClass fromClass) {
    final Type type = property.getType();
    if ((type instanceof Classifier)) {
      final Iterable<Property> ids = ClassifierUtils.getId(((Classifier)type));
      final Function2<String, Property, String> _function = (String acc, Property id) -> {
        String _xblockexpression = null;
        {
          final String idName = PropertyUtils.getDatabaseName(id, id.getName(), null);
          String _xifexpression = null;
          boolean _notEquals = (!Objects.equal(acc, ""));
          if (_notEquals) {
            StringConcatenation _builder = new StringConcatenation();
            _builder.append(", ");
            _builder.append(idName);
            _xifexpression = (acc + _builder);
          } else {
            StringConcatenation _builder_1 = new StringConcatenation();
            _builder_1.append(idName);
            _xifexpression = (acc + _builder_1);
          }
          _xblockexpression = _xifexpression;
        }
        return _xblockexpression;
      };
      final String idsName = IterableExtensions.<Property, String>fold(ids, "", _function);
      final Function2<String, Property, String> _function_1 = (String acc, Property id) -> {
        String _xblockexpression = null;
        {
          final String idName = PropertyUtils.getDatabaseName(id, id.getName(), null);
          final String propName = PropertyUtils.getDatabaseName(property, property.getName(), idName);
          String _xifexpression = null;
          boolean _notEquals = (!Objects.equal(acc, ""));
          if (_notEquals) {
            StringConcatenation _builder = new StringConcatenation();
            _builder.append(", ");
            _builder.append(propName);
            _xifexpression = (acc + _builder);
          } else {
            StringConcatenation _builder_1 = new StringConcatenation();
            _builder_1.append(propName);
            _xifexpression = (acc + _builder_1);
          }
          _xblockexpression = _xifexpression;
        }
        return _xblockexpression;
      };
      final String idsNameInClass = IterableExtensions.<Property, String>fold(ids, "", _function_1);
      final String propName = PropertyUtils.getDatabaseName(property, property.getName(), null);
      final String schema = SqlClassifierUtils.generateSchemaName(fromClass);
      final String typeSchema = SqlClassifierUtils.generateSchemaName(((Classifier)type));
      StringConcatenation _builder = new StringConcatenation();
      _builder.newLine();
      _builder.append("ALTER TABLE ONLY ");
      _builder.append(schema);
      String _dBTableName = AssociationClassUtils.getDBTableName(fromClass);
      _builder.append(_dBTableName);
      _builder.newLineIfNotEmpty();
      _builder.append("\t");
      _builder.append("ADD CONSTRAINT ");
      String _dBTableName_1 = AssociationClassUtils.getDBTableName(fromClass);
      _builder.append(_dBTableName_1, "\t");
      _builder.append("_");
      _builder.append(propName, "\t");
      _builder.append("_ids_fkey");
      _builder.newLineIfNotEmpty();
      _builder.append("\t");
      _builder.append("FOREIGN KEY (");
      _builder.append(idsNameInClass, "\t");
      _builder.append(") REFERENCES ");
      _builder.append(typeSchema, "\t");
      String _dBTableName_2 = ClassifierUtils.getDBTableName(((Classifier)type));
      _builder.append(_dBTableName_2, "\t");
      _builder.append("(");
      _builder.append(idsName, "\t");
      _builder.append(");");
      _builder.newLineIfNotEmpty();
      return _builder.toString();
    }
    StringConcatenation _builder_1 = new StringConcatenation();
    return _builder_1.toString();
  }
  
  public static String generateAssociationForeignKeysEnum(final Property property, final AssociationClass fromClass, final String schema) {
    final Type type = property.getType();
    if ((type instanceof Classifier)) {
      String _databaseName = PropertyUtils.getDatabaseName(property, property.getName(), null);
      final String propName = ("code_" + _databaseName);
      final String typeSchema = SqlClassifierUtils.generateSchemaName(((Classifier)type));
      StringConcatenation _builder = new StringConcatenation();
      _builder.newLine();
      _builder.append("ALTER TABLE ONLY ");
      _builder.append(schema);
      String _dBTableName = AssociationClassUtils.getDBTableName(fromClass);
      _builder.append(_dBTableName);
      _builder.newLineIfNotEmpty();
      _builder.append("\t");
      _builder.append("ADD CONSTRAINT ");
      String _dBTableName_1 = AssociationClassUtils.getDBTableName(fromClass);
      _builder.append(_dBTableName_1, "\t");
      _builder.append("_");
      _builder.append(propName, "\t");
      _builder.append("_code_fkey");
      _builder.newLineIfNotEmpty();
      _builder.append("\t");
      _builder.append("FOREIGN KEY (");
      _builder.append(propName, "\t");
      _builder.append(") REFERENCES ");
      _builder.append(typeSchema, "\t");
      String _dBTableName_2 = ClassifierUtils.getDBTableName(((Classifier)type));
      _builder.append(_dBTableName_2, "\t");
      _builder.append("(code);");
      _builder.newLineIfNotEmpty();
      return _builder.toString();
    }
    StringConcatenation _builder_1 = new StringConcatenation();
    return _builder_1.toString();
  }
  
  public static Object generateAttributType(final Property property) {
    Object type = TypeUtils.getDatabaseType(property.getType());
    boolean _equals = Objects.equal(type, "character");
    if (_equals) {
      final Object length = Utils.getAttributeLength(property);
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
      final String schema = SqlClassifierUtils.generateSchemaName(clazz);
      final Collection<Property> attributes = ClassifierUtils.getOwnedAttributes(clazz);
      StringConcatenation _builder = new StringConcatenation();
      _builder.newLine();
      _builder.append("CREATE TABLE ");
      _builder.append(schema);
      String _dBTableName = ClassifierUtils.getDBTableName(clazz);
      _builder.append(_dBTableName);
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
      _builder.append(schema);
      String _dBTableName_1 = ClassifierUtils.getDBTableName(clazz);
      _builder.append(_dBTableName_1);
      _builder.newLineIfNotEmpty();
      _builder.append("\t");
      _builder.append("ADD CONSTRAINT ");
      String _dBTableName_2 = ClassifierUtils.getDBTableName(clazz);
      _builder.append(_dBTableName_2, "\t");
      _builder.append("_pkey PRIMARY KEY (code);");
      _builder.newLineIfNotEmpty();
      {
        if ((!hasCode)) {
          _builder.newLine();
          _builder.append("CREATE SEQUENCE ");
          _builder.append(schema);
          String _dBTableName_3 = ClassifierUtils.getDBTableName(clazz);
          _builder.append(_dBTableName_3);
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
          _builder.append(schema);
          String _dBTableName_4 = ClassifierUtils.getDBTableName(clazz);
          _builder.append(_dBTableName_4);
          _builder.append("_code_seq");
          _builder.newLineIfNotEmpty();
          _builder.append("\t");
          _builder.append("OWNED BY ");
          _builder.append(schema, "\t");
          String _dBTableName_5 = ClassifierUtils.getDBTableName(clazz);
          _builder.append(_dBTableName_5, "\t");
          _builder.append(".code;");
          _builder.newLineIfNotEmpty();
        }
      }
      _builder.newLine();
      final Function2<String, Property, String> _function = (String acc, Property att) -> {
        StringConcatenation _builder_1 = new StringConcatenation();
        CharSequence _generateInsertValue = PackageDatabaseScriptGenerator.generateInsertValue(att, clazz);
        _builder_1.append(_generateInsertValue);
        return (acc + _builder_1);
      };
      String _fold = IterableExtensions.<Property, String>fold(attributes, "", _function);
      _builder.append(_fold);
      _builder.newLineIfNotEmpty();
      _xblockexpression = _builder;
    }
    return _xblockexpression;
  }
  
  public static CharSequence generateInsertValue(final Property prop, final Classifier owner) {
    CharSequence _xblockexpression = null;
    {
      final String schema = SqlClassifierUtils.generateSchemaName(owner);
      final boolean hasCode = ClassifierUtils.isEnumWithCode(owner);
      final Object code = Utils.getNomenclatureCode(prop);
      Object libelle = Utils.getNomenclatureLibelle(prop);
      CharSequence _xifexpression = null;
      if ((!hasCode)) {
        StringConcatenation _builder = new StringConcatenation();
        _builder.append("INSERT INTO ");
        _builder.append(schema);
        String _dBTableName = ClassifierUtils.getDBTableName(owner);
        _builder.append(_dBTableName);
        _builder.append(" (LIBELLE) VALUES (");
        _builder.append(libelle);
        _builder.append(");");
        _builder.newLineIfNotEmpty();
        _xifexpression = _builder;
      } else {
        StringConcatenation _builder_1 = new StringConcatenation();
        _builder_1.append("INSERT INTO ");
        _builder_1.append(schema);
        String _dBTableName_1 = ClassifierUtils.getDBTableName(owner);
        _builder_1.append(_dBTableName_1);
        _builder_1.append(" (CODE, LIBELLE) VALUES (");
        _builder_1.append(code);
        _builder_1.append(", \'");
        _builder_1.append(libelle);
        _builder_1.append("\');");
        _builder_1.newLineIfNotEmpty();
        _xifexpression = _builder_1;
      }
      _xblockexpression = _xifexpression;
    }
    return _xblockexpression;
  }
}
