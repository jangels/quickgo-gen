/**
 * 
 */
package com.quickgo.platform.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;
import com.quickgo.platform.base.BaseController;
import com.quickgo.platform.dto.GenSchemeDto;
import com.quickgo.platform.model.GenScheme;
import com.quickgo.platform.service.GenSchemeService;
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
 *  生成方案Controller
 * @ClassName: GenSchemeController
 * @author hugengyong
 * @date 2016-10-27
 * Copyright: Copyright (c) 2016
 * Company:快狗俱乐部
 */
@Controller
@RequestMapping(value = "/gen/genScheme")
public class GenSchemeController extends BaseController {

	@Autowired
	private GenSchemeService genSchemeService;

	@Autowired
	private GenTableService genTableService;
	
	@ModelAttribute
	public GenScheme get(@RequestParam(required=false) String id) {
		if (StringUtils.isNotBlank(id)){
			return genSchemeService.get(id);
		}else{
			return new GenScheme();
		}
	}

	@RequestMapping(value = "/list",method = { RequestMethod.POST , RequestMethod.GET })
	public @ResponseBody
	JSONObject list(GenScheme genScheme, HttpServletRequest request, HttpServletResponse response,Model model) {
        Page<GenScheme> page = genSchemeService.find(new Page<GenScheme>(request, response), genScheme);
		return JsonResponse.success(page);
	}

	@ResponseBody
	@RequestMapping(value = "/form",method = { RequestMethod.POST , RequestMethod.GET })
	public  JSONObject form(GenScheme genScheme,Model model) {
		if (StringUtils.isBlank(genScheme.getPackageName())){
			genScheme.setPackageName("com.qitoon.modules");
		}
//		if (StringUtils.isBlank(genScheme.getFunctionAuthor())){
//			genScheme.setFunctionAuthor(UserUtils.getUser().getName());
//		}
		GenSchemeDto dto=new GenSchemeDto();
		dto.setGenScheme(genScheme);
		dto.setConfig( GenUtils.getConfig());
		dto.setTableList(genTableService.findAll());
		return JsonResponse.success(dto);
	}

	@RequestMapping(value = "/save",method = RequestMethod.POST)
	public  @ResponseBody JSONObject save(GenScheme genScheme, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, genScheme)){
			return form(genScheme,model);
		}
		String result = genSchemeService.save(genScheme);
		return JsonResponse.success(addMessage(redirectAttributes, "操作生成方案'" + genScheme.getName() + "'成功<br/>"+result));
	}


	@RequestMapping(value = "/delete",method = RequestMethod.POST)
	public JSONObject delete(GenScheme genScheme, RedirectAttributes redirectAttributes) {
		genSchemeService.delete(genScheme);
		return JsonResponse.success(addMessage(redirectAttributes, "删除生成方案成功"));
	}

}
