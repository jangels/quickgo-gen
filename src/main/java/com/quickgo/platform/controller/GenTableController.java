/**
 * 
 */
package com.quickgo.platform.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import com.alibaba.fastjson.JSONObject;
import com.quickgo.platform.base.BaseController;
import com.quickgo.platform.dto.GenTableDto;
import com.quickgo.platform.model.GenTable;
import com.quickgo.platform.service.GenTableService;
import com.quickgo.platform.utils.GenUtils;
import com.quickgo.platform.utils.JsonResponse;
import com.quickgo.platform.utils.Page;
import com.quickgo.platform.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


/**
 *  表信息Controller
 * @ClassName: GenTableController
 * @author hugengyong
 * @date 2016-10-27
 * Copyright: Copyright (c) 2016
 * Company:快狗俱乐部
 */
@Controller
@RequestMapping(value = "/gen/genTable")
public class GenTableController extends BaseController {

	@Autowired
	private GenTableService genTableService;
	
	@ModelAttribute
	public GenTable get(@RequestParam(required=false) String id) {
		if (StringUtils.isNotBlank(id)){
			return genTableService.get(id);
		}else{
			return new GenTable();
		}
	}
	@RequestMapping(value = "/index",method = RequestMethod.GET)
	public  @ResponseBody JSONObject index() {

		return JsonResponse.success("测试通过");

	}

	@RequestMapping(value = "/list",method = { RequestMethod.POST , RequestMethod.GET })
	public  @ResponseBody JSONObject list(GenTable genTable, HttpServletRequest request, HttpServletResponse response) {
        Page<GenTable> page = genTableService.find(new Page<GenTable>(request, response), genTable);
		return JsonResponse.success(page);

	}
	@RequestMapping(value = "/form",method = { RequestMethod.POST , RequestMethod.GET })
	public @ResponseBody
	JSONObject form(GenTable genTable) {
		// 获取物理表列表
		List<GenTable> tableList = genTableService.findTableListFormDb(new GenTable());
		GenTableDto dto =new GenTableDto();
		dto.setTableList(tableList);
		// 验证表是否存在
 		if (StringUtils.isBlank(genTable.getId()) && !genTableService.checkTableName(genTable.getName())){
			genTable.setName("");
			return JsonResponse.fail("下一步失败！" + genTable.getName() + " 表已经添加！");
		}
		// 获取物理表字段
		else{
			genTable = genTableService.getTableFormDb(genTable);
		}
		dto.setGenTable(genTable);
		dto.setConfig(GenUtils.getConfig());
		return JsonResponse.success(dto);
	}

//	@ResponseBody
//	@RequestMapping(value = "/save",method = { RequestMethod.POST , RequestMethod.GET })
//	public  JSONObject save(Model model) {
//		// 验证表是否已经存在
//		if (StringUtils.isBlank(genTable.getId()) && !genTableService.checkTableName(genTable.getName())){
//			genTable.setName("");
//			return JsonResponse.success( "保存失败！" + genTable.getName() + " 表已经存在！",genTable);
//		}
//		genTableService.save(genTable);
//		return JsonResponse.success("保存业务表'" + genTable.getName() + "'成功",genTable);
//	}

	@ResponseBody
	@RequestMapping(value = "/save",method = { RequestMethod.POST , RequestMethod.GET })
	public  JSONObject save(@ModelAttribute("genTable")GenTable genTable,Model model) {
		if (!beanValidator(model, genTable)){
			return form(genTable);
		}
		// 验证表是否已经存在
		if (StringUtils.isBlank(genTable.getId()) && !genTableService.checkTableName(genTable.getName())){
			genTable.setName("");
			return JsonResponse.success( "保存失败！" + genTable.getName() + " 表已经存在！");
		}
		genTableService.save(genTable);
		return JsonResponse.success("保存业务表'" + genTable.getName() + "'成功");
	}

	@RequestMapping(value = "/delete",method = { RequestMethod.POST , RequestMethod.GET })
	public @ResponseBody JSONObject delete(GenTable genTable, RedirectAttributes redirectAttributes) {
		genTableService.delete(genTable);

		return JsonResponse.success(addMessage(redirectAttributes, "删除业务表成功"),genTable);
	}

}
