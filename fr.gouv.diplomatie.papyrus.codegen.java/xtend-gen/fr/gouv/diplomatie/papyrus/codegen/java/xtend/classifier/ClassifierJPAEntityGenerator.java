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
    _builder.append("import javax.persistence.Table;");
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
  
  public static String generateTypeAnnotation(final Classifier clazz) {
    boolean _isValueObject = Utils.isValueObject(clazz);
    if (_isValueObject) {
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("@Embeddable");
      return _builder.toString();
    }
    StringConcatenation _builder_1 = new StringConcatenation();
    _builder_1.append("@Entity");
    _builder_1.newLine();
    _builder_1.append("@Table(name = \"");
    String _tableName = ClassifierUtils.getTableName(clazz);
    _builder_1.append(_tableName);
    _builder_1.append("\")");
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
      final Iterable<Type> associationsClasses = ClassifierUtils.getLinkedAssociationClass(clazz);
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
        CharSequence _generateAttribut = ClassifierJPAEntityGenerator.generateAttribut(attribut, "", fromClass);
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
      final Iterable<Type> associationsClasses = ClassifierUtils.getLinkedAssociationClass(clazz);
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
      final Property idFrom = ((Property[])Conversions.unwrapArray(ClassifierUtils.getId(fromClass), Property.class))[0];
      final EnumerationLiteralImpl fetchType = JavaPluginUtils.getFetchType(clazz);
      CharSequence _xifexpression = null;
      boolean _isEntity = Utils.isEntity(end.getType());
      if (_isEntity) {
        CharSequence _xblockexpression_1 = null;
        {
          final String tableName = Utils.toSnakeCase(clazz.getName());
          String _name = idFrom.getName();
          String _firstToUpperCase = Utils.getFirstToUpperCase(((Classifier) fromClass).getName());
          String _plus = (_name + _firstToUpperCase);
          final String idFromName = Utils.toSnakeCase(_plus);
          Type _type = end.getType();
          final Property idTo = ((Property[])Conversions.unwrapArray(ClassifierUtils.getId(((Classifier) _type)), Property.class))[0];
          String _name_1 = idTo.getName();
          String _firstToUpperCase_1 = Utils.getFirstToUpperCase(end.getType().getName());
          String _plus_1 = (_name_1 + _firstToUpperCase_1);
          final String idToName = Utils.toSnakeCase(_plus_1);
          StringConcatenation _builder = new StringConcatenation();
          _builder.newLine();
          _builder.append("@ManyToMany");
          {
            if ((fetchType != null)) {
              _builder.append("(fetch=FetchType.");
              String _name_2 = fetchType.getName();
              _builder.append(_name_2);
              _builder.append(")");
            }
          }
          _builder.newLineIfNotEmpty();
          _builder.append("@JoinTable(name=\"");
          _builder.append(tableName);
          _builder.append("\", joinColumns=@JoinColumn(name=\"");
          _builder.append(idFromName);
          _builder.append("\", referencedColumnName=\"");
          String _snakeCase = Utils.toSnakeCase(idFrom.getName());
          _builder.append(_snakeCase);
          _builder.append("\"),");
          _builder.newLineIfNotEmpty();
          _builder.append("\t");
          _builder.append("inverseJoinColumns=@JoinColumn(name=\"");
          _builder.append(idToName, "\t");
          _builder.append("\", referencedColumnName=\"");
          String _snakeCase_1 = Utils.toSnakeCase(idTo.getName());
          _builder.append(_snakeCase_1, "\t");
          _builder.append("\"))");
          _builder.newLineIfNotEmpty();
          String _name_3 = clazz.getVisibility().getName();
          _builder.append(_name_3);
          _builder.append(" Set<");
          String _name_4 = end.getType().getName();
          _builder.append(_name_4);
          _builder.append("> ");
          String _firstToLowerCase = Utils.getFirstToLowerCase(clazz.getName());
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
          String _snakeCase = Utils.toSnakeCase(fromClass.getName());
          _builder.append(_snakeCase);
          _builder.append("_");
          String _snakeCase_1 = Utils.toSnakeCase(clazz.getName());
          _builder.append(_snakeCase_1);
          _builder.append("\", joinColumns = @JoinColumn(name = \"");
          String _snakeCase_2 = Utils.toSnakeCase(idFrom.getName());
          _builder.append(_snakeCase_2);
          _builder.append("\"))");
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
          CharSequence _xifexpression_2 = null;
          boolean _isNomenclature = Utils.isNomenclature(end.getType());
          if (_isNomenclature) {
            StringConcatenation _builder_1 = new StringConcatenation();
            _builder_1.newLine();
            _builder_1.append("@ElementCollection");
            _builder_1.newLine();
            _builder_1.append("@CollectionTable(name = \"");
            String _snakeCase_3 = Utils.toSnakeCase(fromClass.getName());
            _builder_1.append(_snakeCase_3);
            _builder_1.append("_");
            String _snakeCase_4 = Utils.toSnakeCase(clazz.getName());
            _builder_1.append(_snakeCase_4);
            _builder_1.append("\", joinColumns = @JoinColumn(name = \"");
            String _snakeCase_5 = Utils.toSnakeCase(idFrom.getName());
            _builder_1.append(_snakeCase_5);
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
            _xifexpression_2 = _builder_1;
          } else {
            StringConcatenation _builder_2 = new StringConcatenation();
            _builder_2.newLine();
            _builder_2.append("@ElementCollection");
            _builder_2.newLine();
            _builder_2.append("@CollectionTable(name = \"");
            String _snakeCase_6 = Utils.toSnakeCase(fromClass.getName());
            _builder_2.append(_snakeCase_6);
            _builder_2.append("_");
            String _snakeCase_7 = Utils.toSnakeCase(clazz.getName());
            _builder_2.append(_snakeCase_7);
            _builder_2.append("\", joinColumns = @JoinColumn(name = \"");
            String _snakeCase_8 = Utils.toSnakeCase(idFrom.getName());
            _builder_2.append(_snakeCase_8);
            _builder_2.append("\"))");
            _builder_2.newLineIfNotEmpty();
            String _name_4 = clazz.getVisibility().getName();
            _builder_2.append(_name_4);
            _builder_2.append(" Set<");
            String _name_5 = end.getType().getName();
            _builder_2.append(_name_5);
            _builder_2.append("> ");
            String _firstToLowerCase_2 = Utils.getFirstToLowerCase(clazz.getName());
            _builder_2.append(_firstToLowerCase_2);
            _builder_2.append(";");
            _builder_2.newLineIfNotEmpty();
            _xifexpression_2 = _builder_2;
          }
          _xifexpression_1 = _xifexpression_2;
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
  public static CharSequence generateAttribut(final Property property, final String additionnalName, final Classifier fromClass) {
    CharSequence _xifexpression = null;
    boolean _isValueObject = Utils.isValueObject(property.getType());
    if (_isValueObject) {
      StringConcatenation _builder = new StringConcatenation();
      CharSequence _generateVOAttribute = ClassifierJPAEntityGenerator.generateVOAttribute(property, additionnalName, fromClass);
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
            final String tableName = Utils.toSnakeCase(_plus);
            final Property idFrom = ((Property[])Conversions.unwrapArray(ClassifierUtils.getId(fromClass), Property.class))[0];
            String _name_1 = idFrom.getName();
            String _firstToUpperCase_1 = Utils.getFirstToUpperCase(((Classifier) fromClass).getName());
            String _plus_1 = (_name_1 + _firstToUpperCase_1);
            final String idFromName = Utils.toSnakeCase(_plus_1);
            Type _type_1 = property.getType();
            final Property idTo = ((Property[])Conversions.unwrapArray(ClassifierUtils.getId(((Classifier) _type_1)), Property.class))[0];
            String _name_2 = idTo.getName();
            String _firstToUpperCase_2 = Utils.getFirstToUpperCase(property.getName());
            String _plus_2 = (_name_2 + _firstToUpperCase_2);
            final String idToName = Utils.toSnakeCase(_plus_2);
            StringConcatenation _builder = new StringConcatenation();
            _builder.append("@ManyToMany");
            {
              if ((fetchType != null)) {
                _builder.append("(fetch=FetchType.");
                String _name_3 = fetchType.getName();
                _builder.append(_name_3);
                _builder.append(")");
              }
            }
            _builder.newLineIfNotEmpty();
            _builder.append("@JoinTable(name=\"");
            _builder.append(tableName);
            _builder.append("\", joinColumns=@JoinColumn(name=\"");
            _builder.append(idFromName);
            _builder.append("\", referencedColumnName=\"");
            String _snakeCase = Utils.toSnakeCase(idFrom.getName());
            _builder.append(_snakeCase);
            _builder.append("\"),");
            _builder.newLineIfNotEmpty();
            _builder.append("\t");
            _builder.append("inverseJoinColumns=@JoinColumn(name=\"");
            _builder.append(idToName, "\t");
            _builder.append("\", referencedColumnName=\"");
            String _snakeCase_1 = Utils.toSnakeCase(idTo.getName());
            _builder.append(_snakeCase_1, "\t");
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
              String _name_4 = fetchType.getName();
              _builder_1.append(_name_4);
              _builder_1.append(")");
            }
          }
          _builder_1.newLineIfNotEmpty();
          _builder_1.append("@JoinColumn(name = \"");
          String _databaseName = PropertyUtils.getDatabaseName(idTo_1, idTo_1.getName(), null);
          _builder_1.append(_databaseName);
          _builder_1.append("_");
          String _databaseName_1 = PropertyUtils.getDatabaseName(property, property.getName(), null);
          _builder_1.append(_databaseName_1);
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
      String lengthValue = "";
      final Object length = PropertyUtils.getStereotypePropertyValue(property, Utils.MODEL_ATTRIBUTE, Utils.MODEL_ATTRIBUTE_LENGTH);
      if (((length != null) && (!Objects.equal(length, Integer.valueOf(0))))) {
        StringConcatenation _builder_2 = new StringConcatenation();
        _builder_2.append("@Size(max=");
        _builder_2.append(length);
        _builder_2.append(")");
        lengthValue = _builder_2.toString();
      }
      boolean isAsso = ((property.getAssociation() != null) && Utils.isEntity(property.getType()));
      StringConcatenation _builder_3 = new StringConcatenation();
      _builder_3.newLine();
      String _generateAGAnnotations = ClassifierJPAEntityGenerator.generateAGAnnotations(property);
      _builder_3.append(_generateAGAnnotations);
      _builder_3.newLineIfNotEmpty();
      String _generateComments = Utils.generateComments(property);
      _builder_3.append(_generateComments);
      String _generateAGAnnotations_1 = ClassifierJPAEntityGenerator.generateAGAnnotations(property);
      _builder_3.append(_generateAGAnnotations_1);
      _builder_3.newLineIfNotEmpty();
      {
        if ((property.isMultivalued() && (!Utils.isEntity(property.getType())))) {
          _builder_3.append("@ElementCollection");
          _builder_3.newLine();
        } else {
          {
            if (isId) {
              _builder_3.append("@Id");
            }
          }
          {
            boolean _isSequence = Utils.isSequence(property);
            if (_isSequence) {
              _builder_3.newLineIfNotEmpty();
              _builder_3.append("@GeneratedValue(strategy=GenerationType.SEQUENCE)");
            }
          }
          _builder_3.newLineIfNotEmpty();
          {
            if (isEnum) {
              _builder_3.append("@Enumerated");
              _builder_3.newLine();
            }
          }
          {
            if ((Utils.isEntity(property.getType()) && (!isAsso))) {
              _builder_3.append(link);
            } else {
              if (isAsso) {
                _builder_3.newLineIfNotEmpty();
                CharSequence _generateAssociationAnnotation = ClassifierJPAEntityGenerator.generateAssociationAnnotation(property, fromClass);
                _builder_3.append(_generateAssociationAnnotation);
                _builder_3.newLineIfNotEmpty();
              } else {
                {
                  if ((nullable == false)) {
                    _builder_3.append("@NotNull");
                  }
                }
                _builder_3.newLineIfNotEmpty();
                {
                  boolean _notEquals = (!Objects.equal(lengthValue, ""));
                  if (_notEquals) {
                    _builder_3.append(lengthValue);
                  }
                }
                _builder_3.newLineIfNotEmpty();
                _builder_3.append("@Column(name = \"");
                _builder_3.append(columnName);
                _builder_3.append("\")");
                _builder_3.newLineIfNotEmpty();
              }
            }
          }
        }
      }
      String _name_5 = property.getVisibility().getName();
      _builder_3.append(_name_5);
      _builder_3.append(" ");
      _builder_3.append(array);
      _builder_3.append(typeName);
      _builder_3.append(endArray);
      _builder_3.append(" ");
      _builder_3.append(name);
      _builder_3.append(";");
      _builder_3.newLineIfNotEmpty();
      _xblockexpression = _builder_3;
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
      final String tableName = Utils.toSnakeCase(_plus);
      final Property idFrom = ((Property[])Conversions.unwrapArray(ClassifierUtils.getId(fromClass), Property.class))[0];
      String _name_1 = idFrom.getName();
      String _firstToUpperCase_1 = Utils.getFirstToUpperCase(((Classifier) fromClass).getName());
      String _plus_1 = (_name_1 + _firstToUpperCase_1);
      final String idFromName = Utils.toSnakeCase(_plus_1);
      String _name_2 = id.getName();
      String _firstToUpperCase_2 = Utils.getFirstToUpperCase(property.getName());
      String _plus_2 = (_name_2 + _firstToUpperCase_2);
      final String idToName = Utils.toSnakeCase(_plus_2);
      CharSequence _xifexpression = null;
      if (fromMultiplicity) {
        CharSequence _xifexpression_1 = null;
        if (toMultiplicity) {
          StringConcatenation _builder = new StringConcatenation();
          _builder.append("@ManyToMany");
          {
            if ((fetchType != null)) {
              _builder.append("(fetch=FetchType.");
              String _name_3 = fetchType.getName();
              _builder.append(_name_3);
              _builder.append(")");
            }
          }
          _builder.newLineIfNotEmpty();
          _builder.append("@JoinTable(name=\"");
          _builder.append(tableName);
          _builder.append("\", joinColumns=@JoinColumn(name=\"");
          _builder.append(idFromName);
          _builder.append("\", referencedColumnName=\"");
          String _snakeCase = Utils.toSnakeCase(idFrom.getName());
          _builder.append(_snakeCase);
          _builder.append("\"),");
          _builder.newLineIfNotEmpty();
          _builder.append("\t");
          _builder.append("inverseJoinColumns=@JoinColumn(name=\"");
          _builder.append(idToName, "\t");
          _builder.append("\", referencedColumnName=\"");
          String _snakeCase_1 = Utils.toSnakeCase(id.getName());
          _builder.append(_snakeCase_1, "\t");
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
              String _name_4 = fetchType.getName();
              _builder_1.append(_name_4);
              _builder_1.append(")");
            }
          }
          _builder_1.newLineIfNotEmpty();
          _builder_1.append("@JoinColumn(name = \"");
          String _databaseName = PropertyUtils.getDatabaseName(id, id.getName(), null);
          _builder_1.append(_databaseName);
          _builder_1.append("_");
          String _databaseName_1 = PropertyUtils.getDatabaseName(property, property.getName(), null);
          _builder_1.append(_databaseName_1);
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
              String _name_5 = fetchType.getName();
              _builder_2.append(_name_5);
              _builder_2.append(")");
            }
          }
          _builder_2.newLineIfNotEmpty();
          _builder_2.append("@JoinTable(name=\"");
          _builder_2.append(tableName);
          _builder_2.append("\", joinColumns=@JoinColumn(name=\"");
          _builder_2.append(idFromName);
          _builder_2.append("\", referencedColumnName=\"");
          String _snakeCase_2 = Utils.toSnakeCase(idFrom.getName());
          _builder_2.append(_snakeCase_2);
          _builder_2.append("\"),");
          _builder_2.newLineIfNotEmpty();
          _builder_2.append("\t");
          _builder_2.append("inverseJoinColumns=@JoinColumn(name=\"");
          _builder_2.append(idToName, "\t");
          _builder_2.append("\", referencedColumnName=\"");
          String _snakeCase_3 = Utils.toSnakeCase(id.getName());
          _builder_2.append(_snakeCase_3, "\t");
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
          String _databaseName_2 = PropertyUtils.getDatabaseName(id, id.getName(), null);
          _builder_3.append(_databaseName_2);
          _builder_3.append("_");
          String _databaseName_3 = PropertyUtils.getDatabaseName(property, property.getName(), null);
          _builder_3.append(_databaseName_3);
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
  
  public static CharSequence generateVOAttribute(final Property property, final String additionnalName, final Classifier fromClass) {
    CharSequence _xblockexpression = null;
    {
      String name = Utils.addAdditionnalName(additionnalName, property.getName());
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
          String _snakeCase = Utils.toSnakeCase(fromClass.getName());
          _builder.append(_snakeCase);
          _builder.append("_");
          String _snakeCase_1 = Utils.toSnakeCase(name);
          _builder.append(_snakeCase_1);
          _builder.append("\", joinColumns = @JoinColumn(name = \"");
          String _snakeCase_2 = Utils.toSnakeCase(id.getName());
          _builder.append(_snakeCase_2);
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
