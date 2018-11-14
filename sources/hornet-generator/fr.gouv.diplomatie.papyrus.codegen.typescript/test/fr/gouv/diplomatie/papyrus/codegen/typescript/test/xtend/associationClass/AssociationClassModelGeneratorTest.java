package fr.gouv.diplomatie.papyrus.codegen.typescript.test.xtend.associationClass;

import static org.junit.Assert.assertEquals;

import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Property;
import org.junit.Test;

import fr.gouv.diplomatie.papyrus.codegen.core.test.TestUtils;
import fr.gouv.diplomatie.papyrus.codegen.typescript.test.testutils.HornetModelTypescript;
import fr.gouv.diplomatie.papyrus.codegen.typescript.xtend.associationClass.AssociationClassModelGenerator;

public class AssociationClassModelGeneratorTest {

	@Test
	public void testGenerateEntityMemberAttribute() {
		HornetModelTypescript hmodel = HornetModelTypescript.initModel();
				
		Class class_ = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		Class class2_ = TestUtils.createClass(hmodel.pckage, "maClasse2", false);
		class2_.applyStereotype(hmodel.entity);
		
		Property att = TestUtils.createAttribute(class_, "att", class2_, 0, 1);
		Property id = TestUtils.createAttribute(class2_, "id", hmodel.integerPT, 0, 1);
		
		String expect = "idAtt: {\n" + 
				"    type: sequelize.STRING,\n" + 
				"    field: \"id_att\",\n" + 
				"    allowNull: true,\n" + 
				"    primaryKey: true,\n" + 
				"    references: {\n" + 
				"        model: \"maClasse2Model\",\n" + 
				"        key: \"id\",\n" + 
				"    },\n" + 
				"},\n";
		assertEquals(expect, AssociationClassModelGenerator.generateEntityMemberAttribute(att, id, class_).toString());
	}

	@Test
	public void testGenerateIdAttributeTypeLength() {
		HornetModelTypescript hmodel = HornetModelTypescript.initModel();
		
		Class class_ = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		
		Property prop = TestUtils.createAttribute(class_, "test", hmodel.integerPT, 0, 1);
		prop.applyStereotype(hmodel.attribute);
		
		assertEquals("", AssociationClassModelGenerator.generateIdAttributeTypeLength(prop).toString());
	
		TestUtils.setStereotypePropertyValue(prop, hmodel.attribute, hmodel.attributeHasLength, true);
		assertEquals("", AssociationClassModelGenerator.generateIdAttributeTypeLength(prop).toString());

		TestUtils.setStereotypePropertyValue(prop, hmodel.attribute, hmodel.attributeLength, 10);
		assertEquals("(10)", AssociationClassModelGenerator.generateIdAttributeTypeLength(prop).toString());
		
	}

}
