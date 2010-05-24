package com.sonatype.beaker.core.handler;

import com.sonatype.beaker.core.Handler;
import com.sonatype.beaker.core.Meep;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.XppDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.zip.Adler32;
import java.util.zip.Checksum;

/**
 * ???
 *
 * @author <a href="mailto:jason@planet57.com">Jason Dillon</a>
 * @since 0.1
 */
public class StreamHandler
    implements Handler
{
    private static final Logger log = LoggerFactory.getLogger(StreamHandler.class);

    private final Writer out;

    private final XStream xstream;

    //
    // TODO: Add checksum (for entire stream, not each meep)
    //

    //
    // TODO: Allow stream to be compressed on the fly.
    //
    
    public StreamHandler() {
        this.xstream = new XStream(new XppDriver());
        this.xstream.autodetectAnnotations(true);
        this.out = getWriter();

        // HACK
        try {
            writeHeader();
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    protected Writer getWriter() {
        return new OutputStreamWriter(System.out);
    }

    protected void writeHeader() throws IOException {
        // HACK
        out.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
        out.append("<meep-stream>\n");
        out.flush();
    }

    protected void writeFooter() throws IOException {
        // HACK
        out.append("</meep-stream>\n");
        out.flush();
    }

    public synchronized void handle(final Meep meep) throws Exception {
        assert meep != null;

        String xml = xstream.toXML(meep);

        synchronized (out) {
            out.append(xml).append("\n");
            out.flush();
        }
    }

    public synchronized void stop() throws Exception {
        writeFooter();
        out.flush();
    }
}