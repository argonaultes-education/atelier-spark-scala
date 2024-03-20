import java.io.File

def getFiles(dir : String):List[String] = {
     val directory = new File(dir)
     if (directory.exists && directory.isDirectory) {
        directory.listFiles.map(fileItem => getFiles(fileItem.getAbsolutePath)).toList.flatten
     }
     else if (directory.exists && directory.isFile) {
        List[String](directory.getAbsolutePath)
     } else {
        List[String]()
     }
}