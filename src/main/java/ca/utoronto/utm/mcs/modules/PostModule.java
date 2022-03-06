package ca.utoronto.utm.mcs.modules;

import ca.utoronto.utm.mcs.models.Post;
import dagger.Module;
import dagger.Provides;

import javax.inject.Singleton;

@Module
public class PostModule {

    @Provides
    @Singleton
    public Post providePost() {
        return new Post();
    }
}
