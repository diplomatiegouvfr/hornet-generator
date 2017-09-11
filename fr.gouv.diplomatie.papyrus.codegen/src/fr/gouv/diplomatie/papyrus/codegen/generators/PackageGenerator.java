package fr.gouv.diplomatie.papyrus.codegen.generators;

import org.eclipse.papyrus.infra.tools.file.IPFileSystemAccess;
import org.eclipse.uml2.uml.Package;

import fr.gouv.diplomatie.papyrus.codegen.xtend.pakkage.PackageDatabaseScriptGenerator;
import fr.gouv.diplomatie.papyrus.codegen.xtend.pakkage.PackageModelDaoClassGenerator;

public class PackageGenerator extends HornetGenerator{

	public static void generateModelDao(Package pakkage, IPFileSystemAccess fileSystemAccess) {
		String fileName = GeneratorUtils.getModelDaoPath(pakkage, false) + ".ts";
		
		out.println("PackageGenerator.generateEntityDao : "  + pakkage.getName() + ", fichier : " + fileName);
		
		fileSystemAccess.generateFile(fileName, PackageModelDaoClassGenerator.generateCode(pakkage).toString());
	}
	
	public static void generateDatabaseScript(Package pakkage, IPFileSystemAccess fileSystemAccess) {
		String fileName = GeneratorUtils.getDatabaseScriptPath(pakkage, false) + ".sql";
		
		out.println("PackageGenerator.generateDatabaseScript : "  + pakkage.getName() + ", fichier : " + fileName);
		
		fileSystemAccess.generateFile(fileName, PackageDatabaseScriptGenerator.generateCode(pakkage).toString());
	}
}
