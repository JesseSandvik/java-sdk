package com.blckroot.sdk.output;

import com.blckroot.sdk.string.FormattedTableBuilder;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

public class Output {
    private OutputFormat outputFormat = OutputFormat.TABLE;
    private FormattedTableBuilder formattedTableBuilder = new FormattedTableBuilder();
    private PrintStream printStream = System.out;
    private Object data;
    private String[] headers;
    private List<List<String>> records = new ArrayList<>();

    public OutputFormat getOutputFormat() {
        return outputFormat;
    }

    public Output setOutputFormat(OutputFormat outputFormat) {
        this.outputFormat = outputFormat;
        return this;
    }

    public FormattedTableBuilder getFormattedTableBuilder() {
        return formattedTableBuilder;
    }

    public Output setFormattedTableBuilder(FormattedTableBuilder formattedTableBuilder) {
        this.formattedTableBuilder = formattedTableBuilder;
        return this;
    }

    public PrintStream getPrintStream() {
        return printStream;
    }

    public Output setPrintStream(PrintStream printStream) {
        this.printStream = printStream;
        return this;
    }

    public Object getData() {
        return data;
    }

    public Output setData(Object data) {
        this.data = data;
        return this;
    }

    public String[] getHeaders() {
        return headers;
    }

    public Output setHeaders(String[] headers) {
        this.headers = headers;
        return this;
    }

    public List<List<String>> getRecords() {
        return records;
    }

    public Output addRecord(List<String> record) {
        records.add(record);
        return this;
    }

    public Output setRecords(List<List<String>> records) {
        if (this.records.isEmpty()) {
            this.records = records;
        } else {
            records.forEach(this::addRecord);
        }
        return this;
    }

    public void print() {
        outputFormat.print(this);
    }
}
