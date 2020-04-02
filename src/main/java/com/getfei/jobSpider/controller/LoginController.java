package com.getfei.jobSpider.controller;

import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.getfei.jobSpider.entity.User;
import com.getfei.jobSpider.service.IUserService;
import com.getfei.jobSpider.util.ResponseResult;

@RestController
@RequestMapping("login")
public class LoginController extends BaseController {

	@Autowired
	private IUserService userService;

	@PostMapping
	public ResponseResult<String> login(@RequestParam("username") String username,
			@RequestParam("password") String password, HttpSession session) {
		Subject subject = SecurityUtils.getSubject();
		UsernamePasswordToken token = new UsernamePasswordToken(username,password);
        ResponseResult<String> rr=new ResponseResult<>();
        String message=null;
        try {
            //对用户进行认证登陆
            subject.login(token);
            token.setRememberMe(true);
            User user=userService.getByName(username);
            session.setAttribute("user", user);
            //通过subject获取以认证活动的user
//            ActiverUser activerUser = (ActiverUser) subject.getPrincipal();
            //将user存储到session中
//            WebUtils.getSession().setAttribute("user",activerUser.getUser());
            rr.setState(SUCCESS);
        } catch (UnknownAccountException e) {
        	rr.setState(ERROR);
        	message = "UnknownAccountException -- > 账号不存在：";
        } catch (IncorrectCredentialsException e){
        	rr.setState(ERROR);
        	message = "IncorrectCredentialsException -- > 密码不正确：";
        }catch (Exception e){
        	rr.setState(ERROR);
        	message = "登录失败， 遇到"+e+"错误，信息："+e.getMessage();
        } finally {
        	rr.setMessage(message);
		}
        return rr;
	}

}
