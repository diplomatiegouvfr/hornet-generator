package fr.gouv.diplomatie.papyrus.codegen.transformations;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.papyrus.designer.languages.common.base.HierarchyLocationStrategy;
import org.eclipse.papyrus.designer.languages.common.base.ModelElementsCreator;
import org.eclipse.papyrus.infra.tools.file.ProjectBasedFileAccess;
import org.eclipse.uml2.uml.PackageableElement;

import fr.gouv.diplomatie.papyrus.codegen.generators.PackageGenerator;

import org.eclipse.uml2.uml.Package;

public class ProjectDatabaseScriptElementsCreator extends ModelElementsCreator {
	
	public ProjectDatabaseScriptElementsCreator(IProject project) {
		super(new ProjectBasedFileAccess(project), new HierarchyLocationStrategy(), "");
	}
		
	@Override
	protected void createPackageableElementFile(PackageableElement packageableElement, IProgressMonitor progressMonitor) {
		
		if(packageableElement instanceof Package) {
			PackageGenerator.generateDatabaseScript((Package) packageableElement, fileSystemAccess);
		}
	}

}
