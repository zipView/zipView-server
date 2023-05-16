package zipview_server.zipview.feedback;

import org.springframework.stereotype.Repository;
import zipview_server.zipview.feedback.dto.Feedback;
import zipview_server.zipview.user.dto.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class FeedBackRepository {
    @PersistenceContext
    private EntityManager em;

    public void save(Feedback feedback) {
        em.persist(feedback);
    }

}
