package com.assignment.taskdefinition;

import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.namedparam.EmptySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class SyncService {

  private final NamedParameterJdbcTemplate jdbcTemplate;

  @Transactional
  public void sync() {
    var tableName = new TableName(lastUpdatedTable());
    var from = tableName.toString();
    var to = tableName.theOther();
    log.info("Syncing tables: {} & {}", from, to);
    insert(from, to);
    delete(from, to);
    update(from, to);
  }

  private String lastUpdatedTable() {
    final var sql = """
        SELECT TABLE_NAME
        FROM INFORMATION_SCHEMA.TABLES
        WHERE TABLE_NAME IN (:table_names)
        ORDER BY LAST_MODIFICATION DESC
        LIMIT 1;""";
    var tableNames = List.of(TableName.TASK_DEFINITIONS, TableName.TASK_DEFINITIONS_MIRROR);
    return jdbcTemplate.queryForObject(sql, Map.of("table_names", tableNames), String.class);
  }

  private void insert(String from, String to) {
    final var sql = """
        INSERT INTO $to (ID, NAME, DESCRIPTION)
        SELECT ID, NAME, DESCRIPTION
        FROM $from
        WHERE ID NOT IN (SELECT ID FROM $to);""".replace("$from", from).replace("$to", to);
    execute(sql);
  }

  private void delete(String from, String to) {
    final var sql = """
        DELETE
        FROM $to
        WHERE ID NOT IN (SELECT ID FROM $from);""".replace("$from", from).replace("$to", to);
    execute(sql);
  }

  private void update(String from, String to) {
    final var sql = """
        UPDATE $to
        SET $to.NAME        = (SELECT NAME FROM $from WHERE ID = $to.ID),
            $to.DESCRIPTION = (SELECT DESCRIPTION FROM $from WHERE ID = $to.ID);"""
        .replace("$from", from).replace("$to", to);
    execute(sql);
  }

  private void execute(String sql) {
    jdbcTemplate.update(sql, EmptySqlParameterSource.INSTANCE);
  }
}
