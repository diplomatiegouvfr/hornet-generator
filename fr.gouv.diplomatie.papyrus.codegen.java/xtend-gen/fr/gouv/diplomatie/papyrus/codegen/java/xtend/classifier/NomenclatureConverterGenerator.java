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

import fr.gouv.diplomatie.papyrus.codegen.core.utils.ClassifierUtils;
import fr.gouv.diplomatie.papyrus.codegen.core.utils.Utils;
import fr.gouv.diplomatie.papyrus.codegen.java.generators.GeneratorUtils;
import java.util.Collection;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.Property;
import org.eclipse.xtend2.lib.StringConcatenation;

@SuppressWarnings("all")
public class NomenclatureConverterGenerator {
  public static CharSequence generateCode(final Classifier clazz) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("package ");
    String _import = Utils.toImport(GeneratorUtils.getEntityPackage(clazz));
    _builder.append(_import);
    _builder.append(";");
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    _builder.append("import javax.persistence.AttributeConverter;");
    _builder.newLine();
    _builder.append("import javax.persistence.Converter;");
    _builder.newLine();
    _builder.append(" ");
    _builder.newLine();
    _builder.append("@Converter");
    _builder.newLine();
    _builder.append("public class ");
    String _name = clazz.getName();
    _builder.append(_name);
    _builder.append("Converter implements AttributeConverter<");
    String _name_1 = clazz.getName();
    _builder.append(_name_1);
    _builder.append(", String> {");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.newLine();
    _builder.append("    ");
    CharSequence _generateConvertToDatabaseColumnMethod = NomenclatureConverterGenerator.generateConvertToDatabaseColumnMethod(clazz);
    _builder.append(_generateConvertToDatabaseColumnMethod, "    ");
    _builder.newLineIfNotEmpty();
    _builder.append("    ");
    _builder.newLine();
    _builder.append("    ");
    CharSequence _generateConvertToEntityAttributeMethod = NomenclatureConverterGenerator.generateConvertToEntityAttributeMethod(clazz);
    _builder.append(_generateConvertToEntityAttributeMethod, "    ");
    _builder.newLineIfNotEmpty();
    _builder.append("    ");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    return _builder;
  }
  
  public static CharSequence generateConvertToDatabaseColumnMethod(final Classifier clazz) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("@Override");
    _builder.newLine();
    _builder.append("public Integer convertToDatabaseColumn(");
    String _name = clazz.getName();
    _builder.append(_name);
    _builder.append(" attribute) {");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    CharSequence _generateCTDCSwitchCase = NomenclatureConverterGenerator.generateCTDCSwitchCase(clazz);
    _builder.append(_generateCTDCSwitchCase, "\t");
    _builder.newLineIfNotEmpty();
    _builder.append("}");
    _builder.newLine();
    return _builder;
  }
  
  public static CharSequence generateCTDCSwitchCase(final Classifier clazz) {
    CharSequence _xblockexpression = null;
    {
      final Collection<Property> attributes = ClassifierUtils.getOwnedAttributes(clazz);
      int index = 0;
      String attributesCase = "";
      for (final Property attribut : attributes) {
        {
          String _attributesCase = attributesCase;
          StringConcatenation _builder = new StringConcatenation();
          CharSequence _generateCTDCCase = NomenclatureConverterGenerator.generateCTDCCase(attribut, index);
          _builder.append(_generateCTDCCase);
          attributesCase = (_attributesCase + _builder);
          index = (index + 1);
        }
      }
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("switch (attribute) {");
      _builder.newLine();
      _builder.append("\t");
      _builder.append(attributesCase, "\t");
      _builder.newLineIfNotEmpty();
      _builder.append("\t");
      _builder.append("default:");
      _builder.newLine();
      _builder.append("\t\t");
      _builder.append("throw new IllegalArgumentException(\"Unknown\" + attribute);");
      _builder.newLine();
      _builder.append("}");
      _builder.newLine();
      _xblockexpression = _builder;
    }
    return _xblockexpression;
  }
  
  public static CharSequence generateCTDCCase(final Property property, final int defaultCode) {
    CharSequence _xblockexpression = null;
    {
      Object code = Utils.getNomenclatureCode(property);
      Object libelle = Utils.getNomenclatureLibelle(property);
      if ((libelle == null)) {
        libelle = property.getName();
      }
      if ((code == null)) {
        code = Integer.valueOf(defaultCode);
      }
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("case \"");
      _builder.append(libelle);
      _builder.append("\":");
      _builder.newLineIfNotEmpty();
      _builder.append("\t");
      _builder.append("return ");
      _builder.append(code, "\t");
      _builder.append(";");
      _builder.newLineIfNotEmpty();
      _xblockexpression = _builder;
    }
    return _xblockexpression;
  }
  
  public static CharSequence generateConvertToEntityAttributeMethod(final Classifier clazz) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("@Override");
    _builder.newLine();
    _builder.append("public ");
    String _name = clazz.getName();
    _builder.append(_name);
    _builder.append(" convertToEntityAttribute(Integer dbData) {");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    CharSequence _generateCTEASwitchCase = NomenclatureConverterGenerator.generateCTEASwitchCase(clazz);
    _builder.append(_generateCTEASwitchCase, "\t");
    _builder.newLineIfNotEmpty();
    _builder.append("}");
    _builder.newLine();
    return _builder;
  }
  
  public static CharSequence generateCTEASwitchCase(final Classifier clazz) {
    CharSequence _xblockexpression = null;
    {
      final Collection<Property> attributes = ClassifierUtils.getOwnedAttributes(clazz);
      int index = 0;
      String attributesCase = "";
      for (final Property attribut : attributes) {
        {
          String _attributesCase = attributesCase;
          StringConcatenation _builder = new StringConcatenation();
          CharSequence _generateCTEACase = NomenclatureConverterGenerator.generateCTEACase(attribut, index);
          _builder.append(_generateCTEACase);
          attributesCase = (_attributesCase + _builder);
          index = (index + 1);
        }
      }
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("switch (dbData) {");
      _builder.newLine();
      _builder.append("\t");
      _builder.append(attributesCase, "\t");
      _builder.newLineIfNotEmpty();
      _builder.append("\t");
      _builder.append("default:");
      _builder.newLine();
      _builder.append("\t\t");
      _builder.append("throw new IllegalArgumentException(\"Unknown\" + dbData);");
      _builder.newLine();
      _builder.append("}");
      _builder.newLine();
      _xblockexpression = _builder;
    }
    return _xblockexpression;
  }
  
  public static CharSequence generateCTEACase(final Property property, final int defaultCode) {
    CharSequence _xblockexpression = null;
    {
      Object code = Utils.getNomenclatureCode(property);
      Object libelle = Utils.getNomenclatureLibelle(property);
      if ((libelle == null)) {
        libelle = property.getName();
      }
      if ((code == null)) {
        code = Integer.valueOf(defaultCode);
      }
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("case ");
      _builder.append(code);
      _builder.append(":");
      _builder.newLineIfNotEmpty();
      _builder.append("\t");
      _builder.append("return \"");
      _builder.append(libelle, "\t");
      _builder.append("\";");
      _builder.newLineIfNotEmpty();
      _xblockexpression = _builder;
    }
    return _xblockexpression;
  }
}
