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
import one.trifle.keeneye.exception.RestException
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody

@Controller
@CompileStatic
class ExceptionHandlerController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionHandlerController);

    @ExceptionHandler(RestException)
    @ResponseBody
    String handleException(RestException e) {
        LOGGER.error("Ошибка: {}", e.getMessage(), e);
        return "Ошибка: " + e.getMessage();
    }
}
