package zipview_server.zipview.feedback;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import zipview_server.zipview.feedback.dto.Feedback;
import zipview_server.zipview.feedback.dto.GetNoticeRes;
import zipview_server.zipview.feedback.dto.Notice;
import zipview_server.zipview.user.dto.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class FeedBackRepository {
    @PersistenceContext
    private EntityManager em;

    public void save(Feedback feedback) {
        em.persist(feedback);
    }

    @Transactional
    public void saveNotice(Notice notice){em.persist(notice);}
    public List<Notice> getNotice(){
        return em.createQuery("select n from Notice n", Notice.class)
              .getResultList();
    }
}
