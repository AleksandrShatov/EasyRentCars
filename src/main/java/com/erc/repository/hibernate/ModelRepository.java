package com.erc.repository.hibernate;

import com.erc.domain.hibernate.Model;
import com.erc.repository.CrudOperations;

import java.util.List;

public interface ModelRepository extends CrudOperations<Long, Model> {

    List<Model> findByModelName(String modelName);

    List<Model> findByManufacturer(String manufacturer);

    List<Model> findByFuel(String fuel);

}