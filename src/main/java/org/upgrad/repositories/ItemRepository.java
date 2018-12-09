package org.upgrad.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.upgrad.models.Item;

public interface ItemRepository extends CrudRepository<Item, Integer>{

    @Query(nativeQuery = true, value = "SELECT * from ITEM WHERE ID=?1")
    Item getItemById(int id);
}
