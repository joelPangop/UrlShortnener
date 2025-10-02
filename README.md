
Le projet backend à savoir UrlShortnener-services étant fait en JAVA spring Boot, peut s'executer facilement avec des IDEs telles que IntelliJ
Pour le projet Front-end à savoir UrlShortnener-client, on peut l'ouvrir en ligne de commande et exécuter la commande "npm run start"

Dans la section "1) Raccourcir une URL": 
* Entrer une url par exemple "https://exemple.com/long/path?x=1".
* Cliquer sur "Raccourcir"
  Resultat, une nouvelle url est générée et est affichée au bas de la section comprenant l’url du service suivi d'un code de <=10 charactères les 2 séparés d'un "/"
  par exemple "http://localhost:8080/e621706b40". Le resulata entier devrait ressembler a ceci:
  <br/>
  <strong>
    Code: e621706b40<br/>
    URL courte: http://localhost:8080/e621706b40
   </strong>
* Si on clique directement sur le lien URL courte, une nouvelle page s'ouvre affichant l'url originale

Dans la "section 2) Retrouver l’URL originale"
* Entrer le code généré
* Clicquer sur Retrouver
  Resultat:
  - Si le code n'existe pas, un message d'erreur va s'afficher au bas de la section
  - Si le code existe, l'url d'origine va s'afficher au bas de la section
 
En résumé, il y a 2 moyens de retrouver l'url originale:
* En cliquant sur le lien de l'URL courte et en entrant le code généré par le raccourcissement de l'URL d'origine.


