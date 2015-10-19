import com.hus.Person;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * Created by user on 2015/10/19.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:applicationContext.xml" })
public class MyTest {
    @Autowired
    Person person1;

    @Test
    public void testA() {
        System.out.println(person1 == null);

        ApplicationContext ac = new ClassPathXmlApplicationContext(
            new String[] { "classpath:applicationContext.xml" });
        Person person2 = (Person) ac.getBean(Person.class);
        System.out.println(person2 == null);
    }
}
