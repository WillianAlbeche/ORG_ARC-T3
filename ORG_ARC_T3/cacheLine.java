public class cacheLine {
    private String line;
    private String tag;
    private String data;

    // LINHAS DIRETO
    public cacheLine(String tag,String data, String line){
        this.tag = tag;
        this.data = data;
        this.line = line;
    }
    // LINHAS ASSOCIATIVO
    public cacheLine(String tg, String dt){
        tag = tg;
        data = dt;
        line = "";
    }

    public String getLine(){ return line; }
    public String getTag(){ return tag; }
    public String getData(){ return data; }

    public void updateLine(String nt, String nd){
        tag = nt;
        data = nd;
    }
}
