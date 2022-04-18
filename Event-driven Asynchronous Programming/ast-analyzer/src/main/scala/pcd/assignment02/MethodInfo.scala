package pcd.assignment02

trait MethodInfo:
    def name: String
    def beginLine: Int
    def endLine: Int
    def parent: ClassReport
    override def toString: String =
        s"name: $name, beginLine: $beginLine, endLine: $endLine, parent: ${parent.name}"

trait MutableMethodInfo extends MethodInfo:
    def name_(name: String): Unit
    def beginLine_(beginLine: Int): Unit
    def endLine_(endLine: Int): Unit
    def parent_(parent: ClassReport): Unit

object MutableMethodInfo:
    def apply(name: String, beginLine: Int, endLine: Int, parent: ClassReport): MutableMethodInfo =
        MutableMethodInfoImpl(name, beginLine, endLine, parent)

    private case class MutableMethodInfoImpl(var _name: String,
                                             var _beginLine: Int,
                                             var _endLine: Int,
                                             var _parent: ClassReport) extends MutableMethodInfo:
        override def parent: ClassReport = _parent
        override def parent_(parent: ClassReport): Unit = _parent = parent
        override def name: String = _name
        override def name_(name: String): Unit = _name = name
        override def beginLine: Int = _beginLine
        override def beginLine_(beginLine: Int): Unit = _beginLine = beginLine
        override def endLine: Int = _endLine
        override def endLine_(endLine: Int): Unit = _endLine = endLine