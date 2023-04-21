//package com.yzhou.mybatis.jdbc;
//
//import org.junit.jupiter.api.Test;
//
//import java.sql.*;
//
//public class JDBCTest {
//
//    @Test
//    public void getGeneratedKeysTest(){
//        try {
//            Class.forName("com.mysql.cj.jdbc.Driver");
//            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/yzhou_test", "root", "12345678");
//
//            String sql = "INSERT INTO users (name, age) VALUES (?, ?)";
//            PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
//            pstmt.setString(1, "JDBCTest");
//            pstmt.setString(2, "30");
//            pstmt.executeUpdate();
//
//            ResultSet generatedKeys = pstmt.getGeneratedKeys();
//            if (generatedKeys.next()) {
//                long id = generatedKeys.getLong(1);
//                System.out.println("Generated key: " + id);
//            }
//            pstmt.close();
//            conn.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//}
