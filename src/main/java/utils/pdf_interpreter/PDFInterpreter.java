package utils.pdf_interpreter;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.fit.pdfdom.PDFDomTree;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.StringWriter;
import java.io.Writer;

public class PDFInterpreter {

    private static Logger log = LoggerFactory.getLogger(PDFInterpreter.class);
    private String path = null;

    public PDFInterpreter() {
        path = this.getClass().getClassLoader().getResource("pdfs").getPath();
    }

    public Document parsePDF(String fileName){
        try {
            PDDocument pdf = PDDocument.load(new File(path+File.separator+fileName));
            PDFDomTree tree=new PDFDomTree();
            tree.setStartPage(1);
            tree.setEndPage(1);
            Writer output = new StringWriter();
            tree.writeText(pdf, output);
            pdf.close();
            return Jsoup.parse(output.toString());
        }catch (Exception exp){
            exp.printStackTrace();
        }
        return null;
    }
}
