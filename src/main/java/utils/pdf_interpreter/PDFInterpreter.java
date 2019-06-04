package utils.pdf_interpreter;

import model.BaseTemplate;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.lucene.document.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

public class PDFInterpreter {
    static String templatePath=null,pdfPath=null;
    private static Logger log= LoggerFactory.getLogger(PDFInterpreter.class);

    public PDFInterpreter(){
        templatePath=this.getClass().getClassLoader().getResource("template").getPath();
        pdfPath=this.getClass().getClassLoader().getResource("pdfs").getPath();
    }

    public Map<String,String> getVariables(String pdfFileName, BaseTemplate template){
        try {
            Map<String,String> variableMap=new LinkedHashMap<>();
            String[] templateFile= FileUtils.readFileToString(new File(templatePath+File.separator+template.getFileName()),"UTF-8").split("\\n");
            String[] pdfText=getTextFromPdf(pdfFileName).split("\\n");
            //add error condition to check if template matches
            for(int i=0;i<templateFile.length;i++){
                if(templateFile[i].contains("${")){
                    retrieveValue(variableMap,templateFile[i],pdfText[i]);
                }
            }
            return variableMap;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getTextFromPdf(String pdfFileName){
        LucenePDFDocument lucenePDFDocument=new LucenePDFDocument();
        try {
            File pdfFile=new File(pdfPath+File.separator+pdfFileName);
            Document document=lucenePDFDocument.convertDocument(pdfFile);
            String pdfText= IOUtils.toString(document.getField("contents").readerValue());
            log.info(String.format("PDF to text of:%s\n%s",pdfFileName,pdfText));
            return pdfText;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    public void retrieveValue(Map<String,String> map,String template,String pdfText){
        String[] templateTokens;
        String startToken="",endToken="",value="",variable=null;
        boolean flag=false,matched=false;

        templateTokens=template.split(" ");
        // determine start index and end index- ToDO wrap text,multiple variables in single line
        for(int i=0;i<templateTokens.length;i++){
            if(templateTokens[i].contains("${")&&templateTokens[i].charAt(templateTokens[i].length()-1)=='.'){
                templateTokens[i]=templateTokens[i].substring(0,templateTokens[i].length()-1);
            }
            if(templateTokens[i].matches("^(\\$\\{[a-zA-Z0-9.]*\\})$")){
                variable=templateTokens[i].replaceAll("[${}]","");
                matched=true;
            }else if(matched){
                endToken+=templateTokens[i]+" ";
            }else {
                startToken+=templateTokens[i]+" ";
            }
        }
        value= pdfText.replace(startToken.trim(),"")
                .replace(endToken.trim(),"");
        map.put(variable,value.trim());
    }
}
