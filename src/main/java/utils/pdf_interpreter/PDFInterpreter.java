package utils.pdf_interpreter;

import org.apache.pdfbox.pdmodel.*;
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

    public void traversePDF(String fileName){

        try {
            PDDocument doc = PDDocument.load(new File(path + File.separator + fileName));
            PDDocumentInformation info = doc.getDocumentInformation();
            System.out.println(info.getKeywords());
//            COSDictionary dict = info.getKeywords();
//            for (Map.Entry<COSName, COSBase> object : dict.entrySet()) {
//                System.out.println(object.getKey() + "-----" + object.getValue());
//            }
//            PDDocumentCatalog cat = doc.getDocumentCatalog();
//            System.out.println("Catalog:" + cat);
//
//            PDPageTree lp = cat.getPages();
//            System.out.println("# Pages: " + lp.getCount());
//            PDPage page = lp.get(1);
//            System.out.println("Page: " + page);
//            System.out.println("\tCropBox: " + page.getCropBox());
//            System.out.println("\tMediaBox: " + page.getMediaBox());
//            System.out.println("\tResources: " + page.getResources());
//            System.out.println("\tRotation: " + page.getRotation());
//            System.out.println("\tArtBox: " + page.getArtBox());
//            System.out.println("\tBleedBox: " + page.getBleedBox());
//            Iterator<PDStream> iterable=page.getContentStreams();
//            while(iterable.hasNext()){
//                System.out.println(iterable.next().getCOSObject().toTextString());
//            }
//            //System.out.println("\tContents: " + page.getContents().readAllBytes().());
//            System.out.println("\tTrimBox: " + page.getTrimBox());
//            List<PDAnnotation> la = page.getAnnotations();
//            System.out.println("\t# Annotations: " + la.size());
        }catch (Exception exp){
            exp.printStackTrace();
        }

    }

    public org.apache.lucene.document.Document getLuceneDocument(String fileName)throws Exception{
        LucenePDFDocument converter = new LucenePDFDocument();
        return converter.convertDocument(new File(path + File.separator + fileName));

    }
}
