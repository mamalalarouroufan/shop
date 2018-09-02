package com.shop.web.controller.dict;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shop.api.base.controller.BaseController;
import com.shop.api.base.entity.WebEntity;
import com.shop.api.entity.dict.DictVo;
import com.shop.api.entity.dict.service.DictService;
import com.shop.api.exception.EnumError;
import com.shop.api.utils.constant.ConstantKey;

/**
 * 
 * @ClassName:  UserController   
 * @Description:(用户Controller)   
 * @author: 王杰
 * @date:   2018年7月25日 下午2:02:49   
 */
@Controller
public class DictController extends BaseController {

	@Resource
	private DictService dictService;

	/**
	 * 
	 * @Title: getPhoneArea   
	 * @Description: (获取手机号区域)   
	 * @return      
	 * @return WebEntity<List<DictVo>>  
	 * @author 王杰
	 * @date 2018年7月31日 下午10:58:25   
	 * @throws
	 */
	@ResponseBody
	@RequestMapping(value = "/dict/getPhoneArea", produces = { "application/json" })
	public WebEntity<List<DictVo>> getPhoneArea() {
		return new WebEntity<List<DictVo>>(EnumError.SUCCESS,
				dictService.getDictVoList(ConstantKey.DICT_PHONE_AREA_KEY));
	}

	/**
	 * 
	 * @Title: getPhoneArea   
	 * @Description: (获取证件类型)   
	 * @return      
	 * @return WebEntity<List<DictVo>>  
	 * @author 王杰
	 * @date 2018年7月31日 下午10:58:25   
	 * @throws
	 */
	@ResponseBody
	@RequestMapping(value = "/dict/getCertType", produces = { "application/json" })
	public WebEntity<List<DictVo>> getCertType() {
		return new WebEntity<List<DictVo>>(EnumError.SUCCESS,
				dictService.getDictVoList(ConstantKey.DICT_CERT_TYPE_KEY));
	}

	/**
	 * 
	 * @Title: getPhoneArea   
	 * @Description: (获取教育程度)   
	 * @return      
	 * @return WebEntity<List<DictVo>>  
	 * @author 王杰
	 * @date 2018年7月31日 下午10:58:25   
	 * @throws
	 */
	@ResponseBody
	@RequestMapping(value = "/dict/getEducationLevel", produces = { "application/json" })
	public WebEntity<List<DictVo>> getEducationLevel() {
		return new WebEntity<List<DictVo>>(EnumError.SUCCESS,
				dictService.getDictVoList(ConstantKey.DICT_EDUCATION_LEVEL_KEY));
	}

}
