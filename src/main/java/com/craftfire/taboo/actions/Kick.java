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
