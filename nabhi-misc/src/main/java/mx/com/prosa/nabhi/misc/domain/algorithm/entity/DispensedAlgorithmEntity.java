package mx.com.prosa.nabhi.misc.domain.algorithm.entity;

import com.google.gson.GsonBuilder;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.context.annotation.Lazy;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table( name = "TBL_DISPENSED_ALGORITHM", uniqueConstraints = @UniqueConstraint( columnNames = "NOMBRE", name = "NDX_UK_DISPENSED_NOMBRE" ) )
public class DispensedAlgorithmEntity implements Serializable {

    private static final long serialVersionUID = 23453246534L;

    @Id
    @GeneratedValue( generator = "ALGORITHM" )
    @SequenceGenerator( name = "ALGORITHM", sequenceName = "ALGORITHM_SEQ", allocationSize = 1 )
    @Column( name = "PK_ID" )
    private int id;

    @Column( name = "NOMBRE", length = 50 )
    private String name;

    @Column( name = "TIPO_DISPENSADO", length = 25 )
    private String dispensedType;

    @Column( name = "LIMITE_NOTAS_20" )
    private int notesLimit20;

    @Column( name = "MONTO_MINIMO_20" )
    private int minimumAmount20;

    @Column( name = "LIMITE_NOTAS_50" )
    private int notesLimit50;

    @Column( name = "MONTO_MINIMO_50" )
    private int minimumAmount50;

    @Column( name = "LIMITE_NOTAS_100" )
    private int notesLimit100;

    @Column( name = "MONTO_MINIMO_100" )
    private int minimumAmount100;

    @Column( name = "LIMITE_NOTAS_200" )
    private int notesLimit200;

    @Column( name = "MONTO_MINIMO_200" )
    private int minimumAmount200;

    @Column( name = "LIMITE_NOTAS_500" )
    private int notesLimit500;

    @Column( name = "MONTO_MINIMO_500" )
    private int minimumAmount500;

    @Column( name = "LIMITE_NOTAS_1000" )
    private int notesLimit1000;

    @Column( name = "MONTO_MINIMO_1000" )
    private int minimumAmount1000;

    @ManyToOne( optional = false, cascade = CascadeType.DETACH, fetch = FetchType.EAGER )
    @JoinColumn( name = "FIID", foreignKey = @ForeignKey( name = "FK_DISPENSED_FIID" ) )
    @Fetch( FetchMode.JOIN )
    @Lazy( value = false )
    private IDFForAlgorithmEntity idf;

    public int getId() {
        return id;
    }

    public void setId( int id ) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName( String name ) {
        this.name = name;
    }

    public String getDispensedType() {
        return dispensedType;
    }

    public void setDispensedType( String dispensedType ) {
        this.dispensedType = dispensedType;
    }

    public int getNotesLimit20() {
        return notesLimit20;
    }

    public void setNotesLimit20( int notesLimit20 ) {
        this.notesLimit20 = notesLimit20;
    }

    public int getMinimumAmount20() {
        return minimumAmount20;
    }

    public void setMinimumAmount20( int minimumAmount20 ) {
        this.minimumAmount20 = minimumAmount20;
    }

    public int getNotesLimit50() {
        return notesLimit50;
    }

    public void setNotesLimit50( int notesLimit50 ) {
        this.notesLimit50 = notesLimit50;
    }

    public int getMinimumAmount50() {
        return minimumAmount50;
    }

    public void setMinimumAmount50( int minimumAmount50 ) {
        this.minimumAmount50 = minimumAmount50;
    }

    public int getNotesLimit100() {
        return notesLimit100;
    }

    public void setNotesLimit100( int notesLimit100 ) {
        this.notesLimit100 = notesLimit100;
    }

    public int getMinimumAmount100() {
        return minimumAmount100;
    }

    public void setMinimumAmount100( int minimumAmount100 ) {
        this.minimumAmount100 = minimumAmount100;
    }

    public int getNotesLimit200() {
        return notesLimit200;
    }

    public void setNotesLimit200( int notesLimit200 ) {
        this.notesLimit200 = notesLimit200;
    }

    public int getMinimumAmount200() {
        return minimumAmount200;
    }

    public void setMinimumAmount200( int minimumAmount200 ) {
        this.minimumAmount200 = minimumAmount200;
    }

    public int getNotesLimit500() {
        return notesLimit500;
    }

    public void setNotesLimit500( int notesLimit500 ) {
        this.notesLimit500 = notesLimit500;
    }

    public int getMinimumAmount500() {
        return minimumAmount500;
    }

    public void setMinimumAmount500( int minimumAmount500 ) {
        this.minimumAmount500 = minimumAmount500;
    }

    public int getNotesLimit1000() {
        return notesLimit1000;
    }

    public void setNotesLimit1000( int notesLimit1000 ) {
        this.notesLimit1000 = notesLimit1000;
    }

    public int getMinimumAmount1000() {
        return minimumAmount1000;
    }

    public void setMinimumAmount1000( int minimumAmount1000 ) {
        this.minimumAmount1000 = minimumAmount1000;
    }

    public IDFForAlgorithmEntity getIdf() {
        return idf;
    }

    public void setIdf( IDFForAlgorithmEntity idf ) {
        this.idf = idf;
    }

    @Override
    public String toString() {
        return new GsonBuilder().setPrettyPrinting().create().toJson( this );
    }
}
