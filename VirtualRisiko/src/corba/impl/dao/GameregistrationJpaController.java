/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package corba.impl.dao;

import corba.impl.Gameregistration;
import corba.impl.GameregistrationPK;
import corba.impl.dao.exceptions.NonexistentEntityException;
import corba.impl.dao.exceptions.PreexistingEntityException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import corba.impl.User;

/**
 *
 * @author root
 */
public class GameregistrationJpaController {

    public GameregistrationJpaController() {
        emf = Persistence.createEntityManagerFactory("VirtualRisikoPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Gameregistration gameregistration) throws PreexistingEntityException, Exception {
        if (gameregistration.getGameregistrationPK() == null) {
            gameregistration.setGameregistrationPK(new GameregistrationPK());
        }
        gameregistration.getGameregistrationPK().setGameID(gameregistration.getGame().getId());
        gameregistration.getGameregistrationPK().setUserUsername(gameregistration.getUser().getUsername());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            User user = gameregistration.getUser();
            if (user != null) {
                user = em.getReference(user.getClass(), user.getUsername());
                gameregistration.setUser(user);
            }
            em.persist(gameregistration);
            if (user != null) {
                user.getGameregistrationCollection().add(gameregistration);
                user = em.merge(user);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findGameregistration(gameregistration.getGameregistrationPK()) != null) {
                throw new PreexistingEntityException("Gameregistration " + gameregistration + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Gameregistration gameregistration) throws NonexistentEntityException, Exception {
        gameregistration.getGameregistrationPK().setGameID(gameregistration.getGame().getId());
        gameregistration.getGameregistrationPK().setUserUsername(gameregistration.getUser().getUsername());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Gameregistration persistentGameregistration = em.find(Gameregistration.class, gameregistration.getGameregistrationPK());
            User userOld = persistentGameregistration.getUser();
            User userNew = gameregistration.getUser();
            if (userNew != null) {
                userNew = em.getReference(userNew.getClass(), userNew.getUsername());
                gameregistration.setUser(userNew);
            }
            gameregistration = em.merge(gameregistration);
            if (userOld != null && !userOld.equals(userNew)) {
                userOld.getGameregistrationCollection().remove(gameregistration);
                userOld = em.merge(userOld);
            }
            if (userNew != null && !userNew.equals(userOld)) {
                userNew.getGameregistrationCollection().add(gameregistration);
                userNew = em.merge(userNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                GameregistrationPK id = gameregistration.getGameregistrationPK();
                if (findGameregistration(id) == null) {
                    throw new NonexistentEntityException("The gameregistration with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(GameregistrationPK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Gameregistration gameregistration;
            try {
                gameregistration = em.getReference(Gameregistration.class, id);
                gameregistration.getGameregistrationPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The gameregistration with id " + id + " no longer exists.", enfe);
            }
            User user = gameregistration.getUser();
            if (user != null) {
                user.getGameregistrationCollection().remove(gameregistration);
                user = em.merge(user);
            }
            em.remove(gameregistration);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Gameregistration> findGameregistrationEntities() {
        return findGameregistrationEntities(true, -1, -1);
    }

    public List<Gameregistration> findGameregistrationEntities(int maxResults, int firstResult) {
        return findGameregistrationEntities(false, maxResults, firstResult);
    }

    private List<Gameregistration> findGameregistrationEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select object(o) from Gameregistration as o");
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Gameregistration findGameregistration(GameregistrationPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Gameregistration.class, id);
        } finally {
            em.close();
        }
    }

    public int getGameregistrationCount() {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select count(o) from Gameregistration as o");
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

    public List<Gameregistration> findGameregistrationByPartitaID(int gameID){
         EntityManager em = getEntityManager();
        try {
            Query q = em.createNamedQuery("Gameregistration.findByGameID");
            q.setParameter("gameID", gameID);
            return q.getResultList();
        } finally {
            em.close();
        }
    }

}
