package io.kubernetes.client.informer.cache;

import io.kubernetes.client.common.KubernetesListObject;
import io.kubernetes.client.common.KubernetesObject;
import io.kubernetes.client.informer.ListerWatcher;
import io.kubernetes.client.openapi.ApiException;
import io.kubernetes.client.util.CallGeneratorParams;
import io.kubernetes.client.util.Watch;
import io.kubernetes.client.util.Watchable;

public class MockRunOnceListerWatcher<
        ApiType extends KubernetesObject, ApiListType extends KubernetesListObject>
    implements ListerWatcher<ApiType, ApiListType> {

  private ApiListType list;
  private Watch.Response<ApiType>[] events;

  private boolean listExecuted = false;
  private boolean watchExecuted = false;

  public MockRunOnceListerWatcher(ApiListType list, Watch.Response<ApiType>... events) {
    this.list = list;
    this.events = events;
  }

  @Override
  public ApiListType list(CallGeneratorParams params) throws ApiException {
    if (!listExecuted) {
      listExecuted = true;
      return list;
    }
    try {
      Thread.sleep(100000);
    } catch (InterruptedException e) {
    }
    return null;
  }

  @Override
  public Watchable<ApiType> watch(CallGeneratorParams params) throws ApiException {
    if (!watchExecuted) {
      watchExecuted = true;
      return new MockWatch<>(events);
    }
    try {
      Thread.sleep(100000);
    } catch (InterruptedException e) {
    }
    return new MockWatch<>();
  }
}
