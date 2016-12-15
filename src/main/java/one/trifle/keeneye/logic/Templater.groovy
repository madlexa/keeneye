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

import groovy.text.GStringTemplateEngine
import groovy.text.Template
import groovy.transform.CompileStatic

import java.util.concurrent.ConcurrentHashMap

/**
 * @author Aleksey Dobrynin
 */
@CompileStatic
class Templater {
    // TODO Ehcache::setMemoryStoreEvictionPolicy LRU
    // http://www.ehcache.org/documentation/2.8/apis/cache-eviction-algorithms.html
    private static Map<Long, Template> cache = new ConcurrentHashMap<>()

    private Request request

    Templater(Request request) {
        this.request = request
    }

    String result() {
        Map<String, Object> vals = new HashMap<String, Object>() {
            @Override
            boolean containsKey(Object key) { true }
        }
        vals.putAll(request.params ?: [:])
        return get().make(vals).toString()
    }

    /**
     * get cached template
     *
     * @param query
     * @return GStringTemplate
     */
    private Template get() {
        Template template = cache[request.id]
        if (!template) {
            template = cache[request.id] = new GStringTemplateEngine().createTemplate(request.template ?: "")
        }
        template
    }

    /**
     * remove cache value
     *
     * @return if not found cache then return false else return true
     */
    boolean removeTemplate() {
        cache.remove(request.id) != null
    }
}
