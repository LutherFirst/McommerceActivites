package com.mcommandes.web.controller;


import com.mcommandes.dao.CommandesDao;
import com.mcommandes.model.Commande;
import com.mcommandes.web.exceptions.CommandeNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class CommandeController {

    @Autowired
    CommandesDao commandesDao;

    @PostMapping (value = "/commandes")
    public ResponseEntity<Commande> ajouterCommande(@RequestBody Commande commande){
        return new ResponseEntity<>(commandesDao.save(commande), HttpStatus.CREATED);
    }

    @GetMapping(value = "/commandes/{id}")
    public Optional<Commande> recupererUneCommande(@PathVariable int id){

        Optional<Commande> commande = commandesDao.findById(id);

        if(!commande.isPresent()) throw new CommandeNotFoundException("Cette commande n'existe pas");

        return commande;
    }

    /*
    * Permet de mettre à jour une commande existante.
    * save() mettra à jours uniquement les champs renseignés dans l'objet commande reçu. Ainsi dans ce cas, comme le champs date dans "commande" n'est
    * pas renseigné, la date précédemment enregistrée restera en place
    **/
    @PutMapping(value = "/commandes")
    public void updateCommande(@RequestBody Commande commande) {

        commandesDao.save(commande);
    }
}
