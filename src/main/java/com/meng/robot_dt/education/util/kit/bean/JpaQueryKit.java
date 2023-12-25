package com.meng.robot_dt.education.util.kit.bean;

import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.HashMap;
import java.util.List;

/**
 * @author taorun
 * @date 2023/1/11 16:10
 */
@Component
public class JpaQueryKit {
    @PersistenceContext
    private EntityManager entityManager;

    /**
     * Jpa 查询结果返回 HashMap
     *
     * @param sql
     * @return
     */
    public HashMap getMap(String sql) {
        Query nativeQuery = entityManager.createNativeQuery(sql);
        nativeQuery.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        return (HashMap) nativeQuery.getSingleResult();
    }

    /**
     * Jpa 查询结果返回 List<HashMap>
     *
     * @param sql
     * @return
     */
    public List<HashMap> getListMap(String sql) {
        Query nativeQuery = entityManager.createNativeQuery(sql);
        nativeQuery.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        return nativeQuery.getResultList();
    }

}
