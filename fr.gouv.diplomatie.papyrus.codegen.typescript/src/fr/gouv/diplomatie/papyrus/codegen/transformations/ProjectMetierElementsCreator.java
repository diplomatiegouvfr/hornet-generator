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
import fr.gouv.diplomatie.papyrus.codegen.generators.NomenclatureGenerator;
import fr.gouv.diplomatie.papyrus.codegen.xtend.utils.Utils;


public class ProjectMetierElementsCreator extends ModelElementsCreator {

	
	public ProjectMetierElementsCreator(IProject project) {
		super(new ProjectBasedFileAccess(project), new HierarchyLocationStrategy(), "");
	}
	
	
	@Override
	protected void createPackageableElementFile(PackageableElement packageableElement, IProgressMonitor progressMonitor) {
		
		if(packageableElement instanceof AssociationClass) {
			AssociationClassGenerator.generateMetierClass((AssociationClass)packageableElement, fileSystemAccess);
		}else if(Utils.isNomenclature(packageableElement)) {
			NomenclatureGenerator.generateEnumClass((Classifier)packageableElement, fileSystemAccess);
		}else if(Utils.isEntity(packageableElement)) {
			ClassifierGenerator.generateMetierClass((Classifier)packageableElement, fileSystemAccess);
		}else if(Utils.isValueObject(packageableElement)) {
			ClassifierGenerator.generateValueObjectMetierClass((Classifier)packageableElement, fileSystemAccess);
		}else if(packageableElement instanceof Interface) {
			ClassifierGenerator.generateMetierClass((Interface)packageableElement, fileSystemAccess);
		}
		
	}
	

}
