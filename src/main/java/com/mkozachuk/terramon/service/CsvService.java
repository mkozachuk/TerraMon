package com.mkozachuk.terramon.service;

import com.mkozachuk.terramon.model.Exportable;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class CsvService {

    @Getter
    @Value("${terramon.CSVExporterPath}")
    String exportPath;
    @Value("${terramon.CSVExporterDateFormat}")
    String dateFormat;

    public String exportToCsv(List<? extends Exportable> listForExport) throws IOException {
        String[] csvHeader = listForExport.get(0).getTableHeaders();
        String[] nameMapping = listForExport.get(0).getNameMapping();

        DateFormat dateFormatter = new SimpleDateFormat(dateFormat);
        String currentDateTime = dateFormatter.format(new Date());

        String filename = currentDateTime + ".csv";

        FileWriter writer = new FileWriter(exportPath + filename);

        ICsvBeanWriter csvWriter = new CsvBeanWriter(writer, CsvPreference.STANDARD_PREFERENCE);

        csvWriter.writeHeader(csvHeader);

        for (Object obj : listForExport) {
            csvWriter.write(obj, nameMapping);
        }
        csvWriter.flush();
        csvWriter.close();

        return filename;
    }
}
