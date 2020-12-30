package com.assignment.taskdefinition;

import java.sql.SQLException;
import org.h2.tools.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class TaskDefinitionApplication {

  public static void main(String[] args) throws SQLException {
    Server.createTcpServer("-ifNotExists").start();
    SpringApplication.run(TaskDefinitionApplication.class, args);
  }
}
