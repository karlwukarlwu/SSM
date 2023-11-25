import all.bean.Furn;
import all.dao.FurnMapper;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.math.BigDecimal;

/**
 * Karl Rules!
 * 2023/11/23
 * now File Encoding is UTF-8
 */
public class MapperTest {
    @Test
    public void insertSelective() {

        //1. 获取到容器
        ApplicationContext ioc =
                new ClassPathXmlApplicationContext("applicationContext.xml");
        //2获取FurnMapper
        FurnMapper furnMapper = ioc.getBean(FurnMapper.class);
        //System.out.println("furnMapper--" + furnMapper.getClass());
        //3.添加数据
        Furn furn =
                new Furn(null, "北欧风格沙发~~", "顺平家居~~", new BigDecimal(180), 666, 70, "assets/images/product-image/1.jpg");

        int affected = furnMapper.insertSelective(furn);
        System.out.println("affected--" + affected);
        System.out.println("操作成功~");
    }

    @Test
    public void updateByPrimaryKey() {

        //1. 获取到容器
        ApplicationContext ioc =
                new ClassPathXmlApplicationContext("applicationContext.xml");
        //2获取FurnMapper
        FurnMapper furnMapper = ioc.getBean(FurnMapper.class);

        Furn furn = new Furn();
        furn.setId(5);
        furn.setName("顺平风格的家居-小沙发");

        //会修改所有的字段，如果没有设置字段对应的属性值，那么默认是null
        //int affected = furnMapper.updateByPrimaryKey(furn);

        //根据你设置属性对应字段，生成sql语句
        int affected = furnMapper.updateByPrimaryKeySelective(furn);
        System.out.println("affected--" + affected);
        System.out.println("操作成功~");
    }

    @Test
    public void selectByPrimaryKey() {

        //1. 获取到容器
        ApplicationContext ioc =
                new ClassPathXmlApplicationContext("applicationContext.xml");
        //2获取FurnMapper
        FurnMapper furnMapper = ioc.getBean(FurnMapper.class);

        Furn furn = furnMapper.selectByPrimaryKey(1);
        System.out.println("furn--" + furn);
        System.out.println("操作成功~");
    }

}
