package com.mkozachuk.terramon.service;

import com.mkozachuk.terramon.model.Exportable;
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

    @Value("${terramon.CSVExporterPath}")
    String exportPath;
    @Value("${terramon.CSVExporterDateFormat}")
    String dateFormat;

    public String exportToCsv(List<? extends Exportable> listToExport) throws IOException {
        String[] csvHeader = listToExport.get(0).getTableHeaders();
        String[] nameMapping = listToExport.get(0).getNameMapping();

        DateFormat dateFormatter = new SimpleDateFormat(dateFormat);
        String currentDateTime = dateFormatter.format(new Date());

        String filename = currentDateTime + ".csv";

        FileWriter writer = new FileWriter(exportPath + filename);

        ICsvBeanWriter csvWriter = new CsvBeanWriter(writer, CsvPreference.STANDARD_PREFERENCE);

        csvWriter.writeHeader(csvHeader);

        for (Object obj : listToExport) {
            csvWriter.write(obj, nameMapping);
        }
        csvWriter.flush();
        csvWriter.close();

        return filename;
    }
}
