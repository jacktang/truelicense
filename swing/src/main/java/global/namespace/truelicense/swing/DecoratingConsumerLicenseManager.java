/*
 * Copyright (C) 2005-2017 Schlichtherle IT Services.
 * All rights reserved. Use is subject to license terms.
 */

package global.namespace.truelicense.swing;

import global.namespace.fun.io.api.Source;
import global.namespace.truelicense.api.ConsumerLicenseManager;
import global.namespace.truelicense.api.License;
import global.namespace.truelicense.api.LicenseManagementException;
import global.namespace.truelicense.api.LicenseManagementSchema;

/**
 * A decorator for a consumer license manager.
 * This class is immutable.
 *
 * @author Christian Schlichtherle
 */
abstract class DecoratingConsumerLicenseManager implements ConsumerLicenseManager {

    @SuppressWarnings({"PackageVisibleField"})
    protected final ConsumerLicenseManager manager;

    DecoratingConsumerLicenseManager(final ConsumerLicenseManager manager) {
        assert null != manager;
        this.manager = manager;
    }

    @Override
    public LicenseManagementSchema schema() { return manager.schema(); }

    @Override
    public void install(Source source) throws LicenseManagementException { manager.install(source); }

    @Override
    public License load() throws LicenseManagementException { return manager.load(); }

    @Override
    public void verify() throws LicenseManagementException { manager.verify(); }

    @Override
    public void uninstall() throws LicenseManagementException { manager.uninstall(); }
}
