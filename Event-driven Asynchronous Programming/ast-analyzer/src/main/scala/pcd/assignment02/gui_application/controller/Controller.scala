package pcd.assignment02.gui_application.controller

import io.vertx.core.Vertx

trait Controller:
    def startVerticle(): Unit
    def stopVerticle(): Unit

object Controller:
    def apply(): Controller = ControllerImpl()

    private class ControllerImpl extends Controller:
        val vertx = Vertx.vertx()

        override def startVerticle(): Unit =
            vertx.deployVerticle(MyVerticle())

        override def stopVerticle(): Unit =
            vertx.deploymentIDs().forEach(deploymentId => {
                vertx.undeploy(deploymentId, res =>
                    if res.failed then
                        println("Undeploy failed " + deploymentId)
                    else
                        println("Undeploy succeeded " + deploymentId)
                )
            });
