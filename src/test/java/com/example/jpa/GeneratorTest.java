package com.example.jpa;

import com.example.jpa.freemarker.generator.Generator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author lamen 2022/4/5
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringDataJpaApplication.class)
public class GeneratorTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    public void testGenerator() {
        Generator generator = new Generator();
        generator.generate(jdbcTemplate, "teacher");
    }
}
