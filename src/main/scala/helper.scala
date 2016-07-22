import java.io.FileWriter

import akka.http.model.Uri.Path
import twitter4j.JSONObject

/**
  * Created by rahul on 21/7/16.
  */
object helper {

  def writeJson(path: String,jSONObject: JSONObject)={

      val file: FileWriter = new FileWriter(path,true)
      try {
        file.write(jSONObject.getString("message"))
        System.out.println("Successfully Copied JSON Object to File...")
        System.out.println("\nJSON Object: " + jSONObject)
      }
      catch {
        case e: Exception => {
          System.out.println("Exception : " + e.toString)
        }
      } finally {
        if (file != null) file.close()
      }
    }

}
