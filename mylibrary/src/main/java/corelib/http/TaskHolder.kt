package corelib.http

/**
 *
 * Created by yamamoto on 2018/02/14.
 */

class TaskHolder {
    var tasks = mutableMapOf<String, HttpTask<*>>()

    fun add(key: String, task: HttpTask<*>) {
        val preTask = tasks[key]
        if (preTask != null) {
            preTask.cancel()
        }
        tasks[key] = task
    }

    fun clearAll() {
        tasks.forEach {
            it.value.cancel()
        }
        tasks.clear()
    }
}
