package utils.pdf_interpreter;

import model.BaseTemplate;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;


import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.lucene.document.Document;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PDFInterpreterWithText {
    static String templatePath=null,pdfPath=null;
    private static Logger log= LoggerFactory.getLogger(PDFInterpreterWithText.class);

    public PDFInterpreterWithText(){
        templatePath=this.getClass().getClassLoader().getResource("template").getPath();
        pdfPath=this.getClass().getClassLoader().getResource("pdfs").getPath();
    }

    public Map<String,String> getVariables(String pdfFileName, BaseTemplate template){
        try {
            Map<String,String> variableMap=new HashMap<>();
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
            log.info(pdfText);
            return pdfText;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

//    @Test
//    public void test(){
//        Map<String,String> map=new HashMap<>();
//        retrieveValue(map,"Guide To Your ${what} Liability Policy","A Guide To Your General Liability Policy");
//        System.out.println(map.size());
//    }

    public void retrieveValue(Map<String,String> map,String template,String pdfText){
        String[] templateTokens=template.split(" ");
        String[] pdfTokens=pdfText.split(" ");

        String startToken=null,endToken=null,value="",variable=null;
        boolean flag=false;

        // determine start index and end index- ToDO wrap text,multiple variables in single line
        for(int i=0;i<templateTokens.length;i++){
            if(templateTokens[i].matches("^(\\$\\{[a-zA-Z0-9]*\\})$")){
                startToken=(i>0?templateTokens[i-1]:null);
                endToken=((i+1)<templateTokens.length)?templateTokens[i+1]:null;
                variable=templateTokens[i].replaceAll("[${}]","");
                break;
            }
        }

        for(int i=0;i<pdfTokens.length;i++){
            if(startToken==null){
                flag=true;
            }
            if(pdfTokens[i].equals(startToken)){
                flag=true;
                continue;
            }
            if(pdfTokens[i].equals(endToken)){
                flag=false;
            }
            if(flag){
                value=value+" "+pdfTokens[i];
            }
        }
        map.put(variable,value.trim());
    }
}
