package org.n3r.idworker;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.math.BigDecimal;
import java.util.Optional;

public class Test {

	public static void main(String[] args) {
        String x = "[[{name: \"ip地址\", value: \"143.0.15.91\"}," +
                "{name: \"cpu个数\", value: \"3个\"}," + "{name: \"mac地址\", value: \"M45980CB\"}," +
                "{name: \"操作系统\", value: \"linux\"}]]";
		System.out.println("---");
		JSONArray xx = JSONArray.parseArray(x);
		System.out.println(xx.toJSONString());


		JSONArray baseDataList = new JSONArray();
		JSONArray o = new JSONArray();
		JSONObject o1 = new JSONObject();
		o1.put("name","ip地址");
		o1.put("value", "143.0.15.91");
		JSONObject o2 = new JSONObject();
		o2.put("name","mac地址");
		o2.put("value","M45980CB");
		JSONObject o3 = new JSONObject();
		o3.put("name","cpu个数");
		o3.put("value","3");
		JSONObject o4 = new JSONObject();
		o4.put("name","操作系统");
		o4.put("value","window");
		o.add(o1);
		o.add(o2);
		o.add(o3);
		o.add(o4);
		baseDataList.add(o);
		System.out.println(baseDataList.toJSONString());
	}
}
