package fr.gouv.diplomatie.papyrus.codegen.generators;

import org.eclipse.papyrus.infra.tools.file.IPFileSystemAccess;
import org.eclipse.uml2.uml.Classifier;

import fr.gouv.diplomatie.papyrus.codegen.xtend.classifier.NomenclatureAttributesClassGenerator;
import fr.gouv.diplomatie.papyrus.codegen.xtend.classifier.NomenclatureDtoClassGenerator;
import fr.gouv.diplomatie.papyrus.codegen.xtend.classifier.NomenclatureEnumClassGenerator;
import fr.gouv.diplomatie.papyrus.codegen.xtend.classifier.NomenclatureModelGenerator;

public class NomenclatureGenerator extends HornetGenerator{

	public static void generateEnumClass(Classifier clazz, IPFileSystemAccess fileSystemAccess) {		
		String fileName = GeneratorUtils.getEnumPath(clazz, false) + ".ts";
		
		out.println("NomenclatureGenerator.generateEnumClass : "  + clazz.getName() + ", fichier : " + fileName);
		
		fileSystemAccess.generateFile(fileName, NomenclatureEnumClassGenerator.generateCode(clazz).toString());
		
	}
	
	public static void generateEnumModel(Classifier clazz, IPFileSystemAccess fileSystemAccess) {
		String fileName = GeneratorUtils.getModelPath(clazz, false) + ".ts";
		
		out.println("NomenclatureGenerator.generateEnumModel : "  + clazz.getName() + ", fichier : " + fileName);

		fileSystemAccess.generateFile(fileName, NomenclatureModelGenerator.generateCode(clazz).toString());
		
	}
	
	public static void generateEnumDto(Classifier clazz, IPFileSystemAccess fileSystemAccess) {		
		String fileName = GeneratorUtils.getDtoClassPath(clazz, false) + ".ts";
		
		out.println("NomenclatureGenerator.generateEnumDto : "  + clazz.getName() + ", fichier : " + fileName);

		fileSystemAccess.generateFile(fileName, NomenclatureDtoClassGenerator.generateCode(clazz).toString());
		
	}
	
	public static void generateEnumAttributesInterface(Classifier clazz, IPFileSystemAccess fileSystemAccess) {
		String fileName = GeneratorUtils.getAttributesInterfacePath(clazz, false) + ".ts";
		
		out.println("NomenclatureGenerator.generateEnumAttributesInterface : "  + clazz.getName() + ", fichier : " + fileName);

		fileSystemAccess.generateFile(fileName, NomenclatureAttributesClassGenerator.generateCode(clazz).toString());
		
	}
}
