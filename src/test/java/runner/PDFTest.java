package runner;

import com.google.common.base.Verify;
import model.BaseTemplate;
import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.pdf_interpreter.LucenePDFDocument;
import utils.pdf_interpreter.PDFInterpreter;

import java.io.File;
import java.util.Map;

public class PDFTest {
    private static Logger log = LoggerFactory.getLogger(PDFTest.class);

    @Test
    public void getTextFromPDF()throws Exception{
            LucenePDFDocument lucenePDFDocument=new LucenePDFDocument();
            File file=new File("/Users/raj_asapu/DynamicContentValidationPOC/src/main/resources/pdfs/");
            log.info("####################"+file.getName()+"##############################");
            org.apache.lucene.document.Document document=lucenePDFDocument.convertDocument(new File("src/main/resources/pdfs/"+file.getName()));
            log.info(IOUtils.toString(document.getField("contents").readerValue()));
            log.info("##################################################");
    }

    @Test
    public void getMapOfvariables(){
        Map<String,String> map=new PDFInterpreter().getVariables("TestMustang.pdf", BaseTemplate.CGL_TEMPLATE);
        Verify.verify(map.get("contactPhoneNumber").equals("888-202-3007"),"Contact Number doesn't match:"+map.get("contactPhoneNumber"));
        Verify.verify(map.get("firstName").equals("NarsimhaPolicy Donottouch"),"First Name doesn't match:"+map.get("firstName"));
        Verify.verify(map.get("lastName").equals("NarsimhaPolicy Donottouch"),"Last Name doesn't match:"+map.get("lastName"));
        Verify.verify(map.get("address").equals("23 MAIN"),"Address doesn't match:"+map.get("address"));
        Verify.verify(map.get("ciy").equals("Atlanta"),"City doesn't match:"+map.get("ciy"));
        Verify.verify(map.get("state").equals("GA"),"State doesn't match:"+map.get("state"));
        Verify.verify(map.get("zipcode").equals("30301"),"Zipcode doesn't match:"+map.get("zipcode"));
        Verify.verify(map.get("occupation").equals("Business consulting"),"Occupation doesn't match:"+map.get("occupation"));
        Verify.verify(map.get("telephone").equals("555-444-8888"),"Telephone doesn't match:"+map.get("telephone"));
        Verify.verify(map.get("emailAddress").equals("dsfsfsf@gmail.com"),"Email address doesn't match:"+map.get("emailAddress"));
        Verify.verify(map.get("policyNumber").equals("GL49152363174"),"Policy number doesn't match:"+map.get("policyNumber"));
        Verify.verify(map.get("totalCostOfPolicy").equals("$ 439.97"),"Total cost of policy doesn't match:"+map.get("totalCostOfPolicy"));
    }
}