package com.assignment.taskdefinition;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ScheduledTasks {

  private final SyncService syncService;

  @Scheduled(cron = "0/5 * * * * *")
  public void syncTables() {
    syncService.sync();
  }
}
