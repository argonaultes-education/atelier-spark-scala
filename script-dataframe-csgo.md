
Définition d'une fonction `getFiles` pour lister en récursif les fichiers d'un dossier donné en paramètre.

```scala
//get list of files
import java.io.File

def getFiles(dir : String):List[File] = {
     val directory = new File(dir)
     if (directory.exists && directory.isDirectory) {
        directory.listFiles.map(fileItem => getFiles(fileItem.getAbsolutePath)).toList.flatten
     }
     else if (directory.exists && directory.isFile) {
        List[File](directory)
     } else {
        List[File]()
     }
}
```

```scala
//use function
val parquetfiles = getFiles("/tmp/yourfolder")
// read parquet files into
val resultdf = parquetfiles.map(file => spark.read.parquet(file.getAbsolutePath)).reduce((df1,df2) => df1.union(df2))
//process the group by
val countByActiveWeapon = resultdf.groupBy("activeWeapon").count()
```