package fr.abberrouyne.kata;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import fr.abberrouyne.kata.KataApiApplication;

@SpringBootTest(classes = KataApiApplication.class)
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("simulation")
public class KataApiApplicationTests {

    @Test
    public void contextLoads() {
    }

}
