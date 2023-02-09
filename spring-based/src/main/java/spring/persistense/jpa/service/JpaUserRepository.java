package spring.persistense.jpa.service;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author jensen_deng
 */
public interface JpaUserRepository
    extends JpaRepository<JpaUserDO, Long>, JpaSpecificationExecutor<JpaUserDO> {

  @Transactional
  @Modifying
  @Query(value = "truncate table user", nativeQuery = true)
  default void truncateTable() {}
}
