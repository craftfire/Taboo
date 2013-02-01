package com.craftfire.taboo.actions;

import com.craftfire.taboo.Action;
import com.craftfire.taboo.TabooPlayer;

public class Reprimend extends Action {

    public Reprimend(String[] args) {
        super(args);
        if (args.length < 1) {
            throw new IllegalArgumentException("Missing argument: message");
        }
    }

    @Override
    public void execute(TabooPlayer target) {
        target.sendMessage(getArgs()[0]);
    }

}
