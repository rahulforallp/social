
import twitter4j.JSONObject;
import twitter4j.Status;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;
import java.util.*;

/**
 * Created by rahul on 20/7/16.
 */
public class twitter {
    static int i = 0;

    public static void main(String args[]) {
        newsfeed();
    }

public static JSONObject newsfeed(){
        // confriguring twitter
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
                .setOAuthConsumerKey("Czegep3ehFkxweWMYe9LCN9Gj")
                .setOAuthConsumerSecret("H6xOQyTzI5cOKJmzyqNmX6DCvEwMDoOYZ7mVZjyZdqo8rgjQoJ")
                .setOAuthAccessToken("755698297222811650-5oY9t3L7QrQrmAwskmU1M0i1JP88Aab")
                .setOAuthAccessTokenSecret("xvI0xeZXfxQoExbS61SR81rmmTVi5SLluLmbd6Y4XLfXB");

        // creating facory object
        TwitterFactory tf = new TwitterFactory(cb.build());
        List<Post> userstatus = new ArrayList<>();
        twitter4j.Twitter tw = tf.getInstance();
        JSONObject twitterTimeline = new JSONObject();
        try {
            List<Status> listOfTimeline = tw.getHomeTimeline();
            for (Status status : listOfTimeline) {
                String message = status.getText();
                Date date = status.getCreatedAt();
                int likes = status.getFavoriteCount();
                String user = status.getUser().toString();
                int retweet = status.getRetweetCount();
                userstatus.add(i, new Post(message, date, retweet, likes, user));
                i++;
            }


            System.out.println("Sorting...");
            Collections.sort(userstatus, Comparator.comparing(Post::getpostinDate).reversed()
                    .thenComparing(Post::getNoOfRetweets).reversed()
                    .thenComparing(Post::getNoOfLikes).reversed());
            //userstatus.forEach(System.out::println);
            for (Post post : userstatus) {
                try {
                    twitterTimeline.append("message", post.getStatus());
                } catch (Exception e) {

                }
            }
      /*     helper.writeJson("src/main/resources/appTimeline.json",twitterTimeline);*/
            System.out.println("sorted by Latest Post");

            /*Collections.sort(userstatus, Comparator.comparing(Post::getpostinDate).reversed());
            userstatus.forEach(System.out::println);
            System.out.println("sorted by retweets");

            Collections.sort(userstatus, Comparator.comparing(Post::getNoOfRetweets).reversed());
            userstatus.forEach(System.out::println);

            System.out.println("sorted by likes");
            Collections.sort(userstatus, Comparator.comparing(Post::getNoOfLikes).reversed());
            userstatus.forEach(System.out::println);*/

            /*tw.updateStatus("Rahul Social App "+ now() );*/

            return twitterTimeline;
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return new JSONObject();
        }

    }


    public static class Post {
        String Status;
        Date postingDate;
        int retweet;
        int noOfLikes;
        String Name;

        Post(String Status, Date postingDate, int retweet, int noOfLikes, String Name) {

            this.Status = Status;
            this.postingDate = postingDate;
            this.retweet = retweet;
            this.noOfLikes = noOfLikes;
            this.Name = Name;
        }

        public String getStatus() {
            return Status;
        }
        public Date getpostinDate() {
            return postingDate;
        }
        public int getNoOfLikes() {
            return noOfLikes;
        }
        public String getName() {
            return Name;
        }
        public int getNoOfRetweets() {
            return retweet;
        }

        @Override
        public String toString() {

            return "tweets{" +
                    "status='" + Status + '\'' +
                    ",Name='" + Name.substring(30, 60) + '\'' +
                    ",retweet='" + retweet + '\'' +
                    ",likes='" + noOfLikes + '\'' +
                    '}';
        }
    }
}
