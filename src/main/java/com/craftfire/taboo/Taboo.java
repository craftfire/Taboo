package com.craftfire.taboo;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.regex.Pattern;

public class Taboo {
    private static Random random = new Random();

    private final String name;
    private List<Pattern> patterns;
    private List<String> actions;
    private List<String> substitutions;
    private final String includePermission, excludePermission;

    public Taboo(String name, String includePermission, String excludePermission) {
        this.name = name;
        this.includePermission = includePermission;
        this.excludePermission = excludePermission;
    }

    public String getName() {
        return this.name;
    }

    public boolean matches(String message, TabooPlayer player) {
        // TODO
        return false;
    }

    public String replace(String message) {
        // TODO
        return message;
    }

    public List<String> getActions() {
        return new ArrayList(this.actions);
    }

    public List<String> getSubstitutions() {
        return new ArrayList(this.substitutions);
    }


}
