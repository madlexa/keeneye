package one.trifle.keeneye.controller

import groovy.transform.CompileStatic
import one.trifle.keeneye.model.dictionary.Scenario
import one.trifle.lurry.GQueryTemplate
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseBody

@Controller
@CompileStatic
class ScenarioController {

//    @Value("\${welcome.message:test}")
//    private String message = "Hello World"

    @Autowired
    private GQueryTemplate template

    @RequestMapping(value = "/", method = RequestMethod.GET)
    String index(Map<String, Object> model) {
//        model.message = this.message
        return "scenario"
    }

    @RequestMapping(value = "/", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    List<Scenario> get(@RequestParam(value = "id") Integer id) {
        [new Scenario(id: 1L), new Scenario(id: 2L)]
    }
}