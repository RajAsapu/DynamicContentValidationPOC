package utils.template_processor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import model.PDFParameters;
import org.apache.commons.io.FileUtils;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.pdf_interpreter.ValueRetriever;

import java.io.File;
import java.io.IOException;

public class FillTemplate {
    private static Logger log = LoggerFactory.getLogger(FillTemplate.class);
    private String path = null;
    private ValueRetriever valueRetriever=null;

    public FillTemplate() {
        path = this.getClass().getClassLoader().getResource("template").getPath();
        valueRetriever=new ValueRetriever();
    }

    public PDFParameters fillTemplatesUsingDocument(Document document){
        ObjectMapper objectMapper=new ObjectMapper();
        try {
            JsonParser parser=new JsonParser();
            JsonElement jsonElement= parser.parse(FileUtils.readFileToString(new File(path+ File.separator+"test_mustang_validation_template.json"),"UTF-8"));
            JsonObject jsonObject = jsonElement.getAsJsonObject();
            for(String key:jsonObject.keySet()){
                jsonObject.addProperty(key,valueRetriever.getValue(key,document));
            }
            return objectMapper.readValue(jsonObject.toString(), PDFParameters.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}