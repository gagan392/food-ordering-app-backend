package org.upgrad.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.upgrad.models.States;

@Repository
public interface StateRepository extends CrudRepository<States, Integer> {

}
