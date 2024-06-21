package com.golapp.forestquest.utils.extentions

import java.io.File

fun File.show(): List<String> {
    val result = mutableListOf<String>()
    fun lookAtFile(dir: File?, prevDir: String?) {
        if (dir != null && dir.isDirectory) {
            val children = dir.list()
            if (children != null && children.isNotEmpty()) {
                children.forEach {
                    lookAtFile(File(dir, it), "${prevDir ?: ".."}/${dir.name}")
                }
            } else if (dir.name == name) {
                val emptyResult = "$name is EMPTY"
                result.add(emptyResult)
            }
        } else if (dir != null && dir.isFile) {
            val filePath = "$prevDir/"+dir.name
            result.add(filePath)
        }
    }
    lookAtFile(this, null)
    result.add("total size: ${length()/1000} kb")
    return result
}

fun File.clear(): List<String> {
    val resultList = mutableListOf<String>()
    fun deleteDir(dir: File?): Boolean {
        val result = if (dir != null && dir.isDirectory) {
            val children = dir.list()
            if (children != null) {
                for (i in children.indices) {
                    val success = deleteDir(File(dir, children[i]))
                    if (!success) {
                        return false
                    }
                }
            }
            dir.delete()
        } else if (dir != null && dir.isFile) {
            dir.delete()
        } else false
        dir?.let {
            val message = it.name + if (result) " [deleted]" else " [not deleted]"
            resultList.add(message)
        }
        return result
    }
    try {
        deleteDir(this)
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return resultList
}