package all.service;

import all.bean.Furn;

import java.util.List;

/**
 * Karl Rules!
 * 2023/11/23
 * now File Encoding is UTF-8
 */

public interface FurnService {
    //添加
    public void save(Furn furn);
    //查询所有的家居信息
    public List<Furn> findAll();
    //修改家居
    public void update(Furn furn);
    //删除家居
    public void del(Integer id);
    //根据id返回Furn
    public Furn findById(Integer id);

    //根据家居名称进行查询
    public List<Furn> findByCondition(String name);
}
