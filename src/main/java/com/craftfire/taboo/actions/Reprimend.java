/*
 * This file is part of Taboo.
 *
 * Copyright (c) 2013-2013, CraftFire <http://www.craftfire.com/>
 * Taboo is licensed under the GNU Lesser General Public License.
 *
 * Taboo is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Taboo is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.craftfire.taboo.actions;

import com.craftfire.taboo.Action;
import com.craftfire.taboo.Taboo;
import com.craftfire.taboo.TabooPlayer;

import com.craftfire.commons.yaml.YamlException;
import com.craftfire.commons.yaml.YamlNode;

public class Reprimend extends Action {

    public Reprimend(YamlNode args) {
        super(args);
        if (!args.hasChild("message")) {
            throw new IllegalArgumentException("Missing argument: message");
        }
    }

    @Override
    public void execute(TabooPlayer target, Taboo taboo, String message) throws YamlException {
        target.sendMessage(getArgs().getChild("message").getString());
    }

}
