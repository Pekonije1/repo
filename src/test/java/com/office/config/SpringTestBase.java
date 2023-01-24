package com.office.config;

import com.github.database.rider.spring.api.DBRider;
import lombok.extern.slf4j.Slf4j;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@DBRider
@Slf4j
public abstract class SpringTestBase {
}
