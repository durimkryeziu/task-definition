package com.assignment.taskdefinition;

import static java.util.stream.Collectors.toUnmodifiableList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;

import java.time.Duration;
import java.util.List;
import java.util.stream.IntStream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class TaskDefinitionApplicationTests {

  private static final int initialSize = 3;

  private static final List<TaskDefinition> TASK_DEFINITIONS;
  private static final List<TaskDefinitionMirror> TASK_DEFINITION_MIRRORS;

  static {
    TASK_DEFINITIONS = IntStream.rangeClosed(1, initialSize)
        .mapToObj(i -> TaskDefinition.create("Task " + i, "Description of task " + i))
        .collect(toUnmodifiableList());

    TASK_DEFINITION_MIRRORS = IntStream.rangeClosed(1, initialSize)
        .mapToObj(i -> TaskDefinitionMirror.create("Task " + i, "Description of task " + i))
        .collect(toUnmodifiableList());
  }

  @Autowired
  private TaskDefinitionRepository taskDefinitionRepository;

  @Autowired
  private TaskDefinitionMirrorRepository taskDefinitionMirrorRepository;

  @BeforeEach
  void setUp() {
    taskDefinitionRepository.saveAll(TASK_DEFINITIONS);
    taskDefinitionMirrorRepository.saveAll(TASK_DEFINITION_MIRRORS);
  }

  @Test
  void shouldSyncTablesWhenRowIsAddedToTaskDefinition() {
    var taskDefinition = TaskDefinition.create("Task name", "It's description");

    taskDefinitionRepository.save(taskDefinition);

    await()
        .atMost(Duration.ofSeconds(15))
        .untilAsserted(
            () -> assertThat(taskDefinitionMirrorRepository.count())
                .isEqualTo(taskDefinitionRepository.count())
        );
  }

  @Test
  void shouldSyncTablesWhenRowIsAddedToTaskDefinitionMirror() {
    var anotherTask = TaskDefinitionMirror.create("Another Task", "It's description");
    taskDefinitionMirrorRepository.save(anotherTask);

    anotherTask = TaskDefinitionMirror.create("Another Task 2", "It's description 2");
    taskDefinitionMirrorRepository.save(anotherTask);

    anotherTask = TaskDefinitionMirror.create("Another Task 3", "It's description 3");
    taskDefinitionMirrorRepository.save(anotherTask);

    await()
        .atMost(Duration.ofSeconds(15))
        .untilAsserted(
            () -> assertThat(taskDefinitionRepository.count())
                .isEqualTo(taskDefinitionMirrorRepository.count())
        );
  }

  @Test
  void shouldSyncTablesWhenRowIsRemovedFromTaskDefinition() {
    var taskDefinition = taskDefinitionRepository.findAll().get(0);

    taskDefinitionRepository.deleteById(taskDefinition.getId());

    await()
        .atMost(Duration.ofSeconds(15))
        .untilAsserted(
            () -> assertThat(taskDefinitionMirrorRepository.count())
                .isEqualTo(taskDefinitionRepository.count())
        );
  }

  @Test
  void shouldSyncTablesWhenRowIsRemovedFromTaskDefinitionMirror() {
    var taskDefinitionMirror = taskDefinitionMirrorRepository.findAll().get(0);

    taskDefinitionMirrorRepository.deleteById(taskDefinitionMirror.getId());

    await()
        .atMost(Duration.ofSeconds(15))
        .untilAsserted(
            () -> assertThat(taskDefinitionRepository.count())
                .isEqualTo(taskDefinitionMirrorRepository.count())
        );
  }

  @Test
  void shouldSyncTablesWhenNameOfTaskIsUpdatedOnTaskDefinition() {
    var taskDefinition = taskDefinitionRepository.findAll().get(0);
    taskDefinition.setName(taskDefinition.getName() + " Updated");

    taskDefinitionRepository.save(taskDefinition);

    await()
        .atMost(Duration.ofSeconds(15))
        .untilAsserted(
            () -> assertThat(taskDefinitionMirrorRepository.findAll().get(0))
                .extracting(TaskDefinitionMirror::getName)
                .isEqualTo(taskDefinition.getName())
        );
  }

  @Test
  void shouldSyncTablesWhenNameOfTaskIsUpdatedOnTaskDefinitionMirror() {
    var taskDefinitionMirror = taskDefinitionMirrorRepository.findAll().get(0);
    taskDefinitionMirror.setName(taskDefinitionMirror.getName() + " Updated");

    taskDefinitionMirrorRepository.save(taskDefinitionMirror);

    await()
        .atMost(Duration.ofSeconds(15))
        .untilAsserted(
            () -> assertThat(taskDefinitionRepository.findAll().get(0))
                .extracting(TaskDefinition::getName)
                .isEqualTo(taskDefinitionMirror.getName())
        );
  }

  @Test
  void shouldSyncTablesWhenDescriptionOfTaskIsUpdatedOnTaskDefinition() {
    var taskDefinition = taskDefinitionRepository.findAll().get(1);
    taskDefinition.setDescription("Desc Updated");

    taskDefinitionRepository.save(taskDefinition);

    await()
        .atMost(Duration.ofSeconds(15))
        .untilAsserted(
            () -> assertThat(taskDefinitionMirrorRepository.findAll().get(1))
                .extracting(TaskDefinitionMirror::getDescription)
                .isEqualTo(taskDefinition.getDescription())
        );
  }

  @Test
  void shouldSyncTablesWhenDescriptionOfTaskIsUpdatedOnTaskDefinitionMirror() {
    var taskDefinitionMirror = taskDefinitionMirrorRepository.findAll().get(2);
    taskDefinitionMirror.setDescription("Desc Updated");

    taskDefinitionMirrorRepository.save(taskDefinitionMirror);

    await()
        .atMost(Duration.ofSeconds(15))
        .untilAsserted(
            () -> assertThat(taskDefinitionRepository.findAll().get(2))
                .extracting(TaskDefinition::getDescription)
                .isEqualTo(taskDefinitionMirror.getDescription())
        );
  }

  @Test
  void shouldSyncTablesWhenNameAndDescriptionOfTaskIsUpdatedOnTaskDefinition() {
    var taskDefinition = taskDefinitionRepository.findAll().get(0);
    taskDefinition.setName("Name Updated");
    taskDefinition.setDescription("Desc Updated");

    taskDefinitionRepository.save(taskDefinition);

    await()
        .atMost(Duration.ofSeconds(15))
        .untilAsserted(
            () -> assertThat(taskDefinitionMirrorRepository.findAll().get(0))
                .extracting(TaskDefinitionMirror::getName, TaskDefinitionMirror::getDescription)
                .containsExactly(taskDefinition.getName(), taskDefinition.getDescription())
        );
  }

  @Test
  void shouldSyncTablesWhenNameAndDescriptionOfTaskIsUpdatedOnTaskDefinitionMirror() {
    var taskDefinitionMirror = taskDefinitionMirrorRepository.findAll().get(1);
    taskDefinitionMirror.setName("Name Updated");
    taskDefinitionMirror.setDescription("Desc Updated");

    taskDefinitionMirrorRepository.save(taskDefinitionMirror);

    await()
        .atMost(Duration.ofSeconds(15))
        .untilAsserted(
            () -> assertThat(taskDefinitionRepository.findAll().get(1))
                .extracting(TaskDefinition::getName, TaskDefinition::getDescription)
                .containsExactly(taskDefinitionMirror.getName(), taskDefinitionMirror.getDescription())
        );
  }
}
