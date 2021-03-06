package wu.seal.jsontokotlin

import com.intellij.openapi.editor.Document
import com.intellij.openapi.project.Project
import wu.seal.jsontokotlin.supporter.*

/**
 * to be a helper to insert Import class declare code
 * Created by Seal.Wu on 2017/9/18.
 */


interface IImportClassWriter {

    fun insertGsonImportClass(project: Project?, editFile: Document)


    fun insertJackSonImportClass(project: Project?, editFile: Document)


    fun insertFastJsonImportClass(project: Project?, editFile: Document)


    fun insertImportClassCode(project: Project?, editFile: Document)

    fun insertMoShiImportClass(project: Project?, editFile: Document)

    fun insertLoganSquareImportClass(project: Project?, editFile: Document)

    fun insertCustomImportClass(project: Project?, editFile: Document)
}


object ImportClassWriter : IImportClassWriter {


    override fun insertCustomImportClass(project: Project?, editFile: Document) {
        val importClassString = CustomJsonLibSupporter.annotationImportClassString
        insertImportClassString(editFile, importClassString, project)
    }

    override fun insertMoShiImportClass(project: Project?, editFile: Document) {
        val importClassString = MoShiSupporter.annotationImportClassString
        insertImportClassString(editFile, importClassString, project)
    }

    override fun insertLoganSquareImportClass(project: Project?, editFile: Document) {
        val importClassString = LoganSquareSupporter.annotationImportClassString
        insertImportClassString(editFile, importClassString, project)
    }

    override fun insertImportClassCode(project: Project?, editFile: Document) {

        when (ConfigManager.targetJsonConverterLib) {

            TargetJsonConverter.Gson -> insertGsonImportClass(project, editFile)
            TargetJsonConverter.FastJson -> insertFastJsonImportClass(project, editFile)
            TargetJsonConverter.Jackson -> insertJackSonImportClass(project, editFile)
            TargetJsonConverter.MoShi -> insertMoShiImportClass(project, editFile)
            TargetJsonConverter.LoganSquare -> insertLoganSquareImportClass(project, editFile)
            TargetJsonConverter.Custom -> insertCustomImportClass(project, editFile)

            else -> {
                println("No need to import any Class code")
            }
        }
    }


    override fun insertFastJsonImportClass(project: Project?, editFile: Document) {
        val importClassString = FastjsonSupporter.annotationImportClassString
        insertImportClassString(editFile, importClassString, project)
    }

    override fun insertJackSonImportClass(project: Project?, editFile: Document) {
        val importClassString = JacksonSupporter.annotationImportClassString
        insertImportClassString(editFile, importClassString, project)
    }

    override fun insertGsonImportClass(project: Project?, editFile: Document) {

        val importClassString = GsonSupporter.annotationImportClassString
        insertImportClassString(editFile, importClassString, project)
    }

    private fun insertImportClassString(editFile: Document, importClassString: String, project: Project?) {

        val text = editFile.text
        importClassString.split("\n").forEach { importClassLineString ->

            if (importClassLineString !in text) {

                val packageIndex = text.indexOf("package ")
                val importIndex = Math.max(text.lastIndexOf("import"), packageIndex)
                val insertIndex = if (importIndex == -1) 0 else editFile.getLineEndOffset(editFile.getLineNumber(importIndex))


                executeCouldRollBackAction(project) {
                    editFile.insertString(insertIndex, "\n" + importClassLineString + "\n")
                }

            }
        }
    }

}


