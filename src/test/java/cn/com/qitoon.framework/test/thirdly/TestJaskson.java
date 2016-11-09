/*
package cn.com.qitoon.framework.test.thirdly;


import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.quickgo.platform.utils.JsonUtil;
import org.junit.Test;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

public class TestJaskson {

	
	@Test
	public void test_1() {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("zimu", "zimu");
		map.put("中文", "中文");
		map.put("数字", 50);
		System.out.println(JsonUtil.getInstance().obj2json(map));
		
		Map<String,Object> map2 = (Map<String,Object>)JsonUtil.getInstance().json2obj("{\"zimu1\":\"zimu1\",\"数字1\":500,\"中文1\":\"中文1\"}", Map.class);
		System.out.println(map2.get("zimu1"));
		System.out.println(map2.get("数字1"));
		System.out.println(map2.get("中文1"));
		
		// PC端审核节点跳转地址的url
    	String handleTaskUrl = null;
    	// APP端审核节点跳转地址的url
    	String appUrl = "";
    	Map<String,String> urlMap = new HashMap<String,String>();
    	urlMap.put("handleTaskUrl", handleTaskUrl);
    	urlMap.put("appUrl", appUrl);
    	String urlJson = JsonUtil.getInstance().obj2json(urlMap);
    	System.out.println(urlJson);
    	
    	Map<String,Object> bbb = (Map<String,Object>)JsonUtil.getInstance().json2obj(urlJson,Object.class);
    	System.out.println(bbb);
    	
    	Object aa = JsonUtil.getInstance().json2obj("http://www.syswin.com",Object.class);
    	System.out.println(aa);
    	
    	Object bb = JsonUtil.getInstance().json2obj("{\"appUrl\":\"\",\"handleTaskUrl\":null}",Object.class);
    	System.out.println(bb);
	}
	
	@Test
	public void test_2() {
		List<RuleTreeVo> ruleTreeVoList = new ArrayList<RuleTreeVo>();
		RuleTreeVo ruleTreeVo1 = new RuleTreeVo();
		ruleTreeVo1.setId("1");
		ruleTreeVo1.setIsLeafNode("0");
		ruleTreeVo1.setNodeRule("#报销金额#>=90");
		
		RuleTreeVo ruleTreeVo2 = new RuleTreeVo();
		ruleTreeVo2.setId("2");
		ruleTreeVo2.setIsLeafNode("1");
		ruleTreeVo2.setNodeRule("#报销预算#<190");
		
		ruleTreeVoList.add(ruleTreeVo1);
		ruleTreeVoList.add(ruleTreeVo2);
		
		System.out.println(JsonUtil.getInstance().obj2json(ruleTreeVoList));
		
		String json = "[{\"id\":\"1\",\"pid\":\"null\",\"nodeName\":null,\"nodeRule\":\"#报销金额#>=90\",\"isLeafNode\":\"0\",\"ruleId\":null},{\"id\":\"2\",\"pid\":null,\"nodeName\":null,\"nodeRule\":\"#报销预算#<190\",\"isLeafNode\":\"1\",\"ruleId\":null}]";
		// 第一种方式
		List<Map<String,Object>> ruleTreeVoListNew = (List<Map<String,Object>>)JsonUtil.getInstance().json2obj(json, List.class);
		// 第二种方式
		try {
			JsonNode node = JsonUtil.getMapper().readTree(json);
		} catch (JsonProcessingException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		// 第三种方式
		JavaType javaType = JsonUtil.getCollectionType(ArrayList.class, ArrayList.class,RuleTreeVo.class); 
        List<RuleTreeVo> lst = null;
		try {
			lst = (List<RuleTreeVo>)JsonUtil.getMapper().readValue(json, javaType);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} 
		
		RuleTreeVo ruleTreeVo = lst.get(0);
		System.out.println(ruleTreeVo.getNodeRule());
		System.out.println(ruleTreeVo.getNodeName());
		System.out.println(ruleTreeVo.getNodeName()==null);
		System.out.println(ruleTreeVo.getPid());
		System.out.println(ruleTreeVo.getPid()==null);
	}
	
	@SuppressWarnings({ "unchecked", "unused" })
	@Test
	public void test_3() {
		List<Object> ruleTreeVoList = new ArrayList<Object>();
		RuleTreeVo ruleTreeVo1 = new RuleTreeVo();
		ruleTreeVo1.setId("1");
		ruleTreeVo1.setIsLeafNode("0");
		ruleTreeVo1.setNodeRule("#报销金额#>=90");
		
		RuleTaskNode taskNode = new RuleTaskNode();
		taskNode.setNodeName("任务节点");
		taskNode.setOrderNum(1l);
		ruleTreeVoList.add(ruleTreeVo1);
		ruleTreeVoList.add(taskNode);
		
		System.out.println(JsonUtil.getInstance().obj2json(ruleTreeVoList));
		
		String json = "[{\"id\":\"1\",\"pid\":null,\"nodeName\":null,\"nodeRule\":\"#报销金额#>=90\",\"isLeafNode\":\"0\",\"ruleId\":null},{\"id\":null,\"ruleid\":null,\"nodename\":\"任务节点\",\"participant\":null,\"ordernum\":1}]";
		// 第一种方式
		List<Map<String,Object>> ruleTreeVoListNew = (List<Map<String,Object>>)JsonUtil.getInstance().json2obj(json, List.class);
		// 第二种方式
		try {
			JsonNode node = JsonUtil.getMapper().readTree(json);
			System.out.println(node.get(1).get("nodename"));
		} catch (JsonProcessingException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	
	@Test
	public void test_4() {
		Date da = new Date(1457742107655L);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		System.out.println(sdf.format(da));
	}
	
}
*/
