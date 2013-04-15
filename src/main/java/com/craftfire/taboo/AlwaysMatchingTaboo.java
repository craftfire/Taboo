package com.craftfire.taboo;

import com.craftfire.commons.yaml.YamlException;

public class AlwaysMatchingTaboo extends Taboo {

    public AlwaysMatchingTaboo(TabooManager manager) throws YamlException, TabooException {
        super(manager);
    }

    @Override
    public boolean matches(String message, TabooPlayer player) {
        return true;
    }
}
