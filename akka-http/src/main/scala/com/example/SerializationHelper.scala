package com.example

import org.json4s.FieldSerializer
import org.json4s.FieldSerializer.renameTo

object SerializationHelper {
  val rename = FieldSerializer[AppRequest](renameTo("appId", "application-id") orElse renameTo("appName", "application-name")
    orElse renameTo("amContainerSpec", "am-container-spec") orElse renameTo("unmanagedAM", "unmanaged-AM")
    orElse renameTo("maxAppAttempts", "max-app-attempts") orElse renameTo("appType", "application-type")
    orElse renameTo("kcaaa", "keep-containers-across-application-attempts"))

}
