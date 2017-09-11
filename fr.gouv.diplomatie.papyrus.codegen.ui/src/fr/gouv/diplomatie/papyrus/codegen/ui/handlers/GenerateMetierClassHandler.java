package fr.gouv.diplomatie.papyrus.codegen.ui.handlers;

import org.eclipse.core.resources.IProject;
import org.eclipse.uml2.uml.PackageableElement;

import fr.gouv.diplomatie.papyrus.codegen.transformations.ProjectDatabaseScriptElementsCreator;
import fr.gouv.diplomatie.papyrus.codegen.transformations.ProjectMetierElementsCreator;

public class GenerateMetierClassHandler extends HornetCodeHandler {

	public GenerateMetierClassHandler() {
		super();
		this.message = "= executing Generate Metier Class Handler";
	}
	
	@Override
	public void initiateAndGenerate(IProject project, PackageableElement packageableElement) {
		this.creator = new ProjectMetierElementsCreator(project);
		generate(packageableElement);
	}
	
	public void generate(ProjectDatabaseScriptElementsCreator modelElementsCreator, PackageableElement packageableElement) {
		out.println("generate metier Class Handler : generate()");
		creator.createPackageableElement(packageableElement, null, true);
	}

}
