package com.blckroot.sdk.csv;

import org.apache.commons.csv.CSVFormat;

import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

public class CSVPrinter {
    private String[] headers;
    private List<List<String>> records = new ArrayList<>();

    public void setHeaders(String[] headers) {
        this.headers = headers;
    }

    public void addRecord(List<String> record) {
        this.records.add(record);
    }

    public void setRecords(List<List<String>> records) {
        if (records.isEmpty()) {
            this.records = records;
        } else {
            records.forEach(this::addRecord);
        }
    }

    public void print(PrintStream printStream) {
        try {
            CSVFormat csvFormat = CSVFormat.DEFAULT
                    .builder()
                    .setHeader(headers)
                    .build();

            org.apache.commons.csv.CSVPrinter csvPrinter =
                    new org.apache.commons.csv.CSVPrinter(printStream, csvFormat);

            records.forEach(record -> {
                try {
                    csvPrinter.printRecord(record);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
