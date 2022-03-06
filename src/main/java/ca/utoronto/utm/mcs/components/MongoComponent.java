package ca.utoronto.utm.mcs.components;

import ca.utoronto.utm.mcs.models.Mongo;
import ca.utoronto.utm.mcs.modules.MongoModule;
import dagger.Component;

import javax.inject.Singleton;

@Singleton
@Component(modules = MongoModule.class)
public interface MongoComponent {

    public Mongo buildMongoDB();
}
