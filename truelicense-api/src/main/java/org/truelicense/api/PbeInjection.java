/*
 * Copyright (C) 2005-2015 Schlichtherle IT Services.
 * All rights reserved. Use is subject to license terms.
 */

package org.truelicense.api;

import org.truelicense.api.misc.Injection;
import org.truelicense.api.passwd.PasswordProtection;

/**
 * Injects a Password Based Encryption (PBE) into some target.
 *
 * @param <Target> the type of the target.
 * @author Christian Schlichtherle
 */
public interface PbeInjection<Target>
extends Injection<Target> {

    /**
     * Sets the algorithm name (optional).
     *
     * @return {@code this}
     */
    PbeInjection<Target> algorithm(String algorithm);

    /**
     * Sets the password for generating a secret key for
     * encryption/decryption.
     *
     * @return {@code this}
     */
    PbeInjection<Target> password(PasswordProtection password);
}
