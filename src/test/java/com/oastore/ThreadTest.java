package com.oastore;

import com.oastore.utils.ThreadLocalUtil;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;
import java.util.Map;


public class ThreadTest {
    @Test
    public void testLocal(){
        ThreadLocal t1 =new ThreadLocal<>();

        new  Thread(()->{
            t1.set("TYC");
            System.out.println(Thread.currentThread().getName()+":"+t1.get());
        },"蓝色").start();

        new  Thread(()->{
            t1.set("CJL");
            System.out.println(Thread.currentThread().getName()+":"+t1.get());
        },"红色").start();
    }
}
