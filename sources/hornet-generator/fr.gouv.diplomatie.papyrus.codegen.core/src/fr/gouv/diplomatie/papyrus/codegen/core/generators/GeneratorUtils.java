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
 *
 */

/**
 * fr.gouv.diplomatie.papyrus.codegen.core - Ensembles des outils mis à dispositions
 * pour l'écriture d'un générateur de code Hornet
 *
 * @author MEAE - Ministère de l'Europe et des Affaires étrangères
 * @version v1.3.2
 * @license CECILL-2.1
 */
package fr.gouv.diplomatie.papyrus.codegen.core.generators;

import java.io.File;

import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.Package;

import fr.gouv.diplomatie.papyrus.codegen.core.utils.ModelUtils;
import fr.gouv.diplomatie.papyrus.codegen.core.utils.Utils;

/**
 * classe utilitaire pour les noms et chemins de fichier
 *
 */
public class GeneratorUtils {

	public static Boolean outGenerationDir = false;
	public static String SEP_CHAR = "/";
	
	public static final String SRC_REPOSITORY = "src-gen" + SEP_CHAR;
	public static final String SRC_INCODE_REPOSITORY = "src" + SEP_CHAR;
	
	public static final String MODEL_REPOSITORY = SRC_REPOSITORY + "models" + SEP_CHAR;
	public static final String MODEL_INCODE_REPOSITORY = SRC_INCODE_REPOSITORY + "models" + SEP_CHAR;
	
	public static final String DATABASE_REPOSITORY = SRC_REPOSITORY + "database" + SEP_CHAR ;
	
	public static final String DAO_REPOSITORY = SRC_REPOSITORY  + "dao" + SEP_CHAR ;
	public static final String DAO_INCODE_REPOSITORY = SRC_INCODE_REPOSITORY  + "dao" + SEP_CHAR ;
	
	public static void setOutGenerationDir(Boolean value) {
		outGenerationDir = value;
	}
	
	/**
	 * chemin du fichier contenant la classe modèle de la classe clazz
	 * @param clazz
	 * @param inCode indique si le chemin est pour la génération du chemin du fichier  ou pour un import dans le code
	 * @return
	 */
	public static String getModelPath(Classifier clazz, Boolean inCode) {
		Model application = clazz.getModel();
		String suffix = ModelUtils.getModelFileSuffix(application).toString();
		if(inCode && outGenerationDir) {
			return MODEL_INCODE_REPOSITORY +  "models" + SEP_CHAR  + Utils.toTypescriptFileName(clazz.getName()) + "-" + suffix;
		}
		return MODEL_REPOSITORY +  "models" + SEP_CHAR  + Utils.toTypescriptFileName(clazz.getName())+ "-" + suffix;
	}
	
	/**
	 * chemin du fichier contenant l'interface attributes de la classe clazz
	 * @param clazz
	 * @param inCode indique si le chemin est pour la génération du chemin du fichier  ou pour un import dans le code
	 * @return
	 */
	public static String getAttributesInterfacePath(Classifier clazz, Boolean inCode) {
		Model application = clazz.getModel();
		String suffix = ModelUtils.getAttributesFileSuffix(application).toString();
		if(inCode && outGenerationDir) {
			return MODEL_INCODE_REPOSITORY +  "attributes" + SEP_CHAR + Utils.toTypescriptFileName(clazz.getName()) + "-" + suffix;
		}
		return MODEL_REPOSITORY +  "attributes" + SEP_CHAR + Utils.toTypescriptFileName(clazz.getName()) + "-" + suffix;
	}
	
	/**
	 * chemin du fichier contenant la classe metier de la classe clazz
	 * @param clazz
	 * @param inCode indique si le chemin est pour la génération du chemin du fichier  ou pour un import dans le code
	 * @return
	 */
	public static String getMetierClassPath(Classifier clazz, Boolean inCode) {
		Model application = clazz.getModel();
		String suffix = ModelUtils.getMetierFileSuffix(application).toString();
		if(inCode && outGenerationDir) {
			return MODEL_INCODE_REPOSITORY +  "metier" + SEP_CHAR + Utils.toTypescriptFileName(clazz.getName())+ "-" + suffix;
		}
		return MODEL_REPOSITORY +  "metier" + SEP_CHAR + Utils.toTypescriptFileName(clazz.getName())+ "-" + suffix;
	}
	
	/**
	 * chemin du fichier contenant le dto de la classe clazz
	 * @param clazz
	 * @param inCode indique si le chemin est pour la génération du chemin du fichier  ou pour un import dans le code
	 * @return
	 */
	public static String getDtoClassPath(Classifier clazz, Boolean inCode) {
		Model application = clazz.getModel();
		String suffix = ModelUtils.getDtoFileSuffix(application).toString();
		if(inCode && outGenerationDir) {
			return MODEL_INCODE_REPOSITORY +  "dto" + SEP_CHAR  + Utils.toTypescriptFileName(clazz.getName())+ "-" + suffix;
		}
		return MODEL_REPOSITORY +  "dto" + SEP_CHAR + Utils.toTypescriptFileName(clazz.getName())+ "-" + suffix;
	}
	
	/**
	 * chemin du fichier de création de la base de données
	 * @return
	 */
	public static String getDatabaseScriptPath(Package pakkage) {
		return DATABASE_REPOSITORY + "old" + SEP_CHAR + "createTablesPostgres";
	}
	
	/**
	 * chemin du fichier de création de la base de données
	 * @return
	 */
	public static String getDatabaseSqliteScriptPath(Package pakkage) {
		return DATABASE_REPOSITORY + "old" + SEP_CHAR + "createTablesSqlite";
	}
	
	/**
	 * chemin du fichier de création de la base de données
	 * @return
	 */
	public static String getCreateUserScriptPath(Package pakkage) {
		return DATABASE_REPOSITORY + "old" + SEP_CHAR + "createUsersPostgres";
	}
	
	/**
	 * chemin du fichier de création de la base de données
	 * @return
	 */
	public static String getUpdateDatabaseScriptPath(Package pakkage) {
		return DATABASE_REPOSITORY + "old" + SEP_CHAR + "updateTablesPostgres";
	}

	
	/**
	 * chemin du fichier modelDAO
	 * @param clazz
	 * @param inCode indique si le chemin est pour la génération du chemin du fichier  ou pour un import dans le code
	 * @return
	 */
	public static String getModelDaoPath(Package pakkage, Boolean inCode) {
		if(inCode && outGenerationDir) {
			return DAO_INCODE_REPOSITORY + "model-dao";
		}
		return DAO_REPOSITORY + "model-dao";
	}
	
	/**
	 * chemin du fichier contenant la classe Metier de l'enum
	 * @param clazz
	 * @param inCode indique si le chemin est pour la génération du chemin du fichier  ou pour un import dans le code
	 * @return
	 */
	public static String getEnumPath(Classifier clazz, Boolean inCode) {
		if(inCode && outGenerationDir) {
			return MODEL_INCODE_REPOSITORY + Utils.toTypescriptFileName(clazz.getName()) + "-enum";
		}
		return MODEL_REPOSITORY  +Utils.toTypescriptFileName(clazz.getName())+ "-enum";
	}
	
}
