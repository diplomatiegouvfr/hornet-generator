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
import com.google.common.collect.Iterables;
import fr.gouv.diplomatie.papyrus.codegen.core.generators.GeneratorUtils;
import fr.gouv.diplomatie.papyrus.codegen.core.utils.ModelUtils;
import fr.gouv.diplomatie.papyrus.codegen.core.utils.PropertyUtils;
import fr.gouv.diplomatie.papyrus.codegen.core.utils.Utils;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Consumer;
import org.eclipse.emf.common.util.EList;
import org.eclipse.uml2.uml.Association;
import org.eclipse.uml2.uml.AssociationClass;
import org.eclipse.uml2.uml.AttributeOwner;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Interface;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Stereotype;
import org.eclipse.uml2.uml.Type;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;
import org.eclipse.xtext.xbase.lib.Conversions;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;

/**
 * classe utilitaire pour les Classifier
 */
@SuppressWarnings("all")
public class ClassifierUtils {
  /**
   * options de la méthodes d'import
   */
  public static class ImportOptions {
    public Boolean importInterface;
    
    public Boolean importInterfaceAttributes;
    
    public Boolean importValueObject;
    
    public Boolean importValueObjectAttributes;
  }
  
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
      return Boolean.valueOf(((Objects.equal(attribut.getType(), ofClass) && attribut.isMultivalued()) && (attribut.getAssociation() == null)));
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
   * static def getOneToManyAttributes(Classifier ofType){
   * val pakkage = ofType.package
   * val classes = pakkage.getOwnedTypes().filter[type|
   * Utils.isEntity(type)
   * ]
   * 
   * var attributesRef = new ArrayList
   * 
   * for(classe : classes){
   * val attributes = ClassifierUtils.getMultivaluedOwnedAttributes(classe as Classifier).filter[attr|
   * if(attr.association !== null){
   * val member = attr.association.memberEnds.filter[mem |mem.type == ofType]
   * val memberEnd = attr.association.memberEnds.filter[mem |mem.type == classe]
   * if(member.length > 0 && memberEnd.length > 0){
   * val end = member.get(0);
   * val otherEnd = memberEnd.get(0)
   * return end.isMultivalued && !otherEnd.isMultivalued
   * }
   * return false
   * }else if(attr.type == ofType && !attr.multivalued){
   * return true
   * }
   * return false
   * ]
   * for(attribut : attributes){
   * attributesRef.add(attribut)
   * }
   * }
   * return attributesRef
   * }
   */
  public static ArrayList<Property> getOneToManyAttributes(final Classifier ofType) {
    final org.eclipse.uml2.uml.Package pakkage = ofType.getPackage();
    final Function1<Type, Boolean> _function = (Type type) -> {
      return Boolean.valueOf(Utils.isEntity(type));
    };
    final Iterable<Type> classes = IterableExtensions.<Type>filter(pakkage.getOwnedTypes(), _function);
    final ArrayList<Property> test = new ArrayList<Property>();
    ArrayList<Property> attributesRef = new ArrayList<Property>();
    for (final Type classe : classes) {
      {
        final Function1<Property, Boolean> _function_1 = (Property attr) -> {
          Association _association = attr.getAssociation();
          boolean _tripleNotEquals = (_association != null);
          if (_tripleNotEquals) {
            final Function1<Property, Boolean> _function_2 = (Property mem) -> {
              Type _type = mem.getType();
              return Boolean.valueOf(Objects.equal(_type, ofType));
            };
            final Iterable<Property> member = IterableExtensions.<Property>filter(attr.getAssociation().getMemberEnds(), _function_2);
            final Function1<Property, Boolean> _function_3 = (Property mem) -> {
              Type _type = mem.getType();
              return Boolean.valueOf(Objects.equal(_type, classe));
            };
            final Iterable<Property> memberEnd = IterableExtensions.<Property>filter(attr.getAssociation().getMemberEnds(), _function_3);
            if (((((Object[])Conversions.unwrapArray(member, Object.class)).length > 0) && (((Object[])Conversions.unwrapArray(memberEnd, Object.class)).length > 0))) {
              final Property end = ((Property[])Conversions.unwrapArray(member, Property.class))[0];
              final Property otherEnd = ((Property[])Conversions.unwrapArray(memberEnd, Property.class))[0];
              if (((end.isMultivalued() && (!otherEnd.isMultivalued())) && (otherEnd.getOwner() != ofType))) {
                test.add(otherEnd);
              }
              return Boolean.valueOf(false);
            }
            return Boolean.valueOf(false);
          } else {
            if ((Objects.equal(attr.getType(), ofType) && (!attr.isMultivalued()))) {
              return Boolean.valueOf(true);
            }
          }
          return Boolean.valueOf(false);
        };
        final Iterable<Property> attributes = IterableExtensions.<Property>filter(ClassifierUtils.getMultivaluedOwnedAttributes(((Classifier) classe)), _function_1);
        for (final Property attribut : attributes) {
          attributesRef.add(attribut);
        }
        boolean _isEmpty = test.isEmpty();
        boolean _not = (!_isEmpty);
        if (_not) {
          for (final Property attribut_1 : test) {
            boolean _contains = attributesRef.contains(attribut_1);
            boolean _not_1 = (!_contains);
            if (_not_1) {
              attributesRef.add(attribut_1);
            }
          }
        }
      }
    }
    return attributesRef;
  }
  
  /**
   * retourne les attributs de type many to many de type ofType présents dans le package
   */
  public static ArrayList<Property> getManyToManyAttributes(final Classifier ofType) {
    final org.eclipse.uml2.uml.Package pakkage = ofType.getPackage();
    final Function1<Type, Boolean> _function = (Type type) -> {
      return Boolean.valueOf(Utils.isEntity(type));
    };
    final Iterable<Type> classes = IterableExtensions.<Type>filter(pakkage.getOwnedTypes(), _function);
    ArrayList<Property> attributesRef = new ArrayList<Property>();
    for (final Type classe : classes) {
      {
        final Function1<Property, Boolean> _function_1 = (Property attr) -> {
          Association _association = attr.getAssociation();
          boolean _tripleNotEquals = (_association != null);
          if (_tripleNotEquals) {
            final EList<Property> ends = attr.getAssociation().getOwnedEnds();
            if (((ends != null) && (!ends.isEmpty()))) {
              final Type owner = ends.get(0).getType();
              if ((owner != ofType)) {
                final Function1<Property, Boolean> _function_2 = (Property mem) -> {
                  Type _type = mem.getType();
                  return Boolean.valueOf(Objects.equal(_type, ofType));
                };
                final Iterable<Property> member = IterableExtensions.<Property>filter(attr.getAssociation().getMemberEnds(), _function_2);
                final Function1<Property, Boolean> _function_3 = (Property mem) -> {
                  Type _type = mem.getType();
                  return Boolean.valueOf(Objects.equal(_type, classe));
                };
                final Iterable<Property> memberEnd = IterableExtensions.<Property>filter(attr.getAssociation().getMemberEnds(), _function_3);
                if (((((Object[])Conversions.unwrapArray(member, Object.class)).length > 0) && (((Object[])Conversions.unwrapArray(memberEnd, Object.class)).length > 0))) {
                  final Property end = ((Property[])Conversions.unwrapArray(member, Property.class))[0];
                  final Property otherEnd = ((Property[])Conversions.unwrapArray(memberEnd, Property.class))[0];
                  return Boolean.valueOf((end.isMultivalued() && otherEnd.isMultivalued()));
                }
              }
            }
            return Boolean.valueOf(false);
          }
          return Boolean.valueOf(false);
        };
        final Iterable<Property> attributes = IterableExtensions.<Property>filter(ClassifierUtils.getMultivaluedOwnedAttributes(((Classifier) classe)), _function_1);
        for (final Property attribut : attributes) {
          attributesRef.add(attribut);
        }
      }
    }
    return attributesRef;
  }
  
  /**
   * cherche les classe d'association liée a la classe
   */
  public static ArrayList<Type> getLinkedAssociationClass(final Classifier clazz) {
    final Model model = clazz.getModel();
    final org.eclipse.uml2.uml.Package pakkage = clazz.getPackage();
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
    ArrayList<Type> assoClassesInPakkage = CollectionLiterals.<Type>newArrayList();
    if ((pakkage != null)) {
      final Function1<Type, Boolean> _function_1 = (Type type) -> {
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
      final Iterable<Type> associationsClassesInPakkage = IterableExtensions.<Type>filter(pakkage.getOwnedTypes(), _function_1);
      for (final Type asso : associationsClassesInPakkage) {
        assoClassesInPakkage.add(asso);
      }
    }
    ArrayList<Type> classes = CollectionLiterals.<Type>newArrayList();
    for (final Type asso_1 : associationsClasses) {
      boolean _contains = classes.contains(asso_1);
      boolean _not = (!_contains);
      if (_not) {
        classes.add(asso_1);
      }
    }
    boolean _notEquals = (!Objects.equal(pakkage, model));
    if (_notEquals) {
      for (final Type asso_2 : assoClassesInPakkage) {
        boolean _contains_1 = classes.contains(asso_2);
        boolean _not_1 = (!_contains_1);
        if (_not_1) {
          classes.add(asso_2);
        }
      }
    }
    return classes;
  }
  
  /**
   * test si l'enum a des code ou non
   */
  public static boolean isEnumWithCode(final Classifier clazz) {
    final Collection<Property> valeurs = ClassifierUtils.getOwnedAttributes(clazz);
    final Property value = ((Property[])Conversions.unwrapArray(valeurs, Property.class))[0];
    final Object code = Utils.getNomenclatureCode(value);
    return ((code != null) && (!Objects.equal(code, "")));
  }
  
  /**
   * teste si une classe doit etre générée ou non
   */
  public static boolean canBeGenerated(final Classifier clazz) {
    final Object generated = Utils.getGenerated(clazz);
    if ((Objects.equal(generated, Boolean.valueOf(false)) || (generated == null))) {
      return false;
    }
    return true;
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
   * retourne le nom de la table liée a la classe
   */
  public static String getDBTableName(final Classifier clazz) {
    final Object name = ClassifierUtils.getTableNameValue(clazz);
    if (((name == null) || (name == ""))) {
      return Utils.toDbName(clazz.getName());
    }
    return Utils.toDbName(name.toString());
  }
  
  /**
   * retourne le chemin vers la classe
   */
  public static String getClassPath(final Classifier clazz) {
    if ((clazz != null)) {
      final String path = Utils.getPackagePath(clazz);
      final String appName = clazz.getModel().getName();
      String _name = clazz.getPackage().getName();
      final String fileName = (_name + 
        File.separator);
      final String classPath = ((((path + File.separator) + appName) + File.separator) + fileName);
      return classPath;
    }
    return null;
  }
  
  /**
   * classes nécessaires a importer liées aux attributs dans les classes
   */
  public static ArrayList<Type> getAttributesImport(final Classifier clazz, final Classifier fromClass, final ArrayList<Type> types, final ClassifierUtils.ImportOptions options) {
    final Function1<Property, Boolean> _function = (Property attribut) -> {
      return Boolean.valueOf((Utils.isEntity(attribut.getType()) && (attribut.getType() != clazz)));
    };
    final Iterable<Property> attributes = IterableExtensions.<Property>filter(ClassifierUtils.getOwnedAttributes(clazz), _function);
    final Function1<Property, Boolean> _function_1 = (Property attribut) -> {
      return Boolean.valueOf(Utils.isNomenclature(attribut.getType()));
    };
    final Iterable<Property> attributesEnums = IterableExtensions.<Property>filter(ClassifierUtils.getOwnedAttributes(clazz), _function_1);
    final Function1<Property, Boolean> _function_2 = (Property attribut) -> {
      return Boolean.valueOf(Utils.isValueObject(attribut.getType()));
    };
    final Iterable<Property> attributesValueObject = IterableExtensions.<Property>filter(ClassifierUtils.getOwnedAttributes(clazz), _function_2);
    boolean _isEntity = Utils.isEntity(clazz);
    if (_isEntity) {
      final ArrayList<Property> oneToManyAttributes = ClassifierUtils.getOneToManyAttributes(clazz);
      for (final Property attribut : oneToManyAttributes) {
        if (((!types.contains(attribut.getType())) && (!Objects.equal(attribut.getType(), clazz)))) {
          Type _type = attribut.getType();
          types.add(((Classifier) _type));
        }
      }
      final ArrayList<Property> manyToManyAttributes = ClassifierUtils.getManyToManyAttributes(clazz);
      for (final Property attribut_1 : manyToManyAttributes) {
        if (((!types.contains(attribut_1.getOwner())) && (!Objects.equal(attribut_1.getOwner(), clazz)))) {
          Element _owner = attribut_1.getOwner();
          types.add(((Classifier) _owner));
        }
      }
    }
    final EList<Interface> interfaces = clazz.directlyRealizedInterfaces();
    for (final Property attribut_2 : attributes) {
      boolean _contains = types.contains(attribut_2.getType());
      boolean _not = (!_contains);
      if (_not) {
        types.add(attribut_2.getType());
      }
    }
    for (final Property attribut_3 : attributesEnums) {
      boolean _contains_1 = types.contains(attribut_3.getType());
      boolean _not_1 = (!_contains_1);
      if (_not_1) {
        types.add(attribut_3.getType());
      }
    }
    for (final Interface interface_ : interfaces) {
      {
        if ((options.importInterface).booleanValue()) {
          boolean _contains_2 = types.contains(interface_);
          boolean _not_2 = (!_contains_2);
          if (_not_2) {
            types.add(interface_);
          }
        }
        if ((options.importInterfaceAttributes).booleanValue()) {
          ClassifierUtils.getAttributesImport(interface_, fromClass, types, options);
        }
      }
    }
    boolean _isEntity_1 = Utils.isEntity(fromClass);
    if (_isEntity_1) {
      final ArrayList<Type> assosiationClasses = ClassifierUtils.getLinkedAssociationClass(clazz);
      final Consumer<Type> _function_3 = (Type asso) -> {
        boolean _contains_2 = types.contains(asso);
        boolean _not_2 = (!_contains_2);
        if (_not_2) {
          types.add(asso);
        }
      };
      assosiationClasses.forEach(_function_3);
    }
    final Consumer<Property> _function_4 = (Property attribut_4) -> {
      Type type = attribut_4.getType();
      if ((options.importValueObject).booleanValue()) {
        boolean _contains_2 = types.contains(type);
        boolean _not_2 = (!_contains_2);
        if (_not_2) {
          types.add(type);
        }
      }
      if ((options.importValueObjectAttributes).booleanValue()) {
        if ((type instanceof Classifier)) {
          ClassifierUtils.getAttributesImport(((Classifier)type), fromClass, types, options);
        }
      }
    };
    attributesValueObject.forEach(_function_4);
    return types;
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
   * retourne le schema dans lequel se trouve la classe
   * retourne null si il n'y a pas de schema
   */
  public static Object getSchema(final Classifier clazz) {
    final org.eclipse.uml2.uml.Package pkg = clazz.getPackage();
    return Utils.getSchemaName(pkg);
  }
}
