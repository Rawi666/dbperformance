package dbperformance.dbperformance;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import dbperformance.dbperformance.jdbc.JdbcService;
import dbperformance.dbperformance.jpa.JpaService;
import dbperformance.dbperformance.jpa.TestEntity;
import dbperformance.dbperformance.mybatis.MyBatisService;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class Program implements CommandLineRunner {

    @Autowired
    private JpaService jpaService;

    @Autowired
    private MyBatisService myBatisService;

    @Autowired
    private JdbcService jdbcService;

    private int numberOfEntities = 10_000;

    @Override
    public void run(String... args) throws Exception {
        startJpaSingle();
        startJpaList();
        startJpaListSaveAll();
        startJpaListSaveAllWithBatches();
        startMyBatisSingle();
        startMyBatisList();
        starJdbcList();
    }
    
    private void starJdbcList() {
        var sw = new StopWatch();
        sw.start();
        var entities = generateEntities(numberOfEntities);
        sw.stop();
        log.info("Generating entities took: {}ms", sw.getTotalTimeMillis());
        
        sw = new StopWatch();
        sw.start();
        jdbcService.insert(entities);
        sw.stop();
        log.info("Generating entities (jdbc list) took: {}s", sw.getTotalTimeSeconds());
    }

    private void startMyBatisSingle() {
        var sw = new StopWatch();
        sw.start();
        var entities = generateEntities(numberOfEntities);
        sw.stop();
        log.info("Generating entities took: {}ms", sw.getTotalTimeMillis());
        
        sw = new StopWatch();
        sw.start();
        for (var e : entities) {
            myBatisService.insert(e);
        }
        sw.stop();
        log.info("Generating entities (MyBatis single) took: {}s", sw.getTotalTimeSeconds());
    }

    private void startMyBatisList() {
        var sw = new StopWatch();
        sw.start();
        var entities = generateEntities(numberOfEntities);
        sw.stop();
        log.info("Generating entities took: {}ms", sw.getTotalTimeMillis());
        
        sw = new StopWatch();
        sw.start();
        myBatisService.insert(entities);
        sw.stop();
        log.info("Generating entities (MyBatis list) took: {}s", sw.getTotalTimeSeconds());
    }

    private void startJpaSingle() {
        var sw = new StopWatch();
        sw.start();
        var entities = generateEntities(numberOfEntities);
        sw.stop();
        log.info("Generating entities took: {}ms", sw.getTotalTimeMillis());
        
        sw = new StopWatch();
        sw.start();
        for (var e : entities) {
            jpaService.insert(e);
        }
        sw.stop();
        log.info("Generating entities (JPA single) took: {}s", sw.getTotalTimeSeconds());
    }

    private void startJpaList() {
        var sw = new StopWatch();
        sw.start();
        var entities = generateEntities(numberOfEntities);
        sw.stop();
        log.info("Generating entities took: {}ms", sw.getTotalTimeMillis());

        sw = new StopWatch();
        sw.start();
        jpaService.insert(entities);
        sw.stop();
        log.info("Generating entities (JPA list) took: {}s", sw.getTotalTimeSeconds());
    }

    private void startJpaListSaveAll() {
        var sw = new StopWatch();
        sw.start();
        var entities = generateEntities(numberOfEntities);
        sw.stop();
        log.info("Generating entities took: {}ms", sw.getTotalTimeMillis());

        sw = new StopWatch();
        sw.start();
        jpaService.bulkInsert(entities);
        sw.stop();
        log.info("Generating entities (JPA list save all) took: {}s", sw.getTotalTimeSeconds());
    }

    private void startJpaListSaveAllWithBatches() {
        var sw = new StopWatch();
        sw.start();
        var entities = generateEntities(numberOfEntities);
        sw.stop();
        log.info("Generating entities took: {}ms", sw.getTotalTimeMillis());

        sw = new StopWatch();
        sw.start();
        jpaService.bulkInsertWithBatches(entities);
        sw.stop();
        log.info("Generating entities (JPA list save all with batches) took: {}s", sw.getTotalTimeSeconds());
    }

    private List<TestEntity> generateEntities(int numberOfEntities) {
        var retList = new ArrayList<TestEntity>(numberOfEntities);

        for (int i = 0; i < numberOfEntities; i++) {
            var e = new TestEntity(
                UUID.randomUUID().toString(),
                UUID.randomUUID().toString(),
                UUID.randomUUID().toString(),
                UUID.randomUUID().toString(),
                UUID.randomUUID().toString(),
                UUID.randomUUID().toString(),
                UUID.randomUUID().toString(),
                UUID.randomUUID().toString(),
                UUID.randomUUID().toString(),
                UUID.randomUUID().toString(),
                UUID.randomUUID().toString(),
                UUID.randomUUID().toString(),
                UUID.randomUUID().toString(),
                UUID.randomUUID().toString(),
                UUID.randomUUID().toString(),
                UUID.randomUUID().toString()
            );
            retList.add(e);
        }
        return retList;
    }
    
}
