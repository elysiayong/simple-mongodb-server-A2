package ca.utoronto.utm.mcs.modules;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import dagger.Module;
import dagger.Provides;

@Module
public class MongoModule {

    private static MongoClient db;

    @Provides
    public MongoClient provideMongoClient() {
        db = MongoClients.create();
        return db;
    }

}
