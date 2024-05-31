package hong.dailywod.domain.user.repository;

import hong.dailywod.domain.user.model.User;

public interface UserRepositoryCustom {

    User persist(User user);
}
