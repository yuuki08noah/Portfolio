package com.authserver.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import org.junit.jupiter.api.Test;

public class PostgreSQLConnectionTest {
  private String URL = "jdbc:postgresql://postgres-user:5432/user";
  private String USERNAME = "postgres"; //postgresql 계정
  private String PASSWORD = "yuuki08noah"; //비밀번호

  @Test
  public void ConnectionTest() throws Exception{
    Connection con = DriverManager.getConnection(URL,USERNAME,PASSWORD); //db 연결
    System.out.println(con); //연결 정보 출력
    Statement pre = con.createStatement();

  }
}