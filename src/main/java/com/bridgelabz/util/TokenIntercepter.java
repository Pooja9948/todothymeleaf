package com.bridgelabz.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.bridgelabz.util.token.VerifyToken;

/**
 * @author Pooja todoApp
 *
 */
public class TokenIntercepter implements HandlerInterceptor {

	@Override
	public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {

	}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3)
			throws Exception {

	}
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object arg2) throws Exception {

		
		HttpSession session = request.getSession(false);
		System.out.println("token in interceptor "+session.getAttribute("token"));
		session.getAttribute("token");
		
		int userId = VerifyToken.verifyAccessToken(session.getAttribute("token")+"");
		if (userId == 0) {
			response.setStatus(511);
			return false;
		}
		System.out.println("jhdewdjew wdjhwe wuyew--------> "+userId);
		request.setAttribute("userId", userId);
		return true;
	}

}
