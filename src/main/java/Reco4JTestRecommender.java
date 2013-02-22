import org.apache.log4j.Logger;
import org.reco4j.graph.engine.RecommenderEngine;
import org.reco4j.graph.neo4j.Neo4jGraph;
import org.reco4j.graph.neo4j.engine.RecommenderNeo4jEngine;
import org.reco4j.graph.recommenders.IRecommender;
import org.reco4j.graph.recommenders.RecommenderEvaluator;
import org.reco4j.session.RecommenderSessionManager;

import java.io.IOException;
import java.util.Properties;

public class Reco4JTestRecommender
{
    private static Logger logger = Logger.getLogger(Reco4JTestRecommender.class);

    public Neo4jGraph learningDataSet;
    public Neo4jGraph testingDataSet;

    public static void main(String[] args) {

        Properties properties = loadProperties();

        /*
        Reco4JTestRecommender reco = new Reco4JTestRecommender();
        reco.setUP(properties);
        IRecommender recommender = RecommenderEngine.buildRecommender(reco.learningDataSet, properties);
        RecommenderEngine.evaluateRecommender(reco.testingDataSet, recommender);
        */

        RecommenderNeo4jEngine reco = new RecommenderNeo4jEngine();
        reco.setUP(properties);
        IRecommender recommender = RecommenderEngine.createRecommender(properties);
        recommender.loadRecommender(reco.getLearningDataSet());
        recommender.buildRecommender(reco.getLearningDataSet());
        //IRecommender recommender = RecommenderEngine.buildRecommender(reco.getLearningDataSet(), properties);
        //IRecommender recommender = RecommenderEngine.loadRecommender(reco.getLearningDataSet(), properties);
        //RecommenderPropertiesHandle.getInstance().setProperties(properties);
        RecommenderEvaluator.evaluateRecommender(reco.getTestingDataSet(), recommender);

    }

    public void setUP(Properties properties)
    {
        /*
        InternalAbstractGraphDatabase graphdb = new EmbeddedGraphDatabase("./data/movielens.db");

        WrappingNeoServerBootstrapper srv = new WrappingNeoServerBootstrapper( graphdb );
        srv.start();
        */

        learningDataSet = new Neo4jGraph();
        learningDataSet.setProperties(properties);
        learningDataSet.initDatabase();
        RecommenderSessionManager.getInstance().setLearningDataSet(learningDataSet);

        testingDataSet = new Neo4jGraph();
        testingDataSet.setProperties(properties);
        testingDataSet.setDatabase(learningDataSet.getGraphDB());
        testingDataSet.setIsTest(true);
        RecommenderSessionManager.getInstance().setTestingDataSet(testingDataSet);
    }

    public static Properties loadProperties()
    {
        Properties properties = new Properties();
        try
        {
            properties.load(Reco4JTestRecommender.class.getResourceAsStream("init.properties"));
        }
        catch (IOException ex)
        {
            logger.error("Error while loading properties", ex);
        }
        return properties;
    }
}