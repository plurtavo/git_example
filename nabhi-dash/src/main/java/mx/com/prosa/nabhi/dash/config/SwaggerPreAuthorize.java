package mx.com.prosa.nabhi.dash.config;

import org.springframework.core.annotation.Order;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.OperationBuilderPlugin;
import springfox.documentation.spi.service.contexts.OperationContext;
import springfox.documentation.swagger.common.SwaggerPluginSupport;

import java.util.Optional;

@Component
@Order( SwaggerPluginSupport.SWAGGER_PLUGIN_ORDER )
public class SwaggerPreAuthorize implements OperationBuilderPlugin {


    @Override
    public void apply( final OperationContext context ) {
        Optional.ofNullable( context.findAnnotation( PreAuthorize.class )
                .or( context.findControllerAnnotation( PreAuthorize.class ) )
                .orNull() )
                .ifPresent( preAuth -> context.operationBuilder()
                        .notes( "Privilegio para acceder: **" + preAuth.value() + "**" ) );
    }

    @Override
    public boolean supports( final DocumentationType delimiter ) {
        return SwaggerPluginSupport.pluginDoesApply( delimiter );
    }
}