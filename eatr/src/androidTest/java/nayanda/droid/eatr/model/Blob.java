package nayanda.droid.eatr.model;

/**
 * Created by nayanda on 13/02/18.
 */

public class Blob {
    private String container;
    private String reff;

    public Blob(String container, String reff) {
        this.container = container;
        this.reff = reff;
    }

    public String getContainer() {
        return container;
    }

    public void setContainer(String container) {
        this.container = container;
    }

    public String getReff() {
        return reff;
    }

    public void setReff(String reff) {
        this.reff = reff;
    }
}
