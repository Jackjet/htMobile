package com.kwchina.work.morningMeet.entity;

import com.kwchina.core.base.entity.User;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "x_union_detail")
@Data
public class UnionCheck {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer unionId;
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="reporterId")
    private User reporter ;
    private Date reportDate;
    private String attach;
    private String category;
}

