/*
 * Copyright (C) 2005-2017 Schlichtherle IT Services.
 * All rights reserved. Use is subject to license terms.
 */

package global.namespace.truelicense.tests.v2.xml

import global.namespace.truelicense.tests.core.LicenseVendorPerformance

/** @author Christian Schlichtherle */
object V2XmlLicenseVendorPerformance
extends LicenseVendorPerformance with V2XmlTestContext {
  def main(args: Array[String]): Unit = call()
}
