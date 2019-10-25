package pdf;

import org.apache.pdfbox.io.RandomAccessBufferedFileInputStream;
import org.apache.pdfbox.io.RandomAccessRead;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;

/**
 * pdf 中提取特定文本
 * @author: liumch
 * @create: 2019/8/21 18:36
 **/

public class PdfParse {
    public static String  GetTextFromPdf(String filename) throws Exception {
        String content = null;
        PDDocument pdfdocument = null;

        FileInputStream is = new FileInputStream(filename);
        RandomAccessRead read = new RandomAccessBufferedFileInputStream(is);
        PDFParser parser = new PDFParser(read);
        parser.parse();
        pdfdocument = parser.getPDDocument();
        PDFTextStripper stripper = new PDFTextStripper();
        content = stripper.getText(pdfdocument);
        System.out.println(content);
        is.close();
        return content;
    }
    public static void main(String[] args) {
        try {

            //获取pdf文件路径
            String pdf = GetTextFromPdf("d:\\data\\CN101782896A.pdf");
            System.out.println(pdf);
            //输出到txt文件
            OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream("d:\\data\\pdf.txt"));
            osw.write(pdf);
            osw.flush();
            osw.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
