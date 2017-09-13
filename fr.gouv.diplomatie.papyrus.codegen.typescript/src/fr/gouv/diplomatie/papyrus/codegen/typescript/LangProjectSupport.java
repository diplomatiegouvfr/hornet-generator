package fr.gouv.diplomatie.papyrus.codegen.typescript;

import org.eclipse.core.resources.IProject;
import org.eclipse.papyrus.designer.languages.common.extensionpoints.AbstractSettings;
import org.eclipse.papyrus.designer.languages.common.extensionpoints.ILangProjectSupport;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.Package;

public class LangProjectSupport implements ILangProjectSupport {

	public LangProjectSupport() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public IProject createProject(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IProject createProject(String arg0, Package arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void gatherConfigData(Classifier arg0, AbstractSettings arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public AbstractSettings initialConfigurationData() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setSettings(IProject arg0, AbstractSettings arg1) {
		// TODO Auto-generated method stub
		
	}

}
