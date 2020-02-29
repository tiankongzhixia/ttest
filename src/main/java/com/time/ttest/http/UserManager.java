package com.time.ttest.http;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class UserManager {


    private List<String> userNameList = new ArrayList<>();

    @Getter @Setter
    private String defaultUser;

    public UserManager(String defaultUser) {
        this.setDefaultUser(defaultUser);
    }


    public String getUserName(){
        if (userNameList.size()>0){
            String userName = userNameList.get(userNameList.size() -1);
            userNameList.remove(userNameList.size() -1);
            return userName;
        }
        return defaultUser;
    }

    public void setUserName(String userName){
        userNameList.add(userName);
    }

}
