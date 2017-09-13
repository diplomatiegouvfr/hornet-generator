package fr.gouv.diplomatie.papyrus.codegen.core.utils;

import com.google.common.base.Objects;
import fr.gouv.diplomatie.papyrus.codegen.core.console.ConsoleUtils;
import java.io.File;
import java.util.ArrayList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.uml2.uml.Comment;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.Stereotype;
import org.eclipse.xtend2.lib.StringConcatenation;
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
  
  public static String MODEL_APPLICATION = "Application";
  
  public static String MODEL_APPLICATION_ROOTPACKAGE = "rootPackage";
  
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
  
  public static String lowerCase(final String str) {
    return str.toLowerCase();
  }
  
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
  
  public static String getFirstToLowerCase(final String str) {
    return StringExtensions.toFirstLower(str);
  }
  
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
    final Model model = elem.getModel();
    return Utils.getStereotypePropertyValue(model, Utils.MODEL_APPLICATION, Utils.MODEL_APPLICATION_ROOTPACKAGE);
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
}
