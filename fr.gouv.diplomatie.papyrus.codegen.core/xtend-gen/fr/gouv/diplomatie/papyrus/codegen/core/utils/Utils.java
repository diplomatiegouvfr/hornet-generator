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
import fr.gouv.diplomatie.papyrus.codegen.core.console.ConsoleUtils;
import java.io.File;
import java.util.ArrayList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.Comment;
import org.eclipse.uml2.uml.Generalization;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.Stereotype;
import org.eclipse.uml2.uml.Type;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;
import org.eclipse.xtext.xbase.lib.Conversions;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.Functions.Function2;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.StringExtensions;

@SuppressWarnings("all")
public class Utils {
  public static ConsoleUtils console = new ConsoleUtils();
  
  public static String MODEL_ENTITY = "entity";
  
  public static String MODEL_ENTITY_GENERATED = "generated";
  
  public static String MODEL_ENTITY_TABLENAME = "tableName";
  
  public static String MODEL_ENTITY_PERSISTENT = "isPersistent";
  
  public static String MODEL_NOMENCLATURE = "nomenclature";
  
  public static String MODEL_NOMENCLATURE_VALEURS = "valeurs";
  
  public static String MODEL_NOMENCLATURE_GENERATED = "generated";
  
  public static String MODEL_NOMENCLATURE_TABLENAME = "tableName";
  
  public static String MODEL_CODELIBELLENOMENCLATURE = "CodeLibelleNomenclature";
  
  public static String MODEL_CODELIBELLENOMENCLATURE_CODE = "code";
  
  public static String MODEL_CODELIBELLENOMENCLATURE_LIBELLE = "libelle";
  
  public static String MODEL_VALUEOBJECT = "valueObject";
  
  public static String MODEL_VALUEOBJECT_GENERATED = "generated";
  
  public static String MODEL_VALUEOBJECT_PERSISTENT = "isPersistent";
  
  public static String MODEL_ATTRIBUTE = "attribute";
  
  public static String MODEL_ATTRIBUTE_SEARCHABLE = "searchable";
  
  public static String MODEL_ATTRIBUTE_LENGTH = "length";
  
  public static String MODEL_ATTRIBUTE_PERSISTENT = "isPersistent";
  
  public static String MODEL_ATTRIBUTE_SEARCHANALYZER = "searchAnalyzer";
  
  public static String MODEL_ATTRIBUTE_COLUMNNAME = "columnName";
  
  public static String MODEL_KEYATTRIBUTE = "keyAttribute";
  
  public static String MODEL_SEQUENCE = "sequence";
  
  public static String MODEL_SEQUENCE_STARTWITH = "startWith";
  
  public static String MODEL_SEQUENCE_INCREMENTBY = "incrementBy";
  
  public static String MODEL_SEQUENCE_HASMINVALUE = "hasMinValue";
  
  public static String MODEL_SEQUENCE_MINVALUE = "minValue";
  
  public static String MODEL_SEQUENCE_HASMAXVALUE = "hasMaxValue";
  
  public static String MODEL_SEQUENCE_MAXVALUE = "maxValue";
  
  public static String MODEL_SEQUENCE_CACHE = "cache";
  
  public static String MODEL_SEQUENCE_CYCLE = "cycle";
  
  public static String MODEL_APPLICATION = "application";
  
  public static String MODEL_APPLICATION_ROOTPACKAGE = "rootPackage";
  
  public static String MODEL_HORNETTYPE = "hornetType";
  
  /**
   * génère une chaine a partir d'une liste
   */
  public static String generateList(final Iterable list) {
    String retour = "";
    for (final Object elem : list) {
      if ((elem instanceof NamedElement)) {
        String _retour = retour;
        String _name = ((NamedElement)elem).getName();
        String _plus = ("\"" + _name);
        String _plus_1 = (_plus + "\"");
        String _plus_2 = (_plus_1 + ", ");
        retour = (_retour + _plus_2);
      } else {
        String _retour_1 = retour;
        retour = (_retour_1 + ((("\"" + elem) + "\"") + ", "));
      }
    }
    int _length = retour.length();
    int _minus = (_length - 2);
    return retour.substring(0, _minus);
  }
  
  /**
   * renvoie la chaine en lettres minuscules
   */
  public static String lowerCase(final String str) {
    return str.toLowerCase();
  }
  
  /**
   * renvoie la chaine en lettres capitales
   */
  public static String capitalize(final String s) {
    if (((s == null) || (s.length() == 0))) {
      return s;
    }
    boolean _isUpperCase = Character.isUpperCase(s.charAt(0));
    if (_isUpperCase) {
      return s;
    }
    int _length = s.length();
    boolean _equals = (_length == 1);
    if (_equals) {
      return s.toUpperCase();
    }
    String _upperCase = s.substring(0, 1).toUpperCase();
    String _substring = s.substring(1);
    return (_upperCase + _substring);
  }
  
  /**
   * teste si un element est de type entity
   */
  public static boolean isEntity(final NamedElement elem) {
    if ((elem != null)) {
      return ((Utils.getStereotype(elem, Utils.MODEL_ENTITY) != null) && (!IterableExtensions.isEmpty(Utils.getStereotype(elem, Utils.MODEL_ENTITY))));
    } else {
      return false;
    }
  }
  
  /**
   * teste si un element est de type valueObject
   */
  public static boolean isValueObject(final NamedElement elem) {
    if ((elem != null)) {
      return ((Utils.getStereotype(elem, Utils.MODEL_VALUEOBJECT) != null) && (!IterableExtensions.isEmpty(Utils.getStereotype(elem, Utils.MODEL_VALUEOBJECT))));
    } else {
      return false;
    }
  }
  
  /**
   * teste si un element est de type sequence
   */
  public static boolean isSequence(final NamedElement elem) {
    if ((elem != null)) {
      return ((Utils.getStereotype(elem, Utils.MODEL_SEQUENCE) != null) && (!IterableExtensions.isEmpty(Utils.getStereotype(elem, Utils.MODEL_SEQUENCE))));
    } else {
      return false;
    }
  }
  
  /**
   * teste si un element est un enum
   */
  public static boolean isNomenclature(final NamedElement elem) {
    if ((elem != null)) {
      return ((Utils.getStereotype(elem, Utils.MODEL_NOMENCLATURE) != null) && (!IterableExtensions.isEmpty(Utils.getStereotype(elem, Utils.MODEL_NOMENCLATURE))));
    } else {
      return false;
    }
  }
  
  /**
   * retourne la classe Stereotype
   */
  public static Iterable<Stereotype> getStereotype(final NamedElement clazz, final String stereoname) {
    final Function1<Stereotype, Boolean> _function = (Stereotype stereotype) -> {
      String _name = stereotype.getName();
      return Boolean.valueOf(Objects.equal(_name, stereoname));
    };
    return IterableExtensions.<Stereotype>filter(clazz.getAppliedStereotypes(), _function);
  }
  
  /**
   * retourne la valeur de la propriété liée au stereotype
   */
  public static Object getStereotypePropertyValue(final NamedElement elem, final String stereoname, final String property) {
    if (((Utils.getStereotype(elem, stereoname) == null) || IterableExtensions.isEmpty(Utils.getStereotype(elem, stereoname)))) {
      return null;
    }
    final Stereotype stereotype = ((Stereotype[])Conversions.unwrapArray(Utils.getStereotype(elem, stereoname), Stereotype.class))[0];
    if ((stereotype == null)) {
      return null;
    }
    return elem.getValue(stereotype, property);
  }
  
  /**
   * chemin de fichier passer en notation pointée
   */
  public static String toPath(final String path) {
    return path.replaceAll("\\.", File.separator);
  }
  
  /**
   * inverse
   */
  public static String toImport(final String path) {
    return path.replaceAll("\\/", ".");
  }
  
  /**
   * Génère les commentaires
   */
  public static String generateComments(final NamedElement elem) {
    final EList<Comment> comments = elem.getOwnedComments();
    if (((comments != null) && (!comments.isEmpty()))) {
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("/**");
      _builder.newLine();
      final Function2<String, Comment, String> _function = (String acc, Comment comment) -> {
        StringConcatenation _builder_1 = new StringConcatenation();
        _builder_1.append("* ");
        String _body = comment.getBody();
        _builder_1.append(_body);
        return (acc + _builder_1);
      };
      String _fold = IterableExtensions.<Comment, String>fold(comments, "", _function);
      _builder.append(_fold);
      _builder.newLineIfNotEmpty();
      _builder.append("*/");
      _builder.newLine();
      return _builder.toString();
    } else {
      StringConcatenation _builder_1 = new StringConcatenation();
      return _builder_1.toString();
    }
  }
  
  /**
   * renvoie la chaine avec la première lettre en minuscule
   */
  public static String getFirstToLowerCase(final String str) {
    return StringExtensions.toFirstLower(str);
  }
  
  /**
   * renvoie la chaine avec la premiere lettre en majuscule
   */
  public static String getFirstToUpperCase(final String str) {
    return StringExtensions.toFirstUpper(str);
  }
  
  /**
   * convertit la chaine en snake case
   */
  public static String toSnakeCase(final String varName) {
    String result = "";
    String[] cutName = varName.split("(?=\\p{Upper})");
    for (final String name : cutName) {
      boolean _equals = Objects.equal(result, "");
      if (_equals) {
        String _result = result;
        String _lowerCase = name.toLowerCase();
        result = (_result + _lowerCase);
      } else {
        String _result_1 = result;
        String _lowerCase_1 = name.toLowerCase();
        String _plus = ("_" + _lowerCase_1);
        result = (_result_1 + _plus);
      }
    }
    return result;
  }
  
  /**
   * convertit la chaine en nom de fichier typescript
   */
  public static String toTypescriptFileName(final String varName) {
    String result = "";
    String[] cutName = varName.split("(?=\\p{Upper})");
    for (final String name : cutName) {
      boolean _equals = Objects.equal(result, "");
      if (_equals) {
        String _result = result;
        String _lowerCase = name.toLowerCase();
        result = (_result + _lowerCase);
      } else {
        String _result_1 = result;
        String _lowerCase_1 = name.toLowerCase();
        String _plus = ("-" + _lowerCase_1);
        result = (_result_1 + _plus);
      }
    }
    return result;
  }
  
  /**
   * si additionnalName est définit ajoute le nom additionnel devant le champs
   */
  public static String addAdditionnalName(final String additionnalName, final String name) {
    String newName = "";
    if (((!Objects.equal(additionnalName, "")) && (additionnalName != null))) {
      String _firstToUpperCase = Utils.getFirstToUpperCase(name);
      String _plus = (additionnalName + _firstToUpperCase);
      newName = _plus;
    } else {
      newName = name;
    }
    return newName;
  }
  
  /**
   * renvoie la liste en notation pointée
   */
  public static String getListPoint(final ArrayList<String> names) {
    String result = "";
    for (final String name : names) {
      boolean _equals = Objects.equal(result, "");
      if (_equals) {
        String _result = result;
        result = (_result + name);
      } else {
        String _result_1 = result;
        result = (_result_1 + ("." + name));
      }
    }
    return result;
  }
  
  /**
   * renvoie la liste des noms séparés par des virgules
   */
  public static String getListComma(final ArrayList<String> names) {
    String result = "";
    for (final String name : names) {
      boolean _equals = Objects.equal(result, "");
      if (_equals) {
        String _result = result;
        result = (_result + name);
      } else {
        String _result_1 = result;
        result = (_result_1 + (", " + name));
      }
    }
    return result;
  }
  
  /**
   * renvoie la liste en notation pointée
   */
  public static String getNameFromList(final ArrayList<String> names) {
    String result = "";
    for (final String name : names) {
      boolean _equals = Objects.equal(result, "");
      if (_equals) {
        String _result = result;
        result = (_result + name);
      } else {
        String _result_1 = result;
        String _firstToUpperCase = Utils.getFirstToUpperCase(name);
        result = (_result_1 + _firstToUpperCase);
      }
    }
    return result;
  }
  
  /**
   * package du projet
   */
  public static Object getRootPackage(final NamedElement elem) {
    if ((elem != null)) {
      final Model model = elem.getModel();
      return Utils.getStereotypePropertyValue(model, Utils.MODEL_APPLICATION, Utils.MODEL_APPLICATION_ROOTPACKAGE);
    }
    return null;
  }
  
  public static String getPackagePath(final NamedElement elem) {
    String path = "";
    Object _rootPackage = Utils.getRootPackage(elem);
    boolean _tripleNotEquals = (_rootPackage != null);
    if (_tripleNotEquals) {
      final String rootPackage = Utils.getRootPackage(elem).toString();
      rootPackage.replace("\\", File.separator);
      rootPackage.replace("/", File.separator);
      path = Utils.toPath(rootPackage);
    } else {
      path = (((("fr" + File.separator) + "gouv") + File.separator) + "diplomatie");
    }
    return path;
  }
  
  /**
   * teste si une liste contiens un élément dont le nom est name
   */
  public static boolean containsName(final ArrayList<NamedElement> list, final String name) {
    for (final NamedElement elem : list) {
      String _name = elem.getName();
      boolean _equals = Objects.equal(_name, name);
      if (_equals) {
        return true;
      }
    }
    return false;
  }
  
  /**
   * teste si un type est ou étend le type name
   */
  public static boolean isType(final Type type, final String name) {
    ArrayList<NamedElement> generalization = CollectionLiterals.<NamedElement>newArrayList();
    if ((type instanceof Classifier)) {
      EList<Generalization> _generalizations = ((Classifier)type).getGeneralizations();
      for (final Generalization elem : _generalizations) {
        generalization.add(elem.getGeneral());
      }
    }
    if ((Objects.equal(type.getName(), name) || (((generalization != null) && (!generalization.isEmpty())) && Utils.containsName(generalization, name)))) {
      return true;
    }
    return false;
  }
}
