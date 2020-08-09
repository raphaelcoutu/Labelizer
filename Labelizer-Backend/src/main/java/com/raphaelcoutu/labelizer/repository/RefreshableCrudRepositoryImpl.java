package com.raphaelcoutu.labelizer.repository;

import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.io.Serializable;

public class RefreshableCrudRepositoryImpl<T, ID extends Serializable> extends SimpleJpaRepository<T, ID>
        implements RefreshableCrudRepository<T, ID> {

    private final EntityManager entityManager;

    public RefreshableCrudRepositoryImpl(JpaEntityInformation<T, ?> entityInformation, EntityManager entityManager) {
        super(entityInformation, entityManager);
        this.entityManager = entityManager;
    }

    @Override
    @Transactional
    public void refresh(T t) {
        entityManager.refresh(t);
    }
}
