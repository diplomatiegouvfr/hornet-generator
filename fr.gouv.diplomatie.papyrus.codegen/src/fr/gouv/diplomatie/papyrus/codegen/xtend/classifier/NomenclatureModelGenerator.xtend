package fr.gouv.diplomatie.papyrus.codegen.xtend.classifier;

import org.eclipse.uml2.uml.Classifier
import fr.gouv.diplomatie.papyrus.codegen.xtend.utils.ClassifierUtils
import fr.gouv.diplomatie.papyrus.codegen.xtend.utils.TypeUtils

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
				type: Sequelize.INTEGER,
				field: "code",
				allowNull: false
			}
		}
		'''
	}
}