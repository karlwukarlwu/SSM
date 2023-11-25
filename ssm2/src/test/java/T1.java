import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Karl Rules!
 * 2023/11/23
 * now File Encoding is UTF-8
 */
public class T1 {
    @Test
    public void test1(){
        ApplicationContext ioc = new ClassPathXmlApplicationContext("applicationContext.xml");
        System.out.println(ioc.getBean("pooledDataSource"));
        System.out.println(ioc.getBean("sqlSessionFactory"));
    }
}

