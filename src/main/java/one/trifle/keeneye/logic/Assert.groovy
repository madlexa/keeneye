package one.trifle.keeneye.logic

import groovy.transform.CompileStatic

import java.util.concurrent.ConcurrentHashMap

@CompileStatic
class Assert {
    private static String ERROR_SCRIPT_PREFIX = "Script1"

    // TODO Ehcache::setMemoryStoreEvictionPolicy LRU
    // http://www.ehcache.org/documentation/2.8/apis/cache-eviction-algorithms.html
    private static Map<Long, Script> cache = new ConcurrentHashMap<>()

    private Request request
    private Throwable exception

    Assert(Request request) {
        this.request = request
    }

    /**
     * Test request by code and params
     *
     * @return successful
     */
    boolean test() {
        Script script = getScript()
        ((Map<String, Object>) (request.params ?: [:])).each { script.setProperty(it.key, it.value) }

        try {
            script.run()
            return true
        } catch (Throwable exc) {
            exception = exc
            return false
        }
    }

    /**
     * get cached Script by Request id or created new cache
     *
     * @return cached Script
     */
    Script getScript() {
        Script script = cache[request.id]
        if (!script) {
            script = cache[request.id] = new GroovyShell().parse(request?.code ?: "")
        }
        return script
    }

    /**
     * remove cache value
     *
     * @return if not found cache then return false else return true
     */
    boolean removeScript() {
        cache.remove(request.id) != null
    }

    /**
     * execut result
     *
     * @return Fail.line == -1 if no problem OR fail with exception message and line error
     */
    Fail fail() {
        return exception != null ? new Fail(
                message: exception.getMessage(),
                line: exception.getStackTrace().find {
                    it.getClassName().startsWith(ERROR_SCRIPT_PREFIX)
                }?.getLineNumber()
        ) : new Fail()
    }

    /**
     * executed result with concrete error
     */
    static class Fail {
        String message = ""
        int line = -1
    }
}
