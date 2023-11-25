package all.service.Impl;

import all.bean.Furn;
import all.bean.FurnExample;
import all.dao.FurnMapper;
import all.service.FurnService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * Karl Rules!
 * 2023/11/23
 * now File Encoding is UTF-8
 */
@Service
public class FurnServiceImpl implements FurnService {

    @Resource
    private FurnMapper furnMapper;
//    这个可以在test 文件中找到 以后我们需要属性的时候直接用这个注解调用
//    FurnMapper furnMapper = ioc.getBean(FurnMapper.class);

    @Override
    public void save(Furn furn) {
//        都用selective 是因为我们的id 是自增的
        furnMapper.insertSelective(furn);
    }

    @Override
    public List<Furn> findAll() {

        return furnMapper.selectByExample(null);
    }

    @Override
    public void update(Furn furn) {
        furnMapper.updateByPrimaryKeySelective(furn);
    }

    @Override
    public void del(Integer id) {
        furnMapper.deleteByPrimaryKey(id);
    }

    @Override
    public Furn findById(Integer id) {
        return furnMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<Furn> findByCondition(String name) {
//        这个是mybatis 生成的方法
        FurnExample furnExample = new FurnExample();
        //通过Criteria 对象可以设置查询条件
        FurnExample.Criteria criteria = furnExample.createCriteria();

        //判断name是有具体的内容 如果是空格就不用查询了
        if (StringUtils.hasText(name)) {
//            这些全是mybatis逆向工程的方法
            criteria.andNameLike("%" + name + "%");
        }
        //老师说明:如果name没有传值null ,"", "   ", 依然是查询所有的记录
        return furnMapper.selectByExample(furnExample);
    }
}
