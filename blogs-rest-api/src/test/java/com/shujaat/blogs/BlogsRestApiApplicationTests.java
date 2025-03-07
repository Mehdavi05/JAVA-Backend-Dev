package com.shujaat.blogs;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = BlogsRestApiApplication.class, properties = "spring.profiles.active=test")
class BlogsRestApiApplicationTests {

	@Test
	void contextLoads() {
	}
}
