package com.example.exceptiontt;

import com.example.exceptiontt.jpa.JpaDoReturn;
import com.example.exceptiontt.jpa.entityJpa.UserJpa;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import static org.mockito.Mockito.doReturn;

@SpringBootTest
public class JpaTest {
    @Mock
    private UserJpa userJpa;

    @Mock
    private JpaDoReturn jpaDoReturn;
    @Test
    public void testJpa(){
        userJpa.setUserName("jack");
        userJpa.setAge(22);
        UserJpa userJpa1 = doReturn(userJpa).when(jpaDoReturn).getUserJpa();
        System.out.println(userJpa1.getUserName());
    }
}

