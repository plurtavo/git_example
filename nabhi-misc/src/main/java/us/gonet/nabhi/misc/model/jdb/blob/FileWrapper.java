package us.gonet.nabhi.misc.model.jdb.blob;

import com.google.gson.GsonBuilder;

import java.io.Serializable;

public class FileWrapper implements Serializable {

    private static final long serialVersionUID = 23453246534L;

    private String name;
    private String extension;
    private String format;
    private String codec;

    public String getName() {
        return name;
    }

    public void setName( String name ) {
        this.name = name;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension( String extension ) {
        this.extension = extension;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat( String format ) {
        this.format = format;
    }

    public String getCodec() {
        return codec;
    }

    public void setCodec( String codec ) {
        this.codec = codec;
    }

    @Override
    public String toString() {
        return new GsonBuilder().setPrettyPrinting().create().toJson( this );
    }
}
