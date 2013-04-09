package com.craftfire.taboo;

public interface Layer {
    TabooPlayer getPlayer(String username);

    void broadcast(String message);

    void executeCommand(String command);
}
