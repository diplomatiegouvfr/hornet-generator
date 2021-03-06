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
 * fr.gouv.diplomatie.papyrus.codegen.typescript - Générateur de code typescript pour 
 * des applications Hornet JS
 *
 * @author MEAE - Ministère de l'Europe et des Affaires étrangères
 * @version v1.3.2
 * @license CECILL-2.1
 */
package fr.gouv.diplomatie.papyrus.codegen.typescript.transformations;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.uml2.uml.AssociationClass;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.Interface;
import org.eclipse.uml2.uml.PackageableElement;

import fr.gouv.diplomatie.papyrus.codegen.core.generators.HornetModelElementsCreator;
import fr.gouv.diplomatie.papyrus.codegen.core.utils.ClassifierUtils;
import fr.gouv.diplomatie.papyrus.codegen.core.utils.Utils;
import fr.gouv.diplomatie.papyrus.codegen.typescript.generators.AssociationClassGenerator;
import fr.gouv.diplomatie.papyrus.codegen.typescript.generators.ClassifierGenerator;
import fr.gouv.diplomatie.papyrus.codegen.typescript.generators.NomenclatureGenerator;
import fr.gouv.diplomatie.papyrus.codegen.typescript.generators.PackageGenerator;

import org.eclipse.uml2.uml.Package;

public class ProjectModelElementsCreator extends HornetModelElementsCreator {
	
	public ProjectModelElementsCreator(IProject project, Boolean outGenerationDir) {
		super(project, outGenerationDir);
	}

	@Override
	protected void createPackageableElementFile(PackageableElement packageableElement, IProgressMonitor progressMonitor) {
		
		if(packageableElement instanceof Package) {
			generatePackage((Package) packageableElement);
		}else if(packageableElement instanceof AssociationClass) {
			generateAssociationClass((AssociationClass) packageableElement);
		}else if(Utils.isNomenclature(packageableElement)) {
			generateNomenclature((Classifier)packageableElement);
		}else if(Utils.isEntity(packageableElement)) {
			generateClass((Classifier) packageableElement);
		}else if(Utils.isValueObject(packageableElement)) {
			generateValueObject((Classifier)packageableElement);
		}else if(packageableElement instanceof Interface) {
			generateInterface((Interface) packageableElement);
		}
	}
	
	/**
	 * Génère les fichiers liés au package
	 * @param pakkage
	 */
	protected void generatePackage(Package pakkage) {
		PackageGenerator.generateModelDao(pakkage, fileSystemAccess);
	}
	
	/**
	 * Génère les fichiers liés à la classe entity
	 * @param clazz
	 */
	protected void generateClass(Classifier clazz) {
		if(ClassifierUtils.canBeGenerated(clazz)) {
			ClassifierGenerator.generateModel(clazz, fileSystemAccess);
			ClassifierGenerator.generateAttributesInterface(clazz, fileSystemAccess);
			ClassifierGenerator.generateMetierClass(clazz, fileSystemAccess);
			ClassifierGenerator.generateDto(clazz, fileSystemAccess);
		}else {
			Utils.console.warning.println("La classe "+ clazz.getName() + " ne sera pas générée car elle possède une propriété generated à false");
		}
	}
	
	/**
	 * Génère les fichiers liés à la classe valueObject
	 * @param clazz
	 */
	protected void generateValueObject(Classifier clazz) {
		if(ClassifierUtils.canBeGenerated(clazz)) {
			ClassifierGenerator.generateValueObjectMetierClass(clazz, fileSystemAccess);
			ClassifierGenerator.generateAttributesInterface(clazz, fileSystemAccess);
			ClassifierGenerator.generateDto(clazz, fileSystemAccess);
		}else {
			Utils.console.warning.println("La classe "+ clazz.getName() + " ne sera pas générée car elle possède une propriété generated à false");
		}
	}
	
	/**
	 * Génère les fichiers liés a la classe d'association
	 * @param clazz
	 */
	protected void generateAssociationClass(AssociationClass clazz) {
		AssociationClassGenerator.generateModel(clazz, fileSystemAccess);
		AssociationClassGenerator.generateAttributesInterface(clazz, fileSystemAccess);
		AssociationClassGenerator.generateMetierClass(clazz, fileSystemAccess);
		AssociationClassGenerator.generateDto(clazz, fileSystemAccess);
	}
	

	/**
	 * Génère les fichiers liés a la classe d'association
	 * @param clazz
	 */
	protected void generateInterface(Interface clazz) {
		if(ClassifierUtils.canBeGenerated(clazz)) {
			ClassifierGenerator.generateAttributesInterface(clazz, fileSystemAccess);
			ClassifierGenerator.generateMetierClass(clazz, fileSystemAccess);
		}else {
			Utils.console.warning.println("La classe "+ clazz.getName() + " ne sera pas générée car elle possède une propriété generated à false");
		}
	}
	
	/**
	 * Génère les fichiers liés à la nomenclature
	 * @param clazz
	 */
	protected void generateNomenclature(Classifier clazz) {
		if(ClassifierUtils.canBeGenerated(clazz)) {
			NomenclatureGenerator.generateEnumClass(clazz, fileSystemAccess);
			NomenclatureGenerator.generateEnumModel(clazz, fileSystemAccess);
			NomenclatureGenerator.generateEnumDto(clazz, fileSystemAccess);
			NomenclatureGenerator.generateEnumAttributesInterface(clazz, fileSystemAccess);
		}else {
			Utils.console.warning.println("La classe "+ clazz.getName() + " ne sera pas générée car elle possède une propriété generated à false");
		}
	}
	
}
