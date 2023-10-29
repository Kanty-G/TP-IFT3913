Auteurs :
#Kanty Louange Gakima 	matricule: 20184109
#Yann-Sibril Saah 			matricule: 20061840

Lien Github: https://github.com/gakkal29/TP-IFT3913
Outil externe utilisé: https://www.sonarsource.com/products/sonarqube/

Après plusieurs recherches  et contraintes pour faire l’analyse on a décidé d’utiliser SonarQube comme outil pour faire l’analyse, en passant par docker.

On a d’abord lancé docker en faisant:

-docker pull sonarqube
-docker run -d --name sonarqube -p 9000:9000 -p 9092:9092 sonarqube

Et sonarqube sera lancé au : http://localhost:9000

Et On a créé 2 projets,  TP2-IFT3913 un qui fait l’analyse de tout le projet jfreeChart et l’autre qui fait l’analyse du dossier test seulement qui se nomme TP2-IFT3913-Test

On va rouler une commande qui ressemble à ceci  pour avoir le résultat de l’analyse, en considérant que un des projets créé sur sonarQube se nomme TP2-IFT3913-Test:

Si mon est déjà configuré:

mvn clean verify sonar:sonar \
  -Dsonar.projectKey=TP2-IFT3913-\
  -Dsonar.projectName='TP2 - IFT3913' \
  -Dsonar.host.url=http://localhost:9000 \
  -Dsonar.token= {token key généré par sonarQube}-f {repertoire local de jfreeChart}/pom.xml 

Ou dans le dossier téléchargé de mvn, étant dans le repertoire bin(ce que nous avons fait car on a eu des erreurs lors de la configuration):

./mvn clean verify sonar:sonar -Panalyze-test-classes \
  -Dsonar.projectKey=TP2-IFT3913-Test \
  -Dsonar.projectName='TP2-IFT3913-Test' \
  -Dsonar.host.url=http://localhost:9000 \
  -Dsonar.token={token key généré par sonarQube} -f {repertoire local de jfreeChart}/	pom.xml

Et à ce moment là on a les deux analyses sur le localhost et on a pris les métriques pertinentes pour notre analyse, les résultats se trouvent dans le rapport.





