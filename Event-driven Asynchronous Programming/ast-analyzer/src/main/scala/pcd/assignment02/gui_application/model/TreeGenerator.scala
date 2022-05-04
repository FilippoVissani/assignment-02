package pcd.assignment02.gui_application.model

trait TreeGenerator[A]:
    def addNode(node: Node[A]): Unit
    def generateTree(pred:(A, A) => Boolean): List[Node[A]]
    
object TreeGenerator:
    def apply[A](sorter:(A, A) => Boolean): TreeGenerator[A] = TreeGeneratorImpl(sorter)

    private class TreeGeneratorImpl[A](sorter:(A, A) => Boolean) extends TreeGenerator[A]:
        var _nodes: List[Node[A]] = List()
    
        def addNode(node: Node[A]): Unit =
            _nodes = node :: _nodes
            _nodes = _nodes.sortWith((a, b) => sorter(a.element, b.element))
            println()
    
        def generateTree(hierarchyGenerator:(A, A) => Boolean): List[Node[A]] =
            var roots: List[Node[A]] = _nodes
            roots.foreach(parent => {
                val children = roots.filter(child => hierarchyGenerator(parent.element, child.element))
                val newParent = if children.isEmpty then parent else Node(parent.element, parent.children ::: children)
                roots = roots.filter(r => r != parent)
                roots = roots.filter(r => !children.contains(r))
                roots = newParent :: roots
            })
            roots
