package com.getfei.jobSpider.controller;

import java.util.List;

import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.getfei.jobSpider.entity.User;
import com.getfei.jobSpider.service.IUserService;
import com.getfei.jobSpider.util.ResponseResult;

import cn.hutool.core.util.IdUtil;

@RestController
@RequestMapping("users")
public class UserController extends BaseController {

	@Autowired
	private IUserService userService;

	@GetMapping
	public ResponseResult<List<User>> list() {
		List<User> users = userService.list();
		return new ResponseResult<List<User>>(SUCCESS, users);
	}

	@PostMapping
	public ResponseResult<String> add(@RequestParam(name = "username") String username,
			@RequestParam(name = "password") String password) {
		User user = new User();
		user.setName(username);
		String salt = IdUtil.simpleUUID().toUpperCase();
        user.setSalt(salt);
        //设置密码
        user.setPassword(new Md5Hash(password,salt,2).toString());
		userService.save(user);
		return new ResponseResult<>(SUCCESS);
	}
	
}
