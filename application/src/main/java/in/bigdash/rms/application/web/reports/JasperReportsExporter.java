package in.bigdash.rms.application.web.reports;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.export.Exporter;


public interface JasperReportsExporter {


	public void export(JasperPrint jp, String fileName, HttpServletResponse response) throws JRException, IOException;

}