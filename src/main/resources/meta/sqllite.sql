CREATE TABLE api_method (
  id      INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
  code    VARCHAR(400)                      NOT NULL,
  factory VARCHAR(400)
);

CREATE TABLE api_method_params (
  id          INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
  apiMethodId INTEGER                           NOT NULL,
  key         VARCHAR(400)                      NOT NULL,
  value       CLOB
);
CREATE INDEX idx_api_method_params
  ON api_method_params (apiMethodId);

CREATE TABLE project (
  id         INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
  name       VARCHAR(100)                      NOT NULL,
  createDate DATETIME                          NOT NULL,
  active     BOOLEAN DEFAULT TRUE
);

CREATE TABLE scenario (
  id         INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
  projectId  INTEGER                           NOT NULL,
  name       VARCHAR(100)                      NOT NULL,
  createDate DATETIME                          NOT NULL,
  active     BOOLEAN DEFAULT TRUE,
  type       VARCHAR(16)                       NOT NULL
);
CREATE INDEX idx_scenario
  ON scenario (projectId, active);

CREATE TABLE scenario_request (
  id          INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
  scenarioId  INTEGER                           NOT NULL,
  apiMethodId INTEGER                           NOT NULL,
  "order"     INTEGER                           NOT NULL,
  name        VARCHAR(100)                      NOT NULL,
  request     CLOB                              NOT NULL,
  asserts     CLOB,
  createDate  DATETIME                          NOT NULL,
  active      BOOLEAN DEFAULT TRUE
);
CREATE INDEX idx_scenario
  ON scenario (projectId, active);

CREATE TABLE executed_scenario (
  id         INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
  createDate DATETIME                          NOT NULL,
  scenarioId INTEGER                           NOT NULL,
  successful BOOLEAN                           NOT NULL,
  time       BIGINT
);

CREATE TABLE scenario_report (
  id         INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
  scenarioId INTEGER                           NOT NULL,
  data       CLOB                              NOT NULL,
  report     CLOB                              NOT NULL,
  createDate DATETIME                          NOT NULL
);

CREATE TABLE executed_report (
  id         INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
  scenarioId INTEGER                           NOT NULL,
  result     CLOB,
  error      CLOB,
  createDate DATETIME                          NOT NULL,
  time       BIGINT                            NOT NULL
);

CREATE TABLE executed_request (
  id                 INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
  executedScenarioId INTEGER                           NOT NULL,
  scenarioRequestId  INTEGER                           NOT NULL,
  request            CLOB                              NOT NULL,
  response           CLOB,
  error              CLOB,
  createDate         DATETIME                          NOT NULL,
  time               BIGINT                            NOT NULL
);
