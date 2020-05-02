package com.upgrad.stackoverflow.service.dao;

import com.upgrad.stackoverflow.service.entity.UserEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

/**
 * AdminDao class provides the database access for all the endpoints in admin controller.
 */
@Repository
public class AdminDao {

    @PersistenceContext
    private EntityManager entityManager;


    public UserEntity deleteUser(UserEntity userEntity) {
        entityManager.remove(userEntity);
        return userEntity;
    }

    public UserEntity getUserByUuid(String uuid) {
        try {
            return entityManager.createNamedQuery("userByUuid", UserEntity.class).setParameter("uuid", uuid).getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }
    }


}
