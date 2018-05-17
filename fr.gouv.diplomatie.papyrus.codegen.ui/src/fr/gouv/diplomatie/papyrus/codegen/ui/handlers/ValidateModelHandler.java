package fr.gouv.diplomatie.papyrus.codegen.ui.handlers;

import java.util.ArrayList;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.emf.common.util.URI;
import org.eclipse.papyrus.uml.diagram.common.handlers.CmdHandler;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.PackageableElement;

import fr.gouv.diplomatie.papyrus.codegen.core.console.ConsoleUtils;
import fr.gouv.diplomatie.papyrus.codegen.ui.core.validator.HornetModelValidator;


public class ValidateModelHandler extends CmdHandler {
	
	protected ConsoleUtils console= new ConsoleUtils();
	
	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		
		if(selectedEObject instanceof PackageableElement) {
			PackageableElement packageableElement = (PackageableElement) selectedEObject;
			HornetModelValidator validator = new HornetModelValidator();
			ArrayList<String> validationErrors = validator.validate(packageableElement, console);
			ArrayList<String> validationWarnings = validator.warnings;
			for(String warning : validationWarnings) {
				console.warning.println(warning);
			}
			if(validationErrors.isEmpty()) {
				console.success.println("Modèle valide");
			}else {
				console.err.println("Modèle invalide");
				for(String error : validationErrors) {
					console.err.println(error);
				}
			}
		}
		return null;
	}
	
	@Override
	public boolean isEnabled() {
		updateSelectedEObject();

        if (selectedEObject instanceof Package || selectedEObject instanceof Classifier) {
            URI uri = selectedEObject.eResource().getURI();
            
            IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
            if (uri.segmentCount() < 2) {
                return false;
            }
            IProject modelProject = root.getProject(uri.segment(1));
            return modelProject.exists();
        }

        return false;
	}

}
