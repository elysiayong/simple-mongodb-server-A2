package ca.utoronto.utm.mcs.converter;

import ca.utoronto.utm.mcs.components.DaggerPostComponent;
import ca.utoronto.utm.mcs.models.Post;
import com.mongodb.DBObject;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.json.JSONArray;

import java.util.ArrayList;

public class PostConverter {

    public static Document toDocument(Post p) {
        return new Document("title", p.getTitle())
                .append("author", p.getAuthor())
                .append("content", p.getContent())
                .append("tags", p.getTags());
    }

    public static Post toPost(DBObject doc) {
        Post p = DaggerPostComponent.create().buildPost();
        p.setTitle((String) doc.get("title"));
        p.setAuthor((String) doc.get("author"));
        p.setContent((String) doc.get("content"));
        p.setTags((ArrayList<String>) doc.get("tags"));
        ObjectId id = (ObjectId) doc.get("_id");
        p.set_id(id.toString());
        return p;
    }
}
