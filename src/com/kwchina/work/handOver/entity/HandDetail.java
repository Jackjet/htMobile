package com.kwchina.work.handOver.entity;

import com.kwchina.core.base.entity.User;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;
@Entity
@Table(name = "x_hand_detail")
@Data
public class HandDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer handId;
    private String title;
    private String content;
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="handerId")
    private User hander;
    private Date handDate;
    private String attach;
    private boolean valid;
}

