package org.scalatra.example.model

case class AppRequest(appId: String, appName: String, queue: String, priority: Int,
                      amContainerSpec: Map[String, Map[String, String]], unmanagedAM: Boolean, maxAppAttempts: Int,
                      resource: Map[String, String], appType: String, kcaaa: Boolean)
