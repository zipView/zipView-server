package zipview_server.zipview.user;

import org.springframework.stereotype.Repository;
import zipview_server.config.BaseException;
import zipview_server.zipview.user.dto.PatchPwdReq;
import zipview_server.zipview.user.dto.PostLoginReq;
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
    public String GetRepoPwd(String email) {
        return em.createQuery("select u.password from User u where u.email= :email")
                .setParameter("email",email)
                .getSingleResult().toString();
    }
    public String GetRepoId(String email) {
        return em.createQuery("select u.id from User u where u.email= :email")
                .setParameter("email",email)
                .getSingleResult().toString();
    }
    public String ExistUser(PatchPwdReq patchPwdReq) throws NoResultException {
        return em.createQuery("select u.email from User u where u.email= :email and u.name = :name and u.phone = :phone")
                .setParameter("email", patchPwdReq.getEmail())
                .setParameter("name",patchPwdReq.getName())
                .setParameter("phone",patchPwdReq.getPhone())
                .getSingleResult().toString();
    }

    public List<User> findByEmail(String email) {
        return em.createQuery("select u from User u where u.email = :email", User.class)
                .setParameter("email",email).getResultList();
    }
    public List<User> findByNickName(String nickname){
        return em.createQuery("select u from User u where u.nickname = :nickname ", User.class)
                .setParameter("nickname",nickname).getResultList();
    }

    public String findId(String name, String email){
        return em.createQuery("select u.id from User u where u.name= :name and u.email= :email")
                .setParameter("name",name)
                .setParameter("email",email).getSingleResult().toString();

    }

    public User findOne(String id) {
        return em.find(User.class,id);
    }


}
