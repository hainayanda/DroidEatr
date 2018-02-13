package nayanda.droid.eatr.model;

/**
 * Created by nayanda on 13/02/18.
 */

public class Phone {
    private String number;
    private boolean primary;

    public Phone(String number, boolean primary) {
        this.number = number;
        this.primary = primary;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public boolean isPrimary() {
        return primary;
    }

    public void setPrimary(boolean primary) {
        this.primary = primary;
    }
}
