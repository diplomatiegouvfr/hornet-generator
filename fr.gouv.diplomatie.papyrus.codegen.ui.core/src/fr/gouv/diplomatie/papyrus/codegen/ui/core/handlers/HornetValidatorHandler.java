/**
 * Copyright ou © ou Copr. Ministère de l'Europe et des Affaires étrangères (2017)
 * <p/>
 * pole-architecture.dga-dsi-psi@diplomatie.gouv.fr
 * <p/>
 * Ce logiciel est un programme informatique servant à faciliter la création
 * d'applications Web conformément aux référentiels généraux français : RGI, RGS et RGAA
 * <p/>
 * Ce logiciel est régi par la licence CeCILL soumise au droit français et
 * respectant les principes de diffusion des logiciels libres. Vous pouvez
 * utiliser, modifier et/ou redistribuer ce programme sous les conditions
 * de la licence CeCILL telle que diffusée par le CEA, le CNRS et l'INRIA
 * sur le site "http://www.cecill.info".
 * <p/>
 * En contrepartie de l'accessibilité au code source et des droits de copie,
 * de modification et de redistribution accordés par cette licence, il n'est
 * offert aux utilisateurs qu'une garantie limitée.  Pour les mêmes raisons,
 * seule une responsabilité restreinte pèse sur l'auteur du programme,  le
 * titulaire des droits patrimoniaux et les concédants successifs.
 * <p/>
 * A cet égard  l'attention de l'utilisateur est attirée sur les risques
 * associés au chargement,  à l'utilisation,  à la modification et/ou au
 * développement et à la reproduction du logiciel par l'utilisateur étant
 * donné sa spécificité de logiciel libre, qui peut le rendre complexe à
 * manipuler et qui le réserve donc à des développeurs et des professionnels
 * avertis possédant  des  connaissances  informatiques approfondies.  Les
 * utilisateurs sont donc invités à charger  et  tester  l'adéquation  du
 * logiciel à leurs besoins dans des conditions permettant d'assurer la
 * sécurité de leurs systèmes et ou de leurs données et, plus généralement,
 * à l'utiliser et l'exploiter dans les mêmes conditions de sécurité.
 * <p/>
 * Le fait que vous puissiez accéder à cet en-tête signifie que vous avez
 * pris connaissance de la licence CeCILL, et que vous en avez accepté les
 * termes.
 * <p/>
 * <p/>
 * Copyright or © or Copr. Ministry for Europe and Foreign Affairs (2017)
 * <p/>
 * pole-architecture.dga-dsi-psi@diplomatie.gouv.fr
 * <p/>
 * This software is a computer program whose purpose is to facilitate creation of
 * web application in accordance with french general repositories : RGI, RGS and RGAA.
 * <p/>
 * This software is governed by the CeCILL license under French law and
 * abiding by the rules of distribution of free software.  You can  use,
 * modify and/ or redistribute the software under the terms of the CeCILL
 * license as circulated by CEA, CNRS and INRIA at the following URL
 * "http://www.cecill.info".
 * <p/>
 * As a counterpart to the access to the source code and  rights to copy,
 * modify and redistribute granted by the license, users are provided only
 * with a limited warranty  and the software's author,  the holder of the
 * economic rights,  and the successive licensors  have only  limited
 * liability.
 * <p/>
 * In this respect, the user's attention is drawn to the risks associated
 * with loading,  using,  modifying and/or developing or reproducing the
 * software by the user in light of its specific status of free software,
 * that may mean  that it is complicated to manipulate,  and  that  also
 * therefore means  that it is reserved for developers  and  experienced
 * professionals having in-depth computer knowledge. Users are therefore
 * encouraged to load and test the software's suitability as regards their
 * requirements in conditions enabling the security of their systems and/or
 * data to be ensured and,  more generally, to use and operate it in the
 * same conditions as regards security.
 * <p/>
 * The fact that you are presently reading this means that you have had
 * knowledge of the CeCILL license and that you accept its terms.
 *
 */

/**
 * fr.gouv.diplomatie.papyrus.codegen.ui.core - Outils pour l'écriture d'interface papyrus
 * de générateur 
 *
 * @author MEAE - Ministère de l'Europe et des Affaires étrangères
 * @version v1.1.5
 * @license CECILL-2.1
 */
package fr.gouv.diplomatie.papyrus.codegen.ui.core.handlers;

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

/**
 * Classe appelée par le menu de l'ui Papyrus
 * permettant le lancement de la validation du modèle Papyrus
 *
 */
public abstract class HornetValidatorHandler extends CmdHandler {
	
	protected ConsoleUtils console= new ConsoleUtils();
	protected HornetModelValidator validator;
	
	protected String validationSuccessMessage = "Modèle valide";
	protected String validationFailMessage = "Modèle invalide";
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		
		if(selectedEObject instanceof PackageableElement) {
			PackageableElement packageableElement = (PackageableElement) selectedEObject;
			this.initiateValidator();
			ArrayList<String> validationErrors = validator.validate(packageableElement, console);
			ArrayList<String> validationWarnings = validator.warnings;
			for(String warning : validationWarnings) {
				console.warning.println(warning);
			}
			if(validationErrors.isEmpty()) {
				console.success.println(validationSuccessMessage);
			}else {
				console.err.println(validationFailMessage);
				for(String error : validationErrors) {
					console.err.println(error);
				}
			}
		}
		return null;
	}
	
	/**
	 * initialisation du validateur
	 */
	protected abstract void initiateValidator();
	
	/**
	 * {@inheritDoc}
	 */
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
