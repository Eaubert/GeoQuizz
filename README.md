# GeoQuizz

## Prérecquis

Maven, NodeJs

## Membres:
  - Cuny Louis
  - Sipp Hugo
  - Lefevre Alexandre
  - Aubert Enzo

## Trello :

https://trello.com/b/nQdTXqca/atelier-2
https://www.lucidchart.com/invitations/accept/99081550-8601-4446-8ab0-cbaa90646440

## Installation :
```shell
git clone git@github.com:Eaubert/GeoQuizz.git
```

Il faut régler l'adresse API dans les fichiers :
  - admin/src/configApi/index.js
  
  - jeu/src/config/index.js

```shell
cd GeoQuizz/admin
npm install
npm run dev
```
```shell
cd GeoQuizz/jeu
npm install
npm run dev
```
```shell
cd GeoQuizz/API
mvn clean install
cp target/GeoQuizz.war [your deployement directory]
```
Charger data.sql dans la base de données.

## Règles

### But du jeu :

Replacer le plus précisément possible l’image affichée sur une carte de la ville choisie.

### Déroulement :

Une fois une ville choisie , une série de 10 photos est générée aléatoirement.

Puis la première photo est montrée au joueur , il la place où il pense qu’elle se trouve et ainsi de suite jusqu’à ce qu’il ai replacer les 10 photos.

### Fin de partie :

Les 10 photos ont été placées , la partie est finie , le joueur voit son score final et peut choisir de le sauvegarder ou non.

### Temps de réponse :

Moins de 5 secondes = points marqués multipliés par 4.

Moins de 10 secondes = points marqués multipliés par 2

### Niveau de difficultés :

Les niveaux de difficultés changent juste les distances pour marquer des points.
