package com.sharing.Utils;

import java.io.*;
import java.util.List;

import org.apache.pdfbox.io.RandomAccessBuffer;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.poi.hslf.usermodel.HSLFSlide;
import org.apache.poi.hslf.usermodel.HSLFSlideShow;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.sl.extractor.SlideShowExtractor;
import org.apache.poi.xslf.usermodel.XMLSlideShow;
import org.apache.poi.xslf.usermodel.XSLFSlide;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.openxmlformats.schemas.drawingml.x2006.main.CTRegularTextRun;
import org.openxmlformats.schemas.drawingml.x2006.main.CTTextBody;
import org.openxmlformats.schemas.drawingml.x2006.main.CTTextParagraph;
import org.openxmlformats.schemas.presentationml.x2006.main.CTGroupShape;
import org.openxmlformats.schemas.presentationml.x2006.main.CTShape;
import org.openxmlformats.schemas.presentationml.x2006.main.CTSlide;

/**
 * 文本类型的文件处理工具类，用于获取文件的文本内容
 *
 * @author 李福生
 * @date 2022-4-14
 * @time 下午 03:13
 */
public class TextTypeFileUtil {

    public static final String TXT = "txt";
    public static final String DOC = "doc";
    public static final String DOCX = "docx";
    public static final String PPT = "ppt";
    public static final String PPTX = "pptx";
    public static final String PDF = "pdf";

    public static String[] getFileHandleType() {

        return new String[]{TXT, DOC, DOCX, PPT, PPTX, PDF};
    }

    /**
     * 读取doc文件内容
     *
     * @param filePath 想要读取的文件
     * @return 返回文件内容
     */
    public static String docString(String filePath) {
        File file = new File(filePath);
        if (!file.exists())
            return null;
        FileInputStream fis = null;
        StringBuffer result = new StringBuffer();
        WordExtractor we = null;
        try {
            fis = new FileInputStream(file);
            we = new WordExtractor(fis);
            result.append(we.getText());
        } catch (Exception e) {
            return null;
        } finally {
            try {
                if (we != null)
                    we.close();

                if (fis != null)
                    fis.close();

            } catch (Exception e) {
            }
        }

        return result.toString();
    }

    /**
     * 读取docx文件内容
     *
     * @param filePath 想要读取的文件
     * @return 返回文件内容
     */
    public static String docxString(String filePath) {
        File file = new File(filePath);
        if (!file.exists())
            return null;
        FileInputStream fis = null;
        StringBuffer result = new StringBuffer();
        XWPFDocument doc = null;
        XWPFWordExtractor extractor = null;
        try {
            fis = new FileInputStream(file);
            doc = new XWPFDocument(fis);
            extractor = new XWPFWordExtractor(doc);
            result.append(extractor.getText());
        } catch (Exception e) {
            return null;
        } finally {
            try {
                if (extractor != null)
                    extractor.close();

                if (doc != null)
                    doc.close();

                if (fis != null)
                    fis.close();

            } catch (Exception e) {
            }
        }

        return result.toString();
    }

    /**
     * 读取pdf文件内容
     *
     * @param filePath 想要读取的文件
     * @return 返回文件内容
     */
    public static String pdfString(String filePath) {
        File file = new File(filePath);
        if (!file.exists())
            return null;
        FileInputStream fis = null;
        StringBuffer result = new StringBuffer();
        PDFParser pdf = null;
        PDDocument pdDocument = null;
        PDFTextStripper textStripper = null;
        try {
            fis = new FileInputStream(file);
            pdf = new PDFParser(new RandomAccessBuffer(fis));
            pdf.parse();
            pdDocument = pdf.getPDDocument();
            textStripper = new PDFTextStripper();
            result.append(textStripper.getText(pdDocument));
        } catch (Exception e) {
            return null;
        } finally {
            try {
                if (pdDocument != null)
                    pdDocument.close();

                if (fis != null)
                    fis.close();

            } catch (Exception e) {
            }
        }

        return result.toString();
    }

    /**
     * 读取PPT文件内容
     *
     * @param filePath 想要读取的文件
     * @return 返回文件内容
     */
    public static String pptString(String filePath) {
        File file = new File(filePath);
        if (!file.exists())
            return null;

        FileInputStream fis = null;
        StringBuffer result = new StringBuffer();
        HSLFSlideShow hss = null;
        SlideShowExtractor extractor = null;
        try {
            fis = new FileInputStream(file);
            hss = new HSLFSlideShow(fis);
            // 提取每张幻灯片文字
            List<HSLFSlide> slideList = hss.getSlides();
            extractor = new SlideShowExtractor(hss);
            for (HSLFSlide slide : slideList)
                result.append(extractor.getText(slide));

        } catch (Exception e) {
            return null;
        } finally {
            try {
                if (extractor != null)
                    extractor.close();

                if (hss != null)
                    hss.close();

                if (fis != null)
                    fis.close();
            } catch (Exception e) {
            }
        }

        return result.toString();
    }

    /**
     * 读取PPTX文件内容
     *
     * @param filePath 想要读取的文件
     * @return 返回文件内容
     */
    public static String pptxString(String filePath) {
        File file = new File(filePath);
        if (!file.exists())
            return null;
        FileInputStream fis = null;
        StringBuffer result = new StringBuffer();
        XMLSlideShow xss = null;
        try {
            fis = new FileInputStream(file);
            xss = new XMLSlideShow(fis);
            // 提取每张幻灯片文字
            List<XSLFSlide> slideList = xss.getSlides();
            for (XSLFSlide slide : slideList) {
                CTSlide rawSlide = slide.getXmlObject();
                CTGroupShape spTree = rawSlide.getCSld().getSpTree();
                List<CTShape> spList = spTree.getSpList();
                for (CTShape shape : spList) {
                    CTTextBody txBody = shape.getTxBody();
                    if (null == txBody)
                        continue;
                    List<CTTextParagraph> pList = txBody.getPList();
                    for (CTTextParagraph textParagraph : pList) {
                        List<CTRegularTextRun> textRuns = textParagraph.getRList();
                        for (CTRegularTextRun textRun : textRuns)
                            result.append(textRun.getT());
                    }
                }
            }
        } catch (Exception e) {
            return null;
        } finally {
            try {
                if (xss != null)
                    xss.close();

                if (fis != null)
                    fis.close();

            } catch (Exception e) {
            }
        }

        return result.toString();
    }

    /**
     * 读取txt文件内容
     *
     * @param filePath 想要读取的文件
     * @return 返回文件内容
     */
    public static String txtString(String filePath) {
        File file = new File(filePath);
        if (!file.exists())
            return null;

        StringBuffer buffer = new StringBuffer();
        FileInputStream fis = null;
        InputStreamReader isr = null;
        BufferedReader reader = null;
        try {
            fis = new FileInputStream(file);
            isr = new InputStreamReader(fis);
            reader = new BufferedReader(isr);

            String line;
            while ((line = reader.readLine()) != null)
                buffer.append(line);

        } catch (Exception e) {
            return null;
        } finally {
            try {
                if (reader != null)
                    reader.close();
                if (isr != null)
                    isr.close();
                if (fis != null)
                    fis.close();
            } catch (Exception e) {
            }
        }

        return buffer.toString();
    }

    /**
     * 根据文件类读取文件中的文本
     *
     * @param file     需要读取的文件
     * @param fileType 文件类型
     * @return 返回文件中的所有本文
     */
    public static String getFileString(String file, String fileType) {
        String result;
        switch (fileType) {
            case DOC:
                result = docString(file);
                break;
            case DOCX:
                result = docxString(file);
                break;
            case PPT:
                result = pptString(file);
                break;
            case PPTX:
                result = pptxString(file);
                break;
            case TXT:
                result = txtString(file);
                break;
            case PDF:
                result = pdfString(file);
                break;
            default:
                result = null;
        }
        return result;
    }

}