/*Collegue*/

insert into collegue(nom, prenom, email, mot_de_passe, matricule, num_telephone) values ('John', 'Doe', 'john.doe@email.com', 'password', '007', '02894585468');

/*Vehicule*/

insert into vehicule(immatriculation, marque, modele, categorie, nb_place, statut, proprietaire_societe) values ('AA-000-RR', 'Audi', 'A4', 'CATEGORIE_CP', 5, 'EN_SERVICE', false);

/*Reservation*/

insert into reservation(date_depart, date_arrivee, lieu_depart, lieu_destination, duree_trajet, distance, responsable_id, chauffeur_id, statut, vehicule_id) values (null, null, 'Rennes', 'Nantes', '5000', '120.0', 1, null,'STATUT_EN_COURS', 1)