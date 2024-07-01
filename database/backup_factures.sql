/**
 * @file backup_factures.sql
 * @brief Script SQL pour exporter les factures dans un fichier et enregistrer la date de sauvegarde
 *
 * Ce script sauvegarde les factures créées aujourd'hui dans un fichier CSV,
 * puis met à jour la colonne date_enregistrement dans la table factures avec la date courante. En cas d'erreur, l'opération' est annulée.
 */

START TRANSACTION;

-- Sauvegarde des factures dans un fichier
SELECT * 
INTO OUTFILE CONCAT('backup/factures_backup_', DATE(NOW()), '.csv')
FIELDS TERMINATED BY ',' 
ENCLOSED BY '"'
LINES TERMINATED BY '\n'
FROM factures
WHERE date_creation >= TIMESTAMP(DATE(NOW()));

-- Mise à jour de la date d'enregistrement dans les factures
UPDATE factures
SET date_enregistrement = NOW()
WHERE date_creation >= TIMESTAMP(DATE(NOW()));

COMMIT;

-- Gestion des erreurs
-- En cas d'erreur, faire un ROLLBACK pour annuler les changements
ROLLBACK;
