package fr.gouv.diplomatie.papyrus.codegen.ui.handlers;

import org.eclipse.core.resources.IProject;
import org.eclipse.uml2.uml.PackageableElement;

import fr.gouv.diplomatie.papyrus.codegen.ui.core.handlers.HornetCodeHandler;
import fr.gouv.diplomatie.papyrus.codegen.sql.transformations.ProjectDatabaseScriptElementsCreator;
import fr.gouv.diplomatie.papyrus.codegen.typescript.transformations.ProjectModelElementsCreator;

public class GenerateCodeHandler extends HornetCodeHandler {

	public GenerateCodeHandler() {
		super();
		this.message = "= executing Generate code Handler";
	}
	
	@Override
	public void initiateAndGenerate(IProject project, PackageableElement packageableElement) {
		this.creator = new ProjectModelElementsCreator(project);
		generate(packageableElement);
		this.creator = new ProjectDatabaseScriptElementsCreator(project);
		generate(packageableElement);
	}
	
	public void generate(PackageableElement packageableElement) {
		console.out.println("generate code Handler : generate()");
		creator.createPackageableElement(packageableElement, null, true);
	}
	
}
