package runner;

import com.google.common.base.Verify;
import model.BaseTemplate;
import model.PDFParameters;
import org.apache.commons.io.IOUtils;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.index.IndexableField;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.fit.pdfdom.PDFDomTree;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.pdf_interpreter.LucenePDFDocument;
import utils.pdf_interpreter.PDFInterpreter;
import utils.pdf_interpreter.PDFInterpreterWithText;
import utils.template_processor.FillTemplate;

import java.io.*;
import java.util.Map;

public class PDFTest {
    private static Logger log = LoggerFactory.getLogger(PDFTest.class);

    @Test
    public void test() {
        PDFInterpreter interpreter=new PDFInterpreter();
        Document document=interpreter.parsePDF("4163767_1_1CGL.pdf");
        FillTemplate fillTemplate=new FillTemplate();
        PDFParameters pdfParameters=fillTemplate.fillTemplatesUsingDocument(document);
        log.info(pdfParameters.toString());
        Verify.verify(pdfParameters.getName().equals("NarsimhaPolicy Donottouch"),"Name doesn't match");
        Verify.verify(pdfParameters.getPolicyNumber().equals("GL49152363174"),"Policy Number doesn't match");
    }

    @Test
    public void validate(){
        PDFInterpreter interpreter=new PDFInterpreter();
        interpreter.traversePDF("TestMustang.pdf");
    }

    @Test
    public void getLuceneDocument()throws Exception{
        LucenePDFDocument lucenePDFDocument=new LucenePDFDocument();
        File[] files=new File("/Users/raj_asapu/DynamicContentValidationPOC/src/main/resources/pdfs").listFiles();
        for(File file:files){
            System.out.println("####################"+file.getName()+"##############################");
            org.apache.lucene.document.Document document=lucenePDFDocument.convertDocument(new File("src/main/resources/pdfs/"+file.getName()));
            System.out.println(IOUtils.toString(document.getField("contents").readerValue()));
            System.out.println("##################################################");
        }
    }

    @Test
    public void getMapOfvariables()throws Exception{
        Map<String,String> map=new PDFInterpreterWithText().getVariables("TestMustang.pdf", BaseTemplate.CGL_TEMPLATE);
        System.out.println("################");
        for(Map.Entry<String,String> entry:map.entrySet()){
            System.out.println(entry.getKey()+"--->"+entry.getValue());
        }
    }

    @Test
    public void getMapOfvariables2()throws Exception{
        Map<String,String> map=new PDFInterpreterWithText().getVariables("4163767_1_1CGL.pdf", BaseTemplate.CGL_TEMPLATE);
        System.out.println("################");
        for(Map.Entry<String,String> entry:map.entrySet()){
            System.out.println(entry.getKey()+"--->"+entry.getValue());
        }
    }
}
