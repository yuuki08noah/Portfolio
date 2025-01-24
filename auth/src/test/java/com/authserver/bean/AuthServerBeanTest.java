package com.authserver.bean;

import com.authserver.config.AuthConfig;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@SpringBootTest(classes = AuthConfig.class)
public class AuthServerBeanTest {
  private static AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AuthConfig.class);

  @Test
  public void test() {
    String[] beanNames = ac.getBeanDefinitionNames();
    for (String beanName : beanNames) {
      System.out.println(beanName);
    }
  }
}
