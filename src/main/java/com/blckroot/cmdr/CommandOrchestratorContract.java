package com.blckroot.cmdr;

interface CommandOrchestratorContract {
    Integer execute(String[] arguments) throws Exception;
}
