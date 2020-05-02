package com.upgrad.stackoverflow.service.business;

import com.upgrad.stackoverflow.service.dao.AdminDao;
import com.upgrad.stackoverflow.service.dao.UserDao;
import com.upgrad.stackoverflow.service.entity.UserAuthEntity;
import com.upgrad.stackoverflow.service.entity.UserEntity;
import com.upgrad.stackoverflow.service.exception.AuthorizationFailedException;
import com.upgrad.stackoverflow.service.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AdminBusinessService {
//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.upgrad.stackoverflow.service.business;

import com.upgrad.stackoverflow.service.dao.AdminDao;
import com.upgrad.stackoverflow.service.dao.UserDao;
import com.upgrad.stackoverflow.service.entity.UserAuthEntity;
import com.upgrad.stackoverflow.service.entity.UserEntity;
import com.upgrad.stackoverflow.service.exception.AuthorizationFailedException;
import com.upgrad.stackoverflow.service.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

    @Service
    public class AdminBusinessService {
        @Autowired
        private UserDao userDao;
        @Autowired
        private AdminDao adminDao;

        public AdminBusinessService() {
        }

        @Transactional(
                propagation = Propagation.REQUIRED
        )
        public UserEntity deleteUser(String authorization, String uuid) throws AuthorizationFailedException, UserNotFoundException {
            UserAuthEntity userAuthEntity = this.userDao.getUserAuthByAccesstoken(authorization);
            if (userAuthEntity == null) {
                throw new AuthorizationFailedException("ATHR-001", "User has not signed in");
            } else if (userAuthEntity.getLogoutAt() != null) {
                throw new AuthorizationFailedException("ATHR-002", "User is signed out");
            } else if (userAuthEntity.getUser().getRole().equals("admin")) {
                UserEntity userEntity = this.adminDao.getUserByUuid(uuid);
                if (userEntity == null) {
                    throw new UserNotFoundException("USR-001", "User with entered uuid to be deleted does not exist");
                } else {
                    return this.adminDao.deleteUser(userEntity);
                }
            } else {
                throw new AuthorizationFailedException("ATHR-003", "Unauthorized Access, Entered user is not an admin");
            }
        }
    }

    @Autowired
    private UserDao userDao;

    @Autowired
    private AdminDao adminDao;

    /**
     * The method implements the business logic for userDelete endpoint.
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public UserEntity deleteUser(String authorization, String uuid) throws AuthorizationFailedException, UserNotFoundException {
        UserAuthEntity userAuthEntity = userDao.getUserAuthByAccesstoken(authorization);

    }
}
