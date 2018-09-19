# Modélisation et générateur de code Hornet lite avec Papyrus

## Présentation

Le générateur Hornet Papyrus permet de générer du code pour les applications Hornet Lite
ou un backend JPA à partir d'un diagramme de classe UML réalisé sous Eclipse Papyrus.

### Documentation Hornet-generator

[Documentation d'installation et tutoriel Hornet-generator](docs/Modélisation&#32;avec&#32;papyrus.md)

### Composition

Le projet Hornet-generator est composé de trois répertoires:

- Le répertoire dist contient les archives des plugins Papyrus nécessaires pour la génération.
- Le répertoire docs contient la documentation utilisateur du projet Hornet-generator
- Le répertoire sources contient les sources des plugins.

#### Les plugins

Hornet-generator propose huit plugins différents.

##### Le plugin metamodele

Ce plugin (fr.gouv.diplomatie.papyrus.metamodele) contient les métamodèles utilisés pour la création de modèles.
Ces métamodèles seront proposés dans les profils enregistrés de Papyrus.

##### Les plugins core

Hornet-generator propose deux plugins core.

Un plugin core (fr.gouv.diplomatie.papyrus.codegen.core) qui propose des méthodes générales qui seront utilisés par les autres plugins pour la génération de code.
Notamment, des méthodes d'accès aux données du modèle.

Le plugin ui core (fr.gouv.diplomatie.papyrus.codegen.ui.core) qui propose des méthodes générales qui seront utilisées par
les autres plugins UI. Il sera principalement utilisé pour la création de menu dans l'interface, la validation du modèle et l'appel aux générateurs de code.

##### Le plugin ui

Le plugin ui (fr.gouv.diplomatie.papyrus.codegen.ui) ajoute un menu de génération a l'interface et propose des choix de validation ou de lancement de
génération de code.

##### Les plugins de génération

Les autres plugins permettent la génération des fichiers. Ils sont découpés en fonction du langage du code généré.


#### Langages générés


##### Hornet Lite

Le générateur permet de généré les objets utilisés dans des applications [Hornet.js-lite](https://github.com/diplomatiegouvfr/hornet-js).

##### JPA

Le générateur permet de généré des objets JPA s'appuyant sur les annotations de validation [Java Bean Validation](https://beanvalidation.org/2.0/)


#### Langages utilisés

Le générateur se base sur le langage [xtend](https://www.eclipse.org/xtend/) et la modélisation avec UML2.

## Licence

hornet-generator est sous [licence cecill 2.1](./LICENSE.md).

Site web : http://www.cecill.info