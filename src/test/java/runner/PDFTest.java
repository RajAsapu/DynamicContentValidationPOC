package runner;

import com.google.common.base.Verify;
import model.PDFParameters;
import org.jsoup.nodes.Document;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.pdf_interpreter.PDFInterpreter;
import utils.template_processor.FillTemplate;

public class PDFTest {
    private static Logger log = LoggerFactory.getLogger(PDFTest.class);

    @Test
    public void test() {
        PDFInterpreter interpreter=new PDFInterpreter();
        Document document=interpreter.parsePDF("TestMustang.pdf");
        FillTemplate fillTemplate=new FillTemplate();
        PDFParameters pdfParameters=fillTemplate.fillTemplatesUsingDocument(document);
        log.info(pdfParameters.toString());
        Verify.verify(pdfParameters.getName().equals("NarsimhaPolicy Donottouch"),"Name doesn't match");
        Verify.verify(pdfParameters.getPolicyNumber().equals("GL49152363174"),"Policy Number doesn't match");
    }
}
