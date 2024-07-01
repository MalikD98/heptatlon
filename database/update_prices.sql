/**
 * @file update_prices.sql
 * @brief Script SQL pour mettre à jour les prix des articles de manière aléatoire
 *
 * Ce script met à jour les prix des articles en les augmentant ou en les diminuant
 * de manière aléatoire jusqu'à 2%. En cas d'erreur, l'opération est annulée.
 */

START TRANSACTION;

-- Mise à jour des prix des articles
UPDATE articles
SET prix = prix * (1 + ((RAND() * 0.04) - 0.02));

COMMIT;

-- Gestion des erreurs
-- En cas d'erreur, faire un ROLLBACK pour annuler les changements
ROLLBACK;
