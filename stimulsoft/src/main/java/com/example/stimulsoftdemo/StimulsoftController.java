package com.example.stimulsoftdemo;

import com.stimulsoft.base.licenses.StiLicense;
import com.stimulsoft.base.serializing.StiDeserializationException;
import com.stimulsoft.report.StiReport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.xml.sax.SAXException;
import com.stimulsoft.report.StiSerializeManager;


import javax.servlet.ServletContext;
import java.io.File;
import java.io.IOException;

import static com.stimulsoft.report.enums.StiComponentId.StiReport;

@RestController
@RequestMapping("/Viewer")
@CrossOrigin("*")
public class StimulsoftController {

//    @Value("${stimulsoft.license}")
    private String licenseKey = "";

    @Autowired
    ServletContext context;

    @PostMapping("/{action}")
    public String handleAction(@PathVariable String action) throws StiDeserializationException, IOException, SAXException {
        // Set the license key
        StiLicense.setKey(licenseKey);



        switch (action) {
            case "InitViewer":
                // Load report template
                String reportPath = context.getRealPath("/reports/Dashboards.mrt");

		        StiReport report = StiSerializeManager.deserializeReport(new File(reportPath));

                return report.saveDocumentToString();

            // Initialize the viewer
            // This could involve loading a report file from your server
            case "GetReport":

            case "Interaction":
                // Handle interactions with the report

                // This might involve processing hyperlinks or drill-down actions in the report
                break;

            case "ExportReport":
                // Export the report to a different format
                // This could involve rendering the report to PDF, Excel, Word, etc.
                break;

            case "PrintReport":
                // Print the report
                // This might involve rendering the report to a printable format and sending it to a printer
                break;

            case "LoadReport":
                // Load a report
                // This could involve loading a report file from your server
                break;

            default:
                throw new IllegalArgumentException("Invalid action: " + action);
        }

        return "{}"; // This is a placeholder; replace it with your actual response
    }
}
