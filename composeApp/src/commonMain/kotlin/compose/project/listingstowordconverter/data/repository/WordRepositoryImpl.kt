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
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTPPr
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTSpacing
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STLineSpacingRule
import java.io.ByteArrayOutputStream
import java.math.BigInteger


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
        tableHeader.spacingBefore = 6 * 20 // 6 pt
        tableHeader.spacingAfter = 0

        val ppr: CTPPr = tableHeader.ctp.pPr ?: tableHeader.ctp.addNewPPr()
        val spacing: CTSpacing = if (ppr.isSetSpacing) ppr.spacing else ppr.addNewSpacing()
        spacing.line = BigInteger.valueOf(240) // 1 internal between
        spacing.lineRule = STLineSpacingRule.AUTO
        ppr.addNewWidowControl().`val` = true // no hanging lines



        val titleRun = tableHeader.createRun()
        titleRun.setText("Листинг $number — ${file.name}")
        titleRun.isBold = false
        titleRun.isItalic = true
        titleRun.fontSize = 14
        titleRun.fontFamily = "Times New Roman"
        titleRun.color = "000000"


        //Create table
        val table = document.createTable(1,1)

        val cell = table.getRow(0).getCell(0)
        table.setWidth("100%")

        cell.setFormattedText(file.content)

        val borderSize = 4
        val borderColor = "000000"

        table.setTopBorder(XWPFTable.XWPFBorderType.SINGLE, borderSize, 0, borderColor)
        table.setBottomBorder(XWPFTable.XWPFBorderType.SINGLE, borderSize, 0, borderColor)
        table.setLeftBorder(XWPFTable.XWPFBorderType.SINGLE, borderSize, 0, borderColor)
        table.setRightBorder(XWPFTable.XWPFBorderType.SINGLE, borderSize, 0, borderColor)

        document.createParagraph()
    }



    private fun XWPFTableCell.setFormattedText(text: String){


        val paragraph = paragraphs.firstOrNull()?: addParagraph()

        val ppr: CTPPr = paragraph.ctp.pPr ?: paragraph.ctp.addNewPPr()
        val spacing: CTSpacing = if (ppr.isSetSpacing) ppr.spacing else ppr.addNewSpacing()
        spacing.line = BigInteger.valueOf(240) // 1 internal between
        spacing.lineRule = STLineSpacingRule.AUTO
        ppr.addNewWidowControl().`val` = true // no hanging lines

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

        spacingBetween = 1.0
        alignment = ParagraphAlignment.LEFT

        indentationLeft = 0
        indentationRight = 0
        firstLineIndent = 0

        spacingBefore = 0
        spacingAfter = 0


        //splits text to parts with text - space flag
        val parts = splitToPreservedParts(line)

        parts.forEach { (chunk, isWhitespace) ->
            val run = createRun()
            run.isBold = false
            run.fontSize = 10
            run.fontFamily = "Times New Roman"
            run.color = "000000"

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
    private fun splitToPreservedParts(text: String): List<Pair<String, Boolean>>{
        val result = mutableListOf<Pair<String, Boolean>>()
        var currentChunk = "" //text fragment
        var isCurrentWhitespace = false //flag which means that current chunk is spaces

        text.forEach { ch ->
            //if space
            val isCharWhitespace = ch.isWhitespace()

            when {
                currentChunk.isEmpty() -> {
                    currentChunk+=ch
                    isCurrentWhitespace = isCharWhitespace
                }
                isCharWhitespace == isCurrentWhitespace -> {
                    // continue current block if it still spaces
                    currentChunk += ch
                }
                else -> {
                    // start new block and finish current
                    result.add(currentChunk to isCurrentWhitespace)

                    //continue with new chunk
                    currentChunk = ch.toString()
                    isCurrentWhitespace = isCharWhitespace
                }
            }

        }
        if (currentChunk.isNotEmpty()) {
            result.add(currentChunk to isCurrentWhitespace)
        }

        return  result
    }

}