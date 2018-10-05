# Changelog

## v1.2.1 -> v1.3.0:

### Added

- Ajout de la génération du script de création des utilisateurs et rôles par défaut.
- Ajout de la génération du script de création de la base sqlLite.

### Changed

- Mise à jour du readme.

### Fixed

- Correction de la validation de profil (erreur si le modèle possédait un profil sans nom).
- Ajout du Stéréotype ModelLibrary sur la bibliothèque de type HornetPrimitiveTypes.
- Correction de la génération des champs dans une table d'association (virgule manquante)

## v1.2.0 -> v1.2.1:

### Added

- [Hornet Lite] Ajout de la gestion de la propriété schema pour Hornet Lite . Cette variable est cependant deprecated, sont utilisation est déconseillée.
- Ajout de la propriété schema sur les nomenclature et les associationTable, deprecated aussi.

## V1.1.4 -> v1.2.0:

### Added

- [Hornet Lite] Ajout de la génération des indexs pour les colonnes simples
- [Hornet Lite] Ajout de la possibilité de changer les suffixes des fichiers de model
- [Hornet Lite] Ajout de la génération des attributs dans les tables d'association
- [Hornet Lite] Ajout de champs pour la génération de table d'association (génération de listes des classes liées par la table d'association dans le DTO
, la classe métier et l'interface attributes)
- Documentation: ajout d'exemple de génération

### Changed

- Passage à Papyrus 4.0
- Séparation des profils Lite et JPA
- [Hornet Lite] Uniformisation des champs liés à des associations
- [Hornet Lite] Génération de table d'association: modification nom de champs dans le DTO, la classe métier et l'interface attributes
- [Hornet Lite] Les associations bidirectionnelles sont prises en compte (certaisn champs ne seront plus générés pour des association monodirectionnelle)

### Fixed

- [Hornet Lite] Génération de table d'association: Correction des liens dans le modelDAO.



