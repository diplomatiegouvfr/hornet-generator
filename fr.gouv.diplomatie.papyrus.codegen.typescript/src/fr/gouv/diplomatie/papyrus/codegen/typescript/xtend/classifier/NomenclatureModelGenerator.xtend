package fr.gouv.diplomatie.papyrus.codegen.typescript.xtend.classifier;

import org.eclipse.uml2.uml.Classifier
import fr.gouv.diplomatie.papyrus.codegen.core.utils.ClassifierUtils
import fr.gouv.diplomatie.papyrus.codegen.core.utils.TypeUtils

public class NomenclatureModelGenerator{
	
	static def generateCode(Classifier clazz){
		'''
		import Sequelize = require("sequelize");
		
		export var «ClassifierUtils.getModelName(clazz)»: Sequelize.DefineAttributes={
			code: {
				type: Sequelize.«TypeUtils.getEnumSequelizeType(clazz)»,
				field: "code",
				allowNull: false,
				primaryKey: true
			},
			libelle: {
				type: Sequelize.TEXT,
				field: "libelle",
				allowNull: false
			}
		}
		'''
	}
}