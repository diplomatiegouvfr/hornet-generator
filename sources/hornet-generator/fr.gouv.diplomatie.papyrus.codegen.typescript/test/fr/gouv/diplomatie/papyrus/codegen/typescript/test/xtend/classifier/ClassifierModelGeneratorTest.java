package fr.gouv.diplomatie.papyrus.codegen.typescript.test.xtend.classifier;

import static org.junit.Assert.assertEquals;

import org.eclipse.uml2.uml.AggregationKind;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Property;
import org.junit.Test;

import fr.gouv.diplomatie.papyrus.codegen.core.test.TestUtils;
import fr.gouv.diplomatie.papyrus.codegen.typescript.test.testutils.HornetModelTypescript;
import fr.gouv.diplomatie.papyrus.codegen.typescript.xtend.classifier.ClassifierModelGenerator;

public class ClassifierModelGeneratorTest {

	@Test
	public void testGenerateOneToManyAttributePropertyClassifier() {
		HornetModelTypescript hmodel = HornetModelTypescript.initModel();
			
		Class class_ = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		class_.applyStereotype(hmodel.entity);
		Class class2_ = TestUtils.createClass(hmodel.pckage, "maClasse2", false);
		class2_.applyStereotype(hmodel.entity);
		
		TestUtils.createAttribute(class_, "id", hmodel.stringPT, 0, 1).applyStereotype(hmodel.keyAttribute);
		TestUtils.createAttribute(class2_, "id", hmodel.stringPT, 0, 1).applyStereotype(hmodel.keyAttribute);
		
		class_.createAssociation(true, AggregationKind.NONE_LITERAL, "test", 0, 1, class2_, true, AggregationKind.NONE_LITERAL, "test2", 0, -1);
		Property prop = class_.getAttribute("test", class2_);
		
		String expect = "idTest: {\n" + 
				"    type: Sequelize.STRING,\n" + 
				"    field: \"id_test\",\n" + 
				"    references: {\n" + 
				"        model: \"maClasse2Model\",\n" + 
				"        key: \"id\",\n" + 
				"    },\n" + 
				"},\n";
		assertEquals(expect, ClassifierModelGenerator.generateOneToManyAttribute(prop, class_).toString());
	}

	@Test
	public void testGenerateExtendsId() {
		HornetModelTypescript hmodel = HornetModelTypescript.initModel();
		
		Class class_ = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		class_.applyStereotype(hmodel.entity);
		Class class2_ = TestUtils.createClass(hmodel.pckage, "maClasse2", false);
		class2_.applyStereotype(hmodel.entity);
		
		TestUtils.createAttribute(class_, "id", hmodel.stringPT, 0, 1).applyStereotype(hmodel.keyAttribute);
		TestUtils.createAttribute(class2_, "id", hmodel.stringPT, 0, 1).applyStereotype(hmodel.keyAttribute);
		
		TestUtils.createGeneralization(class_, class2_);
		
		String expect = "id: {\n" + 
				"    type: Sequelize.STRING,\n" + 
				"    field: \"id\",\n" + 
				"    allowNull: true,\n" + 
				"    references: {\n" + 
				"        model: \"maClasse2Model\",\n" + 
				"        key: \"id\",\n" + 
				"    },\n" + 
				"},\n";
		assertEquals(expect, ClassifierModelGenerator.generateExtendsId(class_).toString());

	}

	@Test
	public void testGenerateIdAttributeDefinition() {
		HornetModelTypescript hmodel = HornetModelTypescript.initModel();
		
		Class class_ = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		class_.applyStereotype(hmodel.entity);
		
		Property id = TestUtils.createAttribute(class_, "id", hmodel.stringPT, 0, 1);
		id.applyStereotype(hmodel.keyAttribute);
		
		String expect = "id: {\n" + 
				"    type: Sequelize.STRING,\n" + 
				"    field: \"id\",\n" + 
				"    allowNull: true,\n" + 
				"    primaryKey: true,\n" + 
				"},\n";
		assertEquals(expect, ClassifierModelGenerator.generateIdAttributeDefinition(id, "", class_, false).toString());
		
	}

	@Test
	public void testGenerateEnumAttributeDefinition() {
		HornetModelTypescript hmodel = HornetModelTypescript.initModel();
		
		Class class_ = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		class_.applyStereotype(hmodel.entity);
		Class class2_ = TestUtils.createClass(hmodel.pckage, "maClasse2", false);
		class2_.applyStereotype(hmodel.nomenclature);
		
		Property id = TestUtils.createAttribute(class_, "id", hmodel.stringPT, 0, 1);
		id.applyStereotype(hmodel.keyAttribute);
		
		String expect = "id: {\n" + 
				"    type: Sequelize.STRING,\n" + 
				"    field: \"id\",\n" + 
				"    allowNull: true,\n" + 
				"    primaryKey: true,\n" + 
				"},\n";
		assertEquals(expect, ClassifierModelGenerator.generateIdAttributeDefinition(id, "", class_, false).toString());
	}

	@Test
	public void testGenerateEntityAttributeDefinition() {
		HornetModelTypescript hmodel = HornetModelTypescript.initModel();
		
		Class class_ = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		class_.applyStereotype(hmodel.entity);
		Class class2_ = TestUtils.createClass(hmodel.pckage, "maClasse2", false);
		class2_.applyStereotype(hmodel.nomenclature);
		
		Property id = TestUtils.createAttribute(class2_, "id", hmodel.stringPT, 0, 1);
		id.applyStereotype(hmodel.keyAttribute);
		
		Property prop = TestUtils.createAttribute(class_, "test", class2_, 0, 1);
		
		String expect = "idTest: {\n" + 
				"    type: Sequelize.STRING,\n" + 
				"    field: \"id_test\",\n" + 
				"    allowNull: true,\n" + 
				"    references: {\n" + 
				"        model: \"maClasse2Model\",\n" + 
				"        key: \"id\",\n" + 
				"    },\n" + 
				"},\n";
		assertEquals(expect, ClassifierModelGenerator.generateEntityAttributeDefinition(prop, id, "", "", class_, false, true).toString());
		expect = "addOtherIdTest: {\n" + 
				"    type: Sequelize.STRING,\n" + 
				"    field: \"add_other_id_test\",\n" + 
				"    allowNull: true,\n" + 
				"    references: {\n" + 
				"        model: \"maClasse2Model\",\n" + 
				"        key: \"id\",\n" + 
				"    },\n" + 
				"},\n";
		assertEquals(expect, ClassifierModelGenerator.generateEntityAttributeDefinition(prop, id, "addOther", "add_other", class_, false, true).toString());
	}

	@Test
	public void testGenerateBasicAttributeDefinition() {
		HornetModelTypescript hmodel = HornetModelTypescript.initModel();
		
		Class class_ = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		class_.applyStereotype(hmodel.entity);
		Class class2_ = TestUtils.createClass(hmodel.pckage, "maClasse2", false);
		class2_.applyStereotype(hmodel.nomenclature);
		
		Property prop = TestUtils.createAttribute(class_, "test", hmodel.stringPT, 1, 1);
		
		String expect = "test: {\n" + 
				"    type: Sequelize.STRING,\n" + 
				"    field: \"test\",\n" + 
				"    allowNull: true,\n" + 
				"},\n";
		assertEquals(expect, ClassifierModelGenerator.generateBasicAttributeDefinition(prop, "", "", class_, false, true).toString());
		expect = "test: {\n" + 
				"    type: Sequelize.STRING,\n" + 
				"    field: \"test\",\n" + 
				"    allowNull: false,\n" + 
				"},\n";
		assertEquals(expect, ClassifierModelGenerator.generateBasicAttributeDefinition(prop, "", "", class_, false, false).toString());
		expect = "test: {\n" + 
				"    type: Sequelize.STRING,\n" + 
				"    field: \"test\",\n" + 
				"    allowNull: true,\n" + 
				"},\n";
		assertEquals(expect, ClassifierModelGenerator.generateBasicAttributeDefinition(prop, "", "", class_, true, true).toString());
		expect = "test: {\n" + 
				"    type: Sequelize.STRING,\n" + 
				"    field: \"test\",\n" + 
				"    primaryKey: true,\n" + 
				"    allowNull: false,\n" + 
				"},\n";
		assertEquals(expect, ClassifierModelGenerator.generateBasicAttributeDefinition(prop, "", "", class_, true, false).toString());
	}

	@Test
	public void testGenerateAttributeTypeLength() {
		HornetModelTypescript hmodel = HornetModelTypescript.initModel();
		
		Class class_ = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		class_.applyStereotype(hmodel.entity);
		
		Property prop = TestUtils.createAttribute(class_, "test", hmodel.stringPT, 1, 1);
		prop.applyStereotype(hmodel.attribute);
		
		TestUtils.setStereotypePropertyValue(prop, hmodel.attribute, hmodel.attributeHasLength, true);
		TestUtils.setStereotypePropertyValue(prop, hmodel.attribute, hmodel.attributeLength, 10);	
		assertEquals("(10)", ClassifierModelGenerator.generateAttributeTypeLength(prop).toString());
	}
	
	@Test
	public void testKeyAttributeGenerateAttributeTypeLength() {
		HornetModelTypescript hmodel = HornetModelTypescript.initModel();
		
		Class class_ = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		class_.applyStereotype(hmodel.entity);
		
		Property prop = TestUtils.createAttribute(class_, "test", hmodel.stringPT, 1, 1);
		prop.applyStereotype(hmodel.keyAttribute);
		
		TestUtils.setStereotypePropertyValue(prop, hmodel.keyAttribute, hmodel.attributeHasLength, true);
		TestUtils.setStereotypePropertyValue(prop, hmodel.keyAttribute, hmodel.attributeLength, 10);	
		assertEquals("(10)", ClassifierModelGenerator.generateAttributeTypeLength(prop).toString());
	}

	@Test
	public void testGenerateNIdAttributeDefaultValue() {
		HornetModelTypescript hmodel = HornetModelTypescript.initModel();
		
		Class class_ = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		class_.applyStereotype(hmodel.entity);
		
		Property prop = TestUtils.createAttribute(class_, "test", hmodel.stringPT, 1, 1);
		prop.applyStereotype(hmodel.attribute);
		prop.setStringDefaultValue("defaut");
		assertEquals(",\ndefaultValue: \"defaut\"", ClassifierModelGenerator.generateNIdAttributeDefaultValue(prop).toString());
	}

	@Test
	public void testGenerateReferenceAttributeAssocation() {
		HornetModelTypescript hmodel = HornetModelTypescript.initModel();
		
		Class class_ = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		class_.applyStereotype(hmodel.entity);
		TestUtils.createAttribute(class_, "id", hmodel.integerPT, 0, 1);
		Class class2_ = TestUtils.createClass(hmodel.pckage, "maClasse2", false);
		class2_.applyStereotype(hmodel.entity);
		Property id2 = TestUtils.createAttribute(class2_, "id2", hmodel.integerPT, 0, 1);
		
		class_.createAssociation(true, AggregationKind.NONE_LITERAL, "test", 0, 1, class2_, true, AggregationKind.NONE_LITERAL, "test2", 0, -1);
		Property prop = class_.getAttribute("test", class2_);
		prop.getAssociation().createOwnedEnd("test", class2_);
		prop.getAssociation().createOwnedEnd("test2", class_);
		
		String expect = "id2Test: {\n" + 
				"    type: Sequelize.STRING,\n" + 
				"    field: \"id2Test\",\n" + 
				"    allowNull: true,\n" + 
				"    references: {\n" + 
				"        model: \"maClasse2Model\",\n" + 
				"        key: \"id2\",\n" + 
				"    },\n" + 
				"},\n";
		assertEquals(expect, ClassifierModelGenerator.generateReferenceAttributeAssocation(prop, id2, class_, "").toString());
	}

	@Test
	public void testGenerateReferenceAttributeNA() {
		HornetModelTypescript hmodel = HornetModelTypescript.initModel();
		
		Class class_ = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		class_.applyStereotype(hmodel.entity);
		Class class2_ = TestUtils.createClass(hmodel.pckage, "maClasse2", false);
		class2_.applyStereotype(hmodel.entity);
		Property id = TestUtils.createAttribute(class2_, "id", hmodel.integerPT, 0, 1);
		Property prop = TestUtils.createAttribute(class2_, "test", class_, 0, 1);
		
		String expect ="testId: {\n" + 
				"    type: Sequelize.STRING,\n" + 
				"    field: \"testId\",\n" + 
				"    allowNull: true,\n" + 
				"    references: {\n" + 
				"        model: \"maClasse2Model\",\n" + 
				"        key: \"id\",\n" + 
				"    },\n" + 
				"},\n";
		assertEquals(expect, ClassifierModelGenerator.generateReferenceAttributeNA(prop, id, class_, "").toString());
	}

	@Test
	public void testGenerateMultiValuedPrimitiveTypeModel() {
		HornetModelTypescript hmodel = HornetModelTypescript.initModel();
		
		Class class_ = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		class_.applyStereotype(hmodel.entity);
		
		Property id = TestUtils.createAttribute(class_, "id", hmodel.integerPT, 0, 1);
		id.applyStereotype(hmodel.keyAttribute);
		Property prop = TestUtils.createAttribute(class_, "test", hmodel.stringPT, 0, -1);
		
		String expect ="\n" + 
				"export const MaClasseTestModel: Sequelize.DefineAttributes={\n" + 
				"    test: {\n" + 
				"        type: Sequelize.STRING,\n" + 
				"        field: \"test\",\n" + 
				"        primaryKey: true,\n" + 
				"        allowNull: false,\n" + 
				"    },\n" + 
				"    id:{\n" + 
				"        type: Sequelize.STRING,\n" + 
				"        field: \"id\",\n" + 
				"        allowNull: false,\n" + 
				"        primaryKey: true,\n" + 
				"        references: {\n" + 
				"            model: \"maClasseModel\",\n" + 
				"            key: \"id\",\n" + 
				"        },\n" + 
				"    },\n" + 
				"};\n";
		assertEquals(expect, ClassifierModelGenerator.generateMultiValuedPrimitiveTypeModel(prop, class_).toString());
	}

	@Test
	public void testGenerateMultiValuedPrimitiveTypeModelIdAttributes() {
		HornetModelTypescript hmodel = HornetModelTypescript.initModel();
		
		Class class_ = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		class_.applyStereotype(hmodel.entity);
		
		Property id = TestUtils.createAttribute(class_, "id", hmodel.integerPT, 0, 1);
		id.applyStereotype(hmodel.keyAttribute);
		
		String expect = "id:{\n" + 
				"    type: Sequelize.STRING,\n" + 
				"    field: \"id\",\n" + 
				"    allowNull: false,\n" + 
				"    primaryKey: true,\n" + 
				"    references: {\n" + 
				"        model: \"maClasseModel\",\n" + 
				"        key: \"id\",\n" + 
				"    },\n" + 
				"},\n";
		assertEquals(expect, ClassifierModelGenerator.generateMultiValuedPrimitiveTypeModelIdAttributes(id,  class_).toString());

	}

	@Test
	public void testGenerateNPTypeAssociationModel() {
		HornetModelTypescript hmodel = HornetModelTypescript.initModel();
		
		Class class_ = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		class_.applyStereotype(hmodel.entity);
		Property id = TestUtils.createAttribute(class_, "id", hmodel.integerPT, 0, 1);
		id.applyStereotype(hmodel.keyAttribute);
		
		Class class2_ = TestUtils.createClass(hmodel.pckage, "maClasse2", false);
		class2_.applyStereotype(hmodel.entity);
		Property id2 =TestUtils.createAttribute(class2_, "id2", hmodel.integerPT, 0, 1);
		id2.applyStereotype(hmodel.keyAttribute);
		
		class_.createAssociation(true, AggregationKind.NONE_LITERAL, "test", 0, 1, class2_, true, AggregationKind.NONE_LITERAL, "test2", 0, 1);		
		Property prop = class_.getAttribute("test", class2_);
		prop.getAssociation().createOwnedEnd("test", class2_);
		prop.getAssociation().createOwnedEnd("test2", class_);
		
		String expect = "\n" + 
				"export const MaClasseTestModel: Sequelize.DefineAttributes={\n" + 
				"    id2MaClasse2:{\n" + 
				"        type: Sequelize.STRING,\n" + 
				"        field: \"id2_test\",\n" + 
				"        allowNull: false,\n" + 
				"        primaryKey: true,\n" + 
				"        references: {\n" + 
				"            model: \"maClasse2Model\",\n" + 
				"            key: \"id2\",\n" + 
				"        },\n" + 
				"    },\n" + 
				"    idMaClasse:{\n" + 
				"        type: Sequelize.STRING,\n" + 
				"        field: \"id_ma_classe\",\n" + 
				"        allowNull: false,\n" + 
				"        primaryKey: true,\n" + 
				"        references: {\n" + 
				"            model: \"maClasseModel\",\n" + 
				"            key: \"id\",\n" + 
				"        },\n" + 
				"    },\n" + 
				"};\n" ;
		assertEquals(expect, ClassifierModelGenerator.generateNPTypeAssociationModel(prop,  class_).toString());
	}

	@Test
	public void testGenerateNPTAssociationModelIdAttributes() {
		HornetModelTypescript hmodel = HornetModelTypescript.initModel();
		
		Class class_ = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		class_.applyStereotype(hmodel.entity);
		Property id = TestUtils.createAttribute(class_, "id", hmodel.integerPT, 0, 1);
		id.applyStereotype(hmodel.keyAttribute);
		
		Class class2_ = TestUtils.createClass(hmodel.pckage, "maClasse2", false);
		class2_.applyStereotype(hmodel.entity);
		Property id2 =TestUtils.createAttribute(class2_, "id2", hmodel.integerPT, 0, 1);
		id2.applyStereotype(hmodel.keyAttribute);
		
		Property prop = TestUtils.createAttribute(class_, "test", class2_, 0, 1);
		
		String expect = "id2MaClasse2:{\n" + 
				"    type: Sequelize.STRING,\n" + 
				"    field: \"id2\",\n" + 
				"    allowNull: false,\n" + 
				"    primaryKey: true,\n" + 
				"    references: {\n" + 
				"        model: \"maClasse2Model\",\n" + 
				"        key: \"id2\",\n" + 
				"    },\n" + 
				"},\n";
		assertEquals(expect, ClassifierModelGenerator.generateNPTAssociationModelIdAttributes(prop, id2, class_, "").toString());
		
		expect = "id2MaClasse2:{\n" + 
				"    type: Sequelize.STRING,\n" + 
				"    field: \"id2_add\",\n" + 
				"    allowNull: false,\n" + 
				"    primaryKey: true,\n" + 
				"    references: {\n" + 
				"        model: \"maClasse2Model\",\n" + 
				"        key: \"id2\",\n" + 
				"    },\n" + 
				"},\n";
		assertEquals(expect, ClassifierModelGenerator.generateNPTAssociationModelIdAttributes(prop, id2, class_, "add").toString());

	}

	@Test
	public void testGenerateNPTypeModel() {
		HornetModelTypescript hmodel = HornetModelTypescript.initModel();
		
		Class class_ = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		class_.applyStereotype(hmodel.entity);
		Property id = TestUtils.createAttribute(class_, "id", hmodel.integerPT, 0, 1);
		id.applyStereotype(hmodel.keyAttribute);
		
		Class class2_ = TestUtils.createClass(hmodel.pckage, "maClasse2", false);
		class2_.applyStereotype(hmodel.entity);
		Property id2 =TestUtils.createAttribute(class2_, "id2", hmodel.integerPT, 0, 1);
		id2.applyStereotype(hmodel.keyAttribute);
		
		Property prop = TestUtils.createAttribute(class_, "test", class2_, 0, 1);
		
		String expect = "\nexport const MaClasseTestModel: Sequelize.DefineAttributes={\n" + 
				"    idMaClasse:{\n" + 
				"        type: Sequelize.STRING,\n" + 
				"        field: \"id_ma_classe\",\n" + 
				"        allowNull: false,\n" + 
				"        primaryKey: true,\n" + 
				"        references: {\n" + 
				"            model: \"maClasseModel\",\n" + 
				"            key: \"id\",\n" + 
				"        },\n" + 
				"    },\n" + 
				"    id2MaClasse2:{\n" + 
				"        type: Sequelize.STRING,\n" + 
				"        field: \"id2_test\",\n" + 
				"        allowNull: false,\n" + 
				"        primaryKey: true,\n" + 
				"        references: {\n" + 
				"            model: \"maClasse2Model\",\n" + 
				"            key: \"id2\",\n" + 
				"        },\n" + 
				"    },\n" + 
				"};\n";
		assertEquals(expect, ClassifierModelGenerator.generateNPTypeModel(prop, class_).toString());
	}

	@Test
	public void testGenerateNPTModelIdAttributesPropertyClassifierBoolean() {
		HornetModelTypescript hmodel = HornetModelTypescript.initModel();
		
		Class class_ = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		class_.applyStereotype(hmodel.entity);
		Property id = TestUtils.createAttribute(class_, "id", hmodel.integerPT, 0, 1);
		id.applyStereotype(hmodel.keyAttribute);
		
		Class class2_ = TestUtils.createClass(hmodel.pckage, "maClasse2", false);
		class2_.applyStereotype(hmodel.entity);
		Property id2 =TestUtils.createAttribute(class2_, "id2", hmodel.integerPT, 0, 1);
		id2.applyStereotype(hmodel.keyAttribute);
		
		String expect = "idMaClasse:{\n" + 
				"    type: Sequelize.STRING,\n" + 
				"    field: \"id\",\n" + 
				"    allowNull: false,\n" + 
				"    primaryKey: true,\n" + 
				"    references: {\n" + 
				"        model: \"maClasseModel\",\n" + 
				"        key: \"id\",\n" + 
				"    },\n" + 
				"},\n";
		assertEquals(expect, ClassifierModelGenerator.generateNPTModelIdAttributes(id, class_, false).toString());
		
		expect = "idMaClasse:{\n" + 
				"    type: Sequelize.STRING,\n" + 
				"    field: \"id_ma_classe\",\n" + 
				"    allowNull: false,\n" + 
				"    primaryKey: true,\n" + 
				"    references: {\n" + 
				"        model: \"maClasseModel\",\n" + 
				"        key: \"id\",\n" + 
				"    },\n" + 
				"},\n";
		assertEquals(expect, ClassifierModelGenerator.generateNPTModelIdAttributes(id, class_, true).toString());
	}

	@Test
	public void testGenerateNPTModelIdAttributesPropertyPropertyClassifier() {
		HornetModelTypescript hmodel = HornetModelTypescript.initModel();
		
		Class class_ = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		class_.applyStereotype(hmodel.entity);
		Property id = TestUtils.createAttribute(class_, "id", hmodel.integerPT, 0, 1);
		id.applyStereotype(hmodel.keyAttribute);
		
		Class class2_ = TestUtils.createClass(hmodel.pckage, "maClasse2", false);
		class2_.applyStereotype(hmodel.entity);
		Property id2 =TestUtils.createAttribute(class2_, "id2", hmodel.integerPT, 0, 1);
		id2.applyStereotype(hmodel.keyAttribute);
		
		Property prop = TestUtils.createAttribute(class_, "test", class2_, 0, 1);
		
		String expect = "id2MaClasse2:{\n" + 
				"    type: Sequelize.STRING,\n" + 
				"    field: \"id2_test\",\n" + 
				"    allowNull: false,\n" + 
				"    primaryKey: true,\n" + 
				"    references: {\n" + 
				"        model: \"maClasse2Model\",\n" + 
				"        key: \"id2\",\n" + 
				"    },\n" + 
				"},\n";
		assertEquals(expect, ClassifierModelGenerator.generateNPTModelIdAttributes(prop, id2, class_).toString());
	}

	@Test
	public void testGetAttributeSequelizeTypeDeclaration() {
		HornetModelTypescript hmodel = HornetModelTypescript.initModel();
		
		Class class_ = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		class_.applyStereotype(hmodel.entity);
		Property prop = TestUtils.createAttribute(class_, "test", hmodel.stringPT, 0, 1);
		prop.applyStereotype(hmodel.keyAttribute);
		
		String expect = "Sequelize.STRING";
		assertEquals(expect, ClassifierModelGenerator.getAttributeSequelizeTypeDeclaration(prop).toString());

	}
	
	@Test
	public void testClassGetAttributeSequelizeTypeDeclaration() {
		HornetModelTypescript hmodel = HornetModelTypescript.initModel();
		
		Class class_ = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		class_.applyStereotype(hmodel.entity);
		Class class2_ = TestUtils.createClass(hmodel.pckage, "maClasse2", false);
		class2_.applyStereotype(hmodel.hornetType);
		Property prop = TestUtils.createAttribute(class_, "test", class2_, 0, 1);
		prop.applyStereotype(hmodel.attribute);
		
		TestUtils.setStereotypePropertyValue(class2_, hmodel.hornetType, hmodel.hornetTypeSequelizeType, "TEST");

		String expect = "Sequelize.TEST";
		assertEquals(expect, ClassifierModelGenerator.getAttributeSequelizeTypeDeclaration(prop).toString());
		
		TestUtils.setStereotypePropertyValue(prop, hmodel.attribute, hmodel.attributeHasLength, true);
		TestUtils.setStereotypePropertyValue(prop, hmodel.attribute, hmodel.attributeLength, 10);
		
		expect = "Sequelize.TEST(10)";
		assertEquals(expect, ClassifierModelGenerator.getAttributeSequelizeTypeDeclaration(prop).toString());

	}

	@Test
	public void testGenerateMultiValuedEnumModel() {
		HornetModelTypescript hmodel = HornetModelTypescript.initModel();
		
		Class class_ = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		class_.applyStereotype(hmodel.entity);
		Property id = TestUtils.createAttribute(class_, "id", hmodel.integerPT, 0, 1);
		id.applyStereotype(hmodel.keyAttribute);
		Class class2_ = TestUtils.createClass(hmodel.pckage, "maClasse2", false);
		class2_.applyStereotype(hmodel.nomenclature);
		Property prop = TestUtils.createAttribute(class_, "test", class2_, 0, -1);
		
		String expect = "\nexport const MaClasseTestModel: Sequelize.DefineAttributes={\n" + 
				"    idMaClasse:{\n" + 
				"        type: Sequelize.STRING,\n" + 
				"        field: \"id\",\n" + 
				"        allowNull: false,\n" + 
				"        primaryKey: true,\n" + 
				"        references: {\n" + 
				"            model: \"maClasseModel\",\n" + 
				"            key: \"id\",\n" + 
				"        },\n" + 
				"    },\n" + 
				"    code: {\n" + 
				"        type: Sequelize.INTEGER,\n" + 
				"        field: \"code\",\n" + 
				"        allowNull: false,\n" + 
				"        primaryKey: true,\n" + 
				"        references: {\n" + 
				"            model: \"maClasse2Model\",\n" + 
				"            key: \"code\",\n" + 
				"        },\n" + 
				"    },\n" + 
				"};\n";
		assertEquals(expect, ClassifierModelGenerator.generateMultiValuedEnumModel(prop, class_).toString());
	}

	@Test
	public void testGenerateMultiValuedVOModel() {
		HornetModelTypescript hmodel = HornetModelTypescript.initModel();
		
		Class class_ = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		class_.applyStereotype(hmodel.entity);
		Property id = TestUtils.createAttribute(class_, "id", hmodel.integerPT, 0, 1);
		id.applyStereotype(hmodel.keyAttribute);
		Class class2_ = TestUtils.createClass(hmodel.pckage, "maClasse2", false);
		class2_.applyStereotype(hmodel.valueObject);
		Property prop = TestUtils.createAttribute(class_, "test", class2_, 0, -1);
		TestUtils.createAttribute(class2_, "test2", hmodel.stringPT, 0, 1);
		
		String expect = "\nexport const MaClasseTestModel: Sequelize.DefineAttributes={\n" + 
				"    idMaClasse:{\n" + 
				"        type: Sequelize.STRING,\n" + 
				"        field: \"id\",\n" + 
				"        allowNull: false,\n" + 
				"        primaryKey: true,\n" + 
				"        references: {\n" + 
				"            model: \"maClasseModel\",\n" + 
				"            key: \"id\",\n" + 
				"        },\n" + 
				"    },\n" + 
				"    testTest2: {\n" + 
				"        type: Sequelize.STRING,\n" + 
				"        field: \"test_test2\",\n" + 
				"        allowNull: true,\n" + 
				"    },\n" + 
				"};\n";
		assertEquals(expect, ClassifierModelGenerator.generateMultiValuedVOModel(prop, class_).toString());

	}

}
