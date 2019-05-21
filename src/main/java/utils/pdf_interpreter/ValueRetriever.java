
package utils.pdf_interpreter;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ValueRetriever {
    public String getValue(String key,Document document){
        Elements elements = document.select("div[class=p]");
        List<String> list=elements.eachText();
        String styleKey=getTop(elements,list,key);
        return retrieveValue(key,styleKey,document).trim();
    }

    private String parseStyle(String style,String attr){
        String[] array=style.split(";");
        for(String key:array){
            if(key.contains(attr)){
                return key.split(":")[1];
            }
        }
        return null;
    }

    private String retrieveValue(String key,String styleKey,Document document){
        Elements elements = document.select(String.format("div[style*=%s]",styleKey));
        String result="";
        for(String temp:elements.eachText()){
            if(!key.contains(temp)){
                result=result+" "+temp;
            }
        }
        return result;
    }
    /*
    Fix problem with multi line key
     */
    private String getTop(Elements elements,List<String> list,String key){
        if(list.indexOf(key)!=-1)
            return parseStyle(elements.get(list.indexOf(key)).attr("style"),"top");

        String[] array=key.split(" ");
        Map<String,List<String>> map=new HashMap<>();

        for(int i=0;i<array.length;i++){
            List<String> topList=new ArrayList<>();
            for(int j=0;j<list.size();j++){
                if(list.get(j).equals(array[i])){
                    topList.add(parseStyle(elements.get(j).attr("style"),"top"));
                }
            }
            map.put(array[i],topList);
        }

        for(int index=0;index<map.get(array[0]).size();index++){
            if(map.get(array[1]).contains(map.get(array[0]).get(index))){
                return map.get(array[0]).get(index);
            }
        }
        return null;
    }
}
