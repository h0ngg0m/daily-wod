package hong.dailywod.domain.user.repository;

import jakarta.persistence.EntityManager;

import hong.dailywod.domain.user.model.User;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepositoryCustom {

    private final EntityManager entityManager;

    @Override
    public User persist(User user) {
        entityManager.persist(user);
        return user;
    }
}
