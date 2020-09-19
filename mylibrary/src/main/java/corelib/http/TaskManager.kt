package corelib.http

object TaskManager {

    val taskPool: MutableMap<String, ArrayList<TaskStateListener>> = mutableMapOf()

    val taskCallBacks: MutableMap<String, TaskContainer> = mutableMapOf()

    fun registCallBack(name: String, callback: TaskContainer) {
        taskCallBacks[name] = callback
    }

    fun registTasks(name: String, tasks: ArrayList<TaskStateListener>) {
        taskPool[name] = tasks
    }

    fun registTask(name: String, task: TaskStateListener) {
        if (null == taskPool[name]) {
            taskPool[name] = arrayListOf()
        }
        taskPool[name]!!.add(task)
    }

    fun clear(name: String) {
        taskPool.remove(name)
        taskCallBacks.remove(name)
    }
}

enum class TaskState {
    START,
    RUNNING,
    SUCCESS,
    ERROR,
    CANCEL,
    END;
}

interface TaskStateListener {
    var state: TaskState
    fun onTaskStateChange(state: TaskState)
}

typealias TaskContainer = (List<TaskState>?) -> Unit

class TaskStateChanger(var name: String) : TaskStateListener {

    override var state: TaskState = TaskState.START
        set(value) {
            field = value
            onTaskStateChange(value)
        }

    override fun onTaskStateChange(state: TaskState) {
        val states = arrayListOf<TaskState>()
        TaskManager.taskPool[name]?.forEach {
            if (it.state != TaskState.END) {
                return
            }
            states.add(it.state)
        }
        TaskManager.taskCallBacks[name]?.invoke(states)
    }
}