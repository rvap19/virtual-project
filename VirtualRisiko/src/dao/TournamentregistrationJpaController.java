
package dao;
import domain.Tournamentregistration;
import domain.TournamentregistrationPK;
import dao.exceptions.NonexistentEntityException;
import dao.exceptions.PreexistingEntityException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import domain.User;
import domain.Tournament;

/**
 *
 * @author root
 */
public class TournamentregistrationJpaController {

    public TournamentregistrationJpaController() {
        emf = Persistence.createEntityManagerFactory("VirtualRisikoPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Tournamentregistration tournamentregistration) throws PreexistingEntityException, Exception {
        if (tournamentregistration.getTournamentregistrationPK() == null) {
            tournamentregistration.setTournamentregistrationPK(new TournamentregistrationPK());
        }
        tournamentregistration.getTournamentregistrationPK().setUserUsername(tournamentregistration.getUser().getUsername());
        tournamentregistration.getTournamentregistrationPK().setTournamentID(tournamentregistration.getTournament().getId());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            User user = tournamentregistration.getUser();
            if (user != null) {
                user = em.getReference(user.getClass(), user.getUsername());
                tournamentregistration.setUser(user);
            }
            Tournament tournament = tournamentregistration.getTournament();
            if (tournament != null) {
                tournament = em.getReference(tournament.getClass(), tournament.getId());
                tournamentregistration.setTournament(tournament);
            }
            em.persist(tournamentregistration);
            if (user != null) {
                user.getTournamentregistrationCollection().add(tournamentregistration);
                user = em.merge(user);
            }
            if (tournament != null) {
                tournament.getTournamentregistrationCollection().add(tournamentregistration);
                tournament = em.merge(tournament);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findTournamentregistration(tournamentregistration.getTournamentregistrationPK()) != null) {
                throw new PreexistingEntityException("Tournamentregistration " + tournamentregistration + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Tournamentregistration tournamentregistration) throws NonexistentEntityException, Exception {
        tournamentregistration.getTournamentregistrationPK().setUserUsername(tournamentregistration.getUser().getUsername());
        tournamentregistration.getTournamentregistrationPK().setTournamentID(tournamentregistration.getTournament().getId());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Tournamentregistration persistentTournamentregistration = em.find(Tournamentregistration.class, tournamentregistration.getTournamentregistrationPK());
            User userOld = persistentTournamentregistration.getUser();
            User userNew = tournamentregistration.getUser();
            Tournament tournamentOld = persistentTournamentregistration.getTournament();
            Tournament tournamentNew = tournamentregistration.getTournament();
            if (userNew != null) {
                userNew = em.getReference(userNew.getClass(), userNew.getUsername());
                tournamentregistration.setUser(userNew);
            }
            if (tournamentNew != null) {
                tournamentNew = em.getReference(tournamentNew.getClass(), tournamentNew.getId());
                tournamentregistration.setTournament(tournamentNew);
            }
            tournamentregistration = em.merge(tournamentregistration);
            if (userOld != null && !userOld.equals(userNew)) {
                userOld.getTournamentregistrationCollection().remove(tournamentregistration);
                userOld = em.merge(userOld);
            }
            if (userNew != null && !userNew.equals(userOld)) {
                userNew.getTournamentregistrationCollection().add(tournamentregistration);
                userNew = em.merge(userNew);
            }
            if (tournamentOld != null && !tournamentOld.equals(tournamentNew)) {
                tournamentOld.getTournamentregistrationCollection().remove(tournamentregistration);
                tournamentOld = em.merge(tournamentOld);
            }
            if (tournamentNew != null && !tournamentNew.equals(tournamentOld)) {
                tournamentNew.getTournamentregistrationCollection().add(tournamentregistration);
                tournamentNew = em.merge(tournamentNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                TournamentregistrationPK id = tournamentregistration.getTournamentregistrationPK();
                if (findTournamentregistration(id) == null) {
                    throw new NonexistentEntityException("The tournamentregistration with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(TournamentregistrationPK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Tournamentregistration tournamentregistration;
            try {
                tournamentregistration = em.getReference(Tournamentregistration.class, id);
                tournamentregistration.getTournamentregistrationPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tournamentregistration with id " + id + " no longer exists.", enfe);
            }
            User user = tournamentregistration.getUser();
            if (user != null) {
                user.getTournamentregistrationCollection().remove(tournamentregistration);
                user = em.merge(user);
            }
            Tournament tournament = tournamentregistration.getTournament();
            if (tournament != null) {
                tournament.getTournamentregistrationCollection().remove(tournamentregistration);
                tournament = em.merge(tournament);
            }
            em.remove(tournamentregistration);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Tournamentregistration> findTournamentregistrationEntities() {
        return findTournamentregistrationEntities(true, -1, -1);
    }

    public List<Tournamentregistration> findTournamentregistrationEntities(int maxResults, int firstResult) {
        return findTournamentregistrationEntities(false, maxResults, firstResult);
    }

    private List<Tournamentregistration> findTournamentregistrationEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Tournamentregistration.class));
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

    public Tournamentregistration findTournamentregistration(TournamentregistrationPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Tournamentregistration.class, id);
        } finally {
            em.close();
        }
    }

    public int getTournamentregistrationCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Tournamentregistration> rt = cq.from(Tournamentregistration.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}