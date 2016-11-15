package com.druid.dao;

import com.druid.domain.Person;
import com.druid.support.CustomRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by 1115 on 2016/11/15.
 */
public interface PersonRepository extends CustomRepository<Person,Long> {
    List<Person> findByAddress(String name);

    Person findByNameAndAddress(String name,String address);

    @Query("select p from Person p where p.name=:name and p.address=:address")
    Person withNameAndAddressQuery(@Param("name")String name,@Param("address")String address);

    List<Person> withNameAndAddressNamedQuery(String name,String address);
}
