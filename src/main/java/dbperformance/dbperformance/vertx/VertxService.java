package dbperformance.dbperformance.vertx;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

import com.google.common.collect.Lists;

import org.springframework.stereotype.Service;

import dbperformance.dbperformance.jpa.TestEntity;
import io.vertx.pgclient.PgConnectOptions;
import io.vertx.pgclient.PgPool;
import io.vertx.sqlclient.PoolOptions;
import io.vertx.sqlclient.Transaction;
import io.vertx.sqlclient.Tuple;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class VertxService {
    private PgPool getClient() {
        var connectOptions = new PgConnectOptions()
            .setPort(5432)
            .setHost("localhost")
            .setDatabase("postgres")
            .setUser("postgres")
            .setPassword("mysecretpassword");
        connectOptions.setCachePreparedStatements(true);
        var poolOptions = new PoolOptions()
            .setMaxSize(5);
        var client = PgPool.pool(connectOptions, poolOptions);
        return client;
    }

    @SneakyThrows
    public void insert(TestEntity entity) {
        var client = getClient();
        var tuple = Tuple.of(
            entity.getColumn1(),
            entity.getColumn2(),
            entity.getColumn3(),
            entity.getColumn4(),
            entity.getColumn5(),
            entity.getColumn6(),
            entity.getColumn7(),
            entity.getColumn8(),
            entity.getColumn9(),
            entity.getColumn10(),
            entity.getColumn11(),
            entity.getColumn12(),
            entity.getColumn13(),
            entity.getColumn14(),
            entity.getColumn15(),
            entity.getColumn16());
        var countDownLatch = new CountDownLatch(1);
        client.preparedQuery("INSERT INTO test_entity (column1, column2, column3, column4, column5, column6, column7, column8, column9, column10, column11, column12, column13, column14, column15, column16) VALUES ($1, $2, $3, $4, $5, $6, $7, $8, $9, $10, $11, $12, $13, $14, $15, $16)")
            .execute(tuple, ar -> {
                if (!ar.succeeded()) {
                    log.error("Failed inserting batch", ar.cause());
                }
                countDownLatch.countDown();
            });

        countDownLatch.await();
        client.close();
    }

    @SneakyThrows
    public void insertWithoutTransaction(List<TestEntity> entities) {
        var client = getClient();
        int batchSize = 1000;
        var batches = Lists.partition(entities, batchSize);
        var query = client.preparedQuery("INSERT INTO test_entity (column1, column2, column3, column4, column5, column6, column7, column8, column9, column10, column11, column12, column13, column14, column15, column16) VALUES ($1, $2, $3, $4, $5, $6, $7, $8, $9, $10, $11, $12, $13, $14, $15, $16)");

        var countDownLatch = new CountDownLatch(batches.size());
        for (var batch : batches) {
            var list = batch.stream().map(it -> Tuple.of(
                it.getColumn1(),
                it.getColumn2(),
                it.getColumn3(),
                it.getColumn4(),
                it.getColumn5(),
                it.getColumn6(),
                it.getColumn7(),
                it.getColumn8(),
                it.getColumn9(),
                it.getColumn10(),
                it.getColumn11(),
                it.getColumn12(),
                it.getColumn13(),
                it.getColumn14(),
                it.getColumn15(),
                it.getColumn16()
                )).collect(Collectors.toList());
            query.executeBatch(list, ar -> {
                if (!ar.succeeded()) {
                    log.error("Failed inserting batch", ar.cause());
                }
                countDownLatch.countDown();
            });
        }

        countDownLatch.await();
        client.close();
    }

    @SneakyThrows
    public void insert(List<TestEntity> entities) {
        var client = getClient();
        int batchSize = 1000;
        var batches = Lists.partition(entities, batchSize);
        var countDownLatch = new CountDownLatch(batches.size());
        var tran = new AtomicReference<Transaction>();

        client.begin(res -> {
            if (res.succeeded()) {
                var tx = res.result();
                tran.set(res.result());

                var query = tx.preparedQuery("INSERT INTO test_entity (column1, column2, column3, column4, column5, column6, column7, column8, column9, column10, column11, column12, column13, column14, column15, column16) VALUES ($1, $2, $3, $4, $5, $6, $7, $8, $9, $10, $11, $12, $13, $14, $15, $16)");

                for (var batch : batches) {
                    var list = batch.stream().map(it -> Tuple.of(
                        it.getColumn1(),
                        it.getColumn2(),
                        it.getColumn3(),
                        it.getColumn4(),
                        it.getColumn5(),
                        it.getColumn6(),
                        it.getColumn7(),
                        it.getColumn8(),
                        it.getColumn9(),
                        it.getColumn10(),
                        it.getColumn11(),
                        it.getColumn12(),
                        it.getColumn13(),
                        it.getColumn14(),
                        it.getColumn15(),
                        it.getColumn16()
                        )).collect(Collectors.toList());
                    query.executeBatch(list, ar -> {
                        if (!ar.succeeded()) {
                            log.error("Failed inserting batch", ar.cause());
                        }
                        countDownLatch.countDown();
                    });
                }
            }
        });

        countDownLatch.await();
        
        tran.get().commit(r -> {
            if (r.succeeded()) {
                System.out.println("Transaction succeeded");
            } else {
                System.out.println("Transaction failed " + r.cause().getMessage());
            }
        });
        client.close();
    }
}
