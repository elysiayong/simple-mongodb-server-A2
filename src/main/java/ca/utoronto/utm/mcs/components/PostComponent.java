package ca.utoronto.utm.mcs.components;

import ca.utoronto.utm.mcs.models.Post;
import ca.utoronto.utm.mcs.modules.PostModule;
import dagger.Component;

import javax.inject.Singleton;

@Singleton
@Component(modules = PostModule.class)
public interface PostComponent {
    public Post buildPost();
}
