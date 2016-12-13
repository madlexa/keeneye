/*
 * Copyright 2016 Aleksey Dobrynin
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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