package one.trifle.keeneye.model.particle

import one.trifle.keeneye.model.dictionary.Scenario

import java.time.LocalDateTime

/**
 * @author Aleksey Dobrynin
 */
class ExecScenario {
    Long id
    Scenario scenario
    LocalDateTime time
    Boolean success
}
