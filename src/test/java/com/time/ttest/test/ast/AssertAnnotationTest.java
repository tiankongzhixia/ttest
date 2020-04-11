package com.time.ttest.test.ast;

import com.time.ttest.annotations.Assert;
import com.time.ttest.annotations.Asserts;
import com.time.ttest.asserts.TAssertType;
import org.testng.annotations.Guice;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * @Auther guoweijie

 * @Date 2020-04-11 14:40
 */
@Guice
public class AssertAnnotationTest {


    @Assert(expect = "ok")
    public String assertAstString(){
        return "ok";
    }

    @Assert(expect = "{expect}")
    public String assertAstString(String expect){
        return "ok";
    }

    @Assert(expect = "{expect}")
    public Integer assertAstInt(Integer expect){
        return 200;
    }

    @Assert(path = "$.id",expect = "{expectId}")
    public TTestAssertDTO assertAstObj(Integer expectId){
        return TTestAssertDTO.builder().id(1).name("测试").build();
    }

    @Asserts({
            @Assert(assertType = TAssertType.NotEquals,expect = "false"),
            @Assert(assertType = TAssertType.EqualsTrue),
            @Assert(assertType = TAssertType.NotNull)
    })
    public Boolean assertAstTrue(){
        return true;
    }

    @Assert(assertType = TAssertType.EqualsFalse)
    public Boolean assertAstFalse(){
        return false;
    }

    @Assert(assertType = TAssertType.IsNULL)
    public TTestAssertDTO assertAstNull(){
        return null;
    }


    @Asserts({
            @Assert(assertType = TAssertType.NotNull),
            @Assert(path = "$.id",expect = "{expectId}"),
            @Assert(path = "$.name",expect = "{expectName}"),
            @Assert(path = "$.parent.size()",expect = "3"),
            @Assert(path = "$.parent.size()",expect = "2",assertType = TAssertType.NotEquals),
            @Assert(path = "$.parent",expect = "{parent}",assertType = TAssertType.ObjectEquals),
            @Assert(path = "$.parent..name",expect = "{expectName}"),
    })
    public TTestAssertDTO assertAstObj(Integer expectId, String expectName, TTestAssertDTO parent){
        return TTestAssertDTO.builder()
                .id(1)
                .name("测试")
                .parent(new TTestAssertDTO[]{
                        TTestAssertDTO.builder().name("测试").build(),
                        TTestAssertDTO.builder().name("测试").build(),
                        TTestAssertDTO.builder().name("测试").build(),
                })
                .build();
    }

    @Test
    public void assertAst1(){
        assertAstString();
    }

    @Test
    public void assertAst2(){
        assertAstString("ok");
    }

    @Test
    public void assertAst3(){
        assertAstInt(200);
    }

    @Test
    public void assertAst4(){
        assertAstObj(1,"测试",TTestAssertDTO.builder().name("测试").build());
    }

    @Test
    public void assertAst5(){
        assertAstTrue();
    }

    @Test
    public void assertAst6(){
        assertAstFalse();
    }

    @Test
    public void assertAst7(){
        assertAstNull();
    }
}
