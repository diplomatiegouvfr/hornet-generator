package fr.gouv.diplomatie.papyrus.codegen.sql.test.xtend.pakkage;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.eclipse.uml2.uml.AggregationKind;
import org.eclipse.uml2.uml.Association;
import org.eclipse.uml2.uml.AssociationClass;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Property;
import org.junit.Test;

import fr.gouv.diplomatie.papyrus.codegen.core.test.HornetModel;
import fr.gouv.diplomatie.papyrus.codegen.core.test.TestUtils;
import fr.gouv.diplomatie.papyrus.codegen.sql.xtend.pakkage.PackageUpdateDatabaseScriptGenerator;

public class PackageUpdateDatabaseScriptGeneratorTest {

	@Test
	public void testGenerateTable() {
		HornetModel hmodel = HornetModel.initModel();
		Class class_ = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		TestUtils.createAttribute(class_, "id", hmodel.integerPT, 0, 1).applyStereotype(hmodel.keyAttribute);
		String expect = "\nCREATE TABLE IF NOT EXISTS \"MA_CLASSE\"();\n" + 
				"\n" + 
				"ALTER TABLE \"MA_CLASSE\" ADD COLUMN IF NOT EXISTS \"ID\" text NOT NULL;\n" + 
				"\n" + 
				"\n" + 
				"ALTER TABLE \"MA_CLASSE\" DROP CONSTRAINT IF EXISTS MA_CLASSE_PKEY CASCADE;\n" + 
				"\n" + 
				"ALTER TABLE ONLY \"MA_CLASSE\"\n" + 
				"	ADD CONSTRAINT MA_CLASSE_PKEY PRIMARY KEY (\"ID\");\n" + 
				"\n";
		
		assertEquals(expect, PackageUpdateDatabaseScriptGenerator.generateTable(class_).toString());
	}

	@Test
	public void testGenerateAttrForeignKey() {
		HornetModel hmodel = HornetModel.initModel();
		Class class_ = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		TestUtils.createAttribute(class_, "id", hmodel.integerPT, 0, 1).applyStereotype(hmodel.keyAttribute);
		
		Class class2_ = TestUtils.createClass(hmodel.pckage, "maClasse2", false);
		TestUtils.createAttribute(class2_, "id2", hmodel.integerPT, 0, 1).applyStereotype(hmodel.keyAttribute);
		Property prop = TestUtils.createAttribute(class2_, "prop", class_, 0, 1);
		
		String expect = "ALTER TABLE \"TABLE\" ADD COLUMN IF NOT EXISTS \"ID2_MA_CLASSE2_PROP\" text ;\n";
		assertEquals(expect, PackageUpdateDatabaseScriptGenerator.generateAttrForeignKey(prop, class_, "TABLE", "").toString());
	}

	@Test
	public void testGenerateAttributesAlterForeignKey() {
		HornetModel hmodel = HornetModel.initModel();
		Class class_ = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		TestUtils.createAttribute(class_, "id", hmodel.integerPT, 0, 1).applyStereotype(hmodel.keyAttribute);
		
		Class class2_ = TestUtils.createClass(hmodel.pckage, "maClasse2", false);
		TestUtils.createAttribute(class2_, "id2", hmodel.integerPT, 0, 1).applyStereotype(hmodel.keyAttribute);
		Property prop = TestUtils.createAttribute(class2_, "prop", class_, 0, 1);
		
		String expect = "\nALTER TABLE \"MA_CLASSE\" DROP CONSTRAINT IF EXISTS MA_CLASSE_MA_CLASSE2_PROP_IDS_FKEY CASCADE;\n" + 
				"\n" + 
				"ALTER TABLE ONLY \"MA_CLASSE\"\n" + 
				"    ADD CONSTRAINT MA_CLASSE_MA_CLASSE2_PROP_IDS_FKEY\n" + 
				"    FOREIGN KEY (\"ID2_MA_CLASSE2_PROP\") REFERENCES \"MA_CLASSE2\"(\"ID2\");\n";
		assertEquals(expect, PackageUpdateDatabaseScriptGenerator.generateAttributesAlterForeignKey(prop, class_, "TABLE").toString());
	}

	@Test
	public void testGenerateExtendId() {
		HornetModel hmodel = HornetModel.initModel();
		Class class_ = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		TestUtils.createAttribute(class_, "id", hmodel.integerPT, 0, 1).applyStereotype(hmodel.keyAttribute);
		String expect = "ALTER TABLE \"TABLE\" ADD COLUMN IF NOT EXISTS \"ID\" text NOT NULL;\n";
		assertEquals(expect, PackageUpdateDatabaseScriptGenerator.generateExtendId(class_, "TABLE").toString());
	}

	@Test
	public void testGenerateIdAttributeDefinition() {
		HornetModel hmodel = HornetModel.initModel();
		Class class_ = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		Property id = TestUtils.createAttribute(class_, "id", hmodel.integerPT, 0, 1);
		id.applyStereotype(hmodel.keyAttribute);
		String expect = "ALTER TABLE \"TABLE\" ADD COLUMN IF NOT EXISTS \"ID\" text NOT NULL;\n";
		assertEquals(expect, PackageUpdateDatabaseScriptGenerator.generateIdAttributeDefinition(id, "", "TABLE", "").toString());
		
		expect = "ALTER TABLE \"TABLE\" ADD COLUMN IF NOT EXISTS \"TEST_ID\" text NOT NULL;\n";
		assertEquals(expect, PackageUpdateDatabaseScriptGenerator.generateIdAttributeDefinition(id, "TEST", "TABLE", "").toString());
	}

	@Test
	public void testGenerateStringLength() {
		HornetModel hmodel = HornetModel.initModel();
		Class class_ = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		Property prop = TestUtils.createAttribute(class_, "prop", hmodel.integerPT, 0, 1);
		prop.applyStereotype(hmodel.attribute);
		
		String expect = "";
		assertEquals(expect, PackageUpdateDatabaseScriptGenerator.generateStringLength(prop).toString());
		
		TestUtils.setStereotypePropertyValue(prop, hmodel.attribute, hmodel.attributeLength, 10);
		TestUtils.setStereotypePropertyValue(prop, hmodel.attribute, hmodel.attributeHasLength, true);
		expect = " varying(10)";
		assertEquals(expect, PackageUpdateDatabaseScriptGenerator.generateStringLength(prop).toString());
	}

	@Test
	public void testGenerateEumAttributesDefinition() {
		HornetModel hmodel = HornetModel.initModel();
		Class class_ = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		TestUtils.createAttribute(class_, "id", hmodel.integerPT, 0, 1).applyStereotype(hmodel.keyAttribute);
		
		Class class2_ = TestUtils.createClass(hmodel.pckage, "maClasse2", false);
		class2_.applyStereotype(hmodel.nomenclature);
		
		Property prop = TestUtils.createAttribute(class_, "prop", class2_, 0, 1);
		
		String expect = "ALTER TABLE \"TABLE\" ADD COLUMN IF NOT EXISTS \"CODE_PROP\" integer ;\n";
		assertEquals(expect, PackageUpdateDatabaseScriptGenerator.generateEumAttributesDefinition(prop, "", class_, false, "TABLE", "").toString());
	}

	@Test
	public void testGenerateBasicAttributeDefinition() {
		HornetModel hmodel = HornetModel.initModel();
		Class class_ = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		TestUtils.createAttribute(class_, "id", hmodel.integerPT, 0, 1).applyStereotype(hmodel.keyAttribute);
		Property prop = TestUtils.createAttribute(class_, "prop", hmodel.stringPT, 0, 1);
		
		String expect = "ALTER TABLE \"TABLE\" ADD COLUMN IF NOT EXISTS \"PROP\" text ;\n";
		assertEquals(expect, PackageUpdateDatabaseScriptGenerator.generateBasicAttributeDefinition(prop, "", class_, false, "TABLE", "").toString());
	}

	@Test
	public void testGenerateEntityAttributeDefinition() {
		HornetModel hmodel = HornetModel.initModel();
		Class class_ = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		TestUtils.createAttribute(class_, "id", hmodel.integerPT, 0, 1).applyStereotype(hmodel.keyAttribute);
		
		Class class2_ = TestUtils.createClass(hmodel.pckage, "maClasse2", false);
		class2_.applyStereotype(hmodel.entity);
		Property id = TestUtils.createAttribute(class2_, "id", hmodel.integerPT, 0, 1);
		id.applyStereotype(hmodel.keyAttribute);

		Property prop = TestUtils.createAttribute(class_, "prop", class2_, 0, 1);
		
		String expect = "ALTER TABLE \"TABLE\" ADD COLUMN IF NOT EXISTS \"ID_PROP\" text ;\n";
		assertEquals(expect, PackageUpdateDatabaseScriptGenerator.generateEntityAttributeDefinition(prop, id,  "", class_, false, "TABLE", "").toString());
	}

	@Test
	public void testGenerateIds() {
		HornetModel hmodel = HornetModel.initModel();
		Class class_ = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		TestUtils.createAttribute(class_, "id", hmodel.integerPT, 0, 1).applyStereotype(hmodel.keyAttribute);
		
		String expect = "\nALTER TABLE \"MA_CLASSE\" DROP CONSTRAINT IF EXISTS MA_CLASSE_PKEY CASCADE;\n" + 
				"\n" + 
				"ALTER TABLE ONLY \"MA_CLASSE\"\n" + 
				"	ADD CONSTRAINT MA_CLASSE_PKEY PRIMARY KEY (\"ID\");\n";
		assertEquals(expect, PackageUpdateDatabaseScriptGenerator.generateIds(class_).toString());
	}
	
	@Test
	public void testMultipleGenerateIds() {
		HornetModel hmodel = HornetModel.initModel();
		Class class_ = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		TestUtils.createAttribute(class_, "id", hmodel.integerPT, 0, 1).applyStereotype(hmodel.keyAttribute);
		TestUtils.createAttribute(class_, "id2", hmodel.integerPT, 0, 1).applyStereotype(hmodel.keyAttribute);
		
		String expect = "\nALTER TABLE \"MA_CLASSE\" DROP CONSTRAINT IF EXISTS MA_CLASSE_PKEY CASCADE;\n" + 
				"\n" + 
				"ALTER TABLE ONLY \"MA_CLASSE\"\n" + 
				"	ADD CONSTRAINT MA_CLASSE_PKEY PRIMARY KEY (\"ID\", \"ID2\");\n";
		assertEquals(expect, PackageUpdateDatabaseScriptGenerator.generateIds(class_).toString());
	}

	@Test
	public void testGenerateExtendForeignKey() {
		HornetModel hmodel = HornetModel.initModel();
		Class class_ = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		class_.applyStereotype(hmodel.entity);
		TestUtils.createAttribute(class_, "id", hmodel.integerPT, 0, 1).applyStereotype(hmodel.keyAttribute);

		Class class2_ = TestUtils.createClass(hmodel.pckage, "maClasse2", false);
		class2_.applyStereotype(hmodel.entity);
		TestUtils.createAttribute(class2_, "id2", hmodel.integerPT, 0, 1).applyStereotype(hmodel.keyAttribute);
		
		String expect = "ALTER TABLE \"MA_CLASSE\" DROP CONSTRAINT IF EXISTS MA_CLASSE_MA_CLASSE2_IDS_FKEY CASCADE;\n" + 
				"\n" + 
				"ALTER TABLE ONLY \"MA_CLASSE\"\n" + 
				"    ADD CONSTRAINT MA_CLASSE_MA_CLASSE2_IDS_FKEY\n" + 
				"    FOREIGN KEY (\"ID2\") REFERENCES \"MA_CLASSE2\"(\"ID2\");\n";
		assertEquals(expect, PackageUpdateDatabaseScriptGenerator.generateExtendForeignKey(class2_, class_).toString());

	}

	@Test
	public void testGenerateForeignKey() {
		HornetModel hmodel = HornetModel.initModel();
		Class class_ = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		class_.applyStereotype(hmodel.entity);
		TestUtils.createAttribute(class_, "id", hmodel.integerPT, 0, 1).applyStereotype(hmodel.keyAttribute);

		Class class2_ = TestUtils.createClass(hmodel.pckage, "maClasse2", false);
		class2_.applyStereotype(hmodel.entity);
		TestUtils.createAttribute(class2_, "id2", hmodel.integerPT, 0, 1).applyStereotype(hmodel.keyAttribute);
		
		Property prop = TestUtils.createAttribute(class_, "prop", class2_, 0, 1);
		
		String expect = "\nALTER TABLE \"TABLE\" DROP CONSTRAINT IF EXISTS TABLE_PROP_IDS_FKEY CASCADE;\n" + 
				"\n" + 
				"ALTER TABLE ONLY \"TABLE\"\n" + 
				"    ADD CONSTRAINT TABLE_PROP_IDS_FKEY \n" + 
				"    FOREIGN KEY (\"ID2_PROP\") REFERENCES \"MA_CLASSE2\"(\"ID2\");\n";
		assertEquals(expect, PackageUpdateDatabaseScriptGenerator.generateForeignKey(prop, "", "TABLE", "").toString());
		
		expect = "\nALTER TABLE \"TABLE\" DROP CONSTRAINT IF EXISTS TABLE_TEST_PROP_IDS_FKEY CASCADE;\n" + 
				"\n" + 
				"ALTER TABLE ONLY \"TABLE\"\n" + 
				"    ADD CONSTRAINT TABLE_TEST_PROP_IDS_FKEY \n" + 
				"    FOREIGN KEY (\"TEST_ID2_PROP\") REFERENCES \"MA_CLASSE2\"(\"ID2\");\n";
		assertEquals(expect, PackageUpdateDatabaseScriptGenerator.generateForeignKey(prop, "TEST", "TABLE", "").toString());
		
	}

	@Test
	public void testGenerateEnumForeignKey() {
		HornetModel hmodel = HornetModel.initModel();
		Class class_ = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		class_.applyStereotype(hmodel.entity);
		TestUtils.createAttribute(class_, "id", hmodel.integerPT, 0, 1).applyStereotype(hmodel.keyAttribute);

		Class class2_ = TestUtils.createClass(hmodel.pckage, "maClasse2", false);
		class2_.applyStereotype(hmodel.nomenclature);	
		TestUtils.createAttribute(class_, "test", hmodel.stringPT, 0, 1);
		
		Property prop = TestUtils.createAttribute(class_, "prop", class2_, 0, 1);
		
		String expect = "\nALTER TABLE \"TABLE\" DROP CONSTRAINT IF EXISTS TABLE_PROP_CODE_FKEY CASCADE;\n" + 
				"\n" + 
				"ALTER TABLE ONLY \"TABLE\"\n" + 
				"    ADD CONSTRAINT TABLE_PROP_CODE_FKEY \n" + 
				"    FOREIGN KEY (\"CODE_PROP\") REFERENCES \"MA_CLASSE2\"(\"CODE\");\n";
		assertEquals(expect, PackageUpdateDatabaseScriptGenerator.generateEnumForeignKey(prop, "", "TABLE", "").toString());
		
		expect = "\nALTER TABLE \"TABLE\" DROP CONSTRAINT IF EXISTS TABLE_TEST_PROP_CODE_FKEY CASCADE;\n" + 
				"\n" + 
				"ALTER TABLE ONLY \"TABLE\"\n" + 
				"    ADD CONSTRAINT TABLE_TEST_PROP_CODE_FKEY \n" + 
				"    FOREIGN KEY (\"CODE_TEST_PROP\") REFERENCES \"MA_CLASSE2\"(\"CODE\");\n";
		assertEquals(expect, PackageUpdateDatabaseScriptGenerator.generateEnumForeignKey(prop, "TEST", "TABLE", "").toString());
		
	}

	@Test
	public void testGenerateMutilvaluedPTTable() {
		HornetModel hmodel = HornetModel.initModel();
		Class class_ = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		class_.applyStereotype(hmodel.entity);
		TestUtils.createAttribute(class_, "id", hmodel.integerPT, 0, 1).applyStereotype(hmodel.keyAttribute);
		
		Property prop = TestUtils.createAttribute(class_, "prop", hmodel.stringPT, 0, -1);
		
		String expect = "\nCREATE TABLE IF NOT EXISTS \"MA_CLASSE_PROP\"();\n" + 
				"\n" + 
				"ALTER TABLE \"MA_CLASSE_PROP\" ADD COLUMN IF NOT EXISTS \"PROP\" text NOT NULL;\n" + 
				"ALTER TABLE \"MA_CLASSE_PROP\" ADD COLUMN IF NOT EXISTS \"ID\" text NOT NULL;\n" + 
				"\n" + 
				"ALTER TABLE \"MA_CLASSE_PROP\" DROP CONSTRAINT IF EXISTS MA_CLASSE_PROP_IDS_FKEY CASCADE;\n" + 
				"\n" + 
				"ALTER TABLE ONLY \"MA_CLASSE_PROP\"\n" + 
				"    ADD CONSTRAINT MA_CLASSE_PROP_IDS_FKEY\n" + 
				"    FOREIGN KEY (\"ID\") REFERENCES \"MA_CLASSE\"(\"ID\");\n" + 
				"    \n" + 
				"ALTER TABLE \"MA_CLASSE_PROP\" DROP CONSTRAINT IF EXISTS MA_CLASSE_PROP_PKEY CASCADE;\n" + 
				"\n" + 
				"ALTER TABLE ONLY \"MA_CLASSE_PROP\"\n" + 
				"    ADD CONSTRAINT MA_CLASSE_PROP_PKEY PRIMARY KEY(\"ID\", \"PROP\");\n";
		assertEquals(expect, PackageUpdateDatabaseScriptGenerator.generateMutilvaluedPTTable(prop, class_).toString());
		
	}

	@Test
	public void testGenerateMutilvaluedEntityTable() {
		HornetModel hmodel = HornetModel.initModel();
		Class class_ = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		class_.applyStereotype(hmodel.entity);
		TestUtils.createAttribute(class_, "id", hmodel.integerPT, 0, 1).applyStereotype(hmodel.keyAttribute);
		
		Class class2_ = TestUtils.createClass(hmodel.pckage, "maClasse2", false);
		class2_.applyStereotype(hmodel.entity);
		TestUtils.createAttribute(class2_, "id2", hmodel.integerPT, 0, 1).applyStereotype(hmodel.keyAttribute);
		
		Association asso = class_.createAssociation(true, AggregationKind.NONE_LITERAL, "test", 0, -1, class2_, true, AggregationKind.NONE_LITERAL, "test2", 0, -1);
		Property prop = class_.getAttribute("test", class2_);
		prop.setAssociation(asso);
		
		String expect = "\nCREATE TABLE IF NOT EXISTS \"MA_CLASSE_TEST\"();\n" + 
				"ALTER TABLE \"MA_CLASSE_TEST\" ADD COLUMN IF NOT EXISTS \"ID2_TEST\" text NOT NULL;\n" + 
				"ALTER TABLE \"MA_CLASSE_TEST\" ADD COLUMN IF NOT EXISTS \"ID_MA_CLASSE\" text NOT NULL;\n" + 
				"\n" + 
				"ALTER TABLE \"MA_CLASSE_TEST\" DROP CONSTRAINT IF EXISTS MA_CLASSE_TEST_MA_CLASSE_IDS_FKEY CASCADE;\n" + 
				"\n" + 
				"ALTER TABLE ONLY \"MA_CLASSE_TEST\"\n" + 
				"    ADD CONSTRAINT MA_CLASSE_TEST_MA_CLASSE_IDS_FKEY\n" + 
				"    FOREIGN KEY (\"ID_MA_CLASSE\") REFERENCES \"MA_CLASSE\"(\"ID\");\n" + 
				"    \n" + 
				"ALTER TABLE \"MA_CLASSE_TEST\" DROP CONSTRAINT IF EXISTS MA_CLASSE_TEST_MA_CLASSE2_IDS_FKEY CASCADE;\n" + 
				"    \n" + 
				"ALTER TABLE ONLY \"MA_CLASSE_TEST\"\n" + 
				"    ADD CONSTRAINT MA_CLASSE_TEST_MA_CLASSE2_IDS_FKEY\n" + 
				"    FOREIGN KEY (\"ID2_TEST\") REFERENCES \"MA_CLASSE2\"(\"ID2\");\n" + 
				"    \n" + 
				"ALTER TABLE \"MA_CLASSE_TEST\" DROP CONSTRAINT IF EXISTS MA_CLASSE_TEST_PKEY CASCADE;\n" + 
				"    \n" + 
				"ALTER TABLE ONLY \"MA_CLASSE_TEST\"\n" + 
				"    ADD CONSTRAINT MA_CLASSE_TEST_PKEY PRIMARY KEY(\"ID_MA_CLASSE\", \"ID2_TEST\");\n";
		assertEquals(expect, PackageUpdateDatabaseScriptGenerator.generateMutilvaluedEntityTable(prop, class_).toString());
		
	}

	@Test
	public void testGenerateMultiIdAttributeDef() {
		HornetModel hmodel = HornetModel.initModel();
		Class class_ = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		class_.applyStereotype(hmodel.entity);
		Property prop = TestUtils.createAttribute(class_, "id", hmodel.integerPT, 0, 1);
		prop.applyStereotype(hmodel.keyAttribute);
		
		String expect = "ALTER TABLE \"TABLE\" ADD COLUMN IF NOT EXISTS \"ID\" text NOT NULL;\n";
		assertEquals(expect, PackageUpdateDatabaseScriptGenerator.generateMultiIdAttributeDef(prop, "", "TABLE", "").toString());
		
		expect = "ALTER TABLE \"TABLE\" ADD COLUMN IF NOT EXISTS \"ID_TEST\" text NOT NULL;\n";
		assertEquals(expect, PackageUpdateDatabaseScriptGenerator.generateMultiIdAttributeDef(prop, "TEST", "TABLE", "").toString());
	}

	@Test
	public void testGenerateMultiIdAttributeDefinition() {
		HornetModel hmodel = HornetModel.initModel();
		Class class_ = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		class_.applyStereotype(hmodel.entity);
		Property prop = TestUtils.createAttribute(class_, "id", hmodel.integerPT, 0, 1);
		prop.applyStereotype(hmodel.keyAttribute);
		
		String expect = "ALTER TABLE \"TABLE\" ADD COLUMN IF NOT EXISTS \"ID_MA_CLASSE\" text NOT NULL;\n";
		assertEquals(expect, PackageUpdateDatabaseScriptGenerator.generateMultiIdAttributeDefinition(prop, "", "TABLE", "").toString());
		
		expect = "ALTER TABLE \"TABLE\" ADD COLUMN IF NOT EXISTS \"TEST_ID_MA_CLASSE\" text NOT NULL;\n";
		assertEquals(expect, PackageUpdateDatabaseScriptGenerator.generateMultiIdAttributeDefinition(prop, "TEST", "TABLE", "").toString());
	}

	@Test
	public void testGenerateValueObjectEntityTable() {
		HornetModel hmodel = HornetModel.initModel();
		Class class_ = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		class_.applyStereotype(hmodel.entity);
		Property id = TestUtils.createAttribute(class_, "id", hmodel.integerPT, 0, 1);
		id.applyStereotype(hmodel.keyAttribute);

		Class class2_ = TestUtils.createClass(hmodel.pckage, "maClasse2", false);
		class2_.applyStereotype(hmodel.valueObject);
		TestUtils.createAttribute(class2_, "test", hmodel.stringPT, 0, 1);
		
		Property prop = TestUtils.createAttribute(class_, "id", class2_, 0, 1);
		
		String expect = "\nCREATE TABLE IF NOT EXISTS \"MA_CLASSE_ID\"();\n" + 
				"ALTER TABLE \"MA_CLASSE_ID\" ADD COLUMN IF NOT EXISTS \"ID_TEST\" text ;\n" + 
				"ALTER TABLE \"MA_CLASSE_ID\" ADD COLUMN IF NOT EXISTS \"ID\" text NOT NULL;\n" + 
				"\n" + 
				"ALTER TABLE ONLY \"MA_CLASSE_ID\" DROP CONSTRAINT IF EXISTS MA_CLASSE_ID_IDS_FKEY CASCADE;\n" + 
				"\n" + 
				"ALTER TABLE ONLY \"MA_CLASSE_ID\"\n" + 
				"    ADD CONSTRAINT MA_CLASSE_ID_IDS_FKEY\n" + 
				"    FOREIGN KEY (\"ID\") REFERENCES \"MA_CLASSE\"(\"ID\");\n" + 
				"\n" + 
				"ALTER TABLE ONLY \"MA_CLASSE_ID\" DROP CONSTRAINT IF EXISTS MA_CLASSE_ID_PKEY CASCADE;\n" + 
				"\n" + 
				"ALTER TABLE ONLY \"MA_CLASSE_ID\"\n" + 
				"    ADD CONSTRAINT MA_CLASSE_ID_PKEY PRIMARY KEY(\"ID\");\n\n";
		assertEquals(expect, PackageUpdateDatabaseScriptGenerator.generateValueObjectEntityTable(prop, class_).toString());
	}

	@Test
	public void testGetAttributList() {
		HornetModel hmodel = HornetModel.initModel();
		Class class_ = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		class_.applyStereotype(hmodel.entity);
		Property id = TestUtils.createAttribute(class_, "id", hmodel.integerPT, 1, 1);
		id.applyStereotype(hmodel.keyAttribute);
		TestUtils.createAttribute(class_, "prop", hmodel.integerPT, 1, 1);
		
		ArrayList<String> names = new ArrayList<String>();
		ArrayList<String> expect = new ArrayList<String>();
		expect.add("ID");
		expect.add("PROP");
		assertEquals(expect, PackageUpdateDatabaseScriptGenerator.getAttributList(class_, names , ""));
		
		names = new ArrayList<String>();
		expect = new ArrayList<String>();
		expect.add("TEST_ID");
		expect.add("TEST_PROP");
		assertEquals(expect, PackageUpdateDatabaseScriptGenerator.getAttributList(class_, names , "TEST"));
		
	}

	@Test
	public void testGenerateEnumAttributTable() {
		HornetModel hmodel = HornetModel.initModel();
		Class class_ = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		class_.applyStereotype(hmodel.entity);
		Property id = TestUtils.createAttribute(class_, "id", hmodel.integerPT, 1, 1);
		id.applyStereotype(hmodel.keyAttribute);
		
		Class class2_ = TestUtils.createClass(hmodel.pckage, "maClasse2", false);
		class2_.applyStereotype(hmodel.nomenclature);
		
		Property prop = TestUtils.createAttribute(class_, "prop", class2_, 0, -1);
		
		String expect = "\nCREATE TABLE IF NOT EXISTS \"MA_CLASSE_PROP\"();\n" + 
				"\n" + 
				"ALTER TABLE \"MA_CLASSE_PROP\" ADD COLUMN IF NOT EXISTS \"CODE\" integer NOT NULL;\n" + 
				"ALTER TABLE \"MA_CLASSE_PROP\" ADD COLUMN IF NOT EXISTS \"ID\" text NOT NULL;\n" + 
				"\n" + 
				"ALTER TABLE ONLY \"MA_CLASSE_PROP\" DROP CONSTRAINT IF EXISTS MA_CLASSE_PROP_IDS_FKEY CASCADE;\n" + 
				"\n" + 
				"ALTER TABLE ONLY \"MA_CLASSE_PROP\"\n" + 
				"    ADD CONSTRAINT MA_CLASSE_PROP_IDS_FKEY\n" + 
				"    FOREIGN KEY (\"ID\") REFERENCES \"MA_CLASSE\"(\"ID\");\n" + 
				"\n" + 
				"ALTER TABLE ONLY \"MA_CLASSE_PROP\" DROP CONSTRAINT IF EXISTS MA_CLASSE_PROP_CODE_FKEY CASCADE;\n" + 
				"\n" + 
				"ALTER TABLE ONLY \"MA_CLASSE_PROP\"\n" + 
				"    ADD CONSTRAINT MA_CLASSE_PROP_CODE_FKEY\n" + 
				"    FOREIGN KEY (\"CODE\") REFERENCES \"MA_CLASSE2\"(\"CODE\");\n" + 
				"    \n" + 
				"ALTER TABLE ONLY \"MA_CLASSE_PROP\" DROP CONSTRAINT IF EXISTS MA_CLASSE_PROP_PKEY CASCADE;\n" + 
				"\n" + 
				"ALTER TABLE ONLY \"MA_CLASSE_PROP\"\n" + 
				"    ADD CONSTRAINT MA_CLASSE_PROP_PKEY PRIMARY KEY (\"ID\", \"CODE\");\n";
		assertEquals(expect, PackageUpdateDatabaseScriptGenerator.generateEnumAttributTable(prop, class_).toString());
	}

	@Test
	public void testGenerateSequence() {
		HornetModel hmodel = HornetModel.initModel();
		Class class_ = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		class_.applyStereotype(hmodel.entity);
		Property id = TestUtils.createAttribute(class_, "id", hmodel.integerPT, 1, 1);
		id.applyStereotype(hmodel.keyAttribute);
		id.applyStereotype(hmodel.sequence);
		
		String expect = "CREATE SEQUENCE IF NOT EXISTS \"MA_CLASSE_ID_SEQ\"\n" + 
				"    START WITH 0\n" + 
				"    INCREMENT BY 0\n" + 
				"    NO MAXVALUE\n" + 
				"    NO MINVALUE\n" + 
				"    CACHE 0\n" + 
				"    NO CYCLE;\n" + 
				"    \n" + 
				"ALTER SEQUENCE \"MA_CLASSE_ID_SEQ\"\n" + 
				"	OWNED BY \"MA_CLASSE\".\"ID\";\n" + 
				"\n" + 
				"ALTER TABLE ONLY \"MA_CLASSE\" \n" + 
				"	ALTER COLUMN \"ID\" \n" + 
				"	SET DEFAULT nextval('\"MA_CLASSE_ID_SEQ\"'::regclass);\n";
		assertEquals(expect, PackageUpdateDatabaseScriptGenerator.generateSequence(id).toString());	
	}

	@Test
	public void testGenerateAssociationTable() {
		HornetModel hmodel = HornetModel.initModel();
		Class class_ = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		class_.applyStereotype(hmodel.entity);
		Property id = TestUtils.createAttribute(class_, "id", hmodel.integerPT, 1, 1);
		id.applyStereotype(hmodel.keyAttribute);
		
		Class class2_ = TestUtils.createClass(hmodel.pckage, "maClasse2", false);
		class2_.applyStereotype(hmodel.entity);
		Property id2 = TestUtils.createAttribute(class2_, "id2", hmodel.integerPT, 1, 1);
		id2.applyStereotype(hmodel.keyAttribute);
		
		AssociationClass asso = TestUtils.createAssociationClass(class_, class2_, "test", "test2", "asso");
		
		String expect = "\nCREATE TABLE IF NOT EXISTS \"ASSO\"();\n" + 
				"\n" + 
				"ALTER TABLE \"ASSO\" ADD COLUMN IF NOT EXISTS \"ID2_TEST\" text ;\n" + 
				"ALTER TABLE \"ASSO\" ADD COLUMN IF NOT EXISTS \"ID_TEST2\" text ;\n" + 
				"\n" + 
				"\n" + 
				"ALTER TABLE \"ASSO\" DROP CONSTRAINT IF EXISTS ASSO_TEST_IDS_FKEY CASCADE;\n" + 
				"\n" + 
				"ALTER TABLE ONLY \"ASSO\"\n" + 
				"	ADD CONSTRAINT ASSO_TEST_IDS_FKEY\n" + 
				"	FOREIGN KEY (\"ID2_TEST\") REFERENCES \"MA_CLASSE2\"(\"ID2\");\n" + 
				"\n" + 
				"ALTER TABLE \"ASSO\" DROP CONSTRAINT IF EXISTS ASSO_TEST2_IDS_FKEY CASCADE;\n" + 
				"\n" + 
				"ALTER TABLE ONLY \"ASSO\"\n" + 
				"	ADD CONSTRAINT ASSO_TEST2_IDS_FKEY\n" + 
				"	FOREIGN KEY (\"ID_TEST2\") REFERENCES \"MA_CLASSE\"(\"ID\");\n" + 
				"\n" + 
				"ALTER TABLE \"ASSO\" DROP CONSTRAINT IF EXISTS ASSO_PKEY CASCADE;\n" + 
				"\n" + 
				"ALTER TABLE ONLY \"ASSO\"\n" + 
				"	ADD CONSTRAINT ASSO_PKEY PRIMARY KEY (\"ID2_TEST\", \"ID_TEST2\");\n";
		assertEquals(expect, PackageUpdateDatabaseScriptGenerator.generateAssociationTable(asso).toString());	
	}

	@Test
	public void testGetAssociationAttributList() {
		HornetModel hmodel = HornetModel.initModel();
		Class class_ = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		class_.applyStereotype(hmodel.entity);
		Property id = TestUtils.createAttribute(class_, "id", hmodel.integerPT, 1, 1);
		id.applyStereotype(hmodel.keyAttribute);
		
		Class class2_ = TestUtils.createClass(hmodel.pckage, "maClasse2", false);
		class2_.applyStereotype(hmodel.entity);
		Property id2 = TestUtils.createAttribute(class2_, "id2", hmodel.integerPT, 1, 1);
		id2.applyStereotype(hmodel.keyAttribute);
		
		AssociationClass asso = TestUtils.createAssociationClass(class_, class2_, "test", "test2", "asso");
		
		ArrayList<String> names = new ArrayList<String>();
		ArrayList<String> expect = new ArrayList<String>();
		expect.add("ID2_TEST");
		expect.add("ID_TEST2");
		assertEquals(expect, PackageUpdateDatabaseScriptGenerator.getAssociationAttributList(asso, names , ""));
		
		names = new ArrayList<String>();
		expect = new ArrayList<String>();
		expect.add("ADD_ID2_TEST");
		expect.add("ADD_ID_TEST2");
		assertEquals(expect, PackageUpdateDatabaseScriptGenerator.getAssociationAttributList(asso, names , "ADD"));
	}

	@Test
	public void testGenerateAssociationForeignKeysEntity() {
		/*HornetModel hmodel = HornetModel.initModel();
		Class class_ = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		class_.applyStereotype(hmodel.entity);
		Property id = TestUtils.createAttribute(class_, "id", hmodel.integerPT, 1, 1);
		id.applyStereotype(hmodel.keyAttribute);
		
		Class class2_ = TestUtils.createClass(hmodel.pckage, "maClasse2", false);
		class2_.applyStereotype(hmodel.entity);
		Property id2 = TestUtils.createAttribute(class2_, "id2", hmodel.integerPT, 1, 1);
		id2.applyStereotype(hmodel.keyAttribute);
		
		Association asso = class_.createAssociation(true, AggregationKind.NONE_LITERAL, "test", 0, 1, class2_, true, AggregationKind.NONE_LITERAL, "test2", 0, 1);
		Property prop = class_.getAttribute("test", class2_);
		prop.setAssociation(asso);
		
		String expect = "\nALTER TABLE \"MA_CLASSE\" DROP CONSTRAINT IF EXISTS MA_CLASSE_TEST_IDS_FKEY CASCADE;\n" + 
				"\n" + 
				"ALTER TABLE ONLY \"MA_CLASSE\"\n" + 
				"	ADD CONSTRAINT MA_CLASSE_TEST_IDS_FKEY\n" + 
				"	FOREIGN KEY (\"ID2_TEST\") REFERENCES \"MA_CLASSE2\"(\"ID2\");\n";
		assertEquals(expect, PackageUpdateDatabaseScriptGenerator.generateAssociationForeignKeysEntity(prop, class_).toString());*/
	}

	@Test
	public void testGenerateAssociationForeignKeysEnum() {
		/*HornetModel hmodel = HornetModel.initModel();
		Class class_ = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		class_.applyStereotype(hmodel.entity);
		Property id = TestUtils.createAttribute(class_, "id", hmodel.integerPT, 1, 1);
		id.applyStereotype(hmodel.keyAttribute);
		
		Class class2_ = TestUtils.createClass(hmodel.pckage, "maClasse2", false);
		class2_.applyStereotype(hmodel.nomenclature);
		
		Association asso = class_.createAssociation(true, AggregationKind.NONE_LITERAL, "test", 0, 1, class2_, true, AggregationKind.NONE_LITERAL, "test2", 0, 1);
		Property prop = class_.getAttribute("test", class2_);
		prop.setAssociation(asso);
		
		String expect = "ALTER TABLE \"MA_CLASSE\" DROP CONSTRAINT IF EXISTS MA_CLASSE_CODE_TEST_CODE_FKEY CASCADE;\n" + 
				"\n" + 
				"ALTER TABLE ONLY \"MA_CLASSE\"\n" + 
				"	ADD CONSTRAINT MA_CLASSE_CODE_TEST_CODE_FKEY \n" + 
				"	FOREIGN KEY (\"CODE_TEST\") REFERENCES \"MA_CLASSE2\"(\"CODE\");\n";
		assertEquals(expect, PackageUpdateDatabaseScriptGenerator.generateAssociationForeignKeysEnum(prop, class_).toString());*/
	}

	@Test
	public void testGenerateAttributType() {
		HornetModel hmodel = HornetModel.initModel();
		Class class_ = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		class_.applyStereotype(hmodel.entity);
		Property prop = TestUtils.createAttribute(class_, "id", hmodel.stringPT, 1, 1);
		
		String expect = "text";
		assertEquals(expect, PackageUpdateDatabaseScriptGenerator.generateAttributType(prop).toString());
	}

	@Test
	public void testGenerateNullable() {
		HornetModel hmodel = HornetModel.initModel();
		Class class_ = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		class_.applyStereotype(hmodel.entity);
		Property prop = TestUtils.createAttribute(class_, "id", hmodel.stringPT, 1, 1);
		
		String expect = "NOT NULL";
		assertEquals(expect, PackageUpdateDatabaseScriptGenerator.generateNullable(prop, false).toString());
		
		expect = "";
		assertEquals(expect, PackageUpdateDatabaseScriptGenerator.generateNullable(prop, true).toString());
		
		Property prop2 = TestUtils.createAttribute(class_, "id2", hmodel.stringPT, 0, 1);
		expect = "";
		assertEquals(expect, PackageUpdateDatabaseScriptGenerator.generateNullable(prop2, false).toString());
	}

	@Test
	public void testGenerateEnumTable() {
		HornetModel hmodel = HornetModel.initModel();
		Class class_ = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		class_.applyStereotype(hmodel.nomenclature);
		Property prop = TestUtils.createAttribute(class_, "prop", hmodel.stringPT, 1, 1);
		prop.applyStereotype(hmodel.codeLibelleNomenclature);
		
		String expect = "\nCREATE TABLE IF NOT EXISTS \"MA_CLASSE\"();\n" + 
				"\n" + 
				"ALTER TABLE \"MA_CLASSE\" ADD COLUMN IF NOT EXISTS \"CODE\" integer NOT NULL;\n" + 
				"ALTER TABLE \"MA_CLASSE\" ADD COLUMN IF NOT EXISTS \"LIBELLE\" text;\n" + 
				"\n" + 
				"ALTER TABLE \"MA_CLASSE\" DROP CONSTRAINT IF EXISTS MA_CLASSE_PKEY CASCADE;\n" + 
				"\n" + 
				"ALTER TABLE ONLY \"MA_CLASSE\"\n" + 
				"	ADD CONSTRAINT MA_CLASSE_PKEY PRIMARY KEY (\"CODE\");\n";
		assertEquals(expect, PackageUpdateDatabaseScriptGenerator.generateEnumTable(class_).toString());
		
	}

}
