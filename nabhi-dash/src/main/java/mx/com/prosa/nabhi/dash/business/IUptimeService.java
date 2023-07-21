package mx.com.prosa.nabhi.dash.business;

import mx.com.prosa.nabhi.dash.model.uptime.ReportSearchConditions;
import mx.com.prosa.nabhi.dash.model.uptime.UptimeReport;
import mx.com.prosa.nabhi.dash.report.BookJsonUptime;
import mx.com.prosa.nabhi.misc.exception.dash.UptimeException;

import java.io.ByteArrayOutputStream;


public interface IUptimeService {

    UptimeReport getReportRaw( ReportSearchConditions reportSearchConditions ) throws UptimeException;

    ByteArrayOutputStream getReportRawExcel( ReportSearchConditions reportSearchConditions ) throws UptimeException;

    ByteArrayOutputStream getReportExcel( ReportSearchConditions reportSearchConditions ) throws UptimeException;

    BookJsonUptime getReport( ReportSearchConditions reportSearchConditions ) throws UptimeException;
}
