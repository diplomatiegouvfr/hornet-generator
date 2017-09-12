package fr.gouv.diplomatie.papyrus.codegen.transformations;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.papyrus.designer.languages.common.base.HierarchyLocationStrategy;
import org.eclipse.papyrus.designer.languages.common.base.ModelElementsCreator;
import org.eclipse.papyrus.infra.tools.file.ProjectBasedFileAccess;
import org.eclipse.uml2.uml.AssociationClass;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.Interface;
import org.eclipse.uml2.uml.PackageableElement;

import fr.gouv.diplomatie.papyrus.codegen.generators.AssociationClassGenerator;
import fr.gouv.diplomatie.papyrus.codegen.generators.ClassifierGenerator;
import fr.gouv.diplomatie.papyrus.codegen.generators.GeneratorUtils;
import fr.gouv.diplomatie.papyrus.codegen.generators.NomenclatureGenerator;
import fr.gouv.diplomatie.papyrus.codegen.generators.PackageGenerator;
import fr.gouv.diplomatie.papyrus.codegen.xtend.utils.ClassifierUtils;
import fr.gouv.diplomatie.papyrus.codegen.xtend.utils.Utils;

import org.eclipse.uml2.uml.Package;

public class ProjectModelElementsCreator extends ModelElementsCreator {

	
	public ProjectModelElementsCreator(IProject project) {
		super(new ProjectBasedFileAccess(project), new HierarchyLocationStrategy(), "");
	}
	
	
	@Override
	protected void createPackageableElementFile(PackageableElement packageableElement, IProgressMonitor progressMonitor) {
		
		if(packageableElement instanceof Package) {
			generatePackage((Package) packageableElement);
		}else if(packageableElement instanceof AssociationClass) {
			generateAssociationClass((AssociationClass) packageableElement);
		}else if(Utils.isNomenclature(packageableElement)) {
			generateNomenclature((Classifier)packageableElement);
		}else if(Utils.isEntity(packageableElement)) {
			generateClass((Classifier) packageableElement);
		}else if(Utils.isValueObject(packageableElement)) {
			generateValueObject((Classifier)packageableElement);
		}else if(packageableElement instanceof Interface) {
			generateInterface((Interface) packageableElement);
		}
	}
	
	/**
	 * génère uniquement le script de database
	 * @param packageableElement
	 * @param progressMonitor
	 */
	public void generateDatabaseScript(PackageableElement packageableElement, IProgressMonitor progressMonitor) {
		if(packageableElement instanceof Package) {
			PackageGenerator.generateDatabaseScript((Package) packageableElement, fileSystemAccess);
		}
	}
	
	/**
	 * Génère les fichiers liés au package
	 * @param pakkage
	 */
	protected void generatePackage(Package pakkage) {
		PackageGenerator.generateDatabaseScript(pakkage, fileSystemAccess);
		PackageGenerator.generateModelDao(pakkage, fileSystemAccess);
	}
	
	/**
	 * Génère les fichiers liés à la classe entity
	 * @param clazz
	 */
	protected void generateClass(Classifier clazz) {
		if(ClassifierUtils.canBeGenerated(clazz)) {
			ClassifierGenerator.generateModel(clazz, fileSystemAccess);
			ClassifierGenerator.generateAttributesInterface(clazz, fileSystemAccess);
			ClassifierGenerator.generateMetierClass(clazz, fileSystemAccess);
			ClassifierGenerator.generateDto(clazz, fileSystemAccess);
		}else {
			GeneratorUtils.warning.println("La classe "+ clazz.getName() + " ne sera pas générée car elle possède une propriété generated à false");
		}
	}
	
	/**
	 * Génère les fichiers liés à la classe valueObject
	 * @param clazz
	 */
	protected void generateValueObject(Classifier clazz) {
		if(ClassifierUtils.canBeGenerated(clazz)) {
			ClassifierGenerator.generateValueObjectMetierClass(clazz, fileSystemAccess);
			ClassifierGenerator.generateAttributesInterface(clazz, fileSystemAccess);
		}else {
			GeneratorUtils.warning.println("La classe "+ clazz.getName() + " ne sera pas générée car elle possède une propriété generated à false");
		}
	}
	
	/**
	 * Génère les fichiers liés a la classe d'association
	 * @param clazz
	 */
	protected void generateAssociationClass(AssociationClass clazz) {
		AssociationClassGenerator.generateModel(clazz, fileSystemAccess);
		AssociationClassGenerator.generateAttributesInterface(clazz, fileSystemAccess);
		AssociationClassGenerator.generateMetierClass(clazz, fileSystemAccess);
		AssociationClassGenerator.generateDto(clazz, fileSystemAccess);
	}
	

	/**
	 * Génère les fichiers liés a la classe d'association
	 * @param clazz
	 */
	protected void generateInterface(Interface clazz) {
		if(ClassifierUtils.canBeGenerated(clazz)) {
			ClassifierGenerator.generateAttributesInterface(clazz, fileSystemAccess);
			ClassifierGenerator.generateMetierClass(clazz, fileSystemAccess);
		}else {
			GeneratorUtils.warning.println("La classe "+ clazz.getName() + " ne sera pas générée car elle possède une propriété generated à false");
		}
	}
	
	/**
	 * Génère les fichiers liés à la nomenclature
	 * @param clazz
	 */
	protected void generateNomenclature(Classifier clazz) {
		if(ClassifierUtils.canBeGenerated(clazz)) {
			NomenclatureGenerator.generateEnumClass(clazz, fileSystemAccess);
			NomenclatureGenerator.generateEnumModel(clazz, fileSystemAccess);
			NomenclatureGenerator.generateEnumDto(clazz, fileSystemAccess);
			NomenclatureGenerator.generateEnumAttributesInterface(clazz, fileSystemAccess);
		}else {
			GeneratorUtils.warning.println("La classe "+ clazz.getName() + " ne sera pas générée car elle possède une propriété generated à false");
		}
	}
	
}
