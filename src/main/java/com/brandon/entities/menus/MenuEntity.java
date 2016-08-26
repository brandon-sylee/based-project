package com.brandon.entities.menus;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Created by brandon Lee on 2016-08-26.
 */
@Data
@Entity
public class MenuEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long seq;

    private String name;

    private Long parent;

    private String link;
}
