package com.getfei.jobSpider.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.getfei.jobSpider.entity.Technology;
import com.getfei.jobSpider.service.ITechnologyService;
import com.getfei.jobSpider.util.MongoResult;
import com.getfei.jobSpider.util.ResponseResult;

@RestController
@RequestMapping("technologies")
public class TechnologyController extends BaseController {

	@Autowired
	private ITechnologyService technologyService;

	/** 获取满足条件的技术 */
	@GetMapping()
	public ResponseResult<List<Technology>> get(@RequestParam(value = "type", required = false) String type,
			@RequestParam(value = "name", required = false) String name,
			@RequestParam(value = "alias", required = false) String alias) {
		logger.info("type=" + type + ",name=" + name + ",alias=" + alias);
		List<Technology> technologies;
		technologies = type == null && name == null && alias == null ? technologyService.list()
				: technologyService.getByTypeAndNameLikeAndAliasLike(type, name, alias);
		ResponseResult<List<Technology>> rs = new ResponseResult<>(SUCCESS, technologies);
		rs.setDataCount(technologies.size());
		return rs;
	}

	/** 新增技术 */
	@PostMapping("/")
	public ResponseResult<List<Technology>> add(@RequestParam("name") String name, @RequestParam("type") String type,
			@RequestParam(name = "alias", required = false) String aliasStr) {
		String[] aliases = !"".equals(aliasStr) ? aliasStr.split(",") : null;
		logger.info("preAdd:name=" + name + ",type=" + type + ",aliasStr=" + aliasStr + ",aliases="
				+ Arrays.toString(aliases));
		MongoResult mongoResult = technologyService.add(name, type, aliases);
		Integer state = mongoResult.isSuccess() ? SUCCESS : ERROR;
		ResponseResult<List<Technology>> rs = new ResponseResult<>(state);
		if (state == ERROR) {
			rs.setMessage(mongoResult.getMessage());
		}
		return rs;
	}

	/** 获取所有技术类型 */
	@GetMapping("/types")
	public ResponseResult<List<String>> listType() {
		List<String> types = technologyService.ListType();
		ResponseResult<List<String>> rs = new ResponseResult<>(SUCCESS, types);
		return rs;
	}
	
	/** 获取所有技术类型 */
	@GetMapping("/bytype")
	public ResponseResult<List<Technology>> getByType(@RequestParam("type") String type) {
		List<Technology> r = technologyService.getByType(type);
		ResponseResult<List<Technology>> rs = new ResponseResult<>(SUCCESS, r);
		return rs;
	}
	
	/** 新增别名 */
	@PostMapping("/alias")
	public ResponseResult<List<Technology>> addAlias(@RequestParam("name") String name, @RequestParam("type") String type,
			@RequestParam(name = "alias") String alias) {
		MongoResult mongoResult = technologyService.addAlias(name, type, alias);
		Integer state = mongoResult.isSuccess() ? SUCCESS : ERROR;
		ResponseResult<List<Technology>> rs = new ResponseResult<>(state);
		if (state == ERROR) {
			rs.setMessage(mongoResult.getMessage());
		}
		return rs;
	}

}
