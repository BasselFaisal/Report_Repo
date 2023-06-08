package com.example.stimulsoftdemo;

import java.io.File;
import java.io.InputStream;

import com.stimulsoft.webviewer.StiWebViewer;
//import jakarta.servlet.ServletContext;
//import jakarta.servlet.http.HttpServletResponse;

import com.stimulsoft.base.licenses.StiLicense;
import com.stimulsoft.report.enums.StiExportFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.stimulsoft.report.StiReport;
import com.stimulsoft.report.StiSerializeManager;
import com.stimulsoft.webviewer.StiWebViewerOptions;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;

@Controller
public class MyController {

	@Autowired
	ServletContext context;

	@GetMapping("/")
	public String indexAction( Model model ) throws Exception {

		String reportPath = context.getRealPath("/reports/Dashboards.mrt");
		StiReport report = StiSerializeManager.deserializeReport(new File(reportPath));
		report.render();
		StiWebViewerOptions options = new StiWebViewerOptions();

		model.addAttribute("reportPath", reportPath);
		model.addAttribute("report", report);
		model.addAttribute("options", options);

		return "index";
	}

	@GetMapping("/a")
	public String indexActions( Model model ) throws Exception {

		com.stimulsoft.base.licenses.StiLicense.setKey("6vJhGtLLLz2GNviWmUTrhSqnOItdDwjBylQzQcAOiHlkHnETZDQa/PS+0KAqyGT4DpRlgFmGegaxKasr/6hj3WTsNs" +
				"zXi2AnvR96edDIZl0iQK5oAkmli4CDUblYqrhiAJUrUZtKyoZUOSwbjhyDdjuqCk8reDn/QTemFDwWuF4BfzOqXcdV" +
				"9ceHmq8jqTiwrgF4Bc35HGUqPq+CnYqGQhfU3YY44xsR5JaAuLAXvuP05Oc6F9BQhBMqb6AUXjeD5T9OJWHiIacwv0" +
				"LbxJAg5a1dVBDPR9E+nJu2dNxkG4EcLY4nf4tOvUh7uhose6Cp5nMlpfXUnY7k7Lq9r0XE/b+q1f11KCXK/t0GpGNn" +
				"PL5Xy//JCUP7anSZ0SdSbuW8Spxp+r7StU/XLwt9vqKf5rsY9CN8D8u4Mc8RZiSXceDuKyhQo72Eu8yYFswP9COQ4l" +
				"gOJGcaCv5h9GwR+Iva+coQENBQyY2dItFpsBwSAPvGs2/4V82ztLMsmkTpoAzYupvE2AoddxArDjjTMeyKowMI6qtT" +
				"yhaF9zTnJ7X7gs09lgTg7Hey5I1Q66QFfcwK");

		String reportPath = context.getRealPath("/reports/Dashboards.mrt");
		StiReport report = StiSerializeManager.deserializeReport(new File(reportPath));

		report.render();
//		StiWebViewer options = new StiWebViwerOptions();
//
//		model.addAttribute("reportPath", reportPath);
//		model.addAttribute("report", report);
//		model.addAttribute("options", options);
//		StiReportViewer viewer = new StiReportViewer();
//
//		// Set the report viewer properties.
//		viewer.Report = report;
//		viewer.Width = 800;
//		viewer.Height = 600;
		return "index";
	}

//
	@GetMapping("/report")
	public void getReport(HttpServletResponse response) {
		try {
			// Set the license key
			StiLicense.setKey("6vJhGtLLLz2GNviWmUTrhSqnOItdDwjBylQzQcAOiHmdVlQak8bQq2qNxpmgTY0FXgcdN4b6ZJI7vUKHxLxtKwvhqz" +
					"oXfmd+TtSYmu3qPVHPVbsbqcjV8vQXucJniGHJ4BtO5lxjJpHzXCSGn/M1fvvWIO2nfiWHoyQBiHlqxYfsNaxBUm6z" +
					"L5RFc7Xory2cPJwA1r+3aeHEu4VUrWUD9l7ubfPzKPrFnc6H3znf0L1y0Cg/YrIXfc/pt1iZ6W39bQnu9vPZjlbi08" +
					"+OrMOAK06QC2JdTyMfKeBvX4t/YsYI79O4x1nuBkxHCHdGET+hhNI9+2hfh0C+7QbxSNBYz9rRWQXQKtoeohEKGOPZ" +
					"2gYj5dNwQa9ybVbNyC3Vtw2t2MB7dVwbaRsogAVfs+SoiUoatptgc9PBsmYdVd5xOoMEU4ieMdXaEOvcbiPZHiMdXc" +
					"HYAZB02v9QdA/AkN68e6XL6ujqy2KSOs4CMiU2CRGGe/RaVhEH4rjGSyljd2/lheV/XkIAYe6cmwJsBHqb1Bw8jgUe" +
					"S6kn0iVqYF9ykegQgc5upp1xVCemQxt7nYVcl2dGZLjmThHzzAzmyu3N3Q==");

			// Create a new report
			StiReport report = new StiReport();
			
			// Load the report template
			InputStream reportStream = getClass().getResourceAsStream("/reports/myReport.mrt");
//			report.load

			// Render the report
			report.render();

			// Export the report to PDF and write it to the response
//			byte[] reportBytes = report.exportDocument(StiExportFormat.Pdf);
			response.setContentType("application/pdf");
//			response.getOutputStream().write(reportBytes);
		} catch (Exception e) {
			throw new RuntimeException("Error generating report", e);
		}
	}

}
