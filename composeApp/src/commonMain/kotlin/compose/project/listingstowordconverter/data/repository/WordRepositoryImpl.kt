package compose.project.listingstowordconverter.data.repository

import compose.project.listingstowordconverter.domain.model.FileWithCodeModel
import compose.project.listingstowordconverter.domain.repository.WordRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.apache.poi.xwpf.usermodel.ParagraphAlignment
import org.apache.poi.xwpf.usermodel.XWPFDocument
import org.apache.poi.xwpf.usermodel.XWPFParagraph
import org.apache.poi.xwpf.usermodel.XWPFTable
import org.apache.poi.xwpf.usermodel.XWPFTableCell
import java.io.ByteArrayOutputStream

class WordRepositoryImpl(): WordRepository {

    @Suppress("SimpleDateFormat")
    override suspend fun convertFilesToWord(files: List<FileWithCodeModel>): Result<ByteArray> {
        return withContext(Dispatchers.IO) {
            try {
                val document = XWPFDocument()
                files.forEachIndexed { index, file ->
                    createTable(document,file,index+1)
                }
                ByteArrayOutputStream().use { baos ->
                    document.write(baos)
                    document.close()
                    Result.success(baos.toByteArray())
                }
            }catch (e: Exception){
                Result.failure(e)
            }
        }
    }


    private fun createTable(document: XWPFDocument, file: FileWithCodeModel,number: Int){

        //Create table title with number
        val tableHeader = document.createParagraph()
        tableHeader.alignment = ParagraphAlignment.LEFT
        tableHeader.spacingBefore = 100

        val titleRun = tableHeader.createRun()
        titleRun.setText("Листинг $number — ${file.name}")
        titleRun.isBold = false
        titleRun.fontSize = 12
        titleRun.fontFamily = "Times New Roman"

        //Create table
        val table = document.createTable(1,1)

        val cell = table.getRow(0).getCell(0)

        cell.setFormattedText(file.content)


        val borderSize = 4
        val borderColor = "000000"

        table.setTopBorder(XWPFTable.XWPFBorderType.SINGLE, borderSize, 0, borderColor)
        table.setBottomBorder(XWPFTable.XWPFBorderType.SINGLE, borderSize, 0, borderColor)
        table.setLeftBorder(XWPFTable.XWPFBorderType.SINGLE, borderSize, 0, borderColor)
        table.setRightBorder(XWPFTable.XWPFBorderType.SINGLE, borderSize, 0, borderColor)

        val spacingParagraph = document.createParagraph()
        val spacingRun = spacingParagraph.createRun()
        spacingRun.setText("")
        spacingRun.addBreak()
    }



    private fun XWPFTableCell.setFormattedText(text: String){


        val paragraph = paragraphs.firstOrNull()?: addParagraph()

        // splits text with \n
        val lines = text.split('\n')
        lines.forEachIndexed { lineIndex,line ->
            if (lineIndex > 0){
                // crate Enter
                paragraph.createRun().addBreak()
            }
            paragraph.addFormatLine(line)
        }
    }
    // Add text with spaces to paragraph
    private fun XWPFParagraph.addFormatLine(line:String){
        if (line.isEmpty()) return

        //splits text to parts with text - space flag
        val parts = splitToPreservedParts(line)

        parts.forEach { (chunk, isWhitespace) ->
            val run = createRun()
            run.isBold = false
            run.fontSize = 10
            run.fontFamily = "Times New Roman"

            if (isWhitespace){
                // create new XML element in WOrd document
                val ctTxt = run.ctr.addNewT()
                ctTxt.set(
                    org.apache.xmlbeans.XmlObject.Factory.parse(
                        "<w:t xmlns:w=\"http://schemas.openxmlformats.org/wordprocessingml/2006/main\" xml:space=\"preserve\">$chunk</w:t>"
                    )
                )
            }else {
                // if normal text
                run.setText(chunk)

            }
        }

    }


}