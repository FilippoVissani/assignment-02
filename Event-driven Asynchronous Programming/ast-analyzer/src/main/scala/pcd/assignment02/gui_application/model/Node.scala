package pcd.assignment02.gui_application.model

import pcd.assignment02.gui_application.model.Node.Leaf

enum Node[A]:
    case Subtree(e: A, c: List[Node[A]])
    case Leaf(e: A)

    def element: A = this match
        case Subtree(e, _) => e
        case Leaf(e) => e

    def children: Option[List[Node[A]]] = this match
        case Subtree(_, c) => Some(c)
        case Leaf(_) => None

    def addChild(child: Node[A]): Node[A] = this match
        case Subtree(e, c) => Subtree(e, child :: c)
        case Leaf(e) => Subtree(e, List(child))

    def addChildren(children: List[Node[A]]): Node[A] = this match
        case Subtree(e, c) => Subtree(e, c ::: children)
        case Leaf(e) => Subtree(e, children)

    def foreach(consumer: A => Unit): Unit = this match
        case Subtree(e, c) => consumer(e); c.foreach(n => n.foreach(consumer))
        case _ =>

object Node:
    def apply[A](e: A): Node[A] = Leaf(e)
