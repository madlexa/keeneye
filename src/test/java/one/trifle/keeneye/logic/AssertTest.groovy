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

import one.trifle.keeneye.model.business.Request
import spock.lang.Specification
import spock.lang.Unroll

/**
 * @author Aleksey Dobrynin
 */
class AssertTest extends Specification {

    @Unroll
    "test assert result"() {
        expect:
        Assert tester = new Assert(new Request(id: id, code: code, params: params))
        tester.test() == result
        tester.fail().line == line

        where:
        id | code                                | params            || result || line
        1  | "assert test == 1"                  | [test: 1]         || true   || -1
        2  | "assert test == 2"                  | [test: 1]         || false  || 1
        3  | "assert test == 1\nassert b == 'b'" | [test: 1, b: "b"] || true   || -1
        4  | "assert test == 1\nassert b == 'c'" | [test: 1, b: "b"] || false  || 2
        5  | "assert test == 1"                  | [b: "b"]          || false  || 1
        6  | null                                | [b: "b"]          || true   || -1
        7  | "assert test == 1"                  | null              || false  || 1
    }

    @Unroll
    "test cache and remove"() {
        expect:
        Request request = new Request(id: id, code: code1, params: params)
        Assert tester1 = new Assert(request)
        boolean test1 = tester1.test()

        request.setCode(code2)
        Assert tester2 = new Assert(request)
        boolean test2 = tester2.test()

        tester2.removeScript()
        boolean test3 = tester2.test()

        test1 == result
        test2 == result
        test3 != result

        where:
        id | code1                               | code2                               | params            || result
        1  | "assert test == 1"                  | "assert test == 2"                  | [test: 1]         || true
        2  | "assert test == 2"                  | "assert test == 1"                  | [test: 1]         || false
        3  | "assert test == 1\nassert b == 'b'" | "assert test == 1\nassert b == 'c'" | [test: 1, b: "b"] || true
        4  | "assert test == 1\nassert b == 'c'" | "assert test == 1\nassert b == 'b'" | [test: 1, b: "b"] || false
        5  | "assert test == 1"                  | "assert b == 'b'"                   | [b: "b"]          || false
        7  | "assert test == 1"                  | "assert 1 == 1"                     | null              || false
    }
}