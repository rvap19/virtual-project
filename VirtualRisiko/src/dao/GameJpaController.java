/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package dao;


import dao.exceptions.NonexistentEntityException;
import domain.Game;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;

/**
 *
 * @author root
 */
public class GameJpaController {

    public GameJpaController() {
        emf = Persistence.createEntityManagerFactory("VirtualRisikoPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Game game) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(game);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Game game) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            game = em.merge(game);
            em.getTransaction().commit();
            em.flush();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = game.getId();
                if (findGame(id) == null) {
                    throw new NonexistentEntityException("The game with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Game game;
            try {
                game = em.getReference(Game.class, id);
                game.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The game with id " + id + " no longer exists.", enfe);
            }
            em.remove(game);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Game> findGameEntities() {
        return findGameEntities(true, -1, -1);
    }

    public List<Game> findGameEntities(int maxResults, int firstResult) {
        return findGameEntities(false, maxResults, firstResult);
    }

    private List<Game> findGameEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select object(o) from Game as o");
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Game findGame(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Game.class, id);
        } finally {
            em.close();
        }
    }

    public int getGameCount() {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select count(o) from Game as o");
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

    public List<Game> findActiveGames() {
         EntityManager em = getEntityManager();
        try {
            Query q = em.createNamedQuery("Game.findByAttiva");
            q.setParameter("attiva", true);
            return q.getResultList();



        } finally {
            em.close();
        }
    }

    public List<Game> findActiveGamesByManagerUsername(String username) {
         EntityManager em = getEntityManager();
        try {
            Query q = em.createNamedQuery("Game.findActiveGamesByManagerUsername");
            q.setParameter("attiva", true);
            q.setParameter("managerUsername", username);
            return  q.getResultList();



        } finally {
            em.close();
        }
    }

     public List<Game> findGamesByTorneoAndPhase(int idTorneo,int phase) {
         EntityManager em = getEntityManager();
        try {
            Query q = em.createNamedQuery("Game.findByIDTorneoAndPhase");
            q.setParameter("iDTorneo", idTorneo);
            q.setParameter("faseTorneo", phase);
            return  q.getResultList();



        } finally {
            em.close();
        }
    }



}