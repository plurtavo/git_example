package mx.com.prosa.nabhi.dash.redcat.report;


import mx.com.prosa.nabhi.misc.domain.redcat.dto.Redcat;

import java.util.List;

public class SheetJsonRedcat {

    private String sheetName;
    private String[] columnNames;
    private List < Redcat > redcat;

    public SheetJsonRedcat(String sheetName, String[] columnNames, List<Redcat> redcat) {
        this.sheetName = sheetName;
        this.columnNames = columnNames;
        this.redcat = redcat;
    }

    public SheetJsonRedcat() {
    }

    public String getSheetName() {
        return sheetName;
    }

    public void setSheetName(String sheetName) {
        this.sheetName = sheetName;
    }

    public String[] getColumnNames() {
        return columnNames;
    }

    public void setColumnNames(String[] columnNames) {
        this.columnNames = columnNames;
    }

    public List<Redcat> getRedcat() {
        return redcat;
    }

    public void setRedcat(List<Redcat> redcat) {
        this.redcat = redcat;
    }
}
