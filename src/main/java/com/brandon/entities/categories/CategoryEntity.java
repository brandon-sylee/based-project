package com.brandon.entities.categories;

import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import javax.persistence.*;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * Created by Naver on 2016-08-08.
 */
@Data
@Entity
public class CategoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long seq;

    private String name;

    private Long parent;
}
