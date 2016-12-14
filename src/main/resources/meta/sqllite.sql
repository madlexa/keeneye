-------------------
-- method for execute request
-------------------

CREATE TABLE api_method (
  id      INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
  code    VARCHAR(400)                      NOT NULL,
  factory VARCHAR(400)
);

-------------------
-- additional params for init execute method
-------------------

CREATE TABLE api_method_params (
  id          INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
  apiMethodId INTEGER                           NOT NULL,
  key         VARCHAR(400)                      NOT NULL,
  value       CLOB
);
CREATE INDEX idx_api_method_params
  ON api_method_params (apiMethodId);

-------------------
-- project name for grouping scenarios
-------------------

CREATE TABLE project (
  id         INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
  name       VARCHAR(100)                      NOT NULL,
  createDate DATETIME                          NOT NULL,
  active     BOOLEAN DEFAULT TRUE
);

-------------------
-- scenario for grouping request test steps
-------------------

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

-------------------
-- scenario step
-- one by one execute api method with request template and assert result
-------------------

CREATE TABLE scenario_request (
  id          INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
  scenarioId  INTEGER                           NOT NULL,
  apiMethodId INTEGER                           NOT NULL,
  "order"     INTEGER                           NOT NULL,
  name        VARCHAR(255)                      NOT NULL,
  request     CLOB                              NOT NULL,
  asserts     CLOB,
  createDate  DATETIME                          NOT NULL,
  active      BOOLEAN DEFAULT TRUE
);
CREATE INDEX idx_scenario_request
  ON scenario_request (scenarioId, "order");

-------------------
-- additional params for init api method
-- maybe url
-------------------

CREATE TABLE scenario_report_params (
  id                INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
  scenarioRequestId INTEGER                           NOT NULL,
  key               VARCHAR(400)                      NOT NULL,
  value             CLOB
);
CREATE INDEX idx_scenario_report_params
  ON scenario_report_params (scenarioRequestId);

-------------------
-- group by executed log
-------------------

CREATE TABLE executed_scenario (
  id         INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
  createDate DATETIME                          NOT NULL,
  scenarioId INTEGER                           NOT NULL,
  status     VARCHAR(16)                       NOT NULL,
  result     CLOB,
  time       BIGINT
);
CREATE INDEX idx_executed_scenario_id
  ON executed_scenario (scenarioId, createDate);

-------------------
-- execute result
-------------------

CREATE TABLE executed_request (
  id                 INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
  executedScenarioId INTEGER                           NOT NULL,
  name               VARCHAR(255)                      NOT NULL,
  "order"            INTEGER                           NOT NULL,
  request            CLOB                              NOT NULL,
  response           CLOB,
  status             VARCHAR(16)                       NOT NULL,
  error              CLOB,
  createDate         DATETIME                          NOT NULL,
  time               BIGINT                            NOT NULL
);
CREATE INDEX idx_executed_request_id
  ON executed_request (executedScenarioId, "order");

-------------------
-- hash tags
-- words for quick search scenario
-------------------

CREATE TABLE hash_tags (
  id   INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
  name VARCHAR(255)                      NOT NULL
);
CREATE UNIQUE INDEX idx_hash_tags
  ON hash_tags (hashTag);

-------------------
-- link between scenarios and hash tags
-------------------

CREATE TABLE scenario_tags (
  id         INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
  scenarioId INTEGER                           NOT NULL,
  hashTagId  INTEGER                           NOT NULL
);
CREATE UNIQUE INDEX idx_scenario_tags
  ON scenario_tags (hashTagId, scenarioId);
