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
package one.trifle.keeneye.logic

import spock.lang.Specification
import spock.lang.Unroll

/**
 * @author Aleksey Dobrynin
 */
class TemplaterTest extends Specification {

    @Unroll
    "Templater(#template, #params) == #result"() {
        expect:
        new Templater(new Request(id: id, template: template, params: params)).result() == result

        where:
        id | template                                   | params                          || result
        1  | 'bla ${test} bla'                          | [test: "1"]                     || "bla 1 bla"
        2  | 'bla $test bla'                            | [test: 1, t1: 0]                || "bla 1 bla"
        3  | 'bla ${test == "1" ? res[0] : res[1]} bla' | [test: "1", res: ["yes", "no"]] || "bla yes bla"
        4  | 'bla ${test == 1 ? "yes" : "no"} bla'      | [test: 2]                       || "bla no bla"
        5  | 'bla ${t1} ${t2} bla'                      | [t1: 1, t2: 2]                  || "bla 1 2 bla"
        6  | 'bla <% out << t1 %> ${t2} bla'            | [t1: 1, t2: 2]                  || "bla 1 2 bla"
        7  | '${String.valueOf(" ").hashCode()}'        | [:]                             || "32"
        8  | 'bla ${test} bla'                          | null                            || "bla null bla"
        9  | null                                       | [test: "test"]                  || ""
    }

    @Unroll
    "test cache and remove [#id] '#template1' '#template2'"() {
        expect:
        Request request = new Request(id: id, template: template1, params: params)
        Templater templater1 = new Templater(request)
        String result1 = templater1.result()

        request.setTemplate(template2)
        Templater templater2 = new Templater(request)
        String result2 = templater1.result()

        templater2.removeTemplate()
        String result3 = templater2.result()

        result1 == result
        result2 == result
        result3 == anyResult

        where:
        id | template1          | template2            | params          || result         || anyResult
        -1 | 'bla ${test} bla'  | 'test ${test} test'  | [test: 1]       || 'bla 1 bla'    || 'test 1 test'
        -2 | 'bla ${test} bla'  | 'test ${test} test'  | [:]             || 'bla null bla' || 'test null test'
        -3 | 'bla ${test1} bla' | 'test ${test2} test' | [test1: "test"] || 'bla test bla' || 'test null test'
        -4 | 'bla ${test1} bla' | 'test ${test2} test' | [test2: "test"] || 'bla null bla' || 'test test test'
    }
}
