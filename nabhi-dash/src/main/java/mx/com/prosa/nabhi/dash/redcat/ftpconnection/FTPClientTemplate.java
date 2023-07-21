package mx.com.prosa.nabhi.dash.redcat.ftpconnection;


public class FTPClientTemplate {

    private String user;

    private String p;

    private String ip;

    private int port;

    public FTPClientTemplate(String user, String p, String ip, int port) {
        this.user = user;
        this.p = p;
        this.ip = ip;
        this.port = port;
    }

    public FTPClientTemplate() {
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getP() {
        return p;
    }

    public void setP(String p) {
        this.p = p;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
}
