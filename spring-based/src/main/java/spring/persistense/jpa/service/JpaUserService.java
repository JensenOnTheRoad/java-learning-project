package spring.persistense.jpa.service;

import java.util.List;
import java.util.Optional;

/**
 * @author lin
 */
public interface JpaUserService {

  /** 查询所有用户 */
  List<JpaUserDO> findAll();

  Optional<JpaUserDO> findOne(JpaUserDO object);

  Optional<JpaUserDO> save(JpaUserDO object);

  void truncateTable();
}
