import all.bean.Furn;
import all.service.FurnService;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.math.BigDecimal;
import java.util.List;

/**
 * Karl Rules!
 * 2023/11/23
 * now File Encoding is UTF-8
 */
public class FurnServiceTest {

    //属性
    private ApplicationContext ioc;
    //从spring容器中,获取的是FurnService接口对象/代理对象
    private FurnService furnService;

    @Before
    public void init() {
        ioc = new ClassPathXmlApplicationContext("applicationContext.xml");
        //老韩说明
        //1. 通过FurnService.class 类型获取 FurnService接口对象/代理对象
        furnService = ioc.getBean(FurnService.class);
        //com.sun.proxy.$Proxy21
        System.out.println("furnService-" + furnService.getClass());
    }

    @Test
//    为什么这里有事务 因为在配置文件applicationContext.xml中配置了事务 用的另一种切面编程配置的
    public void save() {

        Furn furn =
                new Furn(null, "小风扇", "顺平家居", new BigDecimal(180), 10,
                        70, "assets/images/product-image/1.jpg");

        furnService.save(furn);

        System.out.println("添加成功~");
    }

    @Test
    public void findAll() {

        List<Furn> furns = furnService.findAll();
        for (Furn furn : furns) {
            System.out.println("furn-" + furn);
        }
    }

    @Test
    public void update() {

        Furn furn = new Furn();
        furn.setId(1);
        furn.setName("北欧风格小桌子~~");
        furn.setMaker("小猪家居");
        //因为imgPath属性有一个默认值,这里用的updateSelective 不想用我们的默认值就设置成null
//        这样selective就会忽略这个字段
        //所以如果我们不希望生成update 语句有对imgPath 字段修改，就显式的设置null

        furn.setImgPath(null);
        furnService.update(furn);

        System.out.println("修改OK");

    }
    @Test
    public void del() {

        furnService.del(5);
        System.out.println("删除OK");
    }
    @Test
    public void findByCondition() {

        List<Furn> furns = furnService.findByCondition("风格");

        for (Furn furn : furns) {
            System.out.println("furn--" + furn);
        }

    }

}