package fr.gouv.diplomatie.papyrus.codegen.ui.handlers;

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
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.console.ConsolePlugin;
import org.eclipse.ui.console.IConsole;
import org.eclipse.ui.console.IConsoleManager;
import org.eclipse.ui.console.MessageConsole;
import org.eclipse.ui.console.MessageConsoleStream;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.PackageableElement;

public abstract class HornetCodeHandler extends CmdHandler {
	
	protected ModelElementsCreator creator;
	protected String message = "= executing Generate Code Handler";
	MessageConsole myConsole = findConsole("Hornet Papyrus Générateur");
	MessageConsoleStream out = myConsole.newMessageStream();
	MessageConsoleStream err = getErrorMessageStream();

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		out.println(this.message);
		
		if(selectedEObject instanceof PackageableElement) {
			out.println("= selected Object is a PackageableElement");
			PackageableElement packageableElement = (PackageableElement) selectedEObject;
			
			IProject project = getCurrentProject();
			if(project != null) {
				try {
					initiateAndGenerate(project, packageableElement);
				}catch(Exception e) {
					StringWriter errors = new StringWriter();
					e.printStackTrace(new PrintWriter(errors));
					err.println(errors.toString());
				}
			}
		}
		return null;
	}
	
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

	private IProject getCurrentProject() {	
		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		URI uri = selectedEObject.eResource().getURI();
		IProject project = root.getProject(uri.segment(1));
		return project;
	}
	
	public void generate(PackageableElement packageableElement) {
		out.println("generate code Handler : generate()");
		creator.createPackageableElement(packageableElement, null, true);
		
	}
	
	private MessageConsole findConsole(String name) {
	      ConsolePlugin plugin = ConsolePlugin.getDefault();
	      IConsoleManager conMan = plugin.getConsoleManager();
	      IConsole[] existing = conMan.getConsoles();
	      for (int i = 0; i < existing.length; i++)
	         if (name.equals(existing[i].getName()))
	            return (MessageConsole) existing[i];
	      //no console found, so create a new one
	      MessageConsole myConsole = new MessageConsole(name, null);
	      conMan.addConsoles(new IConsole[]{myConsole});
	      return myConsole;
	}
	
	private MessageConsoleStream getErrorMessageStream() {
		MessageConsoleStream stream = myConsole.newMessageStream();
		stream.setColor(Display.getDefault().getSystemColor(SWT.COLOR_RED));
		return stream;
	}
}
