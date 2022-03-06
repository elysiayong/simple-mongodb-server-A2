package ca.utoronto.utm.mcs.components;

import ca.utoronto.utm.mcs.models.Http;
import ca.utoronto.utm.mcs.modules.HttpModule;
import dagger.Component;

import javax.inject.Singleton;

@Singleton
@Component(modules = HttpModule.class)
public interface HttpComponent {

    public Http buildHttpServer();
}
