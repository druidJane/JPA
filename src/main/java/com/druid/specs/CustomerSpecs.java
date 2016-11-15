package com.druid.specs;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.criteria.*;
import javax.persistence.metamodel.Attribute;
import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.SingularAttribute;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import static com.google.common.collect.Iterables.toArray;


/**
 * Created by 1115 on 2016/11/15.
 */
public class CustomerSpecs {
    public static<T>Specification<T> byAuto(final EntityManager entityManager,final  T example){//1返回值为Specification的方法byAuto
        final Class<T> type = (Class<T>) example.getClass();//2获得入参example类型

        return new Specification<T>() {
            @Override
            public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<>();//3新建Predicate列表存储构造的查询条件
                EntityType<T> entity = entityManager.getMetamodel().entity(type);//4可以从EntityType获得入参实体类的属性
                for(Attribute<T,?> attr:entity.getDeclaredAttributes()){//5对实体类的所有属性做循环
                    Object attrValue = getValue(example,attr);//6获取实体类某一个属性的值
                    if(attrValue != null){
                        if(attr.getJavaType() == String.class){//7当前属性为字符的时候
                            if(!StringUtils.isEmpty(attrValue)){//8当前属性不为空
                                predicates.add(cb.like(root.get(attribute(entity,attr.getName(),String.class)),
                                        pattern((String)attrValue)));//9构造当前属性的like(前后加%)作为模糊查询
                            }
                        }else{
                            predicates.add(cb.equal(root.get(attribute(entity,attr.getName(),attrValue.getClass())),
                                    attrValue));//10其余情况，一律走equal查询
                        }
                    }
                }
                return predicates.isEmpty()?cb.conjunction():cb.and(toArray(predicates,Predicate.class));//11
            }
            //13获取当前属性的SingularAttribute，SingularAttribute包含实体类的单独属性
            private <E,T> SingularAttribute<T, E> attribute(EntityType<T> entity, String fieldName,Class<E>fieldClass) {
                return entity.getDeclaredSingularAttribute(fieldName,fieldClass);
            }
            //12通过反射获取属性值
            private <T> Object getValue(T example, Attribute<T, ?> attr) {
                return ReflectionUtils.getField((Field)attr.getJavaMember(),example);
            }
        };
    }
    //前有加%
    private static String pattern(String str) {
        return "%"+str+"%";
    }


}
