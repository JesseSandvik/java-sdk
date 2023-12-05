package com.blckroot.sdk.output;

import com.blckroot.sdk.csv.CSVPrinter;
import com.blckroot.sdk.json.JSONService;
import com.blckroot.sdk.string.FormattedTableBuilder;

import java.io.PrintStream;
import java.util.List;

public enum OutputFormat {
    CSV {
        @Override
        public void print(Output output) {
            CSVPrinter csvPrinter = new CSVPrinter();
            csvPrinter.setHeaders(output.getHeaders());
            output.getRecords().forEach(csvPrinter::addRecord);
            csvPrinter.print(output.getPrintStream());
        }
    },
    JSON {
        @Override
        public void print(Output output) {
            JSONService.printFormattedJSON(output.getPrintStream(), output.getData());
        }
    },
    TABLE {
        @Override
        public void print(Output output) {
            FormattedTableBuilder formattedTableBuilder = output.getFormattedTableBuilder();
            formattedTableBuilder.setHeaders(List.of(output.getHeaders()));
            output.getRecords().forEach(formattedTableBuilder::addRecord);
            PrintStream printStream = output.getPrintStream();
            printStream.println(formattedTableBuilder.build());
        }
    };

    public abstract void print(Output output);
}
