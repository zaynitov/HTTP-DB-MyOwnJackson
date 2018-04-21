import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

public class PropertyFileHandler {
    private Integer port;
    private String root;

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getRoot() {
        return root;
    }

    public void setRoot(String root) {
        this.root = root;
    }

    public void initPortAndRoot(String properyFileDestination) throws Exception {
        Properties prop = new Properties();
        InputStream input = null;
        input = new FileInputStream(properyFileDestination);
        prop.load(input);
        setPort(Integer.parseInt(prop.getProperty("port")));
        setRoot(prop.getProperty("root"));
    }
}
