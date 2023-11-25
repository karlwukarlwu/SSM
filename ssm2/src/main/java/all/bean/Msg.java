package all.bean;

import java.util.HashMap;
import java.util.Map;

/**
 * Karl Rules!
 * 2023/11/23
 * now File Encoding is UTF-8
 * Msg:后端返回给前端的json
 */
public class Msg {
    //状态码 200-成功 400-失败
    private int code;
    //信息-说明
    private String msg;
    //返回给客户端/浏览器的数据-Map集合
//    因为你不知道返回多少的数据，所以用map
    private Map<String, Object> extend =
            new HashMap<>();

    //编写几个常用的方法-封装好msg
    //返回success对应的msg
    public static Msg success() {
        Msg msg = new Msg();
        msg.setCode(200);
        msg.setMsg("success");
        return msg;
    }

    //返回fail对应的msg
    public static Msg fail() {
        Msg msg = new Msg();
        msg.setCode(400);
        msg.setMsg("fail");
        return msg;
    }

    //给返回的msg设置数据-不难应该可以看懂
//    如果成功了 要返回数据
//    具体看需求
//    当我们调用类方法的时候 会给我们返回一个对象 我们拿着这个对象 来调用非静态方法
//    成功了以后是这样一个写法return Msg.success().add("furnList", furnList);
    public Msg add(String key, Object value) {
        extend.put(key, value);
        return this;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Map<String, Object> getExtend() {
        return extend;
    }

    public void setExtend(Map<String, Object> extend) {
        this.extend = extend;
    }
}
