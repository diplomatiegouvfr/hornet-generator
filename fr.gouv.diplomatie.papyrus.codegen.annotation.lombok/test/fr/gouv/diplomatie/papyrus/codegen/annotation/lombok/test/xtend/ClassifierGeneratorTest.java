package fr.gouv.diplomatie.papyrus.codegen.annotation.lombok.test.xtend;

import static org.junit.Assert.*;

import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.Interface;
import org.eclipse.uml2.uml.AssociationClass;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Property;
import org.junit.Test;

import fr.gouv.diplomatie.papyrus.codegen.annotation.lombok.test.testutils.HornetModelLombok;
import fr.gouv.diplomatie.papyrus.codegen.annotation.lombok.xtend.ClassifierGenerator;
import fr.gouv.diplomatie.papyrus.codegen.core.test.TestUtils;

public class ClassifierGeneratorTest {
	
	@Test
	public void testClass() {
		ClassifierGenerator gene = new ClassifierGenerator();
		assertEquals(ClassifierGenerator.class, gene.getClass());
	}

	@Test
	public void testGenerateImports() {
		HornetModelLombok hmodel = HornetModelLombok.initModel();
		Classifier class_ = TestUtils.createClass(hmodel.pckage, "class", false);
		
		String expect = "import lombok.AllArgsConstructor;\n" + 
				"import lombok.EqualsAndHashCode;\n" + 
				"import lombok.Getter;\n" + 
				"import lombok.NoArgsConstructor;\n" + 
				"import lombok.Setter;\n" + 
				"import lombok.ToString;\n";
		assertEquals(expect, ClassifierGenerator.generateImports(class_).toString());
	}

	@Test
	public void testNoAnnotationGenerateAnnotations() {
		HornetModelLombok hmodel = HornetModelLombok.initModel();
		Classifier class_ = TestUtils.createClass(hmodel.pckage, "class", false);
		assertEquals("", ClassifierGenerator.generateAnnotations(class_).toString());
	}
	
	@Test
	public void testGenerateAnnotations() {
		HornetModelLombok hmodel = HornetModelLombok.initModel();
		Classifier class_ = TestUtils.createClass(hmodel.pckage, "class", false);
		class_.applyStereotype(hmodel.entity);
		TestUtils.setStereotypePropertyValue(class_, hmodel.entity, hmodel.entityLombokAllArgsCons, true);
		TestUtils.setStereotypePropertyValue(class_, hmodel.entity, hmodel.entityLombokEqAndHashc, true);
		TestUtils.setStereotypePropertyValue(class_, hmodel.entity, hmodel.entityLombokGetter, true);
		TestUtils.setStereotypePropertyValue(class_, hmodel.entity, hmodel.entityLombokNoArgsCons, true);
		TestUtils.setStereotypePropertyValue(class_, hmodel.entity, hmodel.entityLombokSetter, true);
		TestUtils.setStereotypePropertyValue(class_, hmodel.entity, hmodel.entityLombokToString, true);

		String expect = "@Getter\n" + 
				"@Setter\n" + 
				"@NoArgsConstructor\n" + 
				"@AllArgsConstructor\n" + 
				"@ToString\n" + 
				"@EqualsAndHashCode\n";
		assertEquals(expect, ClassifierGenerator.generateAnnotations(class_).toString());
	}
	
	@Test
	public void testToStringGenerateAnnotations() {
		HornetModelLombok hmodel = HornetModelLombok.initModel();
		Class class_ = TestUtils.createClass(hmodel.pckage, "class", false);
		class_.applyStereotype(hmodel.entity);
		Property prop = TestUtils.createAttribute(class_, "test", hmodel.stringPT, 0, 1);
		prop.applyStereotype(hmodel.attribute);
		TestUtils.setStereotypePropertyValue(prop, hmodel.attribute, hmodel.attributeLombokDisplayWith, true);
		
		Property prop2 = TestUtils.createAttribute(class_, "test2", hmodel.stringPT, 0, 1);
		prop2.applyStereotype(hmodel.attribute);
		TestUtils.setStereotypePropertyValue(prop2, hmodel.attribute, hmodel.attributeLombokDisplayWith, false);
		
		TestUtils.setStereotypePropertyValue(class_, hmodel.entity, hmodel.entityLombokToString, true);

		String expect = "@ToString(of = {\"test\"\n" + 
				"})\n";
		assertEquals(expect, ClassifierGenerator.generateAnnotations(class_).toString());
	}

	@Test
	public void testGenerateToStringContent() {
		HornetModelLombok hmodel = HornetModelLombok.initModel();
		Class class_ = TestUtils.createClass(hmodel.pckage, "class", false);
		class_.applyStereotype(hmodel.entity);
		
		Class class2_ = TestUtils.createClass(hmodel.pckage, "class2", false);
		class2_.applyStereotype(hmodel.entity);
		
		Property prop = TestUtils.createAttribute(class_, "test", hmodel.stringPT, 0, 1);
		prop.applyStereotype(hmodel.attribute);
		TestUtils.setStereotypePropertyValue(prop, hmodel.attribute, hmodel.attributeLombokDisplayWith, true);
		
		Property prop2 = TestUtils.createAttribute(class_, "test2", hmodel.stringPT, 0, 1);
		prop2.applyStereotype(hmodel.attribute);
		TestUtils.setStereotypePropertyValue(prop2, hmodel.attribute, hmodel.attributeLombokDisplayWith, false);
		
		Interface interf = hmodel.pckage.createOwnedInterface("interf");
		Property interfProp = interf.createOwnedAttribute("att", hmodel.stringPT, 0, 1);
		interfProp.applyStereotype(hmodel.attribute);
		TestUtils.setStereotypePropertyValue(interfProp, hmodel.attribute, hmodel.attributeLombokDisplayWith, true);
		
		AssociationClass asso = TestUtils.createAssociationClass(class_, class2_, "c1", "c2", "asso");
		asso.applyStereotype(hmodel.associationTable);
		TestUtils.setStereotypePropertyValue(asso, hmodel.associationTable, hmodel.associationTableLombokDisplayWith, true);
		
		class_.createInterfaceRealization("real", interf);
		
		String expect = "\"test\",\n" + 
				"	\"att\",\n" + 
				"	\"asso\"";
		assertEquals(expect, ClassifierGenerator.generateToStringContent(class_).toString());
	}
	
	@Test
	public void testNoDirectAttributeGenerateToStringContent() {
		HornetModelLombok hmodel = HornetModelLombok.initModel();
		Class class_ = TestUtils.createClass(hmodel.pckage, "class", false);
		class_.applyStereotype(hmodel.entity);
		
		Class class2_ = TestUtils.createClass(hmodel.pckage, "class2", false);
		class2_.applyStereotype(hmodel.entity);
	
		Interface interf = hmodel.pckage.createOwnedInterface("interf");
		Property interfProp = interf.createOwnedAttribute("att", hmodel.stringPT, 0, 1);
		interfProp.applyStereotype(hmodel.attribute);
		TestUtils.setStereotypePropertyValue(interfProp, hmodel.attribute, hmodel.attributeLombokDisplayWith, false);
		Property prop2 = interf.createOwnedAttribute("att2", hmodel.stringPT, 0, 1);
		prop2.applyStereotype(hmodel.attribute);
		TestUtils.setStereotypePropertyValue(prop2, hmodel.attribute, hmodel.attributeLombokDisplayWith, true);
		
		AssociationClass asso = TestUtils.createAssociationClass(class_, class2_, "c1", "c2", "asso");
		asso.applyStereotype(hmodel.associationTable);
		TestUtils.setStereotypePropertyValue(asso, hmodel.associationTable, hmodel.associationTableLombokDisplayWith, false);
		
		class_.createInterfaceRealization("real", interf);
		
		String expect = "\"att2\"";
		assertEquals(expect, ClassifierGenerator.generateToStringContent(class_).toString());
	}
	
	@Test
	public void testDirectAttributeGenerateToStringContent() {
		HornetModelLombok hmodel = HornetModelLombok.initModel();
		Class class_ = TestUtils.createClass(hmodel.pckage, "class", false);
	
		Property prop = TestUtils.createAttribute(class_, "test", hmodel.stringPT, 0, 1);
		prop.applyStereotype(hmodel.attribute);
		TestUtils.setStereotypePropertyValue(prop, hmodel.attribute, hmodel.attributeLombokDisplayWith, true);
		
		Property prop2 = TestUtils.createAttribute(class_, "test2", hmodel.stringPT, 0, 1);
		prop2.applyStereotype(hmodel.attribute);
		TestUtils.setStereotypePropertyValue(prop2, hmodel.attribute, hmodel.attributeLombokDisplayWith, false);
		
		Property prop3 = TestUtils.createAttribute(class_, "test3", hmodel.stringPT, 0, 1);
		prop3.applyStereotype(hmodel.attribute);
		TestUtils.setStereotypePropertyValue(prop3, hmodel.attribute, hmodel.attributeLombokDisplayWith, true);
		
		String expect = "\"test\",\n" + 
				"	\"test3\"";
		assertEquals(expect, ClassifierGenerator.generateToStringContent(class_).toString());
	}
	
	@Test
	public void testNODirectAndInterfAttrGenerateToStringContent() {
		HornetModelLombok hmodel = HornetModelLombok.initModel();
		Class class_ = TestUtils.createClass(hmodel.pckage, "class", false);
		class_.applyStereotype(hmodel.entity);
		
		Class class2_ = TestUtils.createClass(hmodel.pckage, "class2", false);
		class2_.applyStereotype(hmodel.entity);

		TestUtils.createAssociationClass(class_, class2_, "c1", "c2", "asso");
		
		String expect = "\"asso\"";
		assertEquals(expect, ClassifierGenerator.generateToStringContent(class_).toString());
	}

	@Test
	public void testHasAttributesNotInToString() {
		HornetModelLombok hmodel = HornetModelLombok.initModel();
		Class class_ = TestUtils.createClass(hmodel.pckage, "class", false);
		class_.applyStereotype(hmodel.entity);
		
		Class class2_ = TestUtils.createClass(hmodel.pckage, "class2", false);
		class2_.applyStereotype(hmodel.entity);
		
		Property prop = TestUtils.createAttribute(class_, "test", hmodel.stringPT, 0, 1);
		prop.applyStereotype(hmodel.attribute);
		TestUtils.setStereotypePropertyValue(prop, hmodel.attribute, hmodel.attributeLombokDisplayWith, true);
		
		Property prop2 = TestUtils.createAttribute(class_, "test2", hmodel.stringPT, 0, 1);
		prop2.applyStereotype(hmodel.attribute);
		TestUtils.setStereotypePropertyValue(prop2, hmodel.attribute, hmodel.attributeLombokDisplayWith, false);
		
		Interface interf = hmodel.pckage.createOwnedInterface("interf");
		Property interfProp = interf.createOwnedAttribute("att", hmodel.stringPT, 0, 1);
		interfProp.applyStereotype(hmodel.attribute);
		TestUtils.setStereotypePropertyValue(interfProp, hmodel.attribute, hmodel.attributeLombokDisplayWith, true);
		
		TestUtils.createAssociationClass(class_, class2_, "c1", "c2", "asso");
		
		class_.createInterfaceRealization("real", interf);
		
		assertEquals(true, ClassifierGenerator.hasAttributesNotInToString(class_));
	}
	
	@Test
	public void testInterfHasAttributesNotInToString() {
		HornetModelLombok hmodel = HornetModelLombok.initModel();
		Class class_ = TestUtils.createClass(hmodel.pckage, "class", false);
		class_.applyStereotype(hmodel.entity);
		
		Class class2_ = TestUtils.createClass(hmodel.pckage, "class2", false);
		class2_.applyStereotype(hmodel.entity);
		
		Property prop = TestUtils.createAttribute(class_, "test", hmodel.stringPT, 0, 1);
		prop.applyStereotype(hmodel.attribute);
		TestUtils.setStereotypePropertyValue(prop, hmodel.attribute, hmodel.attributeLombokDisplayWith, true);
		
		Property prop2 = TestUtils.createAttribute(class_, "test2", hmodel.stringPT, 0, 1);
		prop2.applyStereotype(hmodel.attribute);
		TestUtils.setStereotypePropertyValue(prop2, hmodel.attribute, hmodel.attributeLombokDisplayWith, true);
		
		Interface interf = hmodel.pckage.createOwnedInterface("interf");
		Property interfProp = interf.createOwnedAttribute("att", hmodel.stringPT, 0, 1);
		interfProp.applyStereotype(hmodel.attribute);
		TestUtils.setStereotypePropertyValue(interfProp, hmodel.attribute, hmodel.attributeLombokDisplayWith, false);
		
		TestUtils.createAssociationClass(class_, class2_, "c1", "c2", "asso");
		
		class_.createInterfaceRealization("real", interf);
		
		assertEquals(true, ClassifierGenerator.hasAttributesNotInToString(class_));
	}
	
	@Test
	public void testAssofHasAttributesNotInToString() {
		HornetModelLombok hmodel = HornetModelLombok.initModel();
		Class class_ = TestUtils.createClass(hmodel.pckage, "class", false);
		class_.applyStereotype(hmodel.entity);
		
		Class class2_ = TestUtils.createClass(hmodel.pckage, "class2", false);
		class2_.applyStereotype(hmodel.entity);
		
		Property prop = TestUtils.createAttribute(class_, "test", hmodel.stringPT, 0, 1);
		prop.applyStereotype(hmodel.attribute);
		TestUtils.setStereotypePropertyValue(prop, hmodel.attribute, hmodel.attributeLombokDisplayWith, true);
		
		Property prop2 = TestUtils.createAttribute(class_, "test2", hmodel.stringPT, 0, 1);
		prop2.applyStereotype(hmodel.attribute);
		TestUtils.setStereotypePropertyValue(prop2, hmodel.attribute, hmodel.attributeLombokDisplayWith, true);
		
		Interface interf = hmodel.pckage.createOwnedInterface("interf");
		Property interfProp = interf.createOwnedAttribute("att", hmodel.stringPT, 0, 1);
		interfProp.applyStereotype(hmodel.attribute);
		TestUtils.setStereotypePropertyValue(interfProp, hmodel.attribute, hmodel.attributeLombokDisplayWith, true);
		
		AssociationClass asso = TestUtils.createAssociationClass(class_, class2_, "c1", "c2", "asso");
		asso.applyStereotype(hmodel.associationTable);
		TestUtils.setStereotypePropertyValue(asso, hmodel.associationTable, hmodel.associationTableLombokDisplayWith, false);

		class_.createInterfaceRealization("real", interf);
		
		assertEquals(true, ClassifierGenerator.hasAttributesNotInToString(class_));
	}

}
