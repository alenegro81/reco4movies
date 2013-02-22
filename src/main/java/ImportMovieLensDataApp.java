import org.apache.log4j.Logger;
import org.reco4j.graph.neo4j.Neo4jGraph;
import org.reco4j.graph.neo4j.gremlin.GremlinGraphLoadingUtil;
import org.reco4j.graph.neo4j.util.Neo4JPropertiesHandle;

import java.io.IOException;
import java.util.Properties;

public class ImportMovieLensDataApp
{
    private static Logger logger = Logger.getLogger(ImportMovieLensDataApp.class);

    public static void main(String[] args)
    {
        Properties properties = loadProperties();

        Neo4jGraph graphDB = new Neo4jGraph();
        graphDB.setProperties(properties);
        graphDB.initDatabase();
        graphDB.loadGraph();

        GremlinGraphLoadingUtil.loadMovieLensDataSet(graphDB.getGraphDB(), Neo4JPropertiesHandle.getInstance().getMovieLensBasePath());
    }

    private static Properties loadProperties()
    {
        Properties properties = new Properties();
        try
        {
            properties.load(ImportMovieLensDataApp.class.getResourceAsStream("init.properties"));
        }
        catch (IOException ex)
        {
            logger.error("Error while loading properties", ex);
        }
        return properties;
    }
}

