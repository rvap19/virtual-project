package dao;

import domain.Gameregistration;
import domain.Tournamentregistration;
import domain.User;
import dao.exceptions.IllegalOrphanException;
import dao.exceptions.NonexistentEntityException;
import dao.exceptions.PreexistingEntityException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;




/**
 *
 * @author root
 */
public class UserJpaController {

    public UserJpaController() {
        emf = Persistence.createEntityManagerFactory("VirtualRisikoPU");

    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(User user) throws PreexistingEntityException, Exception {
        if (user.getTournamentregistrationCollection() == null) {
            user.setTournamentregistrationCollection(new ArrayList<Tournamentregistration>());
        }
        if (user.getGameregistrationCollection() == null) {
            user.setGameregistrationCollection(new ArrayList<Gameregistration>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Tournamentregistration> attachedTournamentregistrationCollection = new ArrayList<Tournamentregistration>();
            for (Tournamentregistration tournamentregistrationCollectionTournamentregistrationToAttach : user.getTournamentregistrationCollection()) {
                tournamentregistrationCollectionTournamentregistrationToAttach = em.getReference(tournamentregistrationCollectionTournamentregistrationToAttach.getClass(), tournamentregistrationCollectionTournamentregistrationToAttach.getTournamentregistrationPK());
                attachedTournamentregistrationCollection.add(tournamentregistrationCollectionTournamentregistrationToAttach);
            }
            user.setTournamentregistrationCollection(attachedTournamentregistrationCollection);
            Collection<Gameregistration> attachedGameregistrationCollection = new ArrayList<Gameregistration>();
            for (Gameregistration gameregistrationCollectionGameregistrationToAttach : user.getGameregistrationCollection()) {
                gameregistrationCollectionGameregistrationToAttach = em.getReference(gameregistrationCollectionGameregistrationToAttach.getClass(), gameregistrationCollectionGameregistrationToAttach.getGameregistrationPK());
                attachedGameregistrationCollection.add(gameregistrationCollectionGameregistrationToAttach);
            }
            user.setGameregistrationCollection(attachedGameregistrationCollection);
            em.persist(user);
            for (Tournamentregistration tournamentregistrationCollectionTournamentregistration : user.getTournamentregistrationCollection()) {
                User oldUserOfTournamentregistrationCollectionTournamentregistration = tournamentregistrationCollectionTournamentregistration.getUser();
                tournamentregistrationCollectionTournamentregistration.setUser(user);
                tournamentregistrationCollectionTournamentregistration = em.merge(tournamentregistrationCollectionTournamentregistration);
                if (oldUserOfTournamentregistrationCollectionTournamentregistration != null) {
                    oldUserOfTournamentregistrationCollectionTournamentregistration.getTournamentregistrationCollection().remove(tournamentregistrationCollectionTournamentregistration);
                    oldUserOfTournamentregistrationCollectionTournamentregistration = em.merge(oldUserOfTournamentregistrationCollectionTournamentregistration);
                }
            }
            for (Gameregistration gameregistrationCollectionGameregistration : user.getGameregistrationCollection()) {
                User oldUserOfGameregistrationCollectionGameregistration = gameregistrationCollectionGameregistration.getUser();
                gameregistrationCollectionGameregistration.setUser(user);
                gameregistrationCollectionGameregistration = em.merge(gameregistrationCollectionGameregistration);
                if (oldUserOfGameregistrationCollectionGameregistration != null) {
                    oldUserOfGameregistrationCollectionGameregistration.getGameregistrationCollection().remove(gameregistrationCollectionGameregistration);
                    oldUserOfGameregistrationCollectionGameregistration = em.merge(oldUserOfGameregistrationCollectionGameregistration);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findUser(user.getUsername()) != null) {
                throw new PreexistingEntityException("User " + user + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(User user) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            User persistentUser = em.find(User.class, user.getUsername());
            Collection<Tournamentregistration> tournamentregistrationCollectionOld = persistentUser.getTournamentregistrationCollection();
            Collection<Tournamentregistration> tournamentregistrationCollectionNew = user.getTournamentregistrationCollection();
            Collection<Gameregistration> gameregistrationCollectionOld = persistentUser.getGameregistrationCollection();
            Collection<Gameregistration> gameregistrationCollectionNew = user.getGameregistrationCollection();
            List<String> illegalOrphanMessages = null;
            for (Tournamentregistration tournamentregistrationCollectionOldTournamentregistration : tournamentregistrationCollectionOld) {
                if (!tournamentregistrationCollectionNew.contains(tournamentregistrationCollectionOldTournamentregistration)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Tournamentregistration " + tournamentregistrationCollectionOldTournamentregistration + " since its user field is not nullable.");
                }
            }
            for (Gameregistration gameregistrationCollectionOldGameregistration : gameregistrationCollectionOld) {
                if (!gameregistrationCollectionNew.contains(gameregistrationCollectionOldGameregistration)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Gameregistration " + gameregistrationCollectionOldGameregistration + " since its user field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Tournamentregistration> attachedTournamentregistrationCollectionNew = new ArrayList<Tournamentregistration>();
            for (Tournamentregistration tournamentregistrationCollectionNewTournamentregistrationToAttach : tournamentregistrationCollectionNew) {
                tournamentregistrationCollectionNewTournamentregistrationToAttach = em.getReference(tournamentregistrationCollectionNewTournamentregistrationToAttach.getClass(), tournamentregistrationCollectionNewTournamentregistrationToAttach.getTournamentregistrationPK());
                attachedTournamentregistrationCollectionNew.add(tournamentregistrationCollectionNewTournamentregistrationToAttach);
            }
            tournamentregistrationCollectionNew = attachedTournamentregistrationCollectionNew;
            user.setTournamentregistrationCollection(tournamentregistrationCollectionNew);
            Collection<Gameregistration> attachedGameregistrationCollectionNew = new ArrayList<Gameregistration>();
            for (Gameregistration gameregistrationCollectionNewGameregistrationToAttach : gameregistrationCollectionNew) {
                gameregistrationCollectionNewGameregistrationToAttach = em.getReference(gameregistrationCollectionNewGameregistrationToAttach.getClass(), gameregistrationCollectionNewGameregistrationToAttach.getGameregistrationPK());
                attachedGameregistrationCollectionNew.add(gameregistrationCollectionNewGameregistrationToAttach);
            }
            gameregistrationCollectionNew = attachedGameregistrationCollectionNew;
            user.setGameregistrationCollection(gameregistrationCollectionNew);
            user = em.merge(user);
            for (Tournamentregistration tournamentregistrationCollectionNewTournamentregistration : tournamentregistrationCollectionNew) {
                if (!tournamentregistrationCollectionOld.contains(tournamentregistrationCollectionNewTournamentregistration)) {
                    User oldUserOfTournamentregistrationCollectionNewTournamentregistration = tournamentregistrationCollectionNewTournamentregistration.getUser();
                    tournamentregistrationCollectionNewTournamentregistration.setUser(user);
                    tournamentregistrationCollectionNewTournamentregistration = em.merge(tournamentregistrationCollectionNewTournamentregistration);
                    if (oldUserOfTournamentregistrationCollectionNewTournamentregistration != null && !oldUserOfTournamentregistrationCollectionNewTournamentregistration.equals(user)) {
                        oldUserOfTournamentregistrationCollectionNewTournamentregistration.getTournamentregistrationCollection().remove(tournamentregistrationCollectionNewTournamentregistration);
                        oldUserOfTournamentregistrationCollectionNewTournamentregistration = em.merge(oldUserOfTournamentregistrationCollectionNewTournamentregistration);
                    }
                }
            }
            for (Gameregistration gameregistrationCollectionNewGameregistration : gameregistrationCollectionNew) {
                if (!gameregistrationCollectionOld.contains(gameregistrationCollectionNewGameregistration)) {
                    User oldUserOfGameregistrationCollectionNewGameregistration = gameregistrationCollectionNewGameregistration.getUser();
                    gameregistrationCollectionNewGameregistration.setUser(user);
                    gameregistrationCollectionNewGameregistration = em.merge(gameregistrationCollectionNewGameregistration);
                    if (oldUserOfGameregistrationCollectionNewGameregistration != null && !oldUserOfGameregistrationCollectionNewGameregistration.equals(user)) {
                        oldUserOfGameregistrationCollectionNewGameregistration.getGameregistrationCollection().remove(gameregistrationCollectionNewGameregistration);
                        oldUserOfGameregistrationCollectionNewGameregistration = em.merge(oldUserOfGameregistrationCollectionNewGameregistration);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = user.getUsername();
                if (findUser(id) == null) {
                    throw new NonexistentEntityException("The user with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(String id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            User user;
            try {
                user = em.getReference(User.class, id);
                user.getUsername();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The user with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Tournamentregistration> tournamentregistrationCollectionOrphanCheck = user.getTournamentregistrationCollection();
            for (Tournamentregistration tournamentregistrationCollectionOrphanCheckTournamentregistration : tournamentregistrationCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This User (" + user + ") cannot be destroyed since the Tournamentregistration " + tournamentregistrationCollectionOrphanCheckTournamentregistration + " in its tournamentregistrationCollection field has a non-nullable user field.");
            }
            Collection<Gameregistration> gameregistrationCollectionOrphanCheck = user.getGameregistrationCollection();
            for (Gameregistration gameregistrationCollectionOrphanCheckGameregistration : gameregistrationCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This User (" + user + ") cannot be destroyed since the Gameregistration " + gameregistrationCollectionOrphanCheckGameregistration + " in its gameregistrationCollection field has a non-nullable user field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(user);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<User> findUserEntities() {
        return findUserEntities(true, -1, -1);
    }

    public List<User> findUserEntities(int maxResults, int firstResult) {
        return findUserEntities(false, maxResults, firstResult);
    }

    private List<User> findUserEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(User.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public User findUser(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(User.class, id);
        } finally {
            em.close();
        }
    }

    public User findUserByUsername(String username){
        EntityManager em=this.getEntityManager();
        try{
            Query q = em.createNamedQuery("User.findByUsername");
            q.setParameter("username", username);

            return (User) q.getSingleResult();
        }finally{
            em.close();
        }

    }

    public User findUserByUserNamePassword(String username,String password){
        EntityManager em=this.getEntityManager();
        try{
            Query q=em.createNamedQuery("User.findByUsernamePassword");
            q.setParameter("username", username);
            q.setParameter("password", password);
            return (User)q.getSingleResult();
        }catch(Exception ex){
            return null;
        }finally{
            em.close();
        }
    }

    public int getUserCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<User> rt = cq.from(User.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}