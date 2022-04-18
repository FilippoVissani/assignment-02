package pcd.assignment02

trait FieldInfo:
    def name: String
    def fieldType: String
    def parent: ClassReport
    override def toString: String =
        s"name: $name, fieldType: $fieldType, parent: ${parent.name}"

trait MutableFieldInfo extends FieldInfo:
    def name_(name: String): Unit
    def fieldType_(fieldType: String): Unit
    def parent_(parent: ClassReport): Unit

object MutableFieldInfo:
    def apply(name: String, fieldType: String, parent: ClassReport): MutableFieldInfo =
        MutableFieldInfoImpl(name, fieldType, parent)

    private case class MutableFieldInfoImpl(var _name: String,
                                            var _fieldType: String,
                                            var _parent: ClassReport) extends MutableFieldInfo:
        override def name: String = _name
        override def fieldType: String = _fieldType
        override def parent: ClassReport = _parent
        override def name_(name: String): Unit = _name = name
        override def fieldType_(fieldType: String): Unit = _fieldType = fieldType
        override def parent_(parent: ClassReport): Unit = _parent = parent
