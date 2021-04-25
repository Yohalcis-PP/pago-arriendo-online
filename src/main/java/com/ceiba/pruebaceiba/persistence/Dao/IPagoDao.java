package com.ceiba.pruebaceiba.persistence.Dao;

import com.ceiba.pruebaceiba.persistence.entity.Pago;
import com.ceiba.pruebaceiba.persistence.entity.PagoPK;
import org.springframework.data.repository.CrudRepository;

public interface IPagoDao extends CrudRepository<Pago, PagoPK> {
}
