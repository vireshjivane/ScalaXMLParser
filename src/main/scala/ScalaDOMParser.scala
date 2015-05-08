import javax.xml.xpath.XPathFactory
import javax.xml.parsers._
import javax.xml.xpath.XPathConstants;
import org.w3c.dom._

object ScalaDOMParser extends App {

/***********************************************************/  

  val domFactory = DocumentBuilderFactory.newInstance()
			domFactory.setNamespaceAware(true);

  val builder = domFactory.newDocumentBuilder()
			val doc = builder.parse("input.xml")


/***********************************************************/

			parser(doc)

/***********************************************************/
      
/**
 * @param doc
 * This function will execute an XPath expression to fetch all leaf element from a dom Document 
 */

def parser(doc : Document) : Unit = {

	  	val xpath = XPathFactory.newInstance().newXPath()
			val expr = xpath.compile("//*[not(*)]") // This XPath Expression will fetch all the leaf nodes
			val result = expr.evaluate(doc, XPathConstants.NODESET)
			
      val nodes = result.asInstanceOf[NodeList]

			var counter = 0

            //The NodeList can be converted to an Iterable collection. For the time being I have used simple for.
						for (counter <- 0 to nodes.getLength-1) 
						{
							leafHandler(nodes.item(counter));
						}
	}   
  
 /***********************************************************/
	
 /**
 * @param node
 * This function works on each leaf node supplied by parser and fetches the key value pair.
 * key = path from current node to root of document
 * value = Text Content of current node.
 */

 def leafHandler(node : Node) : Unit = {

		val path = getXPath(node)
		val value = node.getTextContent

    println(path + "=> " + value)
    
 }

/***********************************************************/
	/**
	 * @param node
	 * @return
   * Build the XPath for from root of the document to current given node. 
	 */
	def getXPath(node : Node) : String = {

  	if(node.getParentNode == null)
		{
			return "";
		}
		return getXPath(node.getParentNode) + "/" + node.getNodeName
	}

/***********************************************************/

}


/***********************************************************/

/* Output for reference */

/*

/data/authors/author/name => Robert Roberts
/data/authors/author/address => 10 Tenth St, Decapolis
/data/authors/author/editor => Ella Ellis
/data/authors/author/ms => ftp://docs/rr-10
/data/authors/author/born => 1960/05/26
/data/authors/author/name => Tom Thomas
/data/authors/author/address => 2 Second Av, Duo-Duo
/data/authors/author/editor => Ella Ellis
/data/authors/author/ms => ftp://docs/tt-2
/data/authors/author/name => Mark Marks
/data/authors/author/address => 1 Premier, Maintown
/data/authors/author/editor => Ella Ellis
/data/authors/author/ms => ftp://docs/mm-1
/data/editors/editor/name => Ella Ellis
/data/editors/editor/telephone => 7356

*/

/***********************************************************/
