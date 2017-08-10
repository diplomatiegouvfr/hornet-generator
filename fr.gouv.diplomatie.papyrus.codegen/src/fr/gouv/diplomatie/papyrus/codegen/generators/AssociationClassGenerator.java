package fr.gouv.diplomatie.papyrus.codegen.generators;

import org.eclipse.papyrus.infra.tools.file.IPFileSystemAccess;
import org.eclipse.uml2.uml.AssociationClass;


import fr.gouv.diplomatie.papyrus.codegen.xtend.associationClass.AssociationClassAttributesInterfaceGenerator;
import fr.gouv.diplomatie.papyrus.codegen.xtend.associationClass.AssociationClassDtoClassGenerator;
import fr.gouv.diplomatie.papyrus.codegen.xtend.associationClass.AssociationClassMetierClassGenerator;
import fr.gouv.diplomatie.papyrus.codegen.xtend.associationClass.AssociationClassModelGenerator;

public class AssociationClassGenerator {

	public static void generateModel(AssociationClass clazz, IPFileSystemAccess fileSystemAccess) {
		System.out.println("AssociationClassGenerator.generateModel : "  + clazz.getName());
		
		String fileName = GeneratorUtils.getModelPath(clazz) + ".ts";
		
		fileSystemAccess.generateFile(fileName, AssociationClassModelGenerator.generateCode(clazz).toString());
		
	}
	
	public static void generateAttributesInterface(AssociationClass clazz, IPFileSystemAccess fileSystemAccess) {
		System.out.println("AssociationClassGenerator.generateAttributesInterface : "  + clazz.getName());
		
		String fileName = GeneratorUtils.getAttributesInterfacePath(clazz) + ".ts";
		
		fileSystemAccess.generateFile(fileName, AssociationClassAttributesInterfaceGenerator.generateCode(clazz).toString());
	}
	
	public static void generateMetierClass(AssociationClass clazz, IPFileSystemAccess fileSystemAccess) {
		System.out.println("ClassifierGenerator.generateAttributesInterface : "  + clazz.getName());

		String fileName = GeneratorUtils.getMetierClassPath(clazz) + ".ts";
		
		fileSystemAccess.generateFile(fileName, AssociationClassMetierClassGenerator.generateCode(clazz).toString());
		
	}
	
	public static void generateDto(AssociationClass clazz, IPFileSystemAccess fileSystemAccess) {
		System.out.println("AssociationClassGenerator.generateDto : "  + clazz.getName());
		
		String fileName = GeneratorUtils.getDtoClassPath(clazz) + ".ts";
		
		fileSystemAccess.generateFile(fileName, AssociationClassDtoClassGenerator.generateCode(clazz).toString());
	}
}
