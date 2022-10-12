package com.smartship.backend.app.repositories;

import com.smartship.backend.app.models.User;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

@Primary
@Repository
@Transactional
public class UserRepositoryJpa implements UserRepository {
    @PersistenceContext
    EntityManager entityManager;

    @Override
    public User findById(long id) {
        return entityManager.find(User.class, id);
    }

    @Override
    public List<User> findAll() {
        TypedQuery<User> query = entityManager.createNamedQuery("find_all_users", User.class);
        return query.getResultList();
    }

    @Override
    public User save(User user) {
        return entityManager.merge(user);
    }

    @Override
    public User deleteById(long id) {
        throw new UnsupportedOperationException("Deletion of users not implemented yet");
    }

    @Override
    public User findByEmail(String email) {
        return entityManager.createNamedQuery("find_by_email", User.class)
                .setParameter("email", email)
                .getSingleResult();
    }
}
