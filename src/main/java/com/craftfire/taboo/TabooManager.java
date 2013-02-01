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
package com.craftfire.taboo;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.craftfire.commons.yaml.YamlManager;

public class TabooManager {
    private YamlManager config;
    private List<Taboo> taboos;
    private boolean onlyOnce;
    private Map<String, Action> actions;

    public TabooManager(YamlManager config) {
        this.config = config;
        loadConfig();
    }

    public void loadConfig() {
        // TODO
    }

    public String processMessage(String message, TabooPlayer player) {
        Iterator<Taboo> i = this.taboos.iterator();
        while (i.hasNext()) {
            Taboo taboo = i.next();
            if (taboo.matches(message, player)) {
                executeActions(taboo, player, message);
                message = taboo.replace(message);
                if (this.onlyOnce) {
                    break;
                }
            }
        }
        return message;
    }

    protected void executeActions(Taboo taboo, TabooPlayer player, String message) {
        Iterator i = taboo.getActions().iterator();
        while (i.hasNext()) {
            Action action = this.actions.get(i.next());
            if (action != null) {
                action.execute(player, taboo, message);
            }
        }
    }

}
