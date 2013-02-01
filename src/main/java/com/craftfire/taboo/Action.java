package com.craftfire.taboo;


public abstract class Action {
    private String[] args;

    public Action(String[] args) {
        this.args = args;
    }

    public String[] getArgs() {
        return this.args;
    }

    public abstract void execute(TabooPlayer target);

}
