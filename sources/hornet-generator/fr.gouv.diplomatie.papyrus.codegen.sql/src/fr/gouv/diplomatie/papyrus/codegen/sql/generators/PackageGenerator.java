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
 * fr.gouv.diplomatie.papyrus.codegen.sql - Générateur de code sql pour 
 * des applications Hornet JS
 *
 * @author MEAE - Ministère de l'Europe et des Affaires étrangères
 * @version v1.3.2
 * @license CECILL-2.1
 */
package fr.gouv.diplomatie.papyrus.codegen.sql.generators;

import org.eclipse.papyrus.infra.tools.file.IPFileSystemAccess;
import org.eclipse.uml2.uml.Package;

import fr.gouv.diplomatie.papyrus.codegen.core.generators.GeneratorUtils;
import fr.gouv.diplomatie.papyrus.codegen.core.utils.Utils;
import fr.gouv.diplomatie.papyrus.codegen.sql.utils.SqlGeneratorUtils;
import fr.gouv.diplomatie.papyrus.codegen.sql.xtend.pakkage.PackageCreateBddScriptGenerator;
import fr.gouv.diplomatie.papyrus.codegen.sql.xtend.pakkage.PackageCreateDboScriptGenerator;
import fr.gouv.diplomatie.papyrus.codegen.sql.xtend.pakkage.PackageCreateObjectScriptGenerator;
import fr.gouv.diplomatie.papyrus.codegen.sql.xtend.pakkage.PackageCreateSchemaScriptGenerator;
import fr.gouv.diplomatie.papyrus.codegen.sql.xtend.pakkage.PackageCreateUserGroupsScriptGenerator;
import fr.gouv.diplomatie.papyrus.codegen.sql.xtend.pakkage.PackageCreateUserScriptGenerator;
import fr.gouv.diplomatie.papyrus.codegen.sql.xtend.pakkage.PackageCreateUsersScriptGenerator;
import fr.gouv.diplomatie.papyrus.codegen.sql.xtend.pakkage.PackageDatabaseScriptGenerator;
import fr.gouv.diplomatie.papyrus.codegen.sql.xtend.pakkage.PackageDatabaseSqLiteScriptGenerator;
import fr.gouv.diplomatie.papyrus.codegen.sql.xtend.pakkage.PackageUpdateDatabaseScriptGenerator;

public class PackageGenerator{

	public static void generateDatabaseScript(Package pakkage, IPFileSystemAccess fileSystemAccess) {
		String fileName = GeneratorUtils.getDatabaseScriptPath(pakkage) + ".sql";
		
		Utils.console.out.println("PackageGenerator.generateDatabaseScript : "  + pakkage.getName() + ", fichier : " + fileName);
		
		PackageDatabaseScriptGenerator generator = new PackageDatabaseScriptGenerator();
		
		fileSystemAccess.generateFile(fileName, generator.generateCode(pakkage).toString());
	}
	
	public static void generateUpdateDatabaseScript(Package pakkage, IPFileSystemAccess fileSystemAccess) {
		String fileName = GeneratorUtils.getUpdateDatabaseScriptPath(pakkage) + ".sql";
		
		Utils.console.out.println("PackageGenerator.generateUpdateDatabaseScript : "  + pakkage.getName() + ", fichier : " + fileName);
		
		fileSystemAccess.generateFile(fileName, PackageUpdateDatabaseScriptGenerator.generateCode(pakkage).toString());
	}
	
	public static void generateCreateUserScript(Package pakkage, IPFileSystemAccess fileSystemAccess) {
		String fileName = GeneratorUtils.getCreateUserScriptPath(pakkage) + ".sql";
		
		Utils.console.out.println("PackageGenerator.generateCreateUserScript : "  + pakkage.getName() + ", fichier : " + fileName);
		
		fileSystemAccess.generateFile(fileName, PackageCreateUserScriptGenerator.generateCode(pakkage).toString());
	}
	
	public static void generateDatabaseSqliteScript(Package pakkage, IPFileSystemAccess fileSystemAccess) {
		String fileName = GeneratorUtils.getDatabaseSqliteScriptPath(pakkage) + ".sql";
		
		Utils.console.out.println("PackageGenerator.generateDatabaseSqliteScript : "  + pakkage.getName() + ", fichier : " + fileName);
		
		PackageDatabaseSqLiteScriptGenerator generator = new PackageDatabaseSqLiteScriptGenerator();
		
		fileSystemAccess.generateFile(fileName, generator.generateCode(pakkage).toString());
	}
	
	//--------------------------------------------
	
	public static void generateCreateBddScript(Package pakkage, IPFileSystemAccess fileSystemAccess) {
		String fileName = SqlGeneratorUtils.getCreateBddScriptPath(pakkage) + ".sql";
		
		Utils.console.out.println("PackageGenerator.generateCreateBddScript : "  + pakkage.getName() + ", fichier : " + fileName);
		
		fileSystemAccess.generateFile(fileName, PackageCreateBddScriptGenerator.generateCode(pakkage).toString());
	}
	
	public static void generateCreateDboScript(Package pakkage, IPFileSystemAccess fileSystemAccess) {
		String fileName = SqlGeneratorUtils.getCreateDboScriptPath(pakkage) + ".sql";
		
		Utils.console.out.println("PackageGenerator.generateCreateDboScript : "  + pakkage.getName() + ", fichier : " + fileName);
		
		fileSystemAccess.generateFile(fileName, PackageCreateDboScriptGenerator.generateCode(pakkage).toString());
	}
	
	public static void generateCreateObjectScript(Package pakkage, IPFileSystemAccess fileSystemAccess) {
		String fileName = SqlGeneratorUtils.getCreateObjectScriptPath(pakkage) + ".sql";
		
		Utils.console.out.println("PackageGenerator.generateCreateObjectScript : "  + pakkage.getName() + ", fichier : " + fileName);
		
		PackageCreateObjectScriptGenerator generator = new PackageCreateObjectScriptGenerator();
		
		fileSystemAccess.generateFile(fileName, generator.generateCode(pakkage).toString());
	}
	
	public static void generateCreateSchemaScript(Package pakkage, IPFileSystemAccess fileSystemAccess) {
		String fileName = SqlGeneratorUtils.getCreateSchemaScriptPath(pakkage) + ".sql";
		
		Utils.console.out.println("PackageGenerator.generateCreateSchemaScript : "  + pakkage.getName() + ", fichier : " + fileName);
		
		PackageCreateSchemaScriptGenerator generator = new PackageCreateSchemaScriptGenerator();

		fileSystemAccess.generateFile(fileName, generator.generateCode(pakkage).toString());
	}
	
	public static void generateCreateUserGroupsScript(Package pakkage, IPFileSystemAccess fileSystemAccess) {
		String fileName = SqlGeneratorUtils.getCreateUserGroupsScriptPath(pakkage) + ".sql";
		
		Utils.console.out.println("PackageGenerator.generateCreateUserGroupsScript : "  + pakkage.getName() + ", fichier : " + fileName);
		
		fileSystemAccess.generateFile(fileName, PackageCreateUserGroupsScriptGenerator.generateCode(pakkage).toString());
	}
	
	public static void generateCreateUsersScript(Package pakkage, IPFileSystemAccess fileSystemAccess) {
		String fileName = SqlGeneratorUtils.getCreateUsersScriptPath(pakkage) + ".sql";
		
		Utils.console.out.println("PackageGenerator.generateCreateUsersScript : "  + pakkage.getName() + ", fichier : " + fileName);
		
		fileSystemAccess.generateFile(fileName, PackageCreateUsersScriptGenerator.generateCode(pakkage).toString());
	}
	
}
