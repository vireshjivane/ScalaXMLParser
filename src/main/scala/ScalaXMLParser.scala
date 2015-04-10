import scala.xml._
import scala.io.Source
import scala.collection.mutable.ArrayBuffer

object ScalaXMLParser extends App{

  val xml = XML.loadFile("input.xml")
  val allNodesFromFile = xml \ "_"
  val root = xml.label

  var keys = new ArrayBuffer[String]
  var values = new ArrayBuffer[String]
  
  parser(root, allNodesFromFile)
  displayParsedElements()
 
  
  /* Method to handle the leaf nodes */
  def leafHandler(path: String, node: scala.xml.Node): Unit =
    {
      keys += path
      values += node.text
    }

  
  /* Method to traverse through intermediate nodes and pass leaf nodes to leafHandler() */
  def parser(root: String, nodeSequence: scala.xml.NodeSeq): Unit =
    {

      nodeSequence.foreach { node =>

        {
          if ((node \ "_").isEmpty) {
            var path: String = ""
            path += root + "/" + node.label + "/"
            leafHandler(path, node)
          }
          if ((node \ "_").isEmpty == false) {

            var tpath: String = ""
            tpath = root + "/" + node.label

            val nextLevelNodeSequence = (node \ "_")

            nextLevelNodeSequence.foreach { node =>
              {
                parser(tpath, node) //Recursive call.
              }
            }
          }
        }
      }
    }

  def displayParsedElements(): Unit = {

    (keys, values).zipped foreach { (key, value) => println("key : %s \t\t value : %s".format(key, value)) }
  }
}
  
  
