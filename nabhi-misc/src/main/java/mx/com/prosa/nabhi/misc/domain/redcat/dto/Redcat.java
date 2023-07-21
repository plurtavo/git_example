package mx.com.prosa.nabhi.misc.domain.redcat.dto;

import com.google.gson.GsonBuilder;

import java.io.Serializable;
import java.util.Date;

public class Redcat implements Serializable {

    private static final long serialVersionUID = 23453246534L;

    private int id;
    private String fiid;
    private String termId;
    private int cout1;
    private int cout2;
    private int cout3;
    private int cout4;
    private int cout5;
    private int cout6;
    private int end1;
    private int end2;
    private int end3;
    private int end4;
    private int end5;
    private int end6;
    private int denomination1;
    private int denomination2;
    private int denomination3;
    private int denomination4;
    private int denomination5;
    private int denomination6;
    private String currency1;
    private String currency2;
    private String currency3;
    private String currency4;
    private String currency5;
    private String currency6;
    private int remanenteUSD;
    private int remanenteMXN;
    private int totalOutUSD;
    private int totalOutMXN;
    private int tlfUSD;
    private int tlfMXN;
    private int differenceUSD;
    private int differenceMXN;
    private Date startDate;
    private Date endDate;
    private String coutType;

    public Redcat() {
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

    public int getTlfUSD() {
        return tlfUSD;
    }

    public void setTlfUSD(int tlfUSD) {
        this.tlfUSD = tlfUSD;
    }

    public int getTlfMXN() {
        return tlfMXN;
    }

    public void setTlfMXN(int tlfMXN) {
        this.tlfMXN = tlfMXN;
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
