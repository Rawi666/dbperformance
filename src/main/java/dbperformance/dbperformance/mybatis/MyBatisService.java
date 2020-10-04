package dbperformance.dbperformance.mybatis;

import java.util.List;

import com.google.common.collect.Lists;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dbperformance.dbperformance.jpa.TestEntity;

@Service
@Transactional
public class MyBatisService {
    @Autowired
    private TestEntityMapper mapper;

    public void insert(TestEntity entity) {
        mapper.insert(entity);
    }

    public void insert(List<TestEntity> entities) {
        var batches = Lists.partition(entities, 1000);

        for (var batch : batches) {
            mapper.bulkInsert(batch);
        }
    }
}
