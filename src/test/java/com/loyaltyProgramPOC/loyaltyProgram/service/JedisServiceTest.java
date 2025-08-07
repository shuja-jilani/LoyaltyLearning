//package com.loyaltyProgramPOC.loyaltyProgram.service;
//
//import com.loyaltyProgramPOC.loyaltyProgram.service.JedisService;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//@SpringBootTest
//class JedisServiceTest {
//
//    @Autowired
//    private JedisService jedisService;
//
//    @Test
//    void testSetAndGet() {
//        jedisService.setValue("emp:name", "shuja");
//        String value = jedisService.getValue("emp:name");
//        System.out.println("Retrieved value: " + value);
//        Assertions.assertEquals("shuja", value);
//    }
//
//    @Test
//    void testSetWithExpiry() throws InterruptedException {
//        jedisService.setValueWithExpiry("temp:key", "tempValue", 2); // 2 seconds expiry
//        String value = jedisService.getValue("temp:key");
//        Assertions.assertEquals("tempValue", value);
//
//        Thread.sleep(3000); // wait 3 seconds
//        Assertions.assertNull(jedisService.getValue("temp:key"));
//    }
//
//    @Test
//    void testExists() {
//        jedisService.setValue("check:key", "value");
//        boolean exists = jedisService.exists("check:key");
//        Assertions.assertTrue(exists);
//    }
//}
//
