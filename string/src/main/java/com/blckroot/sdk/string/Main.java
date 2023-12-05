package com.blckroot.sdk.string;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        FormattedTableBuilder formattedTableBuilder = new FormattedTableBuilder();
        formattedTableBuilder.setMaxColumnWidth(20);
        formattedTableBuilder
                .addHeader("HeaderA")
                .addHeader("HeaderB")
                .addHeader("HeaderC")
                .addHeader("HeaderD")
                .addHeader("HeaderE")
                .addHeader("HeaderF")
                .addHeader("HeaderG");

        String[] record = new String[]{
                "ValueA",
                "THIS IS FOR VALUE B TO MAKE THE STRING PARTICULARLY LONG, PARTICULARLY LONG, PARTICULARLY LONG",
                "ValueC",
                "THIS IS A FOR VALUR D TO MAKE THE STRING PARTICULARLY LONG"
        };
        formattedTableBuilder
                .addRecord(List.of(record))
                .addRecord(List.of(record))
                .addRecord(List.of(record))
                .addRecord(List.of(record))
                .addRecord(List.of(record))
                .addRecord(List.of(record));

        System.out.print(formattedTableBuilder.build());
    }
}
