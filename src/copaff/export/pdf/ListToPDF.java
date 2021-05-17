package copaff.export.pdf;

import be.quodlibet.boxable.BaseTable;
import be.quodlibet.boxable.datatable.DataTable;
import java.io.File;
import java.io.IOException;
import java.util.List;
import copaff.alert.AlertMaker;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType0Font;

/*
@author afsal-5502
 */
public class ListToPDF {

    public enum Orientation {
        PORTRAIT, LANDSCAPE
    };

    public boolean doPrintToPdf(List<List> list, File saveLoc, Orientation orientation) {
        try {
            if (saveLoc == null) {
                return false;
            }
            if (!saveLoc.getName().endsWith(".pdf")) {
                saveLoc = new File(saveLoc.getAbsolutePath() + ".pdf");
            }
            //Initialize Document
            PDDocument doc = new PDDocument();
            PDPage page = new PDPage();
            //Create a landscape page
            if (orientation == Orientation.PORTRAIT) {
                page.setMediaBox(new PDRectangle(PDRectangle.A4.getHeight(), PDRectangle.A4.getWidth()));
            } else {
                page.setMediaBox(new PDRectangle(PDRectangle.A4.getWidth(), PDRectangle.A4.getHeight()));
            }
            doc.addPage(page);
            PDFont font =  PDType0Font.load(doc, new File("C:\\Users\\tavos\\Downloads\\Shadows_Into_Light_Two\\ShadowsIntoLightTwo-Regular.ttf"));
            PDPageContentStream contentStream = new PDPageContentStream(doc, page);
            contentStream.setFont(font, 12);
            //Initialize table
            float margin = 10;
            float tableWidth = page.getMediaBox().getWidth() - (2 * margin);
            float yStartNewPage = page.getMediaBox().getHeight() - (2 * margin);
            float yStart = yStartNewPage;
            float bottomMargin = 0;
            BaseTable dataTable = new BaseTable(yStart, yStartNewPage, bottomMargin, tableWidth, margin, doc, page, true,
                    true);
            DataTable t = new DataTable(dataTable, page);
            t.addListToTable(list, DataTable.HASHEADER);
            dataTable.draw();
            contentStream.close();
            doc.save(saveLoc);
            doc.close();

            return true;
        } catch (IOException ex) {
            AlertMaker.showErrorMessage("Error occurred during PDF export", ex.getMessage());
        }
        return false;
    }

}
