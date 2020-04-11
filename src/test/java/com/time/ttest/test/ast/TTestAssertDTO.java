package com.time.ttest.test.ast;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

/**
 * @Auther guoweijie

 * @Date 2020-04-11 14:44
 */
@Builder
@Data
public class TTestAssertDTO {

    public TTestAssertDTO() {
    }

    public TTestAssertDTO(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public TTestAssertDTO(Integer id, String name, TTestAssertDTO[] parent) {
        this.id = id;
        this.name = name;
        this.parent = parent;
    }

    public TTestAssertDTO(Integer id, String name, Date time, TTestAssertDTO[] parent) {
        this.id = id;
        this.name = name;
        this.time = time;
        this.parent = parent;
    }

    private Integer id;

    private String name;

    private Date time;

    private TTestAssertDTO[] parent;
}
