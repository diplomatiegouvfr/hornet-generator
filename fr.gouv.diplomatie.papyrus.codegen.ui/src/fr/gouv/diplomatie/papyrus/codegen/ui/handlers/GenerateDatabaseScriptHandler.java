package fr.gouv.diplomatie.papyrus.codegen.ui.handlers;

import org.eclipse.core.resources.IProject;
import org.eclipse.uml2.uml.PackageableElement;

import fr.gouv.diplomatie.papyrus.codegen.core.generators.GeneratorUtils;
import fr.gouv.diplomatie.papyrus.codegen.ui.core.handlers.HornetCodeHandler;
import fr.gouv.diplomatie.papyrus.codegen.sql.transformations.ProjectDatabaseScriptElementsCreator;

public class GenerateDatabaseScriptHandler extends HornetCodeHandler {

	public GenerateDatabaseScriptHandler() {
		super();
		this.message = "= executing Generate Database Script Handler";
	}
	
	public void generate(PackageableElement packageableElement) {
		GeneratorUtils.out.println("generate database script Handler : generate()");
		creator.createPackageableElement(packageableElement, null, true);
	}

	@Override
	public void initiateAndGenerate(IProject project, PackageableElement packageableElement) {
		this.creator = new ProjectDatabaseScriptElementsCreator(project);
		generate(packageableElement);
	}

}
