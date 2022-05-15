package pcd.assignment02.reactive_programming.utils

object Logger:
  private var send: Int = 0
  private var receive: Int = 0

  def logSend(message: String): Unit =
    synchronized {
      println(s"S${send + 1} ${message} " + Thread.currentThread().getName)
      send = send + 1
    }

  def logReceive(message: String): Unit =
    synchronized {
      println(s"R${receive + 1} ${message} " + Thread.currentThread().getName)
      receive = receive + 1
    }
