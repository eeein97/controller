package org.green.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.acls.model.AccessControlEntry;
import org.springframework.security.web.access.AccessDeniedHandler;

import lombok.extern.log4j.Log4j;
@Log4j
public class CustomAccessDeniedHandler implements AccessDeniedHandler{@Override
	public void handle(HttpServletRequest request, HttpServletResponse response,
			AccessDeniedException accessDeniedException) throws IOException, ServletException {
		log.error("접근거부 핸들러");
		log.error("redirect.........");
		//접근제한이 걸리는 경우 리다이렉트 하는 방식
		response.sendRedirect("/accessErorr");
		
	}
 
}
