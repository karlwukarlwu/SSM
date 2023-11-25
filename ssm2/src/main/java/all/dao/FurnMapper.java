package all.dao;

import all.bean.Furn;
import all.bean.FurnExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface FurnMapper {
//    mapper中实际上是有 Furn和FurnExample两个参数的
    long countByExample(FurnExample example);

    int deleteByExample(FurnExample example);

    int deleteByPrimaryKey(Integer id);

//    全部添加 如果某些属性没有设置就设置为null
    int insert(Furn record);

//    选择性的添加 如果有些属性没有设置就忽略 属性保持默认值
    int insertSelective(Furn record);
//    insert会将数据表所有字段都新增。未手动设置值得，都会被null替代，包括默认值
//    insertSelective只新增设置的参数，默认值未修改不会被替换

    List<Furn> selectByExample(FurnExample example);

    Furn selectByPrimaryKey(Integer id);


    int updateByExampleSelective(@Param("record") Furn record, @Param("example") FurnExample example);
    int updateByExample(@Param("record") Furn record, @Param("example") FurnExample example);

    //根据你设置属性对应字段，生成sql语句 没设置的不管 只修改你设置的字段
    int updateByPrimaryKeySelective(Furn record);
    //会修改所有的字段，如果没有设置字段对应的属性值，那么默认是null
    int updateByPrimaryKey(Furn record);
}