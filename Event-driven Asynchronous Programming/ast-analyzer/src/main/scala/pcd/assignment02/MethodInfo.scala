package pcd.assignment02

trait MethodInfo:
    def name: String
    def beginLine: Int
    def endLine: Int
    def parent: ClassReport

object MethodInfo:
    def apply(name: String, beginLine: Int, endLine: Int, parent: ClassReport): MethodInfo =
        MethodInfoImpl(name, beginLine, endLine, parent)
    
    private case class MethodInfoImpl(override val name: String,
                                      override val beginLine: Int,
                                      override val endLine: Int,
                                      override val parent: ClassReport) extends MethodInfo


