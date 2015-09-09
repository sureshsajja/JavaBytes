/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2015 CodeRevisited.com
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 */

package com.coderevisited.serialization;

import org.junit.Assert;
import org.junit.Test;

import java.io.*;

/**
 * User :  Suresh
 * Date :  09/09/15
 * Version : v1
 */
public class ImmutableSerializationTest {

    @Test
    public void expectMutableExternalizationTest() throws IOException, ClassNotFoundException {
        ImmutableSerialization employee = new ImmutableSerialization("Suresh", 1234);

        ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream("temp.txt"));
        outputStream.writeObject(employee);
        outputStream.close();

        ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream("temp.txt"));
        ImmutableSerialization _employee = (ImmutableSerialization) inputStream.readObject();
        Assert.assertEquals(employee.toString(), _employee.toString());
        Assert.assertEquals(employee.getId(), _employee.getId());
        Assert.assertEquals(employee.getName(), _employee.getName());
    }
}
