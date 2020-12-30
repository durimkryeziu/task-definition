package com.assignment.taskdefinition;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

@Data
@Entity
@Table(name = "task_definitions_mirror")
public class TaskDefinitionMirror {

  @Id
  @GeneratedValue(generator = "UUID")
  @GenericGenerator(
      name = "UUID",
      strategy = "org.hibernate.id.UUIDGenerator"
  )
  @Column(name = "id", nullable = false, updatable = false, length = 36)
  private String id;

  @Column(name = "name", nullable = false)
  private String name;

  @Column(name = "description")
  private String description;

  public static TaskDefinitionMirror create(String name, String description) {
    var taskDefinition = new TaskDefinitionMirror();
    taskDefinition.setName(name);
    taskDefinition.setDescription(description);
    return taskDefinition;
  }
}
