package com.time.ttest.http;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

@Slf4j
public class UserManager {


    private Stack<String> threadUserName = new Stack<>();

    @Getter @Setter
    private String defaultUser;

    UserManager(String defaultUser) {
        this.setDefaultUser(defaultUser);
    }


    String getUserName(){
        if (threadUserName.size()>0){
            log.debug("pop thread user {}",threadUserName.peek());
            return threadUserName.pop();
        }
        return defaultUser;
    }

    public void setUserName(String userName){
        threadUserName.push(userName);
    }

}
