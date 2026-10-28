package com.lenskart.repository;

import org.springframework.data.repository.CrudRepository;

import com.lenskart.entity.PaymentEntity;

public interface PaymentRepository extends CrudRepository<PaymentEntity, Long>{

}
