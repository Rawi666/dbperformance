package dbperformance.dbperformance.pgbulkinsert;

import java.sql.DriverManager;
import java.util.List;

import org.postgresql.PGConnection;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dbperformance.dbperformance.jpa.TestEntity;
import de.bytefish.pgbulkinsert.row.SimpleRowWriter;
import lombok.SneakyThrows;

@Service
@Transactional
public class PgBulkInsertService {
    @Value("${spring.datasource.url}")
    private String url;
    @Value("${spring.datasource.username}")
    private String username;
    @Value("${spring.datasource.password}")
    private String password;

    @SneakyThrows
    public void insert(List<TestEntity> entities) {
        var connection = (PGConnection) DriverManager.getConnection(url, username, password);

        var columnNames = new String[] {
            "column1",
            "column2",
            "column3",
            "column4",
            "column5",
            "column6",
            "column7",
            "column8",
            "column9",
            "column10",
            "column11",
            "column12",
            "column13",
            "column14",
            "column15",
            "column16"
        };

        var table = new SimpleRowWriter.Table("public", "test_entity", columnNames);

        try (var writer = new SimpleRowWriter(table, connection)) {
            for (int rowIdx = 0; rowIdx < entities.size(); rowIdx++) {
                var e = entities.get(rowIdx);
                writer.startRow((row) -> {
                    row.setText("column1", e.getColumn1());
                    row.setText("column2", e.getColumn2());
                    row.setText("column3", e.getColumn3());
                    row.setText("column4", e.getColumn4());
                    row.setText("column5", e.getColumn5());
                    row.setText("column6", e.getColumn6());
                    row.setText("column7", e.getColumn7());
                    row.setText("column8", e.getColumn8());
                    row.setText("column9", e.getColumn9());
                    row.setText("column10", e.getColumn10());
                    row.setText("column11", e.getColumn11());
                    row.setText("column12", e.getColumn12());
                    row.setText("column13", e.getColumn13());
                    row.setText("column14", e.getColumn14());
                    row.setText("column15", e.getColumn15());
                    row.setText("column16", e.getColumn16());
                });
            }
        }
    }
}
