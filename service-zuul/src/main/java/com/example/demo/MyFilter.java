package com.example.demo;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;

import com.netflix.zuul.context.RequestContext;

@Component
public class MyFilter /* implements ZuulFilter */ {
//    @Override
    public String filterType() {
        return "pre";
    }

//    @Override
    public int filterOrder() {
        return 0;
    }

//    @Override
    public boolean shouldFilter() {
        return true;
    }

//    @Override
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        Object accessToken = request.getParameter("token");
        if (accessToken == null) {
            ctx.setSendZuulResponse(false);
            ctx.setResponseStatusCode(401);
            try {
                ctx.getResponse().getWriter().write("token is empty");
            } catch (Exception e) {}

            return null;
        }
        return null;
    }
}
