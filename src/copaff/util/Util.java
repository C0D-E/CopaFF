/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package copaff.util;

import copaff.model.relations.SquadCardModel;
import java.awt.*;
import static java.awt.Color.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javafx.collections.ObservableList;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import static org.apache.pdfbox.pdmodel.font.PDType1Font.*;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.vandeseer.easytable.TableDrawer;
import static org.vandeseer.easytable.settings.HorizontalAlignment.*;
import static org.vandeseer.easytable.settings.VerticalAlignment.MIDDLE;
import org.vandeseer.easytable.structure.Row;
import org.vandeseer.easytable.structure.Table;
import org.vandeseer.easytable.structure.cell.ImageCell;
import org.vandeseer.easytable.structure.cell.ImageCell.ImageCellBuilder;
import org.vandeseer.easytable.structure.cell.TextCell;
import org.vandeseer.easytable.structure.cell.TextCell.TextCellBuilder;
import squadcard.SquadCard;

/**
 *
 * @author tavos
 */
public class Util {

    private final static Color GRAY_LIGHT_1 = new Color(245, 245, 245);
    private final static Color GRAY_LIGHT_2 = new Color(240, 240, 240);
    private final static Color GRAY_LIGHT_3 = new Color(216, 216, 216);
    private static final PDDocument PD_DOCUMENT_FOR_IMAGES = new PDDocument();
    private static final float PADDING = 50f;

    public Util(ObservableList<SquadCard> cards) throws IOException {
        createAndSaveDocumentWithTables(locationToSavePDF.toString(), createSquadCardTable(imagePath, card));
    }

    private void createAndSaveDocumentWithTables(String outputFileName, Table... tables) throws IOException {
        PDDocument document = new PDDocument();
        final PDPage page = new PDPage(PDRectangle.A4);
        document.addPage(page);

        float startY = page.getMediaBox().getHeight() - PADDING;

        try (final PDPageContentStream contentStream = new PDPageContentStream(document, page)) {

            for (final Table table : tables) {

                TableDrawer.builder()
                        .page(page)
                        .contentStream(contentStream)
                        .table(table)
                        .startX(PADDING)
                        .startY(startY)
                        .endY(PADDING)
                        .build()
                        .draw(() -> document, () -> new PDPage(PDRectangle.A4), PADDING);

                startY -= (table.getHeight() + PADDING);
            }

        }

        document.save(outputFileName);
        document.close();

    }

    public static PDImageXObject createImage(String imagePath) throws IOException {
        return PDImageXObject.createFromFile(imagePath, PD_DOCUMENT_FOR_IMAGES);
    }

    private Table createSquadCardTable(String imagePath, SquadCard card) throws IOException {

        return Table.builder()
                .addColumnsOfWidth(120, 80, 50)
                .borderColor(WHITE)
                .textColor(DARK_GRAY)
                .fontSize(7)
                .font(HELVETICA)
                .addRow(createHeaderRow())
                .addRow(createSquad(card.getSquadInGamePosition(), card.getSquad().getName(), card.getSquadPositionPoints()))
                .addRow(createSquadLogo(imagePath))
                .addRow(createPlayer(card.getPlayer1().getName(), card.getKillsPlayer1()))
                .addRow(createPlayer(card.getPlayer2().getName(), card.getKillsPlayer2()))
                .addRow(createPlayer(card.getPlayer3().getName(), card.getKillsPlayer3()))
                .addRow(createPlayer(card.getPlayer4().getName(), card.getKillsPlayer4()))
                .addRow(createTotalPoints(card.getSquadTotalPoints()))
                .build();
    }

    private Row createHeaderRow() {
        return Row.builder()
                .add(TextCell.builder().borderWidth(1).padding(6).text("").colSpan(2).build())
                .add(TextCell.builder().borderWidth(1).padding(6).text("PUNTOS").build())
                .backgroundColor(GRAY)
                .textColor(WHITE)
                .font(HELVETICA_BOLD)
                .fontSize(8)
                .horizontalAlignment(CENTER)
                .build();
    }

    private Row createSquad(int squadInGamePosition, String squadName, int squadPositionPoints) {
        return Row.builder()
                .add(TextCell.builder()
                        .borderWidth(1)
                        .text(String.valueOf(squadInGamePosition) + " - " + squadName)
                        .backgroundColor(GRAY_LIGHT_3)
                        .colSpan(2)
                        .build())
                .add(TextCell.builder()
                        .borderWidth(1)
                        .text(String.valueOf(String.valueOf(squadPositionPoints)))
                        .backgroundColor(GRAY_LIGHT_3)
                        .horizontalAlignment(CENTER)
                        .build())
                .build();
    }

    private Row createSquadLogo(String imagePath) throws IOException {
        return Row.builder()
                .add(createAndGetImageCellBuilder(imagePath).rowSpan(4).build())
                .build();
    }

    private Row createPlayer(String playerName, int playerKills) {
        return Row.builder()
                .add(TextCell.builder()
                        .borderWidth(1)
                        .text(playerName)
                        .backgroundColor(GRAY_LIGHT_3)
                        .build())
                .add(TextCell.builder()
                        .borderWidth(1)
                        .text(String.valueOf(playerKills))
                        .backgroundColor(GRAY_LIGHT_3)
                        .horizontalAlignment(CENTER)
                        .build())
                .build();
    }

    private Row createTotalPoints(int totalPoints) {
        return Row.builder()
                .add(TextCell.builder()
                        .borderWidth(1)
                        .text("Puntos en Total")
                        .backgroundColor(GRAY_LIGHT_2)
                        .colSpan(2)
                        .build())
                .add(TextCell.builder()
                        .borderWidth(1)
                        .text(String.valueOf(totalPoints))
                        .backgroundColor(GRAY_LIGHT_2)
                        .build())
                .build();
    }

    private ImageCellBuilder createAndGetImageCellBuilder(String imagePath) throws IOException {
        return ImageCell.builder()
                .verticalAlignment(MIDDLE)
                .horizontalAlignment(CENTER)
                .borderWidth(1)
                .image(createImage(imagePath))
                .scale(0.4f);
    }

    private TextCellBuilder createAndGetTorvaldsQuoteCellBuilder() {
        return TextCell.builder().borderWidth(1)
                .text("\"I'm doing a (free) operating system (just a hobby, "
                        + "won't be big and professional like gnu) for 386(486) AT clones\" \n\n "
                        + "– Linus Torvalds")
                .verticalAlignment(MIDDLE)
                .horizontalAlignment(JUSTIFY)
                .padding(14)
                .font(HELVETICA_OBLIQUE)
                .backgroundColor(GRAY_LIGHT_1);
    }
}
