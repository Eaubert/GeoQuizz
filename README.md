# GeoQuizz

Membres:
  - Cuny Louis
  - Sipp Hugo
  - Lefevre Alexandre 
  - Aubert Enzo

Trello : https://trello.com/b/nQdTXqca/atelier-2

Installation:

git clone git@github.com:Eaubert/GeoQuizz.git
cd GeoQuizz
cd admin
npm install
npm run dev

Route:

Post : /parties/
			{
				"nbPhotos": "10",
				"joueur": "michel",
				"idMap": "1"
			}
			
Get : /parties/id
	x-lbs-token : ("token de la partie")
	
Get : /parties/id/photos
	x-lbs-token : ("token de la partie")
	id = id de la partie creer
	
Get : /maps/
Get : /maps/id/photos
Get : /photos/
Get : /photos/id/maps

