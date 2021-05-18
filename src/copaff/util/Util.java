/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package copaff.util;

import java.awt.*;
import static java.awt.Color.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javax.imageio.ImageIO;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import static org.apache.pdfbox.pdmodel.font.PDType1Font.*;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.vandeseer.easytable.TableDrawer;
import static org.vandeseer.easytable.settings.HorizontalAlignment.*;
import static org.vandeseer.easytable.settings.VerticalAlignment.MIDDLE;
import org.vandeseer.easytable.structure.*;
import org.vandeseer.easytable.structure.cell.*;
import org.vandeseer.easytable.structure.cell.ImageCell.ImageCellBuilder;
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
    private static final String imageFilePath = System.getProperty("user.home")
            + System.getProperty("file.separator")
            + "copaff";

    public Util(ObservableList<SquadCard> cards, File pdfFilePath) throws IOException {
        File copaFF = new File(imageFilePath);
        if (!copaFF.exists()) {
            copaFF.mkdir();
        }
        ArrayList<Table> tables = new ArrayList<>();
        for (SquadCard card : cards) {
            tables.add(createSquadCardTable(card));
        }
        createAndSaveDocumentWithTables(pdfFilePath.getAbsolutePath(), tables.toArray(new Table[tables.size()]));
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

    private Table createSquadCardTable(SquadCard card) throws IOException {

        return Table.builder()
                .addColumnsOfWidth(120, 80, 50)
                .borderColor(WHITE)
                .textColor(DARK_GRAY)
                .fontSize(7)
                .font(HELVETICA)
                .addRow(createHeaderRow())
                .addRow(createSquadTitle(card.getSquadInGamePosition(), card.getSquad().getName(), card.getSquadPositionPoints()))
                .addRow(createCard(card))
                .addRow(createPlayer(card.getPlayer2().getName(), card.getKillsPlayer2()))
                .addRow(createPlayer(card.getPlayer3().getName(), card.getKillsPlayer3()))
                .addRow(createPlayer(card.getPlayer4().getName(), card.getKillsPlayer4()))
                .addRow(createTotalPoints(card.getSquadTotalPoints()))
                .build();
    }

    private Row createHeaderRow() {
        return Row.builder()
                .add(TextCell.builder().borderWidth(1).padding(6).text("")
                        .colSpan(2)
                        .build())
                .add(TextCell.builder().borderWidth(1).padding(6).text("PUNTOS")
                        .build())
                .backgroundColor(GRAY)
                .textColor(WHITE)
                .font(HELVETICA_BOLD)
                .fontSize(8)
                .horizontalAlignment(CENTER)
                .build();
    }

    private Row createSquadTitle(int squadInGamePosition, String squadName, int squadPositionPoints) {
        return Row.builder()
                .add(TextCell.builder()
                        .borderWidth(1)
                        .text(String.valueOf(squadInGamePosition) + " - " + squadName)
                        .colSpan(2)
                        .build())
                .add(TextCell.builder()
                        .borderWidth(1)
                        .text(String.valueOf(String.valueOf(squadPositionPoints)))
                        .build())
                .backgroundColor(GRAY_LIGHT_3)
                .horizontalAlignment(CENTER)
                .build();
    }

    private Row createCard(SquadCard card) throws IOException {
        File imageOutputPath = new File(imageFilePath + System.getProperty("file.separator") + card.getCardID() + ".png");
        BufferedImage bImage = SwingFXUtils.fromFXImage(card.getSquadLogo(), null);
        ImageIO.write(bImage, "png", imageOutputPath);
        return Row.builder()
                .add(createAndGetImageCellBuilder(imageOutputPath.toString())
                        .rowSpan(4)
                        .build())
                .add(TextCell.builder()
                        .borderWidth(1)
                        .text(card.getPlayer1().getName())
                        .backgroundColor(GRAY_LIGHT_3)
                        .verticalAlignment(MIDDLE)
                        .build())
                .add(TextCell.builder()
                        .borderWidth(1)
                        .text(String.valueOf(card.getKillsPlayer1()))
                        .backgroundColor(GRAY_LIGHT_3)
                        .horizontalAlignment(CENTER)
                        .verticalAlignment(MIDDLE)
                        .build())
                .build();
    }

    private Row createPlayer(String playerName, int playerKills) {
        return Row.builder()
                .add(TextCell.builder()
                        .borderWidth(1)
                        .text(playerName)
                        .backgroundColor(GRAY_LIGHT_3)
                        .verticalAlignment(MIDDLE)
                        .build())
                .add(TextCell.builder()
                        .borderWidth(1)
                        .text(String.valueOf(playerKills))
                        .backgroundColor(GRAY_LIGHT_3)
                        .horizontalAlignment(CENTER)
                        .verticalAlignment(MIDDLE)
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
                        .horizontalAlignment(CENTER)
                        .build())
                .build();
    }

    private ImageCellBuilder createAndGetImageCellBuilder(String imagePath) throws IOException {
        return ImageCell.builder()
                .verticalAlignment(MIDDLE)
                .horizontalAlignment(CENTER)
                .borderWidth(1)
                .image(createImage(imagePath))
                .scale(0.1f);
    }

    public static String generateIDString() {
        return UUID.randomUUID().toString().toUpperCase();
    }
}
