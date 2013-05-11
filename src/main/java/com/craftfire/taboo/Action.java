/*
 * This file is part of Taboo.
 *
 * Copyright (c) 2013 CraftFire <http://www.craftfire.com/>
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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.craftfire.commons.yaml.YamlNode;

public abstract class Action {
    private YamlNode args;

    public Action(YamlNode args) {
        this.args = args;
    }

    public YamlNode getArgs() {
        return this.args;
    }

    public abstract void execute(TabooPlayer target, Taboo taboo, String message);

    public static String format(String text, Taboo taboo, TabooPlayer player, String message) {
        if (text == null) {
            return null;
        }
        String str = text;
        str = str.replaceAll("<player>", Matcher.quoteReplacement(player.getName()));
        str = str.replaceAll("<taboo>", Matcher.quoteReplacement(taboo.getName()));
        if (taboo.getReplacement() != null) {
            str = str.replaceAll("<replacement>", Matcher.quoteReplacement(taboo.getReplacement())); // TODO parse $groups in replacement in context of matched message
        }
        str = str.replaceAll("<mesage>", Matcher.quoteReplacement(message));

        for (Pattern pattern : taboo.getPatterns()) {
            Matcher matcher = pattern.matcher(message);
            if (matcher.find()) {
                str = str.replaceAll("<match>", Matcher.quoteReplacement(matcher.group()));
                for (int i = 0; i <= matcher.groupCount(); ++i) {
                    str = str.replaceAll("<" + i + ">", Matcher.quoteReplacement(matcher.group(i)));
                }
                break;
            }
        }
        str = str.replaceAll("/<", "<");
        str = str.replaceAll("/>", ">");
        return str;
    }
}
