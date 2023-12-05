package com.blckroot.sdk.string;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FormattedTableBuilder {
    private String contentAlignment = "left";
    private int rowHeight = 1;
    private int maxColumnWidth = 80;
    private int cellPadding = 2;
    private String verticalSeparator = "|";
    private String horizontalSeparator = "-";
    private String cellSeparator = "|";
    private List<String> headers = new ArrayList<>();
    private List<List<String>> records = new ArrayList<>();
    private final Map<Integer, Integer> tableGrid = new HashMap<>();
    private final StringBuilder table = new StringBuilder();

    public String getContentAlignment() {
        return contentAlignment;
    }

    public FormattedTableBuilder setContentAlignment(String contentAlignment) {
        this.contentAlignment = contentAlignment;
        return this;
    }

    public int getRowHeight() {
        return rowHeight;
    }

    public FormattedTableBuilder setRowHeight(int rowHeight) {
        this.rowHeight = rowHeight;
        return this;
    }

    public int getMaxColumnWidth() {
        return maxColumnWidth;
    }

    public FormattedTableBuilder setMaxColumnWidth(int maxColumnWidth) {
        this.maxColumnWidth = maxColumnWidth;
        return this;
    }

    public int getCellPadding() {
        return cellPadding;
    }

    public FormattedTableBuilder setCellPadding(int cellPadding) {
        this.cellPadding = cellPadding;
        return this;
    }

    public String getVerticalSeparator() {
        return verticalSeparator;
    }

    public FormattedTableBuilder setVerticalSeparator(String verticalSeparator) {
        this.verticalSeparator = verticalSeparator;
        return this;
    }

    public String getHorizontalSeparator() {
        return horizontalSeparator;
    }

    public FormattedTableBuilder setHorizontalSeparator(String horizontalSeparator) {
        this.horizontalSeparator = horizontalSeparator;
        return this;
    }

    public String getCellSeparator() {
        return cellSeparator;
    }

    public FormattedTableBuilder setCellSeparator(String cellSeparator) {
        this.cellSeparator = cellSeparator;
        return this;
    }

    public List<String> getHeaders() {
        return headers;
    }

    public FormattedTableBuilder addHeader(String header) {
        headers.add(header);
        return this;
    }

    public FormattedTableBuilder setHeaders(List<String> headers) {
        if (this.headers.isEmpty()) {
            this.headers = headers;
        } else {
            headers.forEach(this::addHeader);
        }
        return this;
    }

    public List<List<String>> getRecords() {
        return records;
    }

    public FormattedTableBuilder addRecord(List<String> record) {
        records.add(record);
        return this;
    }

    public FormattedTableBuilder setRecords(List<List<String>> records) {
        if (this.records.isEmpty()) {
            this.records = records;
        } else {
            records.forEach(this::addRecord);
        }
        return this;
    }

    private void addFormattedTableRow(Integer length) {
        for (int i = 0; i < length; i+=1) {
            if (i == 0) {
                table.append(verticalSeparator);
            }
            addHorizontalTableSeparator(tableGrid.get(i) + cellPadding);
            table.append(verticalSeparator);
        }
    }

    private void addHorizontalTableSeparator(Integer count) {
        table.append(horizontalSeparator.repeat(Math.max(0, count)));
    }

    private void addNewLine(Integer count) {
        table.append("\n".repeat(Math.max(0, count)));
    }

    private void addWhitespace(Integer count) {
        table.append(" ".repeat(Math.max(0, count)));
    }

    private String truncateCell(String cellContent) {
        if (cellContent.length() > maxColumnWidth) {
            return cellContent.substring(0, maxColumnWidth - 3) + "...";
        }
        return cellContent;
    }

    private void setTableGrid(List<String> headers, List<List<String>> records) {
        for (int i = 0; i < headers.size(); i+=1) {
            tableGrid.put(i, 0);
        }

        for (int i = 0; i < headers.size(); i+=1) {
            if (headers.get(i).length() > tableGrid.get(i)) {
                int headerLength = headers.get(0).length();
                if (headers.get(i).length() > maxColumnWidth) {
                    headerLength = maxColumnWidth;
                }
                tableGrid.put(i, headerLength);
            }
        }

        records.forEach(record -> {
            for (int i = 0; i < record.size(); i+=1) {
                if (record.get(i).length() > tableGrid.get(i)) {
                    int recordWidth = record.get(i).length();
                    if (recordWidth > maxColumnWidth) {
                        recordWidth = maxColumnWidth;
                    }
                    tableGrid.put(i, recordWidth);
                }
            }
        });

        for (int i = 0; i < headers.size(); i+=1) {
            if (tableGrid.get(i) % 2 != 0) {
                tableGrid.put(i, tableGrid.get(i) + 1);
            }
        }
    }

    private void buildHeader() {
        addNewLine(2);
        addFormattedTableRow(headers.size());
        addNewLine(1);

        for (int i = 0; i < headers.size(); i+=1) {
            buildCell(headers.get(i), i);
        }

        addNewLine(1);
        addFormattedTableRow(headers.size());
    }

    private void buildRecords() {
        records.forEach(record -> {
            addNewLine(rowHeight);
            for (int i = 0; i < record.size(); i+=1) {
                buildCell(record.get(i), i);
            }
        });

        addNewLine(1);
        addFormattedTableRow(headers.size());
        addNewLine(2);
    }

    private Integer getCellPadding(Integer cellIndex, Integer contentCharacterCount) {
        if (contentCharacterCount % 2 != 0) {
            contentCharacterCount+=1;
        }

        if (contentCharacterCount < tableGrid.get(cellIndex)) {
            return cellPadding + (tableGrid.get(cellIndex) - contentCharacterCount);
        }
        return cellPadding;
    }

    private void buildCell(String rawCellData, Integer cellIndex) {
        String cellContent = rawCellData
                .replace("\t", "")
                .replace("\n", "");

        cellContent = truncateCell(cellContent);
        int cellPadding = getCellPadding(cellIndex, cellContent.length());

        if (cellIndex == 0) {
            table.append(cellSeparator);
        }

        if (contentAlignment.equalsIgnoreCase("left")) {
            addWhitespace(1);
        }

        if (contentAlignment.equalsIgnoreCase("center")) {
            addWhitespace(cellPadding / 2);
        }

        if (contentAlignment.equalsIgnoreCase("right")) {
            addWhitespace(cellPadding - 1);
        }

        table.append(cellContent);

        if (cellContent.length() % 2 != 0) {
            addWhitespace(1);
        }

        if (contentAlignment.equalsIgnoreCase("left")) {
            addWhitespace(cellPadding - 1);
        }

        if (contentAlignment.equalsIgnoreCase("center")) {
            addWhitespace(cellPadding / 2);
        }

        if (contentAlignment.equalsIgnoreCase("right")) {
            addWhitespace(1);
        }

        table.append(cellSeparator);
    }

    public String build() {
        setTableGrid(headers, records);
        buildHeader();
        buildRecords();
        return table.toString();
    }
}
