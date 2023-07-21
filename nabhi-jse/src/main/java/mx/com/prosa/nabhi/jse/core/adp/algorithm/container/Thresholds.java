package mx.com.prosa.nabhi.jse.core.adp.algorithm.container;

public class Thresholds {

    private int maxOfNotes;
    private int minimumAmount;

    public Thresholds() {
    }

    public Thresholds( int maxOfNotes, int minimumAmount ) {
        this.maxOfNotes = maxOfNotes;
        this.minimumAmount = minimumAmount;
    }

    public int getMaxOfNotes() {
        return maxOfNotes;
    }

    public void setMaxOfNotes( int maxOfNotes ) {
        this.maxOfNotes = maxOfNotes;
    }

    public int getMinimumAmount() {
        return minimumAmount;
    }

    public void setMinimumAmount( int minimumAmount ) {
        this.minimumAmount = minimumAmount;
    }
}
