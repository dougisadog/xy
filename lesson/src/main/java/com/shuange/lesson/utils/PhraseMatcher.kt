package com.shuange.lesson.utils

import corelib.extension.lowercased

class PhraseMatcher {
    val target: String
    val input: String

    val targets: MutableList<String>
    val inputs: MutableList<String>

    //输入未与目标对应的目标index集合
    var targetErrorIndexes = arrayListOf<Int>()

    //输入未与目标对应的输入index集合
    var inputErrorIndexes = arrayListOf<Int>()

    constructor(target: String, input: String) {
        this.target = target
        this.input = input
        targets = target.lowercased().split(" ").filter { it != "" }.toMutableList()
        inputs = input.lowercased().split(" ").filter { it != "" }.toMutableList()
        compare()
    }

    constructor(targets: MutableList<String>, input: String) {
        this.targets = targets
        this.input = input
        val s = StringBuilder()
        targets.forEachIndexed { index, text ->
            s.append(text)
            if (index < targets.size - 1) {
                s.append(" ")
            }
        }
        target = s.toString()
        inputs = input.lowercased().split(" ").filter { it != "" }.toMutableList()
        compare()
    }

    constructor(target: String, inputs: MutableList<String>) {
        this.target = target
        this.inputs = inputs
        val s = StringBuilder()
        inputs.forEachIndexed { index, text ->
            s.append(text)
            if (index < inputs.size - 1) {
                s.append(" ")
            }
        }
        input = s.toString()
        targets = target.lowercased().split(" ").filter { it != "" }.toMutableList()
        compare()
    }

    fun getErrorCount(): Int {
        return targetErrorIndexes.size
    }


    private fun compare() {
        var sourceInputs = mutableListOf<String>().apply { addAll(inputs) }
        //输入数组上次匹配string成功的index
        var inputIndex = 0
        for (i in 0 until targets.size) {
            val t = targets[i]
            //剩余输入匹配的index
            val position = sourceInputs.indexOf(t)
            if (-1 == position) {
                targetErrorIndexes.add(i)
            } else {
                //截取剩余input数组
                sourceInputs = sourceInputs.subList(position + 1, sourceInputs.size)
                inputErrorIndexes.addAll(inputIndex until inputIndex + position)
                inputIndex += position + 1
            }
        }
        //将剩余未匹配的输入添加到输入错误中
        inputErrorIndexes.addAll(inputIndex until this.inputs.size)
    }
}