package com.blckroot.sdk.file.system.executor;

import java.io.*;
import java.util.List;

class FileSystemExecutorUtility implements FileSystemExecutorContract {
    @Override
    public Integer executeCommand(List<String> command, PrintStream printStream) {
        try {
            ProcessBuilder processBuilder = new ProcessBuilder();
            processBuilder.command(command);
            Process process = processBuilder.start();

            InputStream inputStream = process.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                printStream.println(line);
            }
            process.waitFor();
            return process.exitValue();

        } catch (RuntimeException | InterruptedException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}
