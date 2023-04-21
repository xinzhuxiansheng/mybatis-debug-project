package com.javamain.mybatis.web;

import com.google.gson.Gson;
import com.javamain.mybatis.common.util.MybatisUtils;
import com.javamain.mybatis.dao.UserMapper;
import com.javamain.mybatis.pojo.User;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;

public class UserServlet extends HttpServlet {
    private Gson gson = new Gson();

    /**
     * curl -X POST -d '{"name":"张三", "age":"30"}'  http://localhost:8080/user/add
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();
        if (pathInfo != null) {
            if (pathInfo.equals("/add")) {
                addUser(req, resp);
            }
        } else {
            // Handle default case or send an error
        }
    }

    private void addUser(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(req.getInputStream()));
        User user = gson.fromJson(reader, User.class);

        SqlSessionFactory sqlSessionFactory = MybatisUtils.sqlSessionFactory;
        // 2. 从 SqlSessionFactory 中获取 SqlSession
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            // 3. 获取 Mapper 接口的实例
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);

            // 4. 调用 Mapper 接口的方法执行 SQL 操作
            userMapper.addUser(user);

            // 5. 处理查询结果
            if (user != null) {
                System.out.println("User ID: " + user.getId());
                System.out.println("User Name: " + user.getName());
                System.out.println("User Age: " + user.getAge());
            } else {
                System.out.println("User not found.");
            }
        }


        resp.setContentType("application/json");
        resp.setStatus(HttpServletResponse.SC_OK);
        gson.toJson(user, resp.getWriter());
    }



    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userIdStr = req.getParameter("userId");
        if (StringUtils.isNotBlank(userIdStr)) {
            getUserById(req, resp);
        } else {
            getUserByOther(req, resp);
        }
    }

    /**
     * curl "http://localhost:8080/user?userId=1"
     */
    private void getUserById(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        User user = null;

        SqlSessionFactory sqlSessionFactory = MybatisUtils.sqlSessionFactory;
        // 2. 从 SqlSessionFactory 中获取 SqlSession
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            // 3. 获取 Mapper 接口的实例
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);

            // 4. 调用 Mapper 接口的方法执行 SQL 操作
            int userId = Integer.parseInt(req.getParameter("userId"));
            user = userMapper.getUserById(userId);

            // 5. 处理查询结果
            if (user != null) {
                System.out.println("User ID: " + user.getId());
                System.out.println("User Name: " + user.getName());
                System.out.println("User Age: " + user.getAge());
            } else {
                System.out.println("User not found.");
            }
        }

        resp.setContentType("application/json");
        resp.setStatus(HttpServletResponse.SC_OK);
        gson.toJson(user, resp.getWriter());
    }

    /**
     * curl "http://localhost:8080/user?name=yzhou&age=30"
     */
    private void getUserByOther(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        User user = null;

        SqlSessionFactory sqlSessionFactory = MybatisUtils.sqlSessionFactory;
        // 2. 从 SqlSessionFactory 中获取 SqlSession
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            // 3. 获取 Mapper 接口的实例
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);

            // 4. 调用 Mapper 接口的方法执行 SQL 操作
            // int userId = Integer.parseInt(req.getParameter("userId"));
            String name = req.getParameter("name");
            Integer age = Integer.parseInt(req.getParameter("age"));
            user = userMapper.getUserByOther(name, age);

            // 5. 处理查询结果
            if (user != null) {
                System.out.println("User ID: " + user.getId());
                System.out.println("User Name: " + user.getName());
                System.out.println("User Age: " + user.getAge());
            } else {
                System.out.println("User not found.");
            }
        }

        resp.setContentType("application/json");
        resp.setStatus(HttpServletResponse.SC_OK);
        gson.toJson(user, resp.getWriter());
    }
}