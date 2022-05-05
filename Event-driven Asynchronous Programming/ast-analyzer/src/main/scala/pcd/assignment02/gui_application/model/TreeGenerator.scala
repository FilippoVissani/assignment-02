package pcd.assignment02.gui_application.model

import scala.annotation.tailrec

trait TreeGenerator[A]:
    def addNode(node: Node[A]): Unit
    def generateTree(pred:(A, A) => Boolean): List[Node[A]]
    
object TreeGenerator:
    def apply[A](ordering: Ordering[A]): TreeGenerator[A] = TreeGeneratorImpl(ordering)

    private class TreeGeneratorImpl[A](ordering: Ordering[A]) extends TreeGenerator[A]:
        var _nodes: List[Node[A]] = List()
    
        override def addNode(node: Node[A]): Unit =
            _nodes = node :: _nodes

        override def generateTree(hierarchyGenerator:(A, A) => Boolean): List[Node[A]] =
            @tailrec
            def _generateTree(nodes: List[Node[A]]): List[Node[A]] =
                def isParent(node: Node[A], nodes: List[Node[A]]): Boolean =
                    nodes.exists(n => hierarchyGenerator(node.element, n.element))

                if !nodes.exists(n => isParent(n, nodes)) then nodes
                else
                    val firstParent = nodes.filter(n => isParent(n, nodes)).head
                    val children = nodes.filter(child => hierarchyGenerator(firstParent.element, child.element))
                    val tmp = nodes.filter(n => n != firstParent).filter(n => !children.contains(n))
                    _generateTree(Node(firstParent.element, firstParent.children ::: children) :: tmp)

            _generateTree(_nodes.sortBy(n => n.element)(ordering))
