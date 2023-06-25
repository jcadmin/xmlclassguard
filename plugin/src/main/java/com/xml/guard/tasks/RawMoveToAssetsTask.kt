package com.xml.guard.tasks

import com.google.gson.Gson
import com.xml.guard.entensions.GuardExtension
import com.xml.guard.utils.allDependencyAndroidProjects
import com.xml.guard.utils.getSuffix
import com.xml.guard.utils.javaDirs
import com.xml.guard.utils.removeSuffix
import com.xml.guard.utils.replaceWords
import com.xml.guard.utils.resDirs
import org.gradle.api.DefaultTask
import org.gradle.api.Project
import org.gradle.api.tasks.TaskAction
import java.io.File
import javax.inject.Inject

open class RawMoveToAssetsTask @Inject constructor(
    guardExtension: GuardExtension
) : DefaultTask() {
    init {
        group = "guard"
    }

    private var list = mutableListOf<String>()

    @TaskAction
    fun execute() {
        val androidProjects = allDependencyAndroidProjects()
        //1、遍历res下的xml文件，找到自定义的类(View/Fragment/四大组件等)，并将混淆结果同步到xml文件内
        androidProjects.forEach { project ->
            val rawDirs = project.resDirs().flatMapTo(ArrayList()) { dir ->
                dir.listFiles { _, name ->
                    //过滤res目录下的raw目录
                    name == "raw"
                }?.toList() ?: emptyList()
            }
            rawDirs.forEach {
                var assetsPath = it.parentFile.parentFile.path + File.separator + "assets"
                println("assetsPath :${assetsPath}")
                it.listFiles()?.forEach { file ->
                    if (file.name.getSuffix() == ".json") {
                        list.add(file.name.removeSuffix())
                        var newFile = File(assetsPath + File.separator + file.name)
                        if (!newFile.exists()) newFile.parentFile.mkdirs()
                        newFile.writeText(file.readText())
                        file.delete()
                    }
                }
            }
        }
        if (list.isNotEmpty()) {
            androidProjects.forEach {
                replaceJavaText(it, list)
                replaceXml(it, list)
            }
        }
    }

    private fun replaceJavaText(project: Project, list: List<String>) {
        val javaDirs = project.javaDirs()
        //遍历所有Java\Kt文件，替换混淆后的类的引用，import及new对象的地方
        project.files(javaDirs).asFileTree.forEach { javaFile ->
            var replaceText = javaFile.readText()
            list.forEach {
                replaceText = replaceText(javaFile, replaceText, it)
            }

            javaFile.writeText(replaceText)
        }
    }

    private fun replaceText(
        rawFile: File,
        rawText: String,
        rawPath: String
    ): String {
        var replaceText = rawText

        replaceText = replaceText.replaceWords("(R.raw.${rawPath})", "(\"${rawPath}.json\")")
            .replaceWords(
                "app:lottie_rawRes=\"@raw/${rawPath}\"",
                "app:lottie_fileName=\"${rawPath}.json\""
            )
        return replaceText
    }

    private fun replaceXml(project: Project, list: List<String>) {
        val xmlDirs = project.resDirs().flatMapTo(ArrayList()) { dir ->
            dir.listFiles { _, name ->
                //过滤res目录下的raw目录
                name.startsWith("layout")
            }?.toList() ?: emptyList()
        }
        //遍历所有Java\Kt文件，替换混淆后的类的引用，import及new对象的地方
        project.files(xmlDirs).asFileTree.forEach { javaFile ->
            var replaceText = javaFile.readText()
            list.forEach {
                replaceText = replaceText(javaFile, replaceText, it)
            }
            javaFile.writeText(replaceText)
        }
    }
}