package fr.gouv.diplomatie.papyrus.codegen.generators;

import org.eclipse.papyrus.infra.tools.file.IPFileSystemAccess;
import org.eclipse.uml2.uml.Package;

import fr.gouv.diplomatie.papyrus.codegen.xtend.pakkage.PackageDatabaseScriptGenerator;

public class PackageGenerator {

	public static void generateEntityDao(Package pakkage, IPFileSystemAccess fileSystemAccess) {
		System.out.println("PackageGenerator.generateEntityDao : "  + pakkage.getName());
	}
	
	public static void generateDatabaseScript(Package pakkage, IPFileSystemAccess fileSystemAccess) {
		System.out.println("PackageGenerator.generateDatabaseScript : "  + pakkage.getName());
		
		String fileName = GeneratorUtils.getDatabaseScriptPath(pakkage) + ".sql";
		
		fileSystemAccess.generateFile(fileName, PackageDatabaseScriptGenerator.generateCode(pakkage).toString());
	}
}
