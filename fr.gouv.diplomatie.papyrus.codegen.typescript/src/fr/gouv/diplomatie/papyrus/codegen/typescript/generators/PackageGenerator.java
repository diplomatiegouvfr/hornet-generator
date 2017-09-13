package fr.gouv.diplomatie.papyrus.codegen.typescript.generators;

import org.eclipse.papyrus.infra.tools.file.IPFileSystemAccess;
import org.eclipse.uml2.uml.Package;

import fr.gouv.diplomatie.papyrus.codegen.core.generators.GeneratorUtils;
import fr.gouv.diplomatie.papyrus.codegen.core.utils.Utils;
import fr.gouv.diplomatie.papyrus.codegen.typescript.xtend.pakkage.PackageModelDaoClassGenerator;

public class PackageGenerator{

	public static void generateModelDao(Package pakkage, IPFileSystemAccess fileSystemAccess) {
		String fileName = GeneratorUtils.getModelDaoPath(pakkage, false) + ".ts";
		
		Utils.console.out.println("PackageGenerator.generateEntityDao : "  + pakkage.getName() + ", fichier : " + fileName);
		
		fileSystemAccess.generateFile(fileName, PackageModelDaoClassGenerator.generateCode(pakkage).toString());
	}
	
}
