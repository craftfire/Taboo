package com.craftfire.taboo;

public interface TabooPlayer {

    String getName();

    boolean checkPermission(String node);

    void sendMessage(String message);

    void kick(String message);
}
