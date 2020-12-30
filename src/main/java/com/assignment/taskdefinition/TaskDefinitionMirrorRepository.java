package com.assignment.taskdefinition;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskDefinitionMirrorRepository extends JpaRepository<TaskDefinitionMirror, String> {

}
