package fr.gouv.diplomatie.papyrus.codegen.sql.transformations;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.papyrus.designer.languages.common.base.HierarchyLocationStrategy;
import org.eclipse.papyrus.designer.languages.common.base.ModelElementsCreator;
import org.eclipse.papyrus.infra.tools.file.ProjectBasedFileAccess;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.PackageableElement;

import fr.gouv.diplomatie.papyrus.codegen.sql.generators.PackageGenerator;

public class ProjectCreateUsersScriptElementsCreator extends ModelElementsCreator {
	
	public ProjectCreateUsersScriptElementsCreator(IProject project) {
		super(new ProjectBasedFileAccess(project), new HierarchyLocationStrategy(), "");
	}
	
	/**
	 * génère le script de création des users
	 * @param packageableElement
	 * @param progressMonitor
	 */
	@Override
	protected void createPackageableElementFile(PackageableElement packageableElement, IProgressMonitor progressMonitor) {
		
		if(packageableElement instanceof Package) {
			PackageGenerator.generateCreateUserScript((Package) packageableElement, fileSystemAccess);
		}
	}

}