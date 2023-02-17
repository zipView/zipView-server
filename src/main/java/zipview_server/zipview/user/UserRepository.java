package zipview_server.zipview.user;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class UserRepository {

    @PersistenceContext
    private EntityManager em;

    public void save(User user) {
        em.persist(user);
    }

    public List<User> findByEmail(String email) {
        return em.createQuery("select u from User u where u.email = :email", User.class)
                .setParameter("email",email).getResultList();
    }
    public List<User> findByNickName(String nickname){
        return em.createQuery("select u from User u where u.nickname = :nickname ", User.class)
                .setParameter("nickname",nickname).getResultList();
    }
}
