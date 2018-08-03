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
package fr.gouv.diplomatie.papyrus.codegen.ui.validators;

import com.google.common.base.Objects;
import fr.gouv.diplomatie.papyrus.codegen.core.console.ConsoleUtils;
import fr.gouv.diplomatie.papyrus.codegen.core.utils.Utils;
import fr.gouv.diplomatie.papyrus.codegen.java.utils.JavaPluginUtils;
import fr.gouv.diplomatie.papyrus.codegen.ui.core.validator.HornetModelValidator;
import java.util.ArrayList;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.Property;
import org.eclipse.xtext.xbase.lib.CollectionExtensions;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;
import org.eclipse.xtext.xbase.lib.Conversions;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;

/**
 * validation du modèle pour les stéréotypes liés à la génération java
 */
@SuppressWarnings("all")
public class JavaPluginModelValidator extends HornetModelValidator {
  @Override
  public void validateProperty(final Property packageableElement, final ConsoleUtils console) {
    super.validateProperty(packageableElement, console);
    Element _owner = packageableElement.getOwner();
    final NamedElement owner = ((NamedElement) _owner);
    final ArrayList<String> types = CollectionLiterals.<String>newArrayList("Boolean", "Date", "Real", "BigInteger", "Double", "Integer", "Float", "String");
    final boolean isBooleanTyped = Utils.hasStereotype(packageableElement, JavaPluginUtils.MODEL_BOOLEANTYPED);
    final boolean isDateTyped = Utils.hasStereotype(packageableElement, JavaPluginUtils.MODEL_DATETYPED);
    final boolean isStringTyped = Utils.hasStereotype(packageableElement, JavaPluginUtils.MODEL_STRINGTYPED);
    final boolean isNumericTyped = Utils.hasStereotype(packageableElement, JavaPluginUtils.MODEL_NUMERICTYPED);
    final boolean isCollection = Utils.hasStereotype(packageableElement, JavaPluginUtils.MODEL_COLLECTION);
    if (isCollection) {
      boolean _isMultivalued = packageableElement.isMultivalued();
      boolean _not = (!_isMultivalued);
      if (_not) {
        String _name = packageableElement.getName();
        String _plus = ("L\'attibut " + _name);
        String _plus_1 = (_plus + " de la classe ");
        String _name_1 = owner.getName();
        String _plus_2 = (_plus_1 + _name_1);
        String _plus_3 = (_plus_2 + " possède le stéréotype collection mais n\'est pas multivalué.");
        this.errors.add(_plus_3);
      }
      final Object sizeMin = Utils.getStereotypePropertyValue(packageableElement, JavaPluginUtils.MODEL_COLLECTION, JavaPluginUtils.MODEL_COLLECTION_SIZEMIN);
      final Object hasSizeMin = Utils.getStereotypePropertyValue(packageableElement, JavaPluginUtils.MODEL_COLLECTION, JavaPluginUtils.MODEL_COLLECTION_HASSIZEMIN);
      if (((!Objects.equal(sizeMin, Integer.valueOf(0))) && Objects.equal(hasSizeMin, Boolean.valueOf(false)))) {
        String _name_2 = packageableElement.getName();
        String _plus_4 = ("L\'attibut " + _name_2);
        String _plus_5 = (_plus_4 + " de la classe ");
        String _name_3 = owner.getName();
        String _plus_6 = (_plus_5 + _name_3);
        String _plus_7 = (_plus_6 + " possède, dans le stéréotype collection, un attribut sizeMin valué mais l\'attribut hasSizeMin n\'est pas à true. SizeMin ne sera pas pris en compte. ");
        this.warnings.add(_plus_7);
      }
      final Object sizeMax = Utils.getStereotypePropertyValue(packageableElement, JavaPluginUtils.MODEL_COLLECTION, JavaPluginUtils.MODEL_COLLECTION_SIZEMAX);
      final Object hasSizeMax = Utils.getStereotypePropertyValue(packageableElement, JavaPluginUtils.MODEL_COLLECTION, JavaPluginUtils.MODEL_COLLECTION_HASSIZEMAX);
      if (((!Objects.equal(sizeMax, Integer.valueOf(0))) && Objects.equal(hasSizeMax, Boolean.valueOf(false)))) {
        String _name_4 = packageableElement.getName();
        String _plus_8 = ("L\'attibut " + _name_4);
        String _plus_9 = (_plus_8 + " de la classe ");
        String _name_5 = owner.getName();
        String _plus_10 = (_plus_9 + _name_5);
        String _plus_11 = (_plus_10 + " possède, dans le stéréotype collection, un attribut sizeMax valué mais l\'attribut hasSizeMax n\'est pas à true. SizeMax ne sera pas pris en compte. ");
        this.warnings.add(_plus_11);
      }
    }
    if (isBooleanTyped) {
      final String type = packageableElement.getType().getName();
      types.remove("Boolean");
      boolean _contains = types.contains(type);
      if (_contains) {
        String _name_6 = packageableElement.getName();
        String _plus_12 = ("L\'attibut " + _name_6);
        String _plus_13 = (_plus_12 + " de la classe ");
        String _name_7 = owner.getName();
        String _plus_14 = (_plus_13 + _name_7);
        String _plus_15 = (_plus_14 + " est de type ");
        String _plus_16 = (_plus_15 + type);
        String _plus_17 = (_plus_16 + " mais possède le stéréotype booleanTyped.");
        this.errors.add(_plus_17);
      }
      types.add("Boolean");
      final Object alwaysTrue = Utils.getStereotypePropertyValue(packageableElement, JavaPluginUtils.MODEL_BOOLEANTYPED, JavaPluginUtils.MODEL_BOOLEANTYPED_ALWAYSTRUE);
      final Object alwaysFalse = Utils.getStereotypePropertyValue(packageableElement, JavaPluginUtils.MODEL_BOOLEANTYPED, JavaPluginUtils.MODEL_BOOLEANTYPED_ALWAYSFALSE);
      if ((Objects.equal(alwaysTrue, Boolean.valueOf(true)) && Objects.equal(alwaysFalse, Boolean.valueOf(true)))) {
        String _name_8 = packageableElement.getName();
        String _plus_18 = ("L\'attibut " + _name_8);
        String _plus_19 = (_plus_18 + " de la classe ");
        String _name_9 = owner.getName();
        String _plus_20 = (_plus_19 + _name_9);
        String _plus_21 = (_plus_20 + " possède les deux attributs alwaysTrue et alwaysFalse du stéréotype booleanTyped à true. Ces deux attributs sont incompatibles");
        this.errors.add(_plus_21);
      }
    }
    if (isDateTyped) {
      final String type_1 = packageableElement.getType().getName();
      types.remove("Date");
      boolean _contains_1 = types.contains(type_1);
      if (_contains_1) {
        String _name_10 = packageableElement.getName();
        String _plus_22 = ("L\'attibut " + _name_10);
        String _plus_23 = (_plus_22 + " de la classe ");
        String _name_11 = owner.getName();
        String _plus_24 = (_plus_23 + _name_11);
        String _plus_25 = (_plus_24 + " est de type ");
        String _plus_26 = (_plus_25 + type_1);
        String _plus_27 = (_plus_26 + " mais possède le stéréotype dateTyped.");
        this.errors.add(_plus_27);
      }
      types.add("Date");
      final Object future = Utils.getStereotypePropertyValue(packageableElement, JavaPluginUtils.MODEL_DATETYPED, JavaPluginUtils.MODEL_DATETYPED_FUTURE);
      final Object futureOrPresent = Utils.getStereotypePropertyValue(packageableElement, JavaPluginUtils.MODEL_DATETYPED, JavaPluginUtils.MODEL_DATETYPED_FUTUREORPRESENT);
      final Object Past = Utils.getStereotypePropertyValue(packageableElement, JavaPluginUtils.MODEL_DATETYPED, JavaPluginUtils.MODEL_DATETYPED_PAST);
      final Object PastOrPresent = Utils.getStereotypePropertyValue(packageableElement, JavaPluginUtils.MODEL_DATETYPED, JavaPluginUtils.MODEL_DATETYPED_PASTORPRESENT);
      final ArrayList<Object> list = CollectionLiterals.<Object>newArrayList();
      list.add(future);
      list.add(futureOrPresent);
      list.add(Past);
      list.add(PastOrPresent);
      final Function1<Object, Boolean> _function = (Object att) -> {
        return Boolean.valueOf(Objects.equal(att, Boolean.valueOf(true)));
      };
      final Iterable<Object> test = IterableExtensions.<Object>filter(list, _function);
      int _length = ((Object[])Conversions.unwrapArray(test, Object.class)).length;
      boolean _greaterThan = (_length > 1);
      if (_greaterThan) {
        String _name_12 = packageableElement.getName();
        String _plus_28 = ("L\'attibut " + _name_12);
        String _plus_29 = (_plus_28 + " de la classe ");
        String _name_13 = owner.getName();
        String _plus_30 = (_plus_29 + _name_13);
        String _plus_31 = (_plus_30 + " possède des attributs incompatibles (parmis furture, futureOrPresent, Past et PastOrPresent) dans le stéréotype dateTyped");
        this.errors.add(_plus_31);
      }
    }
    if (isStringTyped) {
      final String type_2 = packageableElement.getType().getName();
      types.remove("String");
      boolean _contains_2 = types.contains(type_2);
      if (_contains_2) {
        String _name_14 = packageableElement.getName();
        String _plus_32 = ("L\'attibut " + _name_14);
        String _plus_33 = (_plus_32 + " de la classe ");
        String _name_15 = owner.getName();
        String _plus_34 = (_plus_33 + _name_15);
        String _plus_35 = (_plus_34 + " est de type ");
        String _plus_36 = (_plus_35 + type_2);
        String _plus_37 = (_plus_36 + " mais possède le stéréotype stringTyped.");
        this.errors.add(_plus_37);
      }
      types.add("String");
      final Object sizeMin_1 = Utils.getStereotypePropertyValue(packageableElement, JavaPluginUtils.MODEL_STRINGTYPED, JavaPluginUtils.MODEL_STRINGTYPED_SIZEMIN);
      final Object hasSizeMin_1 = Utils.getStereotypePropertyValue(packageableElement, JavaPluginUtils.MODEL_STRINGTYPED, JavaPluginUtils.MODEL_STRINGTYPED_HASSIZEMIN);
      if (((!Objects.equal(sizeMin_1, Integer.valueOf(0))) && Objects.equal(hasSizeMin_1, Boolean.valueOf(false)))) {
        String _name_16 = packageableElement.getName();
        String _plus_38 = ("L\'attibut " + _name_16);
        String _plus_39 = (_plus_38 + " de la classe ");
        String _name_17 = owner.getName();
        String _plus_40 = (_plus_39 + _name_17);
        String _plus_41 = (_plus_40 + " possède, dans le stéréotype stringTyped, un attribut sizeMin valué mais l\'attribut hasSizeMin n\'est pas à true. SizeMin ne sera pas pris en compte. ");
        this.warnings.add(_plus_41);
      }
    }
    if (isNumericTyped) {
      final String type_3 = packageableElement.getType().getName();
      CollectionExtensions.<String>removeAll(types, "Real", "BigInteger", "Double", "Integer", "Float");
      boolean _contains_3 = types.contains(type_3);
      if (_contains_3) {
        String _name_18 = packageableElement.getName();
        String _plus_42 = ("L\'attibut " + _name_18);
        String _plus_43 = (_plus_42 + " de la classe ");
        String _name_19 = owner.getName();
        String _plus_44 = (_plus_43 + _name_19);
        String _plus_45 = (_plus_44 + " est de type ");
        String _plus_46 = (_plus_45 + type_3);
        String _plus_47 = (_plus_46 + " mais possède le stéréotype numericTyped.");
        this.errors.add(_plus_47);
      }
      CollectionExtensions.<String>addAll(types, "Real", "BigInteger", "Double", "Integer", "Float");
      final Object negative = Utils.getStereotypePropertyValue(packageableElement, JavaPluginUtils.MODEL_NUMERICTYPED, JavaPluginUtils.MODEL_NUMERICTYPED_NEGATIVE);
      final Object negativeOrZero = Utils.getStereotypePropertyValue(packageableElement, JavaPluginUtils.MODEL_NUMERICTYPED, JavaPluginUtils.MODEL_NUMERICTYPED_NEGATIVEORZERO);
      final Object positive = Utils.getStereotypePropertyValue(packageableElement, JavaPluginUtils.MODEL_NUMERICTYPED, JavaPluginUtils.MODEL_NUMERICTYPED_POSITIVE);
      final Object positiveOrZero = Utils.getStereotypePropertyValue(packageableElement, JavaPluginUtils.MODEL_NUMERICTYPED, JavaPluginUtils.MODEL_NUMERICTYPED_POSITIVEORZERO);
      final ArrayList<Object> list_1 = CollectionLiterals.<Object>newArrayList();
      list_1.add(negative);
      list_1.add(negativeOrZero);
      list_1.add(positive);
      list_1.add(positiveOrZero);
      final Function1<Object, Boolean> _function_1 = (Object att) -> {
        return Boolean.valueOf(Objects.equal(att, Boolean.valueOf(true)));
      };
      final Iterable<Object> test_1 = IterableExtensions.<Object>filter(list_1, _function_1);
      int _length_1 = ((Object[])Conversions.unwrapArray(test_1, Object.class)).length;
      boolean _greaterThan_1 = (_length_1 > 1);
      if (_greaterThan_1) {
        String _name_20 = packageableElement.getName();
        String _plus_48 = ("L\'attibut " + _name_20);
        String _plus_49 = (_plus_48 + " de la classe");
        String _name_21 = owner.getName();
        String _plus_50 = (_plus_49 + _name_21);
        String _plus_51 = (_plus_50 + " possède des attributs incompatibles (parmis negative, negativeOrZero, positive et positiveOrZero) dans le stéréotype numericTyped");
        this.errors.add(_plus_51);
      }
      final Object min = Utils.getStereotypePropertyValue(packageableElement, JavaPluginUtils.MODEL_NUMERICTYPED, JavaPluginUtils.MODEL_NUMERICTYPED_MIN);
      final Object hasMin = Utils.getStereotypePropertyValue(packageableElement, JavaPluginUtils.MODEL_NUMERICTYPED, JavaPluginUtils.MODEL_NUMERICTYPED_HASMIN);
      if (((!Objects.equal(min, Integer.valueOf(0))) && Objects.equal(hasMin, Boolean.valueOf(false)))) {
        String _name_22 = packageableElement.getName();
        String _plus_52 = ("L\'attibut " + _name_22);
        String _plus_53 = (_plus_52 + " de la classe ");
        String _name_23 = owner.getName();
        String _plus_54 = (_plus_53 + _name_23);
        String _plus_55 = (_plus_54 + " possède, dans le stéréotype numericTyped, un attribut min valué mais l\'attribut hasMin n\'est pas à true. Min ne sera pas pris en compte. ");
        this.warnings.add(_plus_55);
      }
      final Object max = Utils.getStereotypePropertyValue(packageableElement, JavaPluginUtils.MODEL_NUMERICTYPED, JavaPluginUtils.MODEL_NUMERICTYPED_MAX);
      final Object hasMax = Utils.getStereotypePropertyValue(packageableElement, JavaPluginUtils.MODEL_NUMERICTYPED, JavaPluginUtils.MODEL_NUMERICTYPED_HASMAX);
      if (((!Objects.equal(max, Integer.valueOf(0))) && Objects.equal(hasMax, Boolean.valueOf(false)))) {
        String _name_24 = packageableElement.getName();
        String _plus_56 = ("L\'attibut " + _name_24);
        String _plus_57 = (_plus_56 + " de la classe ");
        String _name_25 = owner.getName();
        String _plus_58 = (_plus_57 + _name_25);
        String _plus_59 = (_plus_58 + " possède, dans le stéréotype numericTyped, un attribut max valué mais l\'attribut hasMax n\'est pas à true. Max ne sera pas pris en compte. ");
        this.warnings.add(_plus_59);
      }
      final Object decimalMin = Utils.getStereotypePropertyValue(packageableElement, JavaPluginUtils.MODEL_NUMERICTYPED, JavaPluginUtils.MODEL_NUMERICTYPED_DECIMALMIN);
      final Object hasDecimalMin = Utils.getStereotypePropertyValue(packageableElement, JavaPluginUtils.MODEL_NUMERICTYPED, JavaPluginUtils.MODEL_NUMERICTYPED_HASDECIMALMIN);
      if (((!Objects.equal(decimalMin, Double.valueOf(0.0))) && Objects.equal(hasDecimalMin, Boolean.valueOf(false)))) {
        String _name_26 = packageableElement.getName();
        String _plus_60 = ("L\'attibut " + _name_26);
        String _plus_61 = (_plus_60 + " de la classe ");
        String _name_27 = owner.getName();
        String _plus_62 = (_plus_61 + _name_27);
        String _plus_63 = (_plus_62 + " possède, dans le stéréotype numericTyped, un attribut decimalMin valué mais l\'attribut hasDecimalMin n\'est pas à true. decimalMin ne sera pas pris en compte. ");
        this.warnings.add(_plus_63);
      }
      final Object decimalMax = Utils.getStereotypePropertyValue(packageableElement, JavaPluginUtils.MODEL_NUMERICTYPED, JavaPluginUtils.MODEL_NUMERICTYPED_DECIMALMAX);
      final Object hasDecimalMax = Utils.getStereotypePropertyValue(packageableElement, JavaPluginUtils.MODEL_NUMERICTYPED, JavaPluginUtils.MODEL_NUMERICTYPED_HASDECIMALMAX);
      if (((!Objects.equal(decimalMax, Double.valueOf(0.0))) && Objects.equal(hasDecimalMax, Boolean.valueOf(false)))) {
        String _name_28 = packageableElement.getName();
        String _plus_64 = ("L\'attibut " + _name_28);
        String _plus_65 = (_plus_64 + " de la classe ");
        String _name_29 = owner.getName();
        String _plus_66 = (_plus_65 + _name_29);
        String _plus_67 = (_plus_66 + " possède, dans le stéréotype numericTyped, un attribut decimalMax valué mais l\'attribut hasDecimalMax n\'est pas à true. decimalMax ne sera pas pris en compte. ");
        this.warnings.add(_plus_67);
      }
    }
  }
}
