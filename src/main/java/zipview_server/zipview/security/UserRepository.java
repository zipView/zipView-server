package zipview_server.zipview.security;

import org.springframework.stereotype.Repository;
import zipview_server.zipview.security.dto.Member;
import zipview_server.zipview.sender.Dto.PostEmailReq;
import zipview_server.zipview.security.dto.PostUserIdReq;
import zipview_server.zipview.user.dto.User;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class UserRepository {

    @PersistenceContext
    private EntityManager em;

    public void save(Member user) {
        em.persist(user);
    }

    public String GetUserEmail(PostUserIdReq postUserIdReq) throws NoResultException {
        String name = postUserIdReq.getName();
        String phone = postUserIdReq.getPhone();
        return em.createQuery("select u.email from Member u where u.name= :name and u.phone= :phone")
                .setParameter("name", name)
                .setParameter("phone", phone).getSingleResult().toString();
    }
    public String GetRepoPwd(String email) {
        return em.createQuery("select u.password from Member u where u.email= :email")
                .setParameter("email",email)
                .getSingleResult().toString();
    }
    public String GetRepoPwdById(String id) {
        return em.createQuery("select u.password from Member u where u.id= :id")
                .setParameter("id",id)
                .getSingleResult().toString();
    }

    public String GetRepoId(String email) {
        return em.createQuery("select u.id from Member u where u.email= :email")
                .setParameter("email",email)
                .getSingleResult().toString();
    }

    public String ExistUser(PostEmailReq postEmailReq) throws NoResultException {
        return em.createQuery("select u.email from Member u where u.email= :email and u.name = :name and u.phone = :phone")
                .setParameter("email", postEmailReq.getEmail())
                .setParameter("name",postEmailReq.getName())
                .setParameter("phone",postEmailReq.getPhone())
                .getSingleResult().toString();
    }

    public List<Member> findByEmail(String email) {
        return em.createQuery("select u from Member u where u.email = :email", Member.class)
                .setParameter("email",email).getResultList();
    }
    public List<Member> findByNickName(String nickname){
        return em.createQuery("select u from Member u where u.nickname = :nickname ", Member.class)
                .setParameter("nickname",nickname).getResultList();
    }
    public List<Member> findById(String id) {
        return em.createQuery("select u from Member u where u.id = :id", Member.class)
                .setParameter("id",id).getResultList();
    }

    public String findId(String name, String email){
        return em.createQuery("select u.id from Member u where u.name= :name and u.email= :email")
                .setParameter("name",name)
                .setParameter("email",email).getSingleResult().toString();

    }

<<<<<<< HEAD:src/main/java/zipview_server/zipview/security/UserRepository.java
<<<<<<< Updated upstream:src/main/java/zipview_server/zipview/user/UserRepository.java
=======

>>>>>>> 05411ae49f77b0abd7462f9d720d7579b583001f:src/main/java/zipview_server/zipview/user/UserRepository.java
    public User findOne(String id) {
        return em.find(User.class,id);
=======

    public Member findOne(String id) {
        return em.find(Member.class,id);
>>>>>>> Stashed changes:src/main/java/zipview_server/zipview/security/UserRepository.java
    }


}
