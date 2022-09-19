package co.zw.sanders.graphqlserver.provider;

import java.io.IOException;
import java.nio.file.Files;
import javax.annotation.PostConstruct;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import graphql.GraphQL;
import graphql.schema.GraphQLSchema;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import graphql.schema.idl.TypeDefinitionRegistry;
import graphql.schema.idl.TypeRuntimeWiring;

@Component
@Slf4j
@RequiredArgsConstructor
public class GraphQLProvider {
    private GraphQL graphQL;
    private final GraphQLDataFetcher dataFetcher;
    @Value("${graphql.path}")
    private String GRAPHQL_FILE_PATH;

    @PostConstruct
    public void init() {
        final Resource resource = new ClassPathResource(GRAPHQL_FILE_PATH);
        String sdl = null;
        try {
            sdl = Files.readString(resource.getFile().toPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
        GraphQLSchema graphQLSchema = buildSchema(sdl);
        this.graphQL = GraphQL.newGraphQL(graphQLSchema).build();
    }

    private GraphQLSchema buildSchema(String sdl) {
        TypeDefinitionRegistry typeRegistry = new SchemaParser().parse(sdl);
        RuntimeWiring runtimeWiring = buildWiring();
        SchemaGenerator schemaGenerator = new SchemaGenerator();
        return schemaGenerator.makeExecutableSchema(typeRegistry, runtimeWiring);
    }

    private RuntimeWiring buildWiring() {
        return RuntimeWiring.newRuntimeWiring()
                .type(TypeRuntimeWiring.newTypeWiring("Query")
                        .dataFetcher("people", dataFetcher.getPeopleList()))
                .type(TypeRuntimeWiring.newTypeWiring("Query")
                        .dataFetcher("person", dataFetcher.getPersonByName()))
                .build();
    }
    @Bean
    public GraphQL initiateGraphQL() {
        return graphQL;
    }
}
