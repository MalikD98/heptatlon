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

## Tâches Cron
Le projet inclut des scripts pour automatiser certaines tâches de maintenance à l'aide de cron :
- **Mise à jour des prix** (`update_prices_cron.sh`) : Exécute le script SQL `update_prices.sql` pour mettre à jour les prix des articles à intervalles réguliers.
- **Sauvegarde des factures** (`backup_factures_cron.sh`) : Exécute le script SQL `backup_factures.sql` pour sauvegarder les factures dans un fichier de backup.

Pour configurer les tâches cron, ajoutez les lignes suivantes à votre crontab :
```cron
# Mise à jour des prix tous les jours à minuit
0 6 * * * /path/to/cron/update_prices_cron.sh

# Sauvegarde des factures tous les jours à 1h du matin
0 22 * * * /path/to/cron/backup_factures_cron.sh
```
