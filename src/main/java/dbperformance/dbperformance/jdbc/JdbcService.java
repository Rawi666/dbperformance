package dbperformance.dbperformance.jdbc;

import java.util.List;

import javax.sql.DataSource;

import com.google.common.collect.Lists;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dbperformance.dbperformance.jpa.TestEntity;

@Service
@Transactional
public class JdbcService {
    private JdbcTemplate jdbc;

    @Autowired
    public JdbcService(DataSource dataSource) {
        this.jdbc = new JdbcTemplate(dataSource);
    }

    public void insert(List<TestEntity> entities) {
        int batchSize = 1000;
        var batches = Lists.partition(entities, batchSize);

        for (var batch : batches) {
            var sb = new StringBuilder();
            sb.append("INSERT INTO test_entity (column1, column2, column3, column4, column5, column6, column7, column8, column9, column10, column11, column12, column13, column14, column15, column16) VALUES ");
            int i = 0;
            for (var it : batch) {
                sb.append("(");
                sb.append("'" + it.getColumn1() + "',");
                sb.append("'" + it.getColumn2() + "',");
                sb.append("'" + it.getColumn3() + "',");
                sb.append("'" + it.getColumn4() + "',");
                sb.append("'" + it.getColumn5() + "',");
                sb.append("'" + it.getColumn6() + "',");
                sb.append("'" + it.getColumn7() + "',");
                sb.append("'" + it.getColumn8() + "',");
                sb.append("'" + it.getColumn9() + "',");
                sb.append("'" + it.getColumn10() + "',");
                sb.append("'" + it.getColumn11() + "',");
                sb.append("'" + it.getColumn12() + "',");
                sb.append("'" + it.getColumn13() + "',");
                sb.append("'" + it.getColumn14() + "',");
                sb.append("'" + it.getColumn15() + "',");
                sb.append("'" + it.getColumn16() + "'");
                sb.append(")");
                i++;
                if (i < batch.size()) {
                    sb.append(",");
                }
            }

            jdbc.update(sb.toString());
        }
    }
}
