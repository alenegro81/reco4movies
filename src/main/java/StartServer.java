import org.apache.log4j.Logger;
import org.neo4j.kernel.EmbeddedGraphDatabase;
import org.neo4j.kernel.InternalAbstractGraphDatabase;
import org.neo4j.server.WrappingNeoServerBootstrapper;


public class StartServer {

    private static Logger logger = Logger.getLogger(ImportMovieLensDataApp.class);

    public static void main(String[] args)
    {
        InternalAbstractGraphDatabase graphdb = new EmbeddedGraphDatabase("./data/movielens.db");

        WrappingNeoServerBootstrapper srv = new WrappingNeoServerBootstrapper( graphdb );
        srv.start();

    }
}
