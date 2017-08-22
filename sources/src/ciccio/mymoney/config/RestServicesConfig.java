package ciccio.mymoney.config;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.core.Application;

import ciccio.mymoney.providers.JsonProvider;
import ciccio.mymoney.resources.BeneficiarioResource;
import ciccio.mymoney.resources.BilancioMensileResource;
import ciccio.mymoney.resources.CategoriaResource;
import ciccio.mymoney.resources.ContoResource;
import ciccio.mymoney.resources.MensileDefaultResource;

public class RestServicesConfig extends Application {

    @Override
    public Set<Class<?>> getClasses() {
	Set<Class<?>> classes = new HashSet<Class<?>>();

	// Resources
	classes.add(BeneficiarioResource.class);
	classes.add(BilancioMensileResource.class);
	classes.add(CategoriaResource.class);
	classes.add(ContoResource.class);
	classes.add(MensileDefaultResource.class);

	// Providers
	classes.add(JsonProvider.class);

	return classes;
    }

}