package com.blckroot.cmd.command;

import com.blckroot.cmd.filesystem.FileSystemService;
import com.blckroot.cmd.option.Option;
import com.blckroot.cmd.positionalParameter.PositionalParameter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

public class ExecutableCommand extends Command implements ExecutableCommandContract {
    private final String executableFilePath;
    private final List<ExecutableCommand> executableSubcommands = new ArrayList<>();
    private final List<String> arguments = new ArrayList<>();

    public ExecutableCommand(String name, String executableFilePath) {
        super(name);
        this.executableFilePath = executableFilePath;
    }

    public String getExecutableFilePath() {
        return this.executableFilePath;
    }

    @Override
    public List<ExecutableCommand> getExecutableSubcommands() {
        return this.executableSubcommands;
    }

    @Override
    public void addExecutableSubcommand(ExecutableCommand executableSubcommand) {
        this.executableSubcommands.add(executableSubcommand);
    }

    @Override
    public Integer call() throws Exception {
        System.out.println(executableFilePath);
        FileSystemService fileSystemService = new FileSystemService();

        if (fileSystemService.fileExists(executableFilePath) && fileSystemService.fileCanExecute(executableFilePath)) {
            if (System.getProperty("os.name").toLowerCase().contains("windows")) {
                String bashExecutablePath = "C:\\Program Files\\Git\\bin\\bash.exe";
                String shExecutablePath = "C:\\Program Files\\Git\\bin\\sh.exe";
                if (fileSystemService.fileExists(bashExecutablePath) &&
                fileSystemService.fileCanExecute(bashExecutablePath)) {
                    this.arguments.add(bashExecutablePath);
                } else if (fileSystemService.fileExists(shExecutablePath) &&
                        fileSystemService.fileCanExecute(shExecutablePath)) {
                    this.arguments.add(shExecutablePath);
                }
            }
            this.arguments.add(executableFilePath);

            if (!this.getPositionalParameters().isEmpty()) {
                setPositionalParameterArguments();
            }

            if (!this.getOptions().isEmpty()) {
                setOptionArguments();
            }

            return execute(arguments);
        }
        return 0;
    }

    private void setPositionalParameterArguments() {
        for (PositionalParameter positionalParameter : this.getPositionalParameters()) {
            if (positionalParameter.getValue() != null) {
                this.arguments.add(positionalParameter.getValue().toString());
            }
        }
    }

    private void setOptionArguments() {
        for (Option option : this.getOptions()) {
            if (option.getValue() != null) {
                if (option.getParameterLabel() == null) {
                    addArgumentForOptionTypeBoolean(option);
                } else {
                    addArgumentsForOptionTypeString(option);
                }
            }
        }
    }

    private void addArgumentForOptionTypeBoolean(Option option) {
        arguments.add(option.getLongestName());
    }

    private void addArgumentsForOptionTypeString(Option option) {
        arguments.add(option.getLongestName());
        arguments.add(option.getValue().toString());
    }

    private Integer execute(List<String> arguments) throws IOException, InterruptedException {
        ProcessBuilder processBuilder = new ProcessBuilder();
        processBuilder.command(arguments);
        Process process = processBuilder.start();

        InputStream inputStream = process.getInputStream();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

        String line;
        while ((line = bufferedReader.readLine()) != null) {
            System.out.println(line);
        }
        process.waitFor();
        return process.exitValue();
    }
}
