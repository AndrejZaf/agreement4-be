package com.example.agreement4be.util;

import lombok.experimental.UtilityClass;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@UtilityClass
public class DocumentProcessorUtil {
    private static Map<Integer, XWPFRun> getPosToRuns(XWPFParagraph paragraph) {
        int pos = 0;
        Map<Integer, XWPFRun> map = new HashMap<Integer, XWPFRun>(10);
        for (XWPFRun run : paragraph.getRuns()) {
            String runText = run.text();
            if (runText != null) {
                for (int i = 0; i < runText.length(); i++) {
                    map.put(pos + i, run);
                }
                pos += runText.length();
            }
        }
        return (map);
    }

    public static <V> void replace(XWPFDocument document, Map<String, V> map) {
        List<XWPFParagraph> paragraphs = document.getParagraphs();
        for (XWPFParagraph paragraph : paragraphs) {
            replace(paragraph, map);
        }
    }

    public static <V> void replace(XWPFDocument document, String searchText, V replacement) {
        List<XWPFParagraph> paragraphs = document.getParagraphs();
        for (XWPFParagraph paragraph : paragraphs) {
            replace(paragraph, searchText, replacement);
        }
    }

    private static <V> void replace(XWPFParagraph paragraph, Map<String, V> map) {
        for (Map.Entry<String, V> entry : map.entrySet()) {
            replace(paragraph, entry.getKey(), entry.getValue());
        }
    }

    public static <V> void replace(XWPFParagraph paragraph, String searchText, V replacement) {
        boolean found = true;
        while (found) {
            found = false;
            int pos = paragraph.getText().indexOf(searchText);
            if (pos >= 0) {
                found = true;
                Map<Integer, XWPFRun> posToRuns = getPosToRuns(paragraph);
                XWPFRun run = posToRuns.get(pos);
                XWPFRun lastRun = posToRuns.get(pos + searchText.length() - 1);
                int runNum = paragraph.getRuns().indexOf(run);
                int lastRunNum = paragraph.getRuns().indexOf(lastRun);
                String texts[] = replacement.toString().split("\n");
                run.setText(texts[0], 0);
                XWPFRun newRun = run;
                for (int i = 1; i < texts.length; i++) {
                    newRun.addCarriageReturn();
                    newRun = paragraph.insertNewRun(runNum + i);
                /*
                    We should copy all style attributes
                    to the newRun from run
                    also from background color, ...
                    Here we duplicate only the simple attributes...
                 */
                    newRun.setText(texts[i]);
                    newRun.setBold(run.isBold());
                    newRun.setCapitalized(run.isCapitalized());
                    // newRun.setCharacterSpacing(run.getCharacterSpacing());
                    newRun.setColor(run.getColor());
                    newRun.setDoubleStrikethrough(run.isDoubleStrikeThrough());
                    newRun.setEmbossed(run.isEmbossed());
                    newRun.setFontFamily(run.getFontFamily());
                    newRun.setFontSize(run.getFontSize());
                    newRun.setImprinted(run.isImprinted());
                    newRun.setItalic(run.isItalic());
                    newRun.setKerning(run.getKerning());
                    newRun.setShadow(run.isShadowed());
                    newRun.setSmallCaps(run.isSmallCaps());
                    newRun.setStrikeThrough(run.isStrikeThrough());
                    newRun.setUnderline(run.getUnderline());
                }
                for (int i = lastRunNum + texts.length - 1; i > runNum + texts.length - 1; i--) {
                    paragraph.removeRun(i);
                }
            }
        }
    }

    public static <V> void replace2(XWPFParagraph paragraph, String searchText, V replacement, int startPosition) {
        Map<Integer, XWPFRun> posToRuns = getPosToRuns(paragraph);
        XWPFRun run = posToRuns.get(startPosition);
        XWPFRun lastRun = posToRuns.get(startPosition + searchText.length() - 1);
        int runNum = paragraph.getRuns().indexOf(run);
        int lastRunNum = paragraph.getRuns().indexOf(lastRun);
        String texts[] = replacement.toString().split("\n");
        run.setText(texts[0], 0);
        XWPFRun newRun = run;
        for (int i = 1; i < texts.length; i++) {
            newRun.addCarriageReturn();
            newRun = paragraph.insertNewRun(runNum + i);
                /*
                    We should copy all style attributes
                    to the newRun from run
                    also from background color, ...
                    Here we duplicate only the simple attributes...
                 */
            newRun.setText(texts[i]);
            newRun.setBold(run.isBold());
            newRun.setCapitalized(run.isCapitalized());
            // newRun.setCharacterSpacing(run.getCharacterSpacing());
            newRun.setColor(run.getColor());
            newRun.setDoubleStrikethrough(run.isDoubleStrikeThrough());
            newRun.setEmbossed(run.isEmbossed());
            newRun.setFontFamily(run.getFontFamily());
            newRun.setFontSize(run.getFontSize());
            newRun.setImprinted(run.isImprinted());
            newRun.setItalic(run.isItalic());
            newRun.setKerning(run.getKerning());
            newRun.setShadow(run.isShadowed());
            newRun.setSmallCaps(run.isSmallCaps());
            newRun.setStrikeThrough(run.isStrikeThrough());
            newRun.setUnderline(run.getUnderline());
        }
        for (int i = lastRunNum + texts.length - 1; i > runNum + texts.length - 1; i--) {
            paragraph.removeRun(i);
        }
    }
}
