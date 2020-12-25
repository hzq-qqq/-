package cn.itcast.test;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

public class test {
    public static void main(String[] args) {
        HttpServletRequest request = null;

        assert request != null;
        ServletContext servletContext = request.getSession().getServletContext();
        System.out.println(servletContext);
    }
}
