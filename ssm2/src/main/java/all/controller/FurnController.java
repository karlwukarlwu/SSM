package all.controller;

import all.bean.Furn;
import all.bean.Msg;
import all.service.FurnService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

/**
 * Karl Rules!
 * 2023/11/23
 * now File Encoding is UTF-8
 */
@Controller
public class FurnController {
    //注入配置FurnService
    @Resource
    private FurnService furnService;

    @PostMapping("/save")
    @ResponseBody
//    @ResponseBody会自动返回网页需要的数据格式（自动识别 不止json）
//    @RequestBody 将打过来的请求 转成被注解的javabean Furn类型的
    public Msg save(@Validated @RequestBody Furn furn, Errors errors){
//        用validated注解来校验 对应的是Furn上面被加了@notnull这些乱七八糟的注解
        HashMap<String, Object> map = new HashMap<>();
        List<FieldError> fieldErrors = errors.getFieldErrors();
        for (FieldError fieldError : fieldErrors) {
//            这个是spring自带的校验器
//            public String getField() {
//		        return this.field;
//	          }
            map.put(fieldError.getField(),fieldError.getDefaultMessage());
        }
        if (map.isEmpty()) {//说明后端校验通过,因为没有发现校验错误
            furnService.save(furn);
            //返回成功msg
            return Msg.success();
        } else {
            //校验失败，把校验错误信息封装到Msg对象，并返回
            return Msg.fail().add("errorMsg", map);
        }

//        System.out.println("save方法被调用");
//        furnService.save(furn);
////        furnMapper.insertSelective(furn);实际调用的是这个 不用担心缺少参数的问题
//        return Msg.success();
    }
    @RequestMapping("/furns")
    @ResponseBody
    public Msg listFurns(){
        List<Furn> all = furnService.findAll();
        return Msg.success().add("furns",all);
    }

    @PutMapping("/update")
    @ResponseBody
    public Msg update(@RequestBody Furn furn){
        furnService.update(furn);
        System.out.println(furn.getMaker());
        return Msg.success();
    }

    @DeleteMapping("/del/{id}")
//    @PathVariable 用于获取路径上的参数
    @ResponseBody
    public Msg del(@PathVariable Integer id) {
        furnService.del(id);
        return Msg.success();
    }

    //提供接口,根据id返回对应的furn对象-封装Msg
    @GetMapping("/find/{id}")
    @ResponseBody
    public Msg findById(@PathVariable Integer id) {
        Furn furn = furnService.findById(id);
        return Msg.success().add("furn", furn);
    }
    /**
     * 分页请求接口
     *
     * @param pageNum:  要显示第几页 : 默认为1
     * @param pageSize: 每页要显示几条记录：默认为5
     * @return
     */
    @ResponseBody
    @RequestMapping("/furnsByPage")
    public Msg listFurnsByPage(@RequestParam(defaultValue = "1") Integer pageNum,
                               @RequestParam(defaultValue = "5") Integer pageSize) {

        //设置分页参数
        //老师解读
        //1.调用findAll是完成查询，底层会进行物理分页，而不是逻辑分页
        //2.会根据分页参数来计算 limit ?, ?, 在发出SQL语句时，会带limit
        //3.我们后面会给大家抓取SQL
        PageHelper.startPage(pageNum, pageSize);

        List<Furn> furnList = furnService.findAll();


        //将分页查询的结果，封装到PageInfo
        //PageInfo 对象包含了分页的各个信息,
        //比如当前页面pageNum,共有多少记录
        //...

        PageInfo pageInfo = new PageInfo(furnList, pageSize);
        //将pageInfo封装到Msg对象，返回
        return Msg.success().add("pageInfo", pageInfo);
    }

    @ResponseBody
    @RequestMapping("/furnsByConditionPage")
    public Msg listFurnsByConditionPage(@RequestParam(defaultValue = "1") Integer pageNum,
                                        @RequestParam(defaultValue = "5") Integer pageSize,
                                        @RequestParam(defaultValue = "") String search) {

        PageHelper.startPage(pageNum, pageSize);
        List<Furn> furnList = furnService.findByCondition(search);

        PageInfo pageInfo = new PageInfo(furnList, pageSize);

        //将pageInfo封装到Msg对象，返回
        return Msg.success().add("pageInfo", pageInfo);
    }
}
