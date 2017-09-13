package fr.gouv.diplomatie.papyrus.codegen.typescript.generators;

import org.eclipse.papyrus.infra.tools.file.IPFileSystemAccess;
import org.eclipse.uml2.uml.AssociationClass;

import fr.gouv.diplomatie.papyrus.codegen.core.generators.GeneratorUtils;
import fr.gouv.diplomatie.papyrus.codegen.core.utils.Utils;
import fr.gouv.diplomatie.papyrus.codegen.typescript.xtend.associationClass.AssociationClassAttributesInterfaceGenerator;
import fr.gouv.diplomatie.papyrus.codegen.typescript.xtend.associationClass.AssociationClassDtoClassGenerator;
import fr.gouv.diplomatie.papyrus.codegen.typescript.xtend.associationClass.AssociationClassMetierClassGenerator;
import fr.gouv.diplomatie.papyrus.codegen.typescript.xtend.associationClass.AssociationClassModelGenerator;

public class AssociationClassGenerator {

	public static void generateModel(AssociationClass clazz, IPFileSystemAccess fileSystemAccess) {	
		String fileName = GeneratorUtils.getModelPath(clazz, false) + ".ts";
		
		Utils.console.out.println("AssociationClassGenerator.generateModel : "  + clazz.getName() + ", fichier : " + fileName);
		
		fileSystemAccess.generateFile(fileName, AssociationClassModelGenerator.generateCode(clazz).toString());
		
	}
	
	public static void generateAttributesInterface(AssociationClass clazz, IPFileSystemAccess fileSystemAccess) {	
		String fileName = GeneratorUtils.getAttributesInterfacePath(clazz, false) + ".ts";
		
		Utils.console.out.println("AssociationClassGenerator.generateAttributesInterface : "  + clazz.getName() + ", fichier : " + fileName);
		
		fileSystemAccess.generateFile(fileName, AssociationClassAttributesInterfaceGenerator.generateCode(clazz).toString());
	}
	
	public static void generateMetierClass(AssociationClass clazz, IPFileSystemAccess fileSystemAccess) {
		String fileName = GeneratorUtils.getMetierClassPath(clazz, false) + ".ts";
		
		Utils.console.out.println("ClassifierGenerator.generateMetierClass : "  + clazz.getName() + ", fichier : " + fileName);

		fileSystemAccess.generateFile(fileName, AssociationClassMetierClassGenerator.generateCode(clazz).toString());
		
	}
	
	public static void generateDto(AssociationClass clazz, IPFileSystemAccess fileSystemAccess) {	
		String fileName = GeneratorUtils.getDtoClassPath(clazz, false) + ".ts";
		
		Utils.console.out.println("AssociationClassGenerator.generateDto : "  + clazz.getName() + ", fichier : " + fileName);
		
		fileSystemAccess.generateFile(fileName, AssociationClassDtoClassGenerator.generateCode(clazz).toString());
	}
}
