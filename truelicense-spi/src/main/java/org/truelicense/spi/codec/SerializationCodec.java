/*
 * Copyright (C) 2005-2015 Schlichtherle IT Services.
 * All rights reserved. Use is subject to license terms.
 */

package org.truelicense.spi.codec;

import org.truelicense.api.codec.Codec;
import org.truelicense.api.codec.Decoder;
import org.truelicense.api.codec.Encoder;
import org.truelicense.api.io.Sink;
import org.truelicense.api.io.Source;
import org.truelicense.obfuscate.Obfuscate;

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.lang.reflect.Type;

/**
 * A codec which encodes/decodes an object with an
 * {@link ObjectOutputStream}/{@link ObjectInputStream}.
 * This class is immutable.
 *
 * @author Christian Schlichtherle
 */
public class SerializationCodec implements Codec {

    @Obfuscate
    private static final String CONTENT_TYPE = "application/x-java-serialized-object";

    @Obfuscate
    private static final String CONTENT_TRANSFER_ENCODING = "binary";

    /**
     * {@inheritDoc}
     * <p>
     * The implementation in the class {@link SerializationCodec}
     * returns {@code "application/x-java-serialized-object"}.
     *
     * @see java.awt.datatransfer.DataFlavor
     */
    @Override public String contentType() { return CONTENT_TYPE; }

    /**
     * {@inheritDoc}
     * <p>
     * The implementation in the class {@link SerializationCodec}
     * returns {@code "binary"}.
     *
     * @see <a href="http://tools.ietf.org/html/rfc2045">RFC 2045</a>
     */
    @Override public String contentTransferEncoding() {
        return CONTENT_TRANSFER_ENCODING;
    }

    @Override
    public Encoder to(final Sink sink) {
        return new Encoder() {
            @Override
            public void encode(final Object obj) throws Exception {
                try (OutputStream out = sink.output();
                     ObjectOutputStream oos = new ObjectOutputStream(out)) {
                    oos.writeObject(obj);
                }
            }
        };
    }

    @Override
    public Decoder from(final Source source) {
        return new Decoder() {
            @Override
            @SuppressWarnings("unchecked")
            public <T> T decode(final Type expected) throws Exception {
                try (InputStream in = source.input();
                     ObjectInputStream oin = new ObjectInputStream(in)) {
                    return (T) oin.readObject();
                }
            }
        };
    }
}
