package dbperformance.dbperformance.jpa;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class JpaService {
    private TestRepository repository;

    @Autowired
    public JpaService(TestRepository repository) {
        this.repository = repository;
    }

    public void insert(TestEntity entity) {
        repository.save(entity);
    }

    public void insert(List<TestEntity> entities) {
        for (var entity : entities) {
            repository.save(entity);
        }
    }

    public void bulkInsert(List<TestEntity> entities) {
        repository.saveAll(entities);
    }
}
