package com.exiger.services.accounting.tests;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.exiger.services.accounting.Application;

import static org.junit.Assert.fail;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class ApplicationTest {

	@Test
	public void test() {
		Application.main(new String[] {});
		fail("It fails by default.");
	}
}
