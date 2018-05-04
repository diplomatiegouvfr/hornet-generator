package fr.gouv.diplomatie.papyrus.codegen.typescript.test.xtend.associationClass;

import static org.junit.Assert.*;

import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.Profile;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Stereotype;
import org.eclipse.uml2.uml.Type;
import org.eclipse.uml2.uml.UMLPackage;
import org.junit.Test;

import fr.gouv.diplomatie.papyrus.codegen.core.test.TestUtils;
import fr.gouv.diplomatie.papyrus.codegen.core.utils.Utils;
import fr.gouv.diplomatie.papyrus.codegen.typescript.xtend.associationClass.AssociationClassModelGenerator;

public class AssociationClassModelGeneratorTest {

	@Test
	public void testGenerateEntityMemberAttribute() {
		Profile profile = TestUtils.createProfile("profile");
		Model model = TestUtils.createModel("model");
		
		Stereotype stereotype = TestUtils.createStereotype(profile, Utils.MODEL_ENTITY, false);
		Class metaclass = TestUtils.referenceMetaclass(profile, UMLPackage.Literals.CLASS.getName());
        TestUtils.createExtension(metaclass, stereotype, false);
		
		profile.define();
		model.applyProfile(profile);
		
		Type integerPT = TestUtils.importPrimitiveType(model, "Integer");
				
		Class class_ = TestUtils.createClass(model, "maClasse", false);
		Class class2_ = TestUtils.createClass(model, "maClasse2", false);
		class2_.applyStereotype(stereotype);
		
		Property att = TestUtils.createAttribute(class_, "att", class2_, 0, 1);
		Property id = TestUtils.createAttribute(class2_, "id", integerPT, 0, 1);
		
		String expect = "idAtt: {\n" + 
				"	type: Sequelize.STRING,\n" + 
				"	field: \"ID_ATT\",\n" + 
				"	allowNull: true,\n" + 
				"	primaryKey: true,\n" + 
				"	references: {\n" + 
				"		model: \"maClasse2Model\",\n" + 
				"		key: \"id\"\n" + 
				"	}\n" + 
				"}";
		assertEquals(expect, AssociationClassModelGenerator.generateEntityMemberAttribute(att, id, class_).toString());
	}

	@Test
	public void testGenerateIdAttributeTypeLength() {
		Profile profile = TestUtils.createProfile("profile");
		Model model = TestUtils.createModel("model");
		Stereotype stereotype = TestUtils.createStereotype(profile, Utils.MODEL_ATTRIBUTE, false);
		
		Type integerPT = TestUtils.importPrimitiveType(model, "Integer");
		Property attribute = TestUtils.createAttribute(stereotype, Utils.MODEL_ATTRIBUTE_LENGTH, integerPT, 0, 1);
		
		Type booleanPT = TestUtils.importPrimitiveType(model, "Boolean");
		Property hasLength = TestUtils.createAttribute(stereotype, "hasLength", booleanPT, 0, 1);
	
		Class packageMetaclass = TestUtils.referenceMetaclass(profile, UMLPackage.Literals.PROPERTY.getName());
        TestUtils.createExtension(packageMetaclass, stereotype, false);
        
		profile.define();
		model.applyProfile(profile);
		
		Class class_ = TestUtils.createClass(model, "maClasse", false);
		
		Property prop = TestUtils.createAttribute(class_, "test", integerPT, 0, 1);
		prop.applyStereotype(stereotype);
		
		assertEquals("", AssociationClassModelGenerator.generateIdAttributeTypeLength(prop).toString());
	
		TestUtils.setStereotypePropertyValue(prop, stereotype, hasLength, true);
		assertEquals("", AssociationClassModelGenerator.generateIdAttributeTypeLength(prop).toString());

		TestUtils.setStereotypePropertyValue(prop, stereotype, attribute, 10);
		assertEquals("(10)", AssociationClassModelGenerator.generateIdAttributeTypeLength(prop).toString());
		
	}

}
