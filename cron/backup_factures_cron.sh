#!/bin/bash

# Script pour exécuter la sauvegarde des factures
mvn exec:java@run-send-factures
mysql -u [user] -p[password] heptathlon < ../database/backup_factures.sql