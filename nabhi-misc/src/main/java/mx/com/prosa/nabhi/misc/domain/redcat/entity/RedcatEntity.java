package mx.com.prosa.nabhi.misc.domain.redcat.entity;

import com.google.gson.GsonBuilder;
import mx.com.prosa.nabhi.misc.domain.complete.entity.AuditTable;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@EntityListeners( AuditTable.class )
@Table( name = "TBL_REDCAT" )
public class RedcatEntity implements Serializable {

    private static final long serialVersionUID = 23453246534L;

    @Id
    @GeneratedValue( strategy = GenerationType.TABLE )
    @Column( name = "PK_ID" )
    private int id;

    @Column( length = 4, name = "FK_FIID" )
    private String fiid;

    @Column( length = 19, name = "FK_TERMINAL_ID" )
    private String termId;

    @Column( name = "COUT1" )
    private int cout1;

    @Column( name = "COUT2" )
    private int cout2;

    @Column( name = "COUT3" )
    private int cout3;

    @Column( name = "COUT4" )
    private int cout4;

    @Column( name = "COUT5" )
    private int cout5;

    @Column( name = "COUT6" )
    private int cout6;

    @Column( name = "END1" )
    private int end1;

    @Column( name = "END2" )
    private int end2;

    @Column( name = "END3" )
    private int end3;

    @Column( name = "END4" )
    private int end4;

    @Column( name = "END5" )
    private int end5;

    @Column( name = "END6" )
    private int end6;

    @Column( length = 3, name = "DENOMINATION1" )
    private int denomination1;

    @Column( length = 3, name = "DENOMINATION2" )
    private int denomination2;

    @Column( length = 3, name = "DENOMINATION3" )
    private int denomination3;

    @Column( length = 3, name = "DENOMINATION4" )
    private int denomination4;

    @Column( length = 3, name = "DENOMINATION5" )
    private int denomination5;

    @Column( length = 3, name = "DENOMINATION6" )
    private int denomination6;

    @Column( name = "CDE1" )
    private String currency1;

    @Column( name = "CDE2" )
    private String currency2;

    @Column( name = "CDE3" )
    private String currency3;

    @Column( name = "CDE4" )
    private String currency4;

    @Column( name = "CDE5" )
    private String currency5;

    @Column( name = "CDE6" )
    private String currency6;

    @Column( name = "REMANENTE_USD" )
    private int remanenteUSD;

    @Column( name = "REMANENTE_MXN" )
    private int remanenteMXN;

    @Column( name = "TOTAL_OUT_USD" )
    private int totalOutUSD;

    @Column( name = "TOTAL_OUT_MXN" )
    private int totalOutMXN;

    @Column( name = "DIFFERENCE_USD" )
    private int differenceUSD;

    @Column( name = "DIFFERENCE_MXN" )
    private int differenceMXN;

    @Column( name = "START_DATE" )
    private Date startDate;

    @Column( name = "FINAL_DATE" )
    private Date endDate;

    @Column( name = "COUT_TYPE", length = 3)
    private String coutType;

    public RedcatEntity() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFiid() {
        return fiid;
    }

    public void setFiid(String fiid) {
        this.fiid = fiid;
    }

    public String getTermId() {
        return termId;
    }

    public void setTermId(String termId) {
        this.termId = termId;
    }

    public int getCout1() {
        return cout1;
    }

    public void setCout1(int cout1) {
        this.cout1 = cout1;
    }

    public int getCout2() {
        return cout2;
    }

    public void setCout2(int cout2) {
        this.cout2 = cout2;
    }

    public int getCout3() {
        return cout3;
    }

    public void setCout3(int cout3) {
        this.cout3 = cout3;
    }

    public int getCout4() {
        return cout4;
    }

    public void setCout4(int cout4) {
        this.cout4 = cout4;
    }

    public int getCout5() {
        return cout5;
    }

    public void setCout5(int cout5) {
        this.cout5 = cout5;
    }

    public int getCout6() {
        return cout6;
    }

    public void setCout6(int cout6) {
        this.cout6 = cout6;
    }

    public int getEnd1() {
        return end1;
    }

    public void setEnd1(int end1) {
        this.end1 = end1;
    }

    public int getEnd2() {
        return end2;
    }

    public void setEnd2(int end2) {
        this.end2 = end2;
    }

    public int getEnd3() {
        return end3;
    }

    public void setEnd3(int end3) {
        this.end3 = end3;
    }

    public int getEnd4() {
        return end4;
    }

    public void setEnd4(int end4) {
        this.end4 = end4;
    }

    public int getEnd5() {
        return end5;
    }

    public void setEnd5(int end5) {
        this.end5 = end5;
    }

    public int getEnd6() {
        return end6;
    }

    public void setEnd6(int end6) {
        this.end6 = end6;
    }

    public int getDenomination1() {
        return denomination1;
    }

    public void setDenomination1(int denomination1) {
        this.denomination1 = denomination1;
    }

    public int getDenomination2() {
        return denomination2;
    }

    public void setDenomination2(int denomination2) {
        this.denomination2 = denomination2;
    }

    public int getDenomination3() {
        return denomination3;
    }

    public void setDenomination3(int denomination3) {
        this.denomination3 = denomination3;
    }

    public int getDenomination4() {
        return denomination4;
    }

    public void setDenomination4(int denomination4) {
        this.denomination4 = denomination4;
    }

    public int getDenomination5() {
        return denomination5;
    }

    public void setDenomination5(int denomination5) {
        this.denomination5 = denomination5;
    }

    public int getDenomination6() {
        return denomination6;
    }

    public void setDenomination6(int denomination6) {
        this.denomination6 = denomination6;
    }

    public String getCurrency1() {
        return currency1;
    }

    public void setCurrency1(String currency1) {
        this.currency1 = currency1;
    }

    public String getCurrency2() {
        return currency2;
    }

    public void setCurrency2(String currency2) {
        this.currency2 = currency2;
    }

    public String getCurrency3() {
        return currency3;
    }

    public void setCurrency3(String currency3) {
        this.currency3 = currency3;
    }

    public String getCurrency4() {
        return currency4;
    }

    public void setCurrency4(String currency4) {
        this.currency4 = currency4;
    }

    public String getCurrency5() {
        return currency5;
    }

    public void setCurrency5(String currency5) {
        this.currency5 = currency5;
    }

    public String getCurrency6() {
        return currency6;
    }

    public void setCurrency6(String currency6) {
        this.currency6 = currency6;
    }

    public int getRemanenteUSD() {
        return remanenteUSD;
    }

    public void setRemanenteUSD(int remanenteUSD) {
        this.remanenteUSD = remanenteUSD;
    }

    public int getRemanenteMXN() {
        return remanenteMXN;
    }

    public void setRemanenteMXN(int remanenteMXN) {
        this.remanenteMXN = remanenteMXN;
    }

    public int getTotalOutUSD() {
        return totalOutUSD;
    }

    public void setTotalOutUSD(int totalOutUSD) {
        this.totalOutUSD = totalOutUSD;
    }

    public int getTotalOutMXN() {
        return totalOutMXN;
    }

    public void setTotalOutMXN(int totalOutMXN) {
        this.totalOutMXN = totalOutMXN;
    }

    public int getDifferenceUSD() {
        return differenceUSD;
    }

    public void setDifferenceUSD(int differenceUSD) {
        this.differenceUSD = differenceUSD;
    }

    public int getDifferenceMXN() {
        return differenceMXN;
    }

    public void setDifferenceMXN(int differenceMXN) {
        this.differenceMXN = differenceMXN;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getCoutType() {
        return coutType;
    }

    public void setCoutType(String coutType) {
        this.coutType = coutType;
    }

    @Override
    public String toString() {
        return new GsonBuilder().setPrettyPrinting().create().toJson( this );
    }
}
