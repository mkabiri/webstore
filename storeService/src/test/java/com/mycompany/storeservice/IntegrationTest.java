package com.mycompany.storeservice;

import com.mycompany.storeservice.StoreServiceApp;
import com.mycompany.storeservice.config.TestSecurityConfiguration;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Base composite annotation for integration tests.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@SpringBootTest(classes = { StoreServiceApp.class, TestSecurityConfiguration.class })
public @interface IntegrationTest {
}
