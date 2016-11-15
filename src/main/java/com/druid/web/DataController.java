package com.druid.web;

import com.druid.dao.PersonRepository;
import com.druid.domain.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by 1115 on 2016/11/15.
 */
@RestController
public class DataController {
    @Autowired
    PersonRepository personRepository;

    @RequestMapping("/save")
    public Person save(String name,String address,Integer age){
        Person p = personRepository.save(new Person(null, name, age, address));
        return p;
    }
    @RequestMapping("/q1")
    public List<Person> q1(String address){
        List<Person> people = personRepository.findByAddress(address);
        return people;
    }
    @RequestMapping("/q2")
    public Person q2(String name,String address){
        Person p = personRepository.findByNameAndAddress(name, address);
        return p;
    }
    @RequestMapping("/q3")
    public Person q3(String name,String address){
        Person p = personRepository.withNameAndAddressQuery(name,address);
        return p;
    }
    @RequestMapping("/q4")
    public Person q4(String name,String address){
        List<Person> people = personRepository.withNameAndAddressNamedQuery(name, address);
        return people.get(0);
    }
    @RequestMapping("/sort")//排序
    public List<Person> sort(){
        List<Person> people = personRepository.findAll(new Sort(Sort.Direction.ASC, "age"));
        return people;
    }
    @RequestMapping("/page")//分页
    public Page<Person> page(){
        Page<Person> people = personRepository.findAll(new PageRequest(1, 2));
        return people;
    }
    @RequestMapping("/auto")//模糊查询，自动匹配类型为String 的属性，前后加%
    public Page<Person> auto(Person person){
        Page<Person> persons = personRepository.findByAuto(person, new PageRequest(0, 10));
        return persons;
    }
}
