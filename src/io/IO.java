package io;

/**
 *
 *   This is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License as published by
 *   the Free Software Foundation, either version 3 of the License, or
 *   (at your option) any later version.
 *
 *   This is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
 *
 *   You should have received a copy of the GNU General Public License
 *   along with Foobar.  If not, see <http://www.gnu.org/licenses/>.
 *
 *  Authors:
 *  Christopher McMorran (100968013)
 *  James Mulvenna (100965629)
 *  Jenish Zalavadiya (100910343)
 *
 */
public class IO {
    /**
     * Determines if a given String can be converted to an Integer value.
     * @param someValue
     * @return
     */
    public static boolean isInteger(String someValue) {
        try {
            Integer.valueOf(someValue);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
