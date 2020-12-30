package com.assignment.taskdefinition;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class TableNameTest {

  @Test
  void shouldReturnTableNameFromToString() {
    var name = TableName.TASK_DEFINITIONS;

    var tableName = new TableName(name);

    assertThat(tableName).hasToString(name);
  }

  @Test
  void shouldReturnTheOtherTableNameFromTheOther() {
    var name = TableName.TASK_DEFINITIONS;

    var tableName = new TableName(name);

    assertThat(tableName.theOther()).isEqualTo(TableName.TASK_DEFINITIONS_MIRROR);
  }
}
