package fr.gouv.diplomatie.papyrus.codegen.core.test;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.internal.resource.UMLResourceFactoryImpl;
import org.eclipse.uml2.uml.resource.UMLResource;
import org.eclipse.uml2.uml.resource.XMI212UMLResource;
import org.eclipse.uml2.uml.resource.XMI242UMLResource;
import org.eclipse.uml2.uml.resource.XMI2UMLResource;

import java.util.Map;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.common.util.WrappedException;
import org.eclipse.uml2.uml.*;
import org.eclipse.uml2.uml.resources.util.UMLResourcesUtil;

/**
 * Classe contenant des méthodes nécessaires à l'écriture des tests unitaires
 *
 */
public class TestUtils {
	
	 private static final ResourceSet RESOURCE_SET = new ResourceSetImpl();
	
	/**
	 * créer un model name
	 * @param name
	 * @return
	 */
	public static Model createModel(String name) {
        Model model = UMLFactory.eINSTANCE.createModel();
        model.setName(name);
        return model;
    }
	
	/**
	 * créer un profil name
	 * @param name
	 * @return
	 */
	public static Profile createProfile(String name) {
		Profile profile = UMLFactory.eINSTANCE.createProfile();
		profile.setName(name);
        return profile;
	}
	
	/**
	 * créer un stereotype name dans le profile profile
	 * @param profile
	 * @param name
	 * @param isAbstract
	 * @return
	 */
	public static Stereotype createStereotype(Profile profile, String name, boolean isAbstract){
		Stereotype stereotype = profile.createOwnedStereotype(name, isAbstract);
		return stereotype;
	}
	
	/**
	 * créer un package name dans le package nestingPackage
	 * @param nestingPackage
	 * @param name
	 * @return
	 */
	public static Package createPackage(Package nestingPackage, String name) {
    	Package package_ = nestingPackage.createNestedPackage(name);
    	return package_;
    }
	
	/**
	 * créer une classe name dans le package package_
	 * @param package_
	 * @param name
	 * @param isAbstract
	 * @return
	 */
	public static Class createClass(Package package_, String name, boolean isAbstract) {
		Class class_ = package_.createOwnedClass(name, isAbstract);
		return class_;
	}
	
	/**
	 * créer une enumération name dans le package package_
	 * @param package_
	 * @param name
	 * @return
	 */
    protected static Enumeration createEnumeration(Package package_, String name) {
    	Enumeration enumeration = (Enumeration) package_.createOwnedEnumeration(name);
    	return enumeration;
    }
	
	/**
	 * créér un model modelName contenant un package packageName
	 * @param modelName
	 * @param packageName
	 * @return
	 */
	public static Package getpackage(String modelName, String packageName) {
		Model model = createModel(modelName);
		Package pkg = model.createNestedPackage(packageName);
		return pkg;
	}
	
	/**
	 * crééer un model modelName contenant un package packageName
	 * contenant une classe name
	 * @param modelName
	 * @param packageName
	 * @param name
	 * @param isAbstract
	 * @return
	 */
	public static Class getClass(String modelName, String packageName,  String name, boolean isAbstract) {
		Package pkg = getpackage(modelName, packageName);
		Class class_= createClass(pkg, name, isAbstract);
		return class_;
	}
	
	/**
	 * créer un profile profileName qui contient un stereotype stereoName
	 * @param profileName
	 * @param stereoName
	 * @param isAbstract
	 * @return
	 */
	public static Stereotype getStereotype(String profileName, String stereoName, boolean isAbstract) {
		Profile profile = createProfile(profileName);
		Stereotype stereotype = createStereotype(profile, stereoName, isAbstract);
		return stereotype;
	}
	
	/**
	 * créer une extension entre le stéréotype stereotype et la classe metaclass
	 * @param metaclass
	 * @param stereotype
	 * @param required indique si la nouvelle extension est requise
	 * @return
	 */
	public static Extension createExtension(Class metaclass, Stereotype stereotype,boolean required) {
		Extension extension = stereotype.createExtension(metaclass, required);
		return extension;
	}
	
	/**
	 * créer une généralisation entre les deux classes
	 * @param specificClassifier
	 * @param generalClassifier
	 * @return
	 */
	public static Generalization createGeneralization(Classifier specificClassifier, Classifier generalClassifier) {
        Generalization generalization = specificClassifier.createGeneralization(generalClassifier);
        return generalization;
    }
	
	/**
	 * renvoie une référence vers une metaclasse du modèle UML
	 * @param profile
	 * @param name
	 * @return
	 */
    public static Class referenceMetaclass(Profile profile, String name) {
        Package _package = load(URI.createURI(UMLResource.UML_METAMODEL_URI));
        Model umlMetamodel = (Model) _package;
        _package.eResource().setURI(URI.createURI(XMI2UMLResource.UML_METAMODEL_2_4_1_URI));
        Class metaclass = (Class) umlMetamodel.getOwnedType(name);
        profile.createMetaclassReference(metaclass);

        return metaclass;
    }
	 
    /**
     * charge le modèle UML
     * @param uri
     * @return
     */
    protected static Package load(URI uri) {
        Package package_ = null;
        Map<String, Object> extensionToFactoryMap = RESOURCE_SET.getResourceFactoryRegistry().getExtensionToFactoryMap();
        extensionToFactoryMap.put(XMI212UMLResource.PROFILE_FILE_EXTENSION, new UMLResourceFactoryImpl());
        UMLPackage.eINSTANCE.setNsURI(XMI242UMLResource.UML_METAMODEL_2_4_1_NS_URI);
        UMLResourcesUtil.init(RESOURCE_SET);
        try {
            Resource resource = RESOURCE_SET.getResource(uri, true);
            package_ = (Package) EcoreUtil.getObjectByType(resource.getContents(), UMLPackage.Literals.PACKAGE);
        } catch (WrappedException we) {
            
            System.exit(1);
        } 
        return package_;
    }
    
    
    /**
     * créer dans la classe class_ un attribut name de type type avec les limites définies
     * @param class_
     * @param name
     * @param type type de l'attribut
     * @param lowerBound
     * @param upperBound
     * @return
     */
    public static Property createAttribute(org.eclipse.uml2.uml.Class class_,
            String name, Type type, int lowerBound, int upperBound) {
    	Property attribute = class_.createOwnedAttribute(name, type, lowerBound, upperBound);
    	return attribute;
    }

    /**
     * Importe un type primitif du modèle UML
     * @param package_
     * @param name
     * @return
     */
    public static PrimitiveType importPrimitiveType(
            org.eclipse.uml2.uml.Package package_, String name) {
    	Model umlLibrary = (Model) load(URI.createURI(UMLResource.UML_PRIMITIVE_TYPES_LIBRARY_URI));
    	PrimitiveType primitiveType = (PrimitiveType) umlLibrary.getOwnedType(name);
    	package_.createElementImport(primitiveType);
    	return primitiveType;
    }
    
    /**
     * modifie la valeur d'une propriété property d'un stéréotype stereotype sur l'element namedElement
     * @param namedElement
     * @param stereotype
     * @param property
     * @param value
     */
    public static void setStereotypePropertyValue(NamedElement namedElement,
            Stereotype stereotype, Property property, Object value) {
    	namedElement.setValue(stereotype, property.getName(), value);
    }
    
    /**
     * crééer une classe d'association entre les deux classes
     * @param class_
     * @param class2
     * @param attrNameInFirstClass
     * @param attrNameInSecondClass
     * @return
     */
    public static AssociationClass createAssociationClass(Class class_,
    		Class class2, String attrNameInFirstClass,String attrNameInSecondClass, String name ) {
    	AssociationClass association = UMLFactory.eINSTANCE
                .createAssociationClass();
		
		Property att1 = TestUtils.createAttribute(class_, attrNameInFirstClass, class2, 0, -1);
		Property att2 = TestUtils.createAttribute(class2, attrNameInSecondClass, class_, 0, -1);
		att1.setAssociation(association);
		att2.setAssociation(association);
		association.getOwnedEnds().add(att1);
		association.getOwnedEnds().add(att2);
		class_.getNearestPackage().getPackagedElements()
        .add(association);
		association.setName(name);
		return association;
    }
    
    /**
     * crééer une classe d'association entre les deux classes
     * @param class_
     * @param class2
     * @param attrNameInFirstClass
     * @param attrNameInSecondClass
     * @return
     */
    public static AssociationReturn createAssociationClassGetAll(Class class_,
    		Class class2, String attrNameInFirstClass,String attrNameInSecondClass, String name ) {
    	AssociationClass association = UMLFactory.eINSTANCE
                .createAssociationClass();
		
		Property att1 = TestUtils.createAttribute(class_, attrNameInFirstClass, class2, 0, -1);
		Property att2 = TestUtils.createAttribute(class2, attrNameInSecondClass, class_, 0, -1);
		att1.setAssociation(association);
		att2.setAssociation(association);
		association.getOwnedEnds().add(att1);
		association.getOwnedEnds().add(att2);
		class_.getNearestPackage().getPackagedElements()
        .add(association);
		association.setName(name);
		AssociationReturn retour = new AssociationReturn();
		retour.association = association;
		retour.attributeClassUn = att1;
		retour.attributeClassDeux = att2;
		return retour;
    }

}

