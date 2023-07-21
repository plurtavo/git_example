package mx.com.prosa.nabhi.misc.rest;

import com.google.common.collect.Lists;
import org.springframework.stereotype.Component;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.Parameter;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.service.contexts.SecurityContext;

import java.util.ArrayList;
import java.util.List;

@Component
public class SwaggerBase {


    public ApiKey apiKey() {
        return new ApiKey( "Bearer", "Authorization", "header" );
    }

    public SecurityContext securityContext() {
        return SecurityContext.builder().securityReferences( defaultAuth() )
                .forPaths( PathSelectors.any() ).build();
    }

    private List < SecurityReference > defaultAuth() {
        AuthorizationScope authorizationScope
                = new AuthorizationScope( "global", "accessEverything" );
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[ 1 ];
        authorizationScopes[ 0 ] = authorizationScope;
        return Lists.newArrayList(
                new SecurityReference( "Bearer", authorizationScopes ) );
    }

    public List < Parameter > signer(){
        ParameterBuilder aParameterBuilder = new ParameterBuilder();
        aParameterBuilder.name( "signer" ).modelRef( new ModelRef( "string" ) ).parameterType( "header" ).required( true ).build();
        List < Parameter > aParameters = new ArrayList <>();
        aParameters.add( aParameterBuilder.build() );
        return aParameters;
    }


}
