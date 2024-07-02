# Heptathlon - Système de Gestion de Stock et de Facturation

## Description
Ce projet vise à développer un système informatique pour la gestion d'un stock de marchandises et la préparation des factures des ventes effectuées. Il est conçu en utilisant le modèle client-serveur avec Java RMI pour les communications et MySQL pour le stockage des données.

## Fonctionnalités
- **Gestion du Stock** :
  - Référence de l'article
  - Famille de l'article
  - Prix unitaire
  - Nombre total d'exemplaires en stock
- **Facturation des Clients** :
  - Total à payer par client
  - Détails des articles achetés
  - Mode de paiement
  - Date de facturation
- **Opérations sur les Données** :
  - Consulter le stock d'un article
  - Rechercher un article
  - Payer un article
  - Consulter une facture
  - Calculer le chiffre d'affaires
  - Ajouter un article au stock
  - Consulter le nombre d'exemplaires d'un produit

## Architecture
L'architecture du système comprend :
- Un serveur central situé au siège de l'entreprise.
- Des serveurs dans chacun des magasins.
- Des postes clients dans les magasins.

Les communications entre les clients et les serveurs sont gérées via Java RMI.

## Prérequis
- Java 11
- Maven
- MySQL

## Installation
1. **Décompresser le projet** :
    ```bash
    cd heptathlon
    ```

2. **Configurer la base de données** :
    - Créer une base de données MySQL et exécuter le script `init_db.sql` pour créer les tables nécessaires.
    - Mettre à jour les informations de connexion à la base de données dans les fichiers de configuration (si nécessaire).

3. **Construire le projet avec Maven** :
    ```bash
    mvn clean install
    ```

4. **Remplir la base de données avec un jeu de données existant** :
    ```bash
    mvn exec:java@run-seeder
    ```

## Utilisation
1. **Démarrer le serveur** :
    ```bash
    mvn exec:java@run-server
    ```

2. **Démarrer le client** :
    ```bash
    mvn exec:java@run-main
    ```