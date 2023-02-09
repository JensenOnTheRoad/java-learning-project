package spring.persistense.jpa.service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

@Service
public class JpaUserServiceImpl implements JpaUserService {
  @Autowired private JpaUserRepository repository;

  @Override
  public List<JpaUserDO> findAll() {
    return repository.findAll();
  }

  @Override
  public Optional<JpaUserDO> save(JpaUserDO object) {
    JpaUserDO save = repository.save(object);
    object.setId(save.getId());
    return Optional.of(object);
  }

  @Override
  public Optional<JpaUserDO> findOne(JpaUserDO object) {
    Example<JpaUserDO> example = Example.of(object);
    return repository.findOne(example);
  }

  @Override
  public void truncateTable() {
    repository.truncateTable();
  }
}
