package io.kubernetes.client.extended.controller;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import io.kubernetes.client.common.KubernetesObject;
import io.kubernetes.client.extended.controller.reconciler.Request;
import io.kubernetes.client.openapi.models.V1ObjectMeta;
import java.util.concurrent.ThreadFactory;
import java.util.function.Function;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** The Controllers is a set of commonly used utility functions for constructing controller. */
public class Controllers {

  private static final Logger log = LoggerFactory.getLogger(Controllers.class);

  /**
   * The Default key func function works for work-queue, which extracts namespace and name via
   * reflection from the objects.
   *
   * @param <ApiType> the type parameter
   * @return the function
   */
  public static <ApiType extends KubernetesObject>
      Function<ApiType, Request> defaultReflectiveKeyFunc() {
    return (ApiType obj) -> {
      V1ObjectMeta objectMeta = obj.getMetadata();
      return new Request(objectMeta.getNamespace(), objectMeta.getName());
    };
  }

  /**
   * Named thread factory for constructing controller, useful when debugging dumping status of
   * controller worker threads. e.g. for a controller named `foo`, its threads will be named
   * `foo-1`, `foo-2`...
   *
   * @param controllerName the controller name
   * @return the thread factory
   */
  public static ThreadFactory namedControllerThreadFactory(String controllerName) {
    return new ThreadFactoryBuilder().setNameFormat(controllerName + "-%d").build();
  }
}
