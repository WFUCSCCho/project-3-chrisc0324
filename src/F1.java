/**
 * @file: F1.java
 * @description: This class defines the F1 class that stores numerous variables for each driver and performs essential methods
 * @author: Chris Cha {@literal <chah22@wfu.edu>}
 * @date: September 24, 2025
 */
import java.util.Arrays;

public class F1 implements Comparable<F1> {
    private String driver;
    private String nationality;
    private String[] seasons;
    private int championships;
    private int raceWins;
    private int podiums;
    private double points;

    /* Default Constructor */
    public F1() {
        this.driver = "";
        this.nationality = "";
        this.seasons = new String[0];
        this.championships = 0;
        this.raceWins = 0;
        this.podiums = 0;
        this.points = 0.0;
    }

    /* Parametrized Constructor */
    public F1(String driver, String nationality, String[] seasons, int championships, int raceWins, int podiums, double points) {
        this.driver = driver;
        this.nationality = nationality;
        this.seasons = (seasons == null) ? new String[0] : seasons;
        this.championships = championships;
        this.raceWins = raceWins;
        this.podiums = podiums;
        this.points = points;
    }

    /* Copy Constructor */
    public F1(F1 other) {
        this(other.driver, other.nationality, other.seasons, other.championships, other.raceWins, other.podiums, other.points);
    }

    /* Returns a string with basic driver information */
    @Override
    public String toString() {
        String driverInfo;
        driverInfo = "Driver: " + driver + " Nationality: " + nationality + " championships: " + championships + " races won: " + raceWins + " podiums: " + podiums;
        return driverInfo;
    }

    /* Compares one driver with another based on performance stats */
    @Override
    public int compareTo(F1 input) {
        if (input == null) return -1;

        int c = Integer.compare(input.getChampionships(), this.getChampionships());
        if (c != 0) return c;

        c = Integer.compare(input.getRaceWins(), this.getRaceWins());
        if (c != 0) return c;

        c = Integer.compare(input.getPodiums(), this.getPodiums());
        if (c != 0) return c;

        c = Double.compare(input.getPoints(), this.getPoints());
        if (c != 0) return c;

        int thisSeasons = (this.getSeasons() == null) ? 0 : this.getSeasons().length;
        int thatSeasons = (input.getSeasons() == null) ? 0 : input.getSeasons().length;
        c = Integer.compare(thatSeasons, thisSeasons);
        if (c != 0) return c;

        /* stable tie-breaker */
        String dn1 = this.getDriver() == null ? "" : this.getDriver();
        String dn2 = input.getDriver() == null ? "" : input.getDriver();
        c = dn1.compareToIgnoreCase(dn2);
        if (c != 0) return c;

        String nat1 = this.getNationality() == null ? "" : this.getNationality();
        String nat2 = input.getNationality() == null ? "" : input.getNationality();
        return nat1.compareToIgnoreCase(nat2);
    }

    /* Checks the equality of two drivers */
    @Override
    public boolean equals(Object o) {
        int x = this.compareTo((F1) o);
        if (x == 0) {
            return true;
        }
        else {
            return false;
        }
    }

    /* Getter methods for driver information */
    public String getDriver() { return driver; }
    public String getNationality() { return nationality; }
    public String[] getSeasons() { return Arrays.copyOf(seasons, seasons.length); }
    public int getChampionships() { return championships; }
    public int getRaceWins() { return raceWins; }
    public int getPodiums() { return podiums; }
    public double getPoints() { return points; }
}