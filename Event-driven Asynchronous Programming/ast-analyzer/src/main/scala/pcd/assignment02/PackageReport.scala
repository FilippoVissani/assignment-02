package pcd.assignment02

trait PackageReport:
    def name: String
    def path: String
    def methods: List[MethodInfo]
    def fields: List[FieldInfo]

object PackageReport:
    def apply(name: String,
              path: String,
              methods: List[MethodInfo],
              fields: List[FieldInfo]): PackageReport = PackageReportImpl(name, path, methods, fields)
    
    private case class PackageReportImpl(override val name: String,
                                         override val path: String,
                                         override val methods: List[MethodInfo],
                                         override val fields: List[FieldInfo]) extends PackageReport
