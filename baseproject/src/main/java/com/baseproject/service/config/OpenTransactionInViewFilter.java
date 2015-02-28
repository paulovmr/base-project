package com.baseproject.service.config;

import java.io.IOException;

import javax.persistence.EntityTransaction;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletResponse;

import com.baseproject.model.config.MyEntityManager;

@WebFilter(urlPatterns = { "/api/*" })
public class OpenTransactionInViewFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        EntityTransaction transaction = null;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        try {
            transaction = MyEntityManager.get().getTransaction();
            transaction.begin();

            chain.doFilter(request, response);
            transaction.commit();

        } catch (Exception e) {
            e.printStackTrace();

            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }

            httpResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("Sorry, there was an error processing your request.");
        } finally {
            MyEntityManager.free();
        }
    }

    @Override
    public void destroy() {
    }
}