package se.grunka.basic.client;

import javax.ws.rs.Consumes;
import javax.ws.rs.CookieParam;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.MatrixParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.core.util.MultivaluedMapImpl;

public class JerseyProxyClient {
	//TODO a metric shit ton of testing...
	@SuppressWarnings("unchecked")
	public static <T> T build(WebResource resource, Class<T> type) {
		return (T) Proxy.newProxyInstance(type.getClassLoader(), new Class<?>[]{type}, createInvocationHandler(resource, type));
	}

	private static <T> InvocationHandler createInvocationHandler(final WebResource resource, final Class<T> type) {
		return new InvocationHandler() {
			private final WebResource baseResource = resource.path(type.getAnnotation(Path.class).value());

			@Override
			public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
				//TODO don't create base resource until all potential path params have been found
				//TODO if @PathParam annotations are present replace those in methodPath before creating requestBuilder
				//TODO if @QueryParam annotations are present add to baseResource.queryParam
				//TODO if @HeaderParam annotations are present add to requestBuilder.header()
				WebResource webResource = baseResource;
				Annotation[][] parameterAnnotations = method.getParameterAnnotations();
				MultivaluedMapImpl queryParameters = new MultivaluedMapImpl();
				MultivaluedMapImpl formParameters = new MultivaluedMapImpl();
				for (int parameter = 0; parameter < args.length; parameter++) {
					Annotation[] annotations = parameterAnnotations[parameter];
					for (Annotation annotation : annotations) {
						if (annotation instanceof QueryParam) {
							queryParameters.add(((QueryParam) annotation).value(), args[parameter]);
						} else if (annotation instanceof FormParam) {
							formParameters.add(((FormParam) annotation).value(), args[parameter]);
						} else if (annotation instanceof HeaderParam) {
							//TODO collect and set after all parameters have been processed
							//requestBuilder.header(((HeaderParam) annotation).value(), args[parameter]);
						} else if (annotation instanceof CookieParam) {
							//TODO collect and set after all parameters have been processed
							//requestBuilder.cookie(new Cookie(((CookieParam) annotation).value(), String.valueOf(args[parameter])));
						} else if (annotation instanceof MatrixParam) {
							//TODO multivalued map to store all values and then add after all parameters have been processed
							//webResource.getUriBuilder().matrixParam(((MatrixParam) annotation).value(), args[parameter]);
						}
					}
				}
				if (!queryParameters.isEmpty()) {
					webResource = webResource.queryParams(queryParameters);
				}
				Path methodPath = method.getAnnotation(Path.class);
				WebResource.Builder requestBuilder;
				if (methodPath != null) {
					requestBuilder = webResource.path(methodPath.value()).getRequestBuilder();
				} else {
					requestBuilder = webResource.getRequestBuilder();
				}
				Produces produces = method.getAnnotation(Produces.class);
				if (produces != null) {
					requestBuilder = requestBuilder.accept(produces.value());
				}
				Consumes consumes = method.getAnnotation(Consumes.class);
				if (consumes != null) {
					//TODO check if this is correct?
					requestBuilder.type(consumes.value()[0]);
				}
				if (method.isAnnotationPresent(GET.class)) {
					return requestBuilder.get(method.getReturnType());
				} else if (method.isAnnotationPresent(POST.class)) {
					if (formParameters.isEmpty()) {
						return requestBuilder.post(method.getReturnType());
					} else {
						return requestBuilder.post(method.getReturnType(), formParameters);
					}
				} else {
					throw new UnsupportedOperationException();
				}
			}
		};
	}
}
