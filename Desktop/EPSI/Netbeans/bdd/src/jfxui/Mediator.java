/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jfxui;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Guest
 */
public class Mediator { 
    
    public Mediator(EntityManagerFactory emf) {
        if (emf == null) {
            throw new NullPointerException("emf cannot be null");
        }
        this.emf = emf;
    }
    
    // On crée directement un EntityManager à partir de EntityManagerFactory, donc on n'a pas besoin
    // d'un set(), on peut faire directement un create()
    public EntityManager createEntityManager() { 
        return this.emf.createEntityManager();
    }
    
    private EntityManagerFactory emf;
    
}
