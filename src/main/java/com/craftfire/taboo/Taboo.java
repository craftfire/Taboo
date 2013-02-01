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

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.regex.Pattern;

public class Taboo {
    private static Random random = new Random();

    private final String name;
    private List<Pattern> patterns;
    private List<Action> actions = new ArrayList<Action>();
    private List<String> substitutions = new ArrayList<String>();
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

    public List<Action> getActions() {
        return this.actions;
    }

    public List<String> getSubstitutions() {
        return this.substitutions;
    }


}
