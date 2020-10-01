package dbperformance.dbperformance.mybatis;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;

import dbperformance.dbperformance.jpa.TestEntity;

@Mapper
public interface TestEntityMapper {
    @Insert("INSERT INTO test_entity (column1, column2, column3, column4, column5, column6, column7, column8, column9, column10, column11, column12, column13, column14, column15, column16) "
        + "VALUES(#{column1}, #{column2}, #{column3}, #{column4}, #{column5}, #{column6}, #{column7}, #{column8}, #{column9}, #{column10}, #{column11}, #{column12}, #{column13}, #{column14}, #{column15}, #{column16})")
    @Options(useGeneratedKeys = false)
    void insert(TestEntity entity);

    @Insert({"<script>",
        "INSERT INTO test_entity (column1, column2, column3, column4, column5, column6, column7, column8, column9, column10, column11, column12, column13, column14, column15, column16) ",
        "VALUES ",
        "<foreach collection=\"entities\" item=\"it\" separator=\",\"> ",
        "(#{it.column1}, #{it.column2}, #{it.column3}, #{it.column4}, #{it.column5}, #{it.column6}, #{it.column7}, #{it.column8}, #{it.column9}, #{it.column10}, #{it.column11}, #{it.column12}, #{it.column13}, #{it.column14}, #{it.column15}, #{it.column16})",
        "</foreach>",
        "</script>"})
    @Options(useGeneratedKeys = false)
    void bulkInsert(List<TestEntity> entities);
}
