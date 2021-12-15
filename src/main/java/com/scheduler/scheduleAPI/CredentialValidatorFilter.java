//package com.scheduler.scheduleAPI;
//
//
//import javax.servlet.*;
//import javax.servlet.http.HttpServletRequest;
//import java.io.IOException;
//import java.util.Base64;
//
//public class CredentialValidatorFilter implements Filter {
//    @Override
//    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
//        HttpServletRequest request = (HttpServletRequest) servletRequest;
//        System.out.println("In filter");
//        String authHeader = request.getHeader("Authorization");
//        String encodedUsernamePassword = authHeader.substring("Basic ".length()).trim();
//
//        byte[] decodedBytes = Base64.getDecoder().decode(authHeader);
//        String decodedString = new String(decodedBytes);
//
//        int separatorIndex = decodedString.indexOf(':');
//
//        String username = decodedString.substring(0, separatorIndex);
//        String password = decodedString.substring(separatorIndex + 1);
//        System.out.println(decodedString);
//        servletRequest.setAttribute(username, username);
//        servletRequest.setAttribute(password, password);
//
//        filterChain.doFilter(servletRequest, servletResponse);
//    }
//}
