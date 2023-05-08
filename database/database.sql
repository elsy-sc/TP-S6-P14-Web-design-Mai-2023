create database tp_s6_p14_web_design_mai_2023_database;

\c tp_s6_p14_web_design_mai_2023_database postgres;

create sequence seqcategorie start 1 increment 1;
create sequence sequtilisateur start 1 increment 1;
create sequence seqarticle start 1 increment 1;

create table utilisateur
(
    id              text primary key,
    noms            text,
    email           text unique not null,
    motdepasse      text not null
);

create table categorie
(
    id      text primary key,
    libelle text unique not null,
    description text
);

create table article
(
    id              text primary key,
    titre           text not null,
    resume          text,
    contenu         text,
    image           bytea,
    idcategorie     text references categorie (id)
);

insert into utilisateur
values ('ut1', 'John Doe', 'jd@yahoo.fr', 'jd123');
insert into utilisateur
values ('ut2', 'Don Juan', 'dj@gmail.com', 'dj456');
insert into utilisateur
values ('ut3', 'Marcel Upium', 'mu@hotmail.com', 'mu789');

insert into categorie
values ('cat1', 'Apprentissage automatique');
insert into categorie
values ('cat2', 'Traitement automatique du langage naturel');
insert into categorie
values ('cat3', 'Robotique');
insert into categorie
values ('cat4', 'Éthique de l''Intelligence artificielle');
insert into categorie
values ('cat5', 'L''Intelligence artificielle en générale');
insert into categorie
values ('cat6', 'Traitement d''image');
insert into categorie
values ('cat7', 'Deep Learning');
insert into categorie
values ('cat8', 'Reconnaissance faciale');
insert into categorie
values ('cat9', 'Actualité de l''Intelligence artificielle');
insert into categorie
values ('cat10', 'Réseaux antagonistes génératifs');
insert into categorie
values ('cat11', 'Vision par l''ordinateur');

--- chatgpt

INSERT INTO article (id, titre, resume, contenu, image, idcategorie)
VALUES ('1', 'L'impact de l'intelligence artificielle sur la société', 'Découvrez comment l'IA transforme notre monde', '<p>L'intelligence artificielle est en train de transformer notre monde à une vitesse incroyable. Elle est utilisée dans des domaines tels que la médecine, les transports, la finance et bien d'autres encore. Mais avec ces progrès technologiques viennent également des questions éthiques et des préoccupations quant à l'impact de l'IA sur la société.</p> <p>Il est important de réfléchir à l'utilisation de l'IA de manière responsable et de veiller à ce qu'elle soit utilisée pour le bien commun. Cela signifie prendre en compte les implications éthiques, la transparence et la responsabilité dans le développement de l'IA.</p>', NULL, 'societe');

INSERT INTO article (id, titre, resume, contenu, image, idcategorie)
VALUES ('2', 'Les avantages de l'intelligence artificielle dans l'industrie manufacturière', 'Découvrez comment l'IA améliore l'efficacité et la productivité de l'industrie manufacturière', '<p>L'intelligence artificielle peut être utilisée dans l'industrie manufacturière pour améliorer l'efficacité et la productivité. Par exemple, elle peut aider à prévoir les temps d'arrêt de la machine et à identifier les problèmes avant qu'ils ne se produisent. Elle peut également être utilisée pour optimiser les processus de production et réduire les coûts.</p> <p>Cependant, l'utilisation de l'IA dans l'industrie manufacturière soulève également des questions quant à l'impact sur l'emploi et les compétences requises pour travailler dans ce secteur. Il est important de veiller à ce que les travailleurs soient formés pour travailler avec l'IA et à ce que les avantages de cette technologie soient équitablement répartis.</p>', NULL, 'manufacturing');

    INSERT INTO article (id, titre, resume, contenu, image, idcategorie)
VALUES ('4', 'Les défis de l'IA en matière de confidentialité des données', 'Découvrez les défis de l'utilisation de l'IA en matière de confidentialité des données', '<p>L'intelligence artificielle nécessite souvent des quantités massives de données pour fonctionner. Cependant, l'utilisation de ces données soulève des préoccupations quant à la confidentialité et à la protection des données personnelles. Les entreprises doivent s'assurer que les données sont collectées et utilisées de manière éthique et légale.</p> <p>En outre, il est important de prendre en compte les biais qui peuvent être introduits dans les algorithmes de l'IA en fonction des données utilisées pour les entraîner. Cela peut avoir des implications importantes, notamment en matière de discrimination. Il est donc crucial de veiller à ce que les données utilisées pour entraîner les algorithmes de l'IA soient diverses et représentatives.</p>', NULL, 'donnees');

INSERT INTO article (id, titre, resume, contenu, image, idcategorie)
VALUES ('5', 'Les applications de l'IA dans le domaine de la santé', 'Découvrez comment l'IA est utilisée dans le domaine de la santé pour améliorer les soins aux patients', '<p>L'intelligence artificielle est de plus en plus utilisée dans le domaine de la santé pour améliorer les soins aux patients. Elle peut être utilisée pour aider à diagnostiquer des maladies, à prédire les risques de complications et à élaborer des plans de traitement personnalisés.</p> <p>Les avantages potentiels de l'IA en matière de santé sont énormes, mais il est important de veiller à ce que cette technologie soit utilisée de manière responsable et éthique. Les préoccupations quant à la confidentialité des données et à la transparence doivent être prises en compte.</p>', NULL, 'sante');

INSERT INTO article (id, titre, resume, contenu, image, idcategorie)
VALUES ('6', 'Comment l'IA peut être utilisée pour lutter contre la fraude', 'Découvrez comment l'IA peut être utilisée pour identifier la fraude et prévenir les crimes financiers', '<p>L'intelligence artificielle peut être utilisée pour identifier la fraude et prévenir les crimes financiers. Elle peut aider à détecter les modèles de comportement suspect et à identifier les transactions frauduleuses. Elle peut également être utilisée pour surveiller les activités des employés et pour prévenir les fuites de données sensibles.</p> <p>Cependant, l'utilisation de l'IA dans la lutte contre la fraude soulève également des questions quant à la protection de la vie privée et à la responsabilité. Il est important de veiller à ce que les utilisateurs soient informés de l'utilisation de l''IA et de leur donner un moyen de contester les décisions prises à l''aide de cette technologie.</p>', NULL, 'securite');

INSERT INTO article (id, titre, resume, contenu, image, idcategorie)
VALUES ('7', 'Les enjeux éthiques liés à l'IA', 'Découvrez les enjeux éthiques liés à l''utilisation de l'IA', '<p>L'intelligence artificielle soulève de nombreux enjeux éthiques, notamment en ce qui concerne la transparence, la responsabilité et l'utilisation responsable des données. Les préoccupations concernent également les biais qui peuvent être introduits dans les algorithmes de l'IA et qui peuvent avoir des implications importantes, notamment en matière de discrimination.</p> <p>Il est important de veiller à ce que l'IA soit utilisée de manière responsable et éthique. Les entreprises doivent prendre des mesures pour garantir la transparence et la responsabilité de l'utilisation de l'IA et pour minimiser les risques de discrimination.</p>', NULL, 'ethique');

INSERT INTO article (id, titre, resume, contenu, image, idcategorie)
VALUES ('8', 'Les avancées récentes de l'IA dans le domaine de la reconnaissance vocale', 'Découvrez les dernières avancées de l'IA dans le domaine de la reconnaissance vocale', '<p>LIA a connu de nombreuses avancées dans le domaine de la reconnaissance vocale ces dernières années. Les systèmes de reconnaissance vocale sont de plus en plus précis et peuvent être utilisés dans une variété d'applications, notamment dans les assistants personnels, les systèmes de commande vocale pour les voitures et les maisons intelligentes.</p> <p>Ces avancées ont été rendues possibles grâce à l'apprentissage automatique et à l'analyse des données massives. Cependant, des préoccupations subsistent quant à la confidentialité et à la sécurité des données vocales collectées par ces systèmes.</p>', NULL, 'technologie');

INSERT INTO article (id, titre, resume, contenu, image, idcategorie)
VALUES ('9', 'Les applications de l'IA dans le secteur bancaire', 'Découvrez comment lIA est utilisée dans le secteur bancaire pour améliorer les services financiers', '<p>L'IA est de plus en plus utilisée dans le secteur bancaire pour améliorer les services financiers. Elle peut être utilisée pour aider à détecter les fraudes, à prédire les risques de défaut de paiement et à élaborer des offres de produits financiers personnalisées pour les clients.</p> <p>Ces applications de l'IA ont le potentiel d'améliorer considérablement l'efficacité et la rentabilité du secteur bancaire, mais elles soulèvent également des préoccupations quant à la protection de la vie privée et à la sécurité des données.</p>', NULL, 'finance');

INSERT INTO article (id, titre, resume, contenu, image, idcategorie)
VALUES ('11', 'Les chatbots et l'IA', 'Découvrez comment l'IA est utilisée pour créer des chatbots intelligents', '<p>Les chatbots sont des programmes informatiques conçus pour interagir avec les utilisateurs de manière similaire à celle d'un humain. L'IA est utilisée pour créer des chatbots intelligents qui peuvent comprendre et répondre aux questions des utilisateurs de manière plus naturelle.</p> <p>Ces chatbots sont utilisés dans de nombreuses applications, notamment pour le service clientèle et la gestion des commandes en ligne. Cependant, leur utilisation soulève des préoccupations quant à la confidentialité des données personnelles et à la qualité de l'expérience utilisateur.</p>', NULL, 'technologie');

INSERT INTO article (id, titre, resume, contenu, image, idcategorie)
VALUES ('12', 'La formation des modèles d'IA', 'Découvrez comment les modèles d'IA sont formés et les défis liés à leur développement', '<p>La formation des modèles d''IA est une étape clé dans le développement de l'IA. Les modèles sont formés à partir de grandes quantités de données et sont ensuite utilisés pour effectuer des tâches spécifiques, comme la reconnaissance d'images ou le traitement du langage naturel.</p> <p>Cependant, la formation de ces modèles peut être coûteuse et complexe, et il existe des préoccupations quant à la qualité et à la diversité des données utilisées pour leur formation.</p>', NULL, 'recherche');

INSERT INTO article (id, titre, resume, contenu, image, idcategorie)
VALUES ('13', 'Les applications de l'IA dans la médecine', 'Découvrez comment l''IA est utilisée dans le domaine de la médecine pour améliorer les diagnostics et les traitements', '<p>L'IA est de plus en plus utilisée dans le domaine de la médecine pour aider à améliorer les diagnostics et les traitements. Elle peut être utilisée pour analyser des images médicales, prédire le risque de maladies et développer des thérapies personnalisées pour les patients.</p> <p>Ces applications de l'IA ont le potentiel d'améliorer considérablement la qualité des soins de santé, mais elles soulèvent également des préoccupations quant à la confidentialité des données et à la responsabilité en cas d'erreurs.</p>', NULL, 'santé');

INSERT INTO article (id, titre, resume, contenu, image, idcategorie)
VALUES ('15', 'L'IA et la cybersécurité', 'Découvrez comment l'IA est utilisée pour améliorer la cybersécurité', '<p>L'IA est utilisée dans la cybersécurité pour détecter et prévenir les attaques informatiques. Elle peut être utilisée pour identifier les menaces potentielles, pour prévenir les intrusions et pour renforcer les défenses informatiques.</p> <p>Cependant, l'utilisation de l'IA en cybersécurité soulève également des préoccupations quant à la sécurité des données et à la responsabilité en cas d'erreurs.</p>', NULL, 'sécurité');

INSERT INTO article (id, titre, resume, contenu, image, idcategorie)
VALUES ('16', 'Les réseaux de neurones artificiels', 'Découvrez comment fonctionnent les réseaux de neurones artificiels', '<p>Les réseaux de neurones artificiels sont une technique clé utilisée dans l'IA pour simuler les fonctions du cerveau humain. Ils peuvent être utilisés pour des tâches telles que la reconnaissance de formes et la prédiction de résultats.</p> <p>Cependant, la création et la formation de ces réseaux peuvent être coûteuses et complexes, et leur utilisation soulève des préoccupations quant à la transparence et à la compréhension des résultats qu'ils produisent.</p>', NULL, 'technologie');

INSERT INTO article (id, titre, resume, contenu, image, idcategorie)
VALUES ('17', 'L'IA et les voitures autonomes', 'Découvrez comment l'IA est utilisée pour créer des voitures autonomes', '<p>L'IA est utilisée pour créer des voitures autonomes qui peuvent conduire sans intervention humaine. Elle est utilisée pour détecter les obstacles, pour planifier les itinéraires et pour prendre des décisions de conduite en temps réel.</p> <p>Ces voitures autonomes ont le potentiel de réduire les accidents de la route et d'améliorer l'efficacité de la conduite, mais leur utilisation soulève des préoccupations quant à la sécurité des passagers et des autres usagers de la route.</p>', NULL, 'transport');

    INSERT INTO article (id, titre, resume, contenu, image, idcategorie)
VALUES ('19', 'Les chatbots et l'IA', 'Découvrez comment les chatbots utilisent l'IA pour fournir un service client efficace', '<p>Les chatbots sont des programmes informatiques qui utilisent l'IA pour interagir avec les utilisateurs et répondre à leurs questions. Ils sont utilisés pour fournir un service client efficace 24 heures sur 24, 7 jours sur 7, sans intervention humaine.</p> <p>L'IA permet aux chatbots d'apprendre et de s'améliorer au fil du temps, devenant ainsi de plus en plus précis et efficaces dans leurs réponses.</p>', NULL, 'technologie');

INSERT INTO article (id, titre, resume, contenu, image, idcategorie)
VALUES ('20', 'Les éthiques de l'IA', 'Découvrez les défis éthiques posés par l'IA', '<p>L'IA soulève des préoccupations éthiques importantes, notamment en matière de confidentialité des données, de biais algorithmique et de responsabilité. Les décisions prises par les algorithmes de l'IA peuvent avoir des conséquences importantes sur les vies humaines, il est donc important de garantir leur transparence et leur équité.</p> <p>Les spécialistes de l'IA travaillent sur des solutions pour garantir que l'IA est utilisée de manière responsable et éthique, mais il reste encore beaucoup à faire dans ce domaine.</p>', NULL, 'éthique');

INSERT INTO article (id, titre, resume, contenu, image, idcategorie)
VALUES ('21', 'L'IA et le marketing', 'Découvrez comment l'IA est utilisée dans le marketing pour améliorer les campagnes publicitaires', '<p>L'IA est utilisée dans le marketing pour analyser les données des clients et pour créer des campagnes publicitaires plus efficaces. Elle peut être utilisée pour cibler les publicités en fonction des préférences et des comportements des clients, pour prédire les tendances du marché et pour améliorer la personnalisation des offres.</p> <p>Cependant, l'utilisation de l'IA en marketing soulève également des préoccupations quant à la vie privée et à la manipulation des consommateurs.</p>', NULL, 'marketing');

INSERT INTO article (id, titre, resume, contenu, image, idcategorie)
VALUES ('23', 'Les voitures autonomes et l'IA', 'Découvrez comment l'IA est utilisée dans les voitures autonomes', '<p>Les voitures autonomes sont équipées de capteurs et de caméras qui leur permettent de percevoir leur environnement et de prendre des décisions en conséquence. L'IA est utilisée pour traiter les données des capteurs et pour prendre des décisions en temps réel, ce qui permet aux voitures autonomes de conduire de manière sûre et efficace.</p> <p>Cependant, il reste encore des défis importants à surmonter pour rendre les voitures autonomes largement disponibles, notamment en matière de réglementation et de sécurité.</p>', NULL, 'transport');

INSERT INTO article (id, titre, resume, contenu, image, idcategorie)
VALUES ('24', 'L'IA et la médecine', 'Découvrez comment l'IA est utilisée dans le domaine médical pour améliorer les diagnostics', '<p>L'IA est utilisée dans la médecine pour analyser les données des patients et pour aider les médecins à poser des diagnostics plus précis. Elle peut être utilisée pour identifier les signes précoces de maladies, pour prédire l'évolution des maladies et pour recommander des traitements personnalisés.</p> <p>Cependant, l'utilisation de l'IA dans la médecine soulève également des préoccupations quant à la vie privée et à la sécurité des données des patients.</p>', NULL, 'santé');

INSERT INTO article (id, titre, resume, contenu, image, idcategorie)
VALUES ('25', 'Les jeux et l'IA', 'Découvrez comment l'IA est utilisée dans les jeux vidéo pour créer des expériences plus immersives', '<p>L'IA est utilisée dans les jeux vidéo pour créer des personnages non joueurs plus réalistes et plus réactifs, pour adapter la difficulté du jeu en temps réel en fonction du niveau de compétence du joueur et pour personnaliser les expériences de jeu en fonction des préférences du joueur.</p> <p>Cependant, l'utilisation de l'IA dans les jeux vidéo soulève également des préoccupations quant à l'impact sur la créativité des développeurs et sur l'expérience de jeu des joueurs.</p>', NULL, 'jeux');

INSERT INTO article (id, titre, resume, contenu, image, idcategorie)
VALUES ('27', 'L'IA et la sécurité', 'Découvrez comment l'IA est utilisée pour améliorer la sécurité et la surveillance', '<p>L'IA est utilisée dans la sécurité pour surveiller les activités suspectes, pour détecter les menaces et pour améliorer les systèmes de sécurité. Elle peut être utilisée pour surveiller les caméras de sécurité, pour détecter les intrusions dans les réseaux informatiques et pour améliorer la sécurité des transactions financières en ligne.</p> <p>Cependant, l'utilisation de l'IA dans la sécurité soulève également des préoccupations quant à la vie privée et à la surveillance de masse.</p>', NULL, 'sécurité');

INSERT INTO article (id, titre, resume, contenu, image, idcategorie)
VALUES ('28', 'L'IA et l'industrie', 'Découvrez comment l'IA est utilisée dans l'industrie pour améliorer l'efficacité et la productivité', '<p>L'IA est utilisée dans l'industrie pour optimiser les processus de fabrication, pour prévoir les pannes de machines et pour améliorer l'efficacité énergétique. Elle peut également être utilisée pour créer des produits personnalisés en fonction des préférences des clients.</p> <p>Cependant, l'utilisation de l'IA dans l'industrie soulève également des préoccupations quant à l'impact sur l'emploi et la sécurité des travailleurs.</p>', NULL, 'industrie');

INSERT INTO article (id, titre, resume, contenu, image, idcategorie)
VALUES ('29', 'L'IA et l'agriculture', 'Découvrez comment l'IA est utilisée dans l'agriculture pour améliorer la productivité et réduire les coûts', '<p>L'IA est utilisée dans l'agriculture pour surveiller les cultures, pour prédire les rendements et pour optimiser l'utilisation des ressources. Elle peut être utilisée pour surveiller les maladies des plantes, pour détecter les carences en nutriments et pour adapter les pratiques agricoles en conséquence.</p> <p>Cependant, l'utilisation de l'IA dans l'agriculture soulève également des préoccupations quant à la sécurité alimentaire et à l'impact environnemental.</p>', NULL, 'agriculture');

    INSERT INTO article (id, titre, resume, contenu, image, idcategorie)
VALUES ('31', 'L'IA et la médecine', 'Découvrez comment l'IA est utilisée dans la médecine pour améliorer les diagnostics et les traitements', '<p>L'IA est utilisée dans la médecine pour aider les médecins à poser des diagnostics plus précis, pour améliorer les traitements et pour réduire les erreurs médicales. Elle peut être utilisée pour analyser les images médicales, pour prédire l'évolution des maladies et pour recommander des traitements personnalisés en fonction des antécédents médicaux et des caractéristiques du patient.</p> <p>Cependant, l'utilisation de l'IA dans la médecine soulève également des préoccupations quant à la confidentialité des données médicales et à la sécurité des patients.</p>', NULL, 'médecine');

INSERT INTO article (id, titre, resume, contenu, image, idcategorie)
VALUES ('32', 'L'IA et les ressources humaines', 'Découvrez comment l'IA est utilisée dans les ressources humaines pour améliorer les processus de recrutement et de gestion des talents', '<p>L'IA est utilisée dans les ressources humaines pour automatiser les tâches répétitives, pour trier les CV et pour détecter les candidats les plus prometteurs. Elle peut également être utilisée pour améliorer la gestion des talents, pour prévoir les besoins en personnel et pour recommander des formations personnalisées en fonction des compétences et des objectifs de carrière des employés.</p> <p>Cependant, l'utilisation de l'IA dans les ressources humaines soulève également des préoccupations quant à l'objectivité et à l'éthique dans les processus de recrutement et de gestion des talents.</p>', NULL, 'ressources humaines');

INSERT INTO article (id, titre, resume, contenu, image, idcategorie)
VALUES ('33', 'L'IA et les transports', 'Découvrez comment l'IA est utilisée dans les transports pour améliorer la sécurité et l'efficacité', '<p>L'IA est utilisée dans les transports pour améliorer la sécurité routière, pour optimiser les itinéraires et pour réduire la congestion. Elle peut être utilisée pour détecter les comportements dangereux des conducteurs, pour prévoir les temps de trajet et pour recommander des alternatives de transport en temps réel.</p> <p>Cependant, l'utilisation de l'IA dans les transports soulève également des préoccupations quant à la protection des données des utilisateurs et à la sécurité des véhicules autonomes.</p>', NULL, 'transports');