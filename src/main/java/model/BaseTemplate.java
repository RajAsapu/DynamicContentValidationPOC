package model;

public enum BaseTemplate {
    CGL_TEMPLATE("cgl_base_template.txt");

    String fileName;

    BaseTemplate(String fileName){
        this.fileName=fileName;
    }

    public String getFileName(){
        return fileName;
    }
}
