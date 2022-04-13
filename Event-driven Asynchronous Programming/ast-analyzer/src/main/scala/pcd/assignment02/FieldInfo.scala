package pcd.assignment02

trait FieldInfo:
    def name: String
    def fieldType: String
    def parent: ClassReport

object FieldInfo:
    def apply(name: String, fieldType: String, parent: ClassReport): FieldInfo =
        FieldInfoImpl(name, fieldType, parent)

    private case class FieldInfoImpl(override val name: String,
                                     override val fieldType: String,
                                     override val parent: ClassReport) extends FieldInfo
