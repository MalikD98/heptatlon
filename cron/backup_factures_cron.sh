#!/bin/bash

# Script pour exécuter la sauvegarde des factures
mysql -u [user] -p[password] heptathlon < ../database/backup_factures.sql
mvn exec:java@run-send-factures