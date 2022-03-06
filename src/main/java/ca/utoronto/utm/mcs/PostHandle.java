package ca.utoronto.utm.mcs;

import ca.utoronto.utm.mcs.components.DaggerMongoComponent;
import ca.utoronto.utm.mcs.components.DaggerPostComponent;
import ca.utoronto.utm.mcs.models.Mongo;
import ca.utoronto.utm.mcs.models.Post;
import com.mongodb.client.FindIterable;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.bson.Document;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class PostHandle implements HttpHandler {

    public void handle(HttpExchange r) throws IOException {
        try {
            switch (r.getRequestMethod()) {
                case "PUT":
                    handlePut(r);
                    break;
                case "GET":
                    handleGet(r);
                    break;
                case "DELETE":
                    handleDelete(r);
                    break;
                default:
                    r.sendResponseHeaders(405, -1);
                    break;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void handlePut(HttpExchange r) throws IOException {
        String reqbody = Utils.convert(r.getRequestBody());
        try {
            JSONObject dsrl = new JSONObject(reqbody);
            String title = null;
            String author = null;
            String content = null;
            ArrayList<String> tags = null;

            if (dsrl.has("title")) title = dsrl.getString("title");
            if (dsrl.has("author")) author = dsrl.getString("author");
            if (dsrl.has("content")) content = dsrl.getString("content");
            if (dsrl.has("tags")){
                // Reference: https://stackoverflow.com/questions/17037340/converting-jsonarray-to-arraylist
                tags = new ArrayList<String>();
                JSONArray jsonArray = (JSONArray)dsrl.get("tags");
                if (jsonArray != null) {
                    for (int i=0;i<jsonArray.length();i++){
                        tags.add(jsonArray.getString(i));
                    }
                }
            }

            System.out.println(tags);

            if (title == null || author == null || content == null || tags == null) {
                r.sendResponseHeaders(400, -1);
            } else {
                Post post = DaggerPostComponent.create().buildPost();
                post.setTitle(title);
                post.setAuthor(author);
                post.setContent(content);
                post.setTags(tags);
                try {
                    Mongo mongo = DaggerMongoComponent.create().buildMongoDB();
                    String response = mongo.createPost(post);
                    if (response == null) {
                        r.sendResponseHeaders(500, -1);
                    } else {
                        byte[] resbody = response.getBytes();
                        r.sendResponseHeaders(200, resbody.length);
                        OutputStream os = r.getResponseBody();
                        os.write(resbody);
                        os.close();
                    }
                } catch (Exception e) {
                    r.sendResponseHeaders(500, -1);
                }
            }

        } catch (JSONException e) {
            r.sendResponseHeaders(400, -1);
        }
    }

    private void handleGet(HttpExchange r) throws IOException{
        String reqBody = Utils.convert(r.getRequestBody());

        try {
            JSONObject request = new JSONObject(reqBody);
            String id = null;
            String title = null;

            if (request.has("_id")) id = request.getString("_id");
            if (request.has("title")) title = request.getString("title");

            if (title == null && id == null) {
                r.sendResponseHeaders(400, -1);

            } else {
                try {
                    Mongo mongo = DaggerMongoComponent.create().buildMongoDB();
                    FindIterable<Document> data = mongo.getPost(id, title);

                    if (data== null || data.first()==null){ // DNE?
                        r.sendResponseHeaders(404, -1);

                    } else {
                        List<String> response = new ArrayList<>();
                        for (Document d: data) {
                            response.add(d.toJson());
                        }

                        byte[] resBody = response.toString().getBytes();
                        r.sendResponseHeaders(200, resBody.length);
                        OutputStream os = r.getResponseBody();
                        os.write(resBody);
                        os.close();
                    }
                } catch (Exception e) {
                    r.sendResponseHeaders(500, -1);
                }
            }

        } catch (JSONException e) {
            r.sendResponseHeaders(400, -1);
        }
    }

  private void handleDelete(HttpExchange r) throws IOException {

        String reqBody = Utils.convert(r.getRequestBody());

        try {
            JSONObject request = new JSONObject(reqBody);
            String id = null;

            if (request.has("_id")) id = request.getString("_id");

            if (id == null) {
                r.sendResponseHeaders(400, -1);

            } else {
                try {
                    Mongo mongo = DaggerMongoComponent.create().buildMongoDB();
                    r.sendResponseHeaders(mongo.deletePost(id), -1);
                } catch (Exception e) {
                    r.sendResponseHeaders(500, -1);
                }
            }

        } catch (JSONException e) {
            r.sendResponseHeaders(400, -1);
        }
    }
}
