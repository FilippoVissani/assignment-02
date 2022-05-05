package pcd.assignment02.gui_application.model

import scala.annotation.tailrec
import scala.collection.mutable

trait Node[A]:
    def element: A
    def children: List[Node[A]]
    def addChild(child: Node[A]): Node[A]
    def addChildren(children: List[Node[A]]): Node[A]
    def map[B](fun: A => B): Node[B]
    def nodeToString(builder: mutable.StringBuilder, tabs: String): Unit
    def foreach(consumer: A => Unit): Unit

object Node:
    def apply[A](element: A, children: List[Node[A]]): Node[A] = NodeImpl[A](element, children)

    private case class NodeImpl[A](override val element: A,
                                   override val children: List[Node[A]]) extends Node[A]:

        override def addChild(child: Node[A]): Node[A] = Node(element, child :: children)

        override def addChildren(_children: List[Node[A]]): Node[A] = Node(element, children ::: _children)

        override def map[B](fun: A => B): Node[B] = Node(fun(element), children.map(c => c.map(fun)))

        override def nodeToString(builder: mutable.StringBuilder, tabs: String): Unit =
            builder.append(tabs + element + "\n")
            children.foreach(c => c.nodeToString(builder, tabs + "      "))

        override def foreach(consumer: A => Unit): Unit =
            consumer(element)
            children.foreach(c => c.foreach(consumer))

