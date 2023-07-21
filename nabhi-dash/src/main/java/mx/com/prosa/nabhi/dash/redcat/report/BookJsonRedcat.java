package mx.com.prosa.nabhi.dash.redcat.report;

import java.util.List;

public class BookJsonRedcat {

    private List< SheetJsonRedcat > sheetJsonRedcats;

    public BookJsonRedcat(List<SheetJsonRedcat> sheetJsonRedcats) {
        this.sheetJsonRedcats = sheetJsonRedcats;
    }

    public BookJsonRedcat() {
    }

    public List<SheetJsonRedcat> getSheetJsonRedcats() {
        return sheetJsonRedcats;
    }

    public void setSheetJsonRedcats(List<SheetJsonRedcat> sheetJsonRedcats) {
        this.sheetJsonRedcats = sheetJsonRedcats;
    }
}
