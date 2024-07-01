#!/bin/bash

# Script pour exécuter la mise à jour des prix
mysql -u [user] -p[password] heptathlon < ../database/update_prices.sql
