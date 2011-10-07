
package dao;
import dao.exceptions.IllegalOrphanException;
import dao.exceptions.NonexistentEntityException;
import domain.Tournament;
import domain.Tournamentregistration;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Collection;

/**
 *
 * @author root
 */
public class TournamentJpaController {

    public TournamentJpaController() {
        emf = Persistence.createEntityManagerFactory("VirtualRisikoPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Tournament tournament) {
        if (tournament.getTournamentregistrationCollection() == null) {
            tournament.setTournamentregistrationCollection(new ArrayList<Tournamentregistration>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Tournamentregistration> attachedTournamentregistrationCollection = new ArrayList<Tournamentregistration>();
            for (Tournamentregistration tournamentregistrationCollectionTournamentregistrationToAttach : tournament.getTournamentregistrationCollection()) {
                tournamentregistrationCollectionTournamentregistrationToAttach = em.getReference(tournamentregistrationCollectionTournamentregistrationToAttach.getClass(), tournamentregistrationCollectionTournamentregistrationToAttach.getTournamentregistrationPK());
                attachedTournamentregistrationCollection.add(tournamentregistrationCollectionTournamentregistrationToAttach);
            }
            tournament.setTournamentregistrationCollection(attachedTournamentregistrationCollection);
            em.persist(tournament);
            for (Tournamentregistration tournamentregistrationCollectionTournamentregistration : tournament.getTournamentregistrationCollection()) {
                Tournament oldTournamentOfTournamentregistrationCollectionTournamentregistration = tournamentregistrationCollectionTournamentregistration.getTournament();
                tournamentregistrationCollectionTournamentregistration.setTournament(tournament);
                tournamentregistrationCollectionTournamentregistration = em.merge(tournamentregistrationCollectionTournamentregistration);
                if (oldTournamentOfTournamentregistrationCollectionTournamentregistration != null) {
                    oldTournamentOfTournamentregistrationCollectionTournamentregistration.getTournamentregistrationCollection().remove(tournamentregistrationCollectionTournamentregistration);
                    oldTournamentOfTournamentregistrationCollectionTournamentregistration = em.merge(oldTournamentOfTournamentregistrationCollectionTournamentregistration);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Tournament tournament) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Tournament persistentTournament = em.find(Tournament.class, tournament.getId());
            Collection<Tournamentregistration> tournamentregistrationCollectionOld = persistentTournament.getTournamentregistrationCollection();
            Collection<Tournamentregistration> tournamentregistrationCollectionNew = tournament.getTournamentregistrationCollection();
            List<String> illegalOrphanMessages = null;
            for (Tournamentregistration tournamentregistrationCollectionOldTournamentregistration : tournamentregistrationCollectionOld) {
                if (!tournamentregistrationCollectionNew.contains(tournamentregistrationCollectionOldTournamentregistration)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Tournamentregistration " + tournamentregistrationCollectionOldTournamentregistration + " since its tournament field is not nullable.");
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
            tournament.setTournamentregistrationCollection(tournamentregistrationCollectionNew);
            tournament = em.merge(tournament);
            for (Tournamentregistration tournamentregistrationCollectionNewTournamentregistration : tournamentregistrationCollectionNew) {
                if (!tournamentregistrationCollectionOld.contains(tournamentregistrationCollectionNewTournamentregistration)) {
                    Tournament oldTournamentOfTournamentregistrationCollectionNewTournamentregistration = tournamentregistrationCollectionNewTournamentregistration.getTournament();
                    tournamentregistrationCollectionNewTournamentregistration.setTournament(tournament);
                    tournamentregistrationCollectionNewTournamentregistration = em.merge(tournamentregistrationCollectionNewTournamentregistration);
                    if (oldTournamentOfTournamentregistrationCollectionNewTournamentregistration != null && !oldTournamentOfTournamentregistrationCollectionNewTournamentregistration.equals(tournament)) {
                        oldTournamentOfTournamentregistrationCollectionNewTournamentregistration.getTournamentregistrationCollection().remove(tournamentregistrationCollectionNewTournamentregistration);
                        oldTournamentOfTournamentregistrationCollectionNewTournamentregistration = em.merge(oldTournamentOfTournamentregistrationCollectionNewTournamentregistration);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = tournament.getId();
                if (findTournament(id) == null) {
                    throw new NonexistentEntityException("The tournament with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Tournament tournament;
            try {
                tournament = em.getReference(Tournament.class, id);
                tournament.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tournament with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Tournamentregistration> tournamentregistrationCollectionOrphanCheck = tournament.getTournamentregistrationCollection();
            for (Tournamentregistration tournamentregistrationCollectionOrphanCheckTournamentregistration : tournamentregistrationCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Tournament (" + tournament + ") cannot be destroyed since the Tournamentregistration " + tournamentregistrationCollectionOrphanCheckTournamentregistration + " in its tournamentregistrationCollection field has a non-nullable tournament field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(tournament);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Tournament> findTournamentEntities() {
        return findTournamentEntities(true, -1, -1);
    }

    public List<Tournament> findTournamentEntities(int maxResults, int firstResult) {
        return findTournamentEntities(false, maxResults, firstResult);
    }

    private List<Tournament> findTournamentEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Tournament.class));
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

    public Tournament findTournament(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Tournament.class, id);
        } finally {
            em.close();
        }
    }

    public int getTournamentCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Tournament> rt = cq.from(Tournament.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}