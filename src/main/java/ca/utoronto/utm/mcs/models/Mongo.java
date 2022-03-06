package ca.utoronto.utm.mcs.models;

import ca.utoronto.utm.mcs.converter.PostConverter;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;

import static com.mongodb.client.model.Filters.*;
import org.bson.Document;
import org.json.JSONException;
import java.lang.IllegalArgumentException;
import org.json.JSONObject;
import javax.inject.Inject;
import org.bson.types.ObjectId;

public class Mongo {

    private MongoClient db;
    private MongoCollection<Document> col;

    @Inject

    public Mongo(MongoClient db) {
        this.db = db;
        col = this.db.getDatabase("csc301a2").getCollection("posts");
    }

    public MongoCollection<Document> getCol() {
        return this.col;
    }

    public MongoClient getDb() {
        return this.db;
    }

    public void setDb(MongoClient db) {
        this.db = db;
    }

    public String createPost(Post p) {
        Document doc = PostConverter.toDocument(p);
        col.insertOne(doc);

        Document data = this.col.find(doc).first();
        if (data == null) {
            return null;
        }

        JSONObject response = new JSONObject();
        try {
            response.put("_id", data.get("_id").toString());
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

        return response.toString();
    }

    public FindIterable<Document> getPost(String id, String title) {
        // Search ID first
        if (id != null){
            try {
                ObjectId objectId = new ObjectId(id);
                return this.col.find(eq("_id", objectId));
            } catch (IllegalArgumentException ignored) {} // ID IS NOT HEX !
        }

        // Search title instead
        if (title!= null) {
            return this.col.find(regex("title", ".*" + title + ".*"));
        }

        return null;
    }

    public int deletePost(String id) {
        if (id != null){
            try {
                ObjectId objectId = new ObjectId(id);
                if(col.deleteOne(eq ("_id", objectId)).getDeletedCount() > 0) return 200;
                else return 404;
            } catch (IllegalArgumentException i) { return 400; } // ID IS NOT HEX !
        } return 400;
    }
}
