package fr.gouv.diplomatie.papyrus.codegen.sql.test.xtend.pakkage;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.eclipse.uml2.uml.AssociationClass;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Property;
import org.junit.Test;

import fr.gouv.diplomatie.papyrus.codegen.core.test.HornetModel;
import fr.gouv.diplomatie.papyrus.codegen.core.test.TestUtils;
import fr.gouv.diplomatie.papyrus.codegen.sql.xtend.pakkage.PackageDatabaseScriptGenerator;

public class PackageDatabaseScriptGeneratorTest {

	@Test
	public void testClass() {
		PackageDatabaseScriptGenerator gene = new PackageDatabaseScriptGenerator();
		assertEquals(PackageDatabaseScriptGenerator.class, gene.getClass());
	}
	
	@Test
	public void testGenerateTable() {
		HornetModel hmodel = HornetModel.initModel();
		Class class_ = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		TestUtils.createAttribute(class_, "id", hmodel.integerPT, 0, 1).applyStereotype(hmodel.keyAttribute);
		
		String expect = "\nCREATE TABLE \"MA_CLASSE\"(\n" + 
				"	\"ID\" text NOT NULL\n" + 
				");\n" + 
				"\n" + 
				"ALTER TABLE ONLY \"MA_CLASSE\"\n" + 
				"	ADD CONSTRAINT MA_CLASSE_PKEY PRIMARY KEY (\"ID\");\n\n";
		assertEquals(expect, PackageDatabaseScriptGenerator.generateTable(class_).toString());
	}

	@Test
	public void testGenerateAttrForeignKey() {
		HornetModel hmodel = HornetModel.initModel();
		Class class_ = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		Class class2_ = TestUtils.createClass(hmodel.pckage, "maClasse2", false);
		TestUtils.createAttribute(class_, "id", hmodel.integerPT, 0, 1).applyStereotype(hmodel.keyAttribute);
		TestUtils.createAttribute(class2_, "id2", hmodel.integerPT, 0, 1).applyStereotype(hmodel.keyAttribute);
		
		Property prop = TestUtils.createAttribute(class_, "test", class2_, 0, 1);
		
		String expect = "\"ID_MA_CLASSE_TEST\" text \n";
		assertEquals(expect,PackageDatabaseScriptGenerator.generateAttrForeignKey(prop, class_, "").toString());
		
	}

	@Test
	public void testGenerateAttributesAlterForeignKey() {
		HornetModel hmodel = HornetModel.initModel();
		Class class_ = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		Class class2_ = TestUtils.createClass(hmodel.pckage, "maClasse2", false);
		TestUtils.createAttribute(class_, "id", hmodel.integerPT, 0, 1).applyStereotype(hmodel.keyAttribute);
		TestUtils.createAttribute(class2_, "id2", hmodel.integerPT, 0, 1).applyStereotype(hmodel.keyAttribute);
		
		Property prop = TestUtils.createAttribute(class2_, "test", class_, 0, 1);
		
		String expect = "\nALTER TABLE ONLY \"MA_CLASSE\"\n" + 
				"    ADD CONSTRAINT MA_CLASSE_MA_CLASSE2_TEST_IDS_FKEY\n" + 
				"    FOREIGN KEY (\"ID2_MA_CLASSE2_TEST\") REFERENCES \"MA_CLASSE2\"(\"ID2\");\n";
		assertEquals(expect,PackageDatabaseScriptGenerator.generateAttributesAlterForeignKey(prop, class_, "").toString());
	}
	
	@Test
	public void testGenerateExtendId() {
		HornetModel hmodel = HornetModel.initModel();
		Class class_ = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		TestUtils.createAttribute(class_, "id", hmodel.integerPT, 0, 1).applyStereotype(hmodel.keyAttribute);
		
		String expect = "\"ID\" text NOT NULL,\n";
		assertEquals(expect, PackageDatabaseScriptGenerator.generateExtendId(class_).toString());
	}

	@Test
	public void testGenerateIdAttributeDefinition() {
		HornetModel hmodel = HornetModel.initModel();
		Class class_ = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		Property id = TestUtils.createAttribute(class_, "id", hmodel.integerPT, 0, 1);
		id.applyStereotype(hmodel.keyAttribute);
		
		String expect = "\"ID\" text NOT NULL";
		assertEquals(expect, PackageDatabaseScriptGenerator.generateIdAttributeDefinition(id, "").toString());
	}

	@Test
	public void testGenerateStringLength() {
		HornetModel hmodel = HornetModel.initModel();
		Class class_ = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		Property prop = TestUtils.createAttribute(class_, "test", hmodel.integerPT, 0, 1);

		prop.applyStereotype(hmodel.attribute);
		
		String expect = "";
		assertEquals(expect, PackageDatabaseScriptGenerator.generateStringLength(prop).toString());
		
		TestUtils.setStereotypePropertyValue(prop, hmodel.attribute, hmodel.attributeHasLength, true);
		TestUtils.setStereotypePropertyValue(prop, hmodel.attribute, hmodel.attributeLength, 10);
		expect = " varying(10)";
		assertEquals(expect, PackageDatabaseScriptGenerator.generateStringLength(prop).toString());
		
	}

	@Test
	public void testGenerateEumAttributesDefinition() {
		HornetModel hmodel = HornetModel.initModel();
		Class class_ = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		Class class2_ = TestUtils.createClass(hmodel.pckage, "maClasse2", false);
		
		Property prop = TestUtils.createAttribute(class_, "test", class2_, 0, 1);
		
		String expect = "\"CODE_TEST\" integer ";
		assertEquals(expect, PackageDatabaseScriptGenerator.generateEumAttributesDefinition(prop, "", class_, true).toString());
	}

	@Test
	public void testGenerateBasicAttributeDefinition() {
		HornetModel hmodel = HornetModel.initModel();
		Class class_ = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		
		Property prop = TestUtils.createAttribute(class_, "test", hmodel.stringPT, 0, 1);
		
		String expect = "\"TEST\" text ";
		assertEquals(expect, PackageDatabaseScriptGenerator.generateBasicAttributeDefinition(prop, "", class_, true).toString());
	}

	@Test
	public void testGenerateEntityAttributeDefinition() {
		HornetModel hmodel = HornetModel.initModel();
		Class class_ = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		Property id = TestUtils.createAttribute(class_, "id", hmodel.stringPT, 0, 1);
		Property prop = TestUtils.createAttribute(class_, "test", hmodel.stringPT, 0, 1);
		
		String expect = "\"ID_TEST\" text ";
		assertEquals(expect, PackageDatabaseScriptGenerator.generateEntityAttributeDefinition(prop, id, "", class_, true).toString());
	}

	@Test
	public void testGenerateIds() {
		HornetModel hmodel = HornetModel.initModel();
		Class class_ = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		Property id = TestUtils.createAttribute(class_, "id", hmodel.stringPT, 0, 1);
		id.applyStereotype(hmodel.keyAttribute);
		
		String expect = "\nALTER TABLE ONLY \"MA_CLASSE\"\n" + 
				"	ADD CONSTRAINT MA_CLASSE_PKEY PRIMARY KEY (\"ID\");\n";
		assertEquals(expect, PackageDatabaseScriptGenerator.generateIds(class_).toString());
	}

	@Test
	public void testGenerateExtendForeignKey() {
		HornetModel hmodel = HornetModel.initModel();
		Class class_ = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		Property id = TestUtils.createAttribute(class_, "id", hmodel.stringPT, 0, 1);
		id.applyStereotype(hmodel.keyAttribute);
		Class class2_ = TestUtils.createClass(hmodel.pckage, "maClasse2", false);
		Property id2 = TestUtils.createAttribute(class2_, "id2", hmodel.stringPT, 0, 1);
		id2.applyStereotype(hmodel.keyAttribute);
		
		String expect = "\nALTER TABLE ONLY \"MA_CLASSE\"\n" + 
				"    ADD CONSTRAINT MA_CLASSE_MA_CLASSE2_IDS_FKEY\n" + 
				"    FOREIGN KEY (\"ID2\") REFERENCES \"MA_CLASSE2\"(\"ID2\");\n";
		assertEquals(expect, PackageDatabaseScriptGenerator.generateExtendForeignKey(class2_, class_).toString());
	}

	@Test
	public void testGenerateForeignKey() {
		HornetModel hmodel = HornetModel.initModel();
		Class class_ = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		Property id = TestUtils.createAttribute(class_, "id", hmodel.stringPT, 0, 1);
		id.applyStereotype(hmodel.keyAttribute);
		
		Class class2_ = TestUtils.createClass(hmodel.pckage, "maClasse2", false);
		Property id2 = TestUtils.createAttribute(class2_, "id2", hmodel.stringPT, 0, 1);
		id2.applyStereotype(hmodel.keyAttribute);
		
		Property prop = TestUtils.createAttribute(class_, "test", class2_, 0, 1);
		
		String expect = "\nALTER TABLE ONLY \"AB\"\n" + 
				"    ADD CONSTRAINT AB_TEST_IDS_FKEY \n" + 
				"    FOREIGN KEY (\"ID2_TEST\") REFERENCES \"MA_CLASSE2\"(\"ID2\");\n";
		assertEquals(expect, PackageDatabaseScriptGenerator.generateForeignKey(prop, "", "AB", "").toString());
		
	}

	@Test
	public void testGenerateEnumForeignKey() {
		HornetModel hmodel = HornetModel.initModel();
		Class class_ = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		Property id = TestUtils.createAttribute(class_, "id", hmodel.stringPT, 0, 1);
		id.applyStereotype(hmodel.keyAttribute);
		
		Class class2_ = TestUtils.createClass(hmodel.pckage, "maClasse2", false);
		class2_.applyStereotype(hmodel.nomenclature);
		
		Property prop = TestUtils.createAttribute(class_, "test", class2_, 0, 1);
		
		String expect = "\nALTER TABLE ONLY \"AB\"\n" + 
				"    ADD CONSTRAINT AB_TEST_CODE_FKEY \n" + 
				"    FOREIGN KEY (\"CODE_TEST\") REFERENCES \"MA_CLASSE2\"(\"CODE\");\n";
		assertEquals(expect, PackageDatabaseScriptGenerator.generateEnumForeignKey(prop, "", "AB", "").toString());
	}

	@Test
	public void testGenerateMutilvaluedPTTable() {
		HornetModel hmodel = HornetModel.initModel();
		Class class_ = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		Property id = TestUtils.createAttribute(class_, "id", hmodel.stringPT, 0, 1);
		id.applyStereotype(hmodel.keyAttribute);
	
		
		Property prop = TestUtils.createAttribute(class_, "test", hmodel.stringPT, 0, 1);
		String expect = "\nCREATE TABLE \"MA_CLASSE_TEST\"(\n" + 
				"	\"TEST\" text NOT NULL,\n" + 
				"	\"ID\" text NOT NULL\n" + 
				");\n" + 
				"\n" + 
				"ALTER TABLE ONLY \"MA_CLASSE_TEST\"\n" + 
				"    ADD CONSTRAINT MA_CLASSE_TEST_IDS_FKEY\n" + 
				"    FOREIGN KEY (\"ID\") REFERENCES \"MA_CLASSE\"(\"ID\");\n" + 
				"    \n" + 
				"ALTER TABLE ONLY \"MA_CLASSE_TEST\"\n" + 
				"    ADD CONSTRAINT MA_CLASSE_TEST_PKEY PRIMARY KEY(\"ID\", \"TEST\");\n";
		assertEquals(expect, PackageDatabaseScriptGenerator.generateMutilvaluedPTTable(prop, class_).toString());
	}

	@Test
	public void testGenerateMutilvaluedEntityTable() {
		HornetModel hmodel = HornetModel.initModel();
		Class class_ = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		Property id = TestUtils.createAttribute(class_, "id", hmodel.stringPT, 0, 1);
		id.applyStereotype(hmodel.keyAttribute);
		
		Class class2_ = TestUtils.createClass(hmodel.pckage, "maClasse2", false);
		class2_.applyStereotype(hmodel.nomenclature);
		Property id2 = TestUtils.createAttribute(class2_, "id2", hmodel.stringPT, 0, 1);
		id2.applyStereotype(hmodel.keyAttribute);
		
		Property prop = TestUtils.createAttribute(class_, "test", class2_, 0, 1);
		
		String expect = "\nCREATE TABLE \"MA_CLASSE_TEST\"(\n" + 
				"	\"ID2_TEST\" text NOT NULL,\n" + 
				"	\"ID_MA_CLASSE\" text NOT NULL\n" + 
				");\n" + 
				"\n" + 
				"ALTER TABLE ONLY \"MA_CLASSE_TEST\"\n" + 
				"    ADD CONSTRAINT MA_CLASSE_TEST_MA_CLASSE_IDS_FKEY\n" + 
				"    FOREIGN KEY (\"ID_MA_CLASSE\") REFERENCES \"MA_CLASSE\"(\"ID\");\n" + 
				"    \n" + 
				"ALTER TABLE ONLY \"MA_CLASSE_TEST\"\n" + 
				"    ADD CONSTRAINT MA_CLASSE_TEST_MA_CLASSE2_IDS_FKEY\n" + 
				"    FOREIGN KEY (\"ID2_TEST\") REFERENCES \"MA_CLASSE2\"(\"ID2\");\n" + 
				"    \n" + 
				"ALTER TABLE ONLY \"MA_CLASSE_TEST\"\n" + 
				"    ADD CONSTRAINT MA_CLASSE_TEST_PKEY PRIMARY KEY(\"ID_MA_CLASSE\", \"ID2_TEST\");\n";
		assertEquals(expect, PackageDatabaseScriptGenerator.generateMutilvaluedEntityTable(prop, class_).toString());
	}

	@Test
	public void testGenerateMultiIdAttributeDef() {
		HornetModel hmodel = HornetModel.initModel();
		Class class_ = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		Property id = TestUtils.createAttribute(class_, "id", hmodel.stringPT, 0, 1);
		id.applyStereotype(hmodel.keyAttribute);
		String expect = "\"ID\" text NOT NULL";
		assertEquals(expect, PackageDatabaseScriptGenerator.generateMultiIdAttributeDef(id, "").toString());
		
		expect = "\"ID_ADD\" text NOT NULL";
		assertEquals(expect, PackageDatabaseScriptGenerator.generateMultiIdAttributeDef(id, "ADD").toString());
	}

	@Test
	public void testGenerateMultiIdAttributeDefinition() {
		HornetModel hmodel = HornetModel.initModel();
		Class class_ = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		Property id = TestUtils.createAttribute(class_, "id", hmodel.stringPT, 0, 1);
		id.applyStereotype(hmodel.keyAttribute);
		String expect = "\"ID_MA_CLASSE\" text NOT NULL";
		assertEquals(expect, PackageDatabaseScriptGenerator.generateMultiIdAttributeDefinition(id, "").toString());
		
		expect = "\"ADD_ID_MA_CLASSE\" text NOT NULL";
		assertEquals(expect, PackageDatabaseScriptGenerator.generateMultiIdAttributeDefinition(id, "ADD").toString());
	}

	@Test
	public void testGenerateValueObjectEntityTable() {
		HornetModel hmodel = HornetModel.initModel();
		Class class_ = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		Property id = TestUtils.createAttribute(class_, "id", hmodel.stringPT, 0, 1);
		id.applyStereotype(hmodel.keyAttribute);
		
		Class class2_ = TestUtils.createClass(hmodel.pckage, "maClasse2", false);
		class2_.applyStereotype(hmodel.valueObject);
		TestUtils.createAttribute(class2_, "test2", hmodel.stringPT, 0, 1);
		
		Property prop = TestUtils.createAttribute(class_, "test", class2_, 0, 1);
		prop.applyStereotype(hmodel.attribute);
		String expect = "\nCREATE TABLE \"MA_CLASSE_TEST\"(\n" + 
				"	\"TEST_TEST2\" text ,\n" + 
				"	\"ID\" text NOT NULL\n" + 
				");\n" + 
				"\n" + 
				"ALTER TABLE ONLY \"MA_CLASSE_TEST\"\n" + 
				"    ADD CONSTRAINT MA_CLASSE_TEST_IDS_FKEY\n" + 
				"    FOREIGN KEY (\"ID\") REFERENCES \"MA_CLASSE\"(\"ID\");\n" + 
				"    \n" + 
				"ALTER TABLE ONLY \"MA_CLASSE_TEST\"\n" + 
				"    ADD CONSTRAINT MA_CLASSE_TEST_PKEY PRIMARY KEY(\"ID\");\n\n";
		assertEquals(expect, PackageDatabaseScriptGenerator.generateValueObjectEntityTable(prop, class_).toString());
	}

	@Test
	public void testGetAttributList() {
		HornetModel hmodel = HornetModel.initModel();
		Class class_ = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		Property id = TestUtils.createAttribute(class_, "id", hmodel.stringPT, 0, 1);
		id.applyStereotype(hmodel.keyAttribute);
		TestUtils.createAttribute(class_, "test", hmodel.stringPT, 1, 1);
		TestUtils.createAttribute(class_, "test2", hmodel.stringPT, 1, 1);
		
		ArrayList<String> expect = new ArrayList<String>();
		expect.add("TEST");
		expect.add("TEST2");
		ArrayList<String> list = new ArrayList<String>();
		assertEquals(expect, PackageDatabaseScriptGenerator.getAttributList(class_, list, ""));
	}

	@Test
	public void testGenerateEnumAttributTable() {
		HornetModel hmodel = HornetModel.initModel();
		Class class_ = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		Property id = TestUtils.createAttribute(class_, "id", hmodel.stringPT, 0, 1);
		id.applyStereotype(hmodel.keyAttribute);
		
		Class class2_ = TestUtils.createClass(hmodel.pckage, "maClasse2", false);
		class2_.applyStereotype(hmodel.nomenclature);
		
		Property prop = TestUtils.createAttribute(class_, "test", class2_, 0, -1);
		
		String expect = "\nCREATE TABLE \"MA_CLASSE_TEST\"(\n" + 
				"	\"CODE\" integer NOT NULL,\n" + 
				"	\"ID\" text NOT NULL\n" + 
				");\n" + 
				"\n" + 
				"ALTER TABLE ONLY \"MA_CLASSE_TEST\"\n" + 
				"    ADD CONSTRAINT MA_CLASSE_TEST_IDS_FKEY\n" + 
				"    FOREIGN KEY (\"ID\") REFERENCES \"MA_CLASSE\"(\"ID\");\n" + 
				"\n" + 
				"ALTER TABLE ONLY \"MA_CLASSE_TEST\"\n" + 
				"    ADD CONSTRAINT MA_CLASSE_TEST_CODE_FKEY\n" + 
				"    FOREIGN KEY (\"CODE\") REFERENCES \"MA_CLASSE2\"(\"CODE\");\n" + 
				"\n" + 
				"ALTER TABLE ONLY \"MA_CLASSE_TEST\"\n" + 
				"    ADD CONSTRAINT MA_CLASSE_TEST_PKEY PRIMARY KEY (\"ID\", \"CODE\");\n";
		assertEquals(expect, PackageDatabaseScriptGenerator.generateEnumAttributTable(prop, class_).toString());
		
	}

	@Test
	public void testGenerateSequence() {
		HornetModel hmodel = HornetModel.initModel();
		Class class_ = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		Property prop = TestUtils.createAttribute(class_, "prop", hmodel.stringPT, 0, 1);
		prop.applyStereotype(hmodel.sequence);
		
		String expect = "CREATE SEQUENCE \"MA_CLASSE_PROP_SEQ\"\n" + 
				"    START WITH 0\n" + 
				"    INCREMENT BY 0\n" + 
				"    NO MAXVALUE\n" + 
				"    NO MINVALUE\n" + 
				"    CACHE 0\n" + 
				"    NO CYCLE;\n" + 
				"    \n" + 
				"ALTER SEQUENCE \"MA_CLASSE_PROP_SEQ\"\n" + 
				"	OWNED BY \"MA_CLASSE\".\"PROP\";\n" + 
				"\n" + 
				"ALTER TABLE ONLY \"MA_CLASSE\" \n" + 
				"	ALTER COLUMN \"PROP\" \n" + 
				"	SET DEFAULT nextval('\"MA_CLASSE_PROP_SEQ\"'::regclass);\n";
		assertEquals(expect, PackageDatabaseScriptGenerator.generateSequence(prop).toString());
		
		TestUtils.setStereotypePropertyValue(prop, hmodel.sequence, hmodel.sequenceCache, 3);
		TestUtils.setStereotypePropertyValue(prop, hmodel.sequence, hmodel.sequenceCycle, true);
		TestUtils.setStereotypePropertyValue(prop, hmodel.sequence, hmodel.sequenceHasMaxValue, true);
		TestUtils.setStereotypePropertyValue(prop, hmodel.sequence, hmodel.sequenceMaxValue, 10);
		TestUtils.setStereotypePropertyValue(prop, hmodel.sequence, hmodel.sequenceHasMinValue, true);
		TestUtils.setStereotypePropertyValue(prop, hmodel.sequence, hmodel.sequenceMinValue, 2);
		TestUtils.setStereotypePropertyValue(prop, hmodel.sequence, hmodel.sequenceIncrementBy, 2);
		TestUtils.setStereotypePropertyValue(prop, hmodel.sequence, hmodel.sequenceStartWith, 4);

		expect = "CREATE SEQUENCE \"MA_CLASSE_PROP_SEQ\"\n" + 
				"    START WITH 4\n" + 
				"    INCREMENT BY 2\n" + 
				"    MAXVALUE 10\n" + 
				"    MINVALUE 2\n" + 
				"    CACHE 3\n" + 
				"    CYCLE;\n" + 
				"    \n" + 
				"ALTER SEQUENCE \"MA_CLASSE_PROP_SEQ\"\n" + 
				"	OWNED BY \"MA_CLASSE\".\"PROP\";\n" + 
				"\n" + 
				"ALTER TABLE ONLY \"MA_CLASSE\" \n" + 
				"	ALTER COLUMN \"PROP\" \n" + 
				"	SET DEFAULT nextval('\"MA_CLASSE_PROP_SEQ\"'::regclass);\n";
		assertEquals(expect, PackageDatabaseScriptGenerator.generateSequence(prop).toString());
	}

	@Test
	public void testGenerateAssociationTable() {
		HornetModel hmodel = HornetModel.initModel();
		Class class_ = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		class_.applyStereotype(hmodel.entity);
		Class class2_ = TestUtils.createClass(hmodel.pckage, "maClasse2", false);
		class2_.applyStereotype(hmodel.entity);
		
		TestUtils.createAttribute(class_, "id", hmodel.integerPT, 0, 1).applyStereotype(hmodel.keyAttribute);
		TestUtils.createAttribute(class2_, "id", hmodel.integerPT, 0, 1).applyStereotype(hmodel.keyAttribute);
		
		AssociationClass asso = TestUtils.createAssociationClass(class_, class2_, "att2", "att1", "asso");
		
		String expect = "\nCREATE TABLE \"ASSO\"(\n" + 
				"	\"ID_ATT2\" text ,\n" + 
				"	\"ID_ATT1\" text \n" + 
				");\n" + 
				"\n" + 
				"ALTER TABLE ONLY \"ASSO\"\n" + 
				"	ADD CONSTRAINT ASSO_ATT2_IDS_FKEY\n" + 
				"	FOREIGN KEY (\"ID_ATT2\") REFERENCES \"MA_CLASSE2\"(\"ID\");\n" + 
				"\n" + 
				"ALTER TABLE ONLY \"ASSO\"\n" + 
				"	ADD CONSTRAINT ASSO_ATT1_IDS_FKEY\n" + 
				"	FOREIGN KEY (\"ID_ATT1\") REFERENCES \"MA_CLASSE\"(\"ID\");\n" + 
				"\n" + 
				"ALTER TABLE ONLY \"ASSO\"\n" + 
				"	ADD CONSTRAINT ASSO_PKEY PRIMARY KEY (\"ID_ATT2\", \"ID_ATT1\");\n";
		assertEquals(expect, PackageDatabaseScriptGenerator.generateAssociationTable(asso).toString());

	}

	@Test
	public void testGetAssociationAttributList() {
		HornetModel hmodel = HornetModel.initModel();
		Class class_ = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		class_.applyStereotype(hmodel.entity);
		Class class2_ = TestUtils.createClass(hmodel.pckage, "maClasse2", false);
		class2_.applyStereotype(hmodel.entity);
		
		TestUtils.createAttribute(class_, "id", hmodel.integerPT, 0, 1).applyStereotype(hmodel.keyAttribute);
		TestUtils.createAttribute(class2_, "id", hmodel.integerPT, 0, 1).applyStereotype(hmodel.keyAttribute);
		
		AssociationClass asso = TestUtils.createAssociationClass(class_, class2_, "att2", "att1", "asso");
		
		ArrayList<String> expect = new ArrayList<String>();
		expect.add("ID_ATT2");
		expect.add("ID_ATT1");
		ArrayList<String> list = new ArrayList<String>();
		assertEquals(expect, PackageDatabaseScriptGenerator.getAssociationAttributList(asso, list, ""));
	}

	@Test
	public void testGenerateAssociationForeignKeysEntity() {
		HornetModel hmodel = HornetModel.initModel();
		Class class_ = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		class_.applyStereotype(hmodel.entity);
		TestUtils.createAttribute(class_, "id", hmodel.integerPT, 0, 1).applyStereotype(hmodel.keyAttribute);
		
		Class class2_ = TestUtils.createClass(hmodel.pckage, "maClasse2", false);
		class2_.applyStereotype(hmodel.entity);
		TestUtils.createAttribute(class2_, "id", hmodel.integerPT, 0, 1).applyStereotype(hmodel.keyAttribute);

		Property prop = TestUtils.createAttribute(class_, "test", class2_, 0, 1);
		
		String expect = "\nALTER TABLE ONLY \"MA_CLASSE\"\n" + 
				"	ADD CONSTRAINT MA_CLASSE_TEST_IDS_FKEY\n" + 
				"	FOREIGN KEY (\"ID_TEST\") REFERENCES \"MA_CLASSE2\"(\"ID\");\n";
		assertEquals(expect, PackageDatabaseScriptGenerator.generateAssociationForeignKeysEntity(prop, class_));
	}

	@Test
	public void testGenerateAssociationForeignKeysEnum() {
		HornetModel hmodel = HornetModel.initModel();
		Class class_ = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		class_.applyStereotype(hmodel.entity);
		TestUtils.createAttribute(class_, "id", hmodel.integerPT, 0, 1).applyStereotype(hmodel.keyAttribute);
		
		Class class2_ = TestUtils.createClass(hmodel.pckage, "maClasse2", false);
		class2_.applyStereotype(hmodel.nomenclature);

		Property prop = TestUtils.createAttribute(class_, "test", class2_, 0, 1);
		prop.applyStereotype(hmodel.attribute);
		
		String expect = "\nALTER TABLE ONLY \"MA_CLASSE\"\n" + 
				"	ADD CONSTRAINT MA_CLASSE_CODE_TEST_CODE_FKEY \n" + 
				"	FOREIGN KEY (\"CODE_TEST\") REFERENCES \"MA_CLASSE2\"(\"CODE\");\n";
		assertEquals(expect, PackageDatabaseScriptGenerator.generateAssociationForeignKeysEnum(prop, class_, ""));
	}

	@Test
	public void testGenerateAttributType() {
		HornetModel hmodel = HornetModel.initModel();
		Class class_ = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		class_.applyStereotype(hmodel.entity);
		Property prop = TestUtils.createAttribute(class_, "test", hmodel.stringPT, 0, 1);

		String expect = "text";
		assertEquals(expect, PackageDatabaseScriptGenerator.generateAttributType(prop));
	}

	@Test
	public void testGenerateNullable() {
		HornetModel hmodel = HornetModel.initModel();
		Class class_ = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		class_.applyStereotype(hmodel.entity);
		Property prop = TestUtils.createAttribute(class_, "test", hmodel.stringPT, 0, 1);
		
		String expect = "";
		assertEquals(expect, PackageDatabaseScriptGenerator.generateNullable(prop, false).toString());
		
		Property prop2 = TestUtils.createAttribute(class_, "test2", hmodel.stringPT, 1, 1);
		expect = "NOT NULL";
		assertEquals(expect, PackageDatabaseScriptGenerator.generateNullable(prop2, false).toString());
		
		expect = "";
		assertEquals(expect, PackageDatabaseScriptGenerator.generateNullable(prop, true).toString());

	}

	@Test
	public void testGenerateEnumTable() {
		HornetModel hmodel = HornetModel.initModel();
		Class class_ = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		class_.applyStereotype(hmodel.nomenclature);
		
		Property prop = TestUtils.createAttribute(class_, "test", hmodel.stringPT, 0, 1);
		prop.applyStereotype(hmodel.codeLibelleNomenclature);
		
		String expect = "\nCREATE TABLE \"MA_CLASSE\"(\n" + 
				"	\"CODE\" integer NOT NULL,\n" + 
				"	\"LIBELLE\" text\n" + 
				");\n" + 
				"\n" + 
				"ALTER TABLE ONLY \"MA_CLASSE\"\n" + 
				"	ADD CONSTRAINT MA_CLASSE_PKEY PRIMARY KEY (\"CODE\");\n";
		assertEquals(expect, PackageDatabaseScriptGenerator.generateEnumTable(class_).toString());
	}

}
