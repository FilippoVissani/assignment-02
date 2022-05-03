package pcd.assignment02.gui_application.model

trait TreeGenerator[A]:
    def addNode(node: Node[A]): Unit
    def generateTree(pred:(A, A) => Boolean): List[Node[A]]

object TreeGenerator:
    def apply[A](): TreeGenerator[A] = TreeGeneratorImpl()

    private class TreeGeneratorImpl[A] extends TreeGenerator[A]:
        var _nodes: List[Node[A]] = List()

        def addNode(node: Node[A]): Unit = _nodes = node :: _nodes

        def generateTree(pred:(A, A) => Boolean): List[Node[A]] =
            var roots: List[Node[A]] = List()
            _nodes.foreach(n => {
                val children = _nodes.filter(n2 => pred(n.element, n2.element))
                val newParent = if children.isEmpty then n else Node(n.element).addChildren(children)
                roots = newParent :: _nodes.filter(n2 => n2 != n)
            })
            roots
