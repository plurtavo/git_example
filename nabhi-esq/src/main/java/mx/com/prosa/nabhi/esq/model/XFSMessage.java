package mx.com.prosa.nabhi.esq.model;

public class XFSMessage {

    private String nemoTecnico;
    private int codigoXFS;

    public XFSMessage( String nemoTecnico, int codigoXFS ) {
        this.nemoTecnico = nemoTecnico;
        this.codigoXFS = codigoXFS;
    }

    public String getNemoTecnico() {
        return nemoTecnico;
    }

    public void setNemoTecnico(String nemoTecnico) {
        this.nemoTecnico = nemoTecnico;
    }

    public int getCodigoXFS() {
        return codigoXFS;
    }

    public void setCodigoXFS(int codigoXFS) {
        this.codigoXFS = codigoXFS;
    }
    
}
