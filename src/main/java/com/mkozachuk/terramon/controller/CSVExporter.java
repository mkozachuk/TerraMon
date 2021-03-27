package com.mkozachuk.terramon.controller;

import com.mkozachuk.terramon.model.TerraData;
import org.springframework.stereotype.Component;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Component
public class CSVExporter {

    public String exportToCSV(List listToExport) throws IOException {
        String[] csvHeader;
        String[] nameMapping;

        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
        String currentDateTime = dateFormatter.format(new Date());

        FileWriter writer = new FileWriter("/home/pi/csvs/" + currentDateTime + ".csv");


        ICsvBeanWriter csvWriter = new CsvBeanWriter(writer, CsvPreference.STANDARD_PREFERENCE);

        boolean isTerraData = listToExport.get(0) instanceof TerraData;
        if (isTerraData) {
            csvHeader = new String[]{"ID", "Temperature1", "Temperature2", "Humidity", "Add Date"};
            nameMapping = new String[]{"id", "temperature", "temperatureFromHumiditySensor", "humidity", "addAt"};

        } else {
            csvHeader = new String[]{"ID", "Add Date", "Title", "Text"};
            nameMapping = new String[]{"id", "addAt", "title", "text"};
        }

        csvWriter.writeHeader(csvHeader);

        for (Object obj : listToExport) {
            csvWriter.write(obj, nameMapping);
        }
        csvWriter.flush();
        csvWriter.close();

        return currentDateTime + ".csv";
    }
}
