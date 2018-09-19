package fr.gouv.diplomatie.papyrus.codegen.sql.test.testutils;

import org.eclipse.uml2.uml.Property;

import fr.gouv.diplomatie.papyrus.codegen.core.test.HornetModel;
import fr.gouv.diplomatie.papyrus.codegen.core.test.TestUtils;
import fr.gouv.diplomatie.papyrus.codegen.sql.utils.TypeUtils;

public class HornetModelSql extends HornetModel{

	 public static final HornetModelSql instance = new HornetModelSql();
	 
	 public Property hornetTypeDatabaseType;
	 
	 public HornetModelSql() {
		 super();
		 hornetTypeDatabaseType = TestUtils.createAttribute(hornetType, TypeUtils.MODEL_HORNETTYPE_DATABASETYPE, stringPT, 0, 1);
		 
		 profile.define();
		 model.applyProfile(profile);
	 }
	 
	 public static HornetModelSql initModel() {
		instance.clean();
	 	return instance;
	 }
}