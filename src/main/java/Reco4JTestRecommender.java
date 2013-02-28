import org.apache.log4j.Logger;
import org.reco4j.graph.INode;
import org.reco4j.graph.Rating;
import org.reco4j.graph.engine.RecommenderEngine;
import org.reco4j.graph.neo4j.engine.RecommenderNeo4jEngine;
import org.reco4j.graph.recommenders.IRecommender;
import org.reco4j.util.RecommenderPropertiesHandle;

import java.io.IOException;
import java.util.Properties;

public class Reco4JTestRecommender
{
    private static Logger logger = Logger.getLogger(Reco4JTestRecommender.class);

    public static void main(String[] args) {

        logger.info("START");

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
        /*
        recommender.buildRecommender(reco.getLearningDataSet());
        */

        //IRecommender recommender = RecommenderEngine.buildRecommender(reco.getLearningDataSet(), properties);
        //IRecommender recommender = RecommenderEngine.loadRecommender(reco.getLearningDataSet(), properties);
        //RecommenderPropertiesHandle.getInstance().setProperties(properties);

        /*
        RecommenderEvaluator.evaluateRecommender(reco.getTestingDataSet(), recommender);
        */

        for (INode user : reco.getLearningDataSet().getNodesByType(RecommenderPropertiesHandle.getInstance().getUserType()))
        {
            String userId = user.getProperty(RecommenderPropertiesHandle.getInstance().getUserIdentifierName());

            if (userId.equalsIgnoreCase("15"))
                for (Rating rating : recommender.recommend(user))
                {
                    logger.info("Item 12: Item: " + rating.getItem().getProperty("title") + " rating: " + rating.getRate());
                }

        }

        /* not jet implemented
        Node justOneNode = reco.getLearningDataSet().getGraphDB().getNodeById(12);
        for (Rating rating : recommender.recommend(new BasicNode(justOneNode))) {
            logger.info("Item 12: Item: " + rating.getItem().getProperty("title") + " rating: " + rating.getRate());
        }
        */

        logger.info("END");
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