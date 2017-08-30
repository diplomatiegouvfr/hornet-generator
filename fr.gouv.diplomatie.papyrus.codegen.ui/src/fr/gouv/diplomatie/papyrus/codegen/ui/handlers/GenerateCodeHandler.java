package fr.gouv.diplomatie.papyrus.codegen.ui.handlers;

import org.eclipse.core.resources.IProject;
import org.eclipse.uml2.uml.PackageableElement;

import fr.gouv.diplomatie.papyrus.codegen.transformations.ProjectDatabaseScriptElementsCreator;
import fr.gouv.diplomatie.papyrus.codegen.transformations.ProjectModelElementsCreator;

public class GenerateCodeHandler extends HornetCodeHandler {

	public GenerateCodeHandler() {
		super();
		this.message = "= executing Generate code Handler";
	}
	
	@Override
	public void initiateAndGenerate(IProject project, PackageableElement packageableElement) {
		this.creator = new ProjectModelElementsCreator(project);
		generate(packageableElement);
	}
	
	public void generate(ProjectDatabaseScriptElementsCreator modelElementsCreator, PackageableElement packageableElement) {
		System.out.println("generate code Handler : generate()");
		creator.createPackageableElement(packageableElement, null, true);
	}
	
}
