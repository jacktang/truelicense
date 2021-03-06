/*
 * Copyright (C) 2005-2017 Schlichtherle IT Services.
 * All rights reserved. Use is subject to license terms.
 */

package global.namespace.truelicense.tests.v2.json

import global.namespace.truelicense.tests.core.LicenseVendorPerformance

/** @author Christian Schlichtherle */
object V2JsonLicenseVendorPerformance
extends LicenseVendorPerformance with V2JsonTestContext {
  def main(args: Array[String]): Unit = call()
}
