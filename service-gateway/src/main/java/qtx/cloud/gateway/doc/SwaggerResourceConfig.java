package qtx.cloud.gateway.doc;

import java.util.ArrayList;
import java.util.List;
import org.springframework.cloud.gateway.config.GatewayProperties;
import org.springframework.cloud.gateway.support.NameUtils;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;

/**
 * 获取网关路由判断哪些路由需要使用文档
 *
 * @author jmac
 * @since 2022-08-28
 */
@Primary
@Component
public class SwaggerResourceConfig implements SwaggerResourcesProvider {

  private final GatewayProperties gatewayProperties;

  public SwaggerResourceConfig(GatewayProperties gatewayProperties) {
    this.gatewayProperties = gatewayProperties;
  }

  @Override
  public List<SwaggerResource> get() {
    List<SwaggerResource> resources = new ArrayList<>();

    // resources为所有路由都加载到文档，如果需要部分显示，在下方使用filter进行过滤即可
    gatewayProperties
        .getRoutes()
        .forEach(
            route ->
                route.getPredicates().stream()
                    .filter(
                        predicateDefinition ->
                            ("Path").equalsIgnoreCase(predicateDefinition.getName()))
                    .forEach(
                        predicateDefinition ->
                            resources.add(
                                swaggerResource(
                                    route.getId(),
                                    predicateDefinition
                                        .getArgs()
                                        .get(NameUtils.GENERATED_NAME_PREFIX + "0")
                                        .replace("**", "v2/api-docs")))));
    return resources;
  }

  private SwaggerResource swaggerResource(String name, String url) {
    SwaggerResource swaggerResource = new SwaggerResource();
    swaggerResource.setName(name);
    swaggerResource.setLocation(url);
    swaggerResource.setUrl(url);
    swaggerResource.setSwaggerVersion("2.0");
    return swaggerResource;
  }
}
