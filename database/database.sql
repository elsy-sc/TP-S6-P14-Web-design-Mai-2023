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