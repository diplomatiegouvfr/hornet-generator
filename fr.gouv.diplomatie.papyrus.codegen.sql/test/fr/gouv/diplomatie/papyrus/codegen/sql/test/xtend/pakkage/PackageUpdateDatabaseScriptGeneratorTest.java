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
		String expect = "\nCREATE TABLE IF NOT EXISTS ma_classe();\n" + 
				"\n" + 
				"ALTER TABLE ma_classe ADD COLUMN IF NOT EXISTS id text NOT NULL;\n" + 
				"\n" + 
				"\n" + 
				"ALTER TABLE ma_classe DROP CONSTRAINT IF EXISTS ma_classe_pkey CASCADE;\n" + 
				"\n" + 
				"ALTER TABLE ONLY ma_classe\n" + 
				"	ADD CONSTRAINT ma_classe_pkey PRIMARY KEY (id);\n" + 
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
		
		String expect = "ALTER TABLE TABLE ADD COLUMN IF NOT EXISTS id_prop text ;\n";
		assertEquals(expect, PackageUpdateDatabaseScriptGenerator.generateAttrForeignKey(prop, class_, "TABLE", "").toString());
	}

	@Test
	public void testGenerateAttributesAlterForeignKey() {
		HornetModel hmodel = HornetModel.initModel();
		Class class_ = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		TestUtils.createAttribute(class_, "id", hmodel.integerPT, 0, 1).applyStereotype(hmodel.keyAttribute);
		
		Class class2_ = TestUtils.createClass(hmodel.pckage, "maClasse2", false);
		TestUtils.createAttribute(class2_, "id2", hmodel.integerPT, 0, 1).applyStereotype(hmodel.keyAttribute);
		Property prop = TestUtils.createAttribute(class_, "prop", class2_, 0, 1);
		
		String expect = "\nALTER TABLE ma_classe DROP CONSTRAINT IF EXISTS ma_classe_prop_ids_fkey CASCADE;\n" + 
				"\n" + 
				"ALTER TABLE ONLY ma_classe\n" + 
				"    ADD CONSTRAINT ma_classe_prop_ids_fkey\n" + 
				"    FOREIGN KEY (id2_prop) REFERENCES ma_classe2(id2);\n";
		assertEquals(expect, PackageUpdateDatabaseScriptGenerator.generateAttributesAlterForeignKey(prop, class_, "TABLE").toString());
	}

	@Test
	public void testGenerateExtendId() {
		HornetModel hmodel = HornetModel.initModel();
		Class class_ = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		TestUtils.createAttribute(class_, "id", hmodel.integerPT, 0, 1).applyStereotype(hmodel.keyAttribute);
		String expect = "ALTER TABLE TABLE ADD COLUMN IF NOT EXISTS id text NOT NULL;\n";
		assertEquals(expect, PackageUpdateDatabaseScriptGenerator.generateExtendId(class_, "TABLE").toString());
	}

	@Test
	public void testGenerateIdAttributeDefinition() {
		HornetModel hmodel = HornetModel.initModel();
		Class class_ = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		Property id = TestUtils.createAttribute(class_, "id", hmodel.integerPT, 0, 1);
		id.applyStereotype(hmodel.keyAttribute);
		String expect = "ALTER TABLE TABLE ADD COLUMN IF NOT EXISTS id text NOT NULL;\n";
		assertEquals(expect, PackageUpdateDatabaseScriptGenerator.generateIdAttributeDefinition(id, "", "TABLE", "").toString());
		
		expect = "ALTER TABLE TABLE ADD COLUMN IF NOT EXISTS TEST_id text NOT NULL;\n";
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
		
		String expect = "ALTER TABLE TABLE ADD COLUMN IF NOT EXISTS code_prop integer ;\n";
		assertEquals(expect, PackageUpdateDatabaseScriptGenerator.generateEumAttributesDefinition(prop, "", class_, false, "TABLE", "").toString());
	}

	@Test
	public void testGenerateBasicAttributeDefinition() {
		HornetModel hmodel = HornetModel.initModel();
		Class class_ = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		TestUtils.createAttribute(class_, "id", hmodel.integerPT, 0, 1).applyStereotype(hmodel.keyAttribute);
		Property prop = TestUtils.createAttribute(class_, "prop", hmodel.stringPT, 0, 1);
		
		String expect = "ALTER TABLE TABLE ADD COLUMN IF NOT EXISTS prop text ;\n";
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
		
		String expect = "ALTER TABLE TABLE ADD COLUMN IF NOT EXISTS id_prop text ;\n";
		assertEquals(expect, PackageUpdateDatabaseScriptGenerator.generateEntityAttributeDefinition(prop, id,  "", class_, false, "TABLE", "").toString());
	}

	@Test
	public void testGenerateIds() {
		HornetModel hmodel = HornetModel.initModel();
		Class class_ = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		TestUtils.createAttribute(class_, "id", hmodel.integerPT, 0, 1).applyStereotype(hmodel.keyAttribute);
		
		String expect = "\nALTER TABLE ma_classe DROP CONSTRAINT IF EXISTS ma_classe_pkey CASCADE;\n" + 
				"\n" + 
				"ALTER TABLE ONLY ma_classe\n" + 
				"	ADD CONSTRAINT ma_classe_pkey PRIMARY KEY (id);\n";
		assertEquals(expect, PackageUpdateDatabaseScriptGenerator.generateIds(class_).toString());
	}
	
	@Test
	public void testMultipleGenerateIds() {
		HornetModel hmodel = HornetModel.initModel();
		Class class_ = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		TestUtils.createAttribute(class_, "id", hmodel.integerPT, 0, 1).applyStereotype(hmodel.keyAttribute);
		TestUtils.createAttribute(class_, "id2", hmodel.integerPT, 0, 1).applyStereotype(hmodel.keyAttribute);
		
		String expect = "\nALTER TABLE ma_classe DROP CONSTRAINT IF EXISTS ma_classe_pkey CASCADE;\n" + 
				"\n" + 
				"ALTER TABLE ONLY ma_classe\n" + 
				"	ADD CONSTRAINT ma_classe_pkey PRIMARY KEY (id, id2);\n";
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
		
		String expect = "ALTER TABLE ma_classe DROP CONSTRAINT IF EXISTS ma_classe_ma_classe2_ids_fkey CASCADE;\n" + 
				"\n" + 
				"ALTER TABLE ONLY ma_classe\n" + 
				"    ADD CONSTRAINT ma_classe_ma_classe2_ids_fkey\n" + 
				"    FOREIGN KEY (id2) REFERENCES ma_classe2(id2);\n";
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
		
		String expect = "\nALTER TABLE table DROP CONSTRAINT IF EXISTS table_prop_ids_fkey CASCADE;\n" + 
				"\n" + 
				"ALTER TABLE ONLY table\n" + 
				"    ADD CONSTRAINT table_prop_ids_fkey\n" + 
				"    FOREIGN KEY (id2_prop) REFERENCES ma_classe2(id2);\n";
		assertEquals(expect, PackageUpdateDatabaseScriptGenerator.generateForeignKey(prop, "", "table", "").toString());
		
		expect = "\nALTER TABLE table DROP CONSTRAINT IF EXISTS table_test_prop_ids_fkey CASCADE;\n" + 
				"\n" + 
				"ALTER TABLE ONLY table\n" + 
				"    ADD CONSTRAINT table_test_prop_ids_fkey\n" + 
				"    FOREIGN KEY (test_id2_prop) REFERENCES ma_classe2(id2);\n";
		assertEquals(expect, PackageUpdateDatabaseScriptGenerator.generateForeignKey(prop, "test", "table", "").toString());
		
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
		
		String expect = "\nALTER TABLE table DROP CONSTRAINT IF EXISTS table_prop_code_fkey CASCADE;\n" + 
				"\n" + 
				"ALTER TABLE ONLY table\n" + 
				"    ADD CONSTRAINT table_prop_code_fkey\n" + 
				"    FOREIGN KEY (code_prop) REFERENCES ma_classe2(code);\n";
		assertEquals(expect, PackageUpdateDatabaseScriptGenerator.generateEnumForeignKey(prop, "", "table", "").toString());
		
		expect = "\nALTER TABLE table DROP CONSTRAINT IF EXISTS table_test_prop_code_fkey CASCADE;\n" + 
				"\n" + 
				"ALTER TABLE ONLY table\n" + 
				"    ADD CONSTRAINT table_test_prop_code_fkey\n" + 
				"    FOREIGN KEY (code_test_prop) REFERENCES ma_classe2(code);\n";
		assertEquals(expect, PackageUpdateDatabaseScriptGenerator.generateEnumForeignKey(prop, "test", "table", "").toString());
		
	}

	@Test
	public void testGenerateMutilvaluedPTTable() {
		HornetModel hmodel = HornetModel.initModel();
		Class class_ = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		class_.applyStereotype(hmodel.entity);
		TestUtils.createAttribute(class_, "id", hmodel.integerPT, 0, 1).applyStereotype(hmodel.keyAttribute);
		
		Property prop = TestUtils.createAttribute(class_, "prop", hmodel.stringPT, 0, -1);
		
		String expect = "\nCREATE TABLE IF NOT EXISTS ma_classe_prop();\n" + 
				"\n" + 
				"ALTER TABLE ma_classe_prop ADD COLUMN IF NOT EXISTS prop text NOT NULL;\n" + 
				"ALTER TABLE ma_classe_prop ADD COLUMN IF NOT EXISTS id text NOT NULL;\n" + 
				"\n" + 
				"ALTER TABLE ma_classe_prop DROP CONSTRAINT IF EXISTS ma_classe_prop_ids_fkey CASCADE;\n" + 
				"\n" + 
				"ALTER TABLE ONLY ma_classe_prop\n" + 
				"    ADD CONSTRAINT ma_classe_prop_ids_fkey\n" + 
				"    FOREIGN KEY (id) REFERENCES ma_classe(id);\n" + 
				"    \n" + 
				"ALTER TABLE ma_classe_prop DROP CONSTRAINT IF EXISTS ma_classe_prop_pkey CASCADE;\n" + 
				"\n" + 
				"ALTER TABLE ONLY ma_classe_prop\n" + 
				"    ADD CONSTRAINT ma_classe_prop_pkey PRIMARY KEY(id, prop);\n";
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
		
		String expect = "\nCREATE TABLE IF NOT EXISTS ma_classe_test();\n" + 
				"ALTER TABLE ma_classe_test ADD COLUMN IF NOT EXISTS id2_test text NOT NULL;\n" + 
				"ALTER TABLE ma_classe_test ADD COLUMN IF NOT EXISTS id_ma_classe text NOT NULL;\n" + 
				"\n" + 
				"ALTER TABLE ma_classe_test DROP CONSTRAINT IF EXISTS ma_classe_test_ma_classe_ids_fkey CASCADE;\n" + 
				"\n" + 
				"ALTER TABLE ONLY ma_classe_test\n" + 
				"    ADD CONSTRAINT ma_classe_test_ma_classe_ids_fkey\n" + 
				"    FOREIGN KEY (id_ma_classe) REFERENCES ma_classe(id);\n" + 
				"    \n" + 
				"ALTER TABLE ma_classe_test DROP CONSTRAINT IF EXISTS ma_classe_test_ma_classe2_ids_fkey CASCADE;\n" + 
				"    \n" + 
				"ALTER TABLE ONLY ma_classe_test\n" + 
				"    ADD CONSTRAINT ma_classe_test_ma_classe2_ids_fkey\n" + 
				"    FOREIGN KEY (id2_test) REFERENCES ma_classe2(id2);\n" + 
				"    \n" + 
				"ALTER TABLE ma_classe_test DROP CONSTRAINT IF EXISTS ma_classe_test_pkey CASCADE;\n" + 
				"    \n" + 
				"ALTER TABLE ONLY ma_classe_test\n" + 
				"    ADD CONSTRAINT ma_classe_test_pkey PRIMARY KEY(id_ma_classe, id2_test);\n";
		assertEquals(expect, PackageUpdateDatabaseScriptGenerator.generateMutilvaluedEntityTable(prop, class_).toString());
		
	}

	@Test
	public void testGenerateMultiIdAttributeDef() {
		HornetModel hmodel = HornetModel.initModel();
		Class class_ = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		class_.applyStereotype(hmodel.entity);
		Property prop = TestUtils.createAttribute(class_, "id", hmodel.integerPT, 0, 1);
		prop.applyStereotype(hmodel.keyAttribute);
		
		String expect = "ALTER TABLE TABLE ADD COLUMN IF NOT EXISTS id text NOT NULL;\n";
		assertEquals(expect, PackageUpdateDatabaseScriptGenerator.generateMultiIdAttributeDef(prop, "", "TABLE", "").toString());
		
		expect = "ALTER TABLE TABLE ADD COLUMN IF NOT EXISTS id_TEST text NOT NULL;\n";
		assertEquals(expect, PackageUpdateDatabaseScriptGenerator.generateMultiIdAttributeDef(prop, "TEST", "TABLE", "").toString());
	}

	@Test
	public void testGenerateMultiIdAttributeDefinition() {
		HornetModel hmodel = HornetModel.initModel();
		Class class_ = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		class_.applyStereotype(hmodel.entity);
		Property prop = TestUtils.createAttribute(class_, "id", hmodel.integerPT, 0, 1);
		prop.applyStereotype(hmodel.keyAttribute);
		
		String expect = "ALTER TABLE table ADD COLUMN IF NOT EXISTS id_ma_classe text NOT NULL;\n";
		assertEquals(expect, PackageUpdateDatabaseScriptGenerator.generateMultiIdAttributeDefinition(prop, "", "table", "").toString());
		
		expect = "ALTER TABLE table ADD COLUMN IF NOT EXISTS test_id_ma_classe text NOT NULL;\n";
		assertEquals(expect, PackageUpdateDatabaseScriptGenerator.generateMultiIdAttributeDefinition(prop, "test", "table", "").toString());
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
		
		String expect = "\nCREATE TABLE IF NOT EXISTS ma_classe_id();\n" + 
				"ALTER TABLE ma_classe_id ADD COLUMN IF NOT EXISTS id_test text ;\n" + 
				"ALTER TABLE ma_classe_id ADD COLUMN IF NOT EXISTS id text NOT NULL;\n" + 
				"\n" + 
				"ALTER TABLE ONLY ma_classe_id DROP CONSTRAINT IF EXISTS ma_classe_id_ids_fkey CASCADE;\n" + 
				"\n" + 
				"ALTER TABLE ONLY ma_classe_id\n" + 
				"    ADD CONSTRAINT ma_classe_id_ids_fkey\n" + 
				"    FOREIGN KEY (id) REFERENCES ma_classe(id);\n" + 
				"\n" + 
				"ALTER TABLE ONLY ma_classe_id DROP CONSTRAINT IF EXISTS ma_classe_id_pkey CASCADE;\n" + 
				"\n" + 
				"ALTER TABLE ONLY ma_classe_id\n" + 
				"    ADD CONSTRAINT ma_classe_id_pkey PRIMARY KEY(id);\n\n";
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
		expect.add("id");
		expect.add("prop");
		assertEquals(expect, PackageUpdateDatabaseScriptGenerator.getAttributList(class_, names , ""));
		
		names = new ArrayList<String>();
		expect = new ArrayList<String>();
		expect.add("test_id");
		expect.add("test_prop");
		assertEquals(expect, PackageUpdateDatabaseScriptGenerator.getAttributList(class_, names , "test"));
		
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
		
		String expect = "\nCREATE TABLE IF NOT EXISTS ma_classe_prop();\n" + 
				"\n" + 
				"ALTER TABLE ma_classe_prop ADD COLUMN IF NOT EXISTS code integer NOT NULL;\n" + 
				"ALTER TABLE ma_classe_prop ADD COLUMN IF NOT EXISTS id text NOT NULL;\n" + 
				"\n" + 
				"ALTER TABLE ONLY ma_classe_prop DROP CONSTRAINT IF EXISTS ma_classe_prop_ids_fkey CASCADE;\n" + 
				"\n" + 
				"ALTER TABLE ONLY ma_classe_prop\n" + 
				"    ADD CONSTRAINT ma_classe_prop_ids_fkey\n" + 
				"    FOREIGN KEY (id) REFERENCES ma_classe(id);\n" + 
				"\n" + 
				"ALTER TABLE ONLY ma_classe_prop DROP CONSTRAINT IF EXISTS ma_classe_prop_code_fkey CASCADE;\n" + 
				"\n" + 
				"ALTER TABLE ONLY ma_classe_prop\n" + 
				"    ADD CONSTRAINT ma_classe_prop_code_fkey\n" + 
				"    FOREIGN KEY (code) REFERENCES ma_classe2(code);\n" + 
				"    \n" + 
				"ALTER TABLE ONLY ma_classe_prop DROP CONSTRAINT IF EXISTS ma_classe_prop_pkey CASCADE;\n" + 
				"\n" + 
				"ALTER TABLE ONLY ma_classe_prop\n" + 
				"    ADD CONSTRAINT ma_classe_prop_pkey PRIMARY KEY (id, code);\n";
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
		
		String expect = "CREATE SEQUENCE IF NOT EXISTS ma_classe_id_seq\n" + 
				"    START WITH 0\n" + 
				"    INCREMENT BY 0\n" + 
				"    NO MAXVALUE\n" + 
				"    NO MINVALUE\n" + 
				"    CACHE 0\n" + 
				"    NO CYCLE;\n" + 
				"    \n" + 
				"ALTER SEQUENCE ma_classe_id_seq\n" + 
				"	OWNED BY ma_classe.id;\n" + 
				"\n" + 
				"ALTER TABLE ONLY ma_classe\n" + 
				"	ALTER COLUMN id \n" + 
				"	SET DEFAULT nextval('ma_classe_id_seq'::regclass);\n";
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
		
		String expect = "\nCREATE TABLE IF NOT EXISTS asso();\n" + 
				"\n" + 
				"ALTER TABLE asso ADD COLUMN IF NOT EXISTS id2_test text ;\n" + 
				"ALTER TABLE asso ADD COLUMN IF NOT EXISTS id_test2 text ;\n" + 
				"\n" + 
				"ALTER TABLE asso DROP CONSTRAINT IF EXISTS asso_test_ids_fkey CASCADE;\n" + 
				"\n" + 
				"ALTER TABLE ONLY asso\n" + 
				"	ADD CONSTRAINT asso_test_ids_fkey\n" + 
				"	FOREIGN KEY (id2_test) REFERENCES ma_classe2(id2);\n" + 
				"\n" + 
				"ALTER TABLE asso DROP CONSTRAINT IF EXISTS asso_test2_ids_fkey CASCADE;\n" + 
				"\n" + 
				"ALTER TABLE ONLY asso\n" + 
				"	ADD CONSTRAINT asso_test2_ids_fkey\n" + 
				"	FOREIGN KEY (id_test2) REFERENCES ma_classe(id);\n" + 
				"\n" + 
				"ALTER TABLE asso DROP CONSTRAINT IF EXISTS asso_pkey CASCADE;\n" + 
				"\n" + 
				"ALTER TABLE ONLY asso\n" + 
				"	ADD CONSTRAINT asso_pkey PRIMARY KEY (id2_test, id_test2);\n";
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
		expect.add("id2_test");
		expect.add("id_test2");
		assertEquals(expect, PackageUpdateDatabaseScriptGenerator.getAssociationAttributList(asso, names , ""));
		
		names = new ArrayList<String>();
		expect = new ArrayList<String>();
		expect.add("add_id2_test");
		expect.add("add_id_test2");
		assertEquals(expect, PackageUpdateDatabaseScriptGenerator.getAssociationAttributList(asso, names , "add"));
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
		
		String expect = "\nCREATE TABLE IF NOT EXISTS ma_classe();\n" + 
				"\n" + 
				"ALTER TABLE ma_classe ADD COLUMN IF NOT EXISTS code integer NOT NULL;\n" + 
				"ALTER TABLE ma_classe ADD COLUMN IF NOT EXISTS libelle text;\n" + 
				"\n" + 
				"ALTER TABLE ma_classe DROP CONSTRAINT IF EXISTS ma_classe_pkey CASCADE;\n" + 
				"\n" + 
				"ALTER TABLE ONLY ma_classe\n" + 
				"	ADD CONSTRAINT ma_classe_pkey PRIMARY KEY (code);\n\n"
				+ "INSERT INTO ma_classe (CODE, LIBELLE) VALUES (0, 'prop') ON CONFLICT DO NOTHING;\n";
		assertEquals(expect, PackageUpdateDatabaseScriptGenerator.generateEnumTable(class_).toString());
		
	}

}
