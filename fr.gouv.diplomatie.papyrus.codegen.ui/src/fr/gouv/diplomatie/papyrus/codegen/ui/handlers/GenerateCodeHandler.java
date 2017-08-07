package fr.gouv.diplomatie.papyrus.codegen.ui.handlers;

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

import fr.gouv.diplomatie.papyrus.codegen.transformations.ProjectModelElementsCreator;

public class GenerateCodeHandler extends CmdHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		System.out.println("= executing Generate Code Handler");
		
		if(selectedEObject instanceof PackageableElement) {
			System.out.println("= selected Object is a PackageableElement");
			PackageableElement packageableElement = (PackageableElement) selectedEObject;
			
			IProject project = getCurrentProject();
			if(project != null) {
				System.out.println("= Current project is not null");
				ProjectModelElementsCreator modelElementsCreator = new ProjectModelElementsCreator(project);
				generate(modelElementsCreator, packageableElement);
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

	private IProject getCurrentProject() {	
		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		URI uri = selectedEObject.eResource().getURI();
		IProject project = root.getProject(uri.segment(1));
		return project;
	}
	
	public void generate(ProjectModelElementsCreator modelElementsCreator, PackageableElement packageableElement) {
		System.out.println("generate code Handler : generate()");
		modelElementsCreator.createPackageableElement(packageableElement, null, true);
		
	}

}
