package com.brandon.repositories.menus;

import com.brandon.entities.menus.MenuEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by brandon Lee on 2016-08-26.
 */
@Repository
public interface MenuRepository extends JpaRepository<MenuEntity, Long> {
}
