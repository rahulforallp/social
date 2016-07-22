import com.restfb.*;
import com.restfb.experimental.api.Facebook;
import com.restfb.json.JsonObject;
import com.restfb.types.FacebookType;
import com.restfb.types.Page;
import com.restfb.types.Post;
import com.restfb.types.User;
import twitter4j.JSONObject;

import java.io.FileWriter;
import java.util.List;


/**
 * Created by rahul on 20/7/16.
 */

public class facebook {

    private static final DefaultFacebookClient facebookClient = new DefaultFacebookClient("EAACEdEose0cBAHqps9woVLIolA5CL0IBZCcQ0TV7Q0larfZAXi4OyBenLhvhZC9yProMmiZAqk6nnW5tb7PcK4i2xQZAK9E9YCw66YYeUKKck9ScZC7TUZCFHwqILyerV2UZAzDQXiCoTzFqWxLi8lZCVTwx9PRi99Q7hgGOFGnLOvjtoN3gvMTWU", Version.VERSION_2_5);
    /*private static final Page page = facebookClient.fetchObject("<page id>", Page.class);*/
    public static void updateStatus(String message, String link) {
        facebookClient.publish("/feed", FacebookType.class, Parameter.with("message", message), Parameter.with("link", link));
    }

    public static JSONObject newsFeed(){
        Connection<Post> myFeed = facebookClient.fetchConnection("me/feed", Post.class);
        List <Post> newsfeed = myFeed.getData();
        JSONObject timelinejson = new JSONObject();

        for(Post aFeed : newsfeed){
            try {
                timelinejson.append("message", aFeed.getMessage());
            }
            catch (Exception e){
            }
           //System.out.println("Message : "+aFeed.getMessage() + " , Likes : " + aFeed.getLikes()+" , comments : "+aFeed.getComments() +" , Pictures : " + aFeed.getPicture());
        }
       /* helper.writeJson("src/main/resources/appTimeline.json",timelinejson);*/
        return timelinejson;
    }

    public static void main(String[] args) {
        facebook.newsFeed();
       /* // working - only message appearing in status
        facebook.updateStatus("RestFB test2", "");
        // working correctly - message and photo in status
        facebook.updateStatus("RestFB test3", "https://www.facebook.com/mullingar.parish/photos/a.341019462759743.1073741826.341019429426413/431550013706687/?type=3");
        // working correctly - message and video in status
        facebook.updateStatus("RestFB test4", "https://www.facebook.com/RuairiOgclg/videos/574647342685093/");*/

    }

}
