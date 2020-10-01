package dbperformance.dbperformance.jpa;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "test_entity")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class TestEntity {
    @Id
    @Column(name = "column1")
    private String column1;
    @Column(name = "column2")
    private String column2;
    @Column(name = "column3")
    private String column3;
    @Column(name = "column4")
    private String column4;
    @Column(name = "column5")
    private String column5;
    @Column(name = "column6")
    private String column6;
    @Column(name = "column7")
    private String column7;
    @Column(name = "column8")
    private String column8;
    @Column(name = "column9")
    private String column9;
    @Column(name = "column10")
    private String column10;
    @Column(name = "column11")
    private String column11;
    @Column(name = "column12")
    private String column12;
    @Column(name = "column13")
    private String column13;
    @Column(name = "column14")
    private String column14;
    @Column(name = "column15")
    private String column15;
    @Column(name = "column16")
    private String column16;
}
