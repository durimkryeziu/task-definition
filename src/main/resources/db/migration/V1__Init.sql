CREATE TABLE TASK_DEFINITIONS
(
    ID          VARCHAR(36)  NOT NUll PRIMARY KEY,
    NAME        VARCHAR(255) NOT NULL,
    DESCRIPTION VARCHAR(255)
);

CREATE TABLE TASK_DEFINITIONS_MIRROR
(
    ID          VARCHAR(36)  NOT NUll PRIMARY KEY,
    NAME        VARCHAR(255) NOT NULL,
    DESCRIPTION VARCHAR(255)
);
