package fr.gouv.diplomatie.papyrus.codegen.ui.core.handlers;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.emf.common.util.URI;
import org.eclipse.papyrus.designer.languages.common.base.ModelElementsCreator;
import org.eclipse.papyrus.uml.diagram.common.handlers.CmdHandler;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.PackageableElement;

import fr.gouv.diplomatie.papyrus.codegen.core.console.ConsoleUtils;

public abstract class HornetCodeHandler extends CmdHandler {
	
	protected ModelElementsCreator creator;
	protected String message = "= executing Generate Code Handler";
	protected ConsoleUtils console= new ConsoleUtils();

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		console.out.println(this.message);
		
		if(selectedEObject instanceof PackageableElement) {
			console.out.println("= selected Object is a PackageableElement");
			PackageableElement packageableElement = (PackageableElement) selectedEObject;
			
			IProject project = getCurrentProject();
			if(project != null) {
				try {
					initiateAndGenerate(project, packageableElement);
				}catch(Exception e) {
					StringWriter errors = new StringWriter();
					e.printStackTrace(new PrintWriter(errors));
					console.err.println(errors.toString());
				}
			}
		}
		return null;
	}
	
	/**
	 * initie le générateur et le lance
	 * @param project
	 * @param packageableElement
	 */
	public abstract void initiateAndGenerate(IProject project, PackageableElement packageableElement);

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

	/**
	 * récupère le projet
	 * @return
	 */
	private IProject getCurrentProject() {	
		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		URI uri = selectedEObject.eResource().getURI();
		IProject project = root.getProject(uri.segment(1));
		return project;
	}
	
	/**
	 * lance la génération
	 * @param packageableElement
	 */
	public void generate(PackageableElement packageableElement) {
		console.out.println("generate code Handler : generate()");
		creator.createPackageableElement(packageableElement, null, true);
	}
}
