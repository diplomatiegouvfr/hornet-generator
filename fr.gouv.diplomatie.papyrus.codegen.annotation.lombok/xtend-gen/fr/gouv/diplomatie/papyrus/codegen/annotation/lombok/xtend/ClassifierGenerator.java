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
package fr.gouv.diplomatie.papyrus.codegen.annotation.lombok.xtend;

import com.google.common.base.Objects;
import fr.gouv.diplomatie.papyrus.codegen.annotation.lombok.utils.LombokClassifierUtils;
import fr.gouv.diplomatie.papyrus.codegen.core.utils.ClassifierUtils;
import fr.gouv.diplomatie.papyrus.codegen.core.utils.Utils;
import java.util.ArrayList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.uml2.uml.AssociationClass;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.Interface;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Type;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.xbase.lib.Conversions;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;

@SuppressWarnings("all")
public class ClassifierGenerator {
  public static CharSequence generateImports(final Classifier clazz) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("import lombok.AllArgsConstructor;");
    _builder.newLine();
    _builder.append("import lombok.EqualsAndHashCode;");
    _builder.newLine();
    _builder.append("import lombok.Getter;");
    _builder.newLine();
    _builder.append("import lombok.NoArgsConstructor;");
    _builder.newLine();
    _builder.append("import lombok.Setter;");
    _builder.newLine();
    _builder.append("import lombok.ToString;");
    _builder.newLine();
    return _builder;
  }
  
  /**
   * génère les annotations de la classe
   */
  public static CharSequence generateAnnotations(final Classifier clazz) {
    CharSequence _xblockexpression = null;
    {
      Object hasGetter = LombokClassifierUtils.hasGetterAnnotation(clazz);
      Object hasSetter = LombokClassifierUtils.hasSetterAnnotation(clazz);
      Object hasNoArgsCons = LombokClassifierUtils.hasNoArgsConstructor(clazz);
      Object hasAllArgsCons = LombokClassifierUtils.hasAllArgsConstructor(clazz);
      Object hasToString = LombokClassifierUtils.hasToString(clazz);
      Object hasEqAndhashCode = LombokClassifierUtils.hasEqualsAndHashCode(clazz);
      final boolean needToString = ClassifierGenerator.hasAttributesNotInToString(clazz);
      StringConcatenation _builder = new StringConcatenation();
      {
        boolean _equals = Objects.equal(hasGetter, Boolean.valueOf(true));
        if (_equals) {
          _builder.append("@Getter");
        }
      }
      _builder.newLineIfNotEmpty();
      {
        boolean _equals_1 = Objects.equal(hasSetter, Boolean.valueOf(true));
        if (_equals_1) {
          _builder.append("@Setter");
        }
      }
      _builder.newLineIfNotEmpty();
      {
        boolean _equals_2 = Objects.equal(hasNoArgsCons, Boolean.valueOf(true));
        if (_equals_2) {
          _builder.append("@NoArgsConstructor");
        }
      }
      _builder.newLineIfNotEmpty();
      {
        boolean _equals_3 = Objects.equal(hasAllArgsCons, Boolean.valueOf(true));
        if (_equals_3) {
          _builder.append("@AllArgsConstructor");
        }
      }
      _builder.newLineIfNotEmpty();
      {
        boolean _equals_4 = Objects.equal(hasToString, Boolean.valueOf(true));
        if (_equals_4) {
          _builder.append("@ToString");
          {
            if (needToString) {
              _builder.append("(of = {");
              String _generateToStringContent = ClassifierGenerator.generateToStringContent(clazz);
              _builder.append(_generateToStringContent);
              _builder.newLineIfNotEmpty();
              _builder.append("})");
            }
          }
        }
      }
      _builder.newLineIfNotEmpty();
      {
        boolean _equals_5 = Objects.equal(hasEqAndhashCode, Boolean.valueOf(true));
        if (_equals_5) {
          _builder.append("@EqualsAndHashCode");
        }
      }
      _builder.newLineIfNotEmpty();
      _xblockexpression = _builder;
    }
    return _xblockexpression;
  }
  
  /**
   * génère le contenu de l'annotation toString
   */
  public static String generateToStringContent(final Classifier clazz) {
    String names = "";
    final Function1<Property, Boolean> _function = (Property attribut) -> {
      Boolean _lombokDisplayWith = LombokClassifierUtils.getLombokDisplayWith(attribut);
      return Boolean.valueOf(((_lombokDisplayWith).booleanValue() == true));
    };
    final Iterable<Property> attributes = IterableExtensions.<Property>filter(ClassifierUtils.getOwnedAttributes(clazz), _function);
    for (final Property attribut : attributes) {
      boolean _equals = Objects.equal(names, "");
      if (_equals) {
        String _names = names;
        StringConcatenation _builder = new StringConcatenation();
        _builder.append("\"");
        String _name = attribut.getName();
        _builder.append(_name);
        _builder.append("\"");
        names = (_names + _builder);
      } else {
        String _names_1 = names;
        StringConcatenation _builder_1 = new StringConcatenation();
        _builder_1.append(",");
        _builder_1.newLine();
        _builder_1.append("\t");
        _builder_1.append("\"");
        String _name_1 = attribut.getName();
        _builder_1.append(_name_1, "\t");
        _builder_1.append("\"");
        names = (_names_1 + _builder_1);
      }
    }
    final EList<Interface> interfaces = clazz.directlyRealizedInterfaces();
    for (final Interface interface_ : interfaces) {
      {
        final Function1<Property, Boolean> _function_1 = (Property attribut_1) -> {
          Boolean _lombokDisplayWith = LombokClassifierUtils.getLombokDisplayWith(attribut_1);
          return Boolean.valueOf(((_lombokDisplayWith).booleanValue() == true));
        };
        Iterable<Property> interfaceAtteributes = IterableExtensions.<Property>filter(ClassifierUtils.getOwnedAttributes(interface_), _function_1);
        for (final Property attribut_1 : interfaceAtteributes) {
          boolean _equals_1 = Objects.equal(names, "");
          if (_equals_1) {
            String _names_2 = names;
            StringConcatenation _builder_2 = new StringConcatenation();
            _builder_2.append("\"");
            String _name_2 = attribut_1.getName();
            _builder_2.append(_name_2);
            _builder_2.append("\"");
            names = (_names_2 + _builder_2);
          } else {
            String _names_3 = names;
            StringConcatenation _builder_3 = new StringConcatenation();
            _builder_3.append(",");
            _builder_3.newLine();
            _builder_3.append("\t");
            _builder_3.append("\"");
            String _name_3 = attribut_1.getName();
            _builder_3.append(_name_3, "\t");
            _builder_3.append("\"");
            names = (_names_3 + _builder_3);
          }
        }
      }
    }
    boolean _isEntity = Utils.isEntity(clazz);
    if (_isEntity) {
      final ArrayList<Type> associationsClasses = ClassifierUtils.getLinkedAssociationClass(clazz);
      for (final Type asso : associationsClasses) {
        Boolean _lombokDisplayWith = LombokClassifierUtils.getLombokDisplayWith(asso);
        boolean _equals_1 = ((_lombokDisplayWith).booleanValue() == true);
        if (_equals_1) {
          boolean _equals_2 = Objects.equal(names, "");
          if (_equals_2) {
            String _names_2 = names;
            StringConcatenation _builder_2 = new StringConcatenation();
            _builder_2.append("\"");
            String _firstToLowerCase = Utils.getFirstToLowerCase(asso.getName());
            _builder_2.append(_firstToLowerCase);
            _builder_2.append("\"");
            names = (_names_2 + _builder_2);
          } else {
            String _names_3 = names;
            StringConcatenation _builder_3 = new StringConcatenation();
            _builder_3.append(",");
            _builder_3.newLine();
            _builder_3.append("\t");
            _builder_3.append("\"");
            String _firstToLowerCase_1 = Utils.getFirstToLowerCase(asso.getName());
            _builder_3.append(_firstToLowerCase_1, "\t");
            _builder_3.append("\"");
            names = (_names_3 + _builder_3);
          }
        }
      }
    }
    return names;
  }
  
  /**
   * teste si la classe possède des attributs qui ne doivent pas etre présents dans le toString
   */
  public static boolean hasAttributesNotInToString(final Classifier clazz) {
    boolean has = false;
    final Function1<Property, Boolean> _function = (Property attribut) -> {
      Boolean _lombokDisplayWith = LombokClassifierUtils.getLombokDisplayWith(attribut);
      return Boolean.valueOf(((_lombokDisplayWith).booleanValue() == false));
    };
    Iterable<Property> attributes = IterableExtensions.<Property>filter(ClassifierUtils.getOwnedAttributes(clazz), _function);
    final Iterable<Property> _converted_attributes = (Iterable<Property>)attributes;
    int _length = ((Object[])Conversions.unwrapArray(_converted_attributes, Object.class)).length;
    boolean _greaterThan = (_length > 0);
    if (_greaterThan) {
      has = true;
    }
    final EList<Interface> interfaces = clazz.directlyRealizedInterfaces();
    for (final Interface interface_ : interfaces) {
      {
        final Function1<Property, Boolean> _function_1 = (Property attribut) -> {
          Boolean _lombokDisplayWith = LombokClassifierUtils.getLombokDisplayWith(attribut);
          return Boolean.valueOf(((_lombokDisplayWith).booleanValue() == false));
        };
        Iterable<Property> interfaceAtteributes = IterableExtensions.<Property>filter(ClassifierUtils.getOwnedAttributes(interface_), _function_1);
        final Iterable<Property> _converted_interfaceAtteributes = (Iterable<Property>)interfaceAtteributes;
        int _length_1 = ((Object[])Conversions.unwrapArray(_converted_interfaceAtteributes, Object.class)).length;
        boolean _greaterThan_1 = (_length_1 > 0);
        if (_greaterThan_1) {
          has = true;
        }
      }
    }
    boolean _isEntity = Utils.isEntity(clazz);
    if (_isEntity) {
      final ArrayList<Type> associationsClasses = ClassifierUtils.getLinkedAssociationClass(clazz);
      for (final Type asso : associationsClasses) {
        {
          final Function1<Property, Boolean> _function_1 = (Property property) -> {
            return Boolean.valueOf(((property.getType() != clazz) && ((LombokClassifierUtils.getLombokDisplayWith(asso)).booleanValue() == false)));
          };
          final Iterable<Property> end = IterableExtensions.<Property>filter(((AssociationClass) asso).getOwnedEnds(), _function_1);
          int _length_1 = ((Object[])Conversions.unwrapArray(end, Object.class)).length;
          boolean _greaterThan_1 = (_length_1 > 0);
          if (_greaterThan_1) {
            has = true;
          }
        }
      }
    }
    return has;
  }
}
