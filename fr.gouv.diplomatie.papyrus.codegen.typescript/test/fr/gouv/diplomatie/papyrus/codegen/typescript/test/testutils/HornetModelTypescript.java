package fr.gouv.diplomatie.papyrus.codegen.typescript.test.testutils;
import org.eclipse.uml2.uml.Property;

import fr.gouv.diplomatie.papyrus.codegen.core.test.HornetModel;
import fr.gouv.diplomatie.papyrus.codegen.core.test.TestUtils;
import fr.gouv.diplomatie.papyrus.codegen.typescript.utils.TypeUtils;

public class HornetModelTypescript extends HornetModel{

	 public static final HornetModelTypescript instance = new HornetModelTypescript();
	 
	 public Property hornetTypeSequelizeType;
	 public Property hornetTypeTypescriptType;
	 public Property hornetTypeMetierClassType;
	 
	 public HornetModelTypescript() {
		 super();
		 hornetTypeSequelizeType = TestUtils.createAttribute(hornetType, TypeUtils.MODEL_HORNETTYPE_SEQUELIZETYPE, stringPT, 0, 1);
		 hornetTypeTypescriptType = TestUtils.createAttribute(hornetType, TypeUtils.MODEL_HORNETTYPE_TYPESCRIPTTYPE, stringPT, 0, 1);
		 hornetTypeMetierClassType = TestUtils.createAttribute(hornetType, TypeUtils.MODEL_HORNETTYPE_METIERCLASSTYPE, stringPT, 0, 1);
		 
		 profile.define();
		 model.applyProfile(profile);
	 }
	 
	 public static HornetModelTypescript initModel() {
		instance.clean();
	 	return instance;
	 }
}
