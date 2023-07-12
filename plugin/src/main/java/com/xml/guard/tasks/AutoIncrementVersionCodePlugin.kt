package com.xml.guard.tasks

import com.android.build.gradle.internal.dsl.BaseAppModuleExtension
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction
import javax.inject.Inject

open class AutoIncrementVersionCodePlugin @Inject constructor(
    private val extensionName: String
) : DefaultTask() {
    init {
        group = "guard"
    }

    @TaskAction
    fun execute() {

    }
}