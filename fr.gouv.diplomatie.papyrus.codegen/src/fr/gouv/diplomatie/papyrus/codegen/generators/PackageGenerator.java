package fr.gouv.diplomatie.papyrus.codegen.generators;

import org.eclipse.papyrus.infra.tools.file.IPFileSystemAccess;
import org.eclipse.uml2.uml.Package;

import fr.gouv.diplomatie.papyrus.codegen.xtend.pakkage.PackageDatabaseScriptGenerator;
import fr.gouv.diplomatie.papyrus.codegen.xtend.pakkage.PackageModelDaoClassGenerator;

public class PackageGenerator {

	public static void generateModelDao(Package pakkage, IPFileSystemAccess fileSystemAccess) {
		System.out.println("PackageGenerator.generateEntityDao : "  + pakkage.getName());
		
		String fileName = GeneratorUtils.getModelDaoPath(pakkage, false) + ".ts";
		
		fileSystemAccess.generateFile(fileName, PackageModelDaoClassGenerator.generateCode(pakkage).toString());
	}
	
	public static void generateDatabaseScript(Package pakkage, IPFileSystemAccess fileSystemAccess) {
		System.out.println("PackageGenerator.generateDatabaseScript : "  + pakkage.getName());
		
		String fileName = GeneratorUtils.getDatabaseScriptPath(pakkage, false) + ".sql";
		
		fileSystemAccess.generateFile(fileName, PackageDatabaseScriptGenerator.generateCode(pakkage).toString());
	}
}
