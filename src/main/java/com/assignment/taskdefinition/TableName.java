package com.assignment.taskdefinition;

public final class TableName {

  public static final String TASK_DEFINITIONS = "TASK_DEFINITIONS";
  public static final String TASK_DEFINITIONS_MIRROR = "TASK_DEFINITIONS_MIRROR";

  private final String name;

  public TableName(String name) {
    this.name = name;
  }

  public String theOther() {
    return TASK_DEFINITIONS.equals(this.name) ? TASK_DEFINITIONS_MIRROR : TASK_DEFINITIONS;
  }

  @Override
  public String toString() {
    return this.name;
  }
}
