package com.sonatype.beaker.core.marshal;

import com.sonatype.beaker.core.Meep;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.XppDriver;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

/**
 * ???
 *
 * @author <a href="mailto:jason@planet57.com">Jason Dillon</a>
 * @since 0.1
 */
public class Unmarshaller
    extends MarshalSupport
{
    private ObjectInputStream input;

    public Unmarshaller(final InputStream input) throws IOException {
        assert input != null;
        this.input = xstream.createObjectInputStream(input);
    }

    public Meep unmarshal() throws IOException, ClassNotFoundException {
        return unmarshal(input);
    }

    public void close() throws IOException {
        // nothing
    }
}