package zipview_server.zipview.user;

import org.springframework.stereotype.Repository;
import zipview_server.config.BaseException;
import zipview_server.zipview.user.dto.PostUserIdReq;
import zipview_server.zipview.user.dto.User;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class UserRepository {

    @PersistenceContext
    private EntityManager em;

    public void save(User user) {
        em.persist(user);
    }

    public String GetUserEmail(PostUserIdReq postUserIdReq) throws NoResultException {
        String name = postUserIdReq.getName();
        String phone = postUserIdReq.getPhone();
        return em.createQuery("select u.email from User u where u.name= :name and u.phone= :phone")
                .setParameter("name", name)
                .setParameter("phone", phone).getSingleResult().toString();
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
