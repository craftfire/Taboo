package com.craftfire.taboo.actions;

import com.craftfire.taboo.Action;
import com.craftfire.taboo.TabooPlayer;

public class Kick extends Action {

    public Kick(String[] args) {
        super(args);
    }

    @Override
    public void execute(TabooPlayer target) {
        if (getArgs().length > 1) {
            target.kick(getArgs()[0]);
        } else {
            target.kick(null);
        }
    }

}
