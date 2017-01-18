package com.fateking.base;

import com.fateking.model.User;

/**
 * Created by dingxin on 16/12/12.
 */
public interface Global {

    ThreadLocal<User> currentUser = new ThreadLocal<>();

    default User getCurrentUser(){
        return currentUser.get();
    }

    default void remove(){
        currentUser.remove();
    }

    default void setCurrentUser(User user){
        currentUser.set(user);
    }
}
