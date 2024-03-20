Démarrer une session shell dans le container

```bash
docker exec -it csp-sparkmaster-1 /opt/spark/bin/spark-shell --master spark://localhost:7077
```

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
val parquetfiles = getFiles("/tmp/data_huge/matchdata_split")
// read parquet files into
val resultdf = parquetfiles.map(file => spark.read.parquet(file)).reduce((df1,df2) => df1.union(df2))
//process the group by
val countByActiveWeapon = resultdf.groupBy("activeWeapon").count()
```

Afficher le résultat plusieurs fois

```scala
countByActiveWeapon.show
```

Activer le cache sur le résultat

```scala
countByActiveWeapon.cache
```

Tester d'afficher de nouveau le résultat
