package com.example.stimulsoftdemo;

import java.awt.Dimension;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stimulsoft.base.StiJsonSaveMode;
import com.stimulsoft.base.drawing.StiColorEnum;
import com.stimulsoft.base.drawing.StiSolidBrush;
import com.stimulsoft.base.drawing.enums.StiBorderSides;
import com.stimulsoft.base.drawing.enums.StiTextHorAlignment;
import com.stimulsoft.base.exception.StiExceptionProvider;
import com.stimulsoft.base.json.JSONException;
import com.stimulsoft.base.licenses.StiLicense;
import com.stimulsoft.base.system.StiFont;
import com.stimulsoft.base.system.StiFontStyle;
import com.stimulsoft.base.system.geometry.StiRectangle;
import com.stimulsoft.report.StiNameCreation;
import com.stimulsoft.report.StiReport;
import com.stimulsoft.report.components.StiPage;
import com.stimulsoft.report.components.bands.StiDataBand;
import com.stimulsoft.report.components.bands.StiHeaderBand;
import com.stimulsoft.report.components.simplecomponents.StiImage;
import com.stimulsoft.report.components.simplecomponents.StiText;
import com.stimulsoft.report.dictionary.StiDataColumn;
import com.stimulsoft.report.dictionary.StiDataColumnsCollection;
import com.stimulsoft.report.dictionary.StiDictionary;
import com.stimulsoft.report.dictionary.dataSources.StiDataTableSource;
import com.stimulsoft.report.dictionary.databases.StiXmlDatabase;
import com.stimulsoft.report.saveLoad.StiDocument;
import com.stimulsoft.report.utils.data.StiDataColumnsUtil;
import com.stimulsoft.report.utils.data.StiSqlField;
import com.stimulsoft.report.utils.data.StiXmlTable;
import com.stimulsoft.report.utils.data.StiXmlTableFieldsRequest;
import com.stimulsoft.samples.CreateReport;
import com.stimulsoft.viewer.StiViewerFx;
import com.stimulsoft.viewer.events.StiViewCommonEvent;
import org.springframework.web.bind.annotation.*;

/**
 * Sample demonstrate how to create report Copyright Stimulsoft
 */
//@RestController
//@CrossOrigin("*")
public class CreatingReportAtRuntime extends JPanel {

    private static final long serialVersionUID = 330448692680237867L;
    private static final Dimension FRAME_SIZE = new Dimension(800, 800);


    public CreatingReportAtRuntime(final JFrame parentFrame) throws FileNotFoundException, JSONException, JsonProcessingException {
//    @PostMapping("/good/report")
//    public ResponseEntity<?> creatingReportAtRuntime1(@RequestBody Object o) throws FileNotFoundException, JSONException, JsonProcessingException {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setPreferredSize(FRAME_SIZE);
        StiViewerFx viewerPanel = new StiViewerFx(parentFrame);
        add(viewerPanel);

        StiReport report = new StiReport();

        StiPage page = new StiPage(report);
        report.getPages().add(page);
        page.setName(StiNameCreation.createName(report, StiNameCreation.generateName(page)));
        String xsdPath = "Data/Demo.xsd";
        StiXmlDatabase xmlDatabase = new StiXmlDatabase("Demo", xsdPath, "Data/Demo.xml");
        report.setDictionary(new StiDictionary(report));
        report.getDictionary().getDatabases().add(xmlDatabase);

        StiXmlTableFieldsRequest tables = StiDataColumnsUtil.parceXSDSchema(new FileInputStream(xsdPath));
        StiDataTableSource tableSource = null;
        for (StiXmlTable table : tables.getTables()) {
            if (table.getName().equals("Categories")) {
                tableSource = new StiDataTableSource("Demo." + table.getName(), table.getName(), table.getName());
                tableSource.setColumns(new StiDataColumnsCollection());
                for (StiSqlField field : table.getColumns()) {
                    StiDataColumn column = new StiDataColumn(field.getName(), field.getName(), field.getSystemType());
                    tableSource.getColumns().add(column);
                }
                tableSource.setDictionary(report.getDictionary());
                report.getDictionary().getDataSources().add(tableSource);
            }
        }

        // Create TitleBand
        StiHeaderBand titleBand = new StiHeaderBand();
        titleBand.setHeight(0.85);
        titleBand.setName("TitleBand");
        page.getComponents().add(titleBand);

        // Create Title text on header
        StiText headerText = new StiText(new StiRectangle(0, 0, page.getWidth(), 0.85));
        headerText.setTextInternal("Tacticdescription");
        headerText.setHorAlignment(StiTextHorAlignment.Left);

        headerText.setName("TitleHeader");
        headerText.setFont(new StiFont("Arial", 12F, StiFontStyle.Bold));
        titleBand.getComponents().add(headerText);

        // Create HeaderBand
        StiHeaderBand headerBand = new StiHeaderBand();
        headerBand.setHeight(0.5);
        headerBand.setName("HeaderBand");
        page.getComponents().add(headerBand);

        // Create Databand
        StiDataBand dataBand = new StiDataBand();
        dataBand.setDataSourceName("Categories");
        dataBand.setHeight(0.5);
        dataBand.setName("DataBand");
        page.getComponents().add(dataBand);

        double pos = 0;
        double columnWidth = page.getWidth() / tableSource.getColumns().size();
        Integer nameIndex = 1;
        for (StiDataColumn dataColumn : tableSource.getColumns()) {
            // Create text on header
            StiText hText = new StiText(new StiRectangle(pos, 0, columnWidth, 0.5));

            hText.setTextInternal(dataColumn.getName());
            hText.setHorAlignment(StiTextHorAlignment.Center);
            hText.setName("HeaderText" + nameIndex.toString());
            hText.setBrush(new StiSolidBrush(StiColorEnum.Orange.color()));
            hText.getBorder().setSide(StiBorderSides.All);
            headerBand.getComponents().add(hText);

            if (dataColumn.getName().equals("Picture")) {
                StiImage dataImage = new StiImage(new StiRectangle(pos, 0, columnWidth, 0.5));
                dataImage.setDataColumn("Categories." + dataColumn.getName());
                dataImage.setName("DataImage" + nameIndex.toString());
                dataImage.getBorder().setSide(StiBorderSides.All);
                dataBand.getComponents().add(dataImage);
            } else {
                StiText dataText = new StiText(new StiRectangle(pos, 0, columnWidth, 0.5));
                dataText.setText("{Categories." + dataColumn.getName() + "}");
                dataText.setName("DataText" + nameIndex.toString());
                dataText.getBorder().setSide(StiBorderSides.All);
                dataBand.getComponents().add(dataText);
            }
            pos = pos + columnWidth;
            nameIndex++;
        }

        report.Render();
        ObjectMapper mapper = new ObjectMapper();
        Object reportFileContent = mapper.readValue(report.saveToJsonInternal(StiJsonSaveMode.Report), Map.class);
        Map map = new HashMap();
        map.put("reportFile", reportFileContent);
//
//        return new ResponseEntity<>(map, HttpStatus.OK);

        viewerPanel.getStiViewModel().getEventDispatcher()
                   .dispatchStiEvent(new StiViewCommonEvent(StiViewCommonEvent.DOCUMENT_FILE_LOADED, new StiDocument(report), null));

    }

    public static void main(final String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {

                com.stimulsoft.base.licenses.StiLicense.setKey(

                        "6vJhGtLLLz2GNviWmUTrhSqnOItdDwjBylQzQcAOiHmdVlQak8bQq2qNxpmgTY0FXgcdN4b6ZJI7vUKHxLxtKwvhqz" +
                        "oXfmd+TtSYmu3qPVHPVbsbqcjV8vQXucJniGHJ4BtO5lxjJpHzXCSGn/M1fvvWIO2nfiWHoyQBiHlqxYfsNaxBUm6z" +
                        "L5RFc7Xory2cPJwA1r+3aeHEu4VUrWUD9l7ubfPzKPrFnc6H3znf0L1y0Cg/YrIXfc/pt1iZ6W39bQnu9vPZjlbi08" +
                        "+OrMOAK06QC2JdTyMfKeBvX4t/YsYI79O4x1nuBkxHCHdGET+hhNI9+2hfh0C+7QbxSNBYz9rRWQXQKtoeohEKGOPZ" +
                        "2gYj5dNwQa9ybVbNyC3Vtw2t2MB7dVwbaRsogAVfs+SoiUoatptgc9PBsmYdVd5xOoMEU4ieMdXaEOvcbiPZHiMdXc" +
                        "HYAZB02v9QdA/AkN68e6XL6ujqy2KSOs4CMiU2CRGGe/RaVhEH4rjGSyljd2/lheV/XkIAYe6cmwJsBHqb1Bw8jgUe" +
                        "S6kn0iVqYF9ykegQgc5upp1xVCemQxt7nYVcl2dGZLjmThHzzAzmyu3N3Q==");

                try {
                    JFrame frame = new JFrame();
                    frame.add(new CreatingReportAtRuntime(frame));
                    frame.add(new CreateReport(frame));

                    frame.setSize(FRAME_SIZE);
                    frame.setLocationRelativeTo(null);
                    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    frame.setVisible(true);
                } catch (Throwable e) {
                    StiExceptionProvider.show(e, null);
                }
            }
        });
    }
}
