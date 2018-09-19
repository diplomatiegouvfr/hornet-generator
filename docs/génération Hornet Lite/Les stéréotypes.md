# Les stéréotypes

Chaque classe ou attribut du modèle doit avoir un stéréotype associé.
Les stéréotypes apportent des précisions pour la génération de code.

## Le stéréotype domain

Le stéréotype domain peut être assigné au package qui contient les classes

| attibut   | description                                                        | type | Valeur par défaut |
| --------- | ------------------------------------------------------------------ | ---- |-------------------|
| schema | détermine le schema des tables présentes dans le package. | String | null |

## Le stéréotype application

Le stéréotype application peut être assigné au model (dans la vue Model Explorer).

| attibut   | description                                                        | type | Valeur par défaut |
| --------- | ------------------------------------------------------------------ | ---- |-------------------|
| metierFileSuffix | Suffixe des noms des fichiers metier | String | "metier" |
| dtoFileSuffix | Suffixe des noms des fichiers dto | String | "dto" |
| modelFileSuffix | Suffixe des noms des fichiers model | String | "model" |
| attributesFileSuffix | Suffixe des noms des fichiers attributes | String | "attributes" |

## Les stéréotypes de classe

Les stéréotypes de classe proposés par le profil sont les suivants:

- entity: représente une entité majeure du métier qui regroupe des attributs.

Un exemple d'entity est présent dans le tutoriel ci-dessus (classe `Partenaire` ou `Secteur`)

- valueObject: représente une entité de second rang qui n'a d'existence dans le système que parce qu'elle est liée à une entité. Un valueObject ne sera donc généré que s'il est présent en tant que champs dans une classe.

Un exemple de valueObject est présent dans le tutoriel ci-dessus (classe `Assistant`)

- nomenclature: définit un ensemble de couple code / libellé servant à caractériser des entités. Attention il ne s'agit pas d'un référentiel, pour cela utiliser le stéréotype entity.
Les classes possédant le stéréotype `nomenclature` ne doivent posséder que des attributs avec le stéréotype `CodeLibelleNomenclature`. il s'agira des valeurs possibles de l'enuméré. Cette table est généré avec deux champs, code et libéllé.

### Les attributs du stéréotype entity

| attibut   | description                                                        | type | Valeur par défaut |
| --------- | ------------------------------------------------------------------ | ---- |-------------------|
| generated | Indique si la classe doit être générée ou non                      | boolean | true |
| tableName | Nom de la table dans la base de données                            | String | null (valeur générée: nom de la table en Snake case) |
| [`DEPRECATED`: utiliser plutot la gestion par package avec le stéréotype `domain`] schema    | Nom du schema de la table (sera pris en compte plutot que celui du domain) | String | null |

### Les attributs du stéréotype valueObject

| attibut   | description                                                        | type | Valeur par défaut |
| --------- | ------------------------------------------------------------------ | ---- |-------------------|
| generated | Indique si la classe doit être générée ou non                      | boolean | true |

### Les attributs du stéréotype nomenclature

| attibut   | description                                                        | type | Valeur par défaut |
| --------- | ------------------------------------------------------------------ | ---- |-------------------|
| generated | Indique si la classe doit être générée ou non                      | boolean | true |
| tableName | Nom de la table dans la base de données                            | String | null (valeur générée: nom de la table en Snake case) |
| [`DEPRECATED`: utiliser plutot la gestion par package avec le stéréotype `domain`] schema    | Nom du schema de la table (sera pris en compte plutot que celui du domain) | String | null |

## Les stéréotypes de classe d'association

Le stéréotype `AssociationTable` est disponible pour les classes d'associations (ne pas l'ajouter à une classe basique (class)).

| attibut   | description                                                        | type | Valeur par défaut |
| --------- | ------------------------------------------------------------------ | ---- |-------------------|
| tableName | nom de la table                     | String | null(valeur générée: nom de la table en Snake case) |
| [`DEPRECATED`: utiliser plutot la gestion par package avec le stéréotype `domain`] schema    | Nom du schema de la table (sera pris en compte plutot que celui du domain) | String | null |

## Les stéréotypes d'attributs

- attribute: attribut de la classe.
- keyAttribute: identifiant de la classe.

Un attribut devra avoir au moins un des deux attributs attribute ou keyAttribute.

- sequence: sequence liée a un attributs. Elle ne seront appliquées que pour les attributs de type KeyAttribute.
- CodeLibelleNomenclature: couple code / libellé d'une nomenclature.

### Les attributs du stéréotype attribute et KeyAttribute

| attibut   | description                                                        | type | Valeur par défaut |
| --------- | ------------------------------------------------------------------ | ---- |-------------------|
| length | Longueur du champ (l'attribut hasLength doit etre mis a true pour que celle-ci soit pris en compte)| Integer | 0 |
| ColumnName | Nom de la colonne en base de donnée | String | null (valeur générée: nom du champs en Snake case ( + préfixes éventuellement)) |
| hasLength | Indique si l'attribut length est valué | boolean | false |
| index		| Indique si un index doit être génré ou non sur la colonne | boolean | false |

### Les attributs du stéréotype sequence

| attibut   | description                                                        | type | Valeur par défaut |
| --------- | ------------------------------------------------------------------ | ---- |-------------------|
| startWith | Valeur de départ                                                   | Integer | 1 |
| incrementBy | Le pas de l'increment                                            | Integer | 1 |
| minValue | Valeur minimum                                                      | Integer | 0 |
| maxValue | Valeur maximum                                                      | Integer | 0 |
| cache | Spécifie comment les numéros de séquence doivent être préalloués et stockés en mémoire pour un accès plus rapide, par défaut on préalloue 1 nombre.| Integer | 1 |                               
| cycle | Si on atteint maxValue ou minValue, on autorise le générateur à boucler | boolean | false |
| hasMaxValue | Indique si le champs possède une valeur maximum                  | boolean | false |
| hasMinValue |  Indique si le champs possède une valeur minimum                 | boolean | false |

### Les attributs du stéréotype CodeLibelleNomenclature

| attibut   | description                                                        | type | Valeur par défaut |
| --------- | ------------------------------------------------------------------ | ---- |-------------------|
| code | Valeur                                                                  | Integer | 0 |
| libelle | Libéllé lié a la valeur                                              | Integer | 0 |
