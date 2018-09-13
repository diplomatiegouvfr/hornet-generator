# Changelog

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

### Removed

- Suppression de la variable schema sur les classes. La variable schema pris en compte sera celle du package ayant le stéréotype `domain`.

### Fixed

- [Hornet Lite] Génération de table d'association: Correction des liens dans le modelDAO.



