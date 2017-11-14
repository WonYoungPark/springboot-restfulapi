package io.github.wonyoungpark.system.account.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by wyPark on 2016. 6. 13..
 */
@Data
@Entity
public class Account {
    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    private String password;

    private String name;

    private String role;

    @Temporal(TemporalType.TIMESTAMP)
    private Date rgstDt; // 등록일시

    private String rgstId; // 등록ID

    @Temporal(TemporalType.TIMESTAMP)
    private Date updtDt; // 수정일시

    private String updtId; // 수정ID
}