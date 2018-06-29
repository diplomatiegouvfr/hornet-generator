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
package fr.gouv.diplomatie.papyrus.codegen.java.xtend.classifier;

import com.google.common.base.Objects;
import fr.gouv.diplomatie.papyrus.codegen.core.utils.ClassifierUtils;
import fr.gouv.diplomatie.papyrus.codegen.core.utils.PropertyUtils;
import fr.gouv.diplomatie.papyrus.codegen.core.utils.Utils;
import fr.gouv.diplomatie.papyrus.codegen.java.generators.GeneratorUtils;
import fr.gouv.diplomatie.papyrus.codegen.java.transformations.ProjectJPAEntityElementsCreator;
import fr.gouv.diplomatie.papyrus.codegen.java.utils.JavaClassifierUtils;
import fr.gouv.diplomatie.papyrus.codegen.java.utils.JavaPluginUtils;
import fr.gouv.diplomatie.papyrus.codegen.java.utils.TypeUtils;
import java.util.ArrayList;
import java.util.Collection;
import org.eclipse.emf.common.util.EList;
import org.eclipse.uml2.uml.AssociationClass;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.Generalization;
import org.eclipse.uml2.uml.Interface;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Type;
import org.eclipse.uml2.uml.internal.impl.EnumerationLiteralImpl;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;
import org.eclipse.xtext.xbase.lib.Conversions;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.Functions.Function2;
import org.eclipse.xtext.xbase.lib.IterableExtensions;

@SuppressWarnings("all")
public class ClassifierJPAEntityGenerator {
  /**
   * Génère les imports du générateur d'annotation associé
   */
  public static String generateAGImports(final NamedElement elem) {
    if ((ProjectJPAEntityElementsCreator.annotationGenerator != null)) {
      return ProjectJPAEntityElementsCreator.annotationGenerator.generateImports(elem);
    }
    StringConcatenation _builder = new StringConcatenation();
    return _builder.toString();
  }
  
  /**
   * Génère les annotations du générateur d'annotation associé
   */
  public static String generateAGAnnotations(final NamedElement elem) {
    if ((ProjectJPAEntityElementsCreator.annotationGenerator != null)) {
      return ProjectJPAEntityElementsCreator.annotationGenerator.generateAnnotations(elem);
    }
    StringConcatenation _builder = new StringConcatenation();
    return _builder.toString();
  }
  
  public static CharSequence generateCode(final Classifier clazz) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("package ");
    String _import = Utils.toImport(GeneratorUtils.getEntityPackage(clazz));
    _builder.append(_import);
    _builder.append(";");
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    _builder.append("import java.io.Serializable;");
    _builder.newLine();
    _builder.append("import java.util.Date;");
    _builder.newLine();
    _builder.append("import java.util.Set;");
    _builder.newLine();
    _builder.newLine();
    _builder.append("import javax.persistence.AssociationOverride;");
    _builder.newLine();
    _builder.append("import javax.persistence.AssociationOverrides;");
    _builder.newLine();
    _builder.append("import javax.persistence.AttributeOverride;");
    _builder.newLine();
    _builder.append("import javax.persistence.AttributeOverrides;");
    _builder.newLine();
    _builder.append("import javax.persistence.CascadeType;");
    _builder.newLine();
    _builder.append("import javax.persistence.CollectionTable;");
    _builder.newLine();
    _builder.append("import javax.persistence.Column;");
    _builder.newLine();
    _builder.append("import javax.persistence.Convert;");
    _builder.newLine();
    _builder.append("import javax.persistence.ElementCollection;");
    _builder.newLine();
    _builder.append("import javax.persistence.Embeddable;");
    _builder.newLine();
    _builder.append("import javax.persistence.Embedded;");
    _builder.newLine();
    _builder.append("import javax.persistence.Enumerated;");
    _builder.newLine();
    _builder.append("import javax.persistence.Entity;");
    _builder.newLine();
    _builder.append("import javax.persistence.FetchType;");
    _builder.newLine();
    _builder.append("import javax.persistence.GeneratedValue;");
    _builder.newLine();
    _builder.append("import javax.persistence.GenerationType;");
    _builder.newLine();
    _builder.append("import javax.persistence.Inheritance;");
    _builder.newLine();
    _builder.append("import javax.persistence.Id;");
    _builder.newLine();
    _builder.append("import javax.persistence.JoinColumn;");
    _builder.newLine();
    _builder.append("import javax.persistence.JoinTable;");
    _builder.newLine();
    _builder.append("import javax.persistence.ManyToMany;");
    _builder.newLine();
    _builder.append("import javax.persistence.ManyToOne;");
    _builder.newLine();
    _builder.append("import javax.persistence.OneToMany;");
    _builder.newLine();
    _builder.append("import javax.persistence.OneToOne;");
    _builder.newLine();
    _builder.append("import javax.persistence.SequenceGenerator;");
    _builder.newLine();
    _builder.append("import javax.persistence.Table;");
    _builder.newLine();
    _builder.append("import javax.validation.constraints.AssertTrue;");
    _builder.newLine();
    _builder.append("import javax.validation.constraints.AssertFalse;");
    _builder.newLine();
    _builder.append("import javax.validation.constraints.DecimalMax;");
    _builder.newLine();
    _builder.append("import javax.validation.constraints.DecimalMin;");
    _builder.newLine();
    _builder.append("import javax.validation.constraints.Digits;");
    _builder.newLine();
    _builder.append("import javax.validation.constraints.Future;");
    _builder.newLine();
    _builder.append("import javax.validation.constraints.FutureOrPresent;");
    _builder.newLine();
    _builder.append("import javax.validation.constraints.Min;");
    _builder.newLine();
    _builder.append("import javax.validation.constraints.Max;");
    _builder.newLine();
    _builder.append("import javax.validation.constraints.Negative;");
    _builder.newLine();
    _builder.append("import javax.validation.constraints.NegativeOrZero;");
    _builder.newLine();
    _builder.append("import javax.validation.constraints.NotEmpty;");
    _builder.newLine();
    _builder.append("import javax.validation.constraints.NotNull;");
    _builder.newLine();
    _builder.append("import javax.validation.constraints.Null;");
    _builder.newLine();
    _builder.append("import javax.validation.constraints.Past;");
    _builder.newLine();
    _builder.append("import javax.validation.constraints.PastOrPresent;");
    _builder.newLine();
    _builder.append("import javax.validation.constraints.Pattern;");
    _builder.newLine();
    _builder.append("import javax.validation.constraints.Positive;");
    _builder.newLine();
    _builder.append("import javax.validation.constraints.PositiveOrZero;");
    _builder.newLine();
    _builder.append("import javax.validation.constraints.Size;");
    _builder.newLine();
    CharSequence _generateImports = ClassifierJPAEntityGenerator.generateImports(clazz);
    _builder.append(_generateImports);
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    String _generateComments = Utils.generateComments(clazz);
    _builder.append(_generateComments);
    String _generateInheritAnnotation = ClassifierJPAEntityGenerator.generateInheritAnnotation(clazz);
    _builder.append(_generateInheritAnnotation);
    _builder.newLineIfNotEmpty();
    String _generateTypeAnnotation = ClassifierJPAEntityGenerator.generateTypeAnnotation(clazz);
    _builder.append(_generateTypeAnnotation);
    _builder.newLineIfNotEmpty();
    String _generateAGAnnotations = ClassifierJPAEntityGenerator.generateAGAnnotations(clazz);
    _builder.append(_generateAGAnnotations);
    _builder.newLineIfNotEmpty();
    _builder.append("public class ");
    String _name = clazz.getName();
    _builder.append(_name);
    _builder.append(" ");
    CharSequence _generateExtends = ClassifierJPAEntityGenerator.generateExtends(clazz);
    _builder.append(_generateExtends);
    _builder.append(" ");
    CharSequence _generateImplements = ClassifierJPAEntityGenerator.generateImplements(clazz);
    _builder.append(_generateImplements);
    _builder.append(" {");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("/** The Constant serialVersionUID. */");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("private static final long serialVersionUID = 1L;");
    _builder.newLine();
    _builder.append("    ");
    CharSequence _generateAttributes = ClassifierJPAEntityGenerator.generateAttributes(clazz, clazz);
    _builder.append(_generateAttributes, "    ");
    _builder.newLineIfNotEmpty();
    _builder.append("    ");
    String _generateCompareTo = ClassifierJPAEntityGenerator.generateCompareTo(clazz);
    _builder.append(_generateCompareTo, "    ");
    _builder.newLineIfNotEmpty();
    _builder.append("}");
    _builder.newLine();
    return _builder;
  }
  
  /**
   * génère les attributs issus des associations
   */
  public static CharSequence generateAssociationsAttributes(final Classifier clazz) {
    CharSequence _xblockexpression = null;
    {
      final ArrayList<Type> associationsClasses = ClassifierUtils.getLinkedAssociationClass(clazz);
      StringConcatenation _builder = new StringConcatenation();
      final Function2<String, Type, String> _function = (String acc, Type asso) -> {
        StringConcatenation _builder_1 = new StringConcatenation();
        CharSequence _generateAssociationAttributes = ClassifierJPAEntityGenerator.generateAssociationAttributes(((AssociationClass) asso), clazz);
        _builder_1.append(_generateAssociationAttributes);
        return (acc + _builder_1);
      };
      String _fold = IterableExtensions.<Type, String>fold(associationsClasses, "", _function);
      _builder.append(_fold);
      _builder.newLineIfNotEmpty();
      _xblockexpression = _builder;
    }
    return _xblockexpression;
  }
  
  public static String generateTypeAnnotation(final Classifier clazz) {
    boolean _isValueObject = Utils.isValueObject(clazz);
    if (_isValueObject) {
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("@Embeddable");
      return _builder.toString();
    }
    Object schema = JavaClassifierUtils.getSchema(clazz);
    StringConcatenation _builder_1 = new StringConcatenation();
    _builder_1.append("@Entity");
    _builder_1.newLine();
    _builder_1.append("@Table(name = \"");
    String _dbName = Utils.toDbName(ClassifierUtils.getTableName(clazz));
    _builder_1.append(_dbName);
    _builder_1.append("\"");
    {
      if ((schema != null)) {
        _builder_1.append(", schema = \"");
        _builder_1.append(schema);
        _builder_1.append("\"");
      }
    }
    _builder_1.append(")");
    _builder_1.newLineIfNotEmpty();
    return _builder_1.toString();
  }
  
  public static String generateInheritAnnotation(final Classifier clazz) {
    final EList<Interface> interfaces = clazz.directlyRealizedInterfaces();
    int _length = ((Object[])Conversions.unwrapArray(interfaces, Object.class)).length;
    boolean _greaterThan = (_length > 0);
    if (_greaterThan) {
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("@Inheritance");
      _builder.newLine();
      return _builder.toString();
    }
    return null;
  }
  
  /**
   * génère les imports de la classe
   */
  public static CharSequence generateImports(final Classifier clazz) {
    CharSequence _xblockexpression = null;
    {
      ArrayList<Type> imports = ClassifierJPAEntityGenerator.generateAllImports(clazz, CollectionLiterals.<Type>newArrayList());
      StringConcatenation _builder = new StringConcatenation();
      final Function2<String, Type, String> _function = (String acc, Type type) -> {
        StringConcatenation _builder_1 = new StringConcatenation();
        _builder_1.newLine();
        CharSequence _generateImport = ClassifierJPAEntityGenerator.generateImport(type);
        _builder_1.append(_generateImport);
        return (acc + _builder_1);
      };
      String _fold = IterableExtensions.<Type, String>fold(imports, "", _function);
      _builder.append(_fold);
      _builder.newLineIfNotEmpty();
      _builder.newLine();
      String _generateAGImports = ClassifierJPAEntityGenerator.generateAGImports(clazz);
      _builder.append(_generateAGImports);
      _builder.newLineIfNotEmpty();
      _xblockexpression = _builder;
    }
    return _xblockexpression;
  }
  
  /**
   * génère les imports de la classes liés aux attributs
   */
  public static ArrayList<Type> generateAllImports(final Classifier clazz, final ArrayList<Type> types) {
    final Function1<Property, Boolean> _function = (Property attribut) -> {
      return Boolean.valueOf(((Utils.isEntity(attribut.getType()) || Utils.isValueObject(attribut.getType())) || Utils.isNomenclature(attribut.getType())));
    };
    Iterable<Property> attributes = IterableExtensions.<Property>filter(ClassifierUtils.getOwnedAttributes(clazz), _function);
    final EList<Interface> interfaces = clazz.directlyRealizedInterfaces();
    for (final Interface interface_ : interfaces) {
      {
        final Function1<Property, Boolean> _function_1 = (Property attribut) -> {
          return Boolean.valueOf(((Utils.isEntity(attribut.getType()) || Utils.isValueObject(attribut.getType())) || Utils.isNomenclature(attribut.getType())));
        };
        Iterable<Property> interfaceAtteributes = IterableExtensions.<Property>filter(ClassifierUtils.getOwnedAttributes(interface_), _function_1);
        for (final Property attribut : interfaceAtteributes) {
          boolean _contains = types.contains(attribut.getType());
          boolean _not = (!_contains);
          if (_not) {
            types.add(attribut.getType());
          }
        }
      }
    }
    boolean _isEntity = Utils.isEntity(clazz);
    if (_isEntity) {
      final ArrayList<Type> associationsClasses = ClassifierUtils.getLinkedAssociationClass(clazz);
      for (final Type asso : associationsClasses) {
        {
          final Function1<Property, Boolean> _function_1 = (Property property) -> {
            Type _type = property.getType();
            return Boolean.valueOf((_type != clazz));
          };
          final Property end = ((Property[])Conversions.unwrapArray(IterableExtensions.<Property>filter(((AssociationClass) asso).getOwnedEnds(), _function_1), Property.class))[0];
          boolean _contains = types.contains(end.getType());
          boolean _not = (!_contains);
          if (_not) {
            types.add(end.getType());
          }
        }
      }
    }
    final EList<Generalization> extends_ = clazz.getGeneralizations();
    for (final Generalization extend : extends_) {
      boolean _contains = types.contains(extend.getGeneral());
      boolean _not = (!_contains);
      if (_not) {
        types.add(extend.getGeneral());
      }
    }
    for (final Property attribut : attributes) {
      boolean _contains_1 = types.contains(attribut.getType());
      boolean _not_1 = (!_contains_1);
      if (_not_1) {
        types.add(attribut.getType());
      }
    }
    return types;
  }
  
  public static CharSequence generateImport(final Type type) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("import ");
    String _import = Utils.toImport(GeneratorUtils.getEntityPackage(((Classifier) type)));
    _builder.append(_import);
    _builder.append(".");
    String _name = type.getName();
    _builder.append(_name);
    _builder.append(";");
    return _builder;
  }
  
  /**
   * génère les attributs simples de l'entité
   */
  public static CharSequence generateAttributes(final Classifier clazz, final Classifier fromClass) {
    CharSequence _xblockexpression = null;
    {
      final Collection<Property> attributes = ClassifierUtils.getOwnedAttributes(clazz);
      StringConcatenation _builder = new StringConcatenation();
      final Function2<String, Property, String> _function = (String acc, Property attribut) -> {
        StringConcatenation _builder_1 = new StringConcatenation();
        CharSequence _generateAttribut = ClassifierJPAEntityGenerator.generateAttribut(attribut, "", "", fromClass);
        _builder_1.append(_generateAttribut);
        return (acc + _builder_1);
      };
      String _fold = IterableExtensions.<Property, String>fold(attributes, "", _function);
      _builder.append(_fold);
      _builder.newLineIfNotEmpty();
      CharSequence _generateInterfaceAttributes = ClassifierJPAEntityGenerator.generateInterfaceAttributes(clazz);
      _builder.append(_generateInterfaceAttributes);
      _builder.newLineIfNotEmpty();
      {
        boolean _isEntity = Utils.isEntity(clazz);
        if (_isEntity) {
          CharSequence _generateATAttributes = ClassifierJPAEntityGenerator.generateATAttributes(clazz);
          _builder.append(_generateATAttributes);
        }
      }
      _builder.newLineIfNotEmpty();
      _xblockexpression = _builder;
    }
    return _xblockexpression;
  }
  
  public static CharSequence generateATAttributes(final Classifier clazz) {
    CharSequence _xblockexpression = null;
    {
      final ArrayList<Type> associationsClasses = ClassifierUtils.getLinkedAssociationClass(clazz);
      StringConcatenation _builder = new StringConcatenation();
      final Function2<String, Type, String> _function = (String acc, Type asso) -> {
        StringConcatenation _builder_1 = new StringConcatenation();
        CharSequence _generateAssociationAttributes = ClassifierJPAEntityGenerator.generateAssociationAttributes(((AssociationClass) asso), clazz);
        _builder_1.append(_generateAssociationAttributes);
        return (acc + _builder_1);
      };
      String _fold = IterableExtensions.<Type, String>fold(associationsClasses, "", _function);
      _builder.append(_fold);
      _builder.newLineIfNotEmpty();
      _xblockexpression = _builder;
    }
    return _xblockexpression;
  }
  
  public static CharSequence generateAssociationAttributes(final AssociationClass clazz, final Classifier fromClass) {
    CharSequence _xblockexpression = null;
    {
      final Function1<Property, Boolean> _function = (Property property) -> {
        Type _type = property.getType();
        return Boolean.valueOf((_type != fromClass));
      };
      final Property end = ((Property[])Conversions.unwrapArray(IterableExtensions.<Property>filter(clazz.getOwnedEnds(), _function), Property.class))[0];
      final Function1<Property, Boolean> _function_1 = (Property property) -> {
        Type _type = property.getType();
        return Boolean.valueOf(Objects.equal(_type, fromClass));
      };
      final Property fromEnd = ((Property[])Conversions.unwrapArray(IterableExtensions.<Property>filter(clazz.getOwnedEnds(), _function_1), Property.class))[0];
      final Property idFrom = ((Property[])Conversions.unwrapArray(ClassifierUtils.getId(fromClass), Property.class))[0];
      final String idFromDbName = PropertyUtils.getDatabaseName(idFrom, idFrom.getName(), "");
      final EnumerationLiteralImpl fetchType = JavaPluginUtils.getFetchType(clazz);
      CharSequence _xifexpression = null;
      boolean _isEntity = Utils.isEntity(end.getType());
      if (_isEntity) {
        CharSequence _xblockexpression_1 = null;
        {
          final String tableName = Utils.toDbName(clazz.getName());
          String _databaseName = PropertyUtils.getDatabaseName(idFrom, idFrom.getName(), "");
          String _plus = (_databaseName + "_");
          String _dbName = Utils.toDbName(fromEnd.getName());
          final String idFromName = (_plus + _dbName);
          Type _type = end.getType();
          final Property idTo = ((Property[])Conversions.unwrapArray(ClassifierUtils.getId(((Classifier) _type)), Property.class))[0];
          String _databaseName_1 = PropertyUtils.getDatabaseName(idTo, idTo.getName(), "");
          String _plus_1 = (_databaseName_1 + "_");
          String _dbName_1 = Utils.toDbName(end.getName());
          final String idToName = (_plus_1 + _dbName_1);
          final String idToDbName = PropertyUtils.getDatabaseName(idTo, idTo.getName(), "");
          StringConcatenation _builder = new StringConcatenation();
          _builder.newLine();
          _builder.append("@ManyToMany");
          {
            if ((fetchType != null)) {
              _builder.append("(fetch=FetchType.");
              String _name = fetchType.getName();
              _builder.append(_name);
              _builder.append(")");
            }
          }
          _builder.newLineIfNotEmpty();
          _builder.append("@JoinTable(name=\"");
          _builder.append(tableName);
          _builder.append("\", joinColumns=@JoinColumn(name=\"");
          _builder.append(idFromName);
          _builder.append("\", referencedColumnName=\"");
          _builder.append(idFromDbName);
          _builder.append("\"),");
          _builder.newLineIfNotEmpty();
          _builder.append("\t");
          _builder.append("inverseJoinColumns=@JoinColumn(name=\"");
          _builder.append(idToName, "\t");
          _builder.append("\", referencedColumnName=\"");
          _builder.append(idToDbName, "\t");
          _builder.append("\"))");
          _builder.newLineIfNotEmpty();
          String _name_1 = clazz.getVisibility().getName();
          _builder.append(_name_1);
          _builder.append(" Set<");
          String _name_2 = end.getType().getName();
          _builder.append(_name_2);
          _builder.append("> ");
          String _firstToLowerCase = Utils.getFirstToLowerCase(end.getName());
          _builder.append(_firstToLowerCase);
          _builder.append(";");
          _builder.newLineIfNotEmpty();
          _xblockexpression_1 = _builder;
        }
        _xifexpression = _xblockexpression_1;
      } else {
        CharSequence _xifexpression_1 = null;
        boolean _isValueObject = Utils.isValueObject(end.getType());
        if (_isValueObject) {
          StringConcatenation _builder = new StringConcatenation();
          _builder.newLine();
          _builder.append("@ElementCollection");
          _builder.newLine();
          _builder.append("@CollectionTable(name = \"");
          String _dbName = Utils.toDbName(fromClass.getName());
          _builder.append(_dbName);
          _builder.append("_");
          String _dbName_1 = Utils.toDbName(clazz.getName());
          _builder.append(_dbName_1);
          _builder.append("\")");
          _builder.newLineIfNotEmpty();
          String _name = clazz.getVisibility().getName();
          _builder.append(_name);
          _builder.append(" Set<");
          String _name_1 = end.getType().getName();
          _builder.append(_name_1);
          _builder.append("> ");
          String _firstToLowerCase = Utils.getFirstToLowerCase(clazz.getName());
          _builder.append(_firstToLowerCase);
          _builder.append(";");
          _builder.newLineIfNotEmpty();
          _xifexpression_1 = _builder;
        } else {
          StringConcatenation _builder_1 = new StringConcatenation();
          _builder_1.newLine();
          _builder_1.append("@ElementCollection");
          _builder_1.newLine();
          _builder_1.append("@CollectionTable(name = \"");
          String _dbName_2 = Utils.toDbName(fromClass.getName());
          _builder_1.append(_dbName_2);
          _builder_1.append("_");
          String _dbName_3 = Utils.toDbName(clazz.getName());
          _builder_1.append(_dbName_3);
          _builder_1.append("\", joinColumns = @JoinColumn(name = \"");
          _builder_1.append(idFromDbName);
          _builder_1.append("\"))");
          _builder_1.newLineIfNotEmpty();
          String _name_2 = clazz.getVisibility().getName();
          _builder_1.append(_name_2);
          _builder_1.append(" Set<");
          String _name_3 = end.getType().getName();
          _builder_1.append(_name_3);
          _builder_1.append("> ");
          String _firstToLowerCase_1 = Utils.getFirstToLowerCase(clazz.getName());
          _builder_1.append(_firstToLowerCase_1);
          _builder_1.append(";");
          _builder_1.newLineIfNotEmpty();
          _xifexpression_1 = _builder_1;
        }
        _xifexpression = _xifexpression_1;
      }
      _xblockexpression = _xifexpression;
    }
    return _xblockexpression;
  }
  
  public static CharSequence generateInterfaceAttributes(final Classifier clazz) {
    CharSequence _xblockexpression = null;
    {
      final EList<Interface> interfaces = clazz.directlyRealizedInterfaces();
      StringConcatenation _builder = new StringConcatenation();
      final Function2<String, Interface, String> _function = (String acc, Interface interface_) -> {
        StringConcatenation _builder_1 = new StringConcatenation();
        Object _generateAttributes = ClassifierJPAEntityGenerator.generateAttributes(interface_, clazz);
        _builder_1.append(_generateAttributes);
        return (acc + _builder_1);
      };
      String _fold = IterableExtensions.<Interface, String>fold(interfaces, "", _function);
      _builder.append(_fold);
      _builder.newLineIfNotEmpty();
      _xblockexpression = _builder;
    }
    return _xblockexpression;
  }
  
  /**
   * génère un attribut de l'entité
   */
  public static CharSequence generateAttribut(final Property property, final String additionnalName, final String additionnalDBName, final Classifier fromClass) {
    CharSequence _xifexpression = null;
    boolean _isValueObject = Utils.isValueObject(property.getType());
    if (_isValueObject) {
      StringConcatenation _builder = new StringConcatenation();
      CharSequence _generateVOAttribute = ClassifierJPAEntityGenerator.generateVOAttribute(property, additionnalName, additionnalDBName, fromClass);
      _builder.append(_generateVOAttribute);
      _xifexpression = _builder;
    } else {
      StringConcatenation _builder_1 = new StringConcatenation();
      CharSequence _generateBasicAttribute = ClassifierJPAEntityGenerator.generateBasicAttribute(property, additionnalName, fromClass);
      _builder_1.append(_generateBasicAttribute);
      _xifexpression = _builder_1;
    }
    return _xifexpression;
  }
  
  /**
   * génère un attribut de l'entité
   */
  public static CharSequence generateBasicAttribute(final Property property, final String additionnalName, final Classifier fromClass) {
    CharSequence _xblockexpression = null;
    {
      String name = Utils.addAdditionnalName(additionnalName, property.getName());
      final EnumerationLiteralImpl fetchType = JavaPluginUtils.getFetchType(property);
      final boolean nullable = PropertyUtils.isNullable(property);
      final Object shouldbeNull = JavaPluginUtils.getShouldBeNull(property);
      String array = "";
      String link = "";
      String endArray = "";
      boolean _isMultivalued = property.isMultivalued();
      if (_isMultivalued) {
        array = "Set<";
        endArray = ">";
      }
      boolean _isEntity = Utils.isEntity(property.getType());
      if (_isEntity) {
        boolean _isMultivalued_1 = property.isMultivalued();
        if (_isMultivalued_1) {
          Type _type = property.getType();
          Iterable<Property> _id = ClassifierUtils.getId(((Classifier) _type));
          boolean _tripleNotEquals = (_id != null);
          if (_tripleNotEquals) {
            String _name = fromClass.getName();
            String _firstToUpperCase = Utils.getFirstToUpperCase(property.getName());
            String _plus = (_name + _firstToUpperCase);
            final String tableName = Utils.toDbName(_plus);
            final Property idFrom = ((Property[])Conversions.unwrapArray(ClassifierUtils.getId(fromClass), Property.class))[0];
            String _databaseName = PropertyUtils.getDatabaseName(idFrom, idFrom.getName(), "");
            String _plus_1 = (_databaseName + "_");
            String _dbName = Utils.toDbName(fromClass.getName());
            final String idFromName = (_plus_1 + _dbName);
            final String idFromDbName = PropertyUtils.getDatabaseName(idFrom, idFrom.getName(), "");
            Type _type_1 = property.getType();
            final Property idTo = ((Property[])Conversions.unwrapArray(ClassifierUtils.getId(((Classifier) _type_1)), Property.class))[0];
            String _databaseName_1 = PropertyUtils.getDatabaseName(idTo, idTo.getName(), "");
            String _plus_2 = (_databaseName_1 + "_");
            String _databaseName_2 = PropertyUtils.getDatabaseName(property, property.getName(), "");
            final String idToName = (_plus_2 + _databaseName_2);
            final String idToDbName = PropertyUtils.getDatabaseName(idTo, idTo.getName(), "");
            StringConcatenation _builder = new StringConcatenation();
            _builder.append("@ManyToMany");
            {
              if ((fetchType != null)) {
                _builder.append("(fetch=FetchType.");
                String _name_1 = fetchType.getName();
                _builder.append(_name_1);
                _builder.append(")");
              }
            }
            _builder.newLineIfNotEmpty();
            _builder.append("@JoinTable(name=\"");
            _builder.append(tableName);
            _builder.append("\", joinColumns=@JoinColumn(name=\"");
            _builder.append(idFromName);
            _builder.append("\", referencedColumnName=\"");
            _builder.append(idFromDbName);
            _builder.append("\"),");
            _builder.newLineIfNotEmpty();
            _builder.append("\t");
            _builder.append("inverseJoinColumns=@JoinColumn(name=\"");
            _builder.append(idToName, "\t");
            _builder.append("\", referencedColumnName=\"");
            _builder.append(idToDbName, "\t");
            _builder.append("\"))");
            _builder.newLineIfNotEmpty();
            link = _builder.toString();
          }
        } else {
          Type _type_2 = property.getType();
          final Property idTo_1 = ((Property[])Conversions.unwrapArray(ClassifierUtils.getId(((Classifier) _type_2)), Property.class))[0];
          StringConcatenation _builder_1 = new StringConcatenation();
          _builder_1.append("@ManyToOne");
          {
            if ((fetchType != null)) {
              _builder_1.append("(fetch=FetchType.");
              String _name_2 = fetchType.getName();
              _builder_1.append(_name_2);
              _builder_1.append(")");
            }
          }
          _builder_1.newLineIfNotEmpty();
          _builder_1.append("//@JoinColumn(name = \"");
          String _databaseName_3 = PropertyUtils.getDatabaseName(idTo_1, idTo_1.getName(), null);
          _builder_1.append(_databaseName_3);
          _builder_1.append("_");
          String _databaseName_4 = PropertyUtils.getDatabaseName(property, property.getName(), null);
          _builder_1.append(_databaseName_4);
          _builder_1.append("\", nullable = ");
          _builder_1.append(nullable);
          _builder_1.append(")");
          _builder_1.newLineIfNotEmpty();
          link = _builder_1.toString();
        }
      }
      String typeName = "";
      if ((Utils.isEntity(property.getType()) || Utils.isNomenclature(property.getType()))) {
        typeName = property.getType().getName();
      } else {
        typeName = TypeUtils.getJavaType(property.getType());
      }
      final boolean isEnum = Utils.isNomenclature(property.getType());
      final boolean isId = PropertyUtils.isID(property);
      final String columnName = PropertyUtils.getDatabaseName(property, property.getName(), additionnalName);
      boolean isAsso = ((property.getAssociation() != null) && Utils.isEntity(property.getType()));
      StringConcatenation _builder_2 = new StringConcatenation();
      _builder_2.newLine();
      String _generateAGAnnotations = ClassifierJPAEntityGenerator.generateAGAnnotations(property);
      _builder_2.append(_generateAGAnnotations);
      _builder_2.newLineIfNotEmpty();
      String _generateComments = Utils.generateComments(property);
      _builder_2.append(_generateComments);
      String _generateAGAnnotations_1 = ClassifierJPAEntityGenerator.generateAGAnnotations(property);
      _builder_2.append(_generateAGAnnotations_1);
      _builder_2.newLineIfNotEmpty();
      String _generateTypeAnnotation = ClassifierJPAEntityGenerator.generateTypeAnnotation(property);
      _builder_2.append(_generateTypeAnnotation);
      _builder_2.newLineIfNotEmpty();
      {
        if ((property.isMultivalued() && (!Utils.isEntity(property.getType())))) {
          _builder_2.append("@ElementCollection");
          _builder_2.newLine();
        } else {
          {
            if (isId) {
              _builder_2.append("@Id");
            }
          }
          {
            boolean _isSequence = Utils.isSequence(property);
            if (_isSequence) {
              _builder_2.newLineIfNotEmpty();
              CharSequence _generateSequence = ClassifierJPAEntityGenerator.generateSequence(property, name, fromClass);
              _builder_2.append(_generateSequence);
              _builder_2.newLineIfNotEmpty();
            }
          }
          {
            if (isEnum) {
              _builder_2.append("@Enumerated");
              _builder_2.newLine();
            }
          }
          {
            if ((Utils.isEntity(property.getType()) && (!isAsso))) {
              _builder_2.append(link);
            } else {
              if (isAsso) {
                _builder_2.newLineIfNotEmpty();
                CharSequence _generateAssociationAnnotation = ClassifierJPAEntityGenerator.generateAssociationAnnotation(property, fromClass);
                _builder_2.append(_generateAssociationAnnotation);
                _builder_2.newLineIfNotEmpty();
              } else {
                {
                  if (((nullable == false) && (shouldbeNull != Boolean.valueOf(true)))) {
                    _builder_2.append("@NotNull");
                  }
                }
                _builder_2.newLineIfNotEmpty();
                _builder_2.append("@Column(name = \"");
                _builder_2.append(columnName);
                _builder_2.append("\")");
                _builder_2.newLineIfNotEmpty();
              }
            }
          }
        }
      }
      String _name_3 = property.getVisibility().getName();
      _builder_2.append(_name_3);
      _builder_2.append(" ");
      _builder_2.append(array);
      _builder_2.append(typeName);
      _builder_2.append(endArray);
      _builder_2.append(" ");
      _builder_2.append(name);
      _builder_2.append(";");
      _builder_2.newLineIfNotEmpty();
      _xblockexpression = _builder_2;
    }
    return _xblockexpression;
  }
  
  public static CharSequence generateSequence(final Property property, final String name, final Classifier fromClass) {
    CharSequence _xblockexpression = null;
    {
      final Object startWith = Utils.getSequenceStartWith(property);
      final Object incrementBy = Utils.getSequenceIncrementBy(property);
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("//@SequenceGenerator(name=\"");
      String _snakeCase = Utils.toSnakeCase(fromClass.getName());
      _builder.append(_snakeCase);
      _builder.append("_");
      String _snakeCase_1 = Utils.toSnakeCase(name);
      _builder.append(_snakeCase_1);
      _builder.append("_generator\", initialValue=");
      _builder.append(startWith);
      _builder.append(", allocationSize=");
      _builder.append(incrementBy);
      _builder.append(", sequenceName=\"");
      String _snakeCase_2 = Utils.toSnakeCase(fromClass.getName());
      _builder.append(_snakeCase_2);
      _builder.append("_");
      String _snakeCase_3 = Utils.toSnakeCase(name);
      _builder.append(_snakeCase_3);
      _builder.append("_sequence\")");
      _builder.newLineIfNotEmpty();
      _builder.append("//@GeneratedValue(strategy=GenerationType.SEQUENCE, generator=\"");
      String _snakeCase_4 = Utils.toSnakeCase(fromClass.getName());
      _builder.append(_snakeCase_4);
      _builder.append("_");
      String _snakeCase_5 = Utils.toSnakeCase(name);
      _builder.append(_snakeCase_5);
      _builder.append("_generator\")");
      _builder.newLineIfNotEmpty();
      _builder.append("@GeneratedValue(strategy=GenerationType.IDENTITY)");
      _builder.newLine();
      _xblockexpression = _builder;
    }
    return _xblockexpression;
  }
  
  public static CharSequence generateAssociationAnnotation(final Property property, final Classifier fromClass) {
    CharSequence _xblockexpression = null;
    {
      final EList<Property> members = property.getAssociation().getOwnedEnds();
      final boolean nullable = PropertyUtils.isNullable(property);
      final EnumerationLiteralImpl fetchType = JavaPluginUtils.getFetchType(property);
      final Property member = members.get(0);
      final boolean fromMultiplicity = member.isMultivalued();
      final boolean toMultiplicity = property.isMultivalued();
      Type _type = property.getType();
      final Property id = ((Property[])Conversions.unwrapArray(ClassifierUtils.getId(((Classifier) _type)), Property.class))[0];
      String _name = fromClass.getName();
      String _firstToUpperCase = Utils.getFirstToUpperCase(property.getName());
      String _plus = (_name + _firstToUpperCase);
      final String tableName = Utils.toDbName(_plus);
      final Property idFrom = ((Property[])Conversions.unwrapArray(ClassifierUtils.getId(fromClass), Property.class))[0];
      String _databaseName = PropertyUtils.getDatabaseName(idFrom, idFrom.getName(), "");
      String _plus_1 = (_databaseName + "_");
      String _dbName = Utils.toDbName(fromClass.getName());
      final String idFromName = (_plus_1 + _dbName);
      final String idFromDbName = PropertyUtils.getDatabaseName(idFrom, idFrom.getName(), "");
      String _databaseName_1 = PropertyUtils.getDatabaseName(id, id.getName(), "");
      String _plus_2 = (_databaseName_1 + "_");
      String _databaseName_2 = PropertyUtils.getDatabaseName(property, property.getName(), "");
      final String idToName = (_plus_2 + _databaseName_2);
      final String idToDbName = PropertyUtils.getDatabaseName(id, id.getName(), "");
      CharSequence _xifexpression = null;
      if (fromMultiplicity) {
        CharSequence _xifexpression_1 = null;
        if (toMultiplicity) {
          StringConcatenation _builder = new StringConcatenation();
          _builder.append("@ManyToMany");
          {
            if ((fetchType != null)) {
              _builder.append("(fetch=FetchType.");
              String _name_1 = fetchType.getName();
              _builder.append(_name_1);
              _builder.append(")");
            }
          }
          _builder.newLineIfNotEmpty();
          _builder.append("@JoinTable(name=\"");
          _builder.append(tableName);
          _builder.append("\", joinColumns=@JoinColumn(name=\"");
          _builder.append(idFromName);
          _builder.append("\", referencedColumnName=\"");
          _builder.append(idFromDbName);
          _builder.append("\"),");
          _builder.newLineIfNotEmpty();
          _builder.append("\t");
          _builder.append("inverseJoinColumns=@JoinColumn(name=\"");
          _builder.append(idToName, "\t");
          _builder.append("\", referencedColumnName=\"");
          _builder.append(idToDbName, "\t");
          _builder.append("\"))");
          _builder.newLineIfNotEmpty();
          _xifexpression_1 = _builder;
        } else {
          StringConcatenation _builder_1 = new StringConcatenation();
          {
            if ((nullable == false)) {
              _builder_1.append("@NotNull");
            }
          }
          _builder_1.newLineIfNotEmpty();
          _builder_1.append("@ManyToOne");
          {
            if ((fetchType != null)) {
              _builder_1.append("(fetch=FetchType.");
              String _name_2 = fetchType.getName();
              _builder_1.append(_name_2);
              _builder_1.append(")");
            }
          }
          _builder_1.newLineIfNotEmpty();
          _builder_1.append("@JoinColumn(name = \"");
          String _databaseName_3 = PropertyUtils.getDatabaseName(id, id.getName(), null);
          _builder_1.append(_databaseName_3);
          _builder_1.append("_");
          String _databaseName_4 = PropertyUtils.getDatabaseName(property, property.getName(), null);
          _builder_1.append(_databaseName_4);
          _builder_1.append("\")");
          _builder_1.newLineIfNotEmpty();
          _xifexpression_1 = _builder_1;
        }
        _xifexpression = _xifexpression_1;
      } else {
        CharSequence _xifexpression_2 = null;
        if (toMultiplicity) {
          StringConcatenation _builder_2 = new StringConcatenation();
          _builder_2.append("@OneToMany");
          {
            if ((fetchType != null)) {
              _builder_2.append("(fetch=FetchType.");
              String _name_3 = fetchType.getName();
              _builder_2.append(_name_3);
              _builder_2.append(")");
            }
          }
          _builder_2.newLineIfNotEmpty();
          _builder_2.append("@JoinTable(name=\"");
          _builder_2.append(tableName);
          _builder_2.append("\", joinColumns=@JoinColumn(name=\"");
          _builder_2.append(idFromName);
          _builder_2.append("\", referencedColumnName=\"");
          _builder_2.append(idFromDbName);
          _builder_2.append("\"),");
          _builder_2.newLineIfNotEmpty();
          _builder_2.append("\t");
          _builder_2.append("inverseJoinColumns=@JoinColumn(name=\"");
          _builder_2.append(idToName, "\t");
          _builder_2.append("\", referencedColumnName=\"");
          _builder_2.append(idToDbName, "\t");
          _builder_2.append("\", unique=true))");
          _builder_2.newLineIfNotEmpty();
          _xifexpression_2 = _builder_2;
        } else {
          StringConcatenation _builder_3 = new StringConcatenation();
          {
            if ((nullable == false)) {
              _builder_3.append("@NotNull");
            }
          }
          _builder_3.newLineIfNotEmpty();
          _builder_3.append("@OneToOne(cascade = {CascadeType.REMOVE})");
          _builder_3.newLine();
          _builder_3.append("@JoinColumn(name = \"");
          String _databaseName_5 = PropertyUtils.getDatabaseName(id, id.getName(), null);
          _builder_3.append(_databaseName_5);
          _builder_3.append("_");
          String _databaseName_6 = PropertyUtils.getDatabaseName(property, property.getName(), null);
          _builder_3.append(_databaseName_6);
          _builder_3.append("\")");
          _builder_3.newLineIfNotEmpty();
          _xifexpression_2 = _builder_3;
        }
        _xifexpression = _xifexpression_2;
      }
      _xblockexpression = _xifexpression;
    }
    return _xblockexpression;
  }
  
  public static CharSequence generateVOAttribute(final Property property, final String additionnalName, final String additionnalDBName, final Classifier fromClass) {
    CharSequence _xblockexpression = null;
    {
      String name = Utils.addAdditionnalName(additionnalName, property.getName());
      String dbName = PropertyUtils.getDatabaseName(property, property.getName(), additionnalDBName);
      final boolean nullable = PropertyUtils.isNullable(property);
      String array = "";
      String endArray = "";
      boolean _isMultivalued = property.isMultivalued();
      if (_isMultivalued) {
        array = "Set<";
        endArray = ">";
      }
      String typeName = property.getType().getName();
      final Property id = ((Property[])Conversions.unwrapArray(ClassifierUtils.getId(fromClass), Property.class))[0];
      final String idDbName = PropertyUtils.getDatabaseName(id, id.getName(), "");
      StringConcatenation _builder = new StringConcatenation();
      _builder.newLine();
      String _generateComments = Utils.generateComments(property);
      _builder.append(_generateComments);
      String _generateAGAnnotations = ClassifierJPAEntityGenerator.generateAGAnnotations(property);
      _builder.append(_generateAGAnnotations);
      _builder.newLineIfNotEmpty();
      {
        boolean _isMultivalued_1 = property.isMultivalued();
        if (_isMultivalued_1) {
          _builder.append("@ElementCollection");
          _builder.newLine();
          _builder.append("@CollectionTable(name = \"");
          String _dbName = Utils.toDbName(fromClass.getName());
          _builder.append(_dbName);
          _builder.append("_");
          String _dbName_1 = Utils.toDbName(name);
          _builder.append(_dbName_1);
          _builder.append("\", joinColumns = @JoinColumn(name = \"");
          _builder.append(idDbName);
          _builder.append("\"))");
          _builder.newLineIfNotEmpty();
        } else {
          {
            if ((nullable == false)) {
              _builder.append("@NotNull");
            }
          }
          _builder.newLineIfNotEmpty();
          _builder.append("@Embedded");
          _builder.newLine();
        }
      }
      {
        boolean _isMultivalued_2 = property.isMultivalued();
        boolean _not = (!_isMultivalued_2);
        if (_not) {
          CharSequence _generateVOOverrides = ClassifierJPAEntityGenerator.generateVOOverrides(property, dbName);
          _builder.append(_generateVOOverrides);
          _builder.newLineIfNotEmpty();
        }
      }
      String _name = property.getVisibility().getName();
      _builder.append(_name);
      _builder.append(" ");
      _builder.append(array);
      _builder.append(typeName);
      _builder.append(endArray);
      _builder.append(" ");
      _builder.append(name);
      _builder.append(";");
      _builder.newLineIfNotEmpty();
      _xblockexpression = _builder;
    }
    return _xblockexpression;
  }
  
  public static String generateTypeAnnotation(final Property property) {
    final Object shouldbeNull = JavaPluginUtils.getShouldBeNull(property);
    StringConcatenation _builder = new StringConcatenation();
    {
      boolean _equals = Objects.equal(shouldbeNull, Boolean.valueOf(true));
      if (_equals) {
        _builder.append("@Null");
      }
    }
    _builder.newLineIfNotEmpty();
    String retour = _builder.toString();
    boolean _hasStereotype = Utils.hasStereotype(property, JavaPluginUtils.MODEL_BOOLEANTYPED);
    if (_hasStereotype) {
      String _retour = retour;
      StringConcatenation _builder_1 = new StringConcatenation();
      CharSequence _generateBooleanTypedAnnotation = ClassifierJPAEntityGenerator.generateBooleanTypedAnnotation(property);
      _builder_1.append(_generateBooleanTypedAnnotation);
      _builder_1.newLineIfNotEmpty();
      retour = (_retour + _builder_1);
    }
    boolean _hasStereotype_1 = Utils.hasStereotype(property, JavaPluginUtils.MODEL_DATETYPED);
    if (_hasStereotype_1) {
      String _retour_1 = retour;
      StringConcatenation _builder_2 = new StringConcatenation();
      CharSequence _generateDateTypedAnnotation = ClassifierJPAEntityGenerator.generateDateTypedAnnotation(property);
      _builder_2.append(_generateDateTypedAnnotation);
      _builder_2.newLineIfNotEmpty();
      retour = (_retour_1 + _builder_2);
    }
    boolean _hasStereotype_2 = Utils.hasStereotype(property, JavaPluginUtils.MODEL_STRINGTYPED);
    if (_hasStereotype_2) {
      String _retour_2 = retour;
      StringConcatenation _builder_3 = new StringConcatenation();
      CharSequence _generatStringTypedAnnotation = ClassifierJPAEntityGenerator.generatStringTypedAnnotation(property);
      _builder_3.append(_generatStringTypedAnnotation);
      _builder_3.newLineIfNotEmpty();
      retour = (_retour_2 + _builder_3);
    }
    boolean _hasStereotype_3 = Utils.hasStereotype(property, JavaPluginUtils.MODEL_NUMERICTYPED);
    if (_hasStereotype_3) {
      String _retour_3 = retour;
      StringConcatenation _builder_4 = new StringConcatenation();
      CharSequence _generateNumericTypedAnnotation = ClassifierJPAEntityGenerator.generateNumericTypedAnnotation(property);
      _builder_4.append(_generateNumericTypedAnnotation);
      _builder_4.newLineIfNotEmpty();
      retour = (_retour_3 + _builder_4);
    }
    boolean _hasStereotype_4 = Utils.hasStereotype(property, JavaPluginUtils.MODEL_COLLECTION);
    if (_hasStereotype_4) {
      String _retour_4 = retour;
      StringConcatenation _builder_5 = new StringConcatenation();
      CharSequence _generateCollectionAnnotation = ClassifierJPAEntityGenerator.generateCollectionAnnotation(property);
      _builder_5.append(_generateCollectionAnnotation);
      _builder_5.newLineIfNotEmpty();
      retour = (_retour_4 + _builder_5);
    }
    return retour;
  }
  
  public static CharSequence generateBooleanTypedAnnotation(final Property property) {
    CharSequence _xblockexpression = null;
    {
      final Object alwaysTrue = JavaPluginUtils.getAlwaysTrue(property);
      final Object alwaysFalse = JavaPluginUtils.getAlwaysFalse(property);
      StringConcatenation _builder = new StringConcatenation();
      {
        boolean _equals = Objects.equal(alwaysTrue, Boolean.valueOf(true));
        if (_equals) {
          _builder.append("@AssertTrue");
          _builder.newLine();
        } else {
          boolean _equals_1 = Objects.equal(alwaysFalse, Boolean.valueOf(true));
          if (_equals_1) {
            _builder.append("@AssertFalse");
            _builder.newLine();
          }
        }
      }
      _xblockexpression = _builder;
    }
    return _xblockexpression;
  }
  
  public static CharSequence generateDateTypedAnnotation(final Property property) {
    CharSequence _xblockexpression = null;
    {
      final Object future = JavaPluginUtils.getFuture(property);
      final Object futureorPresent = JavaPluginUtils.getFutureOrPresent(property);
      final Object past = JavaPluginUtils.getPast(property);
      final Object pastOrPresent = JavaPluginUtils.getPastOrPresent(property);
      StringConcatenation _builder = new StringConcatenation();
      {
        boolean _equals = Objects.equal(future, Boolean.valueOf(true));
        if (_equals) {
          _builder.append("@Future");
          _builder.newLine();
        } else {
          boolean _equals_1 = Objects.equal(futureorPresent, Boolean.valueOf(true));
          if (_equals_1) {
            _builder.append("@FutureOrPresent");
            _builder.newLine();
          } else {
            boolean _equals_2 = Objects.equal(past, Boolean.valueOf(true));
            if (_equals_2) {
              _builder.append("@Past");
              _builder.newLine();
            } else {
              boolean _equals_3 = Objects.equal(pastOrPresent, Boolean.valueOf(true));
              if (_equals_3) {
                _builder.append("@PastOrPresent");
                _builder.newLine();
              }
            }
          }
        }
      }
      _xblockexpression = _builder;
    }
    return _xblockexpression;
  }
  
  public static CharSequence generatStringTypedAnnotation(final Property property) {
    CharSequence _xblockexpression = null;
    {
      final Object canBeEmpty = JavaPluginUtils.getCanBeEmpty(property);
      final Object pattern = JavaPluginUtils.getPattern(property);
      final Object sizeMin = JavaPluginUtils.getSizeMin(property);
      final Object length = Utils.getAttributeLength(property);
      StringConcatenation _builder = new StringConcatenation();
      {
        boolean _equals = Objects.equal(canBeEmpty, Boolean.valueOf(false));
        if (_equals) {
          _builder.append("@NotEmpty");
          _builder.newLine();
        }
      }
      {
        if ((pattern != null)) {
          _builder.append("@Pattern(regexp = \"");
          _builder.append(pattern);
          _builder.append("\")");
          _builder.newLineIfNotEmpty();
        }
      }
      {
        if (((sizeMin != null) && (length != null))) {
          _builder.append("@Size( min = ");
          _builder.append(sizeMin);
          _builder.append(", max = ");
          _builder.append(length);
          _builder.append(")");
          _builder.newLineIfNotEmpty();
        } else {
          if ((sizeMin != null)) {
            _builder.append("@Size( min = ");
            _builder.append(sizeMin);
            _builder.append(")");
            _builder.newLineIfNotEmpty();
          } else {
            if ((length != null)) {
              _builder.append("@Size( max = ");
              _builder.append(length);
              _builder.append(")");
              _builder.newLineIfNotEmpty();
            }
          }
        }
      }
      _xblockexpression = _builder;
    }
    return _xblockexpression;
  }
  
  public static CharSequence generateNumericTypedAnnotation(final Property property) {
    CharSequence _xblockexpression = null;
    {
      final Object min = JavaPluginUtils.getMin(property);
      final Object max = JavaPluginUtils.getMax(property);
      final Object negative = JavaPluginUtils.getNegative(property);
      final Object negativeOrZero = JavaPluginUtils.getNegativeOrZero(property);
      final Object positive = JavaPluginUtils.getPositive(property);
      final Object positiveOrZero = JavaPluginUtils.getPositiveOrZero(property);
      final Object digits = JavaPluginUtils.getDigits(property);
      final Object digitsInteger = JavaPluginUtils.getDigitsInteger(property);
      final Object digitsFraction = JavaPluginUtils.getDigitsFraction(property);
      final Object decimalMin = JavaPluginUtils.getDecimalMin(property);
      final Object decimalMax = JavaPluginUtils.getDecimalMax(property);
      StringConcatenation _builder = new StringConcatenation();
      {
        if ((min != null)) {
          _builder.append("@Min(");
          _builder.append(min);
          _builder.append(")");
          _builder.newLineIfNotEmpty();
        }
      }
      {
        if ((max != null)) {
          _builder.append("@Max(");
          _builder.append(max);
          _builder.append(")");
          _builder.newLineIfNotEmpty();
        }
      }
      {
        boolean _equals = Objects.equal(negative, Boolean.valueOf(true));
        if (_equals) {
          _builder.append("@Negative");
          _builder.newLine();
        } else {
          boolean _equals_1 = Objects.equal(negativeOrZero, Boolean.valueOf(true));
          if (_equals_1) {
            _builder.append("@NegativeOrZero");
            _builder.newLine();
          } else {
            boolean _equals_2 = Objects.equal(positive, Boolean.valueOf(true));
            if (_equals_2) {
              _builder.append("@Positive");
              _builder.newLine();
            } else {
              boolean _equals_3 = Objects.equal(positiveOrZero, Boolean.valueOf(true));
              if (_equals_3) {
                _builder.append("@PositiveOrZero");
                _builder.newLine();
              }
            }
          }
        }
      }
      {
        boolean _equals_4 = Objects.equal(digits, Boolean.valueOf(true));
        if (_equals_4) {
          {
            if (((digitsInteger != null) && (digitsFraction != null))) {
              _builder.append("@Digits(integer = ");
              _builder.append(digitsInteger);
              _builder.append(", fraction = ");
              _builder.append(digitsFraction);
              _builder.append(")");
              _builder.newLineIfNotEmpty();
            } else {
              if ((digitsInteger != null)) {
                _builder.append("@Digits(integer = ");
                _builder.append(digitsInteger);
                _builder.append(")");
                _builder.newLineIfNotEmpty();
              } else {
                if ((digitsFraction != null)) {
                  _builder.append("@Digits(fraction = ");
                  _builder.append(digitsFraction);
                  _builder.append(")");
                  _builder.newLineIfNotEmpty();
                }
              }
            }
          }
        }
      }
      {
        if ((decimalMin != null)) {
          _builder.append("@DecimalMin(\"");
          _builder.append(decimalMin);
          _builder.append("\")");
          _builder.newLineIfNotEmpty();
        }
      }
      {
        if ((decimalMax != null)) {
          _builder.append("@DecimalMax(\"");
          _builder.append(decimalMax);
          _builder.append("\")");
          _builder.newLineIfNotEmpty();
        }
      }
      _xblockexpression = _builder;
    }
    return _xblockexpression;
  }
  
  public static CharSequence generateCollectionAnnotation(final Property property) {
    CharSequence _xblockexpression = null;
    {
      final Object sizeMin = JavaPluginUtils.getCollectionSizeMin(property);
      final Object sizeMax = JavaPluginUtils.getCollectionSizeMax(property);
      StringConcatenation _builder = new StringConcatenation();
      {
        if (((sizeMin != null) && (sizeMax != null))) {
          _builder.append("@Size( min = ");
          _builder.append(sizeMin);
          _builder.append(", max = ");
          _builder.append(sizeMax);
          _builder.append(")");
          _builder.newLineIfNotEmpty();
        } else {
          if ((sizeMin != null)) {
            _builder.append("@Size( min = ");
            _builder.append(sizeMin);
            _builder.append(")");
            _builder.newLineIfNotEmpty();
          } else {
            if ((sizeMax != null)) {
              _builder.append("@Size( max = ");
              _builder.append(sizeMax);
              _builder.append(")");
              _builder.newLineIfNotEmpty();
            }
          }
        }
      }
      _xblockexpression = _builder;
    }
    return _xblockexpression;
  }
  
  /**
   * génère les override pour les classe embeddable dans une autre classe
   */
  public static CharSequence generateVOOverrides(final Property property, final String name) {
    CharSequence _xblockexpression = null;
    {
      Type _type = property.getType();
      final Function1<Property, Boolean> _function = (Property attr) -> {
        return Boolean.valueOf(((!Utils.isEntity(attr.getType())) && (!Utils.isValueObject(attr.getType()))));
      };
      final Iterable<Property> basicAttributes = IterableExtensions.<Property>filter(ClassifierUtils.getOwnedAttributes(((Classifier) _type)), _function);
      Type _type_1 = property.getType();
      final Function1<Property, Boolean> _function_1 = (Property attr) -> {
        return Boolean.valueOf(Utils.isEntity(attr.getType()));
      };
      final Iterable<Property> entityAttributes = IterableExtensions.<Property>filter(ClassifierUtils.getOwnedAttributes(((Classifier) _type_1)), _function_1);
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("@AttributeOverrides({");
      _builder.newLine();
      _builder.append("\t");
      final Function2<String, Property, String> _function_2 = (String acc, Property attribut) -> {
        String _xifexpression = null;
        boolean _equals = Objects.equal(acc, "");
        if (_equals) {
          StringConcatenation _builder_1 = new StringConcatenation();
          CharSequence _generateAttributeOverride = ClassifierJPAEntityGenerator.generateAttributeOverride(attribut, name);
          _builder_1.append(_generateAttributeOverride);
          _xifexpression = (acc + _builder_1);
        } else {
          StringConcatenation _builder_2 = new StringConcatenation();
          _builder_2.append(",");
          _builder_2.newLine();
          _builder_2.append("\t");
          CharSequence _generateAttributeOverride_1 = ClassifierJPAEntityGenerator.generateAttributeOverride(attribut, name);
          _builder_2.append(_generateAttributeOverride_1, "\t");
          _xifexpression = (acc + _builder_2);
        }
        return _xifexpression;
      };
      String _fold = IterableExtensions.<Property, String>fold(basicAttributes, "", _function_2);
      _builder.append(_fold, "\t");
      _builder.newLineIfNotEmpty();
      _builder.append("})");
      _builder.newLine();
      _builder.append("@AssociationOverrides({");
      _builder.newLine();
      _builder.append("\t");
      final Function2<String, Property, String> _function_3 = (String acc, Property attribut) -> {
        String _xifexpression = null;
        boolean _equals = Objects.equal(acc, "");
        if (_equals) {
          StringConcatenation _builder_1 = new StringConcatenation();
          CharSequence _generateAssociationOverride = ClassifierJPAEntityGenerator.generateAssociationOverride(attribut, name);
          _builder_1.append(_generateAssociationOverride);
          _xifexpression = (acc + _builder_1);
        } else {
          StringConcatenation _builder_2 = new StringConcatenation();
          _builder_2.append(",");
          _builder_2.newLine();
          _builder_2.append("\t");
          CharSequence _generateAssociationOverride_1 = ClassifierJPAEntityGenerator.generateAssociationOverride(attribut, name);
          _builder_2.append(_generateAssociationOverride_1, "\t");
          _xifexpression = (acc + _builder_2);
        }
        return _xifexpression;
      };
      String _fold_1 = IterableExtensions.<Property, String>fold(entityAttributes, "", _function_3);
      _builder.append(_fold_1, "\t");
      _builder.newLineIfNotEmpty();
      _builder.append("})");
      _xblockexpression = _builder;
    }
    return _xblockexpression;
  }
  
  public static CharSequence generateAttributeOverride(final Property property, final String attributName) {
    CharSequence _xifexpression = null;
    if (((!Utils.isEntity(property.getType())) && (!Utils.isValueObject(property.getType())))) {
      CharSequence _xblockexpression = null;
      {
        final String propName = PropertyUtils.getDatabaseName(property, property.getName(), "");
        StringConcatenation _builder = new StringConcatenation();
        _builder.append("@AttributeOverride(name=\"");
        String _name = property.getName();
        _builder.append(_name);
        _builder.append("\",column = @Column(name = \"");
        _builder.append(attributName);
        _builder.append("_");
        _builder.append(propName);
        _builder.append("\"))");
        _xblockexpression = _builder;
      }
      _xifexpression = _xblockexpression;
    } else {
      StringConcatenation _builder = new StringConcatenation();
      return _builder.toString();
    }
    return _xifexpression;
  }
  
  public static CharSequence generateAssociationOverride(final Property property, final String attributName) {
    CharSequence _xblockexpression = null;
    {
      final Type type = property.getType();
      CharSequence _xifexpression = null;
      boolean _isEntity = Utils.isEntity(type);
      if (_isEntity) {
        CharSequence _xblockexpression_1 = null;
        {
          final Property id = ((Property[])Conversions.unwrapArray(ClassifierUtils.getId(((Classifier) type)), Property.class))[0];
          final String propName = PropertyUtils.getDatabaseName(property, property.getName(), "");
          final String idName = PropertyUtils.getDatabaseName(id, id.getName(), "");
          StringConcatenation _builder = new StringConcatenation();
          _builder.append("@AssociationOverride(name=\"");
          String _name = property.getName();
          _builder.append(_name);
          _builder.append("\",joinColumns = @JoinColumn(name = \"");
          _builder.append(attributName);
          _builder.append("_");
          _builder.append(idName);
          _builder.append("_");
          _builder.append(propName);
          _builder.append("\"))");
          _xblockexpression_1 = _builder;
        }
        _xifexpression = _xblockexpression_1;
      } else {
        StringConcatenation _builder = new StringConcatenation();
        return _builder.toString();
      }
      _xblockexpression = _xifexpression;
    }
    return _xblockexpression;
  }
  
  public static String generateCompareTo(final Classifier clazz) {
    Property naturalOrder = JavaClassifierUtils.getNaturalOrderField(clazz);
    if ((naturalOrder == null)) {
      StringConcatenation _builder = new StringConcatenation();
      return _builder.toString();
    } else {
      StringConcatenation _builder_1 = new StringConcatenation();
      _builder_1.newLine();
      _builder_1.append("@Override");
      _builder_1.newLine();
      _builder_1.append("public int compareTo(");
      String _name = clazz.getName();
      _builder_1.append(_name);
      _builder_1.append(" other) {");
      _builder_1.newLineIfNotEmpty();
      _builder_1.append("\t");
      _builder_1.append("return this.");
      String _name_1 = naturalOrder.getName();
      _builder_1.append(_name_1, "\t");
      _builder_1.append(".compareTo(other.");
      String _name_2 = naturalOrder.getName();
      _builder_1.append(_name_2, "\t");
      _builder_1.append(");");
      _builder_1.newLineIfNotEmpty();
      _builder_1.append("}");
      _builder_1.newLine();
      return _builder_1.toString();
    }
  }
  
  /**
   * génère les extends
   */
  public static CharSequence generateExtends(final Classifier clazz) {
    CharSequence _xblockexpression = null;
    {
      final EList<Generalization> parents = clazz.getGeneralizations();
      CharSequence _xifexpression = null;
      int _length = ((Object[])Conversions.unwrapArray(parents, Object.class)).length;
      boolean _greaterThan = (_length > 0);
      if (_greaterThan) {
        CharSequence _xblockexpression_1 = null;
        {
          final Generalization parent = parents.get(0);
          StringConcatenation _builder = new StringConcatenation();
          _builder.append("extends ");
          String _name = parent.getGeneral().getName();
          _builder.append(_name);
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
   * génère les implements
   */
  public static CharSequence generateImplements(final Classifier clazz) {
    CharSequence _xblockexpression = null;
    {
      final EList<Generalization> extends_ = clazz.getGeneralizations();
      final Property naturalOrder = JavaClassifierUtils.getNaturalOrderField(clazz);
      String comparable = "";
      if ((naturalOrder != null)) {
        StringConcatenation _builder = new StringConcatenation();
        _builder.append(" ");
        _builder.append("Comparable<");
        String _name = clazz.getName();
        _builder.append(_name, " ");
        _builder.append(">");
        comparable = _builder.toString();
      }
      CharSequence _xifexpression = null;
      int _length = ((Object[])Conversions.unwrapArray(extends_, Object.class)).length;
      boolean _greaterThan = (_length > 0);
      if (_greaterThan) {
        StringConcatenation _builder_1 = new StringConcatenation();
        {
          if ((comparable != "")) {
            _builder_1.append("implements ");
            _builder_1.append(comparable);
          }
        }
        _xifexpression = _builder_1;
      } else {
        StringConcatenation _builder_2 = new StringConcatenation();
        _builder_2.append("implements Serializable");
        {
          if ((comparable != "")) {
            _builder_2.append(",");
            _builder_2.append(comparable);
          }
        }
        _xifexpression = _builder_2;
      }
      _xblockexpression = _xifexpression;
    }
    return _xblockexpression;
  }
}
