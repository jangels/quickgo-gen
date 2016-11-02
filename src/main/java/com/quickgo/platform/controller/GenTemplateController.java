/**
 * 
 */
package com.quickgo.platform.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;
import com.quickgo.platform.base.BaseController;
import com.quickgo.platform.model.GenTemplate;
import com.quickgo.platform.service.GenTemplateService;
import com.quickgo.platform.utils.JsonResponse;
import com.quickgo.platform.utils.Page;
import com.quickgo.platform.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *  生成模板Controller
 * @ClassName: GenTemplateController
 * @author hugengyong
 * @date 2016-10-27
 * Copyright: Copyright (c) 2016
 * Company:快狗俱乐部
 */
@Controller
@RequestMapping(value = "/gen/genTemplate")
public class GenTemplateController extends BaseController {

	@Autowired
	private GenTemplateService genTemplateService;
	
	@ModelAttribute
	public GenTemplate get(@RequestParam(required=false) String id) {
		if (StringUtils.isNotBlank(id)){
			return genTemplateService.get(id);
		}else{
			return new GenTemplate();
		}
	}

	@RequestMapping(value = "/list",method = { RequestMethod.POST , RequestMethod.GET })
	public @ResponseBody JSONObject list(GenTemplate genTemplate, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<GenTemplate> page = genTemplateService.find(new Page<GenTemplate>(request, response), genTemplate);
		return JsonResponse.success(page);
	}

	@RequestMapping(value = "/form",method = { RequestMethod.POST , RequestMethod.GET })
	public @ResponseBody
	JSONObject form(GenTemplate genTemplate, Model model) {
		return JsonResponse.success(genTemplate);
	}

	@RequestMapping(value = "/save",method = { RequestMethod.POST , RequestMethod.GET })
	public  @ResponseBody JSONObject save(GenTemplate genTemplate, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, genTemplate)){
			return form(genTemplate, model);
		}
		genTemplateService.save(genTemplate);
		return JsonResponse.success(addMessage(redirectAttributes, "保存代码模板'" + genTemplate.getName() + "'成功"));
	}


	@RequestMapping(value = "/delete",method = { RequestMethod.POST , RequestMethod.GET })
	public @ResponseBody JSONObject delete(GenTemplate genTemplate, RedirectAttributes redirectAttributes) {
		genTemplateService.delete(genTemplate);
		addMessage(redirectAttributes, "");
		return JsonResponse.success(addMessage(redirectAttributes, "删除代码模板成功"));
	}

}
