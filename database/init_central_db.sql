CREATE DATABASE IF NOT EXISTS heptathlon_central;
USE heptathlon_central;

-- Création de la table Magasins
CREATE TABLE IF NOT EXISTS magasins (
    reference INT AUTO_INCREMENT PRIMARY KEY, -- Référence unique du magasin
    nom VARCHAR(255),          -- Nom du magasin
    ville VARCHAR(255),        -- Ville du magasin
    code_postal VARCHAR(10),    -- Code postal du magasin
    identifiant VARCHAR(10),    -- Identifiant du magasin
    mot_de_passe VARCHAR(500)    -- mot de passe du magasin
);

-- Création de la table Articles
CREATE TABLE IF NOT EXISTS articles (
    reference INT AUTO_INCREMENT PRIMARY KEY, -- Référence unique de l'article
    libelle VARCHAR(255),      -- Nom de l'article
    famille VARCHAR(50),       -- Famille de l'article
    prix DECIMAL(10, 2)        -- Prix de l'article
);

-- Création de la table Stock
CREATE TABLE IF NOT EXISTS stock (
    magasin_reference INT,     -- Référence du magasin (clé étrangère)
    article_reference INT,     -- Référence de l'article (clé étrangère)
    qte_stock INT,            -- Quantité stockée de l'article dans le magasin
    PRIMARY KEY (magasin_reference, article_reference), -- Clé primaire composite
    FOREIGN KEY (magasin_reference) REFERENCES magasins(reference), -- Clé étrangère vers magasins
    FOREIGN KEY (article_reference) REFERENCES articles(reference)  -- Clé étrangère vers articles
);

-- Création de la table Factures
CREATE TABLE IF NOT EXISTS factures (
    reference INT AUTO_INCREMENT PRIMARY KEY,   -- Référence unique de la facture
    client VARCHAR(255),         -- Nom du client
    mode_paiement VARCHAR(50),   -- Mode de paiement utilisé
    date_creation DATETIME DEFAULT CURRENT_TIMESTAMP, -- Date de création de la facture
    date_enregistrement DATETIME DEFAULT CURRENT_TIMESTAMP         -- Date d'enregistrement de la facture
);

-- Création de la table Commandes
CREATE TABLE IF NOT EXISTS commandes (
     magasin_reference INT,     -- Référence du magasin (clé étrangère)
     article_reference INT,     -- Référence de l'article (clé étrangère)
     facture_reference INT,     -- Référence de la facture (clé étrangère)
     qte_fournie INT,           -- Quantité fournie de l'article dans la commande
     montant DECIMAL(10, 2),      -- Montant total de la facture
     PRIMARY KEY (magasin_reference, article_reference, facture_reference), -- Clé primaire composite
    FOREIGN KEY (magasin_reference) REFERENCES magasins(reference), -- Clé étrangère vers magasins
    FOREIGN KEY (facture_reference) REFERENCES factures(reference),  -- Clé étrangère vers factures
    FOREIGN KEY (article_reference) REFERENCES articles(reference) ON UPDATE CASCADE ON DELETE RESTRICT -- Clé étrangère vers articles
);
