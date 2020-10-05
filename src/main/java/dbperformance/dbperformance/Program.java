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
import dbperformance.dbperformance.pgbulkinsert.PgBulkInsertService;
import dbperformance.dbperformance.vertx.VertxService;
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

    @Autowired
    private VertxService vertxService;

    @Autowired
    private PgBulkInsertService pgBulkInsertService;

    private int numberOfEntities = 100_000;

    @Override
    public void run(String... args) throws Exception {
        // startJpaSingle();
        // startJpaList();
        // startJpaListSaveAll();
        // startMyBatisSingle();
        // startMyBatisList();
        // starJdbcList();
        // starVertxSingle();
        // starVertxList(); 
        // starVertxListWithoutTransaction();
        starPgBulkInsert();
    }

    private void starPgBulkInsert() {
        var sw = new StopWatch();
        sw.start();
        var entities = generateEntities(numberOfEntities);
        sw.stop();
        log.info("Generating entities took: {}ms", sw.getTotalTimeMillis());
        
        sw = new StopWatch();
        sw.start();
        pgBulkInsertService.insert(entities);
        sw.stop();
        log.info("Inserting entities (pg bulk insert) took: {}s", sw.getTotalTimeSeconds());
    }

    private void starVertxSingle() {
        var sw = new StopWatch();
        sw.start();
        var entities = generateEntities(numberOfEntities);
        sw.stop();
        log.info("Generating entities took: {}ms", sw.getTotalTimeMillis());
        
        sw = new StopWatch();
        sw.start();
        for (var e : entities) {
            vertxService.insert(e);
        }
        sw.stop();
        log.info("Inserting entities (vertx single) took: {}s", sw.getTotalTimeSeconds());
    }

    private void starVertxList() {
        var sw = new StopWatch();
        sw.start();
        var entities = generateEntities(numberOfEntities);
        sw.stop();
        log.info("Generating entities took: {}ms", sw.getTotalTimeMillis());
        
        sw = new StopWatch();
        sw.start();
        vertxService.insert(entities);
        sw.stop();
        log.info("Inserting entities (vertx list) took: {}s", sw.getTotalTimeSeconds());
    }

    private void starVertxListWithoutTransaction() {
        var sw = new StopWatch();
        sw.start();
        var entities = generateEntities(numberOfEntities);
        sw.stop();
        log.info("Generating entities took: {}ms", sw.getTotalTimeMillis());
        
        sw = new StopWatch();
        sw.start();
        vertxService.insertWithoutTransaction(entities);
        sw.stop();
        log.info("Inserting entities (vertx list without transaction) took: {}s", sw.getTotalTimeSeconds());
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
        log.info("Inserting entities (jdbc list) took: {}s", sw.getTotalTimeSeconds());
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
        log.info("Inserting entities (MyBatis single) took: {}s", sw.getTotalTimeSeconds());
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
        log.info("Inserting entities (MyBatis list) took: {}s", sw.getTotalTimeSeconds());
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
        log.info("Inserting entities (JPA single) took: {}s", sw.getTotalTimeSeconds());
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
        log.info("Inserting entities (JPA list) took: {}s", sw.getTotalTimeSeconds());
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
        log.info("Inserting entities (JPA list save all) took: {}s", sw.getTotalTimeSeconds());
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
