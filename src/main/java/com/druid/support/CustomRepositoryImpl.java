package com.druid.support;

import com.druid.specs.CustomerSpecs;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import javax.persistence.EntityManager;
import java.io.Serializable;

/**
 * Created by 1115 on 2016/11/15.
 */
public class CustomRepositoryImpl<T,ID extends Serializable>extends SimpleJpaRepository<T,ID> implements CustomRepository<T,ID> {
    private final EntityManager entityManager;
    public CustomRepositoryImpl(Class<T> domainClass, EntityManager em) {
        super(domainClass, em);
        this.entityManager=em;
    }

    @Override
    public Page<T> findByAuto(T example, Pageable pageable) {
        return findAll(CustomerSpecs.byAuto(entityManager,example),pageable);
    }
}
