import all.dao.FurnMapper;
import org.junit.Test;
import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.internal.DefaultShellCallback;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Karl Rules!
 * 2023/11/23
 * now File Encoding is UTF-8
 */
public class MBGTest {
    @Test
    public void generator() throws Exception {

        List<String> warnings = new ArrayList<String>();
        boolean overwrite = true;
        //这里要指定你自己配置的mbg.xml
        //如果这样访问，需要将文件放在项目下
        File configFile = new File("mbg.xml");
        ConfigurationParser cp = new ConfigurationParser(warnings);
        Configuration config = cp.parseConfiguration(configFile);
        DefaultShellCallback callback = new DefaultShellCallback(overwrite);
        MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
        myBatisGenerator.generate(null);

    }

    @Test
    public void deleteByPrimaryKey() {

        //1. 获取到容器
        ApplicationContext ioc =
                new ClassPathXmlApplicationContext("applicationContext.xml");
        //2获取FurnMapper
        FurnMapper furnMapper = ioc.getBean(FurnMapper.class);

        int affected = furnMapper.deleteByPrimaryKey(4);
        System.out.println("affected--" + affected);
        System.out.println("操作成功");
    }


}
