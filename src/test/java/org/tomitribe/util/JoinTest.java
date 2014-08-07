/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.tomitribe.util;

import junit.framework.TestCase;

import java.io.File;
import java.util.Arrays;

public class JoinTest extends TestCase {

    public void test1() throws Exception {

        final String actual = Join.join("&", Arrays.asList(123, "foo", true, new Message("bar")));
        final String expected = "123&foo&true&bar";
        assertEquals(expected, actual);
    }

    public void test2() throws Exception {

        final String actual = Join.join("*", 123, "foo", true, new Message("bar"));
        final String expected = "123*foo*true*bar";
        assertEquals(expected, actual);
    }

    public void test3() throws Exception {

        final String actual = Join.join("*", new Join.NameCallback() {
            @Override
            public String getName(final Object object) {
                return "(" + object + ")";
            }
        }, 123, "foo", true, new Message("bar"));

        final String expected = "(123)*(foo)*(true)*(bar)";
        assertEquals(expected, actual);
    }

    public void test4() throws Exception {

        final String actual = Join.join("*", new Join.NameCallback() {
            @Override
            public String getName(final Object object) {
                return "(" + object + ")";
            }
        }, Arrays.asList(123, "foo", true, new Message("bar")));

        final String expected = "(123)*(foo)*(true)*(bar)";
        assertEquals(expected, actual);
    }

    public void testClassCallback() throws Exception {

        final String actual = Join.join("\n", new Join.ClassCallback(), Yellow.class, Green.class, White.class);

        final String expected = "" +
                "org.tomitribe.util.JoinTest$Yellow\n" +
                "org.tomitribe.util.JoinTest$Green\n" +
                "org.tomitribe.util.JoinTest$White";

        assertEquals(expected, actual);
    }

    public void testMethodCallback() throws Exception {

        final String actual = Join.join("\n", new Join.MethodCallback(),
                Yellow.class.getMethod("yellow", String.class),
                Green.class.getMethod("green", int.class),
                White.class.getMethod("white", boolean.class)
        );

        final String expected = "" +
                "yellow\n" +
                "green\n" +
                "white";

        assertEquals(expected, actual);
    }

    public void testFileCallback() throws Exception {

        final String actual = Join.join("\n", new Join.FileCallback(),
                new File(new File("one"), "yellow"),
                new File(new File("two"), "green"),
                new File(new File("three"), "white")
        );

        final String expected = "" +
                "yellow\n" +
                "green\n" +
                "white";

        assertEquals(expected, actual);
    }

    public static class Message {
        private final String message;

        public Message(final String message) {
            this.message = message;
        }

        @Override
        public String toString() {
            return message;
        }
    }

    public static class Yellow {
        public void yellow(String s) {
        }
    }

    public static class Green {
        public void green(int i) {
        }
    }

    public static class White {
        public void white(boolean b) {
        }
    }
}
