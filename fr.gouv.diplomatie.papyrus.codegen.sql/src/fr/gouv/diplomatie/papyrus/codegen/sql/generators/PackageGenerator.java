package fr.gouv.diplomatie.papyrus.codegen.sql.generators;

import org.eclipse.papyrus.infra.tools.file.IPFileSystemAccess;
import org.eclipse.uml2.uml.Package;

import fr.gouv.diplomatie.papyrus.codegen.core.generators.GeneratorUtils;
import fr.gouv.diplomatie.papyrus.codegen.core.utils.Utils;
import fr.gouv.diplomatie.papyrus.codegen.sql.xtend.pakkage.PackageDatabaseScriptGenerator;

public class PackageGenerator{

	public static void generateDatabaseScript(Package pakkage, IPFileSystemAccess fileSystemAccess) {
		String fileName = GeneratorUtils.getDatabaseScriptPath(pakkage, false) + ".sql";
		
		Utils.console.out.println("PackageGenerator.generateDatabaseScript : "  + pakkage.getName() + ", fichier : " + fileName);
		
		fileSystemAccess.generateFile(fileName, PackageDatabaseScriptGenerator.generateCode(pakkage).toString());
	}
}
