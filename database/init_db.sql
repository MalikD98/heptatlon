CREATE DATABASE IF NOT EXISTS heptathlon;
USE heptathlon;

CREATE TABLE IF NOT EXISTS articles (
    reference INT PRIMARY KEY,
    famille VARCHAR(50),
    prix DECIMAL(10, 2),
    quantite_stock INT
);

CREATE TABLE IF NOT EXISTS commandes (
    reference INT PRIMARY KEY,
    client VARCHAR(255),
    statut VARCHAR(50),
    date_creation DATETIME default CURRENT_TIMESTAMP,
    date_modification DATETIME
);

CREATE TABLE IF NOT EXISTS quantite_commandes (
    commande_reference INT,
    article_reference INT,
    quantite_commande INT,
    PRIMARY KEY (commande_reference, article_reference),
    FOREIGN KEY (commande_reference) REFERENCES commandes(reference),
    FOREIGN KEY (article_reference) REFERENCES articles(reference)
);

CREATE TABLE IF NOT EXISTS factures (
    id INT AUTO_INCREMENT PRIMARY KEY,
    mode_paiement VARCHAR(50),
    date_facturation DATETIME,
    commande_reference INT,
    FOREIGN KEY (commande_reference) REFERENCES commandes(reference)
);
